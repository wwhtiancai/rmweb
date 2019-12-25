<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>机动车轨迹全国查询</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.loadthumb.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/hphm.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>
<script type="text/javascript">
<!--
var queryModel = null;
var passData = null;
var curPage;
var totalCount;
var pageCount;
var isHandled = false;
var curShowStyle;
var passPicWidth;
var passPicHeight;

var selectedFzjgp_bg = 0;

var detailWin = null;
var handledWin = null;

var gateData = null;

var queryStatTimer;

if (!window.console) {
	window.console = {
		log: function() {
			alert(arguments[0]);
		}
	}
}
$(window).bind("load",function(){
	$.datepicker.setDefaults($.datepicker.regional['']);
	$(".jscal").each(function () {
		eval($(this).html());
	});
});

window.onresize = function(){
	initSize();
}

window.onunload = function (){
	if(detailWin != null){
		detailWin.close();
	}
	if(handledWin != null){
		handledWin.close();
	}
}

$(document).ready(function(){
	curShowStyle = $("input[name=showStyle][checked=true]").val();
	if($("#kssj").val() == ''){
		$("#kssj").val(getLastWeek()+" 00:00");
	}
	if($("#jssj").val() == ''){
		$("#jssj").val(getNow(true).substring(0,16));
	}
	initSize();
});

function initSize(){
	bodyWidth = $(document.body).width();
	passPicWidth = parseInt((bodyWidth - 40) * 0.96 * 0.25);
	passPicHeight = parseInt(passPicWidth * 0.8);
	if(curShowStyle == 2){
		//缩略图显示
		$(".passPicB").css("width", passPicWidth + "px");
		$(".passPicB").css("height", passPicHeight + "px");
	}
}

function doChecking(){
	//return true;
	if($("#purpose").val() == ''){
		alert("请选择查询原因！");
		return false;
	}
	if(!checkNull($("#kssj"),"开始时间")) return false;
	if(!checkNull($("#jssj"),"结束时间")) return false;
	if(!compareDate($("#kssj").val(),$("#jssj").val(),"开始时间","结束时间")) return false;
	
	if(!checkNull($("#hpzl"), "号牌种类")) return false;
	if(!checkNull($("#hphm"), "号牌号码")) return false;
	if(!checkHPHM($("#hphm"), "号牌号码")) return false;
	
	
	var cxfwAry = $("input[name=fzjg][checked=true]");
	if(cxfwAry.length < 1){
		alert("请选择查询范围！");
		return false;
	}
	
	return true;
}
function query_cmd(){
	if(!doChecking()){
		return;
	}
	$("#thdQueryResultDiv").html('');
	closes();
	setQueryingHtml();
	curPage = 1;
	var tmpUrl = $("#myform").attr("action") + "&page=" + curPage + "&model=";
	$("#myform").ajaxSubmit({
		url:tmpUrl,
		dataType:"json",
		async:true,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:localReturns
	});
}
function validCxfw(fzjgVal){
	//alert($("#fzjg" + fzjgVal).val() + ', checked:' + ($("#fzjg" + fzjgVal).attr("checked")) + ', isChecked:' + ($("#fzjg" + fzjgVal).attr("checked") == false));
	if($("#fzjg" + fzjgVal).attr("checked") == true){
		var cxfwAry = $("input[type=checkbox][name=fzjg][checked=true]");
		if(cxfwAry.length > 3){
			$("#fzjg" + fzjgVal).attr("checked", false);
			alert("查询范围最大为3个！");
		}
	}
}

