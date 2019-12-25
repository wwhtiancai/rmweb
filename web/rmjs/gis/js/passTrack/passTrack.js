var passPicturesBaseUrl=basePath+"readPassPic.tfc?method=getPassPic&gcxh=";
//var passPicturesBaseUrl="http://192.168.0.221:8083/rmweb/readPassPic.tfc?method=getPassPic&gcxh=";

//========================页面初始化操作以及公共模块开始========================================
var pathCoordinates=[];//存放轨迹的坐标对

var curFlashMark=null;//记录当前闪烁的卡口

var currentBufferRoute=null;//当前绘制的缓冲区路径
var currentLineRoute=null;//已经绘制路线
var displayParameters={
	isDrawPath:false,//标识是否绘制轨迹路径
	isImageGallery:false,//是否图片显示控件
	bufferRoute:false//活动路线分析
};

var legendControl=null;
var tollgateRouteLines=[];

/*
 * 气泡中需要显示的字段配置
 */
 var labelShowFields={
	 KKMC:"卡口名称",
	 BMMC:"管理部门",
	 KKLX:"卡口类型"	 
};
//设置显示Marker之中气泡基本信息
var markerLabelPamaters = {
	markLabels : [// 设置气泡显示内容
	{
		field : 'KKMC',
		title : '卡口名称'
	}, {
		field : 'KKLX',
		title : '类型',
		formatter : function(val) {
			return AppUtils.getDicText("kklx", val);
		}
	}, {
		field : 'GLBM',
		title : '管理部门',
		formatter : function(val) {
			return AppUtils.getDicText("glbm", val);
		}
	} ]
};
/**
 * 显示Grid所需的固定参数
 */
var gridFixedParamters={
	gridContainer:"",//显示Grid的容器对象
	headColumns:[],//Grid显示头部
	gridWidth:"",//Grid宽度
	gridHeight:""//Grid高度
};

var markerFixedParamters={
	customStyle:false,//用户是否自定义样式
	markerImgUrl:"",//制定Marker图标Url
	markerLabels:null//设置Marker显示气泡标签
};

//记录当前Grid之中包含的数据 主要用于记录Grid之中数据
var curentGridData=[];

 /**
	 * 设置右边Grid需要显示的参数
	 */
 var displayGridSetting={
	head_columns:null,
	width:"",
	height:""   
 };
 
 /**
  * 分析所需的全部参数
  */
 var analysisParamter={
 	filter_condition:"",//外部传入的条件 json对象
 	request_url:"",//请求的url
 	grid_head_columns:null,//grid列头内容 数组
 	marker_tips:null,//marker提示内容 数组
 	marker_style:null,//marker样式 json对象
 	grid_width:"",//grid显示宽度
 	grid_height:""//grid显示的宽度
 };
/**
 * 周边查询图层配置
 */
var aroundLayers=[
	 {text:"视频",value:"epgis_vw_dev_tollgate",img:modelPath+"images/common/video.png",imgsize:{width:32,width:32}},
	 {text:"警力",value:"epgis_vw_dev_tollgate",img:"",imgsize:{}}
];


/**
 * 周边查询F
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
		    	  if(data.Result=='Error'){//出错了
		    			//alert(data.Msg);//数据访问更新服务返回的错误信息
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
	//【1】填充号牌种类到页面之中
	fillHPZLToPage("hpzl_select",hpzlList);	
	//【2】填充签收结果到页面之中
	fillCXYYToPage("cxyy_select", cxyyList);
	var curDate=new Date().Format("yyyy-MM-dd hh:mm");
	var curDateTime=new Date();
	var tempDate= new Date();
	var startDateTime=curDateTime.getTime() - 24*60*60*1000*7; 
	tempDate.setTime(startDateTime);
	var startDate=tempDate.Format("yyyy-MM-dd hh:mm");	
	//设置开始时间为当前时间前一周
	$('#query_start_date').datetimebox({showSeconds:false});
	$('#query_start_date').datetimebox('setValue', getLastWeek()+" 00:00");	
	//设置结束时间为当前时间
	$('#query_end_date').datetimebox({showSeconds:false});
	$('#query_end_date').datetimebox('setValue', curDate);	
}


//打开卡口的放大图片
function openBigPic(objImg){
	//alert(objImg.baseUrl);
	var maxImgControl=new MaxImgControl({imgUrl:objImg.baseUrl});
	maxImgControl.show();
}

//设置设置所用按钮为disabled
function setAllButtonsDisabled(){
	$("#gjcxbtn").attr("disabled",true);
	$("#hdqybtn").attr("disabled",true);
	$("#hdlxbtn").attr("disabled",true);
	$("#crkkbtn").attr("disabled",true);
}

//设置所有按钮为enabled
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
 * 获得车辆轨迹查询属性条件
 */
