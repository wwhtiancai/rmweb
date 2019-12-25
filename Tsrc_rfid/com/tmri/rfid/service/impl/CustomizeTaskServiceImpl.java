package com.tmri.rfid.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.esri.aims.mtier.model.metadata.User;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.bean.*;
import com.tmri.rfid.common.*;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.*;
import com.tmri.rfid.util.EriUtil;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.ctrl.view.CustomizeTaskView;
import com.tmri.rfid.dao.CustomizeDao;
import com.tmri.rfid.mapper.CustomizeTaskMapper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;

/*
 *wuweihong
 *2015-11-16
 */
@Service
public class CustomizeTaskServiceImpl extends BaseServiceImpl implements CustomizeTaskService{

	
	private final static Logger LOG = LoggerFactory.getLogger(CustomizeTaskServiceImpl.class);
    @Autowired
    private CustomizeDao customizeDao;
    
	@Autowired
    private CustomizeTaskMapper customizeTaskMapper;
	
	@Autowired
	protected GSysparaCodeService gSysparaCodeService;

	@Resource
	private VehicleService vehicleService;

	@Resource(name = "runtimeProperty")
	private RuntimeProperty runtimeProperty;

	@Resource
	private EriService eriService;

	@Resource
	private EriCustomizeRecordService eriCustomizeRecordService;

	@Resource
	private VehicleLogService vehicleLogService;

	@Resource
	private ReviewRecordService reviewRecordService;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<CustomizeTaskView> queryList(int pageIndex, int pageSize,
			Map condition) throws Exception {
		// TODO Auto-generated method stub
		return (PageList<CustomizeTaskView>) getPageList(CustomizeTaskMapper.class, "queryCustomizeView",
				condition, pageIndex, pageSize);
	}

	@Override
	public List<CustomizeTask> queryList(Map condition) throws Exception {
		// TODO Auto-generated method stub
		return customizeTaskMapper.queryByCondition(condition);
	}

    @Override
	public CustomizeTask create(String lsh, Long clxxid,String lxdh,String sqr,int rwlx,String jbr,int zt, String tid, String bz, Long yclxxid) throws Exception {
		CustomizeTask customerTask = new CustomizeTask();
        if (yclxxid != null) {
            customerTask.setYclxxid(yclxxid);
        }
		if(clxxid != null){
			customerTask.setClxxid(clxxid);
		}
		if(StringUtil.checkBN(sqr)){
			customerTask.setSqr(sqr);
		}
		if(StringUtil.checkBN(lxdh)){
			customerTask.setLxdh(lxdh);
		}
		if(StringUtil.checkBN(jbr)){
			customerTask.setJbr(jbr);
		}
		customerTask.setSqrq(new Date());
		if (StringUtils.isEmpty(lsh)) {
			customerTask.setLsh(
					"R" + DateFormatUtils.format(new Date(), "yyMMdd") + EriUtil.appendZero(String.valueOf(customizeDao.getSeqId()), 6)
			);
		} else {
			customerTask.setLsh(lsh);
		}
		if (StringUtils.isNotEmpty(tid)) {
			customerTask.setTid(tid);
		}
		customerTask.setZt(zt);
		customerTask.setRwlx(rwlx);
		customerTask.setBz(bz);
		int result = customizeTaskMapper.create(customerTask);
		if (result > 0) return customerTask;
		else return null;
	}


	@Override
	public CustomizeTask fetchByXh(Long xh) throws Exception {
		// TODO Auto-generated method stub
		return customizeTaskMapper.queryById(xh);
	}

	@Override
	public CustomizeTask fetchAvailableByLsh(String lsh) throws Exception {
		List<CustomizeTask> customizeTasks = customizeTaskMapper.queryByCondition(
				MapUtilities.buildMap("lsh", lsh, "zt", CustomizeTaskStatus.SUBMIT.getStatus()));
		if (customizeTasks != null && !customizeTasks.isEmpty()) {
			return customizeTasks.get(0);
		} else {
			return null;
		}
	}

