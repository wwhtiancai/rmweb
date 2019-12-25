<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>通行信息上传配置</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript">
<!--
var selectedFzjgp_bg = 0;
var bNeedLoadTollgates = true;
$(document).ready(function(){
loadRoads();
$("#kssj").val(getNow());
$("#jssj").val(getNextYear());
<c:if test="${savedPzms!=null}">
	var m = "<c:out value='${savedPzms}'/>";
    $("input[name='mode']").each(function(){
		if(m == ($(this).val())){$(this).attr("checked", "true");}
	});
	
	changeMode();
	
	if("1" == m || "3" == m){
		$(".fzjgps").each(function(){
			$(this).attr("checked", "true");
			changeFzjgp($(this).val());
		});
	}
	
	if("1" == m || "3" == m){
		$("input[name='fzjg']").each(function(){
			if("<c:out value="${savedZd}"/>".indexOf("[" +$(this).val()+"]")!=-1){
				$(this).attr("checked", "true");
			}
		});
		
		$(".fzjgps").each(function(){
			var f = true;
			var id = "selected"+$(this).val();
			$("#"+id+ "   li   input").each(function(){
				if($(this).attr("checked")){
					f = false;
				}
			});
			if(f){
				$(this).attr("checked", false);
				changeFzjgp($(this).val());
			}
		});
		
	}else if("2" == m){
		$("#dlmc").val("<c:out value='${savedDldm}'/>");
		loadZds();
		$("input[name='zdFzjg']").each(function(){
			if("<c:out value="${savedZd}"/>".indexOf("[" +$(this).val()+"]")!=-1){
				$(this).attr("checked", "true");
			}else{
				$(this).attr("checked", false);
			}
		});
	}

	if("3" == m){
		loadTollgates();
		$("#kkmc").val("<c:out value="${savedKkbh}"/>");
		//$("#kkmc").html("<option value='<c:out value="${savedKkbh}"/>' checked><c:out value="${savedKkmc}"/></option>");
		bNeedLoadTollgates = false;
	}

	$("input[name='dwfl']").each(function(){
		if(dwfl.indexOf($(this).val()) != -1){$(this).attr("checked", "true");}
	});
	
	$("#kssj").val("<c:out value="${savedKssj}" />");
	$("#jssj").val("<c:out value="${savedJssj}" />");
</c:if>
});
function query_cmd(){
	$("#myform").attr("action","<c:url value='/tollgatestatus.dev?method=listReg&xscj='/>" + "<c:out value="${xscj}" />");
	closes();
	$("#myform").submit();
}

function chkIpt()
{
	if(!checkNull($("#kssj"),"开始时间")) return false;
	if(!checkNull($("#jssj"),"结束时间")) return false;
	if(!compareDate($("#kssj").val(),$("#jssj").val(),"开始时间","结束时间")) return false;
	
	var m = $("input[name='mode'][checked=true]").val();
	if("1" == m){
		if($("input[name='fzjg'][checked=true]").length < 1){
			alert("请选择支队");
			return false;
		}
	}else if("2" == m){
		if($("#dlmc").val().length < 1){
			alert("请选择道路");
			return false;
		}
		
		if($("input[name='zdFzjg'][checked=true]").length < 1){
			alert("请选择支队");
			return false;
		}
	}else if("3" == m){
		if($("#kkmc").val().length < 1){
			alert("请选择卡口");
			return false;
		}
	}
	
	return true;
}

function save(){
	if(chkIpt())
	{
		$("#myform").attr("action","<c:url value='/passInfCfg.tfc'/>?method=saveApply&type=" + "<c:out value="${type}"/>" + "&pzbh=<c:out value="${pzbh}" />");
		closes();
		$("#myform").ajaxSubmit({
			dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
		});
	}
}
function returns(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("保存成功！");
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}