function getVehicleParameters(){
	var analysisCondition={
		"query_car_license":"",//车牌号
		"query_license_type":"",//车牌种类
		"query_start_date":"",//查询开始日期
		"query_end_date":"",//查询结束日期
		"query_reason":"",//查询原因
		"query_district":""//查询限制区域			
	};
	//从页面之中获得轨迹分析查询条件
	//【1】获得属性查询条件
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
	//【2】获得空间查询条件
	return analysisCondition;
}

//===========================页面初始化操作以及公共模块结束========================================


//===========================轨迹查询及结果展示开始========================================
/**
 * @param analysisType 根据传入类型设置显示grid参数
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
	  					title : '卡口类型',
	  					align : "center",
	  					formatter : AppUtils.DataGrid.kklxFormatter
	  				},
	  				{
	  					field : "KKJC",
	  					title : "卡口简称"
	  				},
	  				{
	  					field : "DELBTN",
	  					title : "是否移除",
	  					align : "center",
	  					formatter : function() {
	  						return "<a href='javascript:void(0)' class='easyui-linkbutton' onclick='removeRowAndMarker(this)'>移除</a>";
	  					}
	  				}, {
	  					field : "GCSJ",
	  					title : "过车时间",
	  					align : "center"
	  				}, {
	  					field : "CLLX",
	  					title : "车辆类型",
	  					align : "center"
	  				}, {
	  					field : "CLSD",
	  					title : "行车速度",
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
					title : '卡口类型',
					align : "center",
					formatter : AppUtils.DataGrid.kklxFormatter
				},
				{
					field : 'KKJC',
					title : '卡口简称'
				},
				{
					field : 'PASS_TOTALS',
					title : '经过次数'
				},
				{
					field : 'DELBTN',
					title : '是否移除',
					formatter : function() {
						return "<a href='javascript:void(0)' class='easyui-linkbutton' onclick='removeRowAndMarker(this)'>移除</a>";
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
	// 获得显示结果得div对象
	var baseContent = slidebarControl.getContentDiv();
	$(baseContent).empty();
	var gridContainer = $(baseContent);
	var tempWidth = $(baseContent).width();
	var tempHeight = $(baseContent).height();
	//设置高度
    if(grid_height==""){
    	grid_height=tempHeight;
    }
    //设置宽度
    if(grid_width==""){
    	grid_width=tempWidth;
    }
	displayGridSetting.head_columns =head_columns;
	displayGridSetting.height=grid_height;
	//设置Grid所个性化参数		
	gridFixedParamters.gridContainer=gridContainer;//获得显示Grid使用容器
	gridFixedParamters.gridHeight=grid_height;
	gridFixedParamters.gridWidth=grid_width;
	gridFixedParamters.headColumns=head_columns;
	//设置Marker个性化参数
	markerFixedParamters.customStyle=customStyle;
	markerFixedParamters.markerImgUrl="";
	markerFixedParamters.markerLabels=markerLabelPamaters;
}

/**
 *把查询结果用客户端分页显示
 * @param analysisType
 */
