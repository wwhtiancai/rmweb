<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>���⳵���켣��Ϣ��ѯ</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
	media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
	type="text/css">
<script language="JavaScript" src="rmjs/jquery.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js"
	type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.loadthumb.js"
	type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/hphm.js"
	type="text/javascript"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>
<script type="text/javascript">
    var queryModel;
	var passData = null;
	var curPage;
	var totalCount;
	var pageCount;
	var pageSize;
	var isHandled = false;
	var curShowStyle;
	var passPicWidth;
	var passPicHeight;

	var detailWin = null;
	var handledWin = null;

	var gateData = null;

	var queryStatTimer;

	if (!window.console) {
		window.console = {
			log : function() {
				alert(arguments[0]);
			}
		}
	}
	$(window).bind("load", function() {
		$.datepicker.setDefaults($.datepicker.regional['']);
		$(".jscal").each(function() {
			eval($(this).html());
		});
	});

	window.onresize = function() {
		initSize();
	}

	window.onunload = function() {
		if (detailWin != null) {
			detailWin.close();
		}
		if (handledWin != null) {
			handledWin.close();
		}
	}

	$(document).ready(function() {
	    $("[limit]").limit();
		curShowStyle = $("input[name=showStyle][checked=true]").val();
		initSize();
		closes();
//		getTollgate();
		$("#kssj").val(getLastWeek()+" 00:00");
        $("#jssj").val(getNow(true).substring(0,16));
		opens();
	});

	function initSize() {
		bodyWidth = $(document.body).width();
		passPicWidth = parseInt((bodyWidth - 40) * 0.96 * 0.25);
		passPicHeight = parseInt(passPicWidth * 0.8);
		if (curShowStyle == 2) {
			//����ͼ��ʾ
			$(".passPicB").css("width", passPicWidth + "px");
			$(".passPicB").css("height", passPicHeight + "px");
		}
	}

	function doChecking() {
		if (!checkNull($("#kssj"), "��ʼʱ��"))
			return false;
		if (!checkNull($("#jssj"), "����ʱ��"))
			return false;
		if (!checkNull($("#yzm"), "��֤��"))
			return false;
		if (!compareDate($("#kssj").val(), $("#jssj")
				.val(), "��ʼʱ��", "����ʱ��"))
			return false;

		return true;
	}
	function query_cmd() {
		if (!doChecking()) {
			return;
		}
		closes();
		setQueryingHtml();
		curPage = 1;
		var tmpUrl = $("#myform").attr("action") + "&page=" + curPage;
        queryModel = $("#cxlx").val();
		$("#myform").ajaxSubmit({
			url : tmpUrl,
			dataType : "json",
			async : true,
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			success : function(data) {
				localReturns(data);
			}
		});
	}
	
	function validCxfw(fzjgVal) {
		//alert($("#fzjg" + fzjgVal).val() + ', checked:' + ($("#fzjg" + fzjgVal).attr("checked")) + ', isChecked:' + ($("#fzjg" + fzjgVal).attr("checked") == false));
		if ($("#fzjg" + fzjgVal).attr("checked") == true) {
			var cxfwAry = $("input[type=checkbox][name=fzjg][checked=true]");
			if (cxfwAry.length > 3) {
				$("#fzjg" + fzjgVal).attr("checked", false);
				alert("��ѯ��Χ���Ϊ3����");
			}
		}
	}

	function getTollgate() {

		var curUrl = "<c:url value='component.dev?method=getTollgate'/>"
		var glbmVal = $("#myform #glbm").val();
		if (typeof glbmVal != "undefined") {
			curUrl += "&glbm=" + glbmVal;
		}

		var dldmAry = $("#myform input[name=dldm][checked=true]");
		//	alert(dldmAry.length);
		//	alert(dldmAry);
		if (typeof dldmAry != "undefined" && dldmAry.length > 0) {
			//alert(dldmVal.length);
			//alert(dldmVal);
			var tmpDldmVal = '';
			$.each(dldmAry, function(i, dldmInput) {
				if (i != 0) {
					tmpDldmVal += ',';
				}
				tmpDldmVal += dldmInput.value;
				//alert(i + ":" + dldmInput.value);
				//alert();
			});

			curUrl += "&dldm=" + tmpDldmVal;
		}
		var kklxVal = $("#myform #kklx").val();
		if (typeof kklxVal != "undefined") {
			curUrl += "&kklx=" + kklxVal;
		}
		var ldlxVal = $("#myform #ldlx").val();
		if (typeof ldlxVal != "undefined") {
			curUrl += "&dllx=" + ldlxVal;
		}
	//	alert(curUrl);
		//var curUrl = "<c:url value='component.dev?method=getTollgate&glbm='/>" + $("#glbm").val() + "&dldm=" + $("#dldm").val() + "&kklx=" + $("#kklx").val() + "&ldlx=" + $("#ldlx").val();
		$("#myform #kkbhDIV").empty();
		//$("#kkbh").addOption("", "ȫ��");
		closes();
		$
				.getJSON(
						curUrl,
						function(data) {
							if (data['code'] == 1) {
								curHTML = '<ul>';
								gateData = data['msg'];
								$
										.each(
												data['msg'],
												function(i, item) {
													var tkkbh = item.kkbh;
													var tkkjc = decodeURI(item.kkjc);

													curHTML += '<li style="width:49%;float:left;"><input type="checkbox" id="kkbh' + tkkbh + '" name="kkbh" value="' + tkkbh + '">'
															+ tkkjc + '</li>';
													//$("#kkbh").addOption(item.kkbh, decodeURI(item.kkjc));
												});
								curHTML += '<ul>';
								$("#myform #kkbhDIV").html(curHTML);
							}
						});

		opens();
	}

	function getTollgateRoad() {
		var curUrl = "<c:url value='component.dev?method=getTollgateRoad'/>"
		var glbmVal = $("#myform #glbm").val();
		if (typeof glbmVal != "undefined") {
			curUrl += "&glbm=" + glbmVal;
		}
		var kklxVal = $("#myform #kklx").val();
		if (typeof kklxVal != "undefined") {
			curUrl += "&kklx=" + kklxVal;
		}
		var ldlxVal = $("#myform #ldlx").val();
		if (typeof ldlxVal != "undefined") {
			curUrl += "&dllx=" + ldlxVal;
		}

		//	var curUrl = "<c:url value='component.dev?method=getTollgateRoad&glbm='/>" + $("#glbm").val() + "&ldlx=" + $("#ldlx").val() + "&kklx=" + $("#kklx").val();
		//alert(curUrl);
		closes();
		$("#myform #dldmDIV").empty();
		//$("#dldm").addOption("", "");
		$
				.getJSON(
						curUrl,
						function(data) {
							curH = '<ul>';
							if (data['code'] == 1) {
								$
										.each(
												data['msg'],
												function(i, item) {
													curH += '<li style="float:left;width:99%;"><input type="checkbox" id="dldm'
															+ item.dldm
															+ '" name="dldm" value="'
															+ item.dldm
															+ '" onclick="getTollgate()">'
															+ item.dldm
															+ ':'
															+ decodeURI(item.dlmc)
															+ '</li>';
												});
							}
							curH += '</ul>';
							$("#myform #dldmDIV").html(curH);
						});
		opens();
	}

	function getTollgateAndRoad() {
		if($("#ldlx").val() != ""){
			getTollgateRoad();
		}
		if($("#ldlx").val() == ""){
			$("#dldmDIV").html("");
		}
		if($("#glbm").val() == "" && $("#kklx").val() == ""){
			$("#kkbhDIV").html("");
		}
		if($("#ldlx").val() != "" || $("#glbm").val() != "" || $("#kklx").val() != ""){
			getTollgate();
		}
	}

	function selectAllGate(isS) {
		if ($("input[name=kkbh][checked=true]").length > 0) {
			$("input[name=kkbh]").attr("checked", false);
		} else {
			$("input[name=kkbh]").attr("checked", true);
		}
	}

	function getDirect() {
		$("#fxlx").empty();
		curKkbh = $("#kkbh").val();

		if (curKkbh == null || curKkbh == '') {
			$("#fxlx").addOption("", "ȫ��");
			return;
		}

		curUrl = "<c:url value='component.dev?method=getTollgateDirect&kkbh='/>"
				+ curKkbh;
		$.getJSON(curUrl, function(data) {
			$.each(data, function(i, item) {
				$("#fxlx").addOption(item.fxbh, decodeURI(item.fxmc));
			});
		});
	}

	function localReturns(data) {
		var code = data['code'];
		var msg = data['msg'];
		totalCount = data['totalCount'];
		pageCount = data['pageCount'];
		pageSize = data['pageSize'];

		if (code == -1) {
			emptyDataDiv();
			alert("��ѯ����" + decodeURIComponent(msg));
			//return;
		} else if (code == 0) {
			emptyDataDiv();
			alert(decodeURIComponent(msg));
		} else if (code == 1) {
			passData = msg;
			reloadData();
		}

		opens();
		//document.ifrmaeecsideveh.reloadImage();
	}
	function reloadData() {
		if (passData == null) {
			return;
		}
		if (curShowStyle == 1) {
			//�б���ʾ
			getListHtml();
		} else if (curShowStyle == 2) {
			//����ͼ��ʾ
			getThumbHtml();
		}
	}
	function getListHtml() {
		setLoaddataHtml();
		curPageRow = 0;
		curHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list">\n';
		curHtml += '<tr class="head">\n';
		curHtml += '<td width="8%">��������</td>\n';
		curHtml += '<td width="8%">���ƺ���</td>\n';
		curHtml += '<td width="8%">��������</td>\n';
		curHtml += '<td width="12%">��·����</td>\n';
		curHtml += '<td width="22%">���ڼ��</td>\n';
		curHtml += '<td width="22%">��ʻ����</td>\n';
		curHtml += '<td width="12%">����ʱ��</td>\n';
		curHtml += '</tr>\n';

		$
				.each(
						passData,
						function(i, item) {
						    curPageRow = curPageRow + 1;
							curHtml += '<tr class="out" style="cursor:pointer" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" ondblclick="showDetail(\''
									+ item['gcxh']
									+ '\','
									+ curPage
									+ ','
									+ i
									+ ')">\n';
							curHtml += '<td>'
									+ decodeURIComponent(item['hpzlmc'])
									+ '</td>';
							curHtml += '<td class="hpys' + item['hpys'] + '">'
									+ decodeURIComponent(item['hphm'])
									+ '</td>';
							curHtml += '<td>'
									+ decodeURIComponent(item['xzqhmc'])
									+ '</td>';
							curHtml += '<td align="center" limit="9">'
									+ decodeURIComponent(item['dldmmc'])
									+ '</td>';
							curHtml += '<td align="center" limit="16">'
									+ decodeURIComponent(item['kkbhmc'])
									+ '</td>';
							curHtml += '<td align="center" limit="16">'
									+ decodeURIComponent(item['fxlxmc'])
									+ '</td>';
							curHtml += '<td>' + item['gcsj'].substring(0, 19)
									+ '</td>';
							//curHtml += '<td><span>�鿴��ϸ</span>&nbsp;<span>��ȷ׷��</span></td>';
							curHtml += '</tr>';
						});

		curHtml += '<tr><td colspan="8" class="page">';
		if (pageCount>0){
		    curHtml +='�� ' + totalCount + '��&nbsp;';
		    curHtml +='��' + pageCount + 'ҳ';
		}
		curHtml +='&nbsp;��' + curPage + 'ҳ &nbsp;';

		curHtml += '<label id="pageCMD" onclick="gotoPage(1)" style="cursor:hand">��ҳ</label>&nbsp;';
		if (curPage == 1) {
			curHtml += '<label id="pageCMD">��һҳ</label>&nbsp;';
		} else {
			curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage - 1)
					+ ')" style="cursor:hand">��һҳ</label>&nbsp;';
		}
 
		if ((curPage < pageCount) || (pageCount==0 && curPageRow>0 && pageSize==curPageRow)){
		    curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage + 1)
					+ ')" style="cursor:hand">��һҳ</label>&nbsp;';
		}else{
			curHtml += '<label id="pageCMD">��һҳ</label>&nbsp;';
		}

        if (pageCount>0) 
		   curHtml += '<label id="pageCMD" onclick="gotoPage(' + pageCount
				+ ')" style="cursor:hand">ĩҳ</label>&nbsp;</td></tr>';

		curHtml += '</table>';
		$("#data").empty();
		$("#data").html(curHtml);
		$("[limit]").limit();
		$(document.doby).scrollTop($(document.doby).height());
	}
	function getThumbHtml() {
		setLoaddataHtml();
		curHtml = '';
		curCount = 0;
		curHtml += '<table border="0" cellpadding="0" cellspacing="6px" width="100%" class="thumbTable">';
		curH = '';
		$
				.each(
						passData,
						function(i, item) {
							if (i > 0 && (i % 4) == 0) {
								curHtml += '<tr>' + curH + '</tr>';
								curH = '';
							}

							tmpFwdz = decodeURIComponent(item['fwdz']);

							imgSrc = '&gcxh=' + item['gcxh'] + '&tpxh=1';
							if (tmpFwdz != null && tmpFwdz != '') {
								imgSrc = tmpFwdz
										+ '/readPassPic.tfc?method=getPassPic'
										+ imgSrc;
							} else {
								imgSrc = '<c:url value="/readPassPic.tfc?method=getPassPic"/>'
										+ imgSrc;
							}

							//alert("ͼƬ·����" + imgSrc);

							//����ͼģʽ���зŴ󾵹���
							//curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="showDetail(\'' + item['gcxh'] + '\',' + curPage + ',' + i + ')">' + '<img class="passPicB" style="width:' + passPicWidth + 'px;height:' + passPicHeight + 'px;" src="' + imgSrc + '" jqimg="' + imgSrc + '"></div>';
							//����ͼģʽ���޷Ŵ󾵹���
							curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="showDetail(\''
									+ item['gcxh']
									+ '\','
									+ curPage
									+ ','
									+ i
									+ ')">'
									+ '<img class="passPicB" style="width:' + passPicWidth + 'px;height:' + passPicHeight + 'px;" src="' + imgSrc + '"></div>';
							curH += '<div>�������ࣺ'
									+ decodeURIComponent(item['hpzlmc'])
									+ '&nbsp;&nbsp;���ƺ��룺'
									+ decodeURIComponent(item['hphm']) + '<br>';
							curH += '����������'
									+ decodeURIComponent(item['xzqhmc'])
									+ '<br>��·���ƣ�'
									+ decodeURIComponent(item['dldmmc'])
									+ '<br>';
							curH += '���ڼ�ƣ�'
									+ decodeURIComponent(item['kkbhmc'])
									+ '<br>��ʻ����'
									+ decodeURIComponent(item['fxlxmc'])
									+ '<br>';
							curH += '����ʱ�䣺'
									+ decodeURIComponent(item['gcsj'])
											.substring(0, 19)
									+ '<br></div></td>';
							curCount = i;
						});
		if ((curCount % 4) != 0 || curCount == 0) {
			//curHtml += '</div>';
			tmpIdx = 1;
			if (curCount == 0) {
				tmpIdx = 1;
			} else {
				tmpIdx = curCount % 4;
			}
			for (i = tmpIdx; i < 4; i++) {
				curH += '<td width="25%"></td>';
			}

			curHtml += '<tr>' + curH + '</tr>';
		}

		curHtml += '</table><div></div>'

		//curHtml += '</ul>';
		curHtml += '<table border="0" cellspacing="1" cellpadding="0" class="list"><tr><td width="100%" class="page">��'
				+ totalCount
				+ '��&nbsp;��'
				+ pageCount
				+ 'ҳ&nbsp;��'
				+ curPage
				+ 'ҳ &nbsp;';
		curHtml += '<label id="pageCMD" onclick="gotoPage(1)" style="cursor:hand">��ҳ</label>&nbsp;';
		if (curPage == 1) {
			curHtml += '<label id="pageCMD">��һҳ</label>&nbsp;';
		} else {
			curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage - 1)
					+ ')" style="cursor:hand">��һҳ</label>&nbsp;';
		}

		if (curPage >= pageCount) {
			curHtml += '<label id="pageCMD">��һҳ</label>&nbsp;';
		} else {
			curHtml += '<label id="pageCMD" onclick="gotoPage(' + (curPage + 1)
					+ ')" style="cursor:hand">��һҳ</label>&nbsp;';
		}

		curHtml += '<label id="pageCMD" onclick="gotoPage(' + pageCount
				+ ')" style="cursor:hand">ĩҳ</label>&nbsp;</td></tr></table>';

		$("#data").empty();
		$("#data").html(curHtml);
		$(document.doby).scrollTop($(document.doby).height());
	}

	function showDetail(gcxh, pageNum, listNum) {
		detailUrl = "<c:url value='tshpQuery.tfc?method=showLocalTshpDetail&gcxh='/>"
				+ gcxh + "&page=" + pageNum + "&idx=" + listNum;
		detailWin = openwin(detailUrl, "PassDetail");
	}

	function setQueryingHtml() {
		$("#data").empty();
		queryingHtml = '<br><br><img alt="" src="images/running.gif"><br>���ڲ�ѯ�������ĵȺ�...';
		$("#data").html(queryingHtml);
		$("#thdQueryStatDiv").empty();
		queryingStatHtml = '<img src="rmjs/zoom/ajax-loader.gif">���ڲ�ѯ�����Ժ�...';
		$("#thdQueryStatDiv").html(queryingStatHtml);
		//alert();
	}

	function setLoaddataHtml() {
		$("#data").empty();
		loadDataHtml = '<br><br><img alt="" src="images/running.gif"><br>�����������ݣ������ĵȺ�...';
		$("#data").html(loadDataHtml);
		//alert();
	}

	function emptyDataDiv() {
		$("#data").empty();
	}

	function gotoPage(idx) {
		closes();
		$("#pageCMD").attr("disabled", true);
		setQueryingHtml();
		curUrl = "<c:url value='tshpQuery.tfc?method=queryLocalTshp&goto=true&page='/>"
				+ idx + "&cxlx=" + queryModel;

		$.ajax({
			type : "GET",
			url : curUrl,
			cache : false,
			async : true,
			dataType : "json",
			success : function(data) {
				curPage = idx;
				localReturns(data);
			}
		});
		opens();
	}
	function doShowStyleChange() {

		tmpShowStyle = $("input[id=showStyle][checked=true]")
				.val();
		if (passData == null) {
			curShowStyle = tmpShowStyle;
			return;
		}
		if (curShowStyle != tmpShowStyle) {
			if (tmpShowStyle == 1) {
				//�б���ʾ
				getListHtml();
			} else if (tmpShowStyle == 2) {
				//����ͼ��ʾ
				getThumbHtml();
			}
		}
		curShowStyle = tmpShowStyle;
	}

	function doGateSelect() {
		//curArgs 
		var r = window
				.showModalDialog(
						"component.dev?method=fwdTollgateCheckBox",
						curArgs,
						"dialogWidth:800px;dialogHeight:600px;center:1;help:0;resizable:0;status:0;scroll:0;");
		if (typeof r != "undefined") {
			$("#kkbh").val(r[0]);
			$("#kkjc").val(r[1]);
		} else {
			$("#kkbh").val('');
			$("#kkjc").val('');
		}
	}


	function selectGateOnPGIS() {
		var sFeatures = "dialogHeight:600px;dialogWidth:800px;help:no;status:no ";
		var vReturnValue = window.showModalDialog(
				"pgis.dev?method=fwdTollgateDX&kklx=" + $("#kklx").val(),
				gateData, sFeatures);
		if (typeof vReturnValue != "undefined") {

			ckks = $("input[name=kkbh][checked=true]");
			for (i = 0; i < ckks.length; i++) {
				ckks[i].checked = false;
			}

			kks = vReturnValue.split(",");
			if (kks.length > 0) {
				for (i = 0; i < kks.length; i++) {
					$("#kkbh" + kks[i]).attr("checked", true);
				}
			}
		}
	}
