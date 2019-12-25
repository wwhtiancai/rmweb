var alarmMarkers=[];
var curClickMarker=null;
var isStopRefresh=false;//是否停止获得最新预警信息
var passPicturesBaseUrl=basePath+"readPassPic.tfc?method=getPassPic&gcxh=";
var tollgatePicUrl=basePath+"alarmOrTrackPub.gis?method=getTollgatePicture";
var labelShowFields={ 
		 KKMC:"卡口名称",
		 GLBMMC:"管理部门",
		 KKLXMC:"卡口类型"	 
};

var yjtabsConfig = [ {
	title : "最新预警详情",
	href : basePath + 'alarmOrTrackPub.gis?method=alarmDetail',
	reqParams:""
}, {
	title : "预警信息查询 ",
	href : basePath + 'alarmOrTrackPub.gis?method=alarmQuery',
	reqParams:""
},{
	title : "过车信息查询 ",
	href : basePath + 'alarmOrTrackPub.gis?method=passInfo',
	reqParams:""
}, {
	title : "卡口详情 ",
	href : basePath + 'alarmOrTrackPub.gis?method=tollgateInfo',
	reqParams:""
}, {
	title : "卡口工作状态 ",
	href : basePath + 'alarmOrTrackPub.gis?method=tollgateStatus',
	reqParams:""
} ];

/**
 * 卡口图片配置
	 * 02	省际卡口
03	市际卡口
04	县际卡口
05	公路主线卡口
06	公路收费站卡口
07	城区道路卡口
08	城区路口卡口
	 */
var markConfig={
	2:"shengjkk.png",
	3:"sjkk.png",
	4:"xjkk.png",
	5:"zxkk.png",
	6:"sfzkk.png",
	7:"dlkk.png",
	8:"lkkk.png"
};

/**
 * 填充当前用户预警卡口到前端页面之上
 */
function fillAlarmTollgates(){
	$.ajax({
		  url:basePath+ "realAlarm.gis?method=getAlarmTollgates",		
		  data:{glbm:alarmGlbm},//"340204000000"  
		  dataType:'json',
		  success: function(data) {
			  var alarmTollgates=jQuery("#alarmTollgates");
			  if(data!=null&&data.length>0){
				  for(var i=0;i<data.length;i++){
					  var everyTollgate=jQuery("<li style='float:none;'><input type='checkbox' name='cbTollgates' value="+data[i]["KKBH"]+">"+data[i]["KKMC"]+"</li>");
					  alarmTollgates.append(everyTollgate);
				  }
			  }			 
		  }
	});
}

/**
 * 实时预警条件
 */
function fillRealAlarmCondition(){			
	//【1】填充号牌种类到页面之中
	fillHPZLToPage("hpzl_select",hpzlList);
	//【2】填充布控类型到页面之中
	fillBKLXToPage("td_bklx", bklxList);
	//【3】填充当前用户下的预警预警卡口
	fillAlarmTollgates();
}

function selectedAllTollgates(){
	var cbTollgates=$("[name='cbTollgates']");
	$.each(cbTollgates,function(i,cbox){
		$(cbox).attr("checked","checked");		
	});	
}

function unSelectedAllTollgates(){
	var cbTollgates=$("[name='cbTollgates']");
	$.each(cbTollgates,function(i,cbox){
		$(cbox).attr("checked",null);		
	});
}

//获得预警相关的参数
var realAlarmParamters={
	hpzl:"",//号牌种类
	hphm:"",//号牌号码
	bklx:"",//布控类型
	kkbh:""//布控卡口	
};

function getSelectedAlarmTollgates(){
	var selectedTollgates=AppUtils.getCheckBoxValue("cbTollgates");
	return selectedTollgates;
}

/**
 * 获得预警相关的条件
 * @return
 */
