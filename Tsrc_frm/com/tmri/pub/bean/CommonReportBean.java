package com.tmri.pub.bean;

public class CommonReportBean{
	private CommonCellBean[][] content;//��������
	private String reportTitle; //�������
	private String reportStyle;  //������ʽ
	private String reportCondition; //����ͳ�Ʒ�Χ
	private String reportButton;//����ť
	private String reportDepartment;//����ͳ�Ƶ�λ��Ϣ
	private String reportcontenthtml;//�������ݽṹ��
	private String reportcontenthead;//�������ṹ��
	private String reportTjsm;//����ͳ��˵��
	private int head;//headͷ����
	public CommonCellBean[][] getContent(){
		return content;
	}
	public void setContent(CommonCellBean[][] content){
		this.content=content;
	}
	public String getReportTitle(){
		return reportTitle;
	}
	public void setReportTitle(String reportTitle){
		this.reportTitle=reportTitle;
	}
	public String getReportStyle(){
		return reportStyle;
	}
	public void setReportStyle(String reportStyle){
		this.reportStyle=reportStyle;
	}
	public String getReportCondition(){
		return reportCondition;
	}
	public void setReportCondition(String reportCondition){
		this.reportCondition=reportCondition;
	}
	public String getReportButton(){
		return reportButton;
	}
	public void setReportButton(String reportButton){
		this.reportButton=reportButton;
	}
	public String getReportDepartment(){
		return reportDepartment;
	}
	public void setReportDepartment(String reportDepartment){
		this.reportDepartment=reportDepartment;
	}
	public String getReportcontenthtml(){
		return reportcontenthtml;
	}
	public void setReportcontenthtml(String reportcontenthtml){
		this.reportcontenthtml=reportcontenthtml;
	}
	public String getReportcontenthead(){
		return reportcontenthead;
	}
	public void setReportcontenthead(String reportcontenthead){
		this.reportcontenthead=reportcontenthead;
	}
	public int getHead(){
		return head;
	}
	public void setHead(int head){
		this.head=head;
	}
	public String getReportTjsm(){
		return reportTjsm;
	}
	public void setReportTjsm(String reportTjsm){
		this.reportTjsm=reportTjsm;
	}
}
