
//本javascript脚本用于实现自动提示建议的文本控件

//创建自动提示控件的函数
function AutoSuggestControl(oTextbox, oProvider) {
	this.cur = -1;//当前建议选择建议
	this.layer = null;//下拉列表层
	this.provider = oProvider;
	this.idValue = -1;//int的值 用于记录选择选项的id值(如204 都市广场的204)
	this.tableName = "";//自动提示查询的表名称
	this.valueField ="";//查询实时需要值字段
	this.textField = "";//自动提示查询的字段名称
	this.filterCondition = "";//设置过滤条件
	this.limit = 10;//限制返回的记录数量
	this.url="";//获得结果的URL
	this.callBackFun="";//选择完毕之后执行回调函数名称
	this.textbox = oTextbox;//捕获的htmlInputElement元素
	this.timeoutId = null;//超时id属性
	this.userText = oTextbox.value;//用户输入的文本值   
	this.aSuggestions=new Array();
	this.autoSuggestions=true;//是否自动提示
	this.init();//初始化整个自动提示控件    
}

//为用户输入获得一个或者更多建议,若没有建议被传递,不触发自动建议列表
//参数1:表示从服务器返回的建议列表值
AutoSuggestControl.prototype.autosuggest = function (aSuggestions, bTypeAhead,bShowSuggestions) {
	this.cur = -1;//为当前建议列表重新初始化指针
    //保证至少有一个建议选项    
	if (aSuggestions.length > 0) {
		if (bTypeAhead) {		
			this.typeAhead(aSuggestions[0][1]);
			//此处处理自动提示时填充的楼宇,获得其id值
			this.idValue=aSuggestions[0][0];
			this.textbox.setAttribute("autoSuggestValue",aSuggestions[0][0]);//获得当前选择Id值 20141212
		}
		if(bShowSuggestions){
			this.showSuggestions(aSuggestions);
		}
	} else {
		this.hideSuggestions();
	}
};

//创建建议列表层,显示多个建议
AutoSuggestControl.prototype.createDropDown = function () {
	
	//创建一个层并设置类型	   
	this.layer = document.createElement("div");
	this.layer.style.position="absolute";//设置layer的定位方式(必须设置)		
	this.layer.className = "suggestions";
	this.layer.style.zIndex="9920";
	this.layer.style.visibility = "hidden";
	this.layer.style.border="solid #817f82 1px";
	this.layer.style.width = this.textbox.offsetWidth;
	document.body.appendChild(this.layer);    
	//border-bottom-color: #817f82;
    //当用户选择了某个建议,获得文本并把它放置到文本框中去
	var oThis = this;//表示整个自动提示控件对象
	this.layer.onmousedown = this.layer.onmouseup = this.layer.onmouseover = function (oEvent) {
		oEvent = oEvent || window.event;
		oTarget = oEvent.target || oEvent.srcElement;
		if (oEvent.type == "mousedown") {			
			oThis.textbox.value = oTarget.firstChild.nodeValue;
			oThis.idValue=  oTarget.id;//获得当前点选项的Id值
			oThis.textbox.setAttribute("autoSuggestValue",oTarget.id);//20141212修改
			oThis.hideSuggestions();			
			//oThis.onChange(oThis.textbox.value, oThis.idValue);
		} else {
			if (oEvent.type == "mouseover") {
				oThis.highlightSuggestion(oTarget);
			} else {
				oThis.textbox.focus();
			}
		}
	};
};

//返回文本框左边位置的的像素值
AutoSuggestControl.prototype.getLeft = function () {	
	var oNode = this.textbox;
	var iLeft = 0;		
	while (oNode.tagName != "BODY") {										
		oNode = oNode.offsetParent;			
		iLeft += oNode.offsetLeft;			
		parseInt(oNode.currentStyle.borderLeftWidth) > 0 ? iLeft += parseInt(oNode.currentStyle.borderLeftWidth) : 0;
	}	
	return iLeft;	
};

//获得文本框顶端位置坐标的函数
AutoSuggestControl.prototype.getTop = function () {
	var oNode = this.textbox;
	var iTop = 0;
	while (oNode.tagName != "BODY") {				
		oNode = oNode.offsetParent;
		iTop += oNode.offsetTop-oNode.scrollTop;
		parseInt(oNode.currentStyle.borderTopWidth) > 0 ? iTop += parseInt(oNode.currentStyle.borderTopWidth) : 0;
	}	
	return iTop;
	
};

//在下拉列表中高亮显示文本或者先前的选项,并把文本放置到文本框中去
//参数的正负表示选择是下或者前一个选项
AutoSuggestControl.prototype.goToSuggestion = function (iDiff) {
	var cSuggestionNodes = this.layer.childNodes;
	if (cSuggestionNodes.length > 0) {
		var oNode = null;
		if (iDiff > 0) {
			if (this.cur < cSuggestionNodes.length - 1) {
				oNode = cSuggestionNodes[++this.cur];
			}
		} else {
			if (this.cur > 0) {
				oNode = cSuggestionNodes[--this.cur];
			}
		}
		if (oNode) {
			this.highlightSuggestion(oNode);			
			this.textbox.value = oNode.firstChild.nodeValue;			
			this.idValue = oNode.id;
			this.textbox.setAttribute("autoSuggestValue",oNode.id);//增加自定义属性20141212
		}
	}
};