function handled_cmd(){
	handledWin = openwin800("tfcQuery.tfc?method=handleDialog","queryHandled","");
	//alert('做反馈');
}
function localReturns(data){
	var code = data['code'];
	var msg = data['msg'];
	totalCount = data['totalCount'];
	pageCount = data['pageCount'];
	if(code == -1){
		emptyDataDiv();
		alert("查询错误：" + decodeURIComponent(msg));
		opens();
		return;
	}else if(code == 0){
		emptyDataDiv();
		alert(decodeURIComponent(msg));
	}else if(code == 1){
		passData = msg;
		reloadData();
	}
	$("#thdDiv").css("display", "block");
	reloadQueryStat();
	opens();
}
function reloadData(){
	if(passData == null){
		return;
	}
	if(curShowStyle == 1){
		//列表显示
		getListHtml();
	}else if(curShowStyle == 2){
		//缩略图显示
		getThumbHtml();
	}
}
function getListHtml(){
	setLoaddataHtml();
	curHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list">\n';
	curHtml += '<tr class="head">\n';
	curHtml += '<td width="10%">号牌种类</td>\n';
	curHtml += '<td width="10%">号牌号码</td>\n';
	curHtml += '<td width="10%">行政区划</td>\n';
	curHtml += '<td width="20%">道路名称</td>\n';
	curHtml += '<td width="23%">卡口简称</td>\n';
	curHtml += '<td width="12%">行驶方向</td>\n';
	curHtml += '<td width="15%">过车时间</td>\n';
	curHtml += '</tr>\n';
	
	$.each(passData, function(i,item){
		curHtml += '<tr class="out" style="cursor:pointer" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" ondblclick="showDetail(\'' + item['gcxh'] + '\',' + curPage + ',' + i + ')">\n';
		curHtml += '<td>' + decodeURIComponent(item['hpzlmc']) + '</td>';
		curHtml += '<td class="hpys' + item['hpys'] + '">' + decodeURIComponent(item['hphm']) + '</td>';
		curHtml += '<td>' + decodeURIComponent(item['xzqhmc']) + '</td>';
		curHtml += '<td>' + decodeURIComponent(item['dldmmc']) + '</td>';
		curHtml += '<td>' + decodeURIComponent(item['kkbhmc']) + '</td>';
		curHtml += '<td>' + decodeURIComponent(item['fxlxmc']) + '</td>';
		curHtml += '<td>' + item['gcsj'].substring(0, 19) + '</td>';
		//curHtml += '<td><span>查看详细</span>&nbsp;<span>精确追踪</span></td>';
		curHtml += '</tr>';
	});
	
	curHtml += '<tr><td colspan="8" class="page">共' + totalCount + '条&nbsp;共' + pageCount + '页&nbsp;第' + curPage + '页 &nbsp;';
	
	curHtml += '<label id="pageCMD" onclick="gotoPage(1)" style="cursor:hand">首页</label>&nbsp;';
	if(curPage == 1){
		curHtml += '<label id="pageCMD">上一页</label>&nbsp;';
	}else{
		curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage - 1) + ')" style="cursor:hand">上一页</label>&nbsp;';
	}
	
	if(curPage >= pageCount){
		curHtml += '<label id="pageCMD">下一页</label>&nbsp;';
	}else{
		curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage + 1) + ')" style="cursor:hand">下一页</label>&nbsp;';
	}
	
	curHtml += '<label id="pageCMD" onclick="gotoPage(' + pageCount + ')" style="cursor:hand">末页</label>&nbsp;</td></tr>';
	
	curHtml += '</table>';
	$("#data").empty();
	$("#data").html(curHtml);
	$(document.doby).scrollTop($(document.doby).height());
}
function getThumbHtml(){
	setLoaddataHtml();
	curHtml = '';
	curCount = 0;
	curHtml += '<table border="0" cellpadding="0" cellspacing="6px" width="100%" class="thumbTable">';
	curH = '';
	$.each(passData, function(i,item){
		if(i > 0 && (i % 4) == 0){
			curHtml += '<tr>' + curH + '</tr>';
			curH = '';
		}
		
		imgSrc = '/readPassPic.tfc?method=getPassPic&gcxh=' + item['gcxh'] + '&tpxh=1';
		
		if(item['fwdz'] != null && item['fwdz'] != ''){
			imgSrc = decodeURIComponent(item['fwdz']) + imgSrc;
		}
		
		//缩略图模式中有放大镜功能
		//curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="showDetail(\'' + item['gcxh'] + '\',' + curPage + ',' + i + ')">' + '<img class="passPicB" style="width:' + passPicWidth + 'px;height:' + passPicHeight + 'px;" src="' + imgSrc + '" jqimg="' + imgSrc + '"></div>';
		//缩略图模式中无放大镜功能
		curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="showDetail(\'' + item['gcxh'] + '\',' + curPage + ',' + i + ')">' + '<img class="passPicB" style="width:' + passPicWidth + 'px;height:' + passPicHeight + 'px;" src="' + imgSrc + '"></div>';
		curH += '<div>号牌种类：' + decodeURIComponent(item['hpzlmc']) + '&nbsp;&nbsp;号牌号码：' + decodeURIComponent(item['hphm']) + '<br>';
		curH += '行政区划：' + decodeURIComponent(item['xzqhmc']) + '<br>道路名称：' + decodeURIComponent(item['dldmmc']) + '<br>';
		curH += '卡口简称：' + decodeURIComponent(item['kkbhmc']) + '<br>行驶方向：' + decodeURIComponent(item['fxlxmc']) + '<br>';
		curH += '过车时间：' + decodeURIComponent(item['gcsj']).substring(0,19) + '<br></div></td>';
		curCount = i;
	});
	if((curCount % 4) != 0 || curCount == 0){
		//curHtml += '</div>';
		tmpIdx = 1;
		if(curCount == 0){
			tmpIdx = 1;
		}else{
			tmpIdx = curCount % 4;
		}
		for(i = tmpIdx; i < 4; i++){
			curH += '<td width="25%"></td>';
		}
		
		curHtml += '<tr>' + curH + '</tr>';
	}
	
	curHtml += '</table><div></div>'
	
	//curHtml += '</ul>';
	curHtml += '<table border="0" cellspacing="1" cellpadding="0" class="list"><tr><td width="100%" class="page">共' + totalCount + '条&nbsp;共' + pageCount + '页&nbsp;第' + curPage + '页 &nbsp;';
	curHtml += '<label id="pageCMD" onclick="gotoPage(1)" style="cursor:hand">首页</label>&nbsp;';
	if(curPage == 1){
		curHtml += '<label id="pageCMD">上一页</label>&nbsp;';
	}else{
		curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage - 1) + ')" style="cursor:hand">上一页</label>&nbsp;';
	}
	
	if(curPage >= pageCount){
		curHtml += '<label id="pageCMD">下一页</label>&nbsp;';
	}else{
		curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage + 1) + ')" style="cursor:hand">下一页</label>&nbsp;';
	}
	
	curHtml += '<label id="pageCMD" onclick="gotoPage(' + pageCount + ')" style="cursor:hand">末页</label>&nbsp;</td></tr></table>';
	
	$("#data").empty();
	$("#data").html(curHtml);
	$(document.doby).scrollTop($(document.doby).height());
