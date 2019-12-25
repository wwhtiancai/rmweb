package com.tmri.rfid.service;

import com.google.common.io.BaseEncoding;
import com.sansec.impl.util.Bytes;
import com.tmri.rfid.bean.Cert;
import com.tmri.rfid.exception.KMSException;
import com.tmri.rfid.util.BinaryUtil;
import com.tmri.rfid.util.CRC8;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.XmlHelper;
import com.tmri.rfid.webservice.SecretKeyManagerServiceStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 *wuweihong
 *2017-5-9
 */
public class TestThread  implements Runnable{
    private final static Logger LOG = LoggerFactory.getLogger(TestThread.class);
//    public static void main(String[] args) {
//        long t1 = System.currentTimeMillis();
//        for(int i =0;i<100;i++){
//        new Thread(new TestThread()).start();
//        }
//        long t2 = System.currentTimeMillis();
//        System.out.println("��ʼʱ�����=========" +t1);
//        System.out.println("����ʱ�����=========" +t2);
//        System.out.println("����ķ�ʱ��=========" +(t2-t1));
//    }
    @Override
    public void run() {

            try {
                Cert cert = EriUtil.parseCert("30820213308201B7A003020102020900A82176590FE13A20300C06082A811CCF5501837505003056310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249310F300D06035504030C06524649444341301E170D3136303530353136353830375A170D3336303530353136353830375A3065310B300906035504060C02434E310B300906035504080C024A53310B300906035504070C025758310D300B060355040A0C04544D5249310D300B060355040B0C04544D5249311E301C06035504030C1531484841303130303230303534335246494453414D3059301306072A8648CE3D020106082A811CCF5501822D0342000407AD4B6C4BA3E260755B4ACBAC9662B845D7908B77533E0A3EC932176F6074185C223A7CDF254D8F9E180D177C4B69F119EE01EB6B7525C134508DBCF7FF35C0A35D305B301F0603551D230418301680144C32B197D9331BC4A605C1C6E58B625BF0977658300C0603551D13040530030101FF300B0603551D0F040403020106301D0603551D0E041604144C32B197D9331BC4A605C1C6E58B625BF0977658300C06082A811CCF5501837505000348003045022079E7DC0726C52D14FB40ECBD53C7A980A51CC86943A3C273B07FBADE3CCBCADA022100C4E12885F50713BF683E524B9399FD9DCA3EC814411940130BC4913A73C46B8D");
                int protectedKeyIndex = 2,user0sbqEncryptAndDecryptKeyIndex = 9;
//            protectedKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfProtectKeyByKMS());
//            user0sbqEncryptAndDecryptKeyIndex = encryptorIndexService.fetch(configProperty.getEncryptorIdOfEncryptAndDecryptKeyByKMS());

                byte[] phBytes = Bytes.hex2bytes("1126011E"); //����Ϊ8λ16�����ַ���
                int versionType = phBytes[0] >> 4;      //0Ϊ���԰棬1Ϊ��ʽ��
                int version = (phBytes[0] & 0x0F - 1) % 4 + 1;   //���ŵ�һλΪ��Կ�汾�ţ���ǰ��ʹ�á�
                byte[] content = new byte[3];
                //����������Ϊ16���ֽڣ������ź�6λתΪ3���ֽڣ���������õ�16���ֽڵļ���������ȥ��
                for (int i = 0; i < 3; i++) {
                    content[i] = phBytes[3 - i];
                }
                int phNo = phBytes[2] & 0xFF;
                long t1 = System.currentTimeMillis();
                String protectKey = "";
//                URL endpointURL = new URL("http://192.168.10.236:8080/keyManager/services/secretKey");
//                SecretKeyManagerPortBindingStub service = new SecretKeyManagerPortBindingStub(endpointURL, new SecretKeyManagerServiceLocator());
                SecretKeyManagerServiceStub.CreateTmpProKey createTmpProKey = new SecretKeyManagerServiceStub.CreateTmpProKey();
                Map<String, String> randomSecretkeyMap = getSecretKeyMap();
                randomSecretkeyMap.put("mode", "1");
                randomSecretkeyMap.put("keytype", "PRO_KEY");
                randomSecretkeyMap.put("keyindex", String.valueOf(protectedKeyIndex));
                randomSecretkeyMap.put("keyversion", EriUtil.appendZero(String.valueOf(version), 2));
                randomSecretkeyMap.put("publickey", new BASE64Encoder().encode(Bytes.hex2bytes(cert.getPubKey())));
                createTmpProKey.setStrXml(XmlHelper.map2XML(randomSecretkeyMap, "Params", ""));
                Map resultMap = XmlHelper.xml2map(new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").createTmpProKey(createTmpProKey).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    LOG.debug("���ܵ��������Կ�ɹ�");
                    protectKey =   EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                } else {
                    LOG.error("���ܵ��������Կʧ��");
                    throw new KMSException("���ܵ��������Կ", resultMap);
                }
                protectKey = protectKey.substring(192) + protectKey.substring(0, 192);
                LOG.info("protectKey��" + protectKey);

                String tid = "E88200006C9E0859".substring(2, 14);
                byte[] tidBytes = Bytes.hex2bytes(tid);
                byte[] reverseTidBytes = new byte[tidBytes.length];
                for (int i = 0; i < tidBytes.length; i++) {
                    reverseTidBytes[tidBytes.length - 1 - i] = tidBytes[i];
                }
                String reverseTid = EriUtil.bytesToHex(reverseTidBytes);

//                String pmyAndDkmy = EriUtil.base64ToHex(kmsService.generatePMYAndDKMY(version, protectedKeyIndex,
//                        new BASE64Encoder().encode(Bytes.hex2bytes(EriUtil.appendZero(EriUtil.bytesToHex(content), 32))),
//                        new BASE64Encoder().encode(Bytes.hex2bytes(reverseTid)), 10));
                String pmyAndDkmy = "";
                Map<String, String> deriveTwiceAndExportKeyMap = getSecretKeyMap();
                deriveTwiceAndExportKeyMap.put("keytype1", "IDAUTH_ROOTKEY");
                deriveTwiceAndExportKeyMap.put("keyversion1", String.valueOf(version));
                deriveTwiceAndExportKeyMap.put("keytype2", "IDAUTH_ROOTKEY");
                deriveTwiceAndExportKeyMap.put("keyversion2", String.valueOf(version));
                deriveTwiceAndExportKeyMap.put("prokeyindex", String.valueOf(protectedKeyIndex));
                deriveTwiceAndExportKeyMap.put("export", "1");
                deriveTwiceAndExportKeyMap.put("derivenum", "1");
                deriveTwiceAndExportKeyMap.put("drivedatar1", new BASE64Encoder().encode(Bytes.hex2bytes(EriUtil.appendZero(EriUtil.bytesToHex(content), 32))));
                deriveTwiceAndExportKeyMap.put("drivedatar2", new BASE64Encoder().encode(Bytes.hex2bytes(reverseTid)));
                deriveTwiceAndExportKeyMap.put("position", String.valueOf(10));
                deriveTwiceAndExportKeyMap.put("publickey", "");
                SecretKeyManagerServiceStub.FetchTagAuthKeyForIssue fetchTagAuthKeyForIssue =
                        new SecretKeyManagerServiceStub.FetchTagAuthKeyForIssue();
                fetchTagAuthKeyForIssue.setStrXml(XmlHelper.map2XML(deriveTwiceAndExportKeyMap, "Params", ""));
                resultMap = XmlHelper.xml2map(new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagAuthKeyForIssue(fetchTagAuthKeyForIssue).get_return());

                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    pmyAndDkmy = ((Map)resultMap.get("Result")).get("Key").toString();
                    LOG.debug("��������Կ�͵�����Կ�ɹ���" + pmyAndDkmy);
                } else {
                    LOG.error("��������Կ�͵�����Կʧ��");
                    throw new KMSException("��������Կ�͵�����Կ", resultMap);
                }
                LOG.info("����Կ������Կ��" + pmyAndDkmy);
//            eriInitializeContent.setPmy(pmyAndDkmy.substring(0, 32));
//            eriInitializeContent.setDkmy(pmyAndDkmy.substring(32));
                Map<String, String> fetchTagKeyForIssueMap = getSecretKeyMap();
                fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
                fetchTagKeyForIssueMap.put("derivenum", "1");
                fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectedKeyIndex));
                fetchTagKeyForIssueMap.put("publickey", "");
                fetchTagKeyForIssueMap.put("export", "1");
                fetchTagKeyForIssueMap.put("keytype", "LOCK_PWD"); //��������
                fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
                fetchTagKeyForIssueMap.put("keypartition", "");
                fetchTagKeyForIssueMap.put("keyparam", "");
                SecretKeyManagerServiceStub.FetchTagKeyForIssue fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                 resultMap = XmlHelper.xml2map(
                         new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());

                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String sdkl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("������������ɹ�(���ܺ�)��" + sdkl);
                } else {
                    throw new KMSException("������������", resultMap);
                }
