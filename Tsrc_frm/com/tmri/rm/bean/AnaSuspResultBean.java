package com.tmri.rm.bean;

import java.sql.Timestamp;

public class AnaSuspResultBean {
	/**
	 * ���
	 */
	private String xh;

	/**
	 * �������1
	 */
	private String gcxh1;

	/**
	 * �������2
	 */
	private String gcxh2;

	/**
	 * ���ڱ��1
	 */
	private String kkbh1;

	/**
	 * ���ڱ��2
	 */
	private String kkbh2;

	/**
	 * ����ʱ��1
	 */
	private String gcsj1;

	/**
	 * ����ʱ��2
	 */
	private String gcsj2;

	/**
	 * ��������1
	 */
	private String fxlx1;

	/**
	 * ��������2
	 */
	private String fxlx2;

	/**
	 * ������1
	 */
	private String cdh1;

	/**
	 * ������2
	 */
	private String cdh2;

	/**
	 * ��������
	 */
	private String hpzl;

	/**
	 * ���ƺ���
	 */
	private String hphm;

	/**
	 * ��������01-�����ݱȶ����Ƴ�02-�ۺ�ƽ̨�������Ƴ�03-����δ�����ص㳵��
	 */
	private String xylx;

	/**
	 * ����ʶ����
	 */
	private String sbbj;

	/**
	 * ���ƺ��������ʶ�����Ƿ�һ��
	 */
	private String hsbbjpyz;

	/**
	 * ���ʱ��
	 */
	private Timestamp rksj;

	/**
	 * ʶ��ʱ��
	 */
	private Timestamp sbsj;

	/**
	 * �˲���0-���� 1-��ȷ 9-���˲�
	 */
	private String hcjg;

	/**
	 * ����ԭ��1-���ƺ���ʶ�����2-ͼƬ���޻�����������Ϣ3-�޷���ʾͼƬ
	 */
	private String cwyy;

	/**
	 * ����1������
	 */
	private String glbm1;

	/**
	 * ����1��·����
	 */
	private String dldm1;

	/**
	 * ����1����
	 */
	private String kklx1;

	/**
	 * ����1��������
	 */
	private String xzqh1;

	/**
	 * ����1��������
	 */
	private String ljtj1;

	/**
	 * ����2������
	 */
	private String glbm2;

	/**
	 * ����2��·����
	 */
	private String dldm2;

	/**
	 * ����2����
	 */
	private String kklx2;

	/**
	 * ����2��������
	 */
	private String xzqh2;

	/**
	 * ����2��������
	 */
	private String ljtj2;

	/**
	 * ԭʼ���
	 */
	private String ysxh;
	/**
	 *����Ʒ��
	 */
	private String clpp;
	/**
	 * ʹ������
	 */
	private String syxz;
	/**
	 * ����������
	 */
	private String clsyr;
	/**
	 * ����״̬
	 */
	private String clzt;
	/**
	 * ����ԭ�� 1-δ�Ǽ� 2-��ע�� 3-��ǿ�Ʊ���
	 */
	private String jpyy;
	/**
	 * ������ɫ
	 */
	private String csys;
	/**
	 * Ƭ��ַ1
	 */
	private String tpdz1;
	/**
	 * Ƭ��ַ2
	 */
	private String tpdz2;
	/**
	 * ��Ч����
	 */
	private String yxqz;

	/**
	 * ����ʱ��չʾ
	 */
	private String gcsjzs1;
	/**
	 * ����ʱ��2չʾ
	 */
	private String gcsjzs2;
	/**
	 * ʶ��ʱ��չʾ
	 */
	private String sbsjzs;
	/**
	 * ������������
	 */
	private String xylxmc;

	/**
	 * ������������
	 */
	private String hpzlmc;

	/**
	 * ��������1
	 */
	private String kkmc1;

	/**
	 * ��������2
	 */
	private String kkmc2;
	/**
	 * ��������ʡ1
	 */
	private String szs1;
	/**
	 * ��������ʡ2
	 */
	private String szs2;
	/**
	 * ������������1
	 */
	private String xzqhmc1;
	/**
	 * ������������2
	 */
	private String xzqhmc2;

	/**
	 * ʹ����������
	 */
	private String syxzmc;
	/**
	 * ����״̬����
	 */
	private String clztmc;
	/**
	 * ����ԭ������
	 */
	private String jpyymc;
	/**
	 * ������ɫ����
	 */
	private String csysmc;