	public boolean checkTaskStatus(Vehicle vehicle, int ywlx, String tid) throws Exception {
		Eri eri = eriService.fetchByTid(tid);
		if (eri == null) throw new OperationException("02", "CUSTOMIZE", "电子标识未初始化或初始化失败");
		int eriStatus = eri.getZt();
		if (EriStatus.AVAILABLE.getStatus() != eriStatus) {
			throw new OperationException("03", "CUSTOMIZE", "标识当前状态无法个性化");
		}
		List<EriCustomizeRecord> ecrs = eriCustomizeRecordService.fetchByCondition(
				MapUtilities.buildMap("zt", EriCustomizeStatus.IN_PROGRESS.getStatus(), "tid", tid));
		if (!ecrs.isEmpty()) {
			throw new OperationException("04", "CUSTOMIZE", "该标识正在进行个性化中，请重置");
		}
		if (eri.getClxxbfid() != null) {
			VehicleLog oldVehicle = vehicleLogService.fetchById(eri.getClxxbfid());
			if (!(oldVehicle.getHphm().equalsIgnoreCase(vehicle.getHphm()) &&
					oldVehicle.getHpzl().equalsIgnoreCase(vehicle.getHpzl()) &&
					oldVehicle.getFzjg().equalsIgnoreCase(vehicle.getFzjg())) &&
					!(ywlx == CustomizeTaskType.TRANSFER.getType() ||
							ywlx == CustomizeTaskType.MODIFY.getType())) {
				Code hpzlCode = gSysparaCodeService.getCode(CodeTableDefinition.VEHICLE_LICENCE_TYPE, oldVehicle.getHpzl());
				throw new OperationException("10", "CUSTOMIZE",
						String.format("当前卡已与车辆%s(%s)绑定，如需更新请办理其它业务。",
								oldVehicle.getFzjg().substring(0, 1) + oldVehicle.getHphm(), hpzlCode.getDmsm1()));
			}
		}
		Eri boundCard = eriService.fetchByVehicle(vehicle);
        if (boundCard != null && !boundCard.getTid().equalsIgnoreCase(tid)) {
            throw new OperationException("07", "CUSTOMIZE", "该车辆已与卡（" + boundCard.getKh() + "）绑定");
		}
		return true;
	}

