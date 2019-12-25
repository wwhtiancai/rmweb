<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>软件下载</title>
    <link href="/css/rfid.css" type="text/css" rel="stylesheet"/>
    <style tyle="text/css">
        .plugins_list {
            width: 900px;
            position: relative;
            margin-left: -430px;
            left: 50%;
            margin-top: 20px;
        }

        .plugins_list li {
            position: relative;
            background: #fff;
            display: inline;
        }

        .plugins_list .plugin_feed {

        }

        .plugins_list .plugin_feed .right {
            float: right;
        }

        .plugin_feed .txt_version {
            margin-top: 5px;
            color: #a3a3a3;
        }
    </style>
</head>
<body style="margin:0px;background-color:#e5e7e8">
<div class="new_panel" style="height:100%">
    <div class="panel-header">
        <div class="title">
            软件下载
        </div>
    </div>
    <div class="panel-body" style="height:400px;width:100%">
        <ul class="plugins_list clearfix">
            <li class="magnet">
                <div class="plugin_feed">
                    <img src="images/running.gif" class="magnet-thumbnail"/>
                    <div class="magnet-info">
                        <div class="magnet-title">
                            电子标识签注插件
                        </div>
                        <div class="magnet-body">
                            <div class="magnet-tip">
                                用于网页版电子标识签注系统，包含插件及后台连接程序。
                            </div>
                            <div class="download_box">
                                <div class="btn_box" style="position:relative;width:100%">
                                    <a href="/rmweb/system.frm?method=download-plugin&id=customize&version=${eriVersion}" style="float:left" target="_blank">
                                        <i class="ic_win"></i>立即下载</a>
                                    <a href="/rmweb/system.frm?method=download&fileName=readme.doc" style="margin-left:40px;float:left" target="_blank">
                                        <i class="ic_win"></i>操作说明</a>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="txt_version">
                                ${eriVersion}
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>

</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript">
    Plugin_NS = {
        init : function() {
            $("#downloadBtn").click(function() {

            });

            if ($.browser.msie && $.browser.version <= 8.0) {
                $("li").hover(function() {
                    $(this).addClass("lay-on");
                }, function() {
                    $(this).removeClass("lay-on");
                });
            }
        }
    }

    $(function() {
        Plugin_NS.init();
    });

</script>
</body>
</html>