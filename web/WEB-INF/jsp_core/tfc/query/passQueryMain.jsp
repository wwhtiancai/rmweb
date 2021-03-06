<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v ="urn:schemas-microsoft-com:vml">
<head>
<title>机动车轨迹精确查询</title>
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
<%-- 
<script language="javascript" src="http://<c:out value='${pgisdz}'/>/PGIS_S_TileMap/js/EzMapAPI.js"></script>
--%>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>

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

.m1{
	background-color:#A4BED4;
	width:100%;
}
.m2{
	background-color:F4F7F9;
	text-align:center;
}
.m3{
	background-color:F4F7F9;
	text-align:left;
}


</style>
<script type="text/javascript">
<!--
var queryModel = null;
var passData = null;
var curPage;
var totalCount;
var pageCount;
var curShowStyle;
var passPicWidth;
var passPicHeight;

var detailWin = null;
var handledWin = null;

var queryStatTimer;

var _MapApp=null;
//var _table='<c:out value='${pgiskkb}'/>'; 
var _imgSTAT= "images/dd-start.png";
var _imgEND= "images/dd-end.png";
var _imgMID= "images/dd-mid.png";
var _imgURL= null;
var CPHM = ""; //查询车牌号码
var markerArray= new Array();
var _marker = null;
var overlayArray = new Array();
var show = true;
var initMBR ;
var sLevel;

$(window).bind("load",function(){
	$.datepicker.setDefaults($.datepicker.regional['']);
	$(".jscal").each(function () {
		eval($(this).html());
	});
});


$(document).ready(function(){
  	$("#kssj").val(getLastWeek()+" 00:00");
	$("#jssj").val(getNow(true).substring(0,16));
	curShowStyle = $("input[name=showStyle][checked=true]").val();
	initSize();
	changepanel('<c:out value="${firstTab}"/>');
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


function handled_cmd(){
	openwin("tfcQuery.tfc?method=handleDialog","queryHandled","");
	
}

function setQueryingHtml(){
	$("#data").empty();
	queryingHtml = '<br><br><img alt="" src="images/running.gif"><br>正在查询，请耐心等候...';
	$("#data").html(queryingHtml);
}

function checkSpecialHphm() {
	//$("#myform").attr("action",);
	$.ajax({
		url:"<c:url value='/tfcQuery.tfc?method=checkSpecialVeh'/>",
		dataType:"json",
		async:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success: function(data){
			// alert(data['code']);
			if(data['code']>0) {
				alert("无权查询该车辆轨迹！");
				return false;
			} else {
				return true;
			}
		}
	});
}




function doChecking(){
	if(!checkNull($("#hphm"),"号牌号码")) return false;
	if(!checkHPHM($("#hphm"),"号牌号码")) return false;
	if(!checkNull($("#hpzl"),"号牌种类")) return false;
	if(!checkNull($("#purpose"),"查询原因")) return false;
	if(!checkNull($("#kssj"),"开始时间")) return false;
	if(!checkNull($("#jssj"),"结束时间")) return false;
	if(!compareDate($("#kssj").val(),$("#jssj").val(),"开始时间","结束时间")) return false;
	if(!checkNull($("#yzm"),"验证码")) return false;
	if(queryModel=='l113') {
		var cxfwAry = $("input[name=fzjg][checked=true]");
		if(cxfwAry.length < 1){
			alert("请至少选择一个城市（地区）！");
			return false;
		}
	}
	return true;
}

function query_cmd(){
	if(!doChecking()) {
		return;
	}
	// $("#thdQueryResultDiv").html('');
	closes();
	setQueryingHtml();
	$("#pageCMD").attr("disabled", true);
	if(queryModel=='l111') {
		$("#cxfw").val("1");
		curPage = 1;
		$("#myform").attr("action","<c:url value='/tfcQuery.tfc?method=lister&page='/>" + curPage+ "&model=" + queryModel);
		$("#myform").ajaxSubmit({
			dataType:"json",
			async:true,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			success:localReturns
		});
	}
	if(queryModel=='l112') {
		$("#cxfw").val("2");
		curPage = 1;
		$("#myform").attr("action","<c:url value='/tfcQuery.tfc?method=provinceList&page='/>" + curPage+ "&model=" + queryModel);
		$("#myform").ajaxSubmit({
			dataType:"json",
			async:true,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			success:localReturns
		});
	}
	if(queryModel=='l113') {
		$("#thdDiv").css("display", "none");
		$("#cxfw").val("3");
		curPage = 1;
		$("#myform").attr("action","<c:url value='/tfcQuery.tfc?method=roamingList&page='/>"+curPage+ "&model=" + queryModel);
		$("#myform").ajaxSubmit({
			dataType:"json",
			async:true,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			success:localReturns
		});
	}
}

function pageReturn(data){
	var code = data['code'];
	var msg = data['msg'];
	totalCount = data['totalCount'];
	pageCount = data['pageCount'];
	if(code == -1){
		emptyDataDiv();
		alert(decodeURIComponent(msg));
		//return;
	}else if(code == 0){
		emptyDataDiv();
	}else if(code == 1){
		passData = msg;
		reloadData();
		if(queryModel=='l113') {
			$("#thdDiv").css("display", "block");
			reloadQueryStat();		
		}else{
			$("#thdDiv").css("display", "none");
		}
	}
	opens();
}

function localReturns(data){
	var code = data['code'];
	var msg = data['msg'];
	totalCount = data['totalCount'];
	pageCount = data['pageCount'];
	if(code == -1){
		emptyDataDiv();
		alert(decodeURIComponent(msg));
		//return;
	}else if(code == 0){
		emptyDataDiv();
	}else if(code == 1){
		passData = msg;
		reloadData();
		if(queryModel=='l113') {
			$("#thdDiv").css("display", "block");
			reloadQueryStat();		
		}else{
			$("#thdDiv").css("display", "none");
		}
	}
	document.ifrmaeecsideveh.reloadImage();
	opens();
	
}

function reloadQueryStat(){
	curUrl = "<c:url value='tfcQuery.tfc?method=getQueryStatus'/>";
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
					ch +=""+ decodeURI(item['fzjg']) + '：'+"<font color=red><a title='"+decodeURI(item['errmsg'])+"'>查询时发生异常</a></font>；";
				}
				
				//ch += decodeURI(item['fzjg']) + '：' + decodeURI(item['cxjg']) + '；';
			});
			$("#thdQueryResultDiv").html(ch);
		}
	});
}




