<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>车辆通行详细信息</title>
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
				$("#cdhTD").html("第 "+msg['cdh']+" 车道");
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
	//regulation:kkbh(非空,等长(12))
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
		alert("号牌错误！");
		return;
	}
	if($("#veh").css("display") == 'none'){
		$("#veh").slideDown();
		$("#loadingVeh").css("display", "block");
		$("#loadingVeh").html('<img alt="" src="images/running.gif"><br>正在查询，请稍候...');
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
		alert("该通行数据的号牌信息不完整！");
		return false;
	}
	var curUrl = "<c:url value='suspQuery.vmc?method=getSuspList&hpzl='/>"+curPass['hpzl']+"&hphm="+encodeURI(curPass['hphm']);
	openwin800(curUrl,"getSuspList","");

}

</script>
</head>
<body onkeyup="doKeyEvent()">
<div id="panel" style="display:none">
	<div id="paneltitle">车辆通行详细信息</div>
	<div id="block">
		<div id="blocktitle">过车信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="25%">
			<col width="10%">
			<col width="25%">
			<%--
				过车序号,卡口编号,方向类型,车道号,过车时间,号牌种类,号牌号码,号牌颜色,车外廓长,车辆速度
				车身颜色,车辆品牌,车辆类型,辅助号牌种类,辅助号牌号码,辅助号牌颜色,图片路径,图片1文件名,
				图片2文件名,图片3文件名,入库时间,备用字段			
			 --%>
			<tr>
				<td class="head"><input type="hidden" id="hpzl" name="hpzl">号牌种类</td>
				<td class="body" id="hpzlTD"></td>
				<td class="head">号牌号码</td>
				<td class="body" id="hphmTD"></td>
				<td class="head" rowspan="3">特征图片</td>
				<td class="body" rowspan="3">				
				</td>
			</tr>
			<tr>
				<td class="head"><input type="hidden" id="hpys" name="hpys">号牌颜色</td>
				<td class="body" id="hpysTD"></td>
				<td class="head"><input type="hidden" id="csys" name="csys">车身颜色</td>
				<td class="body" id="csysTD"></td>
			</tr>
			<tr>
				<td class="head">过车时间</td>
				<td class="body" id="gcsjTD"></td>
				<td class="head">车辆速度</td>
				<td class="body" id="clsdTD"></td>
			</tr>
			<tr>
				<td class="head">辅助号牌种类</td>
				<td class="body" id="fzhpzlTD"></td>
				<td class="head">辅助号牌号码</td>
				<td class="body" id="fzhphmTD"></td>
				<td class="head">辅助号牌颜色</td>
				<td class="body" id="fzhpysTD"></td>
			</tr>
			<tr>
				<td class="head"><input type="hidden" id="cllx" name="cllx"><input type="hidden" id="clpp" name="clpp">车辆类型/品牌</td>
				<td class="body" id="cllxTD"></td>
				<td class="head">车外廓长</td>
				<td class="body" id="cwkcTD"></td>
				<td class="head">入库时间</td>
				<td class="body" id="rksjTD"></td>
			</tr>
		</table>
	</div>
	<div id="block">
		<div id="blocktitle">卡口信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="25%">
			<col width="10%">
			<col width="25%">
			<%--
			过车序号,卡口编号,方向类型,车道号,过车时间,号牌种类,号牌号码,号牌颜色,车外廓长,车辆速度
			车身颜色,车辆品牌,车辆类型,辅助号牌种类,辅助号牌号码,辅助号牌颜色,图片路径,图片1文件名,
			图片2文件名,图片3文件名,入库时间,备用字段			
			 --%>
			<tr>
				<td class="head">行政区划</td>
				<td class="body" id="xzqhTD"></td>
				<td class="head">道路名称</td>
				<td class="body" id="dldmmcTD"></td>
				<td class="head">卡口简称</td>
				<td class="body" id="kkjcTD"></td>
			</tr>
			<tr>
				<td class="head">行驶方向</td>
				<td class="body" id="fxlxmcTD"></td>
				<td class="head">车道号</td>
				<td class="body" id="cdhTD"></td>
				<td class="head"></td>
				<td class="body"><input type="hidden" id="kkbh" name="kkbh"></td>
			</tr>
		</table>
	</div>
	<div id="veh" style="display:none;">
		<div id="block">
			<div id="blocktitle">机动车登记信息</div>
			<div id="blockmargin">8</div>
			<div id="loadingVeh" style="width:100%;text-align:center;font-size:12px;">
				<img alt="" src="images/running.gif"><br>正在查询，请稍候...
			</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail" id="vehDataTable">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<tr>
					<td class="head">车辆类型</td>
					<td class="body" id="vehCllxTD"></td>
					<td class="head">车辆品牌</td>
					<td class="body" id="vehClppTD"></td>
					<td class="head">车辆型号</td>
					<td class="body" id="vehClxhTD"></td>
				</tr>
				<tr>
					<td class="head">车辆状态</td>
					<td class="body" id="vehClztTD"></td>
					<td class="head">使用性质</td>
					<td class="body" id="vehSyxzTD"></td>
					<td class="head">车身颜色</td>
					<td class="body" id="vehCsysTD"></td>
				</tr>
				<tr>
					<td class="head">初次登记日期</td>
					<td class="body" id="vehCcdjrqTD"></td>
					<td class="head">所有人</td>
					<td class="body" id="vehSyrTD"></td>
					<td class="head"></td>
					<td class="body"></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="susp" style="display:none;">
		<div id="block">
			<div id="blocktitle">布控信息</div>
			<div id="blockmargin">8</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<tr>
					<td class="head">车辆类型</td>
					<td class="body"></td>
					<td class="head">车辆品牌</td>
					<td class="body"></td>
					<td class="head">车辆型号</td>
					<td class="body"></td>
				</tr>
				<tr>
					<td class="head">车辆状态</td>
					<td class="body"></td>
					<td class="head">使用性质</td>
					<td class="body"></td>
					<td class="head">车身颜色</td>
					<td class="body"></td>
				</tr>
				<tr>
					<td class="head">初次登记日期</td>
					<td class="body"></td>
					<td class="head">所有人</td>
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
				<%--input type="button" class="" onclick="getSuspinfo()" value="布控信息"> --%>
				<input type="button" class="button_vehicle" onclick="getVehicle()" value="车辆信息">
				<input type="button" id="btnGetNext1" class="button_left" onclick="getNext(-1)" value="上一条">
				<input type="button" id="btnGetNext2" class="button_right" onclick="getNext(1)" value="下一条">
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
</div>
</body>
</html>
