<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�������Ź���</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
<c:if test="${bean!=null}">
	$("#glbm").val("<c:out value='${bean.glbm}'/>");
	$("#jzlx").val("<c:out value='${bean.jzlx}'/>");
	$("#bmmc").val("<c:out value='${bean.bmmc}'/>");
	$("#bmqc").val("<c:out value='${bean.bmqc}'/>");
	$("#yzmc").val("<c:out value='${bean.yzmc}'/>");
	$("#fzr").val("<c:out value='${bean.fzr}'/>");
	$("#lxr").val("<c:out value='${bean.lxr}'/>");
	$("#lxdh").val("<c:out value='${bean.lxdh}'/>");
	$("#czhm").val("<c:out value='${bean.czhm}'/>");
	$("#lxdz").val("<c:out value='${bean.lxdz}'/>");
	$("#glbms").html("<c:out value='${bean.glbm}'/>");
	$("#bmjbs").html("<c:out value='${bean.bmjbmc}'/>");
	$("#sjbms").html("<c:out value='${bean.sjbmmc}'/>");
</c:if>
});
function save(){
	if(!doChecking()) return;
	$("#myform").attr("action","<c:url value='/department.vmc'/>?method=savePolice");
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
	});
}
function returns(data) { 
	if(data["code"]=="1"){
		parent.query($("#glbm").val());
		alert("����ɹ���");
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}
function doChecking(){
	//object:PLS_DEPARTMENT
	//regulation:glbm(�ǿ�,�ȳ�(12))
	//regulation:jzlx(�ǿ�,�ȳ�(2))
	//regulation:bmmc(�ǿ�,����(64))
	//regulation:bmqc(�ǿ�,����(128))
	//regulation:yzmc(����(128))
	//regulation:fzr(����(32))
	//regulation:lxr(�ǿ�,����(32))
	//regulation:lxdh(�ǿ�,����(50),�绰)
	//regulation:czhm(����(50),�绰)
	//regulation:lxdz(����(128))
	if(!checkNull($("#glbm"),"������")) return false;
	if(!checkPrecision($("#glbm"),"12","������")) return false;
	if(!checkNull($("#jzlx"),"��������")) return false;
	if(!checkPrecision($("#jzlx"),"2","��������")) return false;
	if(!checkNull($("#bmmc"),"��������")) return false;
	if(!checkLength($("#bmmc"),"64","��������")) return false;
	if(!checkNull($("#bmqc"),"����ȫ��")) return false;
	if(!checkLength($("#bmqc"),"128","����ȫ��")) return false;
	if(!checkLength($("#yzmc"),"128","ӡ������")) return false;
	if(!checkLength($("#fzr"),"32","������")) return false;
	if(!checkNull($("#lxr"),"��ϵ��")) return false;
	if(!checkLength($("#lxr"),"32","��ϵ��")) return false;
	if(!checkNull($("#lxdh"),"��ϵ�绰")) return false;
	if(!checkLength($("#lxdh"),"50","��ϵ�绰")) return false;
	if(!checkTel($("#lxdh"),"��ϵ�绰")) return false;
	if(!checkLength($("#czhm"),"50","�������")) return false;
	if(!checkTel($("#czhm"),"�������")) return false;
	if(!checkLength($("#lxdz"),"128","��ϵ��ַ")) return false;
	return true;
}
function del(){
	if(!checkNull($("#glbm"),"������")) return false;
	if(confirm("�Ƿ�ȷ��ɾ���ù����ţ�")){
		$("#myform").attr("action","<c:url value='/department.vmc'/>?method=delPolice");
		closes();
		$("#myform").ajaxSubmit({
			dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returnss
		});
	}
}
function returnss(data) { 
	if(data["code"]=="1"){
		parent.query();
		alert("ɾ���ɹ���");
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}
function addnew(){
  location.href="<c:url value='/department.vmc'/>?method=addPolice&glbm="+$("#glbm").val();
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td>
			<form action="" method="post" name="myform" id="myform">
			<input type="hidden" name="glbm" id="glbm" value="">
			<table border="0" cellspacing="1" cellpadding="0" align="center" class="detail" style="width:98%">
				<col width="12%">
				<col width="38%">
				<col width="12%">
				<col width="38%">
				<tr>
					<td class="head"><span class="gotta">*</span>���Ŵ���</td>
					<td class="body" id="glbms"></td>
					<td class="head"><span class="gotta">*</span>����</td>
					<td class="body"><h:codebox list='${jzlx}' id='jzlx' haveNull='1'/></td>
				</tr>
				<tr>
					<td class="head">���ż���</td>
					<td class="body" id="bmjbs"></td>
					<td class="head">�ϼ�������</td>
					<td class="body" id="sjbms"></td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>��������</td>
					<td colspan="3" class="body"><input type="text" name="bmmc" id="bmmc" maxlength="32"></td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>����ȫ��</td>
					<td colspan="3" class="body"><input type="text" name="bmqc" id="bmqc" maxlength="64"></td>
				</tr>
				<tr>
					<td class="head">ӡ������</td>
					<td colspan="3" class="body"><input type="text" name="yzmc" id="yzmc" maxlength="64"></td>
				</tr>
				<tr>
					<td class="head">������</td>
					<td class="body"><input type="text" name="fzr" id="fzr" maxlength="16"></td>
					<td class="head"><span class="gotta">*</span>��ϵ��</td>
					<td class="body"><input type="text" name="lxr" id="lxr" maxlength="16"></td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>��ϵ�绰</td>
					<td class="body"><input type="text" name="lxdh" id="lxdh" maxlength="50"></td>
					<td class="head">�������</td>
					<td class="body"><input type="text" name="czhm" id="czhm" maxlength="50"></td>
				</tr>
				<tr>
					<td class="head">��ϵ��ַ</td>
					<td colspan="3" class="body"><input type="text" name="lxdz" id="lxdz" maxlength="64"></td>
				</tr>
				<tr>
					<td colspan="4" class="command">
						<input type="button" name="newbutton" id="newbutton" onclick="addnew()" value="����" class="button_new">
						<input type="button" name="savebutton" id="savebutton" onclick="save()" value="����" class="button_save">
						<input type="button" name="delbutton" id="delbutton" onclick="del()" value="ɾ��" class="button_del">
						<input type="button" name="closebutton" onclick="parent.parent.doClose()" value="�ر�" class="button_close">
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>
</body>
</html>
