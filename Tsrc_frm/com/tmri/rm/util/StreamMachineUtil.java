package com.tmri.rm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.bean.RmStreamConsumerStatus;
import com.tmri.rm.bean.RmStreamMachine;
import com.tmri.rm.bean.RmStreamPartitionStatusBean;
import com.tmri.rm.bean.RmStreamStatusBean;
import com.tmri.rm.service.RmStreamStatusService.RmStreamStaus;
import com.tmri.share.frm.util.PermitUtil;
import com.tmri.share.frm.util.StringUtil;

@SuppressWarnings("deprecation")
public final class StreamMachineUtil {
	public static final String key = "#dcf%456ghFd3r5d";

	private StreamMachineUtil() {
	}

	public static String getYxjb(String xtyxms) {
		if ("1".equals(xtyxms) || "2".equals(xtyxms)) {
			return "W";
		}
		if ("3".equals(xtyxms)) {
			return "Z";
		}
		if ("4".equals(xtyxms)) {
			return "G";
		}
		return xtyxms;
	}

	public static boolean isMapReduce(String sxl) {
		if (!StringUtil.checkBN(sxl)) {
			return false;
		}

		return sxl.contains(".MapredDataImporter");
	}

	public static List<RmStreamMachine> getMachineList(String uri) throws Exception {
		List<RmStreamMachine> list = new ArrayList<RmStreamMachine>();
		list.addAll(getZookeeperAddrs(uri));
		list.addAll(getKafkaAddrs(uri));

		List<RmStreamMachine> streamMachineList = new ArrayList<RmStreamMachine>();
		streamMachineList.addAll(getStreamingjobserverAddrs(uri));
		List<String> kList = new ArrayList<String>();
		for (RmStreamMachine rmStreamMachine : streamMachineList) {
			if (null != rmStreamMachine && StringUtil.checkBN(rmStreamMachine.getIp())) {
				kList.add(rmStreamMachine.getIp());
			}
		}

		List<RmStreamMachine> tmpLit = getStreamingworkerAddrs(uri);
		for (RmStreamMachine rmStreamMachine : tmpLit) {
			if (null != rmStreamMachine && !kList.contains(rmStreamMachine.getIp())) {
				streamMachineList.add(rmStreamMachine);
			}
		}
		list.addAll(streamMachineList);

		if(PermitUtil.isCloudEnable()){
		List<RmStreamMachine> hbaseZookpeerList = getHbaseZookeeperAddrs(uri);
		String hbaseRootDir = getHbaseRootDir(uri);
		if (null != hbaseZookpeerList && !hbaseZookpeerList.isEmpty() && StringUtil.checkBN(hbaseRootDir)) {
			for (RmStreamMachine rmStreamMachine : hbaseZookpeerList) {
				if (null != rmStreamMachine) {
					rmStreamMachine.setBz(hbaseRootDir);
				}
			}
		}
		
		list.addAll(hbaseZookpeerList);
		}
		return list;
	}

