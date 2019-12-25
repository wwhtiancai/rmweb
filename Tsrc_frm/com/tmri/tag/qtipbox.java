package com.tmri.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class qtipbox extends TagSupport{
	private String id;
	private int showType=1;
	private String target;
	private String tooltip;
	private String message;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public int getShowType(){
		return showType;
	}
	public void setShowType(int showType){
		this.showType=showType;
	}
	public String getTarget(){
		return target;
	}
	public void setTarget(String target){
		this.target=target;
	}
	public String getTooltip(){
		return tooltip;
	}
	public void setTooltip(String tooltip){
		this.tooltip=tooltip;
	}
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message=message;
	}
	@Override
	public int doStartTag() throws JspException{
		try{
			JspWriter out=this.pageContext.getOut();
			if(id==null||id.length()<1){
				out.print("");
				return SKIP_BODY;
			}else{
				String r="";
				if(showType==1){
					if(target==null||target.length()<1)
						target="topRight";
					if(tooltip==null||tooltip.length()<1)
						tooltip="leftTop";
					if(message==null||message.length()<1)
						message="";
					r="$(\"#"+id+"\").qtip({content:'<span class=\"tip\">"+message+"</span>',position:{corner:{target:'"+target+"',tooltip:'"+tooltip+"'}},style:{name:'blue',tip:true}});";
				}else if(showType==2){
					if(target==null||target.length()<1)
						target="bottomLeft";
					if(tooltip==null||tooltip.length()<1)
						tooltip="topLeft";
					if(message==null||message.length()<1)
						message="ÇëÑ¡Ôñ...";
					r="$(\"#"+id+"\").qtip({api:{beforeShow:function(){var s='<span class=\"tip_selectbox\">"+message+"</span>';if($(\"#"+id+" > option:selected\").get(0)){s=$(\"#"+id+" > option:selected\").get(0).text;}var self=this;self.updateContent('<span class=\"tip_selectbox\">'+s+'</span>');}},content:'<span class=\"tip_selectbox\">&nbsp;</span>',position:{corner:{target:'"+target+"',tooltip:'"+tooltip+"'}},style:{name:'blue',tip:true}});";					
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
		this.id=null;
		this.target=null;
		this.tooltip=null;
		this.message=null;
	}
}
