var showBasicType=false; //资源分类展示打开状态

String.prototype.Trim = function(){ 
    return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
String.prototype.LTrim = function(){ 
    return this.replace(/(^\s*)/g, ""); 
} 
String.prototype.RTrim = function(){ 
    return this.replace(/(\s*$)/g, ""); 
} 

$(document).ready(function() {
	showLoad(); //显示等待条
	initMap();
	var toolBar = new DMap.ZhzxToolBarControl();
	map.addControl(toolBar);
	
	getTraficEventCatalog(); //获得交通事件分类
	initResourceSelect();//初始化其他资源Select内容
	$('#glxqFloatWindow').window({left:"80px", top:"60px",collapsed:false,collapsible:true,closable:true,closed:true});
	$('#roadFloatWindow').window({left:"80px", top:"60px",collapsed:false,collapsible:true,closable:true,closed:true});
	$('#guideFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#resourceFloatWindow').window({left:"80px", top:"30px",collapsed:false,collapsible:true,closable:true,closed:true});
	$('#trafficEventWindow').window({left:"80px", top:"60px",collapsed:false,collapsible:true,closable:true,closed:true});
	showSyncDistrictTree("selectXzqhTreeDiv"); //初始化行政区划树
	showRoadTypePage('selectRoadDiv'); //初始化道路类型

    $('#trafficEventWindow').window({
        onBeforeClose:function(){ 
        	hideEventDialog();
        }
    });
    closeLoad(); //去除等待条
    $('#yjqr').click(); //初始化预警确认  
    //用户显示区域及初始化查询条件
	if(bmjb=="1"){ //部局级
		if(!isNaN(bcenterx)){
			map.setCenter(new DMap.LonLat(parseFloat(bcenterx), parseFloat(bcentery)),5, null);	 
		}		
	}else{ 
		mapToInitMBRAndCenter();
	}
    setInterval(function(){ //十分钟刷新一次预警信息
    	guide_queryEventData("yjqr","btn"); //btn 与 timer两种类型是为了区分是5秒查询还是按钮调用
    },10*60*1000);
  //为地图添加监听事件
    zoomVal=0;
    DMap.$(map).bind('zoomend.yjzhZoomend',function(){
	    var zoom = map.getZoom();
	    if(zoomVal!= zoom){ //部局级	
	    	map.closeInfoWindow();
	    }
	    zoomVal = zoom;
    }); 
	findDimensions();
	setBottomBarCollapse();
	//closed:true, 
	toolBar.addLeftTool({
		name: '交通事件',
		id: 'tool-jczyfl',
		pushGroup: 'tool',
		defaultImge:modelPath+"images/business/common/toolbar/zhzx_jczyfl.jpg",
		activeImage:modelPath+"images/business/common/toolbar/zhzx_jczyfl_hover.jpg",
		onActive: function (){
			showBasicItem();					
		}
	});
	toolBar.addLeftTool({
		name: '管理辖区',
		id: 'tool-xzqh',
		pushGroup: 'tool',
		defaultImge:modelPath+"images/business/common/toolbar/zhzx_xzqh.jpg",
		activeImage:modelPath+"images/business/common/toolbar/zhzx_xzqh_hover.jpg",
		onActive: function (){	
			hideEventDialog();
			$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:false});
			$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
			$('#trafficEventWindow').window({collapsed:false,collapsible:true,closed:true});
			$('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:true});		    
		}
	});
	toolBar.addLeftTool({
		name: '道路',
		id: 'tool-road',
		pushGroup: 'tool',
		defaultImge:modelPath+"images/business/common/toolbar/zhzx_road.jpg",
		activeImage:modelPath+"images/business/common/toolbar/zhzx_road_hover.jpg",
		onActive: function (){
			hideEventDialog();
			$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
			$('#roadFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:false});
			$('#trafficEventWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
			$('#resourceFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
		}
	});
	/*
	setTimeout(function(){
		closeLoad(); //去除等待条
	},500);
	*/
});



function showBasicItem(){
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#trafficEventWindow').window({collapsed:false,collapsible:true,closed:false});
	$('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:true});
}
/**道路类型显示*/
function showRoadTypePage(divId){
	$.ajax({
		url : basePath+"yjzh.gis?method=getRoadTypeTreeJson",
		type : "post",
		dataType:"json",
		success : function(data) {
			var roadTypeHtml = "";
			for(var i=0; i<data.length; i++){
				roadTypeHtml+="<li style='list-style-type:none'><input type='checkbox' name='roadTypeCheck' onclick='resetQueryTimer()' value='"+data[i].dllxdm+"'>"+data[i].dllxmc+"</li>";
			}
			$("#"+divId).html(roadTypeHtml);
		}
	})
}


var Collapseflag=1;//标识是否下方工具条折叠
var bottombarWind=null;
function setBottomBarCollapse(){//设置下方工具条的折叠
	/*
	$('#bottomToolbar').window({
	    tools:[{
            iconCls:'panel-tool-collapse',
            handler:function(){
            	var winHeight =document.body.clientHeight;
            	var btbExpandTop=winHeight-100;
            	var btbCollapseTop=winHeight-100;
            	if(Collapseflag==1){
    				Collapseflag=0;
    				$('#bottomToolbar').window({left:"0px", top:btbExpandTop+"px"});
    			}else{
    				Collapseflag=1;
    				$('#bottomToolbar').window({left:"0px", top:btbCollapseTop+"px"});
    			};
            }
        }]
	});*/
	bottombarWind =$('#bottomToolbar').window({collapsible:true,onCollapse:function(){
		if(bottombarWind!=null){
			var winHeight =document.body.clientHeight;
			bottombarWind.window("resize",{top:winHeight-30});
		}
	},onBeforeExpand:function(){
		if(bottombarWind!=null){
			var winHeight =document.body.clientHeight;
			bottombarWind.window("resize",{top:winHeight-105});
		}
	}});
}

/**
 * 设置页面中各个控件的位置：topbar和bottombar
 */
function findDimensions() { //函数：获取尺寸,并设置各个控件的初始化位置。
	var winWidth =document.body.clientWidth;
	var winHeight =document.body.clientHeight;
    var btbTop=winHeight-85;
    $('#bottomToolbar').window({left:"0px", top:btbTop+"px"});
    //初始化资源位置  
    $('#guideFloatWindow').window({left:winWidth-280+"px", top:"80px"});
    //设置tipWindow 位置
    if(tipWinHasInitial){
   	 var tipWinClosed=$('#tipConditionWin').window("options").closed;
	    if(!tipWinClosed){	 	    
	 	    //导入缩放窗体前的内容	
	    	var left=$("#"+showWindowTipId).offset().left;
			//获得window的高度
			var winHeight=$('#tipConditionWin').window("options").height;
			var top=$("#"+showWindowTipId).offset().top-(winHeight+30);
			fillTipWindowContent(showWindowTipId);
			if(showWindowTipId=="qtzy"){
	 			setTimeout(function(){
	 				fillZySelect();			
				}, 500);
		 	} 			
			$("#tipConditionWin").window("open");
			$("#tipConditionWin").window({left:left,top:top,collapsed:false,collapsible:false,draggable:false,closed:false,closable:true});
			
			 						
		}
	}
}

window.onresize=findDimensions;