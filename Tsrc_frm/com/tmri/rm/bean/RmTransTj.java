package com.tmri.rm.bean;
import java.math.BigDecimal;

import com.tmri.share.frm.util.StringUtil;
/**
 *
 * <p>Title: RMS</p>
 *
 * <p>Description: 数据传输监测表</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2013-06-26 14:30:07</p>
 *
 */
public class RmTransTj {
	private String xh;	//序号
	private String fzjg;	//发证机关
	private String sjlx;	//数据类型
	private String jcsj;	//检查时间
	private String csrq;	//传输日期
	private BigDecimal cgsl;	//传输成功数量
	private BigDecimal sbsl;	//传输失败数量
	private BigDecimal jysl;	//积压数量
	//[特定属性]<!--本区域内开发用户可加入特定属性，特定属性可以被工具软件自动化生成时保留。-->
	
	//[/特定属性]。

	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public String getJcsj() {
		return StringUtil.transBlank(jcsj);
	}
	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}
	public String getCsrq() {
		return StringUtil.transBlank(csrq);
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	public BigDecimal getCgsl() {
		return cgsl;
	}
	public void setCgsl(BigDecimal cgsl) {
		this.cgsl = cgsl;
	}
	public BigDecimal getSbsl() {
		return sbsl;
	}
	public void setSbsl(BigDecimal sbsl) {
		this.sbsl = sbsl;
	}
	public BigDecimal getJysl() {
		return jysl;
	}
	public void setJysl(BigDecimal jysl) {
		this.jysl = jysl;
	}
}