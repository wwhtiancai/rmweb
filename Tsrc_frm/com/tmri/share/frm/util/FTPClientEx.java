package com.tmri.share.frm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPClientEx extends FTPClient {
	public FTPClientEx(String host, String user, String pass) {
		this.host = host;
		this.user = user;
		this.pass = pass;
	}

	public FTPClientEx() {
		this.setControlEncoding("gb2312");
	}

	public String host;
	public String user;
	public String pass;
	public int port;
	private boolean bRetrive = false;
	private boolean bSync = true;

	public void stopSync() {
		bSync = false;
	}

	public synchronized void LockRetrieve() {
		bRetrive = true;
	}

	public synchronized void UnLockRetrieve() {
		bRetrive = false;
	}

	public synchronized boolean checkRetrieve() {
		return !bRetrive;
	}
	
	public String directChineseRep(String direct)
			throws UnsupportedEncodingException {
		String directory = direct;
		int start = 0;
		int end = 0;
		if (directory.startsWith("/")) {
			start = 1;
		} else {
			start = 0;
		}
		end = directory.indexOf("/", start);
		while (true) {
			String subDirectory = directory.substring(start, end);
			if (PicUtil.isChinese(subDirectory)) {
				String newsubDirectory = URLEncoder.encode(subDirectory,
						"UTF-8");
				direct = direct.replace(subDirectory, newsubDirectory);
			}

			start = end + 1;
			end = directory.indexOf("/", start);

			// 检查所有目录是否创建完毕
			if (end <= start) {
				break;
			}
		}
		return direct;
	}

	public boolean CreateDirecroty(String remote) throws IOException {
		remote = remote + "/";
		while (remote.indexOf("//") > 0) {
			remote = remote.replace("//", "/");
		}

		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		String codeDirect = new String(directory.getBytes(System
				.getProperty("file.encoding")), "GBK"); // "iso-8859-1");  
		if (!directory.equalsIgnoreCase("/")
				&& !this.changeWorkingDirectory(codeDirect)) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				
				String subDirectory = new String(remote.substring(start, end).getBytes(System
						.getProperty("file.encoding")), "GBK"); // "iso-8859-1");
				if (PicUtil.isChinese(subDirectory))
					subDirectory = URLEncoder.encode(subDirectory, "UTF-8");

				if (!this.changeWorkingDirectory(subDirectory)) {
					if (this.makeDirectory(subDirectory)) {
						this.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						return false;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return true;
	}
	
	public static final String PicEr_ftp_ero3="11";   // 登录失败！
	public static final String PicEr_ftp_ero5="12";   // CreateDirecroty为false
	
	public static final String PicEr_ftp_ero1="101";   // 路径不是ftp开头
	public static final String PicEr_ftp_ero2="102";   // isPositiveCompletion为false
	public static final String PicEr_ftp_ero4="103";   // setFileType为false
	public static final String PicEr_ftp_ero6="104";   // disconnect异常
	
	public static final String PicEr_ftp_ero9="109";   // 其他异常
	
	private String _ercode;
	
	public String getErcode()
	{
		return _ercode;
	}
	
	public String connectWithEr(String url) {
		if (url.indexOf("//") >= 0) {
			while (url.indexOf("//") >= 0) {
				url = url.replace("//", "/");
			}
			if (url.startsWith("ftp:"))
			    url = url.replace("ftp:/", "ftp://");
			if (url.startsWith("FTP:"))
			    url = url.replace("FTP:/", "ftp://");
			if (url.startsWith("Ftp:"))
			    url = url.replace("Ftp:/", "ftp://");
		}
		if (!url.toLowerCase().startsWith("ftp"))
			return PicEr_ftp_ero1;
		
		if (url.toLowerCase().indexOf(".jpg") >= 0
				|| url.toLowerCase().indexOf(".bmp") > 0)
			url = url.substring(0, url.lastIndexOf("/"));
		boolean isConnectionSuccess = false;
		try {
			java.lang.System.out.println(url);
			URL aURL = new URL(url);
			String strUserInfo = aURL.getUserInfo();
			if (strUserInfo != null && strUserInfo.length() > 0) {
				String[] userInfo = aURL.getUserInfo().split(":");
				if (userInfo.length > 0){
					user = userInfo[0];
					if (user.indexOf("%")>=0){
						user = URLDecoder.decode(user,"utf-8");
					}
				}
				if (userInfo.length > 1)
				{
					pass = userInfo[1];
					if (pass.indexOf("%")>=0){
						pass = URLDecoder.decode(pass,"utf-8");
					}
				}
			}
			if (user == null || user.length() <= 0) {
				user = "Anonymous";
				pass = "";
			}
			
			host = aURL.getHost();
			int port = aURL.getPort();
			if (port == -1)
				port = 21;
			String workingDirectory = aURL.getPath();

			try {
				this.connect(host, port);
				int reply = this.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					return PicEr_ftp_ero2;
				}
				isConnectionSuccess = this.login(user, pass);
				if (!isConnectionSuccess) {
					return PicEr_ftp_ero3;
				}
				if (!this.setFileType(FTP.BINARY_FILE_TYPE)) {
					return PicEr_ftp_ero4;
				}
				
				boolean bRet = CreateDirecroty(workingDirectory);

				if (bRet)
					return "";
				else
				{
					System.out.println("CreateDirecroty"+workingDirectory+" error!");
					return PicEr_ftp_ero5;
				}
				/*
				 * workingDirectory = "/pic/0/00/"; if (workingDirectory !=
				 * null) { this.makeDirectory(workingDirectory); if
				 * (!this.changeWorkingDirectory(workingDirectory)) { return
				 * false; } }
				 */
				// this.enterLocalPassiveMode();
			} catch (IOException e) {
				if (this != null && this.isConnected())
					try {
						this.disconnect();
					} catch (IOException e1) {
						return PicEr_ftp_ero6;
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isConnectionSuccess)
			return "";
		else
			return PicEr_ftp_ero9;
	}
	
	public boolean connectex(String url) {
		_ercode = connectWithEr(url);
		if (_ercode!=null && _ercode.length()<=0)
			return true;
		else
			return false;
	}

	public void SyncLogin() throws SocketException, IOException,
			InterruptedException {
		while (!checkRetrieve() && bSync)
			Thread.sleep(40);
		if (checkRetrieve()) {
			LockRetrieve();
			try {
				this.connect(host);
				this.setSoTimeout(5000);
				this.login(user, pass);
				this.setFileType(FTP.BINARY_FILE_TYPE);
			} catch (IOException e) {
				throw e;
			} finally {
				UnLockRetrieve();
			}
		}
	}

	public void SyncLogout() throws SocketException, IOException,
			InterruptedException {
		while (!checkRetrieve() && bSync)
			Thread.sleep(40);
		if (checkRetrieve()) {
			LockRetrieve();
			try {
				this.logout();
				this.disconnect();
			} catch (IOException e) {
				throw e;
			} finally {
				UnLockRetrieve();
			}
		}
	}

	public void SyncRetrieveFile(String file, OutputStream is)
			throws IOException, InterruptedException {
		while (!checkRetrieve() && bSync)
			Thread.sleep(40);
		if (checkRetrieve()) {
			LockRetrieve();
			try {
				this.retrieveFile(file, is);
			} catch (IOException e) {
				System.out.println("retrieveFile Error!");
				throw e;
			} finally {
				UnLockRetrieve();
			}
		}
	}
}