/**	$(".jqzoomPassQuery").jqueryzoom({
        xzoom: 300, //设置放大 DIV 长度（默认为 200）
        yzoom: 300, //设置放大 DIV 高度（默认为 200）
        offset: 10, //设置放大 DIV 偏移（默认为 10）
        position: "buttom", //设置放大 DIV 的位置（默认为右边）
        preload:1,
        lens:1
    });*/
}

function showDetail(gcxh, pageNum, listNum){
	detailUrl = "<c:url value='passQuery.tfc?method=showPassNationalDetail&gcxh='/>" + gcxh + "&page=" + pageNum + "&idx=" + listNum;
	detailWin = openwin(detailUrl, "PassDetail");
}

function setQueryingHtml(){
	$("#data").empty();
	queryingHtml = '<br><br><img alt="" src="images/running.gif"><br>正在查询，请耐心等候...';
	$("#data").html(queryingHtml);
	$("#thdQueryStatDiv").empty();
	queryingStatHtml = '<img src="rmjs/zoom/ajax-loader.gif">正在查询，请稍后...';
	$("#thdQueryStatDiv").html(queryingStatHtml);
	//alert();
}

function setLoaddataHtml(){
	$("#data").empty();
	loadDataHtml = '<br><br><img alt="" src="images/running.gif"><br>正在整理数据，请耐心等候...';
	$("#data").html(loadDataHtml);
	//alert();
}

