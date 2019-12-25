function MaxImgControl(){
	var args={};
	if(arguments.length>0){
	  args=arguments[0];
	}
	this.imgUrl=args.imgUrl||"about:blank";
	this.defaultMsg="请稍等,数据处理中...";
	this.msg=args.msg||this.defaultMsg;
	this.divId="MaxImgControl";
	this.imgId=this.divId+"_IMG";
	this.isInit=false;
	this.isIframe=args.isIframe||true;
	if(args.isIframe==false){
		this.isIframe=false;
	}
	this.setMsg=function(msg){
		this.msg=msg||this.defaultMsg;
		jQuery("#"+this.divId+"_msg").html(this.msg);
		return this;
	}
	this.show=function(){
		this.init();
		jQuery("#"+this.divId).css({
			display:""
		});
		return this;
	}
	this.hide=function(){
		this.init();
		jQuery("#"+this.divId).css({
			display:"none"
		});
		return this;
	}
	this.init=function(){
		if(this.isInit){
			return this;
		}
		this.loadHtml='<div title="点击关闭" id="'+this.divId+'" style="text-align:center;overflow-y:hidden;position:absolute;width:100%;height:100%;left:0px;top:0px;z-index:10000;display:none;background-color:rgba(255, 255, 255, 0.7)">';
		if(this.isIframe==true){
			this.loadHtml+='<iframe style="text-align:center;overflow-y:hidden;filter:alpha(opacity=70); position:absolute; width:100%; height:100%; left:0px; top:0px; background: #181818;"></iframe>';
		}
		this.loadHtml+='<div style="position:relative;text-align:center;">'

	    +'   <table align="center" width="100%" height="100%">'

	    +'       <tr>'

	    +'          <td align="center" valign="middle">'

	    +'          <image style="border:ridge" id="'+this.imgId+'" align="absMiddle" src="'+this.imgUrl+'">'

	    +'         </td>'

	    +'       </tr>'

	    +'   </table>'

	    +'</div>'

	    +'</div>';
		if(!jQuery("#"+this.divId)[0]){
			jQuery(document.body).append(this.loadHtml);
			jQuery("#"+this.divId).fadeIn("slow");
			
			jQuery("#"+this.divId).bind('click',function(){
				jQuery(this).fadeOut("slow");
			});
		}else{
			//jQuery("#"+this.divId).show("slow");
			jQuery("#"+this.divId).fadeIn("slow");
			jQuery("#"+this.imgId).attr("src",this.imgUrl);
		}
		this.isInit=true;
		return this;
	}
	return this;
}
