package com.tmri.rfid.service;

/**
 * Created by Joey on 2015/9/24.
 */
public interface PlateNumberHandler {

    /**
     * ���ݷ�֤���ء��������ࡢ���ƺ�����������ĳ��ƺ���
     * @param fzjg ��֤���أ��磺��B
     * @param hphm ���ƺ��룬�磺B1234
     * @return ���ƺ��룬�磺��B1234��
     * @throws Exception
     */
    String compose(String fzjg, String hphm) throws Exception;

    /**
     * ���������ĳ��ƺŻ�ȡ������ţ������ӱ�ʶ���Ի�����ʱ����Ҫ������ƺ������ֵ
     * @param cphm �������ƺ��룬�磺��B1234��
     * @return ���ƺ��룬�磺1234
     * @throws Exception
     */
    String getHpxh(String cphm) throws Exception;



}
