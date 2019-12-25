<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
<title>�����û�����</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/xtree.css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/sfz.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
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


var roleBuf="";
<c:forEach items="${plsRoleList}" var="current">
  roleBuf += <c:out value="${current.jsdh}" /> +",";
</c:forEach>
$(document).ready(function(){
	changepanel(1);
	
	/**
	//�����û���Ȩģʽ
	var yhsqms = "<c:out value='${yhsqms}'/>";
	if(yhsqms == '1'){//����ɫ��Ȩ
		$("#qxms_2").attr("disabled", true);		
	}
	$("#qxms_<c:out value='${user.qxms}'/>").attr("checked", true);
	*/
	
	//֧���û���������Ȩ
	var bmjb = "<c:out value='${dept.bmjb}'/>";
	if(bmjb > '4'){
		$("#xtgly_1").attr("disabled", true);
		$("#xtgly_2").attr("checked", true);
		checkxtgly();
	}
	
	var cmodal = "<c:out value='${modal}'/>";
	if(cmodal == "edit"){
		var roleBuf="";
		<c:forEach items="${plsRoleList}" var="current">
		  roleBuf += <c:out value="${current.jsdh}" /> +",";
		</c:forEach>
		$("input[name='kczqx']").each(function(){
			if(roleBuf.indexOf($(this).val()) != -1){$(this).attr("checked", "true");}
		});
		$("#xm").attr("readonly", true);
		$("#yhdh").attr("readonly", true);
		$("#sfzmhm").attr("readonly", true);
		$("#rybh").attr("readonly", true);
		$("#sfmj").attr("disabled", true);
		<c:if test="${user != null}">
			$("#zhyxq").val("<c:out value='${user.zhyxq}'/>");
			$("#mmyxq").val("<c:out value='${user.mmyxq}'/>");
			$("#sfmj").val("<c:out value='${user.sfmj}'/>");
			$("#zt").val("<c:out value='${user.zt}'/>");
			setIpIntoIps("ipks", "<c:out value='${user.ipks}'/>");
			setIpIntoIps("ipjs", "<c:out value='${user.ipjs}'/>");
			setIpIntoIps("mac", "<c:out value='${user.mac}'/>");
			setIpIntoIps("gdip", "<c:out value='${user.gdip}'/>");
			setIpIntoIps("gdip1", "<c:out value='${user.gdip1}'/>");
			setIpIntoIps("gdip2", "<c:out value='${user.gdip2}'/>");
		</c:if>
	}
});
function changepanel(idx){
	for (var i=1; i<=2; i++){
		$("#tagitem"+i).attr("className","tag_head_close");
		$("#tagpanel"+i).css("display","none");
	}
	$("#tagitem"+idx).attr("className","tag_head_open");
	$("#tagpanel"+idx).css("display","block");
}
function checkIpNum(obj, type){
	val = $(obj).val().toUpperCase();
	if(typeof(val) != "undefined" && val != ""){
		var isValid = false;
		if(type == "DEC"){
			reg = /[0-9]{1,3}/;
			if(reg.exec(val) != null){
				val10 = parseInt(val, 10);
				if(val10 >= 0 && val10 <= 255){
					isValid = true;
				}
			}
		}else if(type == "HEX"){
			reg = /[A-F,0-9]{2}/;
			if(reg.exec(val) != null){
				val16 = parseInt(val, 16);
				if(val16 >= 0 && val16 <= 255){
					isValid = true;
				}
			}
		}else if(type =="BIN"){
			reg = /[0-1]{8}/;
			if(reg.exec(val) != null){
				val2 = parseInt(val, 2);
				if(val2 >= 0 && val12 <= 255){
					isValid = true;
				}
			}
		}
		if(!isValid){
			alert("IP��ַ���벻��ȷ!");
			$(obj).focus();
		}else{
			$(obj).val(val);
			setIpsIntoIp(obj);
		}
	}
}
function setIpsIntoIp(obj){
	idName = $(obj).attr("id");
	ipID = idName.substring(0, idName.length-2);
	ipsAry = $("input[name=t" + ipID + "]");
	ipVal = "";
	for(i = 0; i < ipsAry.length; i++){
		tIpVal = ipsAry[i].value;
		if(tIpVal == 'undefined' || tIpVal == null || tIpVal == ''){
			tIpVal = "";
		}
		ipVal += "." + tIpVal;
	}
	ipVal = ipVal.substr(1);
	if(ipVal != "..."){
		$("#" + ipID).val(ipVal);
	}
}
function setIpIntoIps(ipID, ipVal){
	$("#" + ipID).val(ipVal);
	ips = ipVal.split(".");
	for(i = 0; i < ips.length; i++){
		$("#" + ipID + "_" + (i + 1)).val(ips[i]);
	}
}
function doChecking(){
	var val = $("#yhdh").val();
	if(val == null || val == "" || val == "null" || val == "undifined"){
		alert("�û����Ų���Ϊ�գ�");
		return false;
	}
	val = $("#rybh").val();
	if(val == null || val == "" || val == "null" || val == "undifined"){
		alert("����/Э���Ų���Ϊ�գ�");
		return false;
	}
	if($("#sfmj").val() == "1"){
		if($("#rybh").val() != $("#yhdh").val()){
			alert("����ʹ�þ�����Ϊ�û����ţ�");
			return false;
		}
	}
	val = $("#sfzmhm").val();
	if(val == null || val == "" || val == "null" || val == "undifined"){
		alert("���֤���벻��Ϊ�գ�");
		return false;
	}else{
		if(!IdCardValidate(val)){
			alert("���֤�������벻��ȷ��");
			return false;
		}
	}
	if(!checkDate($("#zhyxq").val(), "�˺���Ч��")){
		return false;
	}
	if(!checkDate($("#mmyxq").val(), "�˺���Ч��")){
		return false;
	}
	val = $("#gdip").val();
	if(val == null || val == "" || val == "null" || val == "undifined"){
		alert("�̶�IP1����Ϊ�գ�");
		return false;
	}else{
		if(!checkIP($("#gdip"), "�̶�IP1")){
			return false;
		}
	}
	val = $("#gdip1").val();
	if(!(val == null || val == "" || val == "null" || val == "undifined")){
		if(!checkIP($("#gdip1"), "�̶�IP2")){
			return false;
		}
	}
	val = $("#gdip2").val();
	if(!(val == null || val == "" || val == "null" || val == "undifined")){
		if(!checkIP($("#gdip2"), "�̶�IP3")){
			return false;
		}
	}
	val = $("#ipks").val();
	if(val == null || val == "" || val == "null" || val == "undifined"){
		alert("IP��ʼ��ַ����Ϊ�գ�");
		return false;
	}else{
		if(!checkIP($("#ipks"), "IP��ʼ��ַ")){
			return false;
		}
	}
	val = $("#ipjs").val();
	if(val == null || val == "" || val == "null" || val == "undifined"){
		alert("IP������ַ����Ϊ�գ�");
		return false;
	}else{
		if(!checkIP($("#ipjs"), "IP������ַ")){
			return false;
		}
	}
	return true;
}
function savePlsSysuser(){
	if(!doChecking()){
		return;
	}
	if(confirm("�Ƿ�ȷ�ϱ����û���Ϣ��")){
		$("#myform").attr("action","<c:url value='/sysuser.vmc?method=savePlsSysuser'/>");
		$("#myform").attr("target","paramIframe");
		$("#myform").attr("method","post");
		closes();
		$("#myform").submit();
	}
}



