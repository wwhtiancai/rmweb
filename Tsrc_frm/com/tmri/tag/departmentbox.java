package com.tmri.tag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class departmentbox extends TagSupport{
	private int showType=1;
	private String keyid;
	private String valueid;
	private String valueClassName;
	private String valueCssStyle;
	private String callBack;
	public int getShowType(){
		return showType;
	}
	public void setShowType(int showType){
		this.showType=showType;
	}
	public String getKeyid(){
		return keyid;
	}
	public void setKeyid(String keyid){
		this.keyid=keyid;
	}
	public String getValueid(){
		return valueid;
	}
	public void setValueid(String valueid){
		this.valueid=valueid;
	}
	public String getValueClassName(){
		return valueClassName;
	}
	public void setValueClassName(String valueClassName){
		this.valueClassName=valueClassName;
	}
	public String getValueCssStyle(){
		return valueCssStyle;
	}
	public void setValueCssStyle(String valueCssStyle){
		this.valueCssStyle=valueCssStyle;
	}
	public String getCallBack(){
		return callBack;
	}
	public void setCallBack(String callBack){
		this.callBack=callBack;
	}
	@Override
	public int doStartTag() throws JspException{
		try{
			JspWriter out=this.pageContext.getOut();
			if(keyid!=null&&keyid.length()>0&&valueid!=null&&valueid.length()>0){
				String f="",r="",k="",v="";
				k+=" id=\""+keyid+"\" name=\""+keyid+"\"";
				v+=" id=\""+valueid+"\" name=\""+valueid+"\"";
				if(valueClassName!=null&&valueClassName.length()>0){
					v+=" class=\""+valueClassName+"\"";
				}
				if(valueCssStyle==null||valueCssStyle.length()<1){
					v+=" style=\"width:88%;\"";
				}else{
					v+=" style=\""+valueCssStyle+"\"";
				}
				if(callBack!=null&&callBack.length()>0){
					f=",\'"+callBack+"\'";
				}
				if(showType==1){
					r="<input type=\"text\" "+v+" readOnly style=\"cursor:pointer\" onDblClick=\"selectdepartment($('#"+keyid+"'),$('#"+valueid+"')"+f+")\"><input type=\"hidden\" value=\"\" "+k+"><img src=\"rmjs/treeview/department.gif\" alt=\"点击选择机关\" width=\"18\" height=\"18\" hspace=\"1\" border=\"0\" align=\"absmiddle\" onClick=\"selectdepartment($('#"+keyid+"'),$('#"+valueid+"')"+f+")\" style=\"cursor:pointer;\">";		
				}else if(showType==2){
					r="<input type=\"text\" "+v+" readOnly style=\"cursor:pointer\" onDblClick=\"selectdepartments($('#"+keyid+"'),$('#"+valueid+"')"+f+")\"><input type=\"hidden\" value=\"\" "+k+"><img src=\"rmjs/treeview/department.gif\" alt=\"点击选择机关\" width=\"18\" height=\"18\" hspace=\"1\" border=\"0\" align=\"absmiddle\" onClick=\"selectdepartments($('#"+keyid+"'),$('#"+valueid+"')"+f+")\" style=\"cursor:pointer;\">";					
				}else if(showType==3){
					r="<input type=\"text\" "+v+" readOnly style=\"cursor:pointer\" onDblClick=\"selDeptList(true, $('#"+keyid+"'),$('#"+valueid+"')"+f+")\"><input type=\"hidden\" value=\"\" "+k+"><img src=\"rmjs/treeview/department.gif\" alt=\"点击选择机关\" width=\"18\" height=\"18\" hspace=\"1\" border=\"0\" align=\"absmiddle\" onClick=\"selDeptList(true, $('#"+keyid+"'),$('#"+valueid+"')"+f+")\" style=\"cursor:pointer;\">";
				}
				out.print(r);
			}
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
		this.keyid=null;
		this.valueid=null;
		this.valueClassName=null;
		this.valueCssStyle=null;
	}
}
