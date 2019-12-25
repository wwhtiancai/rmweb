
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

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function PreloadImg(){
  var Arrimg=new Array();
  if(typeof(arguments[0])=="string")Arrimg[0]=arguments[0];
  if(typeof(arguments[0])=="object"){
      for(var i=0;i<arguments[0].length;i++){
          Arrimg[i]=arguments[0][i]
        }
    }
  var img=new Array()
  for(var i=0;i<Arrimg.length;i++){
    img[i]=new Image();
    img[i].src=Arrimg[i];
  }
}
/*
var imgsrc=new Array("http://'+u+'/theme/1/vio_main_r_1.gif","http://'+u+'/theme/1/vio_main_r_bg.png","http://'+u+'/theme/1/vio_main_r_2.gif")
new PreloadImg(imgsrc)//加载了数组里面的图片。
//new PreloadImg("http://www.baidu.com/img/baidu_logo.gif")//直接加载一个图片。
//new PreloadImg("/img/baidu_logo.gif")//加载了站点里的一个图片。
//alert(imgsrc);
*/
function show_Form(m_formname) {
	var fname=m_formname;
	var t=fname.substr(4,1);
	var n=fname.substr(6,1);
	var tab_img_obj ="";
	var tab_r_obj=""; 

	   if (m_formname == 'tab_'+t+'_'+n) {
		for(var i=1;i<=n;i++){
		    var j=i-1;
			var tab_img2_obj ="tab_img_r"+j;
		    var tab_r_obj= document.getElementById("tab_r_"+i); 

			var tab_img_obj ="tab_img_r"+i;
			var tab_div_obj=document.getElementById('tab_'+i);


		   if (t==i){				
			MM_swapImage(tab_img_obj,'',u+'/theme/style/tlb_r2_c3.gif',1);	
			MM_swapImage(tab_img2_obj,'',u+'/theme/style/tlb_r3_c1.gif',1);					
			tab_r_obj.style.background="url("+u+"/theme/style/tlb_r2_c2.gif)";		
			tab_r_obj.style.width="99px";
			tab_w_1.style.width="9px";
			tab_r_1.style.width="120px";	
			//tab_div_obj.style.display='block';	
			if(tab_div_obj){
				tab_div_obj.style.display='block';	
			}
			
					if (i==1){
						MM_swapImage('tab_img_r0','',u+'/theme/style/tlb_r2_c1.gif',1);
						//tab_w_2.style.width="9px";
						//tab_r_2.style.width="99px";
					}else{
						MM_swapImage('tab_img_r0','',u+'/theme/style/tlb_r1_c1.gif',1);		
					}
						if (i==n){
								MM_swapImage(tab_img_obj,'',u+'/theme/style/tlb_r2_c4.gif',1);		
						}

		}else{
				if (i==n){
					MM_swapImage(tab_img_obj,'',u+'/theme/style/tlb_r1_c5.gif',1);	
				}else{
					MM_swapImage(tab_img_obj,'',u+'/theme/style/tlb_r1_c4.gif',1);	
				}
			tab_r_obj.style.background="url("+u+"/theme/style/tlb_r1_c6.gif)";	
			tab_r_obj.style.width="120px";//当前tab的宽度
			//tab_div_obj.style.display='none';
			if(tab_div_obj){
				tab_div_obj.style.display='none';	
			}
			
		}//if
		}//for		
   }
  }

