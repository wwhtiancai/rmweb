	//���ݵ�·���Ϳ����Ƿ���ʾ��Ӧ��Ԫ��
	function showOrHideRoadItem(){
		clickCheckButton(); //���ò�ѯʱ��
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
			//�����ʾΪ·��ʱ�� ����ʾ�Ŷӳ���
			var roadItemValue= ($("input[name='roadItem']:checked")[0]).value;
			if(roadItemValue=="0"){
				//��ʾ��ʱѡ��Ϊ·��
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
			$("#pjsdRadio").hide();//����ƽ���ٶ���Ϊ����
			$("#pdcdRadio").show();//�����Ŷӳ���ѡ��Ϊ��ʾ		
		}else{
			$("#pjsdRadio").show();//����ƽ���ٶ���Ϊ��ʾ
			$("#pdcdRadio").hide();//�����Ŷӳ���ѡ��Ϊ����
		}
	}
	

	function getJtllParams(){ //��ý�ͨ������ѯ����
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
			if(xzqhChecked.lastIndexOf(",")>0){ //���û�ѡ������������ʱ���ѵ�¼�û����ڵ���������������Ϊ����
				param.xzqh = glbm.substr(0,6);
			}else{ //����ǵ�ѡһ�������������������л����ó���
				param.xzqh =xzqhChecked;
			}
		}
*/		
		var xzqhChecked = zTreeOnCheck('selectXzqhTreeDiv'); //��ȫѡ�Ľڵ�ʱֻ���ظ��ڵ㣬��ѡ�����ظ��ڵ�ֻ�����ӽڵ�
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
 * ��ʾ��ͨ����
 */