function showdetail(gcxh, pageNum, listNum){
	// detailUrl = "<c:url value='tfcQuery.tfc?method=detail&gcxh='/>" + gcxh + "&page=" + pageNum + "&idx=" + listNum;
	detailUrl = "<c:url value='tfcQuery.tfc?method=detail&page='/>" + pageNum + "&idx=" + listNum;
	if(queryModel=='l112') {
		detailUrl = detailUrl+"&model=p"	
	}
	detailWin = openwin(detailUrl, "PassDetail");
}



function changepanel(_i){
	emptyDataDiv();
	passData = null;
	<c:forEach items="${funcList}" var="current">
		$("#tagitem<c:out value='${current.gnid}'/>").attr("class", "tag_head_close");
	</c:forEach>
	$("#tagitem" + _i).attr("class", "tag_head_open");
	if(_i == 'l113') {
	  $("#row1").css("display","block");
	} else {
	  $("#row1").css("display","none");
	}
	if(_i == 'l112') {
	  $("#picRadio").css("display","none");
	} else {
	  $("#picRadio").css("display","block");
	}
	$("#thdDiv").css("display", "none");
	queryModel = _i;
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
	}else if(curShowStyle == 3){
		//PGIS显示
		getGisHtml();
	}
}

var passArray = new Array();
function getGisHtml(){
	$("#data").empty();
	var  rr = new String();
	var  Shphm = new String();
	if(passData != null&&passData!=''){
		Shphm = decodeURIComponent(passData[0]['hphm']);
		rr = "[";
		$.each(passData, function(i,item){
			if(item['kkwd'].length>0&&item['kkjd'].length>0) {
				rr += "{'KKBH':'"+item['kkbh']+"','KKJC':'"+decodeURIComponent(item['kkmc'])+"','KKJD':'"+item['kkjd']+"','KKWD':'"+item['kkwd']+"','GCSJ':'"+item['gcsj'].substring(0, 19)+"'},";
			}
		});
		if (rr.length > 1) rr = rr.substr(0,rr.length-1);
		rr += "]";
		passArray = eval("(" + rr + ")");
		curHtml = '<table width="100%" height="505" border="0" cellspacing="0" cellpadding="0"><tr><td height="31"><div style="background-image:url(./images/maptoolbar/PGIS-10.jpg);border-width:0px; width:100%;">';
		curHtml += '<div style="background-image:url(./images/maptoolbar/PGIS-10.jpg);border-width:0px; width:100%;">';
		curHtml += '<img src="./images/maptoolbar/PGIS-11.jpg" onclick="_MapApp.zoomIn();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-12.jpg" onclick="_MapApp.zoomOut();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-13.jpg" onclick="_MapApp.pan();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-14.jpg" onclick="_MapApp.fullExtent();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-15.jpg" onclick="_MapApp.changeDragMode(\'measure\');" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-16.jpg" onclick="_MapApp.measureArea();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-17.jpg" onclick="_MapApp.showOverView();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-19.jpg" onclick="window.print();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-61.jpg" onclick="kkplay();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-62.jpg" onclick="kkpause();" onmouseover="this.style.cursor=\'hand\'">';
		curHtml += '<img src="./images/maptoolbar/PGIS-63.jpg" onclick="kkstop();" onmouseover="this.style.cursor=\'hand\'">;</div></td></tr>';
		curHtml += '<tr><td><div id="mymap" style="border-style:solid; border-width:0px; width:100%;height:100%;  padding-left:4px; padding-right:4px; padding-top:1px; padding-bottom:1px"></div>';
		curHtml += '</td></tr></table>';
		$("#data").html(curHtml);	
      	onLoad(Shphm);  
	} 
}