function getRealAlarmParameters(){
	var hpzlVal=$("#hpzl_select").combobox("getValue");//号牌种类	
	if(hpzlVal=="no"){
		hpzlVal="";
	}
	realAlarmParamters.hpzl=hpzlVal;
	var hphmVal=$("#hphm_text").val();//号牌号码
	realAlarmParamters.hphm=encodeURI(hphmVal);
	var values=$("#td_bklx").divFilter("getValue");
	var bklxValues="";
	//把布控类型的值拼接为字符串
	if(values.length>0){
		for(var i=0;i<values.length;i++){
			if(bklxValues!=""){
				bklxValues+=","+values[i]["value"];
			}else{
				bklxValues+=values[i]["value"];
			}
		}
	}	
	realAlarmParamters.bklx=bklxValues;
	//获得已经布控的卡口编号
	var kkbhValues=getSelectedAlarmTollgates();	
	realAlarmParamters.kkbh=kkbhValues.join(",");
	return realAlarmParamters;
}

/**
 * 刷新布控预警信息
 */
function refreshBkInfo(){	
	getRealAlarmParameters();
	//判断验证一下 如果布控卡口为空不执行操作
	/**
	if(realAlarmParamters.kkbh.length==0){
		$.messager.alert("提示消息", "查询条件预警卡口不能为空！", "info");
		return;
	}
	*/
	for(var i=0;i<alarmMarkers.length;i++){
		map.removeOverlay(alarmMarkers[i]);
	}	
	var reqUrl=basePath+ "realAlarm.gis?method=getExtAlarmResult";
	isStopRefresh=false;
	var refreshMills=10000;	
	document.getElementById("startRefreshBtn").disabled=true;
	document.getElementById("stopRefreshBtn").disabled=false;
	refreshAlarmInfo(reqUrl,realAlarmParamters,refreshMills);		
}

/**
 * 间隔刷新请求函数
 * @param reqUrl 请求的基本url
 * @param reqParams 请求的参数
 * @param freshMillisecond 每次刷新的时间 单位毫秒
 */
function refreshAlarmInfo(reqUrl,reqParams,freshMillisecond) {
	if (isStopRefresh) {
		return;
	}	
	$.ajax({
		url : reqUrl,
		data :reqParams,
		type:"post",
		dataType : 'text',
		success : function(data) {	
			var alarmInfoResult = $.evalJSON(data);
			showAlarmInfoToMap(alarmInfoResult.rows);
			showWarnInfoClientPagination(alarmInfoResult);			
			//在请求完毕之后重新获得请求结果			
			setTimeout(function() {
				// 【1】在地图上显示最新预警的车辆 到达卡口并在地图上显示
				refreshAlarmInfo(reqUrl,reqParams,freshMillisecond);
			}, freshMillisecond);
		}
	});
}

function stopRefreshBKInfo(){	
	document.getElementById("startRefreshBtn").disabled=false;
	document.getElementById("stopRefreshBtn").disabled=true;
	//alert("停止获得预警信息");
	isStopRefresh=true;
	//【清除闪烁效果】
	for(var i=0;i<alarmMarkers.length;i++){
		alarmMarkers[i].clearFlash();
	}	
}

/**
 * 移除预警Markers
 */
function removeAllMarkers(){	
	for(var i=0;i<alarmMarkers.length;i++){
		map.removeOverlay(alarmMarkers[i]);
		alarmMarkers.splice(i,1);
		i--;
	}
	alarmMarkers=[];
	map.closeInfoWindow();
}

function getMarkImg(type){
	var imgName=markConfig[type];
	if(!imgName){
		imgName="province_normal.png";
	}
	var imgPath=modelPath+"images/markers/tollgates/"+imgName;
	return imgPath;
}


/**
 * 显示布控预警信息到地图上
 * @param rows
 */
