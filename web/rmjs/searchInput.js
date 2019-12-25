//shiyl
//回调函数 onLeaveSearchInput(inputObject) -- inputObject为离开焦点的搜索框对象。
//回调事件 onchange() -- 代码关联隐藏框的onchange()事件在搜索框发生变化时会自动调用。
var _index = 0;
var _index2 = 0;
//使用框
var _inputElement;
//隐藏框
var _hiddenInput;
var _noAction;
var _suggestElement;
var _maskElement;
//多参数名，目前使用不到
var _paraNames  = new Array("");
var _paraValues = new Array("");
//传入url地址
var _url;
//传入参数
var _param;
//限制行数,设置为0则行数不限制
var _limitRow = 200;
//设置是否获取焦点就自动显示下拉列表。默认为false。
//现设置自动显示下拉列表的方式是输入空格
var _showOnFocus = false;

//应用例子：初始化列表
//各使用函数方法固定后使用
//设备供应商地址为：/rmweb/equipment.dev?method=getSuppliersMap
//function initSearch(textInputId, hiddenInputId) {
//	initSearchInput('<c:url:method></c:url>', 'param', textInputId, hiddenInputId);
//}

function initSuppliersSearchPolice(textInputId, hiddenInputId){
	initSearchInput("/rmweb/suppliers.dev?method=getPqmj","pqmjmc",textInputId,hiddenInputId);
}
//使用实例：
//查找各类别的厂商
//dwfl为单位分类，如果为多单位，则直接输入，例如：123或234
function initSuppliersSearch(dwfl, textInputId, hiddenInputId){
	initSearchInput("/rmweb/suppliers.dev?method=getSuppliersMap&dwfl="+dwfl,"dwmc",textInputId,hiddenInputId);
}
//查找鉴定计量单位
function initMeasurmentSearch(textInputId, hiddenInputId){
	initSearchInput("/rmweb/devMeasurment.dev?method=getMeasurmentsMap","dwmc",textInputId,hiddenInputId);
}
//查找品牌
function initStyleSearch(textInputId, hiddenInputId, dwbh){
	var url = "/rmweb/vgat.dev?method=getStylesMap";
	if(typeof dwbh!="undefined"){
		url += "&dwbh="+dwbh;
	}
	initSearchInput(url,"ppmc",textInputId,hiddenInputId);
}

//查找巡逻车辆
function initPatrolCarSearch(glbm,textInputId, hiddenInputId){
	var url = "/rmweb/petrolcar.dev?method=getXlcl";
	if(typeof glbm!="undefined"){
		url += "&glbm="+glbm;
	}
	initSearchInput(url,"xlclmc",textInputId,hiddenInputId);
}

//模糊匹配违法行为代码
function initWfxwsSearch(wfxws, textInputId, hiddenInputId){
	var url = "/rmweb/screen.vio?method=getWfxwsMap&wfxws="+wfxws;
	initSearchInput(url,"wfxw",textInputId,hiddenInputId);
}

//通用初始化接口
//url: AJAX请求地址。如果是<select>作为数据源则url为空。
//paraName: 请求参数名称。如果是<select>作为数据源则paraName为<select>的Id。
//textInputId：文本输入框的Id。搜索框是根据本元素生成。
//hiddenInputId: 代码关联隐藏框的Id。
function initSearchInput(url, paraName, textInputId, hiddenInputId) {
	_inputElement = document.getElementById(textInputId);
	if (!_inputElement) { return; }
	_initSearch();
	var changeEvent = function(_event) {
		var event = window.event||_event ;
		_searchInput(url, paraName, hiddenInputId, this,event);
	};
	if (window.attachEvent) {
		_inputElement.onpropertychange = changeEvent;
	} else {
		_inputElement.addEventListener("input", changeEvent, false);
	}
}

