<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.pub.service.SysService"%>
<% 
String cdbh = (String)request.getAttribute("cdbh");
SysService sysService = (SysService)request.getAttribute("sysService");
GSystemService gSystemService = (GSystemService)request.getAttribute("gSystemService");

String cdmc = gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
Depcode depcode = (Depcode)request.getAttribute("depcode");
Code depcodetype = (Code)request.getAttribute("depcodetype");

String title = (String)request.getAttribute("title");
String modal = (String)request.getAttribute("modal");
%>
<html>
<head>
<title><%=cdmc%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<link rel='STYLESHEET' type='text/css' href='codebase/style.css'>
<link rel="STYLESHEET" type="text/css" href="codebase/dhtmlxtree.css">
<script language="JavaScript" src="codebase/dhtmlxcommon.js" type="text/javascript"></script>
<script language="JavaScript" src="codebase/dhtmlxtree.js" type="text/javascript"></script>
<script language="JavaScript">
<%=sysService.procJspLogicVar("00",cdbh,"1")%>
var tree;
function init(){
	<%=sysService.procJspLogicCode("00",cdbh,"1")%>
	setradio(formmain.ozt,'<%=depcode.getZt()%>');
	<%if(modal.equals("add")){%>
		setadd();
	<%}else{%>
		setedit();
	<%}%>

	//部门车驾管代码只能查看
	<%if(depcodetype.getBz().equals("1")){%>
		formmain.btdel.disabled=false;
	<%}else if(depcodetype.getBz().equals("2")){%>
		seteditsyglbm();
	<%}%>
	tonload();
}


function seteditsyglbm(){
	formmain.btreset.disabled=true;
	formmain.btdel.disabled=true;
	settextreadonly(formmain.dmsm1,true);
	//settextreadonly(formmain.dmsm2,true);
	//settextreadonly(formmain.dmsm3,true);
	//settextreadonly(formmain.dmsm4,true);
}

function save(){
	var zt=getradio(formmain.ozt);
	formmain.zt.value=zt;
	var syglbm=tree.getAllChecked();
	formmain.syglbm.value=syglbm;

	
	var dmz=formmain.dmz1.value+formmain.dmz2.value;
	formmain.dmz.value=dmz;
	if(check()){
	  	document.formmain.action="depcode.frm?method=savedepcode";
	  	document.formmain.target="paramIframe";
	  	document.formmain.submit();
	}
}

function del(){
	if(confirm("确认删除吗？")){
		if(check()){
		  	document.formmain.action="depcode.frm?method=deldepcode";
		  	document.formmain.target="paramIframe";
		  	document.formmain.submit();
		}
	}
}

function check(){
	<%=sysService.procJspCheckCode("00",cdbh,"1","")%>
	var syglbm=getckvalue(formmain.syglbm1,",");
	/*
	if(syglbm==''){
		displayInfoHtml("[00R9542J1]"+":使用管理部门不能为空！");
		return false;
	}*/	
	//
	<%if(modal.equals("add")){%>
	var dmz2=formmain.dmz2.value;
	if(dmz2.length!=6){
		displayInfoHtml("[00R9542J1]"+":代码值必须为6位数！");
		return false;
	}		
	<%}%>
	return true;
}

//返回结果
function resultsubmit(strResult,strVal,strMessage) {
	if(strResult=="1"){
		displayInfoHtml(strMessage);
		setedit();
		formmain.modal.value="edit";
	  	window.opener.query();
	} else if(strResult=="2") {
		displayInfoHtml(strMessage);
		setadd();
		formmain.modal.value="new";
		window.opener.query();
	}else {
		displayInfoHtml(strMessage);
	}
}

function setadd(){
	document.formmain.btsave.disabled=false;
	<%if(depcodetype.getBz().equals("1")){%>
		formmain.btdel.disabled=false;
	<%}else if(depcodetype.getBz().equals("2")){%>
		seteditsyglbm();
	<%}%>
	document.formmain.modal.value="add";
}

function setedit(){
	document.formmain.btsave.disabled=false;
	<%if(depcodetype.getBz().equals("1")){%>
		formmain.btdel.disabled=false;
	<%}else if(depcodetype.getBz().equals("2")){%>
		seteditsyglbm();
	<%}%>
	settextreadonly(document.formmain.dmz2,true);
	document.formmain.modal.value="edit";
}

//再次新增
function resetadd(){
	formmain.dmz2.value="";
	settextreadonly(document.formmain.dmz2,false);
	formmain.dmsm1.value="";
	formmain.dmsm2.value="";
	formmain.dmsm3.value="";
	formmain.dmsm4.value="";
	setradio(formmain.ozt,"1");
	clearck(formmain.syglbm1);
	setadd();
}


