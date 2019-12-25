package com.tmri.rfid.bean;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Joey on 2015/9/6.
 */
public class Eri implements RowMapper {

    private String tid; //��ȡ��ʶ��ȡ������Ψһ
    private String kh; //���ţ�Ψһ����ϵͳ����
    private String sf; //����ʡ��
    private int zt; //״̬������EriStatus
    private String ph; //����
    private Date cshrq; //��ʼ������
    private Date scgxhrq; //�ϴθ��Ի�����
    private int klx; //�����ͣ� 0-ͨ����ͨ������ط��п���1-�³�����Ԥװ��
    private String glbm; //�������ţ���ӦBAS_DEPARTMENT���BMDM
    private String bzhh;    //��װ�к�
    private Long clxxbfid;  //������Ϣ����ID����ӦRFID_VEHICLE_LOG.ID
    private String bz;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

	public Date getCshrq() {
		return cshrq;
	}

	public void setCshrq(Date cshrq) {
		this.cshrq = cshrq;
	}

    public Date getScgxhrq() {
        return scgxhrq;
    }

    public void setScgxhrq(Date scgxhrq) {
        this.scgxhrq = scgxhrq;
    }

    public int getKlx() {
        return klx;
    }

    public void setKlx(int klx) {
        this.klx = klx;
    }

    public String getGlbm() {
        return glbm;
    }

    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }

    public String getBzhh() {
        return bzhh;
    }

    public void setBzhh(String bzhh) {
        this.bzhh = bzhh;
    }

    public Long getClxxbfid() {
        return clxxbfid;
    }

    public void setClxxbfid(Long clxxbfid) {
        this.clxxbfid = clxxbfid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Eri eri = new Eri();
        eri.setTid(resultSet.getString("tid"));
        eri.setKh(resultSet.getString("kh"));
        eri.setSf(resultSet.getString("sf"));
        eri.setCshrq(resultSet.getDate("cshrq"));
        eri.setScgxhrq(resultSet.getDate("scgxhrq"));
        eri.setGlbm(resultSet.getString("glbm"));
        eri.setClxxbfid(resultSet.getLong("clxxbfid"));
        eri.setZt(resultSet.getInt("zt"));
        eri.setPh(resultSet.getString("ph"));
        eri.setKlx(resultSet.getInt("klx"));
        eri.setBzhh(resultSet.getString("bzhh"));
        return eri;
    }

}
