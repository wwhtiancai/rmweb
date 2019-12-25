var xzqhPolygonWkt=null;//���������߽�
var roadPolyLineWkt=null;//��·��
var preMaker=null;//ǰһ�α༭��Marker
var hasDisrict=false;//�Ƿ�������������Ϣ
var closeEditMapWin=false;//ȷ���Ƿ���Ҫ�رմ���
var currentCtrlURL=basePath+"gisfeatures.gis?method=";//���ڼ�¼��ǰҳ����Ҫ���õ�ctrlURL
var lableMarkerURL=modelPath+"images/business/common/labelMarker.png";
var queryxzqh=null;
/**
 * �жϵ�ǰ�Ƿ���Դ򿪵�ͼ���ڵĺ���
 * 1������Ĳ����Ƿ���ȷ
 * 2���Ƿ��������PGISդ���ͼ����
 * 3��Ҫ������������ʱ����Ҫ�ж��Ƿ��������PGIS���ݷ��ʸ��·���
 * 4���жϵ�·�����Ƿ�Ϊ���١�������ʡ����ȷ���Ƿ��Ѿ�������Ӧ��ϵ
 * @param devEditParams �ɼ��豸����
 * @param GIS_XZQH_UNLOCK ���������Ƿ����
 * @param GIS_DLDM_UNLOCK ��·�����Ƿ����
 */
function judgeEnableOpenMapWindow(devEditParams,GIS_XZQH_UNLOCK,GIS_DLDM_UNLOCK){
	//��1���жϴ���Ĳ����Ƿ���ȷ
	var isCloseMapWin=judgeEditParams(devEditParams);
	if(isCloseMapWin){
		return;
		//window.close();
	}	
	//��2���Ƿ��������PGISդ���ͼ����
	judgeReachablePGISMapServer("PGIS_S_TileMap");
	//��3��Ҫ������������ʱ����Ҫ�ж��Ƿ��������PGIS���ݷ��ʸ��·���

	if(GIS_XZQH_UNLOCK=="1"){
		//judgeReachablePGISMapServer("PGIS_S_Map");
	}
	//4���жϵ�·���� �����·����Ϊ���١�������ʡ�� ��Ҫ�ж��Ƿ�����صĶ�Ӧ��ϵ
	var roadCodeStart=devEditParams.dldm.substr(0,1);
	//��ʱ��ʶΪ���١���������ʡ�� 
	if(roadCodeStart=="0"||roadCodeStart=="1"||roadCodeStart=="2"){
		//��ʱ��Ҫ�жϵ�·�Ƿ��Ѿ�������Ӧ�Ĺ�ϵ
		if(GIS_DLDM_UNLOCK=="1"){
			//���ú����жϵ�ǰ��·�Ƿ��Ѿ��ڿռ��ͼ���ݽ�����Ӧ��ϵ
			var isMatch=judgeRoadMatchedPGISVector(devEditParams.dldm);
			if(!isMatch){
				var tipInfo="���ڵ�·������ռ����������,��ά�����١�������ʡ���ĵ�·ʸ����Ӧ��ϵ��";
				alert(tipInfo);
				window.close();//�رյ�ͼ�ɼ�����
			}
		}
	}
}

/**
 * ���ݴ���Ĳ�����Ҫ�жϵ�ǰ�Ƿ��������PGIS��ͼ���������ݷ��� ���·���
 */
function judgeReachablePGISMapServer(mapServerType){	
	$.ajax({ 
		url: currentCtrlURL+"reachablePGISMapServer",
		type:"post",
		cache:false,
		async:false,
		data:{"map_server_type":mapServerType},
		success: function(response){
			//�ж��Ƿ���Է���PGISդ���ͼ����
			if(mapServerType=="PGIS_S_TileMap"){
				if(response!="��ȷ����PGISդ���ͼ����"){
					alert(response);				
					window.close();
				}
			//�ж��Ƿ�����������ݷ��ʸ��·���	
			}else if(mapServerType=="PGIS_S_Map"){
				if(response!="��ȷ����PGIS���ݷ��ʸ��·���"){
					alert(response);
					window.close();
				}
			}
		}});		
}

