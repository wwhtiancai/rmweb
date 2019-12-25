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
//显示信息
function editone(glbm,yhdh) {
   var cdbh=userform.cdbh.value;
   if(cdbh=='R994'){
   	   openwin1024("sysuser.frm?method=editOne&modal=1&glbm=" + glbm + "&yhdh="+yhdh,"basadmin",false);
   }else if(cdbh=='R996'){
	   openwin1024("sysuser.frm?method=userView&modal=1&glbm=" + glbm + "&yhdh="+yhdh,"basadmin",false);
   }
}

//新建立
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
    //操作权限
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

//将部门代码$部门名称
function changecxmc(vReturnValue){
	userform.tcdbh.value="";
	userform.tgnid.value="";
	var valarray = vReturnValue.split("$");
	var xtlb=valarray[0];
	userform.cxmc.value=valarray[2];
	
	//判断是cdbh还是gnid,增驾系统类别
	userform.txtlb.value=xtlb;
	if(xtlb.length==2){
		userform.tcdbh.value=valarray[1];
	}else{
		userform.tgnid.value=valarray[1];
	}			
}	
//角色名称
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
	<td width="6%" class="list_headrig">用户代号：</td>
	<td width="15%" class="list_body_out" align="left">
		<input type="text" name="yhdh" class="input_text" maxlength="12" style="width:80">
	</td>
	<td width="6%" class="list_head" align="right">管理部门：</td>
	<td width="20%" class="list_body_out"  align="left">
		<input name="bmmc" type="text" value="<%=depObj.getBmmc()%>"
			class="text_12 text_size_150" readOnly> 包括下级 <input type=checkbox name=bhxj value="1">
	</td>
</tr>
<tr>	
	<td class="list_headrig">用户姓名：</td>
	<td class="list_body_out" align="left">
		<input type="text" name="xm" class="input_text" maxlength="50" style="width:150">
	</td>
	<td class="list_headrig">管理员：</td>
	<td class="list_body_out" align="left"><select name="xtgly" 
		class="text_12" style="width:100">
		<option value="">全部</option>
		<option value="1">是</option>
		<option value="2">否</option>
		</select>
	</td>	
</tr>
<tr>
    <%--	
	<td class="list_headrig">用户业务：</td>
	<td class="list_body_out" align="left">
		<select name="yhssyw"  class="text_12" style="width:100">
		<option value="">全部</option>
		<%=sysService.transListToOptionHtml(yhssywlist,"",false,"3")%>
		</select>
	</td>
	 --%>
	 
	<td class="list_headrig">权限类型：</td>
	<td class="list_body_out" align="left" colspan="3">
		<select name="qxms"  class="text_12" style="width:100">
		<option value="1" selected>操作权限</option>
		<option value="2">管理权限</option>
		</select>
		<select name="sqms"  class="text_12" style="width:100" onchange="changechoose(this.value);">
		<option value="1" selected>角色</option>
		<option value="2">功能</option>
		</select>
		<input type="text" name="cxmc" class="input_text text_readonly" maxlength="50" style="width:250" readOnly>
		<input type=button value="选择" onclick="fnOpenCxmc();">
		<input type=button value="清空" onclick="clearCxmc();">
	</td>
</tr>

<tr>
	<td class="list_headrig">操作权限：</td>
	<td class="list_body_out" align="left" colspan="3">
		<input type="radio"	name="sfsq" value="" checked>全部
		<input type="radio" name="sfsq" value="1">已授权
		<input type="radio" name="sfsq" value="2">未授权
	</td>
</tr>

<tr>	
    <td class="list_headrig">排序方式：</td>
	<td class="list_body_out" align="left">
	<select name="order" class="text_12  text_size_100">
      <option value="yhdh" selected>用户代号</option>
      <option value="xm">姓名</option>
      <option value="sfzmhm">身份证明号码</option>
	  </select><select name="proper" class="text_12  text_size_60">
	  <option value="asc" selected>升序</option>
	  <option value="desc">降序</option>
	  </select>
	</td>
	
	<td align=right class="list_body_out" colspan="2">&nbsp;
		<%if(cdbh.equals("R994")){ %>
		<input type=button class="button" value =" 新 增 " onClick="newone('<%=depObj.getGlbm() %>')">
		<%} %>
		<input type=button class="button" value=" 查 询 " style="cursor:hand" alt="" onClick="query()">
		<input type=button class="button" name=btexit onclick="parent.window.close();" value=" 退 出 ">
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