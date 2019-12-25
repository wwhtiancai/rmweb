package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.framework.bean.SmsContent;
import com.tmri.framework.bean.SmsSetup;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.util.PageController;
/**
 * 系统代码读写
 * @author jianghailong
 *
 */
public interface CodeDao {
  public SysResult saveCode(String modal) throws SQLException;
  public SysResult removeCode() throws Exception;
  public void freshCodebyDmlb(Code code);
  public Object getOtherMemTab(String tablename,String key) throws Exception;
  public List getXzqhList(String dmz,String dmsm1,String xzqh, PageController controller) throws Exception;
  
  public List getJxCodes(String fzjg) throws SQLException;
  public Code getJx(String jxdm,String fzjg) throws SQLException;
  
  public Code getJxByXh(String jxxh) throws SQLException;
  //获取消息接收设置列表
  public List getSmsSetupContent(String xxdm);
  //获取用户列表
  public List getUsers(String xtgly,String kgywyhlx,String xm);
  //保存消息设置  
  public SysResult savesmssetup(SmsSetup smsSetup) throws Exception;
  //获取用户消息个数
  public int getSmsCount(String yhdh);
  //获取用户消息列表
  public List getUserSmsList(SmsContent smsContent,PageController controller);
  //获取单个用户消息
  public SmsContent saveReadOneUserSms(SmsContent smsContent);
  //获取提醒内容设置代码列表
  public List getInformCodes(String dqyhbmjb,String djbmjb);
  //获取提醒内容设置列表
  public List getInformSetups(FrmInformsetup command,PageController controller);
  //获取单条提醒内容设置信息
  public FrmInformsetup getInformSetup(FrmInformsetup command);
}
