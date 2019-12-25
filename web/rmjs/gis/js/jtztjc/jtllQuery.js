	//根据道路类型控制是否显示对应的元素
	function showOrHideRoadItem(){
		clickCheckButton(); //重置查询时间
		//roadType = $("input[name='roadBj']:checked")[0].value;
		var checkroadList=$("input[name='roadBj']:checked");
		roadType=checkroadList==undefined?"1":checkroadList[0].value;
		/**
		if(typeof checkroadList[0] !="object"){
			roadType = checkroadList[0].value;
		}else{
			roadType=1;
		}*/
		if(roadType=="0"){
			setTimeout(function(){
			$("#roadItemTr").css("display","block");
			//如果显示为路段时候 不显示排队长度
			var roadItemValue= ($("input[name='roadItem']:checked")[0]).value;
			if(roadItemValue=="0"){
				//表示此时选择为路段
				flowStatTypeShowControl(false);
			}else{
				flowStatTypeShowControl(true);
			}},200);
		}else{
			setTimeout(function(){
				$("#roadItemTr").css("display","none");
				flowStatTypeShowControl(false);
			},200);
		}
	}

	function flowStatTypeShowControl(showPaiDui){
		var flowStatTypeObj= $("input[name='flowStatType']");
		if(showPaiDui){
			$("#pjsdRadio").hide();//设置平均速度项为隐藏
			$("#pdcdRadio").show();//设置排队长度选项为显示		
		}else{
			$("#pjsdRadio").show();//设置平均速度项为显示
			$("#pdcdRadio").hide();//设置排队长度选项为隐藏
		}
	}
	

	function getJtllParams(){ //获得交通流量查询参数
		var param = {};
		if($("input[name='roadBj']:checked")[0]){
			param.sjbj=$("input[name='roadBj']:checked")[0].value;
		}else{
			param.sjbj="1";
		}	
		if($("input[name='flowStatType']:checked")[0]){
			param.jtldx=$("input[name='flowStatType']:checked")[0].value;
		}else{
			param.jtldx="0";
		}	
		if($("input[name='roadItem']:checked")[0]){
			param.sflk=$("input[name='roadItem']:checked")[0].value;
		}else{
			param.sflk="1";
		}	
		var ssztArray = $("input[name='jtzt']:checked");
		var jtztStr = "";
		if(ssztArray){
			for(var ss=0; ss<ssztArray.length; ss++){
				if(ss!=0){
					jtztStr+=",";
				}
				jtztStr+=ssztArray[ss].value;
			}		
		}
		if(jtztStr!=""){
			param.sszt=jtztStr;
		}else{
			param.sszt="3";
		}
/*		
		var xzqhChecked = getSelectedDistrictTreeIds('selectXzqhTreeDiv');
		if(xzqhChecked==""){
			//param.xzqh =glbm.substr(0,6);
			param.xzqh ="";
		}else{
			if(xzqhChecked.lastIndexOf(",")>0){ //当用户选择多个行政区划时，把登录用户所在的区域行政区划做为条件
				param.xzqh = glbm.substr(0,6);
			}else{ //如果是单选一个城市行政区划，则切换到该城市
				param.xzqh =xzqhChecked;
			}
		}
*/		
		var xzqhChecked = zTreeOnCheck('selectXzqhTreeDiv'); //在全选的节点时只返回父节点，半选不返回父节点只返回子节点
		param.xzqh =xzqhChecked;

		var roadChecked = getSelectedDistrictTreeIds('selectRoadTreeDiv');
		if(roadChecked!=""){
			if(roadChecked.lastIndexOf(",")>0){
				var roadCheckArray = roadChecked.split(",");
				var dldmstr = "";
				for(var i=0; i<roadCheckArray.length; i++){
					if(i!=0){
						dldmstr+=",";
					}
					dldmstr+=roadCheckArray[i];
				}
				param.dldms =dldmstr;
			}else{
				param.dldms =roadChecked;
			}
		}else{
			param.dldms="";
		}
		
		return param;
	}
