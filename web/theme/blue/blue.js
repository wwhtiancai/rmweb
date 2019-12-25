var pos=0,count=0;
var u=location.href;
while(count<4){
	pos=u.indexOf('/',pos+1);
	count++;}
u=location.href.substr(0,pos)+"/";
document.write('<style type=text/css>');
document.write('.sub_image { background: url('+u+'/theme/blue/submit.gif);BORDER-TOP-WIDTH:0px; background-position:0px 0px;BORDER-LEFT-WIDTH:0px;border-bottom-width:0px;width:75px;color:#464646;line-height:17px;border-right-width:0px;font-size: 12px;color: #FFFFFF;	text-decoration: none;');

document.write('}');
document.write('</style>');

function getlocation(){
	if(location.pathname.substr(0,1)=="/"){
		return "/"+location.pathname.split("/")[1]+"/";
	}else{
		return "/"+location.pathname.split("/")[0]+"/";
	}
}

function file_title(title){
var s="";
s+='<table width="768" border="0" align="center" cellpadding="0" cellspacing="0">';
s+='<tr><td><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>';
s+='<td width="9" height="30"><img src="'+u+'/theme/blue/gary_r2_c2.gif" width="9" height="30"></td>';
s+='<td width="250" background="'+u+'/theme/blue/gary_r2_c6.gif" class="text_14 text_white text_bold">&nbsp;&nbsp;'+title+'&nbsp;</td>';
s+='<td width="45"><img src="'+u+'/theme/blue/gary_r2_c8.gif" width="45" height="30"></td>';
s+='<td background="'+u+'/theme/blue/gary_r2_c9.jpg">&nbsp;</td>';
s+='<td width="2"><img src="'+u+'/theme/blue/gary_r2_c11.jpg" width="2" height="30"></td>';
s+='</tr></table></td></tr><tr><td><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>';
s+='<td width="2" background="'+u+'/theme/blue/gary_r3_c2.jpg"></td><td align="center" valign="top" bgcolor="E3E2DE">';
s+='<table width="764" border="0" cellspacing="0" cellpadding="0"><tr><td class="size_1">';
document.write(s);
}

function file_down(){
var s="";
s+='</td></tr></table></td><td width="2" background="'+u+'/theme/blue/gary_r3_c11.jpg"></td>';
s+='</tr></table></td></tr><tr>';
s+='<td><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>';
s+='<td width="2" height="2"><img src="'+u+'/theme/blue/gary_r5_c2.jpg" width="2" height="2"></td>';
s+='<td height="2" background="'+u+'/theme/blue/gary_r5_c3.jpg"><img src="'+u+'/theme/blue/spacer.gif" width="1" height="1"></td>';
s+='<td width="2" height="2"><img src="'+u+'/theme/blue/gary_r5_c11.jpg" width="2" height="2"></td></tr></table></td></tr></table>';
document.write(s);
}

function ts_title(title){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="5"></td></tr></table>';
s+='<fieldset style="width:98%; border-width :thin;">';
s+='<legend class="legend">'+title+'</legend>';
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
document.write(s);
}

function ts_mid(title){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
s+='</fieldset>';
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
s+='<fieldset style="width:98%; border-width :thin;">';
s+='<legend class="legend">'+title+'</legend>';
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
document.write(s);
}

function ts_down(){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
s+='</fieldset>';
document.write(s);
}

function mouse_over1(obj){
obj.style.background='url('+u+'/theme/blue/vio_main_r3_c5.jpg)';
}

function mouse_out1(obj){ 1
obj.style.background='url('+u+'/theme/blue/vio_main_r2_c1.jpg)';
}
function vio_title(m){
	var t="";
		t+='<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">';
		t+='<tr><td height="30" background="'+u+'/theme/blue/vio_main_r3_c6.jpg"  valign="top">';	
		t+='<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0"><tr><td width="20">&nbsp;</td>';
		t+='<td width="156" background="'+u+'/theme/blue/vio_main_r3_c9.jpg" class="text_12" align="center" valign="bottom">&nbsp;'+m+'&nbsp;</td>';
		t+='<td class="text_12 table_val ">';
		document.write(t);
	}

	function vio_seach(){
	var t="";
		t+='<td class="text_12" align="right">&nbsp;&nbsp;</td>';
		t+='</tr></table></td></tr><tr><td  bgcolor="#ffffff" align="center" valign="top">';
		document.write(t);
	}

	function vio_down(){
	var t="";	
		t+='</td></tr></table>';
		document.write(t);
	}
	