//�����û�Ȩ����Ϣ
function saveuserrole() {
	//�����û������ȱ����û���Ϣ
	/**
	var modal=formedit.modal.value;
	if(modal=='new'){
		displayInfoHtml("���ȱ����û���Ϣ���ٽ�����Ȩ��");
		return false;
	}	
	var t_xtgly = $("input[name=xtgly][checked=true]").val();
	if(t_xtgly == 1){
		//ϵͳ����Ա
		if($("input[name=kglqx][checked=true]").size() <= 0){
			alert("��ѡ��ɹ���Ȩ�ޣ�")
			return false;
		}
	}*/
	var t_glbm = $("#glbm").val();
	var t_yhdh = $("#yhdh").val();
	if(t_glbm == '' || t_yhdh == ''){
		alert("��Ҫ�����û���Ϣ���ٽ�����Ȩ��");
		return false;
	}
	closes();
	curUrl = "<c:url value='sysuser.vmc?method=savePlsSysuserRole&glbm='/>" + t_glbm + "&yhdh=" + t_yhdh;
	$("#roleform").attr("action", curUrl);
	$("#roleform").attr("method", "post");
	$("#roleform").attr("target", "paramIframe");
	$("#roleform").submit();
}


function doSaveSysuserReturns(data){
	opens();
	jsonData = eval('(' + data + ')');
	if(jsonData["code"] == '1'){
		alert("����ɹ���");
		window.close();
	}else{
		alert("����ʧ�ܣ�" + decodeURI(jsonData.msg));
	}
	return false;
}
function resetPlsSysuserPwd(){
	if(confirm("�Ƿ�ȷ�������û���" + $("#xm").val() + "�������룿")){
		$("#myform").attr("action","<c:url value='/sysuser.vmc?method=resetPlsSysuserPwd'/>");
		$("#myform").attr("target","paramIframe");
		$("#myform").attr("method","post");
		closes();
		$("#myform").submit();
	}
}
function doResetPlsSysuserPwdResults(data){
	opens();
	jsonData = eval("(" + data + ")");
	if(jsonData.code == '1'){
		alert("��������ɹ���");
	}else{
		alert("��������ʧ�ܣ�" + decodeURI(jsonData.msg));
	}
}
function checkxtgly(){
	var xtgly = $("input[name=xtgly][checked=true]").val();
	if(xtgly == 1){
		//ϵͳ����Ա:��
//		setckenabled(formedit.yyyhz);
//		setckywlxabled(formedit.kgywyhlx1,'');
//		document.getElementById("ksqx").style.display="";	
//		document.getElementById("divkgywyhlx").style.display="";
		$("#kglqxTR").css("display", "block");
	}else if(xtgly == 2){
		//ϵͳ����Ա:��
		//���ý�ɫѡ��
		//clearck(formedit.yyyhz);
//		setckdisabled(formedit.yyyhz);	
//		setckdisabled(formedit.kgywyhlx1);	
//		document.getElementById("ksqx").style.display="none";		
//		document.getElementById("divkgywyhlx").style.display="none";
		$("#kglqxTR").css("display", "none");
	}
}
function checkQxms(){
	/**
	var oqxms=getradio(formedit.oqxms);
	if(oqxms == 1){//��ɫ
		formedit.btyhz.disabled=false;
		divroles.style.display = "block";
		divcxdhs.style.display = "none";
		divcxdhs.innerHTML="";
	}else{//����
		formedit.btyhz.disabled=true;
		divroles.style.display = "none";
		divcxdhs.style.display = "block";
		//���ò˵�Ŀ¼
		getusermenu(2);
	}
	*/
	var oqxms = $("input[name=qxms][checked=true]").val();
	if(oqxms == 1){//��ɫ
		$("#btn_kczqx").attr("disabled", false);
	}else{//����
		$("#btn_kczqx").attr("disabled", true);
	}
}
function showRoleMenu(jsdh, glbm){
	var curUrl = "<c:url value='sysuser.frm?method=rolemenuresult&glbm='/>" + glbm + "&jsdh=" + jsdh;
	window.showModalDialog(curUrl, "dialogWidth:800;dialogHeight:300;help:no;status:no");
}

