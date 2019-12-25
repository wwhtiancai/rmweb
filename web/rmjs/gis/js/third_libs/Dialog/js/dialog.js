
//Menu.WINDOWS_DIV;
var lastSeqID=0;
function Dialog(cell,_opt){
	this.dialogId = cell;
	this.opt = _opt;
	var imageHref = location.href;
	var tempHref = imageHref.substring(0,imageHref.lastIndexOf("/"));
	imageHref = tempHref.substring(0,tempHref.lastIndexOf("/")); 
    this.imageHref = imageHref;
    this._container = document.getElementById("map");
    $(this._container).css({zIndex:5});
    this._containerId = "map";
    this.lastSeqID = lastSeqID;

	this._dialog=null;
	this._map=null;
	this._dialogCss=null;
	this._dialogHeight=0;
	this._dialogWidth=0;
	
	this._title="";
	this._titleCss=null;
	this._text="";
	this._textCss=null;
	this._baseCss="";
	this._hasMask=null;
	
	this.me=null;
	this.titleDiv=null;
	this.textDiv=null;
	this.titleDiv = null;
	this.init(cell,_opt);
}

Dialog.prototype.init =function(cell,_opt){
	    this.dialogId = cell;
		this._dialogCss= cell.dialogCss || null;
		
        this._dialogHeight=300;
        this._dialogWidth=240;        
		this.ofLeft=280;
		this.ofTop=180;
        //debugger;
        if(_opt!=undefined && _opt!=null){
        	if(_opt.width!=undefined && _opt.width!=null && _opt.width){
        		this._dialogWidth=_opt.width;	
        	}
        	if(_opt.height!=undefined && _opt.height!=null && _opt.height){
        		this._dialogHeight=_opt.height;	
        	}
        	if(_opt.ofLeft!=undefined && _opt.ofLeft!=null && _opt.ofLeft){
        		this.ofLeft=_opt.ofLeft;	
        	}
        	if(_opt.ofTop!=undefined && _opt.ofTop!=null && _opt.ofTop){
        		this.ofTop=_opt.ofTop;	
        	}
        }
		//title
		this._title=cell.title || "Dialog Title";
		this._titleCss=cell.titleCss||null;
		//content	
		this._text=cell.text || "content";
		this._textCss=cell.textCss || null;
		this._dialog=document.createElement("DIV");
		this._dialog.id=this.createUniqueID();

		this.titleDiv = document.createElement("DIV");
		this.textDiv = document.createElement("DIV");
		this.closeDiv = document.createElement("DIV");
        this.draw(cell);   
}

Dialog.prototype.createUniqueID =function(){
		var prefix='dialog_';
		lastSeqID += 1; 
		return prefix + this.lastSeqID;        
}

Dialog.prototype.getUniqueID =function(){
		return this._dialog.id;        
}

Dialog.prototype.draw =function(cell){
		var me = this;
		me._map = document.getElementById("map");
		//set dialog css 
// lkn		this._dialog.appendChild(this.titleDiv);
		this._dialog.appendChild(this.closeDiv);
		this._dialog.appendChild(this.textDiv);
		
		//this._container.appendChild(this._dialog);
		$(this._dialog).css({zIndex:5});
		$(document.body).append(this._dialog);
		this.showDialog();
}

Dialog.prototype.remove =function(){
		if(this._dialog){
			this._container.removeChild(this._dialog);
			this._dialog=null;
		}
		return ;
}

Dialog.prototype.setContent =function(text){
		this._text=text;
		this.textDiv.innerHTML=this._text;
}

Dialog.prototype.addContent =function(text){    //likn 
		this._text=text;
		this.textDiv.innerHTML+=this._text;
}

Dialog.prototype.setCloseImg =function(){
	var me=this;
	this.imageHref = modelPath+"js/third_libs";
	var image = $("<img src='"+this.imageHref+"/Dialog/dialogImages/dialog_t4.gif' style='padding-top:2px; cursor:pointer'/>");
	$(image).click(function(){
		me.hideDiv();
	});
	var tempDiv = $("<div style='float:right;height:16px;width:16px;'></div>");
	tempDiv.append(image);
	$(this.closeDiv).append(tempDiv);
}

Dialog.prototype.setTitle =function(title){
		var me=this;
		this._title=title;
		$(this.titleDiv).empty();
		$(this.titleDiv).append("<div onmousedown='dragStart(event, \""+this._dialog.id+"\")' style='float:left; width:"+parseInt(this._dialogWidth-30)+"; cursor:move; height:30px;padding-left:5px; background:url('"+this.imageHref+"/lib/Dialog/dialogImages/dialog_t1.gif') no-repeat left'>"+this._title+"</div>");
		var tempDiv = $("<div style='float:right;height:30px;width:20px; background:url('"+this.imageHref+"/lib/Dialog/dialogImages/dialog_t3.gif') no-repeat right'></div>");
		var image = $("<img src='"+this.imageHref+"/lib/Dialog/dialogImages/dialog_t4.gif' style='padding:5px;cursor : hand'/>");
		$(image).click(function(){
			me.shutDiv();
		});

		tempDiv.append(image);
		$(this.titleDiv).append(tempDiv);
}
Dialog.prototype.showDiv =function(){
	this._dialog.style.display = 'block';
}
Dialog.prototype.hideDiv =function(){
	this._dialog.style.display = 'none';
}
Dialog.prototype.shutDiv =function(){
		//alert("shutDiv");
		try{
			this._dialog.style.display = 'none';
			$(this._dialog).empty();
			this._dialog = null;
		}catch(e){}
		
}

Dialog.prototype.getDialogId =function(){
	return this.dialogId;
}

Dialog.prototype.setDialogCss =function(_dialogCss){
		this._dialogCss = _dialogCss;
		$(this._dialog).css(this._dialogCss);
}

Dialog.prototype.setTitleCss =function(_titleCss){
		this._titleCss = _titleCss;
		$(this.titleDiv).css(this._titleCss);
}


Dialog.prototype.setTextCss =function(_textCss){
		this.textDiv = _textCss;
		$(this.textDiv).css(this._textCss);
}

Dialog.prototype.showDialog =function(){
var me = this;
		if(this._dialogCss == null){
			$(this._dialog).css({
				position : "absolute",
				zIndex:1000,			
				left : this.ofLeft,
				top :  this.ofTop,
				height: this._dialogHeight,
				width:this._dialogWidth,
				border:"2px outset  #B4C6DB",
			//	background:"#eee url('"+this.imageHref+"/lib/Dialog/dialogImages/dialog_bg.png') 0 0 repeat-x",
			//	background:'#faf5f7',
				background:'#ffffff',
				margin:0,
				padding:0,
				opacity:1
			});
		}else{
			$(this._dialog).css(this._dialogCss);
		}
		$(this.titleDiv).empty();
		if(this._titleCss == null){
			$(this.titleDiv).css({
				border:"1px solid  #ddd",
				background:"#B4C6DB",
				clear:"both",
				display:"block",
				width:this._dialogWidth-3,
				height:"30px"
			});
		}else{
			$(this.titleDiv).css(this._titleCss);
		}

		//set text
		if(this._textCss == null){
			$(this.textDiv).css({
				clear:"both",
				display:"block",
				width:"100%",
//  lkn				height : (this._dialogHeight-40)+"px",
                height : (this._dialogHeight)+"px",               
  			    padding:"5px",			    
        		overflow: "auto"
			});
		}else{
			$(this.textDiv).css(this._textCss);
		}
		this.textDiv.innerHTML =this._text;
	}


