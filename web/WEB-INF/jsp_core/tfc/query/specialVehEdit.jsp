<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>红名单车辆维护</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.qtip.js"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.loadthumb.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/hphm.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
    $("#delbutton").attr("disabled",true);
<c:if test="${bean!=null}">
    $("#savebutton").attr("disabled",true);
    $("#xh").val("<c:out value='${bean.xh}'/>");
    $("#hpzl").val("<c:out value='${bean.hpzl}'/>");
    $("#hpzl2").val("<c:out value='${bean.hpzl}'/>");
	$("#hphm").val("<c:out value='${bean.hphm}'/>");
	$("#bzsm").val("<c:out value='${bean.bzsm}'/>");
	$("#hpzl2").attr("disabled",true);
	<c:if test="${bmjb!=null}">
	$("#savebutton").attr("disabled",false);
	$("#delbutton").attr("disabled",false);
	</c:if>
</c:if>
});
function save(){
     
    if(!checkNull($("#hpzl"),"号牌种类")) return false;
	if(!checkNull($("#hphm"),"号牌号码")) return false;
	if(!checkHPHM($("#hphm"),"号牌号码")) return false;
	$("#myform").attr("action","<c:url value='/specialVeh.tfc?method=saveSpecial'/>");
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
	});
}
function returns(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("保存成功！");
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
		$("#delbutton").attr("disabled",true);
		<c:if test="${bean!=null}">
		    <c:if test="${bmjb!=null}">
			$("#delbutton").attr("disabled",false);
			</c:if>
        </c:if>
	}
}

function del(){
	if(confirm("是否确定删除该红名单？")){
		$("#myform").attr("action","<c:url value='/specialVeh.tfc?method=delSpecial'/>");
		closes();
		$("#myform").ajaxSubmit({
			dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
		});
	}
}

function returndeletes(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("删除红名单成功！");
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}

</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">红名单车辆维护</div>
	<form action="" method="post" name="myform" id="myform">
    <input type="hidden" name="xh" id="xh" value="">
    <c:if test="${bean!=null}">
        <input type="hidden" name="hphm" id="hphm" value="">
        <input type="hidden" name="hpzl" id="hpzl" value="">
    </c:if>
	<div id="block">
		<div id="blocktitle">红名单信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="15%">
			<col width="10%">
			<col width="45%">
			<tr>
				<td class="head">号牌种类</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <h:codebox list='${hpzl}' id='hpzl2' haveNull='1'/>
				</c:if>
				<c:if test="${bean==null}">
				    <h:codebox list='${hpzl}' id='hpzl' haveNull='1'/>
				</c:if>
				</td>
				<td class="head">号牌号码</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.hphm}'/>
				</c:if>
				<c:if test="${bean==null}">
				    <input type="text" name="hphm"  id="hphm" maxlength="15" style="width:40%" onKeyUp="hphmToUp()" onblur="hphmToUpAll()" onFocus="setPos()"><img src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()" align="absmiddle" style="cursor:hand"><div id="hphm_div" style="position:absolute;display:none;" onmouseover="setIsHphmDivMouseOn(true)" onmouseout="setIsHphmDivMouseOn(false)"></div>
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">备注说明</td>
				<td class="body" colspan="5"><input type="text" name="bzsm" id="bzsm" maxlength="256"></td>
			</tr>
		</table>
	</div>

	
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
			<input type="button" name="delbutton" id="delbutton" onclick="del()" value="删除" class="button_del">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

</body>
</html>
