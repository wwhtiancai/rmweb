/**
 * 公共的打开窗口选择 
 * @return
 */
/**显示到中队**/
function fnOpenZDGlbm(glbmval,glbmobj,bmmcobj){
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("department.frm?method=choosedept&minbmjb=5&flag=2&glbm="+glbmval, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changebmdm(vReturnValue,glbmobj,bmmcobj);
	}
}  

/**显示到大队**/
function fnOpenGlbm(glbmval,glbmobj,bmmcobj){
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("department.frm?method=choosedept&minbmjb=4&flag=2&glbm="+glbmval, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changebmdm(vReturnValue,glbmobj,bmmcobj);
	}
}  

//将部门代码$部门名称
function changebmdm(vReturnValue,glbmobj,bmmcobj){
	var idx=vReturnValue.indexOf("$");
	glbmobj.value=vReturnValue.substring(0,idx);
	bmmcobj.value=vReturnValue.substring(idx+1);
}	





function fnOpenXzqh(glbmval,xzqhval,xzqhid,xxdzid){
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("basajax.bas?method=choosexzqh&glbm="+glbmval+"&xzqh="+xzqhval, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		var idx=vReturnValue.indexOf("$");
		var xzqhval=vReturnValue.substring(0,idx);
		document.getElementById(xzqhid).value=xzqhval;
		var spath="basajax.bas?method=queryXzqhMc&xzqh="+xzqhval;
		send_request(spath,"fillxxdz('"+xxdzid+"')",false);	
	}
} 

function fillxxdz(xxdzid){
	var results = _xmlHttpRequestObj.responseText;
	document.getElementById(xxdzid).value=results;
}


//从民警和协警表获取警号，姓名，将用户代号用警号填充
function fnOpenJybh(glbmval,xmval,xmobj,jybhobj){
	var url="sysuser.frm?method=userinfoquery&sfmj=1&glbm="+glbmval+"&xm="+encodeURIComponent(encodeURIComponent(xmval));
	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(vReturnValue!=undefined){
		filljybh(vReturnValue,xmobj,jybhobj);
	}			
}

function filljybh(results,xmobj,jybhobj){
	//警号，姓名，将用户代号用警号填充
	if(parseInt(results)==1){
		displayInfoHtml("[0095040J2]:未找到民警/协警/工作人员信息,请先采集基础信息！");
	}else{
		valarray = results.split("#");
		xmobj.value=valarray[0];
		jybhobj.value=valarray[2];
	}
	return;		
}

function fnOpenDwbh(glbmval,dwbhobj,dwmcobj,jyxmobj,dwxzobj){
	var url="basajax.bas?method=chooseunit&glbm="+glbmval;
	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(vReturnValue!=undefined){
		filldwbh(vReturnValue,dwbhobj,dwmcobj,jyxmobj,dwxzobj);
	}			
}

function filldwbh(results,dwbhobj,dwmcobj,jyxmobj,dwxzobj){
	//警号，姓名，将用户代号用警号填充
	if(parseInt(results)==1){
		displayInfoHtml("[0095040J2]:未找到社区信息！");
	}else{
		valarray = results.split("A");
		dwbhobj.value=valarray[0];
		dwmcobj.value=valarray[1];
		jyxmobj.value=valarray[2];
		dwxzobj.value=valarray[3];
	}
	return;		
}

//多选民警
function fnOpenJy(glbmval,jybhval,xcmjobj,jyxmobj){
	var sFeatures="dialogHeight:450px;dialogWidth:400px;help:no;status:no ";
	var url="basajax.bas?method=choosejy&glbm="+glbmval+"&jybh="+jybhval;
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changejy(vReturnValue,xcmjobj,jyxmobj);
	}
}
//将
function changejy(vReturnValue,xcmjobj,jyxmobj){
	if(vReturnValue.indexOf('$')>0){
	    var idx=vReturnValue.indexOf('$')
		xcmjobj.value=vReturnValue.substring(0,idx);
	    jyxmobj.value=vReturnValue.substring(idx+1);;
	}
}	
//选择社区
function fnOpenDwbh2(glbmval,dwbhobj,dwmcobj){
	var sFeatures="dialogHeight:500px;dialogWidth:800px;help:no;status:no ";
	var url="basajax.bas?method=dwbhquery&glbm="+glbmval;
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		filldwbh2(vReturnValue,dwbhobj,dwmcobj);
	}
}

