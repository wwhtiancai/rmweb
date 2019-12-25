<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>����ʵʱ���</title>

<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var isStop = true;
var passIdArray = new Array(30);
var idVal = 1;
var jsq;
var passListHeight = 500;
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
		//�Ը߶�Ϊ׼
		picHeight = curHeight;
		picWidth = picHeight / 0.75;
	}else{
		//�Կ��Ϊ׼
		picWidth = curWidth;
		picHeight = picWidth * 0.75;
	}
	$("#passPic").css("width", picWidth + "px");
	$("#passPic").css("height", picHeight + "px");
	
	passListHeight = (picHeight + 123);
	
	$("#passList").css("height", passListHeight + "px");
	
	var kkjcWidth = $(document.body).width() * 0.4;
	if(kkjcWidth > 130){
		kkjcWidth = kkjcWidth - 42 - 30;
	}
	$("#kkjc").css("width", kkjcWidth + "px");
}

function selectTollgate(){
	if(isStop == false){
		alert('�����ڼ��  ��' + $("#kkjc").val() + '�� ���ڣ�������ͣ����ѡ����Ҫ��ص��������ڣ�');
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
		alert('��ѡ�񿨿�');
		return;
	}
	
	isStop = false;
	$("#startBtn").attr("disabled", true);
	$("#stopBtn").attr("disabled", false);
	$("#passDiv").css("display", "block");
	$("#passList").empty();
	reloadPassData();
}
function stopMonitor(){
	isStop = true;
	$("#stopBtn").attr("disabled", true);
	$("#startBtn").attr("disabled", false);
	$("#passList").scrollTop($("#passList").height());
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
					//���ü����
					//$("#passList").append(getSpaceDiv());
				}else if(returnRownum > 0){
					var cIdx = 0;
					var tmpPass;
					var tmpPassId;
					
					if(returnRownum >= 30){
						$("#passList").empty();
					}else{
						var schild = $("#passList").children();
						passListSize = schild.length;
						//alert("start:" + schild.length);
						if(passListSize >= 30){
							$("#passList").empty();
							for(var j = returnRownum; j < passListSize; j++){
								if(schild[j].id != 'tmpSpace'){
									$("#passList").append(schild[j]);
								}
							}
						}
						//alert("returnRownum/ori:" + returnRownum + "/" + $("#passList").children().length);
						//alert("end:" + schild.length);
						//for(var j = returnRownum; j < 30; j++){
						//	passIdArray[j - returnRownum] = passIdArray[j];
						//}
					}
					
					lastIdx = returnRownum - 1;
					
					if($("#passSpaceDiv") != "undefined"){
						$("#passSpaceDiv").remove();
					}
					
					//���ü����
					$("#passList").append(getSpaceDiv());
					
					tmpIdx = 30 - returnRownum;
					
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
						}
						
						passIdArray[tmpIdx + i] = idVal;
						
						idVal++;
						if(idVal > 10000){
							idVal = 1;
						}
					});
					$("#passList").scrollTop(1000);
				}
			}
		}); 
		
		
		jsq = setTimeout("reloadPassData()", 10000);
	}
}

function getPassListHtml(data){
	//<div class="passDiv">
	//<span class="hphmC h02">��PKJ852</span>
	//<img src="images/detal-big.png" class="passCmdPic" onclick="showDetail('')" align="absmiddle">
	//<img src="images/mark-big.png" class="passCmdPic" onclick="mark(this, '')" align="absmiddle">
	//</div>
	outHtml = '<span class="hphmC h' + data["hpzl"] + '">' + decodeURI(data["hphm"]) + '</span>';
	outHtml = outHtml + '&nbsp;<img src="images/detail-2.jpg" class="passCmdPic" onclick="showDetail(\'' + data["gcxh"] + '\')" align="absmiddle">';
	outHtml = outHtml + '&nbsp;<img src="images/mark-2.jpg" class="passCmdPic" onclick="mark(this, \'' + data["gcxh"] + '\')" align="absmiddle">';
	//outHtml = outHtml + '</div>';
	
	//alert(outHtml);
	
	return outHtml;
}

