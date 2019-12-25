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
	var t=fname.substr(4,2);
	var n=fname.substr(7,2);
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
			tab_div_obj.style.display='block';	
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
			tab_r_obj.style.width="100px";//当前tab的宽度
			tab_div_obj.style.display='none';
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