<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%
	String bodystr=(String)request.getAttribute("bodystr");
	
%>
<html>
	<head>
		<title>可访问接口选择</title>
	</head>
    <%=JspSuport.getInstance().JS_ALL%>	
	<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
	<link href="theme/style/style.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="frmjs/checkdata.js" type="text/javascript"></script>	
	<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
	<script language="javascript" src="frmjs/ajax_func.js" type="text/javascript"></script>
	<script language="JavaScript">
     //获得父窗口传递来的值 
     var kfwjk=window.dialogArguments; 
     var a = kfwjk.split("$");
	 function init()
	 {
		var oTable=document.getElementById("SelectTable");
	    arrTr=oTable.getElementsByTagName("tr");
	    var i,j;
	    var arrCell;
	    var jkid;
	    var icheckObj;
	    for(i=1;i<arrTr.length;i++)
	    {
             arrCell=arrTr[i].cells;
             if(arrCell.length>1)
             {
             	jkid=arrCell[2].firstChild.nodeValue;
             	var icheckObj=arrCell[1].firstChild;
             	if(kfwjk.indexOf(jkid)!="-1")
             	{
                	icheckObj.checked=true;
                	for(j=0;j<a.length;j++)
                	{
                	    var b=a[j].split("#");
                	    if(b[0]==jkid)
                	    {
                	       arrCell[4].firstChild.value=b[1];
                	    }
                	}
             	}
             }             
        }		   	    
	 }
	 function query_cmd()
	 {
	     var xtlb=document.all["xtlb"].value;
         var spath="<c:url value='/ws.frm?method=queryWsContentList'/>"+"&ywlb="+xtlb;
      	 send_request(spath,"getWsContentsList()",false); 
	 }
	 function sync_cmd()
	 {   
         var spath="<c:url value='/ws.frm?method=queryWsContentList'/>"+"&sync=1";
      	 send_request(spath,"getWsContentsList()",false); 
  	 }
  	 function getWsContentsList()
  	 {
    	var xmlDoc = _xmlHttpRequestObj.responseXML;
        var head=xmlDoc.getElementsByTagName("head");
   	    var m_code=head[0].getElementsByTagName("code")[0].firstChild.nodeValue;
   	    var m_des=head[0].getElementsByTagName("message")[0].firstChild.nodeValue;   
   	    var m_sync=head[0].getElementsByTagName("sync")[0].firstChild.nodeValue; 	
 
   		var oContent=document.getElementById("resultsBody");

        if(m_code==null||m_code=="null")
        {
            m_code="0";
        }
   		if(m_code=="1")
   		{
   		    var items=xmlDoc.getElementsByTagName("item");
   			clearBodyById(oContent);
   			var str1="";
   			var str2="";
   			var m=0;
   			var n=0;
   			var str="<table border='0' cellspacing='1' cellpadding='0' class='list_table' id='SelectTable'>";
   			str+="<tr align='center' class='list_head'>";
   			str+="<td width='10%' align='center'>序号</td>";
   			str+="<td width='10%' align='center'>选项</td>";
   			str+="<td width='10%' align='center'>接口ID</td>";
   			str+="<td width='40%' align='center'>接口名称</td>";
   			str+="<td align='center'>日均访问量（次/天）</td>";
   			str+="</tr>";
    		for (var i = 0; i < items.length; i++) 
    		{
        		var item = items[i];
        		var jkid = item.getElementsByTagName("jkid")[0].firstChild.nodeValue;
        		var jkmc = item.getElementsByTagName("jkmc")[0].firstChild.nodeValue;
        		var jklb = item.getElementsByTagName("jklb")[0].firstChild.nodeValue;
        		if(jklb=="1"||jklb=="3")
        		{
        		    m=m+1;
        			str1+="<tr class='list_body_out'>";
       				str1+="<td align='center'>"+(m)+"</td>"; 
       				str1+="<td align='center'><input type='checkbox' name='yhz' id='yhz' value='"+jkid+"'>"+" </td>"; 
       				str1+="<td align='center'>"+jkid+"</td>";
       				str1+="<td align='center'>"+jkmc+"</td>";
       				str1+="<td align='center'><input type='text' name='yhz' id='yhz' style='width:80' maxlength='8' value='10000'></td>";
       				str1+="</tr>"; 
       			}
       			else if(jklb=="2"||jklb=="4")
       			{
       			    n=n+1;
        			str2+="<tr class='list_body_out'>";
       				str2+="<td align='center'>"+(n)+"</td>"; 
       				str2+="<td align='center'><input type='checkbox' name='yhz' id='yhz' value='"+jkid+"'>"+" </td>"; 
       				str2+="<td align='center'>"+jkid+"</td>";
       				str2+="<td align='center'>"+jkmc+"</td>";
       				str2+="<td align='center'><input type='text' name='yhz' id='yhz' style='width:80' maxlength='8' value='10000'></td>";
       				str2+="</tr>";        			
       			}     	       	
      		}
      		str+="<tr class='list_body_out'><td colspan='5' align='left' height='20'>--查询接口--</td></tr>";
       		str+=str1;
       		str+="<tr class='list_body_out'><td colspan='5' align='left' height='20'>--写操作接口--</td></tr>";
       		str+=str2;   
      		str+="</table>";
      		oContent.innerHTML+=str;
      		if(m_sync=="1")
      		{	
      			displayInfoHtml("[00R9810J3]:数据同步成功！"); 
      		} 
      		//var msg="数据同步成功！";
      		//displayInfoHtml_new("00","R981",MSG_RANK_OK,"3",msg);   
      	}
      	else
      	{
      	    displayInfoHtml("[00R9813J4]:"+m_des);
      	    //displayInfoHtml_new("00","R981",MSG_RANK_LE,"4",m_des);
      	}  
  	 }
	function saveForm()
	{
		var oTable=document.getElementById("SelectTable");
	    arrTr=oTable.getElementsByTagName("tr");
	    var i,k;
	    var arrCell;
	    var resultStr="";
	    var kfwjk="";
	    var arrCell;
	    var xh;
	    var jkid;
	    var jkmc;
	    var icheck;
	    var fwcs;
	    k=0;
	    for(i=1;i<arrTr.length;i++)
	    {
             arrCell=arrTr[i].cells;
             if(arrCell.length>1)
             {
             	xh=arrCell[0].firstChild.nodeValue;
             	var icheckObj=arrCell[1].firstChild;
             	if(icheckObj.checked==true)
             	{
                	jkid=arrCell[2].firstChild.nodeValue;
             		jkmc=arrCell[3].firstChild.nodeValue;
                	fwcs=arrCell[4].firstChild.value;        
                	if(fwcs==null||fwcs=="")
                	{
                   		//displayInfoHtml("[00R9812J1]：【日均访问量】不可为空！");
                   		var msg="【日均访问量】不可为空！";
                   		displayInfoHtml_new("00","R981",MSG_RANK_IE,"1",msg);
                   		return false;
                	}      
                	if(_checknum(fwcs)!="1")
                	{
                    	var msg="【日均访问量】必须为数字！";
                    	displayInfoHtml_new("00","R981",MSG_RANK_IE,"2",msg);
                    	return false;
                	} 
                	resultStr=resultStr+jkid+"#"+jkmc+"#"+fwcs+"$";   
                	kfwjk=kfwjk+jkid+"#"+fwcs+"$";    
               	 	k++;            
             	}
            }
        }
        if(k==0)
        {
            //displayInfoHtml("[00R9812J2]：可访问的接口列表不能为空！");
            var msg="可访问的接口列表不能为空！";
            displayInfoHtml_new("00","R981",MSG_RANK_IE,"3",msg);
            return;
        }
        if(k>15)
        {
            var msg="一次申请的接口不能大于15！";
            displayInfoHtml_new("00","R981",MSG_RANK_IE,"4",msg);
            return;           
        }
        resultStr=resultStr.substr(0,resultStr.length-1);
        kfwjk=kfwjk.substr(0,kfwjk.length-1);
        var a_result=new Array(2);
        a_result[0]=resultStr;
		a_result[1]=kfwjk;  
  	    parent.window.returnValue = a_result;
    	parent.window.close();	  		      
	}
	</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">	
