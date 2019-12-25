package com.tmri.rfid.service;

import java.util.Map;

/**
 * Created by Joey on 2017/4/10.
 */
public interface KMSService {

    void reset(String addressList) throws Exception;

    /**
     *
     * @param data  Base64�����ַ���
     * @return Base64�����ַ���
     * @throws Exception
     */
    String SM3Digest(String data) throws Exception;

    /**
     *
     * @param version
     * @param data Base64�����ַ���
     * @param signature Base64�����ַ���
     * @return true - �ɹ��� false - ʧ��
     * @throws Exception
     */
    boolean SM2Verify(int version, String data, String signature) throws Exception;

    /**
     *
     * @param pubKey
     * @param data Base64�����ַ���
     * @param signature Base64�����ַ���
     * @return true - �ɹ��� false - ʧ��
     * @throws Exception
     */
    boolean SM2Verify(String pubKey, String data, String signature) throws Exception;

    /**
     *
     * @param version
     * @param pubKey Base64�����ַ���
     * @param protectedKeyIndex
     * @return
     * @throws Exception
     */
    String createTmpProKey(int version, String pubKey, int protectedKeyIndex) throws Exception;

    String generateLockPassword(int version, String reverseTid, int protectKeyIndex) throws Exception;

    String generateActivatePassword(int version, String reverseTid, int protectedKeyIndex) throws Exception;

    String generateUserWritePassword(int version, int partitionIndex, String reverseTid, int protectKeyIndex) throws Exception;

    String generateUserReadPassword(int version, int partitionIndex, String reverseTid, int protectKeyIndex) throws Exception;

    String generateUser0WritePasswordOld(int version, String reverseTid, int protectKeyIndex, boolean isCity) throws Exception;

    boolean generateUserEncryptPassword(int version, int partitionIndex, boolean upper, String reverseTid, int encryptKeyIndex) throws Exception;

    String CFBEncrypt(int encryptKeyIndex, boolean upper, String hexIV, String text) throws Exception;

    /**
     *
     * @param version ��Կ�汾�ţ����ݵ��ӱ�ʶ���Ż�ȡ
     * @param level 1
     * @param areaCode ��������
     * @param data Base64�����ַ���
     * @return Base64�����ַ���
     * @throws Exception
     */
    String SM2Sign(int version, int level, String areaCode, String data) throws Exception;

    String generatePMYAndDKMY(int version, int protectKeyIndex, String data1, String data2, int position) throws Exception;
}
