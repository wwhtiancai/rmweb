<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.share.frm.util.DateUtil"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.framework.service.SysuserManager"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="java.util.List"%>
<html>
<head>
	<title>用户维护</title>
</head>
<%
	String glqx = (String) request.getAttribute("glqx");
	String sqqx = (String) request.getAttribute("sqqx");
	String xgxqms = (String) request.getAttribute("xgxqms");
	String xtglyzxjb = (String) request.getAttribute("xtglyzxjb");
	String xtglyzxjbmsg = (String) request.getAttribute("xtglyzxjbmsg");
	
	Department dept = (Department)request.getAttribute("department");
	String ywlx="";//dept.getYwlb();
	String kgywyhlx="";
	String yhssyw="";

	SysUser user = (SysUser)request.getAttribute("sysuser");
	String modal = (String)request.getAttribute("modal");
	SysService sysService = (SysService)request.getAttribute("sysservice");
	SysuserManager sysuserManager = (SysuserManager)request.getAttribute("sysuserManager");
	
	String yxq = (String)request.getAttribute("yxq");
	String photoSrc = "sysuser.frm?method=outputphoto&yhdh="+user.getYhdh();
	//取当前部门可授权业务范围
	//String yhssyw = dept.getKclyw();
	//获取当前用户的可管理用户类型
	//String kgywyhlx=sysService.getSessionUserInfo(session).getSysuser().getKgywyhlx();
	//if(kgywyhlx.equals("")){
	//	kgywyhlx="1000000000";
	//}
	
	//自己不能给自己授权
	//if(sysService.getSessionUserInfo(session).getSysuser().getYhdh().equals(user.getYhdh())){
	//	sqqx="0";
	//}
%>	
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<script language="javascript">

//重置密码
function setupPassword(){
	if(confirm("是否确认要将该用户的密码重置为初始化密码(888888)？")){
		document.formedit.action="sysuser.frm?method=saveSetuppassword";
		document.formedit.submit();
	}
}
//启用
function reuse(){
	if(confirm("是否确认要启用该用户？")){
		document.formedit.action="sysuser.frm?method=unlock";
		document.formedit.submit();
	}
}
//停用
function restop(){
	if(confirm("是否确认要停用该用户？")){
		document.formedit.action="sysuser.frm?method=unlock";
		document.formedit.submit();
	}
}

function showUsercodetype(){
    window.location = "handleCode.do?method=showUsercodetype&yhdh=" +formedit.yhdh.value;
}

