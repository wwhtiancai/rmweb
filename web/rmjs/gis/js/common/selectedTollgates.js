var istate = false; //true Ϊ��ʼ��ѡ  false ����ʼ

var markerArray = new Array();//���п��ڶ���
var selectMarkArray=new Array();//ѡ�п��ڶ���

var symbol1={url:modepath+"images/business/common/dragmarker.gif",size : new DMap.Size(32,32)};
var symbol2={url:modepath+"images/business/common/dragmarker.gif",size : new DMap.Size(32,32)};
var symbol3={url:modepath+"images/business/common/dragmarker.gif",size : new DMap.Size(32,32)};
var symbol4={url:modepath+"images/business/common/dragmarker.gif",size : new DMap.Size(32,32)};
var symbol5={url:modepath+"images/business/common/dragmarker.gif",size : new DMap.Size(32,32)};

var symbol9={url:modepath+"images/business/common/dragmarker.gif",size : new DMap.Size(32,32)};

var selectsymbol={url:modepath+"images/business/common/blue-dot.gif",size : new DMap.Size(32,32)};
/**
 * 
 * ��ʼ����ͼ
 * 
 * */
function initMap() {

	if (typeof map == "undefined") {
		window.setTimeout("onLoad()", 10);
		return;
	}
	map = new DMap.Map(document.getElementById('map'));
	map.showZoomBarControl();// ���ŵȼ�
	map.showOverviewMap();// ӥ��
	map.showMapTypeControl();// ��ͼaddControl(new DMap.MapTypeControl());
	map.showScaleControl();// ������addControl(new DMap.ScaleControl());
	map.showCopyright();// ��Ȩ
	// ����������
	var toolBar = new DMap.ToolBarControl();
	
	//��չ������
	toolBar.addTool({
		name: "��ѡ",
		id: 'tool-selectpoint',
		pushGroup: 'tool',
		pushed: true,
		onActive: function (){
			//me._map.deactivate();
			selectToolClik(0);
		}
	});
	
	toolBar.addTool({
		name: "��ѡ",
		id: 'tool-selectrec',
		pushGroup: 'tool',
		pushed: true,
		onActive: function (){
			selectToolClik(1);
		}
	});
	
	toolBar.addTool({
		name: "Բѡ",
		id: 'tool-selectround',
		pushGroup: 'tool',
		pushed: true,
		onActive: function (){
			selectToolClik(2);
		}
	});
	toolBar.addTool({
		name: "���ѡ��",
		id: 'tool-selectclear',
		pushGroup: 'tool',
		pushed: true,
		onActive: function (){
			selectToolClik(3);
		}
	});

	map.addControl(toolBar);
}
//����ѡ������
var drawFeature =null;
function selectToolClik(valTxt){
	clearSelectMark(true);
	switch(valTxt){
		case 0:
			istate =true;
			break;
		case 1:
		     map.activateTool('POLYGON',function(e,lonlats){
		    	 //drawFeature = new DMap.Polygon(lonlats,{fillColor:"green",fillType:"gradientradial", color: 'white',weight:20});
		    	 
		    	 isPointInPolygon(lonLatArrayWkt(lonlats));	
		    	 //map.addOverlay(drawFeature);
		     });
			break;
		case 2:
			map.activateTool('ROUND',function(e,lonlats){				
				var tmp=""+lonlats+"";
				var tmp1=tmp.replaceAll("lon=", "");
					tmp=tmp1.replaceAll("lat=", "");
				var xyArray = tmp.split(",");
				//isPointInPolygon(lonLatArrayWkt(round1.getLonLats()));
				
				isPointInCircle(new DMap.LonLat(xyArray[0],xyArray[1]),xyArray[2]);
			});			
			break;
		case 3:
			clearSelectMark(true);
			break;		
	}
}

/**
 * ����Բ��
 * @parameter valLonlat Բ���ĵ�����
 * @parameter valR		 Բ�뾶
 * 
 * */
