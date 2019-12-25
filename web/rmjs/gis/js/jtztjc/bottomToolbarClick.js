var tipWinHasInitial=false;//判断window是否已经初始化
var showWindowTipId="";//存放最后点击显示条件的tipId 用于窗体缩放时使用
var prevClickJtBtnId="jtll";//上一次点击交通操作按钮id 默认值为交通流量
var jtzdMarkersArray=[];//每类交通阻断Markers
var qtzdSelectedSjlx="";//记录其他阻断当选择的类型

//每个资源条件创建一个对应的动态窗体
function createOrReplaceTipContent(tipTitle,tipHref,width,height){	
	//【2】测试一下window之中的href内容是否作为当前页面内容
	if(tipWinHasInitial){
		$("#tipConditionWin").window({
			title:tipTitle,
			width:width,
			height:height,
			href: tipHref
		});
	}else{
		$("#tipConditionWin").window({
		title:tipTitle,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		draggable:false,
		resizable:false,
		width:width,
		height:height,
		href: tipHref
		});	
		tipWinHasInitial=true;
	}
	//$("#tipConditionWin").window("refresh");
}

function fillTipWindowContent(tipId) {
	showWindowTipId=tipId;
	switch (tipId) {
	case "zbjczy":// 周边基础资源
		createOrReplaceTipContent("周边基础资源","jtztjc.gis?toolTip=zbjczy",280,150);
		break;
	case "zbjlzy":// 警力资源
		createOrReplaceTipContent("警力资源","jtztjc.gis?toolTip=zbjlzy",280,150);	
		break;
	case "jtll":// 交通流量
		createOrReplaceTipContent("交通流量","jtztjc.gis?toolTip=jtll",320,120);
		break;
	case "qtzdsj":// 其他阻断事件
		createOrReplaceTipContent("其他阻断","jtztjc.gis?toolTip=qtzdsj",170,160);
		break;
	}
}

/**
 * 当下方工具条的按钮点击时，根据不同的按钮加载不同的tip
 */

$(function(){
	//-------*工具箱事件*--------
	$('#toolgjx').tooltip({
        content: $('<div></div>'),
        showEvent: 'click',
        onUpdate: function(content){
            content.panel({
            	width: 36,
                border: false,
                href: 'jtztjc.gis?toolTip=maptool'
            });
        },
        onShow: function(){
            var t = $(this);
            t.tooltip('tip').unbind().bind('mouseenter', function(){
                t.tooltip('show');
            }).bind('mouseleave', function(){
                t.tooltip('hide');
            });
        }
    });	
});

function hideOtherTip(buttonId){//隐藏btnCtr之外的所有控件的   tip提示框
	var ctrs=new Array("zbjczy","zbjlzy","jtll","dlsg","jtsg","eltq","qtzdsj"); 
	for(var i=0;i<ctrs.length;i++){
		if(buttonId=ctrs[i]){
			var ctrname="#"+ctrs[i];
			var t = $(ctrname);
        	t.tooltip('hide');
		}
	}
}

var leftToolBarsStatus= [ {
	"btnId" : "zbjczy",
	"status" : "off"//周边资源
}, {
	"btnId" : "zbjlzy",
	"status" : "off"//周边警力资源
}];

var rightToolBarsStatus =[{
	"btnId" : "jtll",
	"status" : "off"//交通流量
}, {
	"btnId" : "dlsg",
	"status" : "off"//道路施工
}, {
	"btnId" : "jtsg",
	"status" : "off"//交通事故
},{
	"btnId" : "eltq",
	"status" : "off"//恶劣天气
}, {
	"btnId" : "qtzdsj",
	"status" : "off"//其他阻断事件
}];

