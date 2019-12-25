/**
 * zhoujn ���ݵ���tagname��ȡ��ȡ�ڲ�ֵ
 */

function getsingletagval(xmlDoc,tagname){
	var x=xmlDoc.getElementsByTagName(tagname)[0];
	var result='';
	if(x.childNodes[0]!=null){
		result=x.childNodes[0].nodeValue;
	}	
	return result;
}

/**
 * zhoujn  ��ȡobj��value
 * @param i_field
 * @return
 */
function getobjvalue(i_field){
  var i_value=i_field.value;
 	if (i_value==null || i_value ==0){
    	return parseInt(0);
 	}
 	return parseInt(i_value);
}

/**
 * zhoujn ��text����ΪreadOnly
 * @param field
 * @param value
 * @return
 */
function settextreadonly(obj,bool){
	if(obj.readOnly!=undefined){
		if(bool){
			obj.readOnly=true;
			//�޸ı���ɫ
			obj.style.backgroundColor ='#D9D9D9';
		}else{
			obj.readOnly=false;
			//�޸ı���ɫ
			obj.style.backgroundColor ='#FFFFFF';
		}	
	}	
}

/**
 * zhoujn ��text����Ϊ��ʾ
 * @param field
 * @param value
 * @return
 */
function settextfocus(obj,bool){
	if(bool){
		//�޸ı���ɫ
		obj.style.backgroundColor ='#FF0000';
	}else{
		//�޸ı���ɫ
		obj.style.backgroundColor ='#FFFFFF';
	}	
}



// -------------------------------
// ���ܽ��ܣ�����select�򣬸�����ֵ��ȡcaption
// ����˵����field select��;value ֵ
// �� �أ�caption
// -------
function getcomboxcaption1(field,value){
  var sCaption = "--";
  var sTmp = field.value;
  field.value = value;
  if(field.options.selectedIndex>=0){
    sCaption = field.options[field.options.selectedIndex].innerText;
    iPos = sCaption.indexOf(":");
    sCaption = sCaption.substr(iPos + 1,sCaption.length);
  }
  field.value = sTmp;
  return sCaption;
}
// -------------------------------
// ���ܽ��ܣ�����select�򣬸�����ֵ��ȡ���caption��
// ����˵����field select��;value ֵ��length ���볤��
// �� �أ�caption
// -------
function getcomboxmulcaption1(field,value,len){
  var sCaption = "--";
  var icount = value.length/len;
  var i;
  var stmpvalue;
  var sTmp = field.value;
  sCaption = "";
  for(i=0;i<icount;i++){
  	stmpvalue = value.substring(i*len,(i+1)*len);
	field.value = stmpvalue;
	if(field.options.selectedIndex>=0){
	  stmpvalue = field.options[field.options.selectedIndex].innerText;
	  iPos = stmpvalue.indexOf(":");
	  sCaption = sCaption + stmpvalue.substr(iPos + 1,stmpvalue.length) + ',';
	}  	
  }
  if(sCaption.length==0){
  	sCaption = "--";
  }else{
  	sCaption = sCaption.substring(0,sCaption.length - 1);
  }
  field.value = sTmp;
  return sCaption;
}
//����valueȡcaption
function getcomboxcaption(field,value){
  var sCaption = "--";
  field.value = value;
  if(field.options.selectedIndex>=0){
    sCaption = field.options[field.options.selectedIndex].innerText;
  }
  return sCaption;
}
//��radio��Ϊenabled
function setradiodiabled(obj){
	if(obj.length!=undefined){
		var length=obj.length
		for(var i=0;i<length;i++){
			obj.item(i).disabled=true;
		}
	}else{
		obj.disabled=true;
	}	
	return true;
}

/**
 * ���û����͵�
 * @param obj
 * @param value
 * @return
 */
function setckyhlx(obj,value){
	if(obj==undefined){
		return true;
	}	
	if(obj.length!=undefined){
		for(var i=0;i<7;i++) {
			if(obj[i].disabled==false){
				if(value.charAt(i)=='1'){
					obj[i].checked=true;
				}else{
					obj[i].checked=false;
				}	
			}else{
				obj[i].checked=false;
			}	
		}
	}else{
		obj.checked=true;
	}
	return true;	
}

