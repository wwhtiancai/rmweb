package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.bean.DesktopLinks;
import com.tmri.share.ora.bean.DbResult;

public interface DesktopManager {
	public List getDesktops() throws Exception;
	public List getMyDesktops(String yhdh) throws Exception;
    public List menuRightMaps(String yhdh, String xtlb, String cdbh) throws Exception;
	public Desktop getMyDesktop(String yhdh,String mkbh) throws Exception;
	public int saveDesktopUserAttribute(Desktop d) throws Exception;
	public int saveDesktopUserPosition(String yhdh,String xy) throws Exception;
	public int saveDesktopUserData(String content,String yhdh,String indexs) throws Exception;
	public List getMyDesktopLinkss(String qx) throws Exception;
    public DbResult delDesktop(String xh) throws Exception;
	public List getMyDesktopDownloads(String fl) throws Exception;
    public DbResult saveMyDesktopLinks(List<DesktopLinks> list) throws Exception;
}
