<%@ page language="java"  contentType="text/html;charset=gb2312" %>
<%
	String sImgAreaName=request.getParameter("ImgAreaName");
	if(sImgAreaName!=null && sImgAreaName.trim().length()>0){
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ZoomImg</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
	var originalZoom=1;
	function LoadImg(){
		var img=eval('document.all.PreImage');
		img.src=opener.document.all("<%=sImgAreaName%>").src;
	}
	function ZoomMax(){
		var img=eval('document.all.PreImage');
		originalZoom=originalZoom*2;
		img.style.zoom=originalZoom;
	}
	function ZoomMin(){
		var img=eval('document.all.PreImage');
		originalZoom=originalZoom/2;
		img.style.zoom=originalZoom;
	}
	function ResetSize(){
		var img=eval('document.all.PreImage');
		img.style.zoom='100%';
	}
	function bbimg(){
		var img=eval('document.all.PreImage');
		var zoom=parseInt(img.style.zoom, 10)||100;
		zoom+=event.wheelDelta/12;
		if (zoom>0) 
			img.style.zoom=zoom+'%';
		return false;
	} 

//-->
</SCRIPT>
</head>
<body onload="LoadImg()" onmousewheel="return bbimg()" style="cursor: move;">
<table width="100%"  border="1" cellspacing="2" cellpadding="2">
	<tr>
		<td align="left" bgcolor="#CCCCCC"><input name="btnZoomMax" type="button" id="btnZoomMax" value="�Ŵ�" onclick="ZoomMax()">
		<input name="btnZoomMin" type="button" id="btnZoomMin" value="��С" onclick="ZoomMin()">
		<input name="btnResetSize" type="button" id="btnResetSize" value="ԭʼ�ߴ�" onclick="ResetSize()">
		<input name="btnClose" type="button" id="btnClose" value="�ر�" onclick="window.close()"> 
		(���������������ͼƬ) </td>
	</tr>
	<tr>
		<td align="center"><img src="" name="PreImage" id="PreImage" ></td>
	</tr>
</table>
</body>
</html>
<%
	}else{
		out.print("ȱ�ٲ���ImgAreaName!");
	}
%>