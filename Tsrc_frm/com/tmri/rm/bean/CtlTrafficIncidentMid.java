package com.tmri.rm.bean;


public class CtlTrafficIncidentMid {
	private String sbly; // "设备来源  1-已备案的事件检测设备；	2-已备案的视频摄像机；	"
	private String sbbh; // 设备编号
	private String jcsj; // 检测事件
	private String sjlx; // 事件类型 dmlb ='6627' and xtlb='66
	private String glbm; // 管理部门
	private String xzqh; // 行政区划
	private String dldm; // 道路代码
	private String lddm; // 路口路段代码
	private String jd; // 经度
	private String wd; // 纬度
	private String tplj; // 图片路径
	private String tp1; // 图片1文件名
	private String tp2; // 图片2文件名
	private String splj; // 视频路径
	private String sp1; // 视频1文件名
	private String sjbh; // 事件编号 6位管理部门头+10位序列
	private String xxly; // 信息来源 1-内网；2-专网
	private String rdlsj; // 入kafka队列时间
	private String zwrksj; // 专网入库时间
	private String nwrksj; // 内网入库时间
	private String bz; // 	

	private byte[] photo1;
	private byte[] photo2;
	private byte[] video1;

	private String tpxx1;
	private String tpxx2;
	private String spxx;

	public String getSbly() {
		return sbly;
	}

	public void setSbly(String sbly) {
		this.sbly = sbly;
	}

	public String getSbbh() {
		return sbbh;
	}

	public void setSbbh(String sbbh) {
		this.sbbh = sbbh;
	}

	public String getJcsj() {
		return jcsj;
	}

	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}

	public String getSjlx() {
		return sjlx;
	}

	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
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

	public String getSplj() {
		return splj;
	}

	public void setSplj(String splj) {
		this.splj = splj;
	}

	public String getSp1() {
		return sp1;
	}

	public void setSp1(String sp1) {
		this.sp1 = sp1;
	}

	public String getSjbh() {
		return sjbh;
	}

	public void setSjbh(String sjbh) {
		this.sjbh = sjbh;
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

	public String getSpxx() {
		return spxx;
	}

	public void setSpxx(String spxx) {
		this.spxx = spxx;
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

	public byte[] getVideo1() {
		return video1;
	}

	public void setVideo1(byte[] video1) {
		this.video1 = video1;
	}
}