	/**
	 * ҵ���������� 0Ϊ�������� 1Ϊҵ����ϸ���� 2Ϊ������������
	 */
	private Integer servType;

	/**
	 * ʶ��ʼʱ��
	 */
	private String sbKssj;

	/**
	 * ʶ�����ʱ��
	 */
	private String sbJssj;
	/**
	 * ��������
	 */
	private Integer fzzs;
	/**
	 * �����
	 */
	private Integer fzh;

	/**
	 * ��������1����
	 */
	private String fxlxmc1;

	/**
	 * 0��nullΪ��ѯxh 1Ϊ��ѯxh+1
	 */
	private String queryMode;

	/**
	 * @���� ���
	 */
	public String getXh() {
		return xh;
	}

	/**
	 * @param xh ���� ���
	 */
	public void setXh(String xh) {
		this.xh = xh;
	}

	/**
	 * @���� �������1
	 */
	public String getGcxh1() {
		return gcxh1;
	}

	/**
	 * @param gcxh1 ���� �������1
	 */
	public void setGcxh1(String gcxh1) {
		this.gcxh1 = gcxh1;
	}

	/**
	 * @���� �������2
	 */
	public String getGcxh2() {
		return gcxh2;
	}

	/**
	 * @param gcxh2 ���� �������2
	 */
	public void setGcxh2(String gcxh2) {
		this.gcxh2 = gcxh2;
	}

	/**
	 * @���� ���ڱ��1
	 */
	public String getKkbh1() {
		return kkbh1;
	}

	/**
	 * @param kkbh1 ���� ���ڱ��1
	 */
	public void setKkbh1(String kkbh1) {
		this.kkbh1 = kkbh1;
	}

	/**
	 * @���� ���ڱ��2
	 */
	public String getKkbh2() {
		return kkbh2;
	}

	/**
	 * @param kkbh2 ���� ���ڱ��2
	 */
	public void setKkbh2(String kkbh2) {
		this.kkbh2 = kkbh2;
	}

	/**
	 * @���� ��������1
	 */
	public String getFxlx1() {
		return fxlx1;
	}

	/**
	 * @param fxlx1 ���� ��������1
	 */
	public void setFxlx1(String fxlx1) {
		this.fxlx1 = fxlx1;
	}

	/**
	 * @���� ��������2
	 */
	public String getFxlx2() {
		return fxlx2;
	}

	/**
	 * @param fxlx2 ���� ��������2
	 */
	public void setFxlx2(String fxlx2) {
		this.fxlx2 = fxlx2;
	}

	/**
	 * @���� ��������
	 */
	public String getHpzl() {
		return hpzl;
	}

	/**
	 * @param hpzl ���� ��������
	 */
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	/**
	 * @���� ���ƺ���
	 */
	public String getHphm() {
		return hphm;
	}

	/**
	 * @param hphm ���� ���ƺ���
	 */
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	/**
	 * @���� ��������01-�����ݱȶ����Ƴ�02-�ۺ�ƽ̨�������Ƴ�03-����δ�����ص㳵��
	 */
	public String getXylx() {
		return xylx;
	}

	/**
	 * @param xylx ���� ��������01-�����ݱȶ����Ƴ�02-�ۺ�ƽ̨�������Ƴ�03-����δ�����ص㳵��
	 */
	public void setXylx(String xylx) {
		this.xylx = xylx;
	}

	/**
	 * @���� ����ʶ����
	 */
	public String getSbbj() {
		return sbbj;
	}

	/**
	 * @param sbbj ���� ����ʶ����
	 */
	public void setSbbj(String sbbj) {
		this.sbbj = sbbj;
	}

	/**
	 * @���� ���ƺ��������ʶ�����Ƿ�һ��
	 */
	public String getHsbbjpyz() {
		return hsbbjpyz;
	}

	/**
	 * @param hpyz ���� ���ƺ��������ʶ�����Ƿ�һ��
	 */
	public void setHsbbjpyz(String hpyz) {
		this.hsbbjpyz = hpyz;
	}

	/**
	 * @���� ���ʱ��
	 */
	public Timestamp getRksj() {
		return rksj;
	}

	/**
	 * @param rksj ���� ���ʱ��
	 */
	public void setRksj(Timestamp rksj) {
		this.rksj = rksj;
	}

