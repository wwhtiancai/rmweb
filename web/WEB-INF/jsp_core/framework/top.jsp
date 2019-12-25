<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="com.tmri.pub.util.*"%>
<%@ page import="com.tmri.share.frm.service.*"%>
<%@ page import="com.tmri.share.frm.bean.*"%>
<% 
	Hashtable rightsobj = (Hashtable)session.getAttribute("rightsobj");
	String stralert = (String)session.getAttribute("stralert"); 
	GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)request.getAttribute("gSysparaCodeService");
	GSystemService sysService = (GSystemService)request.getAttribute("sysService");
    UserSession userSession = sysService.getSessionUserInfo(session);
    Department department = userSession.getDepartment();
    SysUser sysUser = userSession.getSysuser();
    
    String QYZWDLYZ = gSysparaCodeService.getSysParaValue("00","QYZWDLYZ");
    if(QYZWDLYZ==null){
    	QYZWDLYZ = "2";
    }
    String JZRZWGBZDZ = gSysparaCodeService.getSysParaValue("00","JZRZWGBZDZ");    
%>
<%
	String themestyle="1";	
	String loginstyle="";
	Cookie[] cookies = request.getCookies();
	if (cookies==null||cookies.length<1){
	themestyle="1";
	}else{
	for(int i=0;i<cookies.length;i++){
		if(cookies[i].getName().equals("themestyles")){
		themestyle=cookies[i].getValue();
		}
	}
	}
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
<link href="theme/<%=themestyle%>/style.css?v=1" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="theme/<%=themestyle%>/style.js" type="text/javascript"></script>
</head>
<script language="JavaScript" type="text/javascript">
<!--
var h="fold1"; 
var SSLPort="8443";  
var topcount = <c:out value="${topfoldcount}" escapeXml="false"/>;
function init(){
	var sft = "<%=request.getAttribute("SFT")%>";
	var ccookie = document.cookie;
	if(ccookie != '' && typeof(ccookie) != undefined){
		document.cookie = "sft=" + sft;
	}else{
		document.cookie = "sft=" + sft;
	}
<%if(stralert!=null&&stralert.length()>0){%>
alert("<%=stralert%>");
<%}%>	
}
function interfaceDoMenu(c){
	var count=15;
	var ipos = c.indexOf("_");
	var topindex = c.substring(0,ipos);
	var fold1 = c.substr(ipos+1);
	for(var i=1;i<=count;i++){
		try{
			document.getElementById("fold" + topindex + "_" + i).className="interface_top_0";
		}catch(e){}
	}
	document.getElementById("fold" + topindex + "_" + fold1).className="interface_top_1";	
	try{
		parent.leftfrm.interfaceDoMenu(c);
	}catch(e){}
}

function openWindow(sPath,sSfqp)
{
  var windowheight=screen.height;
  var windowwidth =screen.width;
  windowheight=(windowheight-600)/2;
  windowwidth=(windowwidth-800)/2;
  var pop;
  //pop=window.open(sPath,"vehmain","resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=790,height=590,top="+windowheight+",left="+windowwidth);

  if (sSfqp=="1"){
    pop=window.open(sPath,"vehmain<c:out value='${userSession.sysuser.yhdh}'/>","fullscreen=yes,top="+windowheight+",left="+windowwidth+",status=yes,width=800,height=600,scrollbars=yes,resizable=yes");
  }else if(sSfqp=="2"){
    pop=window.open(sPath,"_blank");
  }
  else{
    pop=window.open(sPath,"vehmain<c:out value='${userSession.sysuser.yhdh}'/>","resizable=no,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=590,top="+windowheight+",left="+windowwidth);
  }
  pop.focus();
}

function logout(){
    <%if(JZRZWGBZDZ!=null&&!JZRZWGBZDZ.equals("")){%>
    parent.close();
    <%}else{%>
	document.logoutform.action="login.frm?method=userquit";
	document.logoutform.submit();
	<%}%>
}
function refreshIframe(iframeid){
	window.parent.refreshIframe(iframeid);
}
function reload(){
	parent.location.href = "./";
}

