<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@ page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>��ɫȨ��ά��</title>
<%
	Department dept = (Department)request.getAttribute("department");
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");
 %>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script languag="javascript">
function init(){
	var spath="role.frm?method=queryProgramListByYhdh";
	divroles.innerHTML="";
	send_request(spath,"filljscj()",false);
}


function filljscj(){
	//�����Ͻ����
	var results = _xmlHttpRequestObj.responseText;
	divroles.innerHTML=results;
	domtab.init();
}

function saveForm(){
	var yhzs = "";
	var cddhs = "";
	var tmpsx = "";
	var tmpsx1 = "";
	var tmpsx2 = "";
	var j = 0;
	var k =0;
	if(document.all.cddh){
		if (document.all.cddh.type=="checkbox"){
			//����
			if (document.all["cddh"].checked){
				tmpsx=document.all["cddh"].value;
				j = tmpsx.indexOf("-");
				cddhs = tmpsx.substring(j + 1);
			}else{
				cddhs="";
			}
		}else{
			 var cddh_length=document.all["cddh"].length;
			 for(var i=0;i<cddh_length;i++){	
				 if(document.all["cddh"].item(i).checked==true) {
					 tmpsx=document.all["cddh"].item(i).value;
					 j = tmpsx.indexOf("-");
					 tmpsx = tmpsx.substring(j + 1);
					 j = getCharCounts(tmpsx,"-");
					 if(j==1){
					 	j = cddhs.indexOf(tmpsx);
					 	if(j<0){
					 		cddhs = cddhs + tmpsx + "-#";
					 	}
					 }else{
					 	j = tmpsx.lastIndexOf("-");
					 	tmpsx1 = tmpsx.substring(0,j + 1);
					 	tmpsx2 = tmpsx.substring(j+1);
					 	j = cddhs.indexOf(tmpsx1);
					 	if(j<0){
					 		cddhs=cddhs+tmpsx+"#";
					 	}else{
					 		k = cddhs.indexOf("#",j);
					 		tmpsx1 = cddhs.substring(j,k);
					 		if(tmpsx1.substring(tmpsx1.length-1)=="-"){
					 			tmpsx1 = tmpsx1 + tmpsx2;
					 		}else{
					 			tmpsx1 = tmpsx1 + "," + tmpsx2;
					 		}
					 		cddhs=cddhs.substring(0,j)+tmpsx1+cddhs.substring(k);
					 	}
					 }				 
				 }
			 }
			if (cddhs.substring(cddhs.length-1)=="#") {
				cddhs=cddhs.substring(0,cddhs.length-1);
			}
		}
	}
	document.formedit.cxdh.value=cddhs;
	if(checknull(document.all["cxdh"],"Ȩ��",false)!="1")return false;
	//�жϳ�������Ƿ�Ψ��
	if(confirm("ȷ��ȡ���ò����û�������Ȩ�޵�ĳЩ����ô��"
			+"�ٴ�ȷ���Ƿ�ɾ��?")){
		document.formedit.action="role.frm?method=saveUsermenuCancel";
		document.formedit.submit();
	}
}



//���ؽ��
function resultsubmit(code,result,message){
  if(code=='1'){
	displayInfoHtml(message);
  }else {
	displayInfoHtml(message);
  }
}

function tab_c(idx){
	var tab=document.getElementsByName("tab")
	for(var i=0;i<tab.length;i++) {
		tab[i].style.display="none";
	}
	var idxtab=document.getElementById("tab"+idx);
	idxtab.style.display=""
}


function chooseselect(){
	clearck(formedit.cddh);
}

function fnOpen(){
  	var value=formedit.glbm.value;
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("department.frm?method=choosedept&glbm="+value, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changebmdm(vReturnValue);
	}
}

//�����Ŵ���$��������
function changebmdm(vReturnValue){
	var idx=vReturnValue.indexOf("$");
	formedit.glbm.value=vReturnValue.substring(0,idx);
	formedit.bmmc.value=vReturnValue.substring(idx+1);
}  

function check_item(obj){
	var bj=obj.value;
	var qx_length=document.all["cddh"].length;
	if(obj.checked==true){
		for(var i=0;i<qx_length;i++){
			if(document.all["cddh"].item(i).value.indexOf(bj)==0){
				document.all["cddh"].item(i).checked=true;
			}
		}
	}else{
		for(var i=0;i<qx_length;i++){
			if(document.all["cddh"].item(i).value.indexOf(bj)==0){
				document.all["cddh"].item(i).checked=false;
			}
		}
	}
}
</script>
<link href="theme/domtab/domtab.css" rel="stylesheet" type="text/css"> 
<script language="javascript" type="text/javascript"  src="theme/domtab/domtab.js"></script>

<body onLoad="init();">
<script language="JavaScript" type="text/javascript">vio_title('����Ȩ��ȡ��')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:4px;"></div>
	<form name="formedit" action="" method="post" target="paramIframe">
    <input type="hidden" name="cxdh">
    <input type="hidden" name="glbm" value="<%=dept.getGlbm()%>">
	<%=JspSuport.getInstance().getWebKey(session)%>
	<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
		<tr>
			<td colspan="4" class="list_body_out" align="right" >
			<input type="button" name="btselect" onClick="chooseselect();" value=" ȫ��ѡ " class="button">
			<input type="button" name="savebutton" onClick="saveForm()" value=" �� �� " class="button">
	       	<input type="button" name="Submit2" value=" �� �� " onclick="quit()" class="button"></td>
		</tr>	
		<tr>
			<td  class="list_headrig" width="10%">������</td>
			<td class="list_body_out" colspan="3" align="left"><input name="bmmc" value="<%=gDepartmentService.getDepartmentName(dept.getGlbm())%>" 
				type="text"class="text_12 text_size_150 text_readonly" readOnly><input type=button value="ѡ��" onclick="fnOpen();" 
				class="button_query" style="height:19"> �����¼�   <input type=checkbox name=bhxj value="1"></td>
		</tr>

		<tr>
			<td class="list_headrig">ȡ��Ȩ��</td>
			<td class="list_body_out" colspan="3" id="divroles" height="400"></td>
		</tr>
	</table></form>
	
<script language="JavaScript" type="text/javascript">vio_down();</script>

<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
</body>
</html>