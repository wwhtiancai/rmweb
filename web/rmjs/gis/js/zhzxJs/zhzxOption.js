function xzqhTreeDialogFunc(){ //��֯��������
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:false});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#leftFloatWindow').window({collapsed:false,collapsible:true,closed:true});
}

function roadTreeDialogFunc(){	
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:false});
	$('#leftFloatWindow').window({collapsed:false,collapsible:true,closed:true});
}
var autoSuggest=null;
function initAutoSuggestInput(dwfl, textInputId){
	//�˿ռ���ѡ��ֵ֮�� �Ѷ�Ӧֵ������ �Զ�������֮�� autoSuggestValue
	autoSuggest= new AutoSuggestControl(document.getElementById(textInputId), new SuggestionProvider());
	autoSuggest.tableName = "dev_suppliers";//�Զ���ʾ��ѯ�ı�����
	autoSuggest.textField = "dwmc";//�Զ���ʾ��ѯ���ֶ�����
	autoSuggest.valueField="dwbh";
	autoSuggest.filterCondition="dwfl like '%"+dwfl+"%'";
	autoSuggest.url=basePath+"zhzx.gis?method=getAtuoSuggesetDevSuppliers";	
	autoSuggest.callBackFun="resetQueryTimer";//ѡ����Ϻ��ƶ��ص���������
}
function kkCond(dmz,sbCn,sbTable){ //��֯��������
		hideDialog();
		if(kkDialog){
			kkDialog.showDiv();
			//kkDialog = null;
		}else{	
			var _opt = {width:500,height:150,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			//_dialogOpt[]
			kkDialog = new Dialog('dialog_'+dmz, _opt);
	        kkDialog.setCloseImg();
	        //var content = "<div id='kkDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	                
	        var content ="";
	        content+=createQueryFromDB(codeMap.kk_kklx+"", "��������", 1, 0, "kk_kklx", 500);
	        //content+=createQueryFromDB(codeMap.kk_kkxz+"", "��������", 1, 1, "kk_kkxz", 400);
	        //content+=createQueryFromDB(codeMap.kk_wlwz+"", "����λ��", 1, 1, "kk_wlwz", 400);
	        //content+=createQueryFromDB(codeMap.kk_ljtj+"", "��������", 1, 1, "kk_ljtj", 400);
	        //content+=createQueryFromDB(codeMap.kk_yxzt+"", "����״̬", 1, 1, "kk_yxzt", 500);
	        content+=createQueryFromDB("novalue"+"", "�豸����", 2, 1, "kk_sbcs", 500);
	        kkDialog.setContent(content);
	        kkDialog.showDiv();
		}  
		initAutoSuggestInput("1","kk_sbcsmc","resetQueryTimer()");
	} 
	
	function zfqzCond(dmz,sbCn,sbTable){	//��֯Υ��ȡ֤����	
		hideDialog();
		if(zfqzDialog){
			zfqzDialog.showDiv();
		}else{	
			//var _opt = {width:550,height:300,ofLeft:324,ofTop:30,margin:0,padding:0,opacity:1};
			var _dialogZDYOpt = {width:540,height:180,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			zfqzDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			zfqzDialog.setCloseImg();
			//var content = "<div id='zfqzDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	
			var content = "";
			content+=createQueryFromDB(codeMap.zfqz_sblx+"", "�豸����", 1, 0, "zfqz_sblx", 540);
			content+=createQueryFromDB("novalue"+"", "�豸����", 2, 1, "zfqz_sbcs", 540);
	        zfqzDialog.setContent(content);
	        zfqzDialog.showDiv();
		}  
		initAutoSuggestInput("3","zfqz_sbcsmc","resetQueryTimer()");
	} 

	function spjkCond(dmz,sbCn,sbTable){	//��֯��Ƶ�������	
		hideDialog();
		if(spjkDialog){
			spjkDialog.showDiv();
		}else{			
			var _dialogSPJKOpt = {width:380,height:130,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			spjkDialog = new Dialog('dialog_'+dmz, _dialogSPJKOpt);
			spjkDialog.setCloseImg();
			//var content = "<div id='spjkDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content ="";
			content+=createQueryFromDB(codeMap.spjk_txcc+"", "ͼ��ߴ�", 1, 0, "spjk_txcc", 380);
			//content+=createQueryFromDB(codeMap.spjk_gzzt+"", "����״̬", 1, 0, "spjk_gzzt", 380);
			content+=createQueryFromDB("novalue"+"", "�豸����", 2, 1, "spjk_sbcs", 380);
	        spjkDialog.setContent(content);
	        spjkDialog.showDiv();
		}  
		initAutoSuggestInput("6","spjk_sbcsmc","resetQueryTimer()");
	} 
	
	
//case '04':   //��ͨ��ȫִ������վ
 function zffwzCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(zffwzDialog){
			zffwzDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:400,height:200,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			zffwzDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			zffwzDialog.setCloseImg();
			//var content = "<div id='zffwzDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content ="";
			content+=createQueryFromDB(codeMap.zffwz_fwzlx+"", "ִ��վ����", 1, 0, "zffwz_fwzlx", 380);
			content+=createQueryFromDB("1:��,0:��", "�Ƿ����Ӫ��", 1, 0, "zffwz_sfdlyf", 380);
			content+=createQueryFromDB(codeMap.zffwz_jcfx+"", "��鷽��", 1, 0, "zffwz_jcfx", 380);
			zffwzDialog.setContent(content);
	        zffwzDialog.showDiv();
		}  
	}

 //case '05':   //ͣ����
 function tccCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(tccDialog){
			tccDialog.showDiv();
		}else{		
			//
			tccDialog = new Dialog('dialog_'+dmz, _dialogOpt);
			tccDialog.setCloseImg();
			//var content = "<div id='tccDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content="";
			content+=createQueryFromDB(codeMap.tcc_fwzlx+"", "ͣ��������", 1, 0, "tcc_fwzlx", 400);
			content+=createQueryFromDB(codeMap.tcc_jsly+"", "������Դ", 1, 0, "tcc_jsly", 400);
			content+=createQueryFromDB("|500:С��500,500|1000:500��1000,1000|:����1000", "ͣ����λ��", 1, 0, "tcc_tcpws", 400);
			tccDialog.setContent(content);
	        tccDialog.showDiv();
		}  
	}
//case '06':   //�������豸
 function qxjcCond(dmz,sbCn,sbTable){	//	
	hideDialog();
	if(qxjcDialog){
		qxjcDialog.showDiv();
	}else{			
		var _dialogZDYOpt = {width:460,height:200,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
		qxjcDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
		qxjcDialog.setCloseImg();
		//var content = "<div id='qxjcDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
		var content = "";
		content+=createQueryFromDB(codeMap.qxjc_yjlx+"", "Ԥ������", 1, 0, "qxjc_yjlx", 460);
		content+=createQueryFromDB("novalue"+"", "�豸����", 2, 1, "qxjc_sbcs", 460);
		qxjcDialog.setContent(content);
        qxjcDialog.showDiv();
	}  
	initAutoSuggestInput("B","qxjc_sbcsmc","resetQueryTimer()");
}
 
//case '07':   //��������豸
 function lljcCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(lljcDialog){
			lljcDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:480,height:210,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			lljcDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			lljcDialog.setCloseImg();
			//var content = "<div id='lljcDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.lljc_sblx+"", "�豸����", 1, 0, "lljc_sblx", 480);
			content+=createQueryFromDB(codeMap.lljc_jcsjlx+"", "�����������", 1, 0, "lljc_jcsjlx", 480);
			content+=createQueryFromDB("novalue"+"", "�豸����", 2, 1, "lljc_sbcs", 480);
			lljcDialog.setContent(content);
	        lljcDialog.showDiv();
		}  
		initAutoSuggestInput("9","lljc_sbcsmc","resetQueryTimer()");
	}
 
 //case '08':   //�ɱ���Ϣ��־
 function kbxxCond(dmz,sbCn,sbTable){	//	
		hideDialog();
		if(kbbzDialog){
			kbbzDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:520,height:210,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			kbbzDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			kbbzDialog.setCloseImg();
			//var content = "<div id='kbxxDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.kbxx_sblx+"", "�豸����", 1, 0, "kbxx_sblx", 520);
			content+=createQueryFromDB(codeMap.kbxx_sbgg+"", "�豸���", 1, 0, "kbxx_sbgg", 520);
			content+=createQueryFromDB(codeMap.kbxx_fbfs+"", "������ʽ", 1, 0, "kbxx_fbfs", 520);
			kbbzDialog.setContent(content);
	        kbbzDialog.showDiv();
		}  
	}
 
//case '09':   //��ͨ�źſ����豸
 function xhkzCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(jtxhDialog){
			jtxhDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:425,height:125,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			jtxhDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			jtxhDialog.setCloseImg();
			//var content = "<div id='xhkzDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content ="";
			//alert(codeMap.kbbz_sblx);
			content+=createQueryFromDB(codeMap.kbbz_sblx+"", "�豸����", 1, 0, "kbbz_sblx", 480);
			content+=createQueryFromDB("1:��,2:��", "�Ƿ�����", 1, 0, "kbbz_sflw", 480);
			jtxhDialog.setContent(content);
	        jtxhDialog.showDiv();
		}  
	}
 
 //case '11':   //��ͨ����===============================*******
 function jtbmCond(dmz,sbCn,sbTable){	//	
		hideDialog();
		if(jtbmDialog){
			jtbmDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:420,height:150,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			jtbmDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			jtbmDialog.setCloseImg();
			//var content = "<div id='jtbmDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content = "";
			content+=createQueryFromDB("|3:С��3��,3|5:3-5��,5|:����5��", "�ϳ���", 1, 0, "jtbm_tcs", 400);
			content+=createQueryFromDB("|3:С��3��,3|5:3-5��,5|:����5��", "���ϳ���", 1, 0, "jtbm_qzcs", 400);
			content+=createQueryFromDB("|3:С��3��,3|5:3-5��,5|:����5��", "������", 1, 0, "jtbm_dcs", 400);
			jtbmDialog.setContent(content);
	        jtbmDialog.showDiv();
		}  
	}
 //case '12':   //��������
 function xfbmCond(dmz,sbCn,sbTable){	//	
		hideDialog();
		if(xfbmDialog){
			xfbmDialog.showDiv();
		}else{		
			var _dialogZDYOpt = {width:410,height:180,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			xfbmDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			xfbmDialog.setCloseImg();
			//var content = "<div id='xfbmDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.xfbm_jgjb+"", "��������", 1, 0, "xfbm_jgjb", 400);
			content+=createQueryFromDB("|3:С��3��,3|5:3-5��,5|:����5��", "��������", 1, 0, "xfbm_xfcs", 400);
			content+=createQueryFromDB("|10:С��10��,10|20:10-20��,20|:����20��", "��������", 1, 0, "xfbm_xfrs", 400);
			xfbmDialog.setContent(content);
	        xfbmDialog.showDiv();
		}  
	}
 //case '13':   //ҽԺ
 function yyCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(yyDialog){
			yyDialog.showDiv();
		}else{		
			var _dialogZDYOpt = {width:410,height:200,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			yyDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			yyDialog.setCloseImg();
			//var content = "<div id='yyDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.yy_yylx+"", "ҽԺ����", 1, 0, "yy_yylx", 400);
			content+=createQueryFromDB(codeMap.yy_yyjb+"", "ҽԺ����", 1, 0, "yy_yyjb", 400);
			content+=createQueryFromDB("|3:С��3,3|10:3��10,10|:����10", "���ȳ���", 1, 0, "yy_jjcs", 400);
			yyDialog.setContent(content);
	        yyDialog.showDiv();
		}  
	}
 
 //case '14':   //����
 function xlcCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(xlcDialog){
			xlcDialog.showDiv();
		}else{	
			var _dialogZDYOpt = {width:460,height:100,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			xlcDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			xlcDialog.setCloseImg();
			//var content = "<div id='xlcDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.xlc_zzjb+"", "���ʼ���", 1, 0, "xlc_zzjb", 400);
			xlcDialog.setContent(content);
	        xlcDialog.showDiv();
		}  
	}
 //case '16':   //������
 function jjdCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(jjdDialog){
			jjdDialog.showDiv();
		}else{			
			var _dialogJJDOpt = {width:350,height:130,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			jjdDialog = new Dialog('dialog_'+dmz, _dialogJJDOpt);
			jjdDialog.setCloseImg();
			
			//var content = "<div id='jjdDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"��������</div>";	        
			var content="";
			content+=createQueryFromDB(codeMap.jjd_bmjb+"", "���ż���", 1, 0, "jjd_bmjb", 330);
			content+=createQueryFromDB(codeMap.jjd_bmfl+"", "���ŷ���", 1, 0, "jjd_bmfl", 330);
			jjdDialog.setContent(content);
			jjdDialog.showDiv();
		}  
	}	
	
	//
	/*
	 * �������ݿ��趨�Ŀ�����������̬������ѯ����html
	 */
	function createQueryFromDB(valueStr,leftTitle,controlType,isSelectAll,idCode,dialogWidth){
		var content="<div class='dialogRowCss' style='float:left;'><span style='color:#28557e'>"+leftTitle+"</span></div>";
		if(controlType==1){
			var contianerDIV=document.createElement("<div style='width:"+dialogWidth-90+"px'></div>");
	        var valueArray = valueStr.split(",");
	        var trFlag="";
	        var valueRemainLen=0;//��¼��ǰʣ�¼���Ԫ��
	        //��1��ͨ��ֵ���鳤�ȵõ���Ҫ���⴦�������Ԫ��
	        var everyRowDiv="";
	        var colsDivArray=[];
	        for(var i=0;i<3;i++){
	        	colsDivArray[i]=$("<div style='float:left; padding:5px 10px 10px 10px;'></div>");
	        }
	        colsDivArray[colsDivArray.length]=$("<div style='clear:both;'></div>");
	        for(var i=0; i<valueArray.length; i++){
	        	var dmz = valueArray[i].substring(0,valueArray[i].lastIndexOf(":"));
	        	var mc = valueArray[i].substring(valueArray[i].lastIndexOf(":")+1,valueArray[i].length);	
	        	var everyColDiv=$("<div><input type='checkbox' id='"+idCode+"_"+dmz+"' name='"+idCode+"_cb' onclick='checkClick(this)' /><font style='font-size:12px'>"+mc+"</font></div>");	 
	        	//����ÿһ��div	 
	        	switch(i%3){
	        		case 0:
	        			colsDivArray[0].append(everyColDiv);
	        			break;
	        		case 1:
	        			colsDivArray[1].append(everyColDiv);
	        			break;
	        		case 2:
	        			colsDivArray[2].append(everyColDiv);
	        			break;
	        	} 		
	        }	
	        //ʵ�ְ��Ѿ����е�Div���õ�ҳ��֮�в�ʵ�ַֻ���
	        for(var i=0;i<colsDivArray.length;i++){
	        	$(contianerDIV).append(colsDivArray[i]); 
	        }
	        $(contianerDIV).append("<div style='clear:both;'></div>"); 
	        content+=$(contianerDIV).html();
		}else{
			content+="<div class='dialogRowValueCss'>";
			var width="";
			if(dialogWidth>=380){
				width=dialogWidth-200;
			}else{
				width=dialogWidth-140;
			}
			content+="<span><input type='text' id='"+idCode+"mc' value='' style='width:"+width+"px'  onclick='stopQueryTimer()'></span></div>";
		}		
		
        return content;
	}
	
	function hideDialog(){ //��������dialog
		    if(kkDialog){
		        kkDialog.hideDiv();		        
		    }
		    if(zfqzDialog){
		    	zfqzDialog.hideDiv();	
		    }
		    if(spjkDialog){
		    	spjkDialog.hideDiv();
		    }
		    if(zffwzDialog){
		    	zffwzDialog.hideDiv();   	
		    }
		    if(tccDialog){
		    	tccDialog.hideDiv();	    	
		    }	    
		    if(qxjcDialog){
		    	qxjcDialog.hideDiv();
		    }
		    if(lljcDialog){
		    	lljcDialog.hideDiv();
		    }
		    if(kbbzDialog){
		    	kbbzDialog.hideDiv();
		    }
		    if(jtxhDialog){
		    	jtxhDialog.hideDiv();
		    }
		    if(jtbmDialog){
		    	jtbmDialog.hideDiv();
		    }
		    if(xfbmDialog){
		    	xfbmDialog.hideDiv();
		    }
		    if(yyDialog){
		    	yyDialog.hideDiv();
		    }
		    if(xlcDialog){
		    	xlcDialog.hideDiv();
		    }
		    if(jjdDialog){
		    	jjdDialog.hideDiv();
		    }
		    /*
		    if(xzqhTreeDialog){
		    	xzqhTreeDialog.hideDiv();
		    }
		    if(roadTreeDialog){
		    	roadTreeDialog.hideDiv();
		    }
		    */
		}
	
		function getSbssjcByDmz(dmz){ //ͨ���豸����ֵ����豸��ʩ���
		switch(dmz){
		   case "01":
			   return "kk";
		   case '02':   
          	   return "zfqz";
		   case '03':   
          	   return "spjk";
		   case '04':   
          	   return "zffwz";
		   case "05":
			   return "tcc";
		   case '06':   
          	   return "qxjc";
		   case '07':   
          	   return "lljc";
		   case '08':   
          	   return "kbxx";
		   case "09":
			   return "jtxh";
		   case '11':   
          	   return "jtbm";
		   case '12':   
          	   return "xfbm";
		   case '13':   
          	   return "yy";
		   case '14':   
          	   return "xlc";
		   case '16':   
          	   return "jjd";
		}		
	}
	function getDmzBySbssjc(dmz){ //ͨ���豸��ʩ��ƻ���豸����ֵ
		switch(dmz){
		   case "kk":
			   return "01";
		   case 'zfqz':   
          	   return "02";
		   case 'spjk':   
          	   return "03";
		   case 'zffwz':   
          	   return "04";
		   case "tcc":
			   return "05";
		   case 'qxjc':   
          	   return "06";
		   case 'lljc':   
          	   return "07";
		   case 'kbxx':   
          	   return "08";
		   case "jtxh":
			   return "09";
		   case 'jtbm':   
          	   return "10";
		   case 'xfbm':   
          	   return "11";
		   case 'yy':   
          	   return "12";
		   case 'xlc':   
          	   return "13";
		   case 'jjd':   
          	   return "14";
		}		
	}