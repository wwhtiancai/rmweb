package com.tmri.share.frm.util;

import java.util.TreeMap;

public class Constants {
	public static final String SYS_SYSTEMTOPIC = "���������ӱ�ʶ���й���ϵͳ";
	// ��ǰϵͳ���ص�ϵͳ���
	public static final String SYS_SYSTEM_XTLB = "SYS_SYSTEM_XTLB";

	public static String SYS_VERSION = "1.0";
	public static String SYS_MADE_DATE = "2013.07.16";
	public static String SYS_TYPE = "1";// 1-������ 2-��Դ��
	public static String SYS_SSL_PORT = "9443";
	public static String SYS_PATH = "drv";
	public static String SYS_CAS_SWITCH = "";
	public static String SYS_XTZT = "1";// ϵͳ״̬
	public static String SYS_SG_TJKSR = "01";// �¹�ͳ�ƿ�ʼ��
	public static String SYS_SG_SFSYY = "2";// ͳ���¿�ʼ�Ƿ�����һ���� 1-�� 2-��
	public static String SYS_SFT = "";// ʡ��ͷ
	public static String SYS_CONTEXT = "rmweb";
	public static String SYS_PORT = "";
	public static String SYS_OTHER_STATE = "1";
	// �ڲ��û���������ɾ�������
	public static final String INNER_USER = "admin";
	// ϵͳ������Ϣ
	public static final String SYS_NO_THE_USER = "ϵͳ����û�и��û�";
	public static final String SYS_CHANGE_PASSWORD = "���޸�����";
	public static final String SYS_PASSWORD_INVALID = "���볬����Ч��";
	public static final String SYS_USER_INVALID = "�û�������Ч��";
	public static final String SYS_IP_INVALID = "���ǺϷ���IP��ַ!";
	public static final String SYS_PASSWORD_ERROR = "�������!";
	public static final String SYS_NO_HIGHXZQH = "δ�ҵ�������!";
	public static final String SYS_FIRST_LOGIN = "��һ�ε�½��ϵͳ!";
	public static final String SYS_NO_RIGHT = "û�и��õ�½�û���Ȩ!";
	public static final String SYS_ZT_INVALID = "�û�ʧЧ!";
	public static final String SYS_IP_NOTEXISTS = "IP��ַ����ϵͳ�����趨�ķ�Χ��!";
	public static final String SYS_SFZ_LOGIN = "��ǰ�û�����ʹ�ö���֤��¼ϵͳ!";
	public static final String SYS_PKI_LOGIN = "��ǰ�û�����ʹ������֤�飨PKI����¼ϵͳ!";
	public static final String SYS_QUERYERR_YZM = "��ѯ��֤����";
	public static final String SYS_BMFZJG_ERR = "���ŷ�֤���ز��ڱ��ط�֤���ط�Χ�ڣ�";
	public static final String SYS_GLY_PASS = "ϵͳ����Ա�û�����ǿ�ȱ������������ϣ����޸�����!";
	public static final String SYS_PTYH_PASS = "��ͨ�û�����ǿ�ȱ�����������ϣ����޸�����!";
	public static final String SYS_PTYH_GDDZTS = "��ͨ�û������ù̶�IP��ַ���ն˷���ϵͳ!";
	public static final String SYS_PTYH_GDDZFS = "��ͨ�û������ڹ̶�IP��ַ��Χ�ڵ��ն��Ϸ���ϵͳ!";
	public static String SYS_WGFWFWQ_BJ = "0";// ��ҷ��ʷ��������
	public static final String USERSESSION = "USERSESSION"; // session �û���Ϣ
	// �洢���̲�������ʱ�Ĳ�������
	public static final int OP_TYPE_INSERT_FLAG = 1;
	public static final int OP_TYPE_UPDATE_FLAG = 2;
	public static final int OP_TYPE_DELETE_FLAG = 3;
	// ��ȫ����ģ����֤��ʾ��Ϣ
	public static final String SAFE_CTRL_MSG_0 = "����";
	public static final String SAFE_CTRL_MSG_81 = "��װ��δ����";
	public static final String SAFE_CTRL_MSG_82 = "Ӧ�÷�����δ�Ǽ�";
	public static final String SAFE_CTRL_MSG_83 = "��Կ���Ϸ�";
	public static final String SAFE_CTRL_MSG_84 = "��Կ���ڣ�����ϵϵͳ����Ա";
	public static final String SAFE_CTRL_MSG_85 = "Ӧ�÷�������������ϵͳ����Ա����";
	public static final String SAFE_CTRL_MSG_91 = "Ӧ��ϵͳ�汾��������";
	public static final String SAFE_CTRL_MSG_92 = "Ӧ��ϵͳ�汾�Ѿ����ڣ�����ʹ��";
	public static final String SAFE_CTRL_MSG_93 = "�������ݿ��쳣������ϵϵͳ����Ա";
	public static final String SAFE_CTRL_MSG_99 = "�޷�ʶ��ȫ�����";
	// ϵͳ����ȫ
	public static final String SAFE_CTRL_MSG_101 = "TERR02:��Ȩ���ʸù���ģ��";
	public static final String SAFE_CTRL_MSG_102 = "TERR03:δ��֤HTTP����";
	public static final String SAFE_CTRL_MSG_103 = "TERR04:δǩ��HTTP����";
	public static final String SAFE_DATA_ERROR = "У��λ����ȷ�����ݱ��Ƿ��޸�";
	// ϵͳ����˵�����
	public static final String MENU_JSGL = "K011";// ��ɫ����
	public static final String MENU_BMGL = "K010";// ���Ź���
	public static final String MENU_BMZH = "9506";// ����ת��
	public static final String MENU_RWGL = "K061";// �������
	public static final String MENU_YHGL = "K012";// �û�����
	public static final String MENU_YHGL_GJ = "K013";// �û�����켣��ѯ
	public static final String MENU_YHGL_YH = "R991";// �û�
	public static final String MENU_YHGL_SQ = "K010";// ��Ȩ
	public static final String MENU_ZYQX_QX = "R995";// ����Ȩ��ȡ��
	public static final String MENU_XTCS = "K021";// ϵͳ��������
	public static final String MENU_BMCS = "K029";// ���Ų�������
	public static final String MENU_NCSX = "K026";// �ڴ����ˢ��
	public static final String MENU_CSDMWH = "K025";// ���̴���ά��
	public static final String MENU_TXXXSCWH = "R959";// ͨ����Ϣ�ϴ�ά��

	// �ӿڹ���˵�����
	public static String MENU_JKSQ = "R981";// �ӿ�����Ǽ�
	public static String MENU_JKSQM = "R982";// �ӿ���Ȩ���ѯ
	public static String MENU_JKRZCX = "R983";// �ӿڷ�����־��ѯ
	public static String MENU_JKFWTJ = "R984";// �ӿڷ�����ͳ��
	public static String MENU_JKXXFX = "R985";// �ӿڷ������Է���
	public static final String MENU_DABH = "9601";// ���ŵ����������ά��
	public static final String MENU_FGZRWH = "R955";// �ǹ���ʱ��ά��
	public static final String MENU_DMWH = "R953";// ����ά��
	public static final String MENU_DMCX = "K023";// �����ѯ
	public static final String MENU_XXGG = "K024";// ��Ϣ����
	public static final String MENU_CSRZCX = "9309";// ������־��ѯ
	public static final String MENU_XZQHWH = "R991";// ��������ά��
	public static final String MENU_BMYWDMWH = "R954";// ������ش���ά��ά��
	public static final String MENU_RZCX = "K013";// ��־��ѯ
	public static final String MENU_BBBD = "9204";// ����汾�ȶ�
	public static final String MENU_SMSSETUP = "R977";// ��Ϣ�����û�����
	public static final String MENU_INFORMSETUP = "9110";// �񾯹���������������
	public static final String MENU_DEPPARASETUP = "9109";// ���Ų����޸ļ�������
	public static final String MENU_COMMONCODECTRL = "G023";// ����汾�ȶ�
	public static final String MENU_YWGG = "A001";// ҵ�񹫸�
	public static final String FUNC_SGYW = "A014";// �¹�ҵ�񹫸�
	// ϵͳ���������
	public static final String SYS_JSZ_XTLB = "02";
	public static final String SYS_XTLB_FRAME = "00";
	public static final String SYS_XTLB_DMTMS = "06";
	public static final String SYS_XTLB_ACD = "03";
	public static final String SYS_XTLB_VIO = "04";
	public static final String SYS_XTLB_BASE = "05";
	public static final String SYS_XTLB_REVISE = "32";
	public static final String SYS_ALL_XTLB = "01,02,03,04,05,06,00,31,32,50";