function del(){
	if(confirm("是否确定删除该配置信息？")){
		$("#myform").attr("action","<c:url value='/passInfCfg.tfc'/>?method=del&pzbh=<c:out value='${pzbh}' />");
		closes();
		$("#myform").ajaxSubmit({
			dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
		});
	}
}

function returndeletes(data) { 
	if(data["code"]=="1"){
		window.opener.query_cmd();
		alert("删除成功！");
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}




function getNow(time){
	var r;
	var d=new Date();
	r=d.getFullYear()+"-";
	if(d.getMonth()>=9)
		r+=(d.getMonth()+1)+"-";
	else
		r+="0"+(d.getMonth()+1)+"-";	
	if(d.getDate()<10)
		r+="0"+(d.getDate());
	else
		r+=(d.getDate());
	return r;
}
function getNextYear(){
	var today = new Date(); 
	var x=today.getFullYear(); 
	x=x+1;
	today.setFullYear(x); 
	var month=today.getMonth()+1;
	var day=today.getDate(); 
	if(month<10) sMonth="0"+month; 
	else sMonth = month; 
	if(day<10) sDay="0"+day; 
	else sDay = day; 
	r=today.getFullYear()+"-"+sMonth+"-"+sDay; 
	return r;
}
function toggleZd(obj, id){
	$("#"+id+ "   li   input").each(function(){
		if($(obj).attr("checked")){
			$(this).attr("checked", "true");
		}else{
			$(this).attr("checked", false);
		}
	});
	
	bNeedLoadTollgates = true;
}
function changeFzjgp(fzjgp){
	bNeedLoadTollgates = true;
	if($("#fzjgp" + fzjgp).attr("checked")){
		checkedFzjgp = $(".fzjgps[checked=true]");
		
		/*if(checkedFzjgp.length > 3){
			alert("查询省份不能超过3个！");
			$("#fzjgp" + fzjgp).attr("checked", false);
			return;
		}*/
		
		$(".fzjgps").attr("disabled", true);
		var checkedFzjgp = $(".fzjgps[checked=true]");
		curUrl = "<c:url value='passQuery.tfc?method=getRemoteMachine&fzjgp='/>" + encodeURI(encodeURI(fzjgp));
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success: function(data){
				$(".fzjgps").attr("disabled", false);
				var jg = data['jg'];
				var preCurH = "";
				if(jg == '1'){
					
					curH = '<ul style="width:100%;">';
					$.each(data['msg'], function(i, item){
						if(decodeURI(item['fzjg']).indexOf("ZD") == -1){
							t = '<li style="float:left;width:78px;">';
							t += '<input type="checkbox" name="fzjg" onclick="changeFzjg(this)" value="' + decodeURI(item['fzjg']) + '">' + decodeURI(item['azdmc']);
							t += '</li>';
							curH += t;
						}
						else{
							preCurH = "<table id='selected" + fzjgp + "' border=0 cellpadding=2 cellspacing=2 "
							+"width=100% style='font-size:12px;border:1px dashed #DDDDDD;margin-top:4px; "
							+"margin-bottom:4px'><tr><td width=20 align=center><input type='checkbox' "
							+"id='chb"+decodeURI(item['fzjg'])+"' onclick='toggleZd(this,\"selected"+ fzjgp +"\")' />"
							+"</td><td width=60 align=center bgcolor='#EFEFEF'>"
							+"<label for='chb"+decodeURI(item['fzjg'])+"'>" 
							+ decodeURI(item['azdmc']) + "</label></td><td>";
						}
					});
					curH += '</ul>';
					curH += '</td></tr></table>';
					$("#dsfzjg").append(preCurH + curH);
				}else if(jg == '0'){
					alert('未找到');
				}
				var ssss = $("#dsfzjg").children();
				$.each(ssss, function(i,item){
					selectedFzjgp_bg = (selectedFzjgp_bg + 1) % 2;
					$(item).attr("class", "selectedFzjgpDivBg" + selectedFzjgp_bg)
				});
			},
			exception: function(data){
				$(".fzjgps").attr("disabled", false);
			}
		});
	}else{
		$("#selected" + fzjgp).remove();
	}
}

