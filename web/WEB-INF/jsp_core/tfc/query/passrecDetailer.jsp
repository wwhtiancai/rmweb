<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<c:if test="${queryList!=null && queryListSize!=null && queryListSize>0}">
<head>
<title>过车信息</title>
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
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	$("[limit]").limit();
<c:if test="${vehPassrec.byzd!=null&&vehPassrec.byzd=='1'}">
	pointer=19;
</c:if>	
	doRadio(<c:out value='${vehPassrec.ll}'/>);
	window.onresize=doResize;
});
function doResize(){
	if(panelpointer==2)
		doThumb();
	else if(panelpointer==3)
		setDetail();
	else if(panelpointer==4)
		setSlide();
}
var hpzl="",hphm="";
var booleanvehicle=0,booleansuspinfo=0;
var info = new Array();
var pointer=0;
var panelpointer=1;
var selectpointer=1;
var slidepointer=0;
var keydown=0;
var listsize='<c:out value='${queryListSize}'/>';
try{
	listsize=new Number(listsize);
}catch(e){
	listsize=0;
}
//0:gxch,1:hpzl,2:hpzlmc,3:hphm,4:hpysmc,5:gcsj,6:rksj,7:clsd,8:fzhphm,9:fzhpysmc,10:cllxmc,
// 11:csysmc,12:clpp,13:kkmc,14:fxlxmc,15:cdh,16:cwkc,17:tplj,18-20:tp,21:xzqhmc,22:dldmmc,23:kkbh
<c:set var="listsize" value="0"/>
<c:forEach items="${queryList}" var="current">
info[<c:out value='${listsize}'/>]=new Array("<c:out value='${current.gcxh}'/>","<c:out value='${current.hpzl}'/>","<c:out value='${current.hpzlmc}'/>","<c:out value='${current.hphm}'/>","<c:out value='${current.hpysmc}'/>","<c:out value='${current.gcsj}'/>","<c:out value='${current.rksj}'/>","<c:out value='${current.clsd}'/>","<c:out value='${current.fzhphm}'/>","<c:out value='${current.fzhpysmc}'/>","<c:out value='${current.cllxmc}'/>","<c:out value='${current.csysmc}'/>","<c:out value='${current.clpp}'/>","<c:out value='${current.kkmc}'/>","<c:out value='${current.fxlxmc}'/>","<c:out value='${current.cdh}'/>","<c:out value='${current.cwkc}' escapeXml='false'/>","<c:out value='${current.tplj}' escapeXml='false'/>","<c:out value='${current.tp1}' escapeXml='false'/>","<c:out value='${current.tp2}' escapeXml='false'/>","<c:out value='${current.tp3}' escapeXml='false'/>","<c:out value='${current.xzqhmc}'/>","<c:out value='${current.dldmmc}'/>","<c:out value='${current.kkbh}'/>");
<c:set var="listsize" value="${listsize+1}"/></c:forEach>
function doRadio(x){
	if(x==1){
		$("input[name='ll']").val('1');
		$("#llfs1").attr("checked","true");
		panelpointer=1;
		selectpointer=1;
		$("#panel1").css("display","block");
		$("#panel2").css("display","none");
		$("#panel3").css("display","none");
		$("#panel4").css("display","none");
		$("#pagepanel").css("display","block");
		doList();
	}else if(x==2){
		$("input[name='ll']").val('2');
		$("#llfs2").attr("checked","true");
		panelpointer=2;
		selectpointer=2;
		$("#panel1").css("display","none");
		$("#panel2").css("display","block");
		$("#panel3").css("display","none");
		$("#panel4").css("display","none");
		$("#pagepanel").css("display","block");
		doThumb();
	}else if(x==3){
		showdetail(pointer);
	}else if(x==4){
		$("input[name='ll']").val('4');
		$("#llfs4").attr("checked","true");
		panelpointer=4;
		$("#panel1").css("display","none");
		$("#panel2").css("display","none");
		$("#panel3").css("display","none");
		$("#panel4").css("display","block");
		$("#pagepanel").css("display","none");
		doShowMap();
	}
}