function init(){
	var tmparr;
	var tmp,tmproles,tmpcxdh;
	var strRoles,strCxdhs;
	
	
	//置系统管理员
	setradio(formedit.xtgly1,'<%=user.getXtgly()%>');
	var xtglyzxjb=formedit.xtglyzxjb.value;
	if(xtglyzxjb==2){
		document.getElementById("xtgly11").disabled=true;
	}
	

	//根据当前用户设置，可授予的用户类型
	setckywlxabled(formedit.kgywyhlx1,'<%=kgywyhlx%>');
	setckywlxabled(formedit.yhssyw1,'<%=yhssyw%>');
	
	
	//置自由角色属性
	var xtgly=getradio(formedit.xtgly1);
	if(xtgly==2){
		document.getElementById("ksqx").style.display="none";
		document.getElementById("divkgywyhlx").style.display="none";
		//
	}else{
		document.getElementById("ksqx").style.display="";
		//系统管理员，置可管理业务用户类型
		setckyhlx(formedit.kgywyhlx1,'<%=user.getKgywyhlx()%>');
	}		

	//置用户所属业务
	setckyhlx(formedit.kgywyhlx1,'<%=user.getKgywyhlx()%>');
	setckyhlx(formedit.yhssyw1,'<%=user.getYhssyw()%>');
	
	//判断该用户状态，正常提示不需要解锁
	var zt=formedit.zt.value;
	if(zt=='3'){
		formedit.btlock.disabled=false;
		formedit.btstop.disabled=true;
		formedit.btfail.disabled=true;
	}else if(zt=='2'){
		formedit.btlock.disabled=true;
		formedit.btstop.disabled=true;
		formedit.btfail.disabled=false;
	}else{
		formedit.btlock.disabled=true;
		formedit.btstop.disabled=false;
		formedit.btfail.disabled=true;
	}		

	//置检验点
	<%--
	<%if(ywlx.charAt(3)=='1'&&!ywlx.substring(0,10).equals("1111111111")){%>
	<%}else{%>
		settextreadonly(formedit.jyd,true);
	<%}%>
    --%>

	//置权限模式，缺省角色
	setradio(formedit.oqxms,'<%=user.getQxms()%>');
	formedit.qxms.value='<%=user.getQxms()%>';
	checkQxms();

	//角色模式是否可改为自由权限
	var xgxqms=formedit.xgxqms.value;
	if(xgxqms=='1'){
		//自由模式不能选择
		document.getElementById("oqxms2").disabled=true;
	}
		
	
	<%if(!user.getYhdh().equals("")){%>
	    setedit();
	<%}else{%>
		setadd();
		document.all["mmyxq"].value="<%=yxq%>";
		document.all["zhyxq"].value="<%=yxq%>";
	<%}%>

	//置管理权限
	var glqx=formedit.glqx.value;
	if(glqx=='0'){
		setview();
	}	
	//授权权限
	var sqqx=formedit.sqqx.value;
	if(sqqx=='0'){
		formedit.btsaverole.disabled=true;
	}	
	//置强制二代证登录
	setck(formedit.bz,'<%=user.getBz()%>');
	
	
    //20110802atm信息初始化,编辑状态
    var sfmj=formedit.sfmj.value;
	if(sfmj=='4'){
		formedit.btuserinfo.disabled=true;
		settextreadonly(formedit.yhdh,false);
	}else{
		formedit.btuserinfo.disabled=false;
		settextreadonly(formedit.yhdh,true);
	}
	//获取操作和管理权限
	getuserglqxandczqx();
	
	var i = 0;
	checkfields[i++] = new CheckObj("yhdh","用户名",FRM_CHECK_NULL,0,1);
	//checkfields[i++] = new CheckObj("yhdh","用户名",FRM_CHECK_SPECIAL_CHAR,0,1);
	//checkfields[i++] = new CheckObj("xm","用户姓名",FRM_CHECK_NULL,0,1);
	//checkfields[i++] = new CheckObj("sfzmhm","身份证明号码",FRM_CHECK_NULL,0,1);		
	//checkfields[i++] = new CheckObj("sfzmhm","身份证明号码",FRM_CHECK_SFZMHM,0,1);
	checkfields[i++] = new CheckObj("mmyxq","密码有效期",FRM_CHECK_NULL,0,1);
	checkfields[i++] = new CheckObj("mmyxq","密码有效期",FRM_CHECK_DATE,0,1);
	checkfields[i++] = new CheckObj("zhyxq","账户有效期",FRM_CHECK_NULL,0,1);
	checkfields[i++] = new CheckObj("zhyxq","账户有效期",FRM_CHECK_DATE,0,1);	
	checkfields[i++] = new CheckObj("ipqsdz1","IP起始地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipqsdz2","IP起始地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipqsdz3","IP起始地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipqsdz4","IP起始地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz1","IP结束地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz2","IP结束地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz3","IP结束地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz4","IP结束地址",FRM_CHECK_IPDZ,0,1);	

	checkfields[i++] = new CheckObj("gdipdz11","固定IP地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz12","固定IP地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz13","固定IP地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz14","固定IP地址",FRM_CHECK_IPDZ,0,1);	
	<%--
	checkfields[i++] = new CheckObj("gdipdz21","固定IP地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz22","固定IP地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz23","固定IP地址",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz24","固定IP地址",FRM_CHECK_IPDZ,0,1);		
	--%>
	checklen = i;	
}

function setadd(){
	formedit.btupload.disabled=true;
	formedit.btreset.disabled=true;
	formedit.btdel.disabled=true;
	formedit.btfail.disabled=true;
	formedit.btsave.disabled=false;
	formedit.btlock.disabled=true;
	formedit.btstop.disabled=true;		
}

function setedit(){
	formedit.btupload.disabled=false;
	formedit.btreset.disabled=false;
	formedit.btdel.disabled=false;
	formedit.btsave.disabled=false;	
}

function setview(){
	formedit.btupload.disabled=true;
	formedit.btreset.disabled=true;
	formedit.btdel.disabled=true;
	formedit.btfail.disabled=true;
	formedit.btsave.disabled=true;
	formedit.btlock.disabled=true;
	formedit.btstop.disabled=true;	
}

function resetform(){
	document.all["yhdh"].value="";
	document.all["xm"].value="";
	document.all["rybh"].value="";
	document.all["sfzmhm"].value="";
	document.all["mm"].value="888888";
	document.all["bz"].value="";
	setadd();
}

function del(){
	if(confirm("是否确信删除当前用户？")){
		document.formedit.action="sysuser.frm?method=removeUser";
		document.formedit.submit();
	}
}

function getLoginFail(glbm,yhdh){
	var url="sysuser.frm?method=getLoginFail&glbm=" + glbm + "&yhdh="+yhdh;
	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		formedit.zt.value="1";
		formedit.ztmc.value="正常";
		formedit.btfail.disabled=true;
	}	
}

