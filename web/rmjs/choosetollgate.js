//传递参数
//包括管理部门glbm、卡口类型kklx、道路类型dllx、道路代码dldm、卡口信息（多个,kkbh,kkmc）
function selectTollgate(hcmc,fid){
	if(typeof fid=='undefined'){
		fid="myform";
	}
	var data = $("#"+fid).serializeArray(); //自动将form表单封装成json
	//openwin("component.dev?method=getTollgateDialogQuery","alarm");
	var retdata = window.showModalDialog("component.dev?method=getTollgateDialogQuery", 
			data, "dialogWidth:800px;dialogHeight:680px;center:1;help:0;resizable:0;status:0;scroll:no;");
	if (typeof retdata != "undefined"){
		var hashTable = new JSMap(); 
		for(var i=0;i<retdata.length;i++){
			hashTable.put(retdata[i].name,retdata[i].value);
		}
		setHashTableHtml(hashTable);
		
		var kkbh=hashTable.get("kkbh");
		var tmpUrl = "/rmweb/cookie.tfc?method=saveTollgateConfig&hcmc="+hcmc+"&&kkbhs="+kkbh;
		$("#"+fid).ajaxSubmit({
			url:tmpUrl,
			dataType:"json",
			async:false,
			contentType:"application/x-www-form-urlencoded;charset=utf-8"
		});
	}else{
		//$("#kkbhs").val("");
	}
	returnCookie(hcmc, fid);
}

//将返回的信息html
function setHashTableHtml(hashTable){
	$("#glbm").val(hashTable.get("glbm"));
	$("#xzqh").val(hashTable.get("xzqh"));
	$("#kklx").val(hashTable.get("kklx"));
	$("#dllx").val(hashTable.get("dllx"));
	$("#dldm").val(hashTable.get("dldm"));
	$("#kkbh").val(hashTable.get("kkbh"));
	
	$("#glbmmc").val(hashTable.get("glbmmc"));
	$("#xzqhmc").val(hashTable.get("xzqhmc"));
	$("#kklxmc").val(hashTable.get("kklxmc"));
	$("#dllxmc").val(hashTable.get("dllxmc"));
	$("#dldmmc").val(hashTable.get("dldmmc"));
	$("#kkbhmc").val(hashTable.get("kkbhmc"));
	
	var html="";
	if($("#kkbhmc").val()!=""){
		html=html+"<ul style='width:100%'><li style='width:99%'>卡口名称:"+$("#kkbhmc").val()+"</li></ul>";	
	}else{
		if($("#glbmmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>管理部门:"+$("#glbmmc").val()+"</li></ul>";
		}
		if($("#xzqhmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>行政区划:"+$("#xzqhmc").val()+"</li></ul>";
		}
		if($("#kklxmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>卡口类型:"+$("#kklxmc").val()+"</li></ul>";
		}
		if($("#dllxmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>道路类型:"+$("#dllxmc").val()+"</li></ul>";
		}
		if($("#dldmmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>道路名称:"+$("#dldmmc").val()+"</li></ul>";
		}
	}
	
	//$("#kktj").html(html);
}

function returnCookie(hcmc,fid){
	describeCookie(hcmc,fid);
}
function describeCookie(hcmc,fid){
	if(typeof fid=='undefined'){
		fid="myform";
	}
	var cookieUrl = "/rmweb/cookie.tfc?method=getCookie&hcmc="+hcmc;
	$("#"+fid).ajaxSubmit({
		url:cookieUrl,
		dataType:"json",
		async:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			retrunCookie(data);
		}
	});
}


function retrunCookie(data){
	var code = data['code'];
	var msg = data['msg'];
	var html=data['html'];
	var span=data['span'];
	if(code == -1){
		alert("查询错误：" + decodeURIComponent(msg));
	}else if(code == 1){
		$("#kktj").html(decodeURIComponent(html).replace(/\+/g," "));
		var tspan=decodeURIComponent(span).replace(/\+/g," ");
		$("#tspan").html(tspan);
		//$("#kktj").html("<ul style='width:100%'><li style='width:99%'>卡口简称:<span class='tiper' id='tiper_control'><font color='blue'>1111</font></span><div class='tiper_control' style='display:none'><table border='0' cellspacing='1' cellpadding='0' class='list'><tr class='out' style='cursor:pointer'><td>江苏省无锡市锡澄高速无锡互通进高速卡口</td><td>江苏省无锡市京沪高速无锡互通出高速卡口</td></tr></table></div></li></ul>");
		tiper(-40,10);
		$.each(msg, function(i,item){
			$("#glbm").val(item['glbm']);
			$("#kklx").val(item['kklx']);
			$("#dllx").val(item['dllx']);
			$("#dldm").val(item['dldm']);
			$("#kkbh").val(item['kkbhs']);
			
			$("#glbmmc").val(decodeURIComponent(item['glbmmc']));
			$("#kklxmc").val(decodeURIComponent(item['kklxmc']));
			$("#dllxmc").val(decodeURIComponent(item['dllxmc']));
			$("#dldmmc").val(decodeURIComponent(item['dldmmc']));
			$("#kkbhmc").val(decodeURIComponent(item['kkmcs']));
		});
	}
}