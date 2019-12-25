var passPicturesBaseUrl=basePath+"readPassPic.tfc?method=getPassPic&gcxh=";
//var passPicturesBaseUrl="http://192.168.0.221:8083/rmweb/readPassPic.tfc?method=getPassPic&gcxh=";

//========================ҳ���ʼ�������Լ�����ģ�鿪ʼ========================================
var pathCoordinates=[];//��Ź켣�������

var curFlashMark=null;//��¼��ǰ��˸�Ŀ���

var currentBufferRoute=null;//��ǰ���ƵĻ�����·��
var currentLineRoute=null;//�Ѿ�����·��
var displayParameters={
	isDrawPath:false,//��ʶ�Ƿ���ƹ켣·��
	isImageGallery:false,//�Ƿ�ͼƬ��ʾ�ؼ�
	bufferRoute:false//�·�߷���
};

var legendControl=null;
var tollgateRouteLines=[];

/*
 * ��������Ҫ��ʾ���ֶ�����
 */
 var labelShowFields={
	 KKMC:"��������",
	 BMMC:"������",
	 KKLX:"��������"	 
};
//������ʾMarker֮�����ݻ�����Ϣ
var markerLabelPamaters = {
	markLabels : [// ����������ʾ����
	{
		field : 'KKMC',
		title : '��������'
	}, {
		field : 'KKLX',
		title : '����',
		formatter : function(val) {
			return AppUtils.getDicText("kklx", val);
		}
	}, {
		field : 'GLBM',
		title : '������',
		formatter : function(val) {
			return AppUtils.getDicText("glbm", val);
		}
	} ]
};
/**
 * ��ʾGrid����Ĺ̶�����
 */
var gridFixedParamters={
	gridContainer:"",//��ʾGrid����������
	headColumns:[],//Grid��ʾͷ��
	gridWidth:"",//Grid���
	gridHeight:""//Grid�߶�
};

var markerFixedParamters={
	customStyle:false,//�û��Ƿ��Զ�����ʽ
	markerImgUrl:"",//�ƶ�Markerͼ��Url
	markerLabels:null//����Marker��ʾ���ݱ�ǩ
};

//��¼��ǰGrid֮�а��������� ��Ҫ���ڼ�¼Grid֮������
var curentGridData=[];

 /**
	 * �����ұ�Grid��Ҫ��ʾ�Ĳ���
	 */
 var displayGridSetting={
	head_columns:null,
	width:"",
	height:""   
 };
 
 /**
  * ���������ȫ������
  */
 var analysisParamter={
 	filter_condition:"",//�ⲿ��������� json����
 	request_url:"",//�����url
 	grid_head_columns:null,//grid��ͷ���� ����
 	marker_tips:null,//marker��ʾ���� ����
 	marker_style:null,//marker��ʽ json����
 	grid_width:"",//grid��ʾ���
 	grid_height:""//grid��ʾ�Ŀ��
 };
/**
 * �ܱ߲�ѯͼ������
 */
var aroundLayers=[
	 {text:"��Ƶ",value:"epgis_vw_dev_tollgate",img:modelPath+"images/common/video.png",imgsize:{width:32,width:32}},
	 {text:"����",value:"epgis_vw_dev_tollgate",img:"",imgsize:{}}
];


/**
 * �ܱ߲�ѯF
 * 
 * @param point
 * @param distince
 * @param layers
 */
function aroundSearch(point,distince,layers){
	var wktString = "POINT("+point.lon+" "+point.lat+")";
	//alert(distince);
	for(var i=0;i<layers.length;i++){
			  var layer=layers[i];
			  var mapData=new DMap.MapData({
					serviceMethod:"bufferSearch"
					,layerName:layers[i].value
					,colList:"all"
					,beginRecord:0
					,featureLimit:1000
					,returnType:1
					,wktStr:wktString
					,r:distince
				});
		      mapData.sendRequest(function(data,config){
		    	  if(data.Result=='Error'){//������
		    			//alert(data.Msg);//���ݷ��ʸ��·��񷵻صĴ�����Ϣ
		    		  	showTip(data.Msg);
		    			return;
		    		}
		    	 var layerData=data[0];
				var rowList=layerData.rowList;
				var roundPoints=layerData.roundPoints;
				if(roundPoints){
					var polygon=DMap.Overlay.createByWKT(roundPoints);
					map.addOverlay(polygon);
				}
				for(var j=0;j<rowList.length;j++){
					   var shape = rowList[j].values.SHAPE;
					   var mark=DMap.Overlay.createByWKT(shape,{type:0,url:layer.img});
					   map.addOverlay(mark);
				}
		   });
	}	
}

