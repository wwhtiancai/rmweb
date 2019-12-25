<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>${cxmc}</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/date_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function(){	
})



</script>
</head>
<body onUnload="closesubwin()">
<div id="panel" style="display:none">
<div id="paneltitle">${cxmc}</div>

<div class="queryresult"></div>
<div id="result">
	<div id="resulttitle">查询内容</div>
	<table border="0" id="query_table" cellspacing="1" cellpadding="0" class="list">
		<col width="10%">
		<col width="25%">
		<col width="10%">		
		<col width="55%">
        <tr class="head">
        	<td>执行序号</td>
        	<td>主键信息</td>
        	<td>结果标记</td>
        	<td>返回信息</td>  
        </tr>     
        <c:forEach items="${queryList}" var="current" varStatus="status">        	
            <tr class="out" onMouseOver="this.className='over'"
                onMouseOut="this.className='out'" style="cursor: pointer">                
                <td align="center"><c:out value="${current.zxxh}"/></td>
                <td align="center"><c:out value="${current.zjxx}"/></td>
                <td align="center">
                	<c:if test="${current.jgbj=='1'}">
                		成功
                	</c:if>
                	<c:if test="${current.jgbj!='1'}">
                		失败
                	</c:if>            	              	             	
                </td>
                <td align="left"><c:out value="${current.fhxx}"/></td>                
            </tr>
        </c:forEach> 
        </form>        
		<tr>
			<td colspan="4" class="page">
			<c:out value="${controller.clientScript}" escapeXml="false"/>
			<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
			</td>
		</tr>

    </table>
</div>
</div>
</body>
</html>