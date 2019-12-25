<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>机动车轨迹模糊查询</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/hphm.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>
<script type="text/javascript">
<!--

$(window).bind("load",function(){
	$.datepicker.setDefaults($.datepicker.regional['']);
	$(".jscal").each(function () {
		eval($(this).html());
	});
});

var _i;
$(document).ready(function(){
  	changepanel(1);
	<c:if test="${tfcpass!=null}">
		$("[limit]").limit();
		$("#kkbh").val("<c:out value='${tfcpass.kkbh}'/>");
		$("#kkmc").val("<c:out value='${tfcpass.kkmc}'/>");
		$("#csys").val("<c:out value='${tfcpass.csys}'/>");
		$("#hpys").val("<c:out value='${tfcpass.hpys}'/>");
		$("#hphm").val("<c:out value='${tfcpass.hphm}'/>");
		$("#hpzl").val("<c:out value='${tfcpass.hpzl}'/>");
		$("#kssj").val("<c:out value='${tfcpass.kssj}'/>");
		$("#jssj").val("<c:out value='${tfcpass.jssj}'/>");
		getDirect("<c:out value='${tfcpass.fxlx}'/>");
	</c:if>
});
function query_cmd(){
	if(!doChecking()) return;
	$("#myform"+_i).attr("action","<c:url value='/tfcQuery.tfc?method=blurList'/>");
	closes();
	$("#myform"+_i).submit();
}

function showdetail(gcxh){
	openwin("<c:url value='/tfcQuery.tfc'/>?method=blurDetail&gcxh="+gcxh, "tfcDetail");
}


function changepanel(i){
	for (var j=1;j<=2;j++){
		document.getElementById('tagpanel'+j).style.display="none";
		document.getElementById('tagitem'+j).className="tag_head_close";	
	}
	_i = i;
	document.getElementById('tagpanel'+i).style.display="block";
	document.getElementById('tagitem'+i).className="tag_head_open";
}

function doChecking(){
	//object:TFC_PASS
	//regulation:hphm(超长(15),号牌)
	//regulation:kssj(比较(jssj(结束时间)))
	if(!compareDate($("#kssj").val(),$("#jssj").val(),"开始时间","结束时间")) return false;
	return true;
}


function selectTollgate(){
	curKKBH = $("#kkbh").val();
	curKKJC = $("#kkmc").val();	
	curArgs = curKKBH + "||" + curKKJC;
	
	var r = window.showModalDialog("component.dev?method=fwdTollgateRadio", curArgs, "dialogWidth:800px;dialogHeight:600px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r != "undefined"){
		$("#kkbh").val(r[0]);
		$("#kkmc").val(r[1]);
	}else{
		$("#kkbh").val('');
		$("#kkmc").val('');
	}
	closes();
	getDirect();
	opens();
}

function getDirect(val){
	$("#fxlx").empty();
	curKkbh = $("#kkbh").val();
	if(curKkbh == null || curKkbh == ''){
		return;
	}
	curUrl = "<c:url value='component.dev?method=getTollgateDirect&kkbh='/>" + curKkbh;
	$.getJSON(curUrl, 
		function(data){
			$.each(data, function(i,item){
				$("#fxlx").addOption(item.fxbh, decodeURI(item.fxmc));
			});
			$("#fxlx").val(val);
		}		
	);
}



//-->
</script>
</head>
<body>
<div id="panel" style="display:none; width:100%">
	<div id="paneltitle">机动车轨迹模糊查询</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">本市查询</td>
			<td class="tag_head_close" align="center" onClick="changepanel(2)" id="tagitem2">全省查询</td>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>

	<table border="0" cellspacing="0" cellpadding="0" style="display:none;" class="tag_body_table" id="tagpanel1" width="100%">
		<tr>
			<td valign="middle">
			<form action="" method="post" name="myform1" id="myform1">
				<table border="0" cellspacing="1" cellpadding="0" class="query">
					<col width="10%">
					<col width="23%">
					<col width="10%">
					<col width="23%">
					<col width="10%">
					<col width="24%">
					<tr>
						<td class="head">卡口名称</td>
						<td class="body">
							<input type="text" id="kkmc" name="kkmc" value="" readonly="readonly" style="width:88%;">
							<img alt="" src="images/tollgate-s2.gif" onclick="selectTollgate()" align="absmiddle" style="cursor:hand">
							<input type="hidden" id="kkbh" name="kkbh" value="">
						</td>
						<td class="head">方向</td>
						<td class="body"><select id="fxlx" name="fxlx" style="width:100%"></select></td>		
						<td class="head">车身颜色</td>
						<td class="body"><h:codebox list='${csys}' id='csys' haveNull='1'/></td>
					</tr>
					<tr>
						<td class="head">号牌种类</td>
						<td class="body"><h:codebox list='${hpzl}' id='hpzl' haveNull='1'/></td>				
						<td class="head">号牌号码</td>
						<td class="body"><input type="text" name="hphm" id="hphm" maxlength="15" style="width:120" onKeyUp="hphmToUp()" onblur="hphmToUpAll()" onFocus="setPos()"><img src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()" align="absmiddle" style="cursor:hand"><div id="hphm_div" style="position:absolute;display:none;"></div></td>
						<td class="head">号牌颜色</td>
						<td class="body"><h:codebox list='${hpys}' id='hpys' haveNull='1'/></td>
					</tr>
					<tr>
						<td class="head">过车时间</td>
						<td class="body" colspan="3">
							<h:datebox id="kssj" showType="2"/> 至 <h:datebox id="jssj" showType="2"/>
						</td>				
						<td colspan="2" class="submit"><input type="button" value="查询" class="button_query" onClick="query_cmd()">&nbsp;<input type="button" value="关闭" class="button_close" onClick="javascript:window.close()"></td>
					</tr>
				</table>
				<c:if test="${queryList!=null}">
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="33%">
						<col width="12%">
						<col width="15%">
						<col width="11%">
						<col width="9%">
						<col width="20%">
						<tr class="head">
							<td>卡口名称</td>
							<td>号牌种类</td>
							<td>号牌号码</td>
							<td>号牌颜色</td>
							<td title="单位：Km/h">车辆速度</td>
							<td>过车时间</td>
						</tr>
						<c:forEach items="${queryList}" var="current">
						<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.kkbh}'/>')">
							<td align="left" limit="19"><c:out value="${current.kkmc}"/></td>
							<td align="left" limit="19"><c:out value="${current.hpzlmc}"/></td>
							<td limit="11"><c:out value="${current.hphm}"/></td>
							<td><c:out value="${current.hpysmc}"/></td>
							<td><c:out value="${current.clsd}"/></td>
							<td limit="time"><c:out value="${current.gcsj}"/></td>
						</tr>
						</c:forEach>
						<tr>
							<td colspan="7" class="page">
							<c:out value="${controller.clientScript}" escapeXml="false"/>
							<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
							</td>
						</tr>
					</table>
				</c:if>
			</form>
			</td>
		</tr>
	</table>
	
	<table border="0" cellspacing="0" cellpadding="0" style="display:none;" class="tag_body_table" id="tagpanel2">
		<tr>
			<td valign="middle">
			  <form action="" method="post" name="myform2" id="myform2">
			  	
			  </form>
			</td>
		</tr>
	</table>
	
</div>
</body>
</html>