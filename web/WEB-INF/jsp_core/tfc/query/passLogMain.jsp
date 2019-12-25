<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�켣������־�ۺϲ�ѯ</title>
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


$(document).ready(function(){
	$("#kssj").val(getLastWeek()+" 00:00");
	$("#jssj").val(getNow(true).substring(0,16));
	<c:if test="${tpq!=null}">
		$("[limit]").limit();
		$("#kssj").val("<c:out value='${tpq.kssj}'/>");
		$("#jssj").val("<c:out value='${tpq.jssj}'/>");
		$("#cxyy").val("<c:out value='${tpq.cxyy}'/>");
		$("#jz").val("<c:out value='${tpq.jz}'/>");
		$("#cxfw").val("<c:out value='${tpq.cxfw}'/>");
		$("#sffk").val("<c:out value='${tpq.sffk}'/>");
		$("#cxlx").val("<c:out value='${tpq.cxlx}'/>");
		$("#cxrxm").val("<c:out value='${tpq.cxrxm}'/>");
	</c:if>
	
});

function doChecking(){
	if(!checkNull($("#kssj"),"��ʼʱ��")) return false;
	if(!checkNull($("#jssj"),"����ʱ��")) return false;
	if(!compareDate($("#kssj").val(),$("#jssj").val(),"��ʼʱ��","����ʱ��")) return false;
	return true;
}

function query_cmd(){
	if(!doChecking()) return;
	$("#myform").attr("action","<c:url value='/passLogQuery.tfc?method=lister'/>");
	closes();
	$("#myform").submit();
}
function showdetail(cxxh){
	openwin("<c:url value='/passLogQuery.tfc'/>?method=detail&cxxh="+cxxh, "detail");
}

//-->
</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">�켣������־�ۺϲ�ѯ</div>
	<div id="query">
		<div id="querytitle">��ѯ����</div>
		<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="24%">
			<tr>
				<td class="head">��ѯԭ��</td>
				<td class="body"><h:codebox list='${cxyyList}' id='cxyy' haveNull='1'/></td>
				<td class="head">����</td>
				<td class="body"><h:codebox list='${jzList}' id='jz' haveNull='1'/></td>
				<td class="head">��ѯ��Χ</td>
				<td class="body"><h:codebox list='${cxfwList}' id='cxfw' haveNull='1'/></td>	
			</tr>
			<tr>
				<td class="head">�Ƿ���</td>
				<td class="body"><h:codebox list='${sffkList}' id='sffk' haveNull='1' defaultVal="0" /></td>
				<td class="head">��ѯ����</td>
				<td class="body">
					<select id="cxlx" name="cxlx" style="width:100%">
						<option value=""></option>
						<option value="1" selected>1:��ȷ</option>
						<option value="2">2:ģ��</option>
					</select>
				</td>
				<td class="head">��ѯ������</td>
				<td class="body"><input type="text" id="cxrxm" name="cxrxm" maxlength="128"/></td>	
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>��ѯʱ��</td>
				<td class="body" colspan="3">
					<h:datebox id="kssj" name="kssj" showType="2" />
					��
					<h:datebox id="jssj" name="jssj" showType="2" />
				</td>
				<td class="submit"  colspan="2">
				  	<input type="button" class="button_query" value="��ѯ" onclick="query_cmd()">
					<input type="button" class="button_close" value="�ر�" onclick="javascript:window.close()"> 
				 </td>
			</tr>
		 </table>
		</form>
	</div>
	<div class="queryresult"></div>
	<c:if test="${queryList!=null}">
		<div id="result">
			<div id="resulttitle">��ѯ����</div>
			<table border="0" cellspacing="1" cellpadding="0" class="list">
				<col width="18%">
				<col width="15%">
				<col width="12%">
				<col width="20%">
				<col width="10%">
				<col width="10%">
				<col width="15%">
				<tr class="head">
					<td>��ѯ����</td>
					<td>��ѯ������</td>
					<td>����</td>
					<td>��ѯԭ��</td>
					<td>��ѯ��Χ</td>
					<td>�Ƿ���</td>
					<td>��ѯʱ��</td>
				</tr>
				<c:forEach items="${queryList}" var="current">
				<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.cxxh}'/>')">
					<td><c:out value="${current.glbm}"/></td>
					<td><c:out value="${current.cxrxm}"/></td>
					<td><c:out value="${current.jz}"/></td>
					<td><c:out value="${current.cxyymc}"/></td>
					<td><c:out value="${current.cxfwmc}"/></td>
					<td><c:out value="${current.sffk}"/></td>
					<td limit="time"><c:out value="${current.cxsj}"/></td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan="7" class="page">
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