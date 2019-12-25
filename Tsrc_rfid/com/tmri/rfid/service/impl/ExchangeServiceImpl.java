package com.tmri.rfid.service.impl;

import java.io.*;
import java.util.*;

import javax.annotation.Resource;

import com.tmri.rfid.common.DataExchangeType;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tmri.rfid.bean.DataExchangeMapping;
import com.tmri.rfid.bean.MyDataEx;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.ExchangeService;
import com.tmri.rfid.service.GAService;
import com.tmri.rfid.service.ZWService;

/**
 * 公安网通用数据交换获取与存储
 * @author stone
 * @date 2016-3-23 下午1:57:16
 */
@Service
public class ExchangeServiceImpl extends BaseServiceImpl implements ExchangeService {

    private final static Logger LOG = LoggerFactory.getLogger(ExchangeServiceImpl.class);

	@Resource
    private GAService GAServiceImpl;
	@Resource
    private ZWService ZWServiceImpl;

	@Resource(name = "runtimeProperty")
	private RuntimeProperty runtimeProperty;

	private FTPClient ftpClient;

	public ExchangeServiceImpl() {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
	}

	public void saveDataAsFile(String sj, String sjlx) throws Exception{
		LOG.info(String.format("------> calling /saveDataAsFile sjlx = %s, sj = %s", sjlx, sj));
		String outPath = runtimeProperty.getDataExchangeFolder() + "/out/";
		FileUtil.createFolder(outPath);
		String filePathAndName = outPath + EriUtil.generateUniqueSerialNo() + "_" + sjlx;
		FileUtils.writeStringToFile(new File(filePathAndName), sj, "UTF-8");
	}

	private void saveDataByFTP(String sj, String sjlx) throws Exception {
		try {
			LOG.info("Attempting to connect to server...");

			// Connect to server
			ftpClient.setDefaultTimeout(20000);
			ftpClient.setConnectTimeout(20000);
			ftpClient.setDataTimeout(20000);
			ftpClient.connect(runtimeProperty.getDataExchangeFTPAddress());
			ftpClient.login(runtimeProperty.getDataExchangeFTPUsername(), runtimeProperty.getDataExchangeFTPPassword());
			ftpClient.changeWorkingDirectory("/out/");

			// Check for failed connection
			if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
			{
				ftpClient.disconnect();
				throw new FTPConnectionClosedException("Unable to connect to FTP server.");
			}

			String fileName = EriUtil.generateUniqueSerialNo() + "_" + sjlx;
			ByteArrayInputStream bais = new ByteArrayInputStream(sj.getBytes("UTF-8"));
			ftpClient.enterLocalPassiveMode();
			boolean result = ftpClient.storeFile("/out/" + fileName, bais);
			LOG.info("save remote file " + fileName + " " + (result ? "success" : "fail"));
		} catch(Exception e) {
			LOG.error("sava data by ftp fail", e);
		} finally {
			if(ftpClient.isConnected())
				ftpClient.disconnect();
		}
	}

	public List<MyDataEx> getFiles() throws IOException{
		LOG.info("------> calling /getFiles");
		return getFiles("");
	}