//                LOG.info("generateLockPassword��" + kmsService.generateLockPassword(version, reverseTid, protectedKeyIndex ));
                fetchTagKeyForIssueMap.clear();
                 fetchTagKeyForIssueMap = getSecretKeyMap();
                fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
                fetchTagKeyForIssueMap.put("derivenum", "1");
                fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectedKeyIndex));
                fetchTagKeyForIssueMap.put("publickey", "");
                fetchTagKeyForIssueMap.put("export", "1");
                fetchTagKeyForIssueMap.put("keytype", "KILL_PWD"); //��������
                fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
                fetchTagKeyForIssueMap.put("keypartition", "");
                fetchTagKeyForIssueMap.put("keyparam", "");
                fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                 resultMap = XmlHelper.xml2map(
                         new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());

                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String mhkl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����������ɹ�(���ܺ�)��" + mhkl);
                } else {
                    throw new KMSException("����������", resultMap);
                }
//                LOG.info("generateActivatePassword��" + kmsService.generateActivatePassword(version, reverseTid, protectedKeyIndex));
//            eriInitializeContent.setSdkl(kmsService.generateLockPassword(version, reverseTid, protectedKeyIndex ));
//            eriInitializeContent.setMhkl(kmsService.generateActivatePassword(version, reverseTid, protectedKeyIndex));
                //�����µ�USER0д����,2016-11-15�޸�ʹ��ȫ������Կ
