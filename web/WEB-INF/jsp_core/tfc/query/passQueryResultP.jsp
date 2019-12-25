<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>����ͨ����ϸ��Ϣ</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var curPage = -1;
var curIdx = -1;
var curPass;

var passPicWid = null;

window.onunload = function(){
	if(passPicWid != null){
		passPicWid.close();
	}
}

$(document).ready(function(){
	code = <c:out value='${code}'/>;
	if(code == 1){
		curPage = <c:out value='${page}'/>;
		curIdx = <c:out value='${idx}'/>;
		getPass(curIdx);
	}
});
function getPass(idx){
	if(curPage == -1){
		return;
	}
	closes();
	$("#veh").css("display","none");
	curUrl = "<c:url value='tfcQuery.tfc?method=doPassDetail&page='/>" + curPage + "&idx=" + idx;
	$.getJSON(curUrl,function(data){
			opens();
			code = data['code'];
			if(code == 1){
				msg = data['msg'];
				curPass = msg;
				$("#hpzlTD").html(decodeURIComponent(msg['hpzlmc']));
				$("#hphmTD").html(decodeURIComponent(msg['hphm']));
				$("#hpysTD").html(decodeURIComponent(msg['hpysmc']));
				
				$("#hpzl").val(msg['hpzl']);
				$("#hpys").val(msg['hpys']);
				
				$("#gcsjTD").html(msg['gcsj'].substring(0,19));
				if(msg['clsd'] > 0){
					$("#clsdTD").html(msg['clsd'] + '&nbsp;km/h');
				}else{
					if(msg['clsd'] == -1){
						$("#clsdTD").html('--');
					}else{
						$("#clsdTD").html(msg['clsd']);
					}
				}
				if(msg['cwkc'] > 0){
					$("#cwkcTD").html(msg['cwkc'] + '&nbsp;cm');
				}else{
					/**if(msg['cwkc'] == -1){
						$("#cwkcTD").html('--');
					}else{
						$("#cwkcTD").html(msg['cwkc']);
					}*/
					$("#cwkcTD").html('--');
				}
				$("#cllxTD").html(decodeURIComponent(msg['cllxmc']) + '/' + decodeURIComponent(msg['clpp']));
				$("#csysTD").html(decodeURIComponent(msg['csysmc']));
				
				$("#cllx").val(msg['cllx']);
				$("#csys").val(msg['csys']);
				$("#clpp").val(msg['clpp']);
				
				$("#xzqhTD").html(decodeURIComponent(msg['xzqhmc']));
				$("#dldmmcTD").html(decodeURIComponent(msg['dldmmc']));
				$("#kkjcTD").html(decodeURIComponent(msg['kkbhmc']));
				$("#fxlxmcTD").html(decodeURIComponent(msg['fxlxmc']));
				$("#cdhTD").html("�� "+msg['cdh']+" ����");
				$("#kkbh").val(msg['kkbh']);
				
				$("#fzhpzlTD").html(decodeURIComponent(msg['fzhpzlmc']));
				$("#fzhphmTD").html(decodeURIComponent(msg['fzhphm']));
				$("#fzhpysTD").html(decodeURIComponent(msg['fzhpysmc']));
				
				$("#rksjTD").html(decodeURIComponent(msg['rksj']).substring(0,19));
				
				curIdx = idx;
			}else{
				alert(decodeURIComponent(data['msg']));
			}
		}		
	);
}

function doChecking(){
	//object:DEV_TOLLGATE
	//regulation:kkbh(�ǿ�,�ȳ�(12))
	return true;
}

function getNext(p){
	getPass(curIdx + p);
}
function doKeyEvent(){
	keyCode = event.keyCode;
	if($("#btnGetNext1").attr("disabled") == false && $("#btnGetNext2").attr("disabled") == false){
		if(keyCode == 37){
			//left
			getNext(-1);
		}else if(keyCode == 39){
			//right
			getNext(1);
		}
	}
	return true;
}

