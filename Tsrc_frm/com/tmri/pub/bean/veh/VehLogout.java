/***********************************************************************
 * Module:  Vehicle_logout.java
 * From Table:  VEHICLE_LOGOUT
 * 源自:  注销/转出机动车信息
 * Author:  Shi Jianrong
 ***********************************************************************/
package com.tmri.pub.bean.veh;

import java.io.Serializable;

public class VehLogout implements Serializable {
	private String xh = ""; // 机动车序号
	private String hpzl = ""; // 号牌种类
	private String hphm = ""; // 号牌号码
	private String clpp1 = ""; // 中文品牌
	private String clxh = ""; // 车辆型号
	private String clpp2 = ""; // 英文品牌
	private String gcjk = ""; // 国产/进口
	private String zzg = ""; // 制造国
	private String zzcmc = ""; // 制造厂名称
	private String clsbdh = ""; // 车辆识别代号
	private String fdjh = ""; // 发动机号
	private String cllx = ""; // 车辆类型
	private String csys = ""; // 车身颜色
	private String syxz = ""; // 使用性质
	private String sfzmhm = ""; // 身份证明号码
	private String sfzmmc = ""; // 身份证明名称
	private String syr = ""; // 机动车所有人
	private String syq = ""; // 所有权
	private String ccdjrq = ""; // 初次登记日期
	private String djrq = ""; // 最近定检日期
	private String yxqz = ""; // 检验有效期止
	private String qzbfqz = ""; // 强制报废期止
	private String fzjg = ""; // 发证机关
	private String glbm = ""; // 管理部门
	private String fprq = ""; // 发牌日期
	private String fzrq = ""; // 发行驶证日期
	private String fdjrq = ""; // 发登记证书日期
	private String fhgzrq = ""; // 发合格证日期
	private String bxzzrq = ""; // 保险终止日期
	private String bpcs = ""; // 补领号牌次数
	private String bzcs = ""; // 补领行驶证次数
	private String bdjcs = ""; // 补/换领证书次数
	private String djzsbh = ""; // 登记证书编号
	private String zdjzshs = ""; // 制登记证书行数
	private String dabh = ""; // 档案编号
	private String xzqh = ""; // 管理辖区
	private String zt = ""; // 机动车状态
	private String dybj = ""; // 抵押标记
	private String jbr = ""; // 经办人
	private String clly = ""; // 车辆来源
	private String lsh = ""; // 注册流水号
	private String fdjxh = ""; // 发动机型号
	private String rlzl = ""; // 燃料种类
	private String pl = ""; // 排量
	private String gl = ""; // 功率
	private String zxxs = ""; // 转向形式
	private String cwkc = ""; // 车外廓长
	private String cwkk = ""; // 车外廓宽
	private String cwkg = ""; // 车外廓高
	private String hxnbcd = ""; // 货箱内部长度
	private String hxnbkd = ""; // 货箱内部宽度
	private String hxnbgd = ""; // 货箱内部高度
	private String gbthps = ""; // 钢板弹簧片数
	private String zs = ""; // 轴数
	private String zj = ""; // 轴距
	private String qlj = ""; // 前轮距
	private String hlj = ""; // 后轮距
	private String ltgg = ""; // 轮胎规格
	private String lts = ""; // 轮胎数
	private String zzl = ""; // 总质量
	private String zbzl = ""; // 整备质量
	private String hdzzl = ""; // 核定载质量
	private String hdzk = ""; // 核定载客
	private String zqyzl = ""; // 准牵引总质量
	private String qpzk = ""; // 驾驶室前排载客人数
	private String hpzk = ""; // 驾驶室后排载客人数
	private String hbdbqk = ""; // 环保达标情况
	private String ccrq = ""; // 出厂日期
	private String hdfs = ""; // 获得方式
	private String llpz1 = ""; // 来历凭证1
	private String pzbh1 = ""; // 凭证编号1
	private String llpz2 = ""; // 来历凭证2
	private String pzbh2 = ""; // 凭证编号2
	private String xsdw = ""; // 销售单位
	private String xsjg = ""; // 销售价格
	private String xsrq = ""; // 销售日期
	private String jkpz = ""; // 进口凭证
	private String jkpzhm = ""; // 进口凭证号码
	private String hgzbh = ""; // 合格证编号
	private String nszm = ""; // 纳税证明
	private String nszmbh = ""; // 纳税证明编号
	private String gxrq = ""; // 更新日期
	private String xgzl = ""; // 相关资料
	private String qmbh = ""; // 前膜编号
	private String hmbh = ""; // 后膜编号
	private String bz = ""; // 备注
	private String ywlx = ""; // 业务类型
	private String zxyy = ""; // 注销原因
	private String zxrq = ""; // 注销日期
	private String hsqymc = ""; // 回收企业名称
	private String zxjbr = ""; // 注销经办人
	private String zxjg = ""; // 注销机关(异地)
	private String zxxgzl = ""; // 注销相关资料
	private String ggrq = ""; // 公告日期
	private String zxbz = ""; // 注销备注
	private String zxlsh = ""; // 注销流水号
	private String jyw = ""; // 校验位
	private String zsxzqh = ""; // 住所行政区划
	private String zsxxdz = ""; // 住所详细地址
	private String yzbm1 = ""; // 住所邮政编码
	private String lxdh = ""; // 联系电话
	private String zzz = ""; // 暂住居住证明
	private String zzxzqh = ""; // 暂住行政区划
	private String zzxxdz = ""; // 暂住详细地址
	private String yzbm2 = ""; // 暂住邮政编码
	private String zdyzt = ""; // 自定义状态
	private String yxh = ""; // 原机动车序号
	private String hshp = ""; // 回收号牌
	private String hsxsz = ""; // 回收行驶证
	private String hsdjzs = ""; // 回收登记证书
	private String cfjg = ""; // 处罚机关
	private String jdsbh = ""; // 处罚决定书编号
	private String cfsj = ""; // 处罚时间
	private String jxmj = ""; // 监销民警
	private String dzyx = ""; // 电子邮箱
	private String xszbh = ""; // 行驶证证芯编号
	private String sjhm = ""; // 手机号码
	private String jyhgbzbh = ""; //
	private String rowcounter = "";