// -------------------------------
// ���ܽ��ܣ������ѡ����ѡ��
// ����˵����obj ��ѡ��;
// �� �أ�
// ���ڽ�ɫ������Ȩ value��A�ָ�
// -------
function setckrole(obj,value){
	if(obj==undefined){
		return true;
	}	
	if(obj.length!=undefined){
		for(var i=0;i<obj.length;i++) {
			var temp =obj[i].value;
		 	if(checkinarray(temp,value)){
				obj[i].checked=true;
			}
		}
	}else{
		obj.checked=true;
	}
	return true;
}

//���ڼ�� idxvalue�Ƿ���value��A�ָ��������棩
function checkinarray(idxval,val){
	var result=false;
	var valarray = val.split("A");
	for(i=0;i<valarray.length;i++){
		if(idxval==valarray[i]){
			result=true;
			break;
		}	
	}	
	return result;
}

// -------------------------------
// ���ܽ��ܣ������ѡ����ѡ��
// ����˵����obj ��ѡ��;
// �� �أ�
// -------
function setck(obj,value){
	if(obj==undefined){
		return true;
	}	
	if(obj.length!=undefined){
		for(var i=0;i<obj.length;i++) {
			var temp =obj[i].value;
		 	if(value.indexOf(temp)>-1){
				obj[i].checked=true;
			}
		}
	}else{
		if(obj.value==value){
			obj.checked=true;
		}
	}	
	return true;
}

//-------------------------------
//���ܽ��ܣ���ȫ����ѡ�����Ϊ enabled
//����˵����obj ��ѡ��;
//�� �أ�
//-------
function setckenabled(obj){
	 if(obj!=undefined){
		 for(var i=0;i<obj.length;i++) {
			obj[i].disabled=false;
		 }
	 }
	 return true;	
}

//-------------------------------
//���ܽ��ܣ���ȫ����ѡ�����Ϊ disabled
//����˵����obj ��ѡ��;
//�� �أ�
//-------
function setckdisabled(obj){
	 if(obj!=undefined){
		 if(obj.length!=undefined){
			 for(var i=0;i<obj.length;i++) {
				obj[i].disabled=true;
			 }
		 }else{
			 obj.disabled=true;
		 }	 
	 }
	 return true;	
}


//����˵��
//����value��checkbox�� disabled,enabled
function setckywlxabled(obj,value){
	 if(obj!=undefined){
		 if(value.charAt(0)=='1'){
			 for(var i=0;i<7;i++) {
				 obj[i].disabled=false;
			 }	 
		 }else{
			 for(var i=0;i<7;i++) {
				if(value.charAt(i)=='1'){
					obj[i].disabled=false;
				}else{
					obj[i].disabled=true;
				}	
			}
		 } 	 
	 }
	 return true;		
}


// -------------------------------
// ���ܽ��ܣ������ѡ����ѡ��
// ����˵����obj ��ѡ��;
// �� �أ�
// -------
function clearck(obj){
	 if(obj!=undefined){
		 if(obj.length!=undefined){
			 for(var i=0;i<obj.length;i++) {
				obj[i].disabled=false;
				obj[i].checked=false;
			 }
		 }else{
			 obj.disabled=false;
			 obj.checked=false;
		 }	 
	 }

	return true;
}

function selectck(obj){
	 if(obj!=undefined){
		 if(obj.length!=undefined){
			 for(var i=0;i<obj.length;i++) {
				obj[i].disabled=false;
				obj[i].checked=true;
			 }
		 }else{
			 obj.disabled=false;
			 obj.checked=true;
		 }	 
	 }

	return true;
}

//��ȡcheck��λֵ
function getckbitvalue(obj){
	var value="";
	if(obj!=undefined){
		if(obj.length!=undefined){
			for (var j = 0; j <obj.length ; j++) {
				if(obj[j].checked==true&&obj[j].disabled==false){
					value=value+'1';
				}else{
					value=value+'0';
				}	
			}	
		}	
	}
	value=value+'000';
	return value;	
}

//set



