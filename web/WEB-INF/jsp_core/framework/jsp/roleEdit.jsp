<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gb2312" %>
<%@ page import="com.tmri.share.frm.service.*" %>
<%@page import="com.tmri.framework.bean.*" %>
<%@page import="com.tmri.pub.service.SysService" %>
<%@page import="com.tmri.framework.service.RoleManager" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>角色权限维护</title>
    <%
        Role role = (Role) request.getAttribute("frmRole");
        //List foldList = (List)request.getAttribute("foldList");
        //String roleMenus = (String)request.getAttribute("roleMenus");
        String modal = (String) request.getAttribute("modal");
        //String jbs = (String)request.getAttribute("jbs");
        SysService sysService = (SysService) request.getAttribute("sysService");

        GHtmlService gHtmlService = (GHtmlService) request.getAttribute("gHtmlService");
        RoleManager roleManager = (RoleManager) request.getAttribute("roleManager");
        List roleList = (List) request.getAttribute("roleList");
        List jbslist = (List) request.getAttribute("jbslist");
    %>
</head>
<link href="theme/domtab/domtab.css" rel="stylesheet" type="text/css">
<body>
<div class="s1" style="height:4px;"></div>
<form name="formedit" id="formedit" action="" method="post" target="paramIframe">
    <input type="hidden" name="jsdh" value="<%=role.getJsdh()%>">
    <input type="hidden" name="cxdh">
    <input type="hidden" name="jssx" value="<%=role.getJssx()%>">
    <input type="hidden" name="jscj" value="<%=role.getJscj()%>">
    <input type="hidden" name="glbm" value="<%=role.getGlbm()%>">
    <input type="hidden" name="modal" value="<%=modal%>">
    <%=JspSuport.getInstance().getWebKey(session)%>
    <table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">

        <tr>
            <td class="list_headrig" width="10%">角色名称</td>
            <td class="list_body_out" align="left"><input type="text" name="jsmc" class="text_12 text_size_150"
                                                          value="<%=role.getJsmc()%>"/></td>
            <td class="list_headrig" width="10%">角色级别</td>
            <td class="list_body_out" align="left">
                <%=gHtmlService.transListToCheckBoxHtml(jbslist, "jscj1", role.getJscj(), "3")%>
            </td>
        </tr>
        <tr>
            <td class="list_headrig">上级角色代号</td>
            <td class="list_body_out" align="left"><select name="sjjsdh" class="text_12 text_size_150"
                                                           onchange="Edit_Role_NS.changejscj();">
                <%=gHtmlService.transListToOptionHtml(roleList, role.getSjjsdh(), false, "3")%>
            </select></td>

            <td class="list_headrig">角色属性</td>
            <td class="list_body_out" align="left"><input type="text" readOnly name="jssxmc"
                                                          class="text_12 text_size_150"
                                                          value="<%=roleManager.getJssx(role.getJssx())%>"></td>
        </tr>
        <tr>
            <td class="list_headrig">角色权限</td>
            <td class="list_body_out" colspan="3" id="divroles" height="350" valign="top"></td>
        </tr>
        <tr>
            <td class="list_headrig">备　　注</td>
            <td class="list_body_out" colspan="3" align="left"><input type="text" name="bz" style="width:98%"
                                                                      value="<%=role.getBz()%>"></td>
        </tr>
        <tr>
            <td colspan="4" class="list_body_out" align="right">
                <input type="button" id="selectAllBtn" value=" 全 选 " class="button">
                <input type="button" id="resetBtn" value=" 清 空 " class="button">
                <input type="button" id="saveBtn" value=" 保 存 " class="button">
                <% if (role.getJsdh().length() > 0) { %>
                <input type="button" id="delBtn" value=" 删 除 " class="button">
                <input type="button" id="getHisBtn" value=" 查看历史 " class="button">
                <% } %>
                <input type="button" value=" 退 出 " onclick="quit()" class="button">
            </td>
        </tr>
    </table>