function showAlarmInfoToMap(rows){
	//仅仅清除预警Markers
	removeAllMarkers();	
	//【1】获得预警卡口信息显示到地图上
	for(var i=0;i<rows.length;i++){
		var kkjd=rows[i].KKJD;//预警卡口经度
		var kkwd=rows[i].KKWD;//预警卡口纬度
		var kklx=rows[i].KKLX;
		var markImg=getMarkImg(kklx);
		if(kkjd!=""&&kkwd!=""){			 
			var lonlat=new DMap.LonLat(kkjd,kkwd);
			var mark = new DMap.Marker(lonlat,{type:0,text:rows[i].KKMC,url:markImg});
			mark.setCommonEvent();
			var lonlat=mark.getLonlat();
			mark.point=lonlat;
			mark.name=name;
			mark.datas=rows[i];
			mark.flash(5000);
			alarmMarkers.push(mark);
			//[****]获得当前当前打开的气泡信息 并且更新当前气泡的最新气泡内容		
			if(curClickMarker&&lonlat.equals(curClickMarker.getLonlat())){	
				curClickMarker.datas=mark.datas;
				 if(mark.datas.roads&&mark.datas.roads.length!=0){
					 var infor=createMarkTip(mark.point,curClickMarker.datas);
					  var popupInfoDom=map.getInfoWindow().getContentContainers()[0];
					  popupInfoDom.innerHTML="";
					  popupInfoDom.innerHTML=infor; 
				 }						
			}		
			DMap.$(mark).bind("click",function(e){		
				curClickMarker=this;
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
					  var infor=createMarkTip(me.point,me.datas);
					  me.infor="";
					  me.infor=infor;
					  map.openInfoWindowHtml(me.point, infor);
				  }
			 	}); 	
		    });
			map.addOverlay(mark);
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
*显示最新的预警信息到右边的页面
* @param data
*/
function showWarnInfoClientPagination(data) {		
	var content = slidebarControl.getContentDiv();
	//$(content).empty();
	slidebarControl.slideOut();
	if (!$("#yjdg").get(0)) {	
		var titleHtml="<span id='yjtitle' style='color:red;font-weight:bolder;margin-top:7px;";
		titleHtml+="height:20px;width:100%;font-size:12px;text-align:center;'>最新预警信息</span>";
		$(titleHtml).appendTo(content);
		$("<div>").attr("id", "yjdg").appendTo(content).css("width", "340px");		
	}
	$("#yjdg").datagrid({
		pageSize : 15,
		fix : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		pageList:[10,15,20],
		loadFilter:pagerFilter,
		columns : [[{
			field : "HPHM",
			title : "布控车牌号牌"
		}, {
			field : "KKMC",
			title : "预警卡口名称",				
			align : "left"
		},{
			field : "BKLXMC",
			title : "布控类型",				
			align : "left",
			hidden:true
		},
		{
			field : "YJSJ",
			title : "预警时间",				
			align : "left"
		},{
			field : "FXBM",
			title : "方向别名",				
			align : "left"
		}]],			
		data: data,			
		onClickRow : function(rowIndex, rowData) {
			var kkjd=rowData.KKJD;//预警卡口经度
			var kkwd=rowData.KKWD;//预警卡口纬度
			if(kkjd!=""&&kkwd!=""){	
				var markWkt="POINT("+kkjd+" "+kkwd+")";
				var mark = DMap.Overlay.createByWKT(markWkt);				
				map.setCenter(mark.getLonlat());
				for (var i = 0; i < alarmMarkers.length; i++) {
					if (mark.getLonlat().equals(alarmMarkers[i].getLonlat())) {
						if (curClickMarker != null) {
							curClickMarker.clearFlash();
							curClickMarker = null;
						}
						curClickMarker = alarmMarkers[i];
						curClickMarker.flash();
						window.setTimeout(function() {
							if (curClickMarker != null) {
								curClickMarker.clearFlash();
								curClickMarker = null;
							}
						}, 5000);
					}
				}
			}else{
				$.messager.alert("提示消息", "点击卡口无坐标，无法定位！", "info");
			}
		
		},
		rowStyler: function(index,rowData){
		    var kkjd=rowData["KKJD"]||rowData["kkjd"];
			var kkwd=rowData["KKWD"]||rowData["kkwd"];
			if(!kkjd||!kkwd){
				return 'color:red;';
			}
		}
	});
	var pager = $("#yjdg").datagrid("getPager");
	$(pager).pagination({
		showRefresh : false,
		showPageList : false,
		afterPageText : "页 共 {pages} 页",
		displayMsg : "共 {total} 条",
	    buttons: [{
			iconCls:'icon-markers-to-map',
			title:"居中卡口位置到地图",
			handler:function(){				
				dataGridAutoZoom($("#yjdg"));
			}
		}]
	});
}

