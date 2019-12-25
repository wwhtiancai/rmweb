package com.tmri.share.frm.service;

import java.util.List;


public interface GHtmlService {

	public String transDmlbToOptionHtml(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String ywdx,
			String dmsmType, String dmsmValue);
	
	public String transListToOptionHtml(List list, String defauls,boolean havaNull, String showType);
	
	public String transDmlbToOptionHtml(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String ywdx)
			throws Exception;

	/**
	 * �������2010-05-27
	 * 
	 * @param list
	 * @param defauls
	 * @param havaNull
	 * @param showType
	 * @param dmsmType��1:��ʾdmsm1,2:��ʾdmsm2,3��ʾdmsm3,4��ʾdmsm4,5-valueΪdmz+dmsm2;
	 *            captionΪdmz+dmsm1 6:��ʾ��������ݣ�ֵΪDMSM2
	 * @return
	 * @throws Exception
	 */
	public String transListToOptionHtml(String xtlb, String dmlb,
			String defauls, boolean havaNull, String dmsmType) throws Exception;

	public String transDmlbToCheckBoxHtml(String xtlb, String dmlb,
			String ctrlName, String selectValues, String showType);

	public String transListToCheckBoxHtml(List list, String ctrlName,
			String selectValues, String showType);

	/**
	 * ��ĳ������б�ת��ΪCheckBox HTML��Ϣ
	 * 
	 * @param list �����б�
	 * @param ctrlName HTML CheckBox����
	 * @param selectValues Ĭ��ѡ�е�ֵ��","�ָ�
	 * @param showType ��ʾ��ʽ 1:ֻ��ʾ���� 2:ֻ��ʾ���� 3:��ʾ��������� *
	 * @param dmsmType��1:��ʾdmsm1,2:��ʾdmsm2,3��ʾdmsm3,4��ʾdmsm4,5-valueΪdmz+dmsm2;
	 *            captionΪdmsm1
	 * @return
	 * @throws Exception
	 */
	public String transListToCheckBoxHtml(List list, String ctrlName,
			String selectValues, String showType, String dmsmType)
			throws Exception;
	
}
