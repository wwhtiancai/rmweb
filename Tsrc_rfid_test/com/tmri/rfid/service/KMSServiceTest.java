package com.tmri.rfid.service;

import com.sansec.hsm.saf.SAFCrypto;
import com.sansec.hsm.saf.SAFResult;
import com.sansec.impl.util.Bytes;
import com.tmri.rfid.bean.Cert;
import com.tmri.rfid.mapper.EriMapper;
import com.tmri.rfid.property.ConfigProperty;
import com.tmri.rfid.util.EriUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;

/**
 * Created by Joey on 2017/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class KMSServiceTest {

    private final static Logger LOG = LoggerFactory.getLogger(KMSServiceTest.class);

    @Resource
    private ConfigProperty configProperty;

    @Resource
    private KMSService kmsService;

    @Resource
    private EncryptorIndexService encryptorIndexService;

    @Test
    public void testCreateTmpKey() throws Exception {
        Cert cert = EriUtil.parseCert("30820213308201B7A003020102020900A82176590FE13A20300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303530353136353830375A170D3336303530353136353830375A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531484841303130303230303534335246494453414D3059301306072A8648CE3D020106082A811CCF5501822D0342000407AD4B6C4BA3E260755B4ACBAC9662B845D7908B77533E0A3EC932176F6074185C223A7CDF254D8F9E180D177C4B69F119EE01EB6B7525C134508DBCF7FF35C0A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000348003045022079E7DC0726C52D14FB40ECBD53C7A980A51CC86943A3C273B07FBADE3CCBCADA022100C4E12885F50713BF683E524B9399FD9DCA3EC814411940130BC4913A73C46B8D");
        int protectedKeyIndex = -1,user0sbqEncryptAndDecryptKeyIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectKeyByKMS());
            String protectKey = kmsService.createTmpProKey(1,
                    new BASE64Encoder().encode(Bytes.hex2bytes(cert.getPubKey())), protectedKeyIndex);
        } catch (Exception e) {
            LOG.error("异常", e);
        } finally {
            if (protectedKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectKeyByKMS(), protectedKeyIndex);
            }
            if (user0sbqEncryptAndDecryptKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS(), user0sbqEncryptAndDecryptKeyIndex);
            }
        }
    }

    @Test
    public void testGeneratePMYAndDKMY() throws Exception{
        Cert cert = EriUtil.parseCert("30820213308201B7A003020102020900A82176590FE13A20300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303530353136353830375A170D3336303530353136353830375A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531484841303130303230303534335246494453414D3059301306072A8648CE3D020106082A811CCF5501822D0342000407AD4B6C4BA3E260755B4ACBAC9662B845D7908B77533E0A3EC932176F6074185C223A7CDF254D8F9E180D177C4B69F119EE01EB6B7525C134508DBCF7FF35C0A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000348003045022079E7DC0726C52D14FB40ECBD53C7A980A51CC86943A3C273B07FBADE3CCBCADA022100C4E12885F50713BF683E524B9399FD9DCA3EC814411940130BC4913A73C46B8D");
        int protectedKeyIndex = -1,user0sbqEncryptAndDecryptKeyIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectKeyByKMS());
            user0sbqEncryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS());

            SAFCrypto safapi = SAFCrypto.getInstance();
            SAFResult safResult;

            byte[] phBytes = Bytes.hex2bytes("1126011E"); //批号为8位16进制字符串
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = (phBytes[0] & 0x0F - 1) % 4 + 1;   //批号第一位为密钥版本号，当前不使用。
            byte[] content = new byte[16];
            //加密内容需为16个字节，将批号后6位转为3个字节，并倒序放置到16个字节的加密内容中去。
            for (int i = 0; i < 3; i++) {
                content[13 + i] = phBytes[3 - i];
            }
            int phNo = phBytes[2] & 0xFF;

            String protectKey = kmsService.createTmpProKey(version,
                    new BASE64Encoder().encode(Bytes.hex2bytes(cert.getPubKey())), protectedKeyIndex);
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);

            String tid = "E88200006C9E0859".substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);

            String pmyAndDkmyBase64 = kmsService.generatePMYAndDKMY(version, protectedKeyIndex,
                    new BASE64Encoder().encode(content),
                    new BASE64Encoder().encode(reverseTidBytes), 10);
            String pmyAndDkmyHex = new String(new BASE64Decoder().decodeBuffer(pmyAndDkmyBase64));
            LOG.info("结果：" + pmyAndDkmyHex);
        } catch(Exception e) {
            LOG.error("异常", e);
        } finally {
            if (protectedKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectKeyByKMS(), protectedKeyIndex);
            }
            if (user0sbqEncryptAndDecryptKeyIndex >= 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS(), user0sbqEncryptAndDecryptKeyIndex);
            }
        }
    }

}