function call_back(n)    
{ 
	var t='';
	t+='<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0" class="text_12">';
	t+='<tr ><td width="10" background="'+u+'/theme/style/tlb_r0.gif"></td>';
    t+='<td width="10"><img src="'+u+'/theme/style/tlb_r2_c1.gif" name="tab_img_r0" width="10" height="30" id="tab_img_r0"></td>';

	for(var i=1;i<=n;i++){
		var div_x;
		try{
		div_x =document.getElementById("div_"+i).innerHTML; 
		}catch(e){
			div_x="参数管理";
		}
		if (i==1)
		{
		t+='<td width="120" align="center" background="'+u+'/theme/style/tlb_r2_c2.gif" id="tab_r_'+i+'">';
		t+=div_x;
	    t+='<td width="30" id="tab_w_'+i+'"><img src="'+u+'/theme/style/tlb_r2_c3.gif" id="tab_img_r'+i+'"></td>';
		}else{
		
		t+='<td width="120" align="center" background="'+u+'/theme/style/tlb_r1_c6.gif" id="tab_r_'+i+'"> ';
		t+=div_x;
		if (i==n)
		{
		 t+='<td width="9" id="tab_w_'+i+'"><img src="'+u+'/theme/style/tlb_r1_c5.gif"  id="tab_img_r'+i+'"></td>';
		}else{
	    t+='<td width="9" id="tab_w_'+i+'"><img src="'+u+'/theme/style/tlb_r1_c4.gif" id="tab_img_r'+i+'"></td>';
		}
		}
	} 
	var div_xx;
	try{
		div_xx =document.getElementById("div_xx").innerHTML; 
		t+='<td background="'+u+'/theme/style/tlb_r1.gif" align="right">'+div_xx+'&nbsp;</td></tr></table>';
	}catch (e) {
		t+='<td background="'+u+'/theme/style/tlb_r1.gif">&nbsp;</td></tr></table>';
	}

	document.getElementById("div_show").innerHTML=t;

}  

function showtop_Form(m,n) {
	for(var i=1;i<=n;i++){
		var tab_img_obj= document.getElementById("tab_top_"+i); 
		var tabtop_css=document.getElementById('tabtop_css_'+i);
		var tab_div_obj=document.getElementById('tab_'+i);
		
		   if (m==i){				 
		   tab_img_obj.style.background="url("+u+"/theme/style/tab3_2.png)";	
		   tabtop_css.className="text_bold text_white";
		   tab_div_obj.style.display='block';
		   
		   }
		   else{
			 tab_img_obj.style.background="url("+u+"/theme/style/tab3_1.png)";	
			 tabtop_css.className="text_white";
			tab_div_obj.style.display='none';	
		   }
	}
	
}
  
function call_top(n)    
{ 
	var t='';
	t+='<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" background="'+u+'/theme/style/tab3_0.png" class="text_12"><tr align="center" >';
	t+='<td width="9"></td>';
	for(var i=1;i<=n;i++){
		var div_x;
		div_x =document.getElementById("divtop_"+i).innerHTML; 
		if (i==1)
		{
//			t+='<td width="171" background="'+u+'/theme/style/tab2_2.png" id="tab_top_'+i+'" onClick="showtop_Form('+i+','+n+');" style="cursor:hand;" class="text_white">';
		t+='<td width="198" background="'+u+'/theme/style/tab3_2.png" id="tab_top_'+i+'" style="cursor:hand;" class="text_white">';
		t+=div_x;
		t+="</td>";
	  //  t+='</td><td width="3" background="'+u+'/theme/style/tab2_jg.png"></td>';
		}else{		
//		t+='<td width="171" background=" " id="tab_top_'+i+'" onClick="showtop_Form('+i+','+n+');" style="cursor:hand;"> ';
		t+='<td width="198" background="'+u+'/theme/style/tab3_1.png" id="tab_top_'+i+'" style="cursor:hand;" class="text_white"> ';
		t+=div_x;
		//t+='</td><td width="3" background="'+u+'/theme/style/tab2_jg.png"></td>';	
		t+="</td>";
		}
	} 
    t+='<td>&nbsp;</td></tr></table>';
    document.getElementById("div_top_show").innerHTML=t;
}  


