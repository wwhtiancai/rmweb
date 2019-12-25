<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
  <head>
    <title>统计系统帮助中心</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
	<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
	<style type="text/css">
	a{
		cursor: hand;
	}
	li{
		font-size:12px;
		text-align: left;
		float: none;
	}
	</style>
  </head>
  
  <body style="padding: 20px;">
  	<center><h1>统计系统帮助中心</h1></center>
  	<table border="0" cellspacing="1" cellpadding="0" class="list">
  		<tr class="head">
  			<td width="10%">序号</td>
  			<td width="80%">问题类型</td>
  			<td width="10%">操作</td>
  		</tr>
    	<tr class="out">
    		<td>1</td>
    		<td>“导出Excel”、“导出Word”功能无法使用</td>
    		<td><a href='<c:url value="/download/ExcelBug.zip"/>' target="_blank">下载</a></td>
    	</tr>
    	<tr class="out">
    		<td>2</td>
    		<td>柱状图、饼图、折线图等功能无法使用</td>
    		<td><a href='<c:url value="/download/Install_Flash_Player_10_ActiveX.zip"/>' target="_blank">下载</a></td>
    	</tr>
    </table>
    <ul>
    <li>说明：</li>
    <li>　　1. “下载帮助”时，请使用“右键”-“目标另存为”进行下载。</li>
    </ul>
  </body>
</html>
