<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312" %>
<html>
<head>
    <title>${cxmc}</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<form action="" method="post" name="myform" id="myform">
    <div id="panel" style="display:none">
        <div id="paneltitle">${cxmc}</div>
        <div id="block">
            <div id="blocktitle">��Ӳ���</div>
            <div id="blockmargin">8</div>
            <input type="hidden" id="jzlx" name="jzlx" value="17"/>
            <table border="0" cellspacing="1" cellpadding="0" class="detail">
                <col width="10%">
                <col width="38%">
                <col width="10%">
                <col width="38%">
                <tr>
                    <td class="head">�ϼ�������</td>
                    <td class="body">
                        <h:managementbox list='${bmlist}' id='sjbm' haveNull='1' cssStyle="width:50%"
                                         onChange="getChange()"/>
                    </td>
                    <td class="head">�ϼ����ż���</td>
                    <td class="body">
                        <input type="text" id="sjjbmc" name="sjjbmc" readonly style="width:50%"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">���Ŵ���</td>
                    <td class="body">
                        <input type="text" id="glbm" name="glbm" style="width:50%" readOnly>
                    </td>
                    <td class="head">���ż���</td>
                    <td class="body">
                        <input type="text" id="jbmc" name="sjjbmc" readonly style="width:50%"/>
                        <input type="hidden" id="bmjb" name="bmjb"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">��������</td>
                    <td class="body">
                        <input type="text" id="bmmc" name="bmmc" style="width:100%">
                    </td>
                    <td class="head">����ȫ��</td>
                    <td class="body">
                        <input type="text" id="bmqc" name="bmqc" style="width:100%">
                    </td>
                </tr>
                <tr>
                    <td class="head">������</td>
                    <td class="body"><input type="text" id="fzr" name="fzr"></td>
                    <td class="head">��ϵ��</td>
                    <td class="body"><input type="text" id="lxr" name="lxr"></td>
                </tr>
                <tr>
                    <td class="head">��ϵ�绰</td>
                    <td class="body"><input type="text" id="lxdh" name="lxdh"></td>
                    <td class="head">�������</td>
                    <td class="body"><input type="text" id="czhm" name="czhm"></td>
                </tr>
                <tr>
                    <td class="head">��ϵ��ַ</td>
                    <td class="body" colspan="3">
                        <input type="text" id="lxdz" name="lxdz" style="width:100%">
                    </td>
                </tr>

            </table>
        </div>
        <table border="0" cellspacing="0" cellpadding="0" class="detail">
            <tr>
                <td class="command">
                    <input type="button" name="savebutton" id="savebutton" onclick="save()" value="����"
                           class="button_save">
                    <input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�"
                           class="button_close">
                </td>
            </tr>
        </table>
    </div>
</form>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>

<script type="text/javascript">
    $(function () {

    });

    function save() {
        if (!doChecking()) return;

        $("#myform").attr("action", "<c:url value='/department.vmc'/>?method=saveTraffic");
        closes();
        $("#myform").ajaxSubmit({
            dataType: "json",
            async: false,
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            success: returns
        });
    }

    function returns(data) {
        if (data["resultId"] == "00") {
            window.opener.query_cmd();
            alert("����ɹ���");
            window.close();
        } else {
            alert(decodeURIComponent(data["message"]));
            opens();
        }
    }

    function doChecking() {
        if (!checkNull($("#sjbm"), "ͬ�����ܲ���")) return false;
        // if(!checkPrecision($("#glbm"),"12","������")) return false;
        if (!checkNull($("#bmjb"), "���ż���"))    return false;

        if (!checkNull($("#bmmc"), "��������")) return false;
        if (!checkLength($("#bmmc"), "64", "��������")) return false;
        if (!checkNull($("#bmqc"), "����ȫ��")) return false;
        if (!checkLength($("#bmqc"), "128", "����ȫ��")) return false;
        if (!checkLength($("#fzr"), "32", "������")) return false;
        if (!checkNull($("#lxr"), "��ϵ��")) return false;
        if (!checkLength($("#lxr"), "32", "��ϵ��")) return false;
        if (!checkNull($("#lxdh"), "��ϵ�绰")) return false;
        if (!checkLength($("#lxdh"), "50", "��ϵ�绰")) return false;
        if (!checkTel($("#lxdh"), "��ϵ�绰")) return false;
        if (!checkLength($("#czhm"), "50", "�������")) return false;
        if (!checkTel($("#czhm"), "�������")) return false;
        if (!checkLength($("#lxdz"), "128", "��ϵ��ַ")) return false;
        return true;
    }



    function getChange() {
        $("#glbm1").val($("#sjbm").val().substr(0, 8));
        if ($("#sjbm").val().length > 0) {
            curUrl = "department.vmc?method=syncGetBmjb&glbm=" + $("#sjbm").val();
            $.get(curUrl, {}, function (data) {
                $("#bmjb").val(data['bmjb']);
                $("#jbmc").val(decodeURI(data['jbmc']));
                $("#sjjbmc").val(decodeURI(data['sjjbmc']));
                $("#glbm").val(data['bmdm']);
            }, "json");
        } else {
            $("#bmjb").val('');
            $("#jbmc").val('');
        }
    }


</script>
</body>
</html>