function showAllRoleMenu(type){
	var roles = "";
	if(type == 1){
		//�ɹ���Ȩ��
		var kglqxAry = $("input[name=kglqx][checked=true]");
		for(i = 0; i < kglqxAry.size(); i++){
			roles += "A" + $(kglqxAry[i]).val();
		}
		
	}else{
		//�ɲ���Ȩ��
		var kczqxAry = $("input[name=kczqx][checked=true]");
		for(i = 0; i < kczqxAry.size(); i++){
			roles += "A" + $(kczqxAry[i]).val();
		}
	}
	if(roles == ""){
		alert("δѡ���ɫ��");
		return;
	}
	roles = roles.substr(1);
	var sFeatures="dialogHeight:400px;dialogWidth:700px;help:no;status:no;scroll:no;";
	var vReturnValue = window.showModalDialog("sysuser.frm?method=rolemenuresult&glbm="+"<c:out value='${cglbm}'/>"+"&jsdh="+roles, "",sFeatures);
}

function doSaveuserroleReturns(data){
	opens();
	jsonData = eval("(" + data + ")");
	if(jsonData['code'] == '1'){
		alert("����ɹ���");
		window.close();
	}else{
		alert("����ʧ�ܣ�" + decodeURI(jsonData.msg));
	}
}
function delPlsSysuser(){
	if(confirm("�Ƿ�ȷ��ɾ���û���" + $("#xm").val() + "����\nɾ�����û���ͬʱ����ɾ�����û���ӵ�н�ɫ��")){
		$("#myform").attr("action","<c:url value='/sysuser.vmc?method=deletePlsSysuser'/>");
		$("#myform").attr("target","paramIframe");
		$("#myform").attr("method","post");
		closes();
		$("#myform").submit();
	}
}
function doDelPlsSysuser(data){
	opens();
	jsonData = eval("(" + data + ")");
	if(jsonData['code'] == '1'){
		alert("�û���ɾ����");
		opener.query_cmd();
		window.close();
	}else{
		alert("ɾ���û�ʧ�ܣ�" + decodeURI(jsonData.msg));
	}
}
</script>
<style type="text/css">
.ip_3{
	width: 25px;
	text-align: right;
}
.ip_2{
	width: 20px;
	text-align: right;
}
.liqx{
	width:170px;
	float:left;
}
.fieldsetqx{
	font-size:12px;
	border:solid 1px #CCCCCC;
	margin-top:6px;
	margin-bottom:6px;
	padding:4px;
}
</style>
</head>
<body>
<div id="panel">
	<div id="paneltitle">�����û�ά��</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">�û���Ϣ</td>
			<td class="tag_head_close" align="center" onClick="changepanel(2)" id="tagitem2">��Ȩ��Ϣ</td>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
	
	<div id="tagpanel1" style="display:none;" class="tag_body_table">
		<form id="myform" name="myform" action="">
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="23%">
			<col width="10%">
			<col width="24%">
			<col width="10%">
			<col width="23%">
			<tr>
				<td class="head">������</td>
				<td class="body"><input type="text" id="glbm" name="glbm" value="<c:out value='${dept.glbm}'/>" readonly></td>
				<td class="head">��������</td>
				<td class="body"><input type="text" id="bmmc" name="bmmc" value="<c:out value='${dept.bmmc}'/>" readonly></td>
				<td class="head"><span class="gotta">*</span>��Ա����</td>
				<td class="body">
					<select id="sfmj" name="sfmj" style="width:100%;">
						<c:forEach items="${sfmjCodeList}" var="current">
							<option value="<c:out value='${current.dmz}'/>"><c:out value='${current.dmsm1}'/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>����</td>
				<td class="body"><input type="text" id="xm" name="xm" value="<c:out value='${user.xm}'/>" maxlength="16" onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"></td>
				<td class="head"><span class="gotta">*</span>�û�����</td>
				<td class="body"><input type="text" id="yhdh" name="yhdh" value="<c:out value='${user.yhdh}'/>" maxlength="16"></td>
				<td class="head"><span class="gotta">*</span>���֤��</td>
				<td class="body"><input type="text" id="sfzmhm" name="sfzmhm" value="<c:out value='${user.sfzmhm}'/>" maxlength="18"></td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>����/Э����</td>
				<td class="body"><input type="text" id="rybh" name="rybh" value="<c:out value='${user.rybh}'/>" maxlength="16"></td>
				<td class="head"><span class="gotta">*</span>������Ч��</td>
				<td class="body"><h:datebox id="zhyxq" name="zhyxq" showType="1"/></td>
				<td class="head"><span class="gotta">*</span>�˻���Ч��</td>
				<td class="body"><h:datebox id="mmyxq" name="mmyxq" showType="1"/></td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>�̶�IP��ַ1</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip_1" name="tgdip" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip_2" name="tgdip" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip_3" name="tgdip" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip_4" name="tgdip" value="">
					<input type="hidden" id="gdip" name="gdip" value="">
				</td>
				<td class="head">�̶�IP��ַ2</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip1_1" name="tgdip1" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip1_2" name="tgdip1" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip1_3" name="tgdip1" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip1_4" name="tgdip1" value="">
					<input type="hidden" id="gdip1" name="gdip1" value="">
				</td>
				<td class="head">�̶�IP��ַ3</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip2_1" name="tgdip2" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip2_2" name="tgdip2" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip2_3" name="tgdip2" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="gdip2_4" name="tgdip2" value="">
					<input type="hidden" id="gdip2" name="gdip2" value="">
				</td>
			</tr>
			<tr>
				<td class="head"><span class="gotta">*</span>IP��ʼ��ַ</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_1" name="tipks" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_2" name="tipks" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_3" name="tipks" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_4" name="tipks" value="">
					<input type="hidden" id="ipks" name="ipks" value="">
				</td>
				<td class="head"><span class="gotta">*</span>IP������ַ</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_1" name="tipjs" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_2" name="tipjs" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_3" name="tipjs" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_4" name="tipjs" value="">
					<input type="hidden" id="ipjs" name="ipjs" value="">
				</td>
				<td class="head"><span class="gotta">*</span>״̬</td>
				<td class="body">
					<select id="zt" name="zt" style="width:100%;">
						<c:forEach items="${ztCodeList}" var="current">
							<option value="<c:out value='${current.dmz}'/>"><c:out value='${current.dmsm1}'/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<c:if test="{user != null">
			<tr>
				<td class="head">�����¼ʱ��</td>
				<td class="body"><c:out value='${user.zjdlsj}'/></td>
				<td class="head"></td>
				<td class="body"></td>
				<td class="head"></td>
				<td class="body"></td>
			</tr>
			</c:if>
			<tr>
				<td class="head">˵��</td>
				<td class="body" colspan="5">
					<font color="red">
					��IP��ʼ��ַ��IP������ַ���̶�IP��ַ1���̶�IP��ַ2���̶�IP��ַ3��Ϊ�û����ʼ��鲼��ϵͳ�Ŀͻ���IP��ַ��</font>
				</td>
			</tr>
			<tr>
				<td class="command" colspan="6">
					<input type="hidden" id="modal" name="modal" value="<c:out value='${modal}'/>">
					<input type="button" class="button_save" value="����" onclick="savePlsSysuser()">
					<input type="button" class="button_other" value="��������" onclick="resetPlsSysuserPwd()">
					<input type="button" class="button_del" value="ɾ��" onclick="delPlsSysuser()">
					<input type="button" class="button_close" value="�ر�" onclick="javascript:window.close()">
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<div id="tagpanel2" style="display:none;" class="tag_body_table">
		<form id="roleform" name="roleform" action="">
		<table border="0" cellspacing="1" cellpadding="0" class="detail" width="100%">
			<col width="10%">
			<col width="75%">
			<col width="15%">
			<tr>
				<td class="head">����Ȩ��</td>
				<td class="body">
					<c:if test="${roleList != null}">
						<fieldset class="fieldsetqx">
							<legend>��ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${roleList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kczqx" id="kczqx<c:out value='${current.jsdh}'/>" value="<c:out value='${current.jsdh}'/>">
										<label><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">˵��</td>
				<td class="body" colspan="2" style="color:red;">ȫ����ȷ��ѯ����ʡģ����ѯ: ���ܶ�����</td>			
			</tr>
			<tr>
				<td class="command" colspan="3">
					<input type="button" class="button_other" value="������Ȩ" onclick="saveuserrole()">
					<input type="button" class="button_close" value="�ر�" onclick="javascript:window.close()">
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<iframe style="display:none;" id="paramIframe" name="paramIframe"></iframe>
</body>
</html>