function changeFzjg(obj){
	bNeedLoadTollgates = true;
	
	if($("input[name='mode'][checked=true]").val() == "3"){
		$("#kkmc").html("<option value=''></option>");
	}
}

function loadRoads(){
		var html = "<option value=''></option>";
		var fzjgp = "", seg = "@@@@";
		$(".fzjgps").each(function(){
			if($(this).attr("checked")){
				fzjgp += ($(this).val() + seg);
			}
		});	
		if(fzjgp.indexOf(seg) != -1){
			fzjgp = fzjgp.substring(0, fzjgp.length - seg.length);
		}
		var curUrl = "<c:url value='passInfCfg.tfc?method=getRoads&fzjgp='/>" + encodeURI(encodeURI(fzjgp));
		$.ajax({
			type:"GET",
			url:curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success:function(data){
				if(data["jg"] == "1"){
					$.each(data["msg"],function(i, item){
						html += "<option value='"+decodeURI(item.dldm)+"'>"+decodeURI(item.dlmc)+"</option>";
					});
				}
				else{
					alert(decodeURI(data["msg"]) + " 请选择其他省份");
				}
			}
		});
		$("#dlmc").html(html);
}

function loadTollgates(){
if(bNeedLoadTollgates){
	var html = "<option value=''></option>";
	var fzjgs = "", seg = "@@@@";
	var dldm = $("#dlmc").val();
	$("input[name='fzjg']").each(function(){
		if($(this).attr("checked")){
			fzjgs += ($(this).val() + seg);
		}
	});
	if(fzjgs.indexOf(seg) != -1){
		fzjgs = fzjgs.substring(0, fzjgs.length - seg.length);
	}else{
		alert("请先选择支队");
		$("#kkmc").html(html);
		return;
	}
	var curUrl = "<c:url value='passInfCfg.tfc?method=getTollgates&fzjgs='/>" + encodeURI(encodeURI(fzjgs));
		$.ajax({
			type:"GET",
			url:curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success:function(data){
				if(data["jg"] == "1"){
					$.each(data["msg"],function(i, item){
						html += "<option value='"+decodeURI(item.kkbh)+"'>"+decodeURI(item.kkmc)+"</option>";
					});
					bNeedLoadTollgates = false;
				}
				else{
					alert(decodeURI(data["msg"] + " 请选择其他支队"));
				}
			}
		});
	$("#kkmc").html(html);
}	
}
function changeMode(){
	var m = $("input[name='mode'][checked=true]").val();
	if("1" == m){
		$("#sf").css("display","block");
		$("#zd").css("display","block");
		$("#zds").css("display","none");
		$("#dl").css("display","none");
		$("#kk").css("display","none");
	}else if("2" == m){
		$("#sf").css("display","none");
		$("#zd").css("display","none");
		$("#zds").css("display","block");
		$("#dl").css("display","block");
		$("#kk").css("display","none");
		
	}else if("3" == m){
		$("#sf").css("display","block");
		$("#zd").css("display","block");
		$("#zds").css("display","none");
		$("#dl").css("display","none");
		$("#kk").css("display","block");
	}
}

function loadZds(){
	var html="";
	var curUrl = "<c:url value='passInfCfg.tfc?method=getZds&dldm='/>" + $("#dlmc").val();
		$.ajax({
			type:"GET",
			url:curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success:function(data){
				if(data["jg"] == "1"){
					var curH = '<ul style="width:100%;">';
					
					$.each(data["msg"],function(i, item){
						t = '<li style="float:left;width:78px;">';
						t += '<input type="checkbox" name="zdFzjg" checked value="' + decodeURI(item['fzjg']) + '">' + decodeURI(item['azdmc']);
						t += '</li>';
						curH += t;
					});
					
					curH += '</ul>';
					html = curH;
				}
			}
		});
	$("#zdsDiv").html(html);	
}
//-->
</script>
</head>
<body onUnload="closesubwin()">
<div id="panel" style="display:none">
	<div id="paneltitle">通行信息上传配置</div>
	<form action="" method="post" name="myform" id="myform" enctype="multipart/form-data">
