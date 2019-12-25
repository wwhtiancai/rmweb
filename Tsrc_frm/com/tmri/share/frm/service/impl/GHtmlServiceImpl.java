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
					// 只显示代码
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
					// 只显示名称
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
					// 显示代码和名称
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
					// 显示代码和代码值+代码说明2
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
					// 显示代码和代码值+代码说明2
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
					// 显示代码和DMSM1 值为代码说明2
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
					// 显示DMSM1和DMSM1
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
					// 显示dmsm1的拼音和dmz:dmsm1,用于showType=3形式的代码拼音检索
					if (showType.equals("8")) {
						_returnStrings = _returnStrings
								+ "<option value="
								+ StringUtil.getAllFirstLetter(frmCode
										.getDmsm1()) + ">" + frmCode.getDmz()
								+ ":" + frmCode.getDmsm1() + "</option>";
					}
					// 显示代码和代码值+代码说明3
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
			throw new Exception("获取代码(" + xtlb + "." + dmlb + ")出错！");
		}
		return _returnStrings;
	}

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
					// 只显示代码
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
					// 只显示代码
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
					// 只显示名称
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
					// 显示代码和名称
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
					// 只显示代码
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

					// 只显示代码
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
					// 只显示名称
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
					// 显示代码和名称
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
