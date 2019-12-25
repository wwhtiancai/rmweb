var resourceType="";//��ѯ��Դ����
var drawGeoWkt="";//���ƻ�����wkt
var drawBufferWkt="";//�������߽�wkt

function addMarkerToMap(mc,jd,wd){
	//���ȶ�λ��ǰ����ĵ�λ��Ϣ
	var labelTextName=mc;
	var lonLatObj=new DMap.LonLat(jd,wd);
	drawGeoWkt="POINT("+jd+" "+ wd+")";//<font style='color:#2f42b0;font-weight:bolder;'> </font>
	var lableTextVal=labelTextName+":"+lonLatObj.lon+", "+lonLatObj.lat;	
	var symbol = {
			 type:3,
  	         url : bufferMarkerUrl,
			 size : new DMap.Size(36,36),
			 offsetType : "mm", 
			 borderWidth : 4,
			 borderColor : "red",
			 color :"white",
			 labelColor:'#2f42b0',
			 labelBackgroundColor:"#fafbd1",
			 labelText:lableTextVal,
			 opacity : 1  			
   	}
	var marker = new DMap.Marker(lonLatObj,symbol);
	map.addOverlay(marker);
	map.setZoom(14);
	map.setCenter(lonLatObj);
}

/**
 * ����ǰ�˵�Buffer����ͼ��
 * @param geoWkt ���ƻ�������ĵ������wkt
 * @param distanc �������
 */
function drawFontEndBufferToMap(geoWkt,distance){
	var degree=DMap.Util.meterToDegree(distance);
	var baseGeometry=new DMap.Geometry(geoWkt);
    var bufferWKT=baseGeometry.buffer(degree).toString();
    drawBufferWkt=bufferWKT;//��ŵ�ȫ�ֱ���֮�л�ÿռ��ѯ���
	var bufferOverlay=DMap.Overlay.createByWKT(bufferWKT,{color:"yellow"});
	map.addOverlay(bufferOverlay);
    
}

function getBufferQueryParams(){
	var bufferQueryParams={};
	//���Ƶ�point���� ��polyline
	bufferQueryParams.sjlx=resourceType;//��ȫ�ֱ���֮�л�õ�ǰ��Ҫ��ѯ��Դ����
	bufferQueryParams.geo=drawGeoWkt;//�ռ��ѯ���Ƶĵ㡢��WKT
	bufferQueryParams.wkt=drawBufferWkt;//���ƺ�Ļ�������WKT;	
	return bufferQueryParams;
}

	/**
	 * ������ɻ����� ִ�пռ��ѯ
	 * @param geoWkt ���ƻ�������ĵ������wkt
	 * @param distanc �������
	 * @param ajaxMethodUrl ��ͬ������Դ���󷽷���ͬ
	 * @param returnResult �Ƿ���Ҫ���ؽ��
	 */
	function buffeBackEndAjaxRequest(geoWkt, distance,ajaxMethodUrl,returnResult) {
		var bufferReqParams=getBufferQueryParams();		
		$.ajax({
			url : basePath + ajaxMethodUrl,
			type : "post",
			cache : false,
			async : false,
			data : bufferReqParams,
			dataType : "json",
			success : function(data) {
				if(data.total==0){
					alert("δ��������!");
				}else{
					if(returnResult){
						returnBufferFeatureToWindow(data.rows);
					}else{
						drawBufferQueryMarkersToMap(data.rows);
					}
				}				
			}
		});
	}

	function returnBufferFeatureToWindow(bufferResult) {
		var resourceIds=[];
		if (bufferResult.length > 0) {
			for (var i = 0; i < bufferResult.length; i++) {
				var sjlx = bufferResult[i].sjlx;
				var id = bufferResult[i].id;
				var geo = bufferResult[i].geo;
				var maker = drawClickGeo(geo, sjlx, id);//������geoctr.js
				resourceIds.psuh(id);
			}
		}
		return resourceIds;
	}

	function drawBufferQueryMarkersToMap(bufferResult) {
		if (bufferResult.length > 0) {
			for (var i = 0; i < bufferResult.length; i++) {
				var sjlx = bufferResult[i].sjlx;
				var id = bufferResult[i].id;
				var geo = bufferResult[i].geo;
				var maker = drawGeo(geo, sjlx, id);//������geoctr.js
			}
		}
	}
	
	function drawClickGeo(geo,xxlx,id){ //���Ƶ�ͼmarker
		 var symbol;
	     if(geo.lastIndexOf("POINT")>=0){
	    	 var imgurl;
	    	 imgurl = getMakerImg[xxlx]['onlyone'];
    		 symbol = {
	   	         url : imgurl,
				 size : new DMap.Size(36,36),
				 offsetType : "mm", 
				 borderWidth : 4,
				 borderColor : "red",
				 color :"white",
				 opacity : 1  			
	    	 }
	     }else{
	    	 symbol = {
	    		animateColor:"blue",
	    		color:"red"	
	    	 }
	     }
		 var marker = DMap.Overlay.createByWKT(geo,symbol);
		 marker.xxlx=xxlx;
	     marker.id=id;
	     if(geo.lastIndexOf("POINT")>=0){ //��
		     marker.lonlat=marker.getLonlat();
	     }else{ //��
	    	 var lonlatArray = marker.getLonLats();
	    	 if(lonlatArray.length%2==1){
	    		 marker.lonlat=lonlatArray[lonlatArray.length/2-0.5];
	    	 }else if(lonlatArray.length==2){
	    		 var midLon = (lonlatArray[0].lon+lonlatArray[1].lon)/2;
	    		 var midLat = (lonlatArray[0].lat+lonlatArray[1].lat)/2;
	    		 marker.lonlat=new DMap.LonLat(midLon,midLat);
	    	 }else{
	    		 var midLon = (lonlatArray[lonlatArray.length/2-1].lon+lonlatArray[lonlatArray.length/2].lon)/2;
	    		 var midLat = (lonlatArray[lonlatArray.length/2-1].lat+lonlatArray[lonlatArray.length/2].lat)/2;
	    		 marker.lonlat=new DMap.LonLat(midLon,midLat);
	    	 }
	     }
		 marker.imgUrl = imgurl;
		 marker.setCommonEvent(); //����marker�ĵ���¼�
		 DMap.$(marker).bind("click",function(e){
			 window.returnValue=this.id;
			 //alert(this.id);
			 window.close();
	     });
		 map.addOverlay(marker);		 
		 return marker;
	}
