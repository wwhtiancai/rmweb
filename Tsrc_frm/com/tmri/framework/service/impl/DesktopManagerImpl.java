package com.tmri.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.bean.DesktopLinks;
import com.tmri.framework.dao.DesktopDao;
import com.tmri.framework.service.DesktopManager;
import com.tmri.share.ora.bean.DbResult;
@Service

public class DesktopManagerImpl implements DesktopManager {
	@Autowired
	DesktopDao desktopDao = null;

//	public void setDesktopDao(DesktopDao desktopDao){
//		this.desktopDao=desktopDao;
//	}
	public List getDesktops() throws Exception{
		return this.desktopDao.getDesktops();
	}
	public List getMyDesktops(String yhdh) throws Exception{
		return this.desktopDao.getMyDesktops(yhdh);
	}
    public List menuRightMaps(String yhdh, String xtlb, String cdbh) throws Exception {
        return this.desktopDao.menuRightMaps(yhdh, xtlb, cdbh);
    }
	public Desktop getMyDesktop(String yhdh,String mkbh) throws Exception{
		return this.desktopDao.getMyDesktop(yhdh,mkbh);
	}
	public int saveDesktopUserAttribute(Desktop d) throws Exception{
		return this.desktopDao.saveDesktopUserAttribute(d);
	}
	public int saveDesktopUserPosition(String yhdh,String xy) throws Exception{
		int r=0;
		if(xy==null||xy.length()<1){
			r=0;
		}else{
			String[] temp=xy.split(";");
			String mkbh="",mkwz="";
			r=1;
			for(int i=0;i<temp.length;i++){
				mkbh=temp[i].substring(0,temp[i].indexOf(":"));
				mkwz=temp[i].substring(temp[i].indexOf(":")+1,temp[i].length());
				if(!mkbh.equals("x")){
					r=this.desktopDao.saveDesktopUserPosition(yhdh,mkbh,mkwz);
					if(r<1){
						break;
					}
				}
			}
		}
		return r;
	}
	public int saveDesktopUserData(String content,String yhdh,String indexs) throws Exception{
		String[] temp=content.split(",");
		int r=0;
		String deletecondition="";
		for(int i=0;i<temp.length;i++){
			deletecondition+="mkbh<>'"+temp[i]+"' and ";
		}
		if(deletecondition.length()>1){
			deletecondition="yhdh='"+yhdh+"' and ("+deletecondition.substring(0,deletecondition.length()-4)+")";
			r=this.desktopDao.deleteDesktopUserByCondition(deletecondition);
			if(r>0){
				int pos=1;
				for(int i=0;i<temp.length;i++){
					Desktop d=new Desktop();
					if(temp[i].equals("0")){
						d.setYhdh(yhdh);
						d.setMkbh("0");
						d.setMkwz("0");
						d.setMksx1(indexs);
					}else{
						pos++;
						d=this.desktopDao.getMyDesktop(yhdh,temp[i]);
						if(d==null||d.getMkbh()==null){
							d=this.desktopDao.getDesktop(temp[i]);
							d.setYhdh(yhdh);
							d.setMkwz(String.valueOf(pos));
							d.setBz("");
						}else{
							d.setMkwz(String.valueOf(pos));
						}
					}
					r=this.desktopDao.saveDesktopUser(d);
					if(r<1){
						break;
					}
				}
			}
		}
		return r;
	}
	public List getMyDesktopLinkss(String qx) throws Exception{
		if(qx==null||qx.length()<1){
            return this.desktopDao.getMyDesktopLinkss("");
		}
		String condition="";
		for(int i=0;i<qx.length();i++){
			condition+=" or xh='"+qx.charAt(i)+"'";
		}
		if(condition.length()>1){
			condition=" where "+condition.substring(3,condition.length());
		}
		return this.desktopDao.getMyDesktopLinkss(condition);
	}
    public DbResult delDesktop(String xh) throws Exception {
        return this.desktopDao.delDesktop(xh);
    }
    public DbResult saveMyDesktopLinks(List<DesktopLinks> list) throws Exception {
        return this.desktopDao.saveMyDesktopLinks(list);
    }
	public List getMyDesktopDownloads(String fl) throws Exception{
		if(fl==null||fl.length()<1||fl.equals("00")){
			return this.desktopDao.getMyDesktopDownloads("");
		}else{
			return this.desktopDao.getMyDesktopDownloads(" where fl='"+fl+"'");
		}
	}
}
