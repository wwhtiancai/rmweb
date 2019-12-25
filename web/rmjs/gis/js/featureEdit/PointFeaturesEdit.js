var xzqhPolygonWkt=null;//行政区划边界
var roadPolyLineWkt=null;//道路线
var preMaker=null;//前一次编辑的Marker
var hasDisrict=false;//是否有行政区划信息
var closeEditMapWin=false;//确认是否需要关闭窗体
var currentCtrlURL=basePath+"gisfeatures.gis?method=";//用于记录当前页面需要调用的ctrlURL
var lableMarkerURL=modelPath+"images/business/common/labelMarker.png";
var queryxzqh=null;
/**
 * 判断当前是否可以打开地图窗口的函数
 * 1、传入的参数是否正确
 * 2、是否可以连接PGIS栅格地图服务
 * 3、要求做行政区划时候需要判断是否可以连接PGIS数据访问更新服务
 * 4、判断道路代码是否为高速、国道、省道以确定是否已经建立对应关系
 * @param devEditParams 采集设备参数
 * @param GIS_XZQH_UNLOCK 行政区划是否解锁
 * @param GIS_DLDM_UNLOCK 道路代码是否解锁
 */
function judgeEnableOpenMapWindow(devEditParams,GIS_XZQH_UNLOCK,GIS_DLDM_UNLOCK){
	//【1】判断传入的参数是否正确
	var isCloseMapWin=judgeEditParams(devEditParams);
	if(isCloseMapWin){
		return;
		//window.close();
	}	
	//【2】是否可以连接PGIS栅格地图服务
	judgeReachablePGISMapServer("PGIS_S_TileMap");
	//【3】要求做行政区划时候需要判断是否可以连接PGIS数据访问更新服务

	if(GIS_XZQH_UNLOCK=="1"){
		//judgeReachablePGISMapServer("PGIS_S_Map");
	}
	//4、判断道路类型 如果道路类型为高速、国道、省道 需要判断是否有相关的对应关系
	var roadCodeStart=devEditParams.dldm.substr(0,1);
	//此时标识为高速、国道或者省道 
	if(roadCodeStart=="0"||roadCodeStart=="1"||roadCodeStart=="2"){
		//此时需要判断道路是否已经建立对应的关系
		if(GIS_DLDM_UNLOCK=="1"){
			//调用函数判断当前道路是否已经于空间地图数据建立对应关系
			var isMatch=judgeRoadMatchedPGISVector(devEditParams.dldm);
			if(!isMatch){
				var tipInfo="请在道路代码与空间关联功能中,先维护高速、国道、省道的道路矢量对应关系！";
				alert(tipInfo);
				window.close();//关闭地图采集窗体
			}
		}
	}
}

/**
 * 根据传入的参数需要判断当前是否可以连接PGIS地图服务与数据访问 更新服务
 */
function judgeReachablePGISMapServer(mapServerType){	
	$.ajax({ 
		url: currentCtrlURL+"reachablePGISMapServer",
		type:"post",
		cache:false,
		async:false,
		data:{"map_server_type":mapServerType},
		success: function(response){
			//判断是否可以访问PGIS栅格地图服务
			if(mapServerType=="PGIS_S_TileMap"){
				if(response!="正确连接PGIS栅格地图服务"){
					alert(response);				
					window.close();
				}
			//判断是否可以连接数据访问更新服务	
			}else if(mapServerType=="PGIS_S_Map"){
				if(response!="正确连接PGIS数据访问更新服务"){
					alert(response);
					window.close();
				}
			}
		}});		
}

/**
 * 判断当前传入的道路代码是否已经与空间信息建立对应关系
 */
function judgeRoadMatchedPGISVector(dldm){
	var isMatch=false;
	//请求后端方法获得当前道路是否已经进行过匹配
	$.ajax({ 
		url: currentCtrlURL+"judgeRoadMatchedPGISVector",
		type:"post",
		cache:false,
		async:false,
		data:{"dldm":dldm},
		dataType:"json",
		success: function(data){
			var code=data["code"];
			if(parseInt(code)>0){
				isMatch=true;
			}else{
				isMatch=false;
			}
			return isMatch;
		}
	});
	return isMatch;
}

