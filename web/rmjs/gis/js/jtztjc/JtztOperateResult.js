var roadType="1";//��·���ͣ�highWay 1����· cityRoad�����е�· 2
var spatialSearchType="jczy";//Ĭ�����ܱ���Դ
var drawPointMarker=null;
var drawPointOrPolylineWkt="";//���ƻ�������wkt
var drawBufferWkt="";//���ƵĻ����Wkt
var zbzylx="";//��ǰѡ����ܱ���Դ���
var drawPolyline=null;
var bufferOverlay=null;
var zySelectList=null;
var drawResourceMarkerType="";//��ǰ��Ҫ���Ƶ���Դmarker����
var resourceMarkerJsonArray=[];//�����Դ��Markerjson����

//===================������Դ��������===============================	

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
	//�������һ�λ��Ƶ���ԴMarkers
	clearResourceMarkers(drawResourceMarkerType);
}

/**
 * �����Դ��Maker��JSON����֮���������ʹ��
 * @param resourceType ��Դ����
 * @param easyuiMarkerData 
 */
function addResourceMarkers(resourceType,easyuiMarkerData){
	var drawMakerArray = [];
	//����easyUi����
	for(var i=0; i<easyuiMarkerData.rows.length; i++){
		var sjlx = easyuiMarkerData.rows[i].sjlx;
		var kklx = easyuiMarkerData.rows[i].tz; //���������ж�
		var idlx=easyuiMarkerData.rows[i].idlx;//��Ҫ���ھ���(������Ħ��)
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
	//������װ�ز�ѯ���
	var markerJson = {
		lx : '',
		val : []
	}
	markerJson.lx=resourceType+"";
	markerJson.val = drawMakerArray;
	resourceMarkerJsonArray.push(markerJson);
}

//��λ��ָ��ͼ��
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

//�������Ч��
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
 * ��֤��������������Ƿ�Ϊ������ 
 * @param inputId
 * @returns {Boolean}
 */
function  validationNumber(inputId){
	var result=false;
	var bufferDis=$("#"+inputId).val();
	//�жϻ�������Ƿ�Ϊ����
	var reg=/^[0-9]*[1-9][0-9]*$/;//������  
	if(reg.test(bufferDis)){
		if(bufferDis>500000){
			alert("������벻Ҫ����500km!");
			result=false;
		}else{
			result=true;
		}
	}else{
		alert("Ҫ�������Ϊ��������!");
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
	//��ʾ�ܱ���Դ����
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
	drawResourceMarkerType=resourceType;//���õ�ǰ���Ƶ�Marker����
	//����·�DateGrid
	$('#zy').datagrid('loadData',{total:0,rows:[]});
	if(resourceType=="jczy"){
		//��û�����Դ��ǰѡ�����
		resultHead=$("#jczy_id").find("option:selected").text();
		zbzylx=$("#jczy_id").find("option:selected").val();
		//�����豸������д���������Ϊ����		
		var filedpara = get_zbzylx(zbzylx);
		//�����豸������д���������Ϊ����
		var param = getQueryParam();		
	    fillResourceList(resultHead,param);
	}else if(resourceType=="jlzy"){
		var selectedJLZY=[];
		//��þ�����Դ��ǰѡ�����
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
	if(xxlx=="01"){//����
		if(kklx=="���翨��"){
		var imgurl = getMakerImg[xxlx]['01']['01'];			
		}else if (kklx=="ʡ�ʿ���"){
		var imgurl = getMakerImg[xxlx]['02']['01'];			
		}else if (kklx=="�мʿ���"){
		var imgurl = getMakerImg[xxlx]['03']['01'];				
		}else if (kklx=="�ؼʿ���"){
		var imgurl = getMakerImg[xxlx]['04']['01'];				
		}else if (kklx=="��·���߿���"){
		var imgurl = getMakerImg[xxlx]['05']['01'];			
		}else if (kklx=="��·�շ�վ����"){
		var imgurl = getMakerImg[xxlx]['06']['01'];			
		}else if (kklx=="������·����"){
		var imgurl = getMakerImg[xxlx]['07']['01'];			
		}else if (kklx=="����·�ڿ���"){
		var imgurl = getMakerImg[xxlx]['08']['01'];				
		}		
	}else if(xxlx=="16"){
		if(kklx=="����"){
			var imgurl = getMakerImg[xxlx]['1'];
		}else if(kklx=="�ܶ�"){
			var imgurl = getMakerImg[xxlx]['2'];
		}else if(kklx=="֧��"){
			var imgurl = getMakerImg[xxlx]['3'];
		}else if(kklx=="���"){
			var imgurl = getMakerImg[xxlx]['4'];
		}else if(kllx=="�ж�"){
			var imgurl = getMakerImg[xxlx]['5'];
		}else{
			var imgurl = getMakerImg[xxlx]['onlyone'];
		}
	}else { //������Դ
		var imgurl = getMakerImg[xxlx]['onlyone'];
	}
	//var imgurl = getMakerImg[xxlx]['onlyone'];
	if(xxlx=="18"){//����
		xxlx = const_gis_marker_car_id;
		if(kklx=="��������"){
			imgurl=getMakerImg[xxlx]['onlyone'];
		}else if(kklx=="����Ħ��"){
			imgurl=getMakerImg[xxlx]['motorcycle'];
		}
	}else if(xxlx=="19"){ //��Ա
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
	 marker.setCommonEvent(); //����marker�ĵ���¼�
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
			//����д��������Ϊ��ǩ ���Ƶ���ͼ��
			var markerSymbol={type:0,labelColor:'blue',labelBackgroundColor:"#4cb748",url:bufferMarkerUrl};
			var marker = new DMap.Marker(lonlats,markerSymbol);
			drawPointMarker=marker;	
			var wktString = "POINT("+lonLatObj.lon+" "+lonLatObj.lat+")";
			drawPointOrPolylineWkt=wktString;//����ȫ�ֱ���֮��
			var pointBufferOverlay=createBufferOverlay(wktString,bufferDis);
			bufferOverlay=pointBufferOverlay;
			map.addOverlay(pointBufferOverlay);
			map.addOverlay(marker);
			map.deactivate();
			//alert("point: "+wktString);
			//ִ�пռ��ѯ�ĺ���			
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
			drawPointOrPolylineWkt=wktString;//����ȫ�ֱ���֮��
			var lineBufferOverlay = createBufferOverlay(wktString, bufferDis);
			bufferOverlay = lineBufferOverlay;
			map.addOverlay(lineBufferOverlay);
			drawPolyline = polyline;
			map.addOverlay(polyline);
			map.deactivate();
			//ִ�пռ��ѯ�ĺ���
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