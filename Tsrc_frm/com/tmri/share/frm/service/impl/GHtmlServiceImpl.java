package com.tmri.share.frm.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.service.GHtmlService;
import com.tmri.share.frm.util.StringUtil;

@Service
public class GHtmlServiceImpl implements GHtmlService{

	@Autowired
	private GSysparaCodeDao gSysparaCodeDao;
	
	public String transDmlbToOptionHtml(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String ywdx,
			String dmsmType, String dmsmValue) {
		String _returnStrings = "";
		List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
		if (!ywdx.equals("")) {
			List tmpList = new Vector();
			Code code = null;
			for (int i = 0; i < list.size(); i++) {
				code = (Code) list.get(i);
				if (code.getYwdx().equals("")
						|| code.getYwdx().indexOf(ywdx) >= 0) {
					if ((dmsmType.equals("dmsm1") && code.getDmsm1().equals(
							dmsmValue))
							|| (dmsmType.equals("dmsm2") && code.getDmsm2()
									.equals(dmsmValue))
							|| (dmsmType.equals("dmsm3") && code.getDmsm3()
									.equals(dmsmValue))
							|| (dmsmType.equals("dmsm4") && code.getDmsm4()
									.equals(dmsmValue)))
						tmpList.add(code);
					else if ((dmsmType.equals("dmsm1,") && code.getDmsm1()
							.indexOf(dmsmValue) >= 0)
							|| (dmsmType.equals("dmsm2,") && code.getDmsm2()
									.indexOf(dmsmValue) >= 0)
							|| (dmsmType.equals("dmsm3,") && code.getDmsm3()
									.indexOf(dmsmValue) >= 0)
							|| (dmsmType.equals("dmsm4,") && code.getDmsm4()
									.indexOf(dmsmValue) >= 0)) {
						tmpList.add(code);
					}
				}
			}
			_returnStrings = transListToOptionHtml(tmpList, defauls, havaNull,
					showType);
		} else {
			List tmpList = new Vector();
			Code code = null;
			for (int i = 0; i < list.size(); i++) {
				code = (Code) list.get(i);
				if ((dmsmType.equals("dmsm1") && code.getDmsm1().equals(
						dmsmValue))
						|| (dmsmType.equals("dmsm2") && code.getDmsm2().equals(
								dmsmValue))
						|| (dmsmType.equals("dmsm3") && code.getDmsm3().equals(
								dmsmValue))
						|| (dmsmType.equals("dmsm4") && code.getDmsm4().equals(
								dmsmValue)))
					tmpList.add(code);
				else if ((dmsmType.equals("dmsm1,") && code.getDmsm1().indexOf(
						dmsmValue) >= 0)
						|| (dmsmType.equals("dmsm2,") && code.getDmsm2()
								.indexOf(dmsmValue) >= 0)
						|| (dmsmType.equals("dmsm3,") && code.getDmsm3()
								.indexOf(dmsmValue) >= 0)
						|| (dmsmType.equals("dmsm4,") && code.getDmsm4()
								.indexOf(dmsmValue) >= 0)) {
					tmpList.add(code);
				}
			}
			_returnStrings = transListToOptionHtml(tmpList, defauls, havaNull,
					showType);
		}
		return _returnStrings;
	}
	
