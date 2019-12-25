package com.tmri.rm.bean;

import java.sql.Timestamp;

public class AnaSuspResultBean {
	/**
	 * 序号
	 */
	private String xh;

	/**
	 * 过车序号1
	 */
	private String gcxh1;

	/**
	 * 过车序号2
	 */
	private String gcxh2;

	/**
	 * 卡口编号1
	 */
	private String kkbh1;

	/**
	 * 卡口编号2
	 */
	private String kkbh2;

	/**
	 * 过车时间1
	 */
	private String gcsj1;

	/**
	 * 过车时间2
	 */
	private String gcsj2;

	/**
	 * 方向类型1
	 */
	private String fxlx1;

	/**
	 * 方向类型2
	 */
	private String fxlx2;

	/**
	 * 车道号1
	 */
	private String cdh1;

	/**
	 * 车道号2
	 */
	private String cdh2;

	/**
	 * 号牌种类
	 */
	private String hpzl;

	/**
	 * 号牌号码
	 */
	private String hphm;

	/**
	 * 嫌疑类型01-大数据比对套牌车02-综合平台布控套牌车03-逾期未报废重点车辆
	 */
	private String xylx;

	/**
	 * 二次识别标记
	 */
	private String sbbj;

	/**
	 * 号牌号码与二次识别结果是否一致
	 */
	private String hsbbjpyz;

	/**
	 * 入库时间
	 */
	private Timestamp rksj;

	/**
	 * 识别时间
	 */
	private Timestamp sbsj;

	/**
	 * 核查结果0-错误 1-正确 9-待核查
	 */
	private String hcjg;

	/**
	 * 错误原因1-号牌号码识别错误2-图片中无机动车号牌信息3-无法显示图片
	 */
	private String cwyy;

	/**
	 * 卡口1管理部门
	 */
	private String glbm1;

	/**
	 * 卡口1道路代码
	 */
	private String dldm1;

	/**
	 * 卡口1类型
	 */
	private String kklx1;

	/**
	 * 卡口1行政区划
	 */
	private String xzqh1;

	/**
	 * 卡口1拦截条件
	 */
	private String ljtj1;

	/**
	 * 卡口2管理部门
	 */
	private String glbm2;

	/**
	 * 卡口2道路代码
	 */
	private String dldm2;

	/**
	 * 卡口2类型
	 */
	private String kklx2;

	/**
	 * 卡口2行政区划
	 */
	private String xzqh2;

	/**
	 * 卡口2拦截条件
	 */
	private String ljtj2;

	/**
	 * 原始序号
	 */
	private String ysxh;
	/**
	 *车辆品牌
	 */
	private String clpp;
	/**
	 * 使用性质
	 */
	private String syxz;
	/**
	 * 车辆所有人
	 */
	private String clsyr;
	/**
	 * 车辆状态
	 */
	private String clzt;
	/**
	 * 假牌原因 1-未登记 2-已注销 3-已强制报废
	 */
	private String jpyy;
	/**
	 * 车身颜色
	 */
	private String csys;
	/**
	 * 片地址1
	 */
	private String tpdz1;
	/**
	 * 片地址2
	 */
	private String tpdz2;
	/**
	 * 有效期至
	 */
	private String yxqz;

	/**
	 * 过车时间展示
	 */
	private String gcsjzs1;
	/**
	 * 过车时间2展示
	 */
	private String gcsjzs2;
	/**
	 * 识别时间展示
	 */
	private String sbsjzs;
	/**
	 * 嫌疑类型名称
	 */
	private String xylxmc;

	/**
	 * 号牌种类名称
	 */
	private String hpzlmc;

	/**
	 * 卡口名称1
	 */
	private String kkmc1;

	/**
	 * 卡口名称2
	 */
	private String kkmc2;
	/**
	 * 卡口所在省1
	 */
	private String szs1;
	/**
	 * 卡口所在省2
	 */
	private String szs2;
	/**
	 * 行政区划名称1
	 */
	private String xzqhmc1;
	/**
	 * 行政区划名称2
	 */
	private String xzqhmc2;

