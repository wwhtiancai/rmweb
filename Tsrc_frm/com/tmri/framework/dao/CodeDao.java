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
 * ϵͳ�����д
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
  //��ȡ��Ϣ���������б�
  public List getSmsSetupContent(String xxdm);
  //��ȡ�û��б�
  public List getUsers(String xtgly,String kgywyhlx,String xm);
  //������Ϣ����  
  public SysResult savesmssetup(SmsSetup smsSetup) throws Exception;
  //��ȡ�û���Ϣ����
  public int getSmsCount(String yhdh);
  //��ȡ�û���Ϣ�б�
  public List getUserSmsList(SmsContent smsContent,PageController controller);
  //��ȡ�����û���Ϣ
  public SmsContent saveReadOneUserSms(SmsContent smsContent);
  //��ȡ�����������ô����б�
  public List getInformCodes(String dqyhbmjb,String djbmjb);
  //��ȡ�������������б�
  public List getInformSetups(FrmInformsetup command,PageController controller);
  //��ȡ������������������Ϣ
  public FrmInformsetup getInformSetup(FrmInformsetup command);
}