function fillPageConditions(){
	//��1�����������ൽҳ��֮��
	fillHPZLToPage("hpzl_select",hpzlList);	
	//��2�����ǩ�ս����ҳ��֮��
	fillCXYYToPage("cxyy_select", cxyyList);
	var curDate=new Date().Format("yyyy-MM-dd hh:mm");
	var curDateTime=new Date();
	var tempDate= new Date();
	var startDateTime=curDateTime.getTime() - 24*60*60*1000*7; 
	tempDate.setTime(startDateTime);
	var startDate=tempDate.Format("yyyy-MM-dd hh:mm");	
	//���ÿ�ʼʱ��Ϊ��ǰʱ��ǰһ��
	$('#query_start_date').datetimebox({showSeconds:false});
	$('#query_start_date').datetimebox('setValue', getLastWeek()+" 00:00");	
	//���ý���ʱ��Ϊ��ǰʱ��
	$('#query_end_date').datetimebox({showSeconds:false});
	$('#query_end_date').datetimebox('setValue', curDate);	
}


//�򿪿��ڵķŴ�ͼƬ
function openBigPic(objImg){
	//alert(objImg.baseUrl);
	var maxImgControl=new MaxImgControl({imgUrl:objImg.baseUrl});
	maxImgControl.show();
}

//�����������ð�ťΪdisabled
function setAllButtonsDisabled(){
	$("#gjcxbtn").attr("disabled",true);
	$("#hdqybtn").attr("disabled",true);
	$("#hdlxbtn").attr("disabled",true);
	$("#crkkbtn").attr("disabled",true);
}

//�������а�ťΪenabled
function setAllButtonsEnable(){
	$("#gjcxbtn").attr("disabled",false);
	$("#hdqybtn").attr("disabled",false);
	$("#hdlxbtn").attr("disabled",false);
	$("#crkkbtn").attr("disabled",false);
	$("#gjcxbtn").addClass("button_other");
	$("#hdqybtn").addClass("button_other");
	$("#hdlxbtn").addClass("button_other");
	$("#crkkbtn").addClass("button_other");
}
/**
 * ��ó����켣��ѯ��������
 */
function getVehicleParameters(){
	var analysisCondition={
		"query_car_license":"",//���ƺ�
		"query_license_type":"",//��������
		"query_start_date":"",//��ѯ��ʼ����
		"query_end_date":"",//��ѯ��������
		"query_reason":"",//��ѯԭ��
		"query_district":""//��ѯ��������			
	};
	//��ҳ��֮�л�ù켣������ѯ����
	//��1��������Բ�ѯ����
	var carLicenses=$("#query_car_licenses").val();	
	var carLicenseType=$("#hpzl_select").combobox('getValue');
	var startDate=$("#query_start_date").datetimebox('getValue');
	var endDate=$("#query_end_date").datetimebox('getValue');
	var queryReason=$("#cxyy_select").combobox('getValue');;
	//var queryDistrict=$("input[name='queryRadioDistrict']:checked").val();
	analysisCondition.query_car_license=encodeURI(carLicenses);
	analysisCondition.query_license_type=carLicenseType;
	analysisCondition.query_start_date=startDate;
	analysisCondition.query_end_date=endDate;
	analysisCondition.query_reason=queryReason;
	analysisCondition.query_district="";
	//��2����ÿռ��ѯ����
	return analysisCondition;
}

//===========================ҳ���ʼ�������Լ�����ģ�����========================================


//===========================�켣��ѯ�����չʾ��ʼ========================================
/**
 * @param analysisType ���ݴ�������������ʾgrid����
 */
function setGridAndMarkerParameters(analysisType) {
	var head_columns =null;
	var grid_height="";
	var grid_width="";
	var customStyle=false;
	var markerImgUrl="";
	var common_columns =[ [
	  				{
	  					field : 'KKLX',
	  					title : '��������',
	  					align : "center",
	  					formatter : AppUtils.DataGrid.kklxFormatter
	  				},
	  				{
	  					field : "KKJC",
	  					title : "���ڼ��"
	  				},
	  				{
	  					field : "DELBTN",
	  					title : "�Ƿ��Ƴ�",
	  					align : "center",
	  					formatter : function() {
	  						return "<a href='javascript:void(0)' class='easyui-linkbutton' onclick='removeRowAndMarker(this)'>�Ƴ�</a>";
	  					}
	  				}, {
	  					field : "GCSJ",
	  					title : "����ʱ��",
	  					align : "center"
	  				}, {
	  					field : "CLLX",
	  					title : "��������",
	  					align : "center"
	  				}, {
	  					field : "CLSD",
	  					title : "�г��ٶ�",
	  					align : "center"
	  				} ] ];
	switch (analysisType) {
	case "track":
		head_columns=common_columns;
	    grid_height="300";
		grid_width="";
		customStyle=true;
		markerImgUrl=modelPath + "js/third_libs/sketchmap/img/round.png";		
		break;
	case "pass_frequency":
		head_columns = [ [
				{
					field : 'KKLX',
					title : '��������',
					align : "center",
					formatter : AppUtils.DataGrid.kklxFormatter
				},
				{
					field : 'KKJC',
					title : '���ڼ��'
				},
				{
					field : 'PASS_TOTALS',
					title : '��������'
				},
				{
					field : 'DELBTN',
					title : '�Ƿ��Ƴ�',
					formatter : function() {
						return "<a href='javascript:void(0)' class='easyui-linkbutton' onclick='removeRowAndMarker(this)'>�Ƴ�</a>";
					}
				} ] ];
		grid_height = "";
		grid_width = "";
		customStyle = false;
		markerImgUrl = "";		
		break;
		grid_height = "";
		grid_width = "";
		customStyle = false;
		markerImgUrl = "";	
		break;
	case "live_area":
	case "live_route":
		head_columns =common_columns;
		grid_height = "";
		grid_width = "";
		customStyle = false;
		markerImgUrl = "";	
		break;
	}
	// �����ʾ�����div����
	var baseContent = slidebarControl.getContentDiv();
	$(baseContent).empty();
	var gridContainer = $(baseContent);
	var tempWidth = $(baseContent).width();
	var tempHeight = $(baseContent).height();
	//���ø߶�
    if(grid_height==""){
    	grid_height=tempHeight;
    }
    //���ÿ��
    if(grid_width==""){
    	grid_width=tempWidth;
    }
	displayGridSetting.head_columns =head_columns;
	displayGridSetting.height=grid_height;
	//����Grid�����Ի�����		
	gridFixedParamters.gridContainer=gridContainer;//�����ʾGridʹ������
	gridFixedParamters.gridHeight=grid_height;
	gridFixedParamters.gridWidth=grid_width;
	gridFixedParamters.headColumns=head_columns;
	//����Marker���Ի�����
	markerFixedParamters.customStyle=customStyle;
	markerFixedParamters.markerImgUrl="";
	markerFixedParamters.markerLabels=markerLabelPamaters;
}

