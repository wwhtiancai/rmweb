<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>车辆信息添加</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="css/combo.select.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)"/>
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

        .icon {
            display: inline-block;
            width: 32px;
            height: 32px;
            overflow: hidden;
            opacity: 0.5;
        }

        .icon-capture {
            background: url(images/Camera_32px.png) no-repeat center;
            border-radius: 90px;
            background-color: white;
        }

        .active {
            background-color: gray;
            opacity: 1;
        }

        .cba {
            width: 150px;
            height: 18px;
        }
        .selectlala {
            text-align: left;
            line-height: 20px;
            padding-left: 5px;
            width: 147px;
            font-family:  Arial,verdana,tahoma;
            height: 20px;
        }
        .combo-select {
            margin-bottom: 0;
            border-radius: 0;
        }
        .combo-input {
            padding: 2px;
        }

        input {
            border: 1px #ccc solid;
            padding: 2px;
        }

        select {
            border: 0;
        }
    </style>
</head>
<body class="rfid" >
<div id="panel" style="display:none">
    <div id="paneltitle">车辆信息添加</div>
    <form action="" method="post" name="myform" id="myform">
        <div id="block">
            <div id="blocktitle">车辆信息</div>
            <div id="blockmargin">8</div>
            <input type="hidden" id="xszzp" name="xszzp"/>
            <table border="0" cellspacing="1" cellpadding="0" class="detail">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <tr>
                    <td class="head">号牌种类</td>
                    <td class="body">
                        <select id="hpzl" name="hpzl">
                            <c:forEach var="type" items="${licenceTypes}">
                                <option value="${type.dmz}">${type.dmsm1}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">发证机关</td>
                    <td class="body">
                        <select id="fzjg" name="fzjg">
                            <c:forEach var="fzjg" items="${localFzjg}">
                                <option value="${fzjg}">${fzjg}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">号牌号码</td>
                    <td class="body">
                        <input type="text" id="hphm" name="hphm"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">车辆类型</td>
                    <td class="body">
                        <select id="cllx" name="cllx">
                            <c:forEach var="type" items="${carTypes}">
                                <option value="${type.dmz}">${type.dmsm1}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">车身主颜色</td>
                    <td class="body">
                        <select id="csys" name="csys">
                            <c:forEach var="color" items="${carColors}">
                                <option value="${color.dmz}">${color.dmsm1}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">所有人</td>
                    <td class="body">
                        <input type="text" id="syr" name="syr"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">使用性质</td>
                    <td class="body">
                        <select id="syxz" name="syxz">
                            <c:forEach var="type" items="${usingPurposes}">
                                <option value="${type.dmz}">${type.dmsm1}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">排量</td>
                    <td class="body">
                        <input type="text" id="pl" name="pl"/>
                    </td>
                    <td class="head">总质量</td>
                    <td class="body">
                        <input type="text" id="zzl" name="zzl"/>
                    </td>
                </tr>

                <tr>
                    <td class="head">整备质量</td>
                    <td class="body">
                        <input type="text" id="zbzl" name="zbzl"/>
                    </td>
                    <td class="head">核定载质量</td>
                    <td class="body">
                        <input type="text" id="hdzzl" name="hdzzl"/>
                    </td>
                    <td class="head">核定载客</td>
                    <td class="body">
                        <input type="text" id="hdzk" name="hdzk"/>
                    </td>

                </tr>
                <tr>
                    <td class="head">出厂日期</td>
                    <td class="body">
                        <h:datebox id="ccrq" name="ccrq" showType="1" width="75%"/>
                    </td>
                    <td class="head">检验有效期止</td>
                    <td class="body">
                        <h:datebox id="yxqz" name="yxqz" showType="1" width="75%"/>
                    </td>
                    <td class="head">强制报废期止</td>
                    <td class="body">
                        <h:datebox id="qzbfqz" name="qzbfqz" showType="1" width="75%"/>
                    </td>

                </tr>
                <tr>
                    <td class="head">功率</td>
                    <td class="body">
                        <input type="text" id="gl" name="gl"/>
                    </td>
                    <td class="body" colspan="4"/>
                </tr>
                <tr>
                    <td class="head">
                        行驶证照片
                    </td>
                    <td class="body" colspan="6">
                        <img id="uploadImg" width="320" heigth="240" style="width:320px;height:240px;"/>
                    </td>
                </tr>
            </table>
        </div>

        <table border="0" cellspacing="0" cellpadding="0" class="detail">
            <tr>
                <td class="command">
                    <input class="button_default" type="button" name="captureBtn" id="captureBtn" value="拍摄照片"/>
                    <input type="button" name="submitBtn" id="submitBtn"  disabled="disabled"  value="提交" class="button_default"/>
                    <input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_default">
                </td>
            </tr>
        </table>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="jQuery-webcam-master/jquery.webcam.js"></script>
