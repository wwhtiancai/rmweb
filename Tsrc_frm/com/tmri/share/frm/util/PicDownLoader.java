package com.tmri.share.frm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class PicDownLoader extends Thread {

	protected boolean bQuit = false;

	public PicDownLoader(String defpath) {
		if (defpath != null && defpath.length() > 0)
			_defpath = defpath;
	}

	private static String _defpath = "D:\\Image";

	private static String _ftpprefix;

	private static String getPrefix() {
		if (_ftpprefix == null || _ftpprefix.length() <= 0) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				String ip = addr.getHostAddress();// 获得本机IP
				_ftpprefix = "ftp://" + ip;
				System.out.println("PicDownLoader prefix: " + _ftpprefix);
			} catch (Exception e) {
				e.printStackTrace();
				java.lang.System.out.println(" Error getHostAddress "
						+ e.getMessage());
			}
		}
		return _ftpprefix;
	}

	public void Quit() {
		this.bQuit = true;
		System.out.println("PicDownLoader Quit!");
	}

	public void KeepRun() {
		if (this.isInterrupted() || !this.isAlive())
			this.start();
	}

	public void ftpUpload(String host, String user, String pass,
			String rmotefile, String localfile) throws IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			// url = "ftp://jcbk:jcbk@10.2.44.52/bike.jpg";
			ftpClient.connect(host);
			ftpClient.setSoTimeout(5000);
			// ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
			if (ftpClient.login(user, pass)) {
				try {
					FileInputStream is = new FileInputStream(localfile);
					ftpClient.storeFile(rmotefile, is);
					is.close();
					is = null;

					if (!ftpClient.completePendingCommand()) {
						if (ftpClient.isConnected()) {
							ftpClient.logout();
							ftpClient.disconnect();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					java.lang.System.out.println(rmotefile
							+ " Error storeFile " + e.getMessage());
				} finally {
					ftpClient.logout();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
			ftpClient = null;
		}
	}

	private String _errcode;

	public String getErrcode() {
		return _errcode;
	}

	public String getErrorCode() {
		if (_errcode == null || _errcode.length() <= 0)
			return "1";
		else
			return _errcode;
	}

	private long _downsize = 0;

	public long getDownSize() {
		return _downsize;
	}

	public String ftpDownloadToTempFile(String url) throws IOException {
		if (url.length() <= 0) {
			_errcode = "-701";
			return null;
		}
		this._downsize = 0;
		FTPClientEx ftpClient = new FTPClientEx();

		ftpClient.setControlEncoding("GBK");
		String lockfile = null;
		String retfile = null;
		InputStream is = null;
		URL aURL = null;
		try {
			aURL = new URL(url);
			String host = aURL.getHost();

			while ((lockfile = TempFileContainer.getInstance().QueryFile(host)) == null) {
				sleep(50);
			}

			try {
				File file = new File(lockfile);
				// 路径为文件且不为空则进行删除
				try {
					if (file.isFile() && file.exists()) {
						file.deleteOnExit();
						// if (!file.delete())
						// _errcode = PicEr_tmp_lock;
					}
				} catch (Exception e) {
					// _errcode = PicEr_tmp_lock;
				}

				if (!ftpClient.connectex(url)) {
					_errcode = ftpClient.getErcode();
					return retfile;
				}

				String rmtfile = aURL.getFile();

				rmtfile = new String(rmtfile.getBytes(System.getProperty("file.encoding")), "GBK");

				FTPFile[] files = ftpClient.listFiles(rmtfile);
				if (files.length > 0)
					is = ftpClient.retrieveFileStream(rmtfile);
				else {
					String filename = getFtpFilename(rmtfile);
					files = ftpClient.listFiles(filename);
					if (files.length > 0)
						is = ftpClient.retrieveFileStream(filename);
					else {
						String newfilename = URLDecoder.decode(filename,
								"utf-8");
						files = ftpClient.listFiles(newfilename);
						if (files.length > 0)
							is = ftpClient.retrieveFileStream(files[0]
									.getName());
						else {
							newfilename = URLDecoder.decode(filename, "GBK");
							files = ftpClient.listFiles(newfilename);
							if (files.length > 0) {
								is = ftpClient.retrieveFileStream(files[0]
										.getName());
							} else {
								_errcode = "-711";
								System.out.println("Error down:" + url);
							}
						}
					}
				}

				if (is != null) {
					FileOutputStream os = new FileOutputStream(lockfile);
					int iRead = 0;
					if (os != null) {
						try {
							byte[] byteArr = new byte[1024];
							// 读取的字节数
							int readCount = is.read(byteArr);
							// 如果已到达文件末尾，则返回-1
							while (readCount != -1) {
								for (int i = 0; i < readCount; i++) {
									os.write(byteArr[i]);
								}
								iRead += readCount;
								readCount = is.read(byteArr);
							}
							retfile = lockfile;
						} catch (Exception e) {
							e.printStackTrace();
							java.lang.System.out.println("Error os.write(b)"
									+ e.getMessage());
							_errcode = "-712";
						} finally {
							if (os != null) {
								os.close();
								os = null;
							}
						}
					}
					this._downsize = iRead;
					is.close();
					is = null;

					if (!ftpClient.completePendingCommand()) {
						if (ftpClient.isConnected()) {
							ftpClient.logout();
							ftpClient.disconnect();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				ftpClient.logout();
				java.lang.System.out.println(url + " Error retrieveFileStream "
						+ e.getMessage());
				_errcode = "-713";
			} finally {
				aURL = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TempFileContainer.getInstance().ReleaseFile(lockfile);
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
			ftpClient = null;
			if (is != null) {
				is.close();
				is = null;
			}
			aURL = null;
		}
		return retfile;
	}

	public String httpDownloadToTempFile(String url) throws IOException {
		if (url.length() <= 0) {
			_errcode = "-701";
			return null;
		}
		_downsize = 0;

		url = url.replace(" ", "%20");
		url = url.replace("\\", "/");
		url = UrlSplitEncode(url);

		String lockfile = null;
		String retfile = null;
		URL aURL = null;
		int nStartPos = 0;
		int nRead = 0;
		try {
			aURL = new URL(url);
			String host = aURL.getHost();

			while ((lockfile = TempFileContainer.getInstance().QueryFile(host)) == null) {
				sleep(50);
			}

			File file = new File(lockfile);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.deleteOnExit();
				// if (!file.delete())
				// _errcode = PicEr_tmp_lock;
			}

			HttpURLConnection httpConnection = null;
			try {
				httpConnection = (HttpURLConnection) aURL.openConnection();
				httpConnection.setConnectTimeout(20000);  
				httpConnection.setReadTimeout(20000);
			} catch (Exception e) {
				e.printStackTrace();
				_errcode = "-721";
				retfile = null;
			}
			if (httpConnection != null) {
				// 获得文件长度
				long nEndPos = getFileSize(url);
				System.out.println(url);
				// String cnRmtfile = aURL.getFile(); // 中文远程文件名
				// String newrmtfile = cnRmtfile.replace('/', '\\');
				// mkdirs(retfile);
				RandomAccessFile oSavedFile = new RandomAccessFile(lockfile,
						"rw");
				httpConnection.setRequestProperty("User-Agent",
						"Internet Explorer");
				String sProperty = "bytes=" + nStartPos + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				InputStream input = null;
				try {
					input = httpConnection.getInputStream();
				} catch (Exception e) {
					e.printStackTrace();
					_errcode = "-722";
					retfile = null;
				}
				if (input != null) {
					byte[] b = new byte[1024];
					// 读取网络文件,写入指定的文件中
					while ((nRead = input.read(b, 0, 1024)) > 0
							&& ((nStartPos < nEndPos) || (nEndPos <= 0))) {
						oSavedFile.write(b, 0, nRead);
						nStartPos += nRead;
					}
					this._downsize = nStartPos;
					System.out.println(sProperty);
					retfile = lockfile;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			_errcode = "-729";
			retfile = null;
		} finally {
			TempFileContainer.getInstance().ReleaseFile(lockfile);
			aURL = null;
		}
		return retfile;
	}

	public String DownloadToTempFile(String url) throws IOException {
		if (url.length() <= 0)
			return null;
		String retfile = null;
		if (url.toLowerCase().startsWith("ftp"))
			retfile = ftpDownloadToTempFile(url);
		else if (url.toLowerCase().startsWith("http")) {
			retfile = httpDownloadToTempFile(url);
		} else if (url.indexOf(":\\") > 0) // windows系统路径
		{
			retfile = url.replace("/", "\\");
		} else if (url.charAt(0) == '/') // linux系统路径
		{
			retfile = url;
		}
		return retfile;
	}

	protected static void mkdirs(String localfile) {
		if (localfile == null)
			return;
		int index = localfile.lastIndexOf("\\");
		String path = localfile.substring(0, index);
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
	}

	protected static String getFtpFilename(String fullpath) {
		if (fullpath == null)
			return null;
		int index = fullpath.lastIndexOf("/");
		String filename = fullpath.substring(index + 1);
		return filename;
	}

	public static String ftpDownloadToFile(String url) throws IOException {
		if (url.length() <= 0)
			return null;
		FTPClient ftpClient = new FTPClient();
		String lockfile = null;
		String retfile = null;
		InputStream is = null;
		URL aURL = null;
		try {
			java.lang.System.out.println(url);
			aURL = new URL(url);
			String cnRmtfile = aURL.getFile(); // 中文远程文件名
			String host = aURL.getHost();
			String user = null;
			String pass = null;
			try {
				String strUserInfo = aURL.getUserInfo();
				if (strUserInfo != null) {
					String[] userInfo = strUserInfo.split(":");
					if (userInfo.length > 0)
						user = userInfo[0];
					if (userInfo.length > 1)
						pass = userInfo[1];
				}
			} catch (Exception e) {
				java.lang.System.out.println(" Error aURL.getUserInfo().split "
						+ e.getMessage());
			}
			;
			if (user == null || user.length() <= 0) {
				user = "Anonymous";
				pass = "";
			}

			int nQueryNum = 0;
			while ((lockfile = TempFileContainer.getInstance().QueryFile(host)) == null) {
				sleep(200);
				nQueryNum++;
				if (nQueryNum > 500) {
					TempFileContainer.getInstance().Release();
					nQueryNum = 0;
				}
			}

			ftpClient.connect(host);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setSoTimeout(5000);
			// ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			if (ftpClient.login(user, pass)) {
				try {
					// "/2009年12月03日/其他图片目录/机号001车道A12009年12月03日18时10分32秒RX75D1H1驶向北至南卡口抓拍时速46公里.JPG";
					FTPFile[] files = ftpClient.listFiles(new String(cnRmtfile
							.getBytes("GBK"), "ISO-8859-1"));
					if (files.length > 0)
						is = ftpClient.retrieveFileStream(new String(cnRmtfile
								.getBytes("GBK"), "ISO-8859-1"));
					else
						java.lang.System.out.println(cnRmtfile + " is empty");
					if (is != null) {
						String newrmtfile = cnRmtfile.replace('/', '\\');
						String localfile = _defpath + newrmtfile;
						java.lang.System.out.println(localfile);
						mkdirs(localfile);

						FileOutputStream os = new FileOutputStream(localfile);
						int iRead = 0;
						if (os != null) {
							try {
								byte[] byteArr = new byte[1024];
								// 读取的字节数
								int readCount = is.read(byteArr);
								// 如果已到达文件末尾，则返回-1
								while (readCount != -1) {
									for (int i = 0; i < readCount; i++) {
										os.write(byteArr[i]);
									}
									iRead += readCount;
									readCount = is.read(byteArr);
								}
								retfile = cnRmtfile;
								// retfile = getPrefix() + cnRmtfile;
							} catch (Exception e) {
								e.printStackTrace();
								java.lang.System.out
										.println("Error os.write(b)"
												+ e.getMessage());
							} finally {
								if (os != null) {
									os.close();
									os = null;
								}
							}
						}
						is.close();
						is = null;

						if (iRead == 0) {
							File file = new File(retfile);
							if (file.isFile() && file.exists()) {
								file.delete();
							}
						}

						if (!ftpClient.completePendingCommand()) {
							if (ftpClient.isConnected()) {
								ftpClient.logout();
								ftpClient.disconnect();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					ftpClient.logout();
					retfile = null;
					java.lang.System.out.println(url
							+ " Error retrieveFileStream " + e.getMessage());
				} finally {
					aURL = null;
				}
			} else {
				String msg = "Ftp login fail,user:" + user + "pass:" + pass;
				java.lang.System.out.println(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retfile = null;
		} finally {
			TempFileContainer.getInstance().ReleaseFile(lockfile);
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
			ftpClient = null;
			if (is != null) {
				is.close();
				is = null;
			}
			aURL = null;
		}
		return retfile;
	}

	// 获得文件长度
	public static long getFileSize(String sURL) {
		int nFileLength = -1;
		try {
			URL url = new URL(sURL);
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setConnectTimeout(20000);  
			httpConnection.setReadTimeout(20000);
			httpConnection
					.setRequestProperty("User-Agent", "Internet Explorer");

			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				System.err.println("Error Code : " + responseCode);
				return -2; // -2 represent access is error
			}
			String sHeader;
			for (int i = 1;; i++) {
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Integer.parseInt(httpConnection
								.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Content-Length:" + nFileLength);
		return nFileLength;
	}

	public String httpDownloadToFile(String url) throws IOException {
		if (url.length() <= 0)
			return null;
		url = url.replace(" ", "%20");
		url = url.replace("\\", "/");
		url = UrlSplitEncode(url);
		String lockfile = null;
		String retfile = null;
		URL aURL = null;
		int nStartPos = 0;
		int nRead = 0;
		try {
			aURL = new URL(url);
			String host = aURL.getHost();

			while ((lockfile = TempFileContainer.getInstance().QueryFile(host)) == null) {
				sleep(50);
			}

			HttpURLConnection httpConnection = (HttpURLConnection) aURL
					.openConnection();
			httpConnection.setConnectTimeout(20000);  
			httpConnection.setReadTimeout(20000);
			// 获得文件长度
			long nEndPos = getFileSize(url);
			String cnRmtfile = aURL.getFile(); // 中文远程文件名
			String newrmtfile = cnRmtfile.replace('/', '\\');
			String localfile = "D:\\Image" + newrmtfile;
			java.lang.System.out.println(localfile);
			mkdirs(localfile);
			RandomAccessFile oSavedFile = new RandomAccessFile(localfile, "rw");
			httpConnection
					.setRequestProperty("User-Agent", "Internet Explorer");
			String sProperty = "bytes=" + nStartPos + "-";
			// 告诉服务器book.rar这个文件从nStartPos字节开始传
			httpConnection.setRequestProperty("RANGE", sProperty);
			System.out.println(sProperty);
			InputStream input = httpConnection.getInputStream();
			byte[] b = new byte[1024];
			// 读取网络文件,写入指定的文件中
			while ((nRead = input.read(b, 0, 1024)) > 0
					&& ((nStartPos < nEndPos) || (nEndPos <= 0))) {
				oSavedFile.write(b, 0, nRead);
				nStartPos += nRead;
			}
			retfile = cnRmtfile;
			// retfile = getPrefix() + cnRmtfile;
		} catch (Exception e) {
			e.printStackTrace();
			retfile = null;
		} finally {
			TempFileContainer.getInstance().ReleaseFile(lockfile);
			aURL = null;
		}
		return retfile;
	}

	public String DownloadToFile(String url) throws IOException {
		String retfile = null;
		if (url.toLowerCase().startsWith("ftp"))
			retfile = ftpDownloadToFile(url);
		else if (url.toLowerCase().startsWith("http")) {
			retfile = httpDownloadToFile(url);
		}
		if (retfile != null)
			retfile = getPrefix() + retfile;
		return retfile;
	}

	public String DownloadToFtp(String url, String ftpput) throws IOException {
		String retfile = null;
		String localfile = null;
		if (url.startsWith("ftp"))
			localfile = ftpDownloadToFile(url);
		else if (url.startsWith("http"))
			localfile = httpDownloadToFile(url);
		if (localfile != null) {
			FTPClient putClient = new FTPClient();

			URL putURL = new URL(ftpput);
			String puthost = putURL.getHost();
			String putuser = null;
			String putpass = null;
			try {
				String strUserInfo = putURL.getUserInfo();
				if (strUserInfo != null) {
					String[] userInfo = strUserInfo.split(":");
					if (userInfo.length > 0)
						putuser = userInfo[0];
					if (userInfo.length > 1)
						putpass = userInfo[1];
				}
			} catch (Exception e) {
				java.lang.System.out
						.println(" Error putURL.getUserInfo().split "
								+ e.getMessage());
			}
			if (putuser == null || putuser.length() <= 0) {
				putuser = "Anonymous";
				putpass = "";
			}

			putClient.connect(puthost);
			putClient.setControlEncoding("GBK");
			putClient.setSoTimeout(5000);
			if (putClient.login(putuser, putpass)) {
				InputStream is = new FileInputStream(localfile);
				if (putClient.appendFile(ftpput, is)) {
					retfile = ftpput + localfile;
				}
			}
			if (!putClient.completePendingCommand()) {
				if (putClient.isConnected()) {
					putClient.logout();
					putClient.disconnect();
				}
			}
			putClient = null;
		}
		return retfile;
	}

	private String UrlSplitEncode(String url)
			throws UnsupportedEncodingException {
		String[] splitstrs = url.split("/");
		String newurl = "";
		for (int i = 0; i < splitstrs.length; i++) {
			String tmpStr = splitstrs[i];
			
			tmpStr = PicUtil.encodeChinese(tmpStr);

			if (newurl.length() <= 0)
				newurl = tmpStr;
			else
				newurl = newurl + "/" + tmpStr;
		}
		return newurl;
	}

	public static void main(String[] args) throws Exception{

//		PicDownLoader down = new PicDownLoader("");
//
//		String file = "1257-%E6%A4%B4%E4%B8%B7E0006-2544465-0.jpg";
//		String file2 = null;
//		try {
//			file = URLDecoder.decode(file, "utf-8");
//			file2 = URLDecoder.decode(file, "ISO-8859-1");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//
//		String url = "ftp://Administrator:8251@10.150.247.19:21/F/Image/2013101514/000000000005/"
//				+ file + file2;// 140015913桂KHZ133.jpg";
//
//		url = "http://10.10.13.99:9080/KK2/common/jsp/find_creat_realpic4jcbk.jsp?kkbh=110000100005&fxlx=1&cdh=1&hphm=%E4%BA%ACAF6805&txsj=2013-11-10 13:10:10&sbbm=ZK00036";
//
//		url = "http://10.58.1.182:8080/DJSPTWeb/ImageServlet?flag=WNOR&base64Flag=0&HPHM=%E8%B1%ABA521TN&CPSY=1&SBBH=KK70004A&JCSJ=20140117130447890";
//
//		url = "http://10.38.134.151:7012/E/2151689$1$0$3/20140127/13/1257-%E6%A4%B4%E4%B8%B7E0006-2544465-0.jpg";
//		
//		url = "ftp://ncjj1:123456@10.67.208.10/2014/02/12/511300034/R100342014021211305904.jpg";
//		
////		url = "ftp://10.104.137.204/tmritrans/tmripic1/0/2014022016/150600100004/0150600100004101_36a8c8ccab86c87e83d094f58ccacc5c_20140220160135.jpg";
//		
//		url = "ftp://kk:kk@10.103.175.252/省道105线1229公里300米上行/2014/03/06/15/192.168.1.17_20140306150351940_蒙JDP333_蓝.jpg";
//		
//		try {
//			down.DownloadToTempFile(url);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		PicDownLoader down = new PicDownLoader("");
		down.ftpDownloadToTempFile("ftp://vion:vion@10.183.140.146:9022/卡口/和政路西街南段/20141230/19/序号620102V506000947车道S1卡口行驶方向由南向北号牌种类02号牌号码甘AJK043采集方式1采集部门620102010000核对标记0备注.jpg");
	}
}
