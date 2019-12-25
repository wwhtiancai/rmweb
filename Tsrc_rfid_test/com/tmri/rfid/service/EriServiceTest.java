package com.tmri.rfid.service;

import com.google.common.io.BaseEncoding;
import com.sansec.hsm.saf.SAFAPI;
import com.sansec.hsm.saf.SAFCrypto;
import com.sansec.hsm.saf.SAFResult;
import com.sansec.impl.device.DeviceFactory;
import com.sansec.impl.util.Bytes;
import com.tmri.rfid.bean.Cert;
import com.tmri.rfid.bean.Eri;
import com.tmri.rfid.bean.EriCustomizeContent;
import com.tmri.rfid.bean.EriInitializeContent;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.mapper.EriMapper;
import com.tmri.rfid.property.ConfigProperty;
import com.tmri.rfid.util.*;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joey on 2015/11/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class EriServiceTest extends TestCase{

    private final static Logger LOG = LoggerFactory.getLogger(EriServiceTest.class);

    @Resource
    private EriService eriService;

    @Autowired
    private EriMapper eriMapper;

    @Resource
    private EncryptorIndexService encryptorIndexService;

    @Resource
    private ConfigProperty configProperty;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DeviceFactory.initDevice();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGenerateCipherCodeForUpgrade() {
        try {
            Map resultMap = new HashMap();
            String[] khs = {
                    "320000000002",
                    "320000000003",
                    "320000000004",
                    "320000000006",
                    "320000000007",
                    "320000000008",
                    "320000000009",
                    "320000000010",
                    "320000000011",
                    "320000000012",
                    "320000000013",
                    "320000000015",
                    "320000000016",
                    "320000000017",
                    "320000000018",
                    "320000000019",
                    "320000000020",
                    "320000000021",
                    "320000000022",
                    "320000000023",
                    "320000000024",
                    "320000000025",
                    "320000000026",
                    "320000000027",
                    "320000000028",
                    "320000000029",
                    "320000000030",
                    "320000000031",
                    "320000000032",
                    "320000000033",
                    "320000000034",
                    "320000000041",
                    "320000000042",
                    "320000000043",
                    "320000000044",
                    "320000000046",
                    "320000000047",
                    "320000000048",
                    "320000000049",
                    "320000000050",
                    "320000000051",
                    "320000000052",
                    "320000000053",
                    "320000000054",
                    "320000000055",
                    "320000000056",
                    "320000000057",
                    "320000000058",
                    "320000000059",
                    "320000000060",
                    "320000000061",
                    "320000000062",
                    "320000000063",
                    "320000000064",
                    "320000000065",
                    "320000000066",
                    "320000000067",
                    "320000000068",
                    "320000000069",
                    "320000000070",
                    "320000000071",
                    "320000000072",
                    "320000000073",
                    "320000000074",
                    "320000000075",
                    "320000000076",
                    "320000000077",
                    "320000000078",
                    "320000000082",
                    "320000000083",
                    "320000000084",
                    "320000000086",
                    "320000000088",
                    "320000000089",
                    "320000000090",
                    "320000000091",
                    "320000000092",
                    "320000000093",
                    "320000000094",
                    "320000000096",
                    "320000000097",
                    "320000000098",
                    "320000000099",
                    "320000000100",
                    "320000000101",
                    "320000000102",
                    "320000000103",
                    "320000000104",
                    "320000000105",
                    "320000000106",
                    "320000000107",
                    "320000000108",
                    "320000000109",
                    "320000000110",
                    "320000000111",
                    "320000000112",
                    "320000000113",
                    "320000000114",
                    "320000000115",
                    "320000000116",
                    "320000000117",
                    "320000000118",
                    "320000000119",
                    "320000000120",
                    "320000000121",
                    "320000000122",
                    "320000000123",
                    "320000000124",
                    "320000000125",
                    "320000000126",
                    "320000000127",
                    "320000000128",
                    "320000000129",
                    "320000000130",
                    "320000000131",
                    "320000000132",
                    "320000000133",
                    "320000000134",
                    "320000000135",
                    "320000000136",
                    "320000000137",
                    "320000000138",
                    "320000000139",
                    "320000000140",
                    "320000000141",
                    "320000000142",
                    "320000000143",
                    "320000000144",
                    "320000000145",
                    "320000000146",
                    "320000000147",
                    "320000000148",
                    "320000000149",
                    "320000000150",
                    "320000000151",
                    "320000000152",
                    "320000000153",
                    "320000000154",
                    "320000000155",
                    "320000000156",
                    "320000000157",
                    "320000000158",
                    "320000000159",
                    "320000000160",
                    "320000000161",
                    "320000000162",
                    "320000000163",
                    "320000000164",
                    "320000000165",
                    "320000000166",
                    "320000000167",
                    "320000000168",
                    "320000000169",
                    "320000000170",
                    "320000000171",
                    "320000000172",
                    "320000000173",
                    "320000000174",
                    "320000000175",
                    "320000000176",
                    "320000000177",
                    "320000000178",
                    "320000000179",
                    "320000000180",
                    "320000000181",
                    "320000000182",
                    "320000000183",
                    "320000000184",
                    "320000000185",
                    "320000000186",
                    "320000000187",
                    "320000000188",
                    "320000000189",
                    "320000000190",
                    "320000000191",
                    "320000000192",
                    "320000000193",
                    "320000000194",
                    "320000000235",

            };
            for (int i = 0; i < khs.length; i++) {
                String kh = khs[i];
                List<Eri> eriList = eriMapper.queryByCondition(MapUtilities.buildMap("kh", kh, "zt", 1, "sf", "32"));
                if (eriList != null && !eriList.isEmpty()) {
                    String tid = eriList.get(0).getTid();
                    Map map = eriService.generateCipherCodeForUpgrade(tid,
                            "30820213308201B7A003020102020900A82176590FE13A20300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303530353136353830375A170D3336303530353136353830375A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531484841303130303230303534335246494453414D3059301306072A8648CE3D020106082A811CCF5501822D0342000407AD4B6C4BA3E260755B4ACBAC9662B845D7908B77533E0A3EC932176F6074185C223A7CDF254D8F9E180D177C4B69F119EE01EB6B7525C134508DBCF7FF35C0A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000348003045022079E7DC0726C52D14FB40ECBD53C7A980A51CC86943A3C273B07FBADE3CCBCADA022100C4E12885F50713BF683E524B9399FD9DCA3EC814411940130BC4913A73C46B8D");
                    Map subMap = new HashMap();
                    subMap.put("frame", map.get("frame"));
                    subMap.put("sign", map.get("sign"));
                    resultMap.put(tid, subMap);
                }
            }
            String result = GsonHelper.getGson().toJson(resultMap);
            LOG.info("数据内容为:" + result);
        } catch (OperationException oe) {
            LOG.error("异常", oe);
        } catch (Exception e) {
            LOG.error("系统异常", e);
        }
    }

    @Test
    public void testGenerateCipherCodeForCustomize() throws Exception {
        try {
            EriCustomizeContent eriCustomizeContent = new EriCustomizeContent();
            eriCustomizeContent.setTid("");
            eriCustomizeContent.setKh("");
        } catch (Exception e) {
            LOG.error("异常", e);
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerateCipherCode() throws Exception {
        try {
//            eriService.initialize("E8830000F47E3C5A", "32",
//                "30820212308201B6A00302010202087F4B5CBF89EE21CC300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303531323135353532345A170D3336303531323135353532345A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531444441303230303230303535345246494453414D3059301306072A8648CE3D020106082A811CCF5501822D03420004C4CC9B91F5F8F2C083E2086A0E4E1CE3B2C07963E18B4A18C8A64EB95EB0FCB23EF0BDA53C297A952DB48E452D9F4C678F7E0E360852FDDA837F11DA2A8CEAC4A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000348003045022100804E46DDD9711A5F87B19DDEE3BB0BC9AE512B0D622B8617FD101B824B6BA50E02205666E08B1CA06CAF1291673AEEA05302249F3FCD5F22BD9B1B87686F65CC62BD",
//                false);

//            eriService.initialize("E881000AA8545C4B", "32",
//                    "30820213308201B6A00302010202082B1F11E07E8A0765300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3137313031323039353134355A170D3337313031323039353134355A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531525241303230303330313132315246494453414D3059301306072A8648CE3D020106082A811CCF5501822D0342000468BEB766CECD2B60B109B7AFCC138A8F433C25A83B2C8FB6EA8CD8F3C084DC3DCDA103BE5CE75B87692A3D6D55270A7CF61D0681B13C9AF87CE9B81268E57BEFA35D305B301F0603551D230418301680142B6A0FC25AB98EF6B7CEA448EB4309DFF4705BAC300C0603551D1304053003010100300B0603551D0F040403020186301D0603551D0E041604140891083288C50F0137E5A3461F0DAEDF71533B62300C06082A811CCF5501837505000349003046022100E14EFAE23FEA52562FB6B43834C9E976472DEBEFCF4516F8D937027C621AA7F1022100D2B2E83E5081F72B8EBD780B5CA53DEF8BD62D988A975C8920AB41318083EF3C",
//                    false);
            eriService.initialize("E8830000F47E3C5A", "32",
                    "30820212308201B6A00302010202086D0EC866CA497C09300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303531323135343630315A170D3336303531323135343630315A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531444441303230303230303535315246494453414D3059301306072A8648CE3D020106082A811CCF5501822D03420004DE1A796A17E5BB244513C0E3298BF637F516D4233D8AE712D21BE764E0049A67380F61E0F27CA92A171AC9CD2036EA997D3775C775B9657C1D5920C61511F40AA35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF55018375050003480030450221009C698FD6C23FC392A25D646A59A37764650926D53DD90E1A16BEBAC0D032F70B0220694C7E221E8E2F0CE81FAE5F676663DEDABB2B3B7593F1270DA3F4132349C3D7",
                    false);

        } catch (Exception e) {
            LOG.error("异常", e);
            e.printStackTrace();
        }
    }

    @Test
    public void testVerifyCert() throws Exception {
        eriService.verifyCert(EriUtil.parseCert("30820202308201A6A003020102020900C99B7D0278C3B535300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3135313232333039353734385A170D3335313232333039353734385A3054310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310C300A060355040A0C03414243310C300A060355040B0C03414243310F300D06035504030C063343414142433059301306072A8648CE3D020106082A811CCF5501822D03420004D661AB9EDEF7BE4B3BB931F832231DB0C68ABDE47E0220D26220CE516C32CABCBDE3DCE747FB5FB379EB2A42EEBE6BA6EF1CCA0B2BDB6ACEDB97BFB92CD07F34A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000348003045022100EFA8B2ED0573330FD43397E9744A1FFFEA840AB5719C2FFDAF5662FFF558528E022072614918C13A1DA5689DF963D6F5C358F648E19F7B9B5BCE081F9B1648DC2248"));
    }

    @Test
    public void testUser0WriteKey() {
        int protectedKeyIndex = -1, encryptAndDecryptKeyIndex = -1, u0XklIndex = -1;
        try {
            encryptAndDecryptKeyIndex = 101;
            protectedKeyIndex = 102;
            SAFAPI safapi = SAFCrypto.getInstance();
            String districtCode = "4403";
            SAFResult safResult = safapi.SAF_Div_SM1AndSM4(3, 1, 21, 1,
                    EriUtil.appendZero(districtCode, 32, 2).getBytes(), 0, null, encryptAndDecryptKeyIndex);
            if (safResult.getResultCode() != 0) {
                throw new RuntimeException("分散写口令根密钥失败");
            }

            String tid = "E8820000C26208AE".substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);
            byte[] reverseTidAfterAppendZeroBytes = EriUtil.appendZero(reverseTid, 32).getBytes();

            //生成新的USER0写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, encryptAndDecryptKeyIndex, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER0区新写口令成功" + new String(safResult.getData()));
            } else {
                LOG.error("生成USER0区新写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区新写口令失败");
            }

            //生成新的USER0写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 37, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER0区新写口令成功" + new String(safResult.getData()));
            } else {
                LOG.error("生成USER0区新写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER0区新写口令失败");
            }

        } catch (Exception e) {
            LOG.error("异常", e);
        }
    }

    @Test
    public void testUser1Write() throws Exception{
        EriCustomizeContent eriCustomizeContent = new EriCustomizeContent();
        eriCustomizeContent.setTid("E8820000589008C0");
        eriCustomizeContent.setKh("320000002404");
        eriCustomizeContent.setPh("111502D2");
        eriCustomizeContent.setZt(1);
        eriCustomizeContent.setSf("32");
        String totalOut = "";
        Cert cert = EriUtil.parseCert("30820211308201B6A0030201020208272D55AAC201D6BA300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303531323135343834325A170D3336303531323135343834325A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531444441303230303230303535325246494453414D3059301306072A8648CE3D020106082A811CCF5501822D034200048444E6699CE968BFF5B179669C87092689967750B4140D7F02FCF3158797A041524A36C616425DBE675D11BEBCD85915F4462B149EF68B558E541094207A9F90A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF550183750500034700304402207DD0DD6F99D754AEB3CBC80866A4C4CEADD6F654E052BB2138BFDBED77D6E124022020F8F60928D71EA1AB4CFA08061D917C2E74D3848321051B1BEE75DFEEF6C963");
        for (int i = 1; i <= 5; i++) {
            generate(eriCustomizeContent, cert, i);
            String result = eriCustomizeContent.getFrame() + eriCustomizeContent.getSign();
            String finalResult = "";
            for (int j = 0; j < result.length(); j += 2) {
                finalResult = finalResult + result.substring(j, j + 2) + " ";
            }
            totalOut = totalOut + "V3 (User" + i + ") : " + finalResult + "\r\n";
        }
        System.out.println(totalOut);
    }

    private void generate(EriCustomizeContent eriCustomizeContent, Cert cert, int index) throws Exception {
        int protectedKeyIndex = -1, encryptAndDecryptKeyIndex = -1, u0XklIndex = -1;
        try {
            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectedKey());
            encryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKey());
            byte[] phBytes = Bytes.hex2bytes(eriCustomizeContent.getPh());
            int versionType = phBytes[0] >> 4;      //0为测试版，1为正式版
            int version = phBytes[0] & 0x0F - 1;   //批号第一位为密钥版本号，当前不使用。
            boolean flag = (phBytes[1] & 0x01) > 0;
            //生成随机密钥，用于进行密钥导出
            SAFAPI safapi = SAFCrypto.getInstance();
            SAFResult safResult = safapi.SAF_GenKey_SM1AndSM4(1, protectedKeyIndex, 0, null, null, null, null, null, null, null, null, null);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成随机密钥成功");
            } else {
                LOG.error("生成随机密钥失败");
                throw new RuntimeException("生成随机密钥失败");
            }

            safResult = safapi.SAF_TransKey_LMK2SM2(protectedKeyIndex, 0, Bytes.hex2bytes(cert.getPubKey()));
            if (safResult.getResultCode() == 0) {
                LOG.debug("加密导出随机密钥成功");
            } else {
                LOG.error("加密导出随机密钥失败");
                throw new RuntimeException("加密导出随机密钥失败");
            }

            String protectKey = EriUtil.bytesToHex(safResult.getData());
            protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
            eriCustomizeContent.setProtectKey(protectKey);

            String tid = eriCustomizeContent.getTid().substring(2, 14);
            byte[] tidBytes = Bytes.hex2bytes(tid);
            byte[] reverseTidBytes = new byte[tidBytes.length];
            for (int i = 0; i < tidBytes.length; i++) {
                reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
            }
            String reverseTid = EriUtil.bytesToHex(reverseTidBytes);
            byte[] reverseTidAfterAppendZeroBytes = EriUtil.appendZero(reverseTid, 32).getBytes();

            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 13 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setSdkl(new String(safResult.getData()));
                LOG.debug("生成锁定口令成功(加密后)：" + eriCustomizeContent.getSdkl());
            } else {
                LOG.error("生成锁定口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成锁定口令失败");
            }

            //USER1分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 45 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER1区写口令成功：" + new String(safResult.getData()));
                eriCustomizeContent.setU1xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER1区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER1区写口令失败");
            }


            //USER2分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 53 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER2区写口令成功：" + new String(safResult.getData()));
                eriCustomizeContent.setU2xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER2区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER2区写口令失败");
            }
            //USER3分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 61 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER3区写口令成功：" + new String(safResult.getData()));
                eriCustomizeContent.setU3xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER3区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER3区写口令失败");
            }
            //USER4分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 69 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER4区写口令成功：" + new String(safResult.getData()));
                eriCustomizeContent.setU4xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER4区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER4区写口令失败");
            }
            //USER5分区写口令
            safResult = safapi.SAF_Div_SM1AndSM4(2, 1, 77 + version, 1, reverseTidAfterAppendZeroBytes, protectedKeyIndex, null, 0);
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成USER5区写口令成功：" + new String(safResult.getData()));
                eriCustomizeContent.setU5xkl(new String(safResult.getData()));
            } else {
                LOG.debug("生成USER5区写口令失败(" + safResult.getResultCode() + ")");
                throw new RuntimeException("生成USER5区写口令失败");
            }

            composeCustomizeDataFrame(eriCustomizeContent, index);
            LOG.debug("数据帧为：" + eriCustomizeContent.getFrame());

            byte[] frameData = Bytes.hex2bytes(eriCustomizeContent.getFrame());
            if (frameData.length % 2 > 0) {
                LOG.error("数据区长度为奇数");
                throw new RuntimeException("数据区长度为奇数");
            }
            safResult = safapi.SAF_SM3_HASH(1, 0, null, null, frameData.length, frameData);
            byte[] summary = safResult.getData();
            if (safResult.getResultCode() == 0) {
                LOG.debug("生成数据帧摘要成功：" + EriUtil.bytesToHex(summary));
            } else {
                LOG.error("生成证书摘要失败");
                throw new RuntimeException("生成证书摘要失败");
            }

            safResult = safapi.SAF_SM2PriKeySign(10 + version, summary.length, summary);
            if (safResult.getResultCode() == 0) {
                eriCustomizeContent.setSign(EriUtil.bytesToHex(safResult.getData()));
                LOG.debug("对个性化数据进行签名成功,签名为：" + eriCustomizeContent.getSign());
            } else {
                LOG.error("对个性化数据进行签名失败");
                throw new RuntimeException("对个性化数据进行签名失败");
            }
        } finally {
            if (protectedKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfProtectedKey(), protectedKeyIndex);
            }
            if (encryptAndDecryptKeyIndex > 0) {
                encryptorIndexService.release(configProperty.getEncryptorIdOfEncryptAndDecryptKey(), encryptAndDecryptKeyIndex);
            }
        }
    }

    private void composeCustomizeDataFrame(EriCustomizeContent eriCustomizeContent,int index) {
        String data = "0200";   //数据格式版本02，数据域标识00
        data += eriCustomizeContent.getProtectKey();
        data += eriCustomizeContent.getTid();
        data += eriCustomizeContent.getSdkl();
        if (index == 1) {
            data += eriCustomizeContent.getU1xkl();
            data += eriCustomizeContent.getU1xkl();
        } else if (index == 2) {
            data += eriCustomizeContent.getU2xkl();
            data += eriCustomizeContent.getU2xkl();
        } else if (index == 3) {
            data += eriCustomizeContent.getU3xkl();
            data += eriCustomizeContent.getU3xkl();
        } else if (index == 4) {
            data += eriCustomizeContent.getU4xkl();
            data += eriCustomizeContent.getU4xkl();
        } else if (index == 5) {
            data += eriCustomizeContent.getU5xkl();
            data += eriCustomizeContent.getU5xkl();
        }
        data += "5A"; //分区数据分隔符
        data += "01"; //写分区数
        if (index == 1) {
            //user1
            data += "310409"; //User1头
            data += "010203040506070809010203040506070809";
        } else if (index == 2) {
            //user2
            data += "32040A"; //user2头
            data += "0102030405060708090102030405060708090202";
        } else if (index == 3) {
            //user3
            data += "330409"; //user3头
            data += "010203040506070809010203040506070803";
        } else if (index == 4) {
            //user4
            data += "340409"; //user4头
            data += "010203040506070809010203040506070804";
        } else if (index == 5) {
            //user5
            data += "35040F"; //user5头
            data += "000102030405060708090A0B0C0D0E0F000102030405060708090A0B0C0D0E0F";
        }
        if (data.length() / 2 % 2 != 0) {
            data += "00"; //由于数据区长度此时为奇数，为保证读写器能按字正常传输，需要补0，保证数据区长度为偶数。
        }
        eriCustomizeContent.setFrame(data);
    }

    public static void main(String args[]) throws Exception {
        System.out.println(EriUtil.base64ToHex("lNHmKca4GWtsmpwa6tvHVwOsZpRzFMt9xsC/JYvC7i4="));
    }


}
