var drawPointOrPolylineWkt="";//���ƻ�������wkt
var drawBufferWkt="";//���ƵĻ������߽�Wkt
var zbzylx="";//�ռ��ѯʱ����Ҫ��ѯ����Դ����
var zySelectList=null;
var drawPointMarker=null;
var drawPolyline=null;
var bufferOverlay=null;
var resourceMarkerJsonArray=[];//�����Դ��Markerjson����
var drawResourceMarkerType="";//��ǰ��Ҫ���Ƶ���Դmarker����
var currentResourceWinType="";//��ǰ�򿪵Ĵ�������һ����Դ����


var tipWinHasInitial=false;//�ж�window�Ƿ��Ѿ���ʼ��
var showWindowTipId="";//����������ʾ������tipId ���ڴ�������ʱʹ��
//ÿ����Դ��������һ����Ӧ�Ķ�̬����
function createOrReplaceTipContent(tipTitle,tipHref,width,height){	
	//��2������һ��window֮�е�href�����Ƿ���Ϊ��ǰҳ������
	if(tipWinHasInitial){
		$("#tipConditionWin").window({
			title:tipTitle,
			width:width,
			height:height,
			href: tipHref
		});
	}else{
		$("#tipConditionWin").window({
		title:tipTitle,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		draggable:false,
		resizable:false,
		width:width,
		height:height,
		href: tipHref
		});	
		tipWinHasInitial=true;
	}	
}

function fillTipWindowContent(tipId) {
	showWindowTipId=tipId;
	switch (tipId) {
	case "zbsp":
		createOrReplaceTipContent("�ܱ���Ƶ","yjzh.gis?method=zbsp",230,120);
		break;
	case "zbjl":
		createOrReplaceTipContent("�ܱ߾���","yjzh.gis?method=zbjl",240,150);	
		break;
	case "yjzy":
		createOrReplaceTipContent("Ӧ����Դ","yjzh.gis?method=yjzy",270,170);
		break;
	case "qtzy":
		createOrReplaceTipContent("������Դ","yjzh.gis?method=qtzy",260,160);
		break;
	}
}
//��¼�ײ�ÿһ����ť�Ŀ���״̬
var leftToolBarsStatus = [{
	"btnId":"zbsp",
	"status":"off"//�ܱ���Ƶ
	},
	{"btnId":"zbjl",
	 "status": "off"//�ܱ߾���
	},
	{"btnId":"yjzy",
	 "status": "off"//������Դ
	},{
	"btnId":"qtzy",
	"status": "off"//������Դ
	}];

var rightToolBarsStatus = [ {
	"btnId" : "yjqr",
	"status" : "off"// Ԥ��ȷ��
}, {
	"btnId" : "rgcj",
	"status" : "off"// �˹��ɼ�
}, {
	"btnId" : "yjcz",
	"status" : "off"// Ԥ������
}, {
	"btnId" : "czfk",
	"status" : "off"//���÷���
} ];


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
					}
				}
			}	
		}
	}
}
//�������Ч��
/*
function clearResourceFlash(){
	if(selectedResourceObj){
		selectedResourceObj.clearFlash();
		selectedResourceObj=null;
	}	
}
*/
/**
 * ��ʾ���߹ر���Դ����
 * @param isShow
 */
