<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>首页</title>
</head>
<body>
<c:choose>
    <c:when test="${userInfo != null}">
        身份校验成功
    </c:when>
    <c:otherwise>
        身份校验失败<c:if test="${error != null}">${error}</c:if>
    </c:otherwise>
</c:choose>
</body>
</html>
