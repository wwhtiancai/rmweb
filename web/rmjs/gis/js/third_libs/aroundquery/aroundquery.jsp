<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
var aroundQueryServer="<%=basePath%>";
if(typeof(jQuery)=="undefined"){
		document.writeln("<SCRIPT type='text/javascript' src='"+aroundQueryServer+"rmjs/gis/js/third_libs/jquery/jquery-1.6.2.js'></SCRIPT>");
}else{
	var n=jQuery.fn.jquery.replace(/\./g,"");
	var v1=parseInt(n);
	var v2=162;//jQuery最小版本为1.6.2
	if(v2>v1){
			document.writeln("<SCRIPT type='text/javascript' src='"+aroundQueryServer+"rmjs/gis/js/third_libs/jquery/jquery-1.6.2.js'></SCRIPT>");
	}
}

document.writeln("<link rel='stylesheet' href='"+aroundQueryServer+"rmjs/gis/js/third_libs/multiselect/jquery-ui.css' type='text/css'></link>");
document.writeln("<link rel='stylesheet' href='"+aroundQueryServer+"rmjs/gis/js/third_libs/multiselect/jquery.multiSelect.css' type='text/css'></link>");
document.writeln("<SCRIPT  type='text/javascript' src='"+aroundQueryServer+"rmjs/gis/js/third_libs/aroundquery/aroundquery.js' charset='GBK'></SCRIPT>");