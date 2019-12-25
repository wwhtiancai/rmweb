package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.dao.VehicleDao;
import com.tmri.rfid.mapper.VehicleMapper;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.VehicleUtil;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/6.
 */
@Service
public class VehicleServiceImpl extends BaseServiceImpl implements VehicleService {

    private final static Logger LOG = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehicleMapper vehicleMapper;

	@Autowired
	protected GSysparaCodeService gSysparaCodeService;

    @Resource
    protected EriService eriService;

    @Resource
    protected VehicleLogService vehicleLogService;

    @Resource(name = "runtimeProperty")
    private RuntimeProperty runtimeProperty;

    @Resource
    private RemoteVehicleService remoteVehicleService;

    public List<Vehicle> fetchByCondition(Map condition) throws Exception {
        return vehicleMapper.queryByCondition(condition);
    }

    public List<Vehicle> fetchByCondition(Map condition, PageBounds pageBounds) throws Exception {
        return vehicleMapper.queryByCondition(condition, pageBounds);
    }

    public Vehicle fetchByHphm(String hpzl, String hphm) {
        return vehicleMapper.queryByHphm(hpzl, hphm);
    }


	@Override
	public Vehicle fetchById(Long id) throws Exception{
		return vehicleMapper.queryById(id);
	}

	
	public Map<String, Object> translate(VehicleInfo bean) throws Exception{
        Map<String, Object> map = MapUtilities.transBean2Map(bean);
        if(bean.getHpzl().equals("MH")){
            map.put("cllx", gSysparaCodeService.getCodeValue("00", "2004", bean.getCllx()).trim());
            return map;
        }
        if (StringUtil.checkBN(bean.getHpzl())) {
            map.put("hpzl", gSysparaCodeService.getCodeValue("00", "1007", bean.getHpzl()).trim());
        }
        if (StringUtil.checkBN(bean.getCllx())) {
            map.put("cllx", gSysparaCodeService.getCodeValue("00", "1004", bean.getCllx()).trim());
            int zzl;
            try {
                zzl = VehicleUtil.getZzl(bean);
            } catch (IllegalArgumentException iae) {
                zzl = 0;
            }
            map.put("zzl",  Math.round(zzl / 100.0) / 10.0 + "吨");
            if (bean.getCllx().toUpperCase().startsWith("K")) {
                map.put("hdzk", bean.getHdzk() + "人");
            }
        }
        if (StringUtil.checkBN(bean.getSyxz())) {
            map.put("syxz", gSysparaCodeService.getCodeValue("00", "1003", bean.getSyxz().trim()).trim());
        }
        if (StringUtil.checkBN(bean.getHphm())) {
            map.put("hphm", bean.getFzjg().substring(0, 1) + bean.getHphm());
        }
        if (bean.getPl() != null) {
            double pl = Math.round(bean.getPl()/100.0)/10.0;
            map.put("pl",  pl+"L");
        }
        if (StringUtil.checkBN(bean.getCsys())) {
            map.put("csys", gSysparaCodeService.getCodeValue("00", "1008", bean.getCsys().substring(0,1)).trim()+"色");
        }
        return map;
	}

    @Override
    public Vehicle synchronize(String lsh, String fzjg, String hphm, String hpzl,String Mhcllx) throws Exception {
        LOG.info(String.format("------> calling VehicleService.synchronize lsh=%s,fzjg=%s,hphm=%s,hpzl=%s", lsh, fzjg, hphm, hpzl));
        Vehicle vehicle;
        if(hpzl.equals("MH")){
            vehicle = new Vehicle();
            vehicle.setHphm("民"+ hphm);
            vehicle.setHpzl(hpzl);
            vehicle.setCllx(gSysparaCodeService.getCode("00", "2004", Mhcllx).getDmz2().toString());
            List<Vehicle> old = vehicleMapper.queryByCondition(MapUtilities.buildMap("hphm", hphm));
            if (old == null || old.isEmpty()) {
                if (vehicleMapper.create(vehicle) > 0) {
                    return vehicle;
                } else {
                    throw new RuntimeException("同步(新增)车辆信息失败");
                }
            }else{
                return vehicle;
            }
        }

        if (runtimeProperty.isRemoteFetchVehicle()) {
            if (StringUtils.isNotEmpty(lsh)) {
                vehicle = remoteVehicleService.fetch(lsh);
            } else {
                vehicle = remoteVehicleService.fetch(fzjg, hphm, hpzl);
            }
            if (StringUtils.isEmpty(vehicle.getHphm()) || StringUtils.isEmpty(vehicle.getHpzl()) || StringUtils.isEmpty(vehicle.getFzjg())) {
                throw new RuntimeException("业务流程错误，无法获取车辆信息,请按流程操作");
            }
            List<Vehicle> old = vehicleMapper.queryByCondition(MapUtilities.buildMap("jdcxh", vehicle.getJdcxh()));
            if (old == null || old.isEmpty()) {
                if (vehicleMapper.create(vehicle) > 0) {
                    return vehicle;
                } else {
                    throw new RuntimeException("同步(新增)车辆信息失败");
                }
            } else if (old.size() > 1) {
                throw new RuntimeException("车辆信息错误（重复）");
            } else {
                vehicle.setId(old.get(0).getId());
                if (vehicleMapper.update(vehicle) > 0) {
                    return vehicle;
                } else {
                    throw new RuntimeException("同步(更新)车辆信息(ID:" + vehicle.getId() + ")失败");
                }
            }
        } else {
            if (StringUtils.isNotEmpty(lsh)) {
                return fetchByLsh(lsh);
            } else {
                List<Vehicle> vehicleList = fetchByCondition(MapUtilities.buildMap("fzjg", fzjg, "hphm", hphm, "hpzl", hpzl));
                if (vehicleList == null || vehicleList.isEmpty()) {
                    return null;
                } else {
                    return vehicleList.get(0);
                }
            }
        }
    }

    public Vehicle fetchByLsh(String lsh) {
        return vehicleMapper.queryByLsh(lsh);
    }

    @Override
    public boolean create(Vehicle vehicle) throws Exception {
        return vehicleMapper.create(vehicle) > 0;
    }
}
