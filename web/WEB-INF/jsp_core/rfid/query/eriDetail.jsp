<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>电子标签维护</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.operations .row{
	    text-align: left;
        color: rgb(90, 85, 85);
    	margin: 5 0 5 0;
	}
	.operations .date{
		display: inline-block;
	    width: 180px;
	    color: #0B8C0B;
	}
	.operations .jbr,.operations .bmmc,.operations .hphm,.operations .gxhzt{
	    color: blue;
	}
</style>
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
                <td class="body">
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

	<c:if test="${eriOperations!=null}">
		<div id="block" class="operations">
			<div id="blocktitle">电子标识流动信息</div>
			<div id="blockmargin">8</div>
			
			
			<!-- 1-原料入库 2-初始化 3-科研所入库 4-科研所出库 5-总队入库 6-支队领用 7-个性化 8-报废 -->
			<c:forEach items="${eriOperations}" var="current">
				<c:if test="${current.type==2}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						完成初始化
					</div>
				</c:if>
				<c:if test="${current.type==3}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						由 <span class="jbr"><c:out value="${current.jbrxm}"></c:out></span> 
						入库 
						<span class="bmmc"><c:out value="${current.bmmc}"></c:out></span>
					</div>
				</c:if>
				<c:if test="${current.type==4}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						由 <span class="jbr"><c:out value="${current.jbrxm}"></c:out></span> 
						从 
						<span class="bmmc"><c:out value="${current.bmmc}"></c:out></span> 
						出库
					</div>
				</c:if>
				<c:if test="${current.type==5}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						由 <span class="jbr"><c:out value="${current.jbrxm}"></c:out></span> 
						入库 
						<span class="bmmc"><c:out value="${current.bmmc}"></c:out></span>
					</div>
				</c:if>
				<c:if test="${current.type==6}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						由 <span class="jbr"><c:out value="${current.jbrxm}"></c:out></span> 
						从 <span class="jbr"><c:out value="${current.glbmmc}"></c:out></span> 出库 
						</br>
						<span class="date"></span>
						由 <span class="bmmc"><c:out value="${current.bmmc}"></c:out></span>-<span class="jbr"><c:out value="${current.lyr}"></c:out></span> 领用
					</div>
				</c:if>
				<c:if test="${current.type==7}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						由 <span class="jbr"><c:out value="${current.jbrxm == null ? current.jbr : current.jbrxm}"></c:out></span> 进行签注
						<span class="hphm"><c:out value="${current.hphm}"/>（<c:out value="${current.hpzl}"/>）</span>
						<span class="gxhzt">
                            <c:choose>
                                <c:when test="${current.gxhzt == 0}">
                                    <b style="color:orange">进行中</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="cancel-btn" data-id="${current.id}">取消</a>
                                </c:when>
                                <c:when test="${current.gxhzt == 1}">
                                    <b style="color:green">成功</b>
                                </c:when>
                                <c:when test="${current.gxhzt == 2}">
                                    <b style="color:red">失败</b>
                                </c:when>
                                <c:when test="${current.gxhzt == 3}">
                                    <b style="color:gray">取消</b>
                                </c:when>
                            </c:choose>
                        </span>
					</div>
				</c:if>
				<c:if test="${current.type == 8}">
					<div class="row">
						<span class="date"><fmt:formatDate value='${current.rq}' pattern="yyyy-MM-dd HH:mm:ss" /> :</span>
						由 <span class="jbr"><c:out value="${current.jbrxm}"></c:out></span> 报废，原因：
						<span class="bfyy">
							<c:out value="${current.bz}"/>
						</span>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:if>
	
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script language="JavaScript" type="text/javascript">
    var Eri_Detail_NS = {

        init : function(){
            <c:if test="${bean!=null}">
            $("#tid").val("<c:out value='${bean.tid}'/>");
            $("#kh").val("<c:out value='${bean.kh}'/>");
            $("#ph").val("<c:out value='${bean.ph}'/>");

            $("#cshrq").val("<c:out value='${bean.cshrq}'/>");
            </c:if>

            $(".cancel-btn").click(function() {
                Tools_NS.showConfirmDialog({
                    id : 'cancelDialog',
                    title : '提示',
                    message : '是否确定要取消该个性化记录',
                    width: 400,
                    onCancel: 'void()',
                    onConfirm: function() {
                        $.unblockUI();
                    }
                });
            });
        },

        cancel : function(id) {

        }

    }

    $(function() {

        Eri_Detail_NS.init();

    });
</script>
</body>
</html>
