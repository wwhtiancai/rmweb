<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>证书上传</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="css/xtree.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
</head>
<body>
<div id="panel">
    <div id="paneltitle">
        证书上传
    </div>
    <div id="uploadCert">
        <form action="/rmweb/system.frm?method=upload-cert" method="post" name="myform" id="myform" enctype="multipart/form-data">
            <div id="block">
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="10%">
                    <col width="20%">
                    <col width="10%">
                    <col width="50%">
                    <col width="10%">
                    <c:if test="${resultId != null && resultId == '00'}">
                        <tr>
                            <td class="body" colspan="5">
                                <div style="font-size:18px;color:red;font-weight:bold;text-align:center">证书上传成功！</div>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${resultId != null && resultId != '00'}">
                        <tr>
                            <div style="font-size:18px;color:red;font-weight:bold;text-align:center"><c:out value="${resultMsg}"/></div>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="head">证书类型：</td>
                        <td class="body">
                            <select id="type" name="type">
                                <c:forEach items="${typeMap}" var="item">
                                    <option value="${item.key}">${item.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="head">请选择证书：</td>
                        <td class="body">
                            <input type="file" name="file_upload" style="width:80%;">
                        </td>
                        <td class="command">
                            <input type="submit" name="savebutton" id="savebutton" value="提交" class="button_save">
                        </td>
                    </tr>
                    <c:if test="${cert1 != null}">
                        <tr>
                            <td class="head">全国根证书：</td>
                            <td class="body" colspan="4">
                                <div style='word-wrap:break-word; word-break:break-all;display:block;width:100%;'>
                                    <c:out value="${cert1}"/>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${cert2 != null}">
                        <tr>
                            <td class="head">地市级证书：</td>
                            <td class="body" colspan="4">
                                <div style='word-wrap:break-word; word-break:break-all;display:block;width:100%;'>
                                    <c:out value="${cert2}"/>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
    var Cert_Upload_NS = {

        init : function() {

        }

    };

    $(function() {
        Cert_Upload_NS.init();
    });
</script>
</body>
</html>