function show_zc_Form(m,n) {

	for(var i=1;i<=n;i++){
		var tab_l_obj= document.getElementById("tab_l_"+i); 
		var tab_r_obj= document.getElementById("tb_r_"+i); 
		var tab_zc_obj= document.getElementById("tab_zc_"+i); 
		var tab_div_obj=document.getElementById('tab_'+i);
		var xc_obj=document.getElementById('xc_'+i);

		   if (m==i){				 
	
		   tab_l_obj.style.background="url("+u+"/theme/style/zhcx_3_r1_c3.png)";	
		   tab_zc_obj.style.background="url("+u+"/theme/style/zhcx_3_r1_c4.png)";		   
		   tab_r_obj.style.background="url("+u+"/theme/style/zhcx_3_r1_c8.png)";		
//		   tab_div_obj.style.display='block';
		   xc_obj.style.display='block';
		   }
		   else{
			tab_l_obj.style.background="url("+u+"/theme/style/zhcx_3_r1_c10.png)";	
			tab_zc_obj.style.background="url("+u+"/theme/style/zhcx_3_r1_c11.png)";
			tab_r_obj.style.background="url("+u+"/theme/style/zhcx_3_r1_c13.png)";			   
		//tab_div_obj.style.display='none';
			xc_obj.style.display='none';
		   }
		   /*
		   if (i==1){
			   ifrmaeecsideveh.reloadImage();
		   }
		   if (i==2){
			   ifrmaeecsidedrv.reloadImage();
		   }
		   if (i==3){
			   ifrmaeecsideVehFuzzy.reloadImage();
		   }
		   if (i==4){
			   ifrmaeecsideFuzzy.reloadImage();
		   }*/
	}
	// yzwnull();
}

function call_cx(n)    
{ 
	var t='';
    t+='<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" ><tr>';
    t+='<td height="27" valign="bottom" align="center" background="'+u+'/theme/style/mj_bg_1.gif"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="text_12">';
    t+='<tr><td width="150" height="21" align="center"><b>综合查询</b></td>';
    t+='<td><table border="0" cellpadding="0" cellspacing="0" class="text_12"><tr><td width="10"></td>';
            
	for(var i=1;i<=n;i++){
		var div_x;
		div_x =document.getElementById("div_"+i).innerHTML; 
		if (i==1)
		{
	    t+='<td width="3" background="'+u+'/theme/style/zhcx_3_r1_c3.png" width="3" height="21"  id="tab_l_'+i+'"></td>';
//	    t+='<td width="100" align="center" valign="bottom" background="'+u+'/theme/style/zhcx_3_r1_c4.png" id="tab_zc_'+i+'" style="cursor:hand;" onClick="show_zc_Form('+i+','+n+');">';
	    t+='<td width="100" align="center" valign="bottom" background="'+u+'/theme/style/zhcx_3_r1_c4.png" id="tab_zc_'+i+'" style="cursor:hand;">';
	    t+=div_x;
	    t+='</td><td width="3" background="'+u+'/theme/style/zhcx_3_r1_c8.png" width="3" height="21" id="tb_r_'+i+'"></td><td width="10"></td>';
		}else{
		t+='<td width="3" background="'+u+'/theme/style/zhcx_3_r1_c10.png" width="3" height="21"  id="tab_l_'+i+'"></td>';
//		t+='<td width="100" align="center" valign="bottom" background="'+u+'/theme/style/zhcx_3_r1_c11.png" id="tab_zc_'+i+'" style="cursor:hand;" onClick="show_zc_Form('+i+','+n+');">';
		t+='<td width="100" align="center" valign="bottom" background="'+u+'/theme/style/zhcx_3_r1_c11.png" id="tab_zc_'+i+'" style="cursor:hand;">';
		t+=div_x;
		t+='</td><td width="3" background="'+u+'/theme/style/zhcx_3_r1_c13.png" width="3" height="21"  id="tb_r_'+i+'"></td><td width="10"></td>'; 
		}
	} 
	
	t+='<td>&nbsp;</td>';
	t+='</tr></table></td></tr></table></td></tr><tr>';
	t+='<td height="6" background="'+u+'/theme/style/zhcx_3_r2_c1.png">';
	t+='<table width="100%" height="6" border="0" cellpadding="0" cellspacing="0">';
	t+='<tr><td width="160" height="6" ><img src="'+u+'/theme/style/spacer.gif" width="1" height="1" /></td>';
	for(var j=1;j<=n;j++){
    if (j==1){
	t+='<td width="106" align="center" valign="top"><span id="xc_'+j+'" style="display:block;"><img src="'+u+'/theme/style/zhcx_3_r2_c6.png" width="11" height="6" /></span></td>';
	t+='<td width="10"><img src="'+u+'/theme/style/spacer.gif" width="1" height="1" /></td>';
	}else{
	t+='<td width="106" align="center" valign="top"><span id="xc_'+j+'" style="display:none;"><img src="'+u+'/theme/style/zhcx_3_r2_c6.png" width="11" height="6" /></span></td>';
	t+='<td width="10"><img src="'+u+'/theme/style/spacer.gif" width="1" height="1" /></td>';
	}
	}
    t+='<td width=""><img src="'+u+'/theme/style/spacer.gif" width="1" height="1" /></td>';
	t+='</tr></table></td></tr></table>';
	document.getElementById("div_show").innerHTML=t;
} 



