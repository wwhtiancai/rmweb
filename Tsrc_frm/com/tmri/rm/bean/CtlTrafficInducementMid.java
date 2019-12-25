package com.tmri.rm.bean;


public class CtlTrafficInducementMid {
	private String sbbh;// 设备编号
	private String fbsj;// 发布时间交通诱导信息发布时间, yyyy-MM-ddhh24:mi:ss
	private String xxlx;// 信息类型：1-交通路况信息；2-交通管制信息；3-道路施工信息；4-交通事故信息；5-交通事件信息；6-停车场位信息；7-其他发布信息
	private String qssj;// 展示起始时间
	private String jssj;// 展示结束时间
	private String fbfs;// 发布方式 dmlb ='6822' and xtlb ='68'
	private String glbm;// 管理部门
	private String xzqh;// 行政区划
	private String dldm;// 道路代码
	private String lddm;// 路口路段代码
	private String jd;// 经度
	private String wd;// 纬度
	private String fbnr;// 发布内容
	private String fbyh;// 发布用户
	private String jlzt;// 记录状态 1-正常；0-作废
	private String tplj;// 图片路径
	private String tp1;// 图片1文件名
	private String tp2;// 图片2文件名
	private String xxly;// 信息来源 1-内网；2-专网
	private String rdlsj;// 入kafka队列时间
	private String zwrksj;// 专网入库时间
	private String nwrksj;// 内网入库时间
	private String zfsj;// 作废时间
	private String bz;// 	
	
	private byte[] photo1;
	private byte[] photo2;
	private String tpxx1;
	private String tpxx2;

	public String getSbbh() {
		return sbbh;
	}

	public void setSbbh(String sbbh) {
		this.sbbh = sbbh;
	}

	public String getFbsj() {
		return fbsj;
	}

	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}

	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}

	public String getQssj() {
		return qssj;
	}

	public void setQssj(String qssj) {
		this.qssj = qssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getFbfs() {
		return fbfs;
	}

	public void setFbfs(String fbfs) {
		this.fbfs = fbfs;
	}

	public String getGlbm() {
		return glbm;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getDldm() {
		return dldm;
	}

	public void setDldm(String dldm) {
		this.dldm = dldm;
	}

	public String getLddm() {
		return lddm;
	}

	public void setLddm(String lddm) {
		this.lddm = lddm;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getFbnr() {
		return fbnr;
	}

	public void setFbnr(String fbnr) {
		this.fbnr = fbnr;
	}

	public String getFbyh() {
		return fbyh;
	}

	public void setFbyh(String fbyh) {
		this.fbyh = fbyh;
	}

	public String getJlzt() {
		return jlzt;
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	public String getTplj() {
		return tplj;
	}

	public void setTplj(String tplj) {
		this.tplj = tplj;
	}

	public String getTp1() {
		return tp1;
	}

	public void setTp1(String tp1) {
		this.tp1 = tp1;
	}

	public String getTp2() {
		return tp2;
	}

	public void setTp2(String tp2) {
		this.tp2 = tp2;
	}

	public String getXxly() {
		return xxly;
	}

	public void setXxly(String xxly) {
		this.xxly = xxly;
	}

	public String getRdlsj() {
		return rdlsj;
	}

	public void setRdlsj(String rdlsj) {
		this.rdlsj = rdlsj;
	}

	public String getZwrksj() {
		return zwrksj;
	}

	public void setZwrksj(String zwrksj) {
		this.zwrksj = zwrksj;
	}

	public String getNwrksj() {
		return nwrksj;
	}

	public void setNwrksj(String nwrksj) {
		this.nwrksj = nwrksj;
	}

	public String getZfsj() {
		return zfsj;
	}

	public void setZfsj(String zfsj) {
		this.zfsj = zfsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getTpxx1() {
		return tpxx1;
	}

	public void setTpxx1(String tpxx1) {
		this.tpxx1 = tpxx1;
	}

	public String getTpxx2() {
		return tpxx2;
	}

	public void setTpxx2(String tpxx2) {
		this.tpxx2 = tpxx2;
	}

	public byte[] getPhoto1() {
		return photo1;
	}

	public void setPhoto1(byte[] photo1) {
		this.photo1 = photo1;
	}

	public byte[] getPhoto2() {
		return photo2;
	}

	public void setPhoto2(byte[] photo2) {
		this.photo2 = photo2;
	}
}
