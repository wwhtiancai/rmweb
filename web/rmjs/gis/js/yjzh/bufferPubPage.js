var resourceType="";//查询资源类型
var drawGeoWkt="";//绘制缓冲区wkt
var drawBufferWkt="";//缓冲区边界wkt

function addMarkerToMap(mc,jd,wd){
	//首先定位当前传入的点位信息
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
 * 绘制前端的Buffer到地图上
 * @param geoWkt 绘制缓冲所需的点或者线wkt
 * @param distanc 缓冲距离
 */
function drawFontEndBufferToMap(geoWkt,distance){
	var degree=DMap.Util.meterToDegree(distance);
	var baseGeometry=new DMap.Geometry(geoWkt);
    var bufferWKT=baseGeometry.buffer(degree).toString();
    drawBufferWkt=bufferWKT;//存放到全局变量之中获得空间查询结果
	var bufferOverlay=DMap.Overlay.createByWKT(bufferWKT,{color:"yellow"});
	map.addOverlay(bufferOverlay);
    
}

function getBufferQueryParams(){
	var bufferQueryParams={};
	//绘制的point或者 线polyline
	bufferQueryParams.sjlx=resourceType;//从全局变量之中获得当前需要查询资源类型
	bufferQueryParams.geo=drawGeoWkt;//空间查询绘制的点、线WKT
	bufferQueryParams.wkt=drawBufferWkt;//绘制后的缓冲区的WKT;	
	return bufferQueryParams;
}

	/**
	 * 后端生成缓冲区 执行空间查询
	 * @param geoWkt 绘制缓冲所需的点或者线wkt
	 * @param distanc 缓冲距离
	 * @param ajaxMethodUrl 不同类型资源请求方法不同
	 * @param returnResult 是否需要返回结果
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
					alert("未返回数据!");
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
				var maker = drawClickGeo(geo, sjlx, id);//引用了geoctr.js
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
				var maker = drawGeo(geo, sjlx, id);//引用了geoctr.js
			}
		}
	}
	
	function drawClickGeo(geo,xxlx,id){ //绘制地图marker
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
	     if(geo.lastIndexOf("POINT")>=0){ //点
		     marker.lonlat=marker.getLonlat();
	     }else{ //线
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
		 marker.setCommonEvent(); //激活marker的点击事件
		 DMap.$(marker).bind("click",function(e){
			 window.returnValue=this.id;
			 //alert(this.id);
			 window.close();
	     });
		 map.addOverlay(marker);		 
		 return marker;
	}
