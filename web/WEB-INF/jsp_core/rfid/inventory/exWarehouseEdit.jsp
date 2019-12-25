<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312" %>
<html>
<head>
    <title>���ⵥά��</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" rel="stylesheet" type="text/css">
    <style>
        .khdiv {
            margin-top: 10px;
        }

        .sjsl input {
            width: 50px;
            text-align: center;
        }
    </style>

</head>
<body class="rfid">
<div id="panel" style="display:none">
    <div id="paneltitle">���ⵥά��</div>
    <form action="" method="post" name="myform" id="myform">
        <c:if test="${bean==null}">
            <input type="hidden" name="detailList" id="detailList">
        </c:if>
        <div id="block">
            <div id="blocktitle">���ⵥ��Ϣ</div>
            <div id="blockmargin">8</div>
            <table border="0" cellspacing="1" cellpadding="0" class="detail">
                <col width="10%">
                <col width="30%">
                <col width="10%">
                <col width="30%">
                <tr>
                    <td class="head">���ⵥ��</td>
                    <td class="body">
                        <c:if test="${bean!=null}">
                            <c:out value='${bean.ckdh}'/>
                            <input type="hidden" name="ckdh" id="ckdh">
                        </c:if>
                        <c:if test="${bean==null}">
                            <!-- <input type="text" name="ckdh" id="ckdh"> -->
                        </c:if>
                    </td>
                    <td class="head">��������</td>
                    <td class="body">
                        <c:if test="${bean!=null}">
                            <c:out value='${bean.ssbm}'/>
                        </c:if>
                        <c:if test="${bean==null}">
                            <select id="ssbm" name="ssbm" style="width: 80%;">
                                <c:forEach var="current" items="${xjbmList}">
                                    <option value="${current.glbm}">
                                        <c:out value="${current.bmmc}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td class="head">������</td>
                    <td class="body">
                        <input type="text" name="sqdh" id="sqdh">
                    </td>
                    <td class="head">��������</td>
                    <td class="body">
                        <input type="text" name="cksl" id="cksl" style="width:60%">
                    </td>
                </tr>
                <tr id="sqdxx">
                    <td class="head">��������</td>
                    <td class="body" id="sqsl"></td>
                    <td class="head">�ѳ�������</td>
                    <td class="body" id="ycksl"></td>
                </tr>
                <tr>
                    <td class="head">��Ʒ���</td>
                    <td class="body">
                        <select id="cplb" name="cplb" onChange="Ex_Warehouse_Edit_NS.changeCplb()">
                            <option value="">--��ѡ��--</option>
                            <c:forEach var="productCategory" items="${productCategories}">
                                <option value="${productCategory.cplb}">
                                    <c:out value="${productCategory.lbmc}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">��Ʒ����</td>
                    <td class="body">
                        <select id="cpdm" name="cpdm">
                            <option value="">--����ѡ���Ʒ���--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="head">��ע</td>
                    <td class="body" colspan="3">
                        <c:if test="${bean!=null}">
                            <c:out value='${bean.bz}'/>
                        </c:if>
                        <c:if test="${bean==null}">
                            <textarea name="bz" id="bz" rows="4"></textarea>
                        </c:if>
                    </td>
                </tr>

                <c:if test="${list==null}">
                    <tr>
                        <td class="head" style="padding:10px 0;">��/�к�:</td>
                        <td class="body">
                            <input type="text" name="bzhm" id="bzhm" style="width:75%"/>
                            <input type="text" style="display:none;"/>
                            <input type="button" name="addInbutton" id="addInbutton" value="���" class="button_save"/>
                        </td>
                        <td class="body" colspan="2" style="text-align:right;">
                            <input type="button" name="addByKHbutton" id="addByKHbutton" onclick="Ex_Warehouse_Edit_NS.addByKH()" value="������"
                                   class="button_save">
                            <input type="button" name="savebutton" id="savebutton" onclick="Ex_Warehouse_Edit_NS.save()" value="�ύ"
                                   class="button_save">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="ȡ��"
                                   class="button_close">
                        </td>
                    </tr>
                </c:if>

                <c:if test="${list!=null}">
                    <tr>
                        <td class="body" colspan="4" style="text-align:right;">
                            <input type="button" name="examinebutton" id="examineBtn" onclick="Ex_Warehouse_Edit_NS.examine(2)" value="ͨ��"
                                   class="button_save">
                            <input type="button" name="unexamineBtn" id="unexamineBtn" onclick="Ex_Warehouse_Edit_NS.examine(3)" value="��ͨ��"
                                   class="button_save">
                            <input type="button" name="exportBtn" id="exportBtn" onclick="Ex_Warehouse_Edit_NS.exportExcel()" value="����"
                                   class="button_print">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="ȡ��"
                                   class="button_close">
                        </td>
                    </tr>
                </c:if>
            </table>

            <div id="productList">
                <c:if test="${list==null}">
                    <table border="0" cellspacing="1" cellpadding="0" class="list" id="list">
                        <col width="7%">
                        <col width="8%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="8%">
                        <col width="15%">
                        <tr class="head">
                            <td>���</td>
                            <td>
                                ��λ
                            </td>
                            <td>
                                ��װ����
                            </td>
                            <td>
                                ��ʼ����
                            </td>
                            <td>
                                ��ֹ����
                            </td>
                            <td>
                                ʵ������
                            </td>
                            <td>
                                ����
                            </td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${list!=null}">
                    <table border="0" cellspacing="1" cellpadding="0" class="detail" style="margin-top:30px;">
                        <tr>
                            <td class="head" width="100%"
                                style="height:30px;text-align:left;font-weight:bold;padding-left:10px;font-size:14px;">
                                �����嵥
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="1" cellpadding="0" class="list">
                        <col width="7%">
                        <col width="8%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="10%">
                        <col width="15%">
                        <tr class="head">
                            <td>���</td>
                            <td>
                                ��λ
                            </td>
                            <td>
                                ��װ����
                            </td>
                            <td>
                                ��ʼ����
                            </td>
                            <td>
                                ��ֹ����
                            </td>
                            <td>
                                ʵ������
                            </td>
                            <td>
                                ����
                            </td>
                        </tr>
                        <c:set var="rowcount" value="0"/>
                        <c:forEach items="${list}" var="current">
                            <tr class="out dw_<c:out value="${current.dw}" />" onMouseOver="mouseOver(this)"
                                onMouseOut="mouseOut(this)" style="cursor: pointer"
                                bzhm="<c:out value="${current.bzhm}" />">
                                <td><c:out value="${rowcount+1}"/></td>
                                <td>
                                    <c:forEach var="unit" items="${units}">
                                        <c:if test="${unit.code == current.dw}">
                                            <c:out value="${unit.name}"/>
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:out value="${current.bzhm}"/>
                                </td>
                                <td>
                                    <c:out value="${current.qskh}"/>
                                </td>
                                <td>
                                    <c:out value="${current.zzkh}"/>
                                </td>
                                <td>
                                    <c:out value="${current.sjsl}"/>
                                </td>
                                <td>
                                    <c:if test="${current.dw == 1}">
                                        <a href="#" class="zk" onclick="Ex_Warehouse_Edit_NS._expand(this)">չ��</a>
                                        <a href="#" class="zd" onclick="Ex_Warehouse_Edit_NS._collapse(this)" style="display: none;">�۵�</a>
                                    </c:if>
                                </td>
                            </tr>
                            <c:set var="rowcount" value="${rowcount+1}"/>
                        </c:forEach>
                    </table>
                </c:if>
            </div>

        </div>
    </form>

