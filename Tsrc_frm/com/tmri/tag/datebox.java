package com.tmri.tag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class datebox extends TagSupport{
	private int showType=1;
	private String id;
	private String name;
	private String width;
	
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@Override
	public int doStartTag() throws JspException{
		try{
			JspWriter out=this.pageContext.getOut();
			if(id==null||id.length()<1){
				out.print("");
				return SKIP_BODY;
			}
			String r="";
			
			
			
			if(null == name || "".equals(name)){
				name = id;
			}
			
			if(showType==1){
				String curWidth = "";
				if(null == width || "".equals(width)){
					curWidth = "106px";
				}else{
					curWidth = width;
				}
				
				r+="";
				r+="<input type=\"text\" name=\""+name+"\" id=\""+id+"\" style=\"width:" + curWidth + "\">";
				r+="<script type=\"text/jsdemo\" class=\"jscal\">";
				r+="$('#"+id+"').datepicker({showOn:'both',buttonImageOnly:true,buttonImage:'./rmjs/cal/cal.gif',buttonText:'选择日期'});";	
				r+="</script>";
			}else if(showType==2){
				String curWidth = "";
				if(null == width || "".equals(width)){
					curWidth = "156";
				}else{
					curWidth = width;
				}
				
				r+="";
				r+="<input type=\"text\" name=\""+name+"\" id=\""+id+"\" style=\"width:" + curWidth + "\" maxlength=\"16\">";
				r+="<script type=\"text/jsdemo\" class=\"jscal\">";
				r+="$('#"+id+"').datetimepicker({showOn:'both',buttonImageOnly:true,buttonImage:'./rmjs/cal/cal.gif',buttonText:'选择时间'});";	
				r+="</script>";
			}else if(showType==3){
				// 只能选择近一个月内的时间
				String curWidth = "";
				if(null == width || "".equals(width)){
					curWidth = "106";
				}else{
					curWidth = width;
				}
				
				r+="";
				r+="<input type=\"text\" name=\""+name+"\" id=\""+id+"\" style=\"width:" + curWidth + "\" readonly>";
				r+="<script type=\"text/jsdemo\" class=\"jscal\">";
				r+="$('#"+id+"').datepicker({minDate:'-1m', maxDate:'-1d', showOn:'both',buttonImageOnly:true,buttonImage:'./rmjs/cal/cal.gif',buttonText:'选择日期'});";	
				r+="</script>";
			}else{
				out.print("");
				return SKIP_BODY;
			}
			out.print(r);
		}catch(Exception e){
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException{
		return EVAL_PAGE;
	}
	@Override
	public void release(){
		super.release();
		this.id=null;
	}

}
