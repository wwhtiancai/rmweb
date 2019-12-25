/**
 * zhoujn 根据单个tagname获取获取内部值
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
 * zhoujn  获取obj的value
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
 * zhoujn 将text框置为readOnly
 * @param field
 * @param value
 * @return
 */
function settextreadonly(obj,bool){
	if(obj.readOnly!=undefined){
		if(bool){
			obj.readOnly=true;
			//修改背景色
			obj.style.backgroundColor ='#D9D9D9';
		}else{
			obj.readOnly=false;
			//修改背景色
			obj.style.backgroundColor ='#FFFFFF';
		}	
	}	
}

/**
 * zhoujn 将text框置为提示
 * @param field
 * @param value
 * @return
 */
function settextfocus(obj,bool){
	if(bool){
		//修改背景色
		obj.style.backgroundColor ='#FF0000';
	}else{
		//修改背景色
		obj.style.backgroundColor ='#FFFFFF';
	}	
}



// -------------------------------
// 功能介绍：依据select域，根据其值获取caption
// 参数说明：field select域;value 值
// 返 回：caption
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
// 功能介绍：依据select域，根据其值获取多个caption，
// 参数说明：field select域;value 值；length 代码长度
// 返 回：caption
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
//根据value取caption
function getcomboxcaption(field,value){
  var sCaption = "--";
  field.value = value;
  if(field.options.selectedIndex>=0){
    sCaption = field.options[field.options.selectedIndex].innerText;
  }
  return sCaption;
}
//将radio改为enabled
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
 * 置用户类型的
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
// 功能介绍：清除复选框组选择
// 参数说明：obj 复选框;
// 返 回：
// 用于角色特殊授权 value是A分割
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

//将于检测 idxvalue是否在value（A分割数组里面）
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
// 功能介绍：清除复选框组选择
// 参数说明：obj 复选框;
// 返 回：
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
//功能介绍：将全部复选框组改为 enabled
//参数说明：obj 复选框;
//返 回：
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
//功能介绍：将全部复选框组改为 disabled
//参数说明：obj 复选框;
//返 回：
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


//功能说明
//根据value置checkbox的 disabled,enabled
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
// 功能介绍：清除复选框组选择
// 参数说明：obj 复选框;
// 返 回：
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

//获取check的位值
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
// 功能介绍：获取复选框组选择的值
// 参数说明：obj 复选框;split 连接字符；
// 返 回：复选框选择的连接字符串值
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
//获取checkbox所有值，包括disabled选择的
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
// 功能介绍：获取复选框选择的个数
// 参数说明：obj 复选框
// 返 回：选择个数
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
// 功能介绍：取单选框的内容.
// 参数说明：单选框的数组名
// 返 回：单选框的值
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
// 功能介绍：根据值选中单选框
// 参数说明：obj 单选框;value 选中的单选框的值
// 返 回：无
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
//  功能介绍：删除select控件所有的选择项目
//  参数说明：field select控件
//  返回值  ：无返回值
//-------------------------------
function clearFieldOptions(field){
  while(field.options.length>0){
    field.options[0] = null;
  }
}
//-------------------------------
//  功能介绍：改变combox的值，如果不存在，可以产生新的值
//  参数说明：field select控件
//  返回值  ：无返回值
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
//  功能介绍：获取select控件的Caption的值
//  参数说明：field select控件
//  返回值  ：无返回值
//-------------------------------
function getFieldOptionsCaption(field){
  var result = "";
  if(field.options.selectedIndex>=0){
  	result = field.options[field.options.selectedIndex].innerHTML;
  }
  return result;
}
//-------------------------------
//  功能介绍：增加select控件列表项目
//  参数说明：field select控件;value 列表项目值;caption 列表项目显示标题
//  返回值  ：无返回值
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
//  功能介绍：删除包含输入值的列表项
//  参数说明：obj 列表框;strvalue 输入值
//  返回值  ：无返回值
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
//  功能介绍：保留包含输入值的列表项
//  参数说明：obj 列表框;strvalue 输入值
//  返回值  ：无返回值
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
//  功能介绍：给表单所有域赋值
//  参数说明：form 表单;value 输入值
//  返回值  ：无返回值
//-------------------------------
function setformtextvalue(form,value){
  for(i=0;i<form.elements.length;i++)
    if(form.elements[i].type=="text"||form.elements[i].type=="select-one"||form.elements[i].type=="hidden")
    	form.elements[i].value = value;
}


//获取selectdindex的innertText
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
				// 按下鼠标左键时的事件。
				evt=window.event||evt; 
				//获取当前事件对象。
				_IsMousedown=true;
				// 记录已经准备开始移动了。
				_ClickLeft=evt.clientX-parseInt(move.returnStyle(movediv,'left'));
				//记录当前坐标轴
				_ClickTop=evt.clientY-parseInt(move.returnStyle(movediv,'top'));
			}
			function doMove(evt) {
				//鼠标开始移动时的事件。
				evt=window.event||evt;
				// 获取当前事件对象。
				if(!_IsMousedown)return false;
				// 如果_IsMousedown不等于真了返回。
				movediv.style.left=evt.clientX-_ClickLeft+"px";
				// 把鼠标当前移动的位置赋值给
				movediv.style.top=evt.clientY-_ClickTop+"px";
				// 当前位置减去开始位置就是层当前存放的位置。 
			} 
			function endMove() { 
				//释放鼠标左键时的事件。
				if(_IsMousedown) {
					//如果_IsMousedown还为真那么就赋值为假。
					if(this.isIE){
    					movediv.releaseCapture();
					}	
					//该函数从当前的窗口释放鼠标捕获，并恢复通常的鼠标输入处理。
					_IsMousedown=false;
				}
			}
			movediv.onmousedown=startMove;
			// 鼠标按下事件。
			document.onmousemove=doMove;
			// 鼠标移动事件。
			document.onmouseup=endMove;
			// 鼠标释放事件。
			movediv.onselectstart=movediv.oncontextmenu=function () {
				return false;
			};
			// 禁止选择和右键菜单。 
		},isIE:(navigator.appName=="Microsoft Internet Explorer") ,returnStyle:function (obj,styleName) { 
    		var myObj=typeof obj=="string"?document.getElementById(obj):obj;
			if(document.all) {
				return eval("myObj.currentStyle."+styleName);
			}else{
				return eval("document.defaultView.getComputedStyle(myObj,null)."+ styleName);
			}
		}
	};