function getVehiclePassTrackResult(analysisType) {
	// 【1】获得请求基本地址
	var reqUrl = basePath + "passTrack.gis?method=";
	var useDefinedMarker=false;
	var markerImgUrl="";
	switch (analysisType) {
	case "track":// 轨迹查询结果
		reqUrl += "getTrackPathQuery";
		useDefinedMarker=true;
		markerImgUrl=modelPath + "js/third_libs/sketchmap/img/round.png";
		break;
	case "pass_frequency":// 经常出入卡口分析结果
		reqUrl += "passTollgateFrequency";
		break;
	case "live_route":// 车辆活动路线分析结果
		reqUrl += "getTrackPathQuery";
		break;	
	}
	// 【2】获得分析所需的参数
	var out_condition = getVehicleParameters();
	// 把外部分析条件转换为json字符串
	var filter_condition = ObjectToStr(out_condition);
	var reqParams = {
		"analysis_condition" : filter_condition
	};
	// 【3】发送Ajax请求获得返回结果
	$.ajax({
		url : reqUrl,
		data : reqParams,
		dataType : 'text',
		success : function(data) {
			var resultJSONData = $.evalJSON(data);			
			// 获得显示结果得div对象
			var baseContent = slidebarControl.getContentDiv();
			$(baseContent).empty();
			slidebarControl.slideOut();
			var gridContainer = $(baseContent);
			if(resultJSONData.total<=0){
				dataGridClientShowData(gridContainer, headColumns, gridWidth,
						gridHeight, resultJSONData);
				setAllButtonsEnable();//在执行过程设置所有按钮为可点击	
				$.messager.alert("提示消息", "无过车记录！", "info");
				return;
			}
			//每次请求完毕之后把数据存放在客户端数组
			curentGridData=[];
			curentGridData=resultJSONData.rows;	
			// 获得显示Grid所需的基本参数 headColumns 以及长度与宽度
			setGridAndMarkerParameters(analysisType);
			var gridWidth = $(baseContent).width();
			var gridHeight = $(baseContent).height();
			// 获得分析结果之中grid 宽度和高度
			if (displayGridSetting.width != "") {
				gridWidth = displayGridSetting.width;
			}
			if (displayGridSetting.height != "") {
				gridHeight = displayGridSetting.height;
			}
			var headColumns = displayGridSetting.head_columns;	
			// 【3.1】显示结果到DataGrid之中
			dataGridClientShowData(gridContainer, headColumns, gridWidth,
					gridHeight, resultJSONData);
			setAllButtonsEnable();//在执行过程设置所有按钮为可点击
			// 【3.2】绘制Markers及其他要素到地图页面		
			//根据不同绘制类型传入不同Marker样式
			drawTollgateMarkerToMap(resultJSONData.rows, useDefinedMarker, markerImgUrl,markerLabelPamaters);			
			//【3.3】绘制过车动画到地图上
			if(displayParameters.isDrawPath&&pathCoordinates.length>0){
				drawDynamicMarkerPath(map,pathCoordinates);
			}else{
				$.messager.alert("提示消息", "过车卡口为坐标无法显示轨迹！", "info");
			}
			//是否显示过车图片
			if(displayParameters.isImageGallery){			
				var imgGCXHArray=[];
				var gridPageData=resultJSONData.rows;
				for(var m=0;m<gridPageData.length;m++){
					var gcxh=gridPageData[m].GCXH;
					imgGCXHArray.push(gcxh);
				}
				showGridEveryPagePassImages($(baseContent),imgGCXHArray);
			}
			//是否显示缓冲路径
			if(displayParameters.bufferRoute){
				drawBufferRoute(curentGridData);
			}			
		}
	});
}

/**
 * 生成绘制Marker函数
 * @param userDefined 是否用户自定义
 * @param markerImgUrl marker图片
 * @param text 显示mark图片
 * @param wktPointShape 绘制markerWktPoint
 * 下面两个参数是在用户自定时候用
 * @param labelText 标签文本
 * @param markerText 气泡提示文字
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
 * 绘制卡口Markers到地图上
 * @param data
 * @param useDefinedMarker
 * @param markerImgUrl
 * @param parameters
 */