	public String transListToOptionHtml(List list, String defauls,boolean havaNull, String showType) {
		try {

			String _returnStrings = "";
			if (showType == null) {
				showType = "2";
			}
			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}
			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Code frmCode = (Code) iterator.next();
					// ֻ��ʾ����
					if (showType.equals("1")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ "</option>";
						}
					}
					// ֻ��ʾ����
					if (showType.equals("2")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
					// ��ʾ���������
					if (showType.equals("3")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ ":" + frmCode.getDmsm1() + "</option>";
						}
					}
					// ��ʾ����ʹ���ֵ+����˵��2
					if (showType.equals("4")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm2() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ ":" + frmCode.getDmsm2() + "</option>";
						}
					}
					// ��ʾ����ʹ���ֵ+����˵��2
					if (showType.equals("5")) {
						if (frmCode.getDmsm2().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmsm2() + " selected>"
									+ frmCode.getDmsm2() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmsm2() + ">"
									+ frmCode.getDmsm2() + "</option>";
						}
					}
					// ��ʾ�����DMSM1 ֵΪ����˵��2
					if (showType.equals("6")) {
						if (frmCode.getDmsm2().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmsm2() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmsm2() + ">"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
					// ��ʾDMSM1��DMSM1
					if (showType.equals("7")) {
						if (frmCode.getDmsm2().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmsm1() + " selected>"
									+ frmCode.getDmsm1() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmsm1() + ">"
									+ frmCode.getDmsm1() + "</option>";
						}
					}
					// ��ʾdmsm1��ƴ����dmz:dmsm1,����showType=3��ʽ�Ĵ���ƴ������
					if (showType.equals("8")) {
						_returnStrings = _returnStrings
								+ "<option value="
								+ StringUtil.getAllFirstLetter(frmCode
										.getDmsm1()) + ">" + frmCode.getDmz()
								+ ":" + frmCode.getDmsm1() + "</option>";
					}
					// ��ʾ����ʹ���ֵ+����˵��3
					if (showType.equals("9")) {
						if (frmCode.getDmz().equals(defauls)) {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + " selected>"
									+ frmCode.getDmz() + ":"
									+ frmCode.getDmsm3() + "</option>";
						} else {
							_returnStrings = _returnStrings + "<option value="
									+ frmCode.getDmz() + ">" + frmCode.getDmz()
									+ ":" + frmCode.getDmsm3() + "</option>";
						}
					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	
	public String transDmlbToOptionHtml(String xtlb, String dmlb,
			String defauls, boolean havaNull, String showType, String ywdx)
			throws Exception {
		String _returnStrings = "";
		try {
			List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
			if (!ywdx.equals("")) {
				List tmpList = new Vector();
				Code code = null;
				for (int i = 0; i < list.size(); i++) {
					code = (Code) list.get(i);
					if (code.getYwdx().equals("")
							|| code.getYwdx().indexOf(ywdx) >= 0) {
						tmpList.add(code);
					}
				}
				_returnStrings =transListToOptionHtml(tmpList, defauls,
						havaNull, showType);
			} else {
				_returnStrings =transListToOptionHtml(list, defauls, havaNull,
						showType);
			}
		} catch (Exception e) {
			throw new Exception("��ȡ����(" + xtlb + "." + dmlb + ")����");
		}
		return _returnStrings;
	}

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
			String defauls, boolean havaNull, String dmsmType) throws Exception {
		try {
			String _returnStrings = "";
			String dmz = "";
			String dmsm = "";
			if (havaNull) {
				_returnStrings = "<option value=''></option>";
			}
			List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Code frmCode = (Code) iterator.next();
					// ֻ��ʾ����
					if (dmsmType.equals("1")) {
						dmsm = frmCode.getDmsm1();
						dmz = dmsm;
					} else if (dmsmType.equals("2")) {
						dmsm = frmCode.getDmsm2();
						dmz = dmsm;
					} else if (dmsmType.equals("3")) {
						dmsm = frmCode.getDmsm3();
						dmz = dmsm;
					} else if (dmsmType.equals("4")) {
						dmsm = frmCode.getDmsm4();
						dmz = dmsm;
					} else if (dmsmType.equals("5")) {
						dmz = frmCode.getDmz() + ":" + frmCode.getDmsm2();
						dmsm = frmCode.getDmz() + ":" + frmCode.getDmsm1();
					} else {
						dmz = frmCode.getDmsm2();
						dmsm = frmCode.getDmz() + ":" + frmCode.getDmsm1();
					}
					if (dmz.equals(defauls)) {
						_returnStrings = _returnStrings + "<option value="
								+ dmz + " selected>" + dmsm + "</option>";
					} else {
						_returnStrings = _returnStrings + "<option value="
								+ dmz + ">" + dmsm + "</option>";
					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	

	public String transDmlbToCheckBoxHtml(String xtlb, String dmlb,
			String ctrlName, String selectValues, String showType) {
		String _returnStrings = "";
		List list = this.gSysparaCodeDao.getCodes(xtlb, dmlb);
		_returnStrings = transListToCheckBoxHtml(list, ctrlName, selectValues,
				showType);
		return _returnStrings;
	}

	public String transListToCheckBoxHtml(List list, String ctrlName,
			String selectValues, String showType) {
		try {

			String _returnStrings = "";
			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Code frmCode = (Code) iterator.next();
					// ֻ��ʾ����
					if (showType.equals("1")) {
						if (selectValues != null
								&& selectValues.indexOf(frmCode.getDmz()) >= 0) {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + frmCode.getDmz()
									+ "' checked>" + frmCode.getDmz()
									+ "&nbsp;";
						} else {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + frmCode.getDmz()
									+ "'>" + frmCode.getDmz() + "&nbsp;";
						}

					}
					// ֻ��ʾ����
					if (showType.equals("2")) {
						if (selectValues != null
								&& selectValues.indexOf(frmCode.getDmz()) >= 0) {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + frmCode.getDmz()
									+ "' checked>" + frmCode.getDmsm1()
									+ "&nbsp;";
						} else {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + frmCode.getDmz()
									+ "'>" + frmCode.getDmsm1() + "&nbsp;";
						}

					}
					// ��ʾ���������
					if (showType.equals("3")) {
						if (selectValues != null
								&& selectValues.indexOf(frmCode.getDmz()) >= 0) {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + frmCode.getDmz()
									+ "' checked>" + frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "&nbsp;";
						} else {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + frmCode.getDmz()
									+ "'>" + frmCode.getDmz() + ":"
									+ frmCode.getDmsm1() + "&nbsp;";
						}

					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

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
			throws Exception {
		try {
			String _returnStrings = "";
			String dmz = "";
			String dmsm = "";

			if (list != null) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Code frmCode = (Code) iterator.next();
					dmz = frmCode.getDmz();
					// ֻ��ʾ����
					if (dmsmType.equals("1")) {
						dmsm = frmCode.getDmsm1();
					} else if (dmsmType.equals("2")) {
						dmsm = frmCode.getDmsm2();
					} else if (dmsmType.equals("3")) {
						dmsm = frmCode.getDmsm3();
					} else if (dmsmType.equals("4")) {
						dmsm = frmCode.getDmsm4();
					} else {
						dmz = frmCode.getDmz() + ":" + frmCode.getDmsm2();
						dmsm = frmCode.getDmz() + ":" + frmCode.getDmsm1();
					}

					// ֻ��ʾ����
					if (showType.equals("1")) {
						if (selectValues != null
								&& selectValues.indexOf(dmz) >= 0) {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + dmz
									+ "' checked>" + dmsm + "&nbsp;";
						} else {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + dmz + "'>" + dmz
									+ "&nbsp;";
						}

					}
					// ֻ��ʾ����
					if (showType.equals("2")) {
						if (selectValues != null
								&& selectValues.indexOf(frmCode.getDmz()) >= 0) {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + dmz
									+ "' checked>" + dmsm + "&nbsp;";
						} else {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + dmz + "'>"
									+ dmsm + "&nbsp;";
						}

					}
					// ��ʾ���������
					if (showType.equals("3")) {
						if (selectValues != null
								&& selectValues.indexOf(frmCode.getDmz()) >= 0) {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + dmz
									+ "' checked>" + dmz + ":" + dmsm
									+ "&nbsp;";
						} else {
							_returnStrings = _returnStrings
									+ "<input type='checkbox' name='"
									+ ctrlName + "' value='" + dmz + "'>" + dmz
									+ ":" + dmsm + "&nbsp;";
						}

					}
				}
			}
			return _returnStrings;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
}
