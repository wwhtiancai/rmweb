<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="java.util.List"%>
<%@page import="com.tmri.share.frm.service.GHtmlService"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%
  String str_tilte="";
  String str_czlx=request.getParameter("czlx");//操作类型 wh=维护 cx=查询
  String bhxt=(String)request.getAttribute("bhxt");
  GHtmlService sysservice = (GHtmlService)request.getAttribute("gHtmlService");
  GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)request.getAttribute("gSysparaCodeService");
  List<Codetype> queryList = (List<Codetype>)request.getAttribute("queryList");
  if(str_czlx.equals("wh"))
  {
    str_tilte="系统代码维护";
  }
  if(str_czlx.equals("cx"))
  {
    str_tilte="系统代码查询";
  }
  %>
<html>
<head>
<title><%=str_tilte%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function query_cmd(){             //本页刷新
document.formain.action="<c:url value="/code.frm?method=queryList"/>";
document.formain.submit();
}

function init(){
<c:if test="${codetype!=null}">
formain.dmlb.value="<c:out value='${codetype.dmlb}'/>";
formain.lbsm.value="<c:out value='${codetype.lbsm}'/>";
formain.dmlx.value="<c:out value='${codetype.dmlx}'/>";
formain.xtlb.value="<c:out value='${codetype.xtlb}'/>";
</c:if>
}
//显示信息
function showdetail(xtlb,dmlb) {
   ActionForm.dmlb.value=dmlb;
   ActionForm.xtlb.value=xtlb;
   winid = openwin("","bbb2",false);
   ActionForm.target="bbb2";
   ActionForm.action="code.frm?method=editOne&czlx=<c:out value='${czlx}'/>";
   ActionForm.submit();
   winid.focus();
}
//新增代码
function newcode(xtlb,dmlb,dmcd){
//
var dmcd;
var windowheight=screen.height;
var windowwidth =screen.width;
    windowheight=(windowheight-500)/2;
    windowwidth=(windowwidth-800)/2;
    window.open("<c:url value='/code.frm?method=newcode'/>&dmlb="+dmlb+"&xtlb="+xtlb+"&dmcd="+dmcd+"&czlx=<c:out value='${czlx}'/>","excmain","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=500,top="+windowheight+",left="+windowwidth);
}
</script>
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/tools.js" type="text/javascript"></script>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<div class="s1" style="height: 3px;"></div>
	<Form name="ActionForm" action="" method="post"> 
     <input type=hidden name="dmlb">
     <input type=hidden name="xtlb">
    </Form>	
<form name="formain" method="post"  action="">	
 <input type=hidden name='czlx' id='czlx' value='<c:out value='${czlx}'/>'>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
<tr>
<td width="8%" class="list_headrig">代码分类</td>
<td width="15%" class="list_body_out">
	<select name="xtlb" class="text_12 text_size_100" size="1">
					<option value="" selected></option>
					<option value="00">公共代码</option>
					<option value="31">传输管理</option>
					<option value="61">设备管理</option>
					<option value="62">数据接入</option>
					<option value="63">缉查布控</option>
                    <option value="90">RFID</option>
	 </select>
</td>

<td width="8%" class="list_headrig">代码类型</td>
<td width="15%" class="list_body_out"><select name="dmlx" class="text_12 text_size_100" size="1">
	<%=sysservice.transDmlbToOptionHtml("00","0010","",true,"2","") %>
	                </select></td>	                
<td width="8%" class="list_headrig">代码类别</td>
<td width="15%" class="list_body_out"><input type="text" name="dmlb" class="input_text" maxlength="6" class="text_size_all">
</td>
<td width="8%" class="list_headrig">类别说明</td>
<td class="list_body_out" width="15%"><input type="text" name="lbsm" class="input_text" maxlength="12" class="text_size_all"></td>
<td width="8%"  align="right" class="list_body_out"><input type="button" class="button" value=" 查 询 " style="cursor:hand" alt="" onClick="query_cmd()">&nbsp;<input name="exit" type=button class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()"></td>
</tr>
</table>
</form>
<div class="s1" style="height: 3px;"></div>
	<c:if test="${queryList!=null}">
	
<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<col/>
				<col/>
				<col/>
				<col/>
				<col/>
		<thead>
					 	<tr align="center" class="detail_head" height="20">
						<td width="100">代码类别</td>
						<td >类别说明</td>
						<td width="100">类别属性</td>
						<td width="100">代码类型</td>
						<td width="80">代码长度</td>
						<c:if test="${czlx=='wh'}">
						<td width="80">操作</td>
						</c:if>
				  </tr>
<%
	int i = 0;
	String str_class = "";
	if(queryList!=null){				  
        for(Codetype codetype:queryList){
        i++;
    	if(i%2==0){
    		str_class = "list_body_tr_1";
    	}else{
    		str_class = "list_body_tr_2";
    	}
        %>
		<tr class="<%=str_class%>"	height="23" onMouseOver="this.className='list_body_over'" onMouseOut="this.className='<%=str_class%>'" style="cursor:hand" onDblClick="showdetail('<%=codetype.getXtlb() %>','<%=codetype.getDmlb() %>')">
						<td height="20"><%=codetype.getDmlb() %></td>
					   <td align="left"><%=codetype.getLbsm() %></td>
					   <td align="center"><%if(codetype.getLbsx().equals("1")){%>可维护<% }else{ %>不可维护<%} %></td>
					   <Td align="left"><%=gSysparaCodeService.transCode("00","0010",codetype.getDmlx()) %></Td>
					   <td><%=codetype.getDmcd() %></td>
						<%if(str_czlx.equals("wh")){ %>
					   <td>
					   <%if(codetype.getLbsx().equals("1")){%>
					   	<input type="button" name="bjcode" onclick="newcode('<%=codetype.getXtlb() %>','<%=codetype.getDmlb() %>','<%=codetype.getDmcd() %>')" value="新增代码 " class="button"  style="width:80">
					   <%} %>
					   </td>
					   <%} %>
					</tr>        
        <%   
        }  				
    }  
 %>
 		<tr>
				<td colspan="7" align="right" class="page">
					<c:out value="${controller.clientScript}" escapeXml="false"/>
					<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
	    </tr>
  </thead>
</table>
</c:if>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>