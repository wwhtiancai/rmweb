<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%
	String str_tilte = "接口申请登记";
%>
<html>
	<head>
		<title><%=str_tilte%></title>
		<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
  function init()
  {
     document.all["jkqsrq"].value=getTodayN(0);
     document.all["jkjzrq"].value="2020-01-01";
     <c:if test="${wsAppForm!=null}">       
        document.all["xtlb"].value="<c:out value='${wsAppForm.xtlb}'/>";
        document.all["azdm"].value="<c:out value='${wsAppForm.azdm}'/>";
        document.all["kfwjk"].value="<c:out value='${wsAppForm.kfwjk}'/>";
        document.all["scbj"].value="<c:out value='${wsAppForm.scbj}'/>";
        tmp="<c:out value='${wsAppForm.ksip}'/>";
        tmparr=tmp.split(".");
        if(tmparr.length==4)
        {
            document.all["ipqsdz1"].value=tmparr[0];
		    document.all["ipqsdz2"].value=tmparr[1];
	        document.all["ipqsdz3"].value=tmparr[2];
	        document.all["ipqsdz4"].value=tmparr[3];
        }
        tmp="<c:out value='${wsAppForm.jsip}'/>";
        tmparr=tmp.split(".");
        if(tmparr.length==4)
        {
            document.all["ipjsdz1"].value=tmparr[0];
		    document.all["ipjsdz2"].value=tmparr[1];
	        document.all["ipjsdz3"].value=tmparr[2];
	        document.all["ipjsdz4"].value=tmparr[3];
        }
        var divContents="<c:out value='${divContents}'/>"
        getWsContents(divContents);
        document.all["querybutton"].disabled=false;    
        document.all["savebutton"].disabled=false;     
        document.all["printbutton"].disabled=false; 
        document.all["delbutton"].disabled=false; 
        var scbj="<c:out value='${wsAppForm.scbj}'/>";
        if(scbj=="1")
        {
           document.all["uploadbutton"].disabled=true; 
        }
        else
        {
           document.all["uploadbutton"].disabled=false; 
        }
        document.all["dyrjmc"].disabled=true;
        document.all["dyrjkfdw"].focus();  
	  </c:if>
	  <c:if test="${wsAppForm==null}">
	     document.all["querybutton"].disabled=false;
	     document.all["savebutton"].disabled=true;     
         document.all["printbutton"].disabled=true; 
         document.all["uploadbutton"].disabled=true;         
         document.all["dyrjmc"].focus();
	  </c:if>
		var i = 0;
		checkfields[i++] = new CheckObj("dyrjmc","系统名称",FRM_CHECK_NULL,0,1);
		checkfields[i++] = new CheckObj("dyrjkfdw","开发单位名称",FRM_CHECK_NULL,0,1);
		checkfields[i++] = new CheckObj("dyzdw","使用单位名称",FRM_CHECK_NULL,0,1);

		checkfields[i++] = new CheckObj("ipqsdz1","IP起始地址",FRM_CHECK_IPDZ,0,1);		
		checkfields[i++] = new CheckObj("ipqsdz2","IP起始地址",FRM_CHECK_IPDZ,0,1);
		checkfields[i++] = new CheckObj("ipqsdz3","IP起始地址",FRM_CHECK_IPDZ,0,1);
		checkfields[i++] = new CheckObj("ipqsdz4","IP起始地址",FRM_CHECK_IPDZ,0,1);
		
		checkfields[i++] = new CheckObj("ipjsdz1","IP截止地址",FRM_CHECK_IPDZ,0,1);		
		checkfields[i++] = new CheckObj("ipjsdz2","IP截止地址",FRM_CHECK_IPDZ,0,1);
		checkfields[i++] = new CheckObj("ipjsdz3","IP截止地址",FRM_CHECK_IPDZ,0,1);
		checkfields[i++] = new CheckObj("ipjsdz4","IP截止地址",FRM_CHECK_IPDZ,0,1);	
		
		checkfields[i++] = new CheckObj("jkqsrq","接口启用日期",FRM_CHECK_NULL,0,1);
		checkfields[i++] = new CheckObj("jkqsrq","接口启用日期",FRM_CHECK_DATE,0,1);
		checkfields[i++] = new CheckObj("bz","系统功能简述",FRM_CHECK_NULL,0,0);		
		checkfields[i++] = new CheckObj("kfwjk","使用接口列表不能为空",FRM_CHECK_NULL,0,0);	
	    checklen = i;	
	    	    	    	   
    }
    function delForm()
    {
       if(confirm("是否确信删除该记录？"))
       {
          document.all["dyrjmc"].disabled=false;
          document.formedit.action="<c:url value="/ws.frm?method=delWsAppForm"/>";
          document.formedit.submit();
          document.all["dyrjmc"].disabled=true;
          return;
       }
    }
    
    //保存表单信息
    function saveForm()
    {
        var strModal;
        strModal="<c:out value='${modal}'/>";
        document.all["dyrjmc"].disabled=false;
	    document.all["ksip"].value=document.all["ipqsdz1"].value + "." + document.all["ipqsdz2"].value +"." + document.all["ipqsdz3"].value +"." + document.all["ipqsdz4"].value;
	    document.all["jsip"].value=document.all["ipjsdz1"].value + "." + document.all["ipjsdz2"].value +"." + document.all["ipjsdz3"].value +"." + document.all["ipjsdz4"].value;
		if(checkallfields(checkfields,1)==0){
	    		return 0;
		}		
	    document.formedit.action="<c:url value='/ws.frm?method=saveWsAppForm'/>&modal="+strModal;
	    document.formedit.submit();
	    document.all["dyrjmc"].disabled=true;
	    return; 
	 }
	 function uploadForm()
	 {
	    document.all["dyrjmc"].disabled=false;
	    document.formedit.action="<c:url value='/ws.frm?method=uploadWsAppForm'/>";
	    document.formedit.submit();
	    document.all["dyrjmc"].disabled=true;
	    return;
	 }
	 //返回结果
	 function resultDel(strResult,strMessage)
	 {
	    if(strResult=="1") 
	    {
	       displayInfoHtml(strMessage);
	       parent.showAppFormList();
        }
        else
        {
           displayInfoHtml(strMessage);
        }
     }
     //返回结果
     function resultSave(strResult,strMessage)
     {
        if(strResult=="1") 
        {
           document.all["uploadbutton"].disabled=false; 
           document.all["printbutton"].disabled=false; 
	       displayInfoHtml(strMessage);
           parent.showAppFormList();
        }
        else
        {
           displayInfoHtml(strMessage);
        }
      }
      
      function printForm()
      {
        var azdm=document.all["azdm"].value;
        var dyrjmc=document.all["dyrjmc"].value;
        var windowheight=screen.height;
    	var windowwidth =screen.width;
    	windowheight=(windowheight-600)/2;
    	windowwidth=(windowwidth-800)/2;
    	var spath="<c:url value="/ws.frm?method=printWsAppFrom"/>&azdm="+azdm+"&dyrjmc="+dyrjmc;
    	spath=encodeURI(encodeURI(spath));
    	window.open(spath,"basadmin","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=600,top="+windowheight+",left="+windowwidth);          
      }