function doShowMap(){
	var  rr = new String();
	if(info.length>0){
		rr = "{";
		for(var i=0;i<info.length;i++){
			rr+="'"+info[i][23]+"':'"+info[i][3]+"':'"+info[i][5]+"',";
		}
		rr = rr.substr(0,rr.length-1);
		rr += "}";
	}
	alert(rr);	

}



function doList(){
	var html="";
	html+='<table border="0" cellspacing="1" cellpadding="0" class="list">';
	html+='<col width="10%">';
	html+='<col width="10%">';
	html+='<col width="12%">';
	html+='<col width="18%">';
	html+='<col width="25%">';
	html+='<col width="10%">';
	html+='<col width="15%">';
	html+='<tr class="head">';
	html+='<td>号牌种类</td>';
	html+='<td>号牌号码</td>';
	html+='<td>行政区划</td>';
	html+='<td>道路名称</td>';
	html+='<td>卡口简称</td>';
	html+='<td>方向</td>';
	html+='<td>过车时间</td>';
	html+='</tr>';
	if(info.length>0){
		for(var i=0;i<info.length;i++){
			html+='<tr class="out" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" style="cursor:pointer" onDblClick="showdetail('+i+')">';
			html+='<td>'+info[i][2]+'</td>';
			if(info[i][1]=='01'){
				html+='<td bgcolor="yellow"><font color="black"><B>'+info[i][3]+'</B></font></td>';
			}else if(info[i][1]=='23'){
				html+='<td bgcolor="white"><font color="black"><B>'+getsubstr(info[i][3],0,6)+'</B></font><font color="red"><B>'+getsubstr(info[i][3],6,7)+'</B></font></td>';
			}else if(info[i][1]=='02'){
				html+='<td bgcolor="blue"><font color="white"><B>'+info[i][3]+'</B></font></td>';
			}else if(info[i][1]=='06'){
				html+='<td bgcolor="black"><font color="white"><B>'+info[i][3]+'</B></font></td>';
			}else{
				html+='<td>'+info[i][3]+'</td>';
			}
			html+='<td>'+info[i][21]+'</td>';
			html+='<td>'+info[i][22]+'</td>';
			html+='<td>'+info[i][13]+'</td>';
			html+='<td>'+info[i][14]+'</td>';
			html+='<td>'+info[i][5]+'</td>';
		}
	}
	html+='</table>';
	$("#listpanel").html(html);	
}
function doThumb(){
	var w=(document.body.clientWidth-36)/4;
	var h=w-w/10;
	var html="",htm="";
	var img="";
	var c=0;
	html+='<table border="0" cellspacing="0" cellpadding="0" align="center">\r\n';
	for(var i=0;i<info.length;i++){
		c++;
		if(info[i][18].length>0)
			img=info[i][18];
		else if(info[i][19].length>0)
			img=info[i][19];
		else if(info[i][20].length>0)
			img=info[i][20];
		else
			img="";
		htm+='<td width="24%" align="center"><div class="s1" style="height:7px;"></div><div class="jqzoomPassQuery" onclick="showdetail(\''+i+'\')"><img src="'+img+'" alt="" border="0" width="'+w+'" height="'+h+'" align="absmiddle"></div><div class="xx">'+info[i][2]+'，'+info[i][3]+'<br>'+info[i][5]+'</div></td>\r\n';
		if(c%4!=0){
			htm+='<td width="10">&nbsp;</td>\r\n';
		}else{
			html+='<tr>\r\n'+htm+'</tr>\r\n';
			htm="";
		}
	}
	if(c%4<4&&c%4>0){
		htm="";
		for(var i=0;i<4;i++){
			if(i<(c%4)){
				if(info[i][18].length>0)
					img=info[i][18];
				else if(info[i][19].length>0)
					img=info[i][19];
				else if(info[i][20].length>0)
					img=info[i][20];
				else
					img="";
				htm+='<td width="24%" align="center"><div class="s1" style="height:7px;"></div><div class="jqzoom2" onclick="showdetail(\''+i+'\')"><img src="'+img+'" alt="" border="0" width="'+w+'" height="'+h+'" align="absmiddle"></div><div class="xx">'+info[i][2]+'，'+info[i][3]+'<br>'+info[i][5]+'</div></td>\r\n';
				if(i<3){
					htm+='<td width="10">&nbsp;</td>\r\n';
				}
			}else{
				htm+='<td align="center"></td>\r\n';
				if(i<3){
					htm+='<td width="10">&nbsp;</td>\r\n';
				}
			}
		}
		html+='<tr>\r\n'+htm+'</tr>\r\n';
	}
	html+='</table>';
	$("#thumbspanel").html(html);
}

