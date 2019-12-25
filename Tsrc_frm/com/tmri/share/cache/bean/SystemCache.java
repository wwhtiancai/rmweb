package com.tmri.share.cache.bean;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.tmri.share.frm.bean.CachType;
import com.tmri.share.frm.bean.Roadsegitem;

public class SystemCache {

	private static SystemCache applicationContext = null;
	private static Hashtable tab;
	private static Hashtable _cachServiceList;

	// 车道错误信息（过车图片）
	private static Map<String, String> lanePErrorMap;

	// 车道错误信息（特征图片）
	private static Map<String, String> laneZErrorMap;

	// 二次识别运行机器名称
	private static String RECG_JQMC = "";

	private static final Map<String, Roadsegitem> roadSegItemMap = new ConcurrentHashMap<String, Roadsegitem>();

	private SystemCache() {
		tab = new Hashtable();
		_cachServiceList = new Hashtable();
		lanePErrorMap = new ConcurrentHashMap<String, String>();
		laneZErrorMap = new ConcurrentHashMap<String, String>();
	}

	public static SystemCache getInstance() {
		if (applicationContext == null) {
			applicationContext = new SystemCache();
		}
		return applicationContext;
	}

	public void addRoadSeg(Roadsegitem roadsegitem) {
		if (null != roadsegitem) {
			roadSegItemMap.put(getRoadSegKey(roadsegitem.getGlbm(), roadsegitem.getDldm(), roadsegitem.getLddm()), roadsegitem);
		}
	}

	public String getRoadSegKey(String glbm, String dldm, String lddm) {
		return glbm + "_" + dldm + "_" + lddm;
	}
	
	public Roadsegitem getRoadSeg(String glbm, String dldm, String lddm){
		return roadSegItemMap.get(getRoadSegKey(glbm, dldm, lddm));
	}

	// 注册 车道错误信息（过车图片）
	public void regLanePErrorMap(String lanKey, String cwdm) {
		this.lanePErrorMap.put(lanKey, cwdm);
	}

	public String getLanePError(String lanKey) {
		return this.lanePErrorMap.get(lanKey);
	}

	// 注册 车道错误信息（特征图片）
	public void regLaneZError(String lanKey, String cwdm) {
		this.laneZErrorMap.put(lanKey, cwdm);
	}

	public String getLaneZError(String lanKey) {
		return this.laneZErrorMap.get(lanKey);
	}

	/**
	 * 需要全部重新加载
	 */
	public void refresh() {
		tab.clear();
	}

	public void remove(Object key) {
		tab.remove(key);
	}

	public Object getValue(Object key) {
		return tab.get(key);
	}

	public boolean repreg(Object key, Object value) {
		tab.put(key, value);
		return true;
	}

	public boolean reg(Object key, Object value) {
		if (tab.containsKey(key)) {
			return false;
		} else {
			if (value != null) {
				tab.put(key, value);
			}
			return true;
		}

	}

	public void putCachServiceList(String key, String name) {
		_cachServiceList.put(key, name);
	}

	public boolean contains(Object key) {
		return tab.containsKey(key);
	}

	public String dumpContext() {
		StringBuffer buffer = new StringBuffer();

		for (Enumeration e = tab.keys(); e.hasMoreElements();) {
			Object key = e.nextElement();
			buffer.append(key);
			buffer.append("\n");
			Map map = (Map) tab.get(key);
			Set set = map.keySet();
			for (Iterator i = set.iterator(); i.hasNext();) {
				Object dmz = i.next();
				Object object = map.get(dmz);
				buffer.append(dmz);
				buffer.append(object);
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}

	// 刷新缓存变量名称列表
	public List<CachType> getCachServiceList() {
		List<CachType> resultList = new Vector<CachType>();
		for (Iterator<String> itr = _cachServiceList.keySet().iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			String name = (String) _cachServiceList.get(key);
			CachType type = new CachType();
			type.setKey(key);
			type.setName(name);
			resultList.add(type);
		}
		return resultList;
	}

	public void setRecg_jqmc(String jqmc) {
		RECG_JQMC = jqmc;
	}

	public String getRecg_jqmc() {
		return RECG_JQMC;
	}
}