</script>	
  </head>
<body onload="init()">
	<form name="formedit" action="" method="post" target="paramIframe">
	    <input type="hidden" name="xtlb" value="<c:out value='${xtlb}'/>"/>
	    <input type="hidden" name="azdm" value="<c:out value='${azdm}'/>"/>
		<input type="hidden" name="ksip">
		<input type="hidden" name="jsip">
	    <input type="hidden" name="kfwjk" />
	    <input type="hidden" name="scbj" />
		<%=JspSuport.getInstance().getWebKey(session)%>	    
	     <table border="0" cellspacing="1" cellpadding="0" class="list_table">	     
				<tr>
					<td align="right" class="list_head" width="20%">
						系统名称&nbsp;
					</td>
					<td class="list_body_out">
					    <input type="text" class="text_12"  onfocus="movelast();"  name="dyrjmc" maxlength="128" style="width:300" value="<c:out value='${wsAppForm.dyrjmc}'/>">						
					</td>
				</tr>
				<tr>
					<td align="right" class="list_head">
						开发单位名称&nbsp;
					</td>
					<td class="list_body_out">
					    <input type="text" class="text_12"  onfocus="movelast();"  name="dyrjkfdw" maxlength="128" style="width:300" value="<c:out value='${wsAppForm.dyrjkfdw}'/>">						
					</td>
				</tr>				
				<tr>
					<td align="right" class="list_head">
						使用单位名称&nbsp;
					</td>
					<td class="list_body_out">
					    <input type="text" class="text_12"  onfocus="movelast();"  name="dyzdw" maxlength="128" style="width:300" value="<c:out value='${wsAppForm.dyzdw}'/>">
					</td>
				</tr>
				<tr>
					<td align="right" class="list_head">
						使用单位发证机关&nbsp;
					</td>
					<td class="list_body_out">
					    <select name="dyfzjg" class="text_12" style="width:150" >
							<c:out value="${dyfzjgOptions}" escapeXml="false" />
		   				</select>	
					</td>
				</tr>				
				<tr>
					<td align="right" class="list_head">
						IP地址范围&nbsp;
					</td>
					<td class="list_body_out">
					   <input type="text" style="width:28" name="ipqsdz1" maxlength="3" class="text_12"> .
					   <input type="text" style="width:28" name="ipqsdz2" maxlength="3" class="text_12"> .
					   <input type="text" style="width:28" name="ipqsdz3" maxlength="3" class="text_12"> .
					   <input type="text" style="width:28" name="ipqsdz4" maxlength="3"class="text_12">
					   ~
					   <input type="text" style="width:28" name="ipjsdz1" maxlength="3" class="text_12"> .
					   <input type="text" style="width:28" name="ipjsdz2" maxlength="3" class="text_12"> .
					   <input type="text" style="width:28" name="ipjsdz3" maxlength="3" class="text_12"> .
					   <input type="text" style="width:28" name="ipjsdz4" maxlength="3"class="text_12">
					</td>
				</tr>
				<tr>
					<td align="right" class="list_head">
						接口起止日期&nbsp;
					</td>
					<td class="list_body_out">
						<input name="jkqsrq" onfocus="movelast();"  type="text" class="text_12" style="width:90" value="<c:out value='${wsAppForm.jkqsrq}'/>">
						<img name="popcal" align="absmiddle" src="frmjs/cal/calbtn.gif" width="34" height="22" border="0" onClick="riqi('jkqsrq')" style="cursor:hand">
						--						 
						<input name="jkjzrq" onfocus="movelast();"  type="text" class="text_12" style="width:90" value="<c:out value='${wsAppForm.jkjzrq}'/>">
						<img name="popcal" align="absmiddle" src="frmjs/cal/calbtn.gif" width="34" height="22" border="0" onClick="riqi('jkjzrq')" style="cursor:hand">
					    
					</td>
				</tr>
				
				<tr>
					<td align="right" class="list_head">
						系统功能简述&nbsp;
					</td>
					<td class="list_body_out">
					    <textarea  rows="10" cols="65" class="text_12"  onfocus="movelast();"  name="bz"><c:out value='${wsAppForm.bz}'/></textarea>						
					</td>
				</tr>
	
				<tr>
				   <td height="20" align="right" class="list_head">使用接口列表&nbsp;</td>
				   <td colspan="" align="left" valign="top" class="list_body_out">
				   <span id="divWsContents" style="overflow-y:auto;height:273;width:100%"></span> 
				   </td>
	            </tr>				            
				<tr>
					<td colspan="2" class="list_body_out" align="right">
						<input type="button" name="querybutton" class="button" onclick="showWsListMain()" value="访问接口维护">
						<input type="button" name="savebutton" class="button" onclick="saveForm()" value=" 保 存 ">
						<input type="button" name="printbutton" class="button" onclick="printForm()" value=" 申请表 ">
						<input type="button" name="uploadbutton" class="button" onclick="uploadForm()" value=" 信息上传">
						<c:if test="${wsAppForm!=null}">
							<input type="button" name="delbutton" class="button" onclick="delForm()" value=" 删 除 ">
						</c:if>
						<input name="exitbutton" type="button" class="button" style="cursor:hand;"  value=" 退 出 " onclick="parent.window.close()">&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</body>
	<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
	<%=JspSuport.getInstance().outputCalendar() %>
