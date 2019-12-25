<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>电子标签解绑</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    
</head>
<body class="rfid" style="background:#fff">
<div id="panel" style="height:100%">
    <div id="paneltitle">
		电子标签解绑
    </div>
    <div id="queryPanel" style="margin-top:10px;">
        <table  border="0" cellspacing="1" cellpadding="0" class="query">
            <col width="8%"/>
            <col width="14%"/>
            <col width="8%"/>
            <col width="14%"/>
            <col width="8%"/>
            <col width="14%"/>
            <col width="8%"/>
            <col width="16%"/>
            <col width="10%"/>
            <tr>
                <td class="command" colspan="8">
                    <input type="button" name="requestBtn" id="requestBtn" value="查询" class="button_default" tabindex="4" style="display:none"/>
                    <input type="button" name="readBtn" id="readBtn" value="读卡" class="button_default"/>
                    <input type="button" name="unbindBtn" id="unbindBtn" value="解绑" class="button_default"/>
                    <input type="button" name="debugBtn" id="debugBtn" value="调试" class="button_default"/>
                    
                </td>
            </tr>
        </table>
    </div>
    <div style="margin-top:20px;">
        <input type="hidden" name="bdfzjg" id="bdfzjg" value="${bdfzjg}"/>
        <input type="hidden" name="id" id="id"
               value="<c:out value='${bean.id}'/>">
        <input type="hidden" name="clxxid" id="clxxid"
               value="<c:out value='${bean.clxxid}'/>">
        <input type="hidden" name="syr" id="syr"
               value="<c:out value='${bean.syr}'/>">
        <input type="hidden" name="msg" id="msg" value='${msg}'>
        <input type="hidden" name="xh" id="xh" value="${task.xh}"/>
        <input type="hidden" name="rLsh" id="rLsh"/>
        <input type="hidden" name="latestVersion" id="latestVersion" value="${latestVersion}"/>
        <table border="0" cellspacing="1" cellpadding="0" class="query">
            <col width="10%">
            <col width="10%">
            <col width="20%">
            <col width="10%">
            <col width="20%">
            <col width="10%">
            <col width="20%">
            <tr>
                <td class="head">
                    电子标识信息
                </td>
                <td class="head">卡号</td>
                <td class="body">
							<span id="kh">
							</span>
                </td>
                <td class="head">TID</td>
                <td class="body" colspan="3">
							<span id="tid">
							</span>
                </td>
            </tr>
            <tr>
                <td class="head" rowspan="6">
                    车辆基本信息
                </td>
                <td class="head">
                    号牌种类
                </td>
                <td class="body">
							<span id="rHpzl" name="rHpzl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.hpzl}' />
                                </c:if>
							</span>
                    <span id="wHpzl" name="wHpzl" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    号牌号码
                </td>
                <td class="body">
							<span id="rHphm" name="rHphm" class="base-info" style="width:20%">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.hphm}' />
                                </c:if>
							</span>
                    <span id="wHphm" name="wHphm" style="display:none" class="card-info" style="width:80%"></span>
                </td>
                <td class="head">
                    使用性质
                </td>
                <td class="body">
							<span id="rSyxz" name="rSyxz" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.syxz}' />
                                </c:if>
							</span>
                    <span id="wSyxz" name="wSyxz" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    车辆类型
                </td>
                <td class="body">
							<span id="rCllx" name="rCllx" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.cllx}' />
                                </c:if>
							</span>
                    <span id="wCllx" name="wCllx" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    车身颜色
                </td>
                <td class="body">
							<span id="rCsys" name="rCsys" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.csys}' />
                                </c:if>
							</span>
                    <span id="wCsys" name="wCsys" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    排量
                </td>
                <td class="body">
							<span id="rPl" name="rPl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.pl}' />
                                </c:if>
							</span>
                    <span id="wPl" name="wPl" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    总质量
                </td>
                <td class="body">
							<span id="rZzl" name="rZzl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.zzl}' />
                                </c:if>
							</span>
                    <span id="wZzl" name="wZzl" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    核定载客
                </td>
                <td class="body">
							<span id="rHdzk" name="rHdzk" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.hdzk}' />
                                </c:if>
							</span>
                    <span id="wHdzk" name="wHdzk" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    功率
                </td>
                <td class="body">
							<span id="rGl" name="rGl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.gl}' />
                                </c:if>
							</span>
                    <span id="wGl" name="wGl" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    检验有效期止
                </td>
                <td class="body">
							<span id="rYxqz" name="rYxqz" class="base-info">
								<fmt:formatDate value='${bean.yxqz}' pattern="yyyy-MM-dd" />
							</span>
                    <span id="wYxqz" name="wYxqz" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    强制报废期止
                </td>
                <td class="body">
							<span id="rQzbfqz" name="rQzbfqz" class="base-info">
							<fmt:formatDate value='${bean.qzbfqz}' pattern="yyyy-MM-dd" />
								</span>
                    <span id="wQzbfqz" name="wQzbfqz" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    出厂日期
                </td>
                <td class="body">
							<span id="rCcrq" name="rCcrq" class="base-info">
							<fmt:formatDate value='${bean.ccrq}' pattern="yyyy-MM-dd" />
								</span>
                    <span id="wCcrq" name="wCcrq" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    所有人
                </td>
                <td class="body" colspan="5">
							<span id="rSyr" name="rSyr" class="base-info">
							<c:if test="${bean != null}">
                                <c:out value='${bean.syr}' />
                            </c:if>
							</span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    备注
                </td>
                <td class="body" colspan="5">
							<span id="rBz" name="rBz" class="base-info">
								<c:out value='${bean.bz}' />
							</span>
                </td>
            </tr>
            
        </table>
    </div>
    <c:if test="${task == null}">
        <div>
            <object classid="clsid:055318D9-DF91-437A-B3EF-D27A03806CEE" id="customizeConsole" style="display:none;width:200px;height:200px">
            </object>
        </div>
    </c:if>
    
    <div id = "resultMsg">
    </div>
    
    <div id = "resultMsg1">
    </div>