function isPointInCircle(valLonlat,valR){
	clearSelectMark(true);
   	var icount = 0;
   	var strID = "";
   	var m=DMap.Util.degreeToMeter(valR);
	drawFeature=new DMap.Round([valLonlat,m],{fillColor:'blue'});
	map.addOverlay(drawFeature);
   	for (var i=0,a=markerArray.length; i<a; i++){
	    var uLonlat = new DMap.LonLat(markerArray[i].kkjd,markerArray[i].kkwd);
   		var udistance = Math.abs((uLonlat.lon-valLonlat.lon))*Math.abs((uLonlat.lon-valLonlat.lon))+Math.abs((uLonlat.lat-valLonlat.lat))*Math.abs((uLonlat.lat-valLonlat.lat));
   		//alert(udistance +"   "+valR);
   		if (udistance < valR*valR){
   			icount++;
   			selectsymbol["text"]=markerArray[i].kkjc;
   			var pmarker2 = new DMap.Marker(uLonlat,selectsymbol);
			 DMap.$(pmarker2).bind("click",function(e){
	        	 cancelSelectBayonet(this.getAttributes().kkbh);
	        	 map.removeOverlay(this);
			 });
	        map.addOverlay(pmarker2);
	        selectMarkArray.push(pmarker2);
   			if(icount==1)
             strID += markerArray[i].kkbh; 
            else
             strID += ","+markerArray[i].kkbh;
   		}
   		else continue;
   	}         	
   	document.getElementById('kkbh').value = strID;	
}
//�жϵ��Ƿ�������
function isPointInPolygon(valwkt){	
	clearSelectMark(true);
	var flag=false;
	var bayonex=0,bayoney=0,strID,bayonemc;
	drawFeature= DMap.Overlay.createByWKT(valwkt);
	map.addOverlay(drawFeature);
	for(var i=0,a=markerArray.length;i<a;i++){
		bayonex   = markerArray[i].kkjd;
		bayoney   = markerArray[i].kkwd;
		strID	  = markerArray[i].kkbh;
		bayonemc  = markerArray[i].kkjc;
		var lt=new DMap.LonLat(bayonex,bayoney);
		flag =DMap.Util.isContains(valwkt,lt);
		if(flag){
			selectsymbol["text"]=bayonemc;
			 var markernew =new DMap.Marker(lt,selectsymbol);
			 markernew.setCommonEvent();
			 DMap.$(markernew).bind("click",function(e){
	        	 cancelSelectBayonet(this.getAttributes().kkbh);
	        	 map.removeOverlay(this);
			 });
			 selectMarkArray.push(markernew);
			 map.addOverlay(markernew);	
	 	    if (document.getElementById('kkbh').value){
		    	 document.getElementById('kkbh').value += ","+strID;
		    }else{
		    	document.getElementById('kkbh').value = strID;
		    } 
		}	
	}
}

/**
 * ����������תΪWKT�ַ���
 * 
 * */
function lonLatArrayWkt(lonlats){
	wktString="POLYGON((";
	for(var i=0,a=lonlats.length; i<a; i++){
	    var x = lonlats[i].lon;
	    var y = lonlats[i].lat;
	    wktString += x+" "+y+","; 
	}	
	var wktString = wktString.substring(0,wktString.lastIndexOf(","))+"))";	
	return wktString;
}			