//保存用户基本信息
function saveuser() {
	if(checkallfields(checkfields,1)==0) {
		return 0;
	}
	//
	
	//监测固定ip2地址
	var gdipdz21=formedit.gdipdz21.value;
	var gdipdz22=formedit.gdipdz22.value;
	var gdipdz23=formedit.gdipdz23.value;
	var gdipdz24=formedit.gdipdz24.value;
	
	if(gdipdz21!=""||gdipdz22!=""||gdipdz23!=""||gdipdz24!=""){
		if(checkIp(formedit.gdipdz21,"固定IP地址2",true)!="1"){
			formedit.gdipdz21.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz22,"固定IP地址2",true)!="1"){
			formedit.gdipdz22.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz23,"固定IP地址2",true)!="1"){
			formedit.gdipdz23.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz24,"固定IP地址2",true)!="1"){
			formedit.gdipdz24.value="";
			return false;
		}
		document.all["gdip1"].value=document.all["gdipdz21"].value+"."+document.all["gdipdz22"].value+"."+document.all["gdipdz23"].value+"."+document.all["gdipdz24"].value;
	}else{
		document.all["gdip1"].value="";
	}
	
	var gdipdz31=formedit.gdipdz31.value;
	var gdipdz32=formedit.gdipdz32.value;
	var gdipdz33=formedit.gdipdz33.value;
	var gdipdz34=formedit.gdipdz34.value;	
	if(gdipdz31!=""||gdipdz32!=""||gdipdz33!=""||gdipdz34!=""){
		if(checkIp(formedit.gdipdz31,"固定IP地址3",true)!="1"){
			formedit.gdipdz31.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz32,"固定IP地址3",true)!="1"){
			formedit.gdipdz32.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz33,"固定IP地址3",true)!="1"){
			formedit.gdipdz33.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz34,"固定IP地址3",true)!="1"){
			formedit.gdipdz34.value="";
			return false;
		}
		document.all["gdip2"].value=document.all["gdipdz31"].value+"."+document.all["gdipdz32"].value+"."+document.all["gdipdz33"].value+"."+document.all["gdipdz34"].value;
	}else{
		document.all["gdip2"].value="";
	}
		
	document.all["ipks"].value=document.all["ipqsdz1"].value+"."+document.all["ipqsdz2"].value+"."+document.all["ipqsdz3"].value+"."+document.all["ipqsdz4"].value;
	document.all["ipjs"].value=document.all["ipjsdz1"].value+"."+document.all["ipjsdz2"].value+"."+document.all["ipjsdz3"].value+"."+document.all["ipjsdz4"].value;
	document.all["gdip"].value=document.all["gdipdz11"].value+"."+document.all["gdipdz12"].value+"."+document.all["gdipdz13"].value+"."+document.all["gdipdz14"].value;
	if(formedit.sfmj.value=='1') {
		if(checknull(document.all["rybh"],"警号",true)!="1")return 0;
		if(checknum(document.all["rybh"],"警号",true)!="1")return 0;
		if(checklength(document.all["rybh"],6,"警号",true)!="1")return 0;
	}

	var  yhssyw=getckbitvalue(formedit.yhssyw1);
	formedit.yhssyw.value=yhssyw;

	//车管检验点
	//置检验点,改为根据人员所属业务类型
	//非atm
	if(formedit.sfmj.value!='4') {
		if(yhssyw.substr(1,1)=='1'){
			if(checknull(formedit.jyd,"车管检验点",true)!="1")return 0;
		}else{
			formedit.jyd.value="";
		}
	}
	//加判断不能为空
	//...
	if(formedit.yhssyw.value=="0000000000"){
		displayInfoHtml("[00R9942J4]:用户所属业务不能为空！");	
		return false;
	}
	
	//合并
	var mac=document.all["mac1"].value+"-"+document.all["mac2"].value
		+"-"+document.all["mac3"].value+"-"+document.all["mac4"].value
		+"-"+document.all["mac5"].value+"-"+document.all["mac6"].value;
	document.all["mac"].value=mac.toUpperCase();
	//判断mac地址信息？
	if(mac!="-----"){
		var reg_name=/[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}/; 
		if(!reg_name.test(document.all["mac"].value)){ 
			displayInfoHtml("[00R9942J6]:MAC地址格式不正确！"); 
			return false; 
		} 
	}
	
	//增加atm设备
	if(formedit.sfmj.value=='4') {
		var ipks=document.all["ipks"].value;
		var ipjs=document.all["ipjs"].value;
		if(ipks!=ipjs){
			displayInfoHtml("[00R9942J5]:ATM用户的ip起始和ip结束地址一致！");	
			return false;
		}
	}
	
	document.formedit.action="sysuser.frm?method=saveUser";
	document.formedit.submit();	
}


