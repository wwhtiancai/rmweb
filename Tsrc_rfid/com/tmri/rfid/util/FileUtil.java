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
 * @date 2016-3-31 ����11:14:50
 */
public class FileUtil {
	private static String message;
	private FTPClient ftpClient;
    private final static Logger LOG = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * ��ȡ�ı��ļ�����
	 * 
	 * @param filePathAndName
	 *            ������������·�����ļ���
	 * @param encoding
	 *            �ı��ļ��򿪵ı��뷽ʽ
	 * @return �����ı��ļ�������
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
	 * �½�Ŀ¼
	 * 
	 * @param folderPath
	 *            Ŀ¼
	 * @return ����Ŀ¼�������·��
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
			message = "����Ŀ¼��������";
			LOG.error(message+",folderPath:"+folderPath);
		}
		return txt;
	}

	/**
	 * �༶Ŀ¼����
	 * 
	 * @param folderPath
	 *            ׼��Ҫ�ڱ���Ŀ¼�´�����Ŀ¼��Ŀ¼·�� ���� c:myf
	 * @param paths
	 *            ���޼�Ŀ¼����������Ŀ¼�Ե��������� ���� a|b|c
	 * @return ���ش����ļ����·�� ���� c:myfac
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
			message = "����Ŀ¼��������";
			LOG.error(message+",folderPath:"+folderPath+",paths:"+paths);
		}
		return txts;
	}

	/**
	 * �½��ļ�
	 * 
	 * @param filePathAndName
	 *            �ı��ļ���������·�����ļ���
	 * @param fileContent
	 *            �ı��ļ�����
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
			message = "�����ļ���������";
			LOG.error(message+",filePathAndName:"+filePathAndName);
		}
	}

	/**
	 * �б��뷽ʽ���ļ�����
	 * 
	 * @param filePathAndName
	 *            �ı��ļ���������·�����ļ���
	 * @param fileContent
	 *            �ı��ļ�����
	 * @param encoding
	 *            ���뷽ʽ ���� GBK ���� UTF-8
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
			message = "�����ļ���������";
			LOG.error(message+",filePathAndName:"+filePathAndName);
		}
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param filePathAndName
	 *            �ı��ļ���������·�����ļ���
	 * @return Boolean �ɹ�ɾ������true�����쳣����false
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
				message = (filePathAndName + "ɾ���ļ���������");
			}
		} catch (Exception e) {
			message = e.toString();
			LOG.error(message+",filePathAndName:"+filePathAndName);
		}
		return bea;
	}

	/**
	 * ɾ���ļ���
	 * 
	 * @param folderPath
	 *            �ļ�����������·��
	 * @return
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			message = ("ɾ���ļ��в�������");
			LOG.error(message+",folderPath:"+folderPath);
		}
	}

	/**
	 * ɾ��ָ���ļ����������ļ�
	 * 
	 * @param path
	 *            �ļ�����������·��
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
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * ���Ƶ����ļ�
	 * 
	 * @param oldPathFile
	 *            ׼�����Ƶ��ļ�Դ
	 * @param newPathFile
	 *            �������¾���·�����ļ���
	 * @return
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // �ļ�����ʱ
				InputStream inStream = new FileInputStream(oldPathFile); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			message = ("���Ƶ����ļ���������");
			LOG.error(message+",oldPathFile:"+oldPathFile+",newPathFile:"+newPathFile);
		}
	}

	/**
	 * ���������ļ��е�����
	 * 
	 * @param oldPath
	 *            ׼��������Ŀ¼
	 * @param newPath
	 *            ָ������·������Ŀ¼
	 * @return
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // ����ļ��в����� �������ļ���
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
				if (temp.isDirectory()) {// ��������ļ���
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			message = "���������ļ������ݲ�������";
			LOG.error(message+",oldPath:"+oldPath+",newPath:"+newPath);
		}
	}

	/**
	 * �ƶ��ļ�
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
	 * �ƶ�Ŀ¼
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
	 * �õ�������Ϣ
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
		   // ����
		   ftpClient.connect(ip, port);
		   // ��¼
		   ftpClient.login(userName, userPwd);
		   if (path != null && path.length() > 0) {
		    // ��ת��ָ��Ŀ¼
		    ftpClient.changeWorkingDirectory(path);
		   }
		  } catch (SocketException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
	 /**
	  * �ر�����
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
	  * ��ȡ�ļ���
	  * @param path
	  * @return
	  */
	 public List<String> getFileList(String path) {
		  List<String> fileLists = new ArrayList<String>();
		  // ���ָ��Ŀ¼�������ļ���
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
	  * ��ȡ�ļ�����
	  * @param path
	  * @return
	  */
	 public List<String> getDirList(String path) {
		  List<String> fileLists = new ArrayList<String>();
		  // ���ָ��Ŀ¼�������ļ���
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
		   // �ӷ������϶�ȡָ�����ļ�
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
		   // ��������һ��getReply()�ѽ�������226���ѵ�. �������ǿ��Խ���������null����
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
