package com.tmri.framework.bean;
/**
*数据库表frm_frfile对应的java bean
*By wu
*
*/
import org.springframework.web.multipart.MultipartFile;
import java.sql.Blob;

public class FrFile{
	private String xh;
    private String tid;
    private String wslbmc;
    private String glbm;
    private String bmmc;
    private byte[] fr3;
    private String fr3str;
    private MultipartFile myfile;
    private String filename;
    private Blob fileData;
    private String isedit;
    private String datakey;
    private String tgxbj;
    private String impl;
	private String tmc;
    private String tlb;
    private String xtlb;
    private String frVersion="";//为避免增加两套机会一样的代码
    
    private String userGlbm="";
    private String userBmjb="";
    private Blob fr1;
    
    public String getUserGlbm() {
		return userGlbm;
	}
	public void setUserGlbm(String userGlbm) {
		this.userGlbm = userGlbm;
	}
	public String getUserBmjb() {
		return userBmjb;
	}
	public void setUserBmjb(String userBmjb) {
		this.userBmjb = userBmjb;
	}
	public Blob getFr1(){
			return fr1;
		}
		public void setFr1(Blob fr1){
			this.fr1=fr1;
		}
		public String getFrVersion() {
		return frVersion;
	}
	public void setFrVersion(String frVersion) {
		this.frVersion = frVersion;
	}
	public String getXtlb() {
		return xtlb;
	}
	public void setXtlb(String xtlb) {
		this.xtlb = xtlb;
	}
    public String getTid(){
        return this.tid;
    }
    public void setTid(String tid){
        this.tid=tid;
    }
    public String getGlbm(){
        return this.glbm;
    }
    public void setGlbm(String glbm){
        this.glbm=glbm;
    }
    public byte[] getFr3() {
		return fr3;
	}
	public void setFr3(byte[] fr3) {
		this.fr3 = fr3;
	}
	public String getDatakey(){
        return this.datakey;
    }
    public void setDatakey(String datakey){
        this.datakey=datakey;
    }
    public String getTgxbj(){
        return this.tgxbj;
    }
    public void setTgxbj(String tgxbj){
        this.tgxbj=tgxbj;
    }
    public String getImpl(){
        return this.impl;
    }
    public void setImpl(String impl){
        this.impl=impl;
    }
	public String getTmc(){
        return this.tmc;
    }
    public void setTmc(String tmc){
        this.tmc=tmc;
    }
	public String getTlb() {
		return tlb;
	}
	public void setTlb(String tlb) {
		this.tlb = tlb;
	}
	public String getFr3str() {
		return fr3str;
	}
	public void setFr3str(String fr3str) {
		this.fr3str = fr3str;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getWslbmc() {
		return wslbmc;
	}
	public void setWslbmc(String wslbmc) {
		this.wslbmc = wslbmc;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getIsedit() {
		return isedit;
	}
	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}

	public Blob getFileData() {
		return fileData;
	}
	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public MultipartFile getMyfile() {
		return myfile;
	}
	public void setMyfile(MultipartFile myfile) {
		this.myfile = myfile;
	}
}