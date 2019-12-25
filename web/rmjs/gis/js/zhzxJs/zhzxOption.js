function xzqhTreeDialogFunc(){ //组织卡口条件
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
	//此空间在选择值之后 把对应值放置在 自定义属性之中 autoSuggestValue
	autoSuggest= new AutoSuggestControl(document.getElementById(textInputId), new SuggestionProvider());
	autoSuggest.tableName = "dev_suppliers";//自动提示查询的表名称
	autoSuggest.textField = "dwmc";//自动提示查询的字段名称
	autoSuggest.valueField="dwbh";
	autoSuggest.filterCondition="dwfl like '%"+dwfl+"%'";
	autoSuggest.url=basePath+"zhzx.gis?method=getAtuoSuggesetDevSuppliers";	
	autoSuggest.callBackFun="resetQueryTimer";//选择完毕后制定回调函数名称
}
function kkCond(dmz,sbCn,sbTable){ //组织卡口条件
		hideDialog();
		if(kkDialog){
			kkDialog.showDiv();
			//kkDialog = null;
		}else{	
			var _opt = {width:500,height:150,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			//_dialogOpt[]
			kkDialog = new Dialog('dialog_'+dmz, _opt);
	        kkDialog.setCloseImg();
	        //var content = "<div id='kkDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	                
	        var content ="";
	        content+=createQueryFromDB(codeMap.kk_kklx+"", "卡口类型", 1, 0, "kk_kklx", 500);
	        //content+=createQueryFromDB(codeMap.kk_kkxz+"", "卡口性质", 1, 1, "kk_kkxz", 400);
	        //content+=createQueryFromDB(codeMap.kk_wlwz+"", "网络位置", 1, 1, "kk_wlwz", 400);
	        //content+=createQueryFromDB(codeMap.kk_ljtj+"", "拦截条件", 1, 1, "kk_ljtj", 400);
	        //content+=createQueryFromDB(codeMap.kk_yxzt+"", "运行状态", 1, 1, "kk_yxzt", 500);
	        content+=createQueryFromDB("novalue"+"", "设备厂商", 2, 1, "kk_sbcs", 500);
	        kkDialog.setContent(content);
	        kkDialog.showDiv();
		}  
		initAutoSuggestInput("1","kk_sbcsmc","resetQueryTimer()");
	} 
	
	function zfqzCond(dmz,sbCn,sbTable){	//组织违法取证条件	
		hideDialog();
		if(zfqzDialog){
			zfqzDialog.showDiv();
		}else{	
			//var _opt = {width:550,height:300,ofLeft:324,ofTop:30,margin:0,padding:0,opacity:1};
			var _dialogZDYOpt = {width:540,height:180,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			zfqzDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			zfqzDialog.setCloseImg();
			//var content = "<div id='zfqzDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	
			var content = "";
			content+=createQueryFromDB(codeMap.zfqz_sblx+"", "设备类型", 1, 0, "zfqz_sblx", 540);
			content+=createQueryFromDB("novalue"+"", "设备厂商", 2, 1, "zfqz_sbcs", 540);
	        zfqzDialog.setContent(content);
	        zfqzDialog.showDiv();
		}  
		initAutoSuggestInput("3","zfqz_sbcsmc","resetQueryTimer()");
	} 

	function spjkCond(dmz,sbCn,sbTable){	//组织视频监控条件	
		hideDialog();
		if(spjkDialog){
			spjkDialog.showDiv();
		}else{			
			var _dialogSPJKOpt = {width:380,height:130,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			spjkDialog = new Dialog('dialog_'+dmz, _dialogSPJKOpt);
			spjkDialog.setCloseImg();
			//var content = "<div id='spjkDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content ="";
			content+=createQueryFromDB(codeMap.spjk_txcc+"", "图像尺寸", 1, 0, "spjk_txcc", 380);
			//content+=createQueryFromDB(codeMap.spjk_gzzt+"", "工作状态", 1, 0, "spjk_gzzt", 380);
			content+=createQueryFromDB("novalue"+"", "设备厂商", 2, 1, "spjk_sbcs", 380);
	        spjkDialog.setContent(content);
	        spjkDialog.showDiv();
		}  
		initAutoSuggestInput("6","spjk_sbcsmc","resetQueryTimer()");
	} 
	
	
//case '04':   //交通安全执法服务站
 function zffwzCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(zffwzDialog){
			zffwzDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:400,height:200,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			zffwzDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			zffwzDialog.setCloseImg();
			//var content = "<div id='zffwzDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content ="";
			content+=createQueryFromDB(codeMap.zffwz_fwzlx+"", "执法站类型", 1, 0, "zffwz_fwzlx", 380);
			content+=createQueryFromDB("1:是,0:否", "是否独立营房", 1, 0, "zffwz_sfdlyf", 380);
			content+=createQueryFromDB(codeMap.zffwz_jcfx+"", "检查方向", 1, 0, "zffwz_jcfx", 380);
			zffwzDialog.setContent(content);
	        zffwzDialog.showDiv();
		}  
	}

 //case '05':   //停车场
 function tccCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(tccDialog){
			tccDialog.showDiv();
		}else{		
			//
			tccDialog = new Dialog('dialog_'+dmz, _dialogOpt);
			tccDialog.setCloseImg();
			//var content = "<div id='tccDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content="";
			content+=createQueryFromDB(codeMap.tcc_fwzlx+"", "停车场类型", 1, 0, "tcc_fwzlx", 400);
			content+=createQueryFromDB(codeMap.tcc_jsly+"", "建设来源", 1, 0, "tcc_jsly", 400);
			content+=createQueryFromDB("|500:小于500,500|1000:500至1000,1000|:大于1000", "停车泊位数", 1, 0, "tcc_tcpws", 400);
			tccDialog.setContent(content);
	        tccDialog.showDiv();
		}  
	}
//case '06':   //气象检测设备
 function qxjcCond(dmz,sbCn,sbTable){	//	
	hideDialog();
	if(qxjcDialog){
		qxjcDialog.showDiv();
	}else{			
		var _dialogZDYOpt = {width:460,height:200,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
		qxjcDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
		qxjcDialog.setCloseImg();
		//var content = "<div id='qxjcDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
		var content = "";
		content+=createQueryFromDB(codeMap.qxjc_yjlx+"", "预警类型", 1, 0, "qxjc_yjlx", 460);
		content+=createQueryFromDB("novalue"+"", "设备厂商", 2, 1, "qxjc_sbcs", 460);
		qxjcDialog.setContent(content);
        qxjcDialog.showDiv();
	}  
	initAutoSuggestInput("B","qxjc_sbcsmc","resetQueryTimer()");
}
 
//case '07':   //流量检测设备
 function lljcCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(lljcDialog){
			lljcDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:480,height:210,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			lljcDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			lljcDialog.setCloseImg();
			//var content = "<div id='lljcDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.lljc_sblx+"", "设备类型", 1, 0, "lljc_sblx", 480);
			content+=createQueryFromDB(codeMap.lljc_jcsjlx+"", "检测数据类型", 1, 0, "lljc_jcsjlx", 480);
			content+=createQueryFromDB("novalue"+"", "设备厂商", 2, 1, "lljc_sbcs", 480);
			lljcDialog.setContent(content);
	        lljcDialog.showDiv();
		}  
		initAutoSuggestInput("9","lljc_sbcsmc","resetQueryTimer()");
	}
 
 //case '08':   //可变信息标志
 function kbxxCond(dmz,sbCn,sbTable){	//	
		hideDialog();
		if(kbbzDialog){
			kbbzDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:520,height:210,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			kbbzDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			kbbzDialog.setCloseImg();
			//var content = "<div id='kbxxDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.kbxx_sblx+"", "设备类型", 1, 0, "kbxx_sblx", 520);
			content+=createQueryFromDB(codeMap.kbxx_sbgg+"", "设备规格", 1, 0, "kbxx_sbgg", 520);
			content+=createQueryFromDB(codeMap.kbxx_fbfs+"", "发布方式", 1, 0, "kbxx_fbfs", 520);
			kbbzDialog.setContent(content);
	        kbbzDialog.showDiv();
		}  
	}
 
//case '09':   //交通信号控制设备
 function xhkzCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(jtxhDialog){
			jtxhDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:425,height:125,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			jtxhDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			jtxhDialog.setCloseImg();
			//var content = "<div id='xhkzDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content ="";
			//alert(codeMap.kbbz_sblx);
			content+=createQueryFromDB(codeMap.kbbz_sblx+"", "设备类型", 1, 0, "kbbz_sblx", 480);
			content+=createQueryFromDB("1:是,2:否", "是否联网", 1, 0, "kbbz_sflw", 480);
			jtxhDialog.setContent(content);
	        jtxhDialog.showDiv();
		}  
	}
 
 //case '11':   //交通部门===============================*******
 function jtbmCond(dmz,sbCn,sbTable){	//	
		hideDialog();
		if(jtbmDialog){
			jtbmDialog.showDiv();
		}else{			
			var _dialogZDYOpt = {width:420,height:150,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			jtbmDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			jtbmDialog.setCloseImg();
			//var content = "<div id='jtbmDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content = "";
			content+=createQueryFromDB("|3:小于3辆,3|5:3-5辆,5|:大于5辆", "拖车数", 1, 0, "jtbm_tcs", 400);
			content+=createQueryFromDB("|3:小于3辆,3|5:3-5辆,5|:大于5辆", "清障车数", 1, 0, "jtbm_qzcs", 400);
			content+=createQueryFromDB("|3:小于3辆,3|5:3-5辆,5|:大于5辆", "吊车数", 1, 0, "jtbm_dcs", 400);
			jtbmDialog.setContent(content);
	        jtbmDialog.showDiv();
		}  
	}
 //case '12':   //消防部门
 function xfbmCond(dmz,sbCn,sbTable){	//	
		hideDialog();
		if(xfbmDialog){
			xfbmDialog.showDiv();
		}else{		
			var _dialogZDYOpt = {width:410,height:180,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			xfbmDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			xfbmDialog.setCloseImg();
			//var content = "<div id='xfbmDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.xfbm_jgjb+"", "机构级别", 1, 0, "xfbm_jgjb", 400);
			content+=createQueryFromDB("|3:小于3辆,3|5:3-5辆,5|:大于5辆", "消防车数", 1, 0, "xfbm_xfcs", 400);
			content+=createQueryFromDB("|10:小于10人,10|20:10-20人,20|:大于20人", "消防人数", 1, 0, "xfbm_xfrs", 400);
			xfbmDialog.setContent(content);
	        xfbmDialog.showDiv();
		}  
	}
 //case '13':   //医院
 function yyCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(yyDialog){
			yyDialog.showDiv();
		}else{		
			var _dialogZDYOpt = {width:410,height:200,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			yyDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			yyDialog.setCloseImg();
			//var content = "<div id='yyDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.yy_yylx+"", "医院类型", 1, 0, "yy_yylx", 400);
			content+=createQueryFromDB(codeMap.yy_yyjb+"", "医院级别", 1, 0, "yy_yyjb", 400);
			content+=createQueryFromDB("|3:小于3,3|10:3至10,10|:大于10", "急救车数", 1, 0, "yy_jjcs", 400);
			yyDialog.setContent(content);
	        yyDialog.showDiv();
		}  
	}
 
 //case '14':   //修理厂
 function xlcCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(xlcDialog){
			xlcDialog.showDiv();
		}else{	
			var _dialogZDYOpt = {width:460,height:100,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};			
			xlcDialog = new Dialog('dialog_'+dmz, _dialogZDYOpt);
			xlcDialog.setCloseImg();
			//var content = "<div id='xlcDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content = "";
			content+=createQueryFromDB(codeMap.xlc_zzjb+"", "资质级别", 1, 0, "xlc_zzjb", 400);
			xlcDialog.setContent(content);
	        xlcDialog.showDiv();
		}  
	}
 //case '16':   //交警队
 function jjdCond(dmz,sbCn,sbTable){		
		hideDialog();
		if(jjdDialog){
			jjdDialog.showDiv();
		}else{			
			var _dialogJJDOpt = {width:350,height:130,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
			jjdDialog = new Dialog('dialog_'+dmz, _dialogJJDOpt);
			jjdDialog.setCloseImg();
			
			//var content = "<div id='jjdDialogDiv' class='dialogDivCss' style='width:520px'><div class='dialogTitleFontCss'>"+sbCn+"过滤条件</div>";	        
			var content="";
			content+=createQueryFromDB(codeMap.jjd_bmjb+"", "部门级别", 1, 0, "jjd_bmjb", 330);
			content+=createQueryFromDB(codeMap.jjd_bmfl+"", "部门分类", 1, 0, "jjd_bmfl", 330);
			jjdDialog.setContent(content);
			jjdDialog.showDiv();
		}  
	}	
	
	//
	/*
	 * 根据数据库设定的卡口条件，动态创建查询条件html
	 */
	function createQueryFromDB(valueStr,leftTitle,controlType,isSelectAll,idCode,dialogWidth){
		var content="<div class='dialogRowCss' style='float:left;'><span style='color:#28557e'>"+leftTitle+"</span></div>";
		if(controlType==1){
			var contianerDIV=document.createElement("<div style='width:"+dialogWidth-90+"px'></div>");
	        var valueArray = valueStr.split(",");
	        var trFlag="";
	        var valueRemainLen=0;//记录当前剩下几个元素
	        //【1】通过值数组长度得到需要特殊处理的数组元素
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
	        	//创建每一行div	 
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
	        //实现把已经分列的Div放置到页面之中并实现分换行
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
	
	function hideDialog(){ //隐藏所有dialog
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
	
		function getSbssjcByDmz(dmz){ //通过设备代码值获得设备设施简称
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
	function getDmzBySbssjc(dmz){ //通过设备设施简称获得设备代码值
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