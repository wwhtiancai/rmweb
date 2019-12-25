
/**
 * 设置右边的手风琴列表
 */
function LoadjtztjcList(){
	var content = slidebarControl.getContentDiv();
	
	if (!$("#resultDiv").get(0)) {	
		var titleHtml="<div id='firstpane' class='menu_list'>"
		    +"<p class='menu_head current'>交通流量</p>"
		    +"<div style='display:block;' id='result_jtll' class=menu_body >"		   
		    +"</div>"
		    +"<p class='menu_head'>道路施工</p>"
		    +"<div style='display:none;' id='result_dlsg' class=menu_body >"
		    +"</div>"
		    +"<p class='menu_head'>交通事故</p>"
		    +"<div style='display:none;' id='result_jtsg' class=menu_body >"
		    +"</div>"
		    +"<p class='menu_head'>恶劣天气</p>"
		    +"<div style='display:none;' id='result_eltq' class=menu_body >"
		    +"</div>"
		    +"<p class='menu_head'>其他阻断事件</p>"
		    +"<div style='display:none;' id='result_qtzd' class=menu_body >"
		    +"</div>"
		    +"</div>";
		$(titleHtml).appendTo(content);
		$("<div>").attr("id", "resultDiv").appendTo(content).css("width", "270px");	
	}
	if(slidebarOut){
		slidebarControl.slideOut(); //弹出结果浮动窗口
	}	
}
/*
 * 设置手风琴的样式脚本
 */
function listControl(){
	$("#firstpane .menu_body:eq(0)").show();
	$("#firstpane p.menu_head").click(function(){
		$(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
		$(this).siblings().removeClass("current");
		var headText=$(this).next().attr("id");//$(this).text();
		var htx=headText.replace("result_","");
		//if(queryParam==null) return;
		if(htx=="qtzd"){
			htx="qtzdsj";
		}
		if(currentClickShower==htx){
			return;
		}
		switch(headText){
			case "result_jtll":		
				if(queryParam==null){
					queryParam=getJtllParams();
				}
				//queryParam.sjlx="";
				//getFlowRealStateJson(queryParam);
				
				getAccordionListResult(queryParam, "result_jtll","rs.flow?method=getFlowRealStateJson");
				break;
			case "result_dlsg":				
				if(queryParam==null){
					queryParam=getQueryParam();
				}
				queryParam.sjlxs = "A2";				
				//getLatestBlockedSingle(queryParam,"result_dlsg");
				getAccordionListResult(queryParam, "result_dlsg","jtztjc.gis?method=getLatestBlockedSingle");				
				break;
			case "result_jtsg":
				if(queryParam==null){
					queryParam=getQueryParam();
				}
				queryParam.sjlxs = "A3";
				getAccordionListResult(queryParam, "result_jtsg","jtztjc.gis?method=getLatestBlockedSingle");
				//getLatestBlockedSingle(queryParam,"result_jtsg");
				break;
			case "result_eltq":
				
				if(queryParam==null){
					queryParam=getQueryParam();
				}
				queryParam.sjlxs = "A4";
				getAccordionListResult(queryParam, "result_eltq","jtztjc.gis?method=getLatestBlockedSingle");
				//getLatestBlockedSingle(queryParam,"result_eltq");
				break;
			case "result_qtzd":
				var queryParam = getQueryParam();
				var otherBlockCheckedArray = $("input[name='otherBlockName']:checked");	
				var sjlxStr = "";
				for(var i=0; i<otherBlockCheckedArray.length; i++){
					if(i!=0){
						sjlxStr+=",";
					}
					sjlxStr+=otherBlockCheckedArray[i].id;
				}
				queryParam.sjlxs = sjlxStr;
				getAccordionListResult(queryParam, "result_qtzd","jtztjc.gis?method=getLatestBlockedMul");				
				break;
		}
		
	});
	$("#secondpane .menu_body:eq(0)").show();
	$("#secondpane p.menu_head").mouseover(function(){
		$(this).addClass("current").next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
		$(this).siblings().removeClass("current");
	});
	
}

function getAccordionListResult(valparam,resultAccordion,ajaxUrl){	
	showLoad();
	$.ajax({
		url:basePath+""+ajaxUrl,
		data: valparam,
		type:"post",
		dataType:"json",
		async:false,
		cache:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			if(data.length==0){	
				$(("#"+resultAccordion)).html("");
			}else{
			   var htmlStr="<div>";
			   for(var i=0; i<data.length; i++){        	
			    	var id = data[i].id;
			    	var sxh = data[i].sxh;
			    	var xxlx = data[i].xxlx;
			    	var sjnr = data[i].sjnr;
			    	var sjjs= data[i].sjjs;
			    	var xxly = data[i].xxly;
			    	if(i<=10){
			    		if("result_jtll"==resultAccordion){
				    		var ssztMC="";
				    		if(data[i].sszt=="3"){
				    			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/red.png' /><font color=red> 拥堵</font>";
				    		}else if(data[i].sszt=="2"){
				    			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/yellow.png' /><font color=orange> 缓行</font>";
				    		}else{
				    			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/green.png' /><font color=green> 畅通</font>";
				    		}				    	
				    		htmlStr += "<a href='#' style='font-size:12px'  title='"+data[i].lkldmc+"'>"+ssztMC+"  "+data[i].lkldmc+"</a>";	
			    		}else{
			    			htmlStr += "<a href='#' style='font-size:12px;'  title='"+sjnr+"'>"+sjjs+"</a>";	
			    		}
			    		     		
			    	}else{
			    		break;
			    	}
			   }
			   if(data.length>9){
					htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:gis_block_list(\'"+valparam.sjlxs+"\');return false;>更多信息</a>";
				}
			    htmlStr+="</div>";
			    $("#"+resultAccordion).html(htmlStr);
			}
			closeLoad();
		},error:function(){
			closeLoad();
		}
	});	
}