</form>
<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript" type="text/javascript" src="theme/domtab/domtab.js"></script>
<script language="javascript">
    var ckflag = 1;
    function check_item(obj) {
        var bj = obj.value;
        var qx_length = document.all["cddh"].length;
        if (obj.checked == true) {
            for (var i = 0; i < qx_length; i++) {
                if (document.all["cddh"].item(i).value.indexOf(bj) == 0) {
                    document.all["cddh"].item(i).checked = true;
                }
            }
        } else {
            for (var i = 0; i < qx_length; i++) {
                if (document.all["cddh"].item(i).value.indexOf(bj) == 0) {
                    document.all["cddh"].item(i).checked = false;
                }
            }
        }
    }
    function resultsubmit (code, result, message) {
        if (code == '1') {
            displayInfoHtml(message);
            formedit.jsdh.value = result;
            Edit_Role_NS.setedit();
            formedit.modal.value = "edit";
            window.opener.query();
        } else if (code == '2') {
            displayInfoHtml(message);
            Edit_Role_NS.setadd();
            formedit.jsdh.value = '';
            window.opener.query();
        } else {
            displayInfoHtml(message);
        }
    }
    var Edit_Role_NS = {
        jssx: '<%=role.getJssx()%>',
        jsdh: '<%=role.getJsdh()%>',
        init: function () {
            var tmpsx;
            var i = 0;
            checkfields[i++] = new CheckObj("jsmc", "角色名称", FRM_CHECK_NULL, 0, 1);
            checkfields[i++] = new CheckObj("cxdh", "角色权限", FRM_CHECK_NULL, 0, 0);
            checklen = i;

            if (Edit_Role_NS.jssx == "1") {
                Edit_Role_NS.setview();
            } else {
                if (Edit_Role_NS.jsdh == '') {
                    Edit_Role_NS.setadd();
                } else {
                    Edit_Role_NS.setedit();
                }
            }

            var jscj = document.getElementsByName('jscj1');
            for(i=0; i<jscj.length; i++) {
                $(jscj[i]).click(Edit_Role_NS.changejscj);
            }

            Edit_Role_NS.changejscj();

            $("#getHisBtn").click(Edit_Role_NS.gethis);

            $("#selectAllBtn").click(Edit_Role_NS.chooseselect);

            $("#delBtn").click(Edit_Role_NS.del);

            $("#resetBtn").click(Edit_Role_NS.resetForm);

            $("#saveBtn").click(Edit_Role_NS.save);
        },

        del: function () {
            if (confirm("是否确认删除该角色？")) {
                if (confirm("将会删除该角色及其子角色，该角色及其子角色对应权限，"
                                + "用户拥有该角色及其子角色的操作权限，用户拥有该角色及其子角色的管理权限，"
                                + "再次确认是否删除?")) {
                    document.formedit.action = "role.frm?method=removeRole";
                    document.formedit.submit();
                }
            }
        },

        save: function () {
            var yhzs = "";
            var cddhs = "";
            var tmpsx = "";
            var tmpsx1 = "";
            var tmpsx2 = "";
            var j = 0;
            var k = 0;
            if (document.all.cddh) {
                if (document.all.cddh.type == "checkbox") {
                    //单个
                    if (document.all["cddh"].checked) {
                        tmpsx = document.all["cddh"].value;
                        j = tmpsx.indexOf("-");
                        cddhs = tmpsx.substring(j + 1);
                    } else {
                        cddhs = "";
                    }
                } else {
                    var cddh_length = document.all["cddh"].length;
                    for (var i = 0; i < cddh_length; i++) {
                        if (document.all["cddh"].item(i).checked == true) {
                            tmpsx = document.all["cddh"].item(i).value;
                            j = tmpsx.indexOf("-");
                            tmpsx = tmpsx.substring(j + 1);
                            j = getCharCounts(tmpsx, "-");
                            if (j == 1) {
                                j = cddhs.indexOf(tmpsx);
                                if (j < 0) {
                                    cddhs = cddhs + tmpsx + "-#";
                                }
                            } else {
                                j = tmpsx.lastIndexOf("-");
                                tmpsx1 = tmpsx.substring(0, j + 1);
                                tmpsx2 = tmpsx.substring(j + 1);
                                j = cddhs.indexOf(tmpsx1);
                                if (j < 0) {
                                    cddhs = cddhs + tmpsx + "#";
                                } else {
                                    k = cddhs.indexOf("#", j);
                                    tmpsx1 = cddhs.substring(j, k);
                                    if (tmpsx1.substring(tmpsx1.length - 1) == "-") {
                                        tmpsx1 = tmpsx1 + tmpsx2;
                                    } else {
                                        tmpsx1 = tmpsx1 + "," + tmpsx2;
                                    }
                                    cddhs = cddhs.substring(0, j) + tmpsx1 + cddhs.substring(k);
                                }
                            }
                        }
                    }
                    if (cddhs.substring(cddhs.length - 1) == "#") {
                        cddhs = cddhs.substring(0, cddhs.length - 1);
                    }
                }
            }
            document.all["cxdh"].value = cddhs;
            if (checkallfields(checkfields, 1) == 0) {
                return 0;
            }
            var jscj = getckvalue(formedit.jscj1, 'A');
            formedit.jscj.value = jscj;
            $.post("<c:url value='role.frm?method=saveRole'/>", $("#formedit").serialize(), function(data) {
                if (data) {
                    resultsubmit(data["resultId"], data["resultMsg"], data["resultMsg1"]);
                }
            }, "json");
            return;
        },

        setadd: function () {
            $("#saveBtn").attr("disabled", false);
            $("#delBtn").attr("disabled", true);
        },

        setedit: function () {
            $("#saveBtn").attr("disabled", false);
            $("#delBtn").attr("disabled", false);
        },

        setview: function () {
            $("#saveBtn").attr("disabled", true);
            $("#delBtn").attr("disabled", true);
        },

        resetForm: function () {
            Edit_Role_NS.setadd();
            formedit.jsdh.value = '';
            formedit.jsmc.value = '';
            formedit.jsmc.readOnly = false;
            formedit.modal.value = "new";
        },

        //改变属性层级
        changemenu: function (sjjsdh, jsdh, jscj, jssx) {
            var spath = "role.frm?method=queryProgramListByJscj&sjjsdh=" + sjjsdh + "&jsdh=" + jsdh + "&jscj=" + jscj + "&jssx=" + jssx;
            divroles.innerHTML = "";
            send_request(spath, "Edit_Role_NS.filljscj()", false);
        },

        changejscj: function () {
            var sjjsdh = formedit.sjjsdh.value;
            var jsdh = formedit.jsdh.value;
            var jscj = getckvalue(formedit.jscj1, 'A');
            var jssx = formedit.jssx.value;
            Edit_Role_NS.changemenu(sjjsdh, jsdh, jscj, jssx);
        },
        filljscj: function () {
            //处理管辖部门
            var results = _xmlHttpRequestObj.responseText;
            divroles.innerHTML = results;
            domtab.init();
        },

        chooseselect: function () {
            if (ckflag == 1) {
                selectck(formedit.cddh);
                $("#selectAllBtn").val(" 全不选 ");
                ckflag = 2;
            } else {
                clearck(formedit.cddh);
                $("#selectAllBtn").val(" 全选 ");
                ckflag = 1;
            }
        },

        tab_c: function (idx) {
            var tab = document.getElementsByName("tab")
            for (var i = 0; i < tab.length; i++) {
                tab[i].style.display = "none";
            }
            var idxtab = document.getElementById("tab" + idx);
            idxtab.style.display = "";
        },

        gethis: function () {
            if (jsdh == '') {
                alert("无角色编号，无法查看历史");
                return false;
            }
            var jsdh = formedit.jsdh.value;
            var url = "log.frm?method=logfrmresulttrack&isxls=5&jsdh=" + jsdh;
            var sFeatures = "dialogHeight:600px;dialogWidth:980px;help:no;status:no ";
            var vReturnValue = window.showModalDialog(url, "", sFeatures);
        }
    }
    $(function () {
        Edit_Role_NS.init();
    });
</script>
</body>
</html>