<body <%=bodystr%> onload="init();">
<script language="JavaScript" type="text/javascript">ts_title("<c:out value='${gnmc}' />")</script>
    <form name="formain" method="post" action="">
    <table border="0" cellspacing="1" cellpadding="0" class="list_table">
    	<tr>
			<td width="60" class="list_head">
				&nbsp;业务种类&nbsp;
			</td>
			<td width="120" class="list_head">
				<select name="xtlb" style="width: 100" onchange="query_cmd()">
				    <option>--全部--</option>
					<c:forEach items="${ywlbList}" var="current1">
						<option value="<c:out value='${current1.dmz}'/>">
							<c:out value="${current1.dmsm1}" />
						</option>
					</c:forEach>
				</select>
			</td>
			<td align="right" class="list_head">
			   <input type="button" name="queryBtn" style="cursor: hand;" class="button" onclick="sync_cmd()" value=" 接口信息同步 ">
			   <input type="button" value=" 确 定 " onclick="saveForm()" style="cursor: hand;" class="button">
			</td>
    	</tr>
		<tr>
			<td colspan="3" valign="top">
   		          <span class='contents' id="resultsBody" valign='top'>
   	     		  <table border="0" cellspacing="1" cellpadding="0" class="list_table" id="SelectTable">
   		 			<tr align='center' class='list_head'>
   		 			  <td width='10%' align='center'>序号</td>
   		 			  <td width='10%' align='center'>选项</td>
   		              <td width='10%' align='center'>接口ID</td>
   		              <td width='40%' align='center'>接口名称</td>
   		              <td align='center'>日均访问量（次/天）</td>
   		            </tr>
   		            <c:out value="${inputStr}" escapeXml="false" />	   		            
   		          </table>
   		          </span>
			</td>
		</tr>    	
    </table>
    </form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>