<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.framework.bean.FrmWsControl"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<html>
<%
	String str_tilte = "访问接口维护";
	FrmWsControl frmWsControl = (FrmWsControl)request.getAttribute("wsControl");
%>
<head>
<title><%=str_tilte%></title>
	<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
	<%=JspSuport.getInstance().JS_ALL%>
	<script language="javascript" type="text/javascript">
     function init()
     {
        var divContents="<c:out value='${divContents}'/>";
        getWsContents(divContents);
     }
   function getWsContents(resultStr)
   {
      var a = resultStr.split("$");
      var str="<table width='100%' height='100%' border='1' align='center' cellpadding='0' cellspacing='1'  id='SelectTable' valign='top'>";
      str+="<tr align='center' class='list_head'>";
      str+="<td width='10%' align='center'>序号</td>";
   	  str+="<td width='10%' align='center'>接口ID</td>";
   	  str+="<td width='40%' align='center'>接口名称</td>";
   	  str+="<td align='center'>日均访问量（次/天）</td>";
   	  str+="</tr>";   
      for(var i=0;i<a.length;i++)
      {
         var b=a[i].split('#');
         jkid=b[0];
         jkmc=b[1];
         fwcs=b[2];
         str+="<tr bgcolor='#ffffff' class='list_body_out' ";
       	 str+="style='cursor:hand'/>"; 
       	 str+="<td align='center' height='15'>"+(i+1)+"</td>"; 
         str+="<td align='center'>"+jkid+"</td>";
       	 str+="<td align='center'>"+jkmc+"</td>";
       	 str+="<td align='center'>"+fwcs+"</td>";
       	 str+="</tr>";               
       }
       str+="</table>";         
	   divWsContents.innerHTML=str;     
	}
     
    </script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:8px;"></div>
	<%=JspSuport.getInstance().getWebKey(session)%>		
	     <table border="0" cellspacing="1" cellpadding="0" class="list_table">  
				<tr>
					<td align="right" class="list_head">
						接口授权码&nbsp;
					</td>
					<td class="list_body_out" colspan="3" style="word-break:break-all" >
					    &nbsp;<%=frmWsControl.getJkxlh() %>							
					</td>
				</tr>  
				<tr>
					<td align="right" class="list_head" width="15%">
						安装点&nbsp;
					</td>
					<td class="list_body_out" width="35%">
					    &nbsp;<%=frmWsControl.getAzdm() %>						
					</td>				
					<td align="right" class="list_head" width="15%">
						系统名称&nbsp;
					</td>
					<td class="list_body_out" width="35%">
					    &nbsp;<%=frmWsControl.getDyrjmc() %>					
					</td>
				</tr>
				<tr>
					<td align="right" class="list_head">
						开发单位名称&nbsp;
					</td>
					<td class="list_body_out" colspan="3">
					    &nbsp;<%=frmWsControl.getDyrjkfdw() %>							
					</td>
				</tr>				
				<tr>
					<td align="right" class="list_head">
						使用单位名称&nbsp;
					</td>
					<td class="list_body_out">
					    &nbsp;<%=frmWsControl.getDyzdw() %>	
					</td>
					<td align="right" class="list_head">
						发证机关&nbsp;
					</td>
					<td class="list_body_out">
					    &nbsp;<%=frmWsControl.getDyfzjg()%>	
					</td>					
				</tr>
				<tr>
					<td align="right" class="list_head">
						IP开始地址&nbsp;
					</td>
					<td class="list_body_out">
					   &nbsp;<%=frmWsControl.getKsip() %>		
					</td>
					<td align="right" class="list_head">
						IP截止地址&nbsp;
					</td>		
					<td class="list_body_out">	
					  &nbsp;<%=frmWsControl.getJsip() %>
					</td>	
				</tr>
				<tr>
					<td align="right" class="list_head">
						接口启用日期&nbsp;
					</td>
					<td class="list_body_out">
						&nbsp;<%=frmWsControl.getJkqsrq() %>
					</td>	
					<td align="right" class="list_head">
						接口截止日期&nbsp;
					</td>
					<td class="list_body_out">
						&nbsp;<%=frmWsControl.getJkjzrq() %>
					</td>															
				</tr>
				<tr>
					<td align="right" class="list_head">
						系统功能简述&nbsp;
					</td>
					<td class="list_body_out" colspan="3">
					    <%=frmWsControl.getBz() %>						
					</td>
				</tr>
	
				<tr>
				   <td height="20" align="right" class="list_head">使用接口列表&nbsp;</td>
				   <td align="left" valign="top" class="list_body_out" colspan="3">
				       <span id="divWsContents"></span> 
				   </td>
	            </tr>				            
				<tr>
					<td colspan="4" class="list_body_out" align="right">
						<input name="exit" type="button" class="button" style="cursor:hand;"  value=" 退 出 " onclick="parent.window.close()">&nbsp;
					</td>
				</tr>
			</table>
	
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>


