var drawPointOrPolylineWkt="";//绘制缓冲区的wkt
var drawBufferWkt="";//绘制的缓冲区边界Wkt
var zbzylx="";//空间查询时候需要查询的资源类型
var zySelectList=null;
var drawPointMarker=null;
var drawPolyline=null;
var bufferOverlay=null;
var resourceMarkerJsonArray=[];//存放资源的Markerjson数组
var drawResourceMarkerType="";//当前需要绘制的资源marker类型
var currentResourceWinType="";//当前打开的窗体是那一种资源类型


var tipWinHasInitial=false;//判断window是否已经初始化
var showWindowTipId="";//存放最后点击显示条件的tipId 用于窗体缩放时使用
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
}

function fillTipWindowContent(tipId) {
	showWindowTipId=tipId;
	switch (tipId) {
	case "zbsp":
		createOrReplaceTipContent("周边视频","yjzh.gis?method=zbsp",230,120);
		break;
	case "zbjl":
		createOrReplaceTipContent("周边警力","yjzh.gis?method=zbjl",240,150);	
		break;
	case "yjzy":
		createOrReplaceTipContent("应急资源","yjzh.gis?method=yjzy",270,170);
		break;
	case "qtzy":
		createOrReplaceTipContent("其他资源","yjzh.gis?method=qtzy",260,160);
		break;
	}
}
//记录底部每一个按钮的开关状态
var leftToolBarsStatus = [{
	"btnId":"zbsp",
	"status":"off"//周边视频
	},
	{"btnId":"zbjl",
	 "status": "off"//周边警力
	},
	{"btnId":"yjzy",
	 "status": "off"//警力资源
	},{
	"btnId":"qtzy",
	"status": "off"//其他资源
	}];

var rightToolBarsStatus = [ {
	"btnId" : "yjqr",
	"status" : "off"// 预警确认
}, {
	"btnId" : "rgcj",
	"status" : "off"// 人工采集
}, {
	"btnId" : "yjcz",
	"status" : "off"// 预警处置
}, {
	"btnId" : "czfk",
	"status" : "off"//处置反馈
} ];


function removeDrawOverlay(){
	if(drawPointMarker!=null){
		map.removeOverlay(drawPointMarker);	
	}		
	if(drawPolyline!=null){
		map.removeOverlay(drawPolyline);
	}
	if(bufferOverlay!=null){
		map.removeOverlay(bufferOverlay);
	}
	drawPointOrPolylineWkt="";
	drawBufferWkt="";
}

function clearResourceMarkers(types){
	for(var h=0; h<resourceMarkerJsonArray.length; h++){
		if(resourceMarkerJsonArray[h].lx==types){
			var markerArray = resourceMarkerJsonArray[h].val;
			for(var j=0; j<markerArray.length; j++){
				if(markerArray[j]){
					map.removeOverlay(markerArray[j]);
				}				
			}
		}
	}
	map.closeInfoWindow();
}
/**
 * 添加资源的Maker到JSON数组之中用于清除使用
 * @param resourceType 资源类型
 * @param easyuiMarkerData 
 */
function addResourceMarkers(resourceType,easyuiMarkerData){
	var drawMakerArray = [];
	//解析easyUi数据
	for(var i=0; i<easyuiMarkerData.rows.length; i++){
		var sjlx = easyuiMarkerData.rows[i].sjlx;
		var kklx = easyuiMarkerData.rows[i].tz; //卡口类型判断
		var idlx=easyuiMarkerData.rows[i].idlx;//主要用于警车(汽车、摩托)
		var id=easyuiMarkerData.rows[i].id;
		var geo = easyuiMarkerData.rows[i].geo;
		var maker =null;
		if(sjlx=="18"){
			maker = drawResourceGeo(geo,sjlx,id,idlx);
		}else{
			maker = drawResourceGeo(geo,sjlx,id,kklx);
		}
		drawMakerArray.push(maker);
	}
	//分类型装载查询结果
	var markerJson = {
		lx : '',
		val : []
	}
	markerJson.lx=resourceType+"";
	markerJson.val = drawMakerArray;
	resourceMarkerJsonArray.push(markerJson);

}

