<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<html>
<head>
<title>
任务管理
</title>
</head>
<script language="javascript">
function query_cmd(){             //本页刷新
document.formain.action="<c:url value="/qtm.frm?method=refresh"/>";
document.formain.submit();
}

function init(){
<c:if test="${sysUser!=null}">
formain.glbm.value="<c:out value='${sysUser.glbm}'/>";
formain.yhdh.value="<c:out value='${sysUser.yhdh}'/>";
</c:if>

}

//显示信息
function editTask(xtlb,rwid) {
	if(rwid=="databaseTaskManageTrigger"){
		alert("系统任务，不能修改");
	}else{
	    var windowheight=screen.height;
	    var windowwidth =screen.width;
	    windowheight=(windowheight-600)/4;
	    windowwidth=(windowwidth-800)/4;
	    var t=window.showModalDialog("<c:url value="/qtm.frm?method=editTask"/>&xtlb=" + xtlb + "&rwid="+rwid,"basadmin","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=100,height=200,top="+windowheight+",left="+windowwidth);   
	    query_cmd();
	}
}

function showlog(xtlb,rwid){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-600)/2;
	windowwidth=(windowwidth-800)/2;
	var v_rwid = rwid;
	if(rwid=="databaseTaskManageTrigger"){
		v_rwid = "9999";
	}	  
	window.open("<c:url value="/qtm.frm?method=initShowTaskLog"/>&xtlb="+xtlb + "&rwid="+v_rwid,"tasklog<c:out value='${userSession.sysuser.yhdh}'/>","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=600,top="+windowheight+",left="+windowwidth);

}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<script language="JavaScript" src="theme/style/style.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/tools.js" type="text/javascript"></script>
<body onload="init()">

<form name="formain" method="post"  action="">	
<div class="s1" style="height: 3px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
<tr>
<td width="13%" class="list_headrig">任务管理器启动时间&nbsp;</td>
<td class="list_body_out" width="200">
   <c:out value="${qsmd.runningSince}"/>
</td>
<td width="8%" class="list_headrig">总执行次数&nbsp;</td>
<td class="list_body_out" width="60">
   <c:out value="${qsmd.numJobsExecuted}"/>
</td>
<td width="10%" class="list_headrig">服务器IP地址&nbsp;</td>
<td class="list_body_out" title="设定的系统任务运行IP地址为<c:out value='${taskwebip}'/>">
   <c:out value="${ipdz}"/>
</td>
<td width="140" class="list_headrig">&nbsp;任务管理器状态&nbsp;</td>
<td class="list_body_out" width="130">
   <c:out value="${qsmd.zt}"/>
</td>
<td align=right class="list_body_out" width="160">&nbsp;
   <input type=button class="button" value =" 刷 新 " onClick="query_cmd()">&nbsp;<input name="exit" type=button class="button" style="cursor:hand;"  value=" 退 出 " onclick="parent.window.close()">
</td>
</tr>
</table>
</form>
<div class="s1" style="height:3px;"></div>
	<c:if test="${queryList!=null}">
	<script language="JavaScript" type="text/javascript">ts_title('系统任务列表')</script>			
<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
		<thead>
				   <tr align="center" class="detail_head">
					    <td>任务名称</td>
						<td>上次执行时间</td>
						<td>下次执行时间</td>
						<td>状态</td>
						<td>执行结果</td>
						<td>日志信息</td>
				  </tr>
		<%int i=0;%>
<c:forEach items="${queryList}" var="current">	
<% i=i+1;
String str_class = "list_body_tr_1";
 if (i % 2 == 0) {
str_class = "list_body_tr_2";}%>
<tr class="<%=str_class%>"	height="23" title="双击鼠标可修改任务属性" onMouseOver="this.className='list_body_over'" onMouseOut="this.className='<%=str_class%>'" style="cursor:hand" onDblClick="editTask('<c:out value="${current.xtlb}"/>','<c:out value="${current.rwid}"/>')"">
						<td height="20"><c:out value="${current.rwmc}"/></td>
						<td height="20"><c:out value="${current.previousFireTime}"/></td>
						<td><c:out value="${current.nextFireTime}"/></td>
						<td><c:out value="${current.zt}"/></td>
						<td><c:out value="${current.jobResult}"/></td>
						<td align="center"><input type=button class="button" value ="查看日志" onClick="showlog('<c:out value="${current.xtlb}"/>','<c:out value="${current.rwid}"/>')"></td>
					</tr>
 		</c:forEach>
				</thead>
		 		
		  </table>
</c:if>

<div class="s1" style="height:8px;"></div>
    <c:if test="${qctxList!=null}">
	<script language="JavaScript" type="text/javascript">ts_title('正在执行的任务列表')</script>	
		
<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
		<thead>
				   <tr align="center" class="detail_head">
					    <td width="25%" >任务名称</td>
					    <td width="20%" >开始执行时间</td>
						<td width="20%" >已持续时间</td>
						<td width="35%" >当前执行结果</td>
				  </tr>
		<c:forEach items="${qctxList}" var="current">
		<tr class="list_body_out" onMouseOver="this.className='list_body_over'" onMouseOut="this.className='list_body_out'" style="cursor:hand">
						<td><c:out value="${current.name}"/></td>
						<td><c:out value="${current.fireTime}"/></td>
						<td><c:out value="${current.jobRunTime}"/></td>
						<td title="<c:out value="${current.jobFullResult}"/>"><c:out value="${current.jobResult}"/></td>
					</tr>
 		</c:forEach>
				</thead>
		  </table>
		  </c:if>

</body>
</html>