//-------------------------------
// ���ܽ��ܣ���ȡ��ѡ����ѡ���ֵ
// ����˵����obj ��ѡ��;split �����ַ���
// �� �أ���ѡ��ѡ��������ַ���ֵ
// -------------------------------
function getckvalue(obj,split){
	var value="";
	if(obj!=undefined){
		if(obj.length!=undefined){
			for (var j = 0; j <obj.length ; j++) {
				if(obj[j].checked==true&&obj[j].disabled==false){
					value=value+obj[j].value+split;
				}
			}	
			value=value.substring(0,value.length-split.length);
		}else{
			if(obj.checked==true&&obj.disabled==false){
				value=obj.value;
			}
		}	
	}
	return value;
}
//��ȡcheckbox����ֵ������disabledѡ���
function getckallvalue(obj,split){
	var value="";
	if(obj!=undefined){
		if(obj.length!=undefined){
			for (var j = 0; j <obj.length ; j++) {
				if(obj[j].checked==true){
					value=value+obj[j].value+split;
				}
			}	
			value=value.substring(0,value.length-split.length);
		}else{
			if(obj.checked==true){
				value=obj.value;
			}
		}	
	}
	return value;
}



function getnamevalue(obj,split){
	var value="";
	if(obj!=undefined){
		if(obj.length!=undefined){
			for (var j = 0; j <obj.length ; j++) {
				value=value+obj[j].value+split;
			}	
			value=value.substring(0,value.length-1);
		}else{
			value=obj.value;
		}	
	}
	return value;
}
// -------------------------------
// ���ܽ��ܣ���ȡ��ѡ��ѡ��ĸ���
// ����˵����obj ��ѡ��
// �� �أ�ѡ�����
// -------------------------------
function getcknum(obj){
	var num=0;
	for (var j = 0; j <obj.length ; j++) {
		if(obj[j].checked==true&&obj[j].disabled==false){
			num=num+1;
		}
	}
	return num;
}

// -------------------------------
// ���ܽ��ܣ�ȡ��ѡ�������.
// ����˵������ѡ���������
// �� �أ���ѡ���ֵ
// -------------------------------
function getradio(obj){
	var value="";
	if(obj.length!=undefined){
		var length=obj.length
		for(var i=0;i<length;i++){
			if(obj.item(i).checked){
			  value=obj.item(i).value
			}
		}
	}else{
		if(obj.checked){
			value=obj.value;
		}
	}	
	return value;
}

// -------------------------------
// ���ܽ��ܣ�����ֵѡ�е�ѡ��
// ����˵����obj ��ѡ��;value ѡ�еĵ�ѡ���ֵ
// �� �أ���
// -------------------------------
function setradio(obj,value){
	if(obj.length!=undefined){
		for(var i=0;i<obj.length;i++){
			if(obj.item(i).value==value){
				obj.item(i).checked=true;
				break;
			}
		}
	}else{
		obj.checked=true;
	}	
}
//-------------------------------
//  ���ܽ��ܣ�ɾ��select�ؼ����е�ѡ����Ŀ
//  ����˵����field select�ؼ�
//  ����ֵ  ���޷���ֵ
//-------------------------------
function clearFieldOptions(field){
  while(field.options.length>0){
    field.options[0] = null;
  }
}
//-------------------------------
//  ���ܽ��ܣ��ı�combox��ֵ����������ڣ����Բ����µ�ֵ
//  ����˵����field select�ؼ�
//  ����ֵ  ���޷���ֵ
//-------------------------------
function changecomboxvalue(field,value,caption){
	var bExist = false;
    for(i=0;i<field.options.length;i++){
		if(field.options[i].value==value)
			{
				bExist = true;
				break;
			}
		}
	if(!bExist){
		opt = document.createElement( "OPTION" );
		field.options.add(opt);
		opt.innerText = caption;
		opt.value = value;
	}
	field.value = value;
}
//-------------------------------
//  ���ܽ��ܣ���ȡselect�ؼ���Caption��ֵ
//  ����˵����field select�ؼ�
//  ����ֵ  ���޷���ֵ
//-------------------------------
function getFieldOptionsCaption(field){
  var result = "";
  if(field.options.selectedIndex>=0){
  	result = field.options[field.options.selectedIndex].innerHTML;
  }
  return result;
}
//-------------------------------
//  ���ܽ��ܣ�����select�ؼ��б���Ŀ
//  ����˵����field select�ؼ�;value �б���Ŀֵ;caption �б���Ŀ��ʾ����
//  ����ֵ  ���޷���ֵ
//-------------------------------
function addcomboxitem(field,value,caption){
  opt = document.createElement( "OPTION" );
  field.options.add(opt);
  opt.innerText =caption;
  opt.value = value;
}
function fillcomboxitem(content){
	var field=document.all["dldmlb1"];
	clearFieldOptions(field);
	var level1=content.split("#");
	for(i=0;i<level1.length;i++)
	{
		var level2=level1[i].split("^");
		if(level2!=""){
		addcomboxitem(field,level2[0],level2[1]);}
	}
}
//-------------------------------
//  ���ܽ��ܣ�ɾ����������ֵ���б���
//  ����˵����obj �б��;strvalue ����ֵ
//  ����ֵ  ���޷���ֵ
//-------------------------------
function removeSelect(obj,strvalue){
	var i=0;
	for( i=obj.options.length-1;i>=0;i--){
	 if(strvalue.indexOf(obj.options[i].value)>=0){
	   obj.options[i]=null;	
	 }
	}
	return true;
}
//-------------------------------
//  ���ܽ��ܣ�������������ֵ���б���
//  ����˵����obj �б��;strvalue ����ֵ
//  ����ֵ  ���޷���ֵ
//-------------------------------
function reserveSelect(obj,strvalue){
	var i=0;
	for( i=obj.options.length-1;i>=0;i--)
	{
		 if(strvalue.indexOf(obj.options[i].value)>=0)
		 {
		 }
		 else
		 {
		   obj.options[i]=null;
		 }
	}	
	return true;
}
//-------------------------------
//  ���ܽ��ܣ�����������ֵ
//  ����˵����form ��;value ����ֵ
//  ����ֵ  ���޷���ֵ
//-------------------------------
function setformtextvalue(form,value){
  for(i=0;i<form.elements.length;i++)
    if(form.elements[i].type=="text"||form.elements[i].type=="select-one"||form.elements[i].type=="hidden")
    	form.elements[i].value = value;
}