function selectall(obj){
	if(obj.value=="选择全部"){
		tree.setSubChecked("0", true);
		obj.value="取消选择";
	}else if(obj.value=="取消选择"){
		tree.setSubChecked("0", false);
		obj.value="选择全部";
	}	
}

function tonload(){
	tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
	tree.setImagePath("codebase/imgs/csh_bluebooks/");
	tree.enableCheckBoxes(1);
	//注意使用管理部门的#分割，替换
	tree.loadXML("depcode.frm?method=queryXzqhTreeList&dmlb=<%=depcode.getDmlb()%>&syglbm=<%=depcode.getSyglbm()%>&cjglbm=<%=depcode.getCjglbm()%>");
}

</script>

<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<body onload="init();">
<script language="JavaScript" type="text/javascript">vio_title('<%=title%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<div class="s1" style="height: 3px;"></div>
<form name="formmain" method="post" action="">
<input type="hidden" name="cdbh" value="<%=cdbh%>"/>
<input type="hidden" name="dmlb" value="<%=depcode.getDmlb()%>"/>
<input type="hidden" name="zt" value=""/>
<input type="hidden" name="cjglbm" value="<%=depcode.getCjglbm()%>"/>
<input type="hidden" name="glbm" value="<%=depcode.getGlbm()%>"/>
<input type="hidden" name="syglbm" value="<%=depcode.getSyglbm()%>"/>
<input type="hidden" name="modal" value="<%=modal%>"/>
<input type="hidden" name="dmz" value="<%=depcode.getDmz()%>"/>
<%=JspSuport.getInstance().getWebKey(session)%>		
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="list_table">
  <tr>
    <td width="10%" class="list_head" align="right">代码值&nbsp;</td>
    <td width="20%" class="list_body_out">
    	<%if (modal.equals("add")){%>
    	<input name="dmz1" type="text" maxlength="8" class="text_12 text_readonly" size="6" readOnly value="<%=depcode.getDmz().substring(0,6)%>">
    	<input name="dmz2" type="text" maxlength="6" class="text_12" size="8" value="<%=depcode.getDmz().substring(6)%>"/>
    	<%}else{%>
		<input name="dmz1" type="hidden" maxlength="8" class="text_12 text_readonly" size="6" readOnly value="">
    	<input name="dmz2" type="text" maxlength="20" class="text_12 text_readonly" readOnly size="20" value="<%=depcode.getDmz()%>"/>    	
    	<%}%>
    </td>
    <td width="10%" class="list_head" align="right">名称&nbsp;</td>
    <td width="30%" class="list_body_out">
    	<input type="text" maxlength="256" class="text_12 text_size_250" name="dmsm1" value="<%=depcode.getDmsm1()%>"/>
    </td>    
    <td width="11%" class="list_head" align="right">联系人&nbsp;</td>
    <td width="27%" class="list_body_out">
    	<input type="text" maxlength="256" class="text_12 text_size_120" name="dmsm2" value="<%=depcode.getDmsm2()%>"/>
    </td>     
  </tr>  
  <tr>
    <td class="list_head" align="right">联系电话&nbsp;</td>
    <td class="list_body_out">
    	<input type="text" maxlength="256" class="text_12 text_size_120" name="dmsm3" value="<%=depcode.getDmsm3()%>"/>
    </td>
    <td class="list_head" align="right">地址&nbsp;</td>
    <td class="list_body_out">
    	<input type="text" maxlength="256" class="text_12 text_size_250" name="dmsm4" value="<%=depcode.getDmsm4()%>"/>
    </td>
    <td class="list_head" align="right">状态&nbsp;</td>
    <td class="list_body_out">
			正常<input checked type="radio" name="ozt" value="1">
			停用<input type="radio" name="ozt" value="2">  
    </td>
  </tr>   
  <tr>
    <td class="list_head" align="right">使用管理部门&nbsp;</td>
    <td class="list_body_out" colspan="5">
    	<div id="treeboxbox_tree" style="width:360; height:300;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"/>
    </td>
  </tr>   
 
  <tr>    
    <td class="list_body_out" colspan="6" align="right">
      <input name="btselect" type="button" class="button"  value="选择全部"  onclick="selectall(this);">
      <input name="btreset" type="button" class="button"  value="清空"  onclick="resetadd();">
      <input name="btsave" type="button" class="button"  value="保存"  onclick="save();">
      <input name="btdel" type="button" class="button"  value="删除"  onclick="del();">
      <input name="btclose" type="button" class="button" value="退出"  onclick="window.close();">
    </td>     
  </tr>    
  
</table>
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="5"></td>
	</tr>
</table>
		
<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>


</body>
</html>
