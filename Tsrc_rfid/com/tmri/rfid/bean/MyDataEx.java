package com.tmri.rfid.bean;

/**
 * 
 * @author stone
 * @date 2016-1-28 ����12:04:47
 */
public class MyDataEx {

    private String bh;
    private String sj;//����
    private String sjlx;//��������
    private Integer clbj;//������ 0-��ʼ����ר�������ݽ���ƽ̨�� 1-���ϴ���ר�����ݽ���ƽ̨ 2-ҵ���Ѵ���  5-��ʼ���ɹ��������ݽ���ƽ̨���������� 6-�Ѹ����������� 7-ҵ���Ѵ���
	private String clfs;//����ʽ ��ɾ��
	private String keyName;//��������
    
    public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public Integer getClbj() {
		return clbj;
	}
	public void setClbj(Integer clbj) {
		this.clbj = clbj;
	}
	public String getClfs() {
		return clfs;
	}
	public void setClfs(String clfs) {
		this.clfs = clfs;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
    
}
