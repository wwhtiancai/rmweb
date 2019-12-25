package com.tmri.share.frm.util;

import java.util.TreeMap;

public class Constants {
	public static final String SYS_SYSTEMTOPIC = "机动车电子标识发行管理系统";
	// 当前系统加载的系统类别
	public static final String SYS_SYSTEM_XTLB = "SYS_SYSTEM_XTLB";

	public static String SYS_VERSION = "1.0";
	public static String SYS_MADE_DATE = "2013.07.16";
	public static String SYS_TYPE = "1";// 1-工作库 2-资源库
	public static String SYS_SSL_PORT = "9443";
	public static String SYS_PATH = "drv";
	public static String SYS_CAS_SWITCH = "";
	public static String SYS_XTZT = "1";// 系统状态
	public static String SYS_SG_TJKSR = "01";// 事故统计开始日
	public static String SYS_SG_SFSYY = "2";// 统计月开始是否是上一个月 1-是 2-否
	public static String SYS_SFT = "";// 省份头
	public static String SYS_CONTEXT = "rmweb";
	public static String SYS_PORT = "";
	public static String SYS_OTHER_STATE = "1";
	// 内部用户，用于增删代码类别
	public static final String INNER_USER = "admin";
	// 系统错误信息
	public static final String SYS_NO_THE_USER = "系统里面没有该用户";
	public static final String SYS_CHANGE_PASSWORD = "请修改密码";
	public static final String SYS_PASSWORD_INVALID = "密码超过有效期";
	public static final String SYS_USER_INVALID = "用户超过有效期";
	public static final String SYS_IP_INVALID = "不是合法的IP地址!";
	public static final String SYS_PASSWORD_ERROR = "密码错误!";
	public static final String SYS_NO_HIGHXZQH = "未找到管理部门!";
	public static final String SYS_FIRST_LOGIN = "第一次登陆此系统!";
	public static final String SYS_NO_RIGHT = "没有给该登陆用户授权!";
	public static final String SYS_ZT_INVALID = "用户失效!";
	public static final String SYS_IP_NOTEXISTS = "IP地址不在系统参数设定的范围内!";
	public static final String SYS_SFZ_LOGIN = "当前用户必须使用二代证登录系统!";
	public static final String SYS_PKI_LOGIN = "当前用户必须使用数字证书（PKI）登录系统!";
	public static final String SYS_QUERYERR_YZM = "查询验证报错";
	public static final String SYS_BMFZJG_ERR = "部门发证机关不在本地发证机关范围内！";
	public static final String SYS_GLY_PASS = "系统管理员用户密码强度必须三级及以上，请修改密码!";
	public static final String SYS_PTYH_PASS = "普通用户密码强度必须二级及以上，请修改密码!";
	public static final String SYS_PTYH_GDDZTS = "普通用户需设置固定IP地址的终端访问系统!";
	public static final String SYS_PTYH_GDDZFS = "普通用户必须在固定IP地址范围内的终端上访问系统!";
	public static String SYS_WGFWFWQ_BJ = "0";// 外挂访问服务器标记
	public static final String USERSESSION = "USERSESSION"; // session 用户信息
	// 存储过程操作数据时的操作类型
	public static final int OP_TYPE_INSERT_FLAG = 1;
	public static final int OP_TYPE_UPDATE_FLAG = 2;
	public static final int OP_TYPE_DELETE_FLAG = 3;
	// 安全管理模块验证提示消息
	public static final String SAFE_CTRL_MSG_0 = "正常";
	public static final String SAFE_CTRL_MSG_81 = "安装点未备案";
	public static final String SAFE_CTRL_MSG_82 = "应用服务器未登记";
	public static final String SAFE_CTRL_MSG_83 = "密钥不合法";
	public static final String SAFE_CTRL_MSG_84 = "密钥过期，请联系系统管理员";
	public static final String SAFE_CTRL_MSG_85 = "应用服务器锁定，需系统管理员解锁";
	public static final String SAFE_CTRL_MSG_91 = "应用系统版本即将过期";
	public static final String SAFE_CTRL_MSG_92 = "应用系统版本已经过期，不能使用";
	public static final String SAFE_CTRL_MSG_93 = "连接数据库异常，请联系系统管理员";
	public static final String SAFE_CTRL_MSG_99 = "无法识别安全监测标记";
	// 系统自身安全
	public static final String SAFE_CTRL_MSG_101 = "TERR02:无权访问该功能模块";
	public static final String SAFE_CTRL_MSG_102 = "TERR03:未认证HTTP请求";
	public static final String SAFE_CTRL_MSG_103 = "TERR04:未签名HTTP请求";
	public static final String SAFE_DATA_ERROR = "校验位不正确，数据被非法修改";
	// 系统管理菜单定义
	public static final String MENU_JSGL = "K011";// 角色管理
	public static final String MENU_BMGL = "K010";// 部门管理
	public static final String MENU_BMZH = "9506";// 部门转换
	public static final String MENU_RWGL = "K061";// 任务管理
	public static final String MENU_YHGL = "K012";// 用户管理
	public static final String MENU_YHGL_GJ = "K013";// 用户管理轨迹查询
	public static final String MENU_YHGL_YH = "R991";// 用户
	public static final String MENU_YHGL_SQ = "K010";// 授权
	public static final String MENU_ZYQX_QX = "R995";// 自由权限取消
	public static final String MENU_XTCS = "K021";// 系统参数管理
	public static final String MENU_BMCS = "K029";// 部门参数管理
	public static final String MENU_NCSX = "K026";// 内存参数刷新
	public static final String MENU_CSDMWH = "K025";// 厂商代码维护
	public static final String MENU_TXXXSCWH = "R959";// 通行信息上传维护

