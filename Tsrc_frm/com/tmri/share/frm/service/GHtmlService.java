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
	 * 完成日期2010-05-27
	 * 
	 * @param list
	 * @param defauls
	 * @param havaNull
	 * @param showType
	 * @param dmsmType：1:表示dmsm1,2:表示dmsm2,3表示dmsm3,4表示dmsm4,5-value为dmz+dmsm2;
	 *            caption为dmz+dmsm1 6:显示代码和内容，值为DMSM2
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
	 * 将某类代码列表转化为CheckBox HTML信息
	 * 
	 * @param list 代码列表
	 * @param ctrlName HTML CheckBox名称
	 * @param selectValues 默认选中的值以","分隔
	 * @param showType 显示方式 1:只显示代码 2:只显示内容 3:显示代码和内容 *
	 * @param dmsmType：1:表示dmsm1,2:表示dmsm2,3表示dmsm3,4表示dmsm4,5-value为dmz+dmsm2;
	 *            caption为dmsm1
	 * @return
	 * @throws Exception
	 */
	public String transListToCheckBoxHtml(List list, String ctrlName,
			String selectValues, String showType, String dmsmType)
			throws Exception;
	
}