function setToolBarCss(toolId,toolBarsStatus){
		var setCssBtnId="";
		var setCssIndex=0;
		//设置当前当即按钮样式；清除右边其他按钮选中样式及显示内容
		for(var i=0;i<toolBarsStatus.length;i++){
			var toolBarId=toolBarsStatus[i].btnId;
			if(toolId==toolBarId){
				setCssBtnId=toolId;	
				setCssIndex=i;
			}else{
				//设置为关闭状态的样式
				$("#"+toolBarId).addClass(toolBarId+"Off").removeClass(toolBarId+"On");	
				toolBarsStatus[i].status="off";
				$("#"+toolBarId).attr("status","off");
			}
		}
		if(setCssBtnId!=""){
			//先设置显示或者隐藏ToolTip
			if($("#"+setCssBtnId).attr("status")=="off"){
				if(!((setCssBtnId=="dlsg")||(setCssBtnId=="jtsg")||(setCssBtnId=="eltq"))){
					fillTipWindowContent(setCssBtnId);//填充或者替换window内容
					var left=$("#"+setCssBtnId).offset().left;
					//获得window的高度
					var winHeight=$('#tipConditionWin').window("options").height;
					var top=$("#"+setCssBtnId).offset().top-(winHeight+30);
					$("#tipConditionWin").window({left:left,top:top,collapsed:false,collapsible:false,draggable:false,closed:false,closable:true});
					$("#tipConditionWin").window("open");
				}
				$("#"+setCssBtnId).addClass(setCssBtnId+"On").removeClass(setCssBtnId+"Off");				
				toolBarsStatus[setCssIndex].status="on";	
				$("#"+setCssBtnId).attr("status","on");
			}else{
				if(!((setCssBtnId=="dlsg")||(setCssBtnId=="jtsg")||(setCssBtnId=="eltq"))){
					$("#tipConditionWin").window("close");
				}
				toolBarsStatus[setCssIndex].status="off";
				$("#"+setCssBtnId).attr("status","off");
				$("#"+setCssBtnId).addClass(setCssBtnId+"Off").removeClass(setCssBtnId+"On");		
			}
		}
}

function showTrafficStateListContent(listIndex){
	var menuArray = ['0','1','2','3','4'];
	$("#firstpane .menu_head:eq("+listIndex+")").addClass("current").next("div.menu_body");
	$("#firstpane .menu_head:eq("+listIndex+")").siblings().removeClass("current");
	$("#firstpane .menu_body:eq("+listIndex+")").slideDown();
	for(var i=0; i<menuArray.length; i++){
		if(menuArray[i]!=listIndex){
			$("#firstpane .menu_body:eq("+menuArray[i]+")").slideUp();
		}
	}
	/*
	//弹出右边的信息列表
	$("#firstpane .menu_head:eq("+listIndex+")").addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
	$("#firstpane .menu_head:eq("+listIndex+")").siblings().removeClass("current");
	$("#firstpane .menu_body:eq("+listIndex+")").slideDown();
	*/
}
function getQueryParam(){
	var param={};
	var xzqhChecked = getSelectedDistrictTreeIds('selectXzqhTreeDiv');	       
	/*
	if(xzqhChecked.length>0){
		//alert(bmjb+"<----param.glbms-->"+xzqhChecked);
		if(bmjb=="1"||bmjb=="2"){
			param.glbms=xzqhChecked;
		}else{
			param.xzqhs = xzqhChecked;
		}		
	}else{		
		if(bmjb=="1"||bmjb=="2"){
			param.glbms="";
		}else{
			param.xzqhs ="";
		}
	}*/
	if(xzqhChecked.length>0){
		param.xzqhs = xzqhChecked;
	}else{
		param.xzqhs = "";
	}
	var roadChecked = getSelectedDistrictTreeIds('selectRoadTreeDiv');

	if(prevClickJtBtnId==""){
		curSelectedRoadNodes="";
	}else{
		curSelectedRoadNodes=roadChecked;
	}
	param.dldms =roadChecked ; //获得道路勾选	 roadChecked
	param.geo=drawPointOrPolylineWkt;//空间查询绘制的点、线WKT
	param.wkt=drawBufferWkt;//绘制后的缓冲区的WKT
	param.sjlx=zbzylx;//数据类型
	param.curSelectedRoad=curSelectedRoadNodes;
	if(bmjb=="1"){
		param.sjbj="1";
	}else{
		//param.sjbj="0";
		param.sjbj=$("input[name='roadBj']:checked")[0].value;
		//param.sjbj=$("input[name='roadBj']:checked")[0].value;
	}
	return param;
}

