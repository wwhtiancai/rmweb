package com.tmri.rfid.dao;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.Vehicle;
import com.tmri.share.frm.util.PageController;

/**
 * Created by Joey on 2015/9/6.
 */
public interface VehicleDao {

    Vehicle queryById(Long id);

    Vehicle queryByHPHMAndHPZL(String hphm, String hpzl ,String fzjg);
    
    public List<Vehicle> getVehicleList(Vehicle vehicle,PageController controller) throws Exception;
    
    Vehicle queryVehicle(Map<String, Object> condition);
}