Array.prototype.inArray = function(el){ 
for (var i=0,n=this.length;i<n;i++ ) 
if(this[i]===el)return true; 
return false; 
} 
Array.prototype.unique = function() { 
ret = []; 
for (var i = 0, n = this.length; i < n; i++) 
if (!ret.inArray(this[i])) ret.push(this[i]); 
return ret; 
}; 

function onLoad(shphm){
	alert(shphm);
	// var aa=new String();
	// aa ="{'2011-11-20 14:30':'430600100051','2011-11-20 15:35':'430600100050','2011-11-20 16:00':'430600100057','2011-11-20 16:55':'430600100096','2011-11-20 17:45':'430600100022','2011-11-20 19:30':'430600100021','2011-11-20 20:10':'430600100014','2011-11-20 20:30':'430600100007','2011-11-20 21:00':'430600100033','2011-11-20 22:40':'430600100022','2011-11-20 23:25':'430600100068','2011-11-21 00:00':'430600100041','2011-11-21 01:55':'430600100030','2011-11-21 03:25':'430600100053','2011-11-21 04:00':'430600100027','2011-11-21 04:40':'430600100026','2011-11-21 05:45':'430600100050','2011-11-21 08:25':'430600100066','2011-11-21 10:25':'430600100043','2011-11-21 13:25':'430600100070'}";
	// aa = jsonP;
	//_jsonP = eval('(' + jsonP + ')');
	 //alert(passArray.length);
	 CPHM = shphm;
	 passArray = passArray.reverse();
     if(typeof EzMap =="undefined"){
         window.setTimeout("onLoad()",10);
         return;
     }
 	 if(_compatIE()){
         _MapApp = new EzMap(document.getElementById("mymap"));
         //_MapApp.centerAndZoom(new Point(113.11035,29.07226),10);
        // _MapApp.registerProx ("/rmweb/servlet/com.map.service.XmlHttpProxy");
        var pOverview=new OverView();
		pOverview.width=200;
		pOverview.height=200;
	    pOverview.minLevel=_MapApp.getMaxLevel()-7;
		pOverview.maxLevel=_MapApp.getMaxLevel();
		//pOverview.minLevel=9;
		//pOverview.maxLevel=13;
		_MapApp.addOverView(pOverview);
        _MapApp.showMapControl();   
        initMBR = _MapApp.getBoundsLatLng();
     }else if(_MapApp==null){
      	var pEle=document.getElementById("mymap");
     }
 	//_MapApp.clear();
 	//markerArray.clear();
 	//timeArray.clear();
	//for(var key in _jsonP){
	//	   markerArray.unshift(_jsonP[key]);
	//	   timeArray.unshift(key);		
		   //markerArray.push(_jsonP[key]);
		   //timeArray.push(key);
	//	}
	// alert(key + "  " + _jsonP[key]);
 	//dispTollgate();	
    if (passArray.length) drawResult();
}


