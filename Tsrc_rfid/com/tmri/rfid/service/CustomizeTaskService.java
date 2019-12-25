package com.tmri.rfid.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleInfo;
import com.tmri.rfid.ctrl.view.CustomizeTaskView;

/*
 *wuweihong
 *2015-11-16
 */
public interface CustomizeTaskService {
	PageList<CustomizeTaskView> queryList(int pageIndex, int pageSize, Map condition) throws Exception;

	List<CustomizeTask> queryList(Map condition) throws Exception;

    /**
     *
     * @param clxxid 车辆信息ID
     * @param lxdh 联系电话
     * @param sqr 申请人
     * @param rwlx 任务类型
     * @param jbr 经办人
     * @param zt 状态
     * @param tid 电子标识ID
     * @param yclxxid 原车辆信息ID,用于车辆信息变更
     * @return
     * @throws Exception
     */
	CustomizeTask create(String lsh, Long clxxid,String lxdh,String sqr,int rwlx,String jbr,int zt, String tid, String bz, Long yclxxid) throws Exception;
	
	CustomizeTask fetchByXh(Long xh) throws Exception;

	CustomizeTask fetchAvailableByLsh(String lsh) throws Exception;

	CustomizeTask fetch(String lsh, String fzjg, String hpzl, String hphm, int ywlx, String tid) throws Exception;

	CustomizeTask fetch(Vehicle vehicle, int ywlx, String tid) throws Exception;

    CustomizeTask fetch(Vehicle vehicle, int ywlx, String tid, String operator) throws Exception;

    List<CustomizeTask> fetchByVehicle(VehicleInfo vehicle) throws Exception;

	CustomizeTask fetchByTid(String tid) throws Exception;
    
	CustomizeTask createModify(String lsh, String fzjg, String hpzl, String hphm, String tid, String sqr, String lxdh, String bz) throws Exception;

    CustomizeTask createChange(String ykh, String kh, String sqr, String lxdh, String hphm) throws Exception;

    CustomizeTask createAnnualInspection(String kh, String sqr, String lxdh, String hphm) throws Exception;

	boolean updateByCondition(Map<Object, Object> condition) throws Exception;

	void review(Long xh, int jg, String yj) throws Exception;

    boolean update(CustomizeTask customizeTask);

}