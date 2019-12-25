<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="h" uri="http://5.tmri.cn/tag/h"%>
<%
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
if(session.getAttribute("userSession")==null){
  response.sendRedirect("/rmweb/logout.jsp");
  return;
}
%>
<c:if test="${error!=null}"><c:out value="${error}" escapeXml="false"/></c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10"/>