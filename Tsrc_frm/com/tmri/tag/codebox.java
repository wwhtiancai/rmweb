package com.tmri.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.tmri.share.frm.bean.Code;

public class codebox extends TagSupport{
	private List<Code> list;
	private int showType=1;
	private String id;
	private String name;
	private String defaultVal;
	private String className;
	private String cssStyle;
	private int haveNull=0;
	private int valueField=1;
	private String onChange="";
	public List<Code> getList(){
		return list;
	}
	public void setList(List<Code> list){
		this.list=list;
	}
	public int getShowType(){
		return showType;
	}
	public void setShowType(int showType){
		this.showType=showType;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName(){
		return className;
	}
	public void setClassName(String className){
		this.className=className;
	}
	public String getCssStyle(){
		return cssStyle;
	}
	public void setCssStyle(String cssStyle){
		this.cssStyle=cssStyle;
	}
	public int getHaveNull(){
		return haveNull;
	}
	public void setHaveNull(int haveNull){
		this.haveNull=haveNull;
	}
	public int getValueField(){
		return valueField;
	}
	public void setValueField(int valueField){
		this.valueField=valueField;
	}
	public String getOnChange(){
		return onChange;
	}
	public void setOnChange(String onChange){
		this.onChange=onChange;
	}
	@Override
	public int doStartTag() throws JspException{
		try{
			JspWriter out=this.pageContext.getOut();
			if(list==null||id==null||id.length()<1){
				out.print("");
				return SKIP_BODY;
			}
			String r="",v="",s="",t="";
			if(haveNull==1){
				r+="<option value=\"\"></option>";
			}
			if(list.size()>0){
				if(showType==1){
					for(Code c:list){
						if(valueField==1){
							v=c.getDmsm1();
						}else if(valueField==2){
							v=c.getDmsm2();
						}else if(valueField==3){	
							v=c.getDmsm3();
						}else if(valueField==4){
							v=c.getDmsm4();
						}
						if(c.getBz()==null||c.getBz().length()<1){
							s="";
						}else{
							s=" style=\""+c.getBz()+"\"";
						}
						if(c.getDmz().equals(defaultVal))
							r+="<option value=\""+c.getDmz()+"\""+s+" selected>"+c.getDmz()+":"+v+"</option>";
						else
							r+="<option value=\""+c.getDmz()+"\""+s+">"+c.getDmz()+":"+v+"</option>";
					}
				}else if(showType==2){
					for(Code c:list){
						if(valueField==1){
							v=c.getDmsm1();
						}else if(valueField==2){
							v=c.getDmsm2();
						}else if(valueField==3){	
							v=c.getDmsm3();
						}else if(valueField==4){
							v=c.getDmsm4();
						}
						if(c.getBz()==null||c.getBz().length()<1){
							s="";
						}else{
							s=" style=\""+c.getBz()+"\"";
						}
						if(c.getDmz().equals(defaultVal))
							r+="<option value=\""+c.getDmz()+"\""+s+" selected>"+v+"</option>";
						else
							r+="<option value=\""+c.getDmz()+"\""+s+">"+v+"</option>";
					}
				}else{
					out.print("");
					return SKIP_BODY;
				}
			}
			if(id!=null&&id.length()>0){
				t+=" id=\""+id+"\"";
				if(name != null && name.length() > 0){
					t += " name=\"" + name + "\"";
				}else{
					t += " name=\"" + id + "\"";
				}
			}
			
			if(className!=null&&className.length()>0){
				t+=" class=\""+className+"\"";
			}
            if (cssStyle != null && cssStyle.length() > 0) {
                t += " style=\"" + cssStyle + "\"";
			}
			if(onChange!=null&&onChange.length()>0){
				t+=" onChange=\""+onChange+"\"";
			}
			r="<select "+t+">"+r+"</select>";
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
		this.list=null;
		this.defaultVal = null;
		this.id=null;
		this.className=null;
		this.cssStyle=null;
	}

}