<table border="0" cellspacing="1" cellpadding="0" class="detail"
	width="98%" align="center" valign="top" style="margin-top:6px">
		<col width="10%">
		<col width="40%">
		<col width="10%">
		<col width="40%">
	<tr>
		<td class="head"><span class="gotta">*</span>开始时间&nbsp;</td>
		<td class="body"><input type="text"
			name="kssj" id="kssj" maxlength="10" style="width:80;text-align:center" readonly><a
			href="javascript:void(0)" onClick="riqi('kssj')" HIDEFOCUS><img
			src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
			width="34" height="22" border="0" align="absmiddle"></a></td>
		<td class="head"><span class="gotta">*</span>结束时间&nbsp;</td>
		<td class="body"><input type="text"
			name="jssj" id="jssj" maxlength="10" style="width:80;text-align:center" readonly> <a
			href="javascript:void(0)" onClick="riqi('jssj')" HIDEFOCUS><img
			src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
			width="34" height="22" border="0" align="absmiddle"></a></td>
	</tr>
	<tr>
		<td class="head"><span class="gotta">*</span>配置模式&nbsp;</td>
		<td class="body" colspan="3">
			<input type="radio" name="mode" value="1" checked id="mode1" onclick="changeMode()" /><label for="mode1">按支队</label>
			<input type="radio" name="mode" value="2" id="mode2" onclick="changeMode()" /><label for="mode2">按道路</label>
			<input type="radio" name="mode" value="3" id="mode3" onclick="changeMode()" /><label for="mode3">按卡口</label>
		</td>
	</tr>
	<tr id="sf">
		<td class="head"><span class="gotta">*</span>省份&nbsp;</td>
		<td class="body" colspan="3">
		<c:forEach items="${fzjgList}" var="current">
			<li style="width:80px;">
				<input type="checkbox" class="fzjgps" id="fzjgp<c:out value='${current.fzjg}'/>" value="<c:out value='${current.fzjg}'/>" onclick="changeFzjgp('<c:out value='${current.fzjg}'/>')">
				<label for="fzjgp<c:out value='${current.fzjg}'/>"><c:out value='${current.azdmc}'/></label>
			</li>
		</c:forEach>
		</td>
	</tr>
	<tr id="zd">
		<td class="head"><span class="gotta">*</span>支队&nbsp;</td>
		<td class="body" colspan="3"><div id="dsfzjg"></div></td>
	</tr>
	<tr id="dl" style="display:none">
		<td class="head"><span class="gotta">*</span>道路&nbsp;</td>
		<td class="body" colspan="3">
			<select name="dlmc" id="dlmc" onchange="loadZds()">
				<option value=""></option>
			</select>
		</td>
	</tr>
	<tr id="zds" style="display:none">
		<td class="head"><span class="gotta">*</span>支队&nbsp;</td>
		<td class="body" colspan="3"><div id="zdsDiv"></div></td>
	</tr>
	<tr id="kk" style="display:none">
		<td class="head"><span class="gotta">*</span>卡口&nbsp;</td>
		<td class="body" colspan="3">
			<select name="kkmc" id="kkmc" onclick="loadTollgates()">
				<option value=""></option>
			</select>
		</td>
	</tr>
</table>
		<table border="0" cellspacing="1" cellpadding="0" class="detail" style="margin-top:6px">
			<tr>
				<td class="command">
					<c:if test="${savedPzms!=null}"><input type="button" name="delbutton" id="delbutton" onclick="del()" value="删除" class="button_del"></c:if>
					<input type="button" onclick="save()" value="保存" class="button_save">
					<input type="button" onclick="javascript:window.close();" value="关闭" class="button_close">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<%=JspSuport.getInstance().outputCalendar()%>
</body>
</html>