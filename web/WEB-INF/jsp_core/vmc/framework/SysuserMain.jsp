<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>用户管理</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/xtree.css">
</head>
<body onUnload="closesubwin();">
<div id="query">
	<form action="" method="post" name="myform" id="myform">
	<input type="hidden" id="txtlb" name="txtlb" value=""/>
	<input type="hidden" id="tcdbh" name="tcdbh" value=""/>
	<input type="hidden" id="tgnid" name="tgnid" value=""/>
	<input type="hidden" id="tjsdh" name="tjsdh" value=""/>
	<input type="hidden" id="czqx" name="czqx" value=""/>
	<table border="0" cellspacing="1" cellpadding="0" width="100%" class="query">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="34%">
		<tr>
			<td class="head"><input type="hidden" id="glbm" name="glbm" value="">管理部门</td>
			<td class="body"><input type="text" id="bmmc" name="bmmc" value="" readonly style="width:99%"></td>
			<td class="head">用户代号</td>
			<td class="body"><input type="text" id="yhdh" name="yhdh" value="" style="width:99%" maxlength="6"></td>
			<td class="head">用户姓名</td>
			<td class="body"><input type="text" id="xm" name="xm" maxlength="50" style="width:99%;"></td>
		</tr>
		<tr>
			<td class="head">管理员</td>
			<td class="body">
				<select id="xtgly" name="xtgly" style="width:100%;">
					<option value="">全部</option>
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
			</td>
			<td class="head">操作类型</td>
			<td class="body">
				<input type="radio"	name="sfsq" value="" checked>全部
				<input type="radio" name="sfsq" value="1">已授权
				<input type="radio" name="sfsq" value="2">未授权
			</td>
			<td class="head">包括下级部门</td>
			<td class="body"><input type="checkbox" name="bhxj" value="1"></td>
		</tr>
		<tr>
			<td class="head">权限类型</td>
			<td class="body" colspan="3">
				<select id="qxms" name="qxms" style="width:100px;">
					<option value="1" selected>操作权限</option>
					<option value="2">管理权限</option>
				</select>
				<select id="sqms" name="sqms" style="width:100px;" onchange="changechoose(this.value);">
					<option value="1" selected>角色</option>
					<option value="2">功能</option>
				</select>
				<input type="text" id="cxmc" name="cxmc" maxlength="50" style="width:250px;" readOnly>
				<input type=button value="选择" onclick="fnOpenCxmc();">
				<input type=button value="清空" onclick="clearCxmc();">
			</td>
			<td class="head">排序方式</td>
			<td class="body">
				<select name="order" style="width:48%">
					<option value="yhdh" selected>用户代号</option>
					<option value="xm">姓名</option>
					<option value="sfzmhm">身份证明号码</option>
				</select>
				<select name="proper" style="width:48%">
					<option value="asc" selected>升序</option>
					<option value="desc">降序</option>
				</select>
			</td>
		</tr>
		<tr>

			<td class="command" colspan="6">
				<c:if test="${czyh.xtgly == '1'}">
					<input type="button" class="button_new" value="添加" onclick="add_cmd()"/>
				</c:if>
				<input type="button" class="button_query" value="查询" onclick="query_cmd()">
			</td>
		</tr>
	</table>
	</form>
</div>
<div class="queryresult"></div>
<iframe src="" name="ifrm_sysuserlist" id="ifrm_sysuserlist" marginwidth="1" marginheight="1" hspace="0" vspace="0" 
	scrolling="auto" frameborder="0" style="width:100%;height:460px;">
</iframe>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript">
    <!--
    $(document).ready(function(){
        <c:if test="${dept != null}">
        $("#glbm").val('<c:out value="${dept.glbm}"/>');
        $("#bmmc").val('<c:out value="${dept.bmmc}"/>');
        </c:if>
        <c:if test="${cmd != null}">

        </c:if>
        $("[limit]").limit();
    });
    function doUserSync(){
        if($("#glbm").val() == '' || $("#bmmc").val() == ''){
            alert('请选择管理部门！');
            return;
        }
        curUrl = "<c:url value='sysuser.vmc?method=getSysuserFromTrff&glbm='/>" + $("#glbm").val();
        if($("#yhdh").val() != ''){
            curUrl = curUrl + "&yhdh=" + $("#yhdh").val();
        }
        $.ajax({
            type: "GET",
            url:curUrl,
            cache:false,
            async:false,
            dataType:"json",
            success: function(data){
                if(data.code == '1'){
                    alert("同步成功！");
                }else{
                    alert(decodeURI(data.msg));
                }
            }
        });
    }
    function query_cmd(){
        if(!doChecking()) return;
        $("#myform").attr("target", "ifrm_sysuserlist");
        $("#myform").attr("action","<c:url value='/sysuser.vmc?method=querySysUser'/>");
        //closes();
        $("#myform").submit();
    }
    function doChecking(){
        return true;
    }
    function editone(glbm, yhdh){
        curUrl = "<c:url value='sysuser.vmc?method=editSysuser&modal=1&glbm='/>" + glbm + "&yhdh="+yhdh;
        openwin1024(curUrl, "SysuserEdit", false);
    }
    function fnOpenCxmc(){
        var sqms = $("#sqms").val();
        var sFeatures="dialogHeight:600px;dialogWidth:800px;help:no;status:no ";
        var vReturnValue = window.showModalDialog("sysuser.frm?method=choosecdbhquery&sqms="+sqms, "",sFeatures);
        if(typeof vReturnValue != "undefined"){
            if(sqms=='1'){
                changejsmc(vReturnValue);
            }else if(sqms=='2'){
                changecxmc(vReturnValue);
            }
        }
    }
    function clearCxmc(){
        $("#cxmc").val("");
        $("#tcdbh").val("");
        $("#tgnid").val("");
        $("#tjsdh").val("");
        $("#txtlb").val("");
    }
    function changechoose(val){
        clearCxmc();
    }
    function changejsmc(vReturnValue){
        var valarray = vReturnValue.split("$");
        $("#tjsdh").val(valarray[0]);
        $("#cxmc").val(valarray[1]);
    }
    function changecxmc(vReturnValue){
        $("#tcdbh").val("");
        $("#tgnid").val("");
        var valarray = vReturnValue.split("$");
        var xtlb=valarray[0];
        $("#cxmc").val(valarray[2]);

        //判断是cdbh还是gnid,增驾系统类别
        $("#txtlb").val(xtlb);
        if(xtlb.length == 2){
            $("#tcdbh").val(valarray[1]);
        }else{
            $("#tgnid").val(valarray[1]);
        }
    }

    function add_cmd() {
		openwin1024("<c:url value='sysuser.vmc?method=createSysuser&modal=1&glbm='/>" + $("#glbm").val(), "SysuserCreate", false);
	}
    //-->
</script>
</body>
</html>
