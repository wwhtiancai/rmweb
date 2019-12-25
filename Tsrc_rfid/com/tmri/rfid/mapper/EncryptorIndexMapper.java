package com.tmri.rfid.mapper;

import com.tmri.rfid.bean.EncryptorIndex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Joey on 2016/1/18.
 */
@Repository
public interface EncryptorIndexMapper extends BaseMapper<EncryptorIndex> {

    List<EncryptorIndex> lock(@Param("encryptorId") String encryptorId, @Param("index") int index);

}
