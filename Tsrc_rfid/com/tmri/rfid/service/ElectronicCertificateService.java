package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.ElectronicCertificate;
import com.tmri.rfid.common.ElectronicCertificateSubjectType;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/11/13.
 */
public interface ElectronicCertificateService {

    void create(ElectronicCertificate certificate) throws Exception;

    ElectronicCertificate create(ElectronicCertificateSubjectType ssztlx, String ssztbh, String zsnr, int zsbh) throws Exception;

    List<ElectronicCertificate> fetchByCondition(Map condition) throws Exception;

    List<ElectronicCertificate> fetchByCondition(Map condition, PageBounds pageBounds) throws Exception;

    ElectronicCertificate fetchAvailable(int ztlx, String ztbh, int zsbh) throws Exception;

}