//设置是否获取焦点就自动显示下拉列表。默认为false。
function setSearchOnFocus(flag) {
	_showOnFocus = flag;
}
/////////////////////////////////////////////////////
//初始化元素属性
function _initSearch() {
	if(_isInIE()){//IE
		_inputElement.onblur = _afterLeaveInput;
	}
	
	_inputElement.onkeyup = function() {_handleKeyPress(this, event);};
	_inputElement.onkeydown = function() {_handleEnter(this, event);};
	_inputElement.autocomplete = "off";
	_inputElement.title = "输入关键字搜索。下拉列表最多显示" + _limitRow + "行。";
	_inputElement.style.cursor ="arrow";
	_inputElement.style.paddingRight = "15px";
	_inputElement.style.backgroundRepeat ="no-repeat";
	_inputElement.style.backgroundImage = 'url("rmjs/treeview/custPicker.gif")';
	//_inputElement.style.backgroundAttachment = "fixed";
	_inputElement.style.backgroundPosition = "right";
	if (_showOnFocus) {
		_inputElement.onfocus = function() {_autoShow(this);};
		_inputElement.onclick = function(event) {_handleClick(this,event);};
	} else {
		_inputElement.onclick = function(event) {_handleClick(this,event);};
	}
	_suggestElement = document.getElementById("suggest");
	if (!_suggestElement) {
		_maskElement = document.createElement("iframe");
		_maskElement.setAttribute("frameborder","0");
		_maskElement.style.position = "absolute";
		document.body.appendChild(_maskElement);
		_maskElement.style.z_index = "-1";
		_suggestElement = document.createElement("div");
		_suggestElement.setAttribute("id","suggest");
		_maskElement.style.display = "none";
		document.body.appendChild(_suggestElement);
		//_suggestElement.style.z_index = "100";
		//_suggestElement.onfocus = focusSearch;
		if(_isInIE()){//IE
			_suggestElement.onblur = _afterLeaveInput;
		}
		else{//webkit
			_suggestElement.style.zIndex = "110";
		}
		_suggestElement.onkeydown = function() {_handleEnter(this, event);};
		//在模糊搜索下增加一个关闭模糊搜索功能（针对移动设备）
		_createSearchInputClose();
	}
}
//在模糊搜索下增加一个关闭模糊搜索功能（针对移动设备）
function _createSearchInputClose(){
	if(!_isInIE()){//非IE
		var _multiSelectClose = document.createElement("div");
		_multiSelectClose.setAttribute("id","_SearchInputClose");
		_multiSelectClose.style.display = "none";
		document.body.appendChild(_multiSelectClose);
		_multiSelectClose.style.zIndex = "99";
		_multiSelectClose.style.position = "absolute";
		_multiSelectClose.style.bgColor = "#FFFFFF";
		_multiSelectClose.style.border = "1px solid #000000";
		_multiSelectClose.style.margin=" -1px -1px 0px 0px";
		var temp_str ;
		temp_str = "<table width=\"100%\" border=\"0\" bgcolor=\"#FFFFFF\" >";		
		temp_str += "<tr><td style=\"text-align:right\" ><span style=\"cursor:pointer; text-decoration:underline;color:blue;\" ";
		temp_str += "onclick=\"_clearInput()\" >清除</span>&nbsp;&nbsp;<span style=\"cursor:pointer; text-decoration:underline;color:blue;\" ";
		temp_str += "onclick=\"_closeSuggest()\" >关闭</span> </td></tr> </table> ";
		_multiSelectClose.innerHTML = temp_str;
	}
}
//处理入口
function _searchInput(url, paraName, hiddenInputId, textInput, event) {
	if ((event.propertyName == "value" || (textInput.hasOwnProperty && textInput.hasOwnProperty("value") ))&& !_noAction) {
	    _inputElement = textInput;
	    _hiddenInput = document.getElementById(hiddenInputId);
		_url = url;
		_param = paraName;
		setTimeout("_valueChanged(0)",1);
	}
}

//强制显示
function _autoShow(textInput) {
	textInput.value = textInput.value;
	if (_inputElement !== null && isNull(_inputElement.value)) {
		setTimeout("_valueChanged(1)",50);
		return;
	}
	if(isNull(_url)){
		setTimeout("_valueChanged(2)",50);
	}
}