//控制3个键按下事件 参数：按键事件的对象
AutoSuggestControl.prototype.handleKeyDown = function (oEvent) {
	switch (oEvent.keyCode) {
	  case 38: //向上箭头
		this.goToSuggestion(-1);
		break;
	  case 40: //向下箭头 
		this.goToSuggestion(1);
		break;
	  case 27: //ESC键
		this.textbox.value = this.userText;
		this.selectRange(this.userText.length, 0);
            //继续执行
	  case 13: //Enter键
		this.hideSuggestions();
		oEvent.returnValue = false;
		if (oEvent.preventDefault) {
			oEvent.preventDefault();
		}
		break;
	}
};


//控制键抬起事件 参数:键抬起事件的对象
AutoSuggestControl.prototype.handleKeyUp = function (oEvent) {
	var iKeyCode = oEvent.keyCode;
	var oThis = this;
	  
    //获得当前键入的文本值
	this.userText = this.textbox.value;
	clearTimeout(this.timeoutId);    
    //为退格键和删除键,在没有提示下显示建议列表 
	if (iKeyCode == 8 || iKeyCode == 46) {
     	//如果文本框中的值为空时隐藏层
		if (oThis.textbox.value == "") {
			oThis.layer.innetHTML = "";
			oThis.hideSuggestions();
			oThis.idValue=null;
			oThis.textbox.setAttribute("autoSuggestValue",null);//20141212 修改
		} else {
			this.timeoutId = setTimeout(function () {
				oThis.provider.requestSuggestions(oThis, oThis.autoSuggestions,true);
			}, 500);
		}    
    //确定非字符键没有接口        
	} else {
		if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode < 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //忽略执行
		} else {			
    		//从服务提供者获得建议列表值
			this.timeoutId = setTimeout(function () {
				oThis.provider.requestSuggestions(oThis, oThis.autoSuggestions,true);
			}, 500);					
		}
	}
};
AutoSuggestControl.prototype.initSuggestions = function () {
	var oThis = this;
	oThis.provider.initSuggestions(oThis, false,false);
	this.hideSuggestions();
}
//隐藏建议列表
AutoSuggestControl.prototype.hideSuggestions = function () {
	this.layer.style.visibility = "hidden";
};

//给列表中的节点高亮显示
AutoSuggestControl.prototype.highlightSuggestion = function (oSuggestionNode) {
	for (var i = 0; i < this.layer.childNodes.length; i++) {
		var oNode = this.layer.childNodes[i];
		if (oNode == oSuggestionNode) {
			oNode.style.backgroundColor = "#EBEBEB";
			//oNode.style.color = "#6600FF";
		} else {
			if (oNode.style != "") {
				oNode.style.backgroundColor = "#FFFFFF";
				//oNode.style.color = "#000000";
			}
		}
	}
};

//用自动建议功能的事件控制来初始化文本框
AutoSuggestControl.prototype.init = function () {
    //保存一个this对象的引用
	var oThis = this;

    //附加onkeyup事件    
	this.textbox.onkeyup = function (oEvent) {
        //检查事件对象发生的位置        
		if (!oEvent) {
			oEvent = window.event;
		}    
        //为事件发生对象调用handleKeyUp()方法        
		oThis.handleKeyUp(oEvent);
	};
    //附加onkeydown事件
	this.textbox.onkeydown = function (oEvent) {
				
		//检查事件对象发生的位置	
		if (!oEvent) {
			oEvent = window.event;
		} 
           
        //为事件发生对象调用handleKeyDown()方法         
		oThis.handleKeyDown(oEvent);
	};
    
    //附加onblur事件(隐藏建议列表)       
	this.textbox.onblur = function () {
		oThis.hideSuggestions();
		//如果没有选择清空多余内容
		if(oThis.textbox.autoSuggestValue==null){
			oThis.textbox.value="";
		}
		if(oThis.callBackFun!=""){
			//执行回调函数
			eval(oThis.callBackFun+"()");
		}		
		//oThis.onChange(oThis.textbox.value, oThis.idValue);
	};
	this.textbox.onmouseup = function (oEvent) {
		//alert(oThis.aSuggestions.length);
		oThis.showSuggestions(oThis.aSuggestions);
	}
    //创建建议下拉列表    
	this.createDropDown();
};

