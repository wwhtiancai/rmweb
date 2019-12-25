<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@ taglib prefix="h" uri="http://5.tmri.cn/tag/h"%>
<html>
<head>
<title>${cxmc}</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="theme/domtab/domtab.css" rel="stylesheet" type="text/css"> 
<script language="javascript" type="text/javascript"  src="theme/domtab/domtab.js"></script>
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
$(document).ready(function(){
	//$("#glbm").val("<c:out value='${currentGlbm}'/>");
});
//编辑
function editone(gislx,xtyxms,gjz,csz,mrz,csmc,cssm,sfxs,xssx,dmlb,mrz,sjgf,bz,xsxs){
	var obj = new Object();
	obj.gislx=gislx;
	obj.xtyxms=xtyxms;
	obj.gjz=gjz;
	obj.csz=csz;
	obj.mrz=mrz;
	obj.csmc=csmc;
	obj.cssm=cssm;
	obj.sfxs=sfxs;
	obj.xssx=xssx;
	obj.dmlb=dmlb;
	obj.mrz=mrz;
	obj.sjgf=sjgf;
	obj.bz=bz;
	obj.xsxs=xsxs;
	var returnVal = window.showModalDialog("<c:url value='/gispara.frm?method=editGispara'/>",
									obj,
									"dialogWidth:650px;dialogHeight:480px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof returnVal != "undefined"){
		query_cmd();
	}
}
function reload(){
	window.location.reload();
}
function query_cmd(){
	$("#myform").attr("action","<c:url value='/gispara.frm?method=queryGisparaList'/>");
	closes();
	$("#myform").submit();
}


function refresh(){	
	$("#myform").attr(
			"action",
			"<c:url value='/gisServer.gis?method=refresh'/>");
	$("#myform").ajaxSubmit({
		dataType : "json",
		async : false,
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		success : returns
	});

}

function dataExchange(){
	
	if(window.confirm("执行数据抽取，会删除现有数据是否要继续执行？")){
		$("#myform").attr(
				"action",
				"<c:url value='/gisServer.gis?method=dataExeChange'/>");
		$("#myform").ajaxSubmit({
			dataType : "json",
			async : false,
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			success : returns
		});
	}
}

function returns(data) {
	if (data["code"] == "1") {		
		alert("刷新成功！");
	} else {
		alert(decodeURIComponent(data["message"]));		
	}
	opens();
}


</script>
<script language="JavaScript" type="text/javascript">vio_title('${cxmc}')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<body onUnload="closesubwin();">
<div id="div_show"></div>
<%--
<form action="" method="post" name="myform" id="myform">
<table width="98%" border="0" cellspacing="1" cellpadding="0" class="query">
	<tr>
		<td class="head" width="10%">管理部门</td>
		<td class="body" width="23%">
			<h:managementbox list='${glbm}' id='glbm' haveNull='1'/>
		</td>
		<td class="submit" colspan="4" width="67%">
			<input type="button" value="查询" class="button_query" onClick="query_cmd()">&nbsp;
			<input type="button" value="退出" class="button_close" onClick="quit()">
		</td>	
	</tr>
</table>
</form>
 --%>
<p>
<div class="queryresult"></div>
<form action="" method="post" name="myform" id="myform">
<table width="98%" border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
	<col width="6%">
	<col width="10%">
	<col width="20%">
	<col width="20%">
	<col width="44%">
	<thead>
		<tr align="center" class="list_head">
			<td>序号</td>
			<td>关键值</td>
			<td>参数名称</td>
			<td>参数说明</td>
			<td>参数值</td>
		</tr>
	</thead>
	<c:forEach items="${queryList}" var="current" varStatus="var">
		<tr class="${var.count%2==0?'list_body_tr_1':'list_body_tr_2'}" style="cursor:pointer"
					 onDblClick="editone('${gislx}','${xtyxms}','${current.gjz}','${current.csz}'
								,'${current.mrz}','${current.csmc}','${current.cssm}'
								,'${current.sfxs}','${current.xssx}','${current.dmlb}'
								,'${current.mrz}','${current.sjgf}','${current.bz}'
								,'${current.xsxs}')">	
			<td >${current.xssx}</td>
			<td align="center">${current.gjz}</td>
			<td align="center">${current.csmc}</td>
			<td align="center">${current.cssm}</td>
			<td align="center" style="padding-left:8px;">
				<c:if test="${current.xsxs=='1'}">${current.mrz}</c:if>
				<c:if test="${current.xsxs=='2'}">${current.mrzmc}</c:if>
			</td>
		</tr>	
	</c:forEach>		
</table>
</form>
<table border="0" cellspacing="0" cellpadding="0" class="list_table" align="center">
<tr>
  <td class="list_body_out" align="right">
  <input name="dataExchange" type="button" class="button_other" value="数据抽取 " onclick="dataExchange()">
  <input name="refresh" type="button" class="button_other" value="刷新" onclick="refresh()">
  <input name="exit" type="button" class="button_close" style="cursor:hand;" value=" 退 出 " onclick="quit()">
  </td>
</tr>
</table>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>
