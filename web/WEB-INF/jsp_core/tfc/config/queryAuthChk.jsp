<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�켣��ѯȨ������</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.qtip.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
<c:if test="${bean!=null}">
    $("#xh").val("<c:out value='${bean.xh}'/>");
    $("#yhdh").val("<c:out value='${bean.yhdh}'/>");
	$("#xm").val("<c:out value='${bean.xm}'/>");
	$("#bmmc").val("<c:out value='${bean.bmmc}'/>");
	$("#sfzmhm").val("<c:out value='${bean.sfzmhm}'/>");
	$("#rybh").val("<c:out value='${bean.rybh}'/>");
	$("#fzjg").val("<c:out value='${bean.fzjg}'/>");
	$("#sqsj").val("<c:out value='${bean.sqsj}'/>");
    $("#sqqx").val("<c:out value='${bean.sqqx}'/>");
    $("#spqx").val("<c:out value='${bean.spqx}'/>");
    $("#sqjbr").val("<c:out value='${bean.sqjbr}'/>");
    $("#jlzt").val("<c:out value='${bean.jlzt}'/>");
    
    <c:if test="${bean.jlzt=='1'}">
        $("#savebutton").attr("disabled",true);
    </c:if>
</c:if>
});
function save(){
	if(!doChecking()) return;
	
    $("#myform").attr("action","<c:url value='/queryAuthChk.tfc?method=saveCheck'/>");
	
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
	});
}
function returns(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("��������ɹ���");
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
		<c:if test="${bean.jlzt!='2'}">
        $("#cancelbutton").attr("disabled",true);
        </c:if>
	}
}
function cancel(){
	if(confirm("�Ƿ�ȷ��ȡ������Ȩ��")){
		$("#myform").attr("action","<c:url value='/queryAuthChk.tfc?method=cancelCheck'/>");
		closes();
		$("#myform").ajaxSubmit({
			dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
		});
	}
}

function returndeletes(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("ȡ����Ȩ�ɹ���");
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}
function doChecking(){
	return true;
}

</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">�켣��ѯȨ������</div>
	<form action="" method="post" name="myform" id="myform">
    <input type="hidden" name="xh" id="xh" value="">
    <input type="hidden" name="yhdh" id="yhdh" value="">
    <input type="hidden" name="fzjg" id="fzjg" value="">
    <input type="hidden" name="xm" id="xm" value="">
    <input type="hidden" name="bmmc" id="bmmc" value="">
    <input type="hidden" name="sfzmhm" id="sfzmhm" value="">
    <input type="hidden" name="rybh" id="rybh" value="">
    <input type="hidden" name="sqsj" id="sqsj" value="">
    <input type="hidden" name="sqqx" id="sqqx" value="">
    <input type="hidden" name="spqx" id="spqx" value="">
	<div id="block">
		<div id="blocktitle">Ȩ����Ϣ</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="20%">
			<tr>
				<td class="head">�û�����</td>
				<td colspan="1" class="body"><c:out value='${bean.yhdh}'/></td>
				<td class="head">����</td>
				<td class="body"><c:out value='${bean.xm}'/></td>
                <td class="head">������</td>
				<td class="body"><c:out value='${bean.bmmc}'/></td>
			</tr>
			<tr>
			    <td class="head">��Ա���</td>
				<td colspan="1" class="body"><c:out value='${bean.rybh}'/></td>
				<td class="head">���֤������</td>
				<td class="body"><c:out value='${bean.sfzmhm}'/></td>
				<td class="head"></td>
				<td class="body"></td>
			</tr>
			<tr>
			    <td class="head">��ȨȨ��</td>
				<td class="body"><c:out value='${bean.sqqxmc}'/></td>
			    <td class="head">���뾭����</td>
				<td class="body"><c:out value='${bean.sqjbr}'/></td>
			    <td class="head">����ʱ��</td>
				<td class="body"><c:out value='${bean.sqsj}'/></td>
			</tr>
			<tr>
				<td class="head">������Ȩ��</td>
				<td class="body"><c:out value='${bean.spqxmc}'/></td>
			    <td class="head">����״̬</td>
			    <td class="body">
			    <c:if test="${bean.jlzt=='0'}">
	              <c:out value="δ����"/>
	            </c:if>
	            <c:if test="${bean.jlzt=='1'}">
	              <c:out value="������"/>
	            </c:if>
                </td>
                <td class="head">����ʱ��</td>
				<td class="body"><c:out value='${bean.spsj}'/></td>
			</tr>
		</table>
	</div>

	
	<table border="0" cellspacing="1" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="savebutton" id="savebutton" onclick="save()" value="����" class="button_save">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

</body>
</html>
