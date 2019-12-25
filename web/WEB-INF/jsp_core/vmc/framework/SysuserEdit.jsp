<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�����û�����</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="css/xtree.css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<style type="text/css">
.detail .body input[type='text'].ip_3{
	width: 25px;
	text-align: right;
}
.detail .body input[type='text'].ip_2{
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
<body class="rfid">
<div id="panel">
	<div id="paneltitle">�����û�ά��</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">�û���Ϣ</td>
			<c:if test="${editable}">
			<td class="tag_head_close" align="center" onClick="changepanel(2)" id="tagitem2">��Ȩ��Ϣ</td>
			</c:if>
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
				<td class="head">��Ա����</td>
				<td class="body">
					<select id="sfmj" name="sfmj" disabled="disabled" style="width:100%;">
						<c:forEach items="${sfmjCodeList}" var="current">
							<option value="<c:out value='${current.dmz}'/>"><c:out value='${current.dmsm1}'/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="head">����</td>
				<td class="body"><input type="text" id="xm" name="xm" value="<c:out value='${user.xm}'/>" readonly></td>
				<td class="head">�û�����</td>
				<td class="body"><input type="text" id="yhdh" name="yhdh" value="<c:out value='${user.yhdh}'/>" readonly></td>
				<td class="head">���֤��</td>
				<td class="body"><input type="text" id="sfzmhm" name="sfzmhm" value="<c:out value='${user.sfzmhm}'/>" readonly></td>
			</tr>
			<tr>
				<td class="head">����/Э����</td>
				<td class="body"><input type="text" id="rybh" name="rybh" value="<c:out value='${user.rybh}'/>" readonly></td>
				<td class="head">������Ч��</td>
				<td class="body"><h:datebox id="mmyxq" name="mmyxq" showType="1" width="80%"/><!--input type="text" id="zhyxq" name="zhyxq" value="<c:out value='${user.mmyxq}'/>"--></td>
				<td class="head">�˻���Ч��</td>
				<td class="body"><h:datebox id="zhyxq" name="zhyxq" showType="1" width="79%"/><!--input type="text" id="mmyxq" name="mmyxq" value="<c:out value='${user.zhyxq}'/>"--></td>
			</tr>
			<tr>
				<td class="head">IP��ʼ��ַ</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_1" name="tipks" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_2" name="tipks" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_3" name="tipks" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipks_4" name="tipks" value="">
					<input type="hidden" id="ipks" name="ipks" value="">
				</td>
				<td class="head">IP������ַ</td>
				<td class="body">
					<input type="text" class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_1" name="tipjs" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_2" name="tipjs" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_3" name="tipjs" value="">.<input type="text" 
						class="ip_3" onBlur="checkIpNum(this, 'DEC')" maxlength="3" id="ipjs_4" name="tipjs" value="">
					<input type="hidden" id="ipjs" name="ipjs" value="">
				</td>
				<td class="head">MAC��ַ</td>
				<td class="body">
					<input type="text" class="ip_2" id="mac_1" name="tmac" value="" readonly>-<input type="text" 
						class="ip_2" id="mac_2" name="tmac" value="" readonly>-<input type="text" 
						class="ip_2" id="mac_3" name="tmac" value="" readonly>-<input type="text" 
						class="ip_2" id="mac_4" name="tmac" value="" readonly>-<input type="text" 
						class="ip_2" id="mac_5" name="tmac" value="" readonly>-<input type="text" 
						class="ip_2" id="mac_6" name="tmac" value="" readonly>
					<input type="hidden" id="mac" name="mac" value="">
				</td>
			</tr>
			<tr>
				<td class="head">�̶�IP��ַ1</td>
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
				<td class="head">״̬</td>
				<td class="body">
					<select id="zt" name="zt" disabled="disabled" style="width:100%;">
						<c:forEach items="${ztCodeList}" var="current">
							<option value="<c:out value='${current.dmz}'/>"><c:out value='${current.dmsm1}'/></option>
						</c:forEach>
					</select>
				</td>
				<td class="head">�����¼ʱ��</td>
				<td class="body"><c:out value='${user.zjdlsj}'/></td>
				<td class="head"></td>
				<td class="body"></td>
				
			</tr>
			<tr>
				<td class="head">˵��</td>
				<td class="body" colspan="5">
					<font color="red">
					�û������ڼ��鲼��ϵͳ�����ã��ڼ��鲼��ϵͳ���޸�����󲻻�ı��ۺ�Ӧ��ƽ̨�и��û������룬����ͬ�����û�����Ϊ���鲼��ϵͳĬ�����롣<br>
					��IP��ʼ��ַ��IP������ַ���̶�IP��ַ1���̶�IP��ַ2���̶�IP��ַ3��Ϊ�û����ʼ��鲼��ϵͳ�Ŀͻ���IP��ַ�����ڼ��鲼��ϵͳ���ã����ڼ��鲼��ϵͳ����Ч����</font>
				</td>
			</tr>
			<tr>
				<td class="command" colspan="6">
					<input type="hidden" id="modal" name="modal" value="new">
					<input type="button" class="button_save" value="����" onclick="saveSysuser()">
					<c:if test="${user.zt==2}">
					<input type="button" class="button_save" value="����" onclick="unlockSysuser()">
					</c:if>
					<input type="button" class="button_other" value="��������" onclick="resetSysuserPwd()">
                    <c:if test="${editable}">
                    <input type="button" class="button_del" value="ɾ��" onclick="delSysuser()"/>
                    </c:if>
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
				<td class="head">ϵͳ����Ա</td>
				<td class="body" colspan="2">
					<c:forEach items="${xtglyCodeList}" var="current">
						<input name="xtgly" id="xtgly_<c:out value='${current.dmz}'/>" type="radio" value="<c:out value='${current.dmz}'/>" onclick="checkxtgly()">
	                    <label for="xtgly_<c:out value='${current.dmz}'/>" style="cursor:point;"><c:out value='${current.dmsm1}'/></label>
					</c:forEach>
				</td>			
			</tr>
			<tr id="kglqxTR">
				<td class="head">�ɹ���Ȩ��</td>
				<td class="body">
					<c:if test="${userGrantRoleByOthList != null}">
						<fieldset class="fieldsetqx">
							<legend>��������Ա������Ľ�ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${userGrantRoleByOthList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kglqx" id="kglqx<c:out value='${current.jsdh}'/>" disabled="disabled" value="<c:out value='${current.jsdh}'/>" checked="checked">
										<label onclick="showRoleMenu('<c:out value='${current.jsdh}'/>','<c:out value='${current.glbm}'/>')"><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
					<c:if test="${userGrantRoleByCurList != null}">
						<fieldset class="fieldsetqx">
							<legend>������Ա������Ľ�ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${userGrantRoleByCurList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kglqx" id="kglqx<c:out value='${current.jsdh}'/>" value="<c:out value='${current.jsdh}'/>" checked="checked">
										<label onclick="showRoleMenu('<c:out value='${current.jsdh}'/>','<c:out value='${current.glbm}'/>')"><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
					<c:if test="${userGrantRoleCanByCurList != null}">
						<fieldset class="fieldsetqx">
							<legend>������Ա������Ľ�ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${userGrantRoleCanByCurList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kglqx" id="kglqx<c:out value='${current.jsdh}'/>" value="<c:out value='${current.jsdh}'/>">
										<label onclick="showRoleMenu('<c:out value='${current.jsdh}'/>','<c:out value='${current.glbm}'/>')"><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
				</td>
				<td class="body">
					<input type="button" value="��ѯѡ���ɫȨ��" style="width:120px;" onclick="showAllRoleMenu(1)">
				</td>			
			</tr>
			<tr id="qxmsDR" style="display:none;">
				<td class="head">Ȩ��ģʽ</td>
				<td class="body" colspan="2">
					<input onclick="checkQxms()" type="radio" name="qxms" id="qxms_1" value="1" checked="checked">��ɫ
					<input onclick="checkQxms()" type="radio" name="qxms" id="qxms_2" value="2">����
				</td>			
			</tr>
			<tr>
				<td class="head">����Ȩ��</td>
				<td class="body">
					<c:if test="${userRoleByOthList != null}">
						<fieldset class="fieldsetqx">
							<legend>��������Ա������Ľ�ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${userRoleByOthList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kczqx" id="kczqx<c:out value='${current.jsdh}'/>" disabled="disabled" value="<c:out value='${current.jsdh}'/>" checked="checked">
										<label onclick="showRoleMenu('<c:out value='${current.jsdh}'/>','<c:out value='${current.glbm}'/>')"><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
					<c:if test="${userRoleByCurList != null}">
						<fieldset class="fieldsetqx">
							<legend>������Ա������Ľ�ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${userRoleByCurList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kczqx" id="kczqx<c:out value='${current.jsdh}'/>" value="<c:out value='${current.jsdh}'/>" checked="checked">
										<label onclick="showRoleMenu('<c:out value='${current.jsdh}'/>','<c:out value='${current.glbm}'/>')"><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
					<c:if test="${userRoleCanByCurList != null}">
						<fieldset class="fieldsetqx">
							<legend>������Ա������Ľ�ɫ</legend>
							<ul style="width:100%;">
								<c:forEach items="${userRoleCanByCurList}" var="current">
									<li class="liqx">
										<input type="checkbox" name="kczqx" id="kczqx<c:out value='${current.jsdh}'/>" value="<c:out value='${current.jsdh}'/>">
										<label onclick="showRoleMenu('<c:out value='${current.jsdh}'/>','<c:out value='${current.glbm}'/>')"><c:out value='${current.jsmc}'/></label>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
					</c:if>
				</td>
				<td class="body">
					<input id="btn_kczqx" type="button" value="��ѯѡ���ɫȨ��" style="width:120px;" onclick="showAllRoleMenu(2)">
				</td>			
			</tr>
			<tr>
				<td class="head">˵��</td>
				<td class="body" colspan="2" style="color:red;">����ϵͳ����Ա����С����֧��</td>			
			</tr>
			<tr>
				<td class="command" colspan="3">
					<input type="button" class="button_other" value="������Ȩ" onclick="saveuserrole()">
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<iframe style="display:none;" id="paramIframe" name="paramIframe"></iframe>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
	<!--
	$(document).ready(function(){
		$.datepicker.setDefaults($.datepicker.regional['']);
		$(".jscal").each(function () {
			eval($(this).html());
		});

		<c:if test="${user.zhyxq != null}">
		$("#zhyxq").val("<c:out value='${user.zhyxq}'/>");
		//$("#zhyxq").attr("readonly", "readonly");
		</c:if>
		<c:if test="${user.mmyxq != null}">
		$("#mmyxq").val("<c:out value='${user.mmyxq}'/>");
		//$("#mmyxq").attr("readonly", "readonly");
		</c:if>

		changepanel(1);

		$("#sfmj").val("<c:out value='${user.sfmj}'/>");
		$("#zt").val("<c:out value='${user.zt}'/>");
		setIpIntoIps("ipks", "<c:out value='${user.ipks}'/>");
		setIpIntoIps("ipjs", "<c:out value='${user.ipjs}'/>");
		setIpIntoIps("mac", "<c:out value='${user.mac}'/>");
		setIpIntoIps("gdip", "<c:out value='${user.gdip}'/>");
		setIpIntoIps("gdip1", "<c:out value='${user.gdip1}'/>");
		setIpIntoIps("gdip2", "<c:out value='${user.gdip2}'/>");

		//����ϵͳ����Ա
		$("#xtgly_<c:out value='${user.xtgly}'/>").attr("checked", true);
		checkxtgly();

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
		}
	});
	function changepanel(idx){
		for (var i=1; i<=2; i++){
			$("#tagitem" + i).removeClass("tag_head_open").addClass("tag_head_close");
			$("#tagpanel"+i).css("display","none");
		}
		$("#tagitem" + idx).removeClass("tag_head_close").addClass("tag_head_open");
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
		$("#" + ipID).val(ipVal);
	}
	function setIpIntoIps(ipID, ipVal){
		$("#" + ipID).val(ipVal);
		ips = ipVal.split(".");
		for(i = 0; i < ips.length; i++){
			$("#" + ipID + "_" + (i + 1)).val(ips[i]);
		}
	}
	function datediff(s, e) {
		var arr = s.split("-");
		var starttime = new Date(arr[0], arr[1], arr[2]);
		var starttimes = starttime.getTime();

		var arrs = e.split("-");
		var lktime = new Date(arrs[0], arrs[1], arrs[2]);
		var lktimes = lktime.getTime();

		if (starttimes >= lktimes) {
			return true;
		}

		return false;
	}

	function saveSysuser(){
		if(datediff("2013-09-02", $("#zhyxq").val())){
			alert("�˻���Ч�ڲ���С�ڵ�ǰ����");
			return false;
		}

		if(datediff("2013-09-02", $("#mmyxq").val())){
			alert("������Ч�ڲ���С�ڵ�ǰ����");
			return false;
		}

		if(confirm("�Ƿ�ȷ�ϱ����û���Ϣ��")){
			$("#myform").attr("action","<c:url value='/sysuser.vmc?method=saveSysuser'/>");
			$("#myform").attr("method","post");
			$("#myform").attr("target","paramIframe");
			closes();
			$("#myform").submit();
		}
	}

	function unlockSysuser(){
		if(confirm("�Ƿ�ȷ�Ͻ������û��������󴰿ڽ��رգ�")){
			$("#myform").attr("action","<c:url value='/sysuser.vmc?method=unlockSysuser'/>");
			$("#myform").attr("method","post");
			$("#myform").attr("target","paramIframe");
			closes();
			$("#myform").submit();
		}
	}

	function doSaveSysuserReturns(data){
		opens();
		jsonData = eval("(" + data + ")");
		if(jsonData.code == '1'){
			alert("����ɹ���");
		}else if(jsonData.code == '2'){
			alert("����ɹ���");
			window.close();
			window.opener.query_cmd();
		}else{
			alert("����ʧ�ܣ�" + decodeURI(jsonData.msg));
		}
	}
	function resetSysuserPwd(){
		if(confirm("�Ƿ�ȷ�������û���" + $("#xm").val() + "�������룿")){
			$("#myform").attr("action","<c:url value='/sysuser.vmc?method=resetSysuserPwd'/>");
			closes();
			$("#myform").attr("method","post");
			$("#myform").attr("target","paramIframe");
			$("#myform").submit();
		}
	}
	function doResetSysuserPwdReturns(data){
		opens();
		jsonData = eval("(" + data + ")");
		if(jsonData.code == '1'){
			alert("��������ɹ���");
		}else{
			alert("��������ʧ�ܣ�" + decodeURI(jsonData.msg));
		}
	}
	function checkxtgly(){
		var xtgly = $("input[name=xtgly]:checked").val();
		if(xtgly == 1){
			//ϵͳ����Ա:��
//		setckenabled(formedit.yyyhz);
//		setckywlxabled(formedit.kgywyhlx1,'');
//		document.getElementById("ksqx").style.display="";
//		document.getElementById("divkgywyhlx").style.display="";
			$("#kglqxTR").css("display", "");
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
		var oqxms = $("input[name=qxms]:checked").val();
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
			var kglqxAry = $("input[name=kglqx]:checked");
			for(i = 0; i < kglqxAry.size(); i++){
				roles += "A" + $(kglqxAry[i]).val();
			}
		}else{
			//�ɲ���Ȩ��
			var kczqxAry = $("input[name=kczqx]:checked");
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

	//�����û�Ȩ����Ϣ
	function saveuserrole() {
		//�����û������ȱ����û���Ϣ
		/**
		 var modal=formedit.modal.value;
		 if(modal=='new'){
		displayInfoHtml("���ȱ����û���Ϣ���ٽ�����Ȩ��");
		return false;
	}*/
		var t_glbm = $("#glbm").val();
		var t_yhdh = $("#yhdh").val();
		if(t_glbm == '' || t_yhdh == ''){
			alert("��Ҫ�����û���Ϣ���ٽ�����Ȩ��");
			return false;
		}

		var t_xtgly = $("input[name=xtgly]:checked").val();
		if(t_xtgly == 1){
			//ϵͳ����Ա
			if($("input[name=kglqx]:checked").size() <= 0){
				alert("��ѡ��ɹ���Ȩ�ޣ�");
				return false;
			}
		}
		closes();
		curUrl = "<c:url value='sysuser.vmc?method=saveSysuserRole&glbm='/>" + t_glbm + "&yhdh=" + t_yhdh;
		$("#roleform").attr("action", curUrl);
		$("#roleform").attr("method","post");
		$("#roleform").attr("target","paramIframe");
		$("#roleform").submit();
	}
	function doSaveSysuserRoleReturns(data){
		opens();
		jsonData = eval("(" + data + ")");
		if(jsonData.code == '1'){
			alert("����ɹ���");
		}else{
			alert("����ʧ�ܣ�" + decodeURI(jsonData.msg));
		}
	}

    function delSysuser(){
        Tools_NS.showSimpleConfirmDialog("�Ƿ�ȷ��ɾ����ǰ�û�?", function() {
            $.post("<c:url value='sysuser.vmc?method=remove'/>", decodeURIComponent($("#myform").serialize(), true),
            function(data) {
                if (data) {
                    if (data["resultId"] == "00") {
                        Tools_NS.showWarningDialog("ɾ���ɹ� " + data["resultMsg"], function() {
                            $.unblockUI();
                            window.close();
                            window.opener.query_cmd();
                        })
                    } else {
                        Tools_NS.showWarningDialog(data["resultMsg"]);
                    }
                } else {
                    Tools_NS.showWarningDialog("�ύʧ��");
                }
            }, "json");
        });
    }
	//-->
</script>
</body>
</html>