function drawResult(){	    
	kkArray = [];
    markerArray = [];
	var startKK = null;
	var endKK = null;
	var strPaths = "";
	for (var i=0; i<passArray.length; i++){
	    var strID = passArray[i].KKBH;
	    if (i == passArray.length-1) endKK = strID;
        if (!i) {
        	startKK = strID; 
            strPaths += passArray[i].KKJD + "," + passArray[i].KKWD;
        }
        else strPaths += "," + passArray[i].KKJD + "," + passArray[i].KKWD ;
	    kkArray.push(strID);
        //xx.push(passArray[i].KKJD);
        //yy.push(passArray[i].KKWD);             
      }  

    kkArray = kkArray.unique();
    for (var i = 0; i < kkArray.length; i++){
       var istate = 0;
       var strTitle = "";
       var uIcon = new Icon(); 
       uIcon.height = 30;
       uIcon.width = 18;
       uIcon.leftOffset = 0;
       uIcon.topOffset = 0;
 
       if(kkArray[i] == startKK)  uIcon.image = _imgSTAT;
       else if (kkArray[i] == endKK) uIcon.image = _imgEND;
       else uIcon.image = _imgMID;
       for (var j = 0; j < passArray.length; j++) {
          if (passArray[j].KKBH == kkArray[i]){
            var strPoint = new Point(passArray[j].KKJD, passArray[j].KKWD);
            var strJC = passArray[j].KKJC;
            if (istate > 10) {
              strTitle += "<tr height='24px'><td class='m2'>...</td><td class='m3'>......</td></tr>"; 
            }
            if (!istate){
            strTitle += "<tr height='24px'><td width='20%' class='m2'>"+(j+1)+ "</td><td width='80%' class='m3'>" + passArray[j].GCSJ + "</td></tr>"; 
            }
            if(istate) {strTitle += "<tr height='24px'><td class='m2'>"+(j+1)+ "</td><td class='m3'>" + passArray[j].GCSJ + "</td></tr>"; }
            istate += 1;
          }
       }
    	   
       var uTitle = new Title(strJC, 12, 1, "宋体", "black", "#EEEEE0", "white", 2);
       var uOverLay = new Marker(strPoint, uIcon, uTitle);
       uOverLay.hideTitle();
       addFeatInfo(uOverLay,strJC,strTitle);
       markerArray.push(uOverLay);	      
    }
   dispResult(strPaths,markerArray);
}

function addFeatInfo(vuOverLay,vstrJC,vstrTitle){		 
	var strMsg = "<div style='background-color:#A4BED4;width:100%;height:24px;line-height:24px;'><p>"+vstrJC+"</p></div><div style='background-color:F4F7F9;width:100%;'><table border='0' cellspacing='1' cellpadding='0' class='m1'>"+vstrTitle+"</table></div>"; 
	vuOverLay.addListener("mouseover",function(){vuOverLay.openInfoWindowHtml(strMsg);});			
}

	function closeinfow(){
		   if(_MapApp.getDragMode()=="pan"){
		   	   _MapApp.closeInfoWindow();
		   	}
		}

function dispResult(uPath,uOverlays){

var _pLine = new Polyline(uPath,"#ff0000", 5,0.7,0);
_pLine.setColor("blue");
_pLine.setDashStyle("dash");	
if(uOverlays.length){
	var strMBR= _pLine.getMBR();
	_MapApp.centerAtMBR(strMBR.minX-0.3, strMBR.minY-0.3, strMBR.maxX+0.3, strMBR.maxY+0.3);
}else {
	_MapApp.centerAtMBR(initMBR.minX, initMBR.minY, initMBR.maxX, initMBR.maxY);
}
sLevel=_MapApp.getZoomLevel();

for (var i=0; i<uOverlays.length; i++){
    _MapApp.addOverlay(uOverlays[i]);	
    uOverlays[i].hideTitle();
}		
_MapApp.addOverlay(_pLine);		     
var coords = uPath.split(",");
var pIcon=new Icon();
pIcon.image="images/car.png";
pIcon.height=32;//getMap().viewSize.height;
pIcon.width=32;//getMap().viewSize.width;
pIcon.topOffset=0;
pIcon.leftOffset=0;
_marker = new Marker(new Point(coords[0],coords[1]),pIcon,new Title(CPHM,12,0,"宋体",null,null,"red","2"));
_MapApp.addOverlay(_marker);
_marker.setPath(0,50,uPath);
_marker.hide();
_MapApp.addMapChangeListener(testMapChange);
_MapApp.addMapChangeListener(closeinfow);
}

function testMapChange(){
if (_MapApp.getZoomLevel()>sLevel+2) {

	for (var i=0; i<markerArray.length; i++){
		markerArray[i].showTitle();
	}
}
else{
	for (var i=0; i<markerArray.length; i++){
		markerArray[i].hideTitle();
	}
}
}