/**
 *�Ѳ�ѯ����ÿͻ��˷�ҳ��ʾ
 * @param analysisType
 */
function getVehiclePassTrackResult(analysisType) {
	// ��1��������������ַ
	var reqUrl = basePath + "passTrack.gis?method=";
	var useDefinedMarker=false;
	var markerImgUrl="";
	switch (analysisType) {
	case "track":// �켣��ѯ���
		reqUrl += "getTrackPathQuery";
		useDefinedMarker=true;
		markerImgUrl=modelPath + "js/third_libs/sketchmap/img/round.png";
		break;
	case "pass_frequency":// �������뿨�ڷ������
		reqUrl += "passTollgateFrequency";
		break;
	case "live_route":// �����·�߷������
		reqUrl += "getTrackPathQuery";
		break;	
	}
	// ��2����÷�������Ĳ���
	var out_condition = getVehicleParameters();
	// ���ⲿ��������ת��Ϊjson�ַ���
	var filter_condition = ObjectToStr(out_condition);
	var reqParams = {
		"analysis_condition" : filter_condition
	};
	// ��3������Ajax�����÷��ؽ��
	$.ajax({
		url : reqUrl,
		data : reqParams,
		dataType : 'text',
		success : function(data) {
			var resultJSONData = $.evalJSON(data);			
			// �����ʾ�����div����
			var baseContent = slidebarControl.getContentDiv();
			$(baseContent).empty();
			slidebarControl.slideOut();
			var gridContainer = $(baseContent);
			if(resultJSONData.total<=0){
				dataGridClientShowData(gridContainer, headColumns, gridWidth,
						gridHeight, resultJSONData);
				setAllButtonsEnable();//��ִ�й����������а�ťΪ�ɵ��	
				$.messager.alert("��ʾ��Ϣ", "�޹�����¼��", "info");
				return;
			}
			//ÿ���������֮������ݴ���ڿͻ�������
			curentGridData=[];
			curentGridData=resultJSONData.rows;	
			// �����ʾGrid����Ļ������� headColumns �Լ���������
			setGridAndMarkerParameters(analysisType);
			var gridWidth = $(baseContent).width();
			var gridHeight = $(baseContent).height();
			// ��÷������֮��grid ��Ⱥ͸߶�
			if (displayGridSetting.width != "") {
				gridWidth = displayGridSetting.width;
			}
			if (displayGridSetting.height != "") {
				gridHeight = displayGridSetting.height;
			}
			var headColumns = displayGridSetting.head_columns;	
			// ��3.1����ʾ�����DataGrid֮��
			dataGridClientShowData(gridContainer, headColumns, gridWidth,
					gridHeight, resultJSONData);
			setAllButtonsEnable();//��ִ�й����������а�ťΪ�ɵ��
			// ��3.2������Markers������Ҫ�ص���ͼҳ��		
			//���ݲ�ͬ�������ʹ��벻ͬMarker��ʽ
			drawTollgateMarkerToMap(resultJSONData.rows, useDefinedMarker, markerImgUrl,markerLabelPamaters);			
			//��3.3�����ƹ�����������ͼ��
			if(displayParameters.isDrawPath&&pathCoordinates.length>0){
				drawDynamicMarkerPath(map,pathCoordinates);
			}else{
				$.messager.alert("��ʾ��Ϣ", "��������Ϊ�����޷���ʾ�켣��", "info");
			}
			//�Ƿ���ʾ����ͼƬ
			if(displayParameters.isImageGallery){			
				var imgGCXHArray=[];
				var gridPageData=resultJSONData.rows;
				for(var m=0;m<gridPageData.length;m++){
					var gcxh=gridPageData[m].GCXH;
					imgGCXHArray.push(gcxh);
				}
				showGridEveryPagePassImages($(baseContent),imgGCXHArray);
			}
			//�Ƿ���ʾ����·��
			if(displayParameters.bufferRoute){
				drawBufferRoute(curentGridData);
			}			
		}
	});
}

