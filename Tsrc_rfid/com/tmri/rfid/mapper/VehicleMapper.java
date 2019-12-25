package com.tmri.rfid.mapper;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.bean.Vehicle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Joey on 2015/9/14.
 */
@Repository
public interface VehicleMapper extends BaseMapper<Vehicle>{

    List<Vehicle> queryList(Map<String, Object> map);

    Vehicle queryByLsh(@Param("lsh") String lsh);

    int synchronize(Vehicle vehicle);

    Vehicle queryByHphm(@Param("hpzl") String hpzl, @Param("hphm") String hphm);

}
