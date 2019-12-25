package com.tmri.rm.bean;
import com.tmri.share.frm.util.StringUtil;
public class Vehicle{
	private String dzyx; // 电子邮箱
	private String xszbh; // 行驶证证芯编号
	private String sjhm; // 手机号码
	private String jyhgbzbh; // 检验合格标志
	private String dwbh; // 单位编号
	private String xh; // 机动车序号
	private String hpzl; // 号牌种类
	private String hphm; // 号牌号码
	private String clpp1; // 中文品牌
	private String clxh; // 车辆型号
	private String clpp2; // 英文品牌
	private String gcjk; // 国产/进口
	private String zzg; // 制造国
	private String zzcmc; // 制造厂名称
	private String clsbdh; // 车辆识别代号
	private String fdjh; // 发动机号
	private String cllx; // 车辆类型
	private String csys; // 车身颜色
	private String syxz; // 使用性质
	private String sfzmhm; // 身份证明号码
	private String sfzmmc; // 身份证明名称
	private String syr; // 机动车所有人
	private String syq; // 所有权
	private String ccdjrq; // 初次登记日期
	private String djrq; // 最近定检日期
	private String yxqz; // 检验有效期止
	private String qzbfqz; // 强制报废期止
	private String fzjg; // 发证机关
	private String glbm; // 管理部门
	private String fprq; // 发牌日期
	private String fzrq; // 发行驶证日期
	private String fdjrq; // 发登记证书日期
	private String fhgzrq; // 发合格证日期
	private String bxzzrq; // 保险终止日期
	private long bpcs; // 补领号牌次数
	private long bzcs; // 补领行驶证次数
	private long bdjcs; // 补/换领证书次数
	private String djzsbh; // 登记证书编号
	private long zdjzshs; // 制登记证书行数
	private String dabh; // 档案编号
	private String xzqh; // 管理辖区
	private String zt; // 机动车状态
	private String dybj; // 0-未抵押，1-已抵押
	private String jbr; // 经办人
	private String clly; // 1注册2转入3过户
	private String lsh; // 注册流水号
	private String fdjxh; // 发动机型号
	private String rlzl; // 燃料种类
	private long pl; // 排量
	private long gl; // 功率
	private String zxxs; // 转向形式
	private long cwkc; // 车外廓长
	private long cwkk; // 车外廓宽
	private long cwkg; // 车外廓高
	private long hxnbcd; // 货箱内部长度
	private long hxnbkd; // 货箱内部宽度
	private long hxnbgd; // 货箱内部高度
	private long gbthps; // 钢板弹簧片数
	private long zs; // 轴数
	private long zj; // 轴距
	private long qlj; // 前轮距
	private long hlj; // 后轮距
	private String ltgg; // 轮胎规格
	private long lts; // 轮胎数
	private long zzl; // 总质量
	private long zbzl; // 整备质量
	private long hdzzl; // 核定载质量
	private long hdzk; // 核定载客
	private long zqyzl; // 准牵引总质量
	private long qpzk; // 驾驶室前排载客人数
	private long hpzk; // 驾驶室后排载客人数
	private String hbdbqk; // 环保达标情况
	private String ccrq; // 出厂日期
	private String hdfs; // 获得方式
	private String llpz1; // 来历凭证1
	private String pzbh1; // 凭证编号1
	private String llpz2; // 来历凭证2
	private String pzbh2; // 凭证编号2
	private String xsdw; // 销售单位
	private long xsjg; // 销售价格
	private String xsrq; // 销售日期
	private String jkpz; // 进口凭证
	private String jkpzhm; // 进口凭证编号
	private String hgzbh; // 合格证编号
	private String nszm; // 纳税证明
	private String nszmbh; // 纳税证明编号
	private String gxrq; // 更新日期
	private String xgzl; // 相关资料
	private String qmbh; // 前膜编号
	private String hmbh; // 后膜编号
	private String bz; // 备注
	private String jyw; // 校验位
	private String zsxzqh; // 住所行政区划
	private String zsxxdz; // 住所详细地址
	private String yzbm1; // 住所邮政编码
	private String lxdh; // 联系电话
	private String zzz; // 暂住居住证明
	private String zzxzqh; // 暂住行政区划
	private String zzxxdz; // 暂住详细地址
	private String yzbm2; // 暂住邮政编码
	private String zdyzt; // 自定义状态
	private String yxh; // 原机动车序号
	private String cyry; // 查验人员
	private String dphgzbh; // 底盘合格证编号
	private String sqdm; // 社区代码
	private String clyt; // 车辆用途
	private String ytsx; // 用途属性
	// [特定属性]<!--本区域内开发用户可加入特定属性，特定属性可以被工具软件自动化生成时保留。-->
	public String clzt;
	public String clztmc;
	public String hpzlmc;
	public String csysmc;
	public String cllxmc;
	public String syxzmc;
	public String clytmc;
	public String zsxzqhmc;
	