function emptyDataDiv(){
	$("#data").empty();
}

function gotoPage(idx){
	closes();
	$("#pageCMD").attr("disabled", true);
	setQueryingHtml();
	curUrl = "<c:url value='passQuery.tfc?method=passQueryNationalToJSP&page='/>" + idx + "&model=" + queryModel;
	$.ajax({
		type: "GET",
		url: curUrl,
		cache:false,
		async:true,
		dataType:"json",
		success: function(data){
			curPage = idx;
			localReturns(data);
		}
	});
	opens();
}
function doShowStyleChange(){
	//alert($("input[id=showStyle" + queryModel + "][checked=true]").val());
	tmpShowStyle = $("input[id=showStyle][checked=true]").val();
	if(passData == null){
		curShowStyle = tmpShowStyle;
		return;
	}
	if(curShowStyle != tmpShowStyle){
		if(tmpShowStyle == 1){
			//列表显示
			getListHtml();
		}else if(tmpShowStyle == 2){
			//缩略图显示
			getThumbHtml();
		}
	}
	curShowStyle = tmpShowStyle;
}

function reloadQueryStat(){
	curUrl = "<c:url value='passQuery.tfc?method=getQueryStatus'/>";
	$.ajax({
		type: "GET",
		url: curUrl,
		cache:false,
		async:true,
		dataType:"json",
		success: function(data){
			var jg = data['jg'];
			if(jg == '1'){
				$("#thdQueryStatDiv").html('查询已完成');
				clearTimeout(queryStatTimer);
			}else if(jg == '-9'){
				$("#thdQueryStatDiv").html('获取查询状态异常');
				clearTimeout(queryStatTimer);
			}else{
				$("#thdQueryStatDiv").html('<img src="rmjs/zoom/ajax-loader.gif">正在查询，请稍后...');
				queryStatTimer = setTimeout("reloadQueryStat()", 3000);
			}
			var ch = '';
			$.each(data['msg'], function(i, item){
				var tcxjg = decodeURI(item['cxjg']);
				if(tcxjg==1){
					ch +=""+ decodeURI(item['fzjg']) + '：' + decodeURI(item['errmsg']) + '；';
				}else{
					ch +=""+ decodeURI(item['fzjg']) + '：'+"<font color=red><a title='"+decodeURI(item['errmsg'])+"'>查询时发生错误，鼠标移至此处查看错误详细信息</a></font>；";
				}
			});
			$("#thdQueryResultDiv").html(ch);
		}
	});
}
function getRemoteMachine(){
	$("#dsfzjg").empty();
	if($("#fzjgp").val() == ''){
		return;
	}
	curUrl = "<c:url value='passQuery.tfc?method=getRemoteMachine&fzjgp='/>" + encodeURI(encodeURI($("#fzjgp").val()));
	$.ajax({
		type: "GET",
		url: curUrl,
		cache:false,
		async:true,
		dataType:"json",
		success: function(data){
			var jg = data['jg'];
			if(jg == '1'){
				curH = '<ul style="width:100%;">';
				$.each(data['msg'], function(i, item){
					t = '<li style="float:left;width:80px;">';
					t += '<input type="checkbox" id="fzjg" name="fzjg" value="' + decodeURI(item['fzjg']) + '">' + decodeURI(item['azdmc']);
					t += '</li>';
					curH += t;
				});
				curH += '</ul>';
				$("#dsfzjg").html(curH);
			}else if(jg == '0'){
				alert('未找到');
			}
		}
	});
}
function changeFzjgp(fzjgp){
	if($("#fzjgp" + fzjgp).attr("checked")){
		checkedFzjgp = $(".fzjgps[checked=true]");
		
		if(checkedFzjgp.length > 3){
			alert("查询省份不能超过3个！");
			$("#fzjgp" + fzjgp).attr("checked", false);
			return;
		}
		
		$(".fzjgps").attr("disabled", true);
		var checkedFzjgp = $(".fzjgps[checked=true]");
		curUrl = "<c:url value='passQuery.tfc?method=getRemoteMachine&fzjgp='/>" + encodeURI(encodeURI(fzjgp));
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success: function(data){
				$(".fzjgps").attr("disabled", false);
				var jg = data['jg'];
				if(jg == '1'){
					curH = '<div id="selected' + fzjgp + '"><ul style="width:100%;">';
					$.each(data['msg'], function(i, item){
						t = '<li style="float:left;width:78px;">';
						t += '<input type="checkbox" id="fzjg" name="fzjg" onclick="changeFzjg(this)" value="' + decodeURI(item['fzjg']) + '">' + decodeURI(item['azdmc']);
						t += '</li>';
						curH += t;
					});
					curH += '</ul></div>';
					$("#dsfzjg").append(curH);
				}else if(jg == '0'){
					alert('未找到');
				}
				var ssss = $("#dsfzjg").children();
				$.each(ssss, function(i,item){
					selectedFzjgp_bg = (selectedFzjgp_bg + 1) % 2;
					$(item).attr("class", "selectedFzjgpDivBg" + selectedFzjgp_bg)
				});
			},
			exception: function(data){
				$(".fzjgps").attr("disabled", false);
			}
		});
	}else{
		$("#selected" + fzjgp).remove();
	}
}
function changeFzjg(obj){
	if($(obj).attr("checked")){
		var selectedFzjg = $("input[name=fzjg][checked=true]");
		if(selectedFzjg.length > 6){
			alert("查询范围不能超过6个！");
			$(obj).attr("checked", false);
			return;
		}
		
		curFzjg = $(obj).val();
		if(curFzjg.length == 3){
			if(curFzjg.substr(1,2) == 'ZD'){
				csft = curFzjg.substr(0,1);
				fzjgAry = $("input[name=fzjg]");
				$.each(fzjgAry, function(i, item){
					tmpFzjg = $(item).val();
					if(tmpFzjg.substr(0,1) == csft && tmpFzjg != curFzjg){
						$(item).attr("checked", false);
						$(item).attr("disabled", true);
					}
				});
			}
		}
	}else{
		curFzjg = $(obj).val();
		if(curFzjg.length == 3){
			if(curFzjg.substr(1,2) == 'ZD'){
				csft = curFzjg.substr(0,1);
				fzjgAry = $("input[name=fzjg]");
				$.each(fzjgAry, function(i, item){
					tmpFzjg = $(item).val();
					if(tmpFzjg.substr(0,1) == csft && tmpFzjg != curFzjg){
						$(item).attr("disabled", false);
					}
				});
			}
		}
	}
}
//-->
</script>
<style>
.hpys0{
	background-color: white;
	color:black;
	font-weight: bold;;
}
.hpys1{
	background-color: yellow;
	color:black;
}
.hpys2{
	background-color: blue;
	color:white;
}
.hpys3{
	background-color: black;
	color:white;
}
.hpys4{
	background-color: green;
	color:white;
}
.hpys9{
	background-color: #AAAAAA;
	color:black;
}
.hpys{
	background-color: #AAAAAA;
	color:black;
}
.passThumbLi{
	float: left;
	padding: 2px;
	text-align: left;
	word-wrap: break-word;
}
.xx {
	width:100%;
	background:#E0F1FB;
	font-size:14px;
	color:#114877;
	font-weight:bold;
	padding:3px;
}
.thumbTable{
	font-size:12px;
	width:100%;
	vertical-align: top;
	background-color: F7F7F7;
}
.selectedFzjgpDivBg0{
	background-color: CCECFF;
}
.selectedFzjgpDivBg1{
	background-color: FFFFFF;
}
</style>
</head>
<body>