//保存用户权限信息
function saveuserrole() {
	//新增用户，需先保存用户信息
	var modal=formedit.modal.value;
	if(modal=='new'){
		displayInfoHtml("需先保存用户信息后，再进行授权！");
		return false;
	}	
	
	
	var strTmp;
	document.all["qxms"].value=getradio(document.all["oqxms"]);
	formedit.xtgly.value=getradio(formedit.xtgly1);	

	//获取可授权权限
	//分为系统管理员和非系统管理员
	if(getradio(formedit.xtgly1)==1){
		var grantroles=getckallvalue(formedit.yyyhz,"#");
		formedit.grantroles.value=grantroles;	
		//获取用户类别
		var  kgywyhlx=getckbitvalue(formedit.kgywyhlx1);
		formedit.kgywyhlx.value=kgywyhlx;
		//加判断不能为空
		//...		
		if(formedit.kgywyhlx.value=="0000000000"){
			displayInfoHtml("[00R9942J3]:管理业务用户类型不能为空！");
			return false;
		}	
	}else{
		formedit.grantroles.value=""; 
	}		
		
	
	var cddhs="";
	if(document.all["oqxms"].item(0).checked) {
		var cddhs=getckallvalue(formedit.yhz,"#");
		document.all["roles"].value=cddhs;
		/*可以取消授权
		if(checknull(document.all["roles"],"角色",false)!="1"){
			return false;
		}*/	
	}else {
		if(document.all.cddh)
		{
			if(document.all.cddh.type=="checkbox")
			{
				//单个
				if(document.all["cddh"].checked)
				{
					tmpsx=document.all["cddh"].value;
					j=tmpsx.indexOf("-");
					cddhs=tmpsx.substring(j+1);
				}else {
					cddhs="";
				}
			}else {
				var cddh_length=document.all["cddh"].length;
				for(var i=0;i<cddh_length;i++)
				{
					if(document.all["cddh"].item(i).checked==true)
					{
						tmpsx=document.all["cddh"].item(i).value;
						j=tmpsx.indexOf("-");
						tmpsx=tmpsx.substring(j+1);
						j=getCharCounts(tmpsx,"-");
						if(j==1) {
							j=cddhs.indexOf(tmpsx);
							if(j<0) {
								cddhs=cddhs+tmpsx+"-#";
							}
						}else {
							j=tmpsx.lastIndexOf("-");
							tmpsx1=tmpsx.substring(0,j+1);
							tmpsx2=tmpsx.substring(j+1);
							j=cddhs.indexOf(tmpsx1);
							if(j<0) {
								cddhs=cddhs+tmpsx+"#";
							}else {
								k=cddhs.indexOf("#",j);
								tmpsx1=cddhs.substring(j,k);
								if(tmpsx1.substring(tmpsx1.length-1)=="-") {
									tmpsx1=tmpsx1+tmpsx2;
								}else {
									tmpsx1=tmpsx1+","+tmpsx2;
								}
								cddhs=cddhs.substring(0,j)+tmpsx1+cddhs.substring(k);
							}
						}
					}
				}
				if(cddhs.substring(cddhs.length-1)=="#")
				{
					cddhs=cddhs.substring(0,cddhs.length-1);
				}
			}
		}
		document.all["cxdh"].value=cddhs;
		/*可以取消授权
		if(checknull(document.all["cxdh"],"权限",false)!="1"){
			return false;
		}*/	
	}
	document.formedit.action="sysuser.frm?method=saveUserrole";
	document.formedit.submit();	
}


//返回结果
function resultsubmit(strResult,strVal,strMessage) {
	if(strResult=="1"){
		displayInfoHtml(strMessage);
		setedit();
		formedit.modal.value="edit";
	  	window.opener.query();
	  	//新增用户，提示进行授权维护
	  	var modal=formedit.modal.value;
	  	if(modal=='new'){
		  	formedit.modal.value="edit";
		  	displayInfoHtml("用户新增后，可进行授权管理");
		  	//20110809重新加载授权角色
		  	getuserglqxandczqx();
	  	}
	  	
	} else if(strResult=="2") {
		displayInfoHtml(strMessage);
		setadd();
		formedit.modal.value="new";
		window.opener.query();
	} else if(strResult=="4") {
        //解锁 
		displayInfoHtml(strMessage);
		formedit.zt.value="1";
		formedit.ztmc.value="正常";
		formedit.btlock.disabled=true;
		formedit.btstop.disabled=false;
	} else if(strResult=="5") {
        //启用
		displayInfoHtml(strMessage);
		formedit.zt.value="1";
		formedit.ztmc.value="正常";
		formedit.btlock.disabled=true;
		formedit.btstop.disabled=false;		
	} else if(strResult=="6") {
        //停用
		displayInfoHtml(strMessage);
		formedit.zt.value="3";
		formedit.ztmc.value="停用";
		formedit.btlock.disabled=false;
		formedit.btstop.disabled=true;			
	}else {
		displayInfoHtml(strMessage);
	}
}


//获取role对应菜单
//type 1role 2user
function getusermenu(type){
	var yhz=getckallvalue(formedit.yhz,'#');
	var bmjb=formedit.bmjb.value;
	var yhdh=formedit.yhdh.value;
	var spath="sysuser.frm?method=queryProgramListByJsdh&type="+type+"&sjjsdh="+yhz+"&jscj="+bmjb+"&yhdh="+yhdh;
	send_request(spath,"fillrolemenu()",false);	
}


function fillrolemenu(){
	divcxdhs.style.display="";
	//处理管辖部门
	var results = _xmlHttpRequestObj.responseText;
	divcxdhs.innerHTML=results;
	domtab.init();
}

function getuserglqxandczqx(){
	var glbm=formedit.glbm.value;
	var yhdh=formedit.yhdh.value;
	var sfmj=formedit.sfmj.value;
	var spath="sysuser.frm?method=queryUserczqx&yhdh="+yhdh+"&glbm="+glbm+"&sfmj="+sfmj;
	send_request(spath,"filluserglqxandczqx()",false);	
}


function filluserglqxandczqx(){
	//处理管辖部门
	var results = _xmlHttpRequestObj.responseText;
	var valarray = results.split("AAAAA");
	//分割results,再处理
	divroles.innerHTML=valarray[0];
	divrolesyy.innerHTML=valarray[1];
}