function clearResultHtml(valbtn){
	if(valbtn){
		$(("#"+valbtn)).html("");
	}
	/*
	$("#result_jtll").html("");
	$("#result_dlsg").html("");
	$("#result_jtsg").html("");
	$("#result_eltq").html("");	
	$("#result_qtzd").html("");*/
}
function bottomToolbarClick(buttonId){  //点击下方按钮查询	
	clearResultHtml(buttonId); //清空结果列表的 html
	clearTimer(); //去除查询计时器状态
	hideOtherTip(buttonId);//点击控件的时候，先把其他各个控件的tip隐藏.该方法在:--**本文件内**--
	//clickRoadTree(); //刷新道路树
	hideTreeDialog(); //隐藏树	
	
	if(buttonId!="zbjczy" || buttonId!="zbjlzy"){
		if(prevClickJtBtnId!=buttonId){
			canleCheck();
			prevClickJtBtnId=buttonId;
			curSelectedRoadNodes="";
		}else {
			if((prevClickJtBtnId==buttonId)&&($("#"+buttonId).attr("status")=="off")){
				curSelectedRoadNodes="";
				prevClickJtBtnId="";
			}
		}
	}
	var param = getQueryParam();
	if(buttonId=="zbjczy"){
		spatialSearchType="jczy";
		//在点击基础资源时候 初始化资源select
		var selectVal=$("#jczy_id option:selected").attr("value");		
		if(selectVal==undefined){
			setTimeout(function(){
				fillZySelect();
			},500);
		}	
		reoveBlockMouduleDraw();
		if($("#"+buttonId).attr("status")=="off"){
			//清除已经绘制缓冲边界
			removeDrawOverlay();
			//关闭资源显示窗体
			showOrCloseResourceWin(false);
		}
	}else if(buttonId=="zbjlzy"){
		spatialSearchType="jlzy";
		reoveBlockMouduleDraw();		
		if($("#"+buttonId).attr("status")=="off"){
			//清除已经绘制缓冲边界
			removeDrawOverlay();
			//关闭资源显示窗体
			showOrCloseResourceWin(false);
		}
	}else if(buttonId=="jtll"){
		//清除已经绘制缓冲边界
		reoveBlockMouduleDraw();
		removeClusterMarkers();//清除相关聚合物
		showOrHideRoadItem();
		if($("#"+buttonId).attr("status")=="off"){			
			//关闭资源显示窗体
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
		}else{			
			currentClickShower = buttonId;  
			showTrafficStateListContent(0);//交通流量按钮点击事件.该方法在:--**本文件内**--
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
		}		
	}else if(buttonId=="dlsg"){
		//清除已经绘制缓冲边界
		reoveBlockMouduleDraw();
		removeClusterMarkers();//清除相关聚合物
		$("#tipConditionWin").window("close");
		if($("#"+buttonId).attr("status")=="off"){
			//关闭资源显示窗体
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//右边显示结果收起到初始状态
		}else{
			currentClickShower = buttonId; 
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
			param.sjlxs = "A2";
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;

			getLatestBlockedSingle(param,"result_dlsg");		
			
			showTrafficStateListContent(1);//道路施工按钮点击事件.该方法在:--**本文件内**--			
	    }		
	}else if(buttonId=="jtsg"){
		//清除已经绘制缓冲边界
		reoveBlockMouduleDraw();
		removeClusterMarkers();//清除相关聚合物
		$("#tipConditionWin").window("close");
		if($("#"+buttonId).attr("status")=="off"){
			//关闭资源显示窗体
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//右边显示结果收起到初始状态
		}else{
			currentClickShower = buttonId; 
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
			param.sjlxs = "A3";
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;

			//请求后台
			getLatestBlockedSingle(param,"result_jtsg");		
			
			showTrafficStateListContent(2);//交通事故按钮点击事件.该方法在:--**本文件内**--
		}	
		
	}else if(buttonId=="eltq"){
		//清除已经绘制缓冲边界
		reoveBlockMouduleDraw();
		removeClusterMarkers();//清除相关聚合物
		$("#tipConditionWin").window("close");
		if($("#"+buttonId).attr("status")=="off"){
			//关闭资源显示窗体
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//右边显示结果收起到初始状态
		}else{
			currentClickShower = buttonId; 
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
			param.sjlxs = "A4";
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;
			
			//请求后台
			getLatestBlockedSingle(param,"result_eltq");					
			showTrafficStateListContent(3);//恶劣天气按钮点击事件.该方法在:--**本文件内**--
		}			
	}else if(buttonId=="qtzdsj"){	
		//清除已经绘制缓冲边界
		reoveBlockMouduleDraw();
		removeClusterMarkers();//清除相关聚合物
		if($("#"+buttonId).attr("status")=="off"){			
			//关闭资源显示窗体
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//右边显示结果收起到初始状态
		}else{
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;
			setTimeout(function(){
				showOtherBlock();			
			}, 500);
			currentClickShower = buttonId; 
			var sjlxStr = "";
			var otherBlockCheckedArray = $("input[name='otherBlockName']:checked");	
			for(var i=0; i<otherBlockCheckedArray.length; i++){
				if(i!=0){
					sjlxStr+=",";
				}
				sjlxStr+=otherBlockCheckedArray[i].id;
			}
			param.sjlxs = sjlxStr;
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;			
			showTrafficStateListContent(4);//恶劣天气按钮点击事件.该方法在:--**本文件内**--
		}
	};
}

