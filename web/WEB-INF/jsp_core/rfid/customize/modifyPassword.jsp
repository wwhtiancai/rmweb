<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>���ӱ�ǩǩע���޸Ŀ��</title>
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
            ���ӱ�ǩǩע���޸Ŀ��
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
                        ԭ��ɢ���ӣ�
                    </td>
                    <td class="body">
                        <input type="text" id="sourceFactor" name="sourceFactor"/>
                    </td>
                    <td class="head">
                        �·�ɢ����:
                    </td>
                    <td class="body">
                        <input type="text" id="destFactor" name="destFactor"/>
                    </td>
                    <td class="command">
                        <input type="button" name="customizeBtn" id="customizeBtn" value="ǩע" class="button_default" tabindex="4"/>
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
                        alert("Reader ended without any EndEvent msg recvd (����һ�ο��ܻ᷵��1-5����10�쳣���򷢿��豸�������� -1)");
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
            Tools_NS.showLoading("��������ǩע�豸�����Ե�");
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
                            Tools_NS.showWarningDialog("�����豸�쳣����ȷ���豸�����Ƿ�������", function() {
                                Modify_Password_NS.checkDeviceStatus();
                            });
                        }
                    }, function(error) {
                        Tools_NS.showWarningDialog("�����豸�쳣(" + error + ")", function() {
                            Modify_Password_NS.checkDeviceStatus();
                        });
                    });
        },

        fetchDeviceVersion : function() {
            Tools_NS.showLoading("��������ǩע�豸�����Ե�");
            Modify_Password_NS.sendCommand("1005", function(response) {
                if (response["MainVersion"] < $("#latestVersion").val()) {
                    window.location.href = '/rmweb/system.frm?method=show-plugin';
                } else {
                    Modify_Password_NS.checkDeviceStatus();
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Tools_NS.showWarningDialog("δ����ǩע����������������ȷ��������", function() {
                        Modify_Password_NS.fetchDeviceVersion();
                    });
                }
            });
        },

        customize : function() {

            if (!Modify_Password_NS.SAMCert) {
                alert("��ȷ����д������ȷ����");
                return;
            }

            if (!Modify_Password_NS.tid) {
                alert("�����̵���ӱ�ʶ");
                return;
            }

            if (!$("#sourceFactor").val() || !$("#destFactor").val()) {
                alert("������ԭ��ɢ���Ӻ��·�ɢ����");
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
                            $("#debugInfo").text("����ǩע���ݳɹ�");
                            Modify_Password_NS.data = data["info"]["frame"] + data["info"]["sign"];
                            Modify_Password_NS.writeCard();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id : 'resetTaskDialog',
                                    title : '��ʾ ',
                                    message : '�������ڽ����е������Ƿ��������񲢼���',
                                    width: 400,
                                    onCancle: 'void()',
                                    onConfirm: function() {
                                        Modify_Password_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else if (data["errorCode"] == "02") {
                                $("#debugInfo").text("���ӱ�ʶδ��ʼ��");
                                Modify_Password_NS.unfreeze();
                            } else if (data["errorCode"]) {
                                $("#debugInfo").text(data["resultMsg"]);
                                Modify_Password_NS.unfreeze();
                            }
                        } else {
                            $("#debugInfo").text("��ȡ����ʧ��");
                            Modify_Password_NS.unfreeze();
                        }
                    }, "json");
        },

        uploadWriteResult: function(result, reason) {
            $("#debugInfo").text("�ϴ�ǩע���...");
            $.get("/rmweb/be/eri.rfid?method=modify-password-result",
                    {tid: Modify_Password_NS.tid, result: result, sbyy: encodeURI(reason), _t: Date.parse(new Date())},
                    function(data) {
                        if (data && data["resultId"] == "00") {
                            if (result == 1) {
                                Tools_NS.showConfirmDialog({
                                    id : 'successDialog',
                                    title : '�ɹ� ',
                                    message : '<img src="/rmweb/frmimage/right.gif"/><span style="font-size:18px;font-weight:bold;margin-left:20px">ǩע�ɹ����Ƿ����������һ��</span>',
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
                                title : '��ʾ',
                                message : '�ϴ�ǩע���ʧ�ܣ��Ƿ�����',
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
            Modify_Password_NS.changeCardStatus(0, "��ȡ��...");
            Modify_Password_NS.sendCommand("1003", function (response) {
                if (response["HbReadSpecResult"] == "00") {
                    Modify_Password_NS.changeCardStatus(1, "��ȡ�ɹ�");
                    Modify_Password_NS.content = response["HbReadSpecResultData"];
                    Modify_Password_NS.customize();
                } else {
                    Modify_Password_NS.customize();
                }
            }, function (error) {
                Modify_Password_NS.changeCardStatus(2, "��ȡ�쳣(" + error + ")");
            });
        },

        writeCard: function() {
            if (Modify_Password_NS.data) {
                Tools_NS.showLoading("���ڽ���ǩע�����Ե�...");
                $("#debugInfo").text("����ǩע...");
                Modify_Password_NS.sendCommand("1002:" + Modify_Password_NS.data, function (response) {
                    var result = 0;
                    var reason = "";
                    if (response["HbPrivateWriteSpecResult"] == "00") {
                        result = 1;
                    } else if (response["HbPrivateWriteSpecResult"] == "01") {
                        $("#debugInfo").text("ǩעʧ�ܣ�����֤����ʧ��");
                        reason = "ǩעʧ�ܣ�����֤����ʧ��";
                    } else if (response["HbPrivateWriteSpecResult"] == "02") {
                        $("#debugInfo").text("ǩעʧ�ܣ�" + response["HbPrivateWriteSpecResultDescription"] + "��");
                        reason = "ǩעʧ�ܣ�" + response["HbPrivateWriteSpecResultDescription"] + "��";
                    } else if (response["HbPrivateWriteSpecResult"] == "03") {
                        $("#debugInfo").text("ǩעʧ�ܣ�����TID�뿨��һ��");
                        reason = "ǩעʧ�ܣ�����TID�뿨��һ��";
                    } else if (response["Tid"] != Modify_Password_NS.tid) {
                        $("#debugInfo").text("����������뵱ǰҪǩע�Ŀ���ƥ��");
                        reason = "ǩעʧ�ܣ�����TID�뿨��ƥ��";
                    }
                    Modify_Password_NS.uploadWriteResult(result, reason);
                }, function (error) {
                    Tools_NS.showWarningDialog(error, function() {});
                });
            } else {
                Tools_NS.showWarningDialog("δ��ȡǩע���ݣ���������������", function() {
                    $("#customizeBtn").attr("disabled", "disabled");
                });
            }
        },

        //model: 0-���̵㣬1-�̵�+���ϰ���
        checkCard: function(mode) {
            Modify_Password_NS.tid = null;
            Modify_Password_NS.data = null;
            Modify_Password_NS.content = null;
            Tools_NS.showLoading("�����������ݣ����Ե�...");
            Modify_Password_NS.sendCommand("1001:" + (mode ? mode : "0") , function(response) {
                if (response["SelectSpecResult"] == "00") {
                    Modify_Password_NS.tid = response["Tid"];
                    $("#tid").text(response["Tid"]);
                    if (response["SelectSpecResultData"]) {
                        Modify_Password_NS.content = response["SelectSpecResultData"];
                    }
                    Modify_Password_NS.changeCardStatus(1, "�̵�ɹ�");
                    Modify_Password_NS.doAfterChecked();
                } else if (response["SelectSpecResult"] == "02") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Modify_Password_NS.changeCardStatus(2, "�̵�ʧ��");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵�ʧ��',
                        message : '�̵�ʧ�ܣ���ȷ������ȷ���ñ�ʶ�󣬵㡰ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                } else if (response["SelectSpecResult"] == "04") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Modify_Password_NS.changeCardStatus(2, "�̵�ɹ������ݻ�ȡʧ��");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵�ʧ��',
                        message : '�̵�ɹ������ݻ�ȡʧ�ܣ������ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                } else {
                    $("#tid").text("");
                    $("#kh").text("");
                    Modify_Password_NS.changeCardStatus(2, "�̵��쳣");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵��쳣',
                        message : '�̵��쳣�������ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Modify_Password_NS.query();
                        }
                    });
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Modify_Password_NS.changeCardStatus(2, "����Ӧ,��ȷ���Ƿ�����ȷ���ñ�ʶ");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵�ʧ��',
                        message : '�̵�ʧ�ܣ���ȷ������ȷ���ñ�ʶ�󣬵㡰ȷ�������ԡ�',
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
         * @param status 0-�����У�1-�ɹ���2-ʧ��
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
