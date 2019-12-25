var roadType="1";//道路类型：highWay 1：公路 cityRoad：城市道路 2
var spatialSearchType="jczy";//默认是周边资源
var drawPointMarker=null;
var drawPointOrPolylineWkt="";//绘制缓冲区的wkt
var drawBufferWkt="";//绘制的缓冲的Wkt
var zbzylx="";//当前选择的周边资源类别
var drawPolyline=null;
var bufferOverlay=null;
var zySelectList=null;
var drawResourceMarkerType="";//当前需要绘制的资源marker类型
var resourceMarkerJsonArray=[];//存放资源的Markerjson数组

//===================基础资源基本操作===============================	

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
	//清除掉上一次绘制的资源Markers
	clearResourceMarkers(drawResourceMarkerType);
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
					    //alert();
					   /*
					   selectedResourceObj = markerArray[i];
					   map.panTo(markerArray[i].lonlat);
					   markerArray[i].flash();
					   window.setTimeout("clearResourceFlash()",4000);*/
					}
				}
			}	
		}
	}
}

//清除闪动效果
function clearResourceFlash(){
	if(selectedResourceObj){
		//selectedResourceObj.clearFlash();
	}	
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

function showOrCloseResourceWin(isShow){
	if(isShow){
	    $('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:false});
    }else{
    	 $('#resourceFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
    }	
}

function initResourceSelect(){
	$.ajax({ 
		url: basePath+"jtztjc.gis?method=getAllTypeList",
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
		jQuery("#jczy_id").empty();
		if(zySelectList.length>0){
			for(var i=0;i<zySelectList.length;i++){
				var value=zySelectList[i]["xxlx"];
				var text=zySelectList[i]["sjnr"];
				$("#jczy_id").append("<option value='"+value+"'>"+text+"</option>");
			}				
		}	
	}
}

function spatialBufferSearch(searchType){
	showSpatialSearchResult(searchType);
	//显示周边资源窗体
	var isChecked="";
	if(searchType=="jczy"){
		isChecked=$('#zbgjWinCbk').attr("checked");
	}else{
		isChecked=$('#jlgjWinCbk').attr("checked");
	}
	if(isChecked!=undefined){
		showOrCloseResourceWin(true);
	}else{
		showOrCloseResourceWin(false);
	}
}

function showSpatialSearchResult(resourceType){
	var resultHead="";
	drawResourceMarkerType=resourceType;//设置当前绘制的Marker类型
	//清空下方DateGrid
	$('#zy').datagrid('loadData',{total:0,rows:[]});
	if(resourceType=="jczy"){
		//获得基础资源当前选择类别
		resultHead=$("#jczy_id").find("option:selected").text();
		zbzylx=$("#jczy_id").find("option:selected").val();
		//根据设备类型填写参数，最多为三列		
		var filedpara = get_zbzylx(zbzylx);
		//根据设备类型填写参数，最多为三列
		var param = getQueryParam();		
	    fillResourceList(resultHead,param);
	}else if(resourceType=="jlzy"){
		var selectedJLZY=[];
		//获得警力资源当前选择类别
		$('input[name="zbjlzy_cbk"]:checked').each(function(){
			resultHead=$(this).attr("text");
			selectedJLZY.push($(this).val());	
		 });
		zbzylx=selectedJLZY.join();
		var param = getQueryParam();
		fillPoliceforceList(param);
	}
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


function drawBufferSearch(drawBufferType,bufferInputId,spatialSearch){		
	var result = validationNumber(bufferInputId);
	if (!result) {
		return;
	}
	var bufferDis = "";
	if(drawBufferType=="Point"){
		map.activateTool('POINT', function(e, lonlats) {	
			removeDrawOverlay();
			bufferDis = $("#"+bufferInputId).val();
			var lonLatObj=getAssignPrecisionLonLat(lonlats,12,11);
			//var lableTextVal=lonLatObj.lon+","+lonLatObj.lat;		
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
			//alert("point: "+wktString);
			//执行空间查询的函数			
			eval(spatialSearch+'("'+spatialSearchType+'")');
		});
	}
	if(drawBufferType=="Polyline"){
		map.activateTool('POLYLINE', function(e, lonlats) {
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
			eval(spatialSearch+'("'+spatialSearchType+'")');
		});	
	}
}

function createBufferOverlay(wktString,meter){
	var degree=DMap.Util.meterToDegree(meter);
	var baseGeometry=new DMap.Geometry(wktString);
    var bufferWKT=baseGeometry.buffer(degree).toString();
    drawBufferWkt=bufferWKT;
    //alert("bufferWKT "+bufferWKT);
	var bufferOverlay=DMap.Overlay.createByWKT(bufferWKT,{color:"yellow"});
	return bufferOverlay;
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