	public CustomizeTask fetch(Vehicle vehicle, int ywlx, String tid, String operator) throws Exception {
        if (vehicle == null) throw new RuntimeException("未查找到对应的车辆信息");
        // TODO Auto-generated method stub
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("zt", CustomizeTaskStatus.SUBMIT.getStatus());

        CustomizeTask eriExistTask = null;
        if (StringUtils.isNotEmpty(tid)) {
            Eri eri = eriService.fetchByTid(tid);
            if (eri == null) throw new OperationException("20", "CUSTOMIZE", "无法识别的电子标识");
            if (eri.getZt() != EriStatus.AVAILABLE.getStatus()) {
                throw new OperationException("21", "CUSTOMIZE", "电子标识已失效");
            }
            condition.put("tid", tid);
            List<CustomizeTask> eriTaskList = customizeTaskMapper.queryByCondition(condition);
            if (eriTaskList != null && !eriTaskList.isEmpty()) {
                if (eriTaskList.size() > 1)
                    throw new OperationException("23", "CUSTOMIZE", "当前电子标识存在多个业务");
                else {
                    eriExistTask = eriTaskList.get(0);
                    if (eriExistTask.getRwlx() != ywlx) {
                        CustomizeTaskType type = CustomizeTaskType.getByType(eriExistTask.getRwlx());
                        throw new OperationException("22", "CUSTOMIZE", "当前电子标识正在办理" +
                                type.getDesc() + "业务，如需继续，请先取消对应业务");
                    }
                }
            }
        }

        condition = new HashMap<String, Object>();
        condition.put("zt", CustomizeTaskStatus.SUBMIT.getStatus());
        condition.put("hphm", vehicle.getHphm());
        condition.put("hpzl", vehicle.getHpzl());
        if(!"MH".equals(vehicle.getHpzl())){
            condition.put("fzjg", vehicle.getFzjg());
        }

        List<CustomizeTask> customizeTaskList = customizeTaskMapper.queryByCondition(condition);
        if(customizeTaskList==null || customizeTaskList.isEmpty()){
            if (!runtimeProperty.isAutoGenerateTask()) {
                return null;
            } else if(eriExistTask != null) {
                throw new OperationException("22", "CUSTOMIZE", "当前电子标识正在办理其它车辆的业务，如需继续，请先取消对应业务");
            } else {
                LOG.info("自动生成任务");
                return create(vehicle.getLsh(), vehicle.getId(), null, operator, ywlx,
                        operator, CustomizeTaskStatus.SUBMIT.getStatus(), tid, "自动生成任务", null);
            }
        } else if (customizeTaskList.size() > 1) {
            throw new OperationException("23", "CUSTOMIZE", "当前车辆存在多个业务");
        } else {
            CustomizeTask task = customizeTaskList.get(0);
            if (task.getRwlx() != ywlx) {
                CustomizeTaskType type = CustomizeTaskType.getByType(task.getRwlx());
                throw new OperationException("11", "CUSTOMIZE", "该车辆正在办理" + type.getDesc() + "业务，如需继续，请先完成或取消之前的业务。");
            } else if (eriExistTask != null && eriExistTask.getXh() != task.getXh()) {
                throw new OperationException("22", "CUSTOMIZE", "当前电子标识正在办理其它车辆的业务，如需继续，请先取消对应业务");
            } else if (StringUtils.isNotEmpty(task.getTid()) && StringUtils.isNotEmpty(tid)
                    && !task.getTid().equalsIgnoreCase(tid)) {
                throw new OperationException("24", "CUSTOMIZE", "当前车辆正在办理此业务，如需更换电子标识办理，请先取消原业务");
            }
            return task;
        }
    }

    @Override
	public CustomizeTask fetch(Vehicle vehicle, int ywlx, String tid) throws Exception {
        return fetch(vehicle, ywlx, tid, UserState.getUser().getYhdh());
	}

    public List<CustomizeTask> fetchByVehicle(VehicleInfo vehicle) throws Exception {
        if (vehicle == null) throw new RuntimeException("请输入车辆信息");
        // TODO Auto-generated method stub
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("zts", new int[] {CustomizeTaskStatus.PENDING.getStatus(), CustomizeTaskStatus.SUBMIT.getStatus()});
        condition.put("hphm", vehicle.getHphm());
        condition.put("hpzl", vehicle.getHpzl());
        condition.put("fzjg", vehicle.getFzjg());

        List<CustomizeTask> customizeTaskList = customizeTaskMapper.queryByCondition(condition);
        return customizeTaskList;
    }

	@Override
	public CustomizeTask fetch(String lsh, String fzjg, String hpzl, String hphm, int ywlx, String tid) throws Exception {
		if (CustomizeTaskType.PRE_CUSTOMIZE.getType() == ywlx) {
			return fetchAvailableByLsh(lsh);
		} else {
			Vehicle vehicle = vehicleService.synchronize(lsh, fzjg, hphm, hpzl,null);
			return fetch(vehicle, ywlx, tid);
		}
	}

	@Override
	public boolean updateByCondition(Map<Object, Object> condition) throws Exception {
		return customizeTaskMapper.updateByCondition(condition) > 0;
	}

	@Override
	public CustomizeTask createModify(String lsh, String fzjg, String hpzl, String hphm, String tid, String sqr, String lxdh, String bz) throws Exception {
		Vehicle vehicle = vehicleService.synchronize(lsh, fzjg, hphm, hpzl,null);
		if (vehicle == null) throw new RuntimeException("未查找到对应的车辆信息");
        VehicleLog oldVehicle = vehicleLogService.fetchBoundByTid(tid);
        if (oldVehicle == null) throw new RuntimeException("该卡未绑定车辆");
		// TODO Auto-generated method stub
		List<CustomizeTask> customizeTasks = queryList(MapUtilities.buildMap("tid", tid,
				"zts", new int[] {CustomizeTaskStatus.PENDING.getStatus(), CustomizeTaskStatus.SUBMIT.getStatus()}));
		if(customizeTasks==null || customizeTasks.isEmpty()){
			SysUser user = UserState.getUser();
			return create(vehicle.getLsh(), vehicle.getId(), lxdh, sqr, CustomizeTaskType.MODIFY.getType(),
					user.getYhdh(), CustomizeTaskStatus.PENDING.getStatus(), tid, bz, oldVehicle.getClxxid());
		} else {
			return null;
		}
	}