function showTrafficFlow(){			
	if($("#jtll").attr("status")=="off"){
		
		//reoveBlockMouduleDraw();//�������marker
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
				 * �������¹�����ʾ�������
				 * 1��8��������ʡΪ���Ķ�λͳ�ƽ��ͼ��
				 * 2��8-9���� ����Ϊ����ͳ�ƽ�� ����ʾͼ��
				 * 3��10������ �ۺ���ʾ������� 
				 * 4�����ÿ��ͳ������ʱ���Ե�ǰ��λ����Ϊ��ͼ���ĵ�
				 */
				if(zoom<8){//�ܶӵ�1����ת(ͳ��ʡ����Ϣ)
					queryLlCenterCombine('0');
				}else if(zoom==8||zoom==9){//�ܶӵ�2����ת (ͳ���м���Ϣ)
					queryLlCenterCombine('1');	
				}else{				
					showTrafficFlowToMap(valparam.sflk,valparam.jtldx,message);
				}
				//����󶨵�ͼ,���������¼���ѯ
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
							case zoom<8://��1����ת(ͳ��ʡ����Ϣ)
								queryLlCenterCombine('0');
								break;
							case (zoom==8||zoom==9)://��2����ת (ͳ���м���Ϣ)
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
 * ��ʾ��������ڵ�ͼ�� ��Ҫ����·�ں�·��
 * @param sflk 0��ʾ·�Σ�1��ʾ·�ڣ�ֵ��Ϊ��
 * @param jtldx 0:��ͨ������1:����״̬,2:ƽ���ٶ�(·��ʱ)���Ŷӳ���(·��ʱ)
 * @param trafficData
 */
function showTrafficFlowToMap(sflk,jtldx,trafficData){
	reoveBlockMouduleDraw();
	removeClusterMarkers();
	var markerCommonUrl=modelPath+"images/business/jtztjc/flowIcon/";
	var flowMarkerUrl="";
    var htmlStr = "<div>"; //�ұ߽��html
	var lineSymbol = {
			 weight: 5,
   		 animateColor:"blue",
   		 color:"green"	
   	 };
	var jtldxFlag="0";
	if(sflk=="1"&&jtldx=="2"){
		jtldxFlag="3";//��ʾ��ǰΪ·���� �Ŷӳ���
	}else{
		jtldxFlag=jtldx;
	}	
	var recInt = 0;//��¼�ұ������ʾ10��
	var minLon=0,maxLon=0,minLat=0,maxLat=0;
	redFlowMarkersArray=[];yellowFlowMarkersArra=[]; greenFlowMarkersArray=[];
	//�Ի���������ݽ���ѭ�������Ƶ���ͼҳ����
	for(var i=0;i<trafficData.length;i++){		
		//��1����������״̬������ʽ
		//����ʵʱ״̬���marker��ʽ
		switch(trafficData[i].sszt){
			case "0"://������
				flowMarkerUrl=markerCommonUrl+"gray.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"gray"	
		    	 }; 
				break;
			case "1"://��ɫ
				flowMarkerUrl=markerCommonUrl+"green.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"#05d62a"	
		    	 };
				break;
			case "2"://��ɫ
				flowMarkerUrl=markerCommonUrl+"yellow.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"#ffe100"	
		    	 };
				break;
			case "3"://��ɫ
				flowMarkerUrl=markerCommonUrl+"red.png";
				lineSymbol = {
					 weight: 3,
		    		 animateColor:"blue",
		    		 color:"#cb0a0a"	
		    	 };
				break;
		}
		var bh="";//���
		 if(trafficData[i].sjbj=="0"){
			 bh=trafficData[i].lkldbh;
		 }else{
			 bh=trafficData[i].sbbh;
		 }
		 var lllx="";
		 if(trafficData[i].sflk=="0"){ 
			 lllx="·��";
		 }else{
			 lllx="·��";
		 }
		 var geoType = "POINT";
		 var marker;
		 var flowJd="",flowWd="";
		 if(trafficData[i].jwds){
			 var geo = trafficData[i].jwds;
			 if(geo.lastIndexOf("POINT")>=0){					
				 geoType="POINT";
				 var markerSymbol = { // marker��ʽ
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
			 var pPoint = new DMap.LonLat(jd, wd); //���������
			 var markerSymbol = { // marker��ʽ
				 type : 0,
				 url : flowMarkerUrl,
				 size : new DMap.Size(24, 24),
				 offsetType : "mm",
				 borderWidth : 4,
				 borderColor : "red",
				 color : "white",
				 opacity : 1
				//	labelText : trafficData[i].lkldmc,//��ͼ�ϱ�ע�ĵ�����
				//	labelFontSize : 12
			 };	
			 marker = new DMap.Marker(pPoint, markerSymbol);//����marker����
			 marker.xxlx=const_gis_marker_flow_id;
			 //marker.id=trafficData[i].sbbh;
			 marker.id=i;//������ֵ
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
		marker.setCommonEvent(); //����marker�ĵ���¼�
		DMap.$(marker).bind("click",function(e){
			 openGisInfoWindowHtml(marker,map);
        });   
		map.addOverlay(marker);
		switch(trafficData[i].sszt){
			case "1"://��ɫ����Markers					
				greenFlowMarkersArray.push(marker);
				break;
			case "2"://��ɫ����Markers 
				yellowFlowMarkersArray.push(marker);
				break;
			case "3"://��ɫ����Markers					
				redFlowMarkersArray.push(marker);
				break;
		}
		llMarkerArray.push(marker);
		var ssztMC="";
		if(trafficData[i].sszt=="3"){
			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/red.png' /><font color=red> ӵ��</font>";
		}else if(trafficData[i].sszt=="2"){
			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/yellow.png' /><font color=orange> ����</font>";
		}else{
			ssztMC="<img height=10 width=10 src='"+modelPath+"images/flowIcon/green.png' /><font color=green> ��ͨ</font>";
		}

		if(recInt<=10){ // ֻ��ʾ10�� trafficData[i].sbbh
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
		htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:gis_flow_list(\'A1\');return false;>������Ϣ</a>";
	}		
	htmlStr+="</div>";
	$("#result_jtll").html(htmlStr);
}

function drawFlowMarkersFunc(ifClusterMode){
	if (ifClusterMode) { // �Ƿ����ۺ�Ч��
		var imageRedUrl=getMakerImg['reds']; //��
		var imageYellowUrl=getMakerImg['yellows']; //��ɫ	
		var imageGreenUrl=getMakerImg['greens']; //��ɫ
		var resolutions = map.resolutions;
		var czoom = map.getZoom();
		var offsetPixel = resolutions[czoom]*25; //20��ͼ���size��һ����	
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
			    	//����ÿ���ۺ�Marker ��ƫ����
			    	var clusterOffsetLon=0;
			    	var clusterOffsetLat=0;
			    	switch(i){
			    		case 0://��ɫ��ͳһ����ƫ��
			    			clusterOffsetLon=clusterLonLat.lon-offsetPixel;
			    			clusterOffsetLat=clusterLonLat.lat;
			    			break;
			    		case 1://��ɫ��ͳһ����ƫ��
			    			clusterOffsetLon=clusterLonLat.lon+offsetPixel;
			    			clusterOffsetLat=clusterLonLat.lat;
			    			break;
			    		case 2://��ɫ��ͳһ����ƫ��
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
		if(xzqhChecked.lastIndexOf(",")>0){ //���û�ѡ������������ʱ���ѵ�¼�û����ڵ���������������Ϊ����
			param.xzqh = glbm.substr(0,6);
		}else{ //����ǵ�ѡһ�������������������л����ó���
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
				var pixelLonlatForTwo = resolutions[czoom]*20; //20��ͼ���size��һ����	
				var pixelLonlatForThree = resolutions[czoom]*15; //20��ͼ���size��һ����	
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
						//����ͼ�����λ���㷨
						newX1 = centerx;
						newY1 = centery+pixelLonlatForTwo;
						newX2 = centerx;
						newY2 = centery-pixelLonlatForTwo;
					}else if(ssztArray.length==3){
						//����ͼ������λ���㷨
						newX1 = centerx-pixelLonlatForThree;
						newY1 = centery+pixelLonlatForThree;
						newX2 = centerx+pixelLonlatForThree;
						newY2 = centery+pixelLonlatForThree;					
						newX3 = centerx;
						newY3 = centery-pixelLonlatForThree;				
					}
					
					if(ssztArray.length==0){
						var ydCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'3',ydCount,newX1,newY1,centerx,centery,ind); //ӵ��
						llCenterMarkerArray.push(ydCenterMarker);	
						htmlStr += "<a href='#' style='font-size:12px' onclick='javascript:panToCombineGeo(\""+centerx+"\",\""+centery+"\");return false;' title='"+cmc+"  ӵ��:"+ydCount+"'><font style='font:bold'>"+cmc+"  ӵ��:"+ydCount+"</a>";	
					}else{
						var cText = " ";
						var nText = " ";
						for(var h=0; h<ssztArray.length; h++){
							if(ssztArray[h].value=="3"){
								var ydCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'3',ydCount,newX1,newY1,centerx,centery,ind); //ӵ��
								llCenterMarkerArray.push(ydCenterMarker);
								nText+="ӵ��:"+ydCount;
								cText+=" <font color=red>ӵ��:"+ydCount+"</font>";
							}else if(ssztArray[h].value=="2"){
								var hxCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'2',hxCount,newX2,newY2,centerx,centery,ind); //����
								llCenterMarkerArray.push(hxCenterMarker);	
								nText+="����:"+hxCount;
								cText+=" <font color=orange>����:"+hxCount+"</font>";
							}else{
								var ctCenterMarker = drawLlCenterMarkerFunc(cbh,cmc,'1',ctCount,newX3,newY3,centerx,centery,ind); //��ͨ
								llCenterMarkerArray.push(ctCenterMarker);	
								nText+="��ͨ:"+ctCount;
								cText+=" <font color=green>��ͨ:"+ctCount+"</font>";
							}		
						}	
						htmlStr += "<a href='#' style='font-size:12px' onclick='javascript:panToCombineGeo(\""+centerx+"\",\""+centery+"\");return false;' title='"+nText+"'><font style='font:bold'>"+cmc+"</font> "+cText+"</a>";
					}
				}	
				/*
				if(centerArray.length>9){
					htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:moreCombinInfo(\'A1\');return false;>������Ϣ</a>";
				}
				*/				
				htmlStr+="</div>";
				$("#result_jtll").html(htmlStr);
			}							 
		}
    });
}
//����Ͻ�����ĵ㼰����
function drawLlCenterMarkerFunc(cbh,cmc,ptxx,count,x,y,centerx,centery,ind){//����ʡ�����ĵ�marker	
	//alert(cbh+":"+cmc+":"+":"+count+":"+x+":"+y+":"+centerx+":"+centery+":"+ind);
	 var pPoint = new DMap.LonLat(x,y); //���������
	 var pCenterPoint= new DMap.LonLat(centerx,centery); //���������
	 var pWktPoint = "POINT("+x+" "+y+")";
	 var marker;
	 var imgurl = getMakerImg['blues']; //��
	 if(ptxx=="3"){
		 imgurl = getMakerImg['reds']; //��        
		 marker=generateMarker(true, imgurl, cmc+" ӵ��:"+count, pWktPoint, cmc+" ӵ��:"+count, count);
	 }else if(ptxx=="2"){
		 imgurl = getMakerImg['yellows']; //�� 
		 marker=generateMarker(true, imgurl, cmc+" ����:"+count, pWktPoint, cmc+" ����:"+count, count);
	 }else{
		 imgurl = getMakerImg['greens']; //�� 
		 marker=generateMarker(true, imgurl, cmc+" ��ͨ:"+count, pWktPoint, cmc+" :"+count, count);
	 }

	 marker.id=cbh;
	 marker.lonlat=pCenterPoint;
	 marker.showType=ind;
	 marker.setCommonEvent(); //����marker�ĵ���¼�
	 DMap.$(marker).bind("click",function(e){
         mouseOverMarker(this); //���markerʱ����
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

	