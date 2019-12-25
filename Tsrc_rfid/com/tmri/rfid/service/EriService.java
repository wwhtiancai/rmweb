package com.tmri.rfid.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tmri.rfid.bean.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/9/6.
 */
public interface EriService {
    String HOMEPAGE = "HTTP://RFID.122.GOV.CN/RFID/";
    //    String HOMEPAGE = "HTTP://WWW.TMRI.CN/RFID/";

    EriCustomizeContent customize(String tid, Long lsh, String certStr) throws Exception;

    EriCustomizeContent customize(Eri eri, Vehicle vehicle, String pubKey) throws Exception;

    void saveCustomizeResult(Long xh, int result, String reason, String tid) throws Exception;

    void saveInitializeResult(String tid, int result, String sbyy) throws Exception;

    Eri fetchByTid(String tid) throws Exception;

    EriInitializeContent initialize(String tid, String province, String certStr) throws Exception;

    EriInitializeContent initialize(String tid, String province, String certStr, boolean test) throws Exception;

    EriInitializeContent factoryInitialize(String tid, String kh, String bzhh, String rwh, String sbxh, String gwh, String czr, String certStr) throws Exception;

    EriInitializeContent factoryInitialize(String tid, String kh, String bzhh, String rwh, String sbxh, String gwh, String czr, String certStr, boolean test) throws Exception;

    void saveFactoryInitializeResult(String tid, int result, String bzhh, String sbyy) throws Exception;

    List<Eri> fetchByCondition(Map condition, PageBounds pageBounds) throws Exception;

    List<Eri> fetchByCondition(Map condition) throws Exception;

    List<Eri> fetchByRegion(String qskh, String zzkh) throws Exception;

    boolean verifyIdentity(String id, String sign);

    String generateUrlByKh(String kh) throws Exception;

    void update(Eri eri) throws Exception;
    
    List<Eri> queryByCkdh(String ckdh) throws Exception;
    
    void warehouse(Eri eri) throws Exception;

    Eri fetchByVehicle(String fzjg, String hphm, String hpzl) throws Exception;
    Eri fetchByVehicle(VehicleInfo vehicle) throws Exception;

    boolean updateByCondition(Map<Object, Object> condition) throws Exception;

    User0Data parseUser0Data(String data) throws Exception;

    Eri fetchByJdcxh(String jdcxh) throws Exception;

    boolean checkCardStatus(String tid, VehicleInfo vehicle, int ywlx) throws Exception;

    Map generateCipherCodeForUpgrade(String tid, String certStr) throws Exception;

    Map generateCipherCodeForUpgrade(Eri eri, String certStr) throws Exception;

    EriCustomizeContent modifyPassword(String tid, String content, String certStr, String sourceFactor, String destFactor) throws Exception;

    void modifyPasswordResult(String tid, int result, String sbyy) throws Exception;

    Eri fetchByKh(String kh) throws Exception;

    boolean verifyCert(Cert cert) throws Exception;
    
    EriCustomizeContent cleanCustomize(String tid, String certStr) throws Exception;
    
    void resetVehicleInfo(int cardType, EriCustomizeContent eriCustomizeContent) throws Exception;
    
	void unbindEri(String tid);

}