function dataGridAutoZoom(content){
	var bounds=getDataGridBounds($(content));
	if(bounds==null){
		return;
	}	
	var zoom=map.getZoomByBounds(bounds);
	//map.zoomToLonlatBounds(bounds,zoom);
	map.setCenter(bounds.midLonLat(),zoom-1);
}

function getDataGridBounds(content,lonField,latField){
	var data=$(content).datagrid('getData');
	if(!data){
		return;
	}
	var maxLon=0;
	var maxLat=0;
	var minLon=0;
	var minLat=0;
	var rows=data.rows;
	var lonField=lonField||"kkjd";
	var latField=latField||"kkwd";
	var flag=0;	
	for(var i=0;i<rows.length;i++){
		var rowData=rows[i];
		var lon=rowData[lonField.toLowerCase()]?rowData[lonField.toLowerCase()]:rowData[lonField.toUpperCase()];
		var lat=rowData[latField.toLowerCase()]?rowData[latField.toLowerCase()]:rowData[latField.toUpperCase()];	
		if(((lon!="")&&(lon!=undefined))&&((lon!="")&&(lon!=undefined))){		
			flag++;
			if(flag==1){
				maxLon=lon;
				maxLat=lat;
				minLon=lon;
				minLat=lat;
			}else{
				if(lon>maxLon){
					maxLon=lon;
				}
				if(lat>maxLat){
					maxLat=lat;
				}
				if(lon<minLon){
					minLon=lon;
				}
				if(lat<minLat){
					minLat=lat;
				}
			}			
		}
	}
	if(maxLon==minLon||maxLat==minLat){
		return null;
	}
	//alert(minLon+","+minLat+","+maxLon+","+maxLat);
	return new DMap.LonLatBounds(new Number(minLon),new Number(minLat),new Number(maxLon),new Number(maxLat));
}


/**
 * 创建Mark提示信息
 * @param lonlat
 * @param datas
 * @returns {String}
 */
