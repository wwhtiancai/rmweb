<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>过车实时监控</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var isStop = true;
var idVal = 1;
var jsq;
var passListHeight = 500;

var isBigPicOpen = true;

var isPause = false;

$(function(){  
    $(window).resize(function(){  
    	initSize();
    });
});

$(document).ready(function(){
	initSize();
});

function initSize(){
	curWidth = $(document.body).width() * 0.78;
	curHeight = $(document.body).height() - 280;
	var picHeight = 0;
	var picWidth = 0;
	if(curHeight / curWidth < 0.75){
		//以高度为准
		picHeight = curHeight;
		picWidth = picHeight / 0.75;
	}else{
		//以宽度为准
		picWidth = curWidth;
		picHeight = picWidth * 0.75;
	}
	$("#passPic").css("width", picWidth + "px");
	$("#passPic").css("height", picHeight + "px");
	
	passListHeight = (picHeight + 123);
	
	$("#passList").css("height", passListHeight + "px");
	
	var kkjcWidth = $(document.body).width() * 0.4;
	if(kkjcWidth > 130){
		kkjcWidth = kkjcWidth - 42 - 45;
	}
	$("#kkjc").css("width", kkjcWidth + "px");
	
	rgtTDWidth = $(document.body).width() * 0.96 - 120 - picWidth - 50;
	
	$("#rgtTD").attr("width", rgtTDWidth + "px");
	//$("#rgtCMD").css("height", (picHeight-50) + "px");
	//$("#rgtCMD").css("padding-top", (picHeight-76) + "px");
	
	spicH = (picHeight - 82) / 2;
	
	$("#tp2Div").css("height", spicH + "px");
	$("#tp3Div").css("height", spicH + "px");
}

function selectTollgate(){
	if(isStop == false){
		alert('您正在监控  “' + $("#kkjc").val() + '” 卡口，请先暂停后再选择需要监控的其他卡口！');
		return;
	}
	//openwin("<c:url value='component.dev?method=fwdTollgateRadio'/>", "");
	//return;
	curKKBH = $("#kkbh").val();
	curKKJC = $("#kkjc").val();
	
	curArgs = curKKBH + "||" + curKKJC;
	var r = window.showModalDialog("component.dev?method=fwdTollgateRadio", curArgs, "dialogWidth:800px;dialogHeight:600px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r != "undefined"){
		$("#kkbh").val(r[0]);
		$("#kkjc").val(r[1]);
	}else{
		$("#kkbh").val('');
		$("#kkjc").val('');
	}
	
	closes();
	getDirect();
	opens();
}
function getDirect(){
	$("#direct").empty();
	curKkbh = $("#kkbh").val();
	
	if(curKkbh == null || curKkbh == ''){
		return;
	}
	curUrl = "<c:url value='component.dev?method=getTollgateDirect&kkbh='/>" + curKkbh;
	$.getJSON(curUrl, 
		function(data){
			$.each(data, function(i,item){
				$("#direct").addOption(item.fxbh, decodeURI(item.fxmc));
			});
		}		
	);
}
function startMonitor(){
	//alert("val:" + $("#kkbh").val() + ";isValid:" + ($("#kkbh").val() == null || $("#kkbh").val() == ''));
	if($("#kkbh").val() == null || $("#kkbh").val() == ''){
		alert('请选择卡口');
		return;
	}
	
	isStop = false;
	$("#passDiv").css("display", "block");
	$("#startBtn").attr("disabled", true);
	$("#stopBtn").attr("disabled", false);
	$("#passList").empty();
	doContinue();
	reloadPassData();
}
function stopMonitor(){
	isStop = true;
	$("#stopBtn").attr("disabled", true);
	$("#startBtn").attr("disabled", false);
	$("#gcxh").val('');
	$("#gcsj").val('');
	clearTimeout(jsq);
}
function reloadPassData(){
	if(isStop == false){
		curKkbh = $("#kkbh").val();
		curFxlx = $("#direct").val();
		curGcsj = $("#gcsj").val();
		curGcxh = $("#gcxh").val();
		curUrl = "<c:url value='pass.tfc?method=getRealPass&kkbh='/>" + curKkbh;
		
		
		if(curFxlx != null && curFxlx != ''){
			curUrl = curUrl + "&fxlx=" + curFxlx;
		}
		if(curGcsj != null && curGcsj != ''){
			curUrl = curUrl + "&gcsj=" + curGcsj;
		}
		if(curGcxh != null && curGcxh != ''){
			curUrl = curUrl + "&gcxh=" + curGcxh;
		}
		
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success: function(data){
				//data = eval(msg);
				returnRownum = data.rownum;
				if(returnRownum < 0){
					alert(data.msg);
				}else if(returnRownum == 0){
					if($("#passSpaceDiv") != "undefined"){
						$("#passSpaceDiv").remove();
					}
					//设置间隔条
					//$("#passList").append(getSpaceDiv());
				}else if(returnRownum > 0){
					var cIdx = 0;
					var tmpPass;
					var tmpPassId;
					
					var schild = $("#passList").children();
					$("#passList").empty();
					
					lastIdx = returnRownum - 1;
					
					$.each(data.msg, function(i, item){
						tmpPassDiv = document.createElement("div");
						tmpPassDiv.id = idVal;
						//tmpPassDiv.style = "passDiv";
						tmpPassDiv.innerHTML = getPassListHtml(item);
						
						//$("#passList").append(getPassListHtml(item, tmpIdx + i));
						$("#passList").append(tmpPassDiv);
						$("#" + idVal).attr("class", "passDiv");
						if(i == lastIdx){
							setLastPass(item);
							$("#gcxh").val(item['gcxh']);
							$("#gcsj").val(item['gcsj'].substring(0,19));
						}
						
						idVal++;
						if(idVal > 10000){
							idVal = 1;
						}
					});
					$("#passList").append(getSpaceDiv());
					tmpIdx = 30 - returnRownum + 1;
					if(tmpIdx > 0 && schild.length > 0){
						if(tmpIdx > schild.length){
							tmpIdx = schild.length;
						}
						for(i = 0; i < tmpIdx; i++){
							if(schild[i].id != "tmpSpace"){
								$("#passList").append(schild[i]);
							}
						}
					}
					$("#passList").scrollTop(0);
				}
				jsq = setTimeout("reloadPassData()", 5000);
			},
			error: function(){
				alert('远程获取数据错误，请重新登录！');
			}
		}); 
	}
}

