<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>电子标签签注（修改口令）</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <style>
        .fzjg-select {
            position: absolute;
            top: 19px;
            left: 0;
        }

        .fzjg-select ul {
            width: 160px;
        }

        .fzjg-select li {
            width: 40px;
            background: #E5F7FD;
            text-align: center;
            line-height: 24px;
            cursor: pointer;
        }

        .fzjg-select li:hover {
            background: coral;
        }
    </style>
</head>
<body class="rfid" style="background:#fff">
    <div id="panel" style="height:100%">
        <div id="paneltitle">
            电子标签签注（修改口令）
        </div>
        <div id="queryPanel" style="margin-top:10px;">
            <input type="hidden" name="latestVersion" id="latestVersion" value="${latestVersion}"/>
            <table  border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="15%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="40%"/>
                <tr>
                    <td class="head">
                        原分散因子：
                    </td>
                    <td class="body">
                        <input type="text" id="sourceFactor" name="sourceFactor"/>
                    </td>
                    <td class="head">
                        新分散因子:
                    </td>
                    <td class="body">
                        <input type="text" id="destFactor" name="destFactor"/>
                    </td>
                    <td class="command">
                        <input type="button" name="customizeBtn" id="customizeBtn" value="签注" class="button_default" tabindex="4"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <object classid="clsid:055318D9-DF91-437A-B3EF-D27A03806CEE" id="customizeConsole" style="display:none;width:200px;height:200px">
    </object>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script language="JavaScript" src="rmjs/eri.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
    <!--

    $(function() {
        Modify_Password_NS.init();
    });
    var Modify_Password_NS = {

        sendCommand : function(command, handler, errorHandler) {
            var result = Modify_Password_NS.customizeConsole.sendMessage(command);
            if (result == "0") {
                Modify_Password_NS.getResponse(1, handler, errorHandler);
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
                var response = Modify_Password_NS.customizeConsole.getRecvMessage();
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
                    setTimeout("Modify_Password_NS.getResponse(" + (times + 1) + ", " + handler + "," + errorHandler + ")", 100);
                }
            }
        },

        init : function() {

            Modify_Password_NS.customizeConsole = document.getElementById("customizeConsole");
            try
            {
                Modify_Password_NS.customizeConsole = new ActiveXObject("Eri.Customize");
            }
            catch(e)
            {
                window.location.href = '/rmweb/system.frm?method=show-plugin';
            }
            Modify_Password_NS.fetchDeviceVersion();


            $("#customizeBtn").click(function() {
                Modify_Password_NS.checkCard(1);
            });
        },

        checkDeviceStatus : function() {
            Tools_NS.showLoading("正在配置签注设备，请稍等");
            Modify_Password_NS.sendCommand("1004:${cert}",
                    function(response) {
                        Modify_Password_NS.SAMCert = response["SAMCert"];
                        Modify_Password_NS.connected = response["ConnectStatus"] == "00";
                        if (response["ConnectStatus"] == "00") {
                            Tools_NS.closeLoading();
                            setTimeout(function () {
                                $("#lsh").focus();
                            }, 100);
                        } else {
                            Tools_NS.showWarningDialog("配置设备异常，请确认设备串口是否已连接", function() {
                                Modify_Password_NS.checkDeviceStatus();
                            });
                        }
                    }, function(error) {
                        Tools_NS.showWarningDialog("配置设备异常(" + error + ")", function() {
                            Modify_Password_NS.checkDeviceStatus();
                        });
                    });
        },

        fetchDeviceVersion : function() {
            Tools_NS.showLoading("正在连接签注设备，请稍等");
            Modify_Password_NS.sendCommand("1005", function(response) {
                if (response["MainVersion"] < $("#latestVersion").val()) {
                    window.location.href = '/rmweb/system.frm?method=show-plugin';
                } else {
                    Modify_Password_NS.checkDeviceStatus();
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Tools_NS.showWarningDialog("未启动签注程序，请启动后点击“确定”重试", function() {
                        Modify_Password_NS.fetchDeviceVersion();
                    });
                }
            });
        },

        customize : function() {

            if (!Modify_Password_NS.SAMCert) {
                alert("请确保读写器已正确连接");
                return;
            }

            if (!Modify_Password_NS.tid) {
                alert("请先盘点电子标识");
                return;
            }

            if (!$("#sourceFactor").val() || !$("#destFactor").val()) {
                alert("请输入原分散因子和新分散因子");
                return;
            }

            $.get("/rmweb/be/customize-task.rfid?method=modify-password",
                    {
                        tid:Modify_Password_NS.tid,
                        cert:Modify_Password_NS.SAMCert,
                        content: Modify_Password_NS.content,
                        sourceFactor: $("#sourceFactor").val(),
                        destFactor: $("#destFactor").val(),
                        _t: Date.parse(new Date())
                    }, function(data) {
                        if (data && data["resultId"] == "00") {
                            $("#debugInfo").text("请求签注数据成功");
                            Modify_Password_NS.data = data["info"]["frame"] + data["info"]["sign"];
                            Modify_Password_NS.writeCard();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id : 'resetTaskDialog',
                                    title : '提示 ',
                                    message : '存在正在进行中的任务，是否重置任务并继续',
                                    width: 400,
                                    onCancle: 'void()',
                                    onConfirm: function() {
                                        Modify_Password_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else if (data["errorCode"] == "02") {
                                $("#debugInfo").text("电子标识未初始化");
                                Modify_Password_NS.unfreeze();
                            } else if (data["errorCode"]) {
                                $("#debugInfo").text(data["resultMsg"]);
                                Modify_Password_NS.unfreeze();
                            }
                        } else {
                            $("#debugInfo").text("获取数据失败");
                            Modify_Password_NS.unfreeze();
                        }
                    }, "json");
        },

        uploadWriteResult: function(result, reason) {
            $("#debugInfo").text("上传签注结果...");
            $.get("/rmweb/be/eri.rfid?method=modify-password-result",
                    {tid: Modify_Password_NS.tid, result: result, sbyy: encodeURI(reason), _t: Date.parse(new Date())},
                    function(data) {
                        if (data && data["resultId"] == "00") {
                            if (result == 1) {
                                Tools_NS.showConfirmDialog({
                                    id : 'successDialog',
                                    title : '成功 ',
                                    message : '<img src="/rmweb/frmimage/right.gif"/><span style="font-size:18px;font-weight:bold;margin-left:20px">签注成功，是否继续进行下一条</span>',
                                    width: 400,
                                    cancellable: true,
                                    onCancel: 'void(0)',
                                    onConfirm: function() {
                                        Modify_Password_NS.tid = null;
                                        Modify_Password_NS.data = null;
                                        Modify_Password_NS.content = null;
                                        Modify_Password_NS.checkCard(1);
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
                                message : '上传签注结果失败，是否重试',
                                width: 400,
                                onCancle: 'void()',
                                onConfirm: function() {
                                    $.unblockUI();
                                    Modify_Password_NS.uploadWriteResult(result, reason);
                                }
                            });
                        }
                    }, "json");
        },

        readCard: function() {
            Modify_Password_NS.changeCardStatus(0, "读取中...");
            Modify_Password_NS.sendCommand("1003", function (response) {
                if (response["HbReadSpecResult"] == "00") {
                    Modify_Password_NS.changeCardStatus(1, "读取成功");
                    Modify_Password_NS.content = response["HbReadSpecResultData"];
                    Modify_Password_NS.customize();
                } else {
                    Modify_Password_NS.customize();
                }
            }, function (error) {
                Modify_Password_NS.changeCardStatus(2, "读取异常(" + error + ")");
            });
        },

        writeCard: function() {
            if (Modify_Password_NS.data) {
                Tools_NS.showLoading("正在进行签注，请稍等...");
                $("#debugInfo").text("正在签注...");
                Modify_Password_NS.sendCommand("1002:" + Modify_Password_NS.data, function (response) {
                    var result = 0;
                    var reason = "";
                    if (response["HbPrivateWriteSpecResult"] == "00") {
                        result = 1;
                    } else if (response["HbPrivateWriteSpecResult"] == "01") {
                        $("#debugInfo").text("签注失败，配置证书链失败");
                        reason = "签注失败，配置证书链失败";
                    } else if (response["HbPrivateWriteSpecResult"] == "02") {
                        $("#debugInfo").text("签注失败（" + response["HbPrivateWriteSpecResultDescription"] + "）");
                        reason = "签注失败（" + response["HbPrivateWriteSpecResultDescription"] + "）";
                    } else if (response["HbPrivateWriteSpecResult"] == "03") {
                        $("#debugInfo").text("签注失败，数据TID与卡不一致");
                        reason = "签注失败，数据TID与卡不一致";
                    } else if (response["Tid"] != Modify_Password_NS.tid) {
                        $("#debugInfo").text("请求的数据与当前要签注的卡不匹配");
                        reason = "签注失败，数据TID与卡不匹配";
                    }
                    Modify_Password_NS.uploadWriteResult(result, reason);
                }, function (error) {
                    Tools_NS.showWarningDialog(error, function() {});
                });
            } else {
                Tools_NS.showWarningDialog("未获取签注数据，请重新请求数据", function() {
                    $("#customizeBtn").attr("disabled", "disabled");
                });
            }
        },

        //model: 0-仅盘点，1-盘点+读上半区
        checkCard: function(mode) {
            Modify_Password_NS.tid = null;
            Modify_Password_NS.data = null;
            Modify_Password_NS.content = null;
            Tools_NS.showLoading("正在请求数据，请稍等...");
            Modify_Password_NS.sendCommand("1001:" + (mode ? mode : "0") , function(response) {
                if (response["SelectSpecResult"] == "00") {
                    Modify_Password_NS.tid = response["Tid"];
                    $("#tid").text(response["Tid"]);
                    if (response["SelectSpecResultData"]) {
                        Modify_Password_NS.content = response["SelectSpecResultData"];
                    }
                    Modify_Password_NS.changeCardStatus(1, "盘点成功");
                    Modify_Password_NS.doAfterChecked();
                } else if (response["SelectSpecResult"] == "02") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Modify_Password_NS.changeCardStatus(2, "盘点失败");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点失败',
                        message : '盘点失败，请确认已正确放置标识后，点“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                } else if (response["SelectSpecResult"] == "04") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Modify_Password_NS.changeCardStatus(2, "盘点成功，数据获取失败");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点失败',
                        message : '盘点成功，数据获取失败，点击“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                } else {
                    $("#tid").text("");
                    $("#kh").text("");
                    Modify_Password_NS.changeCardStatus(2, "盘点异常");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点异常',
                        message : '盘点异常，点击“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Modify_Password_NS.changeCardStatus(2, "无响应,请确认是否已正确放置标识");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '盘点失败',
                        message : '盘点失败，请确认已正确放置标识后，点“确定”重试。',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                } else {
                    Modify_Password_NS.errorHandleAfterChecked(error);
                }
            });
        },

        /**
         *
         * @param text
         * @param status 0-进行中，1-成功，2-失败
         */
        changeCardStatus : function(status, text) {
            $("#debugInfo").text(text);
        },

        doAfterChecked : function() {
            Modify_Password_NS.readCard()
        },

        errorHandleAfterChecked : function(checkResponse) {
            $("#debugInfo").text(checkResponse);
        },

        unfreeze: function() {
            $("#customizeBtn").enable();
            $("#closeBtn").enable();
        }

    }
    -->
</script>
</body>
</html>