function createMarkTip(lonlat,datas){
	 var tableHtml="<table style='font-size:12px;width:360px;'>";
	 var attrCount=0;
	 for(var o in labelShowFields){
		 attrCount++;
	 }	 
	 var objStr=ObjectToStr(datas);	 
	 var index=0;
	 var kkbh="";
	 var gcxh="";
	 for(var o in labelShowFields){
		 tableHtml+="<tr>";
		 tableHtml+= "<td>"+(labelShowFields[o]?labelShowFields[o]:labelShowFields[o.toUpperCase()])+":</td><td>"+(datas[o]?datas[o]:datas[o.toUpperCase()])+"</td>";
		 if(index==0){
			 kkbh=datas["KKBH"]?datas["KKBH"]:datas["kkbh"];			 
			 tableHtml+= "<td valign='top' rowspan='"+(attrCount+3)+"'><img onclick='openBigPic(this)' baseUrl='"+tollgatePicUrl+"&kkbh="+kkbh+"' style='border:solid 1px;cursor:hand' width='100px' heigh='100px' src='"+tollgatePicUrl+"&kkbh="+kkbh+"&width=100&height=100'/></td>";
		 }
		 tableHtml+="</tr>";
		 index++;
	 }	
	 //添加卡口预警信息统计
	 /**
	 tableHtml+="<tr><td>今日预警数:</td><td style='color:red;font-weight:bolder;'>10</td></tr>";
	 tableHtml+="<tr><td>未签收预警数:</td><td style='color:red;font-weight:bolder;'>5</td></tr>";
	 tableHtml+="<tr><td>未反馈预警数:</td><td style='color:red;font-weight:bolder;'>4</td></tr>";	
	 */
	 tableHtml+="</table>";
	 //最新预警信息显示
	var warnInfoHtml="<fieldset style='font-size:12px;width:360px;border:solid #1c69b9 1px;'>";
	warnInfoHtml+="<legend style='color:red;font-weight:bolder;'>最新预警信息</legend>";
	warnInfoHtml+="<table style='font-size:12px;' width='100%'>";
	warnInfoHtml+="<tr><td>号牌种类:</td><td>小汽车</td></td><td valign='top' style='text-align:right;' rowspan='5'>";
	warnInfoHtml+="<img onclick='openBigPic(this)' baseUrl='"+passPicturesBaseUrl+datas["GCXH"]+"&width=100&height=100";
	warnInfoHtml+=" style='border:solid 1px;cursor:hand' width='100px' heigh='100px' ";
	warnInfoHtml+=" src='"+passPicturesBaseUrl+datas["GCXH"]+"&width=100&height=100'/></td></tr>";
	warnInfoHtml+="<tr><td>号牌号码:</td><td>"+datas["HPHM"]+"</tr>";
	warnInfoHtml+="<tr><td>布控类型:</td><td>"+datas["BKLXMC"]+"</td></tr>";
	warnInfoHtml+="<tr><td>预警时间:</td><td>"+datas["YJSJ"]+"</td></tr>";	
	warnInfoHtml+="<tr><td>签收状态:</td><td style='color:red;font-weight:bolder;'>未签收</td></tr>";	
	//获得最新预警信息所需的参数 *** 此处需要marker 内容之中获得
	var hphm=datas["HPHM"];//获得号牌号码
	var hpzl=datas["HPZL"];//获得号牌种类
	var bklx=datas["BKLX"];//获得布控类型
	//最新预警详情参数
	var alarmDetailParams="&hpzl="+hpzl+"&hphm="+hphm+"&bklx="+bklx;
	yjtabsConfig[0]["reqParams"]=alarmDetailParams;		
	warnInfoHtml+="<tr><td colspan='3' style='text-align:center;'><a onClick='AppUtils.openInfoWindow("+objStr+",0,"+ObjectToStr(yjtabsConfig)+")' href='#'>最新预警详情</a>&nbsp;</td></tr>";
	//&nbsp;<a onClick='testForecast()' href='#'>轨迹预测</a>
	warnInfoHtml+="</table></fieldset><br>";	
	tableHtml+=warnInfoHtml;
	//加入道路示意
	 //alert(node.attributes.roads.length);	 
	 if(datas.roads&&datas.roads.length!=0){
		 var sketchMap=new SketchMap({
			 direction:datas.sxfxbm?datas.sxfxbm:datas.SXFXBM,
			 width:360,
	    	  roads:datas.roads
	      });
		 tableHtml+=sketchMap.getHTML();
	 }	 
	 
	 //针对过车信息所需的参数
	 var passInfoParams="&hpzl="+hpzl+"&hphm="+hphm;
	 yjtabsConfig[2]["reqParams"]=passInfoParams;
	//加入功能按钮
	var btnHtml="<span style='height:10px;'></span><div style='font-size:12px;text-align:center;width:345px;position:relative'>";	
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",1,"+ObjectToStr(yjtabsConfig)+")' href='#'>预警信息查询</a>&nbsp;&nbsp;";
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",2,"+ObjectToStr(yjtabsConfig)+")' href='#'>过车信息查询</a>&nbsp;&nbsp;";
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",3,"+ObjectToStr(yjtabsConfig)+")' href='#'>卡口详情</a>&nbsp;&nbsp;";
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",4,"+ObjectToStr(yjtabsConfig)+")' href='#'>卡口工作状态</a></div>";	
    var infor=tableHtml+btnHtml;
	return infor;
}

//打开卡口的放大图片
function openBigPic(objImg){
	//alert(objImg.baseUrl);
	var maxImgControl=new MaxImgControl({imgUrl:objImg.baseUrl});
	maxImgControl.show();
}
