/**
 * 初始化地图
 */
var map = null;
var slidebarControl = null;
//绘制缓冲区的marker图标地址
var bufferMarkerUrl=modelPath+"images/business/common/bufferMarkerRed.png";

function initMap() {
	if (typeof map == "undefined") {
		window.setTimeout("initMap()", 200);
		return;
	}
	map = new DMap.Map(document.getElementById('map'));
	map.showZoomBarControl();//缩放等级
	map.showOverviewMap();//鹰眼
	map.showMapTypeControl();//地图addControl(new DMap.MapTypeControl());	
	map.showScaleControl();//比例尺addControl(new DMap.ScaleControl());		
//	map.showMousePositionControl();//显示经纬度坐标	
	//map.setZoom(9);
}

/**
 * marker 闪烁方法
 * FLASH_TIME： 闪烁次数
 * 
 * */
var FLASH_TIME=5; 
var FLASH_MARKER =   null;
var flasmarkArray = new Array();
function flashMark(isResize){
	if(FLASH_MARKER!=null){
		FLASH_MARKER.setZIndex(2000);
		$(FLASH_MARKER.getDoms()[0]).css("z-index",2000);
		if(!isResize){
			FLASH_MARKER.setSize(new DMap.Size(40,40));			
		}
		map.addOverlay(FLASH_MARKER);
		 //map.addOverlay(marker);
		 flasmarkArray.push(FLASH_MARKER);
		 //alert(flasmarkArray.length);
		 FLASH_MARKER.flash(FLASH_TIME);
		 //alert(1);
		setTimeout(function(){		
			for(var i=0;i<flasmarkArray.length;i++){
				clearFlashMark(flasmarkArray[i]);
				map.removeOverlay(flasmarkArray[i]);	
			}
			FLASH_MARKER=null;
		},FLASH_TIME*1000/2);
	}
}
function clearFlashMark(valmarker){
	
	if(typeof(valmarker)=="object"){
		switch(valmarker.gType){
			case "POINT" :
				valmarker.clearFlash();
				break;
		}
	}
}


