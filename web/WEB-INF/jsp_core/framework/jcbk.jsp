<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.rm.bean.*"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>ȫ�����鲼��ϵͳ�Ѱ�װʡ��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${queryList!=null}">
			$("[limit]").limit();
		</c:if>
		showData();
	});
	function query_cmd(){
		$("#myform").attr("action","<c:url value='/jcbk.frm?method=jcbkList'/>");
		closes();
		$("#myform").submit();
	}

	function showData(){
		var html = "<table border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"list\">" + 
		"<col width=\"30%\">" + "<col width=\"70%\">" + 
		"<tr class=\"head\"><td>ʡ��</td><td>����</td></tr>";
		<%
		List<RmVersion> list = (List<RmVersion>)request.getAttribute("queryList");
		for(int i = 0; i < list.size(); i++){%>
			html = html + "<tr class=\"out\" onMouseOver=\"this.className='over'\" onMouseOut=\"this.className='out'\" style=\"cursor:pointer\" >"
					+ "<td align=\"center\" class=\"body\"> <%=list.get(i).getSf()%> </td>" + 
					"<td align=\"center\" class=\"body\">";
			<%String ds[] = list.get(i).getDs().split(" ");
			String fzjg[] = list.get(i).getFzjg().split(" ");
			for(int j = 0; j < ds.length; j++){%>
				html = html + "<a id='<%=fzjg[j]%>' href=\"#\" onclick=\"gotoDsJcbk(' <%=fzjg[j]%>' )\">" + "<%=ds[j]%> </a>" + "&nbsp;&nbsp;";
			<%}%>
			html = html + "</td></tr>";
		<%}%>
		html = html + "</table>";
		$("#tableData").html(html);
		$("[limit]").limit();
	}

	function gotoDsJcbk(fzjg){
		window.open("jcbk.frm?method=getUrl&fzjg="+fzjg);
	}
	
</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">ȫ�����鲼��ϵͳ�Ѱ�װʡ��</div>
	<div id="query">
		<div id="querytitle">��ѯ����</div>
		<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
		</table>
		</form>
	</div>
	<c:if test="${queryList!=null}">
	<div id="result" style="margin-top:6px">
		<div id="resulttitle">��ѯ����</div>
		<!--<table border="0" cellspacing="1" cellpadding="0" class="list">
			<col width="30%">
			<col width="70%">
			<tr class="head">
				<td>ʡ��</td>
				<td>����</td>
			</tr>
			<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" >
				<td align="center" class="body"><c:out value="${current.sf}"/></td>
				<td align="center" class="body"><c:out value="${current.ds}"/></td>
			</tr>
			</c:forEach>
		</table>
		--><div id="tableData">
		</div>
	</div>
    </c:if>
</div>
<br><br>
</body>
</html>