<%@ page contentType="text/html; charset=gb2312"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
	String themestyle="1";
	String loginstyle="";
	Cookie[] cookies = request.getCookies();
	if (cookies==null||cookies.length<1){
	themestyle="1";
	loginstyle="1";
	}else{
	for(int i=0;i<cookies.length;i++){
		if(cookies[i].getName().equals("themestyles")){
			themestyle=cookies[i].getValue();
		}
	}
	}
	ApplicationContext ctx=null;
	if(ctx==null){
		ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	}		
	GSysparaCodeService gSysparaCodeService=(GSysparaCodeService)ctx.getBean(GSysparaCodeService.class);	
    String QYZWDLYZ = gSysparaCodeService.getSysParaValue("00","QYZWDLYZ");
    String JZRZWGBZDZ = gSysparaCodeService.getSysParaValue("00","JZRZWGBZDZ");
    if(QYZWDLYZ==null){
    	QYZWDLYZ = "2";
    }
	String classid=gSysparaCodeService.getSysParaValue("00","ZWKJID");    
	String SSLPORT_PARA=gSysparaCodeService.getSysParaValue("00","SSLPORT");
%>
<html>
<link href="theme/<%=themestyle%>/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="theme/<%=themestyle%>/style.js" type="text/javascript"></script>
<style type="text/css">
.tt2 {
	font-size: 12px;
	color: #003399;
	text-decoration: none;
	background-color: #FFFFFF;
	border: 1px solid #003399;
}
.style3 {
	font-family: Georgia, "Times New Roman", Times, serif;
	font-weight: bold;
	font-size: 14px;
}
.style4 {font-size: 16px}
.style5 {
	font-size: 14px;
	font-weight: bold;
}
.style10 {font-size: x-large}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%=Constants.SYS_SYSTEMTOPIC%></title>
</head>
<script language="JavaScript" src="js/basjs/char_func.js"></script>
<%--
<OBJECT ID="vehPrinter"
	CLASSID="CLSID:F1EFB37B-C778-4AF8-B0C2-B647C9E89E2D"
	CODEBASE="VehPrint_web.cab#version=2,6,0,0">