//处理鼠标点击输入框
function _handleClick(textInput,_event) {
	var event = window.event||_event ;
	var offsetLeft = 0, offsetTop = 0, p = textInput;
	while (p && p.tagName!="BODY" && p.tagName!="body") {
		offsetTop += p.offsetTop;
		offsetLeft += p.offsetLeft;
		p = p.offsetParent;
	}
	var scrollTop, scrollLeft;
	if (document.all) {
		scrollTop = document.body.scrollTop;
		scrollLeft = document.body.scrollLeft;
	} else {
		scrollTop = document.documentElement.scrollTop;
		scrollLeft = document.documentElement.scrollLeft;
	}
	if (!scrollTop) {
		scrollTop = 0;
	}
	if (!scrollLeft) {
		scrollLeft = 0;
	}
	if (event.clientX - offsetLeft + 18 + scrollLeft < textInput.offsetWidth) {
		return;
	}
	if(document.createEvent){//对于webkit，第一次不会触发onpropertychange
		var event = document.createEvent('HTMLEvents');
		event.initEvent("input",true,true);
		textInput.dispatchEvent(event);
	}
	_autoShow(textInput);
}
//处理键盘输入
function _handleKeyPress(textInput,event) {
	if (!event) {return;}
	if (event.keyCode == 40) { //往下按
	    if (_suggestElement.style.display != "none" && _suggestElement.hasChildNodes()) {
		    var suggestItems = _suggestElement.childNodes;
		    if (_index2 > -1) {
		    	if (_index2 < suggestItems.length -2) {
				_index2++;
				} else {
					_index2 = 0;
				}
				_index = _index2;
		    } else {
				if (_index < suggestItems.length -2) {
					_index++;
				} else {
					_index = 0;
				}
			}
			_setSearchStyle();
		} else {
			_autoShow(textInput);
		}
	}
	else if (event.keyCode == 38) { //往上按 
		if (_suggestElement.hasChildNodes()) {
		    var suggestItems = _suggestElement.childNodes;
		    if (_index2 > -1) {
			    if (_index2 > 0) {
					_index2--;
				} else {
					_index2 = suggestItems.length -2;
				}
				_index = _index2;
		    } else {
				if (_index > 0) {
					_index--;
				} else {
					_index = suggestItems.length -2;
				}
			}
			_setSearchStyle();
		}
	} 
	else if (event.keyCode == 13){ //回车
	} 
}

//处理回车
function _handleEnter(textInput,event) {
	if (!event) return;
	if (event.keyCode == 13) {
		if (_suggestElement.hasChildNodes()) {
			_closeSuggest();
			event.returnValue = null;
			window.returnValue = null;
		}
	}
}
//主要处理过程
function _valueChanged(flag){
   	_closeSuggest();
    if (_param != null && _param != '' && _inputElement != null 
    	&& (flag == 2 || flag == 1 || flag == 0 && _inputElement.value != '')
    	&& document.activeElement == _inputElement){
   		var divs = "<br><span style=\"text-align:center;color:red\">正在加载...";
   		if(_isInIE()){//IE
   			divs += "<br><div style=\"text-align:right; padding: 2px 3px 2px 3px;\">"
			+"<a href=\"javascript:_clearInput();\" id=\"clearLink\">清除</a> <a href=\"javascript:_closeSuggest();\" id=\"closeLink\">关闭</a></div>";
   		}
   		else{
   			divs += "<br>";
   		}
   		divs += "</span>";
   		_suggestElement.innerHTML = divs;
		_showSuggest();

    	if (isNull(_url)) {
			var respContent = "";
    		//下拉列表作为数据源
    		var selectElement = document.getElementById(_param);
    		if (selectElement) {
    		    //是否已有匹配项
    		    var match = (_hiddenInput != null && _hiddenInput.value != '');
    			var thisValue = removeSpace(_inputElement.value);
				for (var i = 0; i < selectElement.options.length; i++ ) {
					if (thisValue == "" || match ||
						selectElement.options[i].text.toLowerCase().indexOf(thisValue.toLowerCase()) >= 0) {
						if (respContent != "") respContent += "|";
						respContent += selectElement.options[i].value + "," + selectElement.options[i].text;
					}
				}
			}
            _createSuggest(respContent);
    	} else {
		  	ajax = new Ajax();
			ajax.setParameter(_param,removeSpace(_inputElement.value));
			if(_paraNames){
			 	for(var i=0;i<_paraNames.length;i = i+1){
			       if(_paraNames[i]){
				   		ajax.setParameter(_paraNames[i],removeSpace(_paraValues[i]));
			       }
			  	}
			}
			ajax.setCallback("_showSuggestList");
			ajax.setMethod("GET");
			ajax.setUrl(_url);
			ajax.setAsyn(true);
			ajax.sendRequest();
		}
	} else {
		if (document.activeElement == _inputElement) {
			_changeValue(_hiddenInput, "");
		}
	}
}

