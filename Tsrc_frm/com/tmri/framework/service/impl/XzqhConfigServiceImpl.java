package com.tmri.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.XzqhTransBean;
import com.tmri.framework.dao.XzqhConfigDao;
import com.tmri.framework.service.XzqhConfigService;
import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.dao.SLogDao;
import com.tmri.share.frm.service.GBasService;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

@Service
public class XzqhConfigServiceImpl implements XzqhConfigService {
	@Autowired
	private XzqhConfigDao xzqhConfigDao;
	@Autowired
	private SLogDao sLogDao;
	@Autowired
	private GBasService gbasService;

	public List<FrmXzqhLocal> getFrmXzqhLocal(FrmXzqhLocal bean,
			PageController controller) throws Exception {
		List<FrmXzqhLocal> list = xzqhConfigDao.getFrmXzqhLocal(bean, controller);
		this.transfer(list);
		return list;
	}
	
	private void transfer(List<FrmXzqhLocal> list){
		if (list == null){
			return;
		}
		try {
			List<BasAllxzqh> xzqhList = this.getBasAllXzqhList(new FrmXzqhLocal());
			Map<String, String> xzqhMap = new HashMap<String, String>();
			for (BasAllxzqh basXzqh : xzqhList){
				xzqhMap.put(basXzqh.getXzqh(), basXzqh.getQhmc());
			}
			for (FrmXzqhLocal local : list){
				String sjxzqh = local.getSjxzqh();
				if (StringUtils.isBlank(sjxzqh)){
					continue;
				}
				String [] xzqhs = sjxzqh.split("\\#");
				String sjxzqhmc = "";
				for (String qh : xzqhs){
					if (StringUtils.isBlank(sjxzqhmc)){
						if (xzqhMap.get(qh) == null){
							sjxzqhmc += qh;
						}
						else{
							sjxzqhmc += xzqhMap.get(qh);
						}
					}
					else{
						if (xzqhMap.get(qh) == null){
							sjxzqhmc += (","+ qh);
						}
						else{
							sjxzqhmc += ("," + xzqhMap.get(qh));
						}
					}
				}
				local.setSjxzqhmc(sjxzqhmc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BasAllxzqh> getBasAllXzqhList(FrmXzqhLocal bean) throws Exception {
		return xzqhConfigDao.getBasAllXzqhList(bean);
	}

	public Map<XzqhTransBean, List<BasAllxzqh>> getBasAllXzqhMap(FrmXzqhLocal bean)
			throws Exception {
		List<BasAllxzqh> list = this.getBasAllXzqhList(bean);
		if (list == null) {
			return null;
		}
		Map<XzqhTransBean, List<BasAllxzqh>> map = new TreeMap<XzqhTransBean, List<BasAllxzqh>>();
		for (BasAllxzqh basXzqh : list) {			
			String xzqh = basXzqh.getXzqh().substring(0, 4);
			XzqhTransBean obj = new XzqhTransBean(xzqh);
			if (map.get(obj) == null) {
				try {
					obj.setName(gbasService.getCityName(xzqh));
				} catch (Exception e) {
					obj.setName(xzqh);
				}
				map.put(obj, new ArrayList<BasAllxzqh>());
			}
			List<BasAllxzqh> subList = map.get(obj);
			subList.add(basXzqh);
		}
		return map;
	}

	public DbResult saveXzqhList(List<FrmXzqhLocal> xzqhList, RmLog log)
			throws Exception {
		DbResult result = this.xzqhConfigDao.saveXzqhList(xzqhList);
		sLogDao.saveRmLog(log);
		return result;
	}

}
