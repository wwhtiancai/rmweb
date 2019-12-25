
//��javascript�ű�����ʵ���Զ���ʾ������ı��ؼ�

//�����Զ���ʾ�ؼ��ĺ���
function AutoSuggestControl(oTextbox, oProvider) {
	this.cur = -1;//��ǰ����ѡ����
	this.layer = null;//�����б��
	this.provider = oProvider;
	this.idValue = -1;//int��ֵ ���ڼ�¼ѡ��ѡ���idֵ(��204 ���й㳡��204)
	this.tableName = "";//�Զ���ʾ��ѯ�ı�����
	this.valueField ="";//��ѯʵʱ��Ҫֵ�ֶ�
	this.textField = "";//�Զ���ʾ��ѯ���ֶ�����
	this.filterCondition = "";//���ù�������
	this.limit = 10;//���Ʒ��صļ�¼����
	this.url="";//��ý����URL
	this.callBackFun="";//ѡ�����֮��ִ�лص���������
	this.textbox = oTextbox;//�����htmlInputElementԪ��
	this.timeoutId = null;//��ʱid����
	this.userText = oTextbox.value;//�û�������ı�ֵ   
	this.aSuggestions=new Array();
	this.autoSuggestions=true;//�Ƿ��Զ���ʾ
	this.init();//��ʼ�������Զ���ʾ�ؼ�    
}

//Ϊ�û�������һ�����߸��ཨ��,��û�н��鱻����,�������Զ������б�
//����1:��ʾ�ӷ��������صĽ����б�ֵ
AutoSuggestControl.prototype.autosuggest = function (aSuggestions, bTypeAhead,bShowSuggestions) {
	this.cur = -1;//Ϊ��ǰ�����б����³�ʼ��ָ��
    //��֤������һ������ѡ��    
	if (aSuggestions.length > 0) {
		if (bTypeAhead) {		
			this.typeAhead(aSuggestions[0][1]);
			//�˴������Զ���ʾʱ����¥��,�����idֵ
			this.idValue=aSuggestions[0][0];
			this.textbox.setAttribute("autoSuggestValue",aSuggestions[0][0]);//��õ�ǰѡ��Idֵ 20141212
		}
		if(bShowSuggestions){
			this.showSuggestions(aSuggestions);
		}
	} else {
		this.hideSuggestions();
	}
};

//���������б��,��ʾ�������
AutoSuggestControl.prototype.createDropDown = function () {
	
	//����һ���㲢��������	   
	this.layer = document.createElement("div");
	this.layer.style.position="absolute";//����layer�Ķ�λ��ʽ(��������)		
	this.layer.className = "suggestions";
	this.layer.style.zIndex="9920";
	this.layer.style.visibility = "hidden";
	this.layer.style.border="solid #817f82 1px";
	this.layer.style.width = this.textbox.offsetWidth;
	document.body.appendChild(this.layer);    
	//border-bottom-color: #817f82;
    //���û�ѡ����ĳ������,����ı����������õ��ı�����ȥ
	var oThis = this;//��ʾ�����Զ���ʾ�ؼ�����
	this.layer.onmousedown = this.layer.onmouseup = this.layer.onmouseover = function (oEvent) {
		oEvent = oEvent || window.event;
		oTarget = oEvent.target || oEvent.srcElement;
		if (oEvent.type == "mousedown") {			
			oThis.textbox.value = oTarget.firstChild.nodeValue;
			oThis.idValue=  oTarget.id;//��õ�ǰ��ѡ���Idֵ
			oThis.textbox.setAttribute("autoSuggestValue",oTarget.id);//20141212�޸�
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

//�����ı������λ�õĵ�����ֵ
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

//����ı��򶥶�λ������ĺ���
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

//�������б��и�����ʾ�ı�������ǰ��ѡ��,�����ı����õ��ı�����ȥ
//������������ʾѡ�����»���ǰһ��ѡ��
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
			this.textbox.setAttribute("autoSuggestValue",oNode.id);//�����Զ�������20141212
		}
	}
};

//����3���������¼� �����������¼��Ķ���
AutoSuggestControl.prototype.handleKeyDown = function (oEvent) {
	switch (oEvent.keyCode) {
	  case 38: //���ϼ�ͷ
		this.goToSuggestion(-1);
		break;
	  case 40: //���¼�ͷ 
		this.goToSuggestion(1);
		break;
	  case 27: //ESC��
		this.textbox.value = this.userText;
		this.selectRange(this.userText.length, 0);
            //����ִ��
	  case 13: //Enter��
		this.hideSuggestions();
		oEvent.returnValue = false;
		if (oEvent.preventDefault) {
			oEvent.preventDefault();
		}
		break;
	}
};


