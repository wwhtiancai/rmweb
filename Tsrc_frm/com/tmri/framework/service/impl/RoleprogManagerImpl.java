package com.tmri.framework.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import com.tmri.framework.bean.Fold;
import com.tmri.framework.bean.FrmUserprog;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.dao.RoleprogDao;
import com.tmri.framework.service.RoleprogManager;
import com.tmri.pub.util.FuncUtilfrm;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.util.BeanUtil;
import com.tmri.share.frm.util.StringUtil;
@Service

public class RoleprogManagerImpl implements RoleprogManager{
	@Autowired
	private RoleprogDao roleprogDao;
	
	/**
	 * 获取所有的目录list
	 */
	public List getFoldList(FrmUserprog frmuserprog){
		return this.roleprogDao.getFoldList(frmuserprog);
	}
	public List getFoldListByJsdh(FrmUserprog frmuserprog){
		return this.roleprogDao.getFoldListByJsdh(frmuserprog);
	}	
//	public RoleprogDao getRoleprogDao() {
//		return roleprogDao;
//	}
//	public void setRoleprogDao(RoleprogDao roleprogDao) {
//		this.roleprogDao = roleprogDao;
//	}
	public List getFoldListByYhdh(FrmUserprog frmuserprog){
		return this.roleprogDao.getFoldListByYhdh(frmuserprog);
	}
	
	public List getFoldListByYhdh(String yhdh){
		return this.roleprogDao.getFoldListByYhdh(yhdh);
	}
	
	/**
	 * 获取所有的菜单list
	 */
	public List getProgramList(FrmUserprog frmuserprog){
		return this.roleprogDao.getProgramList(frmuserprog);
	}
	public List getProgramListByJsdh2(FrmUserprog frmuserprog){
		return this.roleprogDao.getProgramListByJsdh2(frmuserprog);
	}	
	public List getProgramListByYhdh(FrmUserprog frmuserprog){
		return this.roleprogDao.getProgramListByYhdh(frmuserprog);
	}	
	
	public List getProgramListByYhdh(String yhdh){
		return this.roleprogDao.getProgramListByYhdh(yhdh);
	}
	/**
	 * 获取所有的功能list
	 */
	public List getFunctionList(FrmUserprog frmuserprog){
		return this.roleprogDao.getFunctionList(frmuserprog);
	}
	
	public List getFunctionListByJsdh(FrmUserprog frmuserprog){
		return this.roleprogDao.getFunctionListByJsdh(frmuserprog);
	}
	public List getFunctionListByYhdh(FrmUserprog frmuserprog){
		return this.roleprogDao.getFunctionListByYhdh(frmuserprog);
	}		
	
	public List getFunctionListByYhdh(String yhdh){
		return this.roleprogDao.getFunctionListByYhdh(yhdh);
	}	
	
	
	public List getUserDeskmenu(String yhdh)throws Exception{
		return this.roleprogDao.getUserDeskmenu(yhdh);
	}
	/**
	 * 根据角色代号获取所有程序号list
	 * 
	 * @param jsdh
	 * @return
	 * @throws DataAccessException
	 */
	public List getProgramListByJsdh(String jsdh){
		return this.roleprogDao.getProgramListByJsdh(jsdh);
	}
	
	
	public List unionFoldList(List foldList,List proList,List funList){
		Fold fold = null;Program pro = null;Function fun = null;
		for(int i=0;i<foldList.size();i++){
			fold = (Fold)foldList.get(i);
			List proList2 = new Vector();
			boolean proexists = false;
			for(int j=0;j<proList.size();j++){
				pro = (Program)proList.get(j);
				if(fold.getMldh().equals(pro.getMldh())){
					proList2.add(pro);
					List funList2 = new Vector();
					boolean funexists = false;					
					for(int k=0;k<funList.size();k++){
						fun = (Function)funList.get(k);
						if(pro.getCdbh().equals(fun.getCdbh())){
							funList2.add(fun);
						}else if(funexists){
							break;
						}
					}
					pro.setGnList(funList2);
				}else if(proexists){
					break;
				}				
			}
			fold.setProList(proList2);
		}
		return foldList;
	}	
	