function filldwbh2(vReturnValue,dwbhobj,dwmcobj){
	if(vReturnValue.indexOf('A')>0){
	    var idx=vReturnValue.indexOf('A')
		dwbhobj.value=vReturnValue.substring(0,idx);
	    dwmcobj.value=vReturnValue.substring(idx+1);;
	}	
}


function fnOpenDwbh3(glbmval,dwbhobj,dwmcobj){
	var sFeatures="dialogHeight:500px;dialogWidth:800px;help:no;status:no ";
	var url="basajax.bas?method=dwbhquery02&glbm="+glbmval;
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		filldwbh2(vReturnValue,dwbhobj,dwmcobj);
	}
}

//选择专项行动台账
function fnOpenAccount(glbmval,tzxhobj,tzmcobj,tbrqobj){
	var url="account.bas?method=chooseaccountitem&glbm="+glbmval;
	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(vReturnValue!=undefined){
		fillaccount(vReturnValue,tzxhobj,tzmcobj,tbrqobj);
	}			
}

function fillaccount(vReturnValue,tzxhobj,tzmcobj,tbrqobj){
	if(vReturnValue.indexOf('A')>0){
		var valarray = vReturnValue.split("A");
		tzxhobj.value=valarray[0];
		tzmcobj.value=valarray[1];
		//数组形式
		var vartbrq=valarray[2].split("#");
		//删除
		while (tbrqobj.childNodes.length > 0) {
			tbrqobj.removeChild(tbrqobj.childNodes[0]);
		}
		//增加空的
		option = document.createElement("option");
		option.innerText = "";
		option.value = "";
		tbrqobj.appendChild(option);
		
		//添加
		for ( var i = 0; i < vartbrq.length; i++) {
			option = document.createElement("option");
			option.innerText = vartbrq[i];
			option.value = vartbrq[i];
			tbrqobj.appendChild(option);
		}
		
		///tbrqobj.value=valarray[2];
	}
}



function fnOpenQybh(glbmval,syxzval,dwmcval,qyForm){
	var url="basajax.bas?method=chooseTranspCorp&glbm="+glbmval+"&dwxz="+syxzval+"&dwmc="+dwmcval;
	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(vReturnValue!=undefined){
		fillQybh(vReturnValue,qyForm);
	}			
}

function fillQybh(results,qyForm){
	//警号，姓名，将用户代号用警号填充
	if(parseInt(results)==1){
		displayInfoHtml("[0095040J2]:未找到客货运企业信息！");
	}else{
		valarray = results.split("A");
		qyForm.qybh.value=valarray[0];
		qyForm.dwmc.value=valarray[1];
		qyForm.dwmc_temp.value=valarray[1];
//		qyForm.jyxm.value=valarray[2];
		qyForm.dwxz.value=valarray[3];
//		qyForm.bmmc.value=valarray[4];
//		qyForm.dwdd.value=valarray[5];
//		qyForm.fr.value=valarray[6];
//		qyForm.frsjhm.value=valarray[7];
//		qyForm.frlxdh.value=valarray[8];
//		qyForm.gllxr.value=valarray[9];
//		qyForm.glsjhm.value=valarray[10];
//		qyForm.gllxdh.value=valarray[11];
		if (qyForm.aqlxr.value==""){
			qyForm.aqlxr.value=valarray[12];
		}
		if(qyForm.aqsjhm.value==""){
			qyForm.aqsjhm.value=valarray[13];
		}
		if(qyForm.aqlxdh.value==""){
			qyForm.aqlxdh.value=valarray[14];
		}
		if (qyForm.name == 'drvresidenceForm'){
			qyForm.assignveh.disabled = false;
		}
	}
	return;		
}