	/**
	 * 使用性质名称
	 */
	private String syxzmc;
	/**
	 * 车辆状态名称
	 */
	private String clztmc;
	/**
	 * 假牌原因名称
	 */
	private String jpyymc;
	/**
	 * 车身颜色名称
	 */
	private String csysmc;

	/**
	 * 业务数据类型 0为基本数据 1为业务详细数据 2为操作流程数据
	 */
	private Integer servType;

	/**
	 * 识别开始时间
	 */
	private String sbKssj;

	/**
	 * 识别结束时间
	 */
	private String sbJssj;
	/**
	 * 分组总数
	 */
	private Integer fzzs;
	/**
	 * 分组号
	 */
	private Integer fzh;

	/**
	 * 放心类型1名称
	 */
	private String fxlxmc1;

	/**
	 * 0或null为查询xh 1为查询xh+1
	 */
	private String queryMode;

	/**
	 * @返回 序号
	 */
	public String getXh() {
		return xh;
	}

	/**
	 * @param xh 设置 序号
	 */
	public void setXh(String xh) {
		this.xh = xh;
	}

	/**
	 * @返回 过车序号1
	 */
	public String getGcxh1() {
		return gcxh1;
	}

	/**
	 * @param gcxh1 设置 过车序号1
	 */
	public void setGcxh1(String gcxh1) {
		this.gcxh1 = gcxh1;
	}

	/**
	 * @返回 过车序号2
	 */
	public String getGcxh2() {
		return gcxh2;
	}

	/**
	 * @param gcxh2 设置 过车序号2
	 */
	public void setGcxh2(String gcxh2) {
		this.gcxh2 = gcxh2;
	}

	/**
	 * @返回 卡口编号1
	 */
	public String getKkbh1() {
		return kkbh1;
	}

	/**
	 * @param kkbh1 设置 卡口编号1
	 */
	public void setKkbh1(String kkbh1) {
		this.kkbh1 = kkbh1;
	}

	/**
	 * @返回 卡口编号2
	 */
	public String getKkbh2() {
		return kkbh2;
	}

	/**
	 * @param kkbh2 设置 卡口编号2
	 */
	public void setKkbh2(String kkbh2) {
		this.kkbh2 = kkbh2;
	}

	/**
	 * @返回 方向类型1
	 */
	public String getFxlx1() {
		return fxlx1;
	}

	/**
	 * @param fxlx1 设置 方向类型1
	 */
	public void setFxlx1(String fxlx1) {
		this.fxlx1 = fxlx1;
	}

	/**
	 * @返回 方向类型2
	 */
	public String getFxlx2() {
		return fxlx2;
	}

	/**
	 * @param fxlx2 设置 方向类型2
	 */
	public void setFxlx2(String fxlx2) {
		this.fxlx2 = fxlx2;
	}

	/**
	 * @返回 号牌种类
	 */
	public String getHpzl() {
		return hpzl;
	}

	/**
	 * @param hpzl 设置 号牌种类
	 */
	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	/**
	 * @返回 号牌号码
	 */
	public String getHphm() {
		return hphm;
	}

	/**
	 * @param hphm 设置 号牌号码
	 */
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	/**
	 * @返回 嫌疑类型01-大数据比对套牌车02-综合平台布控套牌车03-逾期未报废重点车辆
	 */
	public String getXylx() {
		return xylx;
	}

	/**
	 * @param xylx 设置 嫌疑类型01-大数据比对套牌车02-综合平台布控套牌车03-逾期未报废重点车辆
	 */
	public void setXylx(String xylx) {
		this.xylx = xylx;
	}

	/**
	 * @返回 二次识别标记
	 */
	public String getSbbj() {
		return sbbj;
	}

	/**
	 * @param sbbj 设置 二次识别标记
	 */
	public void setSbbj(String sbbj) {
		this.sbbj = sbbj;
	}

	/**
	 * @返回 号牌号码与二次识别结果是否一致
	 */
	public String getHsbbjpyz() {
		return hsbbjpyz;
	}

	/**
	 * @param hpyz 设置 号牌号码与二次识别结果是否一致
	 */
	public void setHsbbjpyz(String hpyz) {
		this.hsbbjpyz = hpyz;
	}

	/**
	 * @返回 入库时间
	 */
	public Timestamp getRksj() {
		return rksj;
	}

