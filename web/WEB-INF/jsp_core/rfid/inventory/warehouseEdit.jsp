<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312" %>
<html>
<head>
    <title>入库单维护</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" rel="stylesheet" type="text/css"/>
</head>
<body class="rfid">
<div id="panel" style="display:none">
    <div id="paneltitle">入库单维护</div>
    <form action="" method="post" name="myform" id="myform">
        <c:if test="${bean==null}">
            <input type="hidden" name="detailList" id="detailList">
        </c:if>
        <div id="block">
            <div id="blocktitle">入库单信息</div>
            <div id="blockmargin">8</div>
            <table border="0" cellspacing="1" cellpadding="0" class="detail">
                <col width="15%">
                <col width="35%">
                <col width="15%">
                <col width="35%">
                <tr>
                    <td class="head">入库单号</td>
                    <td class="body">
                        <c:if test="${bean!=null}">
                            <c:out value='${bean.rkdh}'/>
                            <input type="hidden" name="rkdh" id="rkdh">
                        </c:if>
                        <c:if test="${bean==null}">
                            <!-- <input type="text" name="rkdh" id="rkdh"> -->
                        </c:if>
                    </td>
                    <td class="head">所属部门</td>
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
                    <td class="head">产品类别</td>
                    <td class="body">
                        <select id="cplb" name="cplb" style="width: 80%;" onChange="Warehouse_Edit_NS.changeCplb()">
                            <option value="">--请选择--</option>
                            <c:forEach var="productCategory" items="${productCategories}">
                                <option value="${productCategory.cplb}">
                                    <c:out value="${productCategory.lbmc}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">产品名称</td>
                    <td class="body">
                        <select id="cpdm" name="cpdm" style="width:80%;">
                            <option value="">--请先选择产品类别--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="head">备注</td>
                    <td class="body" colspan="3">
                        <c:if test="${bean!=null}">
                            <c:out value='${bean.bz}'/>
                        </c:if>
                        <c:if test="${bean==null}">
                            <textarea name="bz" id="bz" rows="4" style="width:92%;"></textarea>
                        </c:if>
                    </td>
                </tr>

                <c:if test="${list==null}">
                    <tr>
                        <td class="head" style="padding:10px 0;">箱/盒号:</td>
                        <td class="body">
                            <input type="text" name="bzhm" id="bzhm" style="width:75%"/>
                            <input type="text" style="display:none;"/>
                            <input type="button" name="addInbutton" id="addInbutton" value="添加" class="button_save"/>
                        </td>
                        <td class="body" colspan="2" style="text-align:right;">
                            <input type="button" name="addByKHbutton" id="addByKHbutton" onclick="Warehouse_Edit_NS.addByKH()" value="按卡号"
                                   class="button_save">
                            <input type="button" name="savebutton" id="savebutton" onclick="Warehouse_Edit_NS.save()" value="提交"
                                   class="button_save">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="取消"
                                   class="button_close">
                        </td>
                    </tr>
                </c:if>
            </table>

            <div id="productList">
                <c:if test="${list==null}">
                    <table border="0" cellspacing="1" cellpadding="0" class="list" id="list">
                        <col width="10%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <tr class="head">
                            <td>序号</td>
                            <td>
                                单位
                            </td>
                            <td>
                                包装号码
                            </td>
                            <td>
                                起始卡号
                            </td>
                            <td>
                                终止卡号
                            </td>
                            <td>
                                操作
                            </td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${list!=null}">
                    <table border="0" cellspacing="1" cellpadding="0" class="detail" style="margin-top:30px;">
                        <tr>
                            <td class="head" width="100%"
                                style="height:30px;text-align:left;font-weight:bold;padding-left:10px;font-size:14px;">
                                入库清单
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="1" cellpadding="0" class="list">
                        <col width="10%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <col width="15%">
                        <tr class="head">
                            <td>序号</td>
                            <td>
                                单位
                            </td>
                            <td>
                                包装号码
                            </td>
                            <td>
                                起始卡号
                            </td>
                            <td>
                                终止卡号
                            </td>
                            <td>
                                操作
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
                                    <c:if test="${current.dw == 1}">
                                        <a href="#" class="zk" onclick="Warehouse_Edit_NS._expand(this)">展开</a>
                                        <a href="#" class="zd" onclick="Warehouse_Edit_NS._collapse(this)" style="display: none;">折叠</a>
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
    var Warehouse_Edit_NS = {
        detailJson: {},
        detailList: [],

        sortInsert: function () {
            $(".xh").each(function (i, me) {
                me.innerHTML = i + 1;
            });
        },

        init : function() {
            var units = "<c:out value='${unitsString}'/>";
            Warehouse_Edit_NS.dwList = JSON.parse(units.replace(new RegExp(/(&#034;)/g), '"'));

            //$("#delbutton").attr("disabled",true);
            $("#addbutton").attr("disabled", true);

            <c:if test="${bean!=null}">
            $("#addbutton").attr("disabled", false);
            $("#savebutton").attr("disabled", true);

            var rkdh = "<c:out value='${bean.rkdh}'/>";
            $("#rkdh").val("<c:out value='${bean.rkdh}'/>");
            $("#ssbm").val("<c:out value='${bean.ssbm}'/>");
            $("#cplb").val("<c:out value='${bean.cplb}'/>");
            Warehouse_Edit_NS.changeCplb();
            $("#cpdm").val("<c:out value='${bean.cpdm}'/>");
            $("#bz").val("<c:out value='${bean.bz}'/>");

            $("#cplb").attr("readOnly", "readOnly");
            $("#cpdm").attr("readOnly", "readOnly");

            $("#bzhm").focus();
            </c:if>

            $('#bzhm').keydown(function (event) {
                setTimeout(function () {
                    var key = event.keyCode;
                    if (key == 13) {
                        Warehouse_Edit_NS._addRaw();
                    }
                });
            });
            $("#addInbutton").bind("click", function () {
                Warehouse_Edit_NS._addRaw();
            });
        },

        _addRaw : function () {
            var rkdw = "";
            var bzhm = $("#bzhm").val().trim();
            if (Warehouse_Edit_NS.checkBZHM(bzhm)) {
                rkdw = bzhm.substring(0, 1);

                if (rkdw == 1) {//箱为单位
                    $.ajax({
                        url: "/rmweb/inventory.frm?method=check-by-bzxh",
                        async: false,
                        data: {bzxh: bzhm, type: "warehouse"},
                        success: function () {
                            if (arguments[1] == "success") {
                                var content = arguments[0];
                                var resultJson = JSON.parse(content);
                                if (resultJson["resultId"] == "00") {
                                    var bean = resultJson["bean"];
                                    var num = bean.zzkh - bean.qskh + 1;

                                    if (!Warehouse_Edit_NS.checkNotInsert(bzhm, bean.qskh, bean.zzkh)) {
                                        Tools_NS.showWarningDialog("您录入的包装包装号码已经录入！");
                                    } else if ($("#detailList").val() == "") {//若录入数据为空
                                        $("#cplb").val(resultJson.cplb);
                                        Warehouse_Edit_NS.changeCplb();
                                        $("#cpdm").val(resultJson.cpdm);
                                        Warehouse_Edit_NS._insertData();
                                    } else if (resultJson.cpdm != $("#cpdm").val() || resultJson.cplb != $("#cplb").val()) {
                                        Tools_NS.showWarningDialog("您输入的该包装号码与本批次的产品不符！");
                                    } else {
                                        Warehouse_Edit_NS._insertData(bean);
                                    }

                                } else {
                                    Tools_NS.showWarningDialog(resultJson["resultMsg"]);
                                }
                            }
                        }
                    });
                } else {//盒为单位
                    $.ajax({
                        url: "/rmweb/inventory.frm?method=check-query-by-bzhh",
                        async: false,
                        data: {bzhh: bzhm, checkType: "warehouse"},
                        success: function () {
                            if (arguments[1] == "success") {
                                var resultJson = JSON.parse(arguments[0]);
                                if (resultJson["resultId"] == "00") {
                                    var inventoryList = resultJson["inventoryList"];
                                    var inventory = inventoryList[0];

                                    var bzhm = inventory["bzhh"] ? inventory["bzhh"] : inventory["bzxh"];
                                    var num = inventory["zzkh"] - inventory["qskh"] + 1;

                                    if (!Ex_Warehouse_Edit_NS.checkNotInsert(bzhm, inventory["qskh"], inventory["zzkh"])) {
                                        Tools_NS.showWarningDialog("您录入的包装包装号码已经录入！");
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

            } else {
                displayDialog(1, "您输入包装号码不符合规范", "");
            }
        },

        _expand : function (t) {
            var bzxh = $.trim(t.parentNode.parentNode.getAttribute("bzhm"));

            var successFn = function (arr) {
                //var arr = [{bzhh:1000,qskh:1,zzkh:20,zt:1},{bzhh:1001,qskh:21,zzkh:40,zt:1}];
                arr.sort(function (a, b) {
                    return b.bzhh - a.bzhh
                });
                for (var i in arr) {
                    var obj = arr[i]
                    var addHtml = "<tr class=\"out\" bzxh=\"bzxh_" + bzxh + "\" onMouseOver=\"mouseOver(this)\" onMouseOut=\"mouseOut(this)\" style=\"cursor: pointer\" bzhm=\"" + obj.bzhh + "\">"
                            + "<td></td>"
                            + "<td>&nbsp;盒</td>"
                            + "<td>&nbsp;" + obj.bzhh + "</td>"
                            + "<td>&nbsp;" + obj.qskh + "</td>"
                            + "<td>&nbsp;" + obj.zzkh + "</td>"
                            + "<td></td>"
                            + "</tr>";
                    $(t.parentNode.parentNode).after(addHtml);
                }
                $(t.parentNode).children(".zk").hide();
                $(t.parentNode).children(".zd").show();
            }

            $.ajax({
                url: "/rmweb/inventory.frm?method=query-by-bzxh",
                async: false,
                data: {bzxh: bzxh},
                success: function () {
                    if (arguments[1] == "success") {
                        var content = arguments[0];
                        var resultJson = JSON.parse(content);
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

        _collapse : function (t) {
            var bzxh = $.trim(t.parentNode.parentNode.getAttribute("bzhm"));
            $("[bzxh=bzxh_" + bzxh + "]").each(function () {
                $(this).remove();
            });

            $(t.parentNode).children(".zd").hide();
            $(t.parentNode).children(".zk").show();
        },

        _deleteRaw : function (row) {
            var bzhm = row.parentNode.parentNode.getAttribute("bzhm");
            var dw = bzhm.substring(0, 1);

            delete Warehouse_Edit_NS.detailJson[bzhm];

            $(row.parentNode.parentNode).remove();
            if (dw == 1) {
                $("[bzxh=bzxh_" + bzhm + "]").each(function () {
                    $(this).remove();
                })
            }
            Warehouse_Edit_NS.sortInsert();
        },

        checkBZHM : function (bzhm) {
            var dw = bzhm.substring(0, 1);
            return (bzhm.length == 13 && (dw == 1 || dw == 2 ));
        },

        checkNotInsert: function (hm, qs, zz) {
            var flag = true;
            var dw = hm.substring(0, 1);
            $.each(Warehouse_Edit_NS.detailJson, function() {
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

        setRkDetail : function () {
            Warehouse_Edit_NS.detailList = [];
            for (var i in Warehouse_Edit_NS.detailJson) {
                Warehouse_Edit_NS.detailList.push(Warehouse_Edit_NS.detailJson[i]);
            }
            $("#detailList").val(JSON.stringify(Warehouse_Edit_NS.detailList));
        },

        _insertData : function (inventory) {
            var rkdw = inventory["bzhh"] ? 2 : 1;
            var bzhm = inventory["bzhh"] ? inventory["bzhh"] : inventory["bzxh"];
            var num = inventory["zzkh"] - inventory["qskh"] + 1;
            var detailObj = "<tr class=\"out added\" num=\"" + num + "\" onMouseOver=\"mouseOver(this)\" onMouseOut=\"mouseOut(this)\" style=\"cursor: pointer\" bzhm=\"" + bzhm + "\">"
                    + "<td class=\"xh\"></td>"
                    + "<td>" + Warehouse_Edit_NS.dwList[rkdw] + "</td>"
                    + "<td>" + bzhm + "</td>"
                    + "<td class=\"qskh\">" + inventory.qskh + "</td>"
                    + "<td class=\"zzkh\">" + inventory.zzkh + "</td>"
                    + "<td><a href=\"#\" onclick=\"Warehouse_Edit_NS._deleteRaw(this)\">删除</a></td>"
                    + "</tr>";

            Warehouse_Edit_NS.detailJson[bzhm] = {dw: rkdw, bzhm: bzhm, num: num, qskh : inventory.qskh, zzkh : inventory.zzkh};
            $("#list").append(detailObj);
            $("#bzhm").val("");
            Warehouse_Edit_NS.sortInsert();
        },

        save : function() {
            if (!checkNull($("#cplb"), "产品类别")) return false;
            if (!checkNull($("#cpdm"), "产品名称")) return false;
            if (!Warehouse_Edit_NS.detailJson || Warehouse_Edit_NS.detailJson.length <= 0) {
                Tools_NS.showWarningDialog("请先填写入库包装号");
                return;
            } else {
                Warehouse_Edit_NS.setRkDetail();
            }
            $("#myform").attr("action", "<c:url value='/warehouse.frm?method=save-warehouse'/>");
            closes();
            Tools_NS.showLoading("正在入库，请稍等...");
            $("#myform").ajaxSubmit({
                dataType: "json",
                async: true,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function(data) {
                    Warehouse_Edit_NS.returns(data);
                }
            });
        },

        returns : function(data) {
            if (data["code"] == "1") {
                Tools_NS.showWarningDialog("入库成功", function() {
                    window.opener.query_cmd();
                    window.close();
                });
            } else {
                Tools_NS.showWarningDialog(decodeURIComponent(data["message"]));
                opens();
            }
        },

        del : function() {
            if (confirm("是否确定删除该入库单？")) {
                $("#myform").attr("action", "<c:url value='/warehouse.frm?method=del-warehouse'/>");
                closes();
                $("#myform").ajaxSubmit({
                    dataType: "json",
                    async: false,
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    success: Warehouse_Edit_NS.returndeletes
                });
            }
        },

        returndeletes : function(data) {
            if (data["code"] == "1") {
                window.opener.query_cmd();
                displayDialog(2, "删除入库单成功！", "");
                window.close();
            } else {
                displayDialog(3, decodeURIComponent(data["message"]), "");
                opens();
            }
        },

        changeCplb : function() {
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

        addByKH : function () {
            var cpdm = $("#cpdm").val();
            if (!cpdm) {
                Tools_NS.showWarningDialog("产品代码不可为空");
            } else {
                var khHtml = '<div class="khdiv">起始卡号：<input id = "qskh"></div>' +
                        '<div class="khdiv">终止卡号：<input id = "zzkh"></div>';

                Tools_NS.showConfirmDialog({
                    id: 'addByKhDialog',
                    title: '按卡号添加库存',
                    message: "<div style='height:100px;'>" + khHtml + "</div>",
                    width: 500,
                    cancellable: true,
                    onCancel: function () {
                        $.unblockUI();
                    },
                    onConfirm: function () {
                        if (!($("#qskh").val() && $("#zzkh").val())) {
                            alert("请输入起始卡号和终止卡号");
                        } else {
                            Warehouse_Edit_NS.addXhbyKh();
                            $.unblockUI();
                        }
                    }
                });
            }
        },

        //根据起始卡号和终止卡号将库存信息显示
        addXhbyKh: function () {
            var qskh = $("#qskh").val();
            var zzkh = $("#zzkh").val();
            var cpdm = $("#cpdm").val();

            $.get("<c:url value='/inventory.frm'/>", {
                method: 'getXhByKh',
                qskh: qskh,
                zzkh: zzkh,
                cpdm: cpdm,
                zt: '3',
                _t: new Date().getTime()
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    var inventoryArr = data["resultMsg"];
                    Tools_NS.showLoading("正在添加，请稍等...");
                    Warehouse_Edit_NS.circle(inventoryArr, 0);

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

                    if (!Warehouse_Edit_NS.checkNotInsert(bzhm, inventory["qskh"], inventory["zzkh"])) {
                        Warehouse_Edit_NS.circle(inventory, index + 1);
                    } else {
                        Warehouse_Edit_NS._insertData(inventory);
                        Warehouse_Edit_NS.circle(data, index + 1);
                    }
                }, 5);
            }
        }

    };

    $(function() {
        Warehouse_Edit_NS.init();
    });


</script>
</body>
</html>
