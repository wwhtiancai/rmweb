package com.tmri.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.tmri.share.frm.bean.Roaditem;

public class roadbox extends TagSupport{
	private List<Roaditem> list;
	private int showType=1;
	private String defalut;
	private String id;
	private String className;
	private String cssStyle;
	private int haveNull=0;
	private String onChange="";
	public List<Roaditem> getList(){
		return list;
	}
	public void setList(List<Roaditem> list){
		this.list=list;
	}
	public int getShowType(){
		return showType;
	}
	public void setShowType(int showType){
		this.showType=showType;
	}
	public String getDefalut(){
		return defalut;
	}
	public void setDefalut(String defalut){
		this.defalut=defalut;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
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
			String r="",s="",t="";
			if(haveNull==1){
				r+="<option value=\"\"/></option>";
			}
			if(list.size()>0){
				if(showType==1){
					for(Roaditem road:list){
						if(road.getBz()==null){
							s="";
						}else{
							s=" style=\""+road.getBz()+"\"";
						}
						if(road.getDldm().equals(defalut))
							r+="<option value=\""+road.getDldm()+"\""+s+" selected/>"+road.getDlmc()+"</option>";
						else
							r+="<option value=\""+road.getDldm()+"\""+s+"/>"+road.getDldm()+":"+road.getDlmc()+"</option>";
					}
				}else if(showType==2){
					for(Roaditem road:list){
						if(road.getBz()==null){
							s="";
						}else{
							s=" style=\""+road.getBz()+"\"";
						}
						if(road.getDldm().equals(defalut))
							r+="<option value=\""+road.getDldm()+"\""+s+" selected/>"+road.getDlmc()+"</option>";
						else
							r+="<option value=\""+road.getDldm()+"\""+s+"/>"+road.getDlmc()+"</option>";
					}
				}else if(showType==3){
					for(Roaditem road:list){
						if(road.getBz()==null){
							s="";
						}else{
							s=" style=\""+road.getBz()+"\"";
						}
						if(road.getDldm().equals(defalut))
							r+="<option value=\""+road.getDldm()+"\""+s+" selected/>"+road.getDlmc()+"</option>";
						else
							r+="<option value=\""+road.getDldm()+"\""+s+"/>"+road.getDlmc()+"£¨"+road.getQsmc()+"-"+road.getJsmc()+"£©"+"</option>";
					}
				}else{
					out.print("");
					return SKIP_BODY;
				}
			}
			if(id!=null&&id.length()>0){
				t+=" id=\""+id+"\" name=\""+id+"\"";
			}
			if(className!=null&&className.length()>0){
				t+=" class=\""+className+"\"";
			}
			if(cssStyle==null||cssStyle.length()<1){
				t+=" style=\"width:100%;\"";
			}else{
				t+=" style=\""+cssStyle+"\"";
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
		this.defalut=null;
		this.id=null;
		this.className=null;
		this.cssStyle=null;
	}
}