//更改数值
function _changeValue(obj, newValue) {
	var oldValue = obj.value;
	obj.value = newValue;
	if (newValue != oldValue) {
		if(obj.fireEvent){//IE
			obj.fireEvent("onchange");
		}
		else if(typeof(document.createEvent) == "function"){//webkit
		 	var e = document.createEvent('HTMLEvents');
		    e.initEvent("change", false, false);
		    obj.dispatchEvent(e);
		}
	}
}

//创建下拉列表
function _createSuggest(respContent) {
	var itemWidth = _inputElement.offsetWidth - 5;
	if (respContent.length > 0) {
		var divs = '';
		_index2 = -1;
		var itemsArray = respContent.split("\|");
		var len = itemsArray.length;
      	if (len >= 10) {
      	 	_suggestElement.style.height = "150px";
      		itemWidth = itemWidth - 20;
      	}
      	var itemWidthStyle = "";
      	var thisValue = removeSpace(_inputElement.value);
      	var found = false;
      	var hasMore = false;
      	var itemCount = _limitRow;
		for (var i = 0; i < len; i++ ) {
			if (_limitRow != 0 && i >= _limitRow) {
				hasMore = true;
			}
	        var itemFields = itemsArray[i].split(","); 
	        if (itemFields.length < 2) {
	        	itemFields[1] = itemFields[0];
	        } else {
	        	itemFields[1] = escapeXML(itemFields[1]);
	        }
		     if (thisValue == itemFields[1]) {
		     	if (hasMore) {
		        	divs += ' <div onmouseover="javascript:_suggestOver(this,' + _limitRow;
		        } else {
		        	divs += ' <div onmouseover="javascript:_suggestOver(this,' + i;
		        }
			    divs += ');" onmouseout="javascript:_suggestOut(this);" onclick="javascript:_clickSearch(this);"';
			    divs += itemWidthStyle;
		     	divs += ' class="suggest_link_over"';
				if (_hiddenInput != null && !found) {
					if (hasMore) {
		     			_index = _limitRow;
		     			itemCount = _limitRow + 1;
					} else {
		     			_index = i;
		     		}
					found = true;
					_changeValue(_hiddenInput, itemFields[0]);
				}
				divs += ' code="'+itemFields[0] + '">' + itemFields[1] + '</div>';
		     } else {
		     	if (!hasMore) {
			        divs += ' <div onmouseover="javascript:_suggestOver(this,' + i;
				    divs += ');" onmouseout="javascript:_suggestOut(this);" onclick="javascript:_clickSearch(this);"';
				    divs += itemWidthStyle;
			     	divs += ' class="suggest_link"';
				    divs += ' code="'+itemFields[0] + '">' + itemFields[1] + '</div>';
		     	}
		     }
		}
      	var more = '';
		if (hasMore) {
			more = '<label title="总共有'+ len + '行，当前只显示' + itemCount + '行。">['+ itemCount+'/'+ len +']</label><br>';
		}
		divs += '<div style="width:' + itemWidth +
				'px; text-align:right; padding: 2px 3px 2px 3px;">'+more;
		if(_isInIE()){//IE
   			divs += '<a href="javascript:_clearInput();" id="clearLink">清除</a> <a href="javascript:_closeSuggest();" id="closeLink">关闭</a>';
   		}
		divs += '</div>';
		_suggestElement.innerHTML = divs;
		_showSuggest();
		if (found) {
		    _suggestElement.scrollTop = 0;
	    	var suggestItems = _suggestElement.childNodes;
		    _suggestElement.scrollTop = suggestItems[_index].offsetTop;
		}
		if (_hiddenInput != null && !found) {
			_changeValue(_hiddenInput, "");
		}
	} else {
		_suggestElement.innerHTML = '<div style="width:' + itemWidth +
				 'px; padding: 2px 3px 2px 3px;" code="">无匹配记录。</div>' ;
		if(_isInIE()){//IE
			_suggestElement.innerHTML += '<div style="width:' + itemWidth +
			 'px; text-align:right; padding: 2px 3px 2px 3px;"><a href="javascript:_clearInput();" id="clearLink">清除</a> <a href="javascript:_closeSuggest();" id="closeLink">关闭</a></div>';
		}
		_showSuggest();
		if (_hiddenInput != null) {
			_changeValue(_hiddenInput, "");
		}
	}
}

