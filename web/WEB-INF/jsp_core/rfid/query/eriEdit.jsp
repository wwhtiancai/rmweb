<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>电子标签维护</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">电子标签维护</div>
	<form action="" method="post" name="myform" id="myform">
	<div id="block">
		<div id="blocktitle">电子标签信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="15%">
			<col width="35%">
			<col width="15%">
			<col width="35%">
			<tr>
				<td class="head">TID</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.tid}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="tid" id="tid">
				</c:if>
				</td>
				<td class="head">卡号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.kh}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="kh" id="kh">
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">批号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.ph}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="ph" id="ph">
				</c:if>
				</td>
				<td class="head">省份</td>
				<td class="body">
				<c:if test="${bean!=null}">
					<c:forEach var="province" items="${provinces}">
					    <c:if test="${province.dmz == bean.sf}">
					    	<c:out value='${province.dmsm1}'/>
					    </c:if>
					</c:forEach>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="sf" id="sf">
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">初始化日期</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <fmt:formatDate value='${bean.cshrq}' pattern="yyyy-MM-dd HH:mm:ss" /> 
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="cshrq" id="cshrq">
				</c:if>
				</td>
                <td class="head">状态</td>
                <td class="body" colspan="5">
                    <c:if test="${bean!=null}">
                        <c:forEach var="status" items="${eriStatus}">
                            <c:if test="${status.status == bean.zt}">
                                <c:out value="${status.desc}" />
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${bean==null}">
                        <input type="text" name="zt" id="zt">
                    </c:if>
                </td>
			</tr>
		</table>
	</div>

	<c:if test="${eriCustomizes!=null}">
		<div id="block">
			<div id="blocktitle">个性化信息</div>
			<div id="blockmargin">8</div>
			<table border="0" cellspacing="1" cellpadding="0" class="list">
				<col width="10%">
				<col width="15%">
				<col width="15%">
				<col width="10%">
				<col width="15%">
				<col width="16%">
				<tr class="head">
					<td>
						号牌种类
					</td>
					<td>
						号牌号码
					</td>
					<td>
						流水号
					</td>
					<td>
						状态
					</td>
					<td>
						个性化日期
					</td>
					<td>
						个性化操作人
					</td>
				</tr>
				<c:set var="rowcount" value="0" />
				<c:forEach items="${eriCustomizes}" var="current">
					<tr class="out" onMouseOver="this.className='over'"
						onMouseOut="this.className='out'" style="cursor: pointer">
						<td>
							<c:forEach var="licenseType" items="${licenceTypes}">
                                <c:if test="${licenseType.dmz == current.hpzl}">
                                    <c:out value="${licenseType.dmsm1}" />
                                </c:if>
                            </c:forEach>
						</td>
						<td onClick="vehicleedit('<c:out value='${current.clxxbfid}'/>')">
							<c:out value="${current.fzjg}" />${fn:substring(current.hphm,1,11)}
						</td>
						<td>
							<c:out value="${current.lsh}" />
						</td>
						<td>
                            <c:forEach var="status" items="${eriCustomizeStatus}">
                                <c:if test="${status.status == current.zt}">
                                    <c:out value="${status.desc}" />
                                </c:if>
                            </c:forEach>
						</td>
						<td>
                            <fmt:formatDate value='${bean.cshrq}' pattern="yyyy-MM-dd HH:mm:ss" /> 
						</td>
						<td>
							<c:out value="${current.gxhczr}" />
						</td>
					</tr>
					<c:set var="rowcount" value="${rowcount+1}" />
				</c:forEach>
			</table>
		</div>
	</c:if>
	
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			<c:if test="${bean!=null}">
			    $("#savebutton").attr("disabled",true);
			    $("#tid").val("<c:out value='${bean.tid}'/>");
				$("#kh").val("<c:out value='${bean.kh}'/>");
				$("#ph").val("<c:out value='${bean.ph}'/>");
				
				$("#cshrq").val("<c:out value='${bean.cshrq}'/>");
				<c:if test="${bmjb!=null}">
				$("#savebutton").attr("disabled",false);
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
				displayDialog(2,"保存成功！","");
				window.close();
			}else{
				alert(decodeURIComponent(data["message"]));
				opens();
			}
		}
		
		
		function vehicleedit(clxxbfid) {
			openwin("/rmweb/vehicle.frm?method=edit-vehicle&clxxbfid="+clxxbfid, "vehicleEdit");
		}
	</script>
</body>
</html>