/**
 * ���ɻ���Marker����
 * @param userDefined �Ƿ��û��Զ���
 * @param markerImgUrl markerͼƬ
 * @param text ��ʾmarkͼƬ
 * @param wktPointShape ����markerWktPoint
 * �����������������û��Զ�ʱ����
 * @param labelText ��ǩ�ı�
 * @param markerText ������ʾ����
 */
function generateMarker(userDefined, markerImgUrl, text, wktPointShape,
		labelText, markerText) {
	var drawMarkerStyle = {};
	if(!wktPointShape){
		return null;
	}
	var defaultStyle = DMap.Overlay.createByWKT(wktPointShape, {
		type : 0,
		text : markerText,
		url : markerImgUrl,
		size : new DMap.Size(32, 32)
	});
	var userDefinedStyle = DMap.Overlay.createByWKT(wktPointShape, {
		type : 3,
		labelBorderColor : "#0a7e7e",
		labelColor : "#4486c7",
		labelBackgroundColor : "#f4f4f4",
		size : new DMap.Size(26, 26),
		labelText : labelText,
		text : text,
		markText : markerText,
		url : markerImgUrl
	});
	if (userDefined) {
		drawMarkerStyle = userDefinedStyle;
	} else {
		drawMarkerStyle = defaultStyle;
	}
	return drawMarkerStyle;
}

/**
 * ���ƿ���Markers����ͼ��
 * @param data
 * @param useDefinedMarker
 * @param markerImgUrl
 * @param parameters
 */
function drawTollgateMarkerToMap(data,useDefinedMarker,markerImgUrl,parameters){
	AppUtils.DataGrid.removeAllMarks();
	pathCoordinates=[];//���һ�λ��ƹ켣��������
	//��õ�ǰmarkers�߽緶Χ  @pengx ���ӱ߽綨λ
	var minLon=0,maxLon=0,minLat=0,maxLat=0;	
	for(var i=0;i<data.length;i++){
			var kkjd=data[i].KKJD;
			var kkwd=data[i].KKWD;
		    
			var shape =null;	 
			 var coordinates="";
			if((kkjd!=""&&kkwd!="")&&(kkjd!=null&&kkwd!=null)){
				shape = "POINT("+kkjd+" "+kkwd+")";	
				coordinates=kkjd+" "+kkwd;
				//��ȡ��λ�߽緶Χ
				if(i==1){
				     minLon = kkjd;
				     maxLon = kkjd;
				     minLat = kkwd;
				     maxLat = kkwd;
				}				    
					//�����Сֵ            
			     if(minLon>kkjd){
			         minLon = kkjd;
			     }	            	
			     if(maxLon<kkjd){
			         maxLon = kkjd;
			     }	            	
			     if(minLat>kkwd){
			         minLat = kkwd;
			     }	            	
			     if(maxLat<kkwd){
			         maxLat = kkwd;
			     }	
			}	
		   var kkjc=data[i].KKJC;//���ڼ��
		   var kkmc=data[i].KKJC;//��������
		   var kklx=data[i]["kklx"]?data[i]["kklx"]:data[i]["KKLX"];//��������
			var kkgzzt=data[i]["KKGZZT"]?data[i]["KKGZZT"]:data[i]["kkgzzt"];//���ڹ���״̬
			var markImg=null;
			if(kkgzzt){
				markImg=AppUtils.getGzztMarkImg(kklx,kkgzzt);
			}else{
				markImg=AppUtils.getKKLXMarkImg(kklx);
			}
			if(coordinates!=""){
				pathCoordinates.push(coordinates);	
			}	
		   //�Ե�ǰmarker���и��Ի���ַ����
			var mark=null;
			if(shape!=null){
				if(useDefinedMarker){
					var gcsj=data[i].GCSJ;//����ʱ��
					var labelText=kkjc + "����ʱ�䣺" + gcsj;
					var markerText=i+1;
					//ImgUrl=modelPath + "js/third_libs/sketchmap/img/round.png";
					mark=generateMarker(useDefinedMarker, markerImgUrl, kkmc, shape,
							labelText, markerText);
				}else{
					mark=generateMarker(useDefinedMarker, markImg, kkmc, shape,
							"", "");
				}	
			}			
		   if(mark!=null){
			   map.addOverlay(mark);
			   AppUtils.DataGrid.allMarks.push(mark);		  
			   var lonlat=mark.getLonlat();
				 mark.setCommonEvent();
				 mark.point=lonlat;
				 mark.name=name;
				 mark.datas=data[i];
				 DMap.$(mark).bind("click",function(e){
					   var me=this;
					 	if(me.infor){
					 		map.openInfoWindowHtml(me.point, me.infor);
					 		return;
					 	}
				      $.ajax({
				    	  url:basePath + "alarmOrTrackPub.gis?method=getCDXX",
						  data:{kkbh:me.datas.KKBH},
						  dataType:'text',
						  success: function(data) {
							  var roads=eval(data);
							  me.datas["roads"]=roads;
							  var infor=null;
							  if(parameters["createMarkTip"]){	
								  infor=parameters["createMarkTip"](me.point,me.datas,markerLabelPamaters.markLabels);
							  }else{
								  infor=AppUtils.DataGrid.createMarkTip(me.point,me.datas,markerLabelPamaters.markLabels);
							  }
							  me.infor=infor;
							  map.openInfoWindowHtml(me.point, infor);
						  }
					});
			    });
		   }	
	}
	if(minLon>0&&maxLon>0&&minLat>0&&maxLat>0){
	    var minx = parseFloat(minLon);
        var miny = parseFloat(minLat);
        var maxx = parseFloat(maxLon);            
        var maxy = parseFloat(maxLat);
	    map.centerAtMBR(minx,miny,maxx,maxy);
	}else{
		if(maxLon>0&&maxLat>0){
			var singlePoint=new DMap.LonLat(maxLon,maxLat);
			map.setCenter(singlePoint);
		}			
	}
}


