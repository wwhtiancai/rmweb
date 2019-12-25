<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.share.frm.bean.SysGroup"%>
<%@page import="com.tmri.share.frm.bean.SysPara"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>${cxmc}</title>
</head>
<%
	List sysparaList = (List)request.getAttribute("querylist");
%>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function init(){

}

//显示信息
function editone(xtlb,gjz) {
	openwin("<c:url value="/syspara.frm?method=editsyspara"/>&xtlb=" + xtlb + "&gjz=" + gjz,"basadmin",false);
}

function reload(){
	window.location.reload();
}
</script>
<body onLoad="init()" onUnload="closesubwin();">
<script language="JavaScript" type="text/javascript">vio_title('系统参数管理')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<div id="div_show"></div>
<%
SysGroup sysGroup = null;SysPara syspara = null;
if(sysparaList.size()>1){ %>
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
	<%}else{%>
	
	<div id="tab_<%=i+1%>" style="display:none">	
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
							<td width="40%">参数名称</td>
							<td width="30%">参数值</td>
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
					<tr class="<%=str_class%>" style="cursor:hand" onDblClick="editone('<%=syspara.getXtlb()%>','<%=syspara.getGjz()%>')">	
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
						<td class="list_body_tr_2" rowspan="<%=len%>"><%=fzmc%></td>
<%}%>	  									
						<td ><%=k+1%></td>
						<td align="left"><%=syspara.getGjz()%></td>
						<td  align="left" ><%=syspara.getCsmc()%></td>
						<td align="left" ><%=syspara.getMrz()%></td>
					</tr>			
			<%}%>
			</table>
	  </div>
<%} %>

<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
<tr>

                  <td colspan="2" class="list_body_out" align="right">
                  <input name="exit" type="button" class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()"></td>
                </tr>
</table>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>
