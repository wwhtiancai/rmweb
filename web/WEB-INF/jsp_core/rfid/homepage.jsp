<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>��ҳ</title>
</head>
<body>
<c:choose>
    <c:when test="${userInfo != null}">
        ���У��ɹ�
    </c:when>
    <c:otherwise>
        ���У��ʧ��<c:if test="${error != null}">${error}</c:if>
    </c:otherwise>
</c:choose>
</body>
</html>
