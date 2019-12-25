import com.tmri.framework.dao.SysDao;
import com.tmri.rfid.bean.MyDataEx;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleInfo;
import com.tmri.rfid.util.EriUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Test {

	private final static Logger LOG = LoggerFactory.getLogger(Test.class);

	private FTPClient ftpClient;

	public Test() {
		ftpClient = new FTPClient();
	}

	public void saveDataByFTP(String sj, String sjlx) throws Exception {
		try {
			LOG.info("Attempting to connect to server...");

			// Connect to server
			ftpClient.setConnectTimeout(20000);
			ftpClient.connect("10.2.40.13");
			ftpClient.login("zhangxd", "5idong");
			ftpClient.changeWorkingDirectory("/out/");

			// Check for failed connection
			if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
			{
				ftpClient.disconnect();
				throw new FTPConnectionClosedException("Unable to connect to FTP server.");
			}

			String fileName = EriUtil.appendZero(System.currentTimeMillis() + "", 15) + "_" + sjlx;
			ByteArrayInputStream bais = new ByteArrayInputStream(sj.getBytes());
			boolean result = ftpClient.storeFile("/out/" + fileName, bais);
			LOG.info("save remote file " + fileName + " " + (result ? "success" : "fail"));
		} catch(Exception e) {
			LOG.error("sava data by ftp fail", e);
		} finally {
			if(ftpClient.isConnected())
				ftpClient.disconnect();
		}
	}

	public List<MyDataEx> getFilesByFTP(final String sjlx) throws Exception {
		LOG.info(String.format("------> calling /getFilesByFTP sjlx = %s", sjlx));
		try {
			this.ftpClient.connect("10.2.40.13");
			this.ftpClient.login("zhangxd", "5idong");

			LOG.debug("Buffer Size:" + ftpClient.getBufferSize());
			this.ftpClient.setBufferSize(1024 * 1024);
			LOG.debug("Buffer Size:" + ftpClient.getBufferSize());

			this.ftpClient.enterLocalPassiveMode();
			this.ftpClient.setAutodetectUTF8(true);
			//this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
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
			for (FTPFile ftpFile : dataList) {

				// Check if FTPFile is a regular file
				if (ftpFile != null && ftpFile.getType() == FTPFile.FILE_TYPE) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
						String fileName = ftpFile.getName();
						String tableName = fileName.substring(16);
						MyDataEx myDataEx = new MyDataEx();
						myDataEx.setBh(fileName);

						//I don't know what useful are these methods in this step
						// I just put it for try
						this.ftpClient.enterLocalPassiveMode();
						this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
						this.ftpClient.setAutodetectUTF8(true);
						this.ftpClient.enterLocalPassiveMode();

						ftpClient.retrieveFile(ftpFile.getName(), baos);
						baos.flush();
						String sj = baos.toString();
						LOG.info("数据内容:" + sj);
						JSONObject jsonObject = JSONObject.fromObject(sj);
						myDataEx.setSj(jsonObject.getString("sj"));
						myDataEx.setSjlx(tableName);
						myDataExs.add(myDataEx);
						boolean deleteResult = ftpClient.deleteFile(fileName);
						LOG.info("文件" + fileName + (deleteResult ? "已删除" : "未删除"));
					} catch (Exception e) {
						LOG.error("处理远程文件" + ftpFile.getName() + "失败", e);
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
	
	/*public static void main(String[] args) throws Exception{
		int count = 0;
		long lastTime = System.currentTimeMillis();
		for (int i = 0 ; i < 1000; i++) {
			long now = System.currentTimeMillis();
			if (now > lastTime) {
				lastTime = now;
				count = 0;
			}
			String fileName = now + EriUtil.appendZero(count++ + "", 2);
			System.out.println(fileName);
		}
	}*/

/*	public static void main(String args[]) throws InvocationTargetException, IllegalAccessException {
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setHphm("321321321");
        vehicleInfo.setJdcxh("bbbb");
        Vehicle vehicle = new Vehicle();
        vehicle.setXh("aaaaa");
        BeanUtils.copyProperties(vehicle, vehicleInfo);
        System.out.println(vehicle.getHphm() + "," + vehicle.getXh() + "," + vehicle.getJdcxh());
    }*/

/*    public static void main(String args[]) {
        String str = "0200BFFCC8C61BD2FB447408FC6E2EE9823FAA5EAE47C9EB6E47FEE07BD95260F90BC0259F0096F3ACAD22B3009E8F58240957C397FACAA22DBC563312F6D6FB4CEE3AB71EE1498C3496CBBC4956804D0E3BD4A2FFEF1110D6E1B7AF60D93E147249C3FCD36202ACA56B737CE7DCA397DC00E88100011D50B2F1EBF8F2660A25D2684C565CD94B219770F8112D523A3FA16C4A60622C4F7D1423F8112D523A3FA16C4A60622C4F7D14235A0130040A10286E8A8B3DECE9AE34552B6C3F3E849A492D74001F72789CF54ACE07D360AC8013A70559CF6F03D53866B6C13C237D7EF4A0B1B6FF5AF27E1D1FBBEAC30E9111000AC029C965CB192A7413EDBC03FAF7661ADC9C";
        String result = "";
        for (int i = 0; i < str.length(); ) {
            result = result + " " + str.substring(i, i += 2);
        }
        System.out.println(result);
    }*/

    public static void main(String args[]) throws Exception{
        /*byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream("C:\\Users\\Joey\\Pictures\\MT4模拟.png");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data));*/

        System.out.println(EriUtil.parseCert("30820206308201ABA003020102020900CB307CD9FABCA870300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303330313136353231395A170D3336303330313136353231395A3059310B300906035504060C02434E310B300906035504080C024744310B300906035504070C02535A310D300B060355040A0C04544D5249310D300B060355040B0C04544D52493112301006035504030C093343415246494443413059301306072A8648CE3D020106082A811CCF5501822D034200043929F054D111AFE9C87262982690E202A3007DCCA57997BD191718652C1092A22E450CFA63529F9D0D7217CB0EA8B78597D4F3E066A084A9B7BB4790F09E55D0A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000347003044022058C87F14268BE35D652C3ED0BEEAA9B78A46D35DC45816B31F075D349E900C81022071968A588275BFCE854377310E8145A997C4069A3493AC2C0CAB0AC0A1D00308").getPubKey());
    }
	
}
