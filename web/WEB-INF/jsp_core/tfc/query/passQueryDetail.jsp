<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>������ϸ��Ϣ</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.loadthumb.js" type="text/javascript"></script>
<style>
.aa {
	font-size:12px;font-family:arial;color:blue;font-weight:normal;TEXT-DECORATION:underline;letter-spacing:normal;
}
a.aa:link {
	font-size:12px;font-family:arial;color:blue;font-weight:normal;TEXT-DECORATION:underline;letter-spacing:normal;
}
a.aa:visited {
	font-size:12px;font-family:arial;color:blue;font-weight:normal;TEXT-DECORATION:underline;letter-spacing:normal;
}
a.aa:active {
	font-size:12px;font-family:arial;color:blue;font-weight:normal;TEXT-DECORATION:underline;letter-spacing:normal;
}
a.aa:hover {
	font-size:12px;font-family:arial;color:blue;font-weight:normal;TEXT-DECORATION:underline;letter-spacing:normal;
}
</style>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	$(".jqzoom").jqueryzoom({
		xzoom:300,
		yzoom:300,
		offset:10,
		position:"buttom",
		preload:1,
		lens:1
	});
	<c:if test="${vehpassrec.tp1!=''&&vehpassrec.tp1!=null}">getPic('tp1','<c:out value="${vehpassrec.tp1}" escapeXml="false"/>');</c:if>
	<c:if test="${vehpassrec.tp2!=''&&vehpassrec.tp2!=null}">getPic('tp2','<c:out value="${vehpassrec.tp2}" escapeXml="false"/>');</c:if>
	<c:if test="${vehpassrec.tp3!=''&&vehpassrec.tp3!=null}">getPic('tp3','<c:out value="${vehpassrec.tp3}" escapeXml="false"/>');</c:if>

});
var hpzl="<c:out value='${vehpassrec.hpzl}'/>";
var hphm="<c:out value='${vehpassrec.hphm}'/>";
function doVehicle(){
	if(hpzl.length<1||hphm.length<1){
		alert("��ͨ�����ݵĺ�����Ϣ��������");
		return false;
	}
	var r=window.showModalDialog("tfcQuery.tfc?method=getVadidateImageJs&param=1","dialogWidth:100px;dialogHeight:180px;center:1;help:0;resizable:0;status:0;scroll:0;");
	
	return false;
	
	
	
	if($("#xh").val().length<1){
		getVehicle("<c:out value='${vehpassrec.hpzl}'/>","<c:out value='${vehpassrec.hphm}'/>","setVehicle");
		$("#vehpanel").slideToggle("5000",function(){
			$("#page_control").focus();
		});	
	}else{
		$("#vehpanel").slideToggle("5000",function(){
			$("#page_control").focus();
		});
	}
}

function getVehicle(hpzl,hphm,fun){
	var len=arguments.length;
	if(hpzl==null||hpzl.length<1||hphm==null||hphm.length<1||fun==null||fun.length<1){
		alert("�������ѯ��");
	}else{
		$.getJSON("tfcQuery.tfc?method=getVehicle&hpzl="+hpzl+"&hphm="+encodeURI(encodeURI(hphm)),{async:false},function(data){
			try{
				if(data["result"]=="1"){
					var json='{"xh":"'+data["xh"]+'","hphm":"'+decodeURIComponent(data["hphm"])+'","clxh":"'+decodeURIComponent(data["clxh"])+'","clpp":"'+decodeURIComponent(data["clpp"])+'","syr":"'+decodeURIComponent(data["syr"])+'","lxdh":"'+decodeURIComponent(data["lxdh"])+'","xxdz":"'+decodeURIComponent(data["xxdz"])+'","hpzl":"'+data["hpzl"]+'","hpzlmc":"'+decodeURIComponent(data["hpzlmc"])+'","cllx":"'+data["cllx"]+'","cllxmc":"'+decodeURIComponent(data["cllxmc"])+'","csys":"'+data["csys"]+'","csysmc":"'+decodeURIComponent(data["csysmc"])+'","fdjh":"'+data["fdjh"]+'","clsbdh":"'+data["clsbdh"]+'"}';
					eval(fun+"('"+json+"')");
				}else{
					alert(decodeURIComponent(data["result"]));
					eval(fun+"('"+json+"')");
				}
			}catch(e){
				alert(e);
			}
		});	
	}
}