/**
 * 显示交通流量
 */
function showTrafficFlow(){			
	if($("#jtll").attr("status")=="off"){
		
		//reoveBlockMouduleDraw();//清除绘制marker
		currentClickShower = ""; 
	}else{
		currentClickShower = "jtll"; 
	}	
	var param = getJtllParams();
	eventRecord=param.sjbj+","+param.jtldx+","+param.sflk+","+param.sszt+","+param.xzqh+","+param.dldms;
	//alert(param.sjbj+":"+param.jtldx+":"+param.sflk+":"+param.sszt+":"+param.xzqh+":"+param.dldms);	
	getFlowRealStateJson(param);
}


function getFlowRealStateJson(valparam){
	showLoad();
	$.ajax({	
		url:basePath + "rs.flow?method=getFlowRealStateJson",
		cache:false,
		type:"get",
		data:valparam,
		dataType:"json",
		success:function (data){
	   	    DMap.$(map).unbind('zoomend.blockInfo');
	   	    ifAddBlockListener = false;
	   	    reoveBlockMouduleDraw();
			removeClusterMarkers();
			closeLoad();
			if(!data) return;
			var code = data.code;
			var message=data.message;
		    closeLoad();
			if(code == '0'){
			    $("#result_jtll").html("");
			}else if(code == '1'){
				var zoom = map.getZoom();
				/**
				 * 根据以下规则显示结果集合
				 * 1、8级以下以省为中心定位统计结果图标
				 * 2、8-9级别 以市为中心统计结果 并显示图标
				 * 3、10级以上 聚合显示结果内容 
				 * 4、点击每个统计数字时候，以当前定位中心为地图中心点
				 */
				if(zoom<8){//总队第1次跳转(统计省级信息)
					queryLlCenterCombine('0');
				}else if(zoom==8||zoom==9){//总队第2次跳转 (统计市级信息)
					queryLlCenterCombine('1');	
				}else{				
					showTrafficFlowToMap(valparam.sflk,valparam.jtldx,message);
				}
				//激活绑定地图,流量随着事件查询
				zoomVal=0;
			    if(!ifAddListener){
			     	 ifAddListener = true;
					 DMap.$(map).bind('zoomend.jtllZoomend',function(){
						 if(currentClickShower==""){
						    return;
						 }
					     var zoom = map.getZoom();
					     reoveBlockMouduleDraw();
						 switch(true){
							case zoom<8://第1次跳转(统计省级信息)
								queryLlCenterCombine('0');
								break;
							case (zoom==8||zoom==9)://第2次跳转 (统计市级信息)
								 queryLlCenterCombine('1');	
								break;
							default:
								showTrafficFlow();
								//showTrafficFlowToMap(valparam.sflk,valparam.jtldx,message);
								break;
					     }
						 closeLoad();
						 zoomVal=zoom;
					 });
			    }
				
			}
			
		},error:function(){
			closeLoad();
		}
	});	
}

/**
 * 显示流量结果在地图上 需要区分路口和路段
 * @param sflk 0表示路段，1表示路口，值可为空
 * @param jtldx 0:交通流量，1:饱和状态,2:平均速度(路段时)、排队长度(路口时)
 * @param trafficData
 */
