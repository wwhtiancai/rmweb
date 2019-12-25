<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>红名单车辆维护</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
		<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
		<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
			type="text/css">
		<script language="JavaScript" src="rmjs/jquery.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmtheme/main/main.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="frmjs/common_func.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmjs/tools.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmjs/hphm/jquery-position.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmjs/jquery.loadthumb.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="rmjs/hphm/hphm.js"
			type="text/javascript"></script>

		<script type="text/javascript">
<!--
if (!window.console) {
	window.console = {
		log: function() {
			alert(arguments[0]);
		}
	}
}

$(document).ready(function(){
	<c:if test="${command!=null}">
		$("[limit]").limit();
		$("#hpzl").val("<c:out value='${command.hpzl}'/>");
		$("#hphm").val("<c:out value='${command.hphm}'/>");
		$("#glbm").val("<c:out value='${command.glbm}'/>");
		$("#bzsm").val("<c:out value='${command.bzsm}'/>");
	</c:if>
	
});

function returns(data) { 
	if(data["code"]=="1"){
		alert("保存成功！");	
		query_init();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}

function query_cmd(){
	$("#myform").attr("action","<c:url value='/specialVeh.tfc?method=listSpecial'/>");
	closes();
	$("#myform").submit();
}

function query_init() {
	$("#myform").attr("action","<c:url value='/specialVeh.tfc?method=querySpecial'/>");
	closes();
	$("#myform").submit();	
}

function specialedit(xh) {
	openwin("/rmweb/specialVeh.tfc?method=editSpecial&xh="+xh, "specialEdit");
}


//-->
</script>
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				红名单车辆维护
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform">
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="24%">
						<input type="hidden" name="xh" id="xh" value="">
						<tr>
							<td class="head">
								号牌种类
							</td>
							<td class="body">
								<h:codebox list='${hpzl}' id='hpzl' haveNull='1' />
							</td>
							<td class="head">
								号牌号码
							</td>
							<td class="body">
								<input type="text" name="hphm" id="hphm" maxlength="15"
									style="width: 86%" onKeyUp="hphmToUp()" onblur="hphmToUpAll()"
									onFocus="setPos()">
								<img src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()"
									align="absmiddle" style="cursor: hand">
								<div id="hphm_div" style="position: absolute; display: none;"
									onmouseover="setIsHphmDivMouseOn(true)"
									onmouseout="setIsHphmDivMouseOn(false)"></div>
							</td>
						</tr>
						<tr>
							<td class="head">
								管理部门
							</td>
							<td colspan="1" class="body">
								<select id="glbm" name="glbm" style="width: 100%;">
									<c:forEach var="current" items="${xjbmList}">
										<option value="${current.glbm}">
											<c:out value="${current.bmmc}" />
										</option>
									</c:forEach>
								</select>
							</td>
							<td class="head">
								说明
							</td>
							<td class="body">
								<input type="text" name="bzsm" id="bzsm" maxlength="256">
							</td>
						</tr>
						<tr>
							<td class="submit" colspan="6">
								<input type="button" class="button_query" value="查询"
									onclick="query_cmd()">
								<input type="button" class="button_new" value="新增"
									onclick="specialedit('')">
								<input type="button" class="button_close" value="关闭"
									onclick="javascript:window.close()">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult"></div>
			<c:if test="${queryList!=null}">
				<div id="result">
					<div id="resulttitle">
						查询内容
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="10%">
						<col width="10%">
						<col width="20%">
						<col width="57%">
						<tr class="head">
							<td>
								号牌种类
							</td>
							<td>
								号牌号码
							</td>
							<td>
								管理部门
							</td>
							<td>
								说明
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="specialedit('<c:out value='${current.xh}'/>')">
								<td>
									<c:out value="${current.hpzlmc}" />
								</td>
								<td>
									<c:out value="${current.hphm}" />
								</td>
								<td>
									<c:out value="${current.glbmmc}" />
								</td>
								<td>
									<c:out value="${current.bzsm}" />
								</td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
						<tr>
							<td colspan="6" class="page">
								<c:out value="${controller.clientScript}" escapeXml="false" />
								<c:out value="${controller.clientPageCtrlDesc}"
									escapeXml="false" />
							</td>
						</tr>
					</table>
				</div>
			</c:if>
		</div>
	</body>
</html>