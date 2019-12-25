//shiyl
//�ص����� onLeaveSearchInput(inputObject) -- inputObjectΪ�뿪��������������
//�ص��¼� onchange() -- ����������ؿ��onchange()�¼������������仯ʱ���Զ����á�
var _index = 0;
var _index2 = 0;
//ʹ�ÿ�
var _inputElement;
//���ؿ�
var _hiddenInput;
var _noAction;
var _suggestElement;
var _maskElement;
//���������Ŀǰʹ�ò���
var _paraNames  = new Array("");
var _paraValues = new Array("");
//����url��ַ
var _url;
//�������
var _param;
//��������,����Ϊ0������������
var _limitRow = 200;
//�����Ƿ��ȡ������Զ���ʾ�����б�Ĭ��Ϊfalse��
//�������Զ���ʾ�����б�ķ�ʽ������ո�
var _showOnFocus = false;

//Ӧ�����ӣ���ʼ���б�
//��ʹ�ú��������̶���ʹ��
//�豸��Ӧ�̵�ַΪ��/rmweb/equipment.dev?method=getSuppliersMap
//function initSearch(textInputId, hiddenInputId) {
//	initSearchInput('<c:url:method></c:url>', 'param', textInputId, hiddenInputId);
//}

function initSuppliersSearchPolice(textInputId, hiddenInputId){
	initSearchInput("/rmweb/suppliers.dev?method=getPqmj","pqmjmc",textInputId,hiddenInputId);
}
//ʹ��ʵ����
//���Ҹ����ĳ���
//dwflΪ��λ���࣬���Ϊ�൥λ����ֱ�����룬���磺123��234
function initSuppliersSearch(dwfl, textInputId, hiddenInputId){
	initSearchInput("/rmweb/suppliers.dev?method=getSuppliersMap&dwfl="+dwfl,"dwmc",textInputId,hiddenInputId);
}
//���Ҽ���������λ
function initMeasurmentSearch(textInputId, hiddenInputId){
	initSearchInput("/rmweb/devMeasurment.dev?method=getMeasurmentsMap","dwmc",textInputId,hiddenInputId);
}
//����Ʒ��
function initStyleSearch(textInputId, hiddenInputId, dwbh){
	var url = "/rmweb/vgat.dev?method=getStylesMap";
	if(typeof dwbh!="undefined"){
		url += "&dwbh="+dwbh;
	}
	initSearchInput(url,"ppmc",textInputId,hiddenInputId);
}

//����Ѳ�߳���
function initPatrolCarSearch(glbm,textInputId, hiddenInputId){
	var url = "/rmweb/petrolcar.dev?method=getXlcl";
	if(typeof glbm!="undefined"){
		url += "&glbm="+glbm;
	}
	initSearchInput(url,"xlclmc",textInputId,hiddenInputId);
}

//ģ��ƥ��Υ����Ϊ����
function initWfxwsSearch(wfxws, textInputId, hiddenInputId){
	var url = "/rmweb/screen.vio?method=getWfxwsMap&wfxws="+wfxws;
	initSearchInput(url,"wfxw",textInputId,hiddenInputId);
}

//ͨ�ó�ʼ���ӿ�
//url: AJAX�����ַ�������<select>��Ϊ����Դ��urlΪ�ա�
//paraName: ����������ơ������<select>��Ϊ����Դ��paraNameΪ<select>��Id��
//textInputId���ı�������Id���������Ǹ��ݱ�Ԫ�����ɡ�
//hiddenInputId: ����������ؿ��Id��
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

//�����Ƿ��ȡ������Զ���ʾ�����б�Ĭ��Ϊfalse��
function setSearchOnFocus(flag) {
	_showOnFocus = flag;
}
/////////////////////////////////////////////////////
//��ʼ��Ԫ������
function _initSearch() {
	if(_isInIE()){//IE
		_inputElement.onblur = _afterLeaveInput;
	}
	
	_inputElement.onkeyup = function() {_handleKeyPress(this, event);};
	_inputElement.onkeydown = function() {_handleEnter(this, event);};
	_inputElement.autocomplete = "off";
	_inputElement.title = "����ؼ��������������б������ʾ" + _limitRow + "�С�";
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
		//��ģ������������һ���ر�ģ���������ܣ�����ƶ��豸��
		_createSearchInputClose();
	}
}
//��ģ������������һ���ر�ģ���������ܣ�����ƶ��豸��
function _createSearchInputClose(){
	if(!_isInIE()){//��IE
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
		temp_str += "onclick=\"_clearInput()\" >���</span>&nbsp;&nbsp;<span style=\"cursor:pointer; text-decoration:underline;color:blue;\" ";
		temp_str += "onclick=\"_closeSuggest()\" >�ر�</span> </td></tr> </table> ";
		_multiSelectClose.innerHTML = temp_str;
	}
}
//�������
function _searchInput(url, paraName, hiddenInputId, textInput, event) {
	if ((event.propertyName == "value" || (textInput.hasOwnProperty && textInput.hasOwnProperty("value") ))&& !_noAction) {
	    _inputElement = textInput;
	    _hiddenInput = document.getElementById(hiddenInputId);
		_url = url;
		_param = paraName;
		setTimeout("_valueChanged(0)",1);
	}
}