/**
 * ǰ�˷�ҳ����
 * @param data
 * @returns {___anonymous4154_4198}
 */
function pagerFilter(data){
	if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
		data = {
			total: data.length,
			rows: data
		};
	}
	var dg = $(this);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	pager.pagination({
		onSelectPage:function(pageNum, pageSize){
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh',{
				pageNumber:pageNum,
				pageSize:pageSize
			});
			dg.datagrid('loadData',data);
		}
	});
	if (!data.originalRows){
		data.originalRows = (data.rows);
	}
	var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	data.rows = (data.originalRows.slice(start, end));
	return data;
}
/**
 * �ڿͻ�����ʾ����
 * @param gridContainer grid����
 * @param headColumns ��ͷ��
 * @param gridWidth grid���
 * @param gridHeight grid�߶ȳԷ�
 * @param reqResultData ����������
 */
function dataGridClientShowData(gridContainer,headColumns,gridWidth,gridHeight,reqResultData){
	if(!$("#dg").get(0)){
		$("<div>").attr("id","dg").appendTo(gridContainer).css({"width":gridWidth,"height":gridHeight});
	}
	var content=$("#dg");
	//�˴���Ҫʹ��Client����Grid���ݵ���Ⱦ
	$(content).datagrid({
		pageSize: 15,
		fix : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		width:gridWidth,
		height:gridHeight,
		columns:headColumns,			
		pageList:[10,15,20],
		loadFilter:pagerFilter,
		data: reqResultData,
		rowStyler: function(index,rowData){
		    var kkjd=rowData["KKJD"]||rowData["kkjd"];
			var kkwd=rowData["KKWD"]||rowData["kkwd"];
			if(!kkjd||!kkwd){
				return 'color:red;';
			}
		},		
		onClickRow:function(rowIndex,rowData){
			//ͳһ����grid��֮�еĵ������
			gridRowClickFun(rowIndex, rowData);
		}
	});
	var pager=content.datagrid("getPager");
	$(pager).pagination({
		showRefresh:false,
		showPageList:false,
	    afterPageText: "ҳ �� {pages} ҳ",         
	    displayMsg: "�� {total} ��",
	    buttons: [{
			iconCls:'icon-add',
			title:"��ǰҳ����",
			handler:function(){
				AppUtils.DataGrid.autoZoom($("#dg"));
			}
		}]
	});
}


/**
 * ���ƹ����켣��̬��ʾ�����켣��·
 *@param mapObj
 *@param linePoints
 */
function drawDynamicMarkerPath(mapObj,linePoints) {
	//��1������Linestring
	var lineString=linePoints.join(",");
	var lineWKT="LINESTRING("+lineString+")";
	 var line=DMap.Overlay.createByWKT(lineWKT,{color: 'blue',endarrow: "Classic", weight: 5});
	 mapObj.addOverlay(line);
	// �����ƶ�Ч��
	var moveLonLats = line.getLonLats();
	if (moveLonLats.length > 0) {
		var moveMarker = new DMap.MoveMarker(moveLonLats[0], {
			url : modelPath + "images/gjfx/vehicle_gray.png"
		});
		moveMarker.setRepeat(true);// �Ƿ��ظ�
		mapObj.addOverlay(moveMarker);
		moveMarker.startMove(moveLonLats);
	}
}

/**
 * ���ƻ�����·��
 */
function drawBufferRoute(data){
	if(currentBufferRoute!=null){
		map.removeOverlay(currentBufferRoute);
	}
	if(currentLineRoute!=null){
		map.removeOverlay(currentLineRoute);
	}
	var lonlatArray=[];
	for(var i=0;i<data.length;i++){
		var kkjd=data[i].KKJD;
		var kkwd=data[i].KKWD;
		lonlatArray.push(kkjd+" "+kkwd);
	}				
	var wktString="LINESTRING("+lonlatArray.join(",")+")";
	currentLineRoute=DMap.Overlay.createByWKT(wktString,{color:"#ffc345"});
	map.addOverlay(currentLineRoute);
	currentBufferRoute=createBufferOverlay(wktString,2500);
	map.addOverlay(currentBufferRoute);
}