function top_Form(m,n) {
	for(var i=1;i<=n;i++){
		var j=i-1;
		var tab_t_obj= document.getElementById("top_t_1"); 
		var tab_top_obj= document.getElementById("tab_top_"+i); 
		var tab_r_obj= document.getElementById("top_r_"+i); 
        var tab_r_2_obj= document.getElementById("top_r_"+j); 
        var table_div_obj=document.getElementById('table_'+i);          					        
        if (m==i){
        	tab_top_obj.style.background="url("+u+"/theme/style/top_tb_c1_bg.gif)";		
			tab_top_obj.className="text_12 text_white";
			table_div_obj.style.display = "block";		    			
			   if (i==1){
				   tab_t_obj.style.background="url("+u+"/theme/style/top_tb_c1_1.gif)";
				}else{
				   tab_t_obj.style.background="url("+u+"/theme/style/top_tb_c1_0.gif)";
				   tab_r_2_obj.style.background="url("+u+"/theme/style/top_tb_c3_1.gif)";
				}
			   tab_r_obj.style.background="url("+u+"/theme/style/top_tb_c2_1.gif)";
				if (i==n){
					tab_r_obj.style.background="url("+u+"/theme/style/top_tb_c4_1.gif)";					
				}
					
							
		}else{
			table_div_obj.style.display = "none";	
			tab_r_obj.style.background="url("+u+"/theme/style/top_tb_c3_0.gif)";
//			tab_r_obj.style.height="23px";
			tab_top_obj.className="text_12 text_black";
			tab_top_obj.style.background="url("+u+"/theme/style/top_tb_c0_bg.gif)";	
			
			 if (i==n){
				 tab_r_obj.style.width="21px";
				 tab_r_obj.style.background="url("+u+"/theme/style/top_tb_c4_0.gif)";	
				}else{
				 tab_r_obj.style.width="24px";
				 tab_r_obj.style.background="url("+u+"/theme/style/top_tb_c3_0.gif)";
				}
			//tab_r_obj.style.background="url("+u+"/theme/style/tlb_r1_c6.gif)";
				
		}//if
		}	
	parent.leftfrm.interfaceDoTableContent(m,n);		
}

