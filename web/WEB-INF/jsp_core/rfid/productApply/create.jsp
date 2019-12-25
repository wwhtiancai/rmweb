<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>产品领用</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
    <%--<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
          media="screen" title="Flora (Default)" />--%>
    <link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
          type="text/css">
    <style type="text/css">
        div.khdiv {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        div.khdiv input {
            height: 20px;
        }
    </style>
</head>
<body class="rfid">
<div id="panel" style="display: none">
    <div id="paneltitle">
        产品领用
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="orderApplicationForm" id="productApplyForm">
            <div id="block">
                <div id="blocktitle">领用单</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">领用部门</td>
                        <td class="body">
                            <h:managementbox list='${xjbm}' id='lybm' haveNull='1'/>
                        </td>
                        <td class="head">领用人</td>
                        <td class="body">
                            <input type="text" id="lyr" name="lyr" style="width:100%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">产品类别
                        	<input type="hidden" name="cplb" id="cplb"/>
                        </td>
                        <td class="body" id="cplbmc"></td>
                        <td class="head">产品名称
                        	<input type="hidden" name="cpdm" id="cpdm"/></td>
                        <td class="body" id="cpmc"></td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" style="width:100%"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="head" style="padding:10px 0;">箱/盒号:</td>
                        <td class="body">
                            <input type="text" name="bzh" id="bzh" style="width:70%"/>
                            <input type="button" name="nextBtn" id="nextBtn" value="添加" class="button_save"/>
                        </td>
                        <td class="body" colspan="2" style="text-align:right;">
                            <input type="button" name="addByKhBtn" id="addByKhBtn" value="按卡号" class="button_new"/>
                            <input type="button" name="savebutton" id="savebutton" onclick="save()" value="提交" class="button_save">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="取消" class="button_close">
                        </td>
                    </tr>
                </table>

                <div id="productList">

                    <table border="0" cellspacing="1" cellpadding="0" class="list" id="productListTable">
                        <col width="10%">
                        <col width="30%">
                        <col width="20%">
                        <col width="20%">
                        <col width="20%">
                        <tr class="head">
                            <td>单位</td>
                            <td>箱/盒号</td>
                            <td>起始卡号</td>
                            <td>终止卡号</td>
                            <!-- <td>产品类别</td>
                            <td>产品名称</td> -->
                            <td>操作</td>
                        </tr>
                    </table>
                </div>

            </div>
        </form>
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript">
    if (!window.console) {
        window.console = {
            log: function() {
                alert(arguments[0]);
            }
        }
    }

    var Create_Product_Apply_NS = {

        inventories : [],
        applyList : [],
        
        cpdm:"",

        init : function() {

            $(".button_save").click(function() {
                Create_Product_Apply_NS.createProductApply();
            });

            $("#manualBtn").click(function() {
                $("#productList").show();
            });

            $("body").on("change", "input[name='bzh']", function() {
                Create_Product_Apply_NS.checkInventory($(this).val());
            }).on("click", ".expand-btn", function() {
                var inventoryGroup = $(this).closest("tr").attr("data-inventory-group");
                if ($(this).hasClass("expanded")) {
                    $("tr[data-inventory-group=" + inventoryGroup + "].inventory-detail").hide();
                    $(this).removeClass("expanded");
                    $(this).text("展开");
                } else {
                    $(this).text("收起");
                    $("tr[data-inventory-group=" + inventoryGroup + "].inventory-detail").show();
                    $(this).addClass("expanded");
                }
            }).on("click", ".delete-btn", function() {
                var inventoryGroup = $(this).closest("tr").attr("data-inventory-group");
                Create_Product_Apply_NS.deleteInventory(inventoryGroup);
            });

            $("#addByKhBtn").click(function() {
                Create_Product_Apply_NS.addByKH()
            });
        },

        addByKH : function () {
            var khHtml = '<div class="khdiv">起始卡号：<input id = "qskh"></div>' +
                    '<div class="khdiv">终止卡号：<input id = "zzkh"></div>';

            Tools_NS.showConfirmDialog({
                id: 'addByKhDialog',
                title: '按卡号添加',
                message: "<div style='height:100px;'>" + khHtml + "</div>",
                width: 500,
                cancellable: true,
                onCancel: function () {
                    $.unblockUI();
                },
                onConfirm: function () {
                    if (!checkNull($("#qskh"), "起始卡号")) return false;
                    if (!checkNull($("#zzkh"), "终止卡号")) return false;
                    if ($("#qskh").val().length != 12) {
                        alert("非法卡号，请重新输入");
                        return false;
                    }

                    Create_Product_Apply_NS.addXhbyKh();
                    $.unblockUI();
                }
            });
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

        createProductApply : function() {
            if(!checkNull($("#lybm"), "领用部门")) return false;
            if(!checkNull($("#lyr"), "领用人")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/product-apply.rfid?method=create'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#productApplyForm").serialize() + "&" + $.param({bzhs:Create_Product_Apply_NS.applyList}, true),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
                        window.opener.Product_Apply_NS.query();
                        alert("创建成功！");
                        window.close();
                    }
                },
                dataType: "json"
            });
        },

        deleteInventory : function(inventoryGroup) {
            $("tr[data-inventory-group=" + inventoryGroup + "]").each(function(index, element) {
                var pos = $.inArray($(element).attr("data-bzh"), Create_Product_Apply_NS.inventories);
                if (pos >= 0) Create_Product_Apply_NS.inventories.splice(pos, 1);
                pos = $.inArray($(element).attr("data-bzh"), Create_Product_Apply_NS.applyList);
                if (pos >= 0) Create_Product_Apply_NS.applyList.splice(pos, 1);
                $(element).remove();
            });
        },

        checkInventory : function(bzh) {
            if (!bzh) return;
            if ($.inArray(bzh, Create_Product_Apply_NS.inventories) >= 0) {
                alert("包装箱/盒" + bzh + "已录入，请勿重复录入");
                $("#bzh").val("").focus();
                return;
            }
            $.get("<c:url value='/inventory.frm?method=check-inventory'/>",
                    {bzh: bzh, t: new Date().getTime()},
                    function(data) {
                        if (data && data["resultId"] == "00") {
                            var appendRows = "";

                        	if(Create_Product_Apply_NS.inventories.length > 0){
                        		if(data["inventories"][0]["cpdm"] != Create_Product_Apply_NS.cpdm){
                        			alert("您输入的产品代码与之前输入的不一致！");
                        			return;
                        		}
                            }else{
                            	Create_Product_Apply_NS.cpdm = data["inventories"][0]["cpdm"];
                                $("#cplbmc").html(data["inventories"][0]["cplbmc"]);
                                $("#cpmc").html(data["inventories"][0]["cpmc"]);
                                $("#cplb").val(data["inventories"][0]["cplb"]);
                                $("#cpdm").val(data["inventories"][0]["cpdm"]);
                            }
                            if (bzh.substr(0, 1) == "1") {
                                appendRows = appendRows +
                                        '<tr class="out" data-inventory-group="' + bzh + '" data-bzh="' + bzh + '">' +
                                        '   <td>箱</td>' +
                                        '   <td>' + bzh + '</td>' +
                                        '   <td>' + data["inventories"][0]["cplbmc"] + '</td>' +
                                        '   <td>' + data["inventories"][0]["cpmc"] + '</td>' +
                                        '   <td>' +
                                        '       <a href="javascript:void(0)" class="delete-btn">删除</a>' +
                                        '       <a href="javascript:void(0)" class="expand-btn">展开</a>' +
                                        '   </td>' +
                                        '</tr>';
                                var subInventories = [];
                                for (var i = 0; i < data["inventories"].length; i++) {
                                    var element = data["inventories"][i];
                                    if ($.inArray(element["bzhh"], Create_Product_Apply_NS.inventories) >= 0) {
                                        alert("包装箱/盒" + bzh + "已录入，请勿重复录入");
                                        $("#bzh").val("").focus();
                                        return;
                                    }
                                    subInventories.push(element["bzhh"]);
                                    appendRows = appendRows +
                                            '<tr style="display:none" class="inventory-detail out" data-inventory-group="' + bzh + '" data-bzh="' + element["bzhh"] + '">' +
                                            '   <td>盒</td>' +
                                            '   <td>' + element["bzhh"] + '</td>' +
                                            '   <td>' + element["qskh"] + '</td>' +
                                            '   <td>' + element["zzkh"] + '</td>' +
                                            /* '   <td>' + element["cplbmc"] + '</td>' +
                                            '   <td>' + element["cpmc"] + '</td>' + */
                                            '   <td></td>' +
                                            '</tr>'
                                }
                                Create_Product_Apply_NS.inventories.push(bzh);
                                $.merge(Create_Product_Apply_NS.inventories, subInventories);
                                Create_Product_Apply_NS.applyList.push(bzh);
                                
                            } else if (bzh.substr(0, 1) == "2"){
                                Create_Product_Apply_NS.inventories.push(bzh);
                                Create_Product_Apply_NS.applyList.push(bzh);
                                appendRows = appendRows +
                                        '<tr class="out" data-inventory-group="' + bzh + '" data-bzh="' + bzh + '">' +
                                        '   <td>盒</td>' +
                                        '   <td>' + bzh + '</td>' +
                                        '   <td>' + data["inventories"][0]["qskh"] + '</td>' +
                                        '   <td>' + data["inventories"][0]["zzkh"] + '</td>' +
                                        /* '   <td>' + data["inventories"][0]["cplbmc"] + '</td>' +
                                        '   <td>' + data["inventories"][0]["cpmc"] + '</td>' + */
                                        '   <td><a href="javascript:void(0)" class="delete-btn">删除</a></td>' +
                                        '</tr>';
                            }
                            $("#productListTable").append(appendRows);
                        } else {
                            alert(data["resultMsg"]);
                        }
                        $("#bzh").val("").focus();
            }, "json");
        }
    };

    $(function() {
        Create_Product_Apply_NS.init();
    });
</script>
</body>
</html>