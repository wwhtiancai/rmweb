package com.tmri.share.cache.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.cache.dao.BaseDataCacheDao;
import com.tmri.share.cache.service.BaseDataCacheService;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.DebugLog;
import com.tmri.share.frm.util.NetUtil;

@Service
public class BaseDataCacheServiceImpl implements BaseDataCacheService {

	@Autowired
	private BaseDataCacheDao baseDataCacheDao;

	public void loadBaseData() throws Exception {
        // initPermitKeys();
		initSysParas();
	}

	// ����ϵͳ����
	public void initSysParas() throws Exception {
		System.out.println("ϵͳ������Ϣ����.....");
		String key = null;
		List sysparas = this.baseDataCacheDao.getAllSysParas();
		Hashtable tab = new Hashtable();
		SysPara sysPara = null;
		for (int i = 0; i < sysparas.size(); i++) {
			sysPara = (SysPara) sysparas.get(i);
			key = "syspara:" + sysPara.getXtlb() + "_" + sysPara.getGjz();
			tab.put(key, sysPara);

			// xuxd 1212 �����Ƿ�Ϊdebugģʽ
			if (sysPara.getGjz().equalsIgnoreCase("isdebug")) {
				if (sysPara.getMrz().equals("1")) {
					DebugLog.setDebug(true);
				} else {
					DebugLog.setDebug(false);
				}
			}
		}
		SystemCache.getInstance().remove(Constants.MEM_SYSPARA);
		SystemCache.getInstance().reg(Constants.MEM_SYSPARA, tab);

		// ����ssl
		SysPara para = (SysPara) tab.get("syspara:00_SSLPORT");
		String tmp = "";
		if (para != null)
			tmp = para.getMrz();
		if (tmp == null || tmp.toLowerCase().equals("null")) {
			tmp = "";
		}
		Constants.SYS_SSLPORT = NetUtil.transSSL(tmp);
		SystemCache.getInstance().putCachServiceList(Constants.MEM_SYSPARA.toLowerCase(), "ϵͳ������Ϣ");
		System.out.println(Constants.SYS_SSLPORT);
		System.out.println("ϵͳ������Ϣ���ؽ���");
	}
}