function getRoadItems(glbm,xzqh,field_id,lh){
	var url = 'commonCode.frm?method=queryRoadItem&glbm=' + glbm + '&xzqh=' + xzqh + '&dldm=&dlmc=';
	send_request_tb(url,"procRoadItems('" + field_id +"','" + lh + "')",false);	
}

function procRoadItems(field_id,lh){
	var onechild;
    roadlist = new Array();
   	var xmlDoc = _xmlHttpRequestObj.responseXML;
   	var items = xmlDoc.getElementsByTagName("roaditem");
	for (var i = 0; i < items.length; i++){
		var item = items[i];
		roadlist[i] = new Object();
		onechild = item.getElementsByTagName("dldm")[0].firstChild;
		if(onechild!=null) roadlist[i].dldm = onechild.nodeValue; else roadlist[i].dldm = "";
		onechild = item.getElementsByTagName("dlmc")[0].firstChild;
		if(onechild!=null) roadlist[i].dlmc = onechild.nodeValue;else roadlist[i].dlmc = "";
		onechild = item.getElementsByTagName("dllx")[0].firstChild;
		if(onechild!=null) roadlist[i].dllx = onechild.nodeValue;else roadlist[i].dllx = "";
		onechild = item.getElementsByTagName("glxzdj")[0].firstChild;
		if(onechild!=null) roadlist[i].glxzdj = onechild.nodeValue;else roadlist[i].glxzdj = "";
		onechild = item.getElementsByTagName("dx")[0].firstChild;		
		if(onechild!=null) roadlist[i].dx = onechild.nodeValue;else roadlist[i].dx = "";
		onechild = item.getElementsByTagName("dlxx")[0].firstChild;
		if(onechild!=null) roadlist[i].dlxx = onechild.nodeValue;else roadlist[i].dlxx = "";
		onechild = item.getElementsByTagName("lkldlx")[0].firstChild;
		if(onechild!=null) roadlist[i].lkldlx = onechild.nodeValue;else roadlist[i].lkldlx = "";
		onechild = item.getElementsByTagName("dlwlgl")[0].firstChild;		
		if(onechild!=null) roadlist[i].dlwlgl = onechild.nodeValue;else roadlist[i].dlwlgl = "";
		onechild = item.getElementsByTagName("lmjg")[0].firstChild;		
		if(onechild!=null) roadlist[i].lmjg = onechild.nodeValue;else roadlist[i].lmjg = "";
		onechild = item.getElementsByTagName("fhsslx")[0].firstChild;
		if(onechild!=null) roadlist[i].fhsslx = onechild.nodeValue;else roadlist[i].fhsslx = "";
		onechild = item.getElementsByTagName("qs")[0].firstChild;
		if(onechild!=null) roadlist[i].qs = onechild.nodeValue;else roadlist[i].qs = "";
		onechild = item.getElementsByTagName("js")[0].firstChild;		
		if(onechild!=null) roadlist[i].js = onechild.nodeValue;else roadlist[i].js = "";
		onechild = item.getElementsByTagName("qsmc")[0].firstChild;
		if(onechild!=null) roadlist[i].qsmc = onechild.nodeValue;else roadlist[i].qsmc = "";
		onechild = item.getElementsByTagName("jsmc")[0].firstChild;
		if(onechild!=null) roadlist[i].jsmc = onechild.nodeValue;else roadlist[i].jsmc = "";
	} 
	transRoadListToOptions(document.all[field_id],lh);
}
function transRoadListToOptions(field,lh){
    clearFieldOptions(field);
	for(var i=0;i<roadlist.length;i++){
		addcomboxitem(field,roadlist[i].dldm,roadlist[i].dldm + ":" + roadlist[i].dlmc);
	}
	field.value = lh;
}