</OBJECT>
--%>
<%=JspSuport.getInstance().JS_ALL%>
<SCRIPT type="text/JavaScript" language="javascript">
var context = "<%=Constants.SYS_CONTEXT%>";
var keylength = 1;
//var keylength = 0;
window.setInterval("readQrtext();",200);
function checkDy(){
    if(document.loginform.yhdh.value=="")
    {
       document.loginform.isRead.value="0";
    }
    var isRead=document.loginform.isRead.value;
    if(isRead=="0")//未读取到信息，可读
    {
        return true;
    }
    else
    {
       return false;
    }
}
//显示出错信息
function setPrompt(msg){
}	   
function readQrtext(){
  if (checkDy()){   
 			var barcodetemp ="";
 		/*
 			var strbarcode=vehPrinter.GetQrText();   
   		if (strbarcode=="" ){
       			return 0;
   		}
   		if (strbarcode=="-1"){
       		setPrompt("读取二代证信息时发生错误，请检查设备是否正确连接!");
      			 return 0;
   		}
   		setPrompt("成功读取信息！");
  		parseCardInfo(strbarcode);
  		*/
   	}
}
function parseCardInfo(sss){ 
    //初领时读取数据
    document.loginform.isRead.value="1";
    sfzxx = sss.split("|");
    ls_xm = sfzxx[1];
    ls_sfzmhm = sfzxx[6];//取身份证号
    document.loginform.yhdh.value = ls_sfzmhm;
    document.loginform.dlms.value = "3";
    document.loginform.yhdh.disabled = true;
}   	   
function InitNetSsVerify() {
  <%if(QYZWDLYZ.equals("1")){%>
  UserId = loginform.yhdh.value;
  FReg.UCtrl.SetFPPackInfo(UserId,1);
  iRt = FReg.UCtrl.InitInstance("12345678912345678912345678912345");  
//  FReg.initReturnValue.value = iRt;
  FReg.UCtrl.SetParameter(0);
  FReg.UCtrl.SetFPVersion(1);
  <%}%>
}
function init(){
	var visitname=getCookie('visitname');
	var zhpt_dlmsmc=getCookie('zhpt_dlmsmc');
   <%
   if(Constants.SYS_XTZT.equals("1")){%>
		var url = window.location.href;
		var index = url.indexOf(context);
		url = url.substring(0,index + context.length) + "/sysInit.frm?method=findForward";
		window.location=url;
	<%}%>
	if( visitname != ""){
		document.loginform.yhdh.value = visitname;
		//document.loginform.yzm.focus();
		document.loginform.mm.focus();
	}else{
		document.loginform.yhdh.focus();
	}    
	if(zhpt_dlmsmc!=""&&zhpt_dlmsmc=="2"){
		zwdl_id.style.display = "block";
		mmdl_id.style.display = "none";
	}
	InitNetSsVerify();
}
  function CheckData(flag){
    if(mmdl_id.style.display=="block"){
    	/**if(keylength<document.loginform.mm.value.length){
	      alert("不能复制密码，请手工输入密码！");
	      keylength = 0;
	      document.loginform.mm.focus();
	      event.keyCode = 0;event.returnValue=false;
	      return false;    	
    	}*/
	    if(document.loginform.yhdh.value.trim()==""){
	      alert("请输入登录用户名！")
	      document.loginform.yhdh.focus();
	      event.keyCode = 0;
	      event.returnValue=false;
	      return false;
	    }
	    if(document.loginform.mm.value.trim()==""){
	      alert("请输入登录密码！")
	      document.loginform.mm.focus();
	      event.keyCode = 0;event.returnValue=false;
	      return false;
	    }
	    if(flag==1&&event.srcElement.name!="yzm"){
		    if(document.loginform.yzm.value==""){
		      document.loginform.yzm.focus();
		      event.keyCode = 0;event.returnValue=false;
		      return false;	    	
		    }
	    }
		if(document.loginform.yzm.value.length!=4){
	      alert("请输入4位验证码！")
	      document.loginform.yzm.focus();
	      event.keyCode = 0;event.returnValue=false;
	      return false;		  
		}
	    if(document.loginform.yhdh.disabled==false&&document.loginform.yhdh.value.trim().length>10){
	      alert("登录用户名不能超过10个字。")
	      document.loginform.yhdh.focus();
	      return false;
	    }
	    
	    if(document.loginform.dlms.value=="1"){
	    	setCookie('visitname',document.loginform.yhdh.value,365);
	    }else{
			setCookie('visitname',"",365);    
	    }	
	    document.loginform.yhdh.disabled = false;
		document.loginform.action="login.frm?method=check";
		document.loginform.submit();    
    }else{
    	mmdl_id.style.display="block";
    	zwdl_id.style.display="none";
    	yzmdl_id.style.display = "none";
    }
  }
  function reset(){
   document.loginform.yhdm.value="";
   document.loginform.mm.value="";
   document.loginform.yhdm.focus();
  }
  function ssllogin(){
    <%if(JZRZWGBZDZ!=null&&!JZRZWGBZDZ.equals("")){%>
    	window.location = "<%=JZRZWGBZDZ%>";	  
    <%}else{%>
    	  var url = window.location.href;
    	  //var url='http://10.2.45.8/trffweb';
    	  url=url.substring(0,url.indexOf(context));  
    	  var sslport='<%=SSLPORT_PARA%>';
    	  //var sslport='10.2.45.20:80:9443;10.2.45.8:80:9443';
	      var ip,port,newport,tmp,pos;
	      url = url.substring(url.indexOf("://")+3,url.length);
	      var index = url.indexOf(':');
	      if(index == -1){
	    	  //没有端口的情况
	    	  port="80";
	  	      index = url.indexOf('/');
		      ip = url.substring(0,index);
	      }else{
	    	  ip=url.substring(0,url.indexOf(":",8));
		      port=url.substring(url.indexOf(":",8)+1,url.indexOf("/",8));
	      }
	      tmp=ip+":"+port;
	      pos=sslport.indexOf(tmp);
	      if(pos>=0)
	      {
	    	//newport=sslport.substring(pos+tmp.length+1,sslport.indexOf(";",pos+tmp.length));
	    	  newport=sslport.substring(pos+tmp.length+1,sslport.indexOf(";",pos+tmp.length));
	      }
	      else{
	    	newport="9443";
	      }
   		  url="https://"+ip+":"+newport+"/";
   		  url=url +context+"/login.frm?method=check&dlms=2&jspname=" + loginform.jspname.value;
		  window.location=url;
    <%}%>
  }