function check_item(obj) {
	var bj=obj.value;
	var qx_length=document.all["cddh"].length;
	if(obj.checked==true) {
		for(var i=0;i<qx_length;i++) {
			if(document.all["cddh"].item(i).value.indexOf(bj)==0) {
				document.all["cddh"].item(i).checked=true;
			}
		}
	}else {
		for(var i=0;i<qx_length;i++) {
			if(document.all["cddh"].item(i).value.indexOf(bj)==0) {
				document.all["cddh"].item(i).checked=false;
			}
		}
	}
}

//选择系统管理员，只能角色授权
function checkxtgly(){
	var xtgly=getradio(formedit.xtgly1);
	if(xtgly==1){
		setckenabled(formedit.yyyhz);
		setckywlxabled(formedit.kgywyhlx1,'<%=kgywyhlx%>');
		document.getElementById("ksqx").style.display="";	
		document.getElementById("divkgywyhlx").style.display="";
	}else if(xtgly==2){
		//重置角色选择
		//clearck(formedit.yyyhz);
		setckdisabled(formedit.yyyhz);	
		setckdisabled(formedit.kgywyhlx1);	
		document.getElementById("ksqx").style.display="none";		
		document.getElementById("divkgywyhlx").style.display="none";
	}		
}


function checkQxms(){
	var oqxms=getradio(formedit.oqxms);
	if(oqxms==1){
		formedit.btyhz.disabled=false;
		divroles.style.display = "block";
		divcxdhs.style.display = "none";
		divcxdhs.innerHTML="";
	}else{
		formedit.btyhz.disabled=true;
		divroles.style.display = "none";
		divcxdhs.style.display = "block";
		//重置菜单目录
		getusermenu(2);
	}
}

function uploadqmtp(){
	openwin("","uploadphoto",true);
    document.formedit.action="sysuser.frm?method=uploadphoto";
    document.formedit.target="uploadphoto";
    document.formedit.submit();  	
}
//刷新签名图片
function refreshseal(){
	document.formedit.tpxs.src="<%=photoSrc%>&rand=<%=sysuserManager.getRand()%>";
}

//获取角色权限列表
//条件
function queryrolemenu(jsdh){
	if(jsdh==''){
		displayInfoHtml("[00R9942J1]:角色未选择");
		return;
	}	
  	var glbm=formedit.glbm.value;
	var sFeatures="dialogHeight:400px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("sysuser.frm?method=rolemenuresult&glbm="+glbm+"&jsdh="+jsdh, "",sFeatures);
	
}

function queryselectedrolemenu(obj){
	var jsdh=getckallvalue(obj,"A");
	queryrolemenu(jsdh);
}

//从民警和协警表获取警号，姓名，将用户代号用警号填充
function queryuserinfo(){
	var sfmj=formedit.sfmj.value;
	var glbm=formedit.glbm.value;
	var xm=formedit.xm.value;
	var url="sysuser.frm?method=userinfoquery&sfmj="+sfmj+"&glbm="+glbm+"&xm="+encodeURIComponent(encodeURIComponent(xm));
	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog(url, "",sFeatures);
	if(vReturnValue!=undefined){
		filluserinfo(vReturnValue);
	}			
}

function filluserinfo(results){
	//警号，姓名，将用户代号用警号填充
	if(parseInt(results)==1){
		displayInfoHtml("[00R9940J2]:未找到民警/协警/工作人员信息,请先采集基础信息！");
	}else{
		valarray = results.split("#");
		formedit.xm.value=valarray[0];
		formedit.sfzmhm.value=valarray[1];
		formedit.rybh.value=valarray[2];

		var yhdh=formedit.yhdh.value;
		if(yhdh==''){
			formedit.yhdh.value=formedit.rybh.value;
		}	
	}
	return;		
}

function tab_c(idx){
	var tab=document.getElementsByName("tab")
	for(var i=0;i<tab.length;i++) {
		tab[i].style.display="none";
	}
	var idxtab=document.getElementById("tab"+idx);
	idxtab.style.display=""
}

//选择授权角色
function fnOpenrole(modal,ckname,divroleid){
  	var glbm=formedit.glbm.value;
  	var yhdh=formedit.yhdh.value;
  	var sFeatures="dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("sysuser.frm?method=userRole&glbm="+glbm+"&yhdh="+yhdh+"&modal="+modal, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		var spath="sysuser.frm?method=fillrolehtml&jsdh="+vReturnValue+"&ckname="+ckname;
		send_request(spath,"fillrolehtml('"+divroleid+"')",false);			
	}
}

//根据选择的角色，构造页面
function fillrolehtml(divroleid){
	var results = _xmlHttpRequestObj.responseText;
	document.getElementById(divroleid).value=results;
}

function show_Form(m_formname) {
   vio_klsjxx.style.display = "none";
   vio_qzxx.style.display = "none";  

   
   	if (m_formname == "vio_qzxx") { 
     vio_qzxx.style.display = "block";   
     vio_klsjxx.style.display = "none";  
   	}
   
   	if (m_formname == "vio_klsjxx") {
     vio_klsjxx.style.display = "block";   
     vio_qzxx.style.display = "none";   
	}
}
//20110802新增是否民警变化
function changesfmj(val){
	formedit.xm.value='';
	formedit.yhdh.value='';
	formedit.sfzmhm.value='';

	if(val=='4'){
		formedit.btuserinfo.disabled=true;
		settextreadonly(formedit.yhdh,false);
		getuserglqxandczqx();
	}else{
		formedit.btuserinfo.disabled=false;
		settextreadonly(formedit.yhdh,true);
	}
}

