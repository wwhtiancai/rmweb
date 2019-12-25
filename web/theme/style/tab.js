var pos=0,count=0;
var u=location.href;
while(count<4){
	pos=u.indexOf('/',pos+1);
	count++;}
u=location.href.substr(0,pos)+"/";

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
						tab_w_2.style.width="9px";
						tab_r_2.style.width="99px";
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
		div_x =document.getElementById("div_"+i).innerHTML; 
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
		t+='<td width="150" background="'+u+'/theme/style/tab3_2.png" id="tab_top_'+i+'" style="cursor:hand;" class="text_white">';
		t+=div_x;
		t+="</td>";
	  //  t+='</td><td width="3" background="'+u+'/theme/style/tab2_jg.png"></td>';
		}else{		
//		t+='<td width="171" background=" " id="tab_top_'+i+'" onClick="showtop_Form('+i+','+n+');" style="cursor:hand;"> ';
		t+='<td width="150" background="'+u+'/theme/style/tab3_1.png" id="tab_top_'+i+'" style="cursor:hand;" class="text_white"> ';
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