function drawResourceGeo(geo,xxlx,id,kklx){
	if(xxlx=="01"){//卡口
		if(kklx=="国界卡口"){
		var imgurl = getMakerImg[xxlx]['01']['01'];			
		}else if (kklx=="省际卡口"){
		var imgurl = getMakerImg[xxlx]['02']['01'];			
		}else if (kklx=="市际卡口"){
		var imgurl = getMakerImg[xxlx]['03']['01'];				
		}else if (kklx=="县际卡口"){
		var imgurl = getMakerImg[xxlx]['04']['01'];				
		}else if (kklx=="公路主线卡口"){
		var imgurl = getMakerImg[xxlx]['05']['01'];			
		}else if (kklx=="公路收费站卡口"){
		var imgurl = getMakerImg[xxlx]['06']['01'];			
		}else if (kklx=="城区道路卡口"){
		var imgurl = getMakerImg[xxlx]['07']['01'];			
		}else if (kklx=="城区路口卡口"){
		var imgurl = getMakerImg[xxlx]['08']['01'];				
		}		
	}else if(xxlx=="16"){
		if(kklx=="部局"){
			var imgurl = getMakerImg[xxlx]['1'];
		}else if(kklx=="总队"){
			var imgurl = getMakerImg[xxlx]['2'];
		}else if(kklx=="支队"){
			var imgurl = getMakerImg[xxlx]['3'];
		}else if(kklx=="大队"){
			var imgurl = getMakerImg[xxlx]['4'];
		}else if(kllx=="中队"){
			var imgurl = getMakerImg[xxlx]['5'];
		}else{
			var imgurl = getMakerImg[xxlx]['onlyone'];
		}
	}else { //其他资源
		var imgurl = getMakerImg[xxlx]['onlyone'];
	}
	//var imgurl = getMakerImg[xxlx]['onlyone'];
	if(xxlx=="18"){//警车
		xxlx = const_gis_marker_car_id;
		if(kklx=="警用汽车"){
			imgurl=getMakerImg[xxlx]['onlyone'];
		}else if(kklx=="警用摩托"){
			imgurl=getMakerImg[xxlx]['motorcycle'];
		}
	}else if(xxlx=="19"){ //警员
		xxlx = const_gis_marker_person_id;
	}
	var symbol = {
	     url : imgurl,
		 size : new DMap.Size(36,36),
		 offsetType : "mm", 
		 borderWidth : 4,
		 borderColor : "red",
		 color :"white",
		 opacity : 1  			
	};
	var marker = DMap.Overlay.createByWKT(geo,symbol);	
     marker.xxlx=xxlx;
     marker.id=id;
     marker.lonlat=marker.getLonlat();
	 marker.imgUrl = imgurl;
	 marker.popupWidth = 300;
	 marker.setCommonEvent(); //激活marker的点击事件
	 DMap.$(marker).bind("click",function(e){
		 openGisInfoWindowHtml(marker,map);
     });
	 map.addOverlay(marker);	
	 return marker;
}


//定位到指定图标
var selectedResourceObj;
function panToResourceGeo(id,type){
	//clearResourceFlash();
	var curlevel=map.getZoom();
	for(var h=0; h<resourceMarkerJsonArray.length; h++){
		if(resourceMarkerJsonArray[h].lx==type){
			var markerArray = resourceMarkerJsonArray[h].val;
			if(markerArray.length!=0){
				for(var i=0; i<markerArray.length; i++){
					if(markerArray[i].id==id){
						var symbol=markerArray[i].getSymbol();
						var lon=markerArray[i].lonlat.lon;
						var lat=markerArray[i].lonlat.lat;
						var lonLat=new DMap.LonLat(lon,lat);
					   FLASH_MARKER = new DMap.Marker(lonLat,symbol);
					   if(curlevel<15){
							 map.setZoom(15);
						}
					   map.panTo(markerArray[i].lonlat);
					   flashMark();
					}
				}
			}	
		}
	}
}
//清除闪动效果
/*
function clearResourceFlash(){
	if(selectedResourceObj){
		selectedResourceObj.clearFlash();
		selectedResourceObj=null;
	}	
}
*/
/**
 * 显示或者关闭资源窗体
 * @param isShow
 */
