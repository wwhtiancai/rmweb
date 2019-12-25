<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>轨迹查询权限备案</title>
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
	$("#glbm").val("<c:out value='${bean.glbm}'/>");
	$("#bmmc").val("<c:out value='${bean.bmmc}'/>");
	$("#sfzmhm").val("<c:out value='${bean.sfzmhm}'/>");
	$("#rybh").val("<c:out value='${bean.rybh}'/>");
	$("#fzjg").val("<c:out value='${bean.fzjg}'/>");
	$("#sqsj").val("<c:out value='${bean.sqsj}'/>");
	$("#sqyy").val("<c:out value='${bean.sqyy}'/>");
	$("#sqqx").val("<c:out value='${bean.sqqx}'/>");
	$("#spqx").val("<c:out value='${bean.spqx}'/>");
	$("#jz").val("<c:out value='${bean.jz}'/>");
    
    $("#jlzt").val("<c:out value='${bean.jlzt}'/>");

</c:if>
});
function save(){
	if(!doChecking()) return;
	
    $("#myform").attr("action","<c:url value='/queryAuth.tfc?method=authApply'/>");
	
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
	});
}
function returns(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("保存成功！");
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
	<div id="paneltitle">轨迹查询权限备案</div>
	<form action="" method="post" name="myform" id="myform">
    <input type="hidden" name="xh" id="xh" value="">
    <input type="hidden" name="yhdh" id="yhdh" value="">
    <input type="hidden" name="fzjg" id="fzjg" value="">
    <input type="hidden" name="xm" id="xm" value="">
    <input type="hidden" name="glbm" id="glbm" value="">
    <input type="hidden" name="bmmc" id="bmmc" value="">
    <input type="hidden" name="sfzmhm" id="sfzmhm" value="">
    <input type="hidden" name="rybh" id="rybh" value="">
    <input type="hidden" name="sqsj" id="sqsj" value="">
    <input type="hidden" name="sqqx" id="sqqx" value="">
    <input type="hidden" name="spqx" id="spqx" value="">
    <input type="hidden" name="jz" id="jz" value="">
 	<div id="block">
		<div id="blocktitle">授权信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="16%">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="20%">
			<tr>
				<td class="head">用户代码</td>
				<td colspan="1" class="body"><c:out value='${bean.yhdh}'/></td>
				<td class="head">姓名</td>
				<td class="body"><c:out value='${bean.xm}'/></td>
                <td class="head">管理部门</td>
				<td class="body"><c:out value='${bean.bmmc}'/></td>
			</tr>
			<tr>
			    <td class="head">警员编号</td>
				<td colspan="1" class="body"><c:out value='${bean.rybh}'/></td>
				<td class="head">身份证明号码</td>
				<td class="body"><c:out value='${bean.sfzmhm}'/></td>
			    <td class="head">申请时间</td>
				<td class="body"><c:out value='${bean.sqsj}'/></td>
			</tr>
			<tr>
			    <td class="head">备案状态</td>
			    <td class="body">
			    <c:if test="${bean.bazt=='0' || bean.bazt==''}">
	              <c:out value="未备案"/>
	            </c:if>
	            <c:if test="${bean.bazt=='1'}">
	              <c:out value="已备案"/>
	            </c:if>
			    </td>
			    <td class="head">审批状态</td>
			    <td class="body">
			    <c:if test="${bean.jlzt=='0'}">
	              <c:out value="未审批"/>
	            </c:if>
	            <c:if test="${bean.jlzt=='1'}">
	              <c:out value="已审批"/>
	            </c:if>
                </td>
                <td class="head">审批时间</td>
				<td class="body"><c:out value='${bean.spsj}'/></td>
			</tr>
			<tr>
                <td class="head">授权权限</td>
				<td class="body"><c:out value='${bean.sqqxmc}'/></td>
				<td class="head">备案权限</td>
				<td class="body"><c:out value='${bean.spqxmc}'/></td>
				<td class="head"></td>
				<td class="body"></td>
			</tr>
			<tr>
                <td class="head">申请原因</td>
			    <td class="body" colspan="5">
			        <textarea rows="10" id="sqyy" name="sqyy" maxlength="1024" style="width:100%;"></textarea>
		        </td>
			</tr>
		</table>
	</div>

	
	<table border="0" cellspacing="1" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="savebutton" id="savebutton" onclick="save()" value="申请" class="button_save">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

</body>
</html>