//��ȡselectdindex��innertText
//zhoujn 20100821
function getcommboxtext(obj){
	var selectedIndex=obj.selectedIndex;
	var val=obj.options[selectedIndex].innerText;
	return val;
}

var move={
		moveHandler:function (idname) {
			var movediv=document.getElementById(idname);
			var	_IsMousedown=false,_ClickLeft=0,_ClickTop=0;
			movediv.style.cursor="move";
			function startMove(evt) {
				// ����������ʱ���¼���
				evt=window.event||evt; 
				//��ȡ��ǰ�¼�����
				_IsMousedown=true;
				// ��¼�Ѿ�׼����ʼ�ƶ��ˡ�
				_ClickLeft=evt.clientX-parseInt(move.returnStyle(movediv,'left'));
				//��¼��ǰ������
				_ClickTop=evt.clientY-parseInt(move.returnStyle(movediv,'top'));
			}
			function doMove(evt) {
				//��꿪ʼ�ƶ�ʱ���¼���
				evt=window.event||evt;
				// ��ȡ��ǰ�¼�����
				if(!_IsMousedown)return false;
				// ���_IsMousedown���������˷��ء�
				movediv.style.left=evt.clientX-_ClickLeft+"px";
				// ����굱ǰ�ƶ���λ�ø�ֵ��
				movediv.style.top=evt.clientY-_ClickTop+"px";
				// ��ǰλ�ü�ȥ��ʼλ�þ��ǲ㵱ǰ��ŵ�λ�á� 
			} 
			function endMove() { 
				//�ͷ�������ʱ���¼���
				if(_IsMousedown) {
					//���_IsMousedown��Ϊ����ô�͸�ֵΪ�١�
					if(this.isIE){
    					movediv.releaseCapture();
					}	
					//�ú����ӵ�ǰ�Ĵ����ͷ���겶�񣬲��ָ�ͨ����������봦��
					_IsMousedown=false;
				}
			}
			movediv.onmousedown=startMove;
			// ��갴���¼���
			document.onmousemove=doMove;
			// ����ƶ��¼���
			document.onmouseup=endMove;
			// ����ͷ��¼���
			movediv.onselectstart=movediv.oncontextmenu=function () {
				return false;
			};
			// ��ֹѡ����Ҽ��˵��� 
		},isIE:(navigator.appName=="Microsoft Internet Explorer") ,returnStyle:function (obj,styleName) { 
    		var myObj=typeof obj=="string"?document.getElementById(obj):obj;
			if(document.all) {
				return eval("myObj.currentStyle."+styleName);
			}else{
				return eval("document.defaultView.getComputedStyle(myObj,null)."+ styleName);
			}
		}
	};