/**
 * �Ƴ�����Ԫ��ָ�����±�Ԫ�� ����ʣ������Ԫ��
 * @param arrayObject
 * @param index
 * @returns
 */
function removeElementInArray(arrayObject,index){
	for(var i=0;i<arrayObject.length;i++){
		if(i==index){
			arrayObject.splice(i,1);
		}
	}
	return arrayObject;
}
/**
 * �Ƴ���ǰ������к͵�ͼ��Ӧ��marker
 * @param aobj
 */
function removeRowAndMarker(aobj){		
	var rowIndex=$(aobj).parents("tr:first").index();	
	//��1���Ƴ���ǰ��	
	$("#dg").datagrid('deleteRow', rowIndex);
	//�ӿͻ���Ԫ��֮��ɾ����ǰԪ��
	curentGridData=removeElementInArray(curentGridData, rowIndex);	
	//���Կ���ֱ�Ӵӵ�ͼ���Ƴ���ǰMarker
	removeGridRowAfterMarkerFromMap(rowIndex);
	//�Ƴ�����ȾGrid���
	removeMarkerRenderGrid(curentGridData);
	//��3������ȫ�ֱ������ж��Ƿ��Ƴ�ҳ���ϵ�����Ԫ�� ��ȷ������Path	
	if(displayParameters.isDrawPath){
		drawDynamicMarkerPath(map,pathCoordinates);
	}
	//���ƹ켣·���Ļ�����
	if(displayParameters.bufferRoute){	
		drawBufferRoute(curentGridData);
	}
}

/**
 * ����Ƴ���������ڿͻ�����ȾGrid
 * @param curentGridData 
 */
function removeMarkerRenderGrid(curentGridData){
	//����Grid�����Ի�����	
	var baseContent = slidebarControl.getContentDiv();
		$(baseContent).empty();
		slidebarControl.slideOut();
	var gridContainer = $(baseContent);
	var headColumns=gridFixedParamters.headColumns;
	var gridWidth=gridFixedParamters.gridWidth;
	var gridHeight=gridFixedParamters.gridHeight;
    dataGridClientShowData(gridContainer,headColumns,gridWidth,gridHeight,curentGridData);
}

/**
 * ��grid֮���Ƴ������ݺ� ֱ�Ӵӵ�ͼ���Ƴ���ǰMarker ����ȫ���������
 * @param removeRowIndex
 */
function removeGridRowAfterMarkerFromMap(removeRowIndex) {
for (var i = 0; i < AppUtils.DataGrid.allMarks.length; i++) {
	if (removeRowIndex == i) {
		map.removeOverlay(AppUtils.DataGrid.allMarks[removeRowIndex]);
		AppUtils.DataGrid.allMarks.splice(removeRowIndex, 1);
	}
}
}

/**
 * ��֤���������Ƿ�����
 * @returns {Boolean}
 */
function verifyCondition() {
var verifyResult = true;
var carLicenses = $("#query_car_licenses").val();
var carLicenseType = $("#hpzl_select").combobox('getValue');
var startDate = $("#query_start_date").datetimebox('getValue');
var endDate = $("#query_end_date").datetimebox('getValue');
switch (true) {
case carLicenseType == "no":
	$.messager.alert("��ʾ��Ϣ", "��ѯ�����к������಻��Ϊ�գ�", "info");
	verifyResult = false;
	break;
case carLicenses == "":
	$.messager.alert("��ʾ��Ϣ", "��ѯ�����к��ƺ��벻��Ϊ�գ�", "info");
	verifyResult = false;
	break;
case startDate == "":
	$.messager.alert("��ʾ��Ϣ", "��ѯ�����п�ʼ���ڲ���Ϊ�գ�", "info");
	verifyResult = false;
	break;
case endDate == "":
	$.messager.alert("��ʾ��Ϣ", "��ѯ�����н������ڲ���Ϊ�գ�", "info");
	verifyResult = false;
	break;
}
return verifyResult;
}

/**
 * ִ�з������Ҹ��ݲ�ͬ������ʾ��ͬ�Ľ��
 * @param analysisType
 */
function execAnalysisAndDisplay(analysisType){
	//��1����֤���������Ƿ���ȷ
	var verifyResult=verifyCondition();
	if(!verifyResult){
		return;
	}
	map.clearOverlays();
	if(legendControl!=null){
		legendControl.hide();
	}
	if($("#passPicDiv").get(0)){
		$("#passPicDiv").remove();
	}	
	if (themeLayer != null) {
		map.removeOverlay(themeLayer);
	}	
	displayParameters.isDrawPath=false;
	displayParameters.isImageGallery=false;
	displayParameters.bufferRoute=false;
	setAllButtonsDisabled();//��ִ�й����������а�ťΪ���ɵ��
	switch (analysisType) {
	case "track":// �����켣����
		displayParameters.isDrawPath=true;
		displayParameters.isImageGallery=true;
		//��1����ʾͨ�������Grid�͵�ͼ��
		getVehiclePassTrackResult(analysisType);	
	break;
	case "pass_frequency":// �������ڵ�Ƶ��	
		//��1����ʾͨ�������Grid�͵�ͼ��
		getVehiclePassTrackResult(analysisType);
		break;
	case "live_area":// �������� ���������̨����data���ݷ�ʽ
		var out_condition = getVehicleParameters();
		var filter_condition = ObjectToStr(out_condition);
		addLiveAreaHotspot(filter_condition);
		break;
	case "live_route":// �·�߷���
		displayParameters.bufferRoute=true;
		//��1����ʾͨ�������Grid�͵�ͼ��
		getVehiclePassTrackResult(analysisType);
		break;
	}	
}