/**
 * 
 * @param valparam
 * @param resultAccordion
 * @param sjlxs
 * @return
 */

var  queryParam = null;
function getLatestBlockedSingle(valparam,resultAccordion){	
	queryParam =valparam;
	showLoad();
	$.ajax({
		url:basePath+"jtztjc.gis?method=getLatestBlockedSingle",
		data: valparam,
		type:"post",
		dataType:"json",
		async:false,
		cache:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){	
			if(data.length==0){
				reoveBlockMouduleDraw();//清除绘制marker
				setTimeout(function(){	        		
	        		closeLoad();
	        	}, 500);	
				$(("#"+resultAccordion)).html("");
			}else{
				blockCommonInfo(data,resultAccordion,valparam.sjlxs);					
			}					
		},error:function(){
			closeLoad();
		}
	});	
}

function queryOtherBlockData(){
	
	showLoad();
	//请求后台
	var param = getQueryParam();
	var otherBlockCheckedArray = $("input[name='otherBlockName']:checked");	
	var sjlxStr = "";
	for(var i=0; i<otherBlockCheckedArray.length; i++){
		if(i!=0){
			sjlxStr+=",";
		}
		sjlxStr+=otherBlockCheckedArray[i].id;
	}
	param.sjlxs = sjlxStr;
	/**
	if(sjlxStr!=""){
		sjlxStr="A5,A6,A7,A8,A9";
	}
	*/
	eventRecord=param.xzqhs+","+param.dldms+","+param.sjlxs+","+param.sjbj+","+param.dldms+","+currentClickShower;
	$(function(){
		$.ajax({
			url:basePath+"jtztjc.gis?method=getLatestBlockedMul",
			type:"post",
			data: param,
			dataType:"json",
			async:false,
			cache:false,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			success:function(data){
				if(data.length==0){
					reoveBlockMouduleDraw();//清除绘制marker	
					$("#result_qtzd").html(""); 
					closeLoad();
				}else{								
		        	setTimeout(function(){	 
		        		blockCommonInfo(data,"result_qtzd",param.sjlxs);		        		
		        	}, 500);	
				}
			}
		});
	});
}