	public static final String SYS_XTLB_MEASURMENT = "60";

	// ��ʾ����Ϣ�����ʽ˵��
	// ��ʾ��Ϣ�ȼ�
	public static final String MSG_RANK_OK = "0";// �ɹ�
	public static final String MSG_RANK_FE = "1";// ���ش���
	public static final String MSG_RANK_IE = "2";// �м�����
	public static final String MSG_RANK_LE = "3";// �ͼ�����
	// ��ʾ��Ϣ�㼶
	public static final String MSG_LEVEL_JS = "J";
	public static final String MSG_LEVEL_CTRL = "C";
	public static final String MSG_LEVEL_SERVER = "S";
	public static final String MSG_LEVEL_DAO = "D";
	public static final String MSG_LEVEL_DB = "O";
	// �û��Ự����������
	public static final String SESSION_CHECKWEBKEY = "_checkwebkey";
	// ��֤��
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
	// �ַ�������ָ����
	public static final String DELIMITER1 = "\\$\\$";
	public static final String DELIMITER2 = "~~";
	// ָ����OCX�ؼ�����
	public static final String OCX_OpenDev = "openDev();";
	public static final String OCX_CloseDev = "closeDev();";
	public static final String OCX_GetFeature = "getFeature();";
	// ��������
	public static String SYS_VOL1 = "858993459";
	public static String SYS_VOL2 = "2147483646";
	// ��������ҵ���������У��Զ�����ʱ��
	public static String SYS_TIMEOUT = "60";
	// ��ȡ��·����ʱ�Ƿ�ӹ���������
	public static String SYS_GLBM_TJ = "65";
	// 20120227�ܶӸ��ٲ���ֱ�ӹ���
	public static String SYS_GLBM_GS = "13,42";
	public static String SYS_SSLPORT;

	// �ĵ�ϵͳ��
	public static final String SYS_DTYPE = "10";
	public static final String SYS_BLV = "1";

	// ServletContextע��Key
	public static String SYS_SERVLET_CONTEXT = "TRFF_WEB_SERVLET_CONTEXT";
	// ע��Ӧ�÷�����
	public static String SYS_SERVERNAME = "";
	// ��װ����
	public static String SYS_AZDM = "";
	// ��װ����
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
	// zhoujn 20140915 ������֤���ص�url��Ϣ
	public static String MEM_FZJGURL = "fzjgurl";

	// ��������Ϣ
	public static String MEM_SPECIAL = "special";
	public static String MEM_SPECIAL_LOCAL = "special_local";
	// ��������Ϣ,�¼�
	public static String MEM_SPECIAL_JG = "specialjg";
	public static final String MEM_ALARM_CONFIG = "alarm_config";

	// ����ת��
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
	// ��ȫģ��������ͣ�1-��ʼ����2-����
	public static final int SECURY_MODEL_INIT = 1;
	public static final int SECURY_MODEL_UPDATE = 2;

	// ʱ���ʽ
	public static final String DateFormate2day = "yyyy-MM-dd"; // ����

	/**
	 * ���ݿ����key
	 */
	public static final String DB_PW_KEY = "!r@t#ghjkl;lokik";

	/**
	 * ���������ӱ�ʶ����ϵͳ����
	 */
	public static final int DEFAULT_VEHICLE_COLOR_VALUE = 15;
	
	/**
	 * �������ӱ�ǩ����״̬
	 */
	public static final int ERI_SCRAP_STATE = 5;

	// �豸����
	public enum DeviceType {
		/**
		 * ����
		 */
		TOLLGATE("01"),

		/**
		 * ִ��ȡ֤�豸
		 */
		VIO_EQUIPMENT("02"),

		/**
		 * ��Ƶ��λ
		 */
		VGAT_POSITION("03"),

		/**
		 * ִ������վ
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
		 * ��ʼʱ��
		 */
		KSSD("KSSD"),

		/**
		 * ����ʱ��
		 */
		JSSD("JSSD"),

		JPKSSD("JPKSSD"),

		/**
		 * ����ʱ��
		 */
		JPJSSD("JPJSSD"),

		WNJKSSD("WNJKSSD"),

		/**
		 * ����ʱ��
		 */
		WNJJSSD("WNJJSSD"),