function showTrafficFlowToMap(sflk,jtldx,trafficData){
	reoveBlockMouduleDraw();
	removeClusterMarkers();
	var markerCommonUrl=modelPath+"images/business/jtztjc/flowIcon/";
	var flowMarkerUrl="";
    var htmlStr = "<div>"; //右边结果html
	var lineSymbol = {
			 weight: 5,
   		 animateColor:"blue",
   		 color:"green"	
   	 };
	var jtldxFlag="0";
	if(sflk=="1"&&jtldx=="2"){
		jtldxFlag="3";//表示当前为路口是 排队长度
	}else{
		jtldxFlag=jtldx;
	}	
	var recInt = 0;//记录右边最多显示10条
	var minLon=0,maxLon=0,minLat=0,maxLat=0;
	redFlowMarkersArray=[];yellowFlowMarkersArra=[]; greenFlowMarkersArray=[];
	//对获得流量数据进行循环并绘制到地图页面上
	for(var i=0;i<trafficData.length;i++){		
		//【1】根据流量状态设置样式
		//根据实时状态获得marker样式
		switch(trafficData[i].sszt){
			case "0"://无数据
				flowMarkerUrl=markerCommonUrl+"gray.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"gray"	
		    	 }; 
				break;
			case "1"://绿色
				flowMarkerUrl=markerCommonUrl+"green.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"#05d62a"	
		    	 };
				break;
			case "2"://黄色
				flowMarkerUrl=markerCommonUrl+"yellow.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"#ffe100"	
		    	 };
				break;
			case "3"://红色
				flowMarkerUrl=markerCommonUrl+"red.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"#cb0a0a"	
		    	 };
				break;
		}
		var bh="";//编号
		 if(trafficData[i].sjbj=="0"){
			 bh=trafficData[i].lkldbh;
		 }else{
			 bh=trafficData[i].sbbh;
		 }
		 var lllx="";
		 if(trafficData[i].sflk=="0"){ 
			 lllx="路段";
		 }else{
			 lllx="路口";
		 }
		 var geoType = "POINT";
		 var marker;
		 var flowJd="",flowWd="";
		 if(trafficData[i].jwds){
			 var geo = trafficData[i].jwds;
			 if(geo.lastIndexOf("POINT")>=0){					
				 geoType="POINT";
				 var markerSymbol = { // marker样式
					 type : 0,
					 url : flowMarkerUrl,
					 size : new DMap.Size(24, 24),
					 offsetType : "mm",
					 borderWidth : 4,
					 borderColor : "red",
					 color : "white",
					 opacity : 1
				 };
				 marker = DMap.Overlay.createByWKT(geo,markerSymbol);
				 marker.xxlx=const_gis_marker_flow_id;
				 marker.id=trafficData[i].sbbh;
				 marker.lkldbh=trafficData[i].lkldbh;
				 marker.lonlat = marker.getLonlat();
				 flowJd= marker.lonlat.lon;
				 flowWd= marker.lonlat.lat;
			 }else{
				 geoType="LINESTRING";
				 marker = DMap.Overlay.createByWKT(geo,lineSymbol);
			     marker.xxlx=const_gis_marker_flow_id;
				 marker.id=trafficData[i].sbbh;
				 marker.lkldbh=trafficData[i].lkldbh;
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
		    	 flowJd= marker.lonlat.lon;
				 flowWd= marker.lonlat.lat;
			 }		 
		 }else{
			 geoType="POINT";
			 var jd=trafficData[i].jd;
			 var wd=trafficData[i].wd;				 
			 var pPoint = new DMap.LonLat(jd, wd); //创建点对象
			 var markerSymbol = { // marker样式
				 type : 0,
				 url : flowMarkerUrl,
				 size : new DMap.Size(24, 24),
				 offsetType : "mm",
				 borderWidth : 4,
				 borderColor : "red",
				 color : "white",
				 opacity : 1
				//	labelText : trafficData[i].lkldmc,//地图上标注的点名称
				//	labelFontSize : 12
			 };	
			 marker = new DMap.Marker(pPoint, markerSymbol);//创建marker对象
			 marker.xxlx=const_gis_marker_flow_id;
			 //marker.id=trafficData[i].sbbh;
			 marker.id=i;//存索引值
			 marker.lkldbh=trafficData[i].lkldbh;
			 marker.lonlat = pPoint;
			 flowJd=marker.lonlat.lon;
			 flowWd=marker.lonlat.lat;
		 }
	   	 if(flowJd!=""&&flowWd!=""){
				if(minLon==0){
					minLon = flowJd;
			     	minLat = flowWd;
			     	maxLon = flowJd;
			     	maxLat = flowWd;
				}else{
				 if(maxLon<flowJd){
			        maxLon = flowJd;
			      }             	
			      if(maxLat<flowWd){
			        maxLat = flowWd;
			      }
			 	if(minLon>flowJd){
		           minLon = flowJd;
		         }	
			 	if(minLat>flowWd){
			       minLat = flowWd;
			    } 
				} 
		}
		marker.setCommonEvent(); //激活marker的点击事件
		DMap.$(marker).bind("click",function(e){
			 openGisInfoWindowHtml(marker,map);
        });   
		map.addOverlay(marker);
		switch(trafficData[i].sszt){
			case "1"://绿色流量Markers					
				greenFlowMarkersArray.push(marker);
				break;
			case "2"://黄色流量Markers 
				yellowFlowMarkersArray.push(marker);
				break;
			case "3"://红色流量Markers					
				redFlowMarkersArray.push(marker);
				break;
		}
		llMarkerArray.push(marker);
		var ssztMC="";
		if(trafficData[i].sszt=="3"){
			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/red.png' /><font color=red> 拥堵</font>";
		}else if(trafficData[i].sszt=="2"){
			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/yellow.png' /><font color=orange> 缓行</font>";
		}else{
			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/green.png' /><font color=green> 畅通</font>";
		}

		if(recInt<=10){ // 只显示10条 trafficData[i].sbbh
			htmlStr += "<a href='#' style='font-size:12px' onclick='javascript:panToLlGeo(\""+i+"\",\""+geoType+"\");return false;' title='"+trafficData[i].lkldmc+"'>"+ssztMC+"  "+trafficData[i].lkldmc+"</a>";
		}		
    	recInt++;
	}
	  //alert("red length "+redFlowMarkersArray.length+" \ngreen length "+greenFlowMarkersArray.length+"\nyellow length "+yellowFlowMarkersArray.length);	
	  if(minLon>0&&maxLon>0&&minLat>0&&maxLat>0){
		  var minx = parseFloat(minLon)-0.2;
	      var miny = parseFloat(minLat)-0.2;
	      var maxx = parseFloat(maxLon)+0.2;            
	      var maxy = parseFloat(maxLat)+0.2;		           
	      var statCenterX=0.5*(parseFloat(minx)+parseFloat(miny));
	      var statCenterY=0.5*(parseFloat(maxx)+parseFloat(maxy));
	      var statCenter=new DMap.LonLat(statCenterX,statCenterY);  
	      var curLevel=map.getZoom();
	      if(curLevel<8){
	    	  queryLlCenterCombine('0');
	      }else if(curLevel==8||curLevel==9){
	      	 map.setCenter(statCenter,curLevel);
	      	 queryLlCenterCombine('1');
	      }else if(curLevel>=10&&curLevel<=13){		    		
          	//map.setCenter(statCenter,curLevel);	    	 
	    	  drawFlowMarkersFunc(true);
	      }else{
	    	 //map.setCenter(statCenter,curLevel);
	    	drawFlowMarkersFunc(false);
	      }         
	  }else{
	  	if(maxLon>0&&maxLat>0){
				var statCenter=new DMap.LonLat(maxLon,maxLat);
				map.setCenter(statCenter,10);
		}
	  }
	if(trafficData.length>9){
		htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:gis_flow_list(\'A1\');return false;>更多信息</a>";
	}		
	htmlStr+="</div>";
	$("#result_jtll").html(htmlStr);
}