//                LOG.info("U0xkl��" + kmsService.generateUserWritePassword(version, 0, reverseTid, protectedKeyIndex));
//                LOG.info("U1xkl��" + kmsService.generateUserWritePassword(version, 1, reverseTid, protectedKeyIndex));
//                LOG.info("U2xkl��" + kmsService.generateUserWritePassword(version, 2, reverseTid, protectedKeyIndex));
//                LOG.info("U3xkl��" + kmsService.generateUserWritePassword(version, 3, reverseTid, protectedKeyIndex));
//                LOG.info("U4xkl��" + kmsService.generateUserWritePassword(version, 4, reverseTid, protectedKeyIndex));
//                LOG.info("U5xkl��" + kmsService.generateUserWritePassword(version, 5, reverseTid, protectedKeyIndex));

                fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
                fetchTagKeyForIssueMap.clear();
                 fetchTagKeyForIssueMap = getSecretKeyMap();
                fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
                fetchTagKeyForIssueMap.put("derivenum", "1");
                fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectedKeyIndex));
                fetchTagKeyForIssueMap.put("publickey", "");
                fetchTagKeyForIssueMap.put("export", "1");
                fetchTagKeyForIssueMap.put("keyversion", EriUtil.appendZero(String.valueOf(version), 2));
                fetchTagKeyForIssueMap.put("keypartition", "");
                fetchTagKeyForIssueMap.put("keytype", true ? "WR_PWD" : "RD_PWD");
                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(0), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                 resultMap = XmlHelper.xml2map(
                         new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����USER" + 0 + "��" + (true ? "д" : "��" ) + "����ɹ�:" + kl);
                } else {
                    LOG.error("����USER" + 0 + "��" + (true ? "д" : "��" ) + "����ʧ��(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
                    throw new KMSException("�����û���д����(" + 0 + ")", resultMap);
                }

                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(1), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                resultMap = XmlHelper.xml2map(
                        new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����USER" + 1 + "��" + (true ? "д" : "��" ) + "����ɹ�:" + kl);
                } else {
                    LOG.error("����USER" + 1 + "��" + (true ? "д" : "��" ) + "����ʧ��(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
                    throw new KMSException("�����û���д����(" + 1 + ")", resultMap);
                }
                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(2), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                resultMap = XmlHelper.xml2map(
                        new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����USER" + 2 + "��" + (true ? "д" : "��" ) + "����ɹ�:" + kl);
                } else {
                    LOG.error("����USER" + 2 + "��" + (true ? "д" : "��" ) + "����ʧ��(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
                    throw new KMSException("�����û���д����(" + 2 + ")", resultMap);
                }
                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(3), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                resultMap = XmlHelper.xml2map(
                        new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����USER" + 3 + "��" + (true ? "д" : "��" ) + "����ɹ�:" + kl);
                } else {
                    LOG.error("����USER" + 3 + "��" + (true ? "д" : "��" ) + "����ʧ��(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
                    throw new KMSException("�����û���д����(" + 3 + ")", resultMap);
                }
                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(4), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                resultMap = XmlHelper.xml2map(
                        new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����USER" + 4 + "��" + (true ? "д" : "��" ) + "����ɹ�:" + kl);
                } else {
                    LOG.error("����USER" + 4 + "��" + (true ? "д" : "��" ) + "����ʧ��(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
                    throw new KMSException("�����û���д����(" + 4 + ")", resultMap);
                }
                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(5), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                resultMap = XmlHelper.xml2map(
                        new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
                    LOG.debug("����USER" + 5 + "��" + (true ? "д" : "��" ) + "����ɹ�:" + kl);
                } else {
                    LOG.error("����USER" + 5 + "��" + (true ? "д" : "��" ) + "����ʧ��(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
                    throw new KMSException("�����û���д����(" + 5 + ")", resultMap);
                }
//            eriInitializeContent.setU0xkl(kmsService.generateUserWritePassword(version, 0, reverseTid, protectedKeyIndex));
//            eriInitializeContent.setU1xkl(kmsService.generateUserWritePassword(version, 1, reverseTid, protectedKeyIndex));
//            eriInitializeContent.setU2xkl(kmsService.generateUserWritePassword(version, 2, reverseTid, protectedKeyIndex));
//            eriInitializeContent.setU3xkl(kmsService.generateUserWritePassword(version, 3, reverseTid, protectedKeyIndex));
//            eriInitializeContent.setU4xkl(kmsService.generateUserWritePassword(version, 4, reverseTid, protectedKeyIndex));
//            eriInitializeContent.setU5xkl(kmsService.generateUserWritePassword(version, 5, reverseTid, protectedKeyIndex));

                if (phNo > 1) {
//                    LOG.info("U1xkl��" + kmsService.generateUserWritePassword(version, 1, reverseTid, protectedKeyIndex));
//                    LOG.info("U3xkl��" + kmsService.generateUserWritePassword(version, 3, reverseTid, protectedKeyIndex));
//                    LOG.info("U4xkl��" + kmsService.generateUserWritePassword(version, 4, reverseTid, protectedKeyIndex));
//                    LOG.info("U5xkl��" + kmsService.generateUserWritePassword(version, 5, reverseTid, protectedKeyIndex));
//                eriInitializeContent.setU1dkl(kmsService.generateUserReadPassword(version, 1, reverseTid, protectedKeyIndex));
//                eriInitializeContent.setU3dkl(kmsService.generateUserReadPassword(version, 3, reverseTid, protectedKeyIndex));
//                eriInitializeContent.setU4dkl(kmsService.generateUserReadPassword(version, 4, reverseTid, protectedKeyIndex));
//                eriInitializeContent.setU5dkl(kmsService.generateUserReadPassword(version, 5, reverseTid, protectedKeyIndex));
                }
                fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
                fetchTagKeyForIssueMap.clear();
                 fetchTagKeyForIssueMap = getSecretKeyMap();
                fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
                fetchTagKeyForIssueMap.put("derivenum", "1");
                fetchTagKeyForIssueMap.put("export", "0");
                fetchTagKeyForIssueMap.put("publickey", "");
                fetchTagKeyForIssueMap.put("prokeyindex", "0");
                fetchTagKeyForIssueMap.put("keytype", "ENDE_KEY");
                fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
                fetchTagKeyForIssueMap.put("keyindex", String.valueOf(user0sbqEncryptAndDecryptKeyIndex));
                fetchTagKeyForIssueMap.put("keypartition", true ? "00" : "01");
                fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(0), 2));
                fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
                 resultMap = XmlHelper.xml2map(
                         new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());

                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    LOG.debug("��ɢUSER" + 0 + (true ? "��" : "��") + "�����ӽ�����Կ�ɹ�");
                } else {
                    LOG.error("��ɢUSER" + 0 + (true ? "��" : "��") + "�����ӽ�����Կʧ��");
                    throw new KMSException("��ɢ�û����ӽ�����Կ(" + 0 + "," + (true ? "�ϰ���" : "�°���") + ")", resultMap);
                }
//                kmsService.generateUserEncryptPassword(version, 0, true, reverseTid, user0sbqEncryptAndDecryptKeyIndex);

                //CFB���ܣ�ʹ��TIDΪ��ʼ����
                byte[] iv = new byte[16];
                for (int i = 0; i < 6; i++) {
                    iv[15 - i] = tidBytes[i];
                    iv[5 - i] = tidBytes[i];
                }
                LOG.debug("���ܿ��ŷ����ʼ������" + Bytes.bytes2hex(iv));

                byte[] khBytes = Bytes.hex2bytes("0AF98B0B");
                LOG.debug("���ܿ��ţ�" + Bytes.bytes2hex(khBytes));

//                String encryptHexCardNum = kmsService.CFBEncrypt(user0sbqEncryptAndDecryptKeyIndex, true, EriUtil.bytesToHex(iv), new BASE64Encoder().encode(khBytes));
                String encryptHexCardNum = "";
                Map<String, String> SM1CFBEncryptMap = getSecretKeyMap();
                SM1CFBEncryptMap.put("keytype", "ENDE_KEY");
                SM1CFBEncryptMap.put("keyindex", String.valueOf(user0sbqEncryptAndDecryptKeyIndex));
                SM1CFBEncryptMap.put("keypartition", true ? "00" : "01");
                SM1CFBEncryptMap.put("iv", EriUtil.bytesToHex(iv));
                SM1CFBEncryptMap.put("plaintext", new BASE64Encoder().encode(khBytes));
                SecretKeyManagerServiceStub.Sm1CFBEncrypt sm1CFBEncrypt = new SecretKeyManagerServiceStub.Sm1CFBEncrypt();
                sm1CFBEncrypt.setStrXml(XmlHelper.map2XML(SM1CFBEncryptMap, "Params", ""));
                 resultMap = XmlHelper.xml2map(
                         new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").sm1CFBEncrypt(sm1CFBEncrypt).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    encryptHexCardNum = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Cipher").toString());
                    LOG.info("CFB���ܣ�" + encryptHexCardNum);
                } else {
                    throw new KMSException("CFB����", resultMap);
                }
                LOG.debug("���ܿ�������(ʹ��CFB���ܽӿ�)��" + encryptHexCardNum);
                int checkNum = CRC8.calculate(encryptHexCardNum);
                String checkStr = Integer.toHexString(checkNum).toUpperCase();
                checkStr = BinaryUtil.coverBefore(checkStr, 2);
//            eriInitializeContent.setBkh(encryptHexCardNum + checkStr);

                content = Arrays.copyOf("320000002404".getBytes(), 32);
                String sign = "";
//                String sign = BaseEncoding.base32().encode(
//                        new BASE64Decoder().decodeBuffer(kmsService.SM2Sign(version, 1, "", new BASE64Encoder().encode(content))))
//                        .replaceAll("=", "");
//                LOG.debug("ǩ����ά�룺" + sign);
                Map<String, String> SM2PrivateKeySignatureMap = getSecretKeyMap();
                SM2PrivateKeySignatureMap.put("level", String.valueOf(1));
                SM2PrivateKeySignatureMap.put("areaCode", "");
                SM2PrivateKeySignatureMap.put("data", new BASE64Encoder().encode(content));
                SM2PrivateKeySignatureMap.put("version",  String.valueOf(version));
                SecretKeyManagerServiceStub.Sm2Sign sm2Sign = new SecretKeyManagerServiceStub.Sm2Sign();
                sm2Sign.setStrXml(XmlHelper.map2XML(SM2PrivateKeySignatureMap, "Params", ""));
                 resultMap = XmlHelper.xml2map(new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").sm2Sign(sm2Sign).get_return());

                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
//                    sign = ((Map)resultMap.get("Result")).get("Signature").toString();
                    sign = BaseEncoding.base32().encode(
                            new BASE64Decoder().decodeBuffer(((Map)resultMap.get("Result")).get("Signature").toString()))
                            .replaceAll("=", "");
                    LOG.debug("ǩ���ɹ���" + sign);
                } else {
                    LOG.error("ǩ��ʧ��");
                    throw new KMSException("ǩ��", resultMap);
                }
//            eriInitializeContent.setTm(eriInitializeContent.getTm() + sign);

//            composeInitializeDateFrame(eriInitializeContent);
//            LOG.debug("����֡Ϊ��" + eriInitializeContent.getFrame());

                byte[] frameData = Bytes.hex2bytes("1234567891234");
                if (frameData.length % 2 > 0) {
                    LOG.error("����������Ϊ����");
                    throw new RuntimeException("����������Ϊ����");
                }

//                String summary = kmsService.SM3Digest(new BASE64Encoder().encode(frameData));
                String summary = "";
                SecretKeyManagerServiceStub.Sm3Digest sm3Digest = new SecretKeyManagerServiceStub.Sm3Digest();
                randomSecretkeyMap.clear();
                randomSecretkeyMap = getSecretKeyMap();
                randomSecretkeyMap.put("mode", "0");
                randomSecretkeyMap.put("useridlen", "0");
                randomSecretkeyMap.put("publickey", "");
                randomSecretkeyMap.put("userid", "");
                randomSecretkeyMap.put("subdata", "");
                randomSecretkeyMap.put("data", new BASE64Encoder().encode(frameData));
                sm3Digest.setStrXml(XmlHelper.map2XML(randomSecretkeyMap, "Params", ""));
                resultMap = XmlHelper.xml2map(new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").sm3Digest(sm3Digest).get_return());
                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    LOG.debug("SM3ժҪ�ɹ�");
                    summary = ((Map)resultMap.get("Result")).get("Digest").toString();
                } else {
                    LOG.error("SM3ժҪʧ��");
                    throw new KMSException("����ժҪ", resultMap);
                }
                SM2PrivateKeySignatureMap.clear();
                SM2PrivateKeySignatureMap = getSecretKeyMap();
                SM2PrivateKeySignatureMap.put("level", String.valueOf(1));
                SM2PrivateKeySignatureMap.put("areaCode", "");
                SM2PrivateKeySignatureMap.put("data", summary);
                SM2PrivateKeySignatureMap.put("version",  String.valueOf(version));
                sm2Sign = new SecretKeyManagerServiceStub.Sm2Sign();
                sm2Sign.setStrXml(XmlHelper.map2XML(SM2PrivateKeySignatureMap, "Params", ""));
                resultMap = XmlHelper.xml2map(new SecretKeyManagerServiceStub("http://10.2.44.245:8080/keyManager/services/secretKey").sm2Sign(sm2Sign).get_return());

                if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
                    sign = ((Map)resultMap.get("Result")).get("Signature").toString();
                    LOG.debug("ǩ���ɹ���" + sign);
//                    return sign;
                } else {
                    LOG.error("ǩ��ʧ��");
                    throw new KMSException("ǩ��", resultMap);
                }
//                LOG.error("����������Ϊ����" + EriUtil.base64ToHex(kmsService.SM2Sign(version, 1, "", summary)));
//            eriInitializeContent.setSign(EriUtil.base64ToHex(kmsService.SM2Sign(version, 1, "", summary)));
                long t2 = System.currentTimeMillis();
                System.out.println("��ʼʱ��=========" +t1);
                System.out.println("����ʱ��=========" +t2);
                System.out.println("�ķ�ʱ��=========" +(t2-t1));
            }  catch (Exception e) {
                LOG.error( e.getClass().getName() + ": " + e.getMessage() );
            }
    }
    private Map<String, String> getSecretKeyMap() {
        Map<String, String> randomSecretkeyMap = new HashMap<String, String>();
        randomSecretkeyMap.put("useraccount", "admin");
        randomSecretkeyMap.put("password", "123456");
        return randomSecretkeyMap;
    }

}
