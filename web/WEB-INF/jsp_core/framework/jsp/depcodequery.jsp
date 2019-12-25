<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="java.util.List"%>
<%@page import="com.tmri.share.frm.bean.Department"%>
<% 
String cdbh = (String)request.getAttribute("cdbh");
SysService sysService = (SysService)request.getAttribute("sysService");
GSystemService gSystemService = (GSystemService)request.getAttribute("gSystemService");
GHtmlService gHtmlService = (GHtmlService)request.getAttribute("gHtmlService");

String cdmc = gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
Department depObj = gSystemService.getSessionUserInfo(session).getDepartment();
List gnidList = (List)request.getAttribute("gnidList");
%>
<html>
<head>
<title><%=cdmc%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<script language="JavaScript">
function init(){
	document.formmain.dmlb.focus();
}

function query(){
	if(check()){  
		var cklx=getradio(formmain.oglbm);
		formmain.cklx.value=cklx;
	    //本页刷新
	  	document.formmain.action="depcode.frm?method=depcoderesult";
	  	document.formmain.target="queryresult";
	  	document.formmain.submit();
	}
}

function check(){
    if(checknull(formmain.dmlb,"代码类别",true)!="1"){
		 return false;
   }
	return true;
}

function adddetail(dmz){
    //本页刷新
	formedit.dmlb.value=formmain.dmlb.value;
	formedit.dmz.value=dmz;
  	openwin1024("","depcodeedit",false);
  	document.formedit.action="depcode.frm?method=depcodeedit";
  	document.formedit.target="depcodeedit";
  	document.formedit.submit();  	
}


function showdetail(glbm,dmlb,dmz){
    //本页刷新
    formedit.glbm.value=glbm;
	formedit.dmlb.value=dmlb;
	formedit.dmz.value=dmz;
  	openwin1024("","depcodeedit",false);
  	document.formedit.action="depcode.frm?method=depcodeedit";
  	document.formedit.target="depcodeedit";
  	document.formedit.submit();  	
}

function fnOpen(){
  	var value=formmain.glbm.value;
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("department.frm?method=choosedept&minbmjb=4&flag=2&glbm="+value, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changebmdm(vReturnValue);
	}
}

//将部门代码$部门名称
function changebmdm(vReturnValue){
	var idx=vReturnValue.indexOf("$");
	formmain.glbm.value=vReturnValue.substring(0,idx);
	formmain.bmmc.value=vReturnValue.substring(idx+1);
}


function changedmlb(val){
	if(val==''){
		formmain.btadd.disabled=true;
	}else{
		formmain.btadd.disabled=true;
		var spath="depcode.frm?method=queryDeptcode&dmlb="+val;
		send_request(spath,"filldmlb()",false);				
	}		
}


function filldmlb(){
	var xmlDoc = _xmlHttpRequestObj.responseXML;
	var code=getsingletagval(xmlDoc,"code"); 
	var message=getsingletagval(xmlDoc,"message");    
	if(code==1){
		formmain.btadd.disabled=false;
	}else{
		formmain.btadd.disabled=true;	
	}		 	
}


</script>

<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<body onload="init();">
<script language="JavaScript" type="text/javascript">vio_title('<%=cdmc%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<div class="s1" style="height: 3px;"></div>
<form name="formmain" method="post" action="">
<input type="hidden" name="cdbh" value="<%=cdbh%>"/>
<input type="hidden" name="cklx" value=""/>
<input type="hidden" name="glbm" value="<%=depObj.getGlbm()%>"/>
<table border="0" align="center" cellpadding="0" cellspacing="1" class="list_table">
  <tr>
    <td width="10%" class="list_head" align="right">当前部门&nbsp;</td>
    <td width="" class="list_body_out">
    	<input type="text" readOnly name="bmmc" value="<%=depObj.getBmmc()%>"/> <input type=button
			value="选择" onclick="fnOpen();" class="button_query" style="height: 19">
			包括下级 <input type=checkbox name=bhxj value="1">
    </td>     
    <td width="10%" class="list_head" align="right">代码类别&nbsp;</td>
    <td width="%" class="list_body_out"><select name="dmlb" class="text_12  text_size_140" onchange="changedmlb(this.value);">
			<%=gHtmlService.transListToOptionHtml(gnidList, "", true, "3")%>
    	</select></td>
    <td width="10%" class="list_head" align="right">代码名称&nbsp;</td>
    <td width="" class="list_body_out">
    	<input type="text" name="dmsm1" value=""/></td>  
  </tr>
  <tr>
    <td width="10%" class="list_head" align="right">查询类型&nbsp;</td>
    <td width="" class="list_body_out">
			<input checked type="radio" name="oglbm" value="1">根据创建部门
			<input type="radio" name="oglbm" value="2">根据使用部门
    </td>      	    
    <td  align="right" class="list_body_out" colspan="4">
      <input name="btadd" disabled type="button" class="button" value=" 新 增  "  onclick="adddetail('');">
      <input name="btquery" type="button" class="button" value=" 查 询 "  onclick="query();">
      <input name="btclose" type="button" class="button" value=" 退 出 "  onclick="quit();">
    </td>     

  </tr>
  <tr>
    <td colspan="6" class="list_body_out" align="left">备注:&nbsp;除“驾校名称”外，其它都可以新增。维护原则：部门谁创建谁维护。</td>
  </tr>  
</table>
</form>
<div class="s1" style="height: 3px;"></div>
<iframe src="" name="queryresult" id="queryresult" marginwidth="1"
		marginheight="1" hspace="0" vspace="0" scrolling="auto" frameborder="0" 
		allowtransparency="true" style="width:100%;height:500"></iframe>
		

<script language="JavaScript" type="text/javascript">vio_down()</script>
<form name="formedit" method="post" action="" target="paramIframe">
<input type="hidden" name="glbm" value=""/>
<input type="hidden" name="dmlb" value=""/>
<input type="hidden" name="dmz" value=""/>
</form>
<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>


</body>
</html>
