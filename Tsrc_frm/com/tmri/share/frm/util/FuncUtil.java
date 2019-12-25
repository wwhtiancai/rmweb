package com.tmri.share.frm.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.tmri.share.ora.bean.DbResult;

public class FuncUtil {
	public final static String OS = System.getProperty("os.name");
	public final static String DIR_SEPARATOR = System.getProperty("file.separator");

	private static HashMap<String, String> PRI_HPHM = new HashMap<String, String>();

	private static HashMap<String, String> HPHM_FZJG = new HashMap<String, String>();
	static {
		HPHM_FZJG.put("��M", "��A");
		HPHM_FZJG.put("��H", "��C");
		HPHM_FZJG.put("��L", "��A");
		HPHM_FZJG.put("³U", "³B");
		HPHM_FZJG.put("³V", "³G");
		HPHM_FZJG.put("³Y", "³F");
		HPHM_FZJG.put("��K", "��A");
		HPHM_FZJG.put("��S", "��A");
		HPHM_FZJG.put("��F", "��D");
		HPHM_FZJG.put("��X", "��E");
		HPHM_FZJG.put("��Y", "��E");
		HPHM_FZJG.put("��Z", "��O");
	}

	static {
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("ԥ", "ԥ");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("³", "³");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
		PRI_HPHM.put("��", "��");
	}

	public static String getFzjg(String hphm) {
		String fzjg = "";
		if (hphm.length() < 2) {
			fzjg = hphm;
		} else {
			String hpt = hphm.substring(0, 2);
			if (hpt.indexOf("��") >= 0 || hpt.indexOf("��") >= 0 || hpt.indexOf("��") >= 0 || hpt.indexOf("��") >= 0) {
				fzjg = hpt.substring(0, 1) + "A";
			} else {
				fzjg = (String) HPHM_FZJG.get(hpt);
				if (fzjg == null) {
					fzjg = hpt;
				}
			}
		}
		return fzjg;
	}

	public static void checkGaHPHM(String hpzl, String hphm) throws Exception {
		String result = "0";
		if (!StringUtil.checkBN(hpzl) || !StringUtil.checkBN(hphm) || hphm.length() < 5 || hpzl.length() != 2) {
			throw new Exception("���Ʋ���ȷ��");
		}

		String curPreHphm = hphm.substring(0, 1);
		if (!PRI_HPHM.containsKey(curPreHphm)) {
			DebugLog.debug("��ǰ����ǰ׺Ϊ��" + curPreHphm);
			throw new Exception("�ǹ������գ�");
		}
	}
	
	public static void checkGaHpt(String hphm) throws Exception {
		String curPreHphm = hphm.substring(0, 1);
		if (!PRI_HPHM.containsKey(curPreHphm)) {
			DebugLog.debug("��ǰ����ǰ׺Ϊ��" + curPreHphm);
			throw new Exception("�ǹ������գ�");
		}
	}
	

	public static String checkGaHPHM_x(String hpzl, String hphm) throws Exception {
		if (!StringUtil.checkBN(hpzl) || !StringUtil.checkBN(hphm) || hphm.length() < 5 || hpzl.length() != 2) {
			return "0";
		}
		String curPreHphm = hphm.substring(0, 1);
		if (!PRI_HPHM.containsKey(curPreHphm)) {
			return "0";
		}
		return "1";
	}

	public static String encodeUTF8(String xmlDoc) {
		String str = "";
		try {
			str = URLEncoder.encode(xmlDoc, "utf-8");
			return str;
		} catch (Exception ex) {
			str = ex.toString();
		}
		return str;
	}

	public static String decodeUTF8(String str) throws Exception {
		String xmlDoc = "";
		try {
			xmlDoc = URLDecoder.decode(str, "utf-8");
		} catch (Exception ex) {
			xmlDoc = ex.toString();
		}
		return xmlDoc;
	}

	// �����ǰʱ��
	public static void outDatetime(String sign, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);// �������ڸ�ʽ
		System.out.println(sign + df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}

	public static String getReturnInfo(DbResult obj) {
		String returnVal = "";
		returnVal = returnVal + "<html>\n";
		returnVal = returnVal + "<head><title>Servlet1</title></head>\n";
		returnVal = returnVal + "<body bgcolor=\"#ffffff\">\n";
		returnVal = returnVal + "<script language=\"javascript\">\n";
		returnVal = returnVal + " try{ ";
		returnVal = returnVal + "var message1='" + StringUtil.transScriptStr(obj.getMsg1()) + "';\n";
		returnVal = returnVal + "var message='" + StringUtil.transScriptStr(obj.getMsg()) + "';\n";
		// parent.resultsubmit
		// returnVal=returnVal+"parent.resultsubmit('"+obj.getCode()+"','"+obj.getMsg()+"',message1"+");\n";
		returnVal = returnVal + "parent.resultsubmit('" + obj.getCode() + "',message,message1" + ");\n";
		returnVal = returnVal + "     }catch(e){ ";
		returnVal = returnVal + "     alert('������Ϣ'+e.description); ";
		returnVal = returnVal + " } ";
		returnVal = returnVal + "</script>\n";
		returnVal = returnVal + "</body></html>";
		return returnVal;
	}