function blockCommonInfo(data,blockResultId,sjlxs){
	closeLoad();
	reoveBlockMouduleDraw();//清除绘制marker
	var zoom = map.getZoom();
	var htmlStr = "<div>";
	var minLon=0,maxLon=0,minLat=0,maxLat=0;
    for(var i=0; i<data.length; i++){        	
    	var id = data[i].id;
    	var sxh = data[i].sxh;
    	var xxlx = data[i].xxlx;
    	var sjnr = data[i].sjnr;
    	var sjjs= data[i].sjjs;
    	var xxly = data[i].xxly;
    	var geo = data[i].geo;
    	var marker = drawGeo(geo,xxlx,id);//绘制点、线	    	
    	var jd=marker.lonlat.lon;
    	var wd=marker.lonlat.lat;
    	 if(jd!=""&&wd!=""){
    		 marker.lonlat=new DMap.LonLat(jd,wd);
				if(minLon==0){
					minLon = jd;
			     	minLat = wd;
			     	maxLon = jd;
			     	maxLat = wd;
				}else{
				 if(maxLon<jd){
			        maxLon = jd;
			      }             	
			      if(maxLat<wd){
			        maxLat = wd;
			      }
			 	if(minLon>jd){
		           minLon = jd;
		         }	
			 	if(minLat>wd){
			       minLat = wd;
			    } 
				} 
		}
    	if(zoom>9){
        	//var marker = drawGeo(geo,xxlx,id); //绘制点、线	   
        	map.addOverlay(marker);
        	drawMarkerArray.push(marker);	
        	//对获得查询结果地图居中	        
    	}
    	if(i<=10){
    		htmlStr += "<a href='#' style='font-size:12px;' onclick='panToGeo(\""+geo+"\",\""+id+"\")' title='"+sjnr+"'>"+sjjs+"</a>";        		
    	}
    }       
	if(data.length>9){
		htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:gis_block_list(\'"+sjlxs+"\');return false;>更多信息</a>";
	}
    htmlStr+="</div>";
    $("#"+blockResultId).html(htmlStr);
    
	
	/**
	 * 根据以下规则显示结果集合
	 * 1、8级以下以省为中心定位统计结果图标
	 * 2、8-9级别 以市为中心统计结果 并显示图标
	 * 3、10级以上 聚合显示结果内容 
	 * 4、点击每个统计数字时候，以当前定位中心为地图中心点
	 */
	if(zoom<8){//总队第1次跳转(统计省级信息)
		queryBlockCenterCombine('0');
	}else if(zoom==8||zoom==9){//总队第2次跳转 (统计市级信息)
		queryBlockCenterCombine('1');
	}else{
	    if(minLon>0&&maxLon>0&&minLat>0&&maxLat>0){
		    var minx = parseFloat(minLon)-0.2;
	        var miny = parseFloat(minLat)-0.2;
	        var maxx = parseFloat(maxLon)+0.2;            
	        var maxy = parseFloat(maxLat)+0.2;		           
	        var statCenterX=0.5*(parseFloat(minLon)+parseFloat(maxLon));
	        var statCenterY=0.5*(parseFloat(minLat)+parseFloat(maxLat));
	        var statCenter=new DMap.LonLat(statCenterX,statCenterY);  
	        var curLevel=map.getZoom();
	        if(curLevel==8||curLevel==9){
	        	 //map.setCenter(statCenter,curLevel);
	        	 queryBlockCenterCombine('1');
	        }else if((curLevel>=10&&curLevel<=13)){	
	        	//map.setCenter(statCenter,curLevel);
	        	drawBlockMarkersFunc(true);
	        	//map.centerMBR(new DMap.LonLatBounds(minx,miny,maxx,maxy));            	
	        }else{
	        	drawBlockMarkersFunc(false);
	        	//map.setCenter(statCenter,curLevel);
	        }         
	    }else{
	    	if(maxLon>0&&maxLat>0){
				var statCenter=new DMap.LonLat(maxLon,maxLat);
				//map.setCenter(statCenter,10);
			}
	    }
    }
    zoomVal=0;
    //激活绑定地图
    if(!ifAddBlockListener){
    	ifAddBlockListener = true;
		 DMap.$(map).bind('zoomend.blockInfo',function(){
			 if(currentClickShower==""){
			    return;
			 }
		     var zoom = map.getZoom();
		     switch(true){
				case zoom<7://部局(统计省级信息)			         
		        	 reoveBlockMouduleDraw();
		        	 queryBlockCenterCombine('0');	
					break;
				case zoom<8://总队第1次跳转(统计省级信息)
					reoveBlockMouduleDraw();
					queryBlockCenterCombine('0');
					break;
				case (zoom==8||zoom==9)://总队第2次跳转 (统计市级信息)
		        	 reoveBlockMouduleDraw();
		        	 queryBlockCenterCombine('1');	
					break;
				default:
					reoveBlockMouduleDraw();
					removeClusterMarkers();
					if(currentClickShower=="qtzdsj"){
		         		queryOtherBlockData();
		         	}else{
		         		bottomToolbarClick(currentClickShower);
		         	}
					break;
		     }	
		     closeLoad();
		     zoomVal=zoom;
		 });
    }
    

	      
}

//ifClusterMode：true、不显示名称，false、显示名称 	
function drawBlockMarkersFunc(ifClusterMode){
	if (ifClusterMode) { // 是否做聚合效果
		var imageUrl=modelPath + 'images/markers/m1.png';
		var clusterOption = {
			gridSize : 60,
			minimumClusterSize : 3,
			maxZoom : 14,
			// imagePath:getMakerImg[dmz]['cluster'],
			imagePath : imageUrl,
			imageExtension : ".png",
			zoomOnClick : true,
			maxZoomFunc : "drawBlockMarkersFunc(false)"
		};
		var markerCluster = new DMap.MarkerClusterer(map, drawMarkerArray,
				clusterOption);
		blockMarkerClusterArray.push(markerCluster);
	}
    closeLoad();
}

// 得到其它阻断数据
function getOtherBlockSjlx(){
	var otherBlocks = $("input[name='otherBlockName']:checked");
	var otherBlockStr="";	
	for(var i=0; i<otherBlocks.length; i++){
	   	 if(i!=0){
	   		otherBlockStr+=",";
		 }
		 otherBlockStr+=otherBlocks[i].id;		
	}
	/**
	if(otherBlockStr==""){
		otherBlockStr="A5,A6,A7,A8,A9";
	}
	*/
	return otherBlockStr;
}
/*
//阻断
function gis_block_list(xxlx){
	var param = getQueryParam();
	param.xxlx=xxlx;
	return;
}
//流量
function gis_flow_list(){
	var param = getJtllParams();
	return;
}
*/
//************************ 等待条 ***************************  

function showLoad(valmesg){
	var msg = "正在加载资源,请稍候。。。。。。";
	if(valmesg){
		msg = valmesg;
	}
	var loadDiv= document.getElementById("ly");
	if(!loadDiv){  
		lyDiv = document.createElement("div");
	    lyDiv.id = "ly";
	    lyDiv.style.position = "absolute";
	    lyDiv.style.width = document.body.clientWidth;
	    lyDiv.style.height = document.body.clientHeight;
	    lyDiv.style.top = "0px";
	    lyDiv.style.left = "0px";
	    lyDiv.style.filter = "alpha(opacity=80)";
	    lyDiv.style.backgroundColor = "#777";
	    lyDiv.style.zIndex = 9999;
	    lyDiv.style.display = "block";//absolute
	    var leftVal=(document.body.clientWidth)/2-107;   
	    var imgHtml = "<img style='position:relative ;left:"+leftVal+";top:50%' src='"+modelPath+"images/business/zhzx/progressbar_green.gif' />"; 
	    imgHtml+="<br /><span style='position:relative ;left:"+leftVal+";top:51%;font-size:14px'>"+msg+"</span>";
	    lyDiv.innerHTML = imgHtml; 
	    document.body.appendChild(lyDiv); 
	}
           
};

function closeLoad(){
    try {
        document.body.removeChild(this.lyDiv);        
    } 
    catch (e) {
//        alert("请求失败！");
    }
};

