<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
  <head>
    <title>ͳ��ϵͳ��������</title>
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
  	<center><h1>ͳ��ϵͳ��������</h1></center>
  	<table border="0" cellspacing="1" cellpadding="0" class="list">
  		<tr class="head">
  			<td width="10%">���</td>
  			<td width="80%">��������</td>
  			<td width="10%">����</td>
  		</tr>
    	<tr class="out">
    		<td>1</td>
    		<td>������Excel����������Word�������޷�ʹ��</td>
    		<td><a href='<c:url value="/download/ExcelBug.zip"/>' target="_blank">����</a></td>
    	</tr>
    	<tr class="out">
    		<td>2</td>
    		<td>��״ͼ����ͼ������ͼ�ȹ����޷�ʹ��</td>
    		<td><a href='<c:url value="/download/Install_Flash_Player_10_ActiveX.zip"/>' target="_blank">����</a></td>
    	</tr>
    </table>
    <ul>
    <li>˵����</li>
    <li>����1. �����ذ�����ʱ����ʹ�á��Ҽ���-��Ŀ�����Ϊ���������ء�</li>
    </ul>
  </body>
</html>
