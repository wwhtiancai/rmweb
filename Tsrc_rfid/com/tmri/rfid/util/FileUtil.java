package com.tmri.rfid.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author stone
 * @date 2016-3-31 上午11:14:50
 */
public class FileUtil {
	private static String message;
	private FTPClient ftpClient;
    private final static Logger LOG = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + " ");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public static String createFolder(String folderPath) {
		LOG.info(String.format("------> calling /createFolder folderPath = %s", folderPath));
		
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			message = "创建目录操作出错";
			LOG.error(message+",folderPath:"+folderPath);
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfac
	 */
	public static String createFolders(String folderPath, String paths) {
		LOG.info(String.format("------> calling /createFolders folderPath = %s, paths = %s", folderPath, paths));
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			message = "创建目录操作出错！";
			LOG.error(message+",folderPath:"+folderPath+",paths:"+paths);
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
			LOG.error(message+",filePathAndName:"+filePathAndName);
		}
	}

	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return
	 */
	public static void createFile(String filePathAndName, String fileContent,
			String encoding) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
			LOG.error(message+",filePathAndName:"+filePathAndName);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName + "删除文件操作出错");
			}
		} catch (Exception e) {
			message = e.toString();
			LOG.error(message+",filePathAndName:"+filePathAndName);
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			message = ("删除文件夹操作出错");
			LOG.error(message+",folderPath:"+folderPath);
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			message = ("复制单个文件操作出错");
			LOG.error(message+",oldPathFile:"+oldPathFile+",newPathFile:"+newPathFile);
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			message = "复制整个文件夹内容操作出错";
			LOG.error(message+",oldPath:"+oldPath+",newPath:"+newPath);
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 得到错误信息
	 */
	public static String getMessage() {
		return message;
	}
	/**
	 * 
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 */
	 public void connectServer(String ip, int port, String userName, String userPwd, String path) {
		 ftpClient = new FTPClient();
		  try {
		   // 连接
		   ftpClient.connect(ip, port);
		   // 登录
		   ftpClient.login(userName, userPwd);
		   if (path != null && path.length() > 0) {
		    // 跳转到指定目录
		    ftpClient.changeWorkingDirectory(path);
		   }
		  } catch (SocketException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	 /**
	  * 关闭连接
	  */
	 public void closeServer() {
		  if (ftpClient.isConnected()) {
		   try {
		    ftpClient.logout();
		    ftpClient.disconnect();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		  }
		 }
	 /**
	  * 获取文件名
	  * @param path
	  * @return
	  */
	 public List<String> getFileList(String path) {
		  List<String> fileLists = new ArrayList<String>();
		  // 获得指定目录下所有文件名
		  FTPFile[] ftpFiles = null;
		  try {
		   ftpFiles = ftpClient.listFiles(path);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
		   FTPFile file = ftpFiles[i];
		   if (file.isFile()) {
		    fileLists.add(file.getName());
		   }
		  }
		  return fileLists;
		 }
	 /**
	  * 获取文件夹名
	  * @param path
	  * @return
	  */
	 public List<String> getDirList(String path) {
		  List<String> fileLists = new ArrayList<String>();
		  // 获得指定目录下所有文件名
		  FTPFile[] ftpFiles = null;
		  try {
		   ftpFiles = ftpClient.listDirectories(path);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
		   FTPFile file = ftpFiles[i];
		   if (file.isDirectory()) {
		    fileLists.add(file.getName());
		   }
		  }
		  return fileLists;
		 }
	 
	 public String readFile(String fileName) throws ParseException {
		  InputStream ins = null;
		  StringBuilder builder = null;
		  try {
		   // 从服务器上读取指定的文件
		   ins = ftpClient.retrieveFileStream(fileName);
		   BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
		   String line;
		   builder = new StringBuilder(150);
		   while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		    builder.append(line);
		   }
		   reader.close();
		   if (ins != null) {
		    ins.close();
		   }
		   // 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
		   ftpClient.getReply();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return builder.toString();
		 }
	 
	 public void deleteFile(String fileName) {
		  try {
		   ftpClient.deleteFile(fileName);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	 
	 public void ftpMoveFile(String oldPath,String newPath) {
		  try {
		   ftpClient.rename(oldPath, newPath);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	 
	 public void changeDir(String path) {
		  try {
		   ftpClient.changeWorkingDirectory(path);
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
}