function getPassListHtml(data){
	var isf = true;
	tmpData = "{";
	for(var citem in data){
		if(!isf){
			tmpData = tmpData + ",";		
		}else{
			isf = false;
		}
		tmpData = tmpData + "'" + citem + "':'" + data[citem] + "'";
	}
	tmpData = tmpData + "}";
	//<div class="passDiv">
	//<span class="hphmC h02">粤PKJ852</span>
	//<img src="images/detal-big.png" class="passCmdPic" onclick="showDetail('')" align="absmiddle">
	//<img src="images/mark-big.png" class="passCmdPic" onclick="mark(this, '')" align="absmiddle">
	//</div>
	outHtml = '<span class="hphmC h' + data["hpzl"] + '" onclick="showPassDetail('+ tmpData + ')">' + decodeURI(data["hphm"]) + '</span>';
	//outHtml = outHtml + '&nbsp;<img src="images/detail-2.jpg" class="passCmdPic" onclick="showDetail(\'' + data["gcxh"] + '\')" align="absmiddle">';
	//outHtml = outHtml + '&nbsp;<img src="images/mark-2.jpg" class="passCmdPic" onclick="mark(this, \'' + data["gcxh"] + '\')" align="absmiddle">';
	//outHtml = outHtml + '</div>';
	
	//alert(outHtml);
	
	return outHtml;
}

function setLastPass(data){
	if(isPause){
		return;
	}
	if(!isBigPicOpen){
		return;
	}
	isBigPicOpen = false;
	//$("#tdKkjc").html($("#kkjc").val());
	
	setTableData(data);
	
	isBigPicOpen = true;
	//$("#gcxh").val(data['gcxh']);
	//$("#gcsj").val(data['gcsj']);
	
	curUrl = "<c:url value='pass.tfc?method=getVehicle&kkbh='/>" + data['kkbh'] + "&gcxh=" + data['gcxh'];
	
	$.ajax({
		type: "GET",
		url: curUrl,
		cache:false,
		async:false,
		dataType:"json",
		success: function(data){
			if(data['code'] == 1){
				msg = data['msg'];
				$("#tdVehClpp").html(decodeURI(msg['clpp']));
				$("#tdVehCsys").html(decodeURI(msg['csysmc']));
				$("#tdVehCllx").html(decodeURI(msg['cllxmc']));
				$("#tdVehClzt").html(decodeURI(msg['clztmc']));
				$("#tdVehCcdjrq").html(decodeURI(msg['ccdjrq']));
				$("#tdVehSyr").html(decodeURI(msg['syr']));
				$("#tdVehSyxz").html(decodeURI(msg['syxzmc']));
				$("#tdVehClxh").html(decodeURI(msg['clxh']));
			}else{
				$("#tdVehClpp").html();
				$("#tdVehCsys").html();
				$("#tdVehCllx").html();
				$("#tdVehClzt").html();
				$("#tdVehCcdjrq").html();
				$("#tdVehSyr").html();
				$("#tdVehSyxz").html();
				$("#tdVehClxh").html();
			}
			
		}
	});
	//alert("complete......");
	$("#dclgcxh").val(data['gcxh']);
}

