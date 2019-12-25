<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>部门管理</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">部门管理</div>
<c:if test="${gnid!=null&&gnidsize>0}">
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
<c:set var="gnidcount" value="1"/><c:forEach items="${gnid}" var="current">
			<td class="tag_head_close" align="center" onClick="changepanel(<c:out value='${gnidcount}'/>)" id="tagitem<c:out value='${gnidcount}'/>"><c:out value='${current.mc}'/></td>
<c:set var="gnidcount" value="${gnidcount+1}"/>
</c:forEach>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
<c:set var="gnidcount" value="1"/><c:forEach items="${gnid}" var="current">
	<table border="0" cellspacing="0" cellpadding="0" class="tag_body_table" id="tagpanel<c:out value='${gnidcount}'/>" style="display:none;">
		<tr>
			<td style="width:100%">
				<iframe id="tagframe<c:out value='${gnidcount}'/>" src=""  marginwidth="0" marginheight="0" hspace="0" vspace="0"
						align="top" scrolling="no" frameborder="0" style="width:100%;"></iframe>
			</td>
		</tr>
	</table>
<c:set var="gnidcount" value="${gnidcount+1}"/>
</c:forEach>
</c:if>
</div>
<script type="text/javascript">
	<!--
	var gnids=new Array();
	<c:if test="${gnid!=null&&gnidsize>0}">
	<c:set var="gnidsize" value="0"/><c:forEach items="${gnid}" var="current">gnids[<c:out value='${gnidsize}'/>]=new Array("<c:out value='${gnidsize}'/>","<c:out value='${current.gnid}'/>","<c:out value='${current.mc}'/>","<c:out value='${current.ymdz}'/>");
	<c:set var="gnidsize" value="${gnidsize+1}"/></c:forEach>
	$(document).ready(function(){
		doResize();
		window.onresize=doResize;
		doload(1);
		changepanel(1);
	});
	var iframecount=1;
	function doload(c){
		var iframe=document.getElementById("tagframe"+c);
		iframe.src=gnids[(c-1)][3];
		if(iframe.attachEvent){
			iframe.attachEvent("onload",function(){
				iframecount++;
				if(iframecount<=gnids.length){
					doload(iframecount);
				}
			});
		}else{
			iframe.onload=function(){
				iframecount++;
				if(iframecount<=gnids.length){
					doload(iframecount);
				}
			};
		}
	}
	function changepanel(idx){
		for (var i=1;i<=gnids.length;i++){
			$("#tagitem"+i).attr("className","tag_head_close");
			$("#tagpanel"+i).css("display","none");
		}
		$("#tagitem"+idx).attr("className","tag_head_open");
		$("#tagpanel"+idx).css("display","");
	}
	function doResize(){
		var h=document.body.clientHeight - 88;
		for (var i=1;i<=<c:out value='${gnidsize}'/>;i++){
			$("#tagframe"+i).attr("height",h);
		}
	}
	function doClose(){
		window.close();
	}
	</c:if>
	//-->
</script>
</body>
</html>