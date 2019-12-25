package com.tmri.rfid.bean;

import java.util.Date;
import java.util.HashMap;

/**
 * 
 * @author stone
 * @date 2016-1-20 下午3:05:45
 */
public class DataExch {

    private String bh;
	/**
     * 24位主从标记（若为主，14位主记录编号+10位0，若为从，14位主记录编号+10位顺序号）
     * + 1位是否有子记录（1为有，2为没有, 3为子记录） + 1位处理标记 + 数据类型
     */
    private String sjlx;
    private String sjnr1;
    private String sjnr2;
    private String sjnr3;
    private String sjnr4;
    private String sjnr5;
    private String sjnr6;
    private String sjnr7;
    private String sjnr8;
    private String sjnr9;
    private String sjnr10;
    private Integer clbj;
    private Date cjsj;
    private Date gxsj;

    private String mid;
    private long sxh;//顺序号
    private Date Out_BH;
    
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public String getSjnr1() {
		return sjnr1;
	}
	public void setSjnr1(String sjnr1) {
		this.sjnr1 = sjnr1;
	}
	public String getSjnr2() {
		return sjnr2;
	}
	public void setSjnr2(String sjnr2) {
		this.sjnr2 = sjnr2;
	}
	public String getSjnr3() {
		return sjnr3;
	}
	public void setSjnr3(String sjnr3) {
		this.sjnr3 = sjnr3;
	}
	public String getSjnr4() {
		return sjnr4;
	}
	public void setSjnr4(String sjnr4) {
		this.sjnr4 = sjnr4;
	}
	public String getSjnr5() {
		return sjnr5;
	}
	public void setSjnr5(String sjnr5) {
		this.sjnr5 = sjnr5;
	}
	public String getSjnr6() {
		return sjnr6;
	}
	public void setSjnr6(String sjnr6) {
		this.sjnr6 = sjnr6;
	}
	public String getSjnr7() {
		return sjnr7;
	}
	public void setSjnr7(String sjnr7) {
		this.sjnr7 = sjnr7;
	}
	public String getSjnr8() {
		return sjnr8;
	}
	public void setSjnr8(String sjnr8) {
		this.sjnr8 = sjnr8;
	}
	public String getSjnr9() {
		return sjnr9;
	}
	public void setSjnr9(String sjnr9) {
		this.sjnr9 = sjnr9;
	}
	public String getSjnr10() {
		return sjnr10;
	}
	public void setSjnr10(String sjnr10) {
		this.sjnr10 = sjnr10;
	}
	public Integer getClbj() {
		return clbj;
	}
	public void setClbj(Integer clbj) {
		this.clbj = clbj;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public Date getGxsj() {
		return gxsj;
	}
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}
	public Date getOut_BH() {
		return Out_BH;
	}
	public void setOut_BH(Date out_BH) {
		Out_BH = out_BH;
	}
    
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public long getSxh() {
		return sxh;
	}
	public void setSxh(long sxh) {
		this.sxh = sxh;
	}
	
	public HashMap toMap(){
		HashMap map = new HashMap<String , Object>();
		map.put("sjlx", this.getSjlx());
		map.put("sjnr1", this.getSjnr1());
		map.put("sjnr2", this.getSjnr2());
		map.put("sjnr3", this.getSjnr3());
		map.put("sjnr4", this.getSjnr4());
		map.put("sjnr5", this.getSjnr5());
		map.put("sjnr6", this.getSjnr6());
		map.put("sjnr7", this.getSjnr7());
		map.put("sjnr8", this.getSjnr8());
		map.put("sjnr9", this.getSjnr9());
		map.put("sjnr10", this.getSjnr10());
		map.put("clbj", this.getClbj());

		map.put("mid", this.getMid());
		map.put("cjsj", this.getCjsj());
		map.put("gxsj", this.getGxsj());
		map.put("Out_BH", this.getOut_BH());
		return map;
	}
}