	//gouzao
	public Hashtable getXtlbUnionFoldList(List foldList,List proList,List funList){
		Hashtable ht=new Hashtable();
		//获取tablist
		List tablist=getTabList(proList);
		//排序
		tablist=FuncUtilfrm.sortList(tablist);
		ht.put("tablist", tablist);
		//
		for(int i=0;i<tablist.size();i++){
			Code code=(Code)tablist.get(i);
			List tabfoldlist= null;
			
			tabfoldlist=getFoldList(code.getDmz(),foldList,proList,funList);
			ht.put(code.getDmz(), tabfoldlist);
		}
		
		return ht;
	}	
	
	public List getTabListbyFoldlist(List foldList){
		List templist=this.roleprogDao.getAllfoldlist(foldList);
		List result=new ArrayList();
		for(int i=0;i<templist.size();i++){
			Fold fold=(Fold)templist.get(i);
			if(fold.getLevel().equals("1")){
				Code code=new Code();
				code.setDmz(fold.getMldh());
				code.setDmsm1(fold.getMlmc());
				result.add(code);
			}
		}
		return result;
	}
	
	
	//根据目录构造信息
	public Hashtable getUnionFoldList(List foldList,List proList,List funList){
		Hashtable ht=new Hashtable();
		//获取tablist
		List tablist=getTabListbyFoldlist(foldList);
		//排序
		//tablist=FuncUtil.sortList(tablist);
		ht.put("tablist", tablist);
		//
		for(int i=0;i<tablist.size();i++){
			Code fold=(Code)tablist.get(i);
			List tabfoldlist = new ArrayList();
			
			//取所有子目录
			List childFoldlist=this.roleprogDao.getChildfoldlist(foldList, fold.getDmz());
			for(int j=0;j<childFoldlist.size();j++){
				Fold temp=(Fold)childFoldlist.get(j);
				List proList2=getFoldproList(temp,proList,funList);
				//如果该目录有子菜单，则假如目录
				if(proList2.size()>0){
					temp.setProList(proList2);
					//赋予另外值再处理
					tabfoldlist.add(temp);
				}

			}
			ht.put(fold.getDmz(), tabfoldlist);
		}
		
		return ht;
	}	
	

	
	
	//根据目录编号，构造子目录信息
	List getFoldproList(Fold fold,List proList,List funList){
		List proList2 = new ArrayList();
		boolean proexists = false;
		for(int j=0;j<proList.size();j++){
			Program pro = (Program)proList.get(j);
			//同目录
			if(fold.getMldh().equals(pro.getMldh())){
				proList2.add(pro);
				List funList2 = new ArrayList();
				boolean funexists = false;					
				for(int k=0;k<funList.size();k++){
					Function fun = (Function)funList.get(k);
					if(pro.getCdbh().equals(fun.getCdbh())&&pro.getXtlb().equals(fun.getXtlb())){
						funList2.add(fun);
					}else if(funexists){
						break;
					}
				}
				pro.setGnList(funList2);
			}else if(proexists){
				break;
			}				
		}
		return proList2;
	}
	

	
	
	//排除功能参与构造
	public Hashtable getUnionFoldList(List foldList,List proList){
		Hashtable ht=new Hashtable();
		//获取tablist
		List tablist=getTabList(proList);
		//排序
		tablist=FuncUtilfrm.sortList(tablist);
		ht.put("tablist", tablist);
		//
		for(int i=0;i<tablist.size();i++){
			Code code=(Code)tablist.get(i);
			List tabfoldlist= null;
			
			tabfoldlist=getFoldList(code.getDmz(),foldList,proList);
			ht.put(code.getDmz(), tabfoldlist);
		}
		
		return ht;
	}	
	
	

	
	
	
	//根据管理员和当前用户角色构造Hashtable
	public Hashtable getUnionFoldList(List foldList,List proList,List funList,List roleMenuList){
		Hashtable ht=new Hashtable();
		//获取tablist
		List tablist=getTabList(proList);
		//排序
		tablist=FuncUtilfrm.sortList(tablist);
		ht.put("tablist", tablist);
		//
		for(int i=0;i<tablist.size();i++){
			Code code=(Code)tablist.get(i);
			List tabfoldlist= null;
			
			tabfoldlist=getFoldList(code.getDmz(),foldList,proList,funList);
			ht.put(code.getDmz(), tabfoldlist);
		}
		
		return ht;
	}	
	
	

	
	
