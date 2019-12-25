package com.tmri.rfid.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2016/12/21.
 */
@Repository
public interface StatisticsMapper {

    List<Map> countCustomizationByYhdh(Map condition);
    List<Map> countEri(Map condition);

}