//清除输入
function _clearInput() {
	_inputElement.value = "";
	if(!_isInIE() && _hiddenInput && _inputElement){//非IE
		_hiddenInput.value="";
		var event = document.createEvent('HTMLEvents');
		event.initEvent("input",true,true);
		_inputElement.dispatchEvent(event);		
	}
}

//显示下拉列表
function _showSuggest() { 
	var offsetLeft = 0, offsetTop = 0, p = _inputElement;
	while (p && p.tagName!="BODY" && p.tagName!="body") {
		offsetTop += p.offsetTop;
		offsetLeft += p.offsetLeft;
		if (p.tagName=="FIELDSET") {
			offsetLeft += 2;
		}
		p = p.offsetParent;
	}
	offsetTop = offsetTop + _inputElement.offsetHeight - 1;
	_suggestElement.style.left = offsetLeft;
	_suggestElement.style.top = offsetTop;
	_suggestElement.style.width = _inputElement.offsetWidth;
	
	_maskElement.style.left = offsetLeft;
	_maskElement.style.top = offsetTop;
	_maskElement.style.width = _inputElement.offsetWidth;
	_maskElement.style.display = "block";
	_suggestElement.style.display = "block";
	_maskElement.style.height = _suggestElement.offsetHeight;
	if(!_isInIE()){//非IE
		var _SearchInputClose = document.getElementById("_SearchInputClose"); 
		if(_SearchInputClose){
			_SearchInputClose.style.posLeft = offsetLeft;
			_SearchInputClose.style.posTop =offsetTop+_suggestElement.offsetHeight;	
			_SearchInputClose.style.width = _suggestElement.offsetWidth-2;
			_SearchInputClose.style.display = "block";
		}	
	}
	if(!_isInIE() && typeof(_closeMultiSelect)=="function"){//非IE,关闭多选
		_closeMultiSelect();
	}
}

//URL结果创建下拉列表
function _showSuggestList() { 
    if (ajax.xmlHttp.readyState == 4) {
        if(ajax.xmlHttp.status == 200) {   
            var respContent = ajax.xmlHttp.responseText;	 
			 _createSuggest(respContent);
         } else{ 
			if (_hiddenInput != null) {
				_changeValue(_hiddenInput, "");
			}
	    }
    }
}
   