function drawTollgateMarkerToMap(data,useDefinedMarker,markerImgUrl,parameters){
	AppUtils.DataGrid.removeAllMarks();
	pathCoordinates=[];//清空一次绘制轨迹坐标内容
	//获得当前markers边界范围  @pengx 增加边界定位
	var minLon=0,maxLon=0,minLat=0,maxLat=0;	
	for(var i=0;i<data.length;i++){
			var kkjd=data[i].KKJD;
			var kkwd=data[i].KKWD;
		    
			var shape =null;	 
			 var coordinates="";
			if((kkjd!=""&&kkwd!="")&&(kkjd!=null&&kkwd!=null)){
				shape = "POINT("+kkjd+" "+kkwd+")";	
				coordinates=kkjd+" "+kkwd;
				//获取定位边界范围
				if(i==1){
				     minLon = kkjd;
				     maxLon = kkjd;
				     minLat = kkwd;
				     maxLat = kkwd;
				}				    
					//最大最小值            
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
		   var kkjc=data[i].KKJC;//卡口简称
		   var kkmc=data[i].KKJC;//卡口名称
		   var kklx=data[i]["kklx"]?data[i]["kklx"]:data[i]["KKLX"];//卡口类型
			var kkgzzt=data[i]["KKGZZT"]?data[i]["KKGZZT"]:data[i]["kkgzzt"];//卡口工作状态
			var markImg=null;
			if(kkgzzt){
				markImg=AppUtils.getGzztMarkImg(kklx,kkgzzt);
			}else{
				markImg=AppUtils.getKKLXMarkImg(kklx);
			}
			if(coordinates!=""){
				pathCoordinates.push(coordinates);	
			}	
		   //对当前marker进行个性化地址内容
			var mark=null;
			if(shape!=null){
				if(useDefinedMarker){
					var gcsj=data[i].GCSJ;//过车时间
					var labelText=kkjc + "过车时间：" + gcsj;
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
 * 前端分页过滤
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
 * 在客户端显示数据
 * @param gridContainer grid容器
 * @param headColumns 列头部
 * @param gridWidth grid宽度
 * @param gridHeight grid高度吃饭
 * @param reqResultData 请求结果数据
 */
function dataGridClientShowData(gridContainer,headColumns,gridWidth,gridHeight,reqResultData){
	if(!$("#dg").get(0)){
		$("<div>").attr("id","dg").appendTo(gridContainer).css({"width":gridWidth,"height":gridHeight});
	}
	var content=$("#dg");
	//此处主要使用Client进行Grid数据的渲染
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
			//统一调用grid行之中的点击函数
			gridRowClickFun(rowIndex, rowData);
		}
	});
	var pager=content.datagrid("getPager");
	$(pager).pagination({
		showRefresh:false,
		showPageList:false,
	    afterPageText: "页 共 {pages} 页",         
	    displayMsg: "共 {total} 条",
	    buttons: [{
			iconCls:'icon-add',
			title:"当前页居中",
			handler:function(){
				AppUtils.DataGrid.autoZoom($("#dg"));
			}
		}]
	});
}


/**
 * 绘制过车轨迹动态演示经过轨迹线路
 *@param mapObj
 *@param linePoints
 */
function drawDynamicMarkerPath(mapObj,linePoints) {
	//【1】创建Linestring
	var lineString=linePoints.join(",");
	var lineWKT="LINESTRING("+lineString+")";
	 var line=DMap.Overlay.createByWKT(lineWKT,{color: 'blue',endarrow: "Classic", weight: 5});
	 mapObj.addOverlay(line);
	// 创建移动效果
	var moveLonLats = line.getLonLats();
	if (moveLonLats.length > 0) {
		var moveMarker = new DMap.MoveMarker(moveLonLats[0], {
			url : modelPath + "images/gjfx/vehicle_gray.png"
		});
		moveMarker.setRepeat(true);// 是否重复
		mapObj.addOverlay(moveMarker);
		moveMarker.startMove(moveLonLats);
	}
}

/**
 * 绘制缓冲区路径
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
 * 移除数组元素指定的下标元素 返回剩下数组元素
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
 * 移除当前点击的行和地图对应的marker
 * @param aobj
 */
function removeRowAndMarker(aobj){		
	var rowIndex=$(aobj).parents("tr:first").index();	
	//【1】移除当前行	
	$("#dg").datagrid('deleteRow', rowIndex);
	//从客户端元素之中删除当前元素
	curentGridData=removeElementInArray(curentGridData, rowIndex);	
	//可以考虑直接从地图上移除当前Marker
	removeGridRowAfterMarkerFromMap(rowIndex);
	//移除后渲染Grid表格
	removeMarkerRenderGrid(curentGridData);
	//【3】根据全局变量来判断是否移除页面上的其他元素 以确定绘制Path	
	if(displayParameters.isDrawPath){
		drawDynamicMarkerPath(map,pathCoordinates);
	}
	//绘制轨迹路径的缓冲区
	if(displayParameters.bufferRoute){	
		drawBufferRoute(curentGridData);
	}
}

/**
 * 获得移除后的数据在客户端渲染Grid
 * @param curentGridData 
 */
function removeMarkerRenderGrid(curentGridData){
	//设置Grid所个性化参数	
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
 * 从grid之中移除行数据后 直接从地图上移除当前Marker 无需全部重新填充
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
 * 验证输入条件是否满足
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
	$.messager.alert("提示消息", "查询条件中号牌种类不能为空！", "info");
	verifyResult = false;
	break;
case carLicenses == "":
	$.messager.alert("提示消息", "查询条件中号牌号码不能为空！", "info");
	verifyResult = false;
	break;
case startDate == "":
	$.messager.alert("提示消息", "查询条件中开始日期不能为空！", "info");
	verifyResult = false;
	break;
case endDate == "":
	$.messager.alert("提示消息", "查询条件中结束日期不能为空！", "info");
	verifyResult = false;
	break;
}
return verifyResult;
}

/**
 * 执行分析并且根据不同类型显示不同的结果
 * @param analysisType
 */
function execAnalysisAndDisplay(analysisType){
	//【1】验证输入条件是否正确
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
	setAllButtonsDisabled();//在执行过程设置所有按钮为不可点击
	switch (analysisType) {
	case "track":// 过车轨迹分析
		displayParameters.isDrawPath=true;
		displayParameters.isImageGallery=true;
		//【1】显示通过结果到Grid和地图上
		getVehiclePassTrackResult(analysisType);	
	break;
	case "pass_frequency":// 经过卡口的频次	
		//【1】显示通过结果到Grid和地图上
		getVehiclePassTrackResult(analysisType);
		break;
	case "live_area":// 活动区域分析 独立请求后台返回data数据方式
		var out_condition = getVehicleParameters();
		var filter_condition = ObjectToStr(out_condition);
		addLiveAreaHotspot(filter_condition);
		break;
	case "live_route":// 活动路线分析
		displayParameters.bufferRoute=true;
		//【1】显示通过结果到Grid和地图上
		getVehiclePassTrackResult(analysisType);
		break;
	}	
}

/**
 * 显示图片滚动墙 
 * @param jqDivObj div jquery对象
 * @param imgPathArray 图片路径数组
 * @param imgTitleArray 图片title
 * @param imgDescArray 图片描述
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
 * 显示每页Grid之中包含的图片信息
 * @param imagesContainer 存放图片容器
 * @param imgGCXHArray 存放过车图片的过车序号
 */
function showGridEveryPagePassImages(imagesContainer,imgGCXHArray){	
	//【1】循环获得当前图片
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
 * 添加特定的markers函数
 * @param data
 * @param parameters
 */
function addMarkers(data,parameters){
	AppUtils.DataGrid.removeAllMarks();
	pathCoordinates=[];//清空一次绘制轨迹坐标内容	
	for(var i=0;i<data.length;i++){
			var kkjd=data[i].KKJD;
			var kkwd=data[i].KKWD;
			var shape = null;
			var coordinates="";
		   if(kkjd!=null&&kkwd!=null){
			   shape = "POINT("+kkjd+" "+kkwd+")";	 
			   coordinates=kkjd+" "+kkwd;	
		   }
		   var kkjc=data[i].KKJC;//卡口简称
		   var kkmc=data[i].KKJC;//卡口名称
		   var kklx=data[i]["kklx"]?data[i]["kklx"]:data[i]["KKLX"];//卡口类型
			var kkgzzt=data[i]["KKGZZT"]?data[i]["KKGZZT"]:data[i]["kkgzzt"];//卡口工作状态
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
		   //对当前marker进行个性化地址内容
			var mark=null;		
		   if(parameters&&parameters["special_marker"]){
			   var ImgUrl=modelPath + "js/third_libs/sketchmap/img/round.png";
			   var gcsj=data[i].GCSJ;//过车时间
			   if(shape!=null){
				   mark=DMap.Overlay.createByWKT(shape, {
						type : 3,
						labelBorderColor : "#0a7e7e",
						labelColor : "#4486c7",
						labelBackgroundColor : "#f4f4f4",
						size : new DMap.Size(26,26),
						labelText : kkjc + "过车时间：" + gcsj,
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
 * 显示车辆活动区域分析
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
				// 把结果放置到右边的Grid之中
				isFirst = false;
				// 【2】显示结果到右边的DataGrid之中
				// 显示活动区域分析的datagrid头
				var liveAreaColumns = [ [ {
					field : 'KKLX',
					title : '卡口类型',
					align : "center",
					formatter : AppUtils.DataGrid.kklxFormatter
				}, {
					field : 'KKMC',
					title : '卡口名称'
				}, {
					field : 'KKBH',
					title : '过车时间'
				}, {
					field : 'KKJC',
					title : '车辆类型'
				} ] ];
				AppUtils.DataGrid.init($(baseContent),{
					columns : liveAreaColumns,															
					data:resultRowsJSON,
					width:width,
					height:height,
					onClickRow:function(rowIndex,rowData){								
						//统一调用grid行之中的点击函数
						gridRowClickFun(rowIndex, rowData);
					}
				});
				addMarkers(resultRowsJSON.rows, markerLabelPamaters);
				setAllButtonsEnable();//在执行过程设置所有按钮为可点击
			}
		}
	});
	map.addOverlay(themeLayer);
} 

/**
 * 统一调用grid函数事件函数
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
 * 根据验证码是否正确确定是否执行对应的分析
 * @param analysisType
 */
function vehicleQueryAnalysis(analysisType){	
	execAnalysisAndDisplay(analysisType);
}

//===========================轨迹查询及结果显示结束========================================
