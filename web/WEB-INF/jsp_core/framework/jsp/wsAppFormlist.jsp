<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.framework.bean.FrmWsAppForm"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="java.util.List"%>
<%
	List queryList = (List)request.getAttribute("queryList");
%>
<html>
	<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
	<%=JspSuport.getInstance().JS_ALL%>	
	<script language="javascript" type="text/javascript">
  function init()
  { 
  }
  function query_cmd()
  {   
      //本页刷新
      var dyrjmc=document.all.dyrjmc.value;
      var dyrjkfdw=document.all.dyrjkfdw.value;
      var spath="<c:url value="/ws.frm?method=queryAppFormList"/>&dyrjmc="+dyrjmc+"&dyrjkfdw="+dyrjkfdw;
      spath=encodeURI(encodeURI(spath));
      document.formain.action=spath;
      document.formain.submit();
  }
  
  function create()
  {
    var spath="<c:url value="/ws.frm?method=editJkqx"/>";
    openwin(spath,"basadmin",false);    
  }
  function showdetail(azdm,dyrjmc)
  {
     parent.showWsAppForm(azdm,dyrjmc);
  }
  </script>

<link href="theme/style/style.css" rel="stylesheet" type="text/css">
	<body onload="init()">
		<div class="s1" style="height: 3px;"></div>
		<form name="formain" method="post" action="">
		<table  align="center" border="0" cellspacing="1" cellpadding="0"class="list_table text_12" width="100%">
		  <tr>
		     <td class="list_head">&nbsp;系统名称&nbsp;</td>
		     <td class="list_body_out" width="60">
		        <input name="dyrjmc" onfocus="movelast();"  type="text" class="text_12" style="width:60">
		     </td>
		     <td class="list_head">&nbsp;开发单位&nbsp;</td>
		     <td class="list_body_out" width="60">
		        <input name="dyrjkfdw" onfocus="movelast();"  type="text" class="text_12" style="width:60">
		     </td>		     
		     <td align="right" class="list_body_out">
				&nbsp;<input type="button" class="button" value=" 查 询 " style="cursor:hand" onClick="query_cmd()">
			 </td>
		  </tr>
		</table>
		</form>
		<div class="s1" style="height: 3px;"></div>
		<c:if test="${queryList!=null}">
		    <div   style= "height:90%;overflow-y:auto; "> 
			<table align="center"  border="0" cellspacing="1" cellpadding="0" class="detail_table">
				<col />
				<col />
				<col />
				<col />
				<col />
				<thead>
					<tr class="detail_head">					  
					  <td align="center">系统名称</td>
					  <td width="80" align="center">上传标记</td>				  
					</tr>
					<%
					FrmWsAppForm wsAppForm =null;
					for(int i=0;i<queryList.size();i++){
						wsAppForm = (FrmWsAppForm)queryList.get(i);
						String str_class = "list_body_tr_1";
        	            if (i % 2 == 0) {
          		            str_class = "list_body_tr_2";
        	            }	  	 
						%>						
					<tr  class="<%=str_class%>"  height="23"  align="center"
							onMouseOver="this.className='list_body_over'"
							onMouseOut="this.className='<%=str_class%>'" style="cursor: hand"
							onClick="showdetail('<%=wsAppForm.getAzdm() %>','<%=wsAppForm.getDyrjmc() %>')">
						<td ><%=wsAppForm.getDyrjmc() %></td>
						<td>
						    <%if(wsAppForm.getScbj().equals("0")){%>
						       未上传
						    <%}else{ %>
						    已上传
						    <%}%>
						</td>						
					</tr>
					<%} %>
				</thead>
			</table>
			</div>
		</c:if>
	</body>
</html>