//关闭下拉列表
function _closeSuggest() {  
	if(_suggestElement){
		_suggestElement.innerHTML = '';
		_suggestElement.style.height = "";
		_suggestElement.style.display = "none";
	}
	
	_index = -1;
	if(_maskElement){
		_maskElement.style.display = "none";
	}
	
	if(!_isInIE()){//非IE
		var _SearchInputClose = document.getElementById("_SearchInputClose"); 
		if(_SearchInputClose){
			_SearchInputClose.style.display = "none";
		}		
	}
	
}
//鼠标移到上面
function _suggestOver(item, index2) {
    item.className = 'suggest_link_over';
    _index2 = index2;
}

//鼠标移出
function _suggestOut(item) {
	item.className = 'suggest_link';
}
//选中后设置样式
function _setSearchStyle() {
    if (_suggestElement.hasChildNodes()) {
	    var suggestItems = _suggestElement.childNodes;
	    var count = suggestItems.length;
	    for(var i=0; i<count; i++){
	    	if (i == _index) {
	    		suggestItems[i].className = 'suggest_link_select';
	    		_noAction = true;
	    		_inputElement.value = suggestItems[i].firstChild.nodeValue;
	    		_noAction = false;
	    		if (_hiddenInput != null) {
					_changeValue(_hiddenInput, suggestItems[i].getAttribute("code"));
				}
			} else {
	       		if (suggestItems[i].className != 'suggest_link'){
		       		suggestItems[i].className = 'suggest_link';
	       		}
			}
	    }
	    if (suggestItems[_index].offsetTop > _suggestElement.scrollTop + _suggestElement.offsetHeight ||
	    	suggestItems[_index].offsetTop < _suggestElement.scrollTop) {
	    	_suggestElement.scrollTop = suggestItems[_index].offsetTop;
	    }
    }
}

//鼠标选择一行
function _clickSearch(item) {
	_noAction = true;
	_inputElement.value = item.firstChild.nodeValue;
	_noAction = false;
	if (_hiddenInput != null) {
		_changeValue(_hiddenInput, item.getAttribute("code"));
	}
	_closeSuggest();
}
//离开焦点
function _afterLeaveInput() {
	var _noClose = false;
	var p = document.activeElement;
	while (p && p.tagName!="BODY" && p.tagName!="body") {
		if ((p == _inputElement || p == _suggestElement || p == ID("clearLink")) && p != ID("closeLink")) {
			_noClose = true;
		}
		p = p.offsetParent;
	}
	if (!_noClose && _hiddenInput) {
		if (_inputElement.value == "") {
			_changeValue(_hiddenInput, "");
		}
		setTimeout("_closeSuggest()", 1);
	}
	p = document.activeElement;
	if (!_showOnFocus && (p.tagName == "BODY" || p == ID("clearLink") || p == ID("closeLink"))) {
		try {
			_inputElement.focus();
		} catch (e) {
		
		}
	}
	if (!_noClose) {
		if (typeof(onLeaveSearchInput) == "function") {
			onLeaveSearchInput(_inputElement,_hiddenInput);
		}
	}
}
//因为是可输入可匹配，所以存在问题，可能输入的内容后台查找不到
//所以需要加上判断，防止输入内容不准确，导致误判
//如果输入不准确，hiddenInputId会为空，而textInputId不为空
//如果输入内容后台没有，就保存输入的新内容，则此步不需要判断
//判断正确性要放在非空的前面，否则提示不准确
function checkValidity(textInputId, hiddenInputId, name) { 
	if(ID(hiddenInputId).value==''&&ID(textInputId).value!=''){
		alert("请正确填写"+name+"！");
		return false;
	}
	return true;
}
//检测当前浏览器是否为IE,
//true-IE,false-不是IE
//采用惰性载入函数的方式，仅对一次判断(在频繁重复的调用中加快执行速度) 
function _isInIE(){
	var isInIe = !!(document.all && navigator.userAgent.indexOf('Opera') === -1);
	_isInIE = isInIe?function(){return true;}:function(){return false;};
	return isInIe;
}

//判定字符串是否为空值
function isNull(str) { 
  if (removeSpace(str)==""){
    return true;
  }
  return false;
}