function showOrCloseResourceWin(isShow){
	if(isShow){
	    $('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:false});
    }else{
    	 $('#resourceFloatWindow').window({collapsed:false,collapsible:false,closable:true,closed:true});
    }	
}

function setLeftToolBarsCssAndTip(toolId){
	var setCssBtnId="";
	var setCssIndex=0;
	for(var i=0;i<leftToolBarsStatus.length;i++){
		if(toolId==leftToolBarsStatus[i].btnId){
			setCssBtnId=toolId;	
			setCssIndex=i;
			if($("#"+toolId).attr("status")=="off"){
				leftToolBarsStatus[i].status="on";
			}else{
				leftToolBarsStatus[i].status="off";
			}
		}
	}
	if(setCssBtnId!=""){
		//先设置显示或者隐藏ToolTip
		if($("#"+setCssBtnId).attr("status")=="off"){
			fillTipWindowContent(toolId);//填充或者替换window内容
			var left=$("#"+toolId).offset().left;
			//获得window的高度
			var winHeight=$('#tipConditionWin').window("options").height;
			var top=$("#"+toolId).offset().top-(winHeight+30);
			$("#tipConditionWin").window({left:left,top:top,collapsed:false,collapsible:false,draggable:false,closed:false,closable:true});
			$("#tipConditionWin").window("open");		
		}else{
			$("#tipConditionWin").window("close");
		}
		//再设置工具按钮样式
		setToolBarCss(setCssBtnId);
	}
}

/**
 * 设置工具条样式
 * @param toolId
 */
function setToolBarCss(toolId){
	if($("#"+toolId).attr("status")=="off"){
		//给当前的按钮设置自定义属性 status
		$("#"+toolId).attr("status","on");
		//设置打开状态的样式
		$("#"+toolId).addClass(toolId+"On").removeClass(toolId+"Off");			
	}else{
		$("#"+toolId).attr("status","off");
		//设置为关闭状态的样式
		$("#"+toolId).addClass(toolId+"Off").removeClass(toolId+"On");			
	}
}

function setRightToolBarCss(toolId){
	//设置当前当即按钮样式；清除右边其他按钮选中样式及显示内容
	for(var i=0;i<rightToolBarsStatus.length;i++){
		var rightToolBarId=rightToolBarsStatus[i].btnId;
		if(toolId==rightToolBarId){
			if($("#"+toolId).attr("status")=="off"){
				//给当前的按钮设置自定义属性 status
				rightToolBarsStatus[i].status="on";	
				$("#"+toolId).addClass(toolId+"On").removeClass(toolId+"Off");
				$("#"+toolId).attr("status","on");
			}else{
				rightToolBarsStatus[i].status="off";
				$("#"+toolId).addClass(toolId+"Off").removeClass(toolId+"On");
				$("#"+toolId).attr("status","off");
			}
		}else{
			//设置为关闭状态的样式
			$("#"+rightToolBarId).addClass(rightToolBarId+"Off").removeClass(rightToolBarId+"On");	
			rightToolBarsStatus[i].status="off";
			$("#"+rightToolBarId).attr("status","off");
		}
	}
}

//控制底部按钮的样式函数
function bottomToolbarClick(toolId){
	//如果是左边工具使用左边的样式和事件
	if($("#"+toolId).attr("toolType")=="left"){
		setLeftToolBarsCssAndTip(toolId);
		if($("#"+toolId).attr("status")=="off"){
			clearResourceMarkers(toolId);//清楚当前的Markers
			//如果是当前资源窗体和绘制的Buffer也关闭之	
			if(currentResourceWinType==toolId){
				showOrCloseResourceWin(false);
				removeDrawOverlay();
			}			
		}else{
			currentResourceWinType=toolId;			
		}
		//在其他资源添加select
		if(toolId=="qtzy"&&($("#"+toolId).attr("status")=="on")){
			//在点击基础资源时候 初始化资源select
			var selectVal=$("#qtzy_select option:selected").attr("value");		
			if(selectVal==undefined){
				setTimeout(function(){
					fillZySelect();
				},500);
			}	
		}
	}
	//如果是右边的工具设置右边的工具样式和事件
	if($("#"+toolId).attr("toolType")=="right"){
		if(tipWinHasInitial){
			$("#tipConditionWin").window("close");
		}		
		//设置按钮
		setRightToolBarCss(toolId);
		//调用处理函数		
		emergencyCommandClick(toolId);
	}
}


/**
 * 调用应急指挥相关的接口函数
 * @param toolId
 */
function emergencyCommandClick(toolId){
	clearTimer();//清除计时器
	if($("#"+toolId).attr("status")=="off"){		
		clearMakerInType(toolId);
		currentClickShower="";
		return;
	}else{
		currentClickShower=toolId;
	}
	
	switch(toolId){
		case "yjqr"://预警确认	
			/*
			clearMakerInType("yjcz,czfk");
			map.closeInfoWindow();
			showLoad();
			setTimeout(function(){
				guide_queryEventData(toolId,"btn");
			},500);		
			*/	
			guide_queryEventData(toolId,"btn");
			break;
		case "rgcj"://人工采集
			guide_jumpToRgcjPage();
			break;
		case "yjcz"://预警处置
			/*
			clearMakerInType("yjqr,czfk");
			map.closeInfoWindow();
			showLoad();
			setTimeout(function(){
				guide_queryEventData(toolId,"btn");
			},500);	
			*/
			guide_queryEventData(toolId,"btn");
			break;
		case "czfk"://处置反馈
			/*
			clearMakerInType("yjqr,yjcz");
			map.closeInfoWindow();
			showLoad();
			setTimeout(function(){
				guide_queryEventData(toolId);
			},500);	
			*/
			guide_queryEventData(toolId);
			break;
	}
}

/**
 * 
 * 验证缓冲区输入距离是否为正整数 
 * @param inputId
 * @returns {Boolean}
 */
function  validationNumber(inputId){
	var result=false;
	var bufferDis=$("#"+inputId).val();
	//判断缓冲距离是否为数字
	var reg=/^[0-9]*[1-9][0-9]*$/;//正整数  
	if(reg.test(bufferDis)){
		if(bufferDis>500000){
			alert("缓冲距离不要大于500km!");
			result=false;
		}else{
			result=true;
		}
	}else{
		alert("要输输入的为整型数字!");
		$("#"+inputId).focus().select();
		result=false;
	}
	return result;
}

/**
 * 获得指定经度、纬度 保留多少位数
 * @param lonlats
 * @param lonIndex 
 * @param latIndex 
 */
function getAssignPrecisionLonLat(lonlats,lonIndex,latIndex){
	var lonVal=lonlats.lon;
	var lon=formatFloat(lonVal, lonIndex);	
	var latVal=lonlats.lat;
	var lat=formatFloat(latVal, latIndex);	
	var lonLat=new DMap.LonLat(lon,lat);
	return lonLat;
}

/**
 * 对浮点数进行格式化操作
 * @param num 待格式化的数字
 * @param pos 需要保留的小数位数
 * @returns
 */
function formatFloat(num, pos) {
	var cn = String(Math.round(num * Math.pow(10, pos)) / Math.pow(10, pos));
	var i = cn.indexOf(".");
	if (cn.indexOf(".") == -1) {
		cn += ".";
		while (pos > 0) {
			cn += "0";
			pos--;
		}
	} else {
		while (pos >= num.length - i) {
			cn += "0";
			pos--;
		}
	}
	return cn;
}

function createBufferOverlay(wktString,meter){
	var degree=DMap.Util.meterToDegree(meter);
	var baseGeometry=new DMap.Geometry(wktString);
    var bufferWKT=baseGeometry.buffer(degree).toString();
    drawBufferWkt=bufferWKT;//存放到全局变量之中获得空间查询结果
	var bufferOverlay=DMap.Overlay.createByWKT(bufferWKT,{color:"yellow"});
	return bufferOverlay;
}

/**
 * 统一调用的绘制缓冲区查询函数
 * @param resourceType
 * @param drawBufferType
 * @param bufferInputId
 * @param spatialSearchFun
 */
function drawBufferSearch(resourceType, drawBufferType, bufferInputId,
		spatialSearchFun) {
	//验证是否输入了正确缓冲区距离
	var result = validationNumber(bufferInputId);
	if (!result) {
		return;
	}
	var bufferDis = "";
	if(drawBufferType=="Point"){
		map.activateTool('POINT', function(e, lonlats) {	
			//清除上一次当前类别资源的 Markers
			clearResourceMarkers(resourceType);
			removeDrawOverlay();
			bufferDis = $("#"+bufferInputId).val();
			var lonLatObj=getAssignPrecisionLonLat(lonlats,12,11);
			//把填写的名称作为标签 绘制到地图上
			var markerSymbol={type:0,labelColor:'blue',labelBackgroundColor:"#4cb748",url:bufferMarkerUrl};
			var marker = new DMap.Marker(lonlats,markerSymbol);
			drawPointMarker=marker;	
			var wktString = "POINT("+lonLatObj.lon+" "+lonLatObj.lat+")";
			drawPointOrPolylineWkt=wktString;//放置全局变量之中
			var pointBufferOverlay=createBufferOverlay(wktString,bufferDis);
			bufferOverlay=pointBufferOverlay;
			map.addOverlay(pointBufferOverlay);
			map.addOverlay(marker);
			map.deactivate();
			//执行空间查询的函数		
			eval(spatialSearchFun+'("'+resourceType+'")');
		});
	}
	if(drawBufferType=="Polyline"){
		map.activateTool('POLYLINE', function(e, lonlats) {
			//清除上一次当前类别资源的 Markers
			clearResourceMarkers(resourceType);
			removeDrawOverlay();
			bufferDis = $("#"+bufferInputId).val();
			var polyline = new DMap.Polyline(lonlats, {
				color : 'blue',
				endarrow : "Classic",
				weight : 5,
				animateFromColor : 'red'
			});
			var arr = [];
			$.each(lonlats, function(i, lonlat) {
				arr.push(lonlat.lon + " " + lonlat.lat);
			});
			var wktString = "LINESTRING(" + arr.join(",") + ")";
			drawPointOrPolylineWkt=wktString;//放置全局变量之中
			var lineBufferOverlay = createBufferOverlay(wktString, bufferDis);
			bufferOverlay = lineBufferOverlay;
			map.addOverlay(lineBufferOverlay);
			drawPolyline = polyline;
			map.addOverlay(polyline);
			map.deactivate();
			//执行空间查询的函数
			eval(spatialSearchFun+'("'+resourceType+'")');
		});	
	}
}

function initResourceSelect(){
	$.ajax({ 
		url: basePath+"yjzh.gis?method=getAllTypeList",
		type:"post",
		cache:false,
		async:false,
		dataType:"json",
		success: function(data){
			if(data.length>0){
				zySelectList=data;
			}  
		}
	});
}

function fillZySelect(){
	if(zySelectList!=null){
		jQuery("#qtzy_select").empty();
		if(zySelectList.length>0){
			for(var i=0;i<zySelectList.length;i++){
				var value=zySelectList[i]["xxlx"];
				var text=zySelectList[i]["sjnr"];
				$("#qtzy_select").append("<option value='"+value+"'>"+text+"</option>");
			}				
		}	
	}
}

/**
 *执行空间缓冲区查询 与后台交互 
 * @param resourceType
 */
function spatialBufferSearch(resourceType){
	showSpatialSearchResult(resourceType);
	//显示周边资源窗体
	var winIsShow="";
	switch(resourceType){
		case "zbsp":
			winIsShow=$('#zpsp_cbk').attr("checked");
			break;
		case "zbjl":
			winIsShow=$('#zbjl_cbk').attr("checked");
			break;
		case "yjzy":
			winIsShow=$('#yjzy_cbk').attr("checked");
			break;
		case "qtzy":
			winIsShow=$('#qtzy_cbk').attr("checked");
			break;
	}
	if(winIsShow=="checked"){
		showOrCloseResourceWin(true);
	}else{
		showOrCloseResourceWin(false);
	}
}

/**
 * 获得控件查询所需的参数
 */
function getSpatialQueryParams(){
	var spatialQueryParams={};
	//行政区划
	var xzqhs = getSelectedDistrictTreeIds("selectXzqhTreeDiv");
	//绘制的point或者 线polyline
	spatialQueryParams.sjlx=zbzylx;//从全局变量之中获得当前需要查询资源类型
	spatialQueryParams.xzqhs=xzqhs;
	spatialQueryParams.geo=drawPointOrPolylineWkt;//空间查询绘制的点、线WKT
	spatialQueryParams.wkt=drawBufferWkt;//绘制后的缓冲区的WKT;	
	return spatialQueryParams;
}

/**
 * 显示空间搜索结果
 * @param resourceType
 */
function showSpatialSearchResult(resourceType){
	var resultHead="";
	var isShowWin=false;
	var reqParams={}; //获得datagrid的参数
	drawResourceMarkerType=resourceType;//设置当前绘制的Marker类型
	//清空下方DateGrid
	$('#zy').datagrid('loadData',{total:0,rows:[]});
	switch(resourceType){
		case "zbsp"://周边视频
			resultHead="视频";
			//执行空间查询参数	考虑到直接重用交通阻断的	
			zbzylx="03";		
			reqParams=getSpatialQueryParams();
			//直接调用显示视频
		    fillResourceList(resultHead,reqParams);
			break;
		case "zbjl"://周边警力
			//获得警力资源当前选择类别
			var jlzylx=[];
			$('input[name="zbjlzy_cbk"]:checked').each(function(){
				jlzylx.push($(this).val());	
			 });
			zbzylx=jlzylx.join();//查询的警力资源	
			//获得空间 查询相关参数
			reqParams=getSpatialQueryParams();
			fillPoliceforceList(reqParams);
			break;
		case "yjzy"://应急资源
			var yjzylx=[];
			$('input[name="yjzy_cbk"]:checked').each(function(){
				yjzylx.push($(this).val());	
			 });			 
			resultHead="应急资源";
			if((yjzylx.length>=1)&&(yjzylx.length<4)){
				zbzylx=yjzylx.join()+",";//查询的应急资源
			}else{
				zbzylx=yjzylx.join();//查询的应急资源
			}			
			//获得空间 查询相关参数
			reqParams=getSpatialQueryParams();
			//需要添加一个此类型的函数
			fillResourceList(resultHead,reqParams);
			break;
		case "qtzy"://其他资源
			resultHead=$("#qtzy_select").find("option:selected").text();
			var zylx=$("#qtzy_select").find("option:selected").val();
			zbzylx=zylx;//查询的其他资源	
			//获得空间 查询相关参数
			reqParams=getSpatialQueryParams();
		    fillResourceList(resultHead,reqParams);
			break;
	}		
}
