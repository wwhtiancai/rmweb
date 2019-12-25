<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="java.util.List"%>
<html>
<%
String gnidstr = (String) request.getAttribute("gnid");
%>
<head>
<title>非工作日维护</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<script language="javascript">
function initCal(){
  year=workdayForm.year.value;
  month=workdayForm.month.value;
  if(parseInt(month)+1>12){
    daycount=getDay((parseInt(year)+1)+"-01-01 00:00",-1);
  }else if(parseInt(month)+1>=10){
    daycount=getDay(year+"-"+(parseInt(month)+1)+"-01 00:00",-1);
  }else{
    daycount=getDay(year+"-0"+(parseInt(month)+1)+"-01 00:00",-1);
  }
  daycount=daycount.substr(8,2);
  startday=new Date(year,parseInt(month)-1,'01');
  firstIndex=startday.getDay();
  var week_list = new Array(42);
  for(i=0;i<42;i++)
    week_list[i]="";
  for(i=0;i<parseInt(daycount);i++){
    week_list[firstIndex+i]=i+1;
  }
  oTable=document.getElementById("EditTable");
  while(oTable.rows.length>0){
    oTable.deleteRow(0);
  }
  for(j=0;j<6;j++){
    if(week_list[j*7]==""&&j!=0)
      break;
    oTr=oTable.insertRow(oTable.rows.length);
    for(i=j*7;i<(j+1)*7;i++){
      oTd = oTr.insertCell(i%7);
      oTd.height="40"
      oTd.ClassName='list_body_out'
      oTd.bgColor="#cccccc";
      oEmlement=document.createElement("div");
      if(week_list[i]!=""){
        oTd.ondblclick=function(){changeDay(this.firstChild.firstChild.value,this.bgColor);};
        oEmlement.innerHTML = "<input type='text' id='"+week_list[i]+"' class='text_nobord2' readonly value='"+week_list[i]+"' style='font-size:20pt;width:100%;background-color:transparent;cursor:hand'>";
      }
      else
        oEmlement.innerHTML = "&nbsp;";
      oTd.appendChild(oEmlement);
    }
  }
  if(month>=10){
    queryForm.yymm.value=year+month;

  }else{
    queryForm.yymm.value=year+"0"+month;
  }
  document.queryForm.action="<c:url value='/syspara.frm?method=getNoworkday'/>";
  document.queryForm.submit();
}

function changeDay(days,color){
  year=workdayForm.year.value;
  month=workdayForm.month.value;
  if(month>=10){
    workdayForm.day.value=year+month;
  }else{
    workdayForm.day.value=year+"0"+month;
  }
  if(days.length==1)
    workdayForm.day.value=workdayForm.day.value+"0"+days;
  else
    workdayForm.day.value=workdayForm.day.value+days;
  if(color=="#ff0000")
    workdayForm.bj.value="2";
  else
    workdayForm.bj.value="1";
  workdayForm.sdate.value = workdayForm.day.value.substring(0,4) + "-" + workdayForm.day.value.substring(4,6) + "-"  + workdayForm.day.value.substring(6,8);
  document.workdayForm.action="<c:url value='/syspara.frm?method=changeNoworkday'/>";
  document.workdayForm.submit();
}

function setMr(){
	document.workdayForm.action="<c:url value='/syspara.frm?method=setInitNoworkday'/>";
  	document.workdayForm.submit();
}

function preMonth(){;
  if(workdayForm.month.selectedIndex==0){
    if(workdayForm.year.selectedIndex==0){

    }else{
      workdayForm.year.selectedIndex=workdayForm.year.selectedIndex - 1;
      workdayForm.month.value="12";
      initCal();
    }
  }else{
    workdayForm.month.selectedIndex=workdayForm.month.selectedIndex - 1;
    initCal();
  }
}

function nextMonth(){
  if(workdayForm.month.selectedIndex==11){
    if(workdayForm.year.selectedIndex==2){

    }else{
      workdayForm.year.selectedIndex=workdayForm.year.selectedIndex + 1;
      workdayForm.month.value="1";
      initCal();
    }
  }else{
    workdayForm.month.selectedIndex=workdayForm.month.selectedIndex + 1;
    initCal();
  }
}

function changeYwlb(ywlb){
  queryForm.ywlb.value=ywlb;
  workdayForm.ywlb.value=ywlb;
  initCal();
}

function resultSave(strResult,strMessage)
{
    displayInfoHtml(strMessage);
}
</script>
</head>
<%
int month= new Integer((String)request.getAttribute("mm")).intValue();
int year= new Integer((String)request.getAttribute("yyyy")).intValue();
%>
	<link href="theme/style/style.css" rel="stylesheet" type="text/css">
	<body onUnload="closesubwin();">
		<script language="JavaScript" type="text/javascript">vio_title('非工作日维护')</script>
		<script language="JavaScript" type="text/javascript">vio_seach()</script>
		<span class="s1" style="height: 3px;"></span>