<div id="panel" style="display:none">
	<div id="paneltitle">机动车轨迹全国查询</div>
	<div id="query">
		<div id="querytitle">查询条件</div>
		<form name="myform" id="myform" action="<c:url value='passQuery.tfc?method=nationalQuery'/>" method="post">
		<table border="0" cellspacing="1" cellpadding="0" class="query" width="100%">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="24%">
			<tr>
				<td class="head"><span class="gotta">*</span>查询省份</td>
				<td class="body" colspan="5">
					<ul style="width:100%;">
						<c:forEach items="${fzjgList}" var="current">
							<li style="width:80px;">
								<input type="checkbox" class="fzjgps" id="fzjgp<c:out value='${current.fzjg}'/>" value="<c:out value='${current.fzjg}'/>" onclick="changeFzjgp('<c:out value='${current.fzjg}'/>')">
								<label for="fzjgp<c:out value='${current.fzjg}'/>"><c:out value='${current.azdmc}'/></label>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>查询范围</td>
				<td class="body" colspan="5">
					<div id="dsfzjg"></div>
				</td>
			</tr>
			<tr>
				<td class="head">道路类型</td>
				<td class="body"><h:codebox list='${ldlxList}' id='ldlx' haveNull='1'/></td>
				<td class="head">卡口类型</td>
				<td class="body"><h:codebox list='${kklxList}' id='kklx' haveNull='1'/></td>
				<td class="head"></td>
				<td class="body"></td>
			</tr>
			<tr>
				<td width="10%" class="head"><span class="gotta">*</span>号牌种类</td>
				<td width="24%" class="body"><h:codebox list='${hpzlList}' id='hpzl' haveNull='1'/></td>
				<td width="10%" class="head"><span class="gotta">*</span>号牌号码</td>
				<td width="56%" class="body" colspan="3">
					<input type="text" name="hphm" id="hphm" maxlength="15" style="width:120" onKeyUp="hphmToUp()" onblur="hphmToUpAll()" onFocus="setPos()">
					<img id="hphmPic" src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()" align="absmiddle" style="cursor:hand">
					<div id="hphm_div" style="position:absolute;display:none;" onmouseover="setIsHphmDivMouseOn(true)" onmouseout="setIsHphmDivMouseOn(false)"></div>
				</td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>过车时间</td>
				<td class="body" colspan="3">
					<h:datebox id="kssj" name="kssj" showType="2" />
					至
					<h:datebox id="jssj" name="jssj" showType="2" />
				</td>
				<td class="head"><span class="gotta">*</span>查询原因</td>
				<td class="body">
					<h:codebox id="purpose" name="purpose" list="${cxyyList}" haveNull="1"/>
				</td>
			</tr>
			<tr>
				<td class="head">结果展现方式</td>
				<td class="body" colspan="3">
					<input type="radio" id="showStyle" name="showStyle" value="1" checked onclick="doShowStyleChange()">列表&nbsp;
					<input type="radio" id="showStyle" name="showStyle" value="2" onclick="doShowStyleChange()">缩略图&nbsp;
				</td>
				<td class="submit" colspan="2">
					<input type="button" class="button_query" value="查询" onclick="query_cmd()">
					<input type="button" class="button_save" value="反馈" onclick="handled_cmd()">
					<input type="button" class="button_close" value="关闭" onclick="javascript:window.close()"> 
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="hphm_div" style="position:absolute;display:none;" onmouseover="setIsHphmDivMouseOn(true)" onmouseout="setIsHphmDivMouseOn(false)"></div>
	<div class="s1" style="height:8px;"></div>
	<div id="thdDiv" style="display:none;width:100%;text-align:left;font-size:12px;">
		<div id="thdQueryStatDiv" style="height:24px;line-height:24px;float:left;"><img src="rmjs/zoom/ajax-loader.gif">正在查询，请稍后...</div>
		<div id="thdQueryResultDiv" style="height:24px;line-height:24px;float:left;margin-left:10px;"></div>
		<input type="button" class="button_other" value="刷新数据" onclick="gotoPage(1)" style="float:left;">
	</div>
	<div class="s1" style="height:2px;"></div>
	<div id="data" style="text-align:center;font-size:12px;"></div>
	<input type="hidden" id="toEnd">
</div>
</body>
</html>