//ǿ����ʾ
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

//��������������
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
	if(document.createEvent){//����webkit����һ�β��ᴥ��onpropertychange
		var event = document.createEvent('HTMLEvents');
		event.initEvent("input",true,true);
		textInput.dispatchEvent(event);
	}
	_autoShow(textInput);
}
//�����������
function _handleKeyPress(textInput,event) {
	if (!event) {return;}
	if (event.keyCode == 40) { //���°�
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
	else if (event.keyCode == 38) { //���ϰ� 
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
	else if (event.keyCode == 13){ //�س�
	} 
}

//����س�
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
//��Ҫ�������
function _valueChanged(flag){
   	_closeSuggest();
    if (_param != null && _param != '' && _inputElement != null 
    	&& (flag == 2 || flag == 1 || flag == 0 && _inputElement.value != '')
    	&& document.activeElement == _inputElement){
   		var divs = "<br><span style=\"text-align:center;color:red\">���ڼ���...";
   		if(_isInIE()){//IE
   			divs += "<br><div style=\"text-align:right; padding: 2px 3px 2px 3px;\">"
			+"<a href=\"javascript:_clearInput();\" id=\"clearLink\">���</a> <a href=\"javascript:_closeSuggest();\" id=\"closeLink\">�ر�</a></div>";
   		}
   		else{
   			divs += "<br>";
   		}
   		divs += "</span>";
   		_suggestElement.innerHTML = divs;
		_showSuggest();

    	if (isNull(_url)) {
			var respContent = "";
    		//�����б���Ϊ����Դ
    		var selectElement = document.getElementById(_param);
    		if (selectElement) {
    		    //�Ƿ�����ƥ����
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

//������ֵ
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

//���������б�
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
			more = '<label title="�ܹ���'+ len + '�У���ǰֻ��ʾ' + itemCount + '�С�">['+ itemCount+'/'+ len +']</label><br>';
		}
		divs += '<div style="width:' + itemWidth +
				'px; text-align:right; padding: 2px 3px 2px 3px;">'+more;
		if(_isInIE()){//IE
   			divs += '<a href="javascript:_clearInput();" id="clearLink">���</a> <a href="javascript:_closeSuggest();" id="closeLink">�ر�</a>';
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
				 'px; padding: 2px 3px 2px 3px;" code="">��ƥ���¼��</div>' ;
		if(_isInIE()){//IE
			_suggestElement.innerHTML += '<div style="width:' + itemWidth +
			 'px; text-align:right; padding: 2px 3px 2px 3px;"><a href="javascript:_clearInput();" id="clearLink">���</a> <a href="javascript:_closeSuggest();" id="closeLink">�ر�</a></div>';
		}
		_showSuggest();
		if (_hiddenInput != null) {
			_changeValue(_hiddenInput, "");
		}
	}
}

//�������
function _clearInput() {
	_inputElement.value = "";
	if(!_isInIE() && _hiddenInput && _inputElement){//��IE
		_hiddenInput.value="";
		var event = document.createEvent('HTMLEvents');
		event.initEvent("input",true,true);
		_inputElement.dispatchEvent(event);		
	}
}

//��ʾ�����б�
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
	if(!_isInIE()){//��IE
		var _SearchInputClose = document.getElementById("_SearchInputClose"); 
		if(_SearchInputClose){
			_SearchInputClose.style.posLeft = offsetLeft;
			_SearchInputClose.style.posTop =offsetTop+_suggestElement.offsetHeight;	
			_SearchInputClose.style.width = _suggestElement.offsetWidth-2;
			_SearchInputClose.style.display = "block";
		}	
	}
	if(!_isInIE() && typeof(_closeMultiSelect)=="function"){//��IE,�رն�ѡ
		_closeMultiSelect();
	}
}

//URL������������б�
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
   
//�ر������б�
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
	
	if(!_isInIE()){//��IE
		var _SearchInputClose = document.getElementById("_SearchInputClose"); 
		if(_SearchInputClose){
			_SearchInputClose.style.display = "none";
		}		
	}
	
}
//����Ƶ�����
function _suggestOver(item, index2) {
    item.className = 'suggest_link_over';
    _index2 = index2;
}

//����Ƴ�
function _suggestOut(item) {
	item.className = 'suggest_link';
}
//ѡ�к�������ʽ
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

//���ѡ��һ��
function _clickSearch(item) {
	_noAction = true;
	_inputElement.value = item.firstChild.nodeValue;
	_noAction = false;
	if (_hiddenInput != null) {
		_changeValue(_hiddenInput, item.getAttribute("code"));
	}
	_closeSuggest();
}
//�뿪����
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
//��Ϊ�ǿ������ƥ�䣬���Դ������⣬������������ݺ�̨���Ҳ���
//������Ҫ�����жϣ���ֹ�������ݲ�׼ȷ����������
//������벻׼ȷ��hiddenInputId��Ϊ�գ���textInputId��Ϊ��
//����������ݺ�̨û�У��ͱ�������������ݣ���˲�����Ҫ�ж�
//�ж���ȷ��Ҫ���ڷǿյ�ǰ�棬������ʾ��׼ȷ
function checkValidity(textInputId, hiddenInputId, name) { 
	if(ID(hiddenInputId).value==''&&ID(textInputId).value!=''){
		alert("����ȷ��д"+name+"��");
		return false;
	}
	return true;
}
//��⵱ǰ������Ƿ�ΪIE,
//true-IE,false-����IE
//���ö������뺯���ķ�ʽ������һ���ж�(��Ƶ���ظ��ĵ����мӿ�ִ���ٶ�) 
function _isInIE(){
	var isInIe = !!(document.all && navigator.userAgent.indexOf('Opera') === -1);
	_isInIE = isInIe?function(){return true;}:function(){return false;};
	return isInIe;
}

//�ж��ַ����Ƿ�Ϊ��ֵ
function isNull(str) { 
  if (removeSpace(str)==""){
    return true;
  }
  return false;
}

//��ȥ�ַ���ǰ��Ŀո�
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
//����id������Ӧ�Ķ������Բ���ֵ����Ϣ
function ID(id) {
	return document.getElementById(id);
}

//��ʹ�õ�Ajax��Js����
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
  
    //�������󳣼����������裺
    //1.�ӱ��л�ȡ��Ҫ������
    //2.�����ݽ��й���(��ѡ)
    //3.����Ҫ���ӵ�URL
    //4.�򿪵�������������
    //5.���÷���������ɺ�Ҫ���еĺ������ص�������
    //6.��������
	 
	//--------------------------------------
	 
	//���־���״̬��	
	//0������û�з������ڵ��� open() ֮ǰ���� 
	//1�������Ѿ���������û�з��������� send() ֮ǰ���� 
	//2�������Ѿ��������ڴ���֮�У�����ͨ�����Դ���Ӧ�õ�����ͷ������ 
	//3�������Ѿ�������Ӧ��ͨ���в������ݿ��ã����Ƿ�������û�������Ӧ�� 
	//4����Ӧ����ɣ����Է��ʷ�������Ӧ��ʹ������
	
	//--------------------------------------
	
	//HTTP״̬�룺
	//200:��ʾ��Ӧһ��˳��
	//401��δ����Ȩ 
    //403����ֹ 
    //404��û�ҵ�
    //405 ��ʾ������ʹ�����緢�� HEAD ����֮�಻�ɽ��ܵ����󷽷�
    //407 ���ʾ��Ҫ���д�����֤
    //301�������ƶ� 
	//302���ҵ����������¶�������һ�� URL/URI �ϣ� 
	//305��ʹ�ô����������ʹ��һ���������������������Դ��
    //.....
    
	//----------------------------------------
	
	//XMLHttpRequest.readyState    HTTP����״̬
	//XMLHttpRequest.status        HTTP״̬��
	//XMLHttpRequest.statusText    HTTP״̬����
	//XMLHttpRequest.responseText  ����������Ӧ�ı�
	//XMLHttpRequest.responseXML   ����������Ӧ�ı�
	
	//----------------------------------------
	
	//XMLHttpRequest.getAllResponseHeaders()  ��ȡ��Ӧͷ������
	//XMLHttpRequest.getResponseHeader("Content-Length")  ��ȡ��Ӧ�ĳ���
	//XMLHttpRequest.getResponseHeader("Content-Type")  ��ȡ��Ӧ����������
	
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