<script type="text/javascript" src="frmjs/jquery.combo.select.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script language="JavaScript" type="text/javascript">
    var pos = 0;
    var ctx = null;
    var cam = null;
    var width = 320, height = 240;
    var image = null;
    var Create_Vehicle_NS = {

        baseUrl : '<c:url value="/be/image.rfid?method=show"/>',

        clear: function() {
            $("#myform").clearForm();
            $("#uploadImg").attr("src", "");
            $("#submitBtn").attr("disabled", "disabled");
        },

        init : function() {
            $("#cllx, #hpzl, #csys, #syxz, #fzjg").comboSelect();

            $('#yxqz, #ccrq, #qzbfqz').datepicker({
                showOn: 'both',
                buttonImageOnly: true,
                buttonImage: './rmjs/cal/cal.gif',
                buttonText: '选择日期'
            });

            $("#submitBtn").click(function() {
                $.post('<c:url value="/vehicle.frm?method=create"/>', decodeURIComponent($("#myform").serialize(), true),
                        function(data) {
                    if (data) {
                        if (data["resultId"] == "00") {
                            Tools_NS.showConfirmDialog({
                                id : 'successDialog',
                                title : '成功 ',
                                message : '<img src="/rmweb/frmimage/right.gif"/><span style="font-size:18px;font-weight:bold;margin-left:20px">添加成功，是否继续添加其它车辆</span>',
                                width: 400,
                                cancellable: true,
                                onCancel: 'void(0)',
                                onConfirm: function() {
                                    Create_Vehicle_NS.clear();
                                    $.unblockUI();
                                }
                            });
                        } else {
                            Tools_NS.showWarningDialog("添加失败[" + data["resultMsg"] + "]");
                        }
                    } else {
                        Tools_NS.showWarningDialog("上传数据失败,无响应");
                    }

                }, "json")
            });

            $("#captureBtn").click(function () {
                Tools_NS.showConfirmDialog({
                    id: 'resetTaskDialog',
                    title: '拍摄照片 ',
                    message: '<div style="height:500px;position:relative">' +
                    '<div id="webcam" style="position:absolute;top:0;left:0;border:1px solid gray"></div>' +
                    '<div style="position:absolute;top:196px;left:144px;">' +
                    '<a href="javascript:webcam.capture();void(0);">' +
                    '<i class="icon icon-capture inactive"></i>' +
                    '</a>' +
                    '</div>' +
                    '<div style="position:absolute;left:340px;top:0">' +
                    '<canvas width="1600" height="1200" id="canvas" style="border:2px dashed gray;z-index:-1;display:none"></canvas>' +
                    '</div></div>',
                    width: 800,
                    height: 550,
                    onCancel: 'void()',
                    onConfirm: function () {
                        if (canvas.toDataURL) {
                            $.post('<c:url value="/be/image.rfid?method=upload-picture"/>',
                                    {
                                        type: "data",
                                        image: encodeURIComponent(canvas.toDataURL("image/png")),
                                        _t: Date.parse(new Date())
                                    }, function (data) {
                                        if (data && data["resultId"] == "00") {
                                            $("#uploadImg").attr("src", Create_Vehicle_NS.baseUrl + "&path=" + data["imagePath"]);
                                            $("#xszzp").val(data["imagePath"]);
                                            $("#submitBtn").enable();
                                            $("#submitBtn").focus();
                                            Tools_NS.closeDialog();
                                        }
                                    }, "json");
                        } else {
                            $.post('<c:url value="/be/image.rfid?method=upload-picture"/>',
                                    {
                                        type: "pixel",
                                        image: encodeURIComponent(image.join('|')),
                                        _t: Date.parse(new Date())
                                    }, function (data) {
                                        if (data && data["resultId"] == "00") {
                                            $("#uploadImg").attr("src", Create_Vehicle_NS.baseUrl + "&path=" + data["imagePath"]);
                                            $("#xszzp").value(data["imagePath"]);
                                            $("#submitBtn").enable();
                                            $("#submitBtn").focus();
                                            Tools_NS.closeDialog();
                                        }
                                    }, "json");
                        }
                    }
                });

                $("body").append("<div id=\"flash\"></div>");

                var canvas = document.getElementById("canvas");

                if (canvas.getContext) {
                    ctx = document.getElementById("canvas").getContext("2d");
                    ctx.clearRect(0, 0, 1600, 1200);
                }

                if (canvas.toDataURL) {

                    ctx = canvas.getContext("2d");

                    image = ctx.getImageData(0, 0, 1600, 1200);

                    saveCB = function (data) {

                        var col = data.split(";");
                        var img = image;

                        for (var i = 0; i < 1600; i++) {
                            var tmp = parseInt(col[i]);
                            img.data[pos + 0] = (tmp >> 16) & 0xff;
                            img.data[pos + 1] = (tmp >> 8) & 0xff;
                            img.data[pos + 2] = tmp & 0xff;
                            img.data[pos + 3] = 0xff;
                            pos += 4;
                        }

                        if (pos >= 4 * 1600 * 1200) {
                            ctx.putImageData(img, 0, 0);
                            pos = 0;
                        }
                    };

                } else {

                    saveCB = function (data) {
                        image.push(data);

                        pos += 4 * 1600;

                        if (pos >= 4 * 1600 * 1200) {
                            pos = 0;
                        }
                    };
                }

                $(".icon-capture").mouseover(function (data) {
                    $(".icon-capture").addClass("active");
                }).mouseout(function (data) {
                    $(".icon-capture").removeClass("active");
                });

                $("#webcam").webcam({

                    width: 320,
                    height: 240,
                    quality: 100,
                    mode: "callback",
                    swffile: "jQuery-webcam-master/jscam.swf?_t=123213",

                    onTick: function (remain) {

                    },

                    onSave: saveCB,

                    onCapture: function () {
                        webcam.save();
                    },

                    debug: function (type, string) {
                        $("#status").html(type + ": " + string);
                    },

                    onLoad: function () {
                        var cams = webcam.getCameraList();
                        for (var i in cams) {
                            $("#cams").append("<li>" + cams[i] + "</li>");
                        }
                    }
                });

                $("body").append("<div id=\"flash\" style=\"display:none\"></div>");

            });
        }

    }

    $(function() {
        Create_Vehicle_NS.init();
    })
</script>
</body>
</html>