</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script language="JavaScript" src="rmjs/json2.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">

    Ex_Warehouse_Edit_NS = {

        detailList : [],
        detailJson : {},

        sortInsert: function () {
            $(".xh").each(function (i, me) {
                me.innerHTML = i + 1;
            });
        },

        _expand: function (t) {
            var bzxh = $.trim($(t).parents("tr").attr("bzhm"));

            var successFn = function (arr) {
                arr.sort(function (a, b) {
                    return b.bzhh - a.bzhh
                });
                for (var i in arr) {
                    var obj = arr[i];
                    var addHtml =
                            "<tr class='out' bzxh='bzxh_" + obj.bzxh + "' onMouseOver='mouseOver(this)' onMouseOut='mouseOut(this)' " +
                                "style='cursor: pointer' bzhm='" + obj.bzhh + "'>" +
                            "   <td></td>" +
                            "   <td>��</td>" +
                            "   <td>" + obj.bzhh + "</td>" +
                            "   <td>" + obj.qskh + "</td>" +
                            "   <td>" + obj.zzkh + "</td>" +
                            "   <td></td>" +
                            "   <td></td>" +
                            "</tr>";
                    $(t).parents("tr[bzhm='" + obj.bzxh + "']").after(addHtml);
                }
                $(t).parents("td").children(".zk").hide();
                $(t).parents("td").children(".zd").show();
            };

            $.ajax({
                url: "/rmweb/inventory.frm?method=query-by-bzxh",
                async: false,
                data: {bzxh: bzxh, _t: new Date().getTime()},
                success: function () {
                    if (arguments[1] == "success") {
                        var resultJson = JSON.parse(arguments[0]);
                        if (resultJson["resultId"] == "00") {
                            var inventoryList = resultJson["inventoryList"];
                            successFn(inventoryList);
                        } else {
                            displayDialog(3, resultJson["resultMsg"], "");
                        }
                    }
                }
            });

        },

        _collapse: function (t) {
            var bzxh = $.trim(t.parentNode.parentNode.getAttribute("bzhm"));
            $("[bzxh=bzxh_" + bzxh + "]").each(function () {
                $(this).remove();
            })

            $(t.parentNode).children(".zd").hide();
            $(t.parentNode).children(".zk").show();
        },

        _deleteRaw: function (row) {
            var bzhm = row.parentNode.parentNode.getAttribute("bzhm");
            var dw = bzhm.substring(0, 1);

            delete Ex_Warehouse_Edit_NS.detailJson[bzhm];

            $(row.parentNode.parentNode).remove();
            if (dw == 1) {
                $("[bzxh=bzxh_" + bzhm + "]").each(function () {
                    $(this).remove();
                })
            }
            Ex_Warehouse_Edit_NS.sortInsert();
            Ex_Warehouse_Edit_NS.setSjsl();
        },

        checkBZHM: function (bzhm) {
            var dm = bzhm.substring(0, 1);
            return (bzhm.length == 13 && (dm == 1 || dm == 2 ));
        },
        checkNotInsert: function (hm, qs, zz) {
            var flag = true;
            var dw = hm.substring(0, 1);
            $.each(Ex_Warehouse_Edit_NS.detailJson, function() {
                var qskh = this.qskh;
                var zzkh = this.zzkh;
                if (dw == 1) {
                    if (qs <= qskh && zz >= zzkh) flag = false;
                } else {
                    if (qs >= qskh && zz <= zzkh) flag = false;
                }
            });
            return flag;
        },

        sqsl: 0,
        ycksl: 0,
        checkCKNum: function (addNum) {
            var wcksl = Ex_Warehouse_Edit_NS.sqsl - Ex_Warehouse_Edit_NS.ycksl;
            var rows = $(".added");
            var addedNum = 0;
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var num = parseInt($(row).attr("num"));
                    addedNum += num;
                }
            }
            return wcksl - addedNum >= addNum;
        },
        //�Ժ�Ϊ��λ�����޸�ʵ������
        changeSjsl: function () {
            var sjsl = $(arguments[0]).val();
            //�޸�ʵ������
            Ex_Warehouse_Edit_NS.detailJson[$(arguments[0]).parent().parent().attr("bzhm")].num = sjsl;
            //�޸�ʵ��������
            $(arguments[0]).parent().parent().attr("num", sjsl);
            Ex_Warehouse_Edit_NS.setSjsl();
        },
        //��ȡ��ǰʵ�ʳ�������
        getSjsl: function () {
            var rows = $(".added");
            var addedNum = 0;
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var num = parseInt($(row).attr("num"));
                    addedNum += num;
                }
            }
            return addedNum;
        },
        setSjsl: function () {
            $("#cksl").val(Ex_Warehouse_Edit_NS.getSjsl());
        },

        //ÿ����ӻ�ɾ����¼ʱ�޸�����
        setCkDetail: function () {
            Ex_Warehouse_Edit_NS.detailList = [];
            for (var i in Ex_Warehouse_Edit_NS.detailJson) {
                Ex_Warehouse_Edit_NS.detailList.push(Ex_Warehouse_Edit_NS.detailJson[i]);
            }
            $("#detailList").val(JSON.stringify(Ex_Warehouse_Edit_NS.detailList));
        },

        exportExcel: function () {
            var ckdh = "<c:out value='${bean.ckdh}'/>";
            window.open("/rmweb/exwarehouse.frm?method=export&ckdh=" + ckdh);
        },

        _insertData : function (inventory) {
            var ckdw = inventory["bzhh"] ? 2 : 1;
            var bzhm = inventory["bzhh"] ? inventory["bzhh"] : inventory["bzxh"];
            var num = inventory["zzkh"] - inventory["qskh"] + 1;
            var detailObj = "<tr class=\"out added\" num=\"" + num + "\" onMouseOver=\"mouseOver(this)\" onMouseOut=\"mouseOut(this)\" style=\"cursor: pointer\" bzhm=\"" + bzhm + "\">"
                    + "<td class=\"xh\"></td>"
                    + "<td>" + Ex_Warehouse_Edit_NS.dwList[ckdw] + "</td>"
                    + "<td>" + bzhm + "</td>"
                    + "<td class=\"qskh\">" + inventory.qskh + "</td>"
                    + "<td class=\"zzkh\">" + inventory.zzkh + "</td>"
                    + "<td class=\"sjsl\"><input onChange=\"Ex_Warehouse_Edit_NS.changeSjsl(this);\" onKeydown=\"fCheckInputIntOrBackSpace();\" value=\"" + num + "\"></td>"
                    + "<td><a href=\"#\" onclick=\"Ex_Warehouse_Edit_NS._deleteRaw(this)\">ɾ��</a></td>"
                    + "</tr>";

            Ex_Warehouse_Edit_NS.detailJson[bzhm] = {dw: ckdw, bzhm: bzhm, num: num, qskh : inventory.qskh, zzkh : inventory.zzkh};
            $("#list").append(detailObj);
            $("#bzhm").val("");
            Ex_Warehouse_Edit_NS.sortInsert();
            Ex_Warehouse_Edit_NS.setSjsl();
        },

        _addRaw: function () {
            var ckdw = "";
            var bzhm = $("#bzhm").val();
            if (!Ex_Warehouse_Edit_NS.sqdhReady) {
                displayDialog(1, "�����������Ҫ������뵥�ţ�", "");
            } else if (!Ex_Warehouse_Edit_NS.checkBZHM(bzhm)) {
                displayDialog(1, "�������װ���벻���Ϲ淶��", "");
            } else {
                ckdw = bzhm.substring(0, 1);
                if (ckdw == 1) {

                    $.ajax({
                        url: "/rmweb/inventory.frm?method=check-by-bzxh",
                        async: false,
                        data: {bzxh: bzhm, type: "exwarehouse"},
                        success: function () {
                            if (arguments[1] == "success") {
                                var content = arguments[0];
                                var resultJson = JSON.parse(content);
                                if (resultJson["resultId"] == "00") {
                                    var bean = resultJson["bean"];
                                    var num = bean.zzkh - bean.qskh + 1;

                                    if (!Ex_Warehouse_Edit_NS.checkNotInsert(bzhm, bean.qskh, bean.zzkh)) {
                                        Tools_NS.showWarningDialog("��¼��İ�װ��װ�����Ѿ�¼�룡");
                                    } else if (resultJson.cpdm != $("#cpdm").val() || resultJson.cplb != $("#cplb").val()) {
                                        Tools_NS.showWarningDialog("������ĸð�װ�����뱾���εĲ�Ʒ������");
                                    } else if (!Ex_Warehouse_Edit_NS.checkCKNum(num)) {
                                        Tools_NS.showWarningDialog("¼�������Ѿ��ﵽ��������");
                                    } else {
                                        Ex_Warehouse_Edit_NS._insertData(bean);
                                    }
                                } else {
                                    Tools_NS.showWarningDialog(resultJson["resultMsg"]);
                                }
                            }
                        }
                    });

                } else {
                    $.ajax({
                        url: "/rmweb/inventory.frm?method=check-query-by-bzhh",
                        async: false,
                        data: {bzhh: bzhm, checkType: "exwarehouse"},
                        success: function () {
                            if (arguments[1] == "success") {
                                var resultJson = JSON.parse(arguments[0]);
                                if (resultJson["resultId"] == "00") {
                                    var inventoryList = resultJson["inventoryList"];
                                    var inventory = inventoryList[0];

                                    var bzhm = inventory["bzhh"] ? inventory["bzhh"] : inventory["bzxh"];
                                    var num = inventory["zzkh"] - inventory["qskh"] + 1;

                                    if (!Ex_Warehouse_Edit_NS.checkNotInsert(bzhm, inventory["qskh"], inventory["zzkh"])) {
                                        Tools_NS.showWarningDialog("��¼��İ�װ��װ�����Ѿ�¼�룡");
                                    } else if (!Ex_Warehouse_Edit_NS.checkCKNum(num)) {
                                        Tools_NS.showWarningDialog("¼�������Ѿ��ﵽ��������");
                                    } else {
                                        Ex_Warehouse_Edit_NS._insertData(inventory);
                                    }

                                } else {
                                    Tools_NS.showWarningDialog(resultJson["resultMsg"]);
                                }

                            }
                        }
                    });
                }
            }

        },

        init: function () {

            var units = "<c:out value='${unitsString}'/>";
            Ex_Warehouse_Edit_NS.dwList = JSON.parse(units.replace(new RegExp(/(&#034;)/g), '"'));

            Ex_Warehouse_Edit_NS.sqdhReady = false;

            Ex_Warehouse_Edit_NS.type = "<c:out value='${type}'/>";

            $('#cksl').keydown(function (event) {
                fCheckInputIntOrBackSpace();
            });

            var zt = "<c:out value='${bean.zt}'/>"
            if (zt != 2) {//�����������ͨ�������ܵ���
                $("#exportBtn").hide();
            }

            if (Ex_Warehouse_Edit_NS.type == 2) {
                $("#examineBtn").show();
                $("#unexamineBtn").show();

                $("#savebutton").hide();
            } else {
                $("#examineBtn").hide();
                $("#unexamineBtn").hide();
            }
            $("#sqdxx").hide();
            $("#sqdh").bind('change', function () {
                if (!Ex_Warehouse_Edit_NS.detailJson || Ex_Warehouse_Edit_NS.detailJson.length <= 0) {
                    Tools_NS.showWarningDialog("�������������İ�װ�������޸����뵥��");
                } else {
                    //��ȡ�ö�������Ϣ
                    var sqdh = $("#sqdh").val();
                    $.ajax({
                        url: "/rmweb/order-application.frm?method=fetchbydgdh",
                        async: false,
                        data: {'dgdh': sqdh},
                        success: function () {
                            if (arguments[1] == "success") {
                                var content = arguments[0];
                                var resultJson = JSON.parse(content);
                                if (resultJson["resultId"] == "00") {
                                    var bean = resultJson.resultMsg;
                                    Ex_Warehouse_Edit_NS.sqsl = bean.sl;
                                    Ex_Warehouse_Edit_NS.ycksl = bean.ycksl;
                                    $("#sqdxx").show();
                                    $("#sqsl").html(Ex_Warehouse_Edit_NS.sqsl + "��Ƭ��");
                                    $("#ycksl").html(Ex_Warehouse_Edit_NS.ycksl + "��Ƭ��");
                                    $("#cplb").val(bean.cplb);
                                    Ex_Warehouse_Edit_NS.changeCplb();
                                    $("#cpdm").val(bean.cpdm);
                                    Ex_Warehouse_Edit_NS.sqdhReady = true;
                                } else {
                                    $("#sqdxx").hide();
                                    displayDialog(3, resultJson["resultMsg"], "");
                                    Ex_Warehouse_Edit_NS.sqdhReady = false;
                                }
                            }
                        }
                    });
                }
            });

            <c:if test="${bean!=null}">
            $("#savebutton").attr("disabled", true);

            $("#ckdh").val("<c:out value='${bean.ckdh}'/>");
            $("#ssbm").val("<c:out value='${bean.ssbm}'/>");
            $("#sqdh").val("<c:out value='${bean.sqdh}'/>");
            $("#cksl").val("<c:out value='${bean.cksl}'/>");
            $("#cplb").val("<c:out value='${bean.cplb}'/>");
            Ex_Warehouse_Edit_NS.changeCplb();
            $("#cpdm").val("<c:out value='${bean.cpdm}'/>");
            $("#bz").val("<c:out value='${bean.bz}'/>");

            $("#cplb").attr("readonly", "readonly");
            $("#cpdm").attr("readonly", "readonly");

            $("#bzhm").focus();

            //$("#delbutton").attr("disabled",false);
            <c:if test="${bmjb!=null}">
            </c:if>
            </c:if>


            $('#bzhm').bind('keydown', function (event) {
                setTimeout(function () {//�˴�ͬ�������޷�������ʾ��ʾ
                    var key = event.keyCode;
                    if (key == 13) {
                        Ex_Warehouse_Edit_NS._addRaw();
                    }
                })

            });
            $("#addInbutton").bind("click", function () {
                Ex_Warehouse_Edit_NS._addRaw();
            });

        },

        saveSuccessFn: function () {
            window.opener.query_cmd();
            window.close();
        },

        examine: function (zt) {
            $("#myform").attr("action", "<c:url value='/exwarehouse.frm?method=examine'/>");
            closes();
            $("#myform").ajaxSubmit({
                dataType: "json", async: false,
                data: {zt: zt},
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (data) {
                    if (data && data["resultId"] == "00") {
                        displayDialog(2, "��˳ɹ���", "Ex_Warehouse_Edit_NS.saveSuccessFn();");
                    } else {
                        displayDialog(3, data["resultMsg"], "");
                    }
                }
            });
        },

        save: function () {
            if (!checkNull($("#cplb"), "��Ʒ���")) return false;
            if (!checkNull($("#cpdm"), "��Ʒ����")) return false;
            if (!Ex_Warehouse_Edit_NS.detailJson || Ex_Warehouse_Edit_NS.detailJson.length <= 0) {
                Tools_NS.showWarningDialog("������д�����װ��");
                return;
            } else {
                Ex_Warehouse_Edit_NS.setCkDetail();
            }
            $("#myform").attr("action", "<c:url value='/exwarehouse.frm?method=save-exwarehouse'/>");
            closes();
            Tools_NS.showLoading("���ڳ��⣬���Ե�...");
            $("#myform").ajaxSubmit({
                dataType: "json",
                async: true,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function(data) {
                    Ex_Warehouse_Edit_NS.saveReturns(data);
                }
            });
        },
        saveReturns: function (data) {
            if (data && data["resultId"] == "00") {
                var str = "Ԥ����ɹ���"
                        + "<br/>ʣ��������" + data.sysl
                        + "<br/>Ԥ����������" + data.ycksl;
                if (data.sysl - data.ycksl < 400) {
                    str += "<div style='color:red;'>��Ʒ�����٣��뼰ʱ�����棡<div>"
                }
                Tools_NS.showWarningDialog(str, Ex_Warehouse_Edit_NS.saveSuccessFn);
            } else {
                Tools_NS.showWarningDialog(data["resultMsg"]);
                opens();
            }
        },

        del: function () {
            if (confirm("�Ƿ�ȷ��ɾ���ó��ⵥ��")) {
                $("#myform").attr("action", "<c:url value='/exwarehouse.frm?method=del-exwarehouse'/>");
                closes();
                $("#myform").ajaxSubmit({
                    dataType: "json",
                    async: false,
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    success: Ex_Warehouse_Edit_NS.returndeletes
                });
            }
        },

        returndeletes: function (data) {
            if (data["code"] == "1") {
                window.opener.query_cmd();
                displayDialog(2, "ɾ�����ⵥ�ɹ���", "Ex_Warehouse_Edit_NS.saveSuccessFn();");
                window.close();
            } else {
                displayDialog(3, decodeURIComponent(data["message"]), "");
                opens();
            }
        },

        query_cmd: function () {
            window.location.reload();
        },

        changeCplb: function () {
            var category = $("#cplb").val();
            if (category) {
                $.ajax({
                    url: "/rmweb/product.frm?method=fetch-by-category",
                    async: false,
                    data: {category: category}, success: function () {
                        if (arguments[1] == "success") {
                            var content = arguments[0];
                            var cpdms = JSON.parse(arguments[0]);
                            var options = $("#cpdm")[0].options;
                            var length = options.length;
                            if (length > 1) {
                                for (var i = 1; i < length; i++) {
                                    options.remove(i);
                                }
                            }
                            for (var j = 0; j < cpdms.length; j++) {
                                options.add(new Option(cpdms[j].cpmc, cpdms[j].cpdm));
                            }
                        }

                    }
                });
            }
        },

        //�򿪵�����������ʼ���ź���ֹ���Ż�ȡ���
        addByKH: function () {
            var cpdm = $("#cpdm").val();
            if (!cpdm) {
                Tools_NS.showWarningDialog("��Ʒ���벻��Ϊ��");
            } else {
                var khHtml = '<div class="khdiv">��ʼ���ţ�<input id = "qskh"></div>' +
                        '<div class="khdiv">��ֹ���ţ�<input id = "zzkh"></div>';

                Tools_NS.showConfirmDialog({
                    id: 'addByKhDialog',
                    title: '��������ӿ��',
                    message: "<div style='height:100px;'>" + khHtml + "</div>",
                    width: 500,
                    cancellable: true,
                    onCancel: function () {
                        $.unblockUI();
                    },
                    onConfirm: function () {
                        if (!($("#qskh").val() && $("#zzkh").val())) {
                            alert("��������ʼ���ź���ֹ����");
                        } else {
                            Ex_Warehouse_Edit_NS.addXhbyKh();
                            $.unblockUI();
                        }
                    }
                });
            }
        },

        //������ʼ���ź���ֹ���Ž������Ϣ��ʾ
        addXhbyKh: function () {
            var qskh = $("#qskh").val();
            var zzkh = $("#zzkh").val();
            var cpdm = $("#cpdm").val();

            $.get("<c:url value='/inventory.frm'/>", {
                method: 'getXhByKh',
                qskh: qskh,
                zzkh: zzkh,
                cpdm: cpdm,
                zt: '4',
                _t: new Date().getTime()
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    var inventoryArr = data["resultMsg"];
                    Tools_NS.showLoading("������ӣ����Ե�...");
                    Ex_Warehouse_Edit_NS.circle(inventoryArr, 0);
                }
            }, "json");
        },

        circle : function(data, index) {
            if (index >= data.length) {
                Tools_NS.closeLoading();
            } else {
                setTimeout(function() {
                    var inventory = data[index];
                    var bzhm = inventory["bzhh"] ? inventory["bzhh"] : inventory["bzxh"];
                    var num = inventory["zzkh"] - inventory["qskh"] + 1;

                    if (!Ex_Warehouse_Edit_NS.checkNotInsert(bzhm, inventory["qskh"], inventory["zzkh"])) {
                        Ex_Warehouse_Edit_NS.circle(inventory, index + 1);
                    } else if (!Ex_Warehouse_Edit_NS.checkCKNum(num)) {
                        Tools_NS.showWarningDialog("¼�������Ѿ��ﵽ��������");
                    } else {
                        Ex_Warehouse_Edit_NS._insertData(inventory);
                        Ex_Warehouse_Edit_NS.circle(data, index + 1);
                    }
                }, 5);
            }
        }
    };

    $(function() {
        Ex_Warehouse_Edit_NS.init();
    })


</script>
</body>
</html>