	/**
	 * @���� ʶ��ʱ��
	 */
	public Timestamp getSbsj() {
		return sbsj;
	}

	/**
	 * @param sbsj ���� ʶ��ʱ��
	 */
	public void setSbsj(Timestamp sbsj) {
		this.sbsj = sbsj;
	}

	/**
	 * @���� �˲���0-���� 1-��ȷ 9-���˲�
	 */
	public String getHcjg() {
		return hcjg;
	}

	/**
	 * @param hcjg ���� �˲���0-���� 1-��ȷ 9-���˲�
	 */
	public void setHcjg(String hcjg) {
		this.hcjg = hcjg;
	}

	/**
	 * @���� ����ԭ��1-���ƺ���ʶ�����2-ͼƬ���޻�����������Ϣ3-�޷���ʾͼƬ
	 */
	public String getCwyy() {
		return cwyy;
	}

	/**
	 * @param cwyy ���� ����ԭ��1-���ƺ���ʶ�����2-ͼƬ���޻�����������Ϣ3-�޷���ʾͼƬ
	 */
	public void setCwyy(String cwyy) {
		this.cwyy = cwyy;
	}

	/**
	 * @���� ����1������
	 */
	public String getGlbm1() {
		return glbm1;
	}

	/**
	 * @param glbm1 ���� ����1������
	 */
	public void setGlbm1(String glbm1) {
		this.glbm1 = glbm1;
	}

	/**
	 * @���� ����1��·����
	 */
	public String getDldm1() {
		return dldm1;
	}

	/**
	 * @param dldm1 ���� ����1��·����
	 */
	public void setDldm1(String dldm1) {
		this.dldm1 = dldm1;
	}

	/**
	 * @���� ����1����
	 */
	public String getKklx1() {
		return kklx1;
	}

	/**
	 * @param kklx1 ���� ����1����
	 */
	public void setKklx1(String kklx1) {
		this.kklx1 = kklx1;
	}

	/**
	 * @���� ����1��������
	 */
	public String getXzqh1() {
		return xzqh1;
	}

	/**
	 * @param xzqh1 ���� ����1��������
	 */
	public void setXzqh1(String xzqh1) {
		this.xzqh1 = xzqh1;
	}

	/**
	 * @���� ����1��������
	 */
	public String getLjtj1() {
		return ljtj1;
	}

	/**
	 * @param ljtj1 ���� ����1��������
	 */
	public void setLjtj1(String ljtj1) {
		this.ljtj1 = ljtj1;
	}

	/**
	 * @���� ����2������
	 */
	public String getGlbm2() {
		return glbm2;
	}

	/**
	 * @param glbm2 ���� ����2������
	 */
	public void setGlbm2(String glbm2) {
		this.glbm2 = glbm2;
	}

	/**
	 * @���� ����2��·����
	 */
	public String getDldm2() {
		return dldm2;
	}

	/**
	 * @param dldm2 ���� ����2��·����
	 */
	public void setDldm2(String dldm2) {
		this.dldm2 = dldm2;
	}

	/**
	 * @���� ����2����
	 */
	public String getKklx2() {
		return kklx2;
	}

	/**
	 * @param kklx2 ���� ����2����
	 */
	public void setKklx2(String kklx2) {
		this.kklx2 = kklx2;
	}

	/**
	 * @���� ����2��������
	 */
	public String getXzqh2() {
		return xzqh2;
	}

	/**
	 * @param xzqh2 ���� ����2��������
	 */
	public void setXzqh2(String xzqh2) {
		this.xzqh2 = xzqh2;
	}

	/**
	 * @���� ����2��������
	 */
	public String getLjtj2() {
		return ljtj2;
	}

	/**
	 * @param ljtj2 ���� ����2��������
	 */
	public void setLjtj2(String ljtj2) {
		this.ljtj2 = ljtj2;
	}

	/**
	 * @���� ԭʼ���
	 */
	public String getYsxh() {
		return ysxh;
	}

	/**
	 * @param ysxh ���� ԭʼ���
	 */
	public void setYsxh(String ysxh) {
		this.ysxh = ysxh;
	}

	/**
	 * @return the servType
	 */
	public Integer getServType() {
		return servType;
	}

	/**
	 * @param servType the servType to set
	 */
	public void setServType(Integer servType) {
		this.servType = servType;
	}

