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
	$("#glbm").val($("#glbm1").val()+$("#glbm2").val()+$("#glbm3").val());
	
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
	} else if(data["code"]=="2") {
		alert("该警种的公安部门已存在！");
		opens();
	} else{
		alert(decodeURIComponent(data["message"]));
		opens();    
	}
}

function doChecking(){
	if(!checkNull($("#sjbm"),"同级交管部门")) return false;
	// if(!checkPrecision($("#glbm"),"12","管理部门")) return false;
	if(!checkNull($("#bmjb"),"部门级别"))	return false;
	if(!checkNull($("#jzlx"),"警种类型")) return false;
	if(!checkNull($("#glbm1"),"部门代码"))	return false;
	if(!checkNull($("#glbm2"),"部门代码")) return false;
	
	
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

function getjzdm() {
	$("#glbm2").val($("#jzlx").val());
}


function getChange() {
	$("#glbm1").val($("#sjbm").val().substr(0,8));
	if($("#sjbm").val().length>0){
		curUrl = "department.vmc?method=syncGetBmjb&glbm=" +$("#sjbm").val();
		$.getJSON(curUrl,function(data){
				$("#bmjb").val(data['bmjb']);
				$("#jbmc").val(decodeURI(data['jbmc']));
			}
		);
	} else {
		$("#bmjb").val('');
		$("#jbmc").val('');
	}
}


</script>
</head>
<body>
<form action="" method="post" name="myform" id="myform">
<div id="panel" style="display:none">
<div id="paneltitle">${cxmc}</div>
	<div id="block">
		<div id="blocktitle">公安部门新增</div>
		<div id="blockmargin">8</div>
		<input type="hidden"  id="glbm"   name="glbm"  />
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="38%">
			<col width="10%">
			<col width="38%">
			<tr>
				<td class="head">同级交管部门</td>
				<td class="body">
				<h:managementbox list='${bmlist}'  id='sjbm' haveNull='1'  cssStyle="width:50%"  onChange="getChange()"/>
				</td>
				<td class="head">部门级别</td>
				<td class="body">
					<input type="text"  id="jbmc" name="jbmc"  readonly  style="width:50%" />
					<input type="hidden" id="bmjb" name="bmjb"  />
				</td>
			</tr>
			<tr>
				<td class="head">部门代码</td>
				<td class="body">
					<input type="text" id="glbm1" style="width:30%" readOnly >
					<input type="text" id="glbm2" style="width:16%" readOnly >
					<input type="text" id="glbm3" style="width:16%" readOnly value="00">
				</td>
				<td class="head">警种</td>
				<td class="body"><h:codebox list='${jzlx}' id='jzlx' haveNull='1' cssStyle="width:50%" onChange="getjzdm()"/></td>
			</tr>
			<tr>
				<td class="head">部门名称</td>
				<td class="body" colspan="3">
					<input type="text" id="bmmc" name="bmmc" style="width:100%">
				</td>
			</tr>
			<tr>
				<td class="head">部门全称</td>
				<td class="body" colspan="3">
					<input type="text" id="bmqc" name="bmqc" style="width:100%">
				</td>
			</tr>
			<tr>
				<td class="head">印章名称</td>
				<td class="body" colspan="3">
					<input type="text" id="yzmc" name="yzmc" style="width:100%">
				</td>
			</tr>
			<tr>
				<td class="head">负责人</td>
				<td class="body"><input type="text" id="fzr"  name="fzr"></td>
				<td class="head">联系人</td>
				<td class="body"><input type="text" id="lxr" name="lxr"></td>
			</tr>
			<tr>
				<td class="head">联系电话</td>
				<td class="body"><input type="text" id="lxdh" name="lxdh"></td>
				<td class="head">传真号码</td>
				<td class="body"><input type="text" id="czhm" name="czhm"></td>
			</tr>
			<tr>
				<td class="head">联系地址</td>
				<td class="body" colspan="3">
					<input type="text" id="lxdz" name="lxdz" style="width:100%">
				</td>
			</tr>
			
		</table>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command" >
				<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>