function kkplay(){
if(_marker!= null){
//_MapApp.centerAndZoom(_marker.getPoint(),sLevel+2);
_marker.show();
_marker.play(false);
}}

function kkstop(){
if(_marker!= null){
_marker.stop();
}}

function kkpause(){
if(_marker!= null){
_marker.pause();
}}	

function showorhideOverView(){
if(show){
	_MapApp.showOverView();
	show=false;
}else{
	_MapApp.hideOverView();
	show=true;
	}
}

function getListHtml(){
	$("#data").empty();
	curHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list">\n';
	curHtml += '<tr class="head">\n';
	curHtml += '<td width="10%">号牌种类</td>\n';
	curHtml += '<td width="10%">号牌号码</td>\n';
	curHtml += '<td width="15%">行政区划</td>\n';
	curHtml += '<td width="20%">道路名称</td>\n';
	curHtml += '<td width="20%">卡口简称</td>\n';
	curHtml += '<td width="12%">行驶方向</td>\n';
	curHtml += '<td width="13%">过车时间</td>\n';
	curHtml += '</tr>\n';
	
	$.each(passData, function(i,item){
		curHtml += '<tr class="out" style="cursor:pointer" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" ondblclick="showdetail(\'' + item['gcxh'] + '\',' + curPage + ',' + i + ')">\n';
		curHtml += '<td>' + decodeURIComponent(item['hpzlmc']) + '</td>';
		curHtml += '<td class="hpys' + item['hpys'] + '">' + decodeURIComponent(item['hphm']) + '</td>';
		curHtml += '<td>' + decodeURIComponent(item['xzqhmc']) + '</td>';
		curHtml += '<td limit="14">' + decodeURIComponent(item['dldmmc']) + '</td>';
		curHtml += '<td limit="14">' + decodeURIComponent(item['kkmc']) + '</td>';
		curHtml += '<td limit="14">' + decodeURIComponent(item['fxlxmc']) + '</td>';
		curHtml += '<td>' + item['gcsj'].substring(0, 19) + '</td>';
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
	$("#data").html(curHtml);
	$("[limit]").limit();
}

function getThumbHtml(){
	$("#data").empty();
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
		curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery"  style="cursor:pointer;width:100%;" ondblclick="showdetail(\'' + item['gcxh'] + '\',' + curPage + ',' + i + ')">' + '<img class="passPicB" style="width:' + passPicWidth + 'px;height:' + passPicHeight + 'px;" src="' + imgSrc + '"></div>';
		curH += '<div>号牌种类：' + decodeURIComponent(item['hpzlmc']) + '&nbsp;&nbsp;号牌号码：' + decodeURIComponent(item['hphm']) + '<br>';
		curH += '行政区划：' + decodeURIComponent(item['xzqhmc']) + '<br>道路名称：' + decodeURIComponent(item['dldmmc']) + '<br>';
		curH += '卡口简称：' + decodeURIComponent(item['kkmc']) + '<br>行驶方向：' + decodeURIComponent(item['fxlxmc']) + '<br>';
		curH += '过车时间：' + decodeURIComponent(item['gcsj']).substring(0,19) + '<br></div></td>';
		curCount = i;
	});
	if((curCount % 4) != 0 || curCount == 0){
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
}



function gotoPage(idx){
	closes();
	$("#pageCMD").attr("disabled", true);
	$("#data").empty();
	// curUrl = "<c:url value='tfcQuery.tfc?method=passQueryToJSP'/>&page=" + p;
	curUrl = "<c:url value='tfcQuery.tfc?method=passQueryToJSP&page='/>" + idx + "&model=" + queryModel;
	$.ajax({
		type: "GET",
		url: curUrl,
		cache:false,
		async:true,
		dataType:"json",
		success: function(data){
			curPage = idx;
			// localReturns(data);
			pageReturn(data);
		}
	});
	opens();
}

function emptyDataDiv(){
	$("#data").empty();
}

function doShowStyleChange(){
	tmpShowStyle = $("input[name=showStyle][checked=true]").val();
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
		} else if(tmpShowStyle == 3){
			// PGIS显示
			getGisHtml();
		}
	}
	curShowStyle = tmpShowStyle;
}

//-->
</script>
</head>
<body>
<div id="panel" style="display:none; width:100%">
	<div id="paneltitle">机动车轨迹精确查询</div>