	/**
	 * @return the sbKssj
	 */
	public String getSbKssj() {
		return sbKssj;
	}

	/**
	 * @param sbKssj the sbKssj to set
	 */
	public void setSbKssj(String sbKssj) {
		this.sbKssj = sbKssj;
	}

	/**
	 * @return the sbJssj
	 */
	public String getSbJssj() {
		return sbJssj;
	}

	/**
	 * @param sbJssj the sbJssj to set
	 */
	public void setSbJssj(String sbJssj) {
		this.sbJssj = sbJssj;
	}

	/**
	 * @return the fzzs
	 */
	public Integer getFzzs() {
		return fzzs;
	}

	/**
	 * @param fzzs the fzzs to set
	 */
	public void setFzzs(Integer fzzs) {
		this.fzzs = fzzs;
	}

	/**
	 * @return the fzh
	 */
	public Integer getFzh() {
		return fzh;
	}

	/**
	 * @param fzh the fzh to set
	 */
	public void setFzh(Integer fzh) {
		this.fzh = fzh;
	}

	/**
	 * @return the gcsjzs1
	 */
	public String getGcsjzs1() {
		return gcsjzs1;
	}

	/**
	 * @param gcsjzs1 the gcsjzs1 to set
	 */
	public void setGcsjzs1(String gcsjzs1) {
		this.gcsjzs1 = gcsjzs1;
	}

	/**
	 * @return the sbsjzs
	 */
	public String getSbsjzs() {
		return sbsjzs;
	}

	/**
	 * @param sbsjzs the sbsjzs to set
	 */
	public void setSbsjzs(String sbsjzs) {
		this.sbsjzs = sbsjzs;
	}

	/**
	 * @return the xylxmc
	 */
	public String getXylxmc() {
		return xylxmc;
	}

	/**
	 * @param xylxmc the xylxmc to set
	 */
	public void setXylxmc(String xylxmc) {
		this.xylxmc = xylxmc;
	}

	/**
	 * @return the hpzlmc
	 */
	public String getHpzlmc() {
		return hpzlmc;
	}

	/**
	 * @param hpzlmc the hpzlmc to set
	 */
	public void setHpzlmc(String hpzlmc) {
		this.hpzlmc = hpzlmc;
	}

	/**
	 * @return the gcsjzs2
	 */
	public String getGcsjzs2() {
		return gcsjzs2;
	}

	/**
	 * @param gcsjzs2 the gcsjzs2 to set
	 */
	public void setGcsjzs2(String gcsjzs2) {
		this.gcsjzs2 = gcsjzs2;
	}

	/**
	 * @return the kkmc1
	 */
	public String getKkmc1() {
		return kkmc1;
	}

	/**
	 * @param kkmc1 the kkmc1 to set
	 */
	public void setKkmc1(String kkmc1) {
		this.kkmc1 = kkmc1;
	}

	/**
	 * @return the kkmc2
	 */
	public String getKkmc2() {
		return kkmc2;
	}

	/**
	 * @param kkmc2 the kkmc2 to set
	 */
	public void setKkmc2(String kkmc2) {
		this.kkmc2 = kkmc2;
	}

	/**
	 * @return the szs1
	 */
	public String getSzs1() {
		return szs1;
	}

	/**
	 * @param szs1 the szs1 to set
	 */
	public void setSzs1(String szs1) {
		this.szs1 = szs1;
	}

	/**
	 * @return the szs2
	 */
	public String getSzs2() {
		return szs2;
	}

	/**
	 * @param szs2 the szs2 to set
	 */
	public void setSzs2(String szs2) {
		this.szs2 = szs2;
	}

	/**
	 * @return the xzqhmc1
	 */
	public String getXzqhmc1() {
		return xzqhmc1;
	}

	/**
	 * @param xzqhmc1 the xzqhmc1 to set
	 */
	public void setXzqhmc1(String xzqhmc1) {
		this.xzqhmc1 = xzqhmc1;
	}

	/**
	 * @return the xzqhmc2
	 */
	public String getXzqhmc2() {
		return xzqhmc2;
	}

	/**
	 * @param xzqhmc2 the xzqhmc2 to set
	 */
	public void setXzqhmc2(String xzqhmc2) {
		this.xzqhmc2 = xzqhmc2;
	}

