package com.tmri.share.frm.bean;

import java.io.Serializable;

public class UserSession implements Serializable {
	private SysUser sysuser;

	private Department department;

	private String sessionid;

	private String tjnf;
	  private String tjlx;
	  private String tjksrq;
	  private String tjjsrq;
	  private String fzjg;
	  
	  private String yhdh;//�û�����
	  private String dlsj;//��¼ʱ��
	  private String dlms;//��¼ģʽ
	  private String dlip;//��¼IP
	  
	  private String scdlsj;//�ϴε�¼ʱ��
	  private String scdlms;//�ϴε�¼ģʽ
	  private String scdlip;//�ϴε�¼IP
	  
	  private String mmyxq;//������Ч��ʣ������
	  
	  private String zhyxq;//�˻���Ч��
	  
	  private String dlcs;//���յ�¼����
	  
	  private String bydlcs;//���յ�¼����
	  
	  private String zxrs;//�����û���
	  	  
	  private String zdzxrs;//��������û���
	  
	  private String drfwrs;//���շ����û���
	  
	  private String zfwrs;//�ܷ����û���
	  
	  private String wfsryzm; //Υ��������֤��
	  
	  private String fwzbh; // ����ִ������վ���

	private int dzbzkc; //���ӱ�ʶ���
	  
	public String getSessionid(){
		return sessionid;
	}

	public void setSessionid(String sessionid){
		this.sessionid=sessionid;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public SysUser getSysuser() {
		return sysuser;
	}

	public void setSysuser(SysUser sysuser) {
		this.sysuser = sysuser;
	}

	public String getTjnf() {
		return tjnf;
	}

	public void setTjnf(String tjnf) {
		this.tjnf = tjnf;
	}

	public String getTjlx() {
		return tjlx;
	}

	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}

	public String getTjksrq() {
		return tjksrq;
	}

	public void setTjksrq(String tjksrq) {
		this.tjksrq = tjksrq;
	}

	public String getTjjsrq() {
		return tjjsrq;
	}

	public void setTjjsrq(String tjjsrq) {
		this.tjjsrq = tjjsrq;
	}

	public String getFzjg() {
		return fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getDlsj(){
		return dlsj;
	}

	public void setDlsj(String dlsj){
		this.dlsj=dlsj;
	}

	public String getDlms(){
		return dlms;
	}

	public void setDlms(String dlms){
		this.dlms=dlms;
	}

	public String getDlip(){
		return dlip;
	}

	public void setDlip(String dlip){
		this.dlip=dlip;
	}

	public String getDlcs(){
		return dlcs;
	}

	public void setDlcs(String dlcs){
		this.dlcs=dlcs;
	}

	public String getScdlsj(){
		return scdlsj;
	}

	public void setScdlsj(String scdlsj){
		this.scdlsj=scdlsj;
	}

	public String getScdlms(){
		return scdlms;
	}

	public void setScdlms(String scdlms){
		this.scdlms=scdlms;
	}

	public String getScdlip(){
		return scdlip;
	}

	public void setScdlip(String scdlip){
		this.scdlip=scdlip;
	}

	public String getMmyxq(){
		return mmyxq;
	}

	public void setMmyxq(String mmyxq){
		this.mmyxq=mmyxq;
	}

	public String getZxrs(){
		return zxrs;
	}

	public void setZxrs(String zxrs){
		this.zxrs=zxrs;
	}

	public String getZdzxrs(){
		return zdzxrs;
	}

	public void setZdzxrs(String zdzxrs){
		this.zdzxrs=zdzxrs;
	}

	public String getDrfwrs(){
		return drfwrs;
	}

	public void setDrfwrs(String drfwrs){
		this.drfwrs=drfwrs;
	}

	public String getZfwrs(){
		return zfwrs;
	}

	public void setZfwrs(String zfwrs){
		this.zfwrs=zfwrs;
	}

	public String getYhdh(){
		return yhdh;
	}

	public void setYhdh(String yhdh){
		this.yhdh=yhdh;
	}

	public String getWfsryzm() {
		return wfsryzm;
	}

	public void setWfsryzm(String wfsryzm) {
		this.wfsryzm = wfsryzm;
	}

	public String getZhyxq(){
		return zhyxq;
	}

	public void setZhyxq(String zhyxq){
		this.zhyxq=zhyxq;
	}

	public String getBydlcs(){
		return bydlcs;
	}

	public void setBydlcs(String bydlcs){
		this.bydlcs=bydlcs;
	}

	public String getFwzbh() {
		return fwzbh;
	}

	public void setFwzbh(String fwzbh) {
		this.fwzbh = fwzbh;
	}

	public int getDzbzkc() {
		return dzbzkc;
	}

	public void setDzbzkc(int dzbzkc) {
		this.dzbzkc = dzbzkc;
	}
}
