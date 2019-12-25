<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.framework.bean.FrmWsControl"%>
<%@page import="java.util.List"%>
<%
	String str_tilte = "接口授权码查询";
	List queryList = (List)request.getAttribute("queryList");
%>
<html>
<head>
<title><%=str_tilte%></title>
</head>
    <%=JspSuport.getInstance().JS_ALL%>	
	<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
	<script language="javascript" src="frmjs/ajax_func.js" type="text/javascript"></script>
<script language="javascript">
function init(){
    formain.dyrjmc.value="";
    <c:if test="${tmpAppForm!=null}">  
        formain.dyrjmc.value="<c:out value='${tmpAppForm.dyrjmc}'/>";
    </c:if>  
}

//显示信息
function showDetail(jkxlh)
{
    var windowheight=screen.height;
    var windowwidth =screen.width;
    windowheight=(windowheight-560)/2;
    windowwidth=(windowwidth-700)/2;
    var spath="<c:url value="/ws.frm?method=editWsControl"/>&jkxlh="+jkxlh;
    window.open(spath,"basadmin","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=700,height=560,top="+windowheight+",left="+windowwidth);
}

function reload()
{
   window.location.reload();
}
function sync_cmd()
{
      var spath="<c:url value="/ws.frm?method=syncWsControlList"/>";
      send_request(spath,"showSynResult()",false); 
}
function showSynResult()
{
    	var xmlDoc = _xmlHttpRequestObj.responseXML;
        var head=xmlDoc.getElementsByTagName("head");
   	    var m_code=head[0].getElementsByTagName("code")[0].firstChild.nodeValue;
   	    var m_des=head[0].getElementsByTagName("message")[0].firstChild.nodeValue;
   	    if(m_code==null||m_code=="null")
        {
            m_code="0";
        }
        if(m_code=="1")
   		{
   		    var msg="数据同步成功！"
   		    alert(msg);
   		    //displayInfoHtml_new("00","R982",MSG_RANK_OK,"1",msg);   	
   		    query_cmd();	    
   		}
   		else
   		{
   		    displayInfoHtml_new("00","R982",MSG_RANK_FE,"2",m_des);
   		    displayInfoHtml("[00R9821J2]:"+m_des);
   		}
}
function query_cmd()
{
      //本页刷新
      document.formain.action="<c:url value="/ws.frm?method=queryWsControlList"/>";
      document.formain.submit();  
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/tools.js" type="text/javascript"></script>
<body onLoad="init()" onUnload="closesubwin();">
		<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
		<script language="JavaScript" type="text/javascript">vio_seach()</script>
		<div class="s1" style="height: 3px;"></div>
		<form name="formain" method="post" action="">
			<table border="0" cellspacing="1" cellpadding="0"
				class="list_table text_12" align="center">
				<tr>
					<td width="60" class="list_head">
						&nbsp;外挂系统&nbsp;
					</td>
					<td width="200" class="list_head">
						<select name="dyrjmc" style="width: 180">
				    		<option value="" selected>--全部--</option>
							<c:forEach items="${wgxtList}" var="current1">
							 <option value="<c:out value='${current1.dyrjmc}'/>">
								<c:out value="${current1.dyrjmc}" />
							 </option>
						</c:forEach>
						</select>
					</td>				
					<td align="right" class="list_body_out">
						<input type="button" class="button" value=" 查 询 " style="cursor:hand" onClick="query_cmd()">
						<input type="button" class="button" value=" 数据同步 " style="cursor:hand" onClick="sync_cmd()">
						<input type="button" name="Submit2" class="button" onclick="quit()" value=" 退 出 ">
					</td>
				</tr>
			</table>
		</form>

		<div class="s1" style="height: 3px;"></div>
		<script language="JavaScript" type="text/javascript">ts_title('查询结果-接口授权码列表')</script>
		<table width="98%" border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<col/>
				<col/>
				<col/>
				<col/>
				<col/>
		<thead>
			<tr align="center" class="detail_head">
				<td width="4%">序号</td>
				<td width="25%">外挂系统</td>
				<td width=20%">开发单位</td>
				<td width="16%">使用单位</td>
				<td width="5%">发证机关</td>
				<td width="30%">接口授权码</td>
			</tr>
			<%
			FrmWsControl wsControl = null;
			for(int i=0;i<queryList.size();i++){
				wsControl = (FrmWsControl)queryList.get(i);				
				String str_class = "list_body_tr_1";
        	if (i % 2 == 0) {
          		str_class = "list_body_tr_2";
        	}	  	 %>						
                    <tr height="23" style="cursor:hand" class="<%=str_class%>" onMouseOver="this.className='list_body_over'"
						onMouseOut="this.className='list_body_out'"
						onClick="showDetail('<%=wsControl.getJkxlh() %>')" title="单击鼠标左键，进入详细信息界面">
					  <td align="center" height="10"><%=(i + 1)%></td>
						<td style="word-break:break-all"><%=wsControl.getDyrjmc() %></td>
						<td style="word-break:break-all"><%=wsControl.getDyrjkfdw() %></td>
			          	<td style="word-break:break-all"><%=wsControl.getDyzdw()%></td>    				
			          	<td align="center"><%=wsControl.getDyfzjg() %></td>						
			          	<td style="word-break:break-all"><%=wsControl.getJkxlh() %></td>						
					</tr>
			<%}
			 %>

  </thead>
</table>
<script language="JavaScript" type="text/javascript">vio_down()</script>

</body>
</html>