	// 接口管理菜单定义
	public static String MENU_JKSQ = "R981";// 接口申请登记
	public static String MENU_JKSQM = "R982";// 接口授权码查询
	public static String MENU_JKRZCX = "R983";// 接口访问日志查询
	public static String MENU_JKFWTJ = "R984";// 接口访问量统计
	public static String MENU_JKXXFX = "R985";// 接口访问线性分析
	public static final String MENU_DABH = "9601";// 部门档案编号区段维护
	public static final String MENU_FGZRWH = "R955";// 非工作时间维护
	public static final String MENU_DMWH = "R953";// 代码维护
	public static final String MENU_DMCX = "K023";// 代码查询
	public static final String MENU_XXGG = "K024";// 信息公告
	public static final String MENU_CSRZCX = "9309";// 传输日志查询
	public static final String MENU_XZQHWH = "R991";// 行政区划维护
	public static final String MENU_BMYWDMWH = "R954";// 部门相关代码维护维护
	public static final String MENU_RZCX = "K013";// 日志查询
	public static final String MENU_BBBD = "9204";// 程序版本比对
	public static final String MENU_SMSSETUP = "R977";// 消息接收用户设置
	public static final String MENU_INFORMSETUP = "9110";// 民警工作定期提醒设置
	public static final String MENU_DEPPARASETUP = "9109";// 部门参数修改级别设置
	public static final String MENU_COMMONCODECTRL = "G023";// 程序版本比对
	public static final String MENU_YWGG = "A001";// 业务公告
	public static final String FUNC_SGYW = "A014";// 事故业务公告
	// 系统类别常量定义
	public static final String SYS_JSZ_XTLB = "02";
	public static final String SYS_XTLB_FRAME = "00";
	public static final String SYS_XTLB_DMTMS = "06";
	public static final String SYS_XTLB_ACD = "03";
	public static final String SYS_XTLB_VIO = "04";
	public static final String SYS_XTLB_BASE = "05";
	public static final String SYS_XTLB_REVISE = "32";
	public static final String SYS_ALL_XTLB = "01,02,03,04,05,06,00,31,32,50";

	public static final String SYS_XTLB_MEASURMENT = "60";

