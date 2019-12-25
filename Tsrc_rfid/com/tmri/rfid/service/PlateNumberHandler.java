package com.tmri.rfid.service;

/**
 * Created by Joey on 2015/9/24.
 */
public interface PlateNumberHandler {

    /**
     * 根据发证机关、号牌种类、号牌号码组合完整的车牌号码
     * @param fzjg 发证机关，如：苏B
     * @param hphm 号牌号码，如：B1234
     * @return 车牌号码，如：苏B1234警
     * @throws Exception
     */
    String compose(String fzjg, String hphm) throws Exception;

    /**
     * 根据完整的车牌号获取号牌序号，当电子标识个性化操作时，需要计算号牌号码的数值
     * @param cphm 完整车牌号码，如：苏B1234警
     * @return 号牌号码，如：1234
     * @throws Exception
     */
    String getHpxh(String cphm) throws Exception;



}
