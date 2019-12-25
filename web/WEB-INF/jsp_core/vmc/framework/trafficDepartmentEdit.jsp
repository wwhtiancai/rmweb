<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>${cxmc}</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">

</head>
<body>
<div id="panel" style="display:none">

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
					<td class="body"><input type="text" id="fzr" name="fzr" value="" maxlength="16" onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"></td>
					<td class="head">联系人</td>
					<td class="body"><input type="text" id="lxr" name="lxr" value="" maxlength="16" onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"></td>
					<td class="head">联系电话</td>
					<td class="body"><input type="text" id="lxdh" name="lxdh" maxlength="50" value=""></td>
				</tr>
				<tr>
					<td class="head">传真号码</td>
					<td class="body"><input type="text" id="czhm" name="czhm" maxlength="50" value=""></td>
					<td class="head">联系地址</td>
					<td colspan="3" class="body"><input type="text" id="lxdz" name="lxdz" maxlength="128" value=""></td>
				</tr>

				<tr>
					<td colspan="6" class="command">
					<c:if test="${bean!=null && bean.bjcsbj != 1}">
						<input type="button" name="deletebutton" onclick="del(${bean.glbm})" value="删除" class="button_del"/>
					</c:if>
						<input type="button" name="closesave" onclick="saveXzqh()" value="保存" class="button_save">
						<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
					</td>
				</tr>
			</table>
			</form>	
			
		</div>

	</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script language="JavaScript" type="text/javascript">
	$(document).ready(function(){
		<c:if test="${bean!=null}">
		$("#glbms").html("<c:out value='${bean.glbm}'/>");
		$("#bmjbs").html("<c:out value='${bean.bmjbmc}'/>");
		$("#sjbms").html("<c:out value='${bean.sjbm}'/>:<c:out value='${bean.sjbmmc}'/>");
		$("#bmmcs").html("<c:out value='${bean.bmmc}'/>");
		$("#bmqcs").html("<c:out value='${bean.bmqc}'/>");
		$("#yzmcs").html("<c:out value='${bean.yzmc}'/>");
		$("#fzr").val("<c:out value='${bean.fzr}'/>");
		$("#lxr").val("<c:out value='${bean.lxr}'/>");
		$("#lxdh").val("<c:out value='${bean.lxdh}'/>");
		$("#czhm").val("<c:out value='${bean.czhm}'/>");
		$("#lxdz").val("<c:out value='${bean.lxdz}'/>");
		$("#fzjgs").html("<c:out value='${bean.fzjg}'/>");
		$("#jzjbs").html("<c:out value='${bean.jzjbmc}'/>");
		$("#gltzs").html("<c:out value='${bean.gltzmc}'/>");
		$("#jflys").html("<c:out value='${bean.jflymc}'/>");
		$("#lsgxs").html("<c:out value='${bean.lsgxmc}'/>");
		$("#gaws").html("<c:out value='${bean.jrgawmc}'/>");
		$("#jlzts").html("<c:out value='${bean.jlztmc}'/>");

		<c:if test="${gisoffice == '1'}">
		$("#dldm").val("<c:out value='${bean.dldm}'/>");
		$("#xzqh").val("<c:out value='${bean.xzqh}'/>");
		doDllx();
		getXzqhmc("<c:out value='${bean.xzqh}'/>");
		$("#sjxzqh").val("<c:out value='${bean.sjxzqh}'/>");
		getDllxmc("<c:out value='${bean.dllx}'/>","<c:out value='${bean.glxzdj}'/>");

		$("#xzqhmc").val("<c:out value='${bean.xzqhmc}'/>");
		$("#dllx").val("<c:out value='${bean.dllx}'/>");
		$("#glxzdj").val("<c:out value='${bean.glxzdj}'/>");

		$("#dlmc").val("<c:out value='${bean.dlmc}'/>");
		$("#dljc").val("<c:out value='${bean.dlmc}'/>");
		$("#lkh").val("<c:out value='${bean.lkh}'/>");
		$("#dlms").val("<c:out value='${bean.dlms}'/>");

		$("#jd").val("<c:out value='${bean.jd}'/>");
		$("#wd").val("<c:out value='${bean.wd}'/>");
		</c:if>
		glxzqh = "<c:out value='${xzqh.glbm}'/>";
		if(glxzqh != ""){
			glxzqhAry = glxzqh.split(",");
			for(i = 0; i < glxzqhAry.length; i++){
				$("#xzqhCode" + glxzqhAry[i]).attr("checked", true);
			}
		}

		</c:if>
	});


	function saveXzqh(){
		if(dochecking()){
			$("#myform").attr("action","<c:url value='/department.vmc'/>?method=updateTraffic");
			closes();
			$("#myform").ajaxSubmit({
				dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
			});
		}
	}

	function dochecking(){
		if(!checkNull($("#fzr"),"负责人 ")) return false;
		if(!checkNull($("#lxr"),"联系人 ")) return false;
		if(!checkNull($("#lxdh"),"联系电话")) return false;
		if(!checkNull($("#czhm"),"传真号码 ")) return false;
		if(!checkNull($("#lxdz"),"联系地址 ")) return false;
		return true;
	}
	function returns(data){
		if(data.code == 1){
			window.opener.query_cmd();
			alert("保存成功！");
			window.close();
		}else{
			alert("保存失败！错误内容：" + decodeURIComponent(data.message));
			opens();
		}
	}

	function del(glbm) {
		closes();
		$.post("<c:url value='/department.vmc'/>?method=deleteTraffic", {glbm: glbm}, function(data) {
			if (data["resultId"] == "00") {
				window.opener.query_cmd();
				alert("删除成功！");
				window.close();
			} else {
				alert("删除失败!原因:" + decodeURIComponent(data["resultMsg"]));
				opens();
			}
		}, "json");
	}


</script>
</body>
</html>
