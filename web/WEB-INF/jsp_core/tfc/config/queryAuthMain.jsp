<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>�켣��ѯȨ�ޱ���</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
		<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="rmjs/jquery.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmtheme/main/main.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="frmjs/common_func.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmjs/tools.js"
			type="text/javascript"></script>
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

    $("#bazt1").attr("checked",true); 
<c:if test="${command!=null}">
	$("[limit]").limit();
	$("#glbm").val("<c:out value='${command.glbm}'/>");
	$("#jlzt").val("<c:out value='${command.jlzt}'/>");
	<c:if test="${command.bkxj=='1'}">
	   $("#bkxj").attr("checked",true); 
	</c:if>
	
	<c:if test="${command.jz=='17'}">
	   $("#jz1").attr("checked",true); 
	</c:if>
	
	<c:if test="${command.jz=='99'}">
	   $("#jz2").attr("checked",true); 
	</c:if>
	
	<c:if test="${command.bazt=='0'}">
	   $("#bazt1").attr("checked",true); 
	   $("#bazt2").attr("checked",false); 
	</c:if>
	<c:if test="${command.bazt=='1'}">
	   $("#bazt1").attr("checked",false); 
	   $("#bazt2").attr("checked",true); 
	</c:if>
	<c:if test="${command.bazt=='0,1'}">
	   $("#bazt1").attr("checked",true); 
	   $("#bazt2").attr("checked",true); 
	</c:if>
	
	<c:if test="${command.jlzt=='0'}">
	   $("#jlzt1").attr("checked",true); 
	   $("#jlzt2").attr("checked",false); 
	</c:if>
	<c:if test="${command.jlzt=='1'}">
	   $("#jlzt1").attr("checked",false); 
	   $("#jlzt2").attr("checked",true); 
	</c:if>
	<c:if test="${command.jlzt=='0,1'}">
	   $("#jlzt1").attr("checked",true); 
	   $("#jlzt2").attr("checked",true); 
	</c:if>
</c:if>
});
function query_cmd(){
	if(!doChecking()) return;
	
	var baztstr = "";
	if($("#bazt1").is(":checked"))
	   baztstr = "0";
	if($("#bazt2").is(":checked"))
	   baztstr = "1";
	if($("#bazt1").is(":checked") && $("#bazt2").is(":checked"))
	   baztstr = "0,1";
	$("#bazt").val(baztstr);
	
	var jlztstr = "";
	if($("#jlzt1").is(":checked"))
	   jlztstr = "0";
	if($("#jlzt2").is(":checked"))
	   jlztstr = "1";
	if($("#jlzt1").is(":checked") && $("#jlzt2").is(":checked"))
	   jlztstr = "0,1";
	$("#jlzt").val(jlztstr);
	
	var bkxjstr = "";
	if($("#bkxj").is(":checked"))
	   bkxjstr = "&bkxj=1";
    $("#myform").attr("action","<c:url value='/queryAuth.tfc?method=list&tj=1'/>"+bkxjstr);
	closes();
	$("#tjbj").val("1");
	$("#myform").submit();
}

function doChecking(){
    if(!$("#bazt1").is(":checked") && !$("#bazt2").is(":checked")){
       alert("û��ѡ�񱸰�״̬��");
       return false;
    }

	return true;
}

function returns(data) { 
	if(data["code"]=="1"){
		alert("�ύ�ɹ���");
		var vtj = data["message"];
		if(vtj=="0"){
	        query_untj();
	    }
	    else
	        query_tj();
	} else{
		alert(decodeURIComponent(data["message"]));
		opens();    
	}
}

function editauth(yhdh,jz) {
	
	openwin("/rmweb/queryAuth.tfc?method=editAuth&yhdh="+yhdh+"&jz="+jz, "detailOpen");
	
}