function drawFlowMarkersFunc(ifClusterMode){
	if (ifClusterMode) { // 是否做聚合效果
		var imageRedUrl=getMakerImg['reds']; //红
		var imageYellowUrl=getMakerImg['yellows']; //黄色	
		var imageGreenUrl=getMakerImg['greens']; //绿色
		var resolutions = map.resolutions;
		var czoom = map.getZoom();
		var offsetPixel = resolutions[czoom]*25; //20是图标的size的一半多点	
		var clusterRedOption = {
			gridSize : 60,
			minimumClusterSize : 3,
			maxZoom : 14,
			imagePath : imageRedUrl,
			imageExtension : ".png",
			zoomOnClick : true,
			maxZoomFunc : "drawFlowMarkersFunc(false)"
		};
		var clusterYellowOption = {
				gridSize : 60,
				minimumClusterSize : 3,
				maxZoom : 14,
				imagePath : imageYellowUrl,
				imageExtension : ".png",
				zoomOnClick : true,
				maxZoomFunc : "drawFlowMarkersFunc(false)"
		};
		var clusterGreenOption = {
				gridSize : 60,
				minimumClusterSize : 3,
				maxZoom : 14,
				imagePath : imageGreenUrl,
				imageExtension : ".png",
				zoomOnClick : true,
				maxZoomFunc : "drawFlowMarkersFunc(false)"
		};
		var markerRedCluster = new DMap.MarkerClusterer(map, redFlowMarkersArray,
				clusterRedOption);
		var markerYellowCluster = new DMap.MarkerClusterer(map, yellowFlowMarkersArray,
				clusterYellowOption);
		var markerGreenCluster = new DMap.MarkerClusterer(map, greenFlowMarkersArray,
				clusterGreenOption);
		var flowClusterMarkers=[];
		flowClusterMarkers.push(markerRedCluster);
		flowClusterMarkers.push(markerYellowCluster);
		flowClusterMarkers.push(markerGreenCluster);
		for(var i=0;i<flowClusterMarkers.length;i++){
			var clusterMarkers=flowClusterMarkers[i]._clusters;
			  for(var m=0;m<clusterMarkers.length;m++){
			    	var clusterMarker=clusterMarkers[m];	    	
			    	var clusterLonLat=clusterMarker.getCenter();
			    	//设置每个聚合Marker 做偏移量
			    	var clusterOffsetLon=0;
			    	var clusterOffsetLat=0;
			    	switch(i){
			    		case 0://红色都统一向左偏移
			    			clusterOffsetLon=clusterLonLat.lon-offsetPixel;
			    			clusterOffsetLat=clusterLonLat.lat;
			    			break;
			    		case 1://黄色都统一向右偏移
			    			clusterOffsetLon=clusterLonLat.lon+offsetPixel;
			    			clusterOffsetLat=clusterLonLat.lat;
			    			break;
			    		case 2://绿色都统一向下偏移
			    			clusterOffsetLon = clusterLonLat.lon;
			    			clusterOffsetLat = clusterLonLat.lat-offsetPixel;
			    			break;
			    	}
			    	var offsetLonlat=new DMap.LonLat(clusterOffsetLon,clusterOffsetLat);
			    	clusterMarker.clusterIcon_.setCenter(offsetLonlat);
			  }
		}	  
		redMarkerClusterArray.push(markerRedCluster);
		yellowMarkerClusterArray.push(markerYellowCluster);
		greenMarkerClusterArray.push(markerGreenCluster);
		redFlowMarkersArray=[];
		yellowFlowMarkersArray=[];
		greenFlowMarkersArray=[];
	}
    closeLoad();
}

