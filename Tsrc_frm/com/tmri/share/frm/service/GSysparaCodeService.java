package com.tmri.share.frm.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.tmri.rfid.common.CodeTableDefinition;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.SysPara;

public interface GSysparaCodeService {

    Code getCode(CodeTableDefinition definition, String dmz);

    List getCodes(CodeTableDefinition definition);

	public Code getCode(String xtlb, String dmlb, String dmz);

	public Code getCode(String xtlb, String dmlb, String dmz, String ywdx);

	public List getCodes(String xtlb, String dmlb);
	
	public List getCodesByDb(String xtlb, String dmlb);

	public List getCodes(String xtlb, String dmlb, String ywdx);

	public Code getCodeByDmsm2(String xtlb, String dmlb, String dmsm2);

    public List getCodesByDmsm2(String xtlb, String dmlb, String dmsm2);

    Code getCodeByDmsm2(CodeTableDefinition definition, String dmsm2);

    List getCodesByDmsm2(CodeTableDefinition definition, String dmsm2);

    public List getCodesByDmsm3(String xtlb, String dmlb, String dmsm3);

    public List getCodesByDmsm4(String xtlb, String dmlb, String dmsm4);

	public HashMap getCodesH(String xtlb, String dmlb);

	public List getSelectCodes(String xtlb, String dmlb, String ywdx,String type, String selectValue);

	public List getCodesByDmz(String xtlb, String dmlb, String dmz) throws Exception;

	public List<Code> getCodesByDmz2(String xtlb, String dmlb, String dmz2) throws Exception;

	public String transCode(String xtlb, String dmlb, String dmz);

	public String transCodeByDmsm2(String xtlb, String dmlb, String dmsm2);

	public String transMultiCodeBySplit(String xtlb, String dmlb, String dmz,String split);

	public String transMultiCode(String xtlb, String dmlb, String dmz,int length);

	public String transMultiCode(String xtlb, String dmlb, String dmz,int length, String split);

	public List getTjCodesByDmsm2(String dmlb, String dmsm2);

	public List getTjCodesByDmz(String dmlb, String strDmz) throws SQLException;

	public Code getTjCodeFromMem(String dmlb, String strDmz);

	public String getTjCodeDmsm1ByDmsm2(String dmlb, String strDmsm2);

	public List getDrvCityListFromMem(String dmlb, String strDmz)
			throws SQLException;

	public String getSysParaValue(String xtlb, String gjz);

	public List getDeptParaGlbms(String xtlb, String gjz, String value);

	public List getSysparasShow(String xgjb) throws Exception;

	public List getDepSyspara(SysPara sysPara);

	public SysPara getSyspara(String xtlb, String cslx, String gjz);

	public String getCodeValue(String xtlb, String dmlb, String dmz)
			throws Exception;

	// ȡ�ù������͵�ϵͳ����
	public String getParaValue(String xtlb, String gjz);
	
	public String getTableName(String tableName);

	// ȡ�ò������͵�ϵͳ����
	public String getParaDepartValue(String glbm, String xtlb, String gjz);

	public String getHpzlmc(String hpzl) throws Exception;

	public String getHpysmc(String hpys) throws Exception;

	public String getCsysmc(String csys) throws Exception;

	public String getCllxmc(String cllx) throws Exception;

	public String getVehywyy(String ywlx, String ywyy);

	public String getDrvYwyy(String ywlx, String ywyy);

	public String getFzjgFromHphm(String hphm);

	// ���ݺ������࣬���ƺ���ȡ��֤����
	public String getFzjgFromHphm(String hpzl, String hphm);

	public String getInformCodeMc(String xxdm);
	
	public int enableCloudSystem() throws Exception;
	
	//����Ƿ��������ܳ���
	public  boolean checkJchpt(String hphm);
	
	// ��ȡ��������
	public List<Code> getBklxList();
	
	// ��ȡ�ȶ�����
	public List<Code> getBdlxList(String sjly);
	
	// ��ȡԤ����Ϣ��ѯ����ʾ�Ĳ�������
	public List<Code> getAlarmQueryBklxList();

	
	// ��ȡΥ���豸����
	public List<Code> getDeviceTypeList();
	
	public List<Code> getClflList();
	
	public String getDeptParaValue(String glbm,String xtlb,String gjz);
	
	public String getJccllx(String syxz, String clyt, String cllx, String xzqh);
	
	public String getCtrlpara(String azdm,String gjz) throws Exception;
	
	//��ȡgis������Ϣ
	public String getGispara(String gjz) ;
}