/**
 * 判断传入的参数是否正确不正确给予提示
 * 其中传入的经纬度可以为空的
 * 
 */
function judgeEditParams(devEditParams){
	var editParams=false;	
	var alertMsg="";
	switch(true){		
		case devEditParams.sbmc==""||devEditParams.sbmc==undefined:
			editParams=true;
			alertMsg="参数不正确：未传入设备名称!";
			break;
		case devEditParams.xzqh==""||devEditParams.xzqh==undefined:
			editParams=true;
			alertMsg="参数不正确：未传入行政区划!";
			break;		
		case devEditParams.dldm==""||devEditParams.dldm==undefined:
			editParams=true;
			alertMsg="参数不正确：未传入道路代码!";
			break;
		case devEditParams.lddm==""||devEditParams.lddm==undefined:
			editParams=true;
			alertMsg="参数不正确：未传入路段代码!";
			break;
	}
	if(alertMsg!=""){
		alert(alertMsg);
	}
	return editParams;
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
 * 从地图上拾取点的坐标
 */
function pickUpCoordinatesFromMap(){
	 //判断当前地图等级是否满足设置最小打点等级要求
	var curMapLevel=map.getZoom();
	var zoomOffset=DCMapServer.GlobeParams.ZoomOffset;
	//alert(zoomOffset+"----"+curMapLevel+""+"-------->"+GIS_MIN_LAYER);
	if((curMapLevel-zoomOffset)<(GIS_MIN_LAYER-zoomOffset)){
		alert("当前地图等级小于设定标点等级："+(GIS_MIN_LAYER-zoomOffset)+" \n请放大至此等级或以上标点!");
		return;
	}	
	map.activateTool('POINT', function(e, lonlats) {		
		if(preMaker!=null){
			map.removeOverlay(preMaker);//绘制之前移除上一次绘制Marker	
		}		
		var labelTextName=devEditParamters.sbmc;
		//alert(labelTextName);
		var lonLatObj=getAssignPrecisionLonLat(lonlats,6,6);
		var lableTextVal=labelTextName+":"+lonLatObj.lon+","+lonLatObj.lat+"";	
		var markerSymbol={type:0,labelColor:'#2f42b0',size: new DMap.Size(16,16),url:lableMarkerURL,labelBackgroundColor:"#fafbd1",labelText:lableTextVal};
		var marker = new DMap.Marker(lonlats,markerSymbol);
		//更新上一次的Marker用于移除
		preMaker=marker;
		marker.setCommonEvent();			
		marker.setDragEvent();		
		DMap.$(marker).bind("click", function(e, p) {				
			//【1】首先在点击点对象时候进入的编辑状态		
			marker.enableEditing();	
		});	
		DMap.$(marker).bind("drag", function(e, p) {				
			var curLonLat=this.getLonlat();					
			var lonLatObj=getAssignPrecisionLonLat(curLonLat,6,6);
			var lonLatText=lonLatObj.lon+","+lonLatObj.lat;	
			var newLableTextVal=labelTextName+":"+lonLatText+"";		
			//this._labelDom.innerHTML=newLableTextVal;
			this.setSymbol({type:0,labelColor:'#2f42b0',size: new DMap.Size(16,16),labelBackgroundColor:"#fafbd1",labelText:newLableTextVal});
			marker.redraw(true);
			//拖动时候更新 坐标内容text
			$("#coordinateX").val(lonLatObj.lon);
			$("#coordinateY").val(lonLatObj.lat);	
		});	
		//【***】此处操作标识直接在页面点击时候判断是否可以在地图上采集点
		$("#coordinateX").val(lonLatObj.lon);
		$("#coordinateY").val(lonLatObj.lat);
		map.addOverlay(marker);
	});
}

var positionLayers = RMICGISConfig.PositionLayers;
function graduallyZoomInPosition() {
	map.clearOverlays();
	queryxzqh =devEditParamters.xzqh;	
	// 【1】 如果有行政区划进行定位 获得当前行政区划代码边界区域使用此区域进行定位
	var districtMapData = new DMap.MapData({
		serviceMethod : "search",
		returnType : 1,
		sqlWhere : "XZQDM='"+queryxzqh+"'",
		layerName : positionLayers.XZQH,
		colList : "all",
		//sqlsort : " order by XZQDM ",
		beginRecord : 0,
		featureLimit : 10,
		returnProjectionId : 0
	});
	
	map.wait();
	districtMapData.sendRequest(function(data, config) {		
		map.resume();
		if (data.Result == 'Error') {// 出错了
			alert(data.Msg);// 数据访问更新服务返回的错误信息
			return;
		} else {
			// 获得返回的行政区划多边形进行定位
			var returnResult = data[0].rowList;
			if(returnResult.length==0){
				if(GIS_XZQH_UNLOCK=="1"){
					alert("无法找到该行政区划："+devEditParamters.xzqh+"的空间信息!\n如该行政区划为虚拟行政区划，请通过【行政区划管理】功能配置实际行政区划!");					
					return;
				}
			}else{
				hasDisrict=true;
			}
			if (returnResult.length > 0) {
				for (var i = 0; i < returnResult.length; i++) {
					var district = returnResult[i];
					var districtName = district.values.MC;
					var districtBounds = district.values.SHAPE;
					// 把当前获得行政区划放置全局变量之中
					xzqhPolygonWkt = districtBounds;
					// 使用边界区域进行定位
					var symbol={
							color : 'blue',
							opacity : 0.8,
							fillColor : '#00c5ff',
							fillOpacity : 0,
							dashstyle : 'dash',
							weight : 3
						};
					var polygon = DMap.Overlay.createByWKT(xzqhPolygonWkt, symbol);					
					map.addOverlay(polygon);
					if(devEditParamters.featX!=undefined && devEditParamters.featX!=null && devEditParamters.featX!=""){
						//map.setCenter(new DMap.LonLat(devEditParamters.featX,devEditParamters.featY));
					}else{
						var currentBounds = DMap.Util.lonlatArray2bounds(polygon.getLonLats());
						map.centerAtMBR(currentBounds.left,currentBounds.top,currentBounds.right,currentBounds.bottom);
					}

					//map.setZoom(GIS_MIN_LAYER);
				}
				//alert();
				// 【2】道路代码层级进行定位 通过前面行政区划代码获得区域边界进行定位(目前无道路代码)
				setTimeout(function(){
					drawXZQHRoadsToMap();
				}, 500);			
			}
		}
	});
}

function drawXZQHRoadsToMap() {
	// 对已经获得Polygon WKT去掉不必要的空格
	//alert(positionLayers.GSGS_DL+"---->"+devEditParamters.dldm);
	var filterWkt = xzqhPolygonWkt.replaceAll(",\\s+", ",", "g");
	//alert(devEditParamters.dldm);
	var roadMapData = new DMap.MapData({
		serviceMethod : "spatialSearch",
		returnType : 1,
		sqlWhere : "dldm=" + devEditParamters.dldm,
		layerName : positionLayers.GSGS_DL,// 高速公路
		wktStr : filterWkt,
		colList : "all",
		beginRecord : 0,
		filterMethod:"CROSSES",
		featureLimit : 500,
		returnProjectionId : 0
	});
	roadMapData.sendRequest(function(data, config) {
		if (data.Result == 'Error') {// 出错了
			alert(data.Msg);// 数据访问更新服务返回的错误信息
			return;
		} else {
			// 获得返回的行政区划多边形进行定位
			var returnResult = data[0].rowList;
			for (var i = 0; i < returnResult.length; i++) {
				var road = returnResult[i];
				var roadName = road.values.MC;
				var roadBounds = road.values.SHAPE;
				roadPolyLineWkt = roadBounds;// 放置道路线存放起来
				// 使用边界区域进行定位
				var roadlonlatArray = DMap.Util.wkt2lonlatArray(roadBounds);
				var currentBounds = DMap.Util
						.lonlatArray2bounds(roadlonlatArray);
				var roadLine = new DMap.Polyline(roadlonlatArray, {
					color : 'blue',
					opacity : 0.6,
					fillColor : 'yellow',
					weight : 4
				});
				map.addOverlay(roadLine);
				
				if(devEditParamters.featX!=undefined && devEditParamters.featX!=null && devEditParamters.featX!=""){
					//map.setCenter(new DMap.LonLat(devEditParamters.featX,devEditParamters.featY));
				}else{
					map.centerAtMBR(currentBounds.left,currentBounds.top,currentBounds.right,currentBounds.bottom);
				}
				
				//map.zoomToLonlatBounds(currentBounds, true, GIS_MIN_LAYER - 1);
			}
		}
	});
}

function addFeaturePointToMap(featX,featY){
	var lonLat=new DMap.LonLat(featX,featY);
	var labelTextName=devEditParamters.sbmc;//获得设备名称
	var lonLatObj=getAssignPrecisionLonLat(lonLat,6,6);
	var lableTextVal=labelTextName+":"+lonLatObj.lon+","+lonLatObj.lat+"";//":<font style='color:#2f42b0;font-weight:bolder;'>"+lonLatObj.lon+","+lonLatObj.lat+"</font>";	
	var markerSymbol={type:0,labelColor:'#2f42b0',size: new DMap.Size(16,16),url:lableMarkerURL,labelBackgroundColor:"#fafbd1",labelText:lableTextVal};
	var marker = new DMap.Marker(lonLat,markerSymbol);
	//更新上一次的Marker用于移除
	preMaker=marker;
	marker.setCommonEvent();			
	marker.setDragEvent();		
	DMap.$(marker).bind("click", function(e, p) {				
		//【1】首先在点击点对象时候进入的编辑状态					
		marker.enableEditing();	
	});	
	DMap.$(marker).bind("drag", function(e, p) {				
		var curLonLat=this.getLonlat();					
		var lonLatObj=getAssignPrecisionLonLat(curLonLat,6,6);
		var lonLatText=lonLatObj.lon+","+lonLatObj.lat;			
		var newLableTextVal=labelTextName+":"+lonLatText+"";		
		this._labelDom.innerHTML=newLableTextVal;
		this.setSymbol({type:0,labelColor:'#2f42b0',size: new DMap.Size(16,16),labelBackgroundColor:"#fafbd1",labelText:newLableTextVal});
		marker.redraw(true);
		//拖动时候更新 坐标内容text
		$("#coordinateX").val(lonLatObj.lon);
		$("#coordinateY").val(lonLatObj.lat);	
	});			
	//填充坐标到页面之中
	$("#coordinateX").val(lonLatObj.lon);
	$("#coordinateY").val(lonLatObj.lat);
	map.addOverlay(marker);	
	var currentMapLevel=map.getZoom();
	var zoomOffset=DCMapServer.GlobeParams.ZoomOffset;
	if((currentMapLevel-zoomOffset)<=(GIS_MIN_LAYER-zoomOffset)){
		map.setCenter(lonLat,GIS_MIN_LAYER-zoomOffset);
	}else{
		map.setCenter(lonLat,currentMapLevel);
	}
}

/**
 * 利用此函数统一判断是否满足编辑【添加、编辑】要素条件
 * 1、当前地图等级是否满足设定的地图层级(灵活的) 如13级别
 * 2、如果设定了行政区划，判断采集点是否在行政区划
 * 3、如果设定要求到道路上，需要判断采集点是否在指定的道路上(传入的道路代码)
 */
function judgeEnableEditFeatureCondition(editFeatLonLat){
	var editTipInfo="";	
	var currentMapLevel=map.getZoom();
	var pointLon=editFeatLonLat.lon;
	var pointLat=editFeatLonLat.lat;	
	var pointWkt="POINT("+pointLon+" "+pointLat+")";

	//【1】判断标点是否地图等级是否满足于要求
	var zoomOffset=DCMapServer.GlobeParams.ZoomOffset;
	if((currentMapLevel-zoomOffset)<(GIS_MIN_LAYER-zoomOffset)){
		editTipInfo="当前地图等级小于设定标点等级："+(GIS_MIN_LAYER-zoomOffset)+" \n请放大至大于等于此等级才可保存 ";
		coordMZEditCondition=false;
		alert(editTipInfo);
		return;
	}
	if(GIS_XZQH_UNLOCK=="1"){
		//【2】如果设置要求判断行政区划 判断采集点是否在指定行政区划 1：表示必须判断 0：表示不判断
		var reqParams={
			"judgeType":"district",
			"pointWkt":pointWkt,
			"xzqh":queryxzqh
		};
		if(hasDisrict==false){
			alert("无法找到该行政区划："+devEditParamters.xzqh+"的空间信息!\n如该行政区划为虚拟行政区划，请通过【行政区划管理】功能配置实际行政区划!");					
			return;
		}
		//请求后端方法获得当前道路是否已经进行过匹配
	    //alert(currentCtrlURL+"judgePointInAreaAndRoad");
		$.ajax({ 
			url: currentCtrlURL+"judgePointInAreaAndRoad",
			type:"post",
			data:reqParams,
			async:false,
			success: function(data){					    
				if(data=="inDistrict"){
					editTipInfo="";
					coordMZEditCondition=true;
					//alert("-----0------>");
				}else{
					editTipInfo="采集点必须在行政区划范围内;请拖拽点到行政区划范围内!";
					coordMZEditCondition=false;
					alert(editTipInfo);
					return;
				}
		}});
	}else{
		coordMZEditCondition=true;
	}
	if(coordMZEditCondition==false){
		return;
	}
	//此时标识为高速、国道或者省道 
	var roadCodeStart=devEditParamters.dldm.substr(0,1);
	if(roadCodeStart=="0"||roadCodeStart=="1"||roadCodeStart=="2"){	
		if(GIS_DLDM_UNLOCK=="1"){
			//【3】如果设定了道路为参照，需要判断点是否在指定的道路上
			var resolutions = map.resolutions;
			var curResolutions = resolutions[currentMapLevel];
			var toleranceDegree = 10 * curResolutions;
			var meter = DMap.Util.degreeToMeter(toleranceDegree);
			var reqParams = {
					"judgeType":"road",
					"pointWkt" : pointWkt,
					"toleranceDis" : Math.ceil(meter),
					"xzqh" : devEditParamters.xzqh,
					"dldm" : devEditParamters.dldm
			};
			// 请求后端方法获得当前道路是否已经进行过匹配
			$.ajax({
				url : currentCtrlURL + "judgePointInAreaAndRoad",
				type : "post",
				data : reqParams,
				async:false,
				success : function(data) {
				if (data == "onRoad") {
					editTipInfo = "";
					coordMZEditCondition=true;
					//alert("-----1------>");
				} else {
					editTipInfo = "采集点必须落在道路;请拖拽点到道路路段上!";
					coordMZEditCondition=false;
					alert(editTipInfo);
					return;
				}
			}
			});
		}else{
			//【4】如果设定了道路为仅仅提示信息	
			if(window.confirm("采集点必须落在道路;请拖拽点到道路路段上!")){
				if(((GIS_MIN_LAYER-zoomOffset)<=(currentMapLevel-zoomOffset))&&coordMZEditCondition==true){
				}else{
					coordMZEditCondition=false;
				}				
			}else{
				coordMZEditCondition=false;
				return;
			}						
		}
	}else {
		editTipInfo = "请确认采集点落在指定道路上！";
		coordMZEditCondition=true;
		alert(editTipInfo);		
	}
}

/**
 * 判断采集点是否在区域之中
 * @param editFeatLonLat
 * @param districtPolygonWkt
 */
function judgeEditPiontInDistrict(editFeatLonLat,districtPolygonWkt){
	var isContian=DMap.Util.isContains(districtPolygonWkt,editFeatLonLat);
	return isContian;		
}

