package com.tmri.pub.bean;

//import javax.mail.Flags.Flag;
//import javax.swing.text.AbstractDocument.Content;
//
//import com.sun.java_cup.internal.internal_error;
//import com.tmri.framework.util.common;

public class CommonCellBean{
	private String fieldname;
	private String fieldvalue;
	private String rowspan;
	private String colspan;
	private int align;//1-左 2-中 3-右
	private String content;
	private long content1;
	private int kind;//1-标题 2-内容 3-比率
	private String width;//宽度

	public String getWidth(){
		return width;
	}
	public CommonCellBean(){
		
	}
	public void setWidth(String width){
		this.width=width;
	}
	public CommonCellBean(String content1){
		this.content = content1;
		this.kind = 1;
	}
	public CommonCellBean(String fieldname1,String fieldvalue1,String Content1){
		this.fieldname = fieldname1;
		this.fieldvalue = fieldvalue1;
		this.content = Content1;
		this.kind = 1;
		this.align = 2;
	}
	public CommonCellBean(long Content1){
		this.content1 = content1;
		this.kind = 2;
		this.align = 3;
	}
	public CommonCellBean(long Content1,int kind1){
		this.content1 = content1;
		this.kind = kind1;
		this.align = 3;
	}
	public String getFieldname(){
		return fieldname;
	}
	public void setFieldname(String fieldname){
		this.fieldname=fieldname;
	}
	public String getRowspan(){
		return rowspan;
	}
	public void setRowspan(String rowspan){
		this.rowspan=rowspan;
	}
	public String getColspan(){
		return colspan;
	}
	public void setColspan(String colspan){
		this.colspan=colspan;
	}
	public int getAlign(){
		return align;
	}
	public void setAlign(int align){
		this.align=align;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content=content;
	}
	public long getContent1(){
		return content1;
	}
	public void setContent1(long content1){
		this.content1=content1;
	}
	public int getKind(){
		return kind;
	}
	public void setKind(int kind){
		this.kind=kind;
	}
	public String getFieldvalue(){
		return fieldvalue;
	}
	public void setFieldvalue(String fieldvalue){
		this.fieldvalue=fieldvalue;
	}
	
}