	// 提示语信息编码格式说明
	// 提示信息等级
	public static final String MSG_RANK_OK = "0";// 成功
	public static final String MSG_RANK_FE = "1";// 严重错误
	public static final String MSG_RANK_IE = "2";// 中级错误
	public static final String MSG_RANK_LE = "3";// 低级错误
	// 提示信息层级
	public static final String MSG_LEVEL_JS = "J";
	public static final String MSG_LEVEL_CTRL = "C";
	public static final String MSG_LEVEL_SERVER = "S";
	public static final String MSG_LEVEL_DAO = "D";
	public static final String MSG_LEVEL_DB = "O";
	// 用户会话变量名定义
	public static final String SESSION_CHECKWEBKEY = "_checkwebkey";
	// 验证码
	public static final String QUERY_YZM = "xxsaaaxxxssaasss";
	public static final String SYS_XTLB_QUERY = "12";
	public static final String CHART_STYLE = "  <styles>" + "   <definition>"
			+ "       <style name='myCaptionFont' type='font' size='24'  bold='1' />"
			+ "       <style name='myDataLabels' type='font' size='12'  bold='0' />"
			+ "       <style name='myValuesFont' type='font' size='12'  bold='0'/>" + "   </definition>" + "   <application>"
			+ "            <apply toObject='Caption' styles='myCaptionFont' />" + "            <apply toObject='DataLabels' styles='myDataLabels' />"
			+ "            <apply toObject='DataValues' styles='myValuesFont' />"
			+ "            <apply toObject='YAXISVALUES' styles='myValuesFont' />" + "            <apply toObject='ToolTip' styles='myValuesFont' />"
			+ "   </application>" + "  </styles>";
	// 字符串数组分割符号
	public static final String DELIMITER1 = "\\$\\$";
	public static final String DELIMITER2 = "~~";
	// 指纹仪OCX控件函数
	public static final String OCX_OpenDev = "openDev();";
	public static final String OCX_CloseDev = "closeDev();";
	public static final String OCX_GetFeature = "getFeature();";
	// 自助服务
	public static String SYS_VOL1 = "858993459";
	public static String SYS_VOL2 = "2147483646";
	// 自助服务业务办理过程中，自动跳出时间
	public static String SYS_TIMEOUT = "60";
	// 获取道路代码时是否加管理部门条件
	public static String SYS_GLBM_TJ = "65";
	// 20120227总队高速部门直接管理
	public static String SYS_GLBM_GS = "13,42";
	public static String SYS_SSLPORT;

	// 文档系统用
	public static final String SYS_DTYPE = "10";
	public static final String SYS_BLV = "1";

	// ServletContext注册Key
	public static String SYS_SERVLET_CONTEXT = "TRFF_WEB_SERVLET_CONTEXT";
	// 注册应用服务名
	public static String SYS_SERVERNAME = "";
	// 安装代码
	public static String SYS_AZDM = "";
	// 安装代码
	public static String SYS_XTLB = "";

	public static String MEM_FRM_FUNCTION = "frmfunction";
	public static String MEM_SYSPARA = "syspara";
	public static String MEM_SYSPARAVALUE = "sysparavalue";
	public static String MEM_ROAD = "road";
	public static String MEM_TOLLGATE = "tollgate";
	public static String MEM_DEPARTMENT = "department";
	public static String MEM_FRM_CODE = "frm_code";
	// 20150113
	public static String MEM_ALL_DEPARTMENT = "bas_all_dept";
	public static String MEM_ALL_DEPARTMENT_XJ = "bas_all_dept_xj";
	public static String MEM_ALL_XZQH = "bas_all_xzqh";

	public static String MEM_RELATION = "relation";
	public static String MEM_POLICESTATION = "policestation";
	public static String MEM_MOUNT = "mount";
	public static String MEM_MOUNTCFG = "mountcfg";
	public static String MEM_JKXLH = "trff_jkxlh";
	public static String MEM_SUPPLIER = "supplier";
	// zhoujn 20140915 备案发证机关的url信息
	public static String MEM_FZJGURL = "fzjgurl";

	// 红名单信息
	public static String MEM_SPECIAL = "special";
	public static String MEM_SPECIAL_LOCAL = "special_local";
	// 红名单信息,下级
	public static String MEM_SPECIAL_JG = "specialjg";
	public static final String MEM_ALARM_CONFIG = "alarm_config";

