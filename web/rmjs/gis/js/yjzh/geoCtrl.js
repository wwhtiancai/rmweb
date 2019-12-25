//绘制图标  geo是wkt格式 点：POINT(x y) 线：LINESTRING(x1 y1,x2 y2,...)
//      sjdj:事件等级 ,clzt:处理状态(yjqr,yjcz,czfk) 
function drawGeo(geo,xxlx,id,sjdj,clzt){ //绘制地图marker
	 var symbol;
     if(geo.lastIndexOf("POINT")>=0){
    	 var imgurl;
    	 if(clzt && clzt=="yjqr" || clzt=="yjcz" || clzt=="czfk"){
    	    if(sjdj){   		 
    	    	if(getMakerImg['yjzh'][xxlx]){
    	    		imgurl = getMakerImg['yjzh'][xxlx]['no_handle'][sjdj];
    	    	}else{
    	    		imgurl = getMakerImg['yjzh']['default'];
    	    	}	     
    		 }else{
    			 imgurl = getMakerImg['yjzh']['default'];
    		 }
	       	 symbol = {
       	         url : imgurl,
    			 size : new DMap.Size(48,48),
    			 offsetType : "mm", 
    			 borderWidth : 4,
    			 borderColor : "red",
    			 color :"white",
    			 opacity : 1  			
        	 };
    	 }else{
    		 imgurl = getMakerImg[xxlx]['onlyone'];
    		 symbol = {
	   	         url : imgurl,
				 size : new DMap.Size(36,36),
				 offsetType : "mm", 
				 borderWidth : 4,
				 borderColor : "red",
				 color :"white",
				 opacity : 1  			
	    	 };	
    	 }
     }else{
    	 symbol = {
    		animateColor:"blue",
    		color:"red"	
    	 };
     }
	 var marker = DMap.Overlay.createByWKT(geo,symbol);
	 if(clzt){
		 if(clzt=="yjqr"){
			 clzt=const_gis_marker_incident_alarm_id;
		 }else if(clzt=="yjcz"){
			 clzt=const_gis_marker_incident_info_id;
		 }else if(clzt=="czfk"){
			 clzt=const_gis_marker_incident_confirm_id;
		 }
		 marker.xxlx=clzt;
	 }else{
		 marker.xxlx=xxlx;
	 }     
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
		 openGisInfoWindowHtml(marker,map);
     });
	 map.addOverlay(marker);
	 
	 return marker;
}

//定位到指定图标
var checkedGeoObj;
function panToGeo(id,type){
	for(var h=0; h<markerJsonArray.length; h++){
		if(markerJsonArray[h].lx==type){
			var markerArray = markerJsonArray[h].val;
			if(markerArray.length!=0){
				for(var i=0; i<markerArray.length; i++){
					if(markerArray[i].id==id){

						var symbol=markerArray[i].getSymbol();
						var lon=markerArray[i].lonlat.lon;
						var lat=markerArray[i].lonlat.lat;
						var lonLat=new DMap.LonLat(lon,lat);
						FLASH_MARKER = new DMap.Marker(lonLat,symbol);
					    map.panTo(markerArray[i].lonlat);
					   //markerArray[i].flash();
					   //window.setTimeout("clearPointFlash()",4000);
					   flashMark();
					}
				}
			}	
		}
	}
}
//清除闪动效果
function clearPointFlash(){
	if(checkedGeoObj){
		checkedGeoObj.clearFlash();
	}	
}

//清除marker
function clearMakerInType(types){
	var typeArray = types.split(",");
	for(var i=0; i<typeArray.length; i++){
		for(var h=0; h<markerJsonArray.length; h++){
			if(markerJsonArray[h].lx==typeArray[i]){
				var markerArray = markerJsonArray[h].val;
				for(var j=0; j<markerArray.length; j++){
					if(markerArray[j]){
						map.removeOverlay(markerArray[j]);
					}				
				}
				markerJsonArray[h].val=[];
			}
		}		
	}
}

function clearYjczlcMakerArray(){
	if(yjczlcMakerArray.length>0){
		for(var i=0; i<yjczlcMakerArray.length; i++){
			if(yjczlcMakerArray[i]){
				map.removeOverlay(yjczlcMakerArray[i]);
			}
		}
	}
	
}
//重新设置图标
function resetMarkSymbol(id,type){

}
	