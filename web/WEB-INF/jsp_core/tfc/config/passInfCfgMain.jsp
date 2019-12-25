<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>ͨ����Ϣ�ϴ�����</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript">
<!--

$(document).ready(function(){
<c:if test="${queryList!=null}">
	$("[limit]").limit();
	$("#pzms").val("<c:out value='${pass.pzms}' />");
	fChangMs(true);
	$("#dldm").val("<c:out value='${pass.dldm}' />");
	$("#kkbh").val("<c:out value='${pass.kkbh}' />");
	$("#fzjg").val("<c:out value='${pass.fzjg}' />");
</c:if>
});
function query_cmd(){
	$("#myform").attr("action","<c:url value='/passInfCfg.tfc?method=listReg'/>");
	closes();
	$("#myform").submit();
}

function create(){
	openwin("<c:url value='/passInfCfg.tfc'/>?method=newAdd&type=add", "passInfCfg");
}

function showDetail(pzbh){
	openwin("<c:url value='/passInfCfg.tfc?method=detail&pzbh='/>" + pzbh+ "&type=mod", "passInfCfg");
}

function fChangMs(f){
	var m = $("#pzms").val();
	
	if("1" == m){
		$("#divTj").html("���ڳ���");
		$("#divSr").html("<input name='fzjg' id='fzjg' value='' />");
		if(f){$("#divBt").text("֧��");}
	}else if("2" == m){
		$("#divTj").html("��·����");
		$("#divSr").html("<input name='dldm' id='dldm' value='' />");
		if(f){$("#divBt").text("��·����");}
	}else if("3" == m){
		$("#divTj").text("��������");
		$("#divSr").html("<input name='kkbh' id='kkbh' value='' />");
		if(f){$("#divBt").text("��������");}
	}else{
		$("#divTj").html("");
		$("#divSr").html("");
		if(f){$("#divBt").text("��������");}
	}
}
//-->
</script>
</head>
<body onUnload="closesubwin()">
<div id="panel" style="display:none">
	<div id="paneltitle">ͨ����Ϣ�ϴ�����</div>
	<div id="query">
		<div id="querytitle">��ѯ����</div>
		<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td width="10%" class="head">����ģʽ</td>
				<td width="20%" class="body"><select name='pzms' id='pzms' style="width:100%" onchange="fChangMs(false)">
					<option value="">ȫ��</option>
					<option value="1" selected>��֧��</option>
					<option value="2">����·</option>
					<option value="3">������</option>
				</select></td>
				<td width="10%" class="head"><div id="divTj">���ڳ���</div></td>
				<td width="20%" class="body"><div id="divSr"><input name='fzjg' id='fzjg' value='' /></div></td>
				<td class="submit">
				<input type="button" value="����" class="button_new" onClick="create()">
				&nbsp;<input type="button" value="��ѯ" class="button_query" onClick="query_cmd()">
				&nbsp;<input type="button" value="�ر�" class="button_close" onClick="javascript:window.close()"></td>
			</tr>
		</table>
		</form>
	</div>

<c:if test="${queryList!=null}">
		<div id="result" style="margin-top:6px">
		<div id="resulttitle">��ѯ����</div>
		<table border="0" cellspacing="1" cellpadding="0" class="list">
			<col width="10%">
			<col width="10%">
			<col width="40%">
			<col width="15%">
			<col width="15%">
			<col width="10%">
			<tr class="head">
				<td>���</td>
				<td>����ģʽ</td>
				<td><div id="divBt">���ڳ���</div></td>
				<td>��ʼʱ��</td>
				<td>����ʱ��</td>
				<td>�ɼ���</td>
			</tr>
			
			<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" ondblclick="showDetail('<c:out value="${current.pzbh}"/>')">
				<td align="center" ><c:out value="${current.pzbh}"/></td>
				<td align="center" ><c:out value="${current.pzms}"/></td>
				<td align="left" limit="28" style="padding-left:8px"><c:out value="${current.pzxx}"/></td>
				<td align="center" limit="date"><c:out value="${current.kssj}"/></td>
				<td align="center" limit="date"><c:out value="${current.jssj}"/></td>
				<td align="center" ><c:out value="${current.cjr}"/></td>
			</tr>
			</c:forEach>
			
			<tr>
				<td colspan="6" class="page">
				<c:out value="${controller.clientScript}" escapeXml="false"/>
				<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
			</tr>
		</table>
	</div>
</c:if>
</div>
</body>
</html>