</div>
<c:if test="${task == null}">
    <div id="debugInfo" style="display:none"/>
</c:if>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script language="JavaScript" src="rmjs/eri.js" type="text/javascript"></script>
<script type="text/javascript" src="jQuery-webcam-master/jquery.webcam.js"></script>
<script language="JavaScript" type="text/javascript">
    <!--

    var pos = 0;
    var ctx = null;
    var cam = null;
    var width = 320, height = 240;
    var image = null;

    $(function() {
        Eri_Unbind_NS.init();
    });
    var Eri_Unbind_NS = {

        log: "",

        //baseUrl : '<c:url value="/be/image.rfid?method=show"/>',

        sendCommand : function(command, handler, errorHandler) {
            var result = Eri_Unbind_NS.customizeConsole.sendMessage(command);
            if (result == "0") {
                Eri_Unbind_NS.getResponse(1, handler, errorHandler);
                return true;
            } else {
                return false;
            }
        },

        getResponse: function(times, handler, errorHandler) {
            if (times > 50) {
                if (typeof errorHandler != 'undefined') {
                    errorHandler("NO_RESPONSE");
                }
            } else {
                var response = Eri_Unbind_NS.customizeConsole.getRecvMessage();
                if (response) {
                    var responseObj = $.parseJSON(response);
                    var result = responseObj["result"];
                    if (result == "01") {
                        alert("Reader operation not finished yet");
                    } else if (result == "02") {
                        alert("Delte AccessSpec failed");
                    } else if (result == "03") {
                        alert("AddAccessSpec msg failed")
                    } else if (result == "04") {
                        alert("EnableAccessSpec msg failed");
                    } else if (result == "05") {
                        alert("Start msg failed");
                    } else if (result == "06") {
                        alert("No report msg recvd (-3)");
                    } else if (result == "07") {
                        alert("Reader ended without any EndEvent msg recvd (再试一次可能会返回1-5或者10异常，则发卡设备可能死机 -1)");
                    } else if (result == "08") {
                        alert("Reader ended without any RW report msg recvd (-2)");
                    } else if (result == "09") {
                        alert("The tid string recvd in writeData is not equal with pre-query result");
                    } else if (result == "10") {
                        alert("Add commands to reader failed");
                    } else {
                        if (typeof handler != 'undefined') {
                            handler(responseObj);
                        }
                    }
                } else {
                    setTimeout("Eri_Unbind_NS.getResponse(" + (times + 1) + ", " + handler + "," + errorHandler + ")", 100);
                }
            }
        },



        init : function() {
            <c:if test="${task == null}">
            Eri_Unbind_NS.customizeConsole = document.getElementById("customizeConsole");
            try
            {
                var customizeActiveX = new ActiveXObject("Eri.Customize");
            }
            catch(e)
            {
                window.location.href = '/rmweb/system.frm?method=show-plugin';
            }
            Eri_Unbind_NS.fetchDeviceVersion();
            </c:if>

            $("#readBtn").click(function() {
                $(".card-info").text("");
                Eri_Unbind_NS.tid = null;
                Eri_Unbind_NS.kh = null;
                Eri_Unbind_NS.data = null;
                Eri_Unbind_NS.mode = 1;
                Eri_Unbind_NS.checkCard(1);
            });
            
            $("#unbindBtn").click(function() {
                $("#tid").text("");
                $("#kh").text("");
                $(".card-info").text("");
                Eri_Unbind_NS.mode = 2;
                Eri_Unbind_NS.checkCardStatus();
                /* Eri_Unbind_NS.tid = null;
                Eri_Unbind_NS.data = null;
                Eri_Unbind_NS.mode = 1;
                Eri_Unbind_NS.checkCard(1); */
                
            });

            $("#unbindBtn").focus();
            //$("#debugBtn").attr("disabled", "disabled");

            $("#debugBtn").click(function() {
                Tools_NS.showWarningDialog(Eri_Unbind_NS.log);
            }); 


            <c:forEach var="province" items="${provinces}">
            <c:if test="${province.dmz2 != null}">
            ERI_NS.PROVINCE[${province.dmz2}] = {dmz:'${province.dmz}', dmsm1:'${province.dmsm1}', dmsm2:'${province.dmsm2}'};
            </c:if>
            </c:forEach>
            <c:forEach var="licenceType" items="${licenceTypes}">
            <c:if test="${licenceType.dmz2 != null}">
            if ("${licenceType.dmz2}".length > 0 && ERI_NS.LICENCE_TYPE[${licenceType.dmz2}]) {
                ERI_NS.LICENCE_TYPE[${licenceType.dmz2}] = {
                    dmz: ERI_NS.LICENCE_TYPE[${licenceType.dmz2}]["dmz"] + "/" + '${licenceType.dmz}',
                    dmsm1 : ERI_NS.LICENCE_TYPE[${licenceType.dmz2}]["dmsm1"] + "/" + '${licenceType.dmsm1}'};
            } else {
                ERI_NS.LICENCE_TYPE[${licenceType.dmz2}] = {dmz:'${licenceType.dmz}', dmsm1 : '${licenceType.dmsm1}'};
            }
            </c:if>
            </c:forEach>
            <c:forEach var="vehicleColor" items="${carColors}">
            <c:if test="${vehicleColor.dmz2 != null}">
            ERI_NS.VEHICLE_COLOR[${vehicleColor.dmz2}] = {dmz:'${vehicleColor.dmz}', dmsm1 : '${vehicleColor.dmsm1}'};
            </c:if>
            </c:forEach>
            <c:forEach var="vehicleType" items="${carTypes}">
            <c:if test="${vehicleType.dmz2 != null}">
            if ("${vehicleType.dmz2}".length > 0 && ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}]) {
                ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}] = {
                    dmz: ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}]["dmz"] + "/" + '${vehicleType.dmz}',
                    dmsm1 : ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}]["dmsm1"] + "/" + '${vehicleType.dmsm1}'};
            } else {
                ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}] = {dmz:'${vehicleType.dmz}', dmsm1 : '${vehicleType.dmsm1}'};
            }
            </c:if>
            </c:forEach>
            <c:forEach var="usingPurpose" items="${usingPurposes}">
            <c:if test="${usingPurpose.dmz2 != null}">
            if ("${usingPurpose.dmz2}".length > 0 && ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}]) {
                ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}] = {
                    dmz: ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}]["dmz"] + "/" + '${usingPurpose.dmz}',
                    dmsm1 : ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}]["dmsm1"] + "/" + '${usingPurpose.dmsm1}'};
            } else {
                ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}] = {dmz:'${usingPurpose.dmz}', dmsm1 : '${usingPurpose.dmsm1}'};
            }
            </c:if>
            </c:forEach>

        },

        checkDeviceStatus : function() {
            Tools_NS.showLoading("正在配置签注设备，请稍等");
            Eri_Unbind_NS.sendCommand("1004:${cert}",
                    function(response) {
                        Eri_Unbind_NS.SAMCert = response["SAMCert"];
                        Eri_Unbind_NS.connected = response["ConnectStatus"] == "00";
                        if (response["ConnectStatus"] == "00") {
                            Tools_NS.closeLoading();
                            setTimeout(function () {
                                $("#lsh").focus();
                            }, 100);
                        } else {
                            Tools_NS.showWarningDialog("配置设备异常，请确认设备串口是否已连接", function() {
                                Eri_Unbind_NS.checkDeviceStatus();
                            });
                        }
                    }, function(error) {
                        Tools_NS.showWarningDialog("配置设备异常(" + error + ")", function() {
                            Eri_Unbind_NS.checkDeviceStatus();
                        });
                    });
        },

        fetchDeviceVersion : function() {
            Tools_NS.showLoading("正在连接签注设备，请稍等");
            Eri_Unbind_NS.sendCommand("1005", function(response) {
                if (response["MainVersion"] < $("#latestVersion").val()) {
                    window.location.href = '/rmweb/system.frm?method=show-plugin';
                } else {
                    Eri_Unbind_NS.checkDeviceStatus();
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Tools_NS.showWarningDialog("未启动签注程序，请启动后点击“确定”重试", function() {
                        Eri_Unbind_NS.fetchDeviceVersion();
                    });
                }
            });
        },
        
        checkCardStatus: function() {
            if (!Eri_Unbind_NS.tid) {
	            Eri_Unbind_NS.query();
	            $.unblockUI();
            }else{
            	$("#debugInfo").text("");
                $.get("/rmweb/be/customize-task.rfid?method=check-unBindcard-status",
                    {
                        tid : Eri_Unbind_NS.tid,
                        certStr: Eri_Unbind_NS.SAMCert,
                        _t: Date.parse(new Date())
                    }, function(data) {
                        if (data && data["resultId"] == "00") {
                            //alert("查询卡状态正常");
    						if(data["info"] && data["info"]["frame"] && data["info"]["sign"]){
    	                        Eri_Unbind_NS.data = data["info"]["frame"] + data["info"]["sign"];
    	                        /* $('#resultMsg1').text("frame:"+ JSON.stringify(data["info"]["frame"]) 
    	                        		+ "</br>sign:" + JSON.stringify(data["info"]["sign"])); */
    	                        //alert("data:"+JSON.stringify(Eri_Unbind_NS.data));
    	                        Eri_Unbind_NS.writeCard();
    						}else{
                                Tools_NS.showWarningDialog("请求数据不正常", function() {
                                    Eri_Unbind_NS.clearTask();
                                });
    						}
                        } else {
                            Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                Eri_Unbind_NS.clearTask();
                            });
                        }
                    }, "json"); 
            }
            
        },
        
        query: function() {
            if (!Eri_Unbind_NS.SAMCert) {
                Tools_NS.showWarningDialog("请确保读写器已正确连接");
                return;
            }
            
            Tools_NS.showLoading("正在请求数据，请稍等...");
            //$("#customizeBtn").attr("disabled", "disabled");
            //$("#uploadImg").attr("src", "");
            $(".card-info").text("");
            Eri_Unbind_NS.tid = null;
            Eri_Unbind_NS.kh = null;
            Eri_Unbind_NS.data = null;
            //Eri_Unbind_NS.mode = 2;
            Eri_Unbind_NS.checkCard(1);
        },
      	//model: 0-仅盘点，1-盘点+读上半区
        checkCard: function(mode) {
            $("#tid").text("");
            $("#kh").text("");
            Eri_Unbind_NS.changeCardStatus(0, "盘点中...");
            //alert("1001盘点");
            Eri_Unbind_NS.sendCommand("1001:" + (mode ? mode : "0") , function(response) {
				//$('#resultMsg').text("1001:"+JSON.stringify(response));
                if (response["SelectSpecResult"] == "00") {
                    Eri_Unbind_NS.tid = response["Tid"];
                    $("#tid").text(response["Tid"]);
                    if (response["SelectSpecResultData"]) {
                        var data = ERI_NS.parse(response["SelectSpecResultData"]);
                        $("#kh").text(data["kh"]);
                    }
                    Eri_Unbind_NS.changeCardStatus(1, "盘点成功");

                    //要读卡，必须先盘点
                    Eri_Unbind_NS.readCard();
                    
                    //Eri_Unbind_NS.doAfterChecked();
                } else if (response["SelectSpecResult"] == "02") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Eri_Unbind_NS.changeCardStatus(2, "盘点失败");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点失败',
                        message : '盘点失败，请确认已正确放置标识后，点“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Eri_Unbind_NS.query();
                        }
                    });
                } else if (response["SelectSpecResult"] == "04") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Eri_Unbind_NS.changeCardStatus(2, "盘点成功，数据获取失败");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点失败',
                        message : '盘点成功，数据获取失败，点击“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Eri_Unbind_NS.query();
                        }
                    });
                } else {
                    $("#tid").text("");
                    $("#kh").text("");
                    Eri_Unbind_NS.changeCardStatus(2, "盘点异常");
                    Eri_Unbind_NS.changeCardStatus(2, "盘点成功，数据获取失败");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点异常',
                        message : '盘点异常，点击“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Eri_Unbind_NS.query();
                        }
                    });
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Eri_Unbind_NS.changeCardStatus(2, "无响应,请确认是否已正确放置标识");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点失败',
                        message : '盘点失败，请确认已正确放置标识后，点“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Eri_Unbind_NS.query();
                        }
                    });
                } else {
                    Eri_Unbind_NS.errorHandleAfterChecked(error);
                }
            });
        },

        readCard: function() {
            Eri_Unbind_NS.changeCardStatus(0, "读取中...");
            Eri_Unbind_NS.sendCommand("1003", function (response) {
            	//$("#resultMsg").text(JSON.stringify(response));
            	//$('#resultMsg1').text("read:"+JSON.stringify(response));

            	//alert(response["HbReadSpecResult"]);
            	if (response["HbReadSpecResult"] == "00") {
                    Eri_Unbind_NS.changeCardStatus(1, "读取成功");
                    //$("#resultMsg").text(response["HbReadSpecResultData"]);
                    
                    var cardData = ERI_NS.parse(response["HbReadSpecResultData"]);
                    var tid = response["Tid"];
                    
                    $("#tid").css("color", "green").text("(" + tid + ")");

                	//$('#resultMsg1').text("read:"+JSON.stringify(cardData));
                	
                	/* if(cardData["kh"] && !cardData["hphm"]){//如果为空卡，提示不能解绑
                		Tools_NS.showWarningDialog("此卡无绑定车辆，不能解绑", function() {});
                	}else{ */
                        Eri_Unbind_NS.setCardData(cardData); 
                        Eri_Unbind_NS.doAfterReaded();
                	/* } */
                    
                } /* else if(response["HbReadSpecResult"] == "04"){
                	Tools_NS.showWarningDialog("此卡未个性化过或数据不正确", function() {});
                }  */else {
                    Eri_Unbind_NS.changeCardStatus(2, "读取失败");
                }
            }, function (error) {
                Eri_Unbind_NS.changeCardStatus(2, "读取异常(" + error + ")");
            });
        },

        setCardData : function(cardData) {
            Eri_Unbind_NS.clearCardData();
            Eri_Unbind_NS.kh = cardData["kh"]?cardData["kh"]:"";
            $("#kh").css("color", "green").text(cardData["kh"]?"(" + cardData["kh"] + ")":"");
            
            $("#wSyxz").show().css("color", "green").text((cardData["syxz"]&&cardData["syxz"]["dmsm1"])?"(" + cardData["syxz"]["dmsm1"] + ")":"");
            $("#wHpzl").show().css("color", "green").text((cardData["hpzl"]&&cardData["hpzl"]["dmsm1"])?"(" + cardData["hpzl"]["dmsm1"] + ")":"");
            $("#wHphm").show().css("color", "green").text(cardData["hphm"]?"(" + cardData["hphm"] + ")":"");
            $("#wCllx").show().css("color", "green").text((cardData["cllx"]&&cardData["cllx"]["dmsm1"])?"(" + cardData["cllx"]["dmsm1"] + ")":"");
            $("#wZzl").show().css("color", "green").text(cardData["zzl"] ? "(" + cardData["zzl"] + ")" : "");
            $("#wHdzk").show().css("color", "green").text(cardData["hdzk"] ? "(" + cardData["hdzk"] + ")" : "");
            $("#wPl").show().css("color", "green").text(cardData["pl"] ? "(" + cardData["pl"] + ")" : "");
            $("#wGl").show().css("color", "green").text(cardData["gl"] ? "(" + cardData["gl"] + ")" : "");
            $("#wCcrq").show().css("color", "green").text(cardData["ccrq"]?"(" + cardData["ccrq"] + ")":"");
            $("#wYxqz").show().css("color", "green").text(cardData["yxqz"]?"(" + cardData["yxqz"] + ")":"");
            $("#wQzbfqz").show().css("color", "green").text(cardData["qzbfqz"]?"(" + cardData["qzbfqz"] + ")":"");
            $("#wCsys").show().css("color", "green").text((cardData["csys"]&&cardData["csys"]["dmsm1"])?"(" + cardData["csys"]["dmsm1"] + ")":"");
        },

        clearCardData : function() {
            Eri_Unbind_NS.kh = null;
            $("#kh").empty();
            $("#wSyxz").empty().hide();
            $("#wHpzl").empty().hide();
            $("#wHphm").empty().hide();
            $("#wCllx").empty().hide();
            $("#wZzl").empty().hide();
            $("#wHdzk").empty().hide();
            $("#wPl").empty().hide();
            $("#wGl").empty().hide();
            $("#wCcrq").empty().hide();
            $("#wYxqz").empty().hide();
            $("#wQzbfqz").empty().hide();
            $("#wCsys").empty().hide();
        },

        
        
        writeCard: function() {
            Eri_Unbind_NS.log = Eri_Unbind_NS.log + "s3:" + new Date().getTime() + "|";
            if (Eri_Unbind_NS.data) {
            	
                Tools_NS.showLoading("正在进行签注，请稍等...");
                $("#debugInfo").text("正在签注...");
                
                Eri_Unbind_NS.sendCommand("1002:" + Eri_Unbind_NS.data, function (response) {
                    Eri_Unbind_NS.log = Eri_Unbind_NS.log + "s4:" + new Date().getTime() + "|";
                    var result = 0;
                    var reason = "";
                    

                    //alert("1002："+response["HbPrivateWriteSpecResult"]);
                    
                    if (response["HbPrivateWriteSpecResult"] == "00") {
                        if (response["HbReadSpecResult"] == "00") {
                            var checkData = ERI_NS.parse(response["HbReadSpecResultData"]);
                            if (Eri_Unbind_NS.checkContent(checkData)) {
                                result = 1;
                            } else {
                                reason = $("#debugInfo").text();
                            }
                        } else {
                            $("#debugInfo").text("读取数据失败");
                            reason = "读取数据失败";
                        }
                    } else if (response["HbPrivateWriteSpecResult"] == "01") {
                        $("#debugInfo").text("签注失败，配置证书链失败");
                        reason = "签注失败，配置证书链失败";
                    } else if (response["HbPrivateWriteSpecResult"] == "02") {
                        $("#debugInfo").text("签注失败（" + response["HbPrivateWriteSpecResultDescription"] + "）");
                        reason = "签注失败（" + response["HbPrivateWriteSpecResultDescription"] + "）";
                    } else if (response["HbPrivateWriteSpecResult"] == "03") {
                        $("#debugInfo").text("签注失败，数据TID与卡不一致");
                        reason = "签注失败，数据TID与卡不一致";
                    } else if (response["Tid"] != Eri_Unbind_NS.tid) {
                        $("#debugInfo").text("请求的数据与当前要签注的卡不匹配");
                        reason = "签注失败，数据TID与卡不匹配";
                    } else {
                        $("#debugInfo").text("请求的数据与当前要签注的卡不匹配");
                        reason = "签注结果未知";
                    }
                    if(result == 1){
                        Eri_Unbind_NS.uploadWriteResult(result, reason);
                    }
                }, function (error) {
                    Tools_NS.showWarningDialog(error, function() {});
                }); 
            } else {
                Tools_NS.showWarningDialog("未获取解绑数据，请重新请求数据", function() {
                    //$("#customizeBtn").attr("disabled", "disabled");
                });
            }
        },

        checkContent : function(cardData) {
        	//alert(cardData["kh"]+"==="+Eri_Unbind_NS.kh);
        	if (cardData["kh"] != Eri_Unbind_NS.kh) {
        		//alert("内容校验错误，卡号不正确");
                $("#debugInfo").text("内容校验错误，卡号不正确");
                return false;
            }
        	return true;
        },
        
        uploadWriteResult: function(result, reason) {
            $("#debugInfo").text("上传签注结果...");
            Eri_Unbind_NS.log = Eri_Unbind_NS.log + "s5:" + new Date().getTime() + "|";
            $.get("/rmweb/be/eri.rfid?method=unbind-result",
                    {tid : Eri_Unbind_NS.tid, result: result, sbyy: encodeURI(reason), _t: Date.parse(new Date())},
                    function(data) {
                        Eri_Unbind_NS.log = Eri_Unbind_NS.log + "s6:" + new Date().getTime() + "|";
                        if (data && data["resultId"] == "00") {
                            if (result == 1) {
                                $("#debugInfo").text("解绑成功");
                                $("#customizeBtn").attr("disabled", "disabled");
                                Tools_NS.showConfirmDialog({
                                    id : 'successDialog',
                                    title : '成功 ',
                                    message : '<img src="/rmweb/frmimage/right.gif"/><span style="font-size:18px;font-weight:bold;margin-left:20px">解绑成功，是否继续进行下一条</span>',
                                    width: 400,
                                    cancellable: false,
                                    onCancel: 'void(0)',
                                    onConfirm: function() {
                                        Eri_Unbind_NS.clearTask();
                                        $.unblockUI();
                                    }
                                });
                            } else {
                                $("#debugInfo").text(reason);
                                Tools_NS.showWarningDialog(reason);
                            }
                        } else {
                            Tools_NS.showConfirmDialog({
                                id : 'uploadResultDialog',
                                title : '提示',
                                message : '上传解绑结果失败，是否重试',
                                width: 400,
                                onCancle: 'void()',
                                onConfirm: function() {
                                    $.unblockUI();
                                    Eri_Unbind_NS.uploadWriteResult(result, reason);
                                }
                            });
                        }
                    }, "json");
        },

        /**
         *
         * @param text
         * @param status 0-进行中，1-成功，2-失败
         */
        changeCardStatus : function(status, text) {
            $("#debugInfo").text(text);
        },

        doAfterReaded : function() {
        	//alert("doAfterReaded:"+Eri_Unbind_NS.mode);
            if (Eri_Unbind_NS.mode == 1) {
                //读卡
                //Eri_Unbind_NS.readCard();
            }
            if (Eri_Unbind_NS.mode == 2) {
                //写卡
                Eri_Unbind_NS.checkCardStatus();
            }
        },

        errorHandleAfterChecked : function(checkResponse) {
            $("#debugInfo").text(checkResponse);
        },

        unfreeze: function() {
            $("#requestBtn").enable();
            $("#customizeBtn").enable();
            $("#closeBtn").enable();
        },

        clearTask: function() {
            $(".card-info").text("");
            $(".base-info").text("");
            $("#kh").text("");
            $("#tid").text("");
            $("#debugInfo").text("");
            $("#uploadImg").attr("src", "");
            if ($("#ywlx").val() == 5) {
                var lsh = $("#rLsh").val();
                $("#lsh").val(lsh.substr(0, 7) + ERI_NS.pad(parseInt(lsh.substr(7), 10) + 1, 6));
            } else {
                $("#lsh").val("");
            }
            $("#rLsh").val("");
            $("#syr").val("");
            $("#clxxid").val("");
            $("#id").val("");
            $("#xh").val("");
            $("#hphm").val("");

            $("#unbindBtn").focus();
        }

    }

    -->
</script>
</body>
</html>