</script>	
<link href="theme/domtab/domtab.css" rel="stylesheet" type="text/css"> 
<script language="javascript" type="text/javascript"  src="theme/domtab/domtab.js"></script>
	
	<body onload="init()" onUnload="closesubwin();">
		<script language="JavaScript" type="text/javascript">vio_title('用户维护-编辑')</script>
		<script language="JavaScript" type="text/javascript">vio_seach()</script>
		
		<form name="formedit" action="" method="post" target="paramIframe">
			<input type="hidden" name="modal" value="<%=modal%>">
			
			<input type="hidden" name="glqx" value="<%=glqx%>">
			<input type="hidden" name="sqqx" value="<%=sqqx%>">
			<input type="hidden" name="xgxqms" value="<%=xgxqms%>">
			<input type="hidden" name="xtglyzxjb" value="<%=xtglyzxjb%>">
			
			<input type="hidden" name="bmjb" value="<%=dept.getBmjb()%>">
			<input type="hidden" name="ipks">
			<input type="hidden" name="ipjs">
			<input type="hidden" name="gdip">
			<input type="hidden" name="gdip1">
			<input type="hidden" name="gdip2" value="<%=user.getGdip2()%>">
			<input type="hidden" name="qxms">
			<input type="hidden" name="zt" value="<%=user.getZt()%>">
			<input type="hidden" name="cxdh">
			<input type="hidden" name="roles">
			<input type="hidden" name="grantroles">
			<input type="hidden" name="spjb" value="<%=user.getSpjb()%>">
			<input type="hidden" name="spglbm" value="<%=user.getSpglbm()%>">
			<input type="hidden" name="xtgly">
			<input type="hidden" name="mm" value="888888">
			<input type="hidden" name="temp" value="">
			<input type="hidden" name="kgywyhlx" value="<%=user.getKgywyhlx()%>">
			<input type="hidden" name="yhssyw" value="<%=user.getYhssyw()%>">
			
			<input type="hidden" name="mac" value="<%=user.getMac()%>">
			<%=JspSuport.getInstance().getWebKey(session)%>
			
					
		<span class="s1" style="height: 1px;"></span>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="31" valign="bottom" background="theme/style/tab_user_0.jpg">
  <input name="btyh" id="btyh" type="button" class="tab_user_1" value="用户信息" style='cursor:hand;height:28' onclick="show_Form('vio_qzxx');user_button_color('btyh');">
  <input name="btsq" id="btsq" type="button" class="tab_user_2" value="授权信息" style='cursor:hand;height:28' onclick="show_Form('vio_klsjxx');user_button_color('btsq');">
</td>
  </tr>
