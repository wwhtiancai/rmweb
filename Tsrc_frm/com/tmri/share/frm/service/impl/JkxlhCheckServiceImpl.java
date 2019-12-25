package com.tmri.share.frm.service.impl;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.tmri.share.frm.dao.JkxlhCheckDao;
import com.tmri.share.frm.service.JkxlhCheckService;
import com.tmri.share.frm.util.StringUtil;

@Service
public class JkxlhCheckServiceImpl implements JkxlhCheckService {
	
	@Autowired
	private JkxlhCheckDao jkxlhCheckDao;

	public long checkJkxlh(String jkxlhStr, String jkidStr, String remoteIp) throws Exception {
		if(!StringUtil.checkBN(jkxlhStr)){
			return -401;  //	接口序列号为空		throw new Exception("未输入接口序列号");
		}
		if(!StringUtil.checkBN(jkidStr)){
			return -402;  //  接口ID为空			throw new Exception("未输入接口ID");
		}
		
		Map<String, Object> ctl = this.jkxlhCheckDao.queryControlByJklxh(jkxlhStr);
		if(ctl == null){
			return -403;  //  接口序列号不存在			throw new Exception("接口序列号不存在");
		}

		Map<String, Object> jkxlh = this.jkxlhCheckDao.queryJkxlhCreator(ctl);
		if(jkxlh == null){
			return -404;   //		throw new Exception("验证接口序列号异常");
		}
		if(!jkxlhStr.equals(jkxlh.get("JKXLH"))){
			return -405;  //        throw new Exception("接口序列号不合法");
		}
		
		Map<String, Object> jyw = this.jkxlhCheckDao.queryControlJyw(ctl);
		if(jyw == null){
			return -406; // throw new Exception("验证接口校验位异常");
		}
		if(!jyw.get("JYW").equals(ctl.get("JYW"))){
			return -407; // throw new Exception("接口校验位被非法修改");
		}
		int idx = ctl.get("KFWJK").toString().indexOf("`" + jkidStr + "`:");
		if(idx <= 0){
			return -408; // throw new Exception("该接口序列号不适用于该接口ID");
		}
		
		String strKfwjk = ctl.get("KFWJK").toString().replaceAll("`", "'");	
		JSONObject kfwjkJSON = JSONObject.fromObject(strKfwjk);
		JSONObject curJkidJson = (JSONObject)kfwjkJSON.get(jkidStr);
		String zt = (String)curJkidJson.get("zt");
		if(!"1".equals(zt)){
			return -409;    // throw new Exception("部局已对该序列号停用本接口");
		}else{
			Map<String, Object> chf = this.jkxlhCheckDao.queryHf(ctl, jkidStr);
			if(chf != null){
				if(!"1".equals(chf.get("ZT"))){
					return -410;  // throw new Exception("当前系统已对该序列号停用本接口");
				}
			}
		}
		
		//xuxd 20140902 增加ip地址逻辑判断，支持ip列表方式,为列表时jsip='-';
		String ksip =ctl.get("KSIP").toString();
		String jsip =ctl.get("JSIP").toString();
		if(!"".equals(jsip)&&jsip.equals("-")){
			if(ksip.indexOf(remoteIp)<0){
				return -411;   //  throw new Exception("不是合法的IP地址");
			}
		}else{
			String[] remoteIps = remoteIp.split("\\.");
			String[] ksips = ksip.split("\\.");
			String[] jsips = jsip.split("\\.");
			
			long[] rips = new long[4];
			long[] kips = new long[4];
			long[] jips = new long[4];
			
			for(int i = 0; i < 4; i++){
				rips[i] = Long.valueOf(remoteIps[i]);
				kips[i] = Long.valueOf(ksips[i]);
				jips[i] = Long.valueOf(jsips[i]);
			}
			
			long rip = (rips[0] << 24) + (rips[1] << 16) + (rips[2] << 8) + rips[3];
			long kip = (kips[0] << 24) + (kips[1] << 16) + (kips[2] << 8) + kips[3];
			long jip = (jips[0] << 24) + (jips[1] << 16) + (jips[2] << 8) + jips[3];
			
			if(rip < kip || rip > jip){
				return -411;   //  throw new Exception("不是合法的IP地址");
			}
		}
		
		long curTime = System.currentTimeMillis();
		
		if(((Timestamp)ctl.get("JKQSRQ")).getTime() > curTime){
			return -412;  //  throw new Exception("接口尚未启用");
		}
		
		if(((Timestamp)ctl.get("JKJZRQ")).getTime() < curTime){
			return -413;  // throw new Exception("接口已过申请期限");
		}
		
		Map<String, Object> cnt = this.jkxlhCheckDao.getContent(jkidStr);
		if(null == cnt){
			return -414;  // throw new Exception("接口不存在，或接口被非法修改");
		}
		
		if(((Timestamp)cnt.get("JKYXQ")).getTime() < curTime){
			return -415; //    throw new Exception("接口已过有效期");
		}
		
		if(!"1".equals(cnt.get("JKZT"))){
			return -416;  // throw new Exception("部局未启用该接口");
		}
		return 1;
	}


}