function openImage(imageIdx) {
	var imgPath = "<c:url value='/pic.tfc?method=toZoomJsp&ImgAreaName='/>"+imageIdx;
	window.open(imgPath,'','');
}


function showdetail(i){
	$("#panel1").css("display","none");
	$("#panel2").css("display","none");
	$("#panel3").css("display","block");
	$("#panel4").css("display","none");
	$("#pagepanel").css("display","none");
	pointer=i;
	panelpointer=3;
	$("input[name='ll']").val('3');
	$("#llfs3").attr("checked","true");
	hpzl=info[i][1];
	hphm=info[i][3];
	controlvehicle=0;
	controlsuspinfo=0;
	$("#vehpanel").slideUp();
	$("#susppanel").slideUp();
	// $("#gcxhs").html(info[i][0]);
	$("#xhs").html('第'+$("input[name='toPage']").val()+'页，第'+(new Number(pointer)+1)+'条');
	$("#hpzlmcs").html(info[i][2]);
	$("#hphms").html(info[i][3]);
	$("#hpyss").html(info[i][4]);
	$("#gcsjs").html(info[i][5]);
	$("#rksjs").html(info[i][6]);
	$("#fzhpysmcs").html(info[i][9]);
	$("#fzhpzlmcs").html(info[i][2]);
	$("#fzhphms").html(info[i][8]);
	$("#csysmcs").html(info[i][11]);
	if(info[i][16] > 0){
		$("#cwkcs").html(info[i][16] + '&nbsp;cm');
	}else{
		if(info[i][16] == -1){
			$("#cwkcs").html('--');
		}else{
			$("#cwkcs").html(info[i][16]);
		}
	}
	if(info[i][7] > 0){
		$("#clsds").html(info[i][7] + '&nbsp;km/h');
	}else{
		if(info[i][7] == -1){
			$("#clsds").html('--');
		}else{
			$("#clsds").html(info[i][7]);
		}
	}	
	// $("#clsds").html(temp);
	$("#cllxmcs").html(info[i][10]);
	$("#csysmcs").html(info[i][11]);
	$("#clpps").html(info[i][12]);
	$("#kdmcs").html(info[i][13]);
	$("#fxmcs").html(info[i][14]);
	$("#cdbhs").html(info[i][15]);
	$("#xzqhs").html(info[i][21]);
	
	var w=(document.body.clientWidth/3)-27;
	var h=(w/4)*3;
	if(info[i][18].length>0){
		$("#tp1s").html('<div class="jqzoom" style="width:'+w+'px;height:'+h+'px;text-align:center;" onclick="openImage(\'3tp1\')" id="loadthumb3tp1"><img src="" id="3tp1" alt="点击查看大图" border="0" width="'+w+'px" height="'+h+'px" style="border:1px solid black;" align="absmiddle" jqimg="'+info[i][18]+'"></div>');
		$("#3tp1").loadthumb({"src":info[i][18],"imgId":"3tp1","parentId":"loadthumb3tp1"});
	}else{
		$("#tp1s").html('');
	}
	if(info[i][19].length>0){
		$("#tp2s").html('<div class="jqzoom" style="width:'+w+'px;height:'+h+'px;text-align:center;" onclick="openImage(\'3tp2\')" id="loadthumb3tp2"><img src="" id="3tp2" alt="点击查看大图" border="0" width="'+w+'px" height="'+h+'px" style="border:1px solid black;" align="absmiddle" jqimg="'+info[i][19]+'"></div>');
		$("#3tp2").loadthumb({"src":info[i][19],"imgId":"3tp2","parentId":"loadthumb3tp2"});
	}else{
		$("#tp2s").html('');
	}
	if(info[i][20].length>0){
		$("#tp3s").html('<div class="jqzoom" style="width:'+w+'px;height:'+h+'px;text-align:center;" onclick="openImage(\'3tp3\')" id="loadthumb3tp3"><img src="" id="3tp3" alt="点击查看大图" border="0" width="'+w+'px" height="'+h+'px" style="border:1px solid black;" align="absmiddle" jqimg="'+info[i][20]+'"></div>');
		$("#3tp3").loadthumb({"src":info[i][20],"imgId":"3tp3","parentId":"loadthumb3tp3"});
	}else{
		$("#tp3s").html('');
	}
	$(".jqzoom").jqueryzoom({
		xzoom:320,
		yzoom:320,
		offset:10,
		position:"buttom",
		preload:1,
		lens:1
	});
}
function setDetail(){
	var w=(document.body.clientWidth/3)-27;
	var h=(w/4)*3;
	if(info[pointer][18].length>0){
		$("#tp1s").html('<div class="jqzoom2" style="width:'+w+'px;height:'+h+'px;text-align:center;" onclick="openImage(\'3tp1\')" id="loadthumb3tp1"><img src="" id="3tp1" alt="点击查看大图" border="0" width="'+w+'px" height="'+h+'px" style="border:1px solid black;" align="absmiddle" jqimg="'+info[pointer][18]+'"></div>');
		$("#3tp1").loadthumb({"src":info[pointer][18],"imgId":"3tp1","parentId":"loadthumb3tp1"});
	}else{
		$("#tp1s").html('');
	}
	if(info[pointer][19].length>0){
		$("#tp2s").html('<div class="jqzoom2" style="width:'+w+'px;height:'+h+'px;text-align:center;" onclick="openImage(\'3tp2\')" id="loadthumb3tp2"><img src="" id="3tp2" alt="点击查看大图" border="0" width="'+w+'px" height="'+h+'px" style="border:1px solid black;" align="absmiddle" jqimg="'+info[pointer][19]+'"></div>');
		$("#3tp2").loadthumb({"src":info[pointer][19],"imgId":"3tp2","parentId":"loadthumb3tp2"});
	}else{
		$("#tp2s").html('');
	}
	if(info[pointer][20].length>0){
		$("#tp3s").html('<div class="jqzoom2" style="width:'+w+'px;height:'+h+'px;text-align:center;" onclick="openImage(\'3tp3\')" id="loadthumb3tp3"><img src="" id="3tp3" alt="点击查看大图" border="0" width="'+w+'px" height="'+h+'px" style="border:1px solid black;" align="absmiddle" jqimg="'+info[pointer][20]+'"></div>');
		$("#3tp3").loadthumb({"src":info[pointer][20],"imgId":"3tp3","parentId":"loadthumb3tp3"});
	}else{
		$("#tp3s").html('');
	}
}
function doSlide(){
	var h=document.body.clientHeight-63;
	var w=document.body.clientWidth-333;
	var htm="";
	var img="";
	if(info[pointer][18].length>0){
		if(img.length<1){
			img=info[pointer][18];
			slidepointer=18;
		}
		htm+='<a href="#" class="aa" onClick="setPicture(18)">图片一</a>&nbsp;&nbsp;';
	}
	if(info[pointer][19].length>0){
		if(img.length<1){
			img=info[pointer][19];
			slidepointer=19;
		}
		htm+='<a href="#" class="aa" onClick="setPicture(19)">图片二</a>&nbsp;&nbsp;';
	}
	if(info[pointer][20].length>0){
		if(img.length<1){
			img=info[pointer][20];
			slidepointer=20;
		}
		htm+='<a href="#" class="aa" onClick="setPicture(20)">图片三</a>&nbsp;&nbsp;';
	}
	$("#slidepanel").html('<div class="jqzoom2" style="width:'+w+'px;height:'+h+'px" onClick="showdetail('+pointer+')"><img src="'+img+'" alt="" border="0" width="'+w+'px" height="'+h+'px" align="absmiddle"></div>');
	$("#4tp").html(htm);
	$("#sxh").html('第'+$("input[name='toPage']").val()+'页，第'+(new Number(pointer)+1)+'条');
	$("#shpzlmc").html(info[pointer][2]);
	$("#shphm").html(info[pointer][3]);
	$("#shpysmc").html(info[pointer][4]);
	$("#scwhphm").html(info[pointer][10]);
	$("#scwhpysmc").html(info[pointer][11]);
	$("#shpyzmc").html(info[pointer][12]);
	$("#sgcsj").html(info[pointer][5]);
	if(info[pointer][7].length<1)
		$("#sclsd").html('0 Km/h');
	else
		$("#sclsd").html(info[pointer][7]+' Km/h');
	$("#skdmc").html(info[pointer][17]);
	$("#ssbmc").html(info[pointer][19]);
	$("#sfxmc").html(info[pointer][18]);
	$("#scdbh").html(info[pointer][20]);
}
function setPicture(x){
	slidepointer=x;
	setSlide();
}
function setSlide(){
	var h=document.body.clientHeight-63;
	var w=document.body.clientWidth-333;
	$("#slidepanel").html('<div class="jqzoom2" style="width:'+w+'px;height:'+h+'px" onClick="showdetail('+pointer+')"><img src="'+info[pointer][slidepointer]+'" alt="" border="0" width="'+w+'px" height="'+h+'px" align="absmiddle"></div>');
}
function doClose(){
	doRadio(selectpointer);
}
function doPreview(){
	if(pointer==0){
		try{
			var x=new Number($("input[name='toPage']").val());
			if(x==1){
				if(confirm("已到您此次查询结果列表的第一页的第一条记录，无法进行“上一条”操作！\r\n如果你仍想继续，请关闭此详细页后，在查询页中更改条件继续查询操作。\r\n是否关闭并继续其他查询？")){
					window.close();
				}
			}else{
				x--;
				$("input[name='byzd']").val("1");
				keydown=1;
				closes();
				alert(x);
				submitController(x);
			}
		}catch(e){
			alert(e);
		}
	}else{
		pointer--;
		if(panelpointer==3){
			showdetail(pointer);
		}else if(panelpointer==4){
			doSlide();
		}
	}
}
function doNext(){
	if(pointer==listsize-1){
		try{
			var x=new Number($("input[name='toPage']").val());
			var y=new Number($("input[name='totalPages']").val());
			if((x-1)==(y-1)){
				if(confirm("已到您此次查询结果列表的最后页的最后一条记录，无法进行“下一条”操作！\r\n如果你仍想继续，请关闭此详细页后，在查询页中更改条件继续查询操作。\r\n是否关闭并继续其他查询？")){
					window.close();
				}
			}else{
				x++;
				$("input[name='byzd']").val("0");
				keydown=1;
				closes();
				submitController(x);
			}
		}catch(e){
			alert(e);
		}
	}else{
		pointer++;
		if(panelpointer==3){
			showdetail(pointer);
		}else if(panelpointer==4){
			doSlide();
		}
	}
}
function doKeyDown(){
	if(keydown==0){
		if(panelpointer==3 || panelpointer==4){
			if (event.keyCode==37)
				doPreview();
			else if(event.keyCode==39)
				doNext();
		}
	}
}
function doVehicle(){
	if(hpzl.length<1||hphm.length<1){
		alert("该通行数据的号牌信息不完整！");
		return false;
	}
	if(controlvehicle==0){
		$("#hphm").html("");
		$("#clxh").html("");
		$("#syr").html("");
		$("#lxdh").html("");
		$("#xxdz").html("");
		$("#clpp").html("");
		$("#cllxmc").html("");
		$("#csysmc").html("");
		$("#hpzlmc").html("");
		getVehicle(hpzl,hphm,"setVehicle");
		controlvehicle=1;
	}
	$("#vehpanel").slideToggle("5000",function(){
		$("#page_control").focus();
	});
}
function setVehicle(data){
	if(data==null){
		alert("错误！");
	}else if(data.length>16){
		var json=eval("("+data+")");
		$("#hphm").html(json["hphm"]);
		$("#clxh").html(json["clxh"]);
		$("#syr").html(json["syr"]);
		$("#lxdh").html(json["lxdh"]);
		$("#xxdz").html(json["xxdz"]);
		$("#clpp").html(json["clpp"]);
		$("#cllxmc").html(json["cllxmc"]);
		$("#csysmc").html(json["csysmc"]);
		$("#hpzlmc").html(json["hpzlmc"]);
	}
	$("#vehtable1").css("display","none");
	$("#vehtable2").css("display","block");
}
function doSuspinfo(){
	if(hpzl.length<1||hphm.length<1){
		alert("该通行数据的号牌信息不完整！");
		return false;
	}
	if(controlsuspinfo==0){
		$("#susptable").html("");
		$.getJSON("ajax.do?method=getSuspinfo&hpzl="+hpzl+"&hphm="+encodeURI(encodeURI(hphm)),{async:false},function(data){
			var html="";
			try{
				html+='<col align="center" width="20%">';
				html+='<col align="center" width="20%">';
				html+='<col align="center" width="20%">';
				html+='<col align="center" width="20%">';
				html+='<col align="center" width="10%">';
				html+='<col align="center" width="10%">';
				html+='<tr class="head">';
				html+='<td>布控序号</td>';
				html+='<td>布控类别</td>';
				html+='<td>布控机关</td>';
				html+='<td>布控时间</td>';
				html+='<td>布控状态</td>';
				html+='<td>报警状态</td>';
				html+='</tr>';
				for (var i in data){
					html+='<tr class="out" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" style="cursor:pointer" onDblClick="doSuspinfoDeatil(\''+data[i]['bkxh']+'\')">';
					html+='<td>'+data[i]['bkxh']+'</td>';
					html+='<td>'+decodeURI(data[i]['bklbmc'])+'</td>';					
					html+='<td>'+decodeURI(data[i]['bkjgmc'])+'</td>';
					html+='<td>'+data[i]['bksj']+'</td>';
					html+='<td>'+decodeURI(data[i]['ywztmc'])+'</td>';
					html+='<td>'+decodeURI(data[i]['bjztmc'])+'</td>';
					html+='</tr>';
				}
				$("#susptable").html(html);
				controlsuspinfo=1;
			}catch(e){
				alert(e);
			}
		});
	}
	$("#susppanel").slideToggle("5000",function(){
		$("#page_control").focus();
	});
}
function doSuspinfoDeatil(xh){
	var win=windowopen("","suspinfo");
	$("#myform").attr("target","suspinfo");
	$("#myform").attr("action","suspquery.do?method=queryUnlimitDetail&bkxh="+xh)
	$("#myform").submit();
}