//除去字符串前后的空格
function removeSpace(str) {
  var returnStr = str;

  while(returnStr.charAt(0)==" ") {
	returnStr=returnStr.substring(1,returnStr.length);
  }
  while(returnStr.charAt(returnStr.length-1)==" ") {
	returnStr=returnStr.substring(0,returnStr.length-1);
  }
  return returnStr;
}

function escapeXML(s) {
	var s1 = s.replace("&", "&amp;");
	s1 = s1.replace("<", "&lt;");
	s1 = s1.replace(">", "&gt;");
	s1 = s1.replace("'", "&apos;");
	s1 = s1.replace('"', "&quot;");
	return s1;
}
//查找id号所对应的对象，用以查找值等信息
function ID(id) {
	return document.getElementById(id);
}

//所使用的Ajax的Js对象
function Ajax(){	
	
	this.nameArray = new Array(0);
	this.valueArray = new Array(0);
	this.xmlHttp;		
	this.method = "GET";
	this.url;
	this.asyn = true;
	this.callback;
	this.extraArray = new Array();
}


Ajax.prototype.getExtraArray = function(){
	return this.extraArray;
}

Ajax.prototype.getExtraValue = function(name){
	return this.extraArray[name];
}

Ajax.prototype.setExtraValue = function(name, value){
	return this.extraArray[name] = value;
}

Ajax.prototype.setParameter = function(a,b){
	this.nameArray.push(a);
	this.valueArray.push(encodeURI(encodeURI(b)));
}

Ajax.prototype.setCallback = function(a){
	this.callback = a;
}

Ajax.prototype.setMethod = function(a){
	this.method = a;
}

Ajax.prototype.setUrl = function(a){
	this.url = a;
}

Ajax.prototype.setAsyn = function(a){
	this.asyn = a;
}

Ajax.prototype.sendRequest = function(){
    
    if(!this.checkSetting())
    	return;
	
	if(!this.initialize())
		return;
	
	try{
		if (this.method == "GET")
			if (this.url.indexOf("?") >= 0) {
				this.xmlHttp.open(this.method,this.url + "&isAjaxT=1&" + this.createParameterString(),this.asyn);
			} else {
				this.xmlHttp.open(this.method,this.url + "?isAjaxT=1&" + this.createParameterString(),this.asyn);
			}
		else{
			if (this.url.indexOf("?") >= 0) {
				this.xmlHttp.open(this.method,this.url + "&isAjaxT=1",this.asyn);
			} else {
				this.xmlHttp.open(this.method,this.url + "?isAjaxT=1",this.asyn);
			}
		}

		this.xmlHttp.onreadystatechange = eval(this.callback);
		
	if(this.method == "GET"){
	    this.xmlHttp.send(null);
	}
	if(this.method == "POST"){
	    this.xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    this.xmlHttp.send(this.createParameterStringPost());
	}
	if(this.method == "HEAD"){
	    this.xmlHttp.send(null);
	}  
	
	}catch(e){
//		alert("error: " + e.message + "; error name: " + e.name);  
	}  	    
}

Ajax.prototype.checkSetting = function(){
	
	if(this.callback == null || this.callback == ""){
		alert("please set callback function"); 
		return false;
	}	
	if(this.url == null || this.url == ""){
		alert("please set request url"); 
		return false;
	}	
	if(this.method == null || !(this.method == "GET" || this.method == "POST" || this.method == "HEAD")){
		alert("request method must be one of GET,POST,HEAD"); 
		return false;
	}
	
	return true;
}