//��ʼ����ʾ������Ϣ
function initBayoneData(){
	var tollgates=window.dialogArguments;
	$.each(tollgates, function(i,item){
		if(item.kkjd!=""&&item.kkwd!=""){
			markerArray.push(item);
		}
		//alert(item.kkjd+item.kkwd);
	});
	
	
	/*
	//118.699063,32.751594,119.699063,33.337531
	//var alltanyoneStr ="{'510100100059':{'kkbh':'510100100059','KKJC':'�嶡·(�䶼·-������·)����','KKLX':'07','KKJD':118.699063,'KKWD':32.751594},'510100100046':{'kkbh':'510100100046','KKJC':'������·���Σ��׼�����·��������Ժ��·�ڣ�','KKLX':'07','KKJD':119.699063,'KKWD':33.337531}}";
	var alltanyoneStr=window.dialogArguments;
	if(alltanyoneStr==null || alltanyoneStr==""){
		return null;
	}
	var bayoneList=eval("(" +alltanyoneStr + ")");
		//alert(bayoneList['510100100059']);
	jQuery.map(bayoneList, function(item, key) {
		if(typeof(item.kkbh) != 'undefined' && item.kkbh!=""){
			markerArray.push(item);
		}
			
	});
	*/
	var bayoneid,bayonename,bayonex,bayoney,bayoneType;
	var uLonLat=null,marker=null;
	for (var i=0,a=markerArray.length; i<a; i++){
		bayoneid  = markerArray[i].kkbh;
		bayonename= markerArray[i].kkjc;
		bayoneType= markerArray[i].kklx;
		bayonex   = markerArray[i].kkjd;
		bayoney   = markerArray[i].kkwd;
		//alert(bayoneid+" \t"+bayonename+" \t"+ bayoneType+" \t"+bayonex +" \t"+ bayonex);
		uLonLat = new DMap.LonLat(bayonex,bayoney);
		var uSymbol ={};
		 switch (bayoneType){
	       	case "01":
	       		uSymbol = symbol1;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);
	
		       	break;
	       	
	       	case "02":
	       		uSymbol = symbol2;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);

		       	break;
	       	
	       	case "03":
	       		uSymbol = symbol3;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);

		       	break;
	       	
	       	case "04":
	       		uSymbol = symbol4;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);

		       	break;
	
	       	case "07":
	       		uSymbol = symbol5;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);

		       	break;
	
	       	case "08":
	       		uSymbol = symbol5;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);

		       	break;	       	
	       	default:
	       		uSymbol = symbol9;
	       		uSymbol["text"]=bayonename;
	       		marker = new DMap.Marker(uLonLat, uSymbol,{kkbh:bayoneid}); 
		       	addFeatInfo(marker, bayoneid);

		       	break;
         }
	}
}
//�����ѡ����
function clearSelectMark(isClearAll,mark){
	map.deactivate();
	if(isClearAll){
		for(var i=0,a=selectMarkArray.length;i<a;i++){
			 map.removeOverlay(selectMarkArray[i]);
		}
	}
	if(drawFeature!=null){
		map.removeOverlay(drawFeature);
		drawFeature=null;
	}
   	document.getElementById('kkbh').value = "";
   	selectMarkArray =[];
   	istate = false;
}
//��ӿ���
function addFeatInfo(omarkr, strID){	
	 omarkr.setCommonEvent();
	 DMap.$(omarkr).bind("click",function(e){		
		 if(istate){	
			 selectsymbol["text"]=this.getSymbol().text;
	         var marker2 = new DMap.Marker(this.getLonlat(),selectsymbol,this.getAttributes());
	         marker2.setCommonEvent();
	         DMap.$(marker2).bind("click",function(e){	        	 
	        	 cancelSelectBayonet(this.getAttributes().kkbh);
	        	 map.removeOverlay(this);
	         });
	         map.addOverlay(marker2);
	         selectMarkArray.push(marker2);
	 	    if (document.getElementById('kkbh').value){
		    	 document.getElementById('kkbh').value += ","+strID;
		    }else{
		    	document.getElementById('kkbh').value = strID;
		    } 
		 }
	 });
	map.addOverlay(omarkr);
}

//�����ѡ�п���
function cancelSelectBayonet(valKkbh){
	var bayonetid=valKkbh;
	 var sbayonetids=document.getElementById('kkbh').value;
	     sbayonetids =sbayonetids.split(",");
	     sbayonetids = sbayonetids.splice(jQuery.inArray(bayonetid,sbayonetids),1);
	     document.getElementById('kkbh').value=sbayonetids.toString();
}

window.onload=function(){
	initMap();
	initBayoneData();
};
window.onbeforeunload = function(){ 
	var ddd=document.getElementById('kkbh').value;
	window.returnValue = ddd;
};