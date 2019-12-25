package com.tmri.rm.bean;
import com.tmri.share.frm.util.StringUtil;
/**
 *
 * <p>Title: RMS</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2013-07-02 19:35:18</p>
 *
 */
public class RmVersion {
	private String fzjg;	//��֤����
	private String azdm;	//��װ����
	private String azdmc;   //��װ������
	private String ip;	//IP��ַ
	private String gjz;	//�ؼ���
	private String mc;	//����
	private String version;	//�汾��
	private String jcsj;	//���ʱ��
	//[�ض�����]<!--�������ڿ����û��ɼ����ض����ԣ��ض����Կ��Ա���������Զ�������ʱ������-->
	private String sm;      //�汾˵��
	//[/�ض�����]��
	private String sf;      //ʡ��
	private String webzt;   //Ӧ�÷���״̬
	private String pkgzt;   //�洢����״̬
	
	private String sfdm; // ʡ�ݴ���
	private int azds; // ��װ����
	private int newWebCount; // �Ѹ���Ӧ�ó�������
	private int newPkgCount; // �Ѹ��´洢��������
	private int normalCount; // ��������������
	private String ds; //����
	
	private String web; // Ӧ�ó������
	private String pkg; // �洢���̸���
	private String status; // ״̬
	
	private int webSc; // web�Ƿ��ϴ� 0 δ�ϴ� 1 ���ϴ�
	private int pkgSc; // pkg�Ƿ��ϴ� 0 δ�ϴ� 1 ���ϴ�
	
	private String webClass; // web��ʽ
	private String pkgClass; // pkg��ʽ
	private String statusClass; // status��ʽ
	
	public String getWebClass() {
		return webClass;
	}
	public void setWebClass(String webClass) {
		this.webClass = webClass;
	}
	public String getPkgClass() {
		return pkgClass;
	}
	public void setPkgClass(String pkgClass) {
		this.pkgClass = pkgClass;
	}
	public String getStatusClass() {
		return statusClass;
	}
	public void setStatusClass(String statusClass) {
		this.statusClass = statusClass;
	}
	public int getWebSc() {
		return webSc;
	}
	public void setWebSc(int webSc) {
		this.webSc = webSc;
	}
	public int getPkgSc() {
		return pkgSc;
	}
	public void setPkgSc(int pkgSc) {
		this.pkgSc = pkgSc;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDs() {
		return ds;
	}
	public void setDs(String ds) {
		this.ds = ds;
	}
	public int getNormalCount() {
		return normalCount;
	}
	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}
	public String getSfdm() {
		return sfdm;
	}
	public void setSfdm(String sfdm) {
		this.sfdm = sfdm;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public String getAzdm() {
		return azdm;
	}
	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}
	public String getAzdmc() {
		return azdmc;
	}
	public void setAzdmc(String azdmc) {
		this.azdmc = azdmc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGjz() {
		return gjz;
	}
	public void setGjz(String gjz) {
		this.gjz = gjz;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getJcsj() {
		return StringUtil.transBlank(jcsj);
	}
	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getSf() {
		return sf;
	}
	public void setSf(String sf) {
		this.sf = sf;
	}
	public String getWebzt() {
		return webzt;
	}
	public void setWebzt(String webzt) {
		this.webzt = webzt;
	}
	public String getPkgzt() {
		return pkgzt;
	}
	public void setPkgzt(String pkgzt) {
		this.pkgzt = pkgzt;
	}
	public int getNewWebCount() {
		return newWebCount;
	}
	public void setNewWebCount(int newWebCount) {
		this.newWebCount = newWebCount;
	}
	public int getNewPkgCount() {
		return newPkgCount;
	}
	public void setNewPkgCount(int newPkgCount) {
		this.newPkgCount = newPkgCount;
	}
	public int getAzds() {
		return azds;
	}
	public void setAzds(int azds) {
		this.azds = azds;
	}
}