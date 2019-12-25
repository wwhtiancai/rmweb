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
		alert("�½��Ĳ��Ų���Ϊ�Լ���");
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
		return "������";
	}else if(v==2){
		return "������";
	}else if(v==3){
		return "������";
	}else if(v==4){
		return "�����־�";
	}else if(v==5){
		return "�ɳ���";
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
			alert("�ò�����Ϊ���㲿�ţ����ܽ����½�������");
			doprevious();
			return;
		}
		$("#glbms").html(html);
		$("#jzlx").val(jz);
	  $("#step1").css("display","none");
	  $("#step2").css("display","block");
	}else if(bmlx=="2"){
		if(glbm.substr(4,8)!="00000000"){
			alert("ֻ�й������������ֲ��ܽ��������оֻ�־֣�");
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
			alert("�ò�����Ϊ���㲿�ţ����ܽ����½�������");
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
			alert("ֻ�й������������֣������־ֲ��ܽ��������ܶӡ�֧�ӻ��ӣ�");
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
			alert("�ò�����Ϊ���㲿�ţ����ܽ����½�������");
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
		alert("��ѡ���½����ŵ����͡�");
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
					<td class="head">�½�</td>
					<td class="body"><input type="radio" id="jzlxmc" value="1" onClick="doCheck(0)"><span style="cursor:pointer;" onClick="doCheck(0)">�ڲ�����</span>&nbsp;&nbsp;<input type="radio" id="jzlxmc" value="2" onClick="doCheck(1)"><span style="cursor:pointer;" onClick="doCheck(1)">�����У��֣���</span>&nbsp;&nbsp;<input type="radio" id="jzlxmc" value="3" onClick="doCheck(2)"><span style="cursor:pointer;" onClick="doCheck(2)">�����ܣ�֧���󣩶�&nbsp;&nbsp;</span><input type="radio" id="jzlxmc" value="4" onClick="doCheck(3)"><span style="cursor:pointer;" onClick="doCheck(3)">�ɳ���</span></td>
				</tr>
				<tr>
					<td colspan="2" class="command">
						<input type="button" onclick="donext()" value="��һ��" class="button_other">
						<input type="button" onclick="parent.parent.doClose()" value="�ر�" class="button_close">
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
					<td class="head"><span class="gotta">*</span>���Ŵ���</td>
					<td class="body" id="glbms"></td>
					<td class="head"><span class="gotta">*</span>����</td>
					<td class="body"><h:codebox list='${jzlx}' id='jzlx' haveNull='1'/></td>
				</tr>
				<tr>
					<td class="head">�ϼ����Ŵ���</td>
					<td class="body" id="sjbms"></td>
					<td class="head">���ż���</td>
					<td class="body" id="bmjbs"></td>
				</tr>
				<tr>
					<td class="head">�ϼ���������</td>
					<td colspan="3" class="body" id="sjbmmcs"></td>
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
						<input type="button" onclick="doprevious()" value="��һ��" class="button_other">
						<input type="button" name="savebutton" id="savebutton" onclick="save()" value="����" class="button_save">
						<input type="button" onclick="parent.parent.doClose()" value="�ر�" class="button_close">
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>

</body>
</html>