</html>
<script language="javascript" type="text/javascript">
   function showWsListMain()
   {
       var spath="ws.frm?method=showWsListMain";
       var a_result=window.showModalDialog(spath,document.all["kfwjk"].value,'dialogwidth:500px;dialogheight:400px;center:yes;resizable:yes'); 
       if(a_result!=null)	
       {
           var resultStr=a_result[0];
           var kfwjk=a_result[1];
           document.all["kfwjk"].value=kfwjk;
           var jkid;
           var jkmc;
           var fwcs;
           getWsContents(resultStr);
       }  
   }
   function getWsContents(resultStr)
   {
      document.all["savebutton"].disabled=false; 
      var a = resultStr.split("$");
      var str="<table height='100%' border='0' align='center' cellpadding='0' cellspacing='1'  id='SelectTable' valign='top' class='detail_table' style='width:100%'>";
      str+="<tr align='center' class='detail_head'>";
      str+="<td width='10%'>序号</td>";
   	  str+="<td width='20%'>接口ID</td>";
   	  str+="<td width='40%'>接口名称</td>";
   	  str+="<td >日均访问量(次/天)</td>";
   	  str+="</tr>";   
      for(var i=0;i<a.length;i++)
      {
         var b=a[i].split('#');
         jkid=b[0];
         jkmc=b[1];
         fwcs=b[2];
         str+="<tr class='list_body_tr_2' ";
       	 str+="style='cursor:hand'/>"; 
       	 str+="<td align='center' height='15'>"+(i+1)+"</td>"; 
         str+="<td align='center'>"+jkid+"</td>";
       	 str+="<td align='center'>"+jkmc+"</td>";
       	 str+="<td align='center'>"+fwcs+"</td>";
       	 str+="</tr>";               
       }
       str+="</table>";         
	   divWsContents.innerHTML=str;     
	}
</script>
