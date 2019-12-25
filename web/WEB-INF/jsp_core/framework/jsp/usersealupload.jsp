<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.framework.service.SysuserManager"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="java.util.List"%>
<%
String cdbh=(String)request.getParameter("cdbh");
SysUserSeal sysuserseal=(SysUserSeal)request.getAttribute("sysuserseal");
String zdsx=(String)request.getAttribute("zdsx");
SysuserManager sysuserManager = (SysuserManager)request.getAttribute("sysuserManager");
%>

<head>
<title></title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript"  type="text/JavaScript">
function post(){
  if(document.all["qmtp"].value==""){
    alert("请先点浏览从本地磁盘上选择要上传的图片");
    return false;
  }else{
    document.uploadform.action="sysuser.frm?method=uploadfile";
    document.uploadform.target="paramIframe";
    document.uploadform.submit();
  }
}
function del(){
  document.uploadform.action="sysuser.frm?method=delfile";
  document.uploadform.target="paramIframe";
  document.uploadform.submit();
}

function getFileName(){
  document.all("tpxs").src = document.all("qmtp").value;
}


function resultsubmit(bj,return_out,description){
  if(bj==1){
	var strCommond = "document.uploadform.btdel.disabled=false;";
    strCommond += "opener.refreshseal();";     
	displayInfoHtml("[00R9940J3]:照片保存成功! ",strCommond);
  }else if(bj==2) {
	var strCommond = "document.uploadform.btdel.disabled=false;";
    strCommond += "opener.refreshseal();";    
	displayInfoHtml("[00R9940J4]:照片修改成功! ",strCommond);
  }else if(bj==3) {
    var strCommond = "window.close();";
　　displayInfoHtml("[00R9940J5]:照片已成功删除! ",strCommond);
  }else if(bj==5) {
	displayInfoHtml("[00R9942J6]:照片文件大小超过设定值,请压缩或重新设置系统参数后再上传! ");
  }else {
	displayInfoHtml("[00R9941J7]:"+description);
  }
}

function bbimg(o){
  var zoom=parseInt(o.style.zoom, 10)||100;zoom+=event.wheelDelta/12;if (zoom>0) o.style.zoom=zoom+'%';
  return false;
}

function init(zdsx,id){
	if(zdsx=='0'){
		document.uploadform.btdel.disabled=true;
	}
}

</script>

</head>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue.js" type="text/javascript"></script>   
<BODY onLoad="init('<%=zdsx%>','<%=sysuserseal.getYhdh()%>');">
<script language="JavaScript" type="text/javascript">vio_title('用户签名维护')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<form method="post" enctype="multipart/form-data" name="uploadform">
<input type="hidden" name="yhdh" value="<%=sysuserseal.getYhdh()%>">

<table border="0" cellspacing="1" cellpadding="0" class="list_table" width="98%">
    <%
    String photoSrc = "";
    if(zdsx.equals("0")){
      	photoSrc = "theme/blue/nophoto.jpg";
    } else { 
  		photoSrc = "sysuser.frm?method=outputphoto&yhdh="+sysuserseal.getYhdh()+"&rand="+sysuserManager.getRand();
    }	  
    %>
    <TR  align="center" valign="middle" class="list_body_out">
      <TD class="list_body_out">
           照片路径&nbsp;
        <input type="file" name="qmtp" onChange="getFileName()"  class="text_12">&nbsp;
        <input type="button" name="btsave" value="上  传" onClick="post();"  class="button_query">&nbsp;
        <input type="button" name="btdel" onClick="del();" style="cursor:hand" alt="删除照片" value="删除"  class="button_query">&nbsp;
        <input type="button" name="btclose" value="关闭窗口" onClick="window.close();"  class="button_query">&nbsp;
      </TD>
    </TR>
    <TR>
    	<TD colspan="6" class="list_body_out" align="center" >
      <img name="tpxs" src="<%=photoSrc%>"  width="400" height="300" border="0" alt="上传照片" onmousewheel="return bbimg(this)"  >
      </TD>
    </TR>
   </table>
</form>

<iframe name="paramIframe" style="DISPLAY: none"></iframe>
</body>
