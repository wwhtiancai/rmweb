<%@ page contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript">
function reloadImage(){
  window.location.reload();
}
</script>
<style type="text/css">
body{
font-size:12px;
}
a{
text-decoration:none;
}
</style>
</head>
<body>
<div style="position:absolute; z-index:1; left:0px; top: 0px;">
      <img id="image1" name ="image1" border=0 src="login.frm?method=getVadidateImage"></img>
</div>
<div style="position:absolute; z-index:1; left:82px; top: 18px;">
    <a href="javascript:reloadImage();"><font color="red">��һ��</font></a>
</div>
</body>
</html>