/**
 * ��ʾͼƬ����ǽ 
 * @param jqDivObj div jquery����
 * @param imgPathArray ͼƬ·������
 * @param imgTitleArray ͼƬtitle
 * @param imgDescArray ͼƬ����
 */
function displayImageGallery(jqDivObj,imgPathArray,imgTitleArray,imgDescArray){	
	var passPictures=$("<div></div>").attr("id","passPicDiv").css({width:"300",height:"260"});			
	passPictures.appendTo(jqDivObj);							
	$().prettyPhoto({
			imgId:"passPicDiv",
			theme: 'light_square',
			slideshow:3000, 
			autoplay_slideshow:true,
			social_tools:""
	});		
	$.prettyPhoto.open(imgPathArray,imgTitleArray,imgDescArray);
}

/**
 * ��ʾÿҳGrid֮�а�����ͼƬ��Ϣ
 * @param imagesContainer ���ͼƬ����
 * @param imgGCXHArray ��Ź���ͼƬ�Ĺ������
 */
function showGridEveryPagePassImages(imagesContainer,imgGCXHArray){	
	//��1��ѭ����õ�ǰͼƬ
	var passImagesUrl=[];
	var imagesTitles=[];
	var imagesDesc=[];
	for(var i=0;i<imgGCXHArray.length;i++){
		var imageUrl=passPicturesBaseUrl+imgGCXHArray[i]+"&fhk=1&tpxh=1";
		passImagesUrl.push(imageUrl);
	}
	/**
	var imgPath=modelPath+"js/third_libs/prettyPhoto/images/fullscreen/";
	var api_images = [imgPath+'4.jpg',imgPath+'5.jpg',imgPath+'6.jpg'];
	var api_titles = ['Title 1','Title 2','Title 3'];
	var api_descriptions = ['Description 1','Description 2','Description 3'];
	*/
	if(imgGCXHArray.length>0){
		displayImageGallery(imagesContainer, passImagesUrl, imagesTitles, imagesDesc);
	}	
	//displayImageGallery(imagesContainer, api_images, api_titles, api_descriptions);
}

var bufferOverlay=null;
function createBufferOverlay(wktString,meter){
	var degree=DMap.Util.meterToDegree(meter);
	baseGeometry=new DMap.Geometry(wktString);
    var bufferWKT=baseGeometry.buffer(degree).toString();
	bufferOverlay=DMap.Overlay.createByWKT(bufferWKT,{color:"#9fbf3e"});
	return bufferOverlay;
}

/**
 * ����ض���markers����
 * @param data
 * @param parameters
 */
function addMarkers(data,parameters){
	AppUtils.DataGrid.removeAllMarks();
	pathCoordinates=[];//���һ�λ��ƹ켣��������	
	for(var i=0;i<data.length;i++){
			var kkjd=data[i].KKJD;
			var kkwd=data[i].KKWD;
			var shape = null;
			var coordinates="";
		   if(kkjd!=null&&kkwd!=null){
			   shape = "POINT("+kkjd+" "+kkwd+")";	 
			   coordinates=kkjd+" "+kkwd;	
		   }
		   var kkjc=data[i].KKJC;//���ڼ��
		   var kkmc=data[i].KKJC;//��������
		   var kklx=data[i]["kklx"]?data[i]["kklx"]:data[i]["KKLX"];//��������
			var kkgzzt=data[i]["KKGZZT"]?data[i]["KKGZZT"]:data[i]["kkgzzt"];//���ڹ���״̬
			var markImg=null;
			if(kkgzzt){
				markImg=AppUtils.getGzztMarkImg(kklx,kkgzzt);
			}else{
				markImg=AppUtils.getKKLXMarkImg(kklx);
			}
			if(parameters&&parameters["draw_path"]){
				if(coordinates!=""){
					pathCoordinates.push(coordinates);
				}				
			}			
		   //�Ե�ǰmarker���и��Ի���ַ����
			var mark=null;		
		   if(parameters&&parameters["special_marker"]){
			   var ImgUrl=modelPath + "js/third_libs/sketchmap/img/round.png";
			   var gcsj=data[i].GCSJ;//����ʱ��
			   if(shape!=null){
				   mark=DMap.Overlay.createByWKT(shape, {
						type : 3,
						labelBorderColor : "#0a7e7e",
						labelColor : "#4486c7",
						labelBackgroundColor : "#f4f4f4",
						size : new DMap.Size(26,26),
						labelText : kkjc + "����ʱ�䣺" + gcsj,
						text : kkmc,
						markText : (i + 1),
						//markTextColor:"#e95045",					
						url : ImgUrl
					});	
			   }					   
		   }else{	
			   if(shape!=null){
				   mark=DMap.Overlay.createByWKT(shape,{type:0,text:kkjc,url:markImg,size:new DMap.Size(32,32)});
			   }
		   }
		   var lonlat="";
		   if(mark){
			   map.addOverlay(mark);
			   AppUtils.DataGrid.allMarks.push(mark);	
			   lonlat=mark.getLonlat();
				 mark.setCommonEvent();
				 mark.point=lonlat;
				 mark.name=name;
				 mark.datas=data[i];
				 DMap.$(mark).bind("click",function(e){
					   var me=this;
					 	if(me.infor){
					 		map.openInfoWindowHtml(me.point, me.infor);
					 		return;
					 	}
				      $.ajax({
						  url:basePath + "alarmOrTrackPub.gis?method=getCDXX",
						  data:{kkbh:me.datas.KKBH},
						  dataType:'text',
						  success: function(data) {
							  var roads=eval(data);
							  me.datas["roads"]=roads;
							  var infor=null;
							  if(parameters["createMarkTip"]){
								  infor=parameters["createMarkTip"](me.point,me.datas,parameters.markLabels);
							  }else{		
								  infor=AppUtils.DataGrid.createMarkTip(me.point,me.datas,parameters.markLabels);
							  }
							  me.infor=infor;
							  map.openInfoWindowHtml(me.point, infor);
						  }
					});
			    });
		   } 
	}
}