<table width="100%" height="400" border="0" cellspacing="0" cellpadding="0" align="center">
<form name="workdayForm" action="" method="POST" target="paramIframe">
<input type="hidden" name="day" value="">
<input type="hidden" name="bj" value="">
<input type="hidden" name="sdate" value="">
<input type="hidden" name="ywlb" value="">
<%=JspSuport.getInstance().getWebKey(session)%>
  <tr>
    <td align="center" valign="top">
      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="text_12" >
        <tr align="center">
          <td height="50">
            <input name="pre"  type="button" class="g1" value="<"  onclick="preMonth();" style="width:30">&nbsp;
              <select name="month" class="god2" style="width:80" onchange="initCal();">
                <option value="1">一月</option>
                <option value="2">二月</option>
                <option value="3">三月</option>
                <option value="4">四月</option>
                <option value="5">五月</option>
                <option value="6">六月</option>
                <option value="7">七月</option>
                <option value="8">八月</option>
                <option value="9">九月</option>
                <option value="10">十月</option>
                <option value="11">十一月</option>
                <option value="12">十二月</option>
              </select>
              <select name="year" class="god2" style="width:80" onchange="initCal();">
                <option value="<%=year - 1%>"><%=year - 1%></option>
                <option value="<%=year%>" selected><%=year%></option>
                <option value="<%=year + 1%>"><%=year + 1%></option>
              </select>
            <input type="button" name="next" value=">" class="g1" onClick="nextMonth();" style="width:30">
          </td>
        </tr>
      </table>
      <table width="420" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E3E2DF"  class="text_12">
          <tr>
            <td width="100%">
              <table width="100%" border="0" cellspacing="1" cellpadding="0" align="center" bordercolorlight="#cccccc" bordercolordark="#6699cc" bgcolor="#778899">
                <tr class="detail_head">
                  <td align="center" height="30" width="60"><font size="5pt" color='red'>日</font></td>
                  <td align="center" width="60"><font size="5pt">一</font></td>
                  <td align="center" width="60"><font size="5pt">二</font></td>
                  <td align="center" width="60"><font size="5pt">三</font></td>
                  <td align="center" width="60"><font size="5pt">四</font></td>
                  <td align="center" width="60"><font size="5pt">五</font></td>
                  <td align="center" height="30" width="60"><font size="5pt" color='red'>六</font></td>
                <tr>
              </table>
               <table id="EditTable" width="100%" border="0" cellspacing="1" cellpadding="0" align="center" bordercolorlight="#cccccc" bordercolordark="#6699cc" bgcolor="#778899">
               
              </table>
            </td>
          </tr>
          <tr>
            <td height="30" class="list_body_out">红色为休息日，双击完成工作日和休息日转换</td>
          </tr>
          <tr><td height="15" class="list_body_out"></td></tr>
          <tr>
            <td align="center" class="list_body_out">
                                     系统类别：
<%if(gnidstr.indexOf("1051")>=0){%>
              <input id='ywlb1' type='radio' value='01' name='R1' onclick='changeYwlb(this.value);'>车管
<%}if(gnidstr.indexOf("1052")>=0){%>
              <input id='ywlb2' type='radio' value='02' name='R1' onclick='changeYwlb(this.value);'>驾管
<%}if(gnidstr.indexOf("1053")>=0){%>
              <input id='ywlb3' type='radio' value='03' name='R1' onclick='changeYwlb(this.value);'>事故违法
<%}%>
<%//}if(gnidstr.indexOf("1054")>=0){%>
              <!-- input id='ywlb4' type='radio' value='04' name='R1' onclick='changeYwlb(this.value);'-->
<%//}%>
            </td>
          </tr>
      </table>
    </td>
  </tr>
</form>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="text_12" width="100%" align="center">
<tr><td colspan="2" class="" align="right">
<input name="setMr" type="button" class="button" style="cursor:hand;"  value="置默认休息日" onclick="setMr()">
<input name="exit" type="button" class="button" style="cursor:hand;"  value=" 退 出 " onclick="window.close()">&nbsp;&nbsp;&nbsp;</td>
 </tr> </table>
<script language="JavaScript" type="text/javascript">vio_down()</script>

<form name="queryForm" action="" method="POST" target="paramIframe">
<input type="hidden" name="yymm" value="">
<input type="hidden" name="ywlb" value="">
</form>
<iframe name="paramIframe"  style="DISPLAY: none" height="300" width="300" ></iframe>
<script language="JavaScript" type="text/javascript">
<%
if(month==12){%>
	document.all.month.value='1';
<%}else{%>
	document.all.month.value='<%=month+1%>';
<%}
%>

gnidstr='<%=gnidstr%>';
if(gnidstr.indexOf("1051")>=0){
	queryForm.ywlb.value="01";
	workdayForm.ywlb1.checked=true;
	workdayForm.ywlb.value="01";
}else if(gnidstr.indexOf("1052")>=0){
	queryForm.ywlb.value="02";
	workdayForm.ywlb2.checked=true;
	workdayForm.ywlb.value="02";
}else if(gnidstr.indexOf("1053")>=0){
	queryForm.ywlb.value="03";
	workdayForm.ywlb3.checked=true;
	workdayForm.ywlb.value="03";
}else if(gnidstr.indexOf("1054")>=0){
	queryForm.ywlb.value="04";
	workdayForm.ywlb4.checked=true;
	workdayForm.ywlb.value="04";
}
initCal();
</script>
</body>
</html>