function about(){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-240)/2;
	windowwidth=(windowwidth-320)/2;
	//showWindow("main.frm?method=about",320,240)
	pop=window.open("main.frm?method=about","about","resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=320,height=240,top="+windowheight+",left=" + windowwidth);
}

function desktop(){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-200)/2;
	windowwidth=(windowwidth-320)/2;
	pop=window.open("main.frm?method=desktop","desktop","resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=320,height=200,top="+windowheight+",left=" + windowwidth);
}

function editpassword(){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-290)/2;
	windowwidth=(windowwidth-310)/2;
	pop=window.open("sysuser.frm?method=editpassword","editpassword","resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=310,height=290,top="+windowheight+",left=" + windowwidth);
}

function showWindow(url,width,height){
	var r=window.showModalDialog(url,window,"dialogWidth:"+width+"px;dialogHeight:"+height+"px;center:1;help:0;resizable:0;status:0;scroll:1;");
}
//-->
</script>
<body onload="init()">
<form name=logoutform action="" method=post target="paramIframe">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="top_table">
	<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="62" align="left" valign="top">
						<table width="293" border="0" cellspacing="0" cellpadding="0" align="right" background="theme/style/top_title.gif">
							<tr>
								<td height="27" align="center">
									<table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="20" align="left"><img src="theme/easy/info.png" alt="" border="0"></td>	
											<td style="padding-top:2px;"><a href="#" class="text_12" style="color:white;font-weight:bold;" onClick="about()">关于</td>
											<%--<td width="20" align="left"><img src="theme/style/wdzx.gif" alt="" border="0"></td>
											<td style="padding-top:2px;"><a href="javascript:openWindow('doc.frm?method=showMain')" class="text_12" style="color:white;font-weight:bold;">文档中心</a></td>
											 --%><td width="13" align="center"><img src="theme/style/top_4.gif" alt="" border="0"></td>
											<td width="20" align="left"><img src="theme/easy/warning.png" alt="" border="0"></td>
											<%if(department.getJzlx()!=null&&"17".equals(department.getJzlx())){ %>
											<td style="padding-top:2px;"><a href="javascript:editpassword()" class="text_12" style="color:white;font-weight:bold;">密码修改</td>
											<%}else{%>
											<td style="padding-top:2px;"><a href="javascript:openWindow('sysuser.frm?method=editpolicemanpassword')" class="text_12" style="color:white;font-weight:bold;">密码修改</td>
											<%}%>
											<td width="20" align="left"><img src="theme/easy/desktop.png" alt="" border="0"></td>
											<td style="padding-top:2px;"><a href="javascript:desktop()" class="text_12" style="color:white;font-weight:bold;">定制桌面</td>											
											<%if(QYZWDLYZ.equals("99")){%>
											<td width="20" align="left"><img src="theme/easy/edit.png" alt="" border="0"></td>
											<td style="padding-top:2px;"><a href="javascript:openWindow('sysuser.frm?method=editfinger')" class="text_12" style="color:white;font-weight:bold;">指纹修改</td>
											<%} %>											
											<%--
											<td width="13" align="center"><img src="theme/style/top_4.gif" alt="" border="0"></td>
											<td width="20" align="left"><img src="theme/style/top_2.gif" alt="" border="0"></td>
											<td style="padding-top:2px;"><a href="<c:url value='main.frm?method=right'/>" class="text_12" target="rightfrm" style="color:white;font-weight:bold;">首页</td>
											--%>
											<td width="13" align="center"><img src="theme/style/top_4.gif" alt="" border="0"></td>
											<td width="20" align="left"><img src="theme/easy/exit.png" alt="" border="0"></td>
											<td style="padding-top:2px;"><a href="#" onClick="logout()" class="text_12" style="color:white;font-weight:bold;">退出</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<td valign="bottom"><div id="div_top_show"></div>
				<c:out value="${topfoldhtml}" escapeXml="false"/>
				</td>
				<tr>
					<td valign="middle"><c:out value="${foldhtml}" escapeXml="false"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
<iframe name="paramIframe"  style="DISPLAY: none" height="100" width="500"></iframe>
</html>