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
     * �����ƺ��밴36���Ƽ����Ӧ��10������ֵ��36���ƴ�С����ΪA-Z0-9����AΪ10���Ƶ�0��ZΪ10���Ƶ�25��0Ϊ10���Ƶ�26��9Ϊ10���Ƶ�35��
     * ���ƺ��벻��6λ������ĩβ��9��36���ƣ���
     * @param hphm
     * @return 10������ֵ
     * @throws Exception ���ƺ��볤�Ȳ�Ϊ6λ����ƺ������г���A-Z0-9������ַ���
     */
    public static long parseHPHMToLong(String hphm) throws Exception{
        if (StringUtils.isEmpty(hphm) || hphm.length() > 6) {
            throw new Exception("���ƺ��볤�Ȳ���ȷ");
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
                throw new Exception("���ƺ����д��ڷǷ��ַ�");
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
            throw new Exception("���ݴ���");
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
     * Ϊ���ֲ�0
     * @param number
     * @param numberLength
     * @param position 1-��ǰ�油��2-��ĩβ��
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
        int i = 8; //����8���ֽ�
        int version = certBytes[i] & 0xff;
        if ((certBytes[i] & 0xff) == 0xA0) {
            i += 5;
        }
        if (certBytes[i] == 0x02) {
            i++;
            int serialNumLen = certBytes[i] & 0xff;
            byte[] serialNum = new byte[serialNumLen];
            i++;
            if(certBytes[i] == 0 )//���к������
            {
                i++;
                serialNumLen--;
            }
            for (int j = 0; j < serialNumLen; j++) {
                serialNum[j] = certBytes[i + j];
            }

            i += serialNumLen;
        }
        //ǩ���㷨��ʶ
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
        }
        int lenTemp = certBytes[i] & 0xff; //ǩ���㷨���Ȳ��ᳬ��128
        i += lenTemp;
        i++;
        //�䷢��
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
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
        //��Ч��
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
        }
        lenTemp = certBytes[i] & 0xff;
        i = i + lenTemp + 1;
        //ʹ����
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
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
        //��Կ�����Ϣ
        if ((certBytes[i] & 0xff) == 0x30) {
            i++;
        } else {
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
        }
        lenTemp = certBytes[i] & 0xff;
        if (lenTemp < 0x80) { //ecs/sm2��Կ���Ȳ�����128�ֽ�
            i++;
            i += lenTemp;
            pubKey = Arrays.copyOfRange(certBytes, i - 64, i);
        } else { //rsa��Կ����Ϊ128�ֽ�
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
        int i = 4; //����4���ֽ�
        if (certBytes[i] == 0x30) {
            i++;
        } else {
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
        }
        int certInfoLen = 0;
        if(certBytes[i] < 0) {
            lenSize = certBytes[i] & 0xff % 16;
            i++;
            lengthBuf = Arrays.copyOfRange(certBytes, i, i + lenSize);
            i += lenSize;
            certInfoLen = (int)bytesToLong(lengthBuf);
            i += certInfoLen;
        } else { //֤�鳤����Ч
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
        }

        //����Ϊ֤��ǩ�������Ϣ
        if(certBytes[i] == 0x30) {
            i++;
        }
        else { //��ʽ����
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
        }

        int len1;
        if((certBytes[i] & 0xff) < 128) {//֤��ǩ��������128�ֽ�
            len1 = certBytes[i] & 0xff;
            i++;
        } else {//��ʽ����
            throw new Exception("����֤��ʧ�ܣ���ʽ����");
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
            //ecc��sm2ǩ��
            i += 6;
            if(certBytes[i] == 0x00)//�����(��һ���ֽ�bit8Ϊ1)
                i++;
            r = Arrays.copyOfRange(certBytes, i, i + 32);
            i += 32;
            i += 2;
            if(certBytes[i] == 0x00)//�����
            i++;
            s = Arrays.copyOfRange(certBytes, i, i + 32);
            return bytesToHex(r) + bytesToHex(s);
        }
        else
        {
            //rsaǩ��
            i += 2;
            if(certBytes[i] == 0x00)//�����
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
            int i = 4; //����4���ֽ�
            if (certBytes[i] == 0x30) {
                i++;
            } else {
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }
            int certInfoLen = 0;
            if (certBytes[i] < 0) {
                lenSize = certBytes[i] & 0xff % 16;
                i++;
                lengthBuf = Arrays.copyOfRange(certBytes, i, i + lenSize);
                i += lenSize;
                certInfoLen = (int) bytesToLong(lengthBuf);
                signStartIndex = i + certInfoLen;
            } else { //֤�鳤����Ч
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }

            i = 8; //����8���ֽ�
            int version = certBytes[i] & 0xff;
            if ((certBytes[i] & 0xff) == 0xA0) {
                i += 5;
            }
            if (certBytes[i] == 0x02) {
                i++;
                int serialNumLen = certBytes[i] & 0xff;
                byte[] serialNum = new byte[serialNumLen];
                i++;
                if (certBytes[i] == 0)//���к������
                {
                    i++;
                    serialNumLen--;
                }
                for (int j = 0; j < serialNumLen; j++) {
                    serialNum[j] = certBytes[i + j];
                }

                Log.info("���кţ�" + Bytes.bytes2hex(serialNum));
                i += serialNumLen;
            }
            //ǩ���㷨��ʶ
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }
            int lenTemp = certBytes[i] & 0xff; //ǩ���㷨���Ȳ��ᳬ��128
            i += lenTemp;
            i++;
            //�䷢��
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
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
            //��Ч��
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }
            lenTemp = certBytes[i] & 0xff;
            i = i + lenTemp + 1;
            //ʹ����
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
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
            //��Կ�����Ϣ
            if ((certBytes[i] & 0xff) == 0x30) {
                i++;
            } else {
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }
            lenTemp = certBytes[i] & 0xff;
            if (lenTemp < 0x80) { //ecs/sm2��Կ���Ȳ�����128�ֽ�
                i++;
                i += lenTemp;
                pubKey = Arrays.copyOfRange(certBytes, i - 64, i);
            } else { //rsa��Կ����Ϊ128�ֽ�
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
            //����Ϊ֤��ǩ�������Ϣ
            if (certBytes[i] == 0x30) {
                i++;
            } else { //��ʽ����
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }

            int len1;
            if ((certBytes[i] & 0xff) < 128) {//֤��ǩ��������128�ֽ�
                len1 = certBytes[i] & 0xff;
                i++;
            } else {//��ʽ����
                throw new Exception("����֤��ʧ�ܣ���ʽ����");
            }
            i += len1;
            //***************************
            byte[] rsaSignData = new byte[128];
            if (certBytes[i] == 0x03) {
                i++;
            }
            if ((certBytes[i] & 0xff) < 128) {
                //ecc��sm2ǩ��
                i += 6;
                if (certBytes[i] == 0x00)//�����(��һ���ֽ�bit8Ϊ1)
                    i++;
                r = Arrays.copyOfRange(certBytes, i, i + 32);
                i += 32;
                i += 2;
                if (certBytes[i] == 0x00)//�����
                    i++;
                s = Arrays.copyOfRange(certBytes, i, i + 32);
                cert.setSign(bytesToHex(r) + bytesToHex(s));
            } else {
                //rsaǩ��
                i += 2;
                if (certBytes[i] == 0x00)//�����
                    i++;
                rsaSignData = Arrays.copyOfRange(certBytes, i, i + 128);
                cert.setSign(bytesToHex(rsaSignData));
            }

            byte[] body = Arrays.copyOfRange(certBytes, 4, signStartIndex);
            cert.setBody(bytesToHex(body));
            return cert;
        } catch (Exception e) {
            throw new RuntimeException("����֤��ʧ��", e);
        }
    }

    public static Cert parseCert(String hexCert) throws Exception{
        return parseCert(Bytes.hex2bytes(hexCert));
    }

    public static String parseSecurityModelXh(String hexXh) throws Exception {
        if (hexXh.length() != 16) {
            throw new Exception("��ȫģ�����ӦΪ8���ֽ�");
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
     * ��ȡJsonObject
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json){
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * ����json�ַ�������Map����
     * @param json
     * @return
     */
    public static Map<String,Object> toMap(String json){
        return toMap(parseJson(json));
    }

    /**
     * ��JSONObjec����ת����Map-List����
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
     * ��JSONArray����ת����List����
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
     * ��ʱ��˳������Ψһ���кţ�����Ϊ15λ
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
