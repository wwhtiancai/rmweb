var lastTimeDmz="";
var jtsgEvent;
var bkxyclEvent;
var eltqEvent;
var dzzhEvent;
var jtllEvent;
var cljtwfEvent;
var cltsEvent;
var xrjtwfEvent;
var _dialogOpt = {width:365,height:120,ofLeft:350,ofTop:60,margin:0,padding:0,opacity:1};
function getTraficEventCatalog(){
	$.ajax({	
		url:basePath + "yjzh.gis?method=getTraficEventCatalog",
		cache:false,
		type:"get",
		dataType:"json",
		success:function (data){
			var eventHtml="";
			for(var i=0; i<data.length; i++){
				eventHtml+="<div style='width: 220px; height: 24px; line-height: 24px; border-bottom: f2f2f5 solid 1px; margin-left: 15px; margin-top: 5px;'>";
				eventHtml+="<span style='width: 180px; line-height: 30px; float: left; padding-left: 20px;'>";
				eventHtml+="<input type='checkbox' id='event_"+data[i].eventbh+"' name='eventCheckBox' value='"+data[i].eventbh+"' onclick='checkEventBox(this)' title='"+data[i].eventmc+"' /><a>"+data[i].eventmc+"</a>";
				eventHtml+="</span>";
				
				eventHtml+="<div id='eventImgDiv_"+data[i].eventbh+"' style='width: 18px; line-height: 30px; float: left; padding-right: 10px; display: none'>";
				eventHtml+="<img id='eventImg_"+data[i].eventbh+"' name='"+data[i].eventmc+"' src='"+modelPath+"images/business/zhzx/jia.jpg' style='cursor: pointer' onclick='clickEventImg(this);' />";
				eventHtml+="</div>";
				eventHtml+="</div>";
			}
			//closeLoad();
			$("#trafficEventDiv").html(eventHtml);
		}
	});
	
}

function checkEventBox(obj){ //点击各大类的复选框事件	
	hideEventDialog();//隐藏对话框				
	var dmz = obj.id.substring(obj.id.lastIndexOf("_")+1,obj.id.length);
    if(obj.checked){
    	$("#eventImgDiv_"+dmz).css("display","block");
    }else{
    	$("#eventImg_"+dmz).attr("src",modelPath+"images/business/zhzx/jia.jpg");
    	$("#eventImgDiv_"+dmz).css("display","none");
    	if(jtsgEvent){
	        if(jtsgEvent.dialogId=='dialogEvent_'+dmz){
	        	jtsgEvent.shutDiv();
	        	jtsgEvent=null;
	        }	
	    }
	    if(bkxyclEvent){
	        if(bkxyclEvent.dialogId=='dialogEvent_'+dmz){
	        	bkxyclEvent.shutDiv();
	        	bkxyclEvent=null;
	        }	
	    }
    	if(eltqEvent){
	        if(eltqEvent.dialogId=='dialogEvent_'+dmz){
	        	eltqEvent.shutDiv();
	        	eltqEvent=null;
	        }	
	    }
	    if(dzzhEvent){
	        if(dzzhEvent.dialogId=='dialogEvent_'+dmz){
	        	dzzhEvent.shutDiv();
	        	dzzhEvent=null;
	        }	
	    }
	    if(jtllEvent){
	        if(jtllEvent.dialogId=='dialogEvent_'+dmz){
	        	jtllEvent.shutDiv();
	        	jtllEvent=null;
	        }	
	    }
	    if(cljtwfEvent){
	        if(cljtwfEvent.dialogId=='dialogEvent_'+dmz){
	        	cljtwfEvent.shutDiv();
	        	cljtwfEvent=null;
	        }	
	    }
    	if(cltsEvent){
	        if(cltsEvent.dialogId=='dialogEvent_'+dmz){
	        	cltsEvent.shutDiv();
	        	cltsEvent=null;
	        }	
	    }
	    if(xrjtwfEvent){
	        if(xrjtwfEvent.dialogId=='dialogEvent_'+dmz){
	        	xrjtwfEvent.shutDiv();
	        	xrjtwfEvent=null;
	        }	
	    }
    }				
    resetQueryTimer();//加入查询计时器
}

function clickEventImg(obj){ //点击大类名称右边小图标框事件		
	var eventBh = obj.id.substring(obj.id.lastIndexOf("_")+1,obj.id.length);
    var eventMc =obj.name;
    if(lastTimeDmz==""){
    	lastTimeDmz=eventBh;
    }
    if(lastTimeDmz!=eventBh){ //如果点击另外一个加号图标，则把上一个减号图标改为加号
    	$("#eventImg_"+lastTimeDmz).attr("src",modelPath+"images/business/zhzx/jia.jpg");
    }

    if($("#eventImg_"+eventBh).attr("src").lastIndexOf("jian.jpg")>=0){ //控制加减号
    	$("#eventImg_"+eventBh).attr("src",modelPath+"images/business/zhzx/jia.jpg");
    	//hideDialog();
    }else{
    	$("#eventImg_"+eventBh).attr("src",modelPath+"images/business/zhzx/jian.jpg");   
    	commonEventFunc(eventBh,eventMc);
    }	
    lastTimeDmz=eventBh;
}