	/**
	 * @return the fxlxmc1
	 */
	public String getFxlxmc1() {
		return fxlxmc1;
	}

	/**
	 * @param fxlxmc1 the fxlxmc1 to set
	 */
	public void setFxlxmc1(String fxlxmc1) {
		this.fxlxmc1 = fxlxmc1;
	}

	/**
	 * @return the queryMode
	 */
	public String getQueryMode() {
		return queryMode;
	}

	/**
	 * @param queryMode the queryMode to set
	 */
	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	/**
	 * @return the clpp
	 */
	public String getClpp() {
		return clpp;
	}

	/**
	 * @param clpp the clpp to set
	 */
	public void setClpp(String clpp) {
		this.clpp = clpp;
	}

	/**
	 * @return the syxz
	 */
	public String getSyxz() {
		return syxz;
	}

	/**
	 * @param syxz the syxz to set
	 */
	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}

	/**
	 * @return the clsyr
	 */
	public String getClsyr() {
		return clsyr;
	}

	/**
	 * @param clsyr the clsyr to set
	 */
	public void setClsyr(String clsyr) {
		this.clsyr = clsyr;
	}

	/**
	 * @return the clzt
	 */
	public String getClzt() {
		return clzt;
	}

	/**
	 * @param clzt the clzt to set
	 */
	public void setClzt(String clzt) {
		this.clzt = clzt;
	}

	/**
	 * @return the jpyy
	 */
	public String getJpyy() {
		return jpyy;
	}

	/**
	 * @param jpyy the jpyy to set
	 */
	public void setJpyy(String jpyy) {
		this.jpyy = jpyy;
	}

	/**
	 * @return the csys
	 */
	public String getCsys() {
		return csys;
	}

	/**
	 * @param csys the csys to set
	 */
	public void setCsys(String csys) {
		this.csys = csys;
	}

	/**
	 * @return the tpdz1
	 */
	public String getTpdz1() {
		return tpdz1;
	}

	/**
	 * @param tpdz1 the tpdz1 to set
	 */
	public void setTpdz1(String tpdz1) {
		this.tpdz1 = tpdz1;
	}

	/**
	 * @return the tpdz2
	 */
	public String getTpdz2() {
		return tpdz2;
	}

	/**
	 * @param tpdz2 the tpdz2 to set
	 */
	public void setTpdz2(String tpdz2) {
		this.tpdz2 = tpdz2;
	}

	/**
	 * @return the yxqz
	 */
	public String getYxqz() {
		return yxqz;
	}

	/**
	 * @param yxqz the yxqz to set
	 */
	public void setYxqz(String yxqz) {
		this.yxqz = yxqz;
	}

	/**
	 * @return the syxzmc
	 */
	public String getSyxzmc() {
		return syxzmc;
	}

	/**
	 * @param syxzmc the syxzmc to set
	 */
	public void setSyxzmc(String syxzmc) {
		this.syxzmc = syxzmc;
	}

	/**
	 * @return the clztmc
	 */
	public String getClztmc() {
		return clztmc;
	}

	/**
	 * @param clztmc the clztmc to set
	 */
	public void setClztmc(String clztmc) {
		this.clztmc = clztmc;
	}

	/**
	 * @return the jpyymc
	 */
	public String getJpyymc() {
		return jpyymc;
	}

	/**
	 * @param jpyymc the jpyymc to set
	 */
	public void setJpyymc(String jpyymc) {
		this.jpyymc = jpyymc;
	}

	/**
	 * @return the csysmc
	 */
	public String getCsysmc() {
		return csysmc;
	}

	/**
	 * @param csysmc the csysmc to set
	 */
	public void setCsysmc(String csysmc) {
		this.csysmc = csysmc;
	}

	public String getGcsj1() {
		return gcsj1;
	}

	public void setGcsj1(String gcsj1) {
		this.gcsj1 = gcsj1;
	}

	public String getGcsj2() {
		return gcsj2;
	}

	public void setGcsj2(String gcsj2) {
		this.gcsj2 = gcsj2;
	}

	public String getCdh1() {
		return cdh1;
	}

	public void setCdh1(String cdh1) {
		this.cdh1 = cdh1;
	}

	public String getCdh2() {
		return cdh2;
	}

	public void setCdh2(String cdh2) {
		this.cdh2 = cdh2;
	}
}