	// 卡口转发
	public static String MEM_TOLLGATE_FORWARD = "TOLLGATE_FORWARD";

	public static String MEM_DEV_VIO_EQUIPMENT = "dev_vio_equipment";

	public static final String P_request = "9";

	public static final String JJ = "17";
	public static final String GA = "99";
	public static final String YES = "1";
	public static final String NO = "2";

	public static final String PROC_PARA = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,";
	public static String DEBUG = ConstantsDebug.DEBUG_OFF;

	public static final String NO_EQUIP_GROUP = "ZZZZ";
	// 安全模块操作类型：1-初始化，2-升级
	public static final int SECURY_MODEL_INIT = 1;
	public static final int SECURY_MODEL_UPDATE = 2;

	// 时间格式
	public static final String DateFormate2day = "yyyy-MM-dd"; // 换卡

	/**
	 * 数据库加密key
	 */
	public static final String DB_PW_KEY = "!r@t#ghjkl;lokik";

	/**
	 * 机动车电子标识发行系统常量
	 */
	public static final int DEFAULT_VEHICLE_COLOR_VALUE = 15;
	
	/**
	 * 汽车电子标签报废状态
	 */
	public static final int ERI_SCRAP_STATE = 5;

	// 设备类型
	public enum DeviceType {
		/**
		 * 卡口
		 */
		TOLLGATE("01"),

		/**
		 * 执法取证设备
		 */
		VIO_EQUIPMENT("02"),

		/**
		 * 视频点位
		 */
		VGAT_POSITION("03"),

		/**
		 * 执法服务站
		 */
		STATION("04");

		DeviceType(String value) {
			this.val = value;
		}

		public String toString() {
			return this.val;
		}

		private String val;
	}

	public enum RecgTollgateConfigKey {
		/**
		 * 开始时段
		 */
		KSSD("KSSD"),

		/**
		 * 结束时段
		 */
		JSSD("JSSD"),

		JPKSSD("JPKSSD"),

		/**
		 * 结束时段
		 */
		JPJSSD("JPJSSD"),

		WNJKSSD("WNJKSSD"),

		/**
		 * 结束时段
		 */
		WNJJSSD("WNJJSSD"),

		WBFKSSD("WBFKSSD"),

		/**
		 * 结束时段
		 */
		WBFJSSD("WBFJSSD"),

		/**
		 * 比对车辆类型
		 */
		CLLX("CLLX"),

		/**
		 * 核查未报废
		 */
		BDWBF("BDWBF"),

		/**
		 * 未报废车使用性质
		 */
		WBFSYXZ("WBFSYXZ"),

		/**
		 * 核查未检验
		 */
		BDWNJ("BDWNJ"),

		/**
		 * 未检验车使用性质
		 */
		WNJSYXZ("WNJSYXZ"),

		/**
		 * 关联周边卡口
		 */
		GLZBKK("GLZBKK"),

		/**
		 * 前后关联时段
		 */
		QHGLSD("QHGLSD"),

		/**
		 * 禁行事件编号
		 */
		JXSJBH("JXSJBH"),

		/**
		 * 禁行事件配置
		 */
		JXSJPZ("JXSJPZ");

		private String value;