//-->
</script>
	</head>
	<body onUnload="closesubwin()">
		<div id="panel" style="display: none">
			<div id="paneltitle">
				�켣��ѯȨ�ޱ���
			</div>
			<div id="query">
				<div id="querytitle">
					��ѯ����
				</div>
				<form action="" method="post" name="myform" id="myform">
					<input type="hidden" name="bazt" id="bazt" value="" />
					<input type="hidden" name="jlzt" id="jlzt" value="" />
					<input type="hidden" name="sqlx2" id="sqlx2" value="" />
					<input type="hidden" name="sqyh" id="sqyh" value="" />

					<table border="0" cellspacing="1" cellpadding="0" class="query"
						id="pane11">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="24%">
						<tr>
							<td class="head">
								�û�����
							</td>
							<td class="body">
								<input type="radio" checked name="jz" id="jz1" value="17">
								����&nbsp;
								<input type="radio" name="jz" id="jz2" value="99">
								����
							</td>
							<td class="head">
								������
							</td>
							<td colspan="1" class="body">
								<select id="glbm" name="glbm" style="width: 100%;">
									<c:forEach var="current" items="${xjbmList}">
										<option value="${current.glbm}">
											<c:out value="${current.bmmc}" />
										</option>
									</c:forEach>
								</select>
							</td>
							<td colspan="1" class="body">
								<input type="checkbox" name="bkxj" id="bkxj" value="">
								�����¼�
							</td>
						</tr>
						<tr>
							<td class="head">
								����״̬
							</td>
							<td class="body">
								<input type="checkbox" name="bazt1" id="bazt1" value="0">
								δ����&nbsp;
								<input type="checkbox" name="bazt2" id="bazt2" value="1">
								�ѱ���
							</td>
							<td class="head">
								����״̬
							</td>
							<td class="body" colspan="2">
								<input type="checkbox" name="jlzt1" id="jlzt1" value="0">
								δ����&nbsp;
								<input type="checkbox" name="jlzt2" id="jlzt2" value="1">
								������
							</td>
						</tr>
						<tr>
							<td colspan="5" class="submit">
								<input type="button" value="��ѯ" class="button_query"
									onClick="query_cmd()">
								&nbsp;
								<input type="button" value="�ر�" class="button_close"
									onClick="javascript:window.close();">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult"></div>
			<c:if test="${queryList!=null}">
				<div id="result">
					<div id="resulttitle">
						��ѯ����
					</div>
					<form action="" method="post" name="sqform" id="sqform">
						<table border="0" cellspacing="1" cellpadding="0" class="list">
							<col width="8%">
							<col width="8%">
							<col width="16%">
							<col width="10%">
							<col width="8%">
							<col width="8%">
							<col width="16%">
							<col width="16%">
							<col width="8%">
							<tr class="head">
								<td>
									�û�����
								</td>
								<td>
									����
								</td>
								<td>
									������
								</td>
								<td>
									����ʱ��
								</td>
								<td>
									����״̬
								</td>
								<td>
									����״̬
								</td>
								<td>
									��ȨȨ��
								</td>
								<td>
									����Ȩ��
								</td>
								<td>
									����
								</td>
							</tr>
							<c:forEach items="${queryList}" var="current">
								<input type="hidden" name="xh<c:out value='${current.yhdh}'/>"
									id="xh<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.xh}'/>" />
								<input type="hidden" name="xm<c:out value='${current.yhdh}'/>"
									id="xm<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.xm}'/>" />
								<input type="hidden"
									name="sfzmhm<c:out value='${current.yhdh}'/>"
									id="sfzmhm<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.sfzmhm}'/>" />
								<input type="hidden" name="rybh<c:out value='${current.yhdh}'/>"
									id="rybh<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.rybh}'/>" />
								<input type="hidden" name="glbm<c:out value='${current.yhdh}'/>"
									id="glbm<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.glbm}'/>" />
								<input type="hidden" name="fzjg<c:out value='${current.yhdh}'/>"
									id="fzjg<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.fzjg}'/>" />
								<input type="hidden" name="sqsj<c:out value='${current.yhdh}'/>"
									id="sqsj<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.sqsj}'/>" />
								<input type="hidden"
									name="sqjbr<c:out value='${current.yhdh}'/>"
									id="sqjbr<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.sqjbr}'/>" />
								<input type="hidden" name="jyw<c:out value='${current.yhdh}'/>"
									id="jyw<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.jyw}'/>" />
								<input type="hidden" name="gxsj<c:out value='${current.yhdh}'/>"
									id="gxsj<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.gxsj}'/>" />
								<input type="hidden" name="bz<c:out value='${current.yhdh}'/>"
									id="bz<c:out value='${current.yhdh}'/>"
									value="<c:out value='${current.bz}'/>" />
								<tr class="out" onMouseOver="this.className='over'"
									onMouseOut="this.className='out'">
									<td align="left">
										<c:out value="${current.yhdh}" />
									</td>
									<td>
										<c:out value="${current.xm}" />
									</td>
									<td>
										<c:out value="${current.bmmc}" />
									</td>
									<td limit="date">
										<c:out value="${current.sqsj}" />
									</td>
									<td>
										<c:if test="${current.bazt=='0' || current.bazt==''}">
											<c:out value="δ����" />
										</c:if>
										<c:if test="${current.bazt=='1'}">
											<c:out value="�ѱ���" />
										</c:if>
									</td>
									<td>
										<c:if test="${current.jlzt=='0'}">
											<c:out value="δ����" />
										</c:if>
										<c:if test="${current.jlzt=='1'}">
											<c:out value="������" />
										</c:if>
									</td>
									<td>
										<c:out value="${current.sqqxmc}" />
									</td>
									<td>
										<c:out value="${current.spqxmc}" />
									</td>
									<td>
									    <c:if test="${current.bapply}">
										<input type="button" value="����" class="button_other"
											onClick="editauth('<c:out value='${current.yhdh}'/>','<c:out value='${current.jz}'/>');">
									    </c:if>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="9" class="page">
									<c:out value="${controller.clientScript}" escapeXml="false" />
									<c:out value="${controller.clientPageCtrlDesc}"
										escapeXml="false" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</c:if>
		</div>
	</body>
</html>