<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.share.frm.bean.SysGroup"%>
<%@page import="com.tmri.share.frm.bean.SysPara"%>
<%@page import="com.tmri.share.frm.bean.Department"%>
<%@page import="com.tmri.share.frm.util.StringUtil"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>${cxmc}</title>
</head>
<%
	List sysparaList = (List)request.getAttribute("querylist");
	Department dep = (Department)request.getAttribute("department");
%>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function init(){

}

//显示信息
function editone(xtlb,glbm,gjz) {
	openwin("<c:url value="/syspara.frm?method=editdeppara"/>&glbm=" + glbm + "&xtlb=" + xtlb + "&gjz=" + gjz,"basadmin",false);
}

function reload(){
	window.location.reload();
}
</script>

<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="javascript" src='theme/style/tab.js'></script>
<body onLoad="init()" onUnload="closesubwin();">
<table width="98%" border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
	<Tr>
		<td class="list_headrig" width="15%">管理部门代码&nbsp;</td>
		<td class="list_body_left" width="35%"><%=dep.getGlbm() %></td>
		<td class="list_headrig"  width="15%">管理部门名称&nbsp;</td>
		<td class="list_body_left" width="35%"><%=dep.getBmmc() %>
		</td>
	</Tr>
</table>
<div class="s1" style="height: 3px;"></div>
<div id="div_show"></div>

<%
SysGroup sysGroup = null;
SysPara syspara = null;
if(sysparaList.size()>=1){ %>
		<%for(int i=0;i<sysparaList.size();i++){
			sysGroup = (SysGroup)sysparaList.get(i);%>
		<span id="div_<%=i+1%>" style='display:none'><a href="##" onClick="show_Form('tab_<%=i+1%>_<%=sysparaList.size() %>');" class="text_12"><%=sysGroup.getMc() %></a></span>
	
		<%}%>
<%} %>	
<script>call_back("<%=sysparaList.size() %>");</script>
<%
for(int i=0;i<sysparaList.size();i++){
	sysGroup = (SysGroup)sysparaList.get(i);
	List list1 = sysGroup.getList();
	if(i==0){%>
	<div id="tab_<%=i+1%>" style="display:block">	
	<span style="height:520; overflow: scroll; width: 100%; overflow-x: hidden;">
	<%}else{%>	
	<div id="tab_<%=i+1%>" style="display:none">	
	<span style="height:520; overflow: scroll; width: 100%; overflow-x: hidden;vertical-align:top; ">
	<%}%>
			<table width="98%" border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
							<col/>
							<col/>
							<col/>
							<col/>
							<col/>
					<thead>
						<tr align="center" class="list_head">
							<td width="14%">分类</td>
							<td width="6%">序号</td>
							<td width="10%">关键值</td>
							<td width="30%">参数名称</td>
							<td width="34%">参数值</td>
							<td width="20%">是否<br>继承</td>
						</tr>
				  </thead>
			<%		
			int fzindex=-1;
			for(int k=0;k<list1.size();k++){
				syspara = (SysPara)list1.get(k);
				String str_class = "list_body_tr_1";
        				if (k % 2 == 0) {
          				str_class = "list_body_tr_2";
        				}%>
					<tr class="<%=str_class%>"  style="cursor:hand" onDblClick="editone('<%=syspara.getXtlb()%>','<%=dep.getGlbm() %>','<%=syspara.getGjz()%>')"">				  									
					<% 
					String fzmc = syspara.getFzmc();
					if(fzmc==null) fzmc="";
					if(k>fzindex){
					    int len=0;
						for(int l=k;l<list1.size();l++){
							SysPara syspara1 = (SysPara)list1.get(l);
							String fzmc1 = syspara1.getFzmc();
							if(fzmc1==null) fzmc1="";
							if(fzmc1.equals(fzmc)){
								len++;
								fzindex++;
							}else{
								break;
							}
						}
					%>
						<td  class="list_body_tr_2" rowspan="<%=len%>"><%=fzmc%></td>
					<%}%>
						<td ><%=k+1%></td>
						<td ><%=syspara.getGjz()%></td>
						<td  align="left" ><%=syspara.getCsmc()%></td>
						<td title='<%=syspara.getMrz()%>' align="left" ><%=StringUtil.displayMax(syspara.getMrz(),15)%></td>
						<td  align="left" ><%=syspara.getCssx().equals("1")?"":"是"%></td>
					</tr>			
			<%}%>
			</table>
		</span>
	  </div>
<%} %>



</table>

</body>
</html>
