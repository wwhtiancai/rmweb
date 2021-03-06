<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title><c:out value="${cxmc}" /></title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js"
	type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js"
	type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">var curPage = -1;
var curIdx = -1;
var curPass;
var keyFix;

var suspData = null;

var passPicWid = null;
var suspectWid = null;

window.onunload = function(){
	if(passPicWid != null){
		passPicWid.close();
	}
	if(suspectWid != null){
		suspectWid.close();
	}
}

$(document).ready(function(){
	code = <c:out value='${code}'/>;
	keyFix = '<c:out value='${keyFix}'/>';
	if(code == 1){
		gcxh = '<c:out value='${gcxh}'/>';
		curPage = <c:out value='${page}'/>;
		curIdx = <c:out value='${idx}'/>;
		getPass(curIdx);
	}
});

function setLoaddataHtml() {
		$("#suspinfo").empty();
		loadDataHtml = '<br><br><img alt="" src="images/running.gif"><br>正在载入布控信息...';
		$("#suspinfo").html(loadDataHtml);
	}

function getSuspinfo() {
		//setLoaddataHtml();
		curHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list">\n';
		curHtml += '<tr class="head">\n';
		curHtml += '<td width="8%">布控类型</td>\n';
		curHtml += '<td width="12%">布控子类型</td>\n';
		curHtml += '<td width="6%">数据来源</td>\n';
		curHtml += '<td width="5%">布控级别</td>\n';
		curHtml += '<td width="6%">涉危标记</td>\n';
		curHtml += '<td width="8%">号牌种类</td>\n';
		curHtml += '<td width="5%">车身颜色</td>\n';
		curHtml += '<td width="8%">品牌型号</td>\n';
		curHtml += '<td width="5%">车辆类型</td>\n';
		curHtml += '<td width="12%">布控机关</td>\n';
		curHtml += '</tr>\n';

		$
				.each(
						suspData,
						function(i, item) {
							curHtml += '<tr class="out" style="cursor:pointer" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" ondblclick="showsuspdetail(\''
									+ item['bkxh']
									+ '\','
									+ item['sjlx']
									+ ')">\n';
							curHtml += '<td>'
									+ decodeURIComponent(item['bklxmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['bkzlxmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['bksjlymc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['bkjbmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['swbjmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['hpzlmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['csysmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['clpp'])
									+ '/'
									+ decodeURIComponent(item['clxh'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['cllxmc'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['bkjgmc'])
									+ '</td>';
							//curHtml += '<td><span>查看详细</span>&nbsp;<span>精确追踪</span></td>';
							curHtml += '</tr>';
						});

		
		curHtml += '</table>';
		$("#suspinfo").empty();
		$("#suspinfo").html(curHtml);
		$(document.doby).scrollTop($(document.doby).height());
	}
	
	
function getSusp(hpzl,hphm){

	setLoaddataHtml();
	curUrl = "<c:url value='tshpQuery.tfc?method=doSuspList&hpzl='/>" + hpzl + "&hphm=" +encodeURIComponent(hphm);
	
	$.getJSON(curUrl, 
		function(data){
			code = data['code'];
			if(code == 1){
				msg = data['msg'];
				suspData = msg;
				
				getSuspinfo();
			}else{
				alert(decodeURIComponent(data['msg']));
			}
		}		
	);
}

function getPass(idx){
	//alert(idx);
	if(curPage == -1){
		//alert();
		return;
	}
	closes();
	if(keyFix == 'local'){
		curUrl = "<c:url value='tshpQuery.tfc?method=doPassLocalDetail&page='/>" + curPage + "&idx=" + idx + "&model=<c:out value='${model}'/>"+"&keyFix="+keyFix;
	}else if(keyFix == 'province'){
		curUrl = "<c:url value='tshpQuery.tfc?method=doPassProvinceDetail&page='/>" + curPage + "&idx=" + idx + "&model=<c:out value='${model}'/>"+"&keyFix="+keyFix;
	}else{
		alert('查询异常！');
		return;
	}
	$.getJSON(curUrl, 
		function(data){
			opens();
			code = data['code'];
			if(code == 1){
				msg = data['msg'];
				curPass = msg;
				$("#hpzlTD").html(decodeURIComponent(msg['hpzlmc']));
				$("#hphmTD").html(decodeURIComponent(msg['hphm']));
				$("#hpysTD").html(decodeURIComponent(msg['hpysmc']));
				
				$("#hpzl").val(msg['hpzl']);
				$("#hpzl").val(decodeURIComponent(msg['hphm']));
				$("#hpys").val(msg['hpys']);
				
				$("#gcsjTD").html(msg['gcsj'].substring(0,19));
				if(msg['clsd'] > 0){
					$("#clsdTD").html(msg['clsd'] + '&nbsp;km/h');
				}else{
					if(msg['clsd'] == -1){
						$("#clsdTD").html('--');
					}else{
						$("#clsdTD").html(msg['clsd']);
					}
				}
				
				if(msg['cwkc'] > 0){
					$("#cwkcTD").html(msg['cwkc'] + '&nbsp;cm');
				}else{
					if(msg['cwkc'] == -1){
						$("#cwkcTD").html('--');
					}else{
						$("#cwkcTD").html(msg['cwkc']);
					}
				}
				
				$("#cllxTD").html(decodeURIComponent(msg['cllxmc']) + '/' + decodeURIComponent(msg['clppmc']));
				$("#csysTD").html(decodeURIComponent(msg['csysmc']));
				
				$("#cllx").val(msg['cllx']);
				$("#csys").val(msg['csys']);
				$("#clpp").val(msg['clpp']);
				
				$("#xzqhTD").html(decodeURIComponent(msg['xzqhmc']));
				$("#dldmmcTD").html(decodeURIComponent(msg['dldmmc']));
				$("#kkjcTD").html(decodeURIComponent(msg['kkbhmc']));
				$("#fxlxmcTD").html(decodeURIComponent(msg['fxlxmc']));
				
				$("#ldlxmcTD").html(decodeURIComponent(msg['ldlxmc']));
				$("#kklxmc").html(decodeURIComponent(msg['kklxmc']));
				$("#ljtjmc").html(decodeURIComponent(msg['ljtjmc']));
				$("#sfjbtztp").html(decodeURIComponent(msg['sfjbtztp']));
				
				
				$("#cdhTD").html(msg['cdh']);
				$("#kkbh").val(msg['kkbh']);
				
				$("#fzhpzlTD").html(decodeURIComponent(msg['fzhpzlmc']));
				$("#fzhphmTD").html(decodeURIComponent(msg['fzhphm']));
				$("#fzhpysTD").html(decodeURIComponent(msg['fzhpysmc']));
				
				$("#rksjTD").html(decodeURIComponent(msg['rksj']).substring(0,19));
				
				$("#vehClppTD").html('');
				$("#vehCsysTD").html('');
				$("#vehCllxTD").html('');
				$("#vehClztTD").html('');
				$("#vehCcdjrqTD").html('');
				$("#vehSyrTD").html('');
				$("#vehSyxzTD").html('');
				$("#vehClxhTD").html('');
				
				tmpFwdz = decodeURIComponent(msg['fwdz']);
				tmpTplj = encodeURI(msg['tplj']);
				tmpTp1 =  encodeURI(msg['tp1']);
				tmpTp2 = encodeURI(msg['tp2']);
				tmpTp3 = encodeURI(msg['tp3']);
				
				srcTztp = '&gcxh=' + msg['gcxh'];
				srcTp1 = '';
				srcTp2 = '';
				srcTp3 = '';
				
				tmpTpUrl = '&gcxh=' + msg['gcxh'];
				if(tmpFwdz != null && tmpFwdz != ''){
					tmpTpUrl = tmpFwdz + '/readPassPic.tfc?method=getPassPic&fhk=1' + tmpTpUrl;
					srcTztp = tmpFwdz + '/readTzPic.tfc?method=getTzPicture&fhk=1' + srcTztp;
				}else{
					tmpTpUrl = '<c:url value="/readPassPic.tfc?method=getPassPic&fhk=1"/>' + tmpTpUrl;
					srcTztp = '<c:url value="/readTzPic.tfc?method=getTzPicture&fhk=1"/>' + srcTztp;
				}
				
				srcTp1 = tmpTpUrl + '&tpxh=1';
				
				if(tmpTp2 != null && tmpTp2 != ''){
					srcTp2 = tmpTpUrl + '&tpxh=2';
				}
				if(tmpTp3 != null && tmpTp3 != ''){
					srcTp3 = tmpTpUrl + '&tpxh=3';
				}
				
				bodyWidth = $(document.body).width();
				picWidth = parseInt(bodyWidth * 0.96 / 3 - 20);
				//alert((bodyWidth * 0.96 / 3 - 10) * 0.8);
				picHeight = parseInt(picWidth * 0.8);
				
				//alert(picWidth + "<br>" + picHeight);
				
				imgHtm = '&nbsp;<div class="jqzoomPassQuery" onclick="passPicWid=openImage(\'tp1\')"><img id="tp1" src="' + srcTp1 + '" jqimg="' + srcTp1 + '" width="' + picWidth + 'px" height="' + picHeight + 'px"></div>';
				if(srcTp2 != ''){
					imgHtm += '&nbsp;<div class="jqzoomPassQuery" onclick="passPicWid=openImage(\'tp2\')"><img id="tp2" src="' + srcTp2 + '" jqimg="' + srcTp2 + '" width="' + picWidth + 'px" height="' + picHeight + 'px"></div>';
				}
				if(srcTp3 != ''){
					imgHtm += '&nbsp;<div class="jqzoomPassQuery" onclick="passPicWid=openImage(\'tp3\')"><img id="tp3" src="' + srcTp3 + '" jqimg="' + srcTp3 + '" width="' + picWidth + 'px" height="' + picHeight + 'px"></div>';
				}
				$("#passPics").html(imgHtm);
				
				$("#tztpSrc").attr("src", srcTztp);
				
				$(".jqzoomPassQuery").jqueryzoom({
			        xzoom: 300, //设置放大 DIV 长度（默认为 200）
			        yzoom: 300, //设置放大 DIV 高度（默认为 200）
			        offset: 10, //设置放大 DIV 偏移（默认为 10）
			        position: "buttom", //设置放大 DIV 的位置（默认为右边）
			        preload:1,
			        lens:1
			    });
				curIdx = idx;
				
				getSusp(msg['hpzl'],msg['hphm']);
			}else{
				alert(decodeURIComponent(data['msg']));
			}
		}		
	);
}

function showsuspdetail(bkxh,sjlx){
    openwin("<c:url value='/suspQuery.vmc'/>?method=jcbkDetail&bkxh="+bkxh+"&bkxxlx=1&sjlx="+sjlx, "suspdetail");
}

function doChecking(){
	//object:DEV_TOLLGATE
	//regulation:kkbh(非空,等长(12))
	return true;
}
function getNext(p){
	$("#veh").css("display", "none");
	getPass(curIdx + p);
}
function doKeyEvent(){
	keyCode = event.keyCode;
	if($("#btnGetNext1").attr("disabled") == false && $("#btnGetNext2").attr("disabled") == false){
		if(keyCode == 37){
			//left
			getNext(-1);
		}else if(keyCode == 39){
			//right
			getNext(1);
		}
	}
	return true;
}
function getVehicle(){
	chpzl = decodeURIComponent(curPass['hpzl']);
	chphm = decodeURIComponent(curPass['hphm']);
	if(chpzl.length != 2 && chphm.length != 7){
		alert("号牌错误！");
		return;
	}
	
	if($("#veh").css("display") == 'none'){
		var cyzm = window.showModalDialog("passQuery.tfc?method=showValidCode","","dialogWidth:350px;dialogHeight:150px;center:1;help:0;resizable:0;status:0;scroll:0;");
		if (typeof cyzm == "undefined"){
			return false;		
		}
		$("#loadingVeh").html('<img alt="" src="images/running.gif"><br>正在查询，请稍候...');
		$("#veh").slideDown();
		$("#loadingVeh").css("display", "block");
		$("#vehDataTable").css("display", "none");
		curUrl = "<c:url value='tshpQuery.tfc?method=getVehicle&page='/>" + curPage + "&idx=" + curIdx + "&yzm=" + cyzm+"&keyFix="+keyFix;
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:true,
			dataType:"json",
			success: function(data){
				if(data['code'] == 1){
					$("#loadingVeh").css("display", "none");
					$("#vehDataTable").css("display", "block");
					msg = data['msg'];
					$("#vehClppTD").html(decodeURI(msg['clpp']));
					$("#vehCsysTD").html(decodeURI(msg['csysmc']));
					$("#vehCllxTD").html(decodeURI(msg['cllxmc']));
					$("#vehClztTD").html(decodeURI(msg['clztmc']));
					$("#vehCcdjrqTD").html(decodeURI(msg['ccdjrq']));
					$("#vehSyrTD").html(decodeURI(msg['syr']));
					$("#vehSyxzTD").html(decodeURI(msg['syxzmc']));
					$("#vehClxhTD").html(decodeURI(msg['clxh']));
				}else{
					$("#vehClppTD").html('');
					$("#vehCsysTD").html('');
					$("#vehCllxTD").html('');
					$("#vehClztTD").html('');
					$("#vehCcdjrqTD").html('');
					$("#vehSyrTD").html('');
					$("#vehSyxzTD").html('');
					$("#vehClxhTD").html('');
					$("#loadingVeh").css("display", "none");
					$("#vehDataTable").css("display", "none");
					$("#veh").slideUp();
					alert(decodeURIComponent(data['msg']));
					
					//$("#loadingVeh").html();
				}
			}
		});
	}else{
		$("#veh").slideUp();
	}
}
function getSuspect(){
	var curUrl = "<c:url value='suspQuery.vmc?method=getSuspList&hpzl='/>" + $("#hpzl").val() + "&hphm=" + encodeURI(encodeURI($("#hphm").val()));
	alert(curUrl);
	suspectWid = openwin800(curUrl, "getSuspList", "");
	alert(suspectWid);
}
</script>
</head>
<body onkeyup="doKeyEvent()">
	<div id="panel" style="display:none">
		<div id="paneltitle"><c:out value="${cxmc}" /></div>
		<div id="block">
			<div id="blocktitle">过车信息</div>
			<div id="blockmargin">8</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<%--
				过车序号,卡口编号,方向类型,车道号,过车时间,号牌种类,号牌号码,号牌颜色,车外廓长,车辆速度
				车身颜色,车辆品牌,车辆类型,辅助号牌种类,辅助号牌号码,辅助号牌颜色,图片路径,图片1文件名,
				图片2文件名,图片3文件名,入库时间,备用字段			
			 --%>
				<tr>
					<td class="head"><input type="hidden" id="hpzl" name="hpzl">号牌种类</td>
					<td class="body" id="hpzlTD"></td>
					<td class="head"><input type="hidden" id="hphm" name="hphm">号牌号码</td>
					<td class="body" id="hphmTD"></td>
					<td class="head" rowspan="3">特征图片</td>
					<td class="body" rowspan="3"><img id="tztpSrc" src=""
						height="70px">
					</td>
				</tr>
				<tr>
					<td class="head"><input type="hidden" id="hpys" name="hpys">号牌颜色</td>
					<td class="body" id="hpysTD"></td>
					<td class="head"><input type="hidden" id="csys" name="csys">车身颜色</td>
					<td class="body" id="csysTD"></td>
				</tr>
				<tr>
					<td class="head">过车时间</td>
					<td class="body" id="gcsjTD"></td>
					<td class="head">车辆速度</td>
					<td class="body" id="clsdTD"></td>
				</tr>
				<tr>
					<td class="head">辅助号牌种类</td>
					<td class="body" id="fzhpzlTD"></td>
					<td class="head">辅助号牌号码</td>
					<td class="body" id="fzhphmTD"></td>
					<td class="head">辅助号牌颜色</td>
					<td class="body" id="fzhpysTD"></td>
				</tr>
				<tr>
					<td class="head"><input type="hidden" id="cllx" name="clxx"><input
						type="hidden" id="clpp" name="clpp">车辆类型/品牌</td>
					<td class="body" id="cllxTD"></td>
					<td class="head">车外廓长</td>
					<td class="body" id="cwkcTD"></td>
					<td class="head">入库时间</td>
					<td class="body" id="rksjTD"></td>
				</tr>
			</table>
		</div>
		<div id="block">
			<div id="blocktitle">卡口信息</div>
			<div id="blockmargin">8</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<%--
			过车序号,卡口编号,方向类型,车道号,过车时间,号牌种类,号牌号码,号牌颜色,车外廓长,车辆速度
			车身颜色,车辆品牌,车辆类型,辅助号牌种类,辅助号牌号码,辅助号牌颜色,图片路径,图片1文件名,
			图片2文件名,图片3文件名,入库时间,备用字段			
			 --%>
				<tr>
					<td class="head">行政区划</td>
					<td class="body" id="xzqhTD"></td>
					<td class="head">道路名称</td>
					<td class="body" id="dldmmcTD"></td>
					<td class="head">道路类型</td>
					<td class="body" id="ldlxmcTD"></td>
				</tr>
				<tr>
					<td class="head">卡口简称</td>
					<td class="body" id="kkjcTD"></td>
					<td class="head">行驶方向</td>
					<td class="body" id="fxlxmcTD"></td>
					<td class="head">车道号</td>
					<td class="body" id="cdhTD"></td>
				</tr>
				<tr>
					<td class="head">卡口类型</td>
					<td class="body" id="kklxmc"></td>
					<td class="head">拦截条件</td>
					<td class="body" id="ljtjmc"></td>
					<td class="head">是否具备特征图片</td>
					<td class="body" id="sfjbtztp"></td>
				</tr>
				<input type="hidden" id="kkbh" name="kkbh">

			</table>
		</div>
        
		<div class="s1" style="height:2px;"></div>
		<div id="block">
				<div id="blocktitle">布控信息</div>
		<div id="suspinfo" style="text-align:center;font-size:12px;"></div>
		</div>
		<div class="s1" style="height:4px;"></div>
		<div id="block">
			<div id="blocktitle">通行图片</div>
			<div id="blockmargin">8</div>
			<div id="passPics" style="width:100%;text-align:center;"></div>
		</div>
		<div id="veh" style="display:none;">
			<div id="block">
				<div id="blocktitle">机动车登记信息</div>
				<div id="blockmargin">8</div>
				<div id="loadingVeh"
					style="width:100%;text-align:center;font-size:12px;">
					<img alt="" src="images/running.gif"><br>正在查询，请稍候...
				</div>
				<table border="0" cellspacing="1" cellpadding="0" class="detail"
					id="vehDataTable">
					<col width="10%">
					<col width="23%">
					<col width="10%">
					<col width="23%">
					<col width="10%">
					<col width="24%">
					<tr>
						<td class="head">车辆类型</td>
						<td class="body" id="vehCllxTD"></td>
						<td class="head">车辆品牌</td>
						<td class="body" id="vehClppTD"></td>
						<td class="head">车辆型号</td>
						<td class="body" id="vehClxhTD"></td>
					</tr>
					<tr>
						<td class="head">车辆状态</td>
						<td class="body" id="vehClztTD"></td>
						<td class="head">使用性质</td>
						<td class="body" id="vehSyxzTD"></td>
						<td class="head">车身颜色</td>
						<td class="body" id="vehCsysTD"></td>
					</tr>
					<tr>
						<td class="head">初次登记日期</td>
						<td class="body" id="vehCcdjrqTD"></td>
						<td class="head">所有人</td>
						<td class="body" id="vehSyrTD"></td>
						<td class="head"></td>
						<td class="body"></td>
					</tr>
				</table>
			</div>
		</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<tr>
				<td class="command">
					<%--input type="button" class="button_suspect" onclick="getSuspect()" value="布控信息"> --%>
					<input type="button" class="button_vehicle" onclick="getVehicle()"
					value="车辆信息"> <input type="button" id="btnGetNext1"
					class="button_left" onclick="getNext(-1)" value="上一条"> <input
					type="button" id="btnGetNext2" class="button_right"
					onclick="getNext(1)" value="下一条"> <input type="button"
					name="closebutton" onclick="javascript:window.close();" value="关闭"
					class="button_close"></td>
			</tr>
		</table>
	</div>
</body>
</html>
