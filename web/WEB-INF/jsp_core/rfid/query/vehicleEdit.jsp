<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
<title>��������ά��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">������Ϣ��ѯ</div>
	<form action="" method="post" name="myform" id="myform">
	<div id="block">
		<div id="blocktitle">������Ϣ</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="15%">
			<col width="10%">
			<col width="15%">
			<col width="10%">
			<col width="15%">
			<col width="10%">
			<col width="15%">
			<tr>
				<td class="head">��������</td>
				<td class="body">
				<c:if test="${bean!=null}">
                    <c:forEach var="type" items="${licenceTypes}">
                        <c:choose>
                            <c:when test="${bean.hpzl == type.dmz}">
                                <c:out value="${type.dmsm1}"/>
                            </c:when>
                        </c:choose>
                    </c:forEach>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="hpzl" id="hpzl">
				</c:if>
				</td>
				<td class="head">��֤����</td>
				<td class="body">
					<c:if test="${bean!=null}">
						<c:out value='${bean.fzjg}'/>
					</c:if>
				</td>
				<td class="head">���ƺ���</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${fn:substring(bean.fzjg, 0, 1)}${bean.hphm}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="hphm" id="hphm">
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">��������</td>
				<td class="body">
					<c:if test="${bean!=null}">
						<%-- <c:out value="${bean.cllx}"/> --%>
						<c:forEach var="type" items="${vechileTypes}">
							<c:choose>
								<c:when test="${bean.cllx == type.dmz}">
									<c:out value="${type.dmsm1}"/>
								</c:when>
							</c:choose>
						</c:forEach>

					</c:if>
				</td>
				<td class="head">��������ɫ</td>
				<td class="body">
					<c:if test="${bean!=null}">
						<c:forEach var="color" items="${colors}">
							<c:choose>
								<c:when test="${fn:substring(bean.csys, 0, 1) == color.dmz}">
									<c:out value="${color.dmsm1}"/>
								</c:when>
							</c:choose>
						</c:forEach>

					</c:if>
				</td>
                <td class="head">������</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.syr}'/>
                    </c:if>
                    <c:if test="${bean==null}">
                        <input type="text" name="syr" id="syr">
                    </c:if>
                </td>
			</tr>
			<tr>
				<td class="head">ʹ������</td>
				<td class="body">
					<c:if test="${bean!=null}">
					    <%-- <c:out value='${bean.syxz}'/> --%>
					    <c:forEach var="type" items="${usingPurposes}">
                        <c:choose>
                            <c:when test="${fn:substring(bean.syxz, 0, 1) == type.dmz}">
                                <c:out value="${type.dmsm1}"/>
                            </c:when>
                        </c:choose>
                    </c:forEach>
					</c:if>
				</td>
                <td class="head">����</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.pl}'/>
                    </c:if>
                </td>
                <td class="head">������</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.zzl}'/>
                    </c:if>
                    <c:if test="${bean==null}">
                        <input type="text" name="zzl" id="zzl">
                    </c:if>
                </td>
			</tr>
			<tr>
                <td class="head">��������</td>
                <td class="body">
                    <c:out value='${bean.zbzl}'/>
                </td>
                <td class="head">�˶�������</td>
                <td class="body">
                    <c:out value='${bean.hdzzl}'/>
                </td>
				<td class="head">�˶��ؿ�</td>
				<td class="body">
				    <c:out value='${bean.hdzk}'/>
				</td>
			</tr>
			<tr>
                <td class="head">��������</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <fmt:formatDate value='${bean.ccrq}' pattern="yyyy-MM-dd" />
                    </c:if>
                </td>
				<td class="head">������Ч��ֹ</td>
				<td class="body">
				<c:if test="${bean!=null}">
					<fmt:formatDate value='${bean.yxqz}' pattern="yyyy-MM-dd" />
				</c:if>
				</td>
				<td class="head">ǿ�Ʊ�����ֹ</td>
				<td class="body">
				<c:if test="${bean!=null}">
					<fmt:formatDate value='${bean.qzbfqz}' pattern="yyyy-MM-dd" />
				</c:if>
				</td>
			</tr>
            <tr>
                <td class="head">����</td>
                <td class="body">
                    <c:out value="${bean.gl}"/>
                </td>
                <td class="head">����Ʒ��</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.clpp}'/>
                    </c:if>
                </td>
                <td class="body" colspan="2"/>
            </tr>
            <c:if test="${bean != null && bean.xszzp != null}">
                <tr>
                    <td class="head">
                        ��ʻ֤��Ƭ
                    </td>
                    <td class="body" colspan="5">
                        <img id="uploadImg" width="320" heigth="240" style="width:320px;height:240px;"
                             src="/rmweb/be/image.rfid?method=show&path=${bean.xszzp}"/>
                    </td>
                </tr>
            </c:if>
		</table>
    </div>
    <div id="block">
        <div id="blocktitle">�ƿ���Ϣ</div>
        <div id="blockmargin">8</div>

			<table border="0" cellspacing="1" cellpadding="0" class="list">
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<tr class="head">
					<td>
						TID
					</td>
					<td>
						����
					</td>
					<td>
						�ƿ�����
					</td>
					<td>
						״̬
					</td>
				</tr>
				<c:set var="rowcount" value="0" />
				<c:forEach items="${eriList}" var="current">
					<tr class="out">
						<td>
							<c:out value="${current.tid}" />
						</td>
						<td>
							<c:out value="${current.kh}" />
						</td>
						<td>
							<c:if test="${current.gxhrq != null}">
								<fmt:formatDate value='${current.gxhrq}' pattern="yyyy-MM-dd HH:mm:ss" />
							</c:if>
						</td>
                        <td>
                            <c:choose>
                                <c:when test="${current.zt == 0}">
                                    ������
                                </c:when>
                                <c:when test="${current.zt == 1}">
                                    �ɹ�
                                </c:when>
                                <c:when test="${current.zt ==2 }">
                                    ʧ�ܣ�<c:out value="${current.sbyy}"/>
                                </c:when>
                                <c:when test="${current.zt == 3}">
                                    ȡ��
                                </c:when>
                            </c:choose>
                        </td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="7" class="page">
						<c:out value="${controller.clientScript}" escapeXml="false" />
						<c:out value="${controller.clientPageCtrlDesc}"
							   escapeXml="false" />
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			    <input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			<c:if test="${bean!=null}">
			    //$("#savebutton").attr("disabled",true);
			    $("#hpzl").val("<c:out value='${bean.hpzl}'/>");
				$("#hphm").val("<c:out value='${bean.hphm}'/>");
				$("#syr").val("<c:out value='${bean.syr}'/>");
				$("#yxqz").val("<c:out value='${bean.yxqz}'/>");
				$("#bxzzrq").val("<c:out value='${bean.bxzzrq}'/>");
			</c:if>
		});
		function save(){
		    if(!checkNull($("#hpzl"),"��������")) return false;
			if(!checkNull($("#hphm"),"���ƺ���")) return false;
			if(!checkHPHM($("#hphm"),"���ƺ���")) return false;
            $.post("<c:url value='/specialVeh.tfc?method=saveSpecial'/>", $("#myform").serialize(), function(data){
                if (data && data["resultId"] == "00") {
                    returns();
                }
            }, "json");

		}
		function returns(data) { 
			if(data["code"]=="1"){
				window.opener.query_cmd();
				alert("����ɹ���");
				window.close();
			}else{
				alert(decodeURIComponent(data["message"]));
				opens();
			}
		}
	</script>
</body>
</html>
