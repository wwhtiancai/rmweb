package com.tmri.rfid.service.impl;

import com.sansec.impl.util.Bytes;
import com.tmri.rfid.exception.KMSException;
import com.tmri.rfid.exception.OperationException;
import com.tmri.rfid.service.KMSService;
import com.tmri.rfid.util.EriUtil;
import com.tmri.rfid.util.XmlHelper;
import com.tmri.rfid.webservice.SecretKeyManagerServiceStub;
import com.tmri.share.frm.service.impl.GSysparaCodeServiceImpl;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Joey on 2017/4/10.
 */
@Service
public class KMSServiceImpl implements KMSService {

    private final static Logger LOG = LoggerFactory.getLogger(KMSServiceImpl.class);

    @Resource
    private HttpClient httpClient;

    private List<SecretKeyManagerServiceStub> stubList;

    @Resource
    private GSysparaCodeServiceImpl gSysparaCodeService;

    private SecretKeyManagerServiceStub getStub() throws Exception {
        if (stubList == null) {
            reset(gSysparaCodeService.getSysParaValue("00", "JMFWDZ"));
        }
        if (stubList == null || stubList.isEmpty()) {
            throw new OperationException("无法获取密钥管理系统加解密服务");
        }
        return stubList.get(new Random().nextInt(stubList.size()));
    }

    public void reset(String addressList) throws Exception {
        stubList = new ArrayList<SecretKeyManagerServiceStub>();
        String[] addresses = addressList.split(";");
        for (String address : addresses) {
            stubList.add(new SecretKeyManagerServiceStub(address, httpClient));

        }
    }