	public List<MyDataEx> getFilesByFTP(final String sjlx) throws Exception {
		try {
			this.ftpClient.connect(runtimeProperty.getDataExchangeFTPAddress());
			this.ftpClient.login(runtimeProperty.getDataExchangeFTPUsername(), runtimeProperty.getDataExchangeFTPPassword());

			this.ftpClient.setBufferSize(1024 * 1024);

			this.ftpClient.setAutodetectUTF8(true);
			this.ftpClient.enterLocalPassiveMode();

			ftpClient.changeWorkingDirectory("/in/");
			FTPFile[] dataList;
			//获取所有文件
			if (!StringUtils.isEmpty(sjlx)) {
				dataList = ftpClient.listFiles("/in/", new FTPFileFilter() {
					@Override
					public boolean accept(FTPFile ftpFile) {
						return ftpFile.isFile() && ftpFile.getName().toUpperCase().indexOf(sjlx.toUpperCase()) > 0;
					}
				});
			} else {
				dataList = ftpClient.listFiles("/in/");
			}
			if (dataList == null || dataList.length <= 0) return null;
			Collections.sort(Arrays.asList(dataList), new Comparator<FTPFile>() {
				public int compare(FTPFile f1, FTPFile f2) {
					if (f1.isDirectory() && f2.isFile())
						return -1;
					if (f1.isFile() && f2.isDirectory())
						return 1;
					return f2.getName().compareTo(f1.getName());
				}

				public boolean equals(Object obj) {
					return true;
				}
			});

			List<MyDataEx> myDataExs = new ArrayList<MyDataEx>();
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			for (FTPFile ftpFile : dataList) {
				LOG.info(String.format("------> calling ExchangeService.getFilesByFTP file = %s", ftpFile.getName()));
				// Check if FTPFile is a regular file
				if (ftpFile != null && ftpFile.getType() == FTPFile.FILE_TYPE) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
						ftpClient.retrieveFile(ftpFile.getName(), baos);
						String fileName = ftpFile.getName();
						String tableName = fileName.substring(16);
						MyDataEx myDataEx = new MyDataEx();
						myDataEx.setBh(fileName);

						baos.flush();
						String sj = new String(baos.toByteArray(), "UTF-8");
						LOG.info("数据内容:" + sj);
						JSONObject jsonObject = JSONObject.fromObject(sj);
						myDataEx.setSj(jsonObject.getString("sj"));
						myDataEx.setSjlx(tableName);
						myDataExs.add(myDataEx);
						boolean deleteResult = ftpClient.deleteFile(fileName);
						LOG.info("文件" + fileName + (deleteResult ? "已删除" : "未删除"));
					} catch (Exception e) {
						LOG.error("处理远程文件" + ftpFile.getName() + "失败, 內容：" + baos.toString(), e);
						ftpClient.deleteFile(ftpFile.getName());
					} finally {
						if (baos != null) {
							baos.close();
						}
					}
				}
			}
			return myDataExs;
		} finally {
			if(ftpClient.isConnected())
				ftpClient.disconnect();
		}
	}

	public List<MyDataEx> getFiles(final String sjlx) throws IOException{
		List<MyDataEx> myDataExs = new ArrayList<MyDataEx>();
		//获取所有文件
		File[] dataList;
		if (!StringUtils.isEmpty(sjlx)) {
			dataList = new File(runtimeProperty.getDataExchangeFolder() + "/in/").listFiles(
				new FileFilter() {
					@Override
					public boolean accept(File file) {
						return file.getName().toUpperCase().indexOf(sjlx.toUpperCase()) > 0;
					}
				});
		} else {
			dataList = new File(runtimeProperty.getDataExchangeFolder() + "/in/").listFiles();
		}
		if (dataList == null || dataList.length <= 0) return null;
		Collections.sort(Arrays.asList(dataList), new Comparator<File>() {
			public int compare(File f1, File f2) {
				if (f1.isDirectory() && f2.isFile())
					return -1;
				if (f1.isFile() && f2.isDirectory())
					return 1;
				return f2.getName().compareTo(f1.getName());
			}

			public boolean equals(Object obj) {
				return true;
			}
		});
		for(File file : dataList){
			LOG.info(String.format("------> calling /getFiles file = %s", file.getName()));
			try {
				if (file.getName().indexOf(".") >= 0) {
					file.delete();
				} else {
					String tableName = file.getName().substring(16);
					String sj = FileUtils.readFileToString(file, "UTF-8");
					MyDataEx myDataEx = new MyDataEx();
					myDataEx.setBh(file.getName());
					JSONObject jsonObject = JSONObject.fromObject(sj);
					myDataEx.setSj(jsonObject.getString("sj"));
					myDataEx.setSjlx(tableName);
					myDataExs.add(myDataEx);
				}
			} catch (Exception e) {
				LOG.error("文件读取失败，文件名：" + file.getName() + "===原因：" + e.getMessage());
				try {
					if (file.exists() && !file.delete()) {
						LOG.error("文件删除失败，文件名：" + file.getName());
					}
				} catch (Exception ex) {
					LOG.error("二次删除文件失败", ex);
				}
			}
		}
		return myDataExs;
	}
	
	/**
	 * 保存数据到数据交换表
	 * @param sj 交换数据（约定为json字符串）
	 * @param sjlx 数据类型（约定表名）
	 * @param clfs 处理方式（1-insert 2-update 3-delete）
	 * @param type 内外网类型（1-专网 2-公安网）
	 */
	public void saveData(String sj, String sjlx, String clfs, int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == 1){
			ZWServiceImpl.saveData(sj, sjlx, clfs);
		}else if(type==2){
			GAServiceImpl.saveData(sj, sjlx, clfs);
		}else{
			throw new Exception("System type is null!");
		}
		
	}
	
	/**
	 * 保存数据到数据交换表
	 * @param sj 交换数据（约定为json字符串）
	 * @param sjlx 数据类型（约定表名）
	 * @param type 内外网类型（1-专网 2-公安网）
	 */
	public void saveData(String sj, String sjlx, int type) throws Exception {
		// TODO Auto-generated method stub
		saveData(sj, sjlx, "0", type);
	}
	
	/**
	 * 保存数据到数据交换表
	 * @param object 交换对
	 * @param type 内外网类型（1-专网 2-公安网）
	 */
	public void saveData(Object object, int type) throws Exception {
		String className = object.getClass().getName();
		String jsonStr = GsonHelper.getGson().toJson(MapUtilities.buildMap("sj", GsonHelper.getGson().toJson(object), "className", className));
		DataExchangeMapping dataExchangeMapping = MyConstantUtils.getByClassName(className);
		String sjlx = dataExchangeMapping.getTableName();
		if (runtimeProperty.getDataExchangeType() == DataExchangeType.DB) {
			saveData(jsonStr, sjlx, type);
		} else if (runtimeProperty.getDataExchangeType() == DataExchangeType.FILE) {
			saveDataAsFile(jsonStr, sjlx);
		} else if (runtimeProperty.getDataExchangeType() == DataExchangeType.FTP) {
			saveDataByFTP(jsonStr, sjlx);
		}
	}
	
	/**
	 * 保存数据到数据交换表
	 * @param object 交换对
	 */
	@Override
	public void saveData(Object object) throws Exception {
		int type = MyConstantUtils.obj.environmentType;
		saveData(object, type);
	}

	@Override
	public List<MyDataEx> fetchData() throws Exception {
		return fetchData("");
	}

	@Override
	public List<MyDataEx> fetchData(String sjlx) throws Exception {
		if (runtimeProperty.getDataExchangeType() == DataExchangeType.DB) {
			return getData(sjlx);
		} else if (runtimeProperty.getDataExchangeType() == DataExchangeType.FILE) {
			return getFiles(sjlx);
		} else if (runtimeProperty.getDataExchangeType() == DataExchangeType.FTP) {
			return getFilesByFTP(sjlx);
		} else {
			return null;
		}
	}


	/**
	 * 获取已经数据交换还未同步到业务表的数据
	 * @param sjlx 数据类型（约定表名） 可为空
	 * @param type 内外网类型（1-专网 2-公安网）
	 */
	public List<MyDataEx> getData(String sjlx, int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == 1){
			return ZWServiceImpl.getData(sjlx);
		}else if(type==2){
			return GAServiceImpl.getData(sjlx);
		}else{
			throw new Exception("System type is null!");
		}
	}

	/**
	 * 获取已经数据交换还未同步到业务表的数据(带表名，主键信息)
	 * @param sjlx 数据类型（约定表名） 可为空
	 * @param type 内外网类型（1-专网 2-公安网）
	 */
	public List<MyDataEx> getDataWithTableInfo(String sjlx, int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == 1){
			return ZWServiceImpl.getDataWithTableInfo(sjlx);
		}else if(type==2){
			return GAServiceImpl.getDataWithTableInfo(sjlx);
		}else{
			throw new Exception("System type is null!");
		}
	}
	
	/**
	 * 更新已经同步到业务表的状态
	 * @param bhs 已同步到业务表的编号ID集合
	 * @param type 内外网类型（1-专网 2-公安网）
	 */
	public void updateFlag(String bhs, int type) throws Exception {
		// TODO Auto-generated method stub
		LOG.info(String.format("------> bhs = %s ,type = %s", bhs, type));
		if(type == 1){
			ZWServiceImpl.updateFlag(bhs,"6");
		}else if(type==2){
			GAServiceImpl.updateFlag(bhs,"1");
		}else{
			throw new Exception("System type is null!");
		}
	}
	
	/**
	 * 获取已经数据交换还未同步到业务表的数据(带表名，主键信息)
	 * @param sjlx 数据类型（约定表名） 可为空
	 */
	public List<MyDataEx> getDataWithTableInfo(String sjlx) throws Exception {
		// TODO Auto-generated method stub
		int type = MyConstantUtils.obj.environmentType;
		return getDataWithTableInfo(sjlx, type);
	}
	
	/**
	 * 更新已经同步到业务表的状态
	 * @param bhs 已同步到业务表的编号ID集合
	 */
	@Override
	public void updateFlag(final String bhs) throws Exception {
		if (runtimeProperty.getDataExchangeType() == DataExchangeType.DB) {
			// TODO Auto-generated method stub
			int type = MyConstantUtils.obj.environmentType;
			updateFlag(bhs, type);
		} else if (runtimeProperty.getDataExchangeType() == DataExchangeType.FILE) {
			for (String fileName : bhs.split(",")) {
				File file = new File(runtimeProperty.getDataExchangeFolder() + "/in/" + fileName);
				if (file.exists()) {
					try {
						if (!file.delete()) {
							LOG.error("delete file " + file.getName() + " fail");
						}
					} catch (Exception e) {
						LOG.error("delete file " + file.getName() + " error", e);
					}
				}
			}
		} else if (runtimeProperty.getDataExchangeType() == DataExchangeType.FTP) {
			String[] files = bhs.split(",");
			if (files.length > 0) {
				try {
					ftpClient.connect(runtimeProperty.getDataExchangeFTPAddress());
					ftpClient.login(runtimeProperty.getDataExchangeFTPUsername(), runtimeProperty.getDataExchangeFTPPassword());

					for (String fileName : files) {
						ftpClient.deleteFile("/in/" + fileName);
					}
				} finally {
					if(ftpClient.isConnected())
						ftpClient.disconnect();
				}
			}
		}
	}
	
	/**
	 * 获取已经数据交换还未同步到业务表的数据
	 * @param sjlx 数据类型（约定表名） 可为空
	 */
	public List<MyDataEx> getData(String sjlx) throws Exception {
		// TODO Auto-generated method stub
		int type = MyConstantUtils.obj.environmentType;
		return getData(sjlx, type);
	}


	public static void main(String args[]) {
		List<MyDataEx> myDataExs = new ArrayList<MyDataEx>();
		//获取所有文件
		File[] dataList = new File("D:\\ftp-test\\in").listFiles();
		if (dataList == null || dataList.length <= 0) return;
		Collections.sort(Arrays.asList(dataList), new Comparator<File>() {
			public int compare(File f1, File f2) {
				if (f1.isDirectory() && f2.isFile())
					return -1;
				if (f1.isFile() && f2.isDirectory())
					return 1;
				return f2.getName().compareTo(f1.getName());
			}

			public boolean equals(Object obj) {
				return true;
			}
		});
		for(File file : dataList){
			LOG.info(String.format("------> calling /getFiles file = %s", file.getName()));
			try {
				if (file.getName().indexOf(".") >= 0) {
					file.delete();
				} else {
					String tableName = file.getName().substring(16);
					String sj = FileUtils.readFileToString(file, "UTF-8");
					MyDataEx myDataEx = new MyDataEx();
					myDataEx.setBh(file.getName());
					JSONObject jsonObject = JSONObject.fromObject(sj);
					myDataEx.setSj(jsonObject.getString("sj"));
					myDataEx.setSjlx(tableName);
					myDataExs.add(myDataEx);
				}
			} catch (Exception e) {
				LOG.error("文件读取失败，文件名：" + file.getName() + "===原因：" + e.getMessage());
				if (file.exists() && !file.delete()) {
					LOG.error("文件删除失败，文件名：" + file.getName());
				}
			}
		}
		System.out.println("done");
	}
	
}