	//根据xtlb构造foldlist,proglist
	private List getFoldList(String xtlb,List foldList,List proList,List funList){
		List result=new ArrayList();
		
		//过滤每个目录
		for(int i=0;i<foldList.size();i++){
			Fold temp =(Fold)foldList.get(i);
			Fold fold=new Fold();
			try{
				fold=(Fold)BeanUtil.beanValueCopy(temp, fold);
			}catch(Exception ex){
				ex.printStackTrace();
				fold.setMldh(temp.getMldh());
				fold.setMlmc(temp.getMlmc());
			}
			
			List proList2 = new ArrayList();
			boolean proexists = false;
			for(int j=0;j<proList.size();j++){
				Program pro = (Program)proList.get(j);
				//同目录和同系统类别
				if(fold.getMldh().equals(pro.getMldh())&&pro.getXtlb().equals(xtlb)){
					proList2.add(pro);
					List funList2 = new ArrayList();
					boolean funexists = false;					
					for(int k=0;k<funList.size();k++){
						Function fun = (Function)funList.get(k);
						if(pro.getCdbh().equals(fun.getCdbh())&&pro.getXtlb().equals(fun.getXtlb())){
							funList2.add(fun);
						}else if(funexists){
							break;
						}
					}
					pro.setGnList(funList2);
				}else if(proexists){
					break;
				}				
			}
			
			//如果该目录有子菜单，则假如目录
			if(proList2.size()>0){
				fold.setProList(proList2);
				//赋予另外值再处理
				result.add(fold);
			}
		}
		return result;
	}
	
	//同义　
	private List getFoldList(String xtlb,List foldList,List proList){
		List result=new ArrayList();
		
		//过滤每个目录
		for(int i=0;i<foldList.size();i++){
			Fold temp =(Fold)foldList.get(i);
			Fold fold=new Fold();
			try{
				fold=(Fold)BeanUtil.beanValueCopy(temp, fold);
			}catch(Exception ex){
				ex.printStackTrace();
				fold.setMldh(temp.getMldh());
				fold.setMlmc(temp.getMlmc());
			}
			
			List proList2 = new ArrayList();
			boolean proexists = false;
			for(int j=0;j<proList.size();j++){
				Program pro = (Program)proList.get(j);
				//同目录和同系统类别
				if(fold.getMldh().equals(pro.getMldh())&&pro.getXtlb().equals(xtlb)){
					proList2.add(pro);
				}else if(proexists){
					break;
				}				
			}
			
			//如果该目录有子菜单，则假如目录
			if(proList2.size()>0){
				fold.setProList(proList2);
				//赋予另外值再处理
				result.add(fold);
			}
		}
		return result;
	}	
	
	//构造tab 
	//包括多少系统，每个系统对应的目录，菜单，功能id
	private List getTabList(List proList){
		List result=new ArrayList();
		for(int j=0;j<proList.size();j++){
			Program program=(Program)proList.get(j);
			if(checkXtlb(result,program.getXtlb())){
				Code code=new Code();
				code.setDmz(program.getXtlb());
				code.setDmsm1(this.roleprogDao.getXtlbmc(program.getXtlb()));
				result.add(code);
			}
		}	
		return result;
	}
	
	
	private boolean checkXtlb(List objlist,String xtlb){
		boolean result=true;
		for(int i=0;i<objlist.size();i++){
			Code code=(Code)objlist.get(i);
			if(code.getDmz().equals(xtlb)){
				result=false;
				break;
			}
		}
		return result;
	}
		
	
	