		WBFKSSD("WBFKSSD"),

		/**
		 * ����ʱ��
		 */
		WBFJSSD("WBFJSSD"),

		/**
		 * �ȶԳ�������
		 */
		CLLX("CLLX"),

		/**
		 * �˲�δ����
		 */
		BDWBF("BDWBF"),

		/**
		 * δ���ϳ�ʹ������
		 */
		WBFSYXZ("WBFSYXZ"),

		/**
		 * �˲�δ����
		 */
		BDWNJ("BDWNJ"),

		/**
		 * δ���鳵ʹ������
		 */
		WNJSYXZ("WNJSYXZ"),

		/**
		 * �����ܱ߿���
		 */
		GLZBKK("GLZBKK"),

		/**
		 * ǰ�����ʱ��
		 */
		QHGLSD("QHGLSD"),

		/**
		 * �����¼����
		 */
		JXSJBH("JXSJBH"),

		/**
		 * �����¼�����
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
	 * �����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	public enum AnaEvt {
		/**
		 * �١��������ɷ���
		 */
		FACK_DECK("1"),

		/**
		 * �������ɷ���
		 */
		NO_PLATE("2"),

		/**
		 * �����з���
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

	public static final String[] dev_png_names = { "��ͨ��ȫִ������վ_�ؼ�.png", "��ͨ��ȫִ������վ_�м�.png", "��ͨ��ȫִ������վ_ʡ��.png", "����_����.png", "����_������.png",
			"����_����.png", "����_������.png", "�ɱ���Ϣ��־�յ���.png", "��������豸.png", "�������豸.png", "Υ��ȡ֤�豸.png", "�źſ���ϵͳ.png", "����·�ڿ���_������.png", "����·�ڿ���_����.png",
			"����·�ڿ���_ͣ��.png", "������·����_ͣ��.png", "������·����_����.png", "������·����_������.png", "��·�շ�վ��_������.png", "��·�շ�վ��_ͣ��.png", "��·�շ�վ��_����.png", "ʡ�ʿ���_ͣ��.png",
			"ʡ�ʿ���_����.png", "ʡ�ʿ���_������.png", "�мʿ���_ͣ��.png", "�мʿ���_����.png", "�мʿ���_������.png", "�ؼʿ���_������.png", "�ؼʿ���_����.png", "�ؼʿ���_ͣ��.png",
			"��·���߿���_ͣ��.png", "��·���߿���_����.png", "��·���߿���_������.png", "������.png", "��ͨӦ����Դ.png", "ͣ����.png", "ά�޳�.png", "����.png", "ҽԺ.png" };

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
		 * ������Ϣ
		 */
		TOPIC_TFC_PASS("TOPIC_TFC_PASS"),

		/**
		 * ���ֳ�Υ����Ϣ
		 */
		TOPIC_VIO_SURVEIL("TOPIC_VIO_SURVEIL"),

		/**
		 * ���������Ϣ
		 */
		TOPIC_TRAFFIC_FLOW("TOPIC_TRAFFIC_FLOW"),

		/**
		 * ��������Ϣ
		 */
		TOPIC_ROAD_WEATHER("TOPIC_ROAD_WEATHER"),

		/**
		 * ��ͨ�¼������Ϣ
		 */
		TOPIC_TRAFFIC_INCIDENT("TOPIC_TRAFFIC_INCIDENT"),

		/**
		 * ��ͨ�յ�������Ϣ
		 */
		TOPIC_TRAFFIC_INDUCEMENT("TOPIC_TRAFFIC_INDUCEMENT"),

		/**
		 * ͣ��������ͣ����Ϣ
		 */
		TOPIC_VEHICLE_PARK("TOPIC_VEHICLE_PARK"),

		/**
		 * ������λ��Ϣ
		 */
		TOPIC_GPS_CAR("TOPIC_GPS_CAR"),

		/**
		 * ��Ա��λ��Ϣ
		 */
		TOPIC_GPS_POLICE("TOPIC_GPS_POLICE"),

		/**
		 * ������Ϣͳ����Ϣ
		 */
		TOPIC_STAT_PASS("TOPIC_STAT_PASS"),

		/**
		 * ����ʶ��ȶ���Ϣ
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
		 * ����ͼƬ
		 */
		TFC_PASS("1"),

		/**
		 * ͣ��ͼƬ
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