    @Override
    public String SM3Digest(String data) throws Exception {
        SecretKeyManagerServiceStub.Sm3Digest sm3Digest = new SecretKeyManagerServiceStub.Sm3Digest();
        Map<String, String> randomSecretkeyMap = getSecretKeyMap();
        randomSecretkeyMap.put("mode", "0");
        randomSecretkeyMap.put("useridlen", "0");
        randomSecretkeyMap.put("publickey", "");
        randomSecretkeyMap.put("userid", "");
        randomSecretkeyMap.put("subdata", "");
        randomSecretkeyMap.put("data", data);
        sm3Digest.setStrXml(XmlHelper.map2XML(randomSecretkeyMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(getStub().sm3Digest(sm3Digest).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            LOG.debug("SM3摘要成功");
            return ((Map)resultMap.get("Result")).get("Digest").toString();
        } else {
            LOG.error("SM3摘要失败");
            throw new KMSException("生成摘要", resultMap);
        }
    }

    @Override
    public boolean SM2Verify(int version, String data, String signature) throws Exception {
        SecretKeyManagerServiceStub.Sm2Verify sm2Verify = new SecretKeyManagerServiceStub.Sm2Verify();
        Map<String, String> randomSecretkeyMap = getSecretKeyMap();
        randomSecretkeyMap.put("level", "0");
        randomSecretkeyMap.put("version",  String.valueOf(version));
        randomSecretkeyMap.put("data", data);
        randomSecretkeyMap.put("signature", signature);
        sm2Verify.setStrXml(XmlHelper.map2XML(randomSecretkeyMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(getStub().sm2Verify(sm2Verify).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            return true;
        } else {
            LOG.error("SM2验签失败");
            throw new KMSException("验签(科研所)", resultMap);
        }
    }

    @Override
    public boolean SM2Verify(String hexPubKey, String data, String signature) throws Exception {
        SecretKeyManagerServiceStub.Sm2Verify sm2Verify = new SecretKeyManagerServiceStub.Sm2Verify();
        Map<String, String> randomSecretkeyMap = getSecretKeyMap();
        randomSecretkeyMap.put("level", "2");
        randomSecretkeyMap.put("publickey",  new BASE64Encoder().encode(Bytes.hex2bytes(hexPubKey)));
        randomSecretkeyMap.put("data", data);
        randomSecretkeyMap.put("signature", signature);
        sm2Verify.setStrXml(XmlHelper.map2XML(randomSecretkeyMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(getStub().sm2Verify(sm2Verify).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            return true;
        } else {
            LOG.error("SM2验签失败");
            throw new KMSException("验签(地市)", resultMap);
        }
    }

    @Override
    public String createTmpProKey(int version, String pubKey, int protectedKeyIndex) throws Exception {
        SecretKeyManagerServiceStub.CreateTmpProKey createTmpProKey = new SecretKeyManagerServiceStub.CreateTmpProKey();
        Map<String, String> randomSecretkeyMap = getSecretKeyMap();
        randomSecretkeyMap.put("mode", "1");
        randomSecretkeyMap.put("keytype", "PRO_KEY");
        randomSecretkeyMap.put("keyindex", String.valueOf(protectedKeyIndex));
        randomSecretkeyMap.put("keyversion", EriUtil.appendZero(String.valueOf(version), 2));
        randomSecretkeyMap.put("publickey", pubKey);
        createTmpProKey.setStrXml(XmlHelper.map2XML(randomSecretkeyMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(getStub().createTmpProKey(createTmpProKey).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            LOG.debug("加密导出随机密钥成功");
            return EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
        } else {
            LOG.error("加密导出随机密钥失败");
            throw new KMSException("加密导出随机密钥", resultMap);
        }
    }

    private Map<String, String> getSecretKeyMap() {
        Map<String, String> randomSecretkeyMap = new HashMap<String, String>();
        randomSecretkeyMap.put("useraccount", "admin");
        randomSecretkeyMap.put("password", "123456");
        return randomSecretkeyMap;
    }

    @Override
    public String generateLockPassword(int version, String reverseTid, int protectedKeyIndex) throws Exception {
        Map<String, String> fetchTagKeyForIssueMap = getSecretKeyMap();
        fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
        fetchTagKeyForIssueMap.put("derivenum", "1");
        fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectedKeyIndex));
        fetchTagKeyForIssueMap.put("publickey", "");
        fetchTagKeyForIssueMap.put("export", "1");
        fetchTagKeyForIssueMap.put("keytype", "LOCK_PWD"); //锁定口令
        fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
        fetchTagKeyForIssueMap.put("keypartition", "");
        fetchTagKeyForIssueMap.put("keyparam", "");
        SecretKeyManagerServiceStub.FetchTagKeyForIssue fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
        fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(
                getStub().fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());

        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String sdkl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
            LOG.debug("生成锁定口令成功(加密后)：" + sdkl);
            return sdkl;
        } else {
            throw new KMSException("生成锁定口令", resultMap);
        }
    }

    @Override
    public String generateActivatePassword(int version, String reverseTid, int protectedKeyIndex) throws Exception {
        Map<String, String> fetchTagKeyForIssueMap = getSecretKeyMap();
        fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
        fetchTagKeyForIssueMap.put("derivenum", "1");
        fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectedKeyIndex));
        fetchTagKeyForIssueMap.put("publickey", "");
        fetchTagKeyForIssueMap.put("export", "1");
        fetchTagKeyForIssueMap.put("keytype", "KILL_PWD"); //锁定口令
        fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
        fetchTagKeyForIssueMap.put("keypartition", "");
        fetchTagKeyForIssueMap.put("keyparam", "");
        SecretKeyManagerServiceStub.FetchTagKeyForIssue fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
        fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(
                getStub().fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());

        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String mhkl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
            LOG.debug("生成灭活口令成功(加密后)：" + mhkl);
            return mhkl;
        } else {
            throw new KMSException("生成灭活口令", resultMap);
        }
    }

    /**
     *
     * @param version
     * @param partitionIndex
     * @param reverseTid
     * @param protectKeyIndex
     * @param write true-写口令，false-读口令
     * @return
     * @throws Exception
     */
    private String generateUserPassword(int version, int partitionIndex, String reverseTid, int protectKeyIndex, boolean write) throws Exception {
        SecretKeyManagerServiceStub.FetchTagKeyForIssue fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
        Map<String, String> fetchTagKeyForIssueMap = getSecretKeyMap();
        fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
        fetchTagKeyForIssueMap.put("derivenum", "1");
        fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectKeyIndex));
        fetchTagKeyForIssueMap.put("publickey", "");
        fetchTagKeyForIssueMap.put("export", "1");
        fetchTagKeyForIssueMap.put("keyversion", EriUtil.appendZero(String.valueOf(version), 2));
        fetchTagKeyForIssueMap.put("keypartition", "");
        fetchTagKeyForIssueMap.put("keytype", write ? "WR_PWD" : "RD_PWD");
        fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(partitionIndex), 2));
        fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(
                getStub().fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String kl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
            LOG.debug("生成USER" + partitionIndex + "区" + (write ? "写" : "读" ) + "口令成功:" + kl);
            return kl;
        } else {
            LOG.error("生成USER" + partitionIndex + "区" + (write ? "写" : "读" ) + "口令失败(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
            throw new KMSException("生成用户区写口令(" + partitionIndex + ")", resultMap);
        }
    }

    @Override
    public String generateUserWritePassword(int version, int partitionIndex, String reverseTid, int protectKeyIndex) throws Exception {
        return generateUserPassword(version, partitionIndex, reverseTid, protectKeyIndex, true);
    }

    @Override
    public String generateUserReadPassword(int version, int partitionIndex, String reverseTid, int protectKeyIndex) throws Exception {
        return generateUserPassword(version, partitionIndex, reverseTid, protectKeyIndex, false);
    }

    @Override
    public String generateUser0WritePasswordOld(int version, String reverseTid, int protectKeyIndex, boolean isCity) throws Exception {
        SecretKeyManagerServiceStub.FetchTagKeyForIssue fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
        Map<String, String> fetchTagKeyForIssueMap = getSecretKeyMap();
        fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
        fetchTagKeyForIssueMap.put("derivenum", "1");
        fetchTagKeyForIssueMap.put("prokeyindex", String.valueOf(protectKeyIndex));
        fetchTagKeyForIssueMap.put("publickey", "");
        fetchTagKeyForIssueMap.put("export", "1");
        fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
        fetchTagKeyForIssueMap.put("keypartition", "");
        fetchTagKeyForIssueMap.put("keytype", "WR_PWD");
        fetchTagKeyForIssueMap.put("keyparam", "00");
        fetchTagKeyForIssueMap.put("iscity", isCity ? "1" : "0");
        fetchTagKeyForIssueMap.put("oldmode", isCity ? "1" : "0");
        fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(
                getStub().fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String xkl = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Key").toString());
            LOG.debug("生成USER0区写口令成功" + xkl);
            return xkl;
        } else {
            LOG.error("生成USER0区写口令失败(" + ((Map)resultMap.get("Result")).get("ErrorCode") + ")");
            throw new KMSException("生成用户区写口令(" + (isCity ? "地市" : "全国") + ")", resultMap);
        }
    }

    @Override
    public boolean generateUserEncryptPassword(int version, int partitionIndex, boolean upper, String reverseTid, int encryptKeyIndex) throws Exception {
        SecretKeyManagerServiceStub.FetchTagKeyForIssue fetchTagKeyForIssue = new SecretKeyManagerServiceStub.FetchTagKeyForIssue();
        Map<String, String> fetchTagKeyForIssueMap = getSecretKeyMap();
        fetchTagKeyForIssueMap.put("uid", EriUtil.appendZero(reverseTid, 32));
        fetchTagKeyForIssueMap.put("derivenum", "1");
        fetchTagKeyForIssueMap.put("export", "0");
        fetchTagKeyForIssueMap.put("publickey", "");
        fetchTagKeyForIssueMap.put("prokeyindex", "0");
        fetchTagKeyForIssueMap.put("keytype", "ENDE_KEY");
        fetchTagKeyForIssueMap.put("keyversion", String.valueOf(version));
        fetchTagKeyForIssueMap.put("keyindex", String.valueOf(encryptKeyIndex));
        fetchTagKeyForIssueMap.put("keypartition", upper ? "00" : "01");
        fetchTagKeyForIssueMap.put("keyparam", EriUtil.appendZero(String.valueOf(partitionIndex), 2));
        fetchTagKeyForIssue.setStrXml(XmlHelper.map2XML(fetchTagKeyForIssueMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(
                getStub().fetchTagKeyForIssue(fetchTagKeyForIssue).get_return());

        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            LOG.debug("分散USER" + partitionIndex + (upper ? "上" : "下") + "半区加解密密钥成功");
            return true;
        } else {
            LOG.error("分散USER" + partitionIndex + (upper ? "上" : "下") + "半区加解密密钥失败");
            throw new KMSException("分散用户区加解密密钥(" + partitionIndex + "," + (upper ? "上半区" : "下半区") + ")", resultMap);
        }
    }

    @Override
    public String CFBEncrypt(int encryptKeyIndex, boolean upper, String hexIV, String base64Text) throws Exception {
        Map<String, String> SM1CFBEncryptMap = getSecretKeyMap();
        SM1CFBEncryptMap.put("keytype", "ENDE_KEY");
        SM1CFBEncryptMap.put("keyindex", String.valueOf(encryptKeyIndex));
        SM1CFBEncryptMap.put("keypartition", upper ? "00" : "01");
        SM1CFBEncryptMap.put("iv", hexIV);
        SM1CFBEncryptMap.put("plaintext", base64Text);
        SecretKeyManagerServiceStub.Sm1CFBEncrypt sm1CFBEncrypt = new SecretKeyManagerServiceStub.Sm1CFBEncrypt();
        sm1CFBEncrypt.setStrXml(XmlHelper.map2XML(SM1CFBEncryptMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(
                getStub().sm1CFBEncrypt(sm1CFBEncrypt).get_return());
        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String result = EriUtil.base64ToHex(((Map)resultMap.get("Result")).get("Cipher").toString());
            LOG.info("CFB加密：" + result);
            return result;
        } else {
            throw new KMSException("CFB加密", resultMap);
        }

    }

    @Override
    public String SM2Sign(int version, int level, String areaCode, String data) throws Exception {
        Map<String, String> SM2PrivateKeySignatureMap = getSecretKeyMap();
        SM2PrivateKeySignatureMap.put("level", String.valueOf(level));
        SM2PrivateKeySignatureMap.put("areaCode", areaCode);
        SM2PrivateKeySignatureMap.put("data", data);
        SM2PrivateKeySignatureMap.put("version",  String.valueOf(version));
        SecretKeyManagerServiceStub.Sm2Sign sm2Sign = new SecretKeyManagerServiceStub.Sm2Sign();
        sm2Sign.setStrXml(XmlHelper.map2XML(SM2PrivateKeySignatureMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(getStub().sm2Sign(sm2Sign).get_return());

        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String sign = ((Map)resultMap.get("Result")).get("Signature").toString();
            LOG.debug("签名成功：" + sign);
            return sign;
        } else {
            LOG.error("签名失败");
            throw new KMSException("签名", resultMap);
        }
    }

    @Override
    public String generatePMYAndDKMY(int version, int protectKeyIndex, String data1, String data2, int position) throws Exception {
        Map<String, String> deriveTwiceAndExportKeyMap = getSecretKeyMap();
        deriveTwiceAndExportKeyMap.put("keytype1", "IDAUTH_ROOTKEY");
        deriveTwiceAndExportKeyMap.put("keyversion1", String.valueOf(version));
        deriveTwiceAndExportKeyMap.put("keytype2", "IDAUTH_ROOTKEY");
        deriveTwiceAndExportKeyMap.put("keyversion2", String.valueOf(version));
        deriveTwiceAndExportKeyMap.put("prokeyindex", String.valueOf(protectKeyIndex));
        deriveTwiceAndExportKeyMap.put("export", "1");
        deriveTwiceAndExportKeyMap.put("derivenum", "1");
        deriveTwiceAndExportKeyMap.put("drivedatar1", data1);
        deriveTwiceAndExportKeyMap.put("drivedatar2", data2);
        deriveTwiceAndExportKeyMap.put("position", String.valueOf(position));
        deriveTwiceAndExportKeyMap.put("publickey", "");
        SecretKeyManagerServiceStub.FetchTagAuthKeyForIssue fetchTagAuthKeyForIssue =
                new SecretKeyManagerServiceStub.FetchTagAuthKeyForIssue();
        fetchTagAuthKeyForIssue.setStrXml(XmlHelper.map2XML(deriveTwiceAndExportKeyMap, "Params", ""));
        Map resultMap = XmlHelper.xml2map(getStub().fetchTagAuthKeyForIssue(fetchTagAuthKeyForIssue).get_return());

        if ("00".equals(((Map)resultMap.get("Result")).get("ErrorCode"))) {
            String key = ((Map)resultMap.get("Result")).get("Key").toString();
            LOG.debug("生成批密钥和单卡密钥成功：" + key);
            return key;
        } else {
            LOG.error("生成批密钥和单卡密钥失败");
            throw new KMSException("生成批密钥和单卡密钥", resultMap);
        }
    }
}