	/**
	 * @param rksj 设置 入库时间
	 */
	public void setRksj(Timestamp rksj) {
		this.rksj = rksj;
	}

	/**
	 * @返回 识别时间
	 */
	public Timestamp getSbsj() {
		return sbsj;
	}

	/**
	 * @param sbsj 设置 识别时间
	 */
	public void setSbsj(Timestamp sbsj) {
		this.sbsj = sbsj;
	}

	/**
	 * @返回 核查结果0-错误 1-正确 9-待核查
	 */
	public String getHcjg() {
		return hcjg;
	}

	/**
	 * @param hcjg 设置 核查结果0-错误 1-正确 9-待核查
	 */
	public void setHcjg(String hcjg) {
		this.hcjg = hcjg;
	}

	/**
	 * @返回 错误原因1-号牌号码识别错误2-图片中无机动车号牌信息3-无法显示图片
	 */
	public String getCwyy() {
		return cwyy;
	}

	/**
	 * @param cwyy 设置 错误原因1-号牌号码识别错误2-图片中无机动车号牌信息3-无法显示图片
	 */
	public void setCwyy(String cwyy) {
		this.cwyy = cwyy;
	}

	/**
	 * @返回 卡口1管理部门
	 */
	public String getGlbm1() {
		return glbm1;
	}

	/**
	 * @param glbm1 设置 卡口1管理部门
	 */
	public void setGlbm1(String glbm1) {
		this.glbm1 = glbm1;
	}

	/**
	 * @返回 卡口1道路代码
	 */
	public String getDldm1() {
		return dldm1;
	}

	/**
	 * @param dldm1 设置 卡口1道路代码
	 */
	public void setDldm1(String dldm1) {
		this.dldm1 = dldm1;
	}

	/**
	 * @返回 卡口1类型
	 */
	public String getKklx1() {
		return kklx1;
	}

	/**
	 * @param kklx1 设置 卡口1类型
	 */
	public void setKklx1(String kklx1) {
		this.kklx1 = kklx1;
	}

	/**
	 * @返回 卡口1行政区划
	 */
	public String getXzqh1() {
		return xzqh1;
	}

	/**
	 * @param xzqh1 设置 卡口1行政区划
	 */
	public void setXzqh1(String xzqh1) {
		this.xzqh1 = xzqh1;
	}

	/**
	 * @返回 卡口1拦截条件
	 */
	public String getLjtj1() {
		return ljtj1;
	}

	/**
	 * @param ljtj1 设置 卡口1拦截条件
	 */
	public void setLjtj1(String ljtj1) {
		this.ljtj1 = ljtj1;
	}

	/**
	 * @返回 卡口2管理部门
	 */
	public String getGlbm2() {
		return glbm2;
	}

	/**
	 * @param glbm2 设置 卡口2管理部门
	 */
	public void setGlbm2(String glbm2) {
		this.glbm2 = glbm2;
	}

	/**
	 * @返回 卡口2道路代码
	 */
	public String getDldm2() {
		return dldm2;
	}

	/**
	 * @param dldm2 设置 卡口2道路代码
	 */
	public void setDldm2(String dldm2) {
		this.dldm2 = dldm2;
	}

	/**
	 * @返回 卡口2类型
	 */
	public String getKklx2() {
		return kklx2;
	}

	/**
	 * @param kklx2 设置 卡口2类型
	 */
	public void setKklx2(String kklx2) {
		this.kklx2 = kklx2;
	}

	/**
	 * @返回 卡口2行政区划
	 */
	public String getXzqh2() {
		return xzqh2;
	}

	/**
	 * @param xzqh2 设置 卡口2行政区划
	 */
	public void setXzqh2(String xzqh2) {
		this.xzqh2 = xzqh2;
	}

	/**
	 * @返回 卡口2拦截条件
	 */
	public String getLjtj2() {
		return ljtj2;
	}

	/**
	 * @param ljtj2 设置 卡口2拦截条件
	 */
	public void setLjtj2(String ljtj2) {
		this.ljtj2 = ljtj2;
	}

	/**
	 * @返回 原始序号
	 */
	public String getYsxh() {
		return ysxh;
	}

	/**
	 * @param ysxh 设置 原始序号
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