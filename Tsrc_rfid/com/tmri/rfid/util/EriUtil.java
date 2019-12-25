package com.tmri.rfid.util;

import com.google.gson.*;
import com.sansec.impl.device.util.Bytes;
import com.tmri.rfid.bean.Cert;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.*;

/**
 * Created by Joey on 2015/9/6.
 */
public class EriUtil {

    private final static Logger LOG = LoggerFactory.getLogger(EriUtil.class);

    /**
     * 将号牌号码按36进制计算对应的10进制数值，36进制从小到大为A-Z0-9，即A为10进制的0，Z为10进制的25，0为10进制的26，9为10进制的35。
     * 号牌号码不足6位，会在末尾补9（36进制）。
     * @param hphm
     * @return 10进制数值
     * @throws Exception 号牌号码长度不为6位或号牌号码中有除了A-Z0-9以外的字符。
     */
    public static long parseHPHMToLong(String hphm) throws Exception{
        if (StringUtils.isEmpty(hphm) || hphm.length() > 6) {
            throw new Exception("号牌号码长度不正确");
        }
        long result = 0;
        char[] characters = hphm.toLowerCase().toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int charValue = (int) characters[i];
            if (charValue >= 97 && charValue <= 122) {
                charValue = charValue - 97;
            } else if (charValue >= 48 && charValue <= 57) {
                charValue = charValue - 22;
            } else {
                throw new Exception("号牌号码中存在非法字符");
            }
            result += charValue * Math.pow(36, characters.length - 1 - i);
        }
        if (characters.length > 4) {
            result += Math.pow(36, 4);
        }
        if (characters.length > 5) {
            result += Math.pow(36, 5);
        }
        return result;
    }

    public static String parseLongToHPHM(long value) throws Exception {
        if (value < 0 || value > Math.pow(36, 6) + Math.pow(36, 5) + Math.pow(36, 4))
            throw new Exception("数据错误");
        String hphm = "";
        long remainder;
        long result = value;
        int hphmLength;
        if (value < Math.pow(36, 4)) {
            hphmLength = 4;
        } else if (value < Math.pow(36, 5) + Math.pow(36,4)) {
            hphmLength = 5;
            result = result - (long)Math.pow(36, 4);
        } else {
            hphmLength = 6;
            result = result - (long)Math.pow(36,4) - (long)Math.pow(36, 5);
        }
        do {
            remainder = result % 36;
            result = result / 36;
            if (remainder < 26) {
                hphm = (char)(65 + remainder) + hphm;
            } else {
                hphm = (char)(48 + remainder - 26) + hphm;
            }
        } while ( result > 0);
        int l = hphm.length();
        for (int i = 0; i < hphmLength - l; i++) {
            hphm = "A" + hphm;
        }
        return hphm;
    }

    public static String appendZero(final String number, int numberLength) {
        return appendZero(number, numberLength, 1);
    }

    /**
     * 为数字补0
     * @param number
     * @param numberLength
     * @param position 1-在前面补，2-在末尾补
     * @return
     */
    public static String appendZero(final String number, int numberLength, int position) {
        String result = number;
        if (result.length() < numberLength) {
            for (int i = 0; i < numberLength - number.length(); i++) {
                if (position == 1) {
                    result = "0" + result;
                } else {
                    result = result + "0";
                }
            }
        } else {
            result = result.substring(result.length() - numberLength);
        }
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        String hexStr = Bytes.bytes2hex(bytes);
        return hexStr.substring(0, hexStr.indexOf(","));
    }

    public static String parseHexStr(String hexStr) {
        String str = "";
        for (int i = 0; i < hexStr.length(); i += 2) {
            str = str + "0x" + hexStr.substring(i, i + 2) + ",";
        }
        return str;
    }

    public static String getPubKeyFromCert(String cert) throws Exception{
        byte[] certBytes = Bytes.hex2bytes(cert);
        byte[] pubKey;
        int i = 8; //跳过8个字节
        int version = certBytes[i] & 0xff;
        if ((certBytes[i] & 0xff) == 0xA0) {
            i += 5;
        }
        if (certBytes[i] == 0x02) {
            i++;
            int serialNumLen = certBytes[i] & 0xff;
            byte[] serialNum = new byte[serialNumLen];
            i++;
            if(certBytes[i] == 0 )//序列号有填充
            {
                i++;
                serialNumLen--;
            }
            for (int j = 0; j < serialNumLen; j++) {
                serialNum[j] = certBytes[i + j];
            }

            i += serialNumLen;
        }
        //签名算法标识
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("解析证书失败，格式错误");
        }
        int lenTemp = certBytes[i] & 0xff; //签名算法长度不会超过128
        i += lenTemp;
        i++;
        //颁发者
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("解析证书失败，格式错误");
        }
        lenTemp = certBytes[i] & 0xff;
        if (lenTemp < 0x80) {
            i += lenTemp;
            i ++;
        } else {
            lenTemp = lenTemp - 128;
            i++;
            byte[] lenBufTmp = Arrays.copyOfRange(certBytes, i, i + lenTemp);
            i += lenTemp;
            lenTemp = (int)bytesToLong(lenBufTmp);
            i += lenTemp;
        }
        //有效期
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("解析证书失败，格式错误");
        }
        lenTemp = certBytes[i] & 0xff;
        i = i + lenTemp + 1;
        //使用者
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("解析证书失败，格式错误");
        }
        lenTemp = certBytes[i] & 0xff;
        if (lenTemp < 0x80) {
            i += lenTemp;
            i ++;
        } else {
            lenTemp = lenTemp - 128;
            i++;
            byte[] lenBufTmp = Arrays.copyOfRange(certBytes, i, i + lenTemp);
            i += lenTemp;
            lenTemp = (int)bytesToLong(lenBufTmp);
            i += lenTemp;
        }
        //公钥相关信息
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("解析证书失败，格式错误");
        }
        lenTemp = certBytes[i] & 0xff;
        if (lenTemp < 0x80) { //ecs/sm2公钥长度不超过128字节
            i++;
            i += lenTemp;
            pubKey = Arrays.copyOfRange(certBytes, i - 64, i);
        } else { //rsa公钥长度为128字节
            lenTemp = lenTemp - 128;
            i++;
            byte[] lenBufTmp = Arrays.copyOfRange(certBytes, i, i + lenTemp);
            i += lenTemp;
            lenTemp = (int)bytesToLong(lenBufTmp);
            i += lenTemp;
            pubKey = Arrays.copyOfRange(certBytes, i - 128, i);
        }
        return EriUtil.bytesToHex(pubKey);
    }

    public static String getSignData(String cert) throws Exception {
        byte[] certBytes = Bytes.hex2bytes(cert);
        int lenSize;
        byte[] signData;
        byte[] r;
        byte[] s;
        byte[] lengthBuf;
        int i = 4; //跳过4个字节
        if (certBytes[i] == 0x30) {
            i++;
        } else {
            throw new Exception("解析证书失败，格式错误");
        }
        int certInfoLen = 0;
        if(certBytes[i] < 0) {
            lenSize = certBytes[i] & 0xff % 16;
            i++;
            lengthBuf = Arrays.copyOfRange(certBytes, i, i + lenSize);
            i += lenSize;
            certInfoLen = (int)bytesToLong(lengthBuf);
            i += certInfoLen;
        } else { //证书长度无效
            throw new Exception("解析证书失败，格式错误");
        }

        //以下为证书签名相关信息
        if(certBytes[i] == 0x30) {
            i++;
        }
        else { //格式错误
            throw new Exception("解析证书失败，格式错误");
        }

        int len1;
        if((certBytes[i] & 0xff) < 128) {//证书签名不超过128字节
            len1 = certBytes[i] & 0xff;
            i++;
        } else {//格式错误
            throw new Exception("解析证书失败，格式错误");
        }
        i += len1;
        //***************************
        byte[] rsaSignData = new byte[128];
        if(certBytes[i]==0x03)
        {
            i++;
        }
        if((certBytes[i] & 0xff) < 128)
        {
            //ecc或sm2签名
            i += 6;
            if(certBytes[i] == 0x00)//有填充(第一个字节bit8为1)
                i++;
            r = Arrays.copyOfRange(certBytes, i, i + 32);
            i += 32;
            i += 2;
            if(certBytes[i] == 0x00)//有填充
            i++;
            s = Arrays.copyOfRange(certBytes, i, i + 32);
            return bytesToHex(r) + bytesToHex(s);
        }
        else
        {
            //rsa签名
            i += 2;
            if(certBytes[i] == 0x00)//有填充
            i++;
            rsaSignData = Arrays.copyOfRange(certBytes, i, i + 128);
            return bytesToHex(rsaSignData);
        }
    }

    public static Cert parseCert(byte[] certBytes) throws Exception {
        try {
            Cert cert = new Cert();
            byte[] pubKey;

            int lenSize, signStartIndex;
            byte[] signData;
            byte[] r;
            byte[] s;
            byte[] lengthBuf;
            int i = 4; //跳过4个字节
            if (certBytes[i] == 0x30) {
                i++;
            } else {
                throw new Exception("解析证书失败，格式错误");
            }
            int certInfoLen = 0;
            if (certBytes[i] < 0) {
                lenSize = certBytes[i] & 0xff % 16;
                i++;
                lengthBuf = Arrays.copyOfRange(certBytes, i, i + lenSize);
                i += lenSize;
                certInfoLen = (int) bytesToLong(lengthBuf);
                signStartIndex = i + certInfoLen;
            } else { //证书长度无效
                throw new Exception("解析证书失败，格式错误");
            }

            i = 8; //跳过8个字节
            int version = certBytes[i] & 0xff;
            if ((certBytes[i] & 0xff) == 0xA0) {
                i += 5;
            }
            if (certBytes[i] == 0x02) {
                i++;
                int serialNumLen = certBytes[i] & 0xff;
                byte[] serialNum = new byte[serialNumLen];
                i++;
                if (certBytes[i] == 0)//序列号有填充
                {
                    i++;
                    serialNumLen--;
                }
                for (int j = 0; j < serialNumLen; j++) {
                    serialNum[j] = certBytes[i + j];
                }

                Log.info("序列号：" + Bytes.bytes2hex(serialNum));
                i += serialNumLen;
            }
            //签名算法标识
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("解析证书失败，格式错误");
            }
            int lenTemp = certBytes[i] & 0xff; //签名算法长度不会超过128
            i += lenTemp;
            i++;
            //颁发者
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("解析证书失败，格式错误");
            }
            lenTemp = certBytes[i] & 0xff;
            if (lenTemp < 0x80) {
                i += lenTemp;
                i++;
            } else {
                lenTemp = lenTemp - 128;
                i++;
                byte[] lenBufTmp = Arrays.copyOfRange(certBytes, i, i + lenTemp);
                i += lenTemp;
                lenTemp = (int) bytesToLong(lenBufTmp);
                i += lenTemp;
            }
            //有效期
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("解析证书失败，格式错误");
            }
            lenTemp = certBytes[i] & 0xff;
            i = i + lenTemp + 1;
            //使用者
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("解析证书失败，格式错误");
            }
            lenTemp = certBytes[i] & 0xff;
            if (lenTemp < 0x80) {
                i += lenTemp;
                i++;
            } else {
                lenTemp = lenTemp - 128;
                i++;
                byte[] lenBufTmp = Arrays.copyOfRange(certBytes, i, i + lenTemp);
                i += lenTemp;
                lenTemp = (int) bytesToLong(lenBufTmp);
                i += lenTemp;
            }
            //公钥相关信息
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("解析证书失败，格式错误");
            }
            lenTemp = certBytes[i] & 0xff;
            if (lenTemp < 0x80) { //ecs/sm2公钥长度不超过128字节
                i++;
                i += lenTemp;
                pubKey = Arrays.copyOfRange(certBytes, i - 64, i);
            } else { //rsa公钥长度为128字节
                lenTemp = lenTemp - 128;
                i++;
                byte[] lenBufTmp = Arrays.copyOfRange(certBytes, i, i + lenTemp);
                i += lenTemp;
                lenTemp = (int) bytesToLong(lenBufTmp);
                i += lenTemp;
                pubKey = Arrays.copyOfRange(certBytes, i - 128, i);
            }
            cert.setPubKey(bytesToHex(pubKey));

            i = signStartIndex;
            //以下为证书签名相关信息
            if (certBytes[i] == 0x30) {
                i++;
            } else { //格式错误
                throw new Exception("解析证书失败，格式错误");
            }

            int len1;
            if ((certBytes[i] & 0xff) < 128) {//证书签名不超过128字节
                len1 = certBytes[i] & 0xff;
                i++;
            } else {//格式错误
                throw new Exception("解析证书失败，格式错误");
            }
            i += len1;
            //***************************
            byte[] rsaSignData = new byte[128];
            if (certBytes[i] == 0x03) {
                i++;
            }
            if ((certBytes[i] & 0xff) < 128) {
                //ecc或sm2签名
                i += 6;
                if (certBytes[i] == 0x00)//有填充(第一个字节bit8为1)
                    i++;
                r = Arrays.copyOfRange(certBytes, i, i + 32);
                i += 32;
                i += 2;
                if (certBytes[i] == 0x00)//有填充
                    i++;
                s = Arrays.copyOfRange(certBytes, i, i + 32);
                cert.setSign(bytesToHex(r) + bytesToHex(s));
            } else {
                //rsa签名
                i += 2;
                if (certBytes[i] == 0x00)//有填充
                    i++;
                rsaSignData = Arrays.copyOfRange(certBytes, i, i + 128);
                cert.setSign(bytesToHex(rsaSignData));
            }

            byte[] body = Arrays.copyOfRange(certBytes, 4, signStartIndex);
            cert.setBody(bytesToHex(body));
            return cert;
        } catch (Exception e) {
            throw new RuntimeException("解析证书失败", e);
        }
    }

    public static Cert parseCert(String hexCert) throws Exception{
        return parseCert(Bytes.hex2bytes(hexCert));
    }

    public static String parseSecurityModelXh(String hexXh) throws Exception {
        if (hexXh.length() != 16) {
            throw new Exception("安全模块序号应为8个字节");
        }
        String model = String.valueOf((char)Integer.parseInt(hexXh.substring(0, 2), 16));
        String type = String.valueOf((char)Integer.parseInt(hexXh.substring(2, 4), 16));
        String version = appendZero(String.valueOf(Integer.parseInt(hexXh.substring(4, 6), 16)), 2);
        String batchNo = appendZero(String.valueOf(Integer.parseInt(hexXh.substring(6, 10), 16)), 3);
        String serialNo = appendZero(String.valueOf(Integer.parseInt(hexXh.substring(10), 16)), 5);
        return model + type + version + batchNo + serialNo;
    }
    public static String base64ToHex(String base64Str) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(base64Str);
        String hexStr = Bytes.bytes2hex(bytes);
        return hexStr.substring(0, hexStr.indexOf(","));
    }

    public static void main(String args[]) {
        try {
            System.out.println(EriUtil.parseSecurityModelXh("52410200020002EC"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long bytesToLong(byte[] bytes) {
        long sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            sum = sum + (bytes[i] & 0xff) * (long)Math.pow(256, bytes.length - i - 1);
        }
        return sum;
    }
    
    public static void shortToBytes(byte b[], short s, int index) {  
        b[index + 1] = (byte) (s >> 8);  
        b[index + 0] = (byte) (s >> 0);  
    }

    /**
     * 获取JsonObject
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json){
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 根据json字符串返回Map对象
     * @param json
     * @return
     */
    public static Map<String,Object> toMap(String json){
        return toMap(parseJson(json));
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(JsonObject json){
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ){
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if(value instanceof JsonArray)
                map.put(key, toList((JsonArray) value));
            else if(value instanceof JsonObject)
                map.put(key, toMap((JsonObject) value));
            else if (value instanceof JsonPrimitive) {
                JsonPrimitive primitiveValue = (JsonPrimitive)value;
                if (primitiveValue.isBoolean()) {
                    map.put(key, primitiveValue.getAsBoolean());
                } else if (primitiveValue.isNumber()) {
                    map.put(key, primitiveValue.getAsNumber());
                } else if (primitiveValue.isString()) {
                    map.put(key, primitiveValue.getAsString());
                } else {
                    map.put(key, value.toString());
                }
            } else if (value instanceof JsonNull) {

            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json){
        List<Object> list = new ArrayList<Object>();
        for (int i=0; i<json.size(); i++){
            Object value = json.get(i);
            if(value instanceof JsonArray){
                list.add(toList((JsonArray) value));
            }
            else if(value instanceof JsonObject){
                list.add(toMap((JsonObject) value));
            }
            else{
                list.add(value);
            }
        }
        return list;
    }

    private static int count = 0;
    private static long lastTime = 0;

    /**
     * 按时间顺序生成唯一序列号，长度为15位
     * @return
     */
    public static String generateUniqueSerialNo() {
        long now = System.currentTimeMillis();
        if (now > lastTime) {
            lastTime = now;
            count = 0;
        }
        return now + appendZero(count++ + "", 2);
    }

}