function setTableData(data){
	$("#tdFxmc").html(decodeURI(data['fxlxmc']));
	$("#tdCdh").html(data['cdh']);
	$("#tdHpzlmc").html(decodeURI(data['hpzlmc']));
	$("#tdHphm").html(decodeURI(data['hphm']));
	$("#tdGcsj").html(data['gcsj'].substring(0, 19));
	$("#tdHpysmc").html(decodeURI(data['hpysmc']));
	curClsd = data['clsd'];
	if(curClsd == '-1'){
		$("#tdClsd").html("未测速");
	}else{
		$("#tdClsd").html(curClsd + " km/h");
	}
	curCwkc = data['cwkc'];
	
	if(curCwkc == '-1'){
		$("#tdCwkc").html("未采集");
	}else{
		var iCwkc = parseInt(curCwkc);
		if(iCwkc < 100){
			$("#tdCwkc").html("--");
		}else{
			$("#tdCwkc").html(curCwkc + " cm");
		}
	}
	
	tmpTpUrl = '&gcxh=' + data['gcxh'] + '&tpxh=1&fhk=1';

	$("#passPic").attr("src", '<c:url value="/readPassPic.tfc?method=getPassPic"/>' + tmpTpUrl);
	
	srcTztp = '<c:url value="/readTzPic.tfc?method=getTzPicture"/>&gcxh=' + data['gcxh']+'&fhk=1';
	$("#hptztp").attr("src", srcTztp);
}

function getSpaceDiv(){
	spaceDiv = document.createElement("div");
	spaceDiv.id="tmpSpace";
	spaceDiv.setAttribute("className", "space");
	return spaceDiv;
}

function mark(imgObj, gcxh){
	
}

function showDetail(gcxh){
	
}

function showPassDetail(data){
	//alert(data);
	doPause();
	setTableData(data);
}