function showOrCloseResourceWin(isShow){
	if(isShow){
	    $('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:false});
    }else{
    	 $('#resourceFloatWindow').window({collapsed:false,collapsible:false,closable:true,closed:true});
    }	
}

function setLeftToolBarsCssAndTip(toolId){
	var setCssBtnId="";
	var setCssIndex=0;
	for(var i=0;i<leftToolBarsStatus.length;i++){
		if(toolId==leftToolBarsStatus[i].btnId){
			setCssBtnId=toolId;	
			setCssIndex=i;
			if($("#"+toolId).attr("status")=="off"){
				leftToolBarsStatus[i].status="on";
			}else{
				leftToolBarsStatus[i].status="off";
			}
		}
	}
	if(setCssBtnId!=""){
		//��������ʾ��������ToolTip
		if($("#"+setCssBtnId).attr("status")=="off"){
			fillTipWindowContent(toolId);//�������滻window����
			var left=$("#"+toolId).offset().left;
			//���window�ĸ߶�
			var winHeight=$('#tipConditionWin').window("options").height;
			var top=$("#"+toolId).offset().top-(winHeight+30);
			$("#tipConditionWin").window({left:left,top:top,collapsed:false,collapsible:false,draggable:false,closed:false,closable:true});
			$("#tipConditionWin").window("open");		
		}else{
			$("#tipConditionWin").window("close");
		}
		//�����ù��߰�ť��ʽ
		setToolBarCss(setCssBtnId);
	}
}

/**
 * ���ù�������ʽ
 * @param toolId
 */
function setToolBarCss(toolId){
	if($("#"+toolId).attr("status")=="off"){
		//����ǰ�İ�ť�����Զ������� status
		$("#"+toolId).attr("status","on");
		//���ô�״̬����ʽ
		$("#"+toolId).addClass(toolId+"On").removeClass(toolId+"Off");			
	}else{
		$("#"+toolId).attr("status","off");
		//����Ϊ�ر�״̬����ʽ
		$("#"+toolId).addClass(toolId+"Off").removeClass(toolId+"On");			
	}
}

function setRightToolBarCss(toolId){
	//���õ�ǰ������ť��ʽ������ұ�������ťѡ����ʽ����ʾ����
	for(var i=0;i<rightToolBarsStatus.length;i++){
		var rightToolBarId=rightToolBarsStatus[i].btnId;
		if(toolId==rightToolBarId){
			if($("#"+toolId).attr("status")=="off"){
				//����ǰ�İ�ť�����Զ������� status
				rightToolBarsStatus[i].status="on";	
				$("#"+toolId).addClass(toolId+"On").removeClass(toolId+"Off");
				$("#"+toolId).attr("status","on");
			}else{
				rightToolBarsStatus[i].status="off";
				$("#"+toolId).addClass(toolId+"Off").removeClass(toolId+"On");
				$("#"+toolId).attr("status","off");
			}
		}else{
			//����Ϊ�ر�״̬����ʽ
			$("#"+rightToolBarId).addClass(rightToolBarId+"Off").removeClass(rightToolBarId+"On");	
			rightToolBarsStatus[i].status="off";
			$("#"+rightToolBarId).attr("status","off");
		}
	}
}

//���Ƶײ���ť����ʽ����
function bottomToolbarClick(toolId){
	//�������߹���ʹ����ߵ���ʽ���¼�
	if($("#"+toolId).attr("toolType")=="left"){
		setLeftToolBarsCssAndTip(toolId);
		if($("#"+toolId).attr("status")=="off"){
			clearResourceMarkers(toolId);//�����ǰ��Markers
			//����ǵ�ǰ��Դ����ͻ��Ƶ�BufferҲ�ر�֮	
			if(currentResourceWinType==toolId){
				showOrCloseResourceWin(false);
				removeDrawOverlay();
			}			
		}else{
			currentResourceWinType=toolId;			
		}
		//��������Դ���select
		if(toolId=="qtzy"&&($("#"+toolId).attr("status")=="on")){
			//�ڵ��������Դʱ�� ��ʼ����Դselect
			var selectVal=$("#qtzy_select option:selected").attr("value");		
			if(selectVal==undefined){
				setTimeout(function(){
					fillZySelect();
				},500);
			}	
		}
	}
	//������ұߵĹ��������ұߵĹ�����ʽ���¼�
	if($("#"+toolId).attr("toolType")=="right"){
		if(tipWinHasInitial){
			$("#tipConditionWin").window("close");
		}		
		//���ð�ť
		setRightToolBarCss(toolId);
		//���ô�����		
		emergencyCommandClick(toolId);
	}
}


/**
 * ����Ӧ��ָ����صĽӿں���
 * @param toolId
 */
function emergencyCommandClick(toolId){
	clearTimer();//�����ʱ��
	if($("#"+toolId).attr("status")=="off"){		
		clearMakerInType(toolId);
		currentClickShower="";
		return;
	}else{
		currentClickShower=toolId;
	}
	
	switch(toolId){
		case "yjqr"://Ԥ��ȷ��	
			/*
			clearMakerInType("yjcz,czfk");
			map.closeInfoWindow();
			showLoad();
			setTimeout(function(){
				guide_queryEventData(toolId,"btn");
			},500);		
			*/	
			guide_queryEventData(toolId,"btn");
			break;
		case "rgcj"://�˹��ɼ�
			guide_jumpToRgcjPage();
			break;
		case "yjcz"://Ԥ������
			/*
			clearMakerInType("yjqr,czfk");
			map.closeInfoWindow();
			showLoad();
			setTimeout(function(){
				guide_queryEventData(toolId,"btn");
			},500);	
			*/
			guide_queryEventData(toolId,"btn");
			break;
		case "czfk"://���÷���
			/*
			clearMakerInType("yjqr,yjcz");
			map.closeInfoWindow();
			showLoad();
			setTimeout(function(){
				guide_queryEventData(toolId);
			},500);	
			*/
			guide_queryEventData(toolId);
			break;
	}
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

function createBufferOverlay(wktString,meter){
	var degree=DMap.Util.meterToDegree(meter);
	var baseGeometry=new DMap.Geometry(wktString);
    var bufferWKT=baseGeometry.buffer(degree).toString();
    drawBufferWkt=bufferWKT;//��ŵ�ȫ�ֱ���֮�л�ÿռ��ѯ���
	var bufferOverlay=DMap.Overlay.createByWKT(bufferWKT,{color:"yellow"});
	return bufferOverlay;
}

/**
 * ͳһ���õĻ��ƻ�������ѯ����
 * @param resourceType
 * @param drawBufferType
 * @param bufferInputId
 * @param spatialSearchFun
 */
function drawBufferSearch(resourceType, drawBufferType, bufferInputId,
		spatialSearchFun) {
	//��֤�Ƿ���������ȷ����������
	var result = validationNumber(bufferInputId);
	if (!result) {
		return;
	}
	var bufferDis = "";
	if(drawBufferType=="Point"){
		map.activateTool('POINT', function(e, lonlats) {	
			//�����һ�ε�ǰ�����Դ�� Markers
			clearResourceMarkers(resourceType);
			removeDrawOverlay();
			bufferDis = $("#"+bufferInputId).val();
			var lonLatObj=getAssignPrecisionLonLat(lonlats,12,11);
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
			//ִ�пռ��ѯ�ĺ���		
			eval(spatialSearchFun+'("'+resourceType+'")');
		});
	}
	if(drawBufferType=="Polyline"){
		map.activateTool('POLYLINE', function(e, lonlats) {
			//�����һ�ε�ǰ�����Դ�� Markers
			clearResourceMarkers(resourceType);
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
			eval(spatialSearchFun+'("'+resourceType+'")');
		});	
	}
}

function initResourceSelect(){
	$.ajax({ 
		url: basePath+"yjzh.gis?method=getAllTypeList",
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
		jQuery("#qtzy_select").empty();
		if(zySelectList.length>0){
			for(var i=0;i<zySelectList.length;i++){
				var value=zySelectList[i]["xxlx"];
				var text=zySelectList[i]["sjnr"];
				$("#qtzy_select").append("<option value='"+value+"'>"+text+"</option>");
			}				
		}	
	}
}

/**
 *ִ�пռ仺������ѯ ���̨���� 
 * @param resourceType
 */
function spatialBufferSearch(resourceType){
	showSpatialSearchResult(resourceType);
	//��ʾ�ܱ���Դ����
	var winIsShow="";
	switch(resourceType){
		case "zbsp":
			winIsShow=$('#zpsp_cbk').attr("checked");
			break;
		case "zbjl":
			winIsShow=$('#zbjl_cbk').attr("checked");
			break;
		case "yjzy":
			winIsShow=$('#yjzy_cbk').attr("checked");
			break;
		case "qtzy":
			winIsShow=$('#qtzy_cbk').attr("checked");
			break;
	}
	if(winIsShow=="checked"){
		showOrCloseResourceWin(true);
	}else{
		showOrCloseResourceWin(false);
	}
}

/**
 * ��ÿؼ���ѯ����Ĳ���
 */
function getSpatialQueryParams(){
	var spatialQueryParams={};
	//��������
	var xzqhs = getSelectedDistrictTreeIds("selectXzqhTreeDiv");
	//���Ƶ�point���� ��polyline
	spatialQueryParams.sjlx=zbzylx;//��ȫ�ֱ���֮�л�õ�ǰ��Ҫ��ѯ��Դ����
	spatialQueryParams.xzqhs=xzqhs;
	spatialQueryParams.geo=drawPointOrPolylineWkt;//�ռ��ѯ���Ƶĵ㡢��WKT
	spatialQueryParams.wkt=drawBufferWkt;//���ƺ�Ļ�������WKT;	
	return spatialQueryParams;
}

/**
 * ��ʾ�ռ��������
 * @param resourceType
 */
function showSpatialSearchResult(resourceType){
	var resultHead="";
	var isShowWin=false;
	var reqParams={}; //���datagrid�Ĳ���
	drawResourceMarkerType=resourceType;//���õ�ǰ���Ƶ�Marker����
	//����·�DateGrid
	$('#zy').datagrid('loadData',{total:0,rows:[]});
	switch(resourceType){
		case "zbsp"://�ܱ���Ƶ
			resultHead="��Ƶ";
			//ִ�пռ��ѯ����	���ǵ�ֱ�����ý�ͨ��ϵ�	
			zbzylx="03";		
			reqParams=getSpatialQueryParams();
			//ֱ�ӵ�����ʾ��Ƶ
		    fillResourceList(resultHead,reqParams);
			break;
		case "zbjl"://�ܱ߾���
			//��þ�����Դ��ǰѡ�����
			var jlzylx=[];
			$('input[name="zbjlzy_cbk"]:checked').each(function(){
				jlzylx.push($(this).val());	
			 });
			zbzylx=jlzylx.join();//��ѯ�ľ�����Դ	
			//��ÿռ� ��ѯ��ز���
			reqParams=getSpatialQueryParams();
			fillPoliceforceList(reqParams);
			break;
		case "yjzy"://Ӧ����Դ
			var yjzylx=[];
			$('input[name="yjzy_cbk"]:checked').each(function(){
				yjzylx.push($(this).val());	
			 });			 
			resultHead="Ӧ����Դ";
			if((yjzylx.length>=1)&&(yjzylx.length<4)){
				zbzylx=yjzylx.join()+",";//��ѯ��Ӧ����Դ
			}else{
				zbzylx=yjzylx.join();//��ѯ��Ӧ����Դ
			}			
			//��ÿռ� ��ѯ��ز���
			reqParams=getSpatialQueryParams();
			//��Ҫ���һ�������͵ĺ���
			fillResourceList(resultHead,reqParams);
			break;
		case "qtzy"://������Դ
			resultHead=$("#qtzy_select").find("option:selected").text();
			var zylx=$("#qtzy_select").find("option:selected").val();
			zbzylx=zylx;//��ѯ��������Դ	
			//��ÿռ� ��ѯ��ز���
			reqParams=getSpatialQueryParams();
		    fillResourceList(resultHead,reqParams);
			break;
	}		
}
