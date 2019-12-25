package com.tmri.pub.bean;

public class CommonReportBean{
	private CommonCellBean[][] content;//报表数据
	private String reportTitle; //报表标题
	private String reportStyle;  //报表样式
	private String reportCondition; //报表统计范围
	private String reportButton;//报表按钮
	private String reportDepartment;//报表统计单位信息
	private String reportcontenthtml;//报表内容结构体
	private String reportcontenthead;//报表标题结构体
	private String reportTjsm;//报表统计说明
	private int head;//head头行数
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