</table>
		<div><h2><a name="vio_qzxx" id="vio_qzxx" style="display:block">
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<tr>
					<td width="12%" class="list_headrig">部门代码&nbsp;</td>
					<td class="list_body_left" width="20%">
						<input type="text" size="20" name="glbm" class="text_12 text_size_120 text_readonly"  
							readonly value="<%=user.getGlbm()%>">
					</td>
					<td width="10%" class="list_headrig">部门名称&nbsp;</td>
					<td class="list_body_left" width="30%">
						<input type="text" size="20" name="bmmc" class="text_12 text_size_250 text_readonly" 
							readonly value="<%=sysuserManager.getBmmc(user.getGlbm())%>">
					</td>
					<td class="list_headrig">人员类型&nbsp;</td>
					<td class="list_body_left">
						<select name="sfmj" class="text_12 text_size_120" onchange="changesfmj(this.value);">
							<%=sysuserManager.getRylxOptionHtml(user.getSfmj())%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="list_headrig">用户姓名&nbsp;</td>
					<td class="list_body_left">
						<input type="text"  size="20" name="xm" class="text_12 text_size_120" 
						value="<%=user.getXm()%>"><input type=button name="btuserinfo" value="..." onclick="queryuserinfo();">
					</td>
					<td width="10%" class="list_headrig">用户名&nbsp;</td>
					<td width="20%" class="list_body_left"><input name="yhdh" type="text"  
						class="text_12 text_size_120 text_readonly" maxlength="32" value="<%=user.getYhdh()%>"
							<%if(modal.equals("edit")){ %> readonly <%} %>>
					</td>									
									
					<td class="list_headrig">身份证号&nbsp;
					</td>
					<td class="list_body_left"><input type="text"  readOnly name="sfzmhm" maxlength="18"
							class="text_12 text_size_120 text_readonly" value="<%=user.getSfzmhm()%>">
					</td>
				</tr>
				<tr>
					<td class="list_headrig">警号/协警号&nbsp;
					</td>
					<td class="list_body_left">
						<input type="text" readOnly name="rybh" maxlength="6" class="text_12 text_size_120 text_readonly" 
							value="<%=user.getRybh()%>">
					</td>					

					<td class="list_headrig">密码有效期&nbsp;
					</td>
					<td class="list_body_left"><input type="text" name="mmyxq" class="text_12 text_size_80" value="<%=user.getMmyxq()%>">
						<a href="#" onClick="riqi('mmyxq')" HIDEFOCUS><img
								src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
								width="34" height="22" border="0" align="absmiddle">
						</a>
					</td>
					<td class="list_headrig">账户效期&nbsp;
					</td>
					<td class="list_body_left"><input type="text" name="zhyxq"  class="text_12 text_size_80" value="<%=user.getZhyxq()%>">
						<a href="#" onClick="riqi('zhyxq')" HIDEFOCUS><img
								src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
								width="34" height="22" border="0" align="absmiddle">
						</a>
					</td>
				</tr>
				
				<tr>
			
					<td class="list_headrig">IP起始地址&nbsp;
					</td>
					<td class="list_body_left"><input name="ipqsdz1" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getIpks(),"1",1)%>">.<input type="text" style="width: 28" name="ipqsdz2" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpks(),"1",2)%>">.<input type="text" style="width: 28" name="ipqsdz3" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpks(),"1",3)%>">.<input type="text" style="width: 28" name="ipqsdz4" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpks(),"1",4)%>">
					</td>
					<td class="list_headrig">
						IP结束地址&nbsp;
					</td>
					<td class="list_body_left"  colspan="3"><input type="text" style="width: 28" maxlength="3" name="ipjsdz1"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",1)%>">.<input type="text" style="width: 28" maxlength="3" name="ipjsdz2"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",2)%>">.<input type="text" style="width: 28" maxlength="3" name="ipjsdz3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",3)%>">.<input type="text" style="width: 28" maxlength="3" name="ipjsdz4"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",4)%>">
					</td>
				</tr>
				<tr>					
					<td class="list_headrig">固定IP地址1&nbsp;</td>
					<td class="list_body_left"><input name="gdipdz11" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getGdip(),"1",1)%>">.<input type="text" style="width: 28" name="gdipdz12" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip(),"1",2)%>">.<input type="text" style="width: 28" name="gdipdz13" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip(),"1",3)%>">.<input type="text" style="width: 28" name="gdipdz14" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip(),"1",4)%>">
					</td>	

					<td class="list_headrig">固定IP地址2&nbsp;</td>
					<td class="list_body_left"><input name="gdipdz21" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",1)%>">.<input type="text" style="width: 28" name="gdipdz22" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",2)%>">.<input type="text" style="width: 28" name="gdipdz23" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",3)%>">.<input type="text" style="width: 28" name="gdipdz24" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",4)%>">
					</td>
					
					<td class="list_headrig">固定IP地址3&nbsp;</td>
					<td class="list_body_left" colspan="3"><input name="gdipdz31" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",1)%>">.<input type="text" style="width: 28" name="gdipdz32" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",2)%>">.<input type="text" style="width: 28" name="gdipdz33" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",3)%>">.<input type="text" style="width: 28" name="gdipdz34" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",4)%>">
					</td>

				</tr>
				<tr>
					<td class="list_headrig">车管检验点&nbsp;</td>
					<td class="list_body_left" width="20%">
						<input type="text" size="20" maxlength=2 name="jyd" value="<%=user.getJyd()%>" class="text_12 text_size_120" maxlength=16>
					</td>
					<td class="list_headrig">状态&nbsp;</td>
					<td class="list_body_left">
						<input type="text" size="20" readOnly name="ztmc" class="text_12 text_size_120 text_readonly" value="<%=sysuserManager.getZtmc(user.getZt())%>">
					</td>
					<td class="list_headrig">最近登录时间&nbsp;</td>
					<td class="list_body_left">
						<input type="text" size="20" readOnly name="zjdlsj" class="text_12 text_size_120 text_readonly" value="<%=DateUtil.formatDateTime(user.getZjdlsj())%>">
					</td>
				</tr>		
				<tr>
					<td class="list_headrig">用户类型&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='科技' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='车管' readonly>	
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='驾管' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='违法' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='事故' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='剧毒品' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='其他' readonly>
					</td>
				</tr>	
								
				<tr>
					<td class="list_headrig">限制登录方式&nbsp;</td>
					<td class="list_body_left">
						<input type='checkbox' name='bz' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:60;' value='二代证登录' readonly>
						<input type='checkbox' name='bz' value='2' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:60;' value='PKi登录' readonly>
					</td>	
					<td class="list_headrig">MAC地址&nbsp;</td>
					<td class="list_body_left">
						<input type="text" size=2 maxlength=2 name="mac1" value="<%=sysuserManager.getIpdz(user.getMac(),"3",1)%>" 
							class="text_12" style="text-transform:uppercase;">-
						<input type="text" size=2 maxlength=2 name="mac2" value="<%=sysuserManager.getIpdz(user.getMac(),"3",2)%>" 
							class="text_12" style="text-transform:uppercase;">-
						<input type="text" size=2 maxlength=2 name="mac3" value="<%=sysuserManager.getIpdz(user.getMac(),"3",3)%>" 
							class="text_12" style="text-transform:uppercase;">-
						<input type="text" size=2 maxlength=2 name="mac4" value="<%=sysuserManager.getIpdz(user.getMac(),"3",4)%>" 
							class="text_12" style="text-transform:uppercase;">-
						<input type="text" size=2 maxlength=2 name="mac5" value="<%=sysuserManager.getIpdz(user.getMac(),"3",5)%>" 
							class="text_12" style="text-transform:uppercase;">-
						<input type="text" size=2 maxlength=2 name="mac6" value="<%=sysuserManager.getIpdz(user.getMac(),"3",6)%>" 
							class="text_12" style="text-transform:uppercase;">
					</td>	
					<td class="list_headrig">签名图片&nbsp;</td>
					<td class="list_body_left">
						<img name="tpxs" src="<%=photoSrc%>" border="0" alt="签名图片" width="25" height="20">
					</td>	
				</tr>
				<tr>
					<td class="list_headrig">说明&nbsp;</td>
					<td class="list_body_left" colspan="5"><font color="red">【固定IP地址1、固定IP地址2、固定IP地址3】为用户访问综合应用平台的客户端IP地址</font></td>
			</table>
			
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table"  align="center">
				<tr>
					<td height="30" align="right" class="list_body_out">
					    <input style="cursor: hand" class="button" name="btadd" value=" 清空  " type="button" onClick="resetform();">&nbsp;
						<input style="cursor: hand" class="button" name="btupload" value=" 签名图片维护  " type="button" onClick="uploadqmtp();">&nbsp;
						<input style="cursor: hand" class="button" name="btreset" value="重置密码" type="button" onClick="setupPassword();">&nbsp;
						<input style="cursor: hand" class="button" name="btlock" value="启用" type="button" onClick="reuse();">&nbsp;
						<input style="cursor: hand" class="button" name="btstop" value="停用" type="button" onClick="restop();">&nbsp;
						<input style="cursor: hand" class="button" name="btfail" value="登录失败信息"	type="button" onClick="getLoginFail('<%=user.getGlbm() %>','<%=user.getYhdh() %>');">&nbsp;
						<input style="cursor: hand" class="button" name="btsave" value=" 保 存 " type="button" onClick="saveuser();">&nbsp;
						<input style="cursor: hand" class="button" name="btdel" value=" 删 除 "	type="button" onClick="del();">&nbsp;
						<input style="cursor: hand" class="button" type="button" value=" 退 出 " onclick="quit()">
					</td>
				</tr>
			</table>				
		</a></h2></div>
		
		<div><h2><a name="vio_klsjxx" id="vio_klsjxx" style="display:none">
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<tr>
					<td width="12%" class="list_headrig">系统管理员&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input name="xtgly1" id="xtgly11" type="radio" value="1" onclick="checkxtgly();">
	                    <label for="xtgly11">是</label>
	                    <input name="xtgly1" id="xtgly21" type="radio" value="2"  onclick="checkxtgly();" checked>					
	                   	<label for="xtgly21">否</label>
	                </td>
                 </tr>
                 <tr  id="divkgywyhlx">
					<td class="list_headrig">可管理用户类型&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='科技' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='车管' readonly>	
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='驾管' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='违法' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='事故' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='剧毒品' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='其他' readonly>
					</td>
				</tr>
				<tr id="ksqx">				
					<td class="list_headrig">可管理权限&nbsp;</td>
					<td colspan="4" class="list_body_left">
						<div id="divrolesyy"></div>
					</td>
					<td class="list_body_left" width="10%">
						<input name="btyyyhz" type='button' onclick="queryselectedrolemenu(formedit.yyyhz);" class='button' 
							style='width:100' value='查询选中角色权限' readonly>	
					</td>
				</tr>

				<tr>
					<td class="list_headrig">权限模式&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input onclick="checkQxms()" type="radio" name="oqxms" id="oqxms1" value="1">角色
						<input onclick="checkQxms()" type="radio" name="oqxms" id="oqxms2" value="2">自由
					</td>				
				</tr>
				<tr>					
					<td class="list_headrig">操作权限&nbsp;</td>
					<td colspan="4" valign="top" class="list_body_left">
						<div id="divroles"></div>
						<div id="divcxdhs"></div>
					</td>
					<td class="list_body_left" width="10%">
						<input name="btyhz" type='button' onclick="queryselectedrolemenu(formedit.yhz);" class='button' 
							style='width:100' value='查询选中角色权限' readonly></td>
				</tr>
				</table>		
		
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table"  align="center">
				<tr>
					<td height="30" align="left" class="list_body_out"><%=xtglyzxjbmsg%></td>
				</tr>
				<tr>
					<td height="30" align="right" class="list_body_out">
					<input style="cursor: hand" class="button" name="btsaverole" value=" 保 存 授 权 " type="button" onClick="saveuserrole();">&nbsp;
					</td>
				</tr>
			</table>		

		</a></h2></div>		
	</form>		

	
	<script language="JavaScript" type="text/javascript">vio_down()</script>
	</body>
	<iframe name="paramIframe" style="DISPLAY: none" height="300"
		width="500"></iframe>
<%=JspSuport.getInstance().outputCalendar()%>
</html>