	private String bkcx;
	private String bkxx;
	
	
	public String getBkcx() {
		return bkcx;
	}
	public void setBkcx(String bkcx) {
		this.bkcx = bkcx;
	}
	public String getBkxx() {
		return bkxx;
	}
	public void setBkxx(String bkxx) {
		this.bkxx = bkxx;
	}
	public String getClytmc() {
		return clytmc;
	}
	public void setClytmc(String clytmc) {
		this.clytmc = clytmc;
	}
	public String getZsxzqhmc() {
		return zsxzqhmc;
	}
	public void setZsxzqhmc(String zsxzqhmc) {
		this.zsxzqhmc = zsxzqhmc;
	}
	public String lxr;
	// [/特定属性]。
	public String getClzt(){
		return clzt;
	}
	public void setClzt(String clzt){
		this.clzt=clzt;
	}
	public String getDzyx(){
		return dzyx;
	}
	public void setDzyx(String dzyx){
		this.dzyx=dzyx;
	}
	public String getXszbh(){
		return xszbh;
	}
	public void setXszbh(String xszbh){
		this.xszbh=xszbh;
	}
	public String getSjhm(){
		return sjhm;
	}
	public void setSjhm(String sjhm){
		this.sjhm=sjhm;
	}
	public String getJyhgbzbh(){
		return jyhgbzbh;
	}
	public void setJyhgbzbh(String jyhgbzbh){
		this.jyhgbzbh=jyhgbzbh;
	}
	public String getDwbh(){
		return dwbh;
	}
	public void setDwbh(String dwbh){
		this.dwbh=dwbh;
	}
	public String getXh(){
		return xh;
	}
	public void setXh(String xh){
		this.xh=xh;
	}
	public String getHpzl(){
		return hpzl;
	}
	public void setHpzl(String hpzl){
		this.hpzl=hpzl;
	}
	public String getHphm(){
		return hphm;
	}
	public void setHphm(String hphm){
		this.hphm=hphm;
	}
	public String getClpp1(){
		return clpp1;
	}
	public void setClpp1(String clpp1){
		this.clpp1=clpp1;
	}
	public String getClxh(){
		return clxh;
	}
	public void setClxh(String clxh){
		this.clxh=clxh;
	}
	public String getClpp2(){
		return clpp2;
	}
	public void setClpp2(String clpp2){
		this.clpp2=clpp2;
	}
	public String getGcjk(){
		return gcjk;
	}
	public void setGcjk(String gcjk){
		this.gcjk=gcjk;
	}
	public String getZzg(){
		return zzg;
	}
	public void setZzg(String zzg){
		this.zzg=zzg;
	}
	public String getZzcmc(){
		return zzcmc;
	}
	public void setZzcmc(String zzcmc){
		this.zzcmc=zzcmc;
	}
	public String getClsbdh(){
		return clsbdh;
	}
	public void setClsbdh(String clsbdh){
		this.clsbdh=clsbdh;
	}
	public String getFdjh(){
		return fdjh;
	}
	public void setFdjh(String fdjh){
		this.fdjh=fdjh;
	}
	public String getCllx(){
		return cllx;
	}
	public void setCllx(String cllx){
		this.cllx=cllx;
	}
	public String getCsys(){
		return csys;
	}
	public void setCsys(String csys){
		this.csys=csys;
	}
	public String getSyxz(){
		return syxz;
	}
	public void setSyxz(String syxz){
		this.syxz=syxz;
	}
	public String getSfzmhm(){
		return sfzmhm;
	}
	public void setSfzmhm(String sfzmhm){
		this.sfzmhm=sfzmhm;
	}
	public String getSfzmmc(){
		return sfzmmc;
	}
	public void setSfzmmc(String sfzmmc){
		this.sfzmmc=sfzmmc;
	}
	public String getSyr(){
		return syr;
	}
	public void setSyr(String syr){
		this.syr=syr;
	}
	public String getSyq(){
		return syq;
	}
	public void setSyq(String syq){
		this.syq=syq;
	}
	public String getCcdjrq(){
		return StringUtil.transBlank(ccdjrq);
	}
	public void setCcdjrq(String ccdjrq){
		this.ccdjrq=ccdjrq;
	}
	public String getDjrq(){
		return StringUtil.transBlank(djrq);
	}
	public void setDjrq(String djrq){
		this.djrq=djrq;
	}
	public String getYxqz(){
		return StringUtil.transBlank(yxqz);
	}
	public void setYxqz(String yxqz){
		this.yxqz=yxqz;
	}
	public String getQzbfqz(){
		return StringUtil.transBlank(qzbfqz);
	}
	public void setQzbfqz(String qzbfqz){
		this.qzbfqz=qzbfqz;
	}
	public String getFzjg(){
		return fzjg;
	}
	public void setFzjg(String fzjg){
		this.fzjg=fzjg;
	}
	public String getGlbm(){
		return glbm;
	}
	public void setGlbm(String glbm){
		this.glbm=glbm;
	}
	public String getFprq(){
		return StringUtil.transBlank(fprq);
	}
	public void setFprq(String fprq){
		this.fprq=fprq;
	}
	public String getFzrq(){
		return StringUtil.transBlank(fzrq);
	}
	public void setFzrq(String fzrq){
		this.fzrq=fzrq;
	}
	public String getFdjrq(){
		return StringUtil.transBlank(fdjrq);
	}
	public void setFdjrq(String fdjrq){
		this.fdjrq=fdjrq;
	}
	public String getFhgzrq(){
		return StringUtil.transBlank(fhgzrq);
	}
	public void setFhgzrq(String fhgzrq){
		this.fhgzrq=fhgzrq;
	}
	public String getBxzzrq(){
		return StringUtil.transBlank(bxzzrq);
	}
	public void setBxzzrq(String bxzzrq){
		this.bxzzrq=bxzzrq;
	}
	public long getBpcs(){
		return bpcs;
	}
	public void setBpcs(long bpcs){
		this.bpcs=bpcs;
	}
	public long getBzcs(){
		return bzcs;
	}
	public void setBzcs(long bzcs){
		this.bzcs=bzcs;
	}
	public long getBdjcs(){
		return bdjcs;
	}
	public void setBdjcs(long bdjcs){
		this.bdjcs=bdjcs;
	}
	public String getDjzsbh(){
		return djzsbh;
	}
	public void setDjzsbh(String djzsbh){
		this.djzsbh=djzsbh;
	}
	public long getZdjzshs(){
		return zdjzshs;
	}
	public void setZdjzshs(long zdjzshs){
		this.zdjzshs=zdjzshs;
	}
	public String getDabh(){
		return dabh;
	}
	public void setDabh(String dabh){
		this.dabh=dabh;
	}
	public String getXzqh(){
		return xzqh;
	}
	public void setXzqh(String xzqh){
		this.xzqh=xzqh;
	}
	public String getZt(){
		return zt;
	}
	public void setZt(String zt){
		this.zt=zt;
	}
	public String getDybj(){
		return dybj;
	}
	public void setDybj(String dybj){
		this.dybj=dybj;
	}
	public String getJbr(){
		return jbr;
	}
	public void setJbr(String jbr){
		this.jbr=jbr;
	}
	public String getClly(){
		return clly;
	}
	public void setClly(String clly){
		this.clly=clly;
	}
	public String getLsh(){
		return lsh;
	}
	public void setLsh(String lsh){
		this.lsh=lsh;
	}
	public String getFdjxh(){
		return fdjxh;
	}
	public void setFdjxh(String fdjxh){
		this.fdjxh=fdjxh;
	}
	public String getRlzl(){
		return rlzl;
	}
	public void setRlzl(String rlzl){
		this.rlzl=rlzl;
	}
	public long getPl(){
		return pl;
	}
	public void setPl(long pl){
		this.pl=pl;
	}
	public long getGl(){
		return gl;
	}
	public void setGl(long gl){
		this.gl=gl;
	}
	public String getZxxs(){
		return zxxs;
	}
	public void setZxxs(String zxxs){
		this.zxxs=zxxs;
	}
	public long getCwkc(){
		return cwkc;
	}
	public void setCwkc(long cwkc){
		this.cwkc=cwkc;
	}
	public long getCwkk(){
		return cwkk;
	}
	public void setCwkk(long cwkk){
		this.cwkk=cwkk;
	}
	public long getCwkg(){
		return cwkg;
	}
	public void setCwkg(long cwkg){
		this.cwkg=cwkg;
	}
	public long getHxnbcd(){
		return hxnbcd;
	}
	public void setHxnbcd(long hxnbcd){
		this.hxnbcd=hxnbcd;
	}
	public long getHxnbkd(){
		return hxnbkd;
	}
	public void setHxnbkd(long hxnbkd){
		this.hxnbkd=hxnbkd;
	}
	public long getHxnbgd(){
		return hxnbgd;
	}
	public void setHxnbgd(long hxnbgd){
		this.hxnbgd=hxnbgd;
	}
	public long getGbthps(){
		return gbthps;
	}
	public void setGbthps(long gbthps){
		this.gbthps=gbthps;
	}
	public long getZs(){
		return zs;
	}
	public void setZs(long zs){
		this.zs=zs;
	}
	public long getZj(){
		return zj;
	}
	public void setZj(long zj){
		this.zj=zj;
	}
	public long getQlj(){
		return qlj;
	}
	public void setQlj(long qlj){
		this.qlj=qlj;
	}
	public long getHlj(){
		return hlj;
	}
	public void setHlj(long hlj){
		this.hlj=hlj;
	}
	public String getLtgg(){
		return ltgg;
	}
	public void setLtgg(String ltgg){
		this.ltgg=ltgg;
	}
	public long getLts(){
		return lts;
	}
	public void setLts(long lts){
		this.lts=lts;
	}
	public long getZzl(){
		return zzl;
	}
	public void setZzl(long zzl){
		this.zzl=zzl;
	}
	public long getZbzl(){
		return zbzl;
	}
	public void setZbzl(long zbzl){
		this.zbzl=zbzl;
	}
	public long getHdzzl(){
		return hdzzl;
	}
	public void setHdzzl(long hdzzl){
		this.hdzzl=hdzzl;
	}
	public long getHdzk(){
		return hdzk;
	}
	public void setHdzk(long hdzk){
		this.hdzk=hdzk;
	}
	public long getZqyzl(){
		return zqyzl;
	}
	public void setZqyzl(long zqyzl){
		this.zqyzl=zqyzl;
	}
	public long getQpzk(){
		return qpzk;
	}
	public void setQpzk(long qpzk){
		this.qpzk=qpzk;
	}
	public long getHpzk(){
		return hpzk;
	}
	public void setHpzk(long hpzk){
		this.hpzk=hpzk;
	}
	public String getHbdbqk(){
		return hbdbqk;
	}
	public void setHbdbqk(String hbdbqk){
		this.hbdbqk=hbdbqk;
	}
	public String getCcrq(){
		return StringUtil.transBlank(ccrq);
	}
	public void setCcrq(String ccrq){
		this.ccrq=ccrq;
	}
	public String getHdfs(){
		return hdfs;
	}
	public void setHdfs(String hdfs){
		this.hdfs=hdfs;
	}
	public String getLlpz1(){
		return llpz1;
	}
	public void setLlpz1(String llpz1){
		this.llpz1=llpz1;
	}
	public String getPzbh1(){
		return pzbh1;
	}
	public void setPzbh1(String pzbh1){
		this.pzbh1=pzbh1;
	}
	public String getLlpz2(){
		return llpz2;
	}
	public void setLlpz2(String llpz2){
		this.llpz2=llpz2;
	}
	public String getPzbh2(){
		return pzbh2;
	}
	public void setPzbh2(String pzbh2){
		this.pzbh2=pzbh2;
	}
	public String getXsdw(){
		return xsdw;
	}
	public void setXsdw(String xsdw){
		this.xsdw=xsdw;
	}
	public long getXsjg(){
		return xsjg;
	}
	public void setXsjg(long xsjg){
		this.xsjg=xsjg;
	}
	public String getXsrq(){
		return StringUtil.transBlank(xsrq);
	}
	public void setXsrq(String xsrq){
		this.xsrq=xsrq;
	}
	public String getJkpz(){
		return jkpz;
	}
	public void setJkpz(String jkpz){
		this.jkpz=jkpz;
	}
	public String getJkpzhm(){
		return jkpzhm;
	}
	public void setJkpzhm(String jkpzhm){
		this.jkpzhm=jkpzhm;
	}
	public String getHgzbh(){
		return hgzbh;
	}
	public void setHgzbh(String hgzbh){
		this.hgzbh=hgzbh;
	}
	public String getNszm(){
		return nszm;
	}
	public void setNszm(String nszm){
		this.nszm=nszm;
	}
	public String getNszmbh(){
		return nszmbh;
	}
	public void setNszmbh(String nszmbh){
		this.nszmbh=nszmbh;
	}
	public String getGxrq(){
		return StringUtil.transBlank(gxrq);
	}
	public void setGxrq(String gxrq){
		this.gxrq=gxrq;
	}
	public String getXgzl(){
		return xgzl;
	}
	public void setXgzl(String xgzl){
		this.xgzl=xgzl;
	}
	public String getQmbh(){
		return qmbh;
	}
	public void setQmbh(String qmbh){
		this.qmbh=qmbh;
	}
	public String getHmbh(){
		return hmbh;
	}
	public void setHmbh(String hmbh){
		this.hmbh=hmbh;
	}
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz=bz;
	}
	public String getJyw(){
		return jyw;
	}
	public void setJyw(String jyw){
		this.jyw=jyw;
	}
	public String getZsxzqh(){
		return zsxzqh;
	}
	public void setZsxzqh(String zsxzqh){
		this.zsxzqh=zsxzqh;
	}
	public String getZsxxdz(){
		return zsxxdz;
	}
	public void setZsxxdz(String zsxxdz){
		this.zsxxdz=zsxxdz;
	}
	public String getYzbm1(){
		return yzbm1;
	}
	public void setYzbm1(String yzbm1){
		this.yzbm1=yzbm1;
	}
	public String getLxdh(){
		return lxdh;
	}
	public void setLxdh(String lxdh){
		this.lxdh=lxdh;
	}
	public String getZzz(){
		return zzz;
	}
	public void setZzz(String zzz){
		this.zzz=zzz;
	}
	public String getZzxzqh(){
		return zzxzqh;
	}
	public void setZzxzqh(String zzxzqh){
		this.zzxzqh=zzxzqh;
	}
	public String getZzxxdz(){
		return zzxxdz;
	}
	public void setZzxxdz(String zzxxdz){
		this.zzxxdz=zzxxdz;
	}
	public String getYzbm2(){
		return yzbm2;
	}
	public void setYzbm2(String yzbm2){
		this.yzbm2=yzbm2;
	}
	public String getZdyzt(){
		return zdyzt;
	}
	public void setZdyzt(String zdyzt){
		this.zdyzt=zdyzt;
	}
	public String getYxh(){
		return yxh;
	}
	public void setYxh(String yxh){
		this.yxh=yxh;
	}
	public String getCyry(){
		return cyry;
	}
	public void setCyry(String cyry){
		this.cyry=cyry;
	}
	public String getDphgzbh(){
		return dphgzbh;
	}
	public void setDphgzbh(String dphgzbh){
		this.dphgzbh=dphgzbh;
	}
	public String getSqdm(){
		return sqdm;
	}
	public void setSqdm(String sqdm){
		this.sqdm=sqdm;
	}
	public String getClyt(){
		return clyt;
	}
	public void setClyt(String clyt){
		this.clyt=clyt;
	}
	public String getYtsx(){
		return ytsx;
	}
	public void setYtsx(String ytsx){
		this.ytsx=ytsx;
	}
	public String getClztmc(){
		return clztmc;
	}
	public void setClztmc(String clztmc){
		this.clztmc=clztmc;
	}
	public String getHpzlmc(){
		return hpzlmc;
	}
	public void setHpzlmc(String hpzlmc){
		this.hpzlmc=hpzlmc;
	}
	public String getCsysmc(){
		return csysmc;
	}
	public void setCsysmc(String csysmc){
		this.csysmc=csysmc;
	}
	public String getCllxmc(){
		return cllxmc;
	}
	public void setCllxmc(String cllxmc){
		this.cllxmc=cllxmc;
	}
	public String getSyxzmc(){
		return syxzmc;
	}
	public void setSyxzmc(String syxzmc){
		this.syxzmc=syxzmc;
	}
	public String getLxr(){
		return lxr;
	}
	public void setLxr(String lxr){
		this.lxr=lxr;
	}
	
}
