package com.tmri.rfid.bean;

import java.util.Date;

/**
 * 
 * @author stone
 * @date 2016-3-16 ����10:37:48
 */
public class EriOperation {

	private int type; // 1-ԭ����� 2-��ʼ�� 3-��������� 4-���������� 5-�ܶ���� 6-֧������ 7-���Ի�
    private String dh; //ԭ����ⵥ�š���������ⵥ�š����������ⵥ�š��ܶ���ⵥ�š����õ���
    private String jbr; 
    private String jbrxm;
    private Date rq;
    private String ssbm;
    private String bmmc;
    private String bz;
    
    private String hphm;
    private String hpzl;
    private String fzjg;
    private int gxhzt; //���Ի�״̬
    
    private String lyr;//������
    private String glbmmc;//����������
    private Long id;
    
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public String getJbrxm() {
		return jbrxm;
	}
	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public String getSsbm() {
		return ssbm;
	}
	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getHpzl() {
		return hpzl;
	}
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public int getGxhzt() {
		return gxhzt;
	}
	public void setGxhzt(int gxhzt) {
		this.gxhzt = gxhzt;
	}
	public String getLyr() {
		return lyr;
	}
	public void setLyr(String lyr) {
		this.lyr = lyr;
	}
	public String getGlbmmc() {
		return glbmmc;
	}
	public void setGlbmmc(String glbmmc) {
		this.glbmmc = glbmmc;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