    @Override
    public CustomizeTask createChange(String ykh, String kh, String sqr, String lxdh, String bz) throws Exception {
        Eri eri = eriService.fetchByKh(ykh);
        if (eri == null || eri.getClxxbfid() == null) throw new RuntimeException("原电子标识未签注车辆信息，请确认输入信息是否正确");
        VehicleLog vehicleLog = vehicleLogService.fetchById(eri.getClxxbfid());
        //2017-7-14 以防车辆信息有更新，对车辆信息进行同步更新
        Vehicle vehicle = vehicleService.synchronize(null, vehicleLog.getFzjg(), vehicleLog.getHphm(), vehicleLog.getHpzl(),null);
        Eri newEri = null;
        if (StringUtils.isNotEmpty(kh)) {
            newEri = eriService.fetchByKh(kh);
            List<CustomizeTask> customizeTasks = queryList(MapUtilities.buildMap("tid", newEri.getTid(),
                    "zts", new int[] {CustomizeTaskStatus.PENDING.getStatus(), CustomizeTaskStatus.SUBMIT.getStatus()}));
            if(customizeTasks != null && !customizeTasks.isEmpty()){
                throw new RuntimeException("申请换领的电子标识正在进行其它业务");
            }
        }
        SysUser user = UserState.getUser();
        return create(vehicle.getLsh(), vehicle.getId(), lxdh, sqr, CustomizeTaskType.CHANGE.getType(),
                user.getYhdh(), CustomizeTaskStatus.PENDING.getStatus(), newEri == null ? null : newEri.getTid(),
                bz, vehicle.getId());
    }

    @Override
    public CustomizeTask createAnnualInspection(String kh, String sqr, String lxdh, String bz) throws Exception {
        Eri eri = eriService.fetchByKh(kh);
        if (eri == null) throw new RuntimeException("未找到对应的电子标识，请确认是否输入正确");
        VehicleLog vehicle = vehicleLogService.fetchById(eri.getClxxbfid());
        SysUser user = UserState.getUser();
        return create(vehicle.getLsh(), vehicle.getClxxid(), lxdh, sqr, CustomizeTaskType.ANNUAL_INSPECTION.getType(),
                user.getYhdh(), CustomizeTaskStatus.SUBMIT.getStatus(), eri.getTid(),
                bz, vehicle.getClxxid());
    }

	@Override
	public void review(Long xh, int jg, String yj) throws Exception {
	    CustomizeTask customizeTask = fetchByXh(xh);
		if (jg > 0) {
			reviewRecordService.approve("CUSTOMIZE_" + customizeTask.getRwlx(), xh, yj);
			updateByCondition(MapUtilities.buildMap("cond_xh", xh, "zt", CustomizeTaskStatus.SUBMIT.getStatus()));
		} else {
			reviewRecordService.reject("CUSTOMIZE_" + customizeTask.getRwlx(), xh, yj);
		}
	}

    @Override
    public boolean update(CustomizeTask customizeTask) {
        return customizeTaskMapper.update(customizeTask) > 0;
    }


	@Override
	public CustomizeTask fetchByTid(String tid)
			throws Exception {
		List<CustomizeTask> customizeTasks = customizeTaskMapper.queryByCondition(
				MapUtilities.buildMap("tid", tid, "zt", CustomizeTaskStatus.SUBMIT.getStatus()));
		if (customizeTasks != null && !customizeTasks.isEmpty()) {
			return customizeTasks.get(0);
		} else {
			return null;
		}
		
	}
}
