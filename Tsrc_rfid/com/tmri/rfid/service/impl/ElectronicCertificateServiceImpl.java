package com.tmri.rfid.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.ElectronicCertificate;
import com.tmri.rfid.common.ElectronicCertificateStatus;
import com.tmri.rfid.common.ElectronicCertificateSubjectType;
import com.tmri.rfid.mapper.ElectronicCertificateMapper;
import com.tmri.rfid.service.ElectronicCertificateService;
import com.tmri.rfid.util.MapUtilities;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/11/13.
 */
@Service
public class ElectronicCertificateServiceImpl implements ElectronicCertificateService {

    private final static Logger LOG = LoggerFactory.getLogger(ElectronicCertificateServiceImpl.class);

    @Autowired
    private ElectronicCertificateMapper electronicCertificateMapper;

    @Override
    public void create(ElectronicCertificate certificate) throws Exception {
        electronicCertificateMapper.create(certificate);
    }

    @Override
    public ElectronicCertificate create(ElectronicCertificateSubjectType ssztlx, String ssztbh, String zsnr, int zsbh) throws Exception {
        if (ssztlx == null || StringUtils.isEmpty(ssztbh)) {
            throw new Exception("证书所属主体类型或所属主体编号为空");
        }
        ElectronicCertificate electronicCertificate = fetchAvailable(ssztlx.getType(), ssztbh, zsbh);
        if (electronicCertificate != null) {
            electronicCertificate.setZt(ElectronicCertificateStatus.INVALID.getStatus());
            electronicCertificateMapper.update(electronicCertificate);
        }
        electronicCertificate = new ElectronicCertificate();
        electronicCertificate.setZt(ElectronicCertificateStatus.VALID.getStatus());
        electronicCertificate.setScrq(new Date());
        electronicCertificate.setSsztlx(ssztlx.getType());
        electronicCertificate.setSsztbh(ssztbh);
        electronicCertificate.setZsnr(zsnr);
        electronicCertificate.setZsbh(zsbh);
        create(electronicCertificate);
        return electronicCertificate;
    }

    @Override
    public List<ElectronicCertificate> fetchByCondition(Map condition) throws Exception {
        return electronicCertificateMapper.queryByCondition(condition);
    }

    @Override
    public List<ElectronicCertificate> fetchByCondition(Map condition, PageBounds pageBounds) throws Exception {
        return electronicCertificateMapper.queryByCondition(condition, pageBounds);
    }


    @Override
    public ElectronicCertificate fetchAvailable(int ztlx, String ztbh, int zsbh) throws Exception {
        List<ElectronicCertificate> certificates =
                electronicCertificateMapper.queryByCondition(MapUtilities.buildMap("ssztlx", ztlx, "ssztbh", ztbh, "zt", 1, "zsbh", zsbh));
        if (certificates == null || certificates.isEmpty()) {
            return null;
        } else if (certificates.size() > 1) {
            throw new Exception("找到多张对应的有效电子证书");
        } else
            return certificates.get(0);
    }
}