function top_tb(n)    
{ 
	var t='';
	t+='<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td height="23" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">';
	t+='<tr height="23" ><td background="'+u+'/theme/style/top_tb_1_bg.gif">&nbsp;</td>';
	  for(var i=1;i<=n;i++){
			var div_x;
			var len="60";//15px一个中文
			div_x =document.getElementById("div_"+i).innerHTML; 
			var strlen=div_x.length*12;
			if (strlen>len)
			{len=strlen;}
		if (i==1)
		{
		t+='<td width="20" background="'+u+'/theme/style/top_tb_c1_1.gif" id="top_t_'+i+'"></td>';
		t+='<td width='+len+' align="center" background="'+u+'/theme/style/top_tb_c1_bg.gif" id="tab_top_'+i+'" onClick="top_Form('+i+','+n+');" style="cursor:hand;" class="text_12 text_white" >';
		t+=div_x;
		if (n==1){
			t+='</td><td width="21" background="'+u+'/theme/style/top_tb_c4_1.gif" id="top_r_'+i+'"></td>'
			}else{
			t+='</td><td width="24" background="'+u+'/theme/style/top_tb_c2_1.gif" width="24" height="23"  id="top_r_'+i+'"></td>';
			}
		    
		}else{		
		t+='<td width='+len+' align="center" background="'+u+'/theme/style/top_tb_c0_bg.gif" id="tab_top_'+i+'" onClick="top_Form('+i+','+n+');" style="cursor:hand;" class="text_12" >';
		t+=div_x;
		if (i==n)
		{
		t+='</td><td width="21" background="'+u+'/theme/style/top_tb_c4_0.gif"  id="top_r_'+i+'"></td>';
		}else{
		t+='</td><td width="24" background="'+u+'/theme/style/top_tb_c2_0.gif" width="24" height="23"  id="top_r_'+i+'"></td>';	    
		}
		}
	  	} 
	  t+='</tr></table></td></tr></table>';
     document.getElementById("div_top_show").innerHTML=t;
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

function ts_title_old(title){
	var s="";
	s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="1"></td></tr></table>';
	s+='<table width="98%" border="0" cellpadding="0" cellspacing="0"  bgcolor="#72AFCA"  align="center">';
	s+='<tr><td class="text_12 text_bold text_white" height="22">&nbsp;'+title+'&nbsp;';
	s+='</td></tr></table>';
	//S+='';
	document.write(s);
	}

function ts_title(title){
var s="";
s+='<table width="98%" border="0" cellpadding="0" cellspacing="0" background="'+u+'/theme/style/ts_title_bg.jpg" align="center">';
s+='<tr><td class="text_12 text_bold" height="20">&nbsp;'+title+'&nbsp;';
s+='</td></tr></table>';
//S+='';
document.write(s);
}

function ts_title2(title,mm){
	var s="";
	s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="1"></td></tr></table>';
	s+='<table width="98%" border="0" cellpadding="0" cellspacing="0" background="'+u+'/theme/style/ts_title_bg.jpg" align="center">';
	s+='<tr><td class="text_12 text_bold" height="22">&nbsp;'+title+'&nbsp;';
	s+='</td><td width="400" align="right" class="text_12">&nbsp;'+mm+'&nbsp;&nbsp;</td></tr></table>';
	//S+='';
	document.write(s);
	}
function ts_mid(title){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="1"></td></tr></table>';
s+='<table width="98%" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC" class="list_table_title">';
s+='<tr><td bgcolor="#f0f0f0" class="text_12 text_bold text_gre" height="19">&nbsp;&nbsp;'+title+'&nbsp;';
s+='</td></tr></table>';
document.write(s);
}

function ts_down(){
var s="";
s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="1"></td></tr></table>';
document.write(s);
}
function tss_title(title){
	var s="";
	s+='<fieldset style="width:98%; border-width :thin;">';
	s+='<legend class="legend">'+title+'</legend>';
	s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
	document.write(s);
	}
function tss_down(){
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
	var len="150";//15px一个中文
	var strlen=m.length*15;
	if (strlen>len)
	{len=strlen;}		
	var t="";
/*
	if(location.href.indexOf("&check_tit=X_T")!=-1){
		t+='<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" >';
	}else{
		t+='<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" >';
		t+='<tr><td height="30" background="'+u+'/theme/blue/vio_main_r3_c6.jpg"  valign="top">';	
		t+='<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0"><tr><td width="20">&nbsp;</td>';
		t+='<td background="'+u+'/theme/blue/vio_tit_c1.jpg" width="9"></td>';
		t+='<td width='+len+' background="'+u+'/theme/blue/vio_tit_c3.jpg" class="text_12 text_bold" align="center" valign="bottom">&nbsp;'+m+'&nbsp;</td>';
		t+='<td width="7" background="'+u+'/theme/blue/vio_tit_c5.jpg"></td>';
		t+='<td class="text_12 table_val " align="right">';
	}
	*/

	if(location.href.indexOf("&check_tit=X_T")!=-1){
		t+='<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" >';
	}else{
		t+='<table width="100%" height="50" border="0" cellpadding="0" cellspacing="0"><tr>';
		t+='<td valign="bottom" background="theme/1/vio_main_bg.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="31">';
		t+='<tr height="31" ><td width="42" height="31" class="tl_title_1">&nbsp;</td>';
		t+='<td width='+len+' background="theme/1/vio_main_r_bg.png" class="text_12 text_bold" align="center" >'+m+'&nbsp;&nbsp;</td>';
		t+='<td width="59" height="31" class="tl_title_2">&nbsp;</td>';
		t+='<td colspan="2" align="right" background="theme/1/vio_main_r_3.gif" class="text_12" valign="top" style="padding:2px 0 0 0;">';
	}
	document.write(t);	
}

function vio_seach(){
var t="";
if(location.href.indexOf("&check_tit=X_T")!=-1){
	t+='<tr><td  bgcolor="#ffffff" align="center" valign="top">';
}else{
	t+='</td></tr><tr><td height="2" colspan="5"></td></tr></table></td></tr></table>';
}
document.write(t);
}

function vio_seach_2(m){
	var t="";
		t+='<td class="text_12" align="right">&nbsp;'+m+'&nbsp;</td>';
		t+='</tr></table></td></tr><tr><td  bgcolor="#ffffff" align="center" valign="top">';
		document.write(t);
	}
function vio_down(){
	var t="";
	t+='</td></tr></table>';
	document.write(t);
}

function zhcx_title_old(m){
	var t='';
	t+='<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"><tr><td height="3"></td></tr><tr><td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"><tr>';
	t+='<td width="18"><img src="'+u+'/theme/blue/gary_main_r2_c2.gif" alt="" width="18" height="28" border="0"></td>';
	t+='<td width="20%" background="'+u+'/theme/blue/gary_main_r2_c5.jpg" class="text_12 text_white">&nbsp;'+m+'&nbsp;</td>';
	t+='<td width="46"><img src="'+u+'/theme/blue/gary_main_r2_c7.gif" alt="" width="46" height="28" border="0"></td>';
	t+='<td width="80%" background="'+u+'/theme/blue/gary_main_r2_c9.gif"></td>';
	t+='<td width="21"><img src="'+u+'/theme/blue/gary_main_r2_c11.gif" alt="" width="21" height="28" border="0"></td></tr>';
	t+='<tr><td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>';
	t+='<td width="2" background="'+u+'/theme/blue/gary_main_r3_c2.jpg"></td><td height="17">';
	t+='<img src="'+u+'/theme/blue/spacer.gif" width="1" height="1" /></td></tr></table>';
	t+='</td><td height="17"><img name="gary_main_r3_c11" src="'+u+'/theme/blue/gary_main_r3_c11.png" width="21" height="17" border="0" id="gary_main_r3_c11" alt="" /></td>';
	t+='</tr><tr><td height="17" colspan="5"><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>';
	t+='<td background="'+u+'/theme/blue/gary_main_r3_c2.jpg" width="2"></td>';
	t+='<td height="" valign="top"  align="center" class="text_12" bgcolor="#ffffff">';
	document.write(t);
}
function zhcx_down_old(){
	var t='';	
	t+='</td><td background="'+u+'/theme/blue/gary_main_r5_c13.png" width="5"><img src="'+u+'/theme/blue/spacer.gif"></td>';
	t+='</tr></table></td></tr><tr><td height="20"><img src="'+u+'/theme/blue/gary_main_r7_c2.png" width="18" height="20"></td>';
	t+='<td colspan="3" background="'+u+'/theme/blue/gary_main_r7_c9_1.png"></td><td height="17"><img src="'+u+'/theme/blue/gary_main_r7_c11.png" width="21" height="20"></td></tr></table></td></tr></table>';
	document.write(t);	
}

function zhcx_title(m){
	var t='';
	t+='<div class="s1" style="height: 3px;"></div>';
	t+='<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center"><tr>';
    t+='<td height="27" valign="bottom" background="'+u+'/theme/style/mj_bg_1.gif"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="text_12">';
    t+='<tr><td width="150" height="21" align="center"><b>&nbsp;'+m+'&nbsp;</b></td>';
    var t_x1;
    var t_x2;
	try{
		t_x1 =document.getElementById("t_x1").innerHTML; 
		t+='<td class="text_12">&nbsp;'+t_x1+'</td>';
	}catch (e) {
		t+='<td></td>';
	}
	try{
		t_x2 =document.getElementById("t_x2").innerHTML; 
		t+='<td class="text_12" width="100" align="right" valign="top">'+t_x2+'&nbsp;</td>';
	}catch (e) {
		t+='<td></td>';
	}
    t+='</tr></table></td></tr></table><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td valign="top" align="center">';    
	
	document.write(t);	
}

function cx_title(m){
	var t='';
	t+='<div class="s1" style="height: 3px;"></div>';
	t+='<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center"><tr>';
    t+='<td height="27" valign="bottom" background="'+u+'/theme/style/mj_bg_1.gif"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="text_12">';
    t+='<tr><td width="150" height="21" align="center"><b>&nbsp;'+m+'&nbsp;</b></td>';
    t+='<td></td></tr></table></td></tr></table>';   
	document.write(t);	
}
function cx2_title(m){
	var t='';
	t+='<div class="s1" style="height: 3px;"></div>';
	t+='<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center" class="text_12"><tr>';
    t+='<td height="25" background="'+u+'/theme/style/cx_bg.png" style="padding:0px 0px 0px 20px;"><b>'+m+'&nbsp;</b></td></tr>';
    t+='<tr><td height="105" valign="top">';   
	document.write(t);	
}
function cx2_down(){
	var t='';	
	t+='</td></tr></table>';
	document.write(t);	
}
function zhcx_down(){
	var t='';	
	t+='</td></tr></table>';
	document.write(t);	
}

function ttb_title(title){
	var s="";
	s+='<table width="100%" border="0" cellspacing="0" cellpadding="0">';
	s+='<tr><td width="9"><img src="'+u+'/theme/style/ttd/cd_r1_c1.png" width="9" height="24" /></td>';
	s+='<td background="'+u+'/theme/style/ttd/cd_r1_c2.png" class="text_12 text_black text_bold">&nbsp;&nbsp;'+title+'&nbsp;';
	s+='</td><td width="9"><img src="'+u+'/theme/style/ttd/cd_r1_c4.png" width="9" height="24"></td></tr><tr>';
	s+='<td background="'+u+'/theme/style/ttd/cd_r3_c1.png"></td><td bgcolor="#DCEEF6" class="text_12" height="100" valign="top">';
	document.write(s);
	}

	function ttb_down(){
	var s="";
	s+='</td><td background="'+u+'/theme/style/ttd/cd_r3_c4.png"></td></tr><tr>';
	s+='<td width="9"><img src="'+u+'/theme/style/ttd/cd_r5_c1.png" width="9" height="20" /></td>';
	s+='<td background="'+u+'/theme/style/ttd/cd_r5_c2.png"></td><td background="'+u+'/theme/style/ttd/cd_r5_c4.png"></td></tr></table>';
	document.write(s);
	}

	function sg_title(title){
		var s="";
		s+='<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="text_12"><tr height="25">';
		// s+='<img src="'+u+'/theme/style/oppo-0'+m+'.gif" width="10" height="26" /></td>';
		s+=' <td width="150" background="'+u+'/theme/style/cx_bg.png">&nbsp;&nbsp;&nbsp;'+title+'</td>';
		s+='<td>&nbsp;</td></tr>';  
		s+='</table>';
		document.write(s);
		}
	
	function fmid(title){
		var s="";
		s+='<div class="s1" style="height: 3px;"></div>';		
		s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
		s+='<fieldset style="width:98%; border-width :thin;">';
		s+='<legend class="text_12">'+title+'</legend>';
		s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
		document.write(s);
		}

	function fdown(){
		var s="";
		s+='<table border="0" cellspacing="0" cellpadding="0"><tr><td height="2"></td></tr></table>';
		s+='</fieldset>';
		document.write(s);
		}
	
	function user_button_color(XXSS){
		var xs_name=document.getElementById(XXSS).id;	
		 if (xs_name=="btyh"){
			 document.getElementById("btyh").className= "tab_user_1";
			 document.getElementById("btsq").className= "tab_user_2";
	     }
		 if (xs_name=="btsq"){
			 document.getElementById("btsq").className= "tab_user_1";
			 document.getElementById("btyh").className= "tab_user_2";
		  }		  
	}