var themeLayer=null;
/**
 * ��ʾ������������
 * @param params
 */
function addLiveAreaHotspot(params){
	var baseContent= slidebarControl.getContentDiv();
	$(baseContent).empty();
	slidebarControl.slideOut();	
	var width=$(baseContent).width();
	var height=$(baseContent).height();
	var isFirst = true;
	if (themeLayer != null) {
		map.removeOverlay(themeLayer);
	}
	var implclass = "LiveAreaHotspotDataImpl";//VehicleLiveAreaHotspotDataImpl
	themeLayer=new DMap.ThemeLayer({
		//imageUrl:basePath+"TGISTheme.do",
		imageUrl:basePath+"gisTheme.gis?method=getHotSpotTheme",
		data:{
			THEMETYPE:1000015,
			implclass:implclass,
			analysis_condition:params
		},
		callBack:function(callBackData){
			if (isFirst) {
				var resultRowsJSON = $.evalJSON(callBackData);						
				// �ѽ�����õ��ұߵ�Grid֮��
				isFirst = false;
				// ��2����ʾ������ұߵ�DataGrid֮��
				// ��ʾ����������datagridͷ
				var liveAreaColumns = [ [ {
					field : 'KKLX',
					title : '��������',
					align : "center",
					formatter : AppUtils.DataGrid.kklxFormatter
				}, {
					field : 'KKMC',
					title : '��������'
				}, {
					field : 'KKBH',
					title : '����ʱ��'
				}, {
					field : 'KKJC',
					title : '��������'
				} ] ];
				AppUtils.DataGrid.init($(baseContent),{
					columns : liveAreaColumns,															
					data:resultRowsJSON,
					width:width,
					height:height,
					onClickRow:function(rowIndex,rowData){								
						//ͳһ����grid��֮�еĵ������
						gridRowClickFun(rowIndex, rowData);
					}
				});
				addMarkers(resultRowsJSON.rows, markerLabelPamaters);
				setAllButtonsEnable();//��ִ�й����������а�ťΪ�ɵ��
			}
		}
	});
	map.addOverlay(themeLayer);
} 

/**
 * ͳһ����grid�����¼�����
 * @param rowIndex
 * @param rowData
 */
function gridRowClickFun(rowIndex, rowData){
	var mark=DMap.Overlay.createByWKT("POINT("+rowData["KKJD"]+" "+rowData["KKWD"]+")");
	map.setCenter(mark.getLonlat());
	for(var i=0;i<AppUtils.DataGrid.allMarks.length;i++){
		if(mark.getLonlat().equals(AppUtils.DataGrid.allMarks[i].getLonlat())){
			if(AppUtils.DataGrid.curFlashMark!=null){
				AppUtils.DataGrid.curFlashMark.clearFlash();
				AppUtils.DataGrid.curFlashMark=null;
			}
			AppUtils.DataGrid.curFlashMark=AppUtils.DataGrid.allMarks[i];
			AppUtils.DataGrid.curFlashMark.flash();
			window.setTimeout(function(){
				if(AppUtils.DataGrid.curFlashMark!=null){
					AppUtils.DataGrid.curFlashMark.clearFlash();
					AppUtils.DataGrid.curFlashMark=null;
				}
			},5000);
		}
	}
}

/**
 * ������֤���Ƿ���ȷȷ���Ƿ�ִ�ж�Ӧ�ķ���
 * @param analysisType
 */
function vehicleQueryAnalysis(analysisType){	
	execAnalysisAndDisplay(analysisType);
}

//===========================�켣��ѯ�������ʾ����========================================
