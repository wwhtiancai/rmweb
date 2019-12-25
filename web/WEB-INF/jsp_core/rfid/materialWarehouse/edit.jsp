<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>原料入库</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">原料入库</div>
	<form action="" method="post" name="myform" id="myform">
	<div id="block">
		<div id="blocktitle">原料入库信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<tr>
				<td class="head">入库单号</td>
				<td class="body">
				    <c:out value='${bean.rkdh}'/>
				    <input type="hidden" name="rkdh" id="rkdh">
				</td>
                <td class="head">入库日期</td>
                <td class="body">
                    <fmt:formatDate value="${bean.rkrq}" type="both"/>
                    <input type="hidden" name="rkrq" id="rkrq" value="${bean.rkrq}"/>
                </td>
			</tr>
			<tr>
                <td class="head">经办人</td>
                <td class="body">
                    <c:out value='${bean.jbrxm}'/>
                </td>
				<td class="head">交付单位</td>
				<td class="body">
				    <input type="text" name="jfdw" id="jfdw" value="${bean.jfdw}"/>
				</td>
            </tr>
            <tr>
            	<td class="head">状态</td>
                <td class="body">
                    <select id="zt" name="zt">
                        <c:forEach var="status" items="${materialWarehouseStatus}">
                            <c:choose>
                                <c:when test="${status.status == bean.zt}">
                                    <option value="${status.status}" selected>
                                        <c:out value="${status.desc}"/>
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${status.status}">
                                        <c:out value="${status.desc}"/>
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
			<tr>
				<td class="head">备注</td>
				<td class="body" colspan="3">
				    <textarea name="bz" id="bz" rows="4" cols="100"><c:out value="${bean.bz}"/></textarea>
				</td>
			</tr>
		</table>
		
		
		<table border="0" cellspacing="0" cellpadding="0" class="detail">
			<tr>
				<td class="command">
					<a href="/rmweb/template/ylrkd.xls" >下载模板</a>
                   	<input type="button" name="uploadbutton" id="uploadbutton" value="上传" class="button_new">
					<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
					<input type="button" name="delbutton" id="delbutton" onclick="del()" value="取消" class="button_del">
					<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
				</td>
			</tr>
		</table>
	</div>

	</form>
</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
	
	$(document).ready(function(){
	<c:if test="${bean!=null}">
		$("#addbutton").attr("disabled",false);
	    $("#savebutton").attr("disabled",false);
	    $("#uploadbutton").attr("disabled",true);
		var rkdh = "<c:out value='${bean.rkdh}'/>";
		$("#rkdh").val(rkdh);
		
		$("#delbutton").attr("disabled",false);
		
		var zt = "<c:out value='${bean.zt}'/>"
		if(zt == 0){
			$("#uploadbutton").attr("disabled",false);
			$("#uploadbutton").bind("click",function(){
				openwin800("/rmweb/material-warehouse.rfid?method=openUpload&rkdh="+rkdh, "uploadMaterialWarehouse");
			})
		}
	</c:if>
		
	});
	
	function save(){
		if(!checkNull($("#jfdw"),"交付单位")) return false;
        closes();
        $.ajax({
            url: "<c:url value='/material-warehouse.rfid?method=edit'/>",
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            data: $("#myform").serialize(),
            async: false,
            type: "POST",
            success: function(data) {
                if (data && data["resultId"] == "00") {
        			displayDialog(2,"更新成功！","");
                    window.opener.query_cmd();
                    window.close();
                }else{
                	displayDialog(3,data["resultMsg"],"");
                }
            },
            dataType: "json"
        });
	}
	
	function del(){
		if(confirm("是否确定取消该入库单？")){
			$.ajax({
                url : "<c:url value='/material-warehouse.rfid?method=delete'/>",
                type: "POST",
                dataType: "json",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                async: false,
                data: {
                	rkdh : $("#rkdh").val()
                },
                success: returndeletes
            });
		}
	}
	
	function returndeletes(data) { 
		if (data && data["resultId"] == "00") {
			window.opener.query_cmd();
			displayDialog(2,"取消入库单成功！","");
			window.close();
		}else{
        	displayDialog(3,data["resultMsg"],"");
		}
	}
	
	function query_cmd(){
		window.location.reload(); 
	}
	
	</script>
</body>
</html>