function showHand(){
      var tp = FReg.UCtrl.GetFingerPrintData();      
      loginform.zwtz.value = tp;	
      loginform.dlms.value = "4";
   	  setCookie('visitname',document.loginform.yhdh.value,365);      
      loginform.action="login.frm?method=check";
      loginform.submit();
}
function zwlogin(){
	if(document.loginform.yhdh.value.trim()==""){
		alert("请输入登录用户名。")
		document.loginform.yhdh.focus();
		return false;
	}
	zwdl_id.style.display = "block";
	mmdl_id.style.display ="none";
	yzmdl_id.style.display = "none";  	
	InitNetSsVerify();
}
function HasGotFeatureEvent(){
	showHand(); 
}
function getCookie(c_name){
	if (document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1){ 
			c_start=c_start + c_name.length+1 
			c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) 
				c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	return ""
}
function setCookie(c_name,value,expiredays){
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+	((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}
function BSkeyDown(){    	
	keylength += 1;
}
</SCRIPT>
<style type="text/css">
<!--
body{margin:0;paddin:0;background:#094E89}
.background{background:url(theme/1/login_bg.jpg) repeat-x left top;}
.body{width:1024px;background:url(theme/1/login.jpg) no-repeat left top;}
-->
</style>
<body onLoad="init()"  onKeyDown="if (event.keyCode==13) CheckData(1); ">
<%if(themestyle.equals("1")){%>
<form  id="FReg" name="loginform" action="" method="post">
<input type="hidden" name="yhmm" value=""/>
<input type="hidden" name="dlms" value="1"/>
<input type="hidden" name="isRead" value="0"/> 
<input type="hidden" name="zwtz" value=""/>
<input type="hidden" name="jspname" value="index"/>
<input type="hidden" name="login_modal" value="1"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="background">
	<tr>
		<td align="center" valign="top">
			<div style="position:relative;width:1024px;height:600px;text-align:center;" class="body">
				<div style="width:350px; height:250px;left:650px;top:310px;font-size:14px;position:absolute">
					<div style="height:30px;line-height:30px;">
						<div style="width:100px; text-align:right;float:left;">&nbsp;</div>
						<div style="width:160px; text-align:right;float:left;"><b style="color:red"><c:out value="${message}"/></b>&nbsp;&nbsp;&nbsp;&nbsp;</div>
					</div>
					<div style="height:30px;line-height:30px;">
						<div style="width:100px; text-align:right; float:left;">用户名：</div>
						<div style="width:160px;float:left;text-align:left;">
							<input name="yhdh" type="text" class="login_input" maxlength="10"
								   style="width:135px;margin-top:8px;" value="320000">
							<div id="zwdl_id" style="display:none">
								<%if(QYZWDLYZ.equals("1")){ %>
								<OBJECT ID="UCtrl" width="97" height="104"
										CLASSID="CLSID:<%=classid %>">
									<param name="Token" value="12345678912345678912345678912345" />
								</OBJECT>
								<script for="UCtrl" event="GotFeatureEvent()" language="javascript">
									HasGotFeatureEvent();
								</script>
								<%} %>
							</div>
						</div>
					</div>
					<div id="mmdl_id"  style="height:30px;line-height:30px;display:block;">
						<div style="width:100px; text-align:right;float:left;">密码：</div>
						<div style="width:160px;float:left;text-align:left;">
							<input name="mm" type="password" class="login_input" maxlength="18"  style="width:135px;margin-top:8px;"  onkeyup="BSkeyDown()">
						</div>
					</div>
					<div id="yzmdl_id" style="height:30px;line-height:30px;display:block;">
						<div style="width:100px; text-align:right;float:left;">验证码：</div>
						<div style="width:225px;float:left;">
							<input name="yzm" type="text" class="login_input" maxlength="4"  style="width:75px;text-transform: uppercase;float:left;margin-top:8px;"  value="" >
							<iframe name="ifrmaeecsideveh" width="120" height="30" frameborder="0" src="login.frm?method=getVadidateImageJs" allowTransparency="false" scrolling=no></iframe>
						</div>
					</div>
					<div style="text-align:center;width:260px;height:30px;line-height:30px;margin-top:10px">
						<input name="Submit" type="button" class="button" value="登录" style="width:70px;" onClick="CheckData(2)">
					</div>
					<div style="height:30px;width:260px;"></div>
					<div style="width:320px;">
						<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="text_12 text_white">
							<tr>
								<td width="10%" height="18" align="center">&nbsp;</td>
								<td >版权所有 公安部交通管理科学研究所</td>
							</tr>
							<tr>
								<td height="18"  align="center">&nbsp;</td>
								<td align="left">支持电话：0510-85511931</td>
							</tr>
						</table>
					</div>
				</div>

			</div>
		</td>
	</tr>
</table>

</FORM>
<%}%>


</body>
</html>