<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>交警用户管理</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="css/xtree.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
    <style type="text/css">
        .detail .body input[type='text'].ip_3{
            width: 25px;
            text-align: right;
        }
        .detail .body input[type='text'].ip_2{
            width: 20px;
            text-align: right;
        }
        .liqx{
            width:170px;
            float:left;
        }
        .fieldsetqx{
            font-size:12px;
            border:solid 1px #CCCCCC;
            margin-top:6px;
            margin-bottom:6px;
            padding:4px;
        }
    </style>
</head>
<body>
<div id="panel">
    <div id="paneltitle">交警用户维护</div>
    <table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
        <tr id="tag_console">
            <td class="tag_head_front"></td>
            <td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">用户信息</td>
            <td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
        </tr>
    </table>

    <div id="tagpanel1" style="display:none;" class="tag_body_table">
        <form id="myform" name="myform" action="">
            <table border="0" cellspacing="1" cellpadding="0" class="detail">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="24%">
                <col width="10%">
                <col width="23%">
                <tr>
                    <td class="head">管理部门</td>
                    <td class="body"><input type="text" id="glbm" name="glbm" value="<c:out value='${dept.glbm}'/>" readonly></td>
                    <td class="head">部门名称</td>
                    <td class="body"><input type="text" id="bmmc" name="bmmc" value="<c:out value='${dept.bmmc}'/>" readonly></td>
                    <td class="head">人员类型</td>
                    <td class="body">
                        <select id="sfmj" name="sfmj" style="width:100%;">
                            <c:forEach items="${sfmjCodeList}" var="current">
                                <option value="<c:out value='${current.dmz}'/>"><c:out value='${current.dmsm1}'/></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="head">姓名</td>
                    <td class="body"><input type="text" id="xm" name="xm"/></td>
                    <td class="head">用户代号</td>
                    <td class="body"><input type="text" id="yhdh" name="yhdh"/></td>
                    <td class="head">身份证号</td>
                    <td class="body"><input type="text" id="sfzmhm" name="sfzmhm"/></td>
                </tr>
                <tr>
                    <td class="head">默认密码</td>
                    <td class="body"><input type="password" id="mm" name="mm"/></td>
                    <td class="command" colspan="4">
                        <input type="hidden" id="modal" name="modal" value="new">
                        <input type="button" class="button_save" value="保存" onclick="create_cmd()">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<iframe style="display:none;" id="paramIframe" name="paramIframe"></iframe>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function(){
        $.datepicker.setDefaults($.datepicker.regional['']);
        $(".jscal").each(function () {
            eval($(this).html());
        });

        <c:if test="${user.zhyxq != null}">
        $("#zhyxq").val("<c:out value='${user.zhyxq}'/>");
        //$("#zhyxq").attr("readonly", "readonly");
        </c:if>
        <c:if test="${user.mmyxq != null}">
        $("#mmyxq").val("<c:out value='${user.mmyxq}'/>");
        //$("#mmyxq").attr("readonly", "readonly");
        </c:if>

        changepanel(1);

        $("#sfmj").val("<c:out value='${user.sfmj}'/>");
        $("#zt").val("<c:out value='${user.zt}'/>");
        setIpIntoIps("ipks", "<c:out value='${user.ipks}'/>");
        setIpIntoIps("ipjs", "<c:out value='${user.ipjs}'/>");
        setIpIntoIps("mac", "<c:out value='${user.mac}'/>");
        setIpIntoIps("gdip", "<c:out value='${user.gdip}'/>");
        setIpIntoIps("gdip1", "<c:out value='${user.gdip1}'/>");
        setIpIntoIps("gdip2", "<c:out value='${user.gdip2}'/>");

        //设置系统管理员
        $("#xtgly_<c:out value='${user.xtgly}'/>").attr("checked", true);
        checkxtgly();

        /**
         //设置用户授权模式
         var yhsqms = "<c:out value='${yhsqms}'/>";
         if(yhsqms == '1'){//仅角色授权
		$("#qxms_2").attr("disabled", true);
	}
         $("#qxms_<c:out value='${user.qxms}'/>").attr("checked", true);
         */

                //支队用户不可再授权
        var bmjb = "<c:out value='${dept.bmjb}'/>";
        if(bmjb > '4'){
            $("#xtgly_1").attr("disabled", true);
            $("#xtgly_2").attr("checked", true);
        }
    });
    function changepanel(idx){
        for (var i=1; i<=2; i++){
            $("#tagitem" + i).removeClass("tag_head_open").addClass("tag_head_close");
            $("#tagpanel"+i).css("display","none");
        }
        $("#tagitem" + idx).removeClass("tag_head_close").addClass("tag_head_open");
        $("#tagpanel"+idx).css("display","block");
    }
    function checkIpNum(obj, type){
        val = $(obj).val().toUpperCase();
        if(typeof(val) != "undefined" && val != ""){
            var isValid = false;
            if(type == "DEC"){
                reg = /[0-9]{1,3}/;
                if(reg.exec(val) != null){
                    val10 = parseInt(val, 10);
                    if(val10 >= 0 && val10 <= 255){
                        isValid = true;
                    }
                }
            }else if(type == "HEX"){
                reg = /[A-F,0-9]{2}/;
                if(reg.exec(val) != null){
                    val16 = parseInt(val, 16);
                    if(val16 >= 0 && val16 <= 255){
                        isValid = true;
                    }
                }
            }else if(type =="BIN"){
                reg = /[0-1]{8}/;
                if(reg.exec(val) != null){
                    val2 = parseInt(val, 2);
                    if(val2 >= 0 && val12 <= 255){
                        isValid = true;
                    }
                }
            }
            if(!isValid){
                alert("IP地址输入不正确!");
                $(obj).focus();
            }else{
                $(obj).val(val);
                setIpsIntoIp(obj);
            }
        }
    }
    function setIpsIntoIp(obj){
        idName = $(obj).attr("id");
        ipID = idName.substring(0, idName.length-2);
        ipsAry = $("input[name=t" + ipID + "]");
        ipVal = "";
        for(i = 0; i < ipsAry.length; i++){
            tIpVal = ipsAry[i].value;
            if(tIpVal == 'undefined' || tIpVal == null || tIpVal == ''){
                tIpVal = "";
            }
            ipVal += "." + tIpVal;
        }
        ipVal = ipVal.substr(1);
        $("#" + ipID).val(ipVal);
    }
    function setIpIntoIps(ipID, ipVal){
        $("#" + ipID).val(ipVal);
        ips = ipVal.split(".");
        for(i = 0; i < ips.length; i++){
            $("#" + ipID + "_" + (i + 1)).val(ips[i]);
        }
    }
    function datediff(s, e) {
        var arr = s.split("-");
        var starttime = new Date(arr[0], arr[1], arr[2]);
        var starttimes = starttime.getTime();

        var arrs = e.split("-");
        var lktime = new Date(arrs[0], arrs[1], arrs[2]);
        var lktimes = lktime.getTime();

        if (starttimes >= lktimes) {
            return true;
        }

        return false;
    }

    function create_cmd(){
        if(!checkNull($("#xm"),"姓名 ")) return false;
        if(!checkNull($("#mm"),"密码 ")) return false;
        if(!checkNull($("#yhdh"),"用户代号")) return false;
        if(!checkLength($("#yhdh"), 10, "用户代号")) return false;
        if(!checkNull($("#sfzmhm"),"身份证明号码 ")) return false;
        if(!checkLength($("#sfzmhm"), 20, "身份证明号码")) return false;

        if(confirm("是否确认创建用户信息？")){
            closes();
            $.ajax({
                url: "<c:url value='/sysuser.vmc?method=createSysuser'/>",
                type: "post",
                async: "false",
                data: $("#myform").serialize(),
                dataType: "json",
                success: function(data) {
                    if (data["resultId"] == "00") {
                        alert("创建成功");
                        window.opener.query_cmd();
                        window.close();

                    } else {
                        alert("创建失败：" + data["resultMsg"]);
                        opens();
                    }
                }
            });
        }
    }

    function unlockSysuser(){
        if(confirm("是否确认解锁该用户，解锁后窗口将关闭？")){
            $("#myform").attr("action","<c:url value='/sysuser.vmc?method=unlockSysuser'/>");
            $("#myform").attr("method","post");
            $("#myform").attr("target","paramIframe");
            closes();
            $("#myform").submit();
        }
    }

    function doSaveSysuserReturns(data){
        opens();
        jsonData = eval("(" + data + ")");
        if(jsonData.code == '1'){
            alert("保存成功！");
        }else if(jsonData.code == '2'){
            alert("保存成功！");
            window.close();
        }else{
            alert("保存失败！" + decodeURI(jsonData.msg));
        }
    }
    function resetSysuserPwd(){
        if(confirm("是否确认重置用户（" + $("#xm").val() + "）的密码？")){
            $("#myform").attr("action","<c:url value='/sysuser.vmc?method=resetSysuserPwd'/>");
            closes();
            $("#myform").attr("method","post");
            $("#myform").attr("target","paramIframe");
            $("#myform").submit();
        }
    }
    function doResetSysuserPwdReturns(data){
        opens();
        jsonData = eval("(" + data + ")");
        if(jsonData.code == '1'){
            alert("重置密码成功！");
        }else{
            alert("重置密码失败！" + decodeURI(jsonData.msg));
        }
    }
    function checkxtgly(){
        var xtgly = $("input[name=xtgly][checked=true]").val();
        if(xtgly == 1){
            //系统管理员:是
//		setckenabled(formedit.yyyhz);
//		setckywlxabled(formedit.kgywyhlx1,'');
//		document.getElementById("ksqx").style.display="";
//		document.getElementById("divkgywyhlx").style.display="";
            $("#kglqxTR").css("display", "block");
        }else if(xtgly == 2){
            //系统管理员:否
            //重置角色选择
            //clearck(formedit.yyyhz);
//		setckdisabled(formedit.yyyhz);
//		setckdisabled(formedit.kgywyhlx1);
//		document.getElementById("ksqx").style.display="none";
//		document.getElementById("divkgywyhlx").style.display="none";
            $("#kglqxTR").css("display", "none");
        }
    }
    function checkQxms(){
        /**
         var oqxms=getradio(formedit.oqxms);
         if(oqxms == 1){//角色
		formedit.btyhz.disabled=false;
		divroles.style.display = "block";
		divcxdhs.style.display = "none";
		divcxdhs.innerHTML="";
	}else{//自由
		formedit.btyhz.disabled=true;
		divroles.style.display = "none";
		divcxdhs.style.display = "block";
		//重置菜单目录
		getusermenu(2);
	}
         */
        var oqxms = $("input[name=qxms][checked=true]").val();
        if(oqxms == 1){//角色
            $("#btn_kczqx").attr("disabled", false);
        }else{//自由
            $("#btn_kczqx").attr("disabled", true);
        }
    }
    function showRoleMenu(jsdh, glbm){
        var curUrl = "<c:url value='sysuser.frm?method=rolemenuresult&glbm='/>" + glbm + "&jsdh=" + jsdh;
        window.showModalDialog(curUrl, "dialogWidth:800;dialogHeight:300;help:no;status:no");
    }

    function showAllRoleMenu(type){
        var roles = "";
        if(type == 1){
            //可管理权限
            var kglqxAry = $("input[name=kglqx][checked=true]");
            for(i = 0; i < kglqxAry.size(); i++){
                roles += "A" + $(kglqxAry[i]).val();
            }

        }else{
            //可操作权限
            var kczqxAry = $("input[name=kczqx][checked=true]");
            for(i = 0; i < kczqxAry.size(); i++){
                roles += "A" + $(kczqxAry[i]).val();
            }
        }
        if(roles == ""){
            alert("未选择角色！");
            return;
        }
        roles = roles.substr(1);
        var sFeatures="dialogHeight:400px;dialogWidth:700px;help:no;status:no;scroll:no;";
        var vReturnValue = window.showModalDialog("sysuser.frm?method=rolemenuresult&glbm="+"<c:out value='${cglbm}'/>"+"&jsdh="+roles, "",sFeatures);
    }

    //保存用户权限信息
    function saveuserrole() {
        //新增用户，需先保存用户信息
        /**
         var modal=formedit.modal.value;
         if(modal=='new'){
		displayInfoHtml("需先保存用户信息后，再进行授权！");
		return false;
	}*/
        var t_glbm = $("#glbm").val();
        var t_yhdh = $("#yhdh").val();
        if(t_glbm == '' || t_yhdh == ''){
            alert("需要保存用户信息后，再进行授权！");
            return false;
        }

        var t_xtgly = $("input[name=xtgly][checked=true]").val();
        if(t_xtgly == 1){
            //系统管理员
            if($("input[name=kglqx][checked=true]").size() <= 0){
                alert("请选择可管理权限！")
                return false;
            }
        }
        closes();
        curUrl = "<c:url value='sysuser.vmc?method=saveSysuserRole&glbm='/>" + t_glbm + "&yhdh=" + t_yhdh;
        $("#roleform").attr("action", curUrl);
        $("#roleform").attr("method","post");
        $("#roleform").attr("target","paramIframe");
        $("#roleform").submit();
    }
    function doSaveSysuserRoleReturns(data){
        opens();
        jsonData = eval("(" + data + ")");
        if(jsonData.code == '1'){
            alert("保存成功！");
        }else{
            alert("保存失败！" + decodeURI(jsonData.msg));
        }
    }
    //-->
</script>
</body>
</html>