	private String dwbh = ""; // 单位编号
	private String yqjyqzbfqz = "";//逾期检验强制报废期止
    private String yqjyqz2 = "";//逾期２个检验周期期止
    private String fdjgs = "";//发动机缸数
    private String sfyzhgn = "";//专项做车是否有载货功能 1-是；2-否
    private String syqsrq = "";
    
	public String getYqjyqzbfqz() {
		return yqjyqzbfqz;
	}

	public void setYqjyqzbfqz(String yqjyqzbfqz) {
		this.yqjyqzbfqz = yqjyqzbfqz;
	}

	public String getYqjyqz2() {
		return yqjyqz2;
	}

	public void setYqjyqz2(String yqjyqz2) {
		this.yqjyqz2 = yqjyqz2;
	}

	public String getFdjgs() {
		return fdjgs;
	}

	public void setFdjgs(String fdjgs) {
		this.fdjgs = fdjgs;
	}

	public String getSfyzhgn() {
		return sfyzhgn;
	}

	public void setSfyzhgn(String sfyzhgn) {
		this.sfyzhgn = sfyzhgn;
	}

	public String getSyqsrq() {
		return syqsrq;
	}

	public void setSyqsrq(String syqsrq) {
		this.syqsrq = syqsrq;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getClpp1() {
		return clpp1;
	}

	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}

	public String getClxh() {
		return clxh;
	}

	public void setClxh(String clxh) {
		this.clxh = clxh;
	}

	public String getClpp2() {
		return clpp2;
	}

	public void setClpp2(String clpp2) {
		this.clpp2 = clpp2;
	}

	public String getGcjk() {
		return gcjk;
	}

	public void setGcjk(String gcjk) {
		this.gcjk = gcjk;
	}

	public String getZzg() {
		return zzg;
	}

	public void setZzg(String zzg) {
		this.zzg = zzg;
	}

	public String getZzcmc() {
		return zzcmc;
	}

	public void setZzcmc(String zzcmc) {
		this.zzcmc = zzcmc;
	}

	public String getClsbdh() {
		return clsbdh;
	}

	public void setClsbdh(String clsbdh) {
		this.clsbdh = clsbdh;
	}

	public String getFdjh() {
		return fdjh;
	}

	public void setFdjh(String fdjh) {
		this.fdjh = fdjh;
	}

	public String getCllx() {
		return cllx;
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	public String getCsys() {
		return csys;
	}

	public void setCsys(String csys) {
		this.csys = csys;
	}

	public String getSyxz() {
		return syxz;
	}

	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}

	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	public String getSfzmmc() {
		return sfzmmc;
	}