//在文本框选择文字的范围
AutoSuggestControl.prototype.selectRange = function (iStart, iEnd) {
    //为IE使用文本的范围   
	if (this.textbox.createTextRange) {
		var oRange = this.textbox.createTextRange();
		oRange.moveStart("character", iStart);
		oRange.moveEnd("character", iEnd - this.textbox.value.length);
		oRange.select();
    
    //使用函数 setSelectionRange() 为Mozilla选择范围   
	} else {
		if (this.textbox.setSelectionRange) {
			this.textbox.setSelectionRange(iStart, iEnd);
		}
	}     
	//让文本框获得焦点
	this.textbox.focus();
};
AutoSuggestControl.prototype.getValue=function(){
	if(this.textbox.value==""){
		return null;
	}else{
		return this.idValue;
	}
};
//创建建议列表框的内容,移动到恰当的位置,并显示该层
//参数:建议列表值数组
AutoSuggestControl.prototype.showSuggestions = function (aSuggestions) {
	var oDiv = null;
	this.layer.innerHTML = "";  //清除层中的内容
	if(aSuggestions.length==0){
		this.layer.style.display="none";
		return;
	}else{
		this.layer.style.display="";
	}
	for (var i = 0; i < aSuggestions.length; i++) {
		oDiv = document.createElement("div");
		oDiv.style.fontSize = "12px";		
		oDiv.style.width = this.textbox.offsetWidth + "px";		
		oDiv.style.backgroundColor = "snow";
		oDiv.style.borderBottomWidth = "1px"; 
		oDiv.style.cursor="hand";
        //把id值存放到每个div层中id属性中去
		oDiv.id = aSuggestions[i][0];
       	//把文本放入到节点中innerText中       	
		oDiv.appendChild(document.createTextNode(aSuggestions[i][1]));
		this.layer.appendChild(oDiv);
	}			
	this.layer.style.left = this.getLeft()+ "px";		
	this.layer.style.top = (this.getTop() + this.textbox.offsetHeight) + "px";
	this.layer.style.visibility = "visible";
};

//在文本框中插入一个建议值,高亮显示建议部分的值
//参数:文本框建议值
AutoSuggestControl.prototype.typeAhead = function (sSuggestion) {
    //检查是否支持输入前提示功能
	if (this.textbox.createTextRange || this.textbox.setSelectionRange) {
		var iLen = this.textbox.value.length;
        //处理输入没有记录的情况
		if (sSuggestion == "\u65e0\u76f8\u5173\u8bb0\u5f55!") {
			this.textbox.value = "";
			this.selectRange(0, sSuggestion.length);
		} else {
			this.textbox.value = sSuggestion; 
			this.selectRange(iLen, sSuggestion.length);
		}
	}
};
/**
 * 选择事件
 * @param text
 * @param value
 */
AutoSuggestControl.prototype.onChange=function(text,value){
	//alert(text+"    "+value);
}
AutoSuggestControl.prototype.setValue=function(text,value){
	var aSuggestions = new Array();
	aSuggestions.push([value,text]);
	this.setValues(aSuggestions);
}
AutoSuggestControl.prototype.setValues=function(aSuggestions){
	this.autosuggest(aSuggestions,true,false);
	this.aSuggestions=aSuggestions;
}
//=======通过dwr框架实现把服务器端的数据传输到客户端========
//为客户端提供建议列表的对象
function SuggestionProvider() {
}


//对对象实现添加一个方法:给自动提示控件返回建议列表
SuggestionProvider.prototype.requestSuggestions = function (oAutoSuggestControl, bTypeAhead,bShowSuggestions) {
   //=========通过dwr调用服务器端的函数的到搜寻的结果============
	var keywords = oAutoSuggestControl.textbox.value;
   //以数组的形式返回查询的结果【调用相关的函数返回查询的结果】 
	if (keywords != "") {
		this.initSuggestions(oAutoSuggestControl, bTypeAhead, bShowSuggestions);
	} else {
		oAutoSuggestControl.idValue=null;
		oAutoSuggestControl.textbox.setAttribute("autoSuggestValue",null);//自定义属性
		return;
	}
};

SuggestionProvider.prototype.initSuggestions=function(oAutoSuggestControl, bTypeAhead,bShowSuggestions){
	var aSuggestions = new Array();
	var keywords = oAutoSuggestControl.textbox.value;
	var tableName = oAutoSuggestControl.tableName;
	var valueField=oAutoSuggestControl.valueField;
	var textField = oAutoSuggestControl.textField;
	var filterCondition = oAutoSuggestControl.filterCondition;
	var limit = oAutoSuggestControl.limit;
	var dataUrl=oAutoSuggestControl.url;
	if(!dataUrl){
		dataUrl=basePath+ "kkgl/getAutoSuggestList.do";
	}
	//alert(keywords);
	var data = {
		tableName : tableName,
		textField : textField,
		valueField : valueField,
		inputValue : encodeURIComponent(keywords),
		limit : limit,
		filterCondition:filterCondition
	};
	if(oAutoSuggestControl.data){
		var newData=$.extend(data,oAutoSuggestControl.data);
		data=newData;
	}
	$.ajax({
		 url:dataUrl,
		  data:data,
		  dataType:'text',
		  success: function(data) {			  
			  var opts=eval(data);
			  if(opts.length==0){
					aSuggestions[0] = [null, "\u65e0\u76f8\u5173\u8bb0\u5f55!"];
			  }else{
				  for(var i=0;i<opts.length;i++){
					  aSuggestions.push([opts[i].value, opts[i].text]);
				  }
			  }			  
			  oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead,bShowSuggestions);
			  oAutoSuggestControl.aSuggestions=aSuggestions;
		  }
	});
}
