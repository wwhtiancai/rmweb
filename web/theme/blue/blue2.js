String.prototype.ReplaceAll = stringReplaceAll;
function  stringReplaceAll(AFindText,ARepText){
  raRegExp = new RegExp(AFindText,"g");
  return this.replace(raRegExp,ARepText)
}

var pos=0,count=0;
var u=location.href;
while(count<4){
	pos=u.indexOf('/',pos+1);
	count++;}
u=location.href.substr(0,pos)+"/";

function getlocation(){
	if(location.pathname.substr(0,1)=="/"){
		return "/"+location.pathname.split("/")[1]+"/";
	}else{
		return "/"+location.pathname.split("/")[0]+"/";
	}
}

function file_title(title){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="3"></td></tr></table>';
s+='<table width="98%" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC" class="list_table_title">';
s+='<tr><td bgcolor="#f0f0f0" class="text_12 text_bold">&nbsp;&nbsp;'+title+'&nbsp;';
s+='</td></tr></table>';
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
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="1"></td></tr></table>';
s+='<table width="98%" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC" class="list_table_title">';
s+='<tr><td bgcolor="#f0f0f0" class="text_12 text_bold text_gre" height="20">&nbsp;&nbsp;'+title+'&nbsp;';
s+='</td></tr></table>';
document.write(s);
}

function ts_mid(title){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
s+='<table width="98%" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC" class="list_table_title">';
s+='<tr><td bgcolor="#f0f0f0" class="text_12 text_bold text_gre" height="20">&nbsp;&nbsp;'+title+'&nbsp;';
s+='</td></tr></table>';
document.write(s);
}

function ts_down(){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
s+='</fieldset>';
document.write(s);
}

function mouse_over1(obj){
obj.style.background="'+u+'/theme/blue/vio_main_r3_c5.jpg"
}

function mouse_out1(obj){
obj.style.background="'+u+'/theme/blue/vio_main_r2_c5.jpg"
  /*obj.style.backgroundColor=""*/
}

function vio_title(m){
var t="";
	t+='<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">';
	t+='<tr><td height="30" background="'+u+'/theme/blue/vio_main_r3_c6.jpg"  valign="top">';	
	t+='<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0"><tr><td width="20">&nbsp;</td>';
	t+='<td width="350" background="'+u+'/theme/blue/vio_main_r3_c10.jpg" class="text_12" align="center" valign="bottom">&nbsp;'+m+'&nbsp;</td>';
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

