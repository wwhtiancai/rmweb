<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.tmri.share.frm.util.*"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%
JspSuport.getInstance().setSession(session);
%>
<%
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>