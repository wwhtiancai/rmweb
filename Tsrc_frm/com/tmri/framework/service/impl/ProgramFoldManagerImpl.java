package com.tmri.framework.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.tmri.framework.bean.Fold;
import com.tmri.framework.dao.ProgramFoldDao;
import com.tmri.framework.dao.SysParaDao;
import com.tmri.framework.service.ProgramFoldManager;
import com.tmri.pub.util.WrapUtil;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;

/**
 * 程序 目录管理实现
 * 
 * @author long
 * 
 */
@Service

public class ProgramFoldManagerImpl implements ProgramFoldManager{
	@Autowired
	private ProgramFoldDao programFoldDao;
	@Autowired
	private SysParaDao sysparaDao;

	private int maxYjmls=10;// 最大一级目录个数

	private String KGLYJMLDH="107#108#109#";

//	public SysParaDao getSysparaDao(){
//		return sysparaDao;
//	}
//
//	public void setSysparaDao(SysParaDao sysparaDao){
//		this.sysparaDao=sysparaDao;
//	}
//
//	public ProgramFoldDao getProgramFoldDao(){
//		return programFoldDao;
//	}
//
//	public void setProgramFoldDao(ProgramFoldDao programFoldDao){
//		this.programFoldDao=programFoldDao;
//	}