	// ��ȡĳ����
	public static void hashmap2cstmt(HashMap hm, String strProperties, CallableStatement cstmt, int index) {

		try {
			if (hm.get(strProperties) == null) {
				cstmt.setNull(index, Types.VARCHAR);
			} else {
				cstmt.setString(index, (String) hm.get(strProperties));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String formatDate(String rq, int length) {
		String result = rq;
		if (rq != null && rq.length() >= 10) {
			result = rq.substring(0, length);
		} else {
			result = "";
		}
		return result;
	}

	public static String addQuote2Glbm(String glbmlist) {
		String result = "";
		String[] glbm = glbmlist.split(",");
		for (int i = 0; i < glbm.length; i++) {
			result += "'" + glbm[i] + "',";
		}
		if (!result.equals("")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String addComma(String dwxz) {
		String result = "";
		for (int i = 0; i < dwxz.length(); i++) {
			if (dwxz.charAt(i) != 'Q') {
				result = result + String.valueOf(dwxz.charAt(i)) + ",";
			}
		}
		if (result.substring(result.length() - 1, result.length()).equals(",")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String ReplaceString(String original, String find, String replace) {

		if (original == null) {
			original = "";
		}
		String returnStr = "";
		if (original.indexOf(find) < 0) {
			returnStr = original;
		}
		try {
			while (original.indexOf(find) >= 0) {
				int indexbegin = original.indexOf(find);
				String leftstring = original.substring(0, indexbegin);
				original = original.substring(indexbegin + find.length());
				if (original.indexOf(find) <= 0) {
					returnStr += leftstring + replace + original;
				} else {
					returnStr += leftstring + replace;
				}
			}
			return (returnStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return original;
		}
	}

	/**
	 * �ַ����е�˫���źͻس������Ա���javascript����ʾ
	 * 
	 * @param strJava String
	 * @return String
	 */
	public static String cScriptInfoStr(String strJava) {
		String r = null;
		if (strJava == null) {
			r = "";
		} else {
			r = ReplaceString(strJava, "'", "\\\'");
			r = ReplaceString(r, "\"", "\\\"");
			// \r �س� \n ����
			r = ReplaceString(r, "\r", "\\r");
			r = ReplaceString(r, "\n", "\\n");
		}
		return r;
	}

	/**
	 * ��Blob����ת��Ϊbyte��������
	 * 
	 * @param blob
	 * @return
	 */
	public static byte[] blob2Bytes(Blob blob) {
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;

			while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return bytes;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				return null;
			}
		}
	}

	/**
	 * �ж��Ƿ�Ϊֱ�����ش���
	 * 
	 * @param bmdm
	 * @return
	 */
	public static boolean checkZsjg(String glbm) {
		boolean result = false;
		if (Integer.parseInt(glbm.substring(8, 10)) < 50 && !glbm.substring(8, 10).equals("00")) {
			result = true;
		}
		return result;
	}

	/**
	 * ���ݹ����Ŵ���ȡ֧�ӹ�������
	 * 
	 */
	public static String getZdglbm(String glbm) {
		String result = "";
		if (glbm != null) {
			if (glbm.length() == 12) {
				if (glbm.substring(2, 4).equals("90")) {
					result = glbm.substring(0, 6) + "000000";
				} else {
					result = glbm.substring(0, 4) + "00000000";
				}
			}
		}
		return result;
	}

	public static String getGlbmhead(String glbm) {
		String result = "";
		if (glbm != null) {
			if (glbm.length() == 12) {
				if (glbm.substring(2, 4).equals("90")) {
					result = glbm.substring(0, 6) ;
				} else {
					result = glbm.substring(0, 4) ;
				}
			}
		}
		return result;
	}
	
	
	public static int str2int(String val) {
		int result = 0;
		result = Integer.valueOf(val).intValue();
		return result;
	}

	// ȡ�����ŵ�like ���
	public static String getGlbmlike(String glbm, String bmjb, String bhxj) {
		String result = glbm;
		if (bhxj.equals("1")) {
			// ���ų�������
			if (glbm.equals("010000000000")) {
				result = "%";
				return result;
			}
			// �ж������λ��ȷ���Ƿ��ǿ���
			String tail0910 = glbm.substring(8, 10);
			String tail1112 = glbm.substring(10, 12);

			// �ǿ���
			String tail = "";
			if (!tail0910.equals("00")) {
				// ����
				if (tail1112.equals("00")) {
					result = glbm.substring(0, 8) + tail0910 + "__";
				} else {
					result = glbm;
				}
			} else {
				// ����
				if (!tail1112.equals("00")) {
					tail = tail1112;
				} else {
					tail = "__";
				}
				if (bmjb.equals("2")) {
					result = glbm.substring(0, 2) + "________" + tail;
				} else if (bmjb.equals("3")) {
					if (glbm.substring(2, 4).equals("90")) {
						result = glbm.substring(0, 6) + "____" + tail;
					} else {
						result = glbm.substring(0, 4) + "______" + tail;
					}
				} else if (bmjb.equals("4")) {
					result = glbm.substring(0, 8) + "__" + tail;
				}
			}

		}

		return result;
	}

	// ��ȡhashtable��Ӧkeyֵ
	public static Long getHtLongval(Hashtable ht, String key) {
		Long result = (Long) ht.get(key);
		if (result == null) {
			result = Long.valueOf(0);
		}
		return result;
	}

	// ��ȡlist��
	public static int getListsize(List templist) {
		int result = 0;
		if (templist != null) {
			result = templist.size();
		}
		return result;
	}

	// ���ݵ�ǰϵͳ�滻�ָ���
	public static String separate(String path) {
		if (FuncUtil.OS.toLowerCase().contains("win")) {
			return (path.trim()).replace("\\\\", "\\");
		} else {
			return (path.trim()).replace("//", "/");
		}
	}

	public static String transToScriptInfo(String strJava) {
		String r = null;
		if (strJava == null) {
			r = "";
		} else {
			r = strJava.replaceAll("'", "\"");
			r = r.replaceAll("\"", "\\\"");
			// \r �س� \n ����
			r = r.replaceAll("\r", "");
			r = r.replaceAll("\n", "A~A~");
		}
		return r;
	}

	public static String transException(Exception e) {
		String r = e.getMessage();
		if (r == null) {
			r = "java.lang.NullPointerException.Default";
		} else {
			if (e.getCause() != null) {
				r += "," + e.getCause().getMessage();
			}
			r = StringUtil.replaceStr(r, "'", "\\\'");
			r = StringUtil.replaceStr(r, "\"", "\\\"");
			r = StringUtil.replaceStr(r, "\r", "\\r");
			r = StringUtil.replaceStr(r, "\n", "\\n");
		}
		if (r.length() > 1000) {
			r = r.substring(0, 1000);
		}
		e.printStackTrace();
		return r;
	}

	// ��ȡĳ����
	public static void bean2cstmt(Object object, String strProperties, CallableStatement cstmt, int index) {
		try {
			if (BeanUtils.getProperty(object, strProperties) == null) {
				cstmt.setNull(index, Types.VARCHAR);
			} else {
				cstmt.setString(index, BeanUtils.getProperty(object, strProperties));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String removeFzjh(String fzjhdz, String ip) {
		String iparr[] = ip.split(",");
		if (fzjhdz == null) {
			return iparr[0];
		}
		String fzjhdzarr[] = fzjhdz.split(",");

		for (int i = 0; i < iparr.length; i++) {
			boolean flag = true;
			for (int j = 0; j < fzjhdzarr.length; j++) {
				if (fzjhdzarr[j].equals(iparr[i])) {
					flag = false;
				}
			}
			if (flag == true) {
				return iparr[i];
			}
		}
		return null;
	}

	// ��ȡԶ�̿ͻ��˵�ַ
	public static String getRemoteIpdz(HttpServletRequest request) {
		// 20120104�������Ϻ�F5���⣬ֻ���Ϻ���request��ȡIP��ַ
		// String djglbmString=this.getSysParaValue("00","DJGLBM");
		String ipString = "";
		ipString = request.getHeader("X-Forwarded-For");
		if (ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}
		return ipString;
	}

	// zhoujn 20140512 ���Ӻ���������
	// ����
	public static String encrypt(String data) {
		return DESCoder.encrypt(data);
	}

	// �滻���з�
	public static String encryptTrim(String data) {
		String result = DESCoder.encrypt(data);
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(result);
		result = m.replaceAll("");
		return result;
	}

	// ����
	public static String decrypt(String data) {
		return DESCoder.decrypt(data);
	}

	public static String getAlarmSjlxKey(String sjly, String sjlx, String sjzlx) {
		if (!StringUtil.checkBN(sjly)) {
			sjly = "";
		}
		if (!StringUtil.checkBN(sjlx)) {
			sjlx = "";
		}
		if (!StringUtil.checkBN(sjzlx)) {
			sjzlx = "";
		}

		return sjly + "_" + sjlx + "_" + sjzlx;
	}

	public static void main(String[] args) {
		try {
			String test = "ԥJ65629";
			System.out.println("����ǰ���ַ���" + test);
			System.out.println("���ܺ���ַ���" + FuncUtil.encryptTrim(test));
			System.out.println("���ܺ���ַ���" + FuncUtil.encryptTrim(test));
			System.out.println("���ܺ���ַ���" + FuncUtil.encrypt(test));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}