function setVehicle(data){
	if(data==null){
		alert("����");
	}else if(data.length>16){
		var json=eval("("+data+")");
		$("#xh").val(json["xh"]);
		$("#hphm").html(json["hphm"]);
		$("#clxh").html(json["clxh"]);
		$("#syr").html(json["syr"]);
		$("#lxdh").html(json["lxdh"]);
		$("#xxdz").html(json["xxdz"]);
		$("#clpp").html(json["clpp"]);
		$("#cllx").val(json["cllx"]);
		$("#cllxmc").html(json["cllxmc"]);
		$("#csys").val(json["csys"]);
		$("#csysmc").html(json["csysmc"]);
		$("#hpzl").val(json["hpzl"]);
		$("#hpzlmc").html(json["hpzlmc"]);
	}
	$("#vehtable1").css("display","none");
	$("#vehtable2").css("display","block");
}
function getPic(tpid, tpurl){
	$("#"+tpid).loadthumb({
		"src":tpurl,
		"imgId":tpid,
		"parentId":"loadthumb"+tpid
	});
}
function doSuspinfo(){
	if(hpzl.length<1||hphm.length<1){
		alert("��ͨ�����ݵĺ�����Ϣ��������");
		return false;
	}
	if($("#bkxh").val().length<1){
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
				html+='<td>�������</td>';
				html+='<td>�������</td>';
				html+='<td>���ػ���</td>';
				html+='<td>����ʱ��</td>';
				html+='<td>����״̬</td>';
				html+='<td>����״̬</td>';
				html+='</tr>';
				for (var i in data){
					html+='<tr class="out" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" style="cursor:pointer" onDblClick="showdetail(\''+data[i]['bkxh']+'\')">';
					html+='<td>'+data[i]['bkxh']+'</td>';
					html+='<td>'+decodeURI(data[i]['bklbmc'])+'</td>';					
					html+='<td>'+decodeURI(data[i]['bkjgmc'])+'</td>';
					html+='<td>'+data[i]['bksj']+'</td>';
					html+='<td>'+decodeURI(data[i]['ywztmc'])+'</td>';
					html+='<td>'+decodeURI(data[i]['bjztmc'])+'</td>';
					html+='</tr>';
				}
				$("#susptable").html(html);
			}catch(e){
				alert(e);
			}
		});
	}
	$("#susppanel").slideToggle("5000",function(){
		$("#page_control").focus();
	});
}
function showdetail(xh){
	var win=windowopen("","suspinfo");
	$("#myform").attr("target","suspinfo");
	$("#myform").attr("action","suspquery.do?method=queryUnlimitDetail&bkxh="+xh)
	$("#myform").submit();
}
//-->
</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">������ϸ��Ϣ</div>
	<div id="block">
		<div id="blocktitle">������Ϣ</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<tr>  
					<td width="12%" class="head">�������</td>
					<td width="21%" class="body"><c:out value='${vehpassrec.gcxh}'/></td>
					<td width="12%" class="head">��������</td>
					<td width="21%" class="body"><c:out value='${vehpassrec.hpzlmc}'/></td>
					<td width="12%" class="head">���ƺ���</td>
					<td width="22%" class="body"><c:out value='${vehpassrec.hphm}'/></td>
				</tr>
				<tr>  
					<td class="head">������ɫ</td>
					<td class="body"><c:out value='${vehpassrec.hpysmc}'/></td>
					<td class="head">����ʱ��</td>
					<td class="body"><c:out value='${vehpassrec.gcsj}'/></td>
				  	<td class="head">���ʱ��</td>
				  	<td class="body"><c:out value='${vehpassrec.rksj}'/></td>
				</tr>
				<tr>
				  	<td class="head">�����ٶ�</td>
				  	<td class="body"><c:out value='${vehpassrec.clsd}'/>&nbsp;Km/h</td>
				  	<td class="head">��������</td>
					<td class="body" colspan="3"><c:out value='${vehpassrec.cllxmc}'/></td>
				</tr>
				<tr>
					<td class="head">������������</td>
					<td class="body"><c:out value='${vehpassrec.fzhpzlmc}'/></td>
					<td class="head">�������ƺ���</td>
					<td class="body"><c:out value='${vehpassrec.fzhphm}'/></td>
					<td class="head">����������ɫ</td>
					<td class="body"><c:out value='${vehpassrec.fzhpysmc}'/></td>
				</tr>				
				<tr>
					<td class="head">������ɫ</td>
					<td class="body"><c:out value='${vehpassrec.csysmc}'/></td>
					<td class="head">����Ʒ��</td>
					<td class="body"><c:out value='${vehpassrec.clpp}'/></td>
					<td class="head">��������</td>
					<td class="body"><c:out value='${vehpassrec.cwkc}'/></td>
				</tr>
				<tr>
					<td class="head">��������</td>
					<td class="body"><c:out value='${vehpassrec.kkmc}'/></td>
					<td class="head">����</td>
					<td class="body"><c:out value='${vehpassrec.fxlxmc}'/></td>
					<td class="head">������</td>
					<td class="body"><c:out value='${vehpassrec.cdh}'/></td>
				</tr>
		  </table>
	</div>
	<div id="vehpanel" style="display:none;">
	<div id="block">
		<div id="blocktitle">��������Ϣ</div>
			<input type="hidden" name="xh" id="xh" value="">
			<table border="0" cellspacing="1" cellpadding="0" class="list" id="vehtable1">
				<tr>
					<td class="head">��ѯ��...</td>
				</tr>
			</table>
			<table border="0" cellspacing="1" cellpadding="0" class="detail" id="vehtable2" style="display:none;">
				<tr>
					<td width="12%" class="head">��������</td>
					<td width="21%" class="body"><div id="hpzlmc"></div></td>
					<td width="12%" class="head">���ƺ���</td>
					<td width="21%" class="body"><div id="hphm"></div></td>
					<td width="12%" class="head">�����ͺ�</td>
					<td width="22%" class="body"><div id="clxh"></div></td>
				</tr>
				<tr>
					<td class="head">����Ʒ��</td>
					<td class="body"><div id="clpp"></div></td>
					<td class="head">��������</td>
					<td class="body"><div id="cllxmc"></div></td>
					<td class="head">������ɫ</td>
					<td class="body"><div id="csysmc"></div></td>
				</tr>
				<tr>
					<td class="head">������������</td>
					<td class="body"><div id="syr"></div></td>
					<td class="head">��ϸ��ַ</td>
					<td class="body"><div id="xxdz"></div></td>
					<td class="head">��ϵ�绰</td>
					<td class="body"><div id="lxdh"></div></td>
				</tr>
			</table>
	</div>
	</div>
	<div id="susppanel" style="display:none;">
	<div id="block">
		<div id="blocktitle">������Ϣ</div>
			<input type="hidden" name="bkxh" id="bkxh" value="">
			<table border="0" cellspacing="1" cellpadding="0" class="list" id="susptable">
				<tr>
					<td class="head">��ѯ��...</td>
				</tr>
			</table>
	</div>
	</div>
	<div id="block">
		<div id="blocktitle">����ͼƬ</div>
	  <table border="0" cellspacing="1" cellpadding="0" class="detail" align="center" style="background:white;">
			<tr>
				<td align="center">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="244"><c:if test="${vehpassrec.tp1!=''&&vehpassrec.tp1!=null}"><div class="jqzoom" onclick="openImage('tp1')" id="loadthumbtp1"><img src="" id="tp1" alt="" width="244" height="210" border="0" align="absmiddle" jqimg="<c:out value='${vehpassrec.tp1}' escapeXml="false"/>"></div></c:if></td>
							<td width="4"></td>
							<td width="244"><c:if test="${vehpassrec.tp2!=''&&vehpassrec.tp2!=null}"><div class="jqzoom" onclick="openImage('tp2')" id="loadthumbtp2"><img src="" id="tp2" alt="" width="244" height="210" border="0" align="absmiddle" jqimg="<c:out value='${vehpassrec.tp2}' escapeXml="false"/>"></div></c:if></td>
							<td width="4"></td>
							<td width="244"><c:if test="${vehpassrec.tp3!=''&&vehpassrec.tp3!=null}"><div class="jqzoom" onclick="openImage('tp3')" id="loadthumbtp3"><img src="" id="tp3" alt="" width="244" height="210" border="0" align="absmiddle" jqimg="<c:out value='${vehpassrec.tp3}' escapeXml="false"/>"></div></c:if></td>
						</tr>
					</table>
				</td>
			</tr>
	  </table>
	</div>
	<table border="0" cellspacing="1" cellpadding="0" class="detail" style="width:98%">
		<tr>  
			<td class="command">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" align="left"><input type="text" name="page_control" id="page_control" style="width:0px;"><input type="button" class="button_other" value=" ������Ϣ " onClick="doVehicle()">&nbsp;<input type="button" class="button_other" value=" ���ع��� " onClick="doSuspinfo()"></td>
						<td width="50%" align="right"><input type="button" class="button_close" value=" �ر� " onClick="window.close()"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<form action="" method="post" name="myform" id="myform"><input type="hidden" name="xh" id="xh" value=""/></form>
</div>
</body>
</html>