		private RecgTollgateConfigKey(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	/**
	 * 分析事件
	 * 
	 * @author Administrator
	 * 
	 */
	public enum AnaEvt {
		/**
		 * 假、套牌嫌疑分析
		 */
		FACK_DECK("1"),

		/**
		 * 无牌嫌疑分析
		 */
		NO_PLATE("2"),

		/**
		 * 闯禁行分析
		 */
		FORBIDDEN("3");

		private String value;

		private AnaEvt(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	public static final String DEV_PNG_PATH = "images\\mapshow\\";

	public static final String[] dev_png_codes = { "station_xian", "station_shi", "station_sheng", "cambq_zc", "cambq_bzc", "camgq_zc", "camgq_bzc",
			"inducement", "flow", "weather", "vio", "signal", "cqlkkk_bzc", "cqlkkk_zc", "cqlkkk_ty", "cqdlkk_ty", "cqdlkk_zc", "cqdlkk_bzc",
			"glsfzkk_bzc", "glsfzkk_ty", "glsfzkk_zc", "shengkk_ty", "shengkk_zc", "shengkk_bzc", "shikk_ty", "shikk_zc", "shikk_bzc", "xiankk_bzc",
			"xiankk_zc", "xiankk_ty", "glzxkk_ty", "glzxkk_zc", "glzxkk_bzc", "department", "traffic", "park", "repair", "fire", "hospital" };

	public static final String[] dev_png_names = { "交通安全执法服务站_县际.png", "交通安全执法服务站_市际.png", "交通安全执法服务站_省际.png", "标清_正常.png", "标清_不正常.png",
			"高清_正常.png", "高清_不正常.png", "可变信息标志诱导屏.png", "流量监测设备.png", "气象监测设备.png", "违法取证设备.png", "信号控制系统.png", "城区路口卡口_不正常.png", "城区路口卡口_正常.png",
			"城区路口卡口_停用.png", "城区道路卡口_停用.png", "城区道路卡口_正常.png", "城区道路卡口_不正常.png", "公路收费站卡_不正常.png", "公路收费站卡_停用.png", "公路收费站卡_正常.png", "省际卡口_停用.png",
			"省际卡口_正常.png", "省际卡口_不正常.png", "市际卡口_停用.png", "市际卡口_正常.png", "市际卡口_不正常.png", "县际卡口_不正常.png", "县际卡口_正常.png", "县际卡口_停用.png",
			"公路主线卡口_停用.png", "公路主线卡口_正常.png", "公路主线卡口_不正常.png", "交警队.png", "交通应急资源.png", "停车场.png", "维修厂.png", "消防.png", "医院.png" };

	public static final TreeMap<String, String> getDevPngMap() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < dev_png_codes.length; i++) {
			map.put(dev_png_codes[i], DEV_PNG_PATH + dev_png_names[i]);
		}
		return map;
	}

	public static String getDevPngNames(String code) {
		TreeMap<String, String> map = getDevPngMap();
		String name = "";
		if (!map.isEmpty()) {
			name = map.get(code);
		}
		return name;
	}

	public enum Topic {
		/**
		 * 过车信息
		 */
		TOPIC_TFC_PASS("TOPIC_TFC_PASS"),

		/**
		 * 非现场违法信息
		 */
		TOPIC_VIO_SURVEIL("TOPIC_VIO_SURVEIL"),

		/**
		 * 流量检测信息
		 */
		TOPIC_TRAFFIC_FLOW("TOPIC_TRAFFIC_FLOW"),

		/**
		 * 气象检测信息
		 */
		TOPIC_ROAD_WEATHER("TOPIC_ROAD_WEATHER"),

		/**
		 * 交通事件检测信息
		 */
		TOPIC_TRAFFIC_INCIDENT("TOPIC_TRAFFIC_INCIDENT"),

		/**
		 * 交通诱导发布信息
		 */
		TOPIC_TRAFFIC_INDUCEMENT("TOPIC_TRAFFIC_INDUCEMENT"),

		/**
		 * 停车场车辆停车信息
		 */
		TOPIC_VEHICLE_PARK("TOPIC_VEHICLE_PARK"),

		/**
		 * 警车定位信息
		 */
		TOPIC_GPS_CAR("TOPIC_GPS_CAR"),

		/**
		 * 警员定位信息
		 */
		TOPIC_GPS_POLICE("TOPIC_GPS_POLICE"),

		/**
		 * 过车信息统计信息
		 */
		TOPIC_STAT_PASS("TOPIC_STAT_PASS"),

		/**
		 * 二次识别比对信息
		 */
		TOPIC_RECG_CHECK_QUEUE("TOPIC_RECG_CHECK_QUEUE"), ;

		private Topic(String name) {
			this.name = name;
		}

		private String name;

		@Override
		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}

	public enum PicType {
		/**
		 * 过车图片
		 */
		TFC_PASS("1"),

		/**
		 * 停车图片
		 */
		VEH_PARK("2");

		private PicType(String name) {
			this.name = name;
		}

		private String name;

		@Override
		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}
}
