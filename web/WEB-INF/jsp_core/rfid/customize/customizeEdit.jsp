<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>���ӱ�ǩ���Ի�</title>
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
        ���ӱ�ǩǩע
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
            <c:if test="${task ==null}">
                <tr>
                    <td class="head">
                        ҵ������
                    </td>
                    <td class="body">
                        <input type="hidden" id="ywlx" name="ywlx" value="${ywlx}"/>
                        <c:forEach var="type" items="${custTypes}">
                            <c:if test="${type.type == ywlx}">
                                <c:out value="${type.desc}" />
                            </c:if>
                        </c:forEach>
                    </td>
                    <td class="head">
                        ��ˮ��
                    </td>
                    <td class="body">
                        <div class="input-wrap">
                            <input type="text" id="lsh" name="lsh" value="${task.lsh}" tabindex="1"/>
                        </div>
                    </td>
                    <td class="head">
                        ��������
                    </td>
                    <td class="body">
                        <select id="hpzl" name="hpzl" tabindex="2">
                            <option value="">
                                --��ѡ���������--
                            </option>
                            <c:forEach var="licenceType" items="${licenceTypes}">
                                <option value="${licenceType.dmz}">
                                    <c:out value="${licenceType.dmsm1}" />
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">
                        ���ƺ���
                    </td>
                    <td class="body">
                        <div style="position:relative;float:left;width:20%" class="input-wrap">
                            <input type="text" name="fzjg" id="fzjg"
                                   value='<c:out value="${localFzjg}" />' style="cursor:pointer"
                                   readonly="readonly" maxlength="15">
                        </div>
                        <div style="position:relative;float:right;width:70%" class="input-wrap">
                            <input type="text" name="hphm" id="hphm"  maxlength="15" tabindex="3">
                        </div>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td class="command" colspan="8">
                    <c:if test="${task == null}">
                        <input type="button" name="requestBtn" id="requestBtn" value="��ѯ" class="button_default" tabindex="4"/>
                        <input type="button" name="readBtn" id="readBtn" value="����" class="button_default" style="display:none"/>
                        <input type="button" disabled="disabled" name="customizeBtn" id="customizeBtn" value="ǩע" class="button_default" tabindex="5"/>
                    </c:if>
                    <c:if test="${task != null && (task.zt == 1 || task.zt == 0)}">
                        <input type="button" name="cancelBtn" id="cancelBtn" value="ȡ��" class="button_default"
                               tabindex="7"/>
                    </c:if>
                    <input type="button" id="closeBtn" name="closeBtn" onclick="javascript:window.close();" value="�ر�" class="button_default">
                    <input type="button" id="debugBtn" name="debugBtn" value="����" class="button_default"/>
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
                    ���ӱ�ʶ��Ϣ
                </td>
                <td class="head">����</td>
                <td class="body">
							<span id="kh">
								<c:if test="${eri != null}">
                                    <c:out value="${eri.kh}"/>
                                </c:if>
							</span>
                </td>
                <td class="head">TID</td>
                <td class="body" colspan="3">
							<span id="tid">
								<c:if test="${eri != null}">
                                    <c:out value="${eri.tid}"/>
                                </c:if>
							</span>
                </td>
            </tr>
            <tr>
                <td class="head" rowspan="6">
                    ����������Ϣ
                </td>
                <td class="head">
                    ��������
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
                    ���ƺ���
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
                    ʹ������
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
                    ��������
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
                    ������ɫ
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
                    ����
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
                    ������
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
                    �˶��ؿ�
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
                    ����
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
                    ������Ч��ֹ
                </td>
                <td class="body">
							<span id="rYxqz" name="rYxqz" class="base-info">
								<fmt:formatDate value='${bean.yxqz}' pattern="yyyy-MM-dd" />
							</span>
                    <span id="wYxqz" name="wYxqz" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    ǿ�Ʊ�����ֹ
                </td>
                <td class="body">
							<span id="rQzbfqz" name="rQzbfqz" class="base-info">
							<fmt:formatDate value='${bean.qzbfqz}' pattern="yyyy-MM-dd" />
								</span>
                    <span id="wQzbfqz" name="wQzbfqz" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    ��������
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
                    ������
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
                    ��ע
                </td>
                <td class="body" colspan="5">
							<span id="rBz" name="rBz" class="base-info">
								<c:out value='${bean.bz}' />
							</span>
                </td>
            </tr>
            <c:if test="${!empty task && task !=null}">
                <tr>
                    <td class="head" rowspan="3">
                        �������Ի���Ϣ
                    </td>
                    <td class="head">
                        ������
                    </td>
                    <td class="body">
                        <c:out value='${task.sqr}' />
                    </td>
                    <td class="head">
                        ��ϵ�绰
                    </td>
                    <td class="body">
                        <c:out value='${task.lxdh}' />
                    </td>
                    <td class="head">
                        ������
                    </td>
                    <td class="body">
                        <c:out value='${task.jbr}' />
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        ��������
                    </td>
                    <td class="body">
                        <fmt:formatDate value='${task.sqrq}' pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td class="head">
                        �������
                    </td>
                    <td class="body">
                        <fmt:formatDate value='${task.wcrq}' pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td class="head">
                        ҵ������
                    </td>
                    <td class="body">
                        <c:forEach var="types" items="${custTypes}">
                            <c:if test="${types.type == task.rwlx}">
                                <c:out value="${types.desc}" />
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        ״̬
                    </td>
                    <td class="body">
                        <c:forEach var="status" items="${custStatus}">
                            <c:if test="${status.status == task.zt}">
                                <c:out value="${status.desc}" />
                            </c:if>
                        </c:forEach>
                    </td>
                    <td class="body" colspan="4"></td>
                </tr>
            </c:if>
        </table>
    </div>
    <c:if test="${task == null}">
        <div>
            <object classid="clsid:055318D9-DF91-437A-B3EF-D27A03806CEE" id="customizeConsole" style="display:none;width:200px;height:200px">
            </object>
        </div>
    </c:if>
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
        Edit_Customize_Task_NS.init();
    });
    var Edit_Customize_Task_NS = {

        log: "",

        baseUrl : '<c:url value="/be/image.rfid?method=show"/>',

        sendCommand : function(command, handler, errorHandler) {
            var result = Edit_Customize_Task_NS.customizeConsole.sendMessage(command);
            if (result == "0") {
                Edit_Customize_Task_NS.getResponse(1, handler, errorHandler);
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
                var response = Edit_Customize_Task_NS.customizeConsole.getRecvMessage();
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
                    setTimeout("Edit_Customize_Task_NS.getResponse(" + (times + 1) + ", " + handler + "," + errorHandler + ")", 100);
                }
            }
        },



        init : function() {

            <c:if test="${task == null}">
            Edit_Customize_Task_NS.customizeConsole = document.getElementById("customizeConsole");
            try
            {
                var customizeActiveX = new ActiveXObject("Eri.Customize");
            }
            catch(e)
            {
                window.location.href = '/rmweb/system.frm?method=show-plugin';
            }
            Edit_Customize_Task_NS.fetchDeviceVersion();
            </c:if>

            if($("#msg").val()!=""){
                <c:if test="${bean==null}">
                alert($("#msg").val());
                return;
                </c:if>
                alert($("#msg").val());
            }
            <c:if test="${bean!=null}">
            $("#hpzl").val("<c:out value='${bean.hpzl}'/>");
            $("#hphm").val("${fn:substring(bean.hphm, 1, -1)}");
            $("#syr").val("<c:out value='${bean.syr}'/>");
            </c:if>

            $("#fzjg").dblclick(function() {
                if ($(".fzjg-select").length > 0) {
                    $(".fzjg-select").show();
                } else {
                    if (!Edit_Customize_Task_NS.bdfzjg) {
                        Edit_Customize_Task_NS.bdfzjg = $("#bdfzjg").val().split(",");
                    }
                    var fzjgSelector = "<div class='fzjg-select'><ul>";
                    var existsFzjg = {};
                    $(Edit_Customize_Task_NS.bdfzjg).each(function(index, element){
                        var fzjgProvince = element.substring(0, 1);
                        if (!existsFzjg[fzjgProvince]) {
                            fzjgSelector = fzjgSelector + '<li>' + fzjgProvince + '</li>';
                            existsFzjg[fzjgProvince] = true;
                        }
                    });
                    fzjgSelector += "</ul></div>";
                    $("#fzjg").after(fzjgSelector);
                }
            });
            $("body").on("click", ".fzjg-select li", function() {
                $("#fzjg").val($(this).text());
            });
            $(document).click(function () {
                $(".fzjg-select").hide();
            });

            $("#requestBtn").click(function() {
                Edit_Customize_Task_NS.query();
            });

            $("#customizeBtn").click(function() {
                Edit_Customize_Task_NS.log = "";
                Edit_Customize_Task_NS.fetchData();
            });

            $("#cancelBtn").click(function () {
                Edit_Customize_Task_NS.cancel();
            });


            $("#readBtn").click(function() {
                $(".card-info").text("");
                Edit_Customize_Task_NS.tid = null;
                Edit_Customize_Task_NS.data = null;
                Edit_Customize_Task_NS.mode = 1;
                Edit_Customize_Task_NS.checkCard(1);
            });

            $("#requestBtn").keydown(function(e){
                if (e.keyCode == 13) {
                    Edit_Customize_Task_NS.query();
                }
            });

            $("#customizeBtn").keydown(function(e) {
                if (e.keyCode == 13) {
                    Edit_Customize_Task_NS.fetchData();
                }
            });

            $("#debugBtn").click(function() {
                Tools_NS.showWarningDialog(Edit_Customize_Task_NS.log);
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
            Tools_NS.showLoading("��������ǩע�豸�����Ե�");
            Edit_Customize_Task_NS.sendCommand("1004:${cert}",
                    function(response) {
                        Edit_Customize_Task_NS.SAMCert = response["SAMCert"];
                        Edit_Customize_Task_NS.connected = response["ConnectStatus"] == "00";
                        if (response["ConnectStatus"] == "00") {
                            Tools_NS.closeLoading();
                            setTimeout(function () {
                                $("#lsh").focus();
                            }, 100);
                        } else {
                            Tools_NS.showWarningDialog("�����豸�쳣����ȷ���豸�����Ƿ�������", function() {
                                Edit_Customize_Task_NS.checkDeviceStatus();
                            });
                        }
                    }, function(error) {
                        Tools_NS.showWarningDialog("�����豸�쳣(" + error + ")", function() {
                            Edit_Customize_Task_NS.checkDeviceStatus();
                        });
                    });
        },

        fetchDeviceVersion : function() {
            Tools_NS.showLoading("��������ǩע�豸�����Ե�");
            Edit_Customize_Task_NS.sendCommand("1005", function(response) {
                if (response["MainVersion"] < $("#latestVersion").val()) {
                    window.location.href = '/rmweb/system.frm?method=show-plugin';
                } else {
                    Edit_Customize_Task_NS.checkDeviceStatus();
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Tools_NS.showWarningDialog("δ����ǩע����������������ȷ��������", function() {
                        Edit_Customize_Task_NS.fetchDeviceVersion();
                    });
                }
            });
        },

        cancel: function () {
            $.post("/rmweb/customize-task.frm?method=cancel", {xh: $("#xh").val()}, function (data) {
                if (data && data["resultId"] == "00") {
                    Tools_NS.showConfirmDialog({
                        id: 'Success',
                        title: 'ȡ���ɹ� ',
                        message: '��ҵ����ȡ��',
                        width: 400,
                        cancellable: false,
                        onConfirm: function () {
                            window.close();
                            window.opener.query_cmd();
                        }
                    })
                } else {
                    Tools_NS.showWarningDialog("ȡ��ʧ��:" + data["resultMsg"]);
                }
            }, "json");
        },

        customize : function() {
            if ($("#rLsh").val().length <= 0) {
                alert("���ȷ�������");
                return;
            }

            if (!Edit_Customize_Task_NS.SAMCert) {
                alert("��ȷ����д������ȷ����");
                return;
            }

            if (!Edit_Customize_Task_NS.tid) {
                alert("�����̵���ӱ�ʶ");
                return;
            }

            $("#debugInfo").text("����ǩע����");
            $.get("/rmweb/be/eri.rfid?method=customize",
                    {
                        xh:$("#xh").val(),
                        tid:Edit_Customize_Task_NS.tid,
                        cert:Edit_Customize_Task_NS.SAMCert,
                        _t: Date.parse(new Date())
                    }, function(data) {
                        if (data && data["resultId"] == "00") {
                            $("#debugInfo").text("����ǩע���ݳɹ�");
                            Edit_Customize_Task_NS.data = data["info"]["frame"] + data["info"]["sign"];
                            Edit_Customize_Task_NS.writeCard();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id : 'resetTaskDialog',
                                    title : '��ʾ ',
                                    message : '�������ڽ����е������Ƿ��������񲢼���',
                                    width: 400,
                                    onCancel: 'void()',
                                    onConfirm: function() {
                                        Edit_Customize_Task_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else if (data["errorCode"] == "02") {
                                $("#debugInfo").text("���ӱ�ʶδ��ʼ��");
                                Edit_Customize_Task_NS.unfreeze();
                            } else if (data["errorCode"]) {
                                $("#debugInfo").text(data["resultMsg"]);
                                Edit_Customize_Task_NS.unfreeze();
                            }
                        } else {
                            $("#debugInfo").text("��ȡ����ʧ��");
                            Edit_Customize_Task_NS.unfreeze();
                        }
                    }, "json");
        },

        query: function() {
            if (!Edit_Customize_Task_NS.SAMCert) {
                Tools_NS.showWarningDialog("��ȷ����д������ȷ����");
                return;
            }
            if ($("#lsh").val().length <= 0 && ($("#hphm").val().length <= 0 || $("#hpzl").val().length <= 0 || $("#fzjg").val().length <= 0)) {
                Tools_NS.showWarningDialog("��������ˮ�Ż���������ƺ���ͺ�������");
                return;
            }
            if ($("#lsh").val().length > 0 && $("#lsh").val().length != 13) {
                Tools_NS.showWarningDialog("��������ȷ����ˮ�ţ���ˮ��Ϊ13λ�ַ�");
                return;
            }
            Tools_NS.showLoading("�����������ݣ����Ե�...");
            $("#customizeBtn").attr("disabled", "disabled");
            $("#uploadImg").attr("src", "");
            $(".card-info").text("");
            Edit_Customize_Task_NS.tid = null;
            Edit_Customize_Task_NS.data = null;
            Edit_Customize_Task_NS.mode = 2;
            Edit_Customize_Task_NS.checkCard(1);
        },

        checkCardStatus: function() {
            if (!Edit_Customize_Task_NS.tid) {
                Tools_NS.showConfirmDialog({
                    id : 'queryDialog',
                    title : '��ʾ ',
                    message : 'δ�̵㵽��ʶ������ȷ���ñ�ʶ������',
                    width: 400,
                    onCancle: 'void()',
                    onConfirm: function() {
                        Edit_Customize_Task_NS.query();
                        $.unblockUI();
                    }
                });
            }
            $("#debugInfo").text("");
            $.get("/rmweb/be/customize-task.rfid?method=check-card-status",
                    {
                        lsh:$("#lsh").val(),
                        hphm: encodeURI($("#fzjg").val() + $("#hphm").val()),
                        hpzl: $("#hpzl").val(),
                        fzjg: encodeURI($("#fzjg").val()),
                        tid : Edit_Customize_Task_NS.tid,
                        ywlx: $("#ywlx").val(),
                        _t: Date.parse(new Date())
                    }, function(data) {
                        if (data && data["resultId"] == "00") {
                            if ($("#ywlx").val() != 5) {
                                $("#rHphm").text(data["info"]["hphm"]);
                                $("#rSyr").text(data["info"]["syr"]);
                                $("#rHpzl").text(data["info"]["hpzl"]);
                                $("#rZzl").text(data["info"]["zzl"]);
                                $("#rCsys").text(data["info"]["csys"]);
                                $("#rCllx").text(data["info"]["cllx"]);
                                $("#rSyxz").text(data["info"]["syxz"]);
                                $("#rGl").text(data["info"]["gl"]);
                                $("#rPl").text(data["info"]["pl"]);
                                $("#rCcrq").text(data["info"]["ccrq"]);
                                $("#rQzbfqz").text(data["info"]["qzbfqz"]);
                                $("#rYxqz").text(data["info"]["yxqz"]);
                                $("#rHdzk").text(data["info"]["hdzk"]);
                                $("#rBxzzrq").text(data["info"]["bxzzrq"]);
                            }
                            $("#customizeBtn").enable();
                            $("#customizeBtn").focus();
                            $.unblockUI();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id : 'resetTaskDialog',
                                    title : '��ʾ ',
                                    message : '�������ڽ����е������Ƿ��������񲢼���',
                                    width: 400,
                                    onCancle: 'void()',
                                    onConfirm: function() {
                                        Edit_Customize_Task_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else {
                                Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                    Edit_Customize_Task_NS.clearTask();
                                });
                            }
                        } else if (data && data["resultId"] == "98") {
                            Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                reload();
                            });
                        } else {
                            Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                Edit_Customize_Task_NS.clearTask();
                            });
                        }
                    }, "json");
        },

        fetchData : function() {
            Edit_Customize_Task_NS.log = Edit_Customize_Task_NS.log + "s1:" + new Date().getTime() + "|";
            Tools_NS.showLoading("�����������ݣ����Ե�...");
            $("#debugInfo").text("");
            $("#customizeBtn").attr("disabled", "disabled");
            $.get("/rmweb/be/customize-task.rfid?method=fetch-data",
                    {
                        lsh:$("#lsh").val(),
                        xh:$("#xh").val(),
                        hphm: encodeURI($("#fzjg").val() + $("#hphm").val()),
                        hpzl: $("#hpzl").val(),
                        fzjg: encodeURI($("#fzjg").val()),
                        tid : Edit_Customize_Task_NS.tid,
                        cert:Edit_Customize_Task_NS.SAMCert,
                        ywlx: $("#ywlx").val(),
                        _t: Date.parse(new Date())
                    }, function(data) {
                        Edit_Customize_Task_NS.log = Edit_Customize_Task_NS.log + "s2:" + new Date().getTime() + "|";
                        if (data && data["resultId"] == "00") {
                            $("#rLsh").val(data["CustomizeTask"]["lsh"]);
                            $("#xh").val(data["CustomizeTask"]["xh"]);
                            Edit_Customize_Task_NS.data = data["info"]["frame"] + data["info"]["sign"];
                            Edit_Customize_Task_NS.writeCard();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id : 'resetTaskDialog',
                                    title : '��ʾ ',
                                    message : '�������ڽ����е������Ƿ��������񲢼���',
                                    width: 400,
                                    onCancle: 'void()',
                                    onConfirm: function() {
                                        Edit_Customize_Task_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else if (data["errorCode"] == "02") {
                                Tools_NS.showWarningDialog(data["resultMsg"], function() {});
                            } else {
                                Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                    $("#hphm").val("");
                                    $("#hpzl").val("");
                                    $("#lsh").val("");
                                    Edit_Customize_Task_NS.unfreeze();
                                });
                            }
                        } else if (data && data["resultId"] == "98") {
                            Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                reload();
                            });
                        } else {
                            Tools_NS.showWarningDialog(data["resultMsg"], function() {
                                $("#hphm").val("");
                                $("#hpzl").val("");
                                $("#lsh").val("");
                            });
                        }
                    }, "json");

        },

        readCard: function() {
            Edit_Customize_Task_NS.changeCardStatus(0, "��ȡ��...");
            Edit_Customize_Task_NS.sendCommand("1003", function (response) {
                if (response["HbReadSpecResult"] == "00") {
                    Edit_Customize_Task_NS.changeCardStatus(1, "��ȡ�ɹ�");
                    var cardData = ERI_NS.parse(response["HbReadSpecResultData"])
                    Edit_Customize_Task_NS.setCardData(cardData);
                } else {
                    Edit_Customize_Task_NS.changeCardStatus(2, "��ȡʧ��");
                }
            }, function (error) {
                Edit_Customize_Task_NS.changeCardStatus(2, "��ȡ�쳣(" + error + ")");
            });
        },

        setCardData : function(cardData) {
            Edit_Customize_Task_NS.clearCardData();
            $("#wSyxz").show().css("color", "green").text("(" + cardData["syxz"]["dmsm1"] + ")");
            $("#wHpzl").show().css("color", "green").text("(" + cardData["hpzl"]["dmsm1"] + ")");
            $("#wHphm").show().css("color", "green").text("(" + cardData["hphm"] + ")");
            $("#wCllx").show().css("color", "green").text("(" + cardData["cllx"]["dmsm1"] + ")");
            $("#wZzl").show().css("color", "green").text(cardData["zzl"] ? "(" + cardData["zzl"] + ")" : "");
            $("#wHdzk").show().css("color", "green").text(cardData["hdzk"] ? "(" + cardData["hdzk"] + ")" : "");
            $("#wPl").show().css("color", "green").text(cardData["pl"] ? "(" + cardData["pl"] + ")" : "");
            $("#wGl").show().css("color", "green").text(cardData["gl"] ? "(" + cardData["gl"] + ")" : "");
            $("#wCcrq").show().css("color", "green").text("(" + cardData["ccrq"] + ")");
            $("#wYxqz").show().css("color", "green").text("(" + cardData["yxqz"] + ")");
            $("#wQzbfqz").show().css("color", "green").text("(" + cardData["qzbfqz"] + ")");
            $("#wCsys").show().css("color", "green").text("(" + cardData["csys"]["dmsm1"] + ")");
        },

        clearCardData : function() {
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
            Edit_Customize_Task_NS.log = Edit_Customize_Task_NS.log + "s3:" + new Date().getTime() + "|";
            if (Edit_Customize_Task_NS.data) {
                Tools_NS.showLoading("���ڽ���ǩע�����Ե�...");
                $("#debugInfo").text("����ǩע...");
                Edit_Customize_Task_NS.sendCommand("1002:" + Edit_Customize_Task_NS.data, function (response) {
                    Edit_Customize_Task_NS.log = Edit_Customize_Task_NS.log + "s4:" + new Date().getTime() + "|";
                    var result = 0;
                    var reason = "";
                    if (response["HbPrivateWriteSpecResult"] == "00") {
                        if (response["HbReadSpecResult"] == "00") {
                            var checkData = ERI_NS.parse(response["HbReadSpecResultData"]);
                            if (Edit_Customize_Task_NS.checkContent(checkData)) {
                                result = 1;
                            } else {
                                reason = $("#debugInfo").text();
                            }
                        } else {
                            $("#debugInfo").text("��ȡ����ʧ��");
                            reason = "��ȡ����ʧ��";
                        }
                    } else if (response["HbPrivateWriteSpecResult"] == "01") {
                        $("#debugInfo").text("ǩעʧ�ܣ�����֤����ʧ��");
                        reason = "ǩעʧ�ܣ�����֤����ʧ��";
                    } else if (response["HbPrivateWriteSpecResult"] == "02") {
                        $("#debugInfo").text("ǩעʧ�ܣ�" + response["HbPrivateWriteSpecResultDescription"] + "��");
                        reason = "ǩעʧ�ܣ�" + response["HbPrivateWriteSpecResultDescription"] + "��";
                    } else if (response["HbPrivateWriteSpecResult"] == "03") {
                        $("#debugInfo").text("ǩעʧ�ܣ�����TID�뿨��һ��");
                        reason = "ǩעʧ�ܣ�����TID�뿨��һ��";
                    } else if (response["Tid"] != Edit_Customize_Task_NS.tid) {
                        $("#debugInfo").text("����������뵱ǰҪǩע�Ŀ���ƥ��");
                        reason = "ǩעʧ�ܣ�����TID�뿨��ƥ��";
                    } else {
                        $("#debugInfo").text("����������뵱ǰҪǩע�Ŀ���ƥ��");
                        reason = "ǩע���δ֪";
                    }
                    Edit_Customize_Task_NS.uploadWriteResult(result, reason);
                }, function (error) {
                    Tools_NS.showWarningDialog(error, function() {});
                });
            } else {
                Tools_NS.showWarningDialog("δ��ȡǩע���ݣ���������������", function() {
                    $("#customizeBtn").attr("disabled", "disabled");
                });
            }
        },

        checkContent : function(cardData) {
            if (cardData["kh"] != $("#kh").text().trim()) {
                $("#debugInfo").text("����У����󣬿��Ų���ȷ");
                return false;
            }
            if (cardData["hpzl"] && $("#rHpzl").text() &&
                    cardData["hpzl"]["dmsm1"].toUpperCase().indexOf($("#rHpzl").text().trim().toUpperCase()) < 0) {
                $("#debugInfo").text("����У����󣬺������಻��ȷ");
                return false;
            }
            if (cardData["hphm"] && $("#rHphm").text() &&
                    cardData["hphm"].toUpperCase() != $("#rHphm").text().trim().toUpperCase()) {
                $("#debugInfo").text("����У����󣬺��ƺ��벻��ȷ");
                return false;
            }
            if (cardData["syxz"] && $("#rSyxz").text() &&
                    cardData["syxz"]["dmsm1"].toUpperCase().indexOf($("#rSyxz").text().trim().toUpperCase()) < 0) {
                $("#debugInfo").text("����У�����ʹ�����ʲ���ȷ");
                return false;
            }
            if (cardData["cllx"] && $("#rCllx").text() &&
                    cardData["cllx"]["dmsm1"].toUpperCase().indexOf($("#rCllx").text().trim().toUpperCase()) < 0) {
                $("#debugInfo").text("����У����󣬳������Ͳ���ȷ");
                return false;
            }
            if (cardData["csys"] && $("#rCsys").text() &&
                    (cardData["csys"]["dmsm1"] + "ɫ") != $("#rCsys").text().trim()) {
                $("#debugInfo").text("����У����󣬳�����ɫ����ȷ");
                return false;
            }
            if (cardData["pl"] && $("#rPl").text() &&
                    parseFloat(cardData["pl"].trim().replace("L", "")) != parseFloat($("#rPl").text().trim().replace("L", ""))) {
                $("#debugInfo").text("����У�������������ȷ");
                return false;
            }
            if (cardData["zzl"] && $("#rZzl").text() &&
                    parseFloat(cardData["zzl"].trim().replace("��", "")) != parseFloat($("#rZzl").text().trim().replace("��", ""))) {
                $("#debugInfo").text("����У���������������ȷ");
                return false;
            }
            if (cardData["hdzk"] && $("#rHdzk").text() &&
                    cardData["hdzk"] != $("#rHdzk").text().trim()) {
                $("#debugInfo").text("����У����󣬺˶��ؿͲ���ȷ");
                return false;
            }
            if (cardData["gl"] && $("#rGl").text() &&
                    cardData["gl"] != $("#rGl").text().trim()) {
                if (parseInt($("#rGl").text().trim()) < 255 || parseInt(cardData["gl"]) != 255) {
                    $("#debugInfo").text("����У����󣬹��ʲ���ȷ");
                    return false;
                }
            }
            if (cardData["yxqz"] && $("#rYxqz").text() &&
                    $("#rYxqz").text().trim().indexOf(cardData["yxqz"]) != 0) {
                $("#debugInfo").text("����У����󣬼�����Ч�ڲ���ȷ");
                return false;
            }
            if (cardData["qzbfqz"] && $("#rQzbfqz").text() &&
                    $("#rQzbfqz").text().trim().indexOf(cardData["qzbfqz"]) != 0) {
                if ($("#rYxqz").text()) {
                    var yearOfYxqz = new Date($("#rYxqz").text().replace(/-/g, "/")).getFullYear();
                    var yearOfQzbfqz = new Date($("#rQzbfqz").text().replace(/-/g, "/")).getFullYear();
                    if (yearOfQzbfqz - yearOfYxqz < 31) {
                        $("#debugInfo").text("����У�����ǿ�Ʊ����ڲ���ȷ");
                        return false;
                    }						} else {
                    $("#debugInfo").text("����У�����ǿ�Ʊ����ڲ���ȷ");
                    return false;
                }
            }
            if (cardData["ccrq"] && $("#rCcrq").text() &&
                    $("#rCcrq").text().trim().indexOf(cardData["ccrq"]) != 0) {
                $("#debugInfo").text("����У����󣬳������ڲ���ȷ");
                return false;
            }
            return true;
        },

        uploadWriteResult: function(result, reason) {
            $("#debugInfo").text("�ϴ�ǩע���...");
            Edit_Customize_Task_NS.log = Edit_Customize_Task_NS.log + "s5:" + new Date().getTime() + "|";
            $.get("/rmweb/be/eri.rfid?method=customize-result",
                    {xh : $("#xh").val(), result: result, sbyy: encodeURI(reason), _t: Date.parse(new Date())},
                    function(data) {
                        Edit_Customize_Task_NS.log = Edit_Customize_Task_NS.log + "s6:" + new Date().getTime() + "|";
                        if (data && data["resultId"] == "00") {
                            if (result == 1) {
                                $("#debugInfo").text("ǩע�ɹ�");
                                $("#customizeBtn").attr("disabled", "disabled");
                                Tools_NS.showConfirmDialog({
                                    id : 'successDialog',
                                    title : '�ɹ� ',
                                    message : '<img src="/rmweb/frmimage/right.gif"/><span style="font-size:18px;font-weight:bold;margin-left:20px">ǩע�ɹ����Ƿ����������һ��</span>',
                                    width: 400,
                                    cancellable: true,
                                    onCancel: 'void(0)',
                                    onConfirm: function() {
                                        Edit_Customize_Task_NS.clearTask();
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
                                title : '��ʾ',
                                message : '�ϴ�ǩע���ʧ�ܣ��Ƿ�����',
                                width: 400,
                                onCancle: 'void()',
                                onConfirm: function() {
                                    $.unblockUI();
                                    Edit_Customize_Task_NS.uploadWriteResult(result, reason);
                                }
                            });
                        }
                    }, "json");
        },

        resetTask : function() {
            $.get("/rmweb/be/eri.rfid?method=customize-result",
                    {xh : $("#xh").val(), result: "0", reason: "��������", tid: $("#tid").text(),
                        _t: Date.parse(new Date())},
                    function(data) {
                        if (data && data["resultId"] == "00") {
                            $("#debugInfo").text("����������");
                            Edit_Customize_Task_NS.query();
                        }
                    }, "json");
        },

        //model: 0-���̵㣬1-�̵�+���ϰ���
        checkCard: function(mode) {
            $("#tid").text("");
            $("#kh").text("");
            Edit_Customize_Task_NS.changeCardStatus(0, "�̵���...");
            Edit_Customize_Task_NS.sendCommand("1001:" + (mode ? mode : "0") , function(response) {
                if (response["SelectSpecResult"] == "00") {
                    Edit_Customize_Task_NS.tid = response["Tid"];
                    $("#tid").text(response["Tid"]);
                    if (response["SelectSpecResultData"]) {
                        var data = ERI_NS.parse(response["SelectSpecResultData"]);
                        $("#kh").text(data["kh"]);
                    }
                    Edit_Customize_Task_NS.changeCardStatus(1, "�̵�ɹ�");
                    Edit_Customize_Task_NS.doAfterChecked();
                } else if (response["SelectSpecResult"] == "02") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Edit_Customize_Task_NS.changeCardStatus(2, "�̵�ʧ��");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵�ʧ��',
                        message : '�̵�ʧ�ܣ���ȷ������ȷ���ñ�ʶ�󣬵㡰ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Edit_Customize_Task_NS.query();
                        }
                    });
                } else if (response["SelectSpecResult"] == "04") {
                    $("#tid").text("");
                    $("#kh").text("");
                    Edit_Customize_Task_NS.changeCardStatus(2, "�̵�ɹ������ݻ�ȡʧ��");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵�ʧ��',
                        message : '�̵�ɹ������ݻ�ȡʧ�ܣ������ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Edit_Customize_Task_NS.query();
                        }
                    });
                } else {
                    $("#tid").text("");
                    $("#kh").text("");
                    Edit_Customize_Task_NS.changeCardStatus(2, "�̵��쳣");
                    Edit_Customize_Task_NS.changeCardStatus(2, "�̵�ɹ������ݻ�ȡʧ��");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵��쳣',
                        message : '�̵��쳣�������ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Edit_Customize_Task_NS.query();
                        }
                    });
                }
            }, function(error) {
                if (error == "NO_RESPONSE") {
                    Edit_Customize_Task_NS.changeCardStatus(2, "����Ӧ,��ȷ���Ƿ�����ȷ���ñ�ʶ");
                    Tools_NS.showConfirmDialog({
                        id : 'noCardDialog',
                        title : '�̵�ʧ��',
                        message : '�̵�ʧ�ܣ���ȷ������ȷ���ñ�ʶ�󣬵㡰ȷ�������ԡ�',
                        width: 400,
                        onCancle: 'void()',
                        onConfirm: function() {
                            Edit_Customize_Task_NS.query();
                        }
                    });
                } else {
                    Edit_Customize_Task_NS.errorHandleAfterChecked(error);
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
            if (Edit_Customize_Task_NS.mode == 1) {
                //����
                Edit_Customize_Task_NS.readCard();
            }
            if (Edit_Customize_Task_NS.mode == 2) {
                //д��
                Edit_Customize_Task_NS.checkCardStatus();
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
        }

    }

    -->
</script>
</body>
</html>