function pause_continue(){
	if(isPause){
		doContinue();
	}else{
		doPause();
	}
}
function doPause(){
	isPause = true;
	$("#btn_pause_continue").attr("class", "button_continue");
	$("#btn_pause_continue").val("继续");
	$("#btn_detail").attr("disabled", false);
	//$("#btn_mark").attr("disabled", false);
}
function doContinue(){
	isPause = false;
	$("#btn_pause_continue").attr("class", "button_pause");
	$("#btn_pause_continue").val("暂停");
	$("#btn_detail").attr("disabled", true);
	//$("#btn_mark").attr("disabled", true);
}
function showPassDetailAll(){
	$("#dclgcxh").val();
}
//-->
</script>
<style>
.hphmC{
	width: 90px;
	height: 24px;
	line-height:26px;
	font-size: 14px;
	text-align: center;
	padding-bottom: 2px;
	cursor:hand;
}
.h01{background-color:yellow;color:black;}
.h02{background-color:blue;color:white;}
.h03{background-color:black;color:white;}
.h04{background-color:black;color:white;}
.h06{background-color:black;color:white;}
.h07{background-color:yellow;color:black;}
.h08{background-color:blue;color:white;}
.h14{background-color:green;color:white;}
.h15{background-color:yellow;color:black;}
.h16{background-color:yellow;color:black;}
.h23{background-color:white;color:black;}
.h24{background-color:white;color:black;}
.h25{background-color:green;color:white;}
.h26{background-color:black;color:white;}
.h27{background-color:black;color:white;}
.h31{background-color:white;color:black;}
.h32{background-color:white;color:black;}
.h41{background-color:white;color:#AAAAAA;}
.h42{background-color:white;color:#AAAAAA;}
.h43{background-color:white;color:#AAAAAA;}
.h44{background-color:white;color:#AAAAAA;}
.h99{background-color:white;color:#AAAAAA;}

.space{
	height: 8px;
	width: 100%;
	background-color: white;
	background-color: green;
	background-position:center;
	background-repeat: no-repeat;
}

.passDiv{
	background-color:white;
	line-height:24px;
	vertical-align:middle;
	background-image:url(images/spaceline.jpg);
	background-position:bottom;
	background-repeat:repeat-x;
	padding-bottom:2px;
	padding-top:1px;
	width:100%;
	text-align: center;
}
.passCmdPic{
	cursor:hand;width:12px;height:11px;
	padding-left:2px;
}
</style>
</head>
<body>

<div id="panel" style="display:none">
	<div id="paneltitle">过车实时监控</div>
	<div id="block">
		<div id="blocktitle">卡口</div>
		<div id="blockmargin">6</div>
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td class="head" width="8%">卡口</td>
				<td class="body" width="40%">
					<input type="text" id="kkjc" name="kkjc" value="" readonly="readonly" style="width:330px;">
					
					<input type="button" value="选择" onclick="selectTollgate()">
				</td>
				<td class="head" width="8%">方向</td>
				<td class="body" width="24%">
					<select id="direct" style="width:100%"></select>
				</td>
				<td class="submit" width="40%">
					<input type="hidden" id="kkbh" name="kkbh" value="">
					<input type="hidden" id="gcsj" name="gcsj" value="">
					<input type="hidden" id="gcxh" name="gcxh" value="">
					<input id="startBtn" type="button" value="开始" onclick="startMonitor()" class="button_save">
					<input id="stopBtn" type="button" value="停止" onclick="stopMonitor()" class="button_close">
				</td>
			</tr>
		</table>
	</div>
	<div id="passDiv" style="display:none;">
	<div id="block">
		<div id="blocktitle">通行信息</div>
		<div id="blockmargin">0</div>
		<table border="0" cellspacing="0" cellpadding="0" class="detail" id="tt1">
			<tr>
				<td class="body" rowspan="2" width="110px;">
					<div id="passList" style="border:solid #CCCCCC 1px;width:100%;overflow-y:hidden;text-align:right;">
					</div>
				</td>
				<td class="body" align="center" style="text-align:center;">
					<table border="0" cellspacing="0" cellpadding="0" class="detail">
						<tr>
							<td class="body"><img alt="" src="" id="passPic" onclick="openImage('passPic')"></td>
							<td class="body" width="250px" style="text-align:center;" id="rgtTD">
								<div id="tp2Div" style="margin-bottom:2px;"></div>
								<div id="tp3Div" style="margin-bottom:2px;"></div>
								<div id="tzhpDiv" style="margin-bottom:2px;"><img id="hptztp" height="50px" width="130px" src="" alt=""></div>
								<div id="rgtCMD" style="vertical-align:bottom;width:100%;">
									<input type="hidden" id="dclgcxh" value="">
									<input type="button" value="暂停" onclick="pause_continue()" class="button_pause" id="btn_pause_continue">
									<%--<input type="button" value="详细" onclick="showPassDetailAll()" class="button_print" id="btn_detail">
									<input type="button" value="标记" onclick="mark()" class="button_mark" id="btn_mark"> --%>
								</div>
							</td>
						</tr>
					</table>
					
				</td>
			</tr>
			<tr>
				<td class="body">
					<table border="0" cellspacing="1" cellpadding="0" class="detail">
						<col width="8%">
						<col width="17%">
						<col width="8%">
						<col width="17%">
						<col width="8%%">
						<col width="16%">
						<col width="10%%">
						<col width="16%">
						<tr>
							<td colspan="4" class="head" style="text-align:center;">卡口识别信息</td>
							<td colspan="4" class="head" style="text-align:center;">机动车登记信息</td>
						</tr>
						<tr>
							<td class="head">方向名称</td>
							<td class="body" id="tdFxmc"></td>
							<td class="head">号牌号码</td>
							<td class="body" id="tdHphm"></td>
							
							<td class="head">车辆品牌</td>
							<td class="body" id="tdVehClpp"></td>
							<td class="head">车身颜色</td>
							<td class="body" id="tdVehCsys"></td>
						</tr>
						
						<tr>
							<td class="head">车道号</td>
							<td class="body" id="tdCdh"></td>
							<td class="head">号牌种类</td>
							<td class="body" id="tdHpzlmc"></td>
							
							
							<td class="head">车辆型号</td>
							<td class="body" id="tdVehClxh"></td>
							<td class="head">车辆状态</td>
							<td class="body" id="tdVehClzt"></td>
						</tr>
						<tr>
							<td class="head">车辆速度</td>
							<td class="body" id="tdClsd"></td>
							<td class="head">号牌颜色</td>
							<td class="body" id="tdHpysmc"></td>
							
							<td class="head">车辆类型</td>
							<td class="body" id="tdVehCllx"></td>
							<td class="head">初次登记日期</td>
							<td class="body" id="tdVehCcdjrq"></td>
						</tr>
						<tr>
							<td class="head">过车时间</td>
							<td class="body" id="tdGcsj"></td>
							<td class="head">车外廓长</td>
							<td class="body" id="tdCwkc"></td>
							
							<td class="head">使用性质</td>
							<td class="body" id="tdVehSyxz"></td>
							<td class="head">所有人</td>
							<td class="body" id="tdVehSyr"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	</div>
</div>
</body>
</html>