	private static String getHbaseRootDir(String uri) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/transwarp/server/hyperbaseroot");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			JSONObject entry = JSONObject.fromObject(sb.toString());
			if (null != entry) {
				return entry.getString("root.dir");
			}
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
		return "";
	}

	public static String uploadJar(byte[] jarBytes, String uri, List<RmStreamConsumer> jobList, String streamUri, String xtyxms) throws Exception {
		List<String> streamJobList = new ArrayList<String>();
		List<String> mapReduceJobList = new ArrayList<String>();

		for (RmStreamConsumer consumer : jobList) {
			if (null != consumer) {
				if (isMapReduce(consumer.getSxl())) {
					mapReduceJobList.add(consumer.getJobid());
				} else {
					streamJobList.add(consumer.getJobid());
				}
			}
		}

		stopAllJobs(streamJobList, uri);
		List<String> failStopJobList = getRunningJobs(uri);
		stopAllJobs(failStopJobList, uri);
		stopAllMapReduceJobs(mapReduceJobList, streamUri);
		// List<String> failStopMapJobList = getRunningMapJobs(streamUri);
		// stopAllMapReduceJobs(failStopMapJobList, streamUri);

		File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "jartmp");
		if (!file.exists()) {
			file.mkdirs();
		}

		String tmpFilePath = file.getAbsolutePath() + System.getProperty("file.separator") + System.currentTimeMillis() + ".jar";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmpFilePath);
			fos.write(jarBytes);
			fos.flush();
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		File tmpFile = new File(tmpFilePath);
		try {
			if (PermitUtil.isCloudEnable() && ("4".equals(xtyxms) || "3".equals(xtyxms))) {
				HttpPost postRequestMap = new HttpPost("http://" + streamUri + "/newwebui/FileServlet");
				System.out.println("*************** " + "http://" + streamUri + "/newwebui/FileServlet");
				postRequestMap.addHeader("accept", "application/json");
				MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
				multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				multipartEntity.addPart("StreamingDataImporter", new FileBody(tmpFile));
				System.out.println(tmpFile.length());

				postRequestMap.setEntity(multipartEntity.build());

				HttpResponse rspMap = httpClient.execute(postRequestMap);
				System.out.println("Status code is :" + rspMap.getStatusLine().getStatusCode());
				if (rspMap.getStatusLine().getStatusCode() != 200) {
					return "";
				}
			}

			httpClient.getConnectionManager().shutdown();
			httpClient = new DefaultHttpClient();

			FileEntity entity = new FileEntity(tmpFile);
			HttpPost postRequest = new HttpPost("http://" + uri + "/jars/rmstream");
			postRequest.addHeader("accept", "application/json");
			postRequest.setEntity(entity);

			HttpResponse rsp = httpClient.execute(postRequest);

			InputStreamReader is = null;
			BufferedReader br = null;
			try {
				is = new InputStreamReader(rsp.getEntity().getContent());
				br = new BufferedReader(is);

				String line = "";
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				return sb.toString().trim();
			} finally {
				if (null != is) {
					try {
						is.close();
					} catch (Exception e) {
					}
				}
				if (null != br) {
					try {
						br.close();
					} catch (Exception e) {
					}
				}
			}
		} finally {
			httpClient.getConnectionManager().shutdown();
			tmpFile.delete();
		}
	}

	public static List<RmStreamConsumer> runJobs(String uri, String url, String username, String password, List<RmStreamConsumer> classList, String url2,
			String username2, String password2, String mapUri, String kafkaZookeeperAddr, String hbaseZookeeperAddr, String hbaseBaseDir, String flushSize,
			String flushInterval) throws Exception {
		List<RmStreamConsumer> list = null;
		if (null != classList && !classList.isEmpty()) {
			list = new ArrayList<RmStreamConsumer>();
			for (RmStreamConsumer consumer : classList) {
				if (StringUtil.checkBN(consumer.getSxl()) && consumer.getSxl().trim().length() > 3) {
					try {
						String jobid = null;
						if (isMapReduce(consumer.getSxl())) {
							try {
								jobid = runMapReduceJob(mapUri, consumer.getSxl(), consumer.getTpkey(), kafkaZookeeperAddr, consumer.getGpkey(), consumer
										.getRwsl(), hbaseZookeeperAddr, hbaseBaseDir, flushSize, flushInterval);
								System.out.println("-------------> map reduce jobid: " + jobid);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							try {
								jobid = runJob(uri, url, username, password, consumer.getSxl(), url2, username2, password2);
								System.out.println("-------------> streaming jobid: " + jobid);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						list.add(new RmStreamConsumer(consumer.getSxl(), jobid));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return list;
	}

	public static String runJob(String uri, String url, String username, String password, String className, String url2, String username2, String password2)
			throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			String uu = "http://" + uri + "/jobs?appName=rmstream&classPath=" + className + "&context=jobapp&sync=false";
			HttpPost postRequest = new HttpPost(uu);
			postRequest.addHeader("accept", "application/json");
			String data = "jdbcurl=\"" + url + "\",user=\"" + username + "\",pwd=\"" + password + "\",jdbcurlzp=\"" + url2 + "\",userzp=\"" + username2
					+ "\",pwdzp=\"" + password2 + "\"";
			StringEntity entity = new StringEntity(data);
			postRequest.setEntity(entity);
			
			System.out.println("***" + data);

			HttpResponse response = httpClient.execute(postRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonObject = JSONObject.fromObject(sb.toString());
			if (!"STARTED".equals((String) jsonObject.getString("status"))) {
				throw new Exception("任务运行失败" + jsonObject);
			}
			JSONObject result = JSONObject.fromObject(jsonObject.get("result"));
			System.out.println(result.toString());
			return (String) result.getString("jobId");
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static void stopJob(String jobid, String uri) throws Exception {
		if (!StringUtil.checkBN(jobid)) {
			return;
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpDelete delRequest = new HttpDelete("http://" + uri + "/jobs/" + jobid);
			delRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(delRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			System.out.println(sb.toString());

			JSONObject jsonObject = JSONObject.fromObject(sb.toString());

			if (!"KILLED".equals((String) jsonObject.getString("status"))) {
				throw new Exception("停止任务失败" + jsonObject);
			}
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}

	private static void stopAllJobs(List<String> jobList, String uri) {
		if (null != jobList && !jobList.isEmpty()) {
			for (String jobid : jobList) {
				if (StringUtil.checkBN(jobid) && jobid.trim().length() > 3) {
					try {
						stopJob(jobid, uri);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void stopAllMapReduceJobs(List<String> jobList, String uri) {
		if (null != jobList && !jobList.isEmpty()) {
			for (String jobid : jobList) {
				if (StringUtil.checkBN(jobid) && jobid.trim().length() > 3) {
					try {
						stopMapReduceJob(jobid, uri);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static List<String> getRunningJobs(String uri) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		List<String> resultList = new ArrayList<String>();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/jobs");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String respStr = sb.toString();

			JSONArray jsonArray = JSONArray.fromObject(respStr);
			for (Object object : jsonArray) {
				JSONObject jsonObject = JSONObject.fromObject(object);
				if ("RUNNING".equals((String) jsonObject.getString("status"))) {
					String jobId = (String) jsonObject.getString("jobId");
					if (StringUtil.checkBN(jobId) && jobId.trim().length() > 5) {
						resultList.add(jobId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return resultList;
	}

	@SuppressWarnings("unused")
	private static List<String> getRunningMapJobs(String uri) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		List<String> resultList = new ArrayList<String>();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/transwarp/yarn/application");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String respStr = sb.toString();
			System.out.println(respStr);

			JSONObject jsonObject = JSONObject.fromObject(respStr);
			if (null != jsonObject) {
				JSONObject apps = jsonObject.getJSONObject("apps");
				if (null != apps) {
					JSONArray arrs = JSONArray.fromObject(apps.getString("app"));
					if (null != arrs) {
						for (Object object : arrs) {
							JSONObject jo = JSONObject.fromObject(object);
							if ("RUNNING".equals((String) jo.getString("state"))) {
								String jobId = (String) jo.getString("id");
								if (StringUtil.checkBN(jobId) && jobId.trim().length() > 5) {
									resultList.add(jobId);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return resultList;
	}

	private static List<RmStreamMachine> getZookeeperAddrs(String uri) throws Exception {
		return getAddrList("http://" + uri + "/transwarp/server/zookeeper", "3", "0");
	}

	private static List<RmStreamMachine> getKafkaAddrs(String uri) throws Exception {
		return getAddrList("http://" + uri + "/transwarp/server/kafkaserver", "1", "0");
	}

	private static List<RmStreamMachine> getStreamingworkerAddrs(String uri) throws Exception {
		return getAddrList("http://" + uri + "/transwarp/server/streamingworker", "2", "0");
	}

	private static List<RmStreamMachine> getStreamingjobserverAddrs(String uri) throws Exception {
		return getAddrList("http://" + uri + "/transwarp/server/streamingjobserver", "2", "1");
	}

	private static List<RmStreamMachine> getHbaseZookeeperAddrs(String uri) throws Exception {
		return getAddrList("http://" + uri + "/transwarp/server/hyperbasezookeeper", "4", "0");
	}

	private static List<RmStreamMachine> getAddrList(String url, String jqlx, String sfgljd) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		List<RmStreamMachine> resultList = new ArrayList<RmStreamMachine>();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			JSONObject entry = JSONObject.fromObject(sb.toString());
			if (null != entry) {
				JSONArray entryList = JSONArray.fromObject(entry.get("entry"));
				if (null != entryList) {
					for (Object object : entryList) {
						JSONObject jsonObject = JSONObject.fromObject(object);
						if (null != jsonObject) {
							resultList.add(new RmStreamMachine(jsonObject.getString("ip"), jsonObject.getString("port"), jqlx, sfgljd, jsonObject
									.getString("host")));
						}
					}
				}
			}
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return resultList;
	}

	public static void stopMapReduceJob(String jobid, String uri) throws Exception {
		if (!StringUtil.checkBN(jobid)) {
			return;
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpDelete delRequest = new HttpDelete("http://" + uri + "/transwarp/yarn/application/" + jobid);
			delRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(delRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			System.out.println(sb.toString());

			JSONObject jsonObject = JSONObject.fromObject(sb.toString());

			// TODO --- 成功停止应该返回什么
			if ("ERROR".equals((String) jsonObject.getString("state")) || "FAILED".equals((String) jsonObject.getString("state"))) {
				throw new Exception("停止任务失败" + jsonObject);
			}
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static String runMapReduceJob(String uri, String className, String topic, String kafkaZookeeperAddr, String groupid, String consumerNum,
			String hbaseZookeeperAddr, String hbaseBaseDir, String flushSize, String flushInterval) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			String uu = "http://" + uri + "/transwarp/yarn/application?classpath=" + className + "&topic=" + topic + "&kafkaZookeeperAddr="
					+ kafkaZookeeperAddr + "&groupid=" + groupid + "&consumerNum=" + consumerNum + "&hbaseZookeeperAddr=" + hbaseZookeeperAddr
					+ "&hbaseBaseDir=" + hbaseBaseDir + "&flushSize=" + flushSize + "&flushInterval=" + flushInterval;
			System.out.println("--------> " + uu);
			HttpPost postRequest = new HttpPost(uu);
			postRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(postRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			System.out.println(sb.toString());

			JSONObject jsonObject = JSONObject.fromObject(sb.toString());
			// if (!"SUCCESS".equals((String) jsonObject.getString("result"))) {
			// throw new Exception("任务运行失败" + jsonObject);
			// }
			return (String) jsonObject.getString("applicationid");
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static List<RmStreamStatusBean> getRunStatus(String uri, String tpkey, String gpkey) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		List<RmStreamStatusBean> resultList = new ArrayList<RmStreamStatusBean>();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/transwarp/kafka/consumers/" + gpkey + "/topics/" + tpkey);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			}

			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String respStr = sb.toString();
			System.out.println(respStr);

			JSONObject jsonObject = JSONObject.fromObject(respStr);
			JSONArray consumer = JSONArray.fromObject(jsonObject.get("consumers"));
			List<RmStreamConsumerStatus> consumerList = new ArrayList<RmStreamConsumerStatus>();
			if (null != consumer) {
				for (Object c : consumer) {
					JSONObject consu = JSONObject.fromObject(c);
					String ip = consu.getString("host");
					JSONArray partition = consu.getJSONArray("partitions");
					if (null != partition) {
						for (Object p : partition) {
							JSONObject jp = JSONObject.fromObject(p);
							consumerList.add(new RmStreamConsumerStatus(ip, jp.getLong("offset"), jp.getLong("lag")));
						}
					}
				}
			}

			if (null != consumerList && !consumerList.isEmpty()) {
				Map<String, List<RmStreamStatusBean>> map = new HashMap<String, List<RmStreamStatusBean>>();
				for (RmStreamConsumerStatus rmStreamConsumerStatus : consumerList) {
					if (map.containsKey(rmStreamConsumerStatus.getKey())) {
						map.get(rmStreamConsumerStatus.getKey())
								.add(
										new RmStreamStatusBean(rmStreamConsumerStatus.getKey(), 1, rmStreamConsumerStatus.getOffset(), rmStreamConsumerStatus
												.getLag()));
					} else {
						List<RmStreamStatusBean> li = new ArrayList<RmStreamStatusBean>();
						li.add(new RmStreamStatusBean(rmStreamConsumerStatus.getKey(), 1, rmStreamConsumerStatus.getOffset(), rmStreamConsumerStatus.getLag()));
						map.put(rmStreamConsumerStatus.getKey(), li);
					}
				}
				for (String ip : map.keySet()) {
					int rwsl = 0;
					long cll = 0L;
					long jyl = 0L;
					List<RmStreamStatusBean> tmpList = map.get(ip);
					if (null != tmpList) {
						for (RmStreamStatusBean rmStreamStatusBean : tmpList) {
							rwsl++;
							cll += rmStreamStatusBean.getCll();
							jyl += rmStreamStatusBean.getJyl();
						}
					}
					resultList.add(new RmStreamStatusBean(ip, rwsl, cll, jyl));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return resultList;
	}

	public static String getPwdKey() {
		String result = "";
		for (int i = 0; i < key.length(); i++) {
			result += String.valueOf(new Character((char) ((key.charAt(i) + 5) % 128)));
		}
		return result;
	}

	public static List<RmStreamPartitionStatusBean> getPartitionStatus(String uri, String ip, String tpkey, String gpkey) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		List<RmStreamPartitionStatusBean> resultList = new ArrayList<RmStreamPartitionStatusBean>();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/transwarp/kafka/consumers/" + gpkey + "/topics/" + tpkey);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			}

			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String respStr = sb.toString();
			System.out.println(respStr);

			JSONObject jsonObject = JSONObject.fromObject(respStr);
			JSONArray consumer = JSONArray.fromObject(jsonObject.get("consumers"));
			if (null != consumer) {
				for (Object c : consumer) {
					JSONObject consu = JSONObject.fromObject(c);
					String host = consu.getString("host");
					if (StringUtil.checkBN(ip) && ip.equals(host)) {
						JSONArray partition = consu.getJSONArray("partitions");
						if (null != partition) {
							for (Object p : partition) {
								JSONObject jp = JSONObject.fromObject(p);
								resultList.add(new RmStreamPartitionStatusBean(jp.getString("id"), jp.getLong("offset"), jp.getLong("lag")));
							}
						}
					}
				}
			}

			if (null != resultList) {
				Collections.sort(resultList, new Comparator<RmStreamPartitionStatusBean>() {
					public int compare(RmStreamPartitionStatusBean o1, RmStreamPartitionStatusBean o2) {
						return o1.getBh().compareTo(o2.getBh());
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return resultList;
	}

	public static RmStreamStaus getMapReduceJobStatus(String jobId, String uri) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/transwarp/yarn/application/" + jobId);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String respStr = sb.toString();
			JSONObject j = JSONObject.fromObject(respStr);
			JSONObject jsonObject = JSONObject.fromObject(j.getString("app"));
			if (jsonObject.containsKey("state")) {
				if ("RUNNING".equals(jsonObject.getString("state"))) {
					return RmStreamStaus.RUNNING;
				}
				/*if ("ERROR".equals(jsonObject.getString("state"))) {
					return RmStreamStaus.ERROR;
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return RmStreamStaus.FINSHED;
	}

	public static RmStreamStaus getJobStatus(String jobId, String uri) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			HttpGet getRequest = new HttpGet("http://" + uri + "/jobs/" + jobId + "/status");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			is = new InputStreamReader(response.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String respStr = sb.toString();
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			if (jsonObject.containsKey("status")) {
				if ("RUNNING".equals(jsonObject.getString("status"))) {
					return RmStreamStaus.RUNNING;
				}
				/*if ("ERROR".equals(jsonObject.getString("status"))) {
					return RmStreamStaus.ERROR;
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			httpClient.getConnectionManager().shutdown();
		}

		return RmStreamStaus.FINSHED;
	}

	public static void main(String[] args) throws Exception {
		//String uri = "10.110.167.208:8090";
//		// stopJob("9e1daee6-262b-4a1e-9f0d-441f96a28b43", uri);
		//runJob(uri, "jdbc:oracle:thin:@10.110.167.19:1521:orcl", "rm", "ybjjzdrmdata", "com.tmri.stream.driver.w.TfcPassDriver",
		//		"jdbc:oracle:thin:@10.110.167.19:1521:orcl", "rm", "ybjjzdrmdata");
//		// System.out.println(getRunningJobs("10.2.43.119:8090"));
//		// System.out.println(getRunningMapJobs(uri));
//		// stopMapReduceJob("application_1417445678773_0055", "10.2.43.2:8180");
//		// runMapReduceJob("10.2.43.2:8180", "io.transwarp.MapredDataImporter",
//		// "TOPIC_TFC_PASS", "10.2.43.5:2181,10.2.43.6:2181,10.2.43.7:2181",
//		// "G_TFC_PASS_G2",
//		// "2", "10.2.43.5:2181,10.2.43.6:2181,10.2.43.7:2181", "/hyperbase1",
//		// "10", "1000");
//		// System.out.println(getRunStatus(uri, "TOPIC_TFC_PASS",
//		// "W_TFC_PASS_G1"));
//		// System.out.println(getPartitionStatus(uri, "10.2.43.119",
//		// "TOPIC_TFC_PASS", "W_TFC_PASS_G1"));
//		// System.out.println(getJobStatus("b1ab6ef9-5f7c-426a-b817-2adbbc649273",
//		// "10.2.43.119:8090"));
//
//		// 20f69e75-2196-43cc-a7bf-7c548db4ee9b
		//runMapReduceJob("10.2.43.2:8180", "io.transwarp.MapredDataImporter", "TOPIC_TFC_PASS", "10.2.43.5:2181,10.2.43.6:2181,10.2.43.7:2181", "G_TFC_PASS_G2", "1", "10.2.43.5:2181,10.2.43.6:2181,10.2.43.7:2181", "/hyperbase1", "2500", "3000");
//		System.out.println(getMachineList("10.107.3.40:8180"));
//		testUpload("10.2.43.10:8090");
		runJob("10.2.43.10:8090", "jdbc:oracle:thin:@10.2.43.115:1521:rmsrv", "rms", "rms098", "com.tmri.stream.driver.w.VioSurveilDriver",
						"jdbc:oracle:thin:@10.2.43.115:1521:rmsrv", "rms", "rms098");
	}
	
	private static void testUpload(String uri) throws Exception{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		FileEntity entity = new FileEntity(new File("D://StreamingDemo-assembly_test.jar"));
		HttpPost postRequest = new HttpPost("http://" + uri + "/jars/rmstream");
		postRequest.addHeader("accept", "application/json");
		postRequest.setEntity(entity);

		HttpResponse rsp = httpClient.execute(postRequest);

		InputStreamReader is = null;	
		BufferedReader br = null;
		try {
			is = new InputStreamReader(rsp.getEntity().getContent());
			br = new BufferedReader(is);

			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
