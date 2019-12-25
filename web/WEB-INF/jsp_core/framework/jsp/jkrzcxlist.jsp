<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.framework.bean.FrmWsLog"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="java.util.List"%>
<%
	String str_tilte = "接口日志查询";
	List jkrzcxlist = (List)request.getAttribute("jkrzcxlist");
	String jkoptions = (String)request.getAttribute("jkoptions");
	FrmWsLog wsLog = (FrmWsLog)request.getAttribute("wslog");
%>
<html>
	<head>
		<title><%=str_tilte%></title>
	</head>
	<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
	<%=JspSuport.getInstance().JS_ALL%>	
	<script language="JavaScript" src="frmjs/frmws.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript">
  function init()
  {
      formain.jkdysj.value=getTodayN(-7);
      formain.jkdysj1.value=getTodayN(0);
      <c:if test="${wsLog!=null}">
         formain.xtlb.value="<c:out value='${wsLog.xtlb}'/>";
	     formain.jkid.value="<c:out value='${wsLog.jkid}'/>";
	     formain.jkdysj.value="<c:out value='${wsLog.jkdysj}'/>";
	     formain.jkdysj1.value="<c:out value='${wsLog.jkdysj1}'/>";
	  </c:if>  
  }

  function query_cmd()
  {   
      //本页刷新
      document.formain.action="<c:url value="/ws.frm?method=queryRzcx"/>";
      document.formain.submit();
  }
  
  </script>

<link href="theme/style/style.css" rel="stylesheet" type="text/css">
	<body onload="init()"  onUnload="closesubwin();">
		<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
		<script language="JavaScript" type="text/javascript">vio_seach()</script>
		<div class="s1" style="height: 3px;"></div>
		<form name="formain" method="post" action="">
			<table border="0" cellspacing="1" cellpadding="0"
				class="list_table text_12" width="100%">
				<tr>
					<td width="60" class="list_head">
						&nbsp;业务种类&nbsp;
					</td>
					<td width="120" class="list_head">
						<select name="xtlb" style="width: 100" onchange="query_jkid()">
				    		<option value="" selected>--全部--</option>
							<c:forEach items="${ywlbList}" var="current1">
								<option value="<c:out value='${current1.dmz}'/>">
								<c:out value="${current1.dmsm1}" />
							</option>
						</c:forEach>
						</select>
					</td>				
					<td width="70" class="list_headrig">
						&nbsp;&nbsp;接口名称&nbsp;
					</td>
					<td class="list_body_out" width="120"><select name="jkid" class="text_12" style="width:180">
							<%=jkoptions %>
						</select>
					</td>
					<td width="70" class="list_headrig">调用时间&nbsp;</td>
					<td class="list_body_out" width="250"><input name="jkdysj" onfocus="movelast();"  type="text" class="text_12" style="width:68">
						<img name="popcal" align="absmiddle" src="frmjs/cal/calbtn.gif" width="34" height="22" border="0" onClick="riqi('jkdysj')" style="cursor:hand">
						至
						<input name="jkdysj1" onfocus="movelast();"  type="text" class="text_12" style="width:68">
						<img name="popcal" align="absmiddle" src="frmjs/cal/calbtn.gif" width="34" height="22" border="0" onClick="riqi('jkdysj1')" style="cursor:hand">						
					</td>					
					<td align="right" class="list_body_out">
						<input type="button" class="button" value=" 查 询 " style="cursor:hand" onClick="query_cmd()">
						<input type="button" name="Submit2" class="button" onclick="quit()" value=" 退 出 "></td>
				</tr>
			</table>
		</form>
		<div class="s1" style="height: 3px;"></div>
		<%if(jkrzcxlist!=null){%>
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table">
				<col />
				<col />
				<col />
				<col />
				<col />
				<thead>
					<tr class="detail_head">
					  <td width="60" align="center">接口ID</td>
					  <td width="80" align="center">接口名称</td>
					  <td width="120" align="center">调用时间</td>					  
					  <td width="100" align="center">调用IP</td>
					  <td align="center">返回信息</td>
					</tr>
					<%
					FrmWsLog wslog =null;
					for(int i=0;i<jkrzcxlist.size();i++){
						wslog = (FrmWsLog)jkrzcxlist.get(i);
						String str_class = "list_body_tr_1";
        	if (i % 2 == 0) {
          		str_class = "list_body_tr_2";
        	}	  	 
						%>
						
					<tr  class="<%=str_class%>"  height="23"
							onMouseOver="this.className='list_body_over'"
							onMouseOut="this.className='<%=str_class%>'" style="cursor: hand">
						<td align="center">
						    <%=wslog.getJkid() %>
						</td>
						<td align="center">
							<%=wslog.getJkmc() %>
						</td>
						<td align="center">
							<%=DateUtil.formatDate(wslog.getFwsj(),"yyyy-MM-dd hh:mm:ss","yyyy-MM-dd hh:mm:ss") %>
						</td>						
						<td>
							<%=wslog.getIp() %>
						</td>
						<td align="center">
							<script>outputSkipStr(30,"<%=wslog.getFhxx() %>")</script>
						</td>
					</tr>
					<%} %>
		 		<tr>
				<td colspan="5" align="right" class="page">
					<c:out value="${controller.clientScript}" escapeXml="false"/>
					<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
	    		</tr>					
				</thead>
			</table>
		<%}%>
		<script language="JavaScript" type="text/javascript">vio_down()</script>
		<%=JspSuport.getInstance().outputCalendar() %>
	</body>
</html>