<c:if test="${funcList!=null&&gnidsize>0}">
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<c:forEach items="${funcList}" var="current">
				<td class="tag_head_close" align="center" onClick="changepanel('<c:out value="${current.gnid}"/>')" id="tagitem<c:out value="${current.gnid}"/>"><c:out value="${current.mc}"/></td>
			</c:forEach>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0"  class="tag_body_table" id="tagpanel" width="100%">
		<tr>
			<td valign="middle">
				<form action="" method="post" name="myform" id="myform">
					<input type="hidden" name="byzd"  	id="byzd" 	value="0">
					<input type="hidden" name="cxfw"	id="cxfw" >
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="50%">
						<col width="10%">
						<col width="30%">
						<c:if test="${fzjgList!=null}">
							<tr id="row1" style="display:none;">
								<td class="head">漫游城市</td>
								<td class="body" colspan="3">
									<ul>
										<c:forEach items="${fzjgList}" var="current">
											<li style="float:left;width:150px;">
												<input type="checkbox" id="fzjg<c:out value='${current.fzjg}'/>" name="fzjg" value="<c:out value='${current.fzjg}'/>"><c:out value='${current.azdmc}'/>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="head"><span class="gotta">*</span>号牌号码</td>
							<td class="body" >
								<input type="text" value=""	name="hphm" id="hphm" maxlength="15" style="width:180" onKeyUp="hphmToUp()" onblur="hphmToUpAll()" onFocus="setPos()">
								<img src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()" align="absmiddle" style="cursor:hand">
								<div id="hphm_div" style="position:absolute;display:none;" onmouseover="setIsHphmDivMouseOn(true)" onmouseout="setIsHphmDivMouseOn(false)"></div>
							</td>
							<td class="head"><span class="gotta">*</span>号牌种类</td>
							<td class="body"><h:codebox list='${hpzlList}' id='hpzl' haveNull='1'/></td>
					</tr>
					<tr>
						<td class="head"><span class="gotta">*</span>过车时间</td>
						<td class="body">
							<h:datebox id="kssj" name="kssj" showType="2" />
							至
							<h:datebox id="jssj" name="jssj" showType="2" />
						</td>
						<td class="head"><span class="gotta">*</span>查询原因</td>
						<td class="body"><h:codebox list='${cxyyList}' id='purpose' haveNull='1'/></td>
						</td>
					</tr>
					<tr>
						<td class="head">结果展现方式</td>
						<td class="body">
						  <table border="0" cellspacing="1" cellpadding="0" class="text_12">
						    <tr>
						      <td > <input type="radio" id="showStyle" name="showStyle" value="1" checked onclick="doShowStyleChange()">列表&nbsp;</td>
						      <td id="picRadio"> <input type="radio" id="showStyle" name="showStyle" value="2" onclick="doShowStyleChange()">缩略图&nbsp;</td>
						      <td><c:if test="${pgisdz!=null&&pgisdz!=''}">
						      		<input type="radio" id="showStyle" name="showStyle" value="3" onclick="doShowStyleChange()">PGIS展示
						      	  </c:if>
						      </td>
						    </tr>
						  </table>
						</td>
						<td class="head"><span class="gotta">*</span>验证码</td>
		              	<td  class="body">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="50"><input name="yzm" id="yzm" type="text" maxlength="4" style="width:50;text-transform:uppercase;" value=""></td>
									<td width="5"></td>
									<td width="120"><iframe name="ifrmaeecsideveh" width="120" height="30" frameborder="0" src="tfcQuery.tfc?method=getValidImageOpen" allowTransparency="false" scrolling=no></iframe></td>
								</tr>
							</table>
			            </td>
					</tr>
		            <tr>
		           		<td class="submit" colspan="4">
							<input type="button" class="button_query" value="查询" onclick="query_cmd()">
							<input type="button" class="button_save" value="反馈" onclick="handled_cmd()">
							<input type="button" class="button_close" value="关闭" onclick="javascript:window.close()"> 
						</td>
		            </tr>   	
					</table>
				</form>
			</td>
		</tr>
	</table>
	<div class="s1" style="height:8px;"></div>
	<div id="thdDiv" style="display:none;width:100%;text-align:left;font-size:12px;">
		<div id="thdQueryStatDiv" style="height:24px;line-height:24px;"><img src="rmjs/zoom/ajax-loader.gif">正在查询，请稍后...</div>
		<div id="thdQueryResultDiv"></div>
	</div>
	<div class="s1" style="height:2px;"></div>
	<div id="data" style="text-align:center;font-size:12px;"></div>
</c:if>	
</div>
</body>
</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     