function getVehicle(){
	chpzl = decodeURIComponent(curPass['hpzl']);
	chphm = decodeURIComponent(curPass['hphm']);
	if(chpzl.length != 2 && chphm.length != 7){
		alert("���ƴ���");
		return;
	}
	if($("#veh").css("display") == 'none'){
		$("#veh").slideDown();
		$("#loadingVeh").css("display", "block");
		$("#loadingVeh").html('<img alt="" src="images/running.gif"><br>���ڲ�ѯ�����Ժ�...');
		$("#vehDataTable").css("display", "none");
		curUrl = "<c:url value='tfcQuery.tfc?method=getVehicle&hpzl='/>" + chpzl + "&hphm="+encodeURI(curPass['hphm']);
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:true,
			dataType:"json",
			success: function(data){
				if(data['code'] == 1){
					$("#loadingVeh").css("display", "none");
					$("#vehDataTable").css("display", "block");
					msg = data['msg'];
					$("#vehClppTD").html(decodeURI(msg['clpp']));
					$("#vehCsysTD").html(decodeURI(msg['csysmc']));
					$("#vehCllxTD").html(decodeURI(msg['cllxmc']));
					$("#vehClztTD").html(decodeURI(msg['clztmc']));
					$("#vehCcdjrqTD").html(decodeURI(msg['ccdjrq']));
					$("#vehSyrTD").html(decodeURI(msg['syr']));
					$("#vehSyxzTD").html(decodeURI(msg['syxzmc']));
					$("#vehClxhTD").html(decodeURI(msg['clxh']));
				}else{
					$("#loadingVeh").css("display", "none");
					$("#vehDataTable").css("display", "block");
					$("#vehClppTD").html();
					$("#vehCsysTD").html();
					$("#vehCllxTD").html();
					$("#vehClztTD").html();
					$("#vehCcdjrqTD").html();
					$("#vehSyrTD").html();
					$("#vehSyxzTD").html();
					$("#vehClxhTD").html();
					$("#loadingVeh").css("display", "block");
					$("#vehDataTable").css("display", "none");
					$("#loadingVeh").html(decodeURI(data['msg']));
				}
			}
		});
	}else{
		$("#veh").slideUp();
	}
}


function getSuspinfo() {
	if(curPass['hpzl'].length<1||curPass['hphm'].length<1){
		alert("��ͨ�����ݵĺ�����Ϣ��������");
		return false;
	}
	var curUrl = "<c:url value='suspQuery.vmc?method=getSuspList&hpzl='/>"+curPass['hpzl']+"&hphm="+encodeURI(curPass['hphm']);
	openwin800(curUrl,"getSuspList","");

}

