<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>地理信息设置</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<SCRIPT language="javascript" src="http://<c:out value='${GISDTFWDZ1}'/>/PGIS_S_TileMap/js/EzMapAPI.js"></SCRIPT>
<script type="text/javascript" reload="true" id="www">
<!--
var pmarker;
var xzqhb = '<c:out value="${pgisxzqhb}"/>';
var _table = xzqhb.substr(0,xzqhb.lastIndexOf("."));
var _field = xzqhb.substr(xzqhb.lastIndexOf(".")+1, xzqhb.length);
var AreaCode='<c:out value="${pgisxzqh}"/>';
var initMBR;
_MapApp=null;	
_LegendFunc=null;
$(document).ready(function(){
	var a=window.dialogArguments;
	if(typeof a!="undefined"&&typeof a[0]!="undefined"&&typeof a[1]!="undefined"){
		$("#jd").val(a[0]);
		$("#wd").val(a[1]);
	}
	if(typeof EzMap =="undefined"){
		window.setTimeout("onLoad()",10);
		return;
	}
	if(_compatIE()){
		_MapApp = new EzMap(document.getElementById("mymap"));
		_MapApp.registerProx("/rmweb/Proxy");
		initMBR = _MapApp.getBoundsLatLng();
		//_MapApp.showMapControl();
	}else if(_MapApp==null){
		var pEle=document.getElementById("mymap");
	}
	if($("#jd").val().length>1&&$("#wd").val().length>1){
		_MapApp.centerAndZoom(new Point(document.getElementById("jd").value,document.getElementById("wd").value),12);
		var aPoint= new Point(document.getElementById("jd").value,document.getElementById("wd").value);
		var pIcon=new Icon();
		pIcon.image="./images/flag_red.png";
		pIcon.width=32;
		pIcon.height=32;
		pIcon.leftOffset=16;
		pIcon.topOffset=-16;
		pmarker=new Marker(aPoint,pIcon);
		_MapApp.addOverlay(pmarker);
    $("#show").css("display","none");
		_MapApp.changeDragMode('drawPoint',jd,wd,callback);
	}
	else

		displayExtent();
});

function displayExtent(){
    var pQuery = new QueryObject();
    pQuery.queryType = 6;
    pQuery.tableName = _table; 
    pQuery.where = _field+" = '" + AreaCode + "' or " + _field +" = '" + AreaCode.slice(0,4)+"00'"+" or "+ _field + " = '" + AreaCode.slice(0,2)+"0000'";//根据字段修改，需增加判断条件
    var _subFields = _field + ":行政区划代码";
    pQuery.featurelimit = 500;
    pQuery.addSubFields(_subFields);
    _MapApp.query(pQuery,getAreaFeature);
}
function getAreaFeature(){
   var pLayer = getMapApp().getQueryResult()[0]; 
   var pFeats = pLayer.features;
   var iFeat = new Array();
   var jFeat = new Array();
   var kFeat = new Array();
   for(var iIndex = 0; iIndex < pFeats.length; iIndex++) {
      var pFeat = pFeats[iIndex];
      var pCode = pFeat.getFieldValue(_field);
      if (pCode==AreaCode) iFeat.push(pFeat);
      if (pCode==AreaCode.slice(0,4)+'00') jFeat.push(pFeat);
      if (pCode==AreaCode.slice(0,2)+'0000') kFeat.push(pFeat);
    }
    if (iFeat.length) changeExtends(iFeat);
    else if (jFeat.length) changeExtends(jFeat);
    else if (kFeat.length) changeExtends(kFeat);
    else{
      _MapApp.centerAtMBR(initMBR.minX, initMBR.minY, initMBR.maxX, initMBR.maxY);
      $("#show").css("display","none");
    }
}	

function changeExtends(vstrFeats){
  var pMBR;
  for (var i = 0; i< vstrFeats.length; i++){     
     var strMBR = vstrFeats[i].getMBR();     
     if (!i) pMBR = strMBR; 
     else pMBR = MBR.union(pMBR,strMBR);
  }
  _MapApp.centerAtMBR(pMBR.minX, pMBR.minY, pMBR.maxX, pMBR.maxY);
  $("#show").css("display","none");
}

function callback(){
	_MapApp.clear();
	var aPoint= new Point(document.getElementById("jd").value,document.getElementById("wd").value);
	var pIcon=new Icon();
	pIcon.image="./images/flag_red.png";
	pIcon.width=32;
	pIcon.height=32;
	pIcon.leftOffset=16;
	pIcon.topOffset=-16;
	pmarker=new Marker(aPoint,pIcon);
	_MapApp.addOverlay(pmarker);
	_MapApp.changeDragMode('drawPoint',jd,wd,callback);
}
function addFeature(){
	_MapApp.changeDragMode('drawPoint',jd,wd,callback);
}
function dosubmit(){
	var s=new Array(2);
	s[0]=$("#jd").val();
	s[1]=$("#wd").val();
	window.returnValue=s;
	window.close();
}
function doPos(){
	if($("#jd").val().length>0||$("#wd").val().length>0){
		try{
		  callback();
		}catch(e){
			alert(e);
		}
	}
}
//-->
</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
<table border="0" cellspacing="0" cellpadding="0" align="center" class="detail" style="width:100%;">
	<col width="10%">
	<col width="70%">
	<col width="20%">
	<tr>
		<td colspan="3" height="500">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0"><tr><td height="31">
<div style="background-image:url(./images/maptoolbar/PGIS-10.jpg);border-width:0px; width:100%;">
<img src="./images/maptoolbar/PGIS-11.jpg" hspace="9" onclick="_MapApp.zoomIn();" onmouseover="this.style.cursor='hand'">
<img src="./images/maptoolbar/PGIS-12.jpg" hspace="9" onclick="_MapApp.zoomOut();" onmouseover="this.style.cursor='hand'">
<img src="./images/maptoolbar/PGIS-13.jpg" hspace="9" onclick="_MapApp.pan();" onmouseover="this.style.cursor='hand'">
<img src="./images/maptoolbar/PGIS-51.jpg" hspace="9" onclick="addFeature()" onmouseover="this.style.cursor='hand'">
<img src="./images/maptoolbar/PGIS-21.jpg" hspace="9" onclick="_MapApp.clear();" onmouseover="this.style.cursor='hand'">
</div></td></tr>
<tr><td>
<div id="show" style="position:absolute;z-index:999;width:800px;height:466px;background:white;padding-top:150px;text-align:center;"><img src="images/running.gif" alt="" border="0"></div>
<div id="mymap" style="border-style:solid; border-width:0px; width:100%;height:100%;padding-left:4px; padding-right:4px; padding-top:1px; padding-bottom:1px;"></div>
</td></tr>
</table>
		</td>
	</tr>
	<tr>
		<td class="head" id="namepanel">点位经纬度</td>
		<td class="body"><input type="text" name="jd" id="jd" maxlength="17" style="width:50%"><input type="text" name="wd" id="wd" maxlength="17" style="width:50%"></td>
		<td class="body"><input type="button" id="pos" value="人工定位" onClick="doPos()"></td>
	</tr>
	<tr>
		<td class="head" id="namepanel">操作说明</td>
		<td class="body">通过【漫游】可以移动缩放地方，通过【标注】可以标记点位。</td>
		<td class="body">
		<input type="button" onclick="dosubmit()" value=" 确定 " class="button_save">
		<input type="button" onclick="window.close()" value=" 关闭 " class="button_close">
		</td>
	</tr>
</table>
</body>
</html>
