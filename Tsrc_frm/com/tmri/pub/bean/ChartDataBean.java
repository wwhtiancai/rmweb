package com.tmri.pub.bean;

import java.util.List;
import java.util.Map;

public class ChartDataBean{
	private List<Map<String, Object>> colData;
	private String chartname;//图标Title
	private String pcent;
	private String kind;//分类名称
	public String getKind(){
		return kind;
	}
	public void setKind(String kind){
		this.kind=kind;
	}
	public List<Map<String, Object>> getColData(){
		return colData;
	}
	public void setColData(List<Map<String, Object>> colData){
		this.colData=colData;
	}
	public String getChartname(){
		return chartname;
	}
	public void setChartname(String chartname){
		this.chartname=chartname;
	}
	public String getPcent(){
		return pcent;
	}
	public void setPcent(String pcent){
		this.pcent=pcent;
	}
}
