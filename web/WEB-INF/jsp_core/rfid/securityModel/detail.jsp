<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<!DOCTYPE html>
<head>
    <title>安全模块详情</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
          media="screen" title="Flora (Default)" />
    <link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
          type="text/css">
</head>
<body>
<div id="panel" style="display: none">
    <div id="paneltitle">
        安全模块详情
    </div>
    <div id="materialApply">
        <form action="" method="post" name="securityModelForm" id="securityModelForm">
            <input type="hidden" id="xh" name="xh" value="${bean.xh}"/>
            <div id="block">
                <div id="blocktitle">安全模块详情</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="10%">
                    <col width="20%">
                    <col width="10%">
                    <col width="20%">
                    <col width="10%">
                    <col width="20%">
                    <tr>
                        <td class="head">序列号</td>
                        <td class="body">
                                <c:out value='${bean.xh}'/>
                        </td>
                        <td class="head">类型</td>
                        <td class="body">
                            <c:forEach var="type" items="${securityModelType}">
                                <c:if test="${type.dm == bean.lx}">
                                       <c:out value="${type.mc}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                        <td class="head">权限类型</td>
                        <td class="body">
                                <c:out value='${bean.qulx}'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">芯片1公钥</td>
                        <td class="body" colspan="5">
                            <c:out value='${bean.xp1gy}'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">芯片2公钥</td>
                        <td class="body" colspan="5">
                            <c:out value='${bean.xp2gy}'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">CA公钥</td>
                        <td class="body" colspan="5">
                            <c:out value='${bean.cagy}'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">CA公钥索引号</td>
                        <td class="body">
                            <c:out value='${bean.cagysyh}'/>
                        </td>
                        <td class="body" colspan="4"></td>
                    </tr>
                    <tr>
                        <td class="head">芯片1认证加密密钥版本</td>
                        <td class="body">
                            <c:out value='${bean.xp1mybb}'/>
                        </td>
                        <td class="head">芯片1用户程序版本号</td>
                        <td class="body">
                            <c:out value='${bean.xp1yhcxbb}'/>
                        </td>
                        <td class="head">芯片2用户程序版本号</td>
                        <td class="body">
                            <c:out value='${bean.xp2yhcxbb}'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">STM32固件版本</td>
                        <td class="body">
                            <c:out value='${bean.stm32gjbb}'/>
                        </td>
                        <td class="head">电路板版本号</td>
                        <td class="body">
                            <c:out value='${bean.dlbbb}'/>
                        </td>
                        <td class="head">出厂日期</td>
                        <td class="body">
                            <fmt:formatDate value="${bean.ccrq}" type="date"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">初始化日期</td>
                        <td class="body">
                            <fmt:formatDate value="${bean.cshrq}" type="both"/>
                        </td>
                        <td class="head">操作人</td>
                        <td class="body">
                            <c:out value='${bean.czr}'/>
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                    <tr>
                        <td class="head">上次升级日期</td>
                        <td class="body">
                            <fmt:formatDate value="${bean.scsjrq}" type="both"/>
                        </td>
                        <td class="head">上次升级操作人</td>
                        <td class="body">
                            <c:out value='${bean.scsjczr}'/>
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
                    </td>
                </tr>
            </table>
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

    var Edit_Order_Application_NS = {
        init : function() {
            
        }
    };

    $(function() {
        Edit_Order_Application_NS.init();
    });
</script>
</body>
</html>