function queryLlCenterCombine(ind){
	var param = {};
	if($("input[name='roadBj']:checked")[0]){
		param.sjbj=$("input[name='roadBj']:checked")[0].value;
	}else{
		param.sjbj="1";
	}	

	if($("input[name='flowStatType']:checked")[0]){
		param.jtldx=$("input[name='flowStatType']:checked")[0].value;
	}else{
		param.jtldx="0";
	}	
	if($("input[name='roadItem']:checked")[0]){
		param.sflk=$("input[name='roadItem']:checked")[0].value;
	}else{
		param.sflk="1";
	}	
	var ssztArray = $("input[name='jtzt']:checked");
	var jtztStr = "";
	for(var ss=0; ss<ssztArray.length; ss++){
		if(ss!=0){
			jtztStr+=",";
		}
		jtztStr+=ssztArray[ss].value;
	}
	if(jtztStr!=""){
		param.sszt=jtztStr;
	}else{
		param.sszt="1,2,3";
	}
	
	var xzqhChecked = getSelectedDistrictTreeIds('selectXzqhTreeDiv');	       
	if(xzqhChecked==""){
		param.xzqh =glbm.substr(0,6);
	}else{
		if(xzqhChecked.lastIndexOf(",")>0){ //当用户选择多个行政区划时，把登录用户所在的区域行政区划做为条件
			param.xzqh = glbm.substr(0,6);
		}else{ //如果是单选一个城市行政区划，则切换到该城市
			param.xzqh =xzqhChecked;
		}
	}

	var roadChecked = getSelectedDistrictTreeIds('selectRoadTreeDiv');
	if(roadChecked!=""){
		if(roadChecked.lastIndexOf(",")>0){
			var roadCheckArray = roadChecked.split(",");
			var dldmstr = "";
			for(var i=0; i<roadCheckArray.length; i++){
				if(i!=0){
					dldmstr+=",";
				}
				dldmstr+=glbm+roadCheckArray[i];
			}
			param.dldms =dldmstr;
		}else{
			param.dldms =glbm+roadChecked;
		}
	}else{
		param.dldms="";
	}
	
	var queryUrl ="";
	if(ind=="0"){
		queryUrl=basePath+"jtztjc.gis?method=getProvenceLiuliangCountData";				
	}else{
		queryUrl=basePath+"jtztjc.gis?method=getCityLiuliangCountData";
	}
    
	//param.paramUrl = basePath+"rs.flow?method=getFlowRealStateJson&sjbj="+param.sjbj+"&jtldx="+param.jtldx+"&sflk="+param.sflk+"&sszt="+param.sszt+"&xzqh="+param.xzqh+"&dldms="+param.dldms;
	$.ajax({
		url:queryUrl,
		dataType:"json",
		data:param,
		async:false,
		cache:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(centerArray){
			var htmlStr="<div>";
			if(centerArray){
				if(centerArray.length>10){
					$("#result_jtll").css({"overflow-y":"scroll"});
				}else{
					$("#result_jtll").css({"overflow-y":"hidden"});
				}
				var ssztArray = $("input[name='jtzt']:checked");
				var resolutions = map.resolutions;
				var czoom = map.getZoom();
				var pixelLonlatForTwo = resolutions[czoom]*20; //20是图标的size的一半多点	
				var pixelLonlatForThree = resolutions[czoom]*15; //20是图标的size的一半多点	
				for(var j=0; j<centerArray.length; j++){						
					var centerx = centerArray[j].centerx;
					var centery = centerArray[j].centery;
					if(!centerx || centerx==null || centerx==""){
						continue;									
					}

					var cminx = centerArray[j].minx;
					var cminy = centerArray[j].miny;
					var cmaxx = centerArray[j].maxx;
					var cmaxy = centerArray[j].maxy;
					
					var cbh = centerArray[j].xzqdm;								
					var cmc = centerArray[j].xzqmc;
					var countMap = centerArray[j].countMap;
					var ydCount = countMap['3'];
					var hxCount = countMap['2'];
					var ctCount = countMap['1'];					
					
					var newX1;
					var newY1;
					var newX2;
					var newY2;
					var newX3;
					var newY3;					
					if(ssztArray.length<=1){
						newX1 = centerx;
						newY1 = centery;
					}else if(ssztArray.length==2){
						//两个图标横向位置算法
						newX1 = centerx;
						newY1 = centery+pixelLonlatForTwo;
						newX2 = centerx;
						newY2 = centery-pixelLonlatForTwo;
					}else if(ssztArray.length==3){
						//三个图标三角位置算法
						newX1 = centerx-pixelLonlatForThree;
						newY1 = centery+pixelLonlatForThree;
						newX2 = centerx+pixelLonlatForThree;
						newY2 = centery+pixelLonlatForThree;					
						newX3 = centerx;
						newY3 = centery-pixelLonlatForThree;				
					}
					
					if(ssztArray.length==0){
						var ydCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'3',ydCount,newX1,newY1,centerx,centery,ind); //拥堵
						llCenterMarkerArray.push(ydCenterMarker);	
						htmlStr += "<a href='#' style='font-size:12px' onclick='javascript:panToCombineGeo(\""+centerx+"\",\""+centery+"\");return false;' title='"+cmc+"  拥堵:"+ydCount+"'><font style='font:bold'>"+cmc+"  拥堵:"+ydCount+"</a>";	
					}else{
						var cText = " ";
						var nText = " ";
						for(var h=0; h<ssztArray.length; h++){
							if(ssztArray[h].value=="3"){
								var ydCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'3',ydCount,newX1,newY1,centerx,centery,ind); //拥堵
								llCenterMarkerArray.push(ydCenterMarker);
								nText+="拥堵:"+ydCount;
								cText+=" <font color=red>拥堵:"+ydCount+"</font>";
							}else if(ssztArray[h].value=="2"){
								var hxCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'2',hxCount,newX2,newY2,centerx,centery,ind); //缓行
								llCenterMarkerArray.push(hxCenterMarker);	
								nText+="缓行:"+hxCount;
								cText+=" <font color=orange>缓行:"+hxCount+"</font>";
							}else{
								var ctCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'1',ctCount,newX3,newY3,centerx,centery,ind); //畅通
								llCenterMarkerArray.push(ctCenterMarker);	
								nText+="畅通:"+ctCount;
								cText+=" <font color=green>畅通:"+ctCount+"</font>";
							}		
						}	
						htmlStr += "<a href='#' style='font-size:12px' onclick='javascript:panToCombineGeo(\""+centerx+"\",\""+centery+"\");return false;' title='"+nText+"'><font style='font:bold'>"+cmc+"</font> "+cText+"</a>";
					}
				}	
				/*
				if(centerArray.length>9){
					htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:moreCombinInfo(\'A1\');return false;>更多信息</a>";
				}
				*/				
				htmlStr+="</div>";
				$("#result_jtll").html(htmlStr);
			}							 
		}
    });
}
//绘制辖区中心点及数量
function drawLlCenterMarkerFunc(cbh,cmc,ptxx,count,x,y,centerx,centery,ind){//绘制省市中心点marker	
	//alert(cbh+":"+cmc+":"+":"+count+":"+x+":"+y+":"+centerx+":"+centery+":"+ind);
	 var pPoint = new DMap.LonLat(x,y); //创建点对象
	 var pCenterPoint= new DMap.LonLat(centerx,centery); //创建点对象
	 var pWktPoint = "POINT("+x+" "+y+")";
	 var marker;
	 var imgurl = getMakerImg['blues']; //蓝
	 if(ptxx=="3"){
		 imgurl = getMakerImg['reds']; //红        
		 marker=generateMarker(true, imgurl, cmc+" 拥堵:"+count, pWktPoint, cmc+" 拥堵:"+count, count);
	 }else if(ptxx=="2"){
		 imgurl = getMakerImg['yellows']; //黄 
		 marker=generateMarker(true, imgurl, cmc+" 缓行:"+count, pWktPoint, cmc+" 缓行:"+count, count);
	 }else{
		 imgurl = getMakerImg['greens']; //绿 
		 marker=generateMarker(true, imgurl, cmc+" 畅通:"+count, pWktPoint, cmc+" :"+count, count);
	 }

	 marker.id=cbh;
	 marker.lonlat=pCenterPoint;
	 marker.showType=ind;
	 marker.setCommonEvent(); //激活marker的点击事件
	 DMap.$(marker).bind("click",function(e){
         mouseOverMarker(this); //点击marker时调用
     });

	 map.addOverlay(marker);		 
	 function mouseOverMarker(pmark){		
          if(ind=="0"){
        	  map.setCenter(pmark.lonlat,8,null);
        	  queryLlCenterCombine('0');
          }else{
        	  map.setCenter(pmark.lonlat,12,null);
        	  showTrafficFlow();
        	  //bottomToolbarClick(currentClickShower);	
          }
     }
	 return marker;	    
}

	