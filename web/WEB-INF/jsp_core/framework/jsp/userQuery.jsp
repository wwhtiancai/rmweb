<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.service.SysuserManager"%>
<%@page import="java.util.List"%>
<html>
<head>
<%
String cdbh = (String)request.getAttribute("cdbh");
GSystemService gSystemService = (GSystemService)request.getAttribute("gSystemService");
List yhssywlist = (List)request.getAttribute("yhssywlist");
String cdmc = gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
Department depObj = (Department)request.getAttribute("department");
SysUser sysuser = (SysUser)gSystemService.getSessionUserInfo(session).getSysuser();
%>
<title>
</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
//��ʾ��Ϣ
function editone(glbm,yhdh) {
   var cdbh=userform.cdbh.value;
   if(cdbh=='R994'){
   	   openwin1024("sysuser.frm?method=editOne&modal=1&glbm=" + glbm + "&yhdh="+yhdh,"basadmin",false);
   }else if(cdbh=='R996'){
	   openwin1024("sysuser.frm?method=userView&modal=1&glbm=" + glbm + "&yhdh="+yhdh,"basadmin",false);
   }
}

//�½���
function newone(glbm) {
   openwin1024("sysuser.frm?method=newAdd&glbm=" + glbm,"basadmin",false);
}

function query(){
	var cdbh=userform.cdbh.value;
    if(cdbh=='R994'){
    	document.userform.action="sysuser.frm?method=userResult";
    }else if(cdbh=='R996'){
    	document.userform.action="sysuser.frm?method=userResulttrack";
    }	
    //����Ȩ��
    userform.czqx.value=getradio(userform.sfsq);
    document.userform.target="queryresult";
    document.userform.submit();
}

function fnOpenCxmc(){
	var sqms=userform.sqms.value;
	var sFeatures="dialogHeight:600px;dialogWidth:800px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("sysuser.frm?method=choosecdbhquery&sqms="+sqms, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		if(sqms=='1'){
			changejsmc(vReturnValue);
		}else if(sqms=='2'){
			changecxmc(vReturnValue);
		}	
	}
}   

//�����Ŵ���$��������
function changecxmc(vReturnValue){
	userform.tcdbh.value="";
	userform.tgnid.value="";
	var valarray = vReturnValue.split("$");
	var xtlb=valarray[0];
	userform.cxmc.value=valarray[2];
	
	//�ж���cdbh����gnid,����ϵͳ���
	userform.txtlb.value=xtlb;
	if(xtlb.length==2){
		userform.tcdbh.value=valarray[1];
	}else{
		userform.tgnid.value=valarray[1];
	}			
}	
//��ɫ����
function changejsmc(vReturnValue){
	var valarray = vReturnValue.split("$");
	userform.tjsdh.value=valarray[0];
	userform.cxmc.value=valarray[1];
}	


function clearCxmc(){
	userform.cxmc.value="";
	userform.tcdbh.value="";
	userform.tgnid.value="";
	userform.tjsdh.value="";
	userform.txtlb.value="";
}

function changechoose(val){
	clearCxmc();
}
</script>
</head>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<body onUnload="closesubwin();">
<form name="userform" action="" method="post" target="">
<input type="hidden" name="cdbh" value="<%=cdbh%>"/>
<input type="hidden" name="glbm" value="<%=depObj.getGlbm()%>"/>
<input type="hidden" name="kgywyhlx" value="<%=sysuser.getKgywyhlx()%>"/>


<input type="hidden" name="txtlb" value=""/>
<input type="hidden" name="tcdbh" value=""/>
<input type="hidden" name="tgnid" value=""/>
<input type="hidden" name="tjsdh" value=""/>

<input type="hidden" name="czqx" value=""/>
<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
<tr>
	<td width="6%" class="list_headrig">�û����ţ�</td>
	<td width="15%" class="list_body_out" align="left">
		<input type="text" name="yhdh" class="input_text" maxlength="12" style="width:80">
	</td>
	<td width="6%" class="list_head" align="right">�����ţ�</td>
	<td width="20%" class="list_body_out"  align="left">
		<input name="bmmc" type="text" value="<%=depObj.getBmmc()%>"
			class="text_12 text_size_150" readOnly> �����¼� <input type=checkbox name=bhxj value="1">
	</td>
</tr>
<tr>	
	<td class="list_headrig">�û�������</td>
	<td class="list_body_out" align="left">
		<input type="text" name="xm" class="input_text" maxlength="50" style="width:150">
	</td>
	<td class="list_headrig">����Ա��</td>
	<td class="list_body_out" align="left"><select name="xtgly" 
		class="text_12" style="width:100">
		<option value="">ȫ��</option>
		<option value="1">��</option>
		<option value="2">��</option>
		</select>
	</td>	
</tr>
<tr>
    <%--	
	<td class="list_headrig">�û�ҵ��</td>
	<td class="list_body_out" align="left">
		<select name="yhssyw"  class="text_12" style="width:100">
		<option value="">ȫ��</option>
		<%=sysService.transListToOptionHtml(yhssywlist,"",false,"3")%>
		</select>
	</td>
	 --%>
	 
	<td class="list_headrig">Ȩ�����ͣ�</td>
	<td class="list_body_out" align="left" colspan="3">
		<select name="qxms"  class="text_12" style="width:100">
		<option value="1" selected>����Ȩ��</option>
		<option value="2">����Ȩ��</option>
		</select>
		<select name="sqms"  class="text_12" style="width:100" onchange="changechoose(this.value);">
		<option value="1" selected>��ɫ</option>
		<option value="2">����</option>
		</select>
		<input type="text" name="cxmc" class="input_text text_readonly" maxlength="50" style="width:250" readOnly>
		<input type=button value="ѡ��" onclick="fnOpenCxmc();">
		<input type=button value="���" onclick="clearCxmc();">
	</td>
</tr>

<tr>
	<td class="list_headrig">����Ȩ�ޣ�</td>
	<td class="list_body_out" align="left" colspan="3">
		<input type="radio"	name="sfsq" value="" checked>ȫ��
		<input type="radio" name="sfsq" value="1">����Ȩ
		<input type="radio" name="sfsq" value="2">δ��Ȩ
	</td>
</tr>

<tr>	
    <td class="list_headrig">����ʽ��</td>
	<td class="list_body_out" align="left">
	<select name="order" class="text_12  text_size_100">
      <option value="yhdh" selected>�û�����</option>
      <option value="xm">����</option>
      <option value="sfzmhm">���֤������</option>
	  </select><select name="proper" class="text_12  text_size_60">
	  <option value="asc" selected>����</option>
	  <option value="desc">����</option>
	  </select>
	</td>
	
	<td align=right class="list_body_out" colspan="2">&nbsp;
		<%if(cdbh.equals("R994")){ %>
		<input type=button class="button" value =" �� �� " onClick="newone('<%=depObj.getGlbm() %>')">
		<%} %>
		<input type=button class="button" value=" �� ѯ " style="cursor:hand" alt="" onClick="query()">
		<input type=button class="button" name=btexit onclick="parent.window.close();" value=" �� �� ">
	</td>
</tr>
</table>
</form>

<span class="s4"><br><br></span>
<iframe src="" name="queryresult" id="queryresult" marginwidth="1"
	marginheight="1" hspace="0" vspace="0" scrolling="auto" frameborder="0" allowtransparency="true" 
	style="width:100%;height:520"></iframe>  
<iframe name="paramIframe" style="DISPLAY: none"></iframe>

</body>
</html>