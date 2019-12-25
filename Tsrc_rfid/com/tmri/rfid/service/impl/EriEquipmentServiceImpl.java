package com.tmri.rfid.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmri.rfid.common.EriReaderWriterRegisterStatus;
import com.tmri.rfid.service.ExchangeService;
import com.tmri.rfid.service.ExternalRequestService;
import com.tmri.rfid.util.EriUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.EriEquipmentBean;
import com.tmri.rfid.mapper.EriEquipmentMapper;
import com.tmri.rfid.service.BaseServiceImpl;
import com.tmri.rfid.service.EriEquipmentService;
import com.tmri.rfid.util.MapUtilities;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/*
 *wuweihong
 *2016-2-15
 */
@Service
public class EriEquipmentServiceImpl extends BaseServiceImpl implements EriEquipmentService{
	@Autowired
    private EriEquipmentMapper eriEquipmentMapper;
	
	@Autowired
    private ExchangeService exchangeService;

	@Resource
	private ExternalRequestService externalRequestService;

	@Resource
	private DeviceRegistrationHandler deviceRegistraionHandler;
	
	@Override
	public PageList<EriEquipmentBean> queryList(int pageIndex, int pageSize, Long xh,
			String sbh, String glbm) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("xh", xh);
		map.put("sbh", sbh);
		map.put("glbm", glbm);
		return (PageList<EriEquipmentBean>) getPageList(EriEquipmentMapper.class, "queryByCondition",
	    		map, pageIndex, pageSize);
	}


	@Override
	public int updateEquipmentState(long zt) throws Exception {
		// TODO Auto-generated method stub
		return eriEquipmentMapper.updateState(zt);
	}


	@Override
	public EriEquipmentBean queryById(Long xh) {
		// TODO Auto-generated method stub
		return eriEquipmentMapper.queryById(xh);
	}

	public EriEquipmentBean fetchByAqmkxhAndGlbm(String aqmkxh, String glbm) {
		List<EriEquipmentBean> equipmentBeanList = eriEquipmentMapper.queryByCondition(MapUtilities.buildMap("aqmkxh", aqmkxh,
				"glbm", glbm));
		if (equipmentBeanList != null && !equipmentBeanList.isEmpty()) {
			return equipmentBeanList.get(0);
		} else {
			return null;
		}
	}

	public EriEquipmentBean fetchByAqmkxh(String aqmkxh) {
		List<EriEquipmentBean> equipmentBeanList = eriEquipmentMapper.queryByCondition(MapUtilities.buildMap("aqmkxh", aqmkxh));
		if (equipmentBeanList != null && !equipmentBeanList.isEmpty()) {
			return equipmentBeanList.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public EriEquipmentBean register(String aqmkxh, String glbm) throws Exception {
		String _aqmkxh = aqmkxh;
		if (aqmkxh.length() == 16) {
			_aqmkxh = EriUtil.parseSecurityModelXh(aqmkxh);
		}
		EriEquipmentBean eriEquipmentBean = fetchByAqmkxhAndGlbm(_aqmkxh, glbm);
		if (eriEquipmentBean != null) {
			if (eriEquipmentBean.getZt() == EriReaderWriterRegisterStatus.REGISTERED.getStatus()) {
				return eriEquipmentBean;
			} else if (eriEquipmentBean.getZt() == EriReaderWriterRegisterStatus.DISABLED.getStatus()) {
				return null;
			} else {
				eriEquipmentBean.setZt(EriReaderWriterRegisterStatus.REGISTERED.getStatus());
				update(eriEquipmentBean);
				return eriEquipmentBean;
			}
		} else {
			deviceRegistraionHandler.generate(MapUtilities.buildMap("aqmkxh", _aqmkxh,
					"glbm", glbm, "qyzt", EriReaderWriterRegisterStatus.REGISTERED.name()));
			return null;
		}
	}


	@Override
	public boolean create(EriEquipmentBean eriEquipmentBean) {
		return eriEquipmentMapper.create(eriEquipmentBean) > 0;
	}


	@Override
	public boolean update(EriEquipmentBean eriEquipmentBean) throws Exception {
		if (eriEquipmentMapper.update(eriEquipmentBean) > 0) {
			exchangeService.saveData(eriEquipmentBean);
			return true;
		} else {
			return false;
		}
	}


	@Override
	public int delete(String xh) {
		// TODO Auto-generated method stub
		return eriEquipmentMapper.deleteById(xh);
	}

	@Override
	public EriReaderWriterRegisterStatus checkRegisterStatus(String aqmkxh) throws Exception{
		String _aqmkxh = aqmkxh;
		if (aqmkxh.length() == 16) {
			_aqmkxh = EriUtil.parseSecurityModelXh(aqmkxh);
		}
		EriEquipmentBean eriEquipmentBean = fetchByAqmkxh(_aqmkxh);
		if (eriEquipmentBean == null) return null;
		else {
			for (EriReaderWriterRegisterStatus registerStatus : EriReaderWriterRegisterStatus.values()) {
				if (eriEquipmentBean.getZt() == registerStatus.getStatus()) {
					return registerStatus;
				}
			}
		}
		return null;
	}

	@Override
	public List<EriEquipmentBean> fetchByCondition(Map condition) {
		return eriEquipmentMapper.queryByCondition(condition);
	}
}
