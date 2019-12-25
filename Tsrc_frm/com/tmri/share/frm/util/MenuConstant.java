package com.tmri.share.frm.util;
public final class MenuConstant{
	public static final String P_open="1";
	public static final String P_list="2";
	public static final String P_detail="3";
	public static final String P_count="4";
	public static final String P_save="8";
	public static final String P_request="9";
	public static final String M_AlarmCtrl="R114"; // 预警管理
	public static final String F_AlarmCtrl_Confirm="B020";// 预警管理中的接警管理
	public static final String F_AlarmCtrl_Handle="B022";// 预警管理中的处警管理
	public static final String F_AlarmCtrl_Query="B021";// 预警管理中的预警查询
	public static final String M_TfcPassSuspCtrl="B025";// 实时嫌疑车辆预警
	
	public static final String M_TfcCtrl_Query="A030";// 预警管理中的预警查询
	public static final String F_TfcQueryCtrl_Local = "l111"; // 本地精确查询
	public static final String F_TfcQueryCtrl_Province="l112"; // 本省精确查询
	public static final String F_TfcQueryCtrl_Roam="l113"; // 全省精确查询
	
	
	public static final String F_AlarmQueryCtrl=""; 	// 预警信息查询
	public static final String F_SuspectQueryCtrl=""; 	// 布控信息查询
	public static final String F_PassLogCtrl = "";	  	// 轨迹分析日志查询 
	public static final String F_DevqueryCtrl=""; 		// 卡口信息查询
	 	
	public static final String F_PassQueryCtrl_Fuzzy_Local="l121";// 本地模糊查询
	public static final String F_PassQueryCtrl_Fuzzy_Province="l122";// 本省模糊查询
	public static final String F_PassQueryCtrl_Fuzzy_ProvinceRoaming="l123";// 省内漫游查询
	public static final String M_DepartmentCtrl="K010"; // 部门管理
	public static final String F_DepartmentCtrl_Traffic="l921";// 部门管理中的交警部门管理
	public static final String F_DepartmentCtrl_Police="l922";// 部门管理中的公安部门管理
	public static final String F_DepartmentCtrl_Third ="l923";//部门管理中的企业部门管理
	public static final String F_SysUserCtrl_Traffic="l941";// 用户管理中的交警用户管理
	public static final String F_SysUserCtrl_Police="l942";// 用户管理中的公安用户管理
	public static final String M_SysuserCtrl = "K012";
	public static final String F_SysuserCtrl_JJ = "l941";//交警用户管理
	public static final String F_SysuserCtrl_DGA = "l942";//大公安用户管理
	public static final String M_SuspCtrl="R110"; // 布控申请
	public static final String M_ASuspCtrl="R111"; // 布控审核审批
	public static final String F_SuspCtrl_Apply="l011";// 布控管理中的布控申请
	public static final String F_SuspCtrl_Batch="l012";// 布控管理中的批量布控申请
	public static final String F_SuspCtrl_Audit="l013";// 布控管理中的布控审核
	public static final String F_SuspCtrl_Approve="l014";// 布控管理中的布控审批
	public static final String F_SuspCtrl_Query="l015";// 布控管理中的布控查询
	public static final String F_ASuspCtrl_Query="l016";// 布控管理中的布控查询
	public static final String M_CsuspCtrl="R112"; // 撤控管理
	public static final String F_CsuspCtrl_Apply="l021";// 撤控管理中的撤控申请
	public static final String F_CsuspCtrl_Batch="l022";// 撤控管理中的批量撤控申请
	public static final String F_CsuspCtrl_Audit="l023";// 撤控管理中的撤控审核
	public static final String F_CsuspCtrl_Approve="l024";// 撤控管理中的撤控审批
	public static final String F_CsuspCtrl_Query="l025";// 撤控管理中的撤控查询
	public static final String M_SuspCtrl_Workdefine = "R119";
	
	public static final String M_NonLocalCtrl="R134"; // 辖区内外地车辆布控申请
	public static final String M_NonLocalCtrl_Query="I133";// 辖区内外地车辆查询
	public static final String M_NonLocalCtrl_SuspQuery="I134";// 辖区内外地车辆布控后查询
	
	public static final String E_EspecialCtrl2="l136"; // 重点车辆纳入辖区管理
	public static final String E_EspecialCtrl1="l135"; // 重点车辆撤销辖区管理
}