	//获取用户菜单
	//zhoujn 20100524
	public List getUserMenuListByYhdh(String yhdh){
		return this.roleprogDao.getUserMenu(yhdh);
	}
	
	//zhoujn 20100524 获取维护用户重叠角色代号
	public List getRoleList(String yhdh,String whyh){
		return this.roleprogDao.getRoleList(yhdh,whyh);
	}	
	
	// 修改根据权限模式修改显示
	public String getoutcdbh(Hashtable foldHt,List roleMenuList,String qxms,int irowsize){
		//定义
		//imax现实最大字数
		//iwidth1第一列宽度iwidth2其他列宽度
		
		int imaxsize1=10,imaxsize2=10;
		int iwidth1=150,iwidth2=150;
		String checkval="",title="",value="",color="";
		
		//int irowsize=5;
		//转换成hashtable
		Hashtable roleMenuHt=getRolemenuHt(roleMenuList);
		//获取tab
		String result="<div class='domtab'>";
		result+="<ul class='domtabs'>";
		List tablist=(List)foldHt.get("tablist");
		if(tablist.size()!=0){
			for(int i=0;i<tablist.size();i++){
				Code code=(Code)tablist.get(i);
				result+="<li><a href='#tab"+(i+1)+"' onClick='tab_c("+(i+1)+")' style='width:120'>"+code.getDmsm1()+"</a></li>";
			}
		} else {
			result+="<li><a href='#tab1' onClick='tab_c(1)' style='width:120'>无可授权权限</a></li>";
			result+="<div style='height:300;overflow:scroll;border:1px solid;'><h2><a name='tab1' id='tab1'></a></h2></div>";
		}
		result+="</ul>";
		
		for(int x=0;x<tablist.size();x++){
			Code code=(Code)tablist.get(x);
			List foldList=(List)foldHt.get(code.getDmz());
			result+="<div style='height:300;overflow:scroll;border:1px solid;'><h2><a name='tab"+(x+1)+"' id='tab"+(x+1)+"'></a></h2>";
			
			//输入该系统类别tab内容
			result+="<table width='100%' height=\"100%\" border='0' cellpadding='0' cellspacing='1' class='text_12'>";
			// 0-输出菜单 1-输出功能
			for(int i=0;i<foldList.size();i++){
				Fold fold=(Fold)foldList.get(i);
				List proList=fold.getProList();
				result+="<tr class=\"list_body_out\">\n";
				//获取目录信息
				result+=getFoldtdinputspan(fold,qxms,iwidth1,imaxsize1);
				
				//开始显示菜单
				result+="<td >";
				
				//增加换行处理
				int colsize=0;
				for(int j=0;j<proList.size();j++){
					Program program=(Program)proList.get(j);
					List funList=program.getGnList();
					
					if(funList!=null&&funList.size()>0){
						//如果是功能就直接换行
						if(colsize>0){
							//重置计数
							colsize=0;
							result+="<br>";
						}
						//显示菜单
						checkval=fold.getMldh()+"-"+program.getXtlb()+"-"+program.getCdbh();
						value=StringUtil.displayMax(program.getCxmc(),imaxsize2);
						color="#3399cc";
						result+=getMenuInputspan(qxms,iwidth2,color,"","",checkval,value);

						//功能列表显示，功能空闲
						//空出菜单说明，从1计数
						int funcsize=1;
						for(int k=0;k<funList.size();k++){
							Function function=(Function)funList.get(k);
							String checked=checkJsdh(roleMenuHt,program.getXtlb(),program.getCdbh(),function.getGnid(),qxms);
							//20101108 新增，用户维护自由权限
							String disabled="";
							if(function.getBz().equals("AAA")){
								disabled=" disabled ";
							}
							checkval=fold.getMldh()+"-"+program.getXtlb()+"-"+program.getCdbh()+"-"+function.getGnid();
							value=StringUtil.displayMax(function.getMc(), imaxsize2);
							color="#666600";
							result+=getMenuInputspan("",iwidth2,color,disabled,checked,checkval,value);
							
							funcsize++;
							//irowsize是全部列数，排除目录剩余
							if(funcsize==irowsize-1) funcsize=1;
							if(k==funList.size()-1){
								//如果到最后一个显示，则换行
								result+="<br>";
							}else if(funcsize==1){
								//如果功能较多换行，则空出第一列
								result+="<br>";
								result+="<span class='text_12' style='width:"+(iwidth2+20)+"'></span>";
							}
						}
					}else{
						//20101108 新增，用户维护自由权限
						String disabled="";
						if(program.getBz().equals("AAA")){
							disabled=" disabled ";
						}
						String checked=checkJsdh(roleMenuHt,program.getXtlb(),program.getCdbh(),qxms);
						checkval=fold.getMldh()+"-"+program.getXtlb()+"-"+program.getCdbh();
						value=StringUtil.displayMax(program.getCxmc(),imaxsize2);
						color="#666600";
						result+=getMenuInputspan("",iwidth2,color,disabled,checked,checkval,value);
						
						
						//确定是否换行,除了目录行,所以减一
						colsize++;
						if(colsize==irowsize-1) colsize=0;
						if(j<proList.size()&&colsize==0){
							result+="<br>";
						}
					}
				}
				result+="</td>\n";
				result+="</tr>\n";
			}

			result+="</table>";			
			result+="</div>";
		}
		result+="</div>";
		return result;
	}	
	
	
	
