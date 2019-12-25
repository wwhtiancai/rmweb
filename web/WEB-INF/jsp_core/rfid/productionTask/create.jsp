<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>������������</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
    <style>
        .available-kh {
            font-size:20px;
            cursor:pointer;
        }
        .available-kh td {
            cursor:pointer;
        }
    </style>
</head>
<body class="rfid">
<div id="panel" style="display: none">
    <div id="paneltitle">
        ��������
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="productionTaskForm" id="productionTaskForm">
            <div id="block">
                <div id="blocktitle">��������</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">ʡ��</td>
                        <td class="body">
                            <h:codebox list='${provinces}' id='sf' showType="2" cssStyle="width:100%"
                                       onChange="Create_Production_Task_NS.changeProvince()"/>
                        </td>
                        <td class="head">��������</td>
                        <td class="body">
                            <h:codebox list='${productionTaskType}' id='rwdm' showType="2" cssStyle="width:100%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��Ʒ���</td>
                        <td class="body">
                            <select id="cplb" name="cplb" style="width: 100%;">
                                <option value="">--��ѡ��--</option>
                                <c:forEach var="productCategory" items="${productCategories}">
                                    <option value="${productCategory.cplb}">
                                        <c:out value="${productCategory.lbmc}" />
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="head">��Ʒ����</td>
                        <td class="body">
                            <select id="cpdm" name="cpdm" style="width:100%;">
                                <option value="">--����ѡ���Ʒ���--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��ʼ����</td>
                        <td class="body">
                            <input type="text" id="qskh" name="qskh" style="width:78%;"/>
                            <span><input id="queryAvailableKhBtn" type="button" class="button_default" value="��ѯ" style="width:18%"/></span>
                        </td>
                        <td class="head">����</td>
                        <td class="body"><input type="text" id="sl" name="sl" style="width:60%"/>���䣬��${vestCapacity}*${boxCapacity}Ƭ��</td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100" style="width:100%"></textarea>
                        </td>
                    </tr>
                </table>

                <table border="0" cellspacing="0" cellpadding="0" class="detail">
                    <tr>
                        <td class="command">
                            <input type="button" name="savebutton" id="savebutton" value="�ύ" class="button_save">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="ȡ��" class="button_close">
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript">
    <!--

    var Create_Production_Task_NS = {

        changeProvince : function() {

        },

        init : function() {

            $("#savebutton").click(function() {
                Create_Production_Task_NS.create();
            });

            $("#cplb").change(function() {
                Create_Production_Task_NS.fetchProductByCategory($(this).val());
            });

            $("#queryAvailableKhBtn").click(function() {
                Create_Production_Task_NS.queryAvailableKh();
            });

            $("body").on("click", ".available-kh", function(data) {
                $(this).find("input[name='selectedKh']").attr("checked", "checked");
            });

        },

        create : function() {
            if(!checkNull($("#qskh"), "��ʼ����")) return false;
            if(!checkNull($("#sl"), "����")) return false;
            if(!checkNull($("#cpdm"), "��Ʒ����")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/production-task.rfid?method=create'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: encodeURI($("#productionTaskForm").serialize()),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data) {
                        if (data["resultId"] == "00") {
                            Tools_NS.showConfirmDialog({
                                id : 'successDialog',
                                title: '�����ɹ�',
                                message: '�����ɹ�',
                                width: 400,
                                cancellable: false,
                                onConfirm: function() {
                                    window.opener.Production_Task_NS.query();
                                    window.close();
                                }
                            })
                        } else if (data["resultId"] == "01") {
                            Tools_NS.showWarningDialog("���ֿ����ѱ�ʹ�ã����޸���ʼ���Ż��������", function() {
                                opens();
                            });
                        } else {
                            Tools_NS.showWarningDialog("�ύʧ�ܣ�������", function() {
                                opens();
                            });
                        }
                    } else {
                        Tools_NS.showWarningDialog("�ύʧ�ܣ�������", function() {
                            opens();
                        });
                    }
                },
                dataType: "json"
            });
        },

        fetchProductByCategory : function(category) {
            $.get("product.frm", {method : 'fetch-by-category', category : category}, function(data) {
                if (data) {
                    $("#cpdm").empty();
                    if (data.length > 0) {
                        Create_Production_Task_NS.addOption("cpdm", "", "---��ѡ��---");
                        $.each(data, function(i, product) {
                            Create_Production_Task_NS.addOption("cpdm", product.cpdm, product.cpmc);
                        });
                    } else {
                        Create_Production_Task_NS.addOption("cpdm", "", "---δ�ҵ���Ӧ�Ĳ�Ʒ---");
                    }
                }
            }, "json");
        },

        addOption : function(selectorId, value, text) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//��׷��
            opn.innerText = text;
            opn.value = value;
        },

        queryAvailableKh : function() {
            $.get("<c:url value='/be/inventory.rfid'/>", {method : 'query-available-kh', sf : $("#sf").val(), _t : new Date().getTime()}, function(data) {
                if (data && data["resultId"] == "00") {
                    var khHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list"><tr class="head"><td></td><td>��ʼ����</td><td>��ֹ����</td></tr>';
                    $(data["availableKh"]).each(function(index, ele) {
                        khHtml = khHtml +
                                '<tr class="out available-kh">' +
                                '   <td>' +
                                '       <input name="selectedKh" type="radio" value="' + ele["QSKD"] + '"/>' +
                                '   </td>' +
                                '   <td>' + ele["QSKD"] + '</td>' +
                                '   <td>' + ele["ZZKD"] + '</td>' +
                                '</tr>';
                    });
                    khHtml = khHtml + '</table>';

                    Tools_NS.showConfirmDialog({
                        id: 'queryKhDialog',
                        title: '���ÿ����б�',
                        message: "<div style='height:200px;overflow-y:scroll'>" + khHtml + "</div>",
                        width: 500,
                        cancellable: false,
                        height: 400,
                        onConfirm: function() {
                            Create_Production_Task_NS.chooseQSKH();
                            $.unblockUI();
                        }
                    });
                }
            }, "json");
        },

        chooseQSKH : function() {
            $("#qskh").val($("input:checked[name='selectedKh']").val());
        }

    };

    $(function() {
        Create_Production_Task_NS.init();
    });
    -->
</script>
</body>
</html>