Ajax.prototype.initialize = function(){
	
	if(!this.isSupport()) 
	    return false;
	
	this.xmlHttp = false;
	
    if (window.XMLHttpRequest) {
        this.xmlHttp = new XMLHttpRequest();
    } 
    else {
        try {
            this.xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {
            try {
                this.xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
            	this.xmlHttp = false;
            }
        }
    }   
        
    if(this.xmlHttp)
        return true;
    else
        return false;
}


Ajax.prototype.isSupport = function(){
	
	if (window.XMLHttpRequest || window.ActiveXObject){
		return true;
	}
	else{
		alert("your explorer is nonsupport for XMLHttpRequest");
		return false;
	}
}


Ajax.prototype.createParameterString = function(){
	var temp = "";
	for(var i=0;i<this.nameArray.length;i++){	
		if(temp == "")
		    temp = temp + this.nameArray[i] + "=" + this.valueArray[i];
		else
		    temp = temp + "&" + this.nameArray[i] + "=" + this.valueArray[i];
	}
	return temp;
}


Ajax.prototype.createParameterStringPost = function(){
	
	var temp = "";
	for(var i=0;i<this.nameArray.length;i++){		
		if(temp == "")
		    temp = temp + this.nameArray[i] + "=" + this.valueArray[i];
		else
		    temp = temp + "&" + this.nameArray[i] + "=" + this.valueArray[i];
	}
	return temp;
}

    //-------------------------------------
    
    //var url = "/ajax/ajax.xml";
    //var url = "/ajax/AjaxServlet?param1=" + param1+"&param2=" + param2;
    //var url = "/ajax/ajax.jsp?param1=" + param1+"&param2=" + param2;
  
    //xmlHttp.open(request-type, url, asynch, username, password);	
    //xmlHttp.open("HEAD", url, true);
    //xmlHttp.open("GET", url, true); 
    //xmlHttp.open("POST", url, true);  
    
    //xmlHttp.onreadstatechange = callback;
        
    //xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    
    //xmlHttp.send(null);
    //xmlHttp.send(param1=" + param1+"&param2=" + param2);
    //xmlHttp.send(xmlStr);
  
    //-------------------------------------
  
    //发送请求常见的六个步骤：
    //1.从表单中获取需要的数据
    //2.对数据进行过滤(可选)
    //3.建立要连接的URL
    //4.打开到服务器的连接
    //5.设置服务器在完成后要运行的函数（回调函数）
    //6.发送请求
	 
	//--------------------------------------
	 
	//五种就绪状态：	
	//0：请求没有发出（在调用 open() 之前）。 
	//1：请求已经建立但还没有发出（调用 send() 之前）。 
	//2：请求已经发出正在处理之中（这里通常可以从响应得到内容头部）。 
	//3：请求已经处理，响应中通常有部分数据可用，但是服务器还没有完成响应。 
	//4：响应已完成，可以访问服务器响应并使用它。
	
	//--------------------------------------
	
	//HTTP状态码：
	//200:表示响应一切顺利
	//401：未经授权 
    //403：禁止 
    //404：没找到
    //405 表示不允许使用诸如发送 HEAD 请求之类不可接受的请求方法
    //407 则表示需要进行代理认证
    //301：永久移动 
	//302：找到（请求被重新定向到另外一个 URL/URI 上） 
	//305：使用代理（请求必须使用一个代理来访问所请求的资源）
    //.....
    
	//----------------------------------------
	
	//XMLHttpRequest.readyState    HTTP就绪状态
	//XMLHttpRequest.status        HTTP状态码
	//XMLHttpRequest.statusText    HTTP状态描述
	//XMLHttpRequest.responseText  服务器的响应文本
	//XMLHttpRequest.responseXML   服务器的响应文本
	
	//----------------------------------------
	
	//XMLHttpRequest.getAllResponseHeaders()  获取响应头的内容
	//XMLHttpRequest.getResponseHeader("Content-Length")  获取响应的长度
	//XMLHttpRequest.getResponseHeader("Content-Type")  获取响应的内容类型
	
	//----------------------------------------


function respHandler() { 
	
    if (xmlHttp.readyState == 4) {             
  
        if(xmlHttp.status == 200){   
        	          
            //var respContent = xmlHttp.responseText;
            //var respContent = xmlHttp.responseXML;
            
            //--------------------------------  
            // DOM code 
            //--------------------------------
        }
        else{ 
            alert("error: " + xmlHttp.statusText + "; error code: " + xmlHttp.status);  
        }
    }
}
