package com.tmri.rfid.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleInfo;
import com.tmri.rfid.bean.VehicleLog;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.util.PageController;

/**
 * Created by Joey on 2015/9/6.
 */
public interface VehicleService {

    Vehicle fetchById(Long id) throws Exception;

	List<Vehicle> fetchByCondition(Map condition, PageBounds pageBounds) throws Exception;

    List<Vehicle> fetchByCondition(Map condition) throws Exception;

    Map<String, Object> translate(VehicleInfo vehicle) throws Exception;

    Vehicle synchronize(String lsh, String fzjg, String hphm, String hpzl,String Mhcllx) throws Exception;

    Vehicle fetchByLsh(String lsh);

    boolean create(Vehicle vehicle) throws Exception;

    Vehicle fetchByHphm(String hpzl, String hphm);
}