/**
 * �жϵ�ǰ����ĵ�·�����Ƿ��Ѿ���ռ���Ϣ������Ӧ��ϵ
 */
function judgeRoadMatchedPGISVector(dldm){
	var isMatch=false;
	//�����˷�����õ�ǰ��·�Ƿ��Ѿ����й�ƥ��
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
 * �жϴ���Ĳ����Ƿ���ȷ����ȷ������ʾ
 * ���д���ľ�γ�ȿ���Ϊ�յ�
 * 
 */
function judgeEditParams(devEditParams){
	var editParams=false;	
	var alertMsg="";
	switch(true){		
		case devEditParams.sbmc==""||devEditParams.sbmc==undefined:
			editParams=true;
			alertMsg="��������ȷ��δ�����豸����!";
			break;
		case devEditParams.xzqh==""||devEditParams.xzqh==undefined:
			editParams=true;
			alertMsg="��������ȷ��δ������������!";
			break;		
		case devEditParams.dldm==""||devEditParams.dldm==undefined:
			editParams=true;
			alertMsg="��������ȷ��δ�����·����!";
			break;
		case devEditParams.lddm==""||devEditParams.lddm==undefined:
			editParams=true;
			alertMsg="��������ȷ��δ����·�δ���!";
			break;
	}
	if(alertMsg!=""){
		alert(alertMsg);
	}
	return editParams;
}

/**
 * �Ը��������и�ʽ������
 * @param num ����ʽ��������
 * @param pos ��Ҫ������С��λ��
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
 * ���ָ�����ȡ�γ�� ��������λ��
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
 * �ӵ�ͼ��ʰȡ�������
 */
function pickUpCoordinatesFromMap(){
	 //�жϵ�ǰ��ͼ�ȼ��Ƿ�����������С���ȼ�Ҫ��
	var curMapLevel=map.getZoom();
	var zoomOffset=DCMapServer.GlobeParams.ZoomOffset;
	//alert(zoomOffset+"----"+curMapLevel+""+"-------->"+GIS_MIN_LAYER);
	if((curMapLevel-zoomOffset)<(GIS_MIN_LAYER-zoomOffset)){
		alert("��ǰ��ͼ�ȼ�С���趨���ȼ���"+(GIS_MIN_LAYER-zoomOffset)+" \n��Ŵ����˵ȼ������ϱ��!");
		return;
	}	
	map.activateTool('POINT', function(e, lonlats) {		
		if(preMaker!=null){
			map.removeOverlay(preMaker);//����֮ǰ�Ƴ���һ�λ���Marker	
		}		
		var labelTextName=devEditParamters.sbmc;
		//alert(labelTextName);
		var lonLatObj=getAssignPrecisionLonLat(lonlats,6,6);
		var lableTextVal=labelTextName+":"+lonLatObj.lon+","+lonLatObj.lat+"";	
		var markerSymbol={type:0,labelColor:'#2f42b0',size: new DMap.Size(16,16),url:lableMarkerURL,labelBackgroundColor:"#fafbd1",labelText:lableTextVal};
		var marker = new DMap.Marker(lonlats,markerSymbol);
		//������һ�ε�Marker�����Ƴ�
		preMaker=marker;
		marker.setCommonEvent();			
		marker.setDragEvent();		
		DMap.$(marker).bind("click", function(e, p) {				
			//��1�������ڵ�������ʱ�����ı༭״̬		
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
			//�϶�ʱ����� ��������text
			$("#coordinateX").val(lonLatObj.lon);
			$("#coordinateY").val(lonLatObj.lat);	
		});	
		//��***���˴�������ʶֱ����ҳ����ʱ���ж��Ƿ�����ڵ�ͼ�ϲɼ���
		$("#coordinateX").val(lonLatObj.lon);
		$("#coordinateY").val(lonLatObj.lat);
		map.addOverlay(marker);
	});
}

var positionLayers = RMICGISConfig.PositionLayers;
function graduallyZoomInPosition() {
	map.clearOverlays();
	queryxzqh =devEditParamters.xzqh;	
	// ��1�� ����������������ж�λ ��õ�ǰ������������߽�����ʹ�ô�������ж�λ
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
		if (data.Result == 'Error') {// ������
			alert(data.Msg);// ���ݷ��ʸ��·��񷵻صĴ�����Ϣ
			return;
		} else {
			// ��÷��ص�������������ν��ж�λ
			var returnResult = data[0].rowList;
			if(returnResult.length==0){
				if(GIS_XZQH_UNLOCK=="1"){
					alert("�޷��ҵ�������������"+devEditParamters.xzqh+"�Ŀռ���Ϣ!\n�����������Ϊ����������������ͨ������������������������ʵ����������!");					
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
					// �ѵ�ǰ���������������ȫ�ֱ���֮��
					xzqhPolygonWkt = districtBounds;
					// ʹ�ñ߽�������ж�λ
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
				// ��2����·����㼶���ж�λ ͨ��ǰ��������������������߽���ж�λ(Ŀǰ�޵�·����)
				setTimeout(function(){
					drawXZQHRoadsToMap();
				}, 500);			
			}
		}
	});
}

