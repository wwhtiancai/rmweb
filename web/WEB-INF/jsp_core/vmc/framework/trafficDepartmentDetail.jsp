<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>${cxmc}</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/gisutil.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	<c:if test="${bean!=null}">
		$("#glbms").html("<c:out value='${bean.glbm}'/>");
		$("#bmjbs").html("<c:out value='${bean.bmjbmc}'/>");
		$("#sjbms").html("<c:out value='${bean.sjbm}'/>:<c:out value='${bean.sjbmmc}'/>");
		$("#bmmcs").html("<c:out value='${bean.bmmc}'/>");
		$("#bmqcs").html("<c:out value='${bean.bmqc}'/>");
		$("#yzmcs").html("<c:out value='${bean.yzmc}'/>");
		$("#fzr").html("<c:out value='${bean.fzr}'/>");
		$("#lxr").html("<c:out value='${bean.lxr}'/>");
		$("#lxdh").html("<c:out value='${bean.lxdh}'/>");
		$("#czhm").html("<c:out value='${bean.czhm}'/>");
		$("#lxdz").html("<c:out value='${bean.lxdz}'/>");
		$("#fzjgs").html("<c:out value='${bean.fzjg}'/>");
		$("#jzjbs").html("<c:out value='${bean.jzjbmc}'/>");
		$("#gltzs").html("<c:out value='${bean.gltzmc}'/>");
		$("#jflys").html("<c:out value='${bean.jflymc}'/>");
		$("#lsgxs").html("<c:out value='${bean.lsgxmc}'/>");
		$("#gaws").html("<c:out value='${bean.jrgawmc}'/>");
		$("#jlzts").html("<c:out value='${bean.jlztmc}'/>");
		$("#glxzqhname").html("<c:out value='${glxzqhnames}'/>");
		doDllx();
		getXzqhmc("<c:out value='${bean.xzqh}'/>");
		getDllxmc("<c:out value='${bean.dllx}'/>","<c:out value='${bean.glxzdj}'/>");
	</c:if>
});

function doDllx(){
	if($("#dldm").val().length>0){
		var r=translateDllx($("#dldm").val().substring(0,1));
		var d=r.substring(0,1);
		if(d=="0"||d=="1"||d=="2"||d=="3"||d=="4"||d=="9"||d=="A"||d=="B"||d=="C"){
			$("#lkhTitle").html("公里数");
			$("#lkhValue").html($("#lkh1").val());
			$("#lkh").val("");
		}else{
			$("#lkhTitle").html("路口号");
			$("#lkhValue").html($("#lkmc1").val());
			$("#lkh").val("");
		}
	}else{
		$("#lkhTitle").html("公里数/路口号");
		$("#lkhValue").html("");
	}
	$("#dlmc").val("");
	$("#dlms").val("");
}

function getDllxmc(dllx,glxzdj){
	if(dllx == null || dllx == ''){
		return;
	}
	var html="";
	curUrl = "<c:url value='equipment.dev?method=getDlInfo&dllx='/>" + dllx + "&glxzdj=" + glxzdj;
	$.getJSON(curUrl, 
		function(data){
			$.each(data, function(i,item){
				if(item.dllxmc!=null&&item.dllxmc!=''){
					html += decodeURI(item.dllxmc);
				}
				if(item.glxzdjmc!=null&&item.glxzdjmc!=''){
					html += "/" + decodeURI(item.glxzdjmc);
				}
			});
			$("#dlxx").html(html);
		}		
	);
}
function getXzqhmc(xzqh){
	$.ajax({
		url:"<c:url value='/tollgate.dev'/>?method=getXzqhmc&xzqh=" + xzqh,
		dataType:"json",
		cache:false,
		async:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			if(data["code"] == "1"){
				$("#xzqhmcDiv").text(decodeURIComponent(data["message"]));
			}
		}
	});
}

</script>
</head>
<body>
<div id="panel" style="display:none">
	<input type="hidden" id="lkh1" value="${bean.lkh}">
	<input type="hidden" id="lkmc1" value="${bean.lkmc}">
	<input type="hidden" name="dldm" id="dldm" value="${bean.dldm}">
	<div id="paneltitle">${cxmc}</div>
		<div id="block">
		<div id="blocktitle">基本信息</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<tr>
					<td class="head">管理部门</td>
					<td class="body" id="glbms"></td>
					<td class="head">部门名称</td>
					<td class="body" id="bmmcs"></td>
					<td class="head">部门级别</td>
					<td class="body" id="bmjbs"></td>
				</tr>
				<tr>
					<td class="head">上级管理部门</td>
					<td colspan="5" class="body" id="sjbms"></td>
				</tr>
				<tr>
					<td class="head">部门全称</td>
					<td colspan="5" class="body" id="bmqcs"></td>
				</tr>
				<tr>
					<td class="head">印章名称</td>
					<td colspan="5" class="body" id="yzmcs"></td>
				</tr>

				<tr>
					<td class="head">发证机关</td>
					<td class="body" id="fzjgs"></td>
					<td class="head">接入公安网</td>
					<td class="body" id="gaws"></td>
					<td class="head">记录状态</td>
					<td class="body" id="jlzts"></td>
					<td colspan="2" class="body"></td>
				</tr>
			</table>
		</div>	

		<div id="block">
		<div id="blocktitle">联系与地理信息</div>
		<form action="" method="post" name="myform" id="myform" enctype="multipart/form-data">
			<input type="hidden" name="pglbm" id="pglbm" value="">	
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">	
				<tr>
					<td class="head">负责人</td>
					<td class="body" id="fzr"></td>
					<td class="head">联系人</td>
					<td class="body" id="lxr"></td>
					<td class="head">联系电话</td>
					<td class="body" id="lxdh"></td>
				</tr>
				<tr>
					<td class="head">传真号码</td>
					<td class="body" id="czhm"></td>
					<td class="head">联系地址</td>
					<td colspan="3" class="body" id="lxdz"></td>
				</tr>									
				<tr>
					<td class="head">可管理行政区划</td>
					<td colspan="5" class="body" id="glxzqhname"></td>
				</tr>
				<tr>
					<td class="head">道路代码</td>
					<td class="body">${bean.dlmc}</td>
					<td class="head"><span id="lkhTitle">公里数/路口路段</span></td>
					<td class="body"><div id="lkhValue"></div></td>
					<td class="head">道路米数</td>
					<td class="body">${bean.dlms}</td>
				</tr>
		        <tr>
					<td class="head">道路类型/公路行政等级</td>
					<td class="body">
						<label id="dlxx"></label>
					</td>
					<td class="head">行政区划</td>
					<td class="body">
						<span id="xzqhmcDiv"></span>
					</td>
					<td class="body" colspan="2"></td>
				</tr>
			</table>
			<table border="0" cellspacing="0" cellpadding="0" class="detail">
				<tr>
					<td class="command">
						<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
					</td>
				</tr>
			</table>
			</form>	
		</div>
	</div>
</body>
</html>