	private Fold getCachedFold(String mldh){
		return programFoldDao.getFold(mldh);
	}
	private int indexOfFoldList(List foldList,String mldh){
		int result = -1;
		Fold fold = null;
		for(int i=0;i<foldList.size();i++){
			fold = (Fold)foldList.get(i);
			if(fold.getMldh().equals(mldh)){
				return i;
			}
		}
		return result;
	}
	public HashMap getFoldMenuStr(SysUser sysUser,HashMap paras){
		HashMap map=new HashMap();
		Program program=null;
		Program programold=null;		
		// 程序目录列表
		List proList=this.programFoldDao.getProgramList(sysUser,paras);
		int dcllb_flag = 0;
		int dsplb_flag = 0;
		if(proList.size()>1){		
			programold = (Program)proList.get(0);
			int i = 1;
			while(i<proList.size()){
				program=(Program)proList.get(i);
				//20111104 jianghailong修改 根据许主任 福建省意见 事故待处理列表和待审批列表不再进行权限判定，而根据是否拥有事故处理权限
				if(program.getCdbh().equals("C103")){
					dcllb_flag = 1;
				}
				if(program.getCdbh().equals("C150")){
					dsplb_flag = 1;
				}
				if(program.getCdbh().equals(programold.getCdbh())&&program.getXtlb().equals(programold.getXtlb())){
					//合并GNLB
					String[] tmp = program.getGnid().split(",");
					for(int j=0;j<tmp.length;j++){
						if(programold.getGnid().indexOf(tmp[j])<0){
							programold.setGnid(programold.getGnid() + "," + tmp[j]);
						}
					}
					proList.remove(i);
				}else{
					i++;
					programold = program;
				}
			}
		}
		map.put("prolist",proList);
		List desktopList=new Vector();
		for(int i=0;i<proList.size();i++){
			program=(Program)proList.get(i);			
			if(program.getCxlx().equals("2")){
				//20111104 jianghailong修改 根据许主任 福建省意见 事故待处理列表和待审批列表不再进行权限判定，而根据是否拥有事故处理权限
				if(program.getCdbh().equals("C920")){
					dcllb_flag = 2;
				}
				if(program.getCdbh().equals("C921")){
					dsplb_flag = 2;
				}
				desktopList.add(program);
			}
		}
	//20111104 jianghailong修改 根据许主任 福建省意见 事故待处理列表和待审批列表不再进行权限判定，而根据是否拥有事故处理权限
		if(dcllb_flag==1){
			program = this.programFoldDao.getOneProgram("03","C920");
			proList.add(program);
			desktopList.add(program);
		}
		if(dsplb_flag==1){
			program = this.programFoldDao.getOneProgram("03","C921");
			proList.add(program);
			desktopList.add(program);
		}
		map.put("desktoplist",desktopList);
		//顶级目录
		Fold fold=null;Fold topFold = null;
		List topfoldList = programFoldDao.getTopFoldList();

		// 1级目录列表
		List foldList1=new Vector();
		// 获取2级目录列表
		List foldList2=programFoldDao.getFoldList2(sysUser,paras);
		// 根据2级目录列表建立1级目录		
		int i=0;int iTop = 0;
		while(i<foldList2.size()){
			fold=(Fold)foldList2.get(i);
			//直接挂在顶级目录下，因无法展现，无法支持			
			if(fold.getSjmldh().equals("0000")){
				iTop = this.indexOfFoldList(topfoldList,fold.getMldh());
				if(iTop>=0){
					topfoldList.remove(iTop);						
				}
				foldList2.remove(i);
				continue;
			}			
			iTop = this.indexOfFoldList(topfoldList,fold.getSjmldh());
			if(iTop>=0){
				//挂顶级目录下的目录，提升为1级目录
				//置顶级目录存在标记
				topFold = (Fold)topfoldList.get(iTop);
				topFold.setBz("1");
				int iPos=-1;
				Fold tmpFold=null;
				for(int j=0;j<foldList1.size();j++){
					tmpFold=(Fold)foldList1.get(j);
					if(tmpFold.getMldh().equals(fold.getMldh())){
						iPos=-2;
						break;
					}
					if(new Integer(fold.getSxh()).intValue()<new Integer(tmpFold.getSxh()).intValue()){
						iPos=j;
						break;
					}
				}
				if(iPos!=-2){
					if(iPos==-1){
						foldList1.add(fold);
					}else{
						foldList1.add(iPos,fold);
					}
				}
				foldList2.remove(i);
			}else{
				//不挂顶级目录下的目录，正常挂在一级目录下
				fold=this.getCachedFold(fold.getSjmldh());				
				//寻找顶级目录，置顶级目录标记，无顶级目录不进行处理
				iTop = this.indexOfFoldList(topfoldList,fold.getSjmldh());
				if(iTop>=0){
					topFold = (Fold)topfoldList.get(iTop);
					topFold.setBz("1");
					
					//一级目录排序处理
					int iPos=-1;
					Fold tmpFold=null;
					for(int j=0;j<foldList1.size();j++){
						tmpFold=(Fold)foldList1.get(j);
						if(fold.getMldh().equals(tmpFold.getMldh())){
							iPos=-2;
							break;
						}
						if(new Integer(fold.getSxh()).intValue()<new Integer(tmpFold.getSxh()).intValue()){
							iPos=j;
							break;
						}
					}
					if(iPos!=-2){
						if(iPos==-1){
							foldList1.add(fold);
						}else{
							foldList1.add(iPos,fold);
						}
					}
					i++;
				}else{
					//无顶级目录不显示
					foldList2.remove(i);
				}
			}
		}
		Fold subFold=null;
//		// 1级目录不够，可提升二级目录为1级目录
//		Fold subFold=null;
//		if(foldList1.size()<this.maxYjmls){
//			for(i=0;i<foldList1.size();i++){
//				fold=(Fold)foldList1.get(i);
//				if(this.KGLYJMLDH.indexOf(fold.getMldh())>=0){
//					fold.setProList(new Vector());
//					for(int k=0;k<foldList2.size();k++){						
//						subFold=(Fold)foldList2.get(k);
//						if(subFold.getSjmldh().equals(fold.getMldh())){
//							fold.getProList().add(subFold);
//						}
//					}
//					if(foldList1.size()+fold.getProList().size()-1<=this.maxYjmls){
//						foldList1.remove(i);
//						for(int k=fold.getProList().size()-1;k>=0;k--){
//							subFold=(Fold)fold.getProList().get(k);
//							foldList1.add(i,subFold);
//						}
//					}
//				}
//			}
//		}
		
		
    String topFoldhtml = "";//顶级目录显示的HTML
		String foldhtml="";// TOP显示HTML
		String html="";// LEFT显示的HTML
		String htmltemp = "";//LEFT显示的临时HTML
		String menuhtml="";// 直接挂在一级目录下菜单HTML
		String subfoldhtml="";// 二级目录及包含的菜单HTML

		String display="";
		String style="";
		String tmp="";
		int iTopindex = 0;
		for(iTop=0;iTop<topfoldList.size();iTop++){
			topFold = (Fold)topfoldList.get(iTop);
			if(topFold.getBz().equals("1")){
				iTopindex ++ ;
				topFoldhtml += "<span id='div_" + iTopindex + "' style='display:none'>" + topFold.getMlmc() + "</span>\n";
				
				if(iTopindex==1){
					foldhtml += "<table border='0' width='100%' class='top_tab_bg' cellspacing='0' cellpadding='0' id='table_" + iTopindex +"' style='display:block' height=\"21\">\n";	
				}else{
					foldhtml += "<table border='0' width='100%' class='top_tab_bg text_white' align='center' cellspacing='0' cellpadding='0' id='table_" + iTopindex +"' style='display:none' height=\"21\">\n";
				}
				
				foldhtml += "<tr>\n";
				int iFold1 = 0;
				htmltemp = "";
				for(i=0;i<foldList1.size();i++){
					
					fold=(Fold)foldList1.get(i);
					if(!fold.getSjmldh().equals(topFold.getMldh())) continue;
					iFold1++;
					int strlen_0=fold.getMlmc().length();
					int len=100;//15px一个中文
					int strlen=strlen_0*15;
					if (strlen>len)
					{len=strlen;}		
					if(iFold1==1){
						//扩充版
						if(fold.getMllb()==null||fold.getMllb().equals("")||fold.getMllb().equals("1")){
							foldhtml+="\t\t<td align='center' class=\"interface_top_1\" id=\"fold"+iTopindex + "_"+iFold1+"\" onClick=\"interfaceDoMenu('"+iTopindex + "_" +iFold1+"')\">"+fold.getMlmc()+"</td>\n\t\t<td width='2' valign='bottom'><img src='theme/style/top_4.png' width='2' height='18' align='absmiddle'></td>\n";
						}else if(fold.getMllb().equals("3")){
							foldhtml+="\t\t<td align='center' class=\"interface_top_1\" id=\"fold"+iTopindex + "_"+iFold1+"\" onClick=\"interfaceDoMenu('"+iTopindex + "_" +iFold1+"')\"><img src=\"theme/style/m4.png\" alt=\"\" border=\"0\" hspace=\"2\" style=\"vertical-align:middle;\"/>"+fold.getMlmc()+"</td>\n\t\t<td width='2' valign='bottom'><img src='theme/style/top_4.png' width='2' height='18' align='absmiddle'></td>\n";
						}
					}else {
						
						//扩充版
						if(fold.getMllb()==null||fold.getMllb().equals("")||fold.getMllb().equals("1")){
							foldhtml+="\t\t<td align='center' class=\"interface_top_0\" id=\"fold"+iTopindex + "_"+iFold1+"\" onClick=\"interfaceDoMenu('"+iTopindex + "_" +iFold1+"')\">"+fold.getMlmc()+"</td>\n\t\t<td width='2' valign='bottom'><img src='theme/style/top_4.png' width='2' height='18' align='absmiddle'></td>\n";
						}else if(fold.getMllb().equals("3")){
							foldhtml+="\t\t<td align='center' class=\"interface_top_0\" id=\"fold"+iTopindex + "_"+iFold1+"\" onClick=\"interfaceDoMenu('"+iTopindex + "_" +iFold1+"')\"><img src=\"theme/style/m4.png\" alt=\"\" border=\"0\" hspace=\"2\" style=\"vertical-align:middle;\"/>"+fold.getMlmc()+"</td>\n\t\t<td width='2' valign='bottom'><img src='theme/style/top_4.png' width='2' height='18' align='absmiddle'></td>\n";
						}
					}
					//判断是否有扩充版，用下面的注释中的代码
//					if(iFold1==1){
//						foldhtml+="\t\t<td align='center' class=\"interface_top_1\" id=\"fold"+iTopindex + "_"+iFold1+"\" onClick=\"interfaceDoMenu('"+iTopindex + "_" +iFold1+"')\"><img src=\"theme/style/m4.png\" alt=\"\" border=\"0\" hspace=\"2\" style=\"vertical-align:middle;\"/>"+fold.getMlmc()+"</td>\n\t\t<td width='2' valign='bottom'><img src='theme/style/top_4.png' width='2' height='18' align='absmiddle'></td>\n";
//					}else {
//						foldhtml+="\t\t<td align='center' class=\"interface_top_0\" id=\"fold"+iTopindex + "_"+iFold1+"\" onClick=\"interfaceDoMenu('"+iTopindex + "_" +iFold1+"')\"><img src=\"theme/style/m4.png\" alt=\"\" border=\"0\" hspace=\"2\" style=\"vertical-align:middle;\"/>"+fold.getMlmc()+"</td>\n\t\t<td width='2' valign='bottom'><img src='theme/style/top_4.png' width='2' height='18' align='absmiddle'></td>\n";						
//					}
					// 输出子目录
					int m=0;
					subfoldhtml="";
					for(int k=0;k<foldList2.size();k++){
						subFold=(Fold)foldList2.get(k);
						if(subFold.getSjmldh().equals(fold.getMldh())){
							if(m==0){
								display="block";
								style="m2j_1";
							}else{
								display="none";
								style="m2j_0";
							}
							tmp="";
							// 输出子目录下菜单
							for(int l=0;l<proList.size();l++){
								program=(Program)proList.get(l);
								if(program.getCxlx().equals("1")){
									if(program.getMldh().equals(subFold.getMldh())){
										
										String target="businessjcbk"+sysUser.getYhdh();
										if(program.getCkdx()==null||program.getCkdx().equals("")||program.getCkdx().equals("2")){
											tmp+="\t<tr>\n\t\t<td width=\"182\" id=\"img"
													+l
													+"\" class=\"text_12 text_3j_bg text_3j\" onMouseOver=\"this.className='text_12 text_3j_bg_over text_3j'\" onMouseOut=\"this.className='text_12 text_3j_bg text_3j'\">\n<img src=\"theme/blue/check.gif\" hspace=\"7\" align=\"absmiddle\"><a href=\"javascript://\" onclick=\"javascript:"
													+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";
										}else if(program.getCkdx().equals("1")){
											tmp+="\t<tr>\n\t\t<td width=\"182\" id=\"img"
													+l
													+"\" class=\"text_12 text_3j_bg text_3j\" onMouseOver=\"this.className='text_12 text_3j_bg_over text_3j'\" onMouseOut=\"this.className='text_12 text_3j_bg text_3j'\">\n<img src=\"theme/blue/check.gif\" hspace=\"7\" align=\"absmiddle\"><a href=\"javascript://\" onclick=\"javascript:"
													+"openwin800('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";
										}else{
											tmp+="\t<tr>\n\t\t<td width=\"182\" id=\"img"
													+l
													+"\" class=\"text_12 text_3j_bg text_3j\" onMouseOver=\"this.className='text_12 text_3j_bg_over text_3j'\" onMouseOut=\"this.className='text_12 text_3j_bg text_3j'\">\n<img src=\"theme/blue/check.gif\" hspace=\"7\" align=\"absmiddle\"><a href=\"javascript://\" onclick=\"javascript:"
													+"openwinfull('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";
										}
									}
								}
							}
							subfoldhtml+="<div id=\"tab"+iTopindex+"_"+iFold1+"_" + m +"\" class=\""+style+"\"  onclick=\"interfaceDoSubFold("+ iTopindex + "," +iFold1+","+m+")\">"+subFold.getMlmc()+"</div>\n<div id=\"submenu"+iTopindex+ "_" + iFold1 + "_" +m+"\" style=\"display:"+display+"\"  width=\"182\" align=\"center\">";
							subfoldhtml+="<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"text_3j_bg\">\n"+tmp+"</table>\n</div>\n";
							m++;
						}
					}
					// 输出一级目录下菜单
					menuhtml="";
					for(int l=0;l<proList.size();l++){
						program=(Program)proList.get(l);
						if(program.getCxlx().equals("1")){
							//zhoujn 20140801 增加预警客户端弹出窗口不被再次打开窗口覆盖
							String target="businessjcbk"+sysUser.getYhdh();
							
							if(program.getMldh().equals(fold.getMldh())){
								if(program.getCkdx()==null||program.getCkdx().equals("")||program.getCkdx().equals("2")){
									//zhoujn 20140717 扩充版
									if(program.getCxlb()==null||program.getCxlb().equals("")||program.getCxlb().equals("1")){
//										menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_3\">\n<a href=\"javascript://\" onclick=\"javascript:"+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"
//												+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";	
										menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_3\">\n<a href=\"login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"\" target=\"rightfrm\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";
										
									}else if(program.getCxlb().equals("2")){
										menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_2\">\n<a href=\"javascript://\" onclick=\"javascript:"+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"
												+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";	
										
									}else if(program.getCxlb().equals("3")){	
										menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_1\">\n<a href=\"javascript://\" onclick=\"javascript:"+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"
												+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";	
									}
									
								}else if(program.getCkdx().equals("1")){
									menuhtml+="\t<tr>\n\t\t<td width=\"182\" id=\"img"
											+l
											+"\" class=\"text_12 text_3j_bg text_3j\" onMouseOver=\"this.className='text_12 text_3j_bg_over text_3j'\" onMouseOut=\"this.className='text_12 text_3j_bg text_3j'\">\n<img src=\"theme/blue/check.gif\" hspace=\"7\" align=\"absmiddle\"><a href=\"javascript://\" onclick=\"javascript:"
											+"openwin800('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";
								}else{
									menuhtml+="\t<tr>\n\t\t<td width=\"182\" id=\"img"
											+l
											+"\" class=\"text_12 text_3j_bg text_3j\" onMouseOver=\"this.className='text_12 text_3j_bg_over text_3j'\" onMouseOut=\"this.className='text_12 text_3j_bg text_3j'\">\n<img src=\"theme/blue/check.gif\" hspace=\"7\" align=\"absmiddle\"><a href=\"javascript://\" onclick=\"javascript:"
											+"openwinfull('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";
								}
							}
						}else if(program.getCxlx().equals("5")){
							//zhoujn 20140801 增加预警客户端弹出窗口不被再次打开窗口覆盖
							String target="businessjcbk"+sysUser.getYhdh()+"online"+WrapUtil.getRandomNormalString(4);
							if(program.getMldh().equals(fold.getMldh())){
								if(program.getCxlb()==null||program.getCxlb().equals("")||program.getCxlb().equals("1")){
									menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_3\">\n<a href=\"javascript://\" onclick=\"javascript:"+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"
											+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";	
								}else if(program.getCxlb().equals("2")){
									menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_2\">\n<a href=\"javascript://\" onclick=\"javascript:"+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"
											+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";	
									
								}else if(program.getCxlb().equals("3")){	
									menuhtml+="\t<tr>\n\t\t<td width=\"100%\" id=\"img"+l+"\" class=\"m2j_1\">\n<a href=\"javascript://\" onclick=\"javascript:"+"openwin1024('login.frm?method=sendRedirect&cdbh="+program.getCdbh()+"&xtlb="+program.getXtlb()+"','"
											+target+"','yes');\" style=\"color:#000F53\">"+program.getCxmc()+"</a></td>\n\t</tr>\n";	
								}
							}
						}
					}
					if(!menuhtml.equals("")){
						menuhtml="<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"indterface_left_panel_table\">\n"+menuhtml+"</table>\n";
					}
					htmltemp+="<div id=\"menu"+ iTopindex + "_" + iFold1+"\" style=\"display:";
					if(iFold1==1){
						htmltemp+="block\">\n";
					}else{
						htmltemp+="none\">\n";
					}
					htmltemp+=subfoldhtml+menuhtml+"\n</div>";
				}

				//加入top目录与整个二级目录层的对应关系
				if(iTopindex==1){
					html += "<div id='table_content" + iTopindex + "' style='display:block'>" + htmltemp + "</div>\n";	
				}else{
					html += "<div id='table_content" + iTopindex + "' style='display:none'>" + htmltemp + "</div>\n";
				}				
				foldhtml +="<td>&nbsp;</td></tr>\n";
				foldhtml +="</table>\n";
			}
		}
		topFoldhtml += "<script>top_tb('" + iTopindex + "');</script>\n";
		map.put("topfold",topFoldhtml);
		map.put("topfoldcount",iTopindex + "");
		map.put("fold",foldhtml);

		map.put("menu",html);
		map.put("count",String.valueOf(foldList1.size()));
		map.put("foldlist1",foldList1);
		map.put("total",String.valueOf(proList.size()));
		return map;
	}

	public List getFoldList(String yhdh) throws SQLException{

		return this.programFoldDao.getFoldList(yhdh);
	}

	public List getUserDeskList(String yhdh,Hashtable rightsobj) throws Exception{
		return this.programFoldDao.getUserDeskList(yhdh,rightsobj);
	}

	public List getTopFoldList() throws Exception{
		return this.programFoldDao.getTopFoldList();
	}

	public List getProgramList(SysUser sysUser,HashMap paras){
		return this.programFoldDao.getProgramList(sysUser,paras);
	}
	
	
	//zhoujn 20110311 根据功能名称获取列表
	public List getProgramList(String gnmc) throws Exception {
		return this.programFoldDao.getProgramList(gnmc);
	}

}
