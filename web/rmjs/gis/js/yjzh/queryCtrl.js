var currentClickShower="";//当前选择项
var eventRecord=""; //记录上一次查询时的查询参数，用于是否5秒查询判断
var recTimer;   //计时器
var queryPerTime=5*1000; //自动查询间隔时间(毫秒)
var markerJsonArray = [];//装载markerjson
var yjczlcMakerArray = [];//应急处置流程装载marker
var yjXxlx = "yjqr";
//清除自动查询
function clearTimer(){
	if(recTimer){
		clearTimeout(recTimer); 
	}
}
//重新设定计时时间
function resetQueryTimer(){ 
	if(recTimer){
		clearTimeout(recTimer); 
	}		
    recordTimeQuery();	
}
//按照时间计时查询
function recordTimeQuery(){  
	recTimer = setTimeout(function(){
		if(currentClickShower!=""){
			setTimeout(function(){
				guide_queryEventData(currentClickShower,"timer"); //组织查询
			},500);			
		}
		
	},queryPerTime);
	
}
//获得查询条件参数
function getQueryParam(){
   var params = {}; //查询参数对象
   var sjlxs = ""; //数据类型
   var xflxs = ""; //细分类型
   var dllxs = ""; //道路类型
   var xzqhs = ""; //行政区划
   //事件类型
   var eventCheckedArray = $("input[name='eventCheckBox']:checked");
   for(var i=0; i<eventCheckedArray.length; i++){
	   var checkedEventValue = eventCheckedArray[i].value;
	   if(i!=0){
		  sjlxs+=";";   
		  xflxs+=";";
	   }
	   sjlxs+=checkedEventValue;
	   var xfCheckedArray = $("input[name='eventxf_"+checkedEventValue+"']:checked"); 
	   if(xfCheckedArray.length==0){
		   xflxs+="#";
	   }else{
		   for(var j=0; j<xfCheckedArray.length; j++){
			   if(j!=0){
				   xflxs+=",";
			   }
			   xflxs+=xfCheckedArray[j].value;
		   }
	   }	  
   }	  
   //道路类型
   var roadTypeCheckedArray = $("input[name='roadTypeCheck']:checked");
   for(var i=0; i<roadTypeCheckedArray.length; i++){
	   if(i!=0){
		   dllxs+=","; 
	   }
	   dllxs+=roadTypeCheckedArray[i].value;
   }
   //行政区划
   xzqhs = getSelectedDistrictTreeIds("selectXzqhTreeDiv");
   params.sjlxs = sjlxs;
   params.xflxs = xflxs;
   params.dllxs = dllxs;
   params.xzqhs = xzqhs;
   return params;
}

//分类查询
function guide_queryEventData(queryType,fromType){
	showLoad();
	//clearMakerInType("yjqr,yjcz,czfk");
	clearYjczlcMakerArray(); //清除应急处置流程中查询绘制的图标
    map.closeInfoWindow();
   var params = getQueryParam();
   if(fromType=="timer" && eventRecord==queryType+params.sjlxs+params.xflxs+params.dllxs+params.xzqhs){ //查询条件没变化
	   return;
   }else{
	   eventRecord=queryType+params.sjlxs+params.xflxs+params.dllxs+params.xzqhs;
   }  
   var queryUrl = basePath + "yjzh.gis?method=guideYjqr";
   if(queryType=="yjqr"){ //预警确认
	   queryUrl = basePath + "yjzh.gis?method=guideYjqr";
   }else if(queryType=="yjcz"){ //应急处置
	   queryUrl = basePath + "yjzh.gis?method=guideYjcz";
   }else if(queryType=="czfk"){ //处置反馈
	   queryUrl = basePath + "yjzh.gis?method=guideCzfk";
   }	   
   $.ajax({	
		url:queryUrl,
		cache:false,
		type:"post",
		data:params,
		dataType:"json",
		success:function (data){
			setTimeout(function(){
				closeLoad(); //去除等待条
			},500);			
			$('#guideFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:false});
			//var data={"total":4,"rows":[{"sxh":1,'sjlx':'A',"id":"320200000000050002","tz":"移动摄像","mc":"科研所移动摄像设备","jl":"1390.0","geo":"POINT(120.30566 31.56713)"},{"sxh":2,'sjlx':'A',"id":"320200000000070005","tz":"区间测速","mc":"钱荣路上区间测试设备","jl":"1631.0","geo":"POINT(120.29687 31.57958)"},{"sxh":3,'sjlx':'B',"id":"320200000000070200","tz":"区间测速","mc":"无锡所测试区间测速设备","jl":"245.0","geo":"POINT(120.31347 31.5791)"},{"sxh":4,'sjlx':'C',"id":"320200000000070810","tz":"区间测速","mc":"EEEEEE","jl":"4713.0","geo":"POINT(120.28777 31.61328)"}]}
	        guide_eventResult(data,queryType); //写列表数据
			
			var lonlatArray = [];
			yjczlcMakerArray = [];
			//解析easyUi数据
			if(data.total>0){
				for(var i=0; i<data.rows.length; i++){				
					var id=data.rows[i].id;
					var geo = data.rows[i].geo;  
					var sjlx = data.rows[i].sjlx; //事件类型
					var sjdj = data.rows[i].sjdj; //事件等级
					if(geo!="POINT( )"){
						//var maker = drawGeo(geo,queryType,id);
						var maker = drawGeo(geo,sjlx,id,sjdj,queryType);
						yjczlcMakerArray.push(maker);
						lonlatArray.push(maker.getLonlat());
					}			
				}

				var markerJson={ //分类型装载查询结果
					lx:'',
					val:[]
				};
				markerJson.lx=queryType;
				markerJson.val = yjczlcMakerArray;
				markerJsonArray.push(markerJson);
				if(lonlatArray.length>0){
					var resBound = DMap.LonLatBounds.fromLonLatArray(lonlatArray);
					map.zoomToLonlatBounds(resBound);
					map.setZoom(map.getZoom()-1);
				}				
			}	
		}
   });
}
//跳转到人工采集页面
function guide_jumpToRgcjPage(){
	gis_add_incident();
}
//************************ 等待条 ***************************  
function showLoad(valmesg){
	var msg = "正在加载资源,请稍候。。。。。。";
	if(valmesg){
		msg = valmesg;
	}
	var loadDiv= document.getElementById("ly");
	if(!loadDiv){  
		lyDiv = document.createElement("div");
	    lyDiv.id = "ly";
	    lyDiv.style.position = "absolute";
	    lyDiv.style.width = document.body.clientWidth;
	    lyDiv.style.height = document.body.clientHeight;
	    lyDiv.style.top = "0px";
	    lyDiv.style.left = "0px";
	    lyDiv.style.filter = "alpha(opacity=80)";
	    lyDiv.style.backgroundColor = "#777";
	    lyDiv.style.zIndex = 9999;
	    lyDiv.style.display = "block";//absolute
	    var leftVal=(document.body.clientWidth)/2-107;   
	    var imgHtml = "<img style='position:relative ;left:"+leftVal+";top:50%' src='"+modelPath+"images/business/zhzx/progressbar_green.gif' />"; 
	    imgHtml+="<br /><span style='position:relative ;left:"+leftVal+";top:51%;font-size:14px'>"+msg+"</span>";
	    lyDiv.innerHTML = imgHtml; 
	    document.body.appendChild(lyDiv); 
	}     
};

function closeLoad(){
    try {
        document.body.removeChild(this.lyDiv);        
    } 
    catch (e) {
//        alert("请求失败！");
    }
}; 