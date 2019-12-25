package com.tmri.share.frm.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;


public class PicUtil {

	public static char[] SFT = new char[] { '京', '津', '冀', '晋', '蒙', '辽', '吉',
			'黑', '警', '沪', '苏', '浙', '皖', '闽', '赣', '鲁', '北', '学', '豫', '鄂',
			'湘', '粤', '桂', '琼', '南', '挂', '渝', '川', '贵', '云', '藏', '军', '空',
			'海', '使', '陕', '甘', '青', '宁', '新', '港', '澳', '台', '领' };

	public static final boolean isHphm(String hphm) {
		if (hphm == null || hphm.length() < 6)
			return false;
		char[] ch = hphm.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			for (int j = 0; j < SFT.length; j++) {
				if (SFT[j] == c)
					return true;
			}
		}
		return false;
	}

	public static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static final boolean isChinese(String strName) {
		if (strName == null || strName.length() <= 0)
			return false;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	public static String encodeChinese(String url)
			throws UnsupportedEncodingException {
		if (isChinese(url)) {
			url = URLEncoder.encode(url, "utf-8");
			url = url.replace("%3F", "?");
			url = url.replace("%3D", "=");
			url = url.replace("%26", "&");
			url = url.replace("%2520", "+");
			url = url.replace("%3A", ":");
		}
		return url;
	}

	public static boolean isTztp(String tpmc) {
		return tpmc.substring(0, 1).equals("0");
	}

	public static String getFwdz(HttpServletRequest request) {
		String Ip = NetUtil.getLocalHostIP();
		int port = request.getServerPort();
		String fwdz = Ip + ":" + port;
		return fwdz;
	}

	private static int m_randidx = 1;

	public static int getTransRandIdx(int nTransNum) {
		if (nTransNum <= 0)
			return 0;
		int nRandIdx = m_randidx;
		m_randidx++;
		if (m_randidx > nTransNum)
			m_randidx = 1;
		return nRandIdx;
	}

	public static String getTpmc(String tpxh, String kkbh, String fxlx,
			String cdh, String hphm, String gcsj) throws Exception {
		String tpmc;
		tpmc = tpxh;
		tpmc += kkbh.trim();
		tpmc += fxlx.trim();
		String _cdh = String.format("%02d", Long.parseLong(cdh));
		tpmc += _cdh.trim();
		if (hphm.length() > 0) {
			DesUtil des = new DesUtil("tmri921");//
			hphm = des.encrypt(hphm);
			tpmc += "_" + hphm.trim();
		}
		if (gcsj.length() > 0) {
			gcsj = gcsj.replace(" ", "");
			gcsj = gcsj.replace("-", "");
			gcsj = gcsj.replace(":", "");
			gcsj = gcsj.trim().substring(0, 14);
			tpmc += "_" + gcsj;
		}
		tpmc += ".jpg";
		return tpmc;
	}

	public static String getCplx(String tplj, String jrwz, String tpwz) {
		// 0-磁盘；1-Ftp服务器；2-图片接入服务器；3-图片服务前置机（FTP）；4-图片服务前置机(磁盘)；5-图片服务前置机（图片服务器）6-图片服务后置机(Ftp)；
		String cplx = null;
		if (jrwz.length() == 0)
			jrwz = "1";
		if (tplj.startsWith("ftp")) {
			if ("1".equals(jrwz))
				cplx = "1";
			else if ("2".equals(jrwz) && ("1".equals(tpwz) || "2".equals(tpwz)))
				cplx = "3";
		} else if (tplj.startsWith("http")) {
			if ("1".equals(jrwz))
				cplx = "2";
			else if ("2".equals(jrwz) && ("1".equals(tpwz) || "2".equals(tpwz)))
				cplx = "5";
		} else {
			if ("1".equals(tpwz))
				cplx = "0";
			else if ("2".equals(jrwz) && ("1".equals(tpwz) || "2".equals(tpwz)))
				cplx = "4";
		}
		return cplx;
	}

	public static boolean saveFile(String cplx, String path, String tpmc,
			InputStream is) throws IOException {
		if ("0".equals(cplx) || "4".equals(cplx))
			return saveFileToDisk(cplx, path, tpmc, is);
		else if ("1".equals(cplx) || "3".equals(cplx))
			return saveFileToFtp(cplx, path, tpmc, is);
		else if ("2".equals(cplx) || "5".equals(cplx))
			return saveFileToHttp(cplx, path, tpmc, is);
		return false;
	}

	public static String getLinuxDirect(String tpmc) {
		String[] tpmc_strs = tpmc.split("\\.");
		String path = "";
		if (tpmc_strs.length > 0) {
			String tpmcpre = tpmc_strs[0];
			if (!(tpmcpre.startsWith("0") || tpmcpre.startsWith("1"))) {
				tpmcpre = "1" + tpmcpre.substring(1);
			}
			path = "/" + tpmc.substring(0, 1);

			String[] strarr = tpmc.split("_");
			if (strarr.length > 0) {
				String strDate = strarr[strarr.length - 1];
				String[] strarr2 = strDate.split("\\.");
				if (strarr2.length > 0)
					strDate = strarr2[0];
				if (strDate.length() > 10)
					strDate = strDate.substring(0, 10);
				path += "/" + strDate;
			}

			if (tpmcpre.length() > 13) {
				String kkbh = tpmcpre.substring(1, 13);
				path += "/" + kkbh + "/";
			}
		}
		return path;
	}

	public static String getShareDirect(String tpmc) {
		String[] tpmc_strs = tpmc.split("\\.");
		String path = "";
		if (tpmc_strs.length > 0) {
			String tpmcpre = tpmc_strs[0];
			if (!(tpmcpre.startsWith("0") || tpmcpre.startsWith("1"))) {
				tpmcpre = "1" + tpmcpre.substring(1);
			}
			path = "\\" + tpmc.substring(0, 1);

			String[] strarr = tpmc.split("_");
			if (strarr.length > 0) {
				String strDate = strarr[strarr.length - 1];
				String[] strarr2 = strDate.split("\\.");
				if (strarr2.length > 0)
					strDate = strarr2[0];
				if (strDate.length() > 10)
					strDate = strDate.substring(0, 10);
				path += "\\" + strDate;
			}

			if (tpmcpre.length() > 13) {
				String kkbh = tpmcpre.substring(1, 13);
				path += "\\" + kkbh + "\\";
			}
		}
		return path;
	}

	public static void mksharedirs(String localfile) {
		int index = localfile.indexOf("\\");
		String path = "";
		while (index >= 0) {
			path += localfile.substring(0, index + 1);
			if (path.length() > 10) {
				File file = new File(path);
				File f = new File(file.getAbsolutePath());
				f.mkdir();
				// if (f.mkdir())
				// System.out.println(file.getAbsolutePath() +
				// " create success!");
				// else
				// System.out.println(file.getAbsolutePath() + " create fail!");
				// if (!file.isDirectory()) {
				// file.mkdirs();
				// }
			}
			localfile = localfile.substring(index + 1);
			index = localfile.indexOf("\\");
		}
	}

	public static void mklinuxdirs(String localfile) {
		int index = localfile.indexOf("/");
		String path = "";
		while (index >= 0) {
			if (path.length() <= 0)
				path = localfile.substring(0, index);
			else
				path += "/" + localfile.substring(0, index);
			File file = new File(path);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			localfile = localfile.substring(index + 1);
			index = localfile.indexOf("/");
		}
	}

	private static void deldir(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File subfile = files[i];
					deldir(subfile);
				}
			}
			file.delete();
		}
	}

	public static void deldirs(String path) {
		File file = new File(path);
		deldir(file);
	}

	public static void mkdirs(String localfile) {
		int index = localfile.indexOf("\\");
		String path = "";
		while (index >= 0) {
			if (path.length() <= 0)
				path = localfile.substring(0, index);
			else
				path += "\\" + localfile.substring(0, index);
			File file = new File(path);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			localfile = localfile.substring(index + 1);
			index = localfile.indexOf("\\");
		}
	}

	public static String getDirect(String path, String tpmc) {

		if (path.startsWith("\\\\")) { // 共享目录
			return getShareDirect(tpmc);
		} else if (path.startsWith("/")) { // linux系统磁盘目录
			return getLinuxDirect(tpmc);
		} else {
			String newpath = "";
			String[] tpmc_strs = tpmc.split("\\.");
			if (tpmc_strs.length > 0) {
				String tpmcpre = tpmc_strs[0];
				if (!(tpmcpre.startsWith("0") || tpmcpre.startsWith("1"))) {
					tpmcpre = "1" + tpmcpre.substring(1);
				}
				newpath = "\\" + tpmc.substring(0, 1);

				String[] strarr = tpmc.split("_");
				if (strarr.length > 0) {
					String strDate = strarr[strarr.length - 1];
					String[] strarr2 = strDate.split("\\.");
					if (strarr2.length > 0)
						strDate = strarr2[0];
					if (strDate.length() > 10)
						strDate = strDate.substring(0, 10);
					newpath += "\\" + strDate;
				}

				if (tpmcpre.length() > 13) {
					String kkbh = tpmcpre.substring(1, 13);
					newpath += "\\" + kkbh + "\\";
				}
			}
			return newpath;
		}
	}

	public static boolean saveFileToDisk(String cplx, String path, String tpmc,
			InputStream is) {
		if (path.startsWith("\\\\")) // 共享目录
		{
			String newpath = path;
			if ("0".equals(cplx)) {
				String direct = getShareDirect(tpmc);
				newpath += direct.trim();
			} else {
				String direct = getShareDirect(tpmc);
				newpath += direct.trim();
			}

			PicUtil.mksharedirs(newpath);

			DataOutputStream os;
			try {
				if (isChinese(tpmc))
					tpmc = URLEncoder.encode(tpmc, "UTF-8");
				newpath += "/" + tpmc;

				os = new DataOutputStream(new FileOutputStream(newpath));

				if (os != null) {
					int nRead = 0;
					byte[] b = new byte[1024];
					// 读取网络文件,写入指定的文件中
					while ((nRead = is.read(b, 0, 1024)) > 0) {
						os.write(b, 0, nRead);
					}
					os.close();
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		} else if (path.startsWith("/")) // linux系统磁盘目录
		{
			String newpath = path;
			if ("0".equals(cplx)) {
				String direct = getLinuxDirect(tpmc);
				newpath += direct.trim();
				mklinuxdirs(newpath);
			} else {
				String direct = getLinuxDirect(tpmc);
				newpath += direct.trim();
				mklinuxdirs(newpath);
			}

			DataOutputStream os;
			try {
				if (isChinese(tpmc))
					tpmc = URLEncoder.encode(tpmc, "UTF-8");
				newpath += "/" + tpmc;

				os = new DataOutputStream(new FileOutputStream(newpath));

				if (os != null) {
					int nRead = 0;
					byte[] b = new byte[1024];
					// 读取网络文件,写入指定的文件中
					while ((nRead = is.read(b, 0, 1024)) > 0) {
						os.write(b, 0, nRead);
					}
					os.close();
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File can't open:" + newpath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		} else {
			String newpath = path.trim();
			if ("0".equals(cplx)) {
				String delpath = newpath + "0";
				deldirs(delpath);
				String direct = getDirect("",tpmc);
				newpath += direct.trim();
				mkdirs(newpath);
			} else {
				String delpath = newpath + "1";
				deldirs(delpath);
				String direct = getDirect("",tpmc);
				newpath += direct;
				mkdirs(newpath);
			}

			DataOutputStream os;
			try {
				if (isChinese(tpmc))
					tpmc = URLEncoder.encode(tpmc, "UTF-8");
				
				
				newpath += "\\" + tpmc;

				os = new DataOutputStream(new FileOutputStream(newpath));

				if (os != null) {
					int nRead = 0;
					byte[] b = new byte[1024];
					// 读取网络文件,写入指定的文件中
					while ((nRead = is.read(b, 0, 1024)) > 0) {
						os.write(b, 0, nRead);
					}
					os.close();
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("File can't open:" + newpath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public static boolean saveFileToFtp(String cplx, String path, String tpmc,
			InputStream is) throws IOException {
		if (is == null) {
			System.out.println("saveFileToFtp is null!");
			return false;
		}
		String newpath = path.trim();
		if ("1".equals(cplx) || "3".equals(cplx)) {
			String direct = getDirect(path,tpmc);
			direct = direct.replace("\\", "/");
			newpath += direct.trim();
		}
		if (isChinese(tpmc))
			tpmc = URLEncoder.encode(tpmc, "UTF-8");
		boolean isUpload = false;

		FTPClientEx ftpClient = new FTPClientEx(); // FTPClientEx.QueryFtpClient(newpath);
		if (ftpClient != null) {
			if (ftpClient.connectex(newpath)) {
				isUpload = ftpClient.storeFile(tpmc, is);
				System.out.println("保存前端图片于: " + newpath + "/" + tpmc);
			}
			ftpClient.disconnect();
		} else
			System.out.println("保存前端图片到: " + newpath + "/" + tpmc
					+ "时未成功登录服务器！");
		return isUpload;
	}

	public static boolean saveFileToHttp(String cplx, String path, String tpmc,
			InputStream bis) throws IOException {
		if (bis == null) {
			System.out.println("saveFileToHttp is null!");
			return false;
		}

		String urlstr = path;
		if (isChinese(tpmc)) {
			tpmc = URLEncoder.encode(tpmc, "UTF-8");
			tpmc = tpmc.replace("%3F", "?");
			tpmc = tpmc.replace("%3D", "=");
			tpmc = tpmc.replace("%26", "&");
			tpmc = tpmc.replace("%2520", "+");
			tpmc = tpmc.replace("%3A", ":");
		}

		urlstr = urlstr.replace("%tpmc", tpmc);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len;
		while ((len = bis.read()) > -1) {
			bos.write(len);
		}
		byte fileData[] = bos.toByteArray();

		URL url = new URL(urlstr);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestProperty("content-type", "text/html");
		huc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		huc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		huc.setDoOutput(true);
		huc.setDoInput(true);
		huc.setRequestMethod("POST");
		huc.setConnectTimeout(500000);
		OutputStream pw = huc.getOutputStream();
		pw.write(fileData);
		pw.flush();
		pw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				huc.getInputStream()));
		String content = "";
		String line = br.readLine();
		while (line != null) {
			content = content + line;
			line = br.readLine();
		}
		br.close();
		System.out.println("保存前端图片于: " + urlstr + ";文件大小为： " + fileData.length
				+ "; 返回结果为：" + content);
		if (path.indexOf("%tpmc") > 0 && content.indexOf("<code>1</code>") >= 0)
			return true;
		else if (path.indexOf("%tpmc") > 0)
			return false;
		else
			return true;
	}

	public static String getSavePicXml(String Ip, String code, String msg) {
		msg = FuncUtil.encodeUTF8(msg);
		StringBuffer buf = new StringBuffer();
		buf.append("<head>\n");
		buf.append("<code>" + code + "</code>\n");
		buf.append("<message>" + msg + "</message>\n");
		buf.append("</head>\n");
		return buf.toString();
	}

	public static BufferedInputStream findFile(String path, String tpmc)
			throws IOException {
		if (path.toLowerCase().startsWith("ftp"))
			return findFileFromFtp(path, tpmc);
		else if (path.toLowerCase().startsWith("http")) {
			return null;
		} else if (path.indexOf(":/") > 0 || path.indexOf(":\\") > 0 || path.startsWith("\\\\") || path.startsWith("/")) { // windows系统路径、共享路径、linux系统路径
			return findFileFromDisk(path, tpmc);
		}
		return null;
	}

	public static BufferedInputStream findFileFromFtp(String path, String tpmc)
			throws IOException {
		if (path.length() > 0) {
			String direct = getDirect(path,tpmc);
			direct = direct.replace("\\", "/");
			String newpath = path.trim();
			newpath += direct.trim();

			if (isChinese(tpmc)) {
				tpmc = URLEncoder.encode(tpmc, "UTF-8");
			}
			String url = ComposeUrl(newpath, tpmc);

			System.out.println(url);

			String retfile = null;
			try {
				PicDownLoader downloader = new PicDownLoader("");
				retfile = downloader.DownloadToTempFile(url);
				if (retfile != null && retfile.length() > 0) {
					File file = new File(retfile);
					if (file.isFile() && file.exists()) {
						return new BufferedInputStream(
								new FileInputStream(file));
					}
				}
			} finally {
				TempFileContainer.getInstance().ReleaseFile(retfile);
			}
		}
		return null;
	}

	public static BufferedInputStream findFileFromDisk(String path, String tpmc) {
		if (path.length() > 0) {
			String direct = getDirect(path,tpmc);
			String fullpath = path.trim() + direct.trim();
			if (fullpath.endsWith("\\"))
				fullpath += tpmc.trim();
			else
				fullpath += "\\" + tpmc.trim();
			if (new File(fullpath).exists()) {
				try {
					return new BufferedInputStream(
							new FileInputStream(fullpath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(fullpath + " not exist!");
			}
		}
		return null;
	}

	// 根据他图片路径和图片名称生成完整路径
	public static String ComposeUrl(String tplj, String tpmc) {
		if (tplj == null || tplj.length() <= 0)
			return tpmc;
		else if (tpmc == null || tpmc.length() <= 0)
			return tplj;
		else {
			if (tplj.endsWith("/") || tpmc.startsWith("/")
					|| tplj.endsWith("="))
				return tplj + tpmc;
			else
				return tplj + "/" + tpmc;
		}
	}
	
	public static void main(String[] args) {
		
	}
}