function drawXZQHRoadsToMap() {
	// ���Ѿ����Polygon WKTȥ������Ҫ�Ŀո�
	//alert(positionLayers.GSGS_DL+"---->"+devEditParamters.dldm);
	var filterWkt = xzqhPolygonWkt.replaceAll(",\\s+", ",", "g");
	//alert(devEditParamters.dldm);
	var roadMapData = new DMap.MapData({
		serviceMethod : "spatialSearch",
		returnType : 1,
		sqlWhere : "dldm=" + devEditParamters.dldm,
		layerName : positionLayers.GSGS_DL,// ���ٹ�·
		wktStr : filterWkt,
		colList : "all",
		beginRecord : 0,
		filterMethod:"CROSSES",
		featureLimit : 500,
		returnProjectionId : 0
	});
	roadMapData.sendRequest(function(data, config) {
		if (data.Result == 'Error') {// ������
			alert(data.Msg);// ���ݷ��ʸ��·��񷵻صĴ�����Ϣ
			return;
		} else {
			// ��÷��ص�������������ν��ж�λ
			var returnResult = data[0].rowList;
			for (var i = 0; i < returnResult.length; i++) {
				var road = returnResult[i];
				var roadName = road.values.MC;
				var roadBounds = road.values.SHAPE;
				roadPolyLineWkt = roadBounds;// ���õ�·�ߴ������
				// ʹ�ñ߽�������ж�λ
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
	var labelTextName=devEditParamters.sbmc;//����豸����
	var lonLatObj=getAssignPrecisionLonLat(lonLat,6,6);
	var lableTextVal=labelTextName+":"+lonLatObj.lon+","+lonLatObj.lat+"";//":<font style='color:#2f42b0;font-weight:bolder;'>"+lonLatObj.lon+","+lonLatObj.lat+"</font>";	
	var markerSymbol={type:0,labelColor:'#2f42b0',size: new DMap.Size(16,16),url:lableMarkerURL,labelBackgroundColor:"#fafbd1",labelText:lableTextVal};
	var marker = new DMap.Marker(lonLat,markerSymbol);
	//������һ�ε�Marker�����Ƴ�
	preMaker=marker;
	marker.setCommonEvent();			
	marker.setDragEvent();		
	DMap.$(marker).bind("click", function(e, p) {				
		//��1�������ڵ�������ʱ�����ı༭״̬					
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
		//�϶�ʱ����� ��������text
		$("#coordinateX").val(lonLatObj.lon);
		$("#coordinateY").val(lonLatObj.lat);	
	});			
	//������굽ҳ��֮��
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
 * ���ô˺���ͳһ�ж��Ƿ�����༭����ӡ��༭��Ҫ������
 * 1����ǰ��ͼ�ȼ��Ƿ������趨�ĵ�ͼ�㼶(����) ��13����
 * 2������趨�������������жϲɼ����Ƿ�����������
 * 3������趨Ҫ�󵽵�·�ϣ���Ҫ�жϲɼ����Ƿ���ָ���ĵ�·��(����ĵ�·����)
 */
function judgeEnableEditFeatureCondition(editFeatLonLat){
	var editTipInfo="";	
	var currentMapLevel=map.getZoom();
	var pointLon=editFeatLonLat.lon;
	var pointLat=editFeatLonLat.lat;	
	var pointWkt="POINT("+pointLon+" "+pointLat+")";

	//��1���жϱ���Ƿ��ͼ�ȼ��Ƿ�������Ҫ��
	var zoomOffset=DCMapServer.GlobeParams.ZoomOffset;
	if((currentMapLevel-zoomOffset)<(GIS_MIN_LAYER-zoomOffset)){
		editTipInfo="��ǰ��ͼ�ȼ�С���趨���ȼ���"+(GIS_MIN_LAYER-zoomOffset)+" \n��Ŵ������ڵ��ڴ˵ȼ��ſɱ��� ";
		coordMZEditCondition=false;
		alert(editTipInfo);
		return;
	}
	if(GIS_XZQH_UNLOCK=="1"){
		//��2���������Ҫ���ж��������� �жϲɼ����Ƿ���ָ���������� 1����ʾ�����ж� 0����ʾ���ж�
		var reqParams={
			"judgeType":"district",
			"pointWkt":pointWkt,
			"xzqh":queryxzqh
		};
		if(hasDisrict==false){
			alert("�޷��ҵ�������������"+devEditParamters.xzqh+"�Ŀռ���Ϣ!\n�����������Ϊ����������������ͨ������������������������ʵ����������!");					
			return;
		}
		//�����˷�����õ�ǰ��·�Ƿ��Ѿ����й�ƥ��
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
					editTipInfo="�ɼ������������������Χ��;����ק�㵽����������Χ��!";
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
	//��ʱ��ʶΪ���١���������ʡ�� 
	var roadCodeStart=devEditParamters.dldm.substr(0,1);
	if(roadCodeStart=="0"||roadCodeStart=="1"||roadCodeStart=="2"){	
		if(GIS_DLDM_UNLOCK=="1"){
			//��3������趨�˵�·Ϊ���գ���Ҫ�жϵ��Ƿ���ָ���ĵ�·��
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
			// �����˷�����õ�ǰ��·�Ƿ��Ѿ����й�ƥ��
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
					editTipInfo = "�ɼ���������ڵ�·;����ק�㵽��··����!";
					coordMZEditCondition=false;
					alert(editTipInfo);
					return;
				}
			}
			});
		}else{
			//��4������趨�˵�·Ϊ������ʾ��Ϣ	
			if(window.confirm("�ɼ���������ڵ�·;����ק�㵽��··����!")){
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
		editTipInfo = "��ȷ�ϲɼ�������ָ����·�ϣ�";
		coordMZEditCondition=true;
		alert(editTipInfo);		
	}
}

/**
 * �жϲɼ����Ƿ�������֮��
 * @param editFeatLonLat
 * @param districtPolygonWkt
 */
function judgeEditPiontInDistrict(editFeatLonLat,districtPolygonWkt){
	var isContian=DMap.Util.isContains(districtPolygonWkt,editFeatLonLat);
	return isContian;		
}