function setLastPass(data){
	$("#tdKkjc").html($("#kkjc").val());
	$("#tdFxmc").html(decodeURI(data['fxlxmc']));
	$("#tdCdh").html(data['cdh']);
	$("#tdHpzlmc").html(decodeURI(data['hpzlmc']));
	$("#tdHphm").html(decodeURI(data['hphm']));
	$("#tdGcsj").html(data['gcsj']);
	$("#tdHpysmc").html(decodeURI(data['hpysmc']));
	curClsd = data['clsd'];
	if(curClsd == '-1'){
		$("#tdClsd").html("δ����");
	}else{
		$("#tdClsd").html(curClsd + " km/h");
	}
	
	$("#tdCsysmc").html(decodeURI(data['csysmc']));
	
	curCwkc = data['cwkc'];
	
	if(curCwkc == '-1'){
		$("#tdCwkc").html("δ�ɼ�");
	}else{
		$("#tdCwkc").html(curCwkc + " cm");
	}
	
	
	$("#tdClpp").html(decodeURI(data['clpp']));
	$("#tdCllx").html(decodeURI(data['cllxmc']));
	$("#tdFzhpzlmc").html(decodeURI(data['fzhpzlmc']));
	$("#tdFzhphm").html(decodeURI(data['fzhphm']));
	$("#tdFzhpysmc").html(decodeURI(data['fzhpysmc']));
	
	$("#passPic").attr("src", decodeURIComponent(data['tpjl1']));
	
	$("#gcxh").val(data['gcxh']);
	$("#gcsj").val(data['gcsj']);
	
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
	padding-right: 2px;
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
	background-image: url(images/space.jpg);
	background-position: left;
	background-repeat: no-repeat;
	padding-left:20px; 
}

.passDiv{
	background-color:white;
	line-height:24px;
	vertical-align:middle;
	background-image:url(images/spaceline.jpg);
	background-position:bottom;
	background-repeat:repeat-x;
	padding-bottom:2px;
	padding-top:0px;
}
.passCmdPic{
	cursor:hand;width:12px;height:11px;
	padding-left:2px;
}
</style>
</head>
<body>

<div id="panel" style="display:none">
	<div id="paneltitle">����ʵʱ���</div>
	<div id="block">
		<div id="blocktitle">��ؿ���</div>
		<div id="blockmargin">6</div>
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td class="head" width="8%">����</td>
				<td class="body" width="40%">
					<input type="text" id="kkjc" name="kkjc" value="" readonly="readonly" style="width:330px;">
					<img alt="" src="images/tollgate-s2.gif" onclick="selectTollgate()" align="absmiddle" style="cursor:hand">
				</td>
				<td class="head" width="8%">����</td>
				<td class="body" width="24%">
					<select id="direct" style="width:100%"></select>
				</td>
				<td class="submit" width="40%">
					<input type="hidden" id="kkbh" name="kkbh" value="">
					<input type="hidden" id="gcsj" name="gcsj" value="">
					<input type="hidden" id="gcxh" name="gcxh" value="">
					<input id="startBtn" type="button" value="��ʼ" onclick="startMonitor()" class="button_save">
					<input id="stopBtn" type="button" value="��ͣ" onclick="stopMonitor()" class="button_close">
				</td>
			</tr>
		</table>
	</div>
	<div id="passDiv" style="display:none;">
	<div id="block">
		<div id="blocktitle">��ؽ��</div>
		<div id="blockmargin">1</div>
		<table border="0" cellspacing="0" cellpadding="0" class="detail" id="tt1">
			<tr>
				<td class="body" align="center" style="text-align:center;">
					<img alt="" src="" id="passPic">
				</td>
				<td class="body" rowspan="2" width="156px;">
					<div id="passList" style="border:solid #CCCCCC 1px;width:100%;overflow-y:scroll;text-align:right;padding-right:6px;">
					</div>
				</td>
			</tr>
			<tr>
				<td class="body">
					<table border="0" cellspacing="1" cellpadding="0" class="detail">
						<col width="10%">
						<col width="23%%">
						<col width="10%">
						<col width="23%">
						<col width="10%%">
						<col width="24%">
						<tr>
							<td class="head">��������</td>
							<td class="body" id="tdKkjc"></td>
							<td class="head">��������</td>
							<td class="body" id="tdFxmc"></td>
							<td class="head">������</td>
							<td class="body" id="tdCdh"></td>
						</tr>
						<tr>
							<td class="head">��������</td>
							<td class="body" id="tdHpzlmc"></td>
							<td class="head">���ƺ���</td>
							<td class="body" id="tdHphm"></td>
							<td class="head">����ʱ��</td>
							<td class="body" limit="time" id="tdGcsj"></td>
						</tr>
						<tr>
							<td class="head">������ɫ</td>
							<td class="body" id="tdHpysmc"></td>
							<td class="head">�����ٶ�</td>
							<td class="body" id="tdClsd"></td>
							<td class="head">������ɫ</td>
							<td class="body" id="tdCsysmc"></td>
						</tr>
						<tr>
							<td class="head">��������</td>
							<td class="body" id="tdCwkc"></td>
							<td class="head">����Ʒ��</td>
							<td class="body" id="tdClpp"></td>
							<td class="head">��������</td>
							<td class="body" id="tdCllx"></td>
						</tr>
						<tr>
							<td class="head">������������</td>
							<td class="body" id="tdFzhpzlmc"></td>
							<td class="head">�������ƺ���</td>
							<td class="body" id="tdFzhphm"></td>
							<td class="head">����������ɫ</td>
							<td class="body" id="tdFzhpysmc"></td>
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