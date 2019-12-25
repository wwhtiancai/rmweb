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
var glbm="";
var bmjb=0;
var jz="";
var glbmcontroller=0;
$(document).ready(function(){
<c:if test="${bean!=null}">
	$("#sjbm").val("<c:out value='${bean.glbm}'/>");
	$("#sjbms").html("<c:out value='${bean.glbm}'/>");
	$("#sjbmmcs").html("<c:out value='${bean.bmmc}'/>");
	glbm="<c:out value='${bean.glbm}'/>";
	bmjb=<c:out value='${bean.bmjb}'/>;
	jz="<c:out value='${bean.jzlx}'/>";
</c:if>
	$("#step1").css("display","block");
});
function save(){
	$("#glbm").val("");
	for(var i=1;i<=glbmcontroller;i++){
		$("#glbm").val($("#glbm").val()+$("#glbm"+i).val());
	}
	if(!doChecking()) return;
	if($("#glbm").val()==glbm){
		alert("新建的部门不能为自己！");
		return;
	}
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
function doCheck(s){
	var obj=$(":radio[id=jzlxmc]");
	for(var i=0;i<obj.size();i++){
		obj[i].checked=false;
		if(i==s){
			obj[i].checked=!obj[i].checked;
		}
	}
}
function translateBMJB(v){
	if(v==1){
		return "公安部";
	}else if(v==2){
		return "公安厅";
	}else if(v==3){
		return "公安局";
	}else if(v==4){
		return "公安分局";
	}else if(v==5){
		return "派出所";
	}
}
function doprevious(){
  $("#step1").css("display","block");
	$("#step2").css("display","none");
}
function donext(){
	var bmlx=0;
	var obj=$(":radio[id=jzlxmc]");
	for(var i=0;i<obj.size();i++){
			if(obj[i].checked){
				bmlx=obj[i].value;
				break;
			}
	}
	var html="";
	if(bmlx=="1"){
	  $("#bmjb").val(bmjb);
	  $("#bmjbs").html(translateBMJB(bmjb));
		html+='<input type="text" id="glbm1" style="width:48%" readOnly value="'+glbm.substr(0,6)+'">';
		if(glbm.substr(6,6)=="000000"){
			html+='<input type="text" id="glbm2" style="width:16%" maxlength="2" value="">';
			html+='<input type="text" id="glbm3" style="width:32%" readOnly value="0000">';
			glbmcontroller=3;
		}else if(glbm.substr(8,4)=="0000"){
			html+='<input type="text" id="glbm2" style="width:16%"  readOnly value="'+glbm.substr(6,2)+'">';
			html+='<input type="text" id="glbm3" style="width:16%" maxlength="2" value="">';
			html+='<input type="text" id="glbm4" style="width:16%" readOnly value="00">';
			glbmcontroller=4;
		}else if(glbm.substr(10,2)=="00"){
			html+='<input type="text" id="glbm2" style="width:32%" readOnly value="'+glbm.substr(6,4)+'">';
			html+='<input type="text" id="glbm3" style="width:16%" maxlength="2" value="">';
			glbmcontroller=3;
		}else{
			alert("该部门已为基层部门！不能进行新建工作！");
			doprevious();
			return;
		}
		$("#glbms").html(html);
		$("#jzlx").val(jz);
	  $("#step1").css("display","none");
	  $("#step2").css("display","block");
	}else if(bmlx=="2"){
		if(glbm.substr(4,8)!="00000000"){
			alert("只有公安厅，公安局才能建立城区市局或分局！");
			return;
		}
		if(glbm.substr(2,10)=="0000000000"){
			html+='<input type="text" id="glbm1" style="width:16%" readOnly value="'+glbm.substr(0,2)+'">';
			html+='<input type="text" id="glbm2" style="width:16%" maxlength="2" value="">';
			html+='<input type="text" id="glbm3" style="width:64%" readOnly value="00000000">';
			glbmcontroller=3;
		}else	if(glbm.substr(4,8)=="00000000"){
			html+='<input type="text" id="glbm1" style="width:16%" readOnly value="'+glbm.substr(0,4)+'">';
			html+='<input type="text" id="glbm2" style="width:16%" maxlength="2" value="">';
			html+='<input type="text" id="glbm3" style="width:64%" readOnly value="000000">';
			glbmcontroller=3;
		}else{
			alert("该部门已为基层部门！不能进行新建工作！");
			doprevious();
			return;
		}
		$("#glbms").html(html);
	  $("#bmjb").val(bmjb+1);
	  $("#bmjbs").html(translateBMJB(bmjb+1));
		$("#jzlx").val("31");
	  $("#step1").css("display","none");
	  $("#step2").css("display","block");
	}else if(bmlx=="3"){
		if(glbm.substr(6,6)!="000000"){
			alert("只有公安厅，公安局，公安分局才能建立下属总队、支队或大队！");
			return;
		}
		html+='<input type="text" id="glbm1" style="width:48%" readOnly value="'+glbm.substr(0,6)+'">';
		html+='<input type="text" id="glbm2" style="width:16%" maxlength="2" value="">';
		html+='<input type="text" id="glbm3" style="width:32%" readOnly value="0000">';
		glbmcontroller=3;
		$("#glbms").html(html);
	  $("#bmjb").val(bmjb);
	  $("#bmjbs").html(translateBMJB(bmjb));
		$("#jzlx").val("");
	  $("#step1").css("display","none");
	  $("#step2").css("display","block");
	}else if(bmlx=="4"){
		html+='<input type="text" id="glbm1" style="width:48%" readOnly value="'+glbm.substr(0,6)+'">';
		if(glbm.substr(6,6)=="000000"){
			html+='<input type="text" id="glbm2" style="width:16%" maxlength="2" value="">';
			html+='<input type="text" id="glbm3" style="width:32%" readOnly value="0000">';
			glbmcontroller=3;
		}else if(glbm.substr(8,4)=="0000"){
			html+='<input type="text" id="glbm2" style="width:16%"  readOnly value="'+glbm.substr(6,2)+'">';
			html+='<input type="text" id="glbm3" style="width:16%" maxlength="2" value="">';
			html+='<input type="text" id="glbm4" style="width:16%" readOnly value="00">';
			glbmcontroller=4;
		}else if(glbm.substr(10,2)=="00"){
			html+='<input type="text" id="glbm2" style="width:32%" readOnly value="'+glbm.substr(6,4)+'">';
			html+='<input type="text" id="glbm3" style="width:16%" maxlength="2" value="">';
			glbmcontroller=3;
		}else{
			alert("该部门已为基层部门！不能进行新建工作！");
			doprevious();
			return;
		}
		$("#glbms").html(html);	 
	  $("#bmjb").val(5);
	  $("#bmjbs").html(translateBMJB(5));
		$("#jzlx").val("96");
	  $("#step1").css("display","none");
	  $("#step2").css("display","block");	
	}else{
		alert("清选择新建部门的类型。");
	}
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" id="step1" style="display:none;">
	<tr>
		<td>
			<table border="0" cellspacing="1" cellpadding="0" align="center" class="detail" style="width:98%">
				<col width="12%">
				<col width="88%">
				<tr>
					<td class="head">新建</td>
					<td class="body"><input type="radio" id="jzlxmc" value="1" onClick="doCheck(0)"><span style="cursor:pointer;" onClick="doCheck(0)">内部部门</span>&nbsp;&nbsp;<input type="radio" id="jzlxmc" value="2" onClick="doCheck(1)"><span style="cursor:pointer;" onClick="doCheck(1)">城区市（分）局</span>&nbsp;&nbsp;<input type="radio" id="jzlxmc" value="3" onClick="doCheck(2)"><span style="cursor:pointer;" onClick="doCheck(2)">下属总（支、大）队&nbsp;&nbsp;</span><input type="radio" id="jzlxmc" value="4" onClick="doCheck(3)"><span style="cursor:pointer;" onClick="doCheck(3)">派出所</span></td>
				</tr>
				<tr>
					<td colspan="2" class="command">
						<input type="button" onclick="donext()" value="下一步" class="button_other">
						<input type="button" onclick="parent.parent.doClose()" value="关闭" class="button_close">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" id="step2" style="display:none;">
	<tr>
		<td>
			<form action="" method="post" name="myform" id="myform">
			<input type="hidden" name="glbm" id="glbm" value="">
			<input type="hidden" name="bmjb" id="bmjb" value="">
			<input type="hidden" name="sjbm" id="sjbm" value="">
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
					<td class="head">上级部门代码</td>
					<td class="body" id="sjbms"></td>
					<td class="head">部门级别</td>
					<td class="body" id="bmjbs"></td>
				</tr>
				<tr>
					<td class="head">上级部门名称</td>
					<td colspan="3" class="body" id="sjbmmcs"></td>
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
						<input type="button" onclick="doprevious()" value="上一步" class="button_other">
						<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
						<input type="button" onclick="parent.parent.doClose()" value="关闭" class="button_close">
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>

</body>
</html>
