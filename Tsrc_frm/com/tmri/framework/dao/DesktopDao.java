package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.framework.bean.Desktop;
import com.tmri.framework.bean.DesktopLinks;
import com.tmri.share.ora.bean.DbResult;

public interface DesktopDao {
	public List getDesktops() throws SQLException;
	public List getMyDesktops(String yhdh) throws SQLException;
    public List menuRightMaps(String yhdh, String xtlb, String cdbh) throws Exception;
	public Desktop getDesktop(String mkbh) throws SQLException;
	public Desktop getMyDesktop(String yhdh,String mkbh) throws SQLException;
	public int saveDesktopUserAttribute(Desktop d) throws SQLException;
	public int saveDesktopUserPosition(String yhdh,String mkbh,String position) throws SQLException;
	public int saveDesktopUser(Desktop d) throws SQLException;
	public int deleteDesktopUserByCondition(String condition) throws SQLException;
	public List getMyDesktopLinkss(String condition) throws SQLException;
    public DbResult delDesktop(String xh) throws SQLException;
	public List getMyDesktopDownloads(String condition) throws SQLException;
    public DbResult saveMyDesktopLinks(final List<DesktopLinks> list) throws Exception;
}
