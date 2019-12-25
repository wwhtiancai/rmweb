	function getTollgate() {
		var curUrl = "component.dev?method=getTollgate"
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
		//$("#kkbh").addOption("", "È«²¿");
		closes();
		$.getJSON(curUrl,function(data) {
			if (data['code'] == 1) {
				curHTML = '<ul>';
				gateData = data['msg'];
				$.each(
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
		var curUrl = "component.dev?method=getTollgateRoad"
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
		$.getJSON(curUrl,function(data) {
			curH = '<ul>';
			if (data['code'] == 1) {
				$.each(
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
		var glbmVal = $("#myform #glbm").val();
		var kklxVal = $("#myform #kklx").val();
		var ldlxVal = $("#myform #ldlx").val();

		if (glbmVal == ""&&kklxVal == "" &&ldlxVal == "") {
			$("#myform #kkbhDIV").html("");
			$("#myform #dldmDIV").html("");
		}else{
			getTollgateRoad();
			getTollgate();
		}		
		
	}

function getVehicleDetail(hpzl,hphm) {
	var chpzl = encodeURI(encodeURI(hpzl));
	var chphm = encodeURI(encodeURI(hphm));
	if (chpzl.length != 2 && chphm.length != 7) {
		alert("ºÅÅÆ´íÎó£¡");
		return;
	}

	if ($("#veh").css("display") == 'none') {
		var cyzm = window
				.showModalDialog(
						"vehquery.tfc?method=showValidCode",
						"",
						"dialogWidth:350px;dialogHeight:150px;center:1;help:0;resizable:0;status:0;scroll:0;");
		if (typeof cyzm == "undefined") {
			return false;
		}
		$("#loadingVeh").html(
				'<img alt="" src="images/running.gif"><br>ÕýÔÚ²éÑ¯£¬ÇëÉÔºò...');
		$("#veh").slideDown();
		$("#loadingVeh").css("display", "block");
		$("#vehDataTable").css("display", "none");
		//curUrl = "baseTfcPass.tfc?method=getVehicle&page="
		//		+ curPage + "&idx=" + curIdx + "&yzm=" + cyzm + "&keyFix="+keyFix;
		curUrl = "vehquery.tfc?method=getVehicle&hpzl="
				+ chpzl + "&hphm=" + chphm + "&yzm=" + cyzm ;		
		
		$.ajax({
			type : "GET",
			url : curUrl,
			cache : false,
			async : true,
			dataType : "json",
			success : function(data) {
				if (data['code'] == 1) {
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
					
					$("#vehYxqzTD").html(decodeURI(msg['yxqz']));
					$("#vehQzbfqzTD").html(decodeURI(msg['qzbfqz']));
					$("#vehClytTD").html(decodeURI(msg['clytmc']));
				} else {
					$("#vehClppTD").html('');
					$("#vehCsysTD").html('');
					$("#vehCllxTD").html('');
					$("#vehClztTD").html('');
					$("#vehCcdjrqTD").html('');
					$("#vehSyrTD").html('');
					$("#vehSyxzTD").html('');
					$("#vehClxhTD").html('');
					$("#vehYxqzTD").html('');
					$("#vehQzbfqzTD").html('');
					$("#vehClytTD").html('');
					
					$("#loadingVeh").css("display", "none");
					$("#vehDataTable").css("display", "none");
					$("#veh").slideUp();
					alert(decodeURIComponent(data['msg']));
					//$("#loadingVeh").html();
				}
			}
		});
	} else {
		$("#veh").slideUp();
	}
}