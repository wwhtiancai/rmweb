<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.share.frm.service.GHtmlService" %>
<%@page import="java.util.*"%>
<%
Role role = (Role)request.getAttribute("frmRole");
// SysService sysService = (SysService)request.getAttribute("sysService");
GHtmlService sysService = (GHtmlService)request.getAttribute("gHtmlService");
List jbslist = (List)request.getAttribute("jbslist");
Department dept = (Department)request.getAttribute("department");
%>
<html>
<head>
<title>��ɫ����</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function query(){             //��ҳˢ��
	document.formain.action="role.frm?method=roleresult";
	document.formain.target="queryresult";
	document.formain.submit();
}
function init(){
}

function showdetail(jsdh) {
	openwin1024("role.frm?method=editOne&jsdh="+jsdh,"basadmin",false);
}
function create(jssx) {
	openwin1024("role.frm?method=newAdd&jssx="+jssx,"basadmin",false);
}

function fnOpen(){
  	var value=formain.glbm.value;
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("department.frm?method=choosedept&glbm="+value,
			 "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changebmdm(vReturnValue);
	}
}

//�����Ŵ���$��������
function changebmdm(vReturnValue){
	var idx=vReturnValue.indexOf("$");
	formain.glbm.value=vReturnValue.substring(0,idx);
	formain.bmmc.value=vReturnValue.substring(idx+1);
}

function fnOpenCxmc(){
	var sqms=formain.sqms.value;
	var sFeatures="dialogHeight:600px;dialogWidth:600px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("sysuser.frm?method=choosecdbhquery&sqms="+sqms, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changecxmc(vReturnValue);
	}
}   

//�����Ŵ���$��������
function changecxmc(vReturnValue){
	var valarray = vReturnValue.split("$");
	var xtlb=valarray[0];
	formain.cxmc.value=valarray[2];
	
	//�ж���cdbh����gnid
	formain.txtlb.value=xtlb;
	if(xtlb.length==2){
		formain.tcdbh.value=valarray[1];
	}else{
		formain.tgnid.value=valarray[1];
	}			
}	

function clearCxmc(){
	formain.cxmc.value="";
	formain.tcdbh.value="";
	formain.tgnid.value="";
	formain.txtlb.value="";
}

</script>
</head>
<script language="JavaScript" src="frmjs/tools.js" type="text/javascript"></script>
<body onLoad="init()" onUnload="closesubwin();">
<script language="JavaScript" type="text/javascript">vio_title('��ɫ����')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<form action="" method="post" name="formain" id="formain">
<input type="hidden" name="tcdbh" value=""/>
<input type="hidden" name="tgnid" value=""/>
<input type="hidden" name="txtlb" value=""/>
<input type="hidden" name="sqms" value="2"/>

<div class="s1" style="height: 3px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table"  align="center">
	<tr>
		<td width="10%" class="list_headrig">��ɫ����</td>
		<td class="list_body_out">
			<select name="jscj"  class="text_12 text_size_100">
			<%=sysService.transListToOptionHtml(jbslist, "", true, "3")%>
			</select>
		</td>
		<td width="10%" class="list_headrig">��ɫ����</td>
		<td class="list_body_out">
			<select name="jslx"  class="text_12 text_size_150">
			<option value="">ȫ��</option>
			<option value="1">��ӵ�н�ɫ</option>
			<option value="2" selected>�Զ����ɫ</option>
			<option value="3">�̳��Զ����ɫ</option>
			</select>
		</td>	
			
		<td width="10%" class="list_headrig">��ɫ����</td>
		<td class="list_body_out">
			<input type="text" name="jsmc" class="input_text" maxlength="12" class="text_12 text_size_150"/>
		</td>
	</tr>
	<tr>	
		<td width="10%" class="list_headrig">��������</td>
		<td class="list_body_out">
			<input type="text" name="bmmc" class="input_text" maxlength="12" class="text_12 text_size_150"/>
		</td>
		
		<td class="list_headrig">��������</td>
		<td class="list_body_out" align="left" colspan="3">
			<input type="text" name="cxmc" class="input_text text_readonly" maxlength="50" style="width:150" readOnly>
			<input type=button value="ѡ��" onclick="fnOpenCxmc();">
			<input type=button value="���" onclick="clearCxmc();">
		</td>		
		
	</tr>
	<tr>		
		<td align=right class="list_body_out" colspan="6">&nbsp;
			<%--input type=button class="button" value ="����(ATM)" onClick="create('3')"> --%>
			<input type=button class="button" value =" �� �� " onClick="create('2')">
			<input type="button" class="button" value=" �� ѯ " style="cursor:hand" alt="" onClick="query()">
			<input name="exit" type=button class="button" style="cursor:hand;"  value=" �� �� " onclick="quit()">
		</td>
	</tr>
	<tr>
		<td colspan="6" class="list_body_out"><B>1.</B>��ӵ�н�ɫ����������Ա���赱ǰ�û��ɹ���Ȩ�ޡ�<br>
			<B>2.</B>�Զ����ɫ����ǰ�û�������ɫ���̳���ӵ�н�ɫ��<br>
			<B>3.</B>�̳��Զ����ɫ���Զ����ɫ���¼���ɫ�����ϼ���ɫ���ϼ����ϼ���ɫ�ǵ�ǰ�û������Ľ�ɫ��</td>
	</tr>
</table>
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="5"></td>
	</tr>
</table>

<iframe src="" name="queryresult" id="queryresult" marginwidth="1"
		marginheight="1" hspace="0" vspace="0" scrolling="no" frameborder="0" 
		allowtransparency="true" style="width:100%;height:500"></iframe>


<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>