	public void setSfzmmc(String sfzmmc) {
		this.sfzmmc = sfzmmc;
	}

	public String getSyr() {
		return syr;
	}

	public void setSyr(String syr) {
		this.syr = syr;
	}

	public String getSyq() {
		return syq;
	}

	public void setSyq(String syq) {
		this.syq = syq;
	}

	public String getCcdjrq() {
		return ccdjrq;
	}

	public void setCcdjrq(String ccdjrq) {
		this.ccdjrq = ccdjrq;
	}

	public String getDjrq() {
		return djrq;
	}

	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	public String getYxqz() {
		return yxqz;
	}

	public void setYxqz(String yxqz) {
		this.yxqz = yxqz;
	}

	public String getQzbfqz() {
		return qzbfqz;
	}

	public void setQzbfqz(String qzbfqz) {
		this.qzbfqz = qzbfqz;
	}

	public String getFzjg() {
		return fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getGlbm() {
		return glbm;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getFprq() {
		return fprq;
	}

	public void setFprq(String fprq) {
		this.fprq = fprq;
	}

	public String getFzrq() {
		return fzrq;
	}

	public void setFzrq(String fzrq) {
		this.fzrq = fzrq;
	}

	public String getFdjrq() {
		return fdjrq;
	}

	public void setFdjrq(String fdjrq) {
		this.fdjrq = fdjrq;
	}

	public String getFhgzrq() {
		return fhgzrq;
	}

	public void setFhgzrq(String fhgzrq) {
		this.fhgzrq = fhgzrq;
	}

	public String getBxzzrq() {
		return bxzzrq;
	}

	public void setBxzzrq(String bxzzrq) {
		this.bxzzrq = bxzzrq;
	}

	public String getBpcs() {
		return bpcs;
	}

	public void setBpcs(String bpcs) {
		this.bpcs = bpcs;
	}

	public String getBzcs() {
		return bzcs;
	}

	public void setBzcs(String bzcs) {
		this.bzcs = bzcs;
	}

	public String getBdjcs() {
		return bdjcs;
	}

	public void setBdjcs(String bdjcs) {
		this.bdjcs = bdjcs;
	}

	public String getDjzsbh() {
		return djzsbh;
	}

	public void setDjzsbh(String djzsbh) {
		this.djzsbh = djzsbh;
	}

	public String getZdjzshs() {
		return zdjzshs;
	}

	public void setZdjzshs(String zdjzshs) {
		this.zdjzshs = zdjzshs;
	}

	public String getDabh() {
		return dabh;
	}

	public void setDabh(String dabh) {
		this.dabh = dabh;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getDybj() {
		return dybj;
	}

	public void setDybj(String dybj) {
		this.dybj = dybj;
	}

	public String getJbr() {
		return jbr;
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}

	public String getClly() {
		return clly;
	}

	public void setClly(String clly) {
		this.clly = clly;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getFdjxh() {
		return fdjxh;
	}

	public void setFdjxh(String fdjxh) {
		this.fdjxh = fdjxh;
	}

	public String getRlzl() {
		return rlzl;
	}

	public void setRlzl(String rlzl) {
		this.rlzl = rlzl;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getGl() {
		return gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getZxxs() {
		return zxxs;
	}

	public void setZxxs(String zxxs) {
		this.zxxs = zxxs;
	}

	public String getCwkc() {
		return cwkc;
	}

	public void setCwkc(String cwkc) {
		this.cwkc = cwkc;
	}

	public String getCwkk() {
		return cwkk;
	}

	public void setCwkk(String cwkk) {
		this.cwkk = cwkk;
	}

	public String getCwkg() {
		return cwkg;
	}

	public void setCwkg(String cwkg) {
		this.cwkg = cwkg;
	}

	public String getHxnbcd() {
		return hxnbcd;
	}

	public void setHxnbcd(String hxnbcd) {
		this.hxnbcd = hxnbcd;
	}

	public String getHxnbkd() {
		return hxnbkd;
	}

	public void setHxnbkd(String hxnbkd) {
		this.hxnbkd = hxnbkd;
	}

	public String getHxnbgd() {
		return hxnbgd;
	}

	public void setHxnbgd(String hxnbgd) {
		this.hxnbgd = hxnbgd;
	}

	public String getGbthps() {
		return gbthps;
	}

	public void setGbthps(String gbthps) {
		this.gbthps = gbthps;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public String getZj() {
		return zj;
	}

	public void setZj(String zj) {
		this.zj = zj;
	}

	public String getQlj() {
		return qlj;
	}

	public void setQlj(String qlj) {
		this.qlj = qlj;
	}

	public String getHlj() {
		return hlj;
	}

	public void setHlj(String hlj) {
		this.hlj = hlj;
	}

	public String getLtgg() {
		return ltgg;
	}

	public void setLtgg(String ltgg) {
		this.ltgg = ltgg;
	}

	public String getLts() {
		return lts;
	}

	public void setLts(String lts) {
		this.lts = lts;
	}

	public String getZzl() {
		return zzl;
	}

	public void setZzl(String zzl) {
		this.zzl = zzl;
	}

	public String getZbzl() {
		return zbzl;
	}

	public void setZbzl(String zbzl) {
		this.zbzl = zbzl;
	}

	public String getHdzzl() {
		return hdzzl;
	}

	public void setHdzzl(String hdzzl) {
		this.hdzzl = hdzzl;
	}

	public String getHdzk() {
		return hdzk;
	}

	public void setHdzk(String hdzk) {
		this.hdzk = hdzk;
	}

	public String getZqyzl() {
		return zqyzl;
	}

	public void setZqyzl(String zqyzl) {
		this.zqyzl = zqyzl;
	}

	public String getQpzk() {
		return qpzk;
	}

	public void setQpzk(String qpzk) {
		this.qpzk = qpzk;
	}

	public String getHpzk() {
		return hpzk;
	}

	public void setHpzk(String hpzk) {
		this.hpzk = hpzk;
	}

	public String getHbdbqk() {
		return hbdbqk;
	}

	public void setHbdbqk(String hbdbqk) {
		this.hbdbqk = hbdbqk;
	}

	public String getCcrq() {
		return ccrq;
	}

	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}

	public String getHdfs() {
		return hdfs;
	}

	public void setHdfs(String hdfs) {
		this.hdfs = hdfs;
	}

	public String getLlpz1() {
		return llpz1;
	}

	public void setLlpz1(String llpz1) {
		this.llpz1 = llpz1;
	}

	public String getPzbh1() {
		return pzbh1;
	}

	public void setPzbh1(String pzbh1) {
		this.pzbh1 = pzbh1;
	}

	public String getLlpz2() {
		return llpz2;
	}

	public void setLlpz2(String llpz2) {
		this.llpz2 = llpz2;
	}

	public String getPzbh2() {
		return pzbh2;
	}

	public void setPzbh2(String pzbh2) {
		this.pzbh2 = pzbh2;
	}

	public String getXsdw() {
		return xsdw;
	}

	public void setXsdw(String xsdw) {
		this.xsdw = xsdw;
	}

	public String getXsjg() {
		return xsjg;
	}

	public void setXsjg(String xsjg) {
		this.xsjg = xsjg;
	}

	public String getXsrq() {
		return xsrq;
	}

	public void setXsrq(String xsrq) {
		this.xsrq = xsrq;
	}

	public String getJkpz() {
		return jkpz;
	}

	public void setJkpz(String jkpz) {
		this.jkpz = jkpz;
	}

	public String getJkpzhm() {
		return jkpzhm;
	}

	public void setJkpzhm(String jkpzhm) {
		this.jkpzhm = jkpzhm;
	}

	public String getHgzbh() {
		return hgzbh;
	}

	public void setHgzbh(String hgzbh) {
		this.hgzbh = hgzbh;
	}

	public String getNszm() {
		return nszm;
	}

	public void setNszm(String nszm) {
		this.nszm = nszm;
	}

	public String getNszmbh() {
		return nszmbh;
	}

	public void setNszmbh(String nszmbh) {
		this.nszmbh = nszmbh;
	}

	public String getGxrq() {
		return gxrq;
	}

	public void setGxrq(String gxrq) {
		this.gxrq = gxrq;
	}

	public String getXgzl() {
		return xgzl;
	}

	public void setXgzl(String xgzl) {
		this.xgzl = xgzl;
	}

	public String getQmbh() {
		return qmbh;
	}

	public void setQmbh(String qmbh) {
		this.qmbh = qmbh;
	}

	public String getHmbh() {
		return hmbh;
	}

	public void setHmbh(String hmbh) {
		this.hmbh = hmbh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getZxyy() {
		return zxyy;
	}

	public void setZxyy(String zxyy) {
		this.zxyy = zxyy;
	}

	public String getZxrq() {
		return zxrq;
	}

	public void setZxrq(String zxrq) {
		this.zxrq = zxrq;
	}

	public String getHsqymc() {
		return hsqymc;
	}

	public void setHsqymc(String hsqymc) {
		this.hsqymc = hsqymc;
	}

	public String getZxjbr() {
		return zxjbr;
	}

	public void setZxjbr(String zxjbr) {
		this.zxjbr = zxjbr;
	}

	public String getZxjg() {
		return zxjg;
	}

	public void setZxjg(String zxjg) {
		this.zxjg = zxjg;
	}

	public String getZxxgzl() {
		return zxxgzl;
	}

	public void setZxxgzl(String zxxgzl) {
		this.zxxgzl = zxxgzl;
	}

	public String getGgrq() {
		return ggrq;
	}

	public void setGgrq(String ggrq) {
		this.ggrq = ggrq;
	}

	public String getZxbz() {
		return zxbz;
	}

	public void setZxbz(String zxbz) {
		this.zxbz = zxbz;
	}

	public String getZxlsh() {
		return zxlsh;
	}

	public void setZxlsh(String zxlsh) {
		this.zxlsh = zxlsh;
	}

	public String getJyw() {
		return jyw;
	}

	public void setJyw(String jyw) {
		this.jyw = jyw;
	}

	public String getZsxzqh() {
		return zsxzqh;
	}

	public void setZsxzqh(String zsxzqh) {
		this.zsxzqh = zsxzqh;
	}

	public String getZsxxdz() {
		return zsxxdz;
	}

	public void setZsxxdz(String zsxxdz) {
		this.zsxxdz = zsxxdz;
	}

	public String getYzbm1() {
		return yzbm1;
	}

	public void setYzbm1(String yzbm1) {
		this.yzbm1 = yzbm1;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getZzz() {
		return zzz;
	}

	public void setZzz(String zzz) {
		this.zzz = zzz;
	}

	public String getZzxzqh() {
		return zzxzqh;
	}

	public void setZzxzqh(String zzxzqh) {
		this.zzxzqh = zzxzqh;
	}

	public String getZzxxdz() {
		return zzxxdz;
	}

	public void setZzxxdz(String zzxxdz) {
		this.zzxxdz = zzxxdz;
	}

	public String getYzbm2() {
		return yzbm2;
	}

	public void setYzbm2(String yzbm2) {
		this.yzbm2 = yzbm2;
	}

	public String getZdyzt() {
		return zdyzt;
	}

	public void setZdyzt(String zdyzt) {
		this.zdyzt = zdyzt;
	}

	public String getYxh() {
		return yxh;
	}

	public void setYxh(String yxh) {
		this.yxh = yxh;
	}

	public String getHshp() {
		return hshp;
	}

	public void setHshp(String hshp) {
		this.hshp = hshp;
	}

	public String getHsxsz() {
		return hsxsz;
	}

	public void setHsxsz(String hsxsz) {
		this.hsxsz = hsxsz;
	}

	public String getHsdjzs() {
		return hsdjzs;
	}

	public void setHsdjzs(String hsdjzs) {
		this.hsdjzs = hsdjzs;
	}

	public String getCfjg() {
		return cfjg;
	}

	public void setCfjg(String cfjg) {
		this.cfjg = cfjg;
	}

	public String getJdsbh() {
		return jdsbh;
	}

	public void setJdsbh(String jdsbh) {
		this.jdsbh = jdsbh;
	}

	public String getCfsj() {
		return cfsj;
	}

	public void setCfsj(String cfsj) {
		this.cfsj = cfsj;
	}

	public String getJxmj() {
		return jxmj;
	}

	public void setJxmj(String jxmj) {
		this.jxmj = jxmj;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public String getXszbh() {
		return xszbh;
	}

	public void setXszbh(String xszbh) {
		this.xszbh = xszbh;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getJyhgbzbh() {
		return jyhgbzbh;
	}

	public void setJyhgbzbh(String jyhgbzbh) {
		this.jyhgbzbh = jyhgbzbh;
	}

	public String getRowcounter() {
		return rowcounter;
	}

	public void setRowcounter(String rowcounter) {
		this.rowcounter = rowcounter;
	}

	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	
	



}
