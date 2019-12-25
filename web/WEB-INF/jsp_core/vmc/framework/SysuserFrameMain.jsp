<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>交警用户管理</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/xtree.css">
</head>
<body>
<div>
	<div style="width:24%;left:0;top:0;position:absolute;height:100%">
		<fieldset id="fld" style="border:1px solid #CCCCCC;height:100%">
			<legend style="font-size: 12px;">部门选择</legend>
			<iframe src="" name="ifrm_deplist" id="ifrm_deplist" marginwidth="1" marginheight="1" hspace="0" vspace="0"
					scrolling="yes" frameborder="0" height="100%" width="100%">
			</iframe>
		</fieldset>
	</div>
	<div style="width:74%;right:0;top:0;position:absolute;height:100%">
		<iframe src="" name="ifrm_userlist" id="ifrm_userlist" marginwidth="1" marginheight="1" hspace="0" vspace="0"
				scrolling="auto" frameborder="0"  height="100%" width="100%">
		</iframe>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script type="text/javascript">
    var SysUserFrameMain_NS = {

        initSize : function() {
           /* var ch = document.body.clientHeight - 16;
            $("#ifrm_deplist").css("height", ch);
            $("#ifrm_userlist").css("height", ch);*/
        }

    };

    $(document).ready(function(){
        $("#ifrm_deplist").attr("src", "sysuser.frm?method=queryGlbmYwlbList");
        SysUserFrameMain_NS.initSize();
        window.onresize=function() {
            SysUserFrameMain_NS.initSize();
        };
    });

    function editGlbm(s_glbm){
        $("#ifrm_userlist").attr("src", "sysuser.vmc?method=fwdSysUserMain&glbm=" + s_glbm);
    }

</script>
</body>
</html>
