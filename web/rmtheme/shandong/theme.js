function panel(){
	var h="";
	var t="";
	var m="";
	var height="365";
	if($("#panel > #paneltitle").size()==1){
		m=$("#panel > #paneltitle").html();
		$("#panel > #paneltitle").html("");
		$("#panel > #paneltitle").css("display","none");
	}
	if($("#panel > #panelheight").size()==1){
		height=$("#panel > #panelheight").html();
		$("#panel > #panelheight").html("");
		$("#panel > #panelheight").css("display","none");
	}
	var test="";
	try{
		test=self.name;
	}catch(e){
		test="";
	}
	h=$("#panel").html();
	h=h.ReplaceAll('_sortType="String"','');
	h=h.ReplaceAll('_sortType="Number"','');
	h=h.ReplaceAll('<IMG class=sort-arrow src="">','');
	h=h.ReplaceAll('id=sorttable','id="sorttable"');
	t='';
	t+='<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">';
	t+='	<tr>';
	t+='		<td background="./rmtheme/'+titles+'/p4.gif">';
	t+='			<table border="0" cellspacing="0" cellpadding="0">';
	t+='				<tr>';
	t+='					<td width="50" height="45" background="./rmtheme/'+titles+'/p1.gif"></td>';
	t+='					<td background="./rmtheme/'+titles+'/p2.gif" style="padding:18px 18px 0 18px;font-size:12px;font-weight:bold;">'+m+'</td>';
	t+='					<td width="50" background="./rmtheme/'+titles+'/p3.gif"></td>';
	t+='				</tr>';
	t+='			</table>';
	t+='		</td>';
	t+='	</tr>';
	t+='</table>';
	t+='<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="white">';
	t+='	<tr>';
	t+='		<td height="'+height+'" align="center" valign="top">';
	t+=h;
	t+='		</td>';
	t+='	</tr>';
	t+='</table>';
	$("#panel").html(t);
	$("#panel").fadeIn("normal"); 
	if(typeof(doSortTable)=="function"){
		doSortTable();
	}
}

function query(){
	var m="";
	if($("#query > #querytitle").size()==1){
		m=$("#query > #querytitle").html();
		$("#query > #querytitle").html("");
		$("#query > #querytitle").css("display","none");
	}
	var h=$("#query").html();
	var t='';

	t+='<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td height="5"></td></tr></table>';
	t+='<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"><tr><td align="center">';
	t+=h;
	t+='</td></tr></table>';
	$("#query").html(t);
	$("#query").css("display","block");
}

function result(){
	var m="";
	if($("#result > #resulttitle").size()==1){
		m=$("#result > #resulttitle").html();
		$("#result > #resulttitle").html("");
		$("#result > #resulttitle").css("display","none");
	}
	var h=$("#result").html();
	var t='';
	t+='<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"><tr><td align="center">';
	t+=h;
	t+='</td></tr></table>';
	$("#result").html(t);
}

function block(){
	var h="";
	var t="";
	var m="";
	var p=8;
	var w="100%";
	var first=0;
	var divbox;
	var v=$(":div[id=block]");
	for(var i=0;i<v.size();i++){
		divbox=v[i].childNodes;
		for(var j=0;j<divbox.length;j++){
			if(divbox[j].id=='blocktitle'){
				m=divbox[j].innerHTML;
				divbox[j].innerHTML="";
				divbox[j].style.display='none';
			}else if(divbox[j].id=='blockmargin'){
				p=divbox[j].innerHTML;
				divbox[j].innerHTML="";
				divbox[j].style.display='none';
			}else if(divbox[j].id=='blockwidth'){
				w=divbox[j].innerHTML;
				divbox[j].innerHTML="";
				divbox[j].style.display='none';
			}
		}
		h=v[i].innerHTML;
		if(first==0){
			t+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="7"></td></tr></table>';
			first=1;
		}
		t+='<fieldset style="width:'+w+';text-align:left;border:2px outset white;">';
		t+='<legend style="font-size:12px;color:black;letter-spacing:1px;">'+m+'</legend>';
		t+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="4"></td></tr></table>';
		t+='<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center"><tr><td align="center">';
		t+=h;
		t+='</td></tr></table>';
		t+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="6"></td></tr></table>';
		t+='</fieldset>';
		t+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="'+p+'"></td></tr></table>';
		v[i].innerHTML=t;
		m="";
		p=8;
		t="";
		w="100%";
	}
}

function windowopen(url,winname,width,height){
	var w=760;
	var h=570;
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-h)/2;
	windowwidth=(windowwidth-w)/2;
	subwinname = window.open(url,winname,"resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no,directories=no,width=800,height=600,top="+windowheight+",left="+windowwidth);
	return subwinname;
}
