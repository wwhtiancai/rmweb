<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>${cxmc}</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>

<script type="text/javascript">
<!--
$(document).ready(function(){
	
});

function save() {
	if(!doChecking()) return; 
	$("#myform").attr("action","<c:url value='/department.vmc'/>?method=savePolice");
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
	} else{
		alert(decodeURIComponent(data["message"]));
		opens();    
	}
}

function doChecking(){
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

function del() {
	$("#myform").attr("action","<c:url value='/department.vmc'/>?method=delPolice");
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:delReturn
	});
}


function delReturn(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("删除成功！");
		window.close();
	} else{
		alert(decodeURIComponent(data["message"]));
		opens();    
	}
}

</script>
</head>
<body>
<form action="" method="post" name="myform" id="myform">
<div id="panel" style="display:none">
<div id="paneltitle">${cxmc}</div>
	<div id="block">
		<div id="blocktitle">公安部门修改</div>
		<div id="blockmargin">8</div>
		<input type="hidden"  id="glbm"   name="glbm" value="<c:out value="${bean.glbm}" />" />
		<input type="hidden"  id="jzlx"   name="jzlx" value="<c:out value="${bean.jzlx}" />" />
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="38%">
			<col width="10%">
			<col width="38%">
			<tr>
				<td class="head">同级交管部门</td>
				<td class="body"><c:out value="${bean.sjbmmc}" /></td>
				<td class="head">部门级别</td>
				<td class="body"><c:out value="${bean.bmjbmc}" /></td>
			</tr>
			<tr>
				<td class="head">部门代码</td>
				<td class="body"><c:out value="${bean.glbm}" /></td>
				<td class="head">警种</td>
				<td class="body"><c:out value="${bean.jzjbmc}" /></td>
			</tr>
			<tr>
				<td class="head">部门名称</td>
				<td class="body" colspan="3">
					<input type="text" id="bmmc" name="bmmc"  value="<c:out value="${bean.bmmc}"/>" style="width:100%">
				</td>
			</tr>
			<tr>
				<td class="head">部门全称</td>
				<td class="body" colspan="3">
					<input type="text" id="bmqc" name="bmqc"  value="<c:out value="${bean.bmqc}"/>" style="width:100%">
				</td>
			</tr>
			<tr>
				<td class="head">印章名称</td>
				<td class="body" colspan="3">
					<input type="text" id="yzmc" name="yzmc" value="<c:out value="${bean.yzmc}"/>"	 style="width:100%">
				</td>
			</tr>
			<tr>
				<td class="head">负责人</td>
				<td class="body"><input type="text" id="fzr"  name="fzr" value="<c:out value="${bean.fzr}"/>"></td>
				<td class="head">联系人</td>
				<td class="body"><input type="text" id="lxr" name="lxr"  value="<c:out value="${bean.lxr}"/>"></td>
			</tr>
			<tr>
				<td class="head">联系电话</td>
				<td class="body"><input type="text" id="lxdh" name="lxdh" value="<c:out value="${bean.lxdh}"/>"></td>
				<td class="head">传真号码</td>
				<td class="body"><input type="text" id="czhm" name="czhm" value="<c:out value="${bean.czhm}"/>"></td>
			</tr>
			<tr>
				<td class="head">联系地址</td>
				<td class="body" colspan="3">
					<input type="text" id="lxdz" name="lxdz" value="<c:out value="${bean.lxdz}"/>"	style="width:100%">
				</td>
			</tr>
			
		</table>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command" >
				<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
			  <c:if test="${bean.jlzt == '1'}">
				<input type="button" name="savebutton" id="savebutton" onclick="del()" value="删除" class="button_save">
			  </c:if>
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>