//-->
</script>
</head>
<body onKeyDown="doKeyDown()">
<div id="panel" style="display:none">
	<div id="paneltitle">过车信息</div>
	<div id="query">
		<div id="querytitle">操作</div>
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td width="10%" class="head">浏览方式</td>
				<td width="90%" class="body">
					<input type="radio" name="llfs" id="llfs1" value="1" checked onClick="doRadio(1)"><a href="#" onClick="doRadio(1)" class="aaa">列表形式</a>&nbsp;&nbsp;
					<input type="radio" name="llfs" id="llfs2" value="2" onClick="doRadio(2)"><a href="#" onClick="doRadio(2)" class="aaa">缩略图形式</a>&nbsp;&nbsp;
					<input type="radio" name="llfs" id="llfs3" value="3" onClick="doRadio(3)"><a href="#" onClick="doRadio(3)" class="aaa">详细页形式</a>&nbsp;&nbsp;
					<input type="radio" name="llfs" id="llfs3" value="4" onClick="doRadio(4)"><a href="#" onClick="doRadio(4)" class="aaa">PGIS展示</a>
			</tr>
		</table>
	</div>
	<div id="panel1" style="display:none;">
		<div id="listpanel"></div>
	</div>
	<div id="panel2" style="display:none;">
		<div id="thumbspanel"></div>
	</div>
	<div id="panel3" style="display:none;">
	<div id="block">
		<div id="blocktitle">车辆通行信息</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<tr>  
					<td width="12%"	class="head">号牌种类</td>
					<td width="21%" class="body" id="hpzlmcs"></td>
					<td width="12%" class="head">号牌号码</td>
					<td width="22%" class="body" id="hphms"></td>
					<td width="12%" class="head">号牌颜色</td>
					<td width="21%" class="body" id="hpyss"></td>
				</tr>
				<tr>
					<td class="head">车辆类型</td>
					<td class="body" id="cllxmcs"></td>
					<td class="head">车身颜色</td>
					<td class="body" id="csysmcs"></td>
					<td class="head">车外廓长</td>
					<td class="body" id="cwkcs"></td>
				</tr>
				<tr>
					<td class="head">车辆品牌</td>
					<td class="body" id="clpps"></td>
					<td class="head">车辆速度</td>
					<td class="body" id="clsds"></td>
					<td class="head">行政区划</td>
					<td class="body" id="xzqhs"></td>
				</tr>
				<tr>
					<td class="head">辅助号牌颜色</td>
					<td class="body" id="fzhpysmcs"></td>
					<td class="head">辅助号牌种类</td>
					<td class="body" id="fzhpzlmcs"></td>
					<td class="head">辅助号牌号码</td>
					<td class="body" id="fzhphms"></td>
				</tr>				
				<tr>  
					<td class="head">顺序号</td>
					<td class="body" id="xhs"></td>
					<td class="head">入库时间</td>
					<td class="body" id="rksjs"></td>
					<td class="head">过车时间</td>
					<td class="body" id="gcsjs"></td>
				</tr>
		  </table>
	</div>
	<div id="block">
		<div id="blocktitle">路面信息</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<tr>  
				<td width="12%" class="head">经过卡口</td>
				<td width="21%" class="body" id="kdmcs"></td>
				<td width="12%" class="head">行驶方向</td>
				<td width="22%" class="body" id="fxmcs"></td>
				<td width="12%" class="head">车道号</td>
				<td width="21%" class="body" id="cdbhs"></td>
			</tr>
		</table>
	</div>
	<div id="vehpanel" style="display:none;">
	<div id="block">
		<div id="blocktitle">机动车信息</div>
			<table border="0" cellspacing="1" cellpadding="0" class="list" id="vehtable1">
				<tr>
					<td class="head">查询中...</td>
				</tr>
			</table>
			<table border="0" cellspacing="1" cellpadding="0" class="detail" id="vehtable2" style="display:none;">
				<tr>
					<td width="12%" class="head">号牌种类</td>
					<td width="21%" class="body"><div id="hpzlmc"></div></td>
					<td width="12%" class="head">号牌号码</td>
					<td width="21%" class="body"><div id="hphm"></div></td>
					<td width="12%" class="head">车辆型号</td>
					<td width="22%" class="body"><div id="clxh"></div></td>
				</tr>
				<tr>
					<td class="head">车辆品牌</td>
					<td class="body"><div id="clpp"></div></td>
					<td class="head">车辆类型</td>
					<td class="body"><div id="cllxmc"></div></td>
					<td class="head">车身颜色</td>
					<td class="body"><div id="csysmc"></div></td>
				</tr>
				<tr>
					<td class="head">机动车所有人</td>
					<td class="body"><div id="syr"></div></td>
					<td class="head">详细地址</td>
					<td class="body"><div id="xxdz"></div></td>
					<td class="head">联系电话</td>
					<td class="body"><div id="lxdh"></div></td>
				</tr>
			</table>
	</div>
	</div>
	<div id="susppanel" style="display:none;">
	<div id="block">
		<div id="blocktitle">布控信息</div>
		<input type="hidden" name="bkxh" id="bkxh" value="">
		<table border="0" cellspacing="1" cellpadding="0" class="list" id="susptable">
			<tr>
				<td class="head">查询中...</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="block">
		<div id="blocktitle">车辆图片</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail" align="center" style="background:white;">
			<tr>
				<td align="center">
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td width="32%" id="tp1s"></td>
							<td width="2%"></td>
							<td width="32%" id="tp2s"></td>
							<td width="2%"></td>
							<td width="32%" id="tp3s"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div id="picturepanel"></div>
	</div>
	<table border="0" cellspacing="1" cellpadding="0" class="detail" style="width:98%">
		<tr>  
			<td class="command">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" align="left"><input type="text" name="page_control" id="page_control" style="width:0px;"><input type="button" class="button_other" value=" 车辆信息 " onClick="doVehicle()">&nbsp;<input type="button" class="button_other" value=" 布控关联 " onClick="doSuspinfo()"></td>
						<td width="50%" align="right"><input type="button" class="button_other" title="快捷键为方向键区的“←”" value=" 上一张 " onClick="doPreview()">&nbsp;<input type="button" class="button_other" title="快捷键为方向键区的“→”" value=" 下一张 " onClick="doNext()">&nbsp;<input type="button" class="button_close" value=" 返回 " onClick="doClose()"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>
	
	<div id="panel4"     style="display:block;border-style:solid; border-width:0px; width:100%;height:100%;  padding-left:4px; padding-right:4px; padding-top:1px; padding-bottom:1px"></div>
	<div id="pagepanel" style="display:none;">
		<table border="0" cellspacing="1" cellpadding="0" class="list">
			<tr>
				<td align="right" class="page">
				<c:out value="${controller.clientScript}" escapeXml="false"/>
				<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
			</tr>
		</table>
	</div>
</div>
<input type="hidden" name="totalPages" id="totalPages" value="<c:out value='${controller.totalPages}'/>"/>
<form action="" method="post" name="myform" id="myform"><input type="hidden" name="xh" id="xh" value=""/></form>
</body>
</c:if>
<c:if test="${queryList==null || queryListSize==null || queryListSize<1}">
<head>
<title>过车信息</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">过车信息</div>
	<table border="0" cellspacing="1" cellpadding="0" class="list">
		<col width="32%">
		<col width="10%">
		<col width="12%">
		<col width="12%">
		<col width="8%">
		<col width="10%">
		<col width="16%">
		<tr class="head">
			<td>卡口名称</td>
			<td>号牌种类</td>
			<td>号牌号码</td>
			<td>辅助号牌号码</td>
			<td>号牌颜色</td>
			<td title="单位：Km/h">车辆速度</td>
			<td>过车时间</td>
		</tr>
		<tr>
			<td colspan="7" class="command"><input type="button" class="button_close" value=" 关闭 " onClick="window.close()"></td>
		</tr>
	</table>
</div>
</body>
</c:if>

</html>