</script>
</head>
<body onkeyup="doKeyEvent()">
<div id="panel" style="display:none">
	<div id="paneltitle">����ͨ����ϸ��Ϣ</div>
	<div id="block">
		<div id="blocktitle">������Ϣ</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="25%">
			<col width="10%">
			<col width="25%">
			<%--
				�������,���ڱ��,��������,������,����ʱ��,��������,���ƺ���,������ɫ,��������,�����ٶ�
				������ɫ,����Ʒ��,��������,������������,�������ƺ���,����������ɫ,ͼƬ·��,ͼƬ1�ļ���,
				ͼƬ2�ļ���,ͼƬ3�ļ���,���ʱ��,�����ֶ�			
			 --%>
			<tr>
				<td class="head"><input type="hidden" id="hpzl" name="hpzl">��������</td>
				<td class="body" id="hpzlTD"></td>
				<td class="head">���ƺ���</td>
				<td class="body" id="hphmTD"></td>
				<td class="head" rowspan="3">����ͼƬ</td>
				<td class="body" rowspan="3">				
				</td>
			</tr>
			<tr>
				<td class="head"><input type="hidden" id="hpys" name="hpys">������ɫ</td>
				<td class="body" id="hpysTD"></td>
				<td class="head"><input type="hidden" id="csys" name="csys">������ɫ</td>
				<td class="body" id="csysTD"></td>
			</tr>
			<tr>
				<td class="head">����ʱ��</td>
				<td class="body" id="gcsjTD"></td>
				<td class="head">�����ٶ�</td>
				<td class="body" id="clsdTD"></td>
			</tr>
			<tr>
				<td class="head">������������</td>
				<td class="body" id="fzhpzlTD"></td>
				<td class="head">�������ƺ���</td>
				<td class="body" id="fzhphmTD"></td>
				<td class="head">����������ɫ</td>
				<td class="body" id="fzhpysTD"></td>
			</tr>
			<tr>
				<td class="head"><input type="hidden" id="cllx" name="cllx"><input type="hidden" id="clpp" name="clpp">��������/Ʒ��</td>
				<td class="body" id="cllxTD"></td>
				<td class="head">��������</td>
				<td class="body" id="cwkcTD"></td>
				<td class="head">���ʱ��</td>
				<td class="body" id="rksjTD"></td>
			</tr>
		</table>
	</div>
	<div id="block">
		<div id="blocktitle">������Ϣ</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="25%">
			<col width="10%">
			<col width="25%">
			<%--
			�������,���ڱ��,��������,������,����ʱ��,��������,���ƺ���,������ɫ,��������,�����ٶ�
			������ɫ,����Ʒ��,��������,������������,�������ƺ���,����������ɫ,ͼƬ·��,ͼƬ1�ļ���,
			ͼƬ2�ļ���,ͼƬ3�ļ���,���ʱ��,�����ֶ�			
			 --%>
			<tr>
				<td class="head">��������</td>
				<td class="body" id="xzqhTD"></td>
				<td class="head">��·����</td>
				<td class="body" id="dldmmcTD"></td>
				<td class="head">���ڼ��</td>
				<td class="body" id="kkjcTD"></td>
			</tr>
			<tr>
				<td class="head">��ʻ����</td>
				<td class="body" id="fxlxmcTD"></td>
				<td class="head">������</td>
				<td class="body" id="cdhTD"></td>
				<td class="head"></td>
				<td class="body"><input type="hidden" id="kkbh" name="kkbh"></td>
			</tr>
		</table>
	</div>
	<div id="veh" style="display:none;">
		<div id="block">
			<div id="blocktitle">�������Ǽ���Ϣ</div>
			<div id="blockmargin">8</div>
			<div id="loadingVeh" style="width:100%;text-align:center;font-size:12px;">
				<img alt="" src="images/running.gif"><br>���ڲ�ѯ�����Ժ�...
			</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail" id="vehDataTable">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<tr>
					<td class="head">��������</td>
					<td class="body" id="vehCllxTD"></td>
					<td class="head">����Ʒ��</td>
					<td class="body" id="vehClppTD"></td>
					<td class="head">�����ͺ�</td>
					<td class="body" id="vehClxhTD"></td>
				</tr>
				<tr>
					<td class="head">����״̬</td>
					<td class="body" id="vehClztTD"></td>
					<td class="head">ʹ������</td>
					<td class="body" id="vehSyxzTD"></td>
					<td class="head">������ɫ</td>
					<td class="body" id="vehCsysTD"></td>
				</tr>
				<tr>
					<td class="head">���εǼ�����</td>
					<td class="body" id="vehCcdjrqTD"></td>
					<td class="head">������</td>
					<td class="body" id="vehSyrTD"></td>
					<td class="head"></td>
					<td class="body"></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="susp" style="display:none;">
		<div id="block">
			<div id="blocktitle">������Ϣ</div>
			<div id="blockmargin">8</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<tr>
					<td class="head">��������</td>
					<td class="body"></td>
					<td class="head">����Ʒ��</td>
					<td class="body"></td>
					<td class="head">�����ͺ�</td>
					<td class="body"></td>
				</tr>
				<tr>
					<td class="head">����״̬</td>
					<td class="body"></td>
					<td class="head">ʹ������</td>
					<td class="body"></td>
					<td class="head">������ɫ</td>
					<td class="body"></td>
				</tr>
				<tr>
					<td class="head">���εǼ�����</td>
					<td class="body"></td>
					<td class="head">������</td>
					<td class="body"></td>
					<td class="head"></td>
					<td class="body"></td>
				</tr>
			</table>
		</div>
	</div>
	
	<table border="0" cellspacing="1" cellpadding="0" class="detail">
		<tr>
			<td class="command">
				<%--input type="button" class="" onclick="getSuspinfo()" value="������Ϣ"> --%>
				<input type="button" class="button_vehicle" onclick="getVehicle()" value="������Ϣ">
				<input type="button" id="btnGetNext1" class="button_left" onclick="getNext(-1)" value="��һ��">
				<input type="button" id="btnGetNext2" class="button_right" onclick="getNext(1)" value="��һ��">
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
			</td>
		</tr>
	</table>
</div>
</body>
</html>