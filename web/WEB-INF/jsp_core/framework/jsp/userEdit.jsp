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
	<title>�û�ά��</title>
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
	//ȡ��ǰ���ſ���Ȩҵ��Χ
	//String yhssyw = dept.getKclyw();
	//��ȡ��ǰ�û��Ŀɹ����û�����
	//String kgywyhlx=sysService.getSessionUserInfo(session).getSysuser().getKgywyhlx();
	//if(kgywyhlx.equals("")){
	//	kgywyhlx="1000000000";
	//}
	
	//�Լ����ܸ��Լ���Ȩ
	//if(sysService.getSessionUserInfo(session).getSysuser().getYhdh().equals(user.getYhdh())){
	//	sqqx="0";
	//}
%>	
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<script language="javascript">

//��������
function setupPassword(){
	if(confirm("�Ƿ�ȷ��Ҫ�����û�����������Ϊ��ʼ������(888888)��")){
		document.formedit.action="sysuser.frm?method=saveSetuppassword";
		document.formedit.submit();
	}
}
//����
function reuse(){
	if(confirm("�Ƿ�ȷ��Ҫ���ø��û���")){
		document.formedit.action="sysuser.frm?method=unlock";
		document.formedit.submit();
	}
}
//ͣ��
function restop(){
	if(confirm("�Ƿ�ȷ��Ҫͣ�ø��û���")){
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
	
	
	//��ϵͳ����Ա
	setradio(formedit.xtgly1,'<%=user.getXtgly()%>');
	var xtglyzxjb=formedit.xtglyzxjb.value;
	if(xtglyzxjb==2){
		document.getElementById("xtgly11").disabled=true;
	}
	

	//���ݵ�ǰ�û����ã���������û�����
	setckywlxabled(formedit.kgywyhlx1,'<%=kgywyhlx%>');
	setckywlxabled(formedit.yhssyw1,'<%=yhssyw%>');
	
	
	//�����ɽ�ɫ����
	var xtgly=getradio(formedit.xtgly1);
	if(xtgly==2){
		document.getElementById("ksqx").style.display="none";
		document.getElementById("divkgywyhlx").style.display="none";
		//
	}else{
		document.getElementById("ksqx").style.display="";
		//ϵͳ����Ա���ÿɹ���ҵ���û�����
		setckyhlx(formedit.kgywyhlx1,'<%=user.getKgywyhlx()%>');
	}		

	//���û�����ҵ��
	setckyhlx(formedit.kgywyhlx1,'<%=user.getKgywyhlx()%>');
	setckyhlx(formedit.yhssyw1,'<%=user.getYhssyw()%>');
	
	//�жϸ��û�״̬��������ʾ����Ҫ����
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

	//�ü����
	<%--
	<%if(ywlx.charAt(3)=='1'&&!ywlx.substring(0,10).equals("1111111111")){%>
	<%}else{%>
		settextreadonly(formedit.jyd,true);
	<%}%>
    --%>

	//��Ȩ��ģʽ��ȱʡ��ɫ
	setradio(formedit.oqxms,'<%=user.getQxms()%>');
	formedit.qxms.value='<%=user.getQxms()%>';
	checkQxms();

	//��ɫģʽ�Ƿ�ɸ�Ϊ����Ȩ��
	var xgxqms=formedit.xgxqms.value;
	if(xgxqms=='1'){
		//����ģʽ����ѡ��
		document.getElementById("oqxms2").disabled=true;
	}
		
	
	<%if(!user.getYhdh().equals("")){%>
	    setedit();
	<%}else{%>
		setadd();
		document.all["mmyxq"].value="<%=yxq%>";
		document.all["zhyxq"].value="<%=yxq%>";
	<%}%>

	//�ù���Ȩ��
	var glqx=formedit.glqx.value;
	if(glqx=='0'){
		setview();
	}	
	//��ȨȨ��
	var sqqx=formedit.sqqx.value;
	if(sqqx=='0'){
		formedit.btsaverole.disabled=true;
	}	
	//��ǿ�ƶ���֤��¼
	setck(formedit.bz,'<%=user.getBz()%>');
	
	
    //20110802atm��Ϣ��ʼ��,�༭״̬
    var sfmj=formedit.sfmj.value;
	if(sfmj=='4'){
		formedit.btuserinfo.disabled=true;
		settextreadonly(formedit.yhdh,false);
	}else{
		formedit.btuserinfo.disabled=false;
		settextreadonly(formedit.yhdh,true);
	}
	//��ȡ�����͹���Ȩ��
	getuserglqxandczqx();
	
	var i = 0;
	checkfields[i++] = new CheckObj("yhdh","�û���",FRM_CHECK_NULL,0,1);
	//checkfields[i++] = new CheckObj("yhdh","�û���",FRM_CHECK_SPECIAL_CHAR,0,1);
	//checkfields[i++] = new CheckObj("xm","�û�����",FRM_CHECK_NULL,0,1);
	//checkfields[i++] = new CheckObj("sfzmhm","���֤������",FRM_CHECK_NULL,0,1);		
	//checkfields[i++] = new CheckObj("sfzmhm","���֤������",FRM_CHECK_SFZMHM,0,1);
	checkfields[i++] = new CheckObj("mmyxq","������Ч��",FRM_CHECK_NULL,0,1);
	checkfields[i++] = new CheckObj("mmyxq","������Ч��",FRM_CHECK_DATE,0,1);
	checkfields[i++] = new CheckObj("zhyxq","�˻���Ч��",FRM_CHECK_NULL,0,1);
	checkfields[i++] = new CheckObj("zhyxq","�˻���Ч��",FRM_CHECK_DATE,0,1);	
	checkfields[i++] = new CheckObj("ipqsdz1","IP��ʼ��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipqsdz2","IP��ʼ��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipqsdz3","IP��ʼ��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipqsdz4","IP��ʼ��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz1","IP������ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz2","IP������ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz3","IP������ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("ipjsdz4","IP������ַ",FRM_CHECK_IPDZ,0,1);	

	checkfields[i++] = new CheckObj("gdipdz11","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz12","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz13","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz14","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);	
	<%--
	checkfields[i++] = new CheckObj("gdipdz21","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz22","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz23","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);
	checkfields[i++] = new CheckObj("gdipdz24","�̶�IP��ַ",FRM_CHECK_IPDZ,0,1);		
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
	if(confirm("�Ƿ�ȷ��ɾ����ǰ�û���")){
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
		formedit.ztmc.value="����";
		formedit.btfail.disabled=true;
	}	
}

//�����û�������Ϣ
function saveuser() {
	if(checkallfields(checkfields,1)==0) {
		return 0;
	}
	//
	
	//���̶�ip2��ַ
	var gdipdz21=formedit.gdipdz21.value;
	var gdipdz22=formedit.gdipdz22.value;
	var gdipdz23=formedit.gdipdz23.value;
	var gdipdz24=formedit.gdipdz24.value;
	
	if(gdipdz21!=""||gdipdz22!=""||gdipdz23!=""||gdipdz24!=""){
		if(checkIp(formedit.gdipdz21,"�̶�IP��ַ2",true)!="1"){
			formedit.gdipdz21.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz22,"�̶�IP��ַ2",true)!="1"){
			formedit.gdipdz22.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz23,"�̶�IP��ַ2",true)!="1"){
			formedit.gdipdz23.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz24,"�̶�IP��ַ2",true)!="1"){
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
		if(checkIp(formedit.gdipdz31,"�̶�IP��ַ3",true)!="1"){
			formedit.gdipdz31.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz32,"�̶�IP��ַ3",true)!="1"){
			formedit.gdipdz32.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz33,"�̶�IP��ַ3",true)!="1"){
			formedit.gdipdz33.value="";
			return false;
		}
		if(checkIp(formedit.gdipdz34,"�̶�IP��ַ3",true)!="1"){
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
		if(checknull(document.all["rybh"],"����",true)!="1")return 0;
		if(checknum(document.all["rybh"],"����",true)!="1")return 0;
		if(checklength(document.all["rybh"],6,"����",true)!="1")return 0;
	}

	var  yhssyw=getckbitvalue(formedit.yhssyw1);
	formedit.yhssyw.value=yhssyw;

	//���ܼ����
	//�ü����,��Ϊ������Ա����ҵ������
	//��atm
	if(formedit.sfmj.value!='4') {
		if(yhssyw.substr(1,1)=='1'){
			if(checknull(formedit.jyd,"���ܼ����",true)!="1")return 0;
		}else{
			formedit.jyd.value="";
		}
	}
	//���жϲ���Ϊ��
	//...
	if(formedit.yhssyw.value=="0000000000"){
		displayInfoHtml("[00R9942J4]:�û�����ҵ����Ϊ�գ�");	
		return false;
	}
	
	//�ϲ�
	var mac=document.all["mac1"].value+"-"+document.all["mac2"].value
		+"-"+document.all["mac3"].value+"-"+document.all["mac4"].value
		+"-"+document.all["mac5"].value+"-"+document.all["mac6"].value;
	document.all["mac"].value=mac.toUpperCase();
	//�ж�mac��ַ��Ϣ��
	if(mac!="-----"){
		var reg_name=/[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}/; 
		if(!reg_name.test(document.all["mac"].value)){ 
			displayInfoHtml("[00R9942J6]:MAC��ַ��ʽ����ȷ��"); 
			return false; 
		} 
	}
	
	//����atm�豸
	if(formedit.sfmj.value=='4') {
		var ipks=document.all["ipks"].value;
		var ipjs=document.all["ipjs"].value;
		if(ipks!=ipjs){
			displayInfoHtml("[00R9942J5]:ATM�û���ip��ʼ��ip������ַһ�£�");	
			return false;
		}
	}
	
	document.formedit.action="sysuser.frm?method=saveUser";
	document.formedit.submit();	
}


//�����û�Ȩ����Ϣ
function saveuserrole() {
	//�����û������ȱ����û���Ϣ
	var modal=formedit.modal.value;
	if(modal=='new'){
		displayInfoHtml("���ȱ����û���Ϣ���ٽ�����Ȩ��");
		return false;
	}	
	
	
	var strTmp;
	document.all["qxms"].value=getradio(document.all["oqxms"]);
	formedit.xtgly.value=getradio(formedit.xtgly1);	

	//��ȡ����ȨȨ��
	//��Ϊϵͳ����Ա�ͷ�ϵͳ����Ա
	if(getradio(formedit.xtgly1)==1){
		var grantroles=getckallvalue(formedit.yyyhz,"#");
		formedit.grantroles.value=grantroles;	
		//��ȡ�û����
		var  kgywyhlx=getckbitvalue(formedit.kgywyhlx1);
		formedit.kgywyhlx.value=kgywyhlx;
		//���жϲ���Ϊ��
		//...		
		if(formedit.kgywyhlx.value=="0000000000"){
			displayInfoHtml("[00R9942J3]:����ҵ���û����Ͳ���Ϊ�գ�");
			return false;
		}	
	}else{
		formedit.grantroles.value=""; 
	}		
		
	
	var cddhs="";
	if(document.all["oqxms"].item(0).checked) {
		var cddhs=getckallvalue(formedit.yhz,"#");
		document.all["roles"].value=cddhs;
		/*����ȡ����Ȩ
		if(checknull(document.all["roles"],"��ɫ",false)!="1"){
			return false;
		}*/	
	}else {
		if(document.all.cddh)
		{
			if(document.all.cddh.type=="checkbox")
			{
				//����
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
		/*����ȡ����Ȩ
		if(checknull(document.all["cxdh"],"Ȩ��",false)!="1"){
			return false;
		}*/	
	}
	document.formedit.action="sysuser.frm?method=saveUserrole";
	document.formedit.submit();	
}


//���ؽ��
function resultsubmit(strResult,strVal,strMessage) {
	if(strResult=="1"){
		displayInfoHtml(strMessage);
		setedit();
		formedit.modal.value="edit";
	  	window.opener.query();
	  	//�����û�����ʾ������Ȩά��
	  	var modal=formedit.modal.value;
	  	if(modal=='new'){
		  	formedit.modal.value="edit";
		  	displayInfoHtml("�û������󣬿ɽ�����Ȩ����");
		  	//20110809���¼�����Ȩ��ɫ
		  	getuserglqxandczqx();
	  	}
	  	
	} else if(strResult=="2") {
		displayInfoHtml(strMessage);
		setadd();
		formedit.modal.value="new";
		window.opener.query();
	} else if(strResult=="4") {
        //���� 
		displayInfoHtml(strMessage);
		formedit.zt.value="1";
		formedit.ztmc.value="����";
		formedit.btlock.disabled=true;
		formedit.btstop.disabled=false;
	} else if(strResult=="5") {
        //����
		displayInfoHtml(strMessage);
		formedit.zt.value="1";
		formedit.ztmc.value="����";
		formedit.btlock.disabled=true;
		formedit.btstop.disabled=false;		
	} else if(strResult=="6") {
        //ͣ��
		displayInfoHtml(strMessage);
		formedit.zt.value="3";
		formedit.ztmc.value="ͣ��";
		formedit.btlock.disabled=false;
		formedit.btstop.disabled=true;			
	}else {
		displayInfoHtml(strMessage);
	}
}


//��ȡrole��Ӧ�˵�
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
	//�����Ͻ����
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
	//�����Ͻ����
	var results = _xmlHttpRequestObj.responseText;
	var valarray = results.split("AAAAA");
	//�ָ�results,�ٴ���
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

//ѡ��ϵͳ����Ա��ֻ�ܽ�ɫ��Ȩ
function checkxtgly(){
	var xtgly=getradio(formedit.xtgly1);
	if(xtgly==1){
		setckenabled(formedit.yyyhz);
		setckywlxabled(formedit.kgywyhlx1,'<%=kgywyhlx%>');
		document.getElementById("ksqx").style.display="";	
		document.getElementById("divkgywyhlx").style.display="";
	}else if(xtgly==2){
		//���ý�ɫѡ��
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
		//���ò˵�Ŀ¼
		getusermenu(2);
	}
}

function uploadqmtp(){
	openwin("","uploadphoto",true);
    document.formedit.action="sysuser.frm?method=uploadphoto";
    document.formedit.target="uploadphoto";
    document.formedit.submit();  	
}
//ˢ��ǩ��ͼƬ
function refreshseal(){
	document.formedit.tpxs.src="<%=photoSrc%>&rand=<%=sysuserManager.getRand()%>";
}

//��ȡ��ɫȨ���б�
//����
function queryrolemenu(jsdh){
	if(jsdh==''){
		displayInfoHtml("[00R9942J1]:��ɫδѡ��");
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

//���񾯺�Э�����ȡ���ţ����������û������þ������
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
	//���ţ����������û������þ������
	if(parseInt(results)==1){
		displayInfoHtml("[00R9940J2]:δ�ҵ���/Э��/������Ա��Ϣ,���Ȳɼ�������Ϣ��");
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

//ѡ����Ȩ��ɫ
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

//����ѡ��Ľ�ɫ������ҳ��
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
//20110802�����Ƿ��񾯱仯
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
		<script language="JavaScript" type="text/javascript">vio_title('�û�ά��-�༭')</script>
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
  <input name="btyh" id="btyh" type="button" class="tab_user_1" value="�û���Ϣ" style='cursor:hand;height:28' onclick="show_Form('vio_qzxx');user_button_color('btyh');">
  <input name="btsq" id="btsq" type="button" class="tab_user_2" value="��Ȩ��Ϣ" style='cursor:hand;height:28' onclick="show_Form('vio_klsjxx');user_button_color('btsq');">
</td>
  </tr>
</table>
		<div><h2><a name="vio_qzxx" id="vio_qzxx" style="display:block">
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<tr>
					<td width="12%" class="list_headrig">���Ŵ���&nbsp;</td>
					<td class="list_body_left" width="20%">
						<input type="text" size="20" name="glbm" class="text_12 text_size_120 text_readonly"  
							readonly value="<%=user.getGlbm()%>">
					</td>
					<td width="10%" class="list_headrig">��������&nbsp;</td>
					<td class="list_body_left" width="30%">
						<input type="text" size="20" name="bmmc" class="text_12 text_size_250 text_readonly" 
							readonly value="<%=sysuserManager.getBmmc(user.getGlbm())%>">
					</td>
					<td class="list_headrig">��Ա����&nbsp;</td>
					<td class="list_body_left">
						<select name="sfmj" class="text_12 text_size_120" onchange="changesfmj(this.value);">
							<%=sysuserManager.getRylxOptionHtml(user.getSfmj())%>
						</select>
					</td>
				</tr>
				<tr>
					<td class="list_headrig">�û�����&nbsp;</td>
					<td class="list_body_left">
						<input type="text"  size="20" name="xm" class="text_12 text_size_120" 
						value="<%=user.getXm()%>"><input type=button name="btuserinfo" value="..." onclick="queryuserinfo();">
					</td>
					<td width="10%" class="list_headrig">�û���&nbsp;</td>
					<td width="20%" class="list_body_left"><input name="yhdh" type="text"  
						class="text_12 text_size_120 text_readonly" maxlength="32" value="<%=user.getYhdh()%>"
							<%if(modal.equals("edit")){ %> readonly <%} %>>
					</td>									
									
					<td class="list_headrig">���֤��&nbsp;
					</td>
					<td class="list_body_left"><input type="text"  readOnly name="sfzmhm" maxlength="18"
							class="text_12 text_size_120 text_readonly" value="<%=user.getSfzmhm()%>">
					</td>
				</tr>
				<tr>
					<td class="list_headrig">����/Э����&nbsp;
					</td>
					<td class="list_body_left">
						<input type="text" readOnly name="rybh" maxlength="6" class="text_12 text_size_120 text_readonly" 
							value="<%=user.getRybh()%>">
					</td>					

					<td class="list_headrig">������Ч��&nbsp;
					</td>
					<td class="list_body_left"><input type="text" name="mmyxq" class="text_12 text_size_80" value="<%=user.getMmyxq()%>">
						<a href="#" onClick="riqi('mmyxq')" HIDEFOCUS><img
								src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
								width="34" height="22" border="0" align="absmiddle">
						</a>
					</td>
					<td class="list_headrig">�˻�Ч��&nbsp;
					</td>
					<td class="list_body_left"><input type="text" name="zhyxq"  class="text_12 text_size_80" value="<%=user.getZhyxq()%>">
						<a href="#" onClick="riqi('zhyxq')" HIDEFOCUS><img
								src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
								width="34" height="22" border="0" align="absmiddle">
						</a>
					</td>
				</tr>
				
				<tr>
			
					<td class="list_headrig">IP��ʼ��ַ&nbsp;
					</td>
					<td class="list_body_left"><input name="ipqsdz1" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getIpks(),"1",1)%>">.<input type="text" style="width: 28" name="ipqsdz2" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpks(),"1",2)%>">.<input type="text" style="width: 28" name="ipqsdz3" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpks(),"1",3)%>">.<input type="text" style="width: 28" name="ipqsdz4" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpks(),"1",4)%>">
					</td>
					<td class="list_headrig">
						IP������ַ&nbsp;
					</td>
					<td class="list_body_left"  colspan="3"><input type="text" style="width: 28" maxlength="3" name="ipjsdz1"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",1)%>">.<input type="text" style="width: 28" maxlength="3" name="ipjsdz2"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",2)%>">.<input type="text" style="width: 28" maxlength="3" name="ipjsdz3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",3)%>">.<input type="text" style="width: 28" maxlength="3" name="ipjsdz4"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getIpjs(),"2",4)%>">
					</td>
				</tr>
				<tr>					
					<td class="list_headrig">�̶�IP��ַ1&nbsp;</td>
					<td class="list_body_left"><input name="gdipdz11" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getGdip(),"1",1)%>">.<input type="text" style="width: 28" name="gdipdz12" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip(),"1",2)%>">.<input type="text" style="width: 28" name="gdipdz13" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip(),"1",3)%>">.<input type="text" style="width: 28" name="gdipdz14" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip(),"1",4)%>">
					</td>	

					<td class="list_headrig">�̶�IP��ַ2&nbsp;</td>
					<td class="list_body_left"><input name="gdipdz21" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",1)%>">.<input type="text" style="width: 28" name="gdipdz22" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",2)%>">.<input type="text" style="width: 28" name="gdipdz23" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",3)%>">.<input type="text" style="width: 28" name="gdipdz24" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip1(),"1",4)%>">
					</td>
					
					<td class="list_headrig">�̶�IP��ַ3&nbsp;</td>
					<td class="list_body_left" colspan="3"><input name="gdipdz31" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",1)%>">.<input type="text" style="width: 28" name="gdipdz32" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",2)%>">.<input type="text" style="width: 28" name="gdipdz33" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",3)%>">.<input type="text" style="width: 28" name="gdipdz34" maxlength="3"
							class="text_12" value="<%=sysuserManager.getIpdz(user.getGdip2(),"1",4)%>">
					</td>

				</tr>
				<tr>
					<td class="list_headrig">���ܼ����&nbsp;</td>
					<td class="list_body_left" width="20%">
						<input type="text" size="20" maxlength=2 name="jyd" value="<%=user.getJyd()%>" class="text_12 text_size_120" maxlength=16>
					</td>
					<td class="list_headrig">״̬&nbsp;</td>
					<td class="list_body_left">
						<input type="text" size="20" readOnly name="ztmc" class="text_12 text_size_120 text_readonly" value="<%=sysuserManager.getZtmc(user.getZt())%>">
					</td>
					<td class="list_headrig">�����¼ʱ��&nbsp;</td>
					<td class="list_body_left">
						<input type="text" size="20" readOnly name="zjdlsj" class="text_12 text_size_120 text_readonly" value="<%=DateUtil.formatDateTime(user.getZjdlsj())%>">
					</td>
				</tr>		
				<tr>
					<td class="list_headrig">�û�����&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�Ƽ�' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='����' readonly>	
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�ݹ�' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='Υ��' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�¹�' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�綾Ʒ' readonly>
						<input type='checkbox' name='yhssyw1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='����' readonly>
					</td>
				</tr>	
								
				<tr>
					<td class="list_headrig">���Ƶ�¼��ʽ&nbsp;</td>
					<td class="list_body_left">
						<input type='checkbox' name='bz' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:60;' value='����֤��¼' readonly>
						<input type='checkbox' name='bz' value='2' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:60;' value='PKi��¼' readonly>
					</td>	
					<td class="list_headrig">MAC��ַ&nbsp;</td>
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
					<td class="list_headrig">ǩ��ͼƬ&nbsp;</td>
					<td class="list_body_left">
						<img name="tpxs" src="<%=photoSrc%>" border="0" alt="ǩ��ͼƬ" width="25" height="20">
					</td>	
				</tr>
				<tr>
					<td class="list_headrig">˵��&nbsp;</td>
					<td class="list_body_left" colspan="5"><font color="red">���̶�IP��ַ1���̶�IP��ַ2���̶�IP��ַ3��Ϊ�û������ۺ�Ӧ��ƽ̨�Ŀͻ���IP��ַ</font></td>
			</table>
			
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table"  align="center">
				<tr>
					<td height="30" align="right" class="list_body_out">
					    <input style="cursor: hand" class="button" name="btadd" value=" ���  " type="button" onClick="resetform();">&nbsp;
						<input style="cursor: hand" class="button" name="btupload" value=" ǩ��ͼƬά��  " type="button" onClick="uploadqmtp();">&nbsp;
						<input style="cursor: hand" class="button" name="btreset" value="��������" type="button" onClick="setupPassword();">&nbsp;
						<input style="cursor: hand" class="button" name="btlock" value="����" type="button" onClick="reuse();">&nbsp;
						<input style="cursor: hand" class="button" name="btstop" value="ͣ��" type="button" onClick="restop();">&nbsp;
						<input style="cursor: hand" class="button" name="btfail" value="��¼ʧ����Ϣ"	type="button" onClick="getLoginFail('<%=user.getGlbm() %>','<%=user.getYhdh() %>');">&nbsp;
						<input style="cursor: hand" class="button" name="btsave" value=" �� �� " type="button" onClick="saveuser();">&nbsp;
						<input style="cursor: hand" class="button" name="btdel" value=" ɾ �� "	type="button" onClick="del();">&nbsp;
						<input style="cursor: hand" class="button" type="button" value=" �� �� " onclick="quit()">
					</td>
				</tr>
			</table>				
		</a></h2></div>
		
		<div><h2><a name="vio_klsjxx" id="vio_klsjxx" style="display:none">
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<tr>
					<td width="12%" class="list_headrig">ϵͳ����Ա&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input name="xtgly1" id="xtgly11" type="radio" value="1" onclick="checkxtgly();">
	                    <label for="xtgly11">��</label>
	                    <input name="xtgly1" id="xtgly21" type="radio" value="2"  onclick="checkxtgly();" checked>					
	                   	<label for="xtgly21">��</label>
	                </td>
                 </tr>
                 <tr  id="divkgywyhlx">
					<td class="list_headrig">�ɹ����û�����&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�Ƽ�' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='����' readonly>	
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�ݹ�' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='Υ��' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�¹�' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='�綾Ʒ' readonly>
						<input type='checkbox' name='kgywyhlx1' value='1' ><input type='text' class='text_nobord' 
							style='background-color: #EFEFFE;width:40;' value='����' readonly>
					</td>
				</tr>
				<tr id="ksqx">				
					<td class="list_headrig">�ɹ���Ȩ��&nbsp;</td>
					<td colspan="4" class="list_body_left">
						<div id="divrolesyy"></div>
					</td>
					<td class="list_body_left" width="10%">
						<input name="btyyyhz" type='button' onclick="queryselectedrolemenu(formedit.yyyhz);" class='button' 
							style='width:100' value='��ѯѡ�н�ɫȨ��' readonly>	
					</td>
				</tr>

				<tr>
					<td class="list_headrig">Ȩ��ģʽ&nbsp;</td>
					<td colspan="5" class="list_body_left">
						<input onclick="checkQxms()" type="radio" name="oqxms" id="oqxms1" value="1">��ɫ
						<input onclick="checkQxms()" type="radio" name="oqxms" id="oqxms2" value="2">����
					</td>				
				</tr>
				<tr>					
					<td class="list_headrig">����Ȩ��&nbsp;</td>
					<td colspan="4" valign="top" class="list_body_left">
						<div id="divroles"></div>
						<div id="divcxdhs"></div>
					</td>
					<td class="list_body_left" width="10%">
						<input name="btyhz" type='button' onclick="queryselectedrolemenu(formedit.yhz);" class='button' 
							style='width:100' value='��ѯѡ�н�ɫȨ��' readonly></td>
				</tr>
				</table>		
		
			<table border="0" cellspacing="1" cellpadding="0" class="detail_table"  align="center">
				<tr>
					<td height="30" align="left" class="list_body_out"><%=xtglyzxjbmsg%></td>
				</tr>
				<tr>
					<td height="30" align="right" class="list_body_out">
					<input style="cursor: hand" class="button" name="btsaverole" value=" �� �� �� Ȩ " type="button" onClick="saveuserrole();">&nbsp;
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

