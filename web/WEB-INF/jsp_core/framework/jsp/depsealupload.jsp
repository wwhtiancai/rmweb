<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.framework.service.DepartmentManager"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="java.util.*"%>
<%
String cdbh=(String)request.getParameter("cdbh");
DepartmentSeal qztpseal=(DepartmentSeal)request.getAttribute("qztpseal");
String zdsx=(String)request.getAttribute("zdsx");
DepartmentManager departmentManager = (DepartmentManager)request.getAttribute("departmentManager");
SysService sysService = (SysService)request.getAttribute("sysservice");
GHtmlService gHtmlService = (GHtmlService)request.getAttribute("gHtmlService");

%>

<head>
<title>部门印章维护</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript"  type="text/JavaScript">
function post(){
  if(document.all["yztp"].value==""){
	displayInfoHtml("[00R9922JK]:请先点浏览从本地磁盘上选择要上传的图片");
    //alert("请先点浏览从本地磁盘上选择要上传的图片");
    return false;
  }else{
    document.uploadform.action="department.frm?method=uploadfile";
    document.uploadform.target="paramIframe";
    document.uploadform.submit();
  }
}
function del(){
  document.uploadform.action="department.frm?method=delfile";
  document.uploadform.target="paramIframe";
  document.uploadform.submit();
}

function getFileName(){
  document.all("tpxs").src = document.all("yztp").value;
}


function resultsubmit(bj,description,return_out){
  if(bj==1){
	  displayInfoHtml("[00R9920J2]:照片保存成功! ");
      document.uploadform.btdel.disabled=false;
  }else if(bj==2) {
	  displayInfoHtml("[00R9920J3]:照片修改成功! ");
      document.uploadform.btdel.disabled=false;
  }else if(bj==3) {
	  displayInfoHtml("[00R9920J4]:照片已成功删除! ");
      window.close();
  }else if(bj==5) {
	  displayInfoHtml("[00R9922JL]:照片文件大小超过设定值,请压缩或重新设置系统参数后再上传! ");
  }else {
	  displayInfoHtml(description);
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
	document.uploadform.yzlx.value="<%=qztpseal.getYzlx()%>";
}
function changeyzlx(){
    document.uploadform.action="department.frm?method=uploadphoto";
    document.uploadform.target="";
    document.uploadform.submit();
}
</script>

</head>
<link href="theme/blue/blue.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue.js" type="text/javascript"></script>   
<BODY onLoad="init('<%=zdsx%>','<%=qztpseal.getGlbm()%>');">
<form method="post" enctype="multipart/form-data" name="uploadform">
<input type="hidden" name="glbm" value="<%=qztpseal.getGlbm()%>">

<table border="0" cellspacing="1" cellpadding="0" class="list_table" width="98%">
    <%
    String photoSrc = "";
    if(zdsx.equals("0")){
      	photoSrc = "theme/blue/nophoto.jpg";
    } else { 
    	Random rand = new Random();
    	int randnum = Math.abs(rand.nextInt());
    	photoSrc = "department.frm?method=outputphoto&glbm="+qztpseal.getGlbm()+"&yzlx="+qztpseal.getYzlx()+"&rand="+randnum;
    }	  
    %>
    <TR  align="center" valign="middle" class="list_body_out">
      <TD class="list_body_out">
      印章类型：
      <select  name="yzlx"  class="text_12 text_size_140" size="1" onchange="changeyzlx()">
      <%=gHtmlService.transDmlbToOptionHtml("00","0004","",false,"3","") %>
      </select>
           照片路径&nbsp;
        <input type="file" name="yztp" onChange="getFileName()"  class="text_12">&nbsp;
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
