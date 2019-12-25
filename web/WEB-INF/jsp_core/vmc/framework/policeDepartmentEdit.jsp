<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>公安部门管理</title>
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
		alert("保存成功！");
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}
function doChecking(){
	//object:PLS_DEPARTMENT
	//regulation:glbm(非空,等长(12))
	//regulation:jzlx(非空,等长(2))
	//regulation:bmmc(非空,超长(64))
	//regulation:bmqc(非空,超长(128))
	//regulation:yzmc(超长(128))
	//regulation:fzr(超长(32))
	//regulation:lxr(非空,超长(32))
	//regulation:lxdh(非空,超长(50),电话)
	//regulation:czhm(超长(50),电话)
	//regulation:lxdz(超长(128))
	if(!checkNull($("#glbm"),"管理部门")) return false;
	if(!checkPrecision($("#glbm"),"12","管理部门")) return false;
	if(!checkNull($("#jzlx"),"警种类型")) return false;
	if(!checkPrecision($("#jzlx"),"2","警种类型")) return false;
	if(!checkNull($("#bmmc"),"部门名称")) return false;
	if(!checkLength($("#bmmc"),"64","部门名称")) return false;
	if(!checkNull($("#bmqc"),"部门全称")) return false;
	if(!checkLength($("#bmqc"),"128","部门全称")) return false;
	if(!checkLength($("#yzmc"),"128","印章名称")) return false;
	if(!checkLength($("#fzr"),"32","负责人")) return false;
	if(!checkNull($("#lxr"),"联系人")) return false;
	if(!checkLength($("#lxr"),"32","联系人")) return false;
	if(!checkNull($("#lxdh"),"联系电话")) return false;
	if(!checkLength($("#lxdh"),"50","联系电话")) return false;
	if(!checkTel($("#lxdh"),"联系电话")) return false;
	if(!checkLength($("#czhm"),"50","传真号码")) return false;
	if(!checkTel($("#czhm"),"传真号码")) return false;
	if(!checkLength($("#lxdz"),"128","联系地址")) return false;
	return true;
}
function del(){
	if(!checkNull($("#glbm"),"管理部门")) return false;
	if(confirm("是否确认删除该管理部门？")){
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
		alert("删除成功！");
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
					<td class="head"><span class="gotta">*</span>部门代码</td>
					<td class="body" id="glbms"></td>
					<td class="head"><span class="gotta">*</span>警种</td>
					<td class="body"><h:codebox list='${jzlx}' id='jzlx' haveNull='1'/></td>
				</tr>
				<tr>
					<td class="head">部门级别</td>
					<td class="body" id="bmjbs"></td>
					<td class="head">上级管理部门</td>
					<td class="body" id="sjbms"></td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>部门名称</td>
					<td colspan="3" class="body"><input type="text" name="bmmc" id="bmmc" maxlength="32"></td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>部门全称</td>
					<td colspan="3" class="body"><input type="text" name="bmqc" id="bmqc" maxlength="64"></td>
				</tr>
				<tr>
					<td class="head">印章名称</td>
					<td colspan="3" class="body"><input type="text" name="yzmc" id="yzmc" maxlength="64"></td>
				</tr>
				<tr>
					<td class="head">负责人</td>
					<td class="body"><input type="text" name="fzr" id="fzr" maxlength="16"></td>
					<td class="head"><span class="gotta">*</span>联系人</td>
					<td class="body"><input type="text" name="lxr" id="lxr" maxlength="16"></td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>联系电话</td>
					<td class="body"><input type="text" name="lxdh" id="lxdh" maxlength="50"></td>
					<td class="head">传真号码</td>
					<td class="body"><input type="text" name="czhm" id="czhm" maxlength="50"></td>
				</tr>
				<tr>
					<td class="head">联系地址</td>
					<td colspan="3" class="body"><input type="text" name="lxdz" id="lxdz" maxlength="64"></td>
				</tr>
				<tr>
					<td colspan="4" class="command">
						<input type="button" name="newbutton" id="newbutton" onclick="addnew()" value="新增" class="button_new">
						<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
						<input type="button" name="delbutton" id="delbutton" onclick="del()" value="删除" class="button_del">
						<input type="button" name="closebutton" onclick="parent.parent.doClose()" value="关闭" class="button_close">
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>
</body>
</html>
