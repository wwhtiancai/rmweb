//���ݲ���
//����������glbm����������kklx����·����dllx����·����dldm��������Ϣ�����,kkbh,kkmc��
function selectTollgate(hcmc,fid){
	if(typeof fid=='undefined'){
		fid="myform";
	}
	var data = $("#"+fid).serializeArray(); //�Զ���form����װ��json
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

//�����ص���Ϣhtml
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
		html=html+"<ul style='width:100%'><li style='width:99%'>��������:"+$("#kkbhmc").val()+"</li></ul>";	
	}else{
		if($("#glbmmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>������:"+$("#glbmmc").val()+"</li></ul>";
		}
		if($("#xzqhmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>��������:"+$("#xzqhmc").val()+"</li></ul>";
		}
		if($("#kklxmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>��������:"+$("#kklxmc").val()+"</li></ul>";
		}
		if($("#dllxmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>��·����:"+$("#dllxmc").val()+"</li></ul>";
		}
		if($("#dldmmc").val()!=""){
			html=html+"<ul style='width:100%'><li style='width:99%'>��·����:"+$("#dldmmc").val()+"</li></ul>";
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
		alert("��ѯ����" + decodeURIComponent(msg));
	}else if(code == 1){
		$("#kktj").html(decodeURIComponent(html).replace(/\+/g," "));
		var tspan=decodeURIComponent(span).replace(/\+/g," ");
		$("#tspan").html(tspan);
		//$("#kktj").html("<ul style='width:100%'><li style='width:99%'>���ڼ��:<span class='tiper' id='tiper_control'><font color='blue'>1111</font></span><div class='tiper_control' style='display:none'><table border='0' cellspacing='1' cellpadding='0' class='list'><tr class='out' style='cursor:pointer'><td>����ʡ���������θ���������ͨ�����ٿ���</td><td>����ʡ�����о�������������ͨ�����ٿ���</td></tr></table></div></li></ul>");
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