</script>
<style>
.hpys0 {
	background-color: white;
	color: black;
	font-weight: bold;;
}

.hpys1 {
	background-color: yellow;
	color: black;
}

.hpys2 {
	background-color: blue;
	color: white;
}

.hpys3 {
	background-color: black;
	color: white;
}

.hpys4 {
	background-color: green;
	color: white;
}

.hpys9 {
	background-color: #AAAAAA;
	color: black;
}

.hpys {
	background-color: #AAAAAA;
	color: black;
}

.passThumbLi {
	float: left;
	padding: 2px;
	text-align: left;
	word-wrap: break-word;
}

.xx {
	width: 100%;
	background: #E0F1FB;
	font-size: 14px;
	color: #114877;
	font-weight: bold;
	padding: 3px;
}

.thumbTable {
	font-size: 12px;
	width: 100%;
	vertical-align: top;
	background-color: F7F7F7;
}
</style>
</head>
<body>
	<div id="tag_body" class="tag_body">
		<form name="myform" id="myform"
			action="<c:url value='tshpQuery.tfc?method=queryLocalTshp'/>"
			method="post">
			<input type="hidden" id="cxlx" name="cxlx" value="1">
			<table border="0" cellspacing="1" cellpadding="0" class="query"
				width="100%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="24%">
				<tr>
					<td class="head">������</td>
					<td class="body"><select id="glbm" name="glbm"
						style="width:100%" onchange="getTollgateAndRoad()">
							<option value=""></option>
							<c:forEach var="current" items="${gateDeptList}">
								<option value="<c:out value='${current.glbm}'/>">
									<c:out value='${current.bmmc}' />
								</option>
							</c:forEach>
					</select></td>
					<td class="head">��������</td>
					<td class="body"><h:codebox list='${kklxList}' id='kklx'
							haveNull='1' onChange="getTollgateAndRoad()" />
					</td>
					<td class="head">��·����</td>
					<td class="body"><h:codebox list='${ldlxList}' id='ldlx'
							haveNull='1' onChange="getTollgateAndRoad()" />
					</td>
				</tr>
				<tr>
					<td class="head">���ڼ��</td>
					<td class="body" colspan="3" valign="middle">
					<c:if test="${pgisdz == true}">
							<input type="button" value="PGISѡ��" onclick="selectGateOnPGIS()">
						</c:if> <input type="button" value="ȫ��ѡ��" onclick="selectAllGate(true)">
						<div id="kkbhDIV"
							style="width:100%;overflow:auto;height:100px;border:1px solid #F0F0F0;"></div>
					</td>
					<td class="head">��·����</td>
					<td class="body">
						<div id="dldmDIV" style="overflow:auto;width:100%;height:124px;"></div>
					</td>
				</tr>
				<tr>
					<td width="10%" class="head">��������</td>
					<td width="24%" class="body"><h:codebox list='${hpzlList}'
										id='hpzl' haveNull='1' />
					</td>
					<td width="10%" class="head">���ƺ���</td>
					<td width="56%" class="body" colspan="3"><input type="text"
									name="hphm" id="hphm" maxlength="15" style="width:120"
									onKeyUp="hphmToUp()" onblur="hphmToUpAll()" onFocus="setPos()">
									<img src="./rmjs/hphm/hphm.gif" onclick="showHphmDiv()"
									align="absmiddle" style="cursor:hand"> <font color="red">(���ƺ����ģ�����룬?��ʾ�����ַ���*��ʾ����ַ�)</font>
					</td>
				</tr>
				<tr>
					<td class="head"><span class="gotta">*</span>����ʱ��</td>
					<td class="body" colspan="3"><h:datebox id="kssj" name="kssj"
							showType="2" /> �� <h:datebox id="jssj" name="jssj" showType="2" />
					</td>
					<td class="head">���չ�ַ�ʽ</td>
					<td class="body" colspan="1"><input type="radio"
						id="showStyle" name="showStyle" value="1" checked
						onclick="doShowStyleChange()">�б�&nbsp; <input type="radio"
						id="showStyle" name="showStyle" value="2"
						onclick="doShowStyleChange()">����ͼ&nbsp;
						<div id="dddmm"></div>
					</td>
				</tr>

				<tr>
					<td class="submit" colspan="6"><input type="button"
						class="button_query" value="��ѯ" onclick="query_cmd()">
						<input type="button" class="button_close" value="�ر�"
						onclick="javascript:window.parent.close()">
					</td>
				</tr>
			</table>
		</form>
		<div id="hphm_div" style="position:absolute;display:none;"
			onmouseover="setIsHphmDivMouseOn(true)"
			onmouseout="setIsHphmDivMouseOn(false)"></div>
		<div class="s1" style="height:8px;"></div>
		<div id="thdDiv"
			style="display:none;width:100%;text-align:left;font-size:12px;vertical-align:middle;">
			<div id="thdQueryStatDiv"
				style="height:24px;line-height:24px;float:left;">
				<img src="rmjs/zoom/ajax-loader.gif">���ڲ�ѯ�����Ժ�...
			</div>
			<div id="thdQueryResultDiv"
				style="height:24px;line-height:24px;float:left;margin-left:10px;"></div>
			<input type="button" class="button_other" value="ˢ������"
				onclick="gotoPage(1)" style="float:left;">
		</div>
		<div class="s1" style="height:2px;"></div>
		<div id="data" style="text-align:center;font-size:12px;"></div>
		<input type="hidden" id="toEnd">
	</div>
</body>
</html>