function commonEventFunc(eventBh,eventMc){
	$.ajax({	
		url:basePath + "yjzh.gis?method=getIncidentSjzlx&sjfl="+eventBh,
		cache:false,
		type:"post",
		dataType:"json",
		success:function (data){	
			hideEventDialog();
			var content="<div class='dialogRowCss' style='float:left;'><span style='color:#28557e'></span></div>";
			var contianerDIV=document.createElement("<div style='width:220px'></div>");
	        var trFlag="";
	        var valueRemainLen=0;//记录当前剩下几个元素
	        //【1】通过值数组长度得到需要特殊处理的数组元素
	        var everyRowDiv="";
	        var colsDivArray=[];
	        for(var i=0;i<3;i++){
	        	colsDivArray[i]=$("<div style='float:left; padding:5px 10px 10px 10px;'></div>");
	        }
	        colsDivArray[colsDivArray.length]=$("<div style='clear:both;'></div>");
	        for(var i=0; i<data.length; i++){
	        	var bh = data[i].eventbh;
				var mc = data[i].eventmc;		
	        	var everyColDiv=$("<div><input type='checkbox' name='eventxf_"+eventBh+"' value='"+bh+"' onclick='resetQueryTimer()' /><font style='font-size:12px'>"+mc+"</font></div>");	 
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
			/*
			var content = "<div class='dialogDivCss' style='width:480px'><div class='dialogTitleFontCss'>"+eventMc+"过滤条件</div>";
			content+="<span class='dialogTableCss'><table><tr>";
			for(var i=0; i<data.length; i++){
				var bh = data[i].eventbh;
				var mc = data[i].eventmc;		
				if(i!=0 && i%3==0){
					content+="</tr><tr>";
				}
                if(mc.length>8){
                	mc=mc.substring(0,8)+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+mc.substring(8,mc.length);
                }
    			content+="<td><input type='checkbox' name='eventxf_"+eventBh+"' value='"+bh+"' onclick='resetQueryTimer()'>"+mc+"</td>";
			}
			content+="</tr></table></span></div>";
			*/
			switch(eventBh){
	            case 'A':   //交通事故
	        		if(jtsgEvent){
	        			jtsgEvent.showDiv();
	        		}else{	        			
	        			jtsgEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			jtsgEvent.setCloseImg();	        			
	        			jtsgEvent.setContent(content);
	        			jtsgEvent.showDiv();
	        		}  
	                break;
	            case 'B':   //布控嫌疑车辆
	            	if(bkxyclEvent){
	            		bkxyclEvent.showDiv();
	        		}else{	
	        		    var _singleDialogOpt = {width:600,height:230,ofLeft:340,ofTop:32,margin:0,padding:0,opacity:1};
	        			bkxyclEvent = new Dialog('dialogEvent_'+eventBh, _singleDialogOpt);
	        			bkxyclEvent.setCloseImg();	        			
	        			bkxyclEvent.setContent(content);
	        			bkxyclEvent.showDiv();
	        		}  
	           	    break;
	            case 'C':   //恶劣天气
	            	if(eltqEvent){
	            		eltqEvent.showDiv();
	        		}else{	
	        			eltqEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			eltqEvent.setCloseImg();	        			
	        			eltqEvent.setContent(content);
	        			eltqEvent.showDiv();
	        		}  
	                break;
	            case 'D':   //地质灾害
	            	if(dzzhEvent){
	            		dzzhEvent.showDiv();
	        		}else{	
	        			dzzhEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			dzzhEvent.setCloseImg();	        			
	        			dzzhEvent.setContent(content);
	        			dzzhEvent.showDiv();
	        		}  
	                break;
	            case 'E':   //交通流量大
	            	if(jtllEvent){
	            		jtllEvent.showDiv();
	        		}else{	
	        			jtllEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			jtllEvent.setCloseImg();	        			
	        			jtllEvent.setContent(content);
	        			jtllEvent.showDiv();
	        		}  
	                break;
	            case 'F':   //车辆交通违法
	            	if(cljtwfEvent){
	            		cljtwfEvent.showDiv();
	        		}else{	
	        			cljtwfEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			cljtwfEvent.setCloseImg();	        			
	        			cljtwfEvent.setContent(content);
	        			cljtwfEvent.showDiv();
	        		}  
	                break;	            	 
	            case 'G':   //车辆停驶
	            	if(cltsEvent){
	            		cltsEvent.showDiv();
	        		}else{	
	        			cltsEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			cltsEvent.setCloseImg();	        			
	        			cltsEvent.setContent(content);
	        			cltsEvent.showDiv();
	        		}  
	           	    break;	
	            case 'H':   //行人交通违法
	            	if(xrjtwfEvent){
	            		xrjtwfEvent.showDiv();
	        		}else{	
	        			xrjtwfEvent = new Dialog('dialogEvent_'+eventBh, _dialogOpt);
	        			xrjtwfEvent.setCloseImg();	        			
	        			xrjtwfEvent.setContent(content);
	        			xrjtwfEvent.showDiv();
	        		}  
	           	    break;		           
	         }
		}
	})
}

//隐藏事件过滤条件对话框
function hideEventDialog(){
	if(jtsgEvent){
		jtsgEvent.hideDiv();		        
    }
	if(bkxyclEvent){
	    bkxyclEvent.hideDiv();		        
    }
    if(eltqEvent){
    	eltqEvent.hideDiv();	
    }
    if(dzzhEvent){
    	dzzhEvent.hideDiv();
    }
    if(jtllEvent){
    	jtllEvent.hideDiv();   	
    }
    if(cljtwfEvent){
    	cljtwfEvent.hideDiv();	    	
    }	    
    if(cltsEvent){
    	cltsEvent.hideDiv();
    }
    if(xrjtwfEvent){
    	xrjtwfEvent.hideDiv();
    }
}