	//20110223用于首页面设置快捷菜单
	public String getDeskoutcdbh(Hashtable foldHt,List roleMenuList,String qxms,int irowsize){
		//定义
		//imax现实最大字数
		//iwidth1第一列宽度iwidth2其他列宽度
		
		int imaxsize1=10,imaxsize2=10;
		int iwidth1=200,iwidth2=150;
		String checkval="",title="",value="",color="";
		
		//int irowsize=5;
		//转换成hashtable
		Hashtable roleMenuHt=getRolemenuHt(roleMenuList);
		//获取tab
		String result="<div class='domtab'>";
		result+="<ul class='domtabs'>";
		List tablist=(List)foldHt.get("tablist");
		if(tablist.size()!=0){
			for(int i=0;i<tablist.size();i++){
				Code code=(Code)tablist.get(i);
				result+="<li><a href='#tab"+(i+1)+"' onClick='tab_c("+(i+1)+")' style='width:120'>"+code.getDmsm1()+"</a></li>";
			}
		} else {
			result+="<li><a href='#tab1' onClick='tab_c(1)' style='width:120'>无可授权权限</a></li>";
			result+="<div style='height:300;overflow:scroll;border:1px solid;'><h2><a name='tab1' id='tab1'></a></h2></div>";
		}
		result+="</ul>";
		
		for(int x=0;x<tablist.size();x++){
			Code code=(Code)tablist.get(x);
			List foldList=(List)foldHt.get(code.getDmz());
			result+="<div style='height:300;overflow:scroll;border:1px solid;'><h2><a name='tab"+(x+1)+"' id='tab"+(x+1)+"'></a></h2>";
			
			//输入该系统类别tab内容
			result+="<table width='100%' height=\"100%\" border='0' cellpadding='0' cellspacing='1' class='text_12'>";
			// 0-输出菜单 1-输出功能
			for(int i=0;i<foldList.size();i++){
				int n=0;
				Fold fold=(Fold)foldList.get(i);
				List proList=fold.getProList();
				result+="<tr class=\"list_body_out\">\n";
				//获取目录信息
				result+=getFoldtdinputspan(fold,qxms,iwidth1,imaxsize1);
				
				//开始显示菜单
				result+="<td >";
				for(int j=0;j<proList.size();j++){
					Program program=(Program)proList.get(j);
					//20101108 新增，用户维护自由权限
					String checked=checkJsdh(roleMenuHt,program.getXtlb(),program.getCdbh(),qxms);
					//checkval=fold.getMldh()+"-"+program.getXtlb()+"-"+program.getCdbh();
					checkval=fold.getMldh()+"-"+program.getXtlb()+program.getCdbh();
					value=StringUtil.displayMax(program.getCxmc(),imaxsize2);
					color="#666600";
					result+=getMenuInputspan("",iwidth2,color,"",checked,checkval,value);
					
					n++;
					if(n==irowsize) n=0;
					if(j<proList.size()&&n==0){
						result+="<br>";
					}
				}
				result+="</td>\n";
				result+="</tr>\n";
			}

			result+="</table>";			
			result+="</div>";
		}
		result+="</div>";
		return result;
	}	
	
	
	
	private String getMenuInputspan(String qxms,int width,String color,String disabled,String checked,String checkval,String value){
		String result="";
		String qxmskz="";
		if(qxms.equals("1")){
			qxmskz=" checked disabled ";
		}
		result+="<input class='text_12' type='checkbox' " + qxmskz + " " + disabled + " " + checked + " onclick='check_item(this)' name='cddh' value='"+checkval+"'>";
		result+="<span class='text_12 tr_color_3' style='width:"+width+";color:"+color+";'>"+value+"</span>";
		return result;
	}
	
	private String getFoldtdinputspan(Fold fold,String qxms,int width,int maxsize){
		String result="";
		String qxmskz="";
		if(qxms.equals("1")){
			qxmskz=" checked disabled ";
		}		
		result+="<td width="+width+" valign='top'><span class='text_12 text_bold' style='width:100%;color: #0000ff;'>";
		//处理单二层目录
		String mlmctitle=fold.getMlmc();
		String mlmc=fold.getMlmc();
		
		if(!fold.getSjmldh().equals("0000")){
			mlmctitle=this.roleprogDao.getMlmc(fold.getSjmldh())+"|"+fold.getMlmc();
		}
		String mlmcall="<span style='cursor:hand' title='"+mlmctitle+"'>"+mlmc+"</span>";
		result+="<input type='checkbox' "+qxmskz+" onclick='check_item(this)' name='cmldh' value="+fold.getMldh()+">"+mlmcall+"</td>\n";
		
		return result;
	}
	
		
	//将list 转换成 hasttab
	private Hashtable getRolemenuHt(List roleMenuList){
		Hashtable roleMenuHt=new Hashtable();
		if(roleMenuList!=null){
			Iterator iterator=roleMenuList.iterator();
			while(iterator.hasNext()){
				UserMenu roleMenu=(UserMenu)iterator.next();
				String key=roleMenu.getXtlb()+"-"+roleMenu.getCdbh();
				roleMenuHt.put(key, roleMenu);
			}
		}
		return roleMenuHt;
	}
	
	
	// 有功能id
	private String checkJsdh(Hashtable roleMenuHt,String xtlb,String cdbh,String gnid,String qxms){
		String result="";
		// 循环判断是否选择上的
		if(qxms.equals("1")){
			result=" checked disabled ";
		}else{
			if(roleMenuHt!=null){
				String key=xtlb+"-"+cdbh;
				UserMenu roleMenu=(UserMenu)roleMenuHt.get(key);
				if(roleMenu!=null){
					if(roleMenu.getGnlb().indexOf(gnid)>=0){
						result=" checked ";
					}
				}
			}
		}
		return result;
	}

	private String checkJsdh(Hashtable roleMenuHt,String xtlb,String cdbh,String qxms){
		String result="";
		// 循环判断是否选择上的
		if(qxms.equals("1")){
			result=" checked disabled ";
		}else{
			if(roleMenuHt!=null){
				String key=xtlb+"-"+cdbh;
				UserMenu roleMenu=(UserMenu)roleMenuHt.get(key);
				if(roleMenu!=null){
					result=" checked ";
				}
			}
		}

		return result;
	}
	
	//处理最多显示8个字
//	private String getMaxstr(String val){
//		if(val.length()>8){
//			val=val.substring(0,8);
//		}
//		return val;
//	}	
//		
	
	
	//获取用户操作权限
	public List getUserProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException{
		return this.roleprogDao.getUserProgramListByYhdh(frmuserprog);
	}
	//获取用户操作权限
	public List getUserFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException {
		return this.roleprogDao.getUserFoldListByYhdh(frmuserprog);
	}
}