//���Ƽ�̧���¼� ����:��̧���¼��Ķ���
AutoSuggestControl.prototype.handleKeyUp = function (oEvent) {
	var iKeyCode = oEvent.keyCode;
	var oThis = this;
	  
    //��õ�ǰ������ı�ֵ
	this.userText = this.textbox.value;
	clearTimeout(this.timeoutId);    
    //Ϊ�˸����ɾ����,��û����ʾ����ʾ�����б� 
	if (iKeyCode == 8 || iKeyCode == 46) {
     	//����ı����е�ֵΪ��ʱ���ز�
		if (oThis.textbox.value == "") {
			oThis.layer.innetHTML = "";
			oThis.hideSuggestions();
			oThis.idValue=null;
			oThis.textbox.setAttribute("autoSuggestValue",null);//20141212 �޸�
		} else {
			this.timeoutId = setTimeout(function () {
				oThis.provider.requestSuggestions(oThis, oThis.autoSuggestions,true);
			}, 500);
		}    
    //ȷ�����ַ���û�нӿ�        
	} else {
		if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode < 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //����ִ��
		} else {			
    		//�ӷ����ṩ�߻�ý����б�ֵ
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
//���ؽ����б�
AutoSuggestControl.prototype.hideSuggestions = function () {
	this.layer.style.visibility = "hidden";
};

//���б��еĽڵ������ʾ
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

//���Զ����鹦�ܵ��¼���������ʼ���ı���
AutoSuggestControl.prototype.init = function () {
    //����һ��this���������
	var oThis = this;

    //����onkeyup�¼�    
	this.textbox.onkeyup = function (oEvent) {
        //����¼���������λ��        
		if (!oEvent) {
			oEvent = window.event;
		}    
        //Ϊ�¼������������handleKeyUp()����        
		oThis.handleKeyUp(oEvent);
	};
    //����onkeydown�¼�
	this.textbox.onkeydown = function (oEvent) {
				
		//����¼���������λ��	
		if (!oEvent) {
			oEvent = window.event;
		} 
           
        //Ϊ�¼������������handleKeyDown()����         
		oThis.handleKeyDown(oEvent);
	};
    
    //����onblur�¼�(���ؽ����б�)       
	this.textbox.onblur = function () {
		oThis.hideSuggestions();
		//���û��ѡ����ն�������
		if(oThis.textbox.autoSuggestValue==null){
			oThis.textbox.value="";
		}
		if(oThis.callBackFun!=""){
			//ִ�лص�����
			eval(oThis.callBackFun+"()");
		}		
		//oThis.onChange(oThis.textbox.value, oThis.idValue);
	};
	this.textbox.onmouseup = function (oEvent) {
		//alert(oThis.aSuggestions.length);
		oThis.showSuggestions(oThis.aSuggestions);
	}
    //�������������б�    
	this.createDropDown();
};

//���ı���ѡ�����ֵķ�Χ
AutoSuggestControl.prototype.selectRange = function (iStart, iEnd) {
    //ΪIEʹ���ı��ķ�Χ   
	if (this.textbox.createTextRange) {
		var oRange = this.textbox.createTextRange();
		oRange.moveStart("character", iStart);
		oRange.moveEnd("character", iEnd - this.textbox.value.length);
		oRange.select();
    
    //ʹ�ú��� setSelectionRange() ΪMozillaѡ��Χ   
	} else {
		if (this.textbox.setSelectionRange) {
			this.textbox.setSelectionRange(iStart, iEnd);
		}
	}     
	//���ı����ý���
	this.textbox.focus();
};
AutoSuggestControl.prototype.getValue=function(){
	if(this.textbox.value==""){
		return null;
	}else{
		return this.idValue;
	}
};
//���������б�������,�ƶ���ǡ����λ��,����ʾ�ò�
//����:�����б�ֵ����
AutoSuggestControl.prototype.showSuggestions = function (aSuggestions) {
	var oDiv = null;
	this.layer.innerHTML = "";  //������е�����
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
        //��idֵ��ŵ�ÿ��div����id������ȥ
		oDiv.id = aSuggestions[i][0];
       	//���ı����뵽�ڵ���innerText��       	
		oDiv.appendChild(document.createTextNode(aSuggestions[i][1]));
		this.layer.appendChild(oDiv);
	}			
	this.layer.style.left = this.getLeft()+ "px";		
	this.layer.style.top = (this.getTop() + this.textbox.offsetHeight) + "px";
	this.layer.style.visibility = "visible";
};

//���ı����в���һ������ֵ,������ʾ���鲿�ֵ�ֵ
//����:�ı�����ֵ
AutoSuggestControl.prototype.typeAhead = function (sSuggestion) {
    //����Ƿ�֧������ǰ��ʾ����
	if (this.textbox.createTextRange || this.textbox.setSelectionRange) {
		var iLen = this.textbox.value.length;
        //��������û�м�¼�����
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
 * ѡ���¼�
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
//=======ͨ��dwr���ʵ�ְѷ������˵����ݴ��䵽�ͻ���========
//Ϊ�ͻ����ṩ�����б�Ķ���
function SuggestionProvider() {
}


//�Զ���ʵ�����һ������:���Զ���ʾ�ؼ����ؽ����б�
SuggestionProvider.prototype.requestSuggestions = function (oAutoSuggestControl, bTypeAhead,bShowSuggestions) {
   //=========ͨ��dwr���÷������˵ĺ����ĵ���Ѱ�Ľ��============
	var keywords = oAutoSuggestControl.textbox.value;
   //���������ʽ���ز�ѯ�Ľ����������صĺ������ز�ѯ�Ľ���� 
	if (keywords != "") {
		this.initSuggestions(oAutoSuggestControl, bTypeAhead, bShowSuggestions);
	} else {
		oAutoSuggestControl.idValue=null;
		oAutoSuggestControl.textbox.setAttribute("autoSuggestValue",null);//�Զ�������
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
