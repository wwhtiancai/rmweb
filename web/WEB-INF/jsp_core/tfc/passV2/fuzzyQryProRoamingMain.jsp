<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>机动车轨迹模糊查询</title>
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
	initSize();
	changepanel('<c:out value="${firstTab}"/>');
	closes();
//	getTollgate();
	opens();
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
	if($("#purpose" + queryModel).val() == ''){
		alert("请选择查询原因！");
		return false;
	}
	if(!checkNull($("#kssj" + queryModel),"开始时间")) return false;
	if(!checkNull($("#jssj" + queryModel),"结束时间")) return false;
	if(!checkNull($("#yzm"),"验证码")) return false;
	if(!compareDate($("#kssj" + queryModel).val(),$("#jssj" + queryModel).val(),"开始时间","结束时间")) return false;
	
	if(queryModel == 'l121'){
		
	}else if(queryModel == 'l122'){
		
	}else if(queryModel == 'l123'){
		var cxfwAry = $("input[name=fzjg][checked=true]");
		if(cxfwAry.length < 1){
			alert("请选择查询范围！");
			return false;
		}
	}
	
	return true;
}
function query_cmd(formID){
	if(!doChecking()){
		return;
	}
	$("#thdQueryResultDiv").html('');
	closes();
	setQueryingHtml();
	curPage = 1;
	var tmpUrl = $("#myform" + formID).attr("action") + "&page=" + curPage + "&model=" + formID;
	$("#myform" + formID).ajaxSubmit({
		url:tmpUrl,
		dataType:"json",
		async:true,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:localReturns
	});
}
function changepanel(i){
	emptyDataDiv();
	passData = null;
	<c:forEach items="${funcList}" var="current">
		$("#tagitem<c:out value='${current.gnid}'/>").attr("class", "tag_head_close");
		$("#tag_body_<c:out value='${current.gnid}'/>").css("display", "none");
		$("#myform<c:out value='${current.gnid}'/> input[name=hphm]").attr("id", "hphm<c:out value='${current.gnid}'/>");
	</c:forEach>
	$("#tagitem" + i).attr("class", "tag_head_open");
	$("#tag_body_" + i).css("display", "block");
	$("#myform" + i + " input[name=hphm]").attr("id", "hphm");
	if($("#kssj" + i).val() == ''){
		$("#kssj" + i).val(getLastWeek()+" 00:00");
	}
	if($("#jssj" + i).val() == ''){
		$("#jssj" + i).val(getNow(true).substring(0,16));
	}
	
	//document.getElementById("yzm_" + i).reloadImage();		
			
	clearTimeout(queryStatTimer);
	$("#thdDiv").css("display", "none");
	$.getJSON("<c:url value='passQuery.tfc?method=interruptUserQueryThd'/>", function(data){});
	queryModel = i;
	curShowStyle = $("input[id=showStyle" + queryModel + "][checked=true]").val();
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
function getTollgate(){
	if(queryModel != 'l121'){
		return;
	}
	var curUrl = "<c:url value='component.dev?method=getTollgate'/>"
	var glbmVal = $("#myforml121 #glbm").val();
	if(typeof glbmVal != "undefined"){
		curUrl += "&glbm=" + glbmVal;
	}
	var dldmAry = $("#myforml121 input[name=dldm][checked=true]");
	//alert(dldmVal);
	if(typeof dldmAry != "undefined" && dldmAry.length > 0){
		//alert(dldmVal.length);
		//alert(dldmVal);
		var tmpDldmVal = '';
		$.each(dldmAry, function(i, dldmInput){
			if(i != 0){
				tmpDldmVal += ',';
			}
			tmpDldmVal += dldmInput.value;
			//alert(i + ":" + dldmInput.value);
			//alert();
		});
		
		curUrl += "&dldm=" + tmpDldmVal;
	}
	var kklxVal = $("#myforml121 #kklx").val();
	if(typeof kklxVal != "undefined"){
		curUrl += "&kklx=" + kklxVal;
	}
	var ldlxVal = $("#myforml121 #ldlx").val();
	if(typeof ldlxVal != "undefined"){
		curUrl += "&dllx=" + ldlxVal;
	}
	//var curUrl = "<c:url value='component.dev?method=getTollgate&glbm='/>" + $("#glbm").val() + "&dldm=" + $("#dldm").val() + "&kklx=" + $("#kklx").val() + "&ldlx=" + $("#ldlx").val();
	$("#myforml121 #kkbhDIV").empty();
	//$("#kkbh").addOption("", "全部");
	closes();
	$.getJSON(curUrl, 
		function(data){
			if(data['code'] == 1){
				curHTML = '<ul>';
				gateData = data['msg'];
				$.each(data['msg'], function(i,item){
					var tkkbh = item.kkbh;
					var tkkjc = decodeURI(item.kkjc);
					
					curHTML += '<li style="width:49%;float:left;"><input type="checkbox" id="kkbh' + tkkbh + '" name="kkbh" value="' + tkkbh + '">' + tkkjc + '</li>';
					//$("#kkbh").addOption(item.kkbh, decodeURI(item.kkjc));
				});
				curHTML += '<ul>';
				$("#myforml121 #kkbhDIV").html(curHTML);
			}
		}		
	);
	opens();
}
function getTollgateRoad(){
	if(queryModel != 'l121'){
		return;
	}
	var curUrl = "<c:url value='component.dev?method=getTollgateRoad'/>"
	var glbmVal = $("#myforml121 #glbm").val();
	if(typeof glbmVal != "undefined"){
		curUrl += "&glbm=" + glbmVal;
	}
	var kklxVal = $("#myforml121 #kklx").val();
	if(typeof kklxVal != "undefined"){
		curUrl += "&kklx=" + kklxVal;
	}
	var ldlxVal = $("#myforml121 #ldlx").val();
	if(typeof ldlxVal != "undefined"){
		curUrl += "&dllx=" + ldlxVal;
	}
	
//	var curUrl = "<c:url value='component.dev?method=getTollgateRoad&glbm='/>" + $("#glbm").val() + "&ldlx=" + $("#ldlx").val() + "&kklx=" + $("#kklx").val();
	//alert(curUrl);
	closes();
	$("#myforml121 #dldmDIV").empty();
	//$("#dldm").addOption("", "");
	$.getJSON(curUrl, function(data){
		curH = '<ul>';
		if(data['code'] == 1){
			$.each(data['msg'], function(i,item){
				curH += '<li style="float:left;width:99%;"><input type="checkbox" id="dldm' + item.dldm + '" name="dldm" value="' + item.dldm + '" onclick="getTollgate()">' + item.dldm + ':' + decodeURI(item.dlmc) + '</li>';
			});
		}
		curH += '</ul>';
		$("#myforml121 #dldmDIV").html(curH);
	});
	opens();
}

function getTollgateAndRoad(){
	getTollgateRoad();
	getTollgate();
}

function selectAllGate(isS){
	if($("input[name=kkbh][checked=true]").length > 0){
		$("input[name=kkbh]").attr("checked", false);
	}else{
		$("input[name=kkbh]").attr("checked", true);
	}
}

function getDirect(){
	$("#fxlx").empty();
	curKkbh = $("#kkbh").val();
	
	if(curKkbh == null || curKkbh == ''){
		$("#fxlx").addOption("", "全部");
		return;
	}
	
	curUrl = "<c:url value='component.dev?method=getTollgateDirect&kkbh='/>" + curKkbh;
	$.getJSON(curUrl, 
		function(data){
			$.each(data, function(i,item){
				$("#fxlx").addOption(item.fxbh, decodeURI(item.fxmc));
			});
		}		
	);
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
		//return;
	}else if(code == 0){
		emptyDataDiv();
		alert(decodeURIComponent(msg));
	}else if(code == 1){
		passData = msg;
		reloadData();
	}
	if(queryModel == 'l122' || queryModel == 'l123'){
		$("#thdDiv").css("display", "block");
		$("#thdQueryStatDiv").html('');
		reloadQueryStat();		
	}else{
		$("#thdDiv").css("display", "none");
	}
	
	opens();
	//document.ifrmaeecsideveh.reloadImage();
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
		
		tmpFwdz = decodeURIComponent(item['fwdz']);
		
		imgSrc = '&gcxh=' + item['gcxh'] + '&tpxh=1';
		if(tmpFwdz != null && tmpFwdz != ''){
			imgSrc = tmpFwdz + '/readPassPic.tfc?method=getPassPic' + imgSrc;
		}else{
			imgSrc = '<c:url value="/readPassPic.tfc?method=getPassPic"/>' + imgSrc;
		}
		
		//alert("图片路径：" + imgSrc);
		
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
	detailUrl = "";
	if(queryModel == 'l121'){
		detailUrl = "<c:url value='passQuery.tfc?method=showPassLocalDetail&gcxh='/>" + gcxh + "&page=" + pageNum + "&idx=" + listNum;
	}else if(queryModel == 'l122'){
		detailUrl = "<c:url value='passQuery.tfc?method=showPassProvinceDetail&gcxh='/>" + gcxh + "&page=" + pageNum + "&idx=" + listNum;
	}else if(queryModel == 'l123'){
		detailUrl = "<c:url value='passQuery.tfc?method=showPassProRoamingDetail&gcxh='/>" + gcxh + "&page=" + pageNum + "&idx=" + listNum;
	}
	
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
	curUrl = '';
	if(queryModel == 'l121'){
		curUrl = "<c:url value='passQuery.tfc?method=passQueryLocalToJSP&page='/>" + idx + "&model=" + queryModel;
	}else if(queryModel == 'l122'){
		curUrl = "<c:url value='passQuery.tfc?method=passQueryProvinceToJSP&page='/>" + idx + "&model=" + queryModel;
	}else if(queryModel == 'l123'){
		curUrl = "<c:url value='passQuery.tfc?method=passQueryProRoamingToJSP&page='/>" + idx + "&model=" + queryModel;
	}
	
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
	tmpShowStyle = $("input[id=showStyle" + queryModel + "][checked=true]").val();
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

function doGateSelect(){
	//curArgs 
	var r = window.showModalDialog("component.dev?method=fwdTollgateCheckBox", curArgs, "dialogWidth:800px;dialogHeight:600px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r != "undefined"){
		$("#kkbh").val(r[0]);
		$("#kkjc").val(r[1]);
	}else{
		$("#kkbh").val('');
		$("#kkjc").val('');
	}
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
				ch += decodeURI(item['fzjg']) + '：' + decodeURI(item['cxjg']) + '；';
			});
			$("#thdQueryResultDiv").html(ch);
		}
	});
}
function selectGateOnPGIS(){
	var sFeatures="dialogHeight:600px;dialogWidth:800px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("pgis.dev?method=fwdTollgateDX&kklx="+$("#kklx").val(), gateData, sFeatures);
	if(typeof vReturnValue!= "undefined"){
		
		ckks = $("input[name=kkbh][checked=true]");
		for(i = 0; i < ckks.length; i++){
			ckks[i].checked = false;
		}
		
		kks = vReturnValue.split(",");
		if(kks.length > 0){
			for(i = 0; i < kks.length; i++){
				$("#kkbh" + kks[i]).attr("checked", true);
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
</style>
</head>
<body>
<div id="query">
	<div id="querytitle">查询条件</div>
	<form name="myform" id="myform" action="<c:url value='passqry.tfc?method=fuzzyQryProRoamingMain'/>" method="post">
		<table border="0" cellspacing="1" cellpadding="0" class="query" width="100%">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="24%">
			<tr>
				<td class="head">查询范围</td>
				<td class="body" colspan="5">
					<ul>
						<c:forEach items="${fzjgList}" var="current">
							<li style="float:left;width:150px;">
								<input type="checkbox" id="fzjg<c:out value='${current.fzjg}'/>" name="fzjg" value="<c:out value='${current.fzjg}'/>" onclick="validCxfw('<c:out value='${current.fzjg}'/>')"><c:out value='${current.azdmc}'/>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="head">卡口类型</td>
				<td class="body"><h:codebox list='${kklxList}' id='kklx' haveNull='1'/></td>
				<td class="head">道路类型</td>
				<td class="body"><h:codebox list='${ldlxList}' id='ldlx' haveNull='1'/></td>
				<td class="head"></td>
				<td class="body"></td>
			</tr>
			<tr>
				<td width="10%" class="head">号牌种类</td>
				<td width="24%" class="body"><h:codebox list='${hpzlList}' id='hpzl' haveNull='1'/></td>
				<td width="10%" class="head">号牌号码</td>
				<td width="56%" class="body" colspan="3">
					<input type="text" name="hphm" id="hphm" maxlength="15" style="width:120" onKeyUp="hphmToUp()" onblur="hphmToUpAll()" onFocus="setPos()">
					<img id="hphmPic" src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()" align="absmiddle" style="cursor:hand">
					<font color="red">(号牌号码可模糊输入，?表示单个字符，*表示多个字符)</font>
				</td>
			</tr>
			<tr>
				<td width="10%" class="head">车身颜色</td>
				<td width="24%" class="body" colspan="5">
					<ul style="width:100%;">
					<c:forEach var="current" items="${csysList}">
						<li style="float:left;width:55px;"><input type="checkbox" id="csys<c:out value='${current.dmz}'/>" name="csys" value="<c:out value="${current.dmz}"/>"><c:out value="${current.dmz}"/>:<c:out value="${current.dmsm1}"/></li>
					</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>过车时间</td>
				<td class="body" colspan="3">
					<h:datebox id="kssjl123" name="kssj" showType="2" />
					至
					<h:datebox id="jssjl123" name="jssj" showType="2" />
				</td>
				<td class="head"><span class="gotta">*</span>查询原因</td>
				<td class="body">
					<h:codebox id="purposel123" name="purpose" list="${cxyyList}" haveNull="1"/>
				</td>
			</tr>
			<tr>
				<td class="head">结果展现方式</td>
				<td class="body" colspan="3">
					<input type="radio" id="showStylel123" name="showStyle" value="1" checked onclick="doShowStyleChange()">列表&nbsp;
					<input type="radio" id="showStylel123" name="showStyle" value="2" onclick="doShowStyleChange()">缩略图&nbsp;
					<div id="dddmm"></div>
				</td>
				<td class="submit" colspan="2">
					<input type="button" class="button_query" value="查询" onclick="query_cmd('l123')">
					<input type="button" class="button_save" value="反馈" onclick="handled_cmd()">
					<input type="button" class="button_close" value="关闭" onclick="javascript:window.close()"> 
				</td>
			</tr>
		</table>
	</form>
</div>
	<div id="hphm_div" style="position:absolute;display:none;" onmouseover="setIsHphmDivMouseOn(true)" onmouseout="setIsHphmDivMouseOn(false)"></div>
	<div class="s1" style="height:8px;"></div>
	<div id="thdDiv" style="display:none;width:100%;text-align:left;font-size:12px;vertical-align:middle;">
		<div id="thdQueryStatDiv" style="height:24px;line-height:24px;float:left;"><img src="rmjs/zoom/ajax-loader.gif">正在查询，请稍后...</div>
		<div id="thdQueryResultDiv" style="height:24px;line-height:24px;float:left;margin-left:10px;"></div>
		<input type="button" class="button_other" value="刷新数据" onclick="gotoPage(1)" style="float:left;">
	</div>
	<div class="s1" style="height:2px;"></div>
	<div id="data" style="text-align:center;font-size:12px;"></div>
	<input type="hidden" id="toEnd">
</body>
</html>