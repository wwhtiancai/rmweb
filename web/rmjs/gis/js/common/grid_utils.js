var tollgatePicUrl=basePath+"alarmOrTrackPub.gis?method=getTollgatePicture";
var cdxxUrl=basePath+"alarmOrTrackPub.gis?method=getCDXX";
var AppUtils=AppUtils||{};

AppUtils.DataGrid={};
AppUtils.DataGrid.allMarks=[];//存放查询出的所有卡口
AppUtils.DataGrid.curFlashMark=null;//记录当前闪烁的卡口
/**
 * 清除所有叠加物
 */
AppUtils.DataGrid.removeAllMarks=function(){
	for(var i=0;i<AppUtils.DataGrid.allMarks.length;i++){
		map.removeOverlay(AppUtils.DataGrid.allMarks[i]);
		AppUtils.DataGrid.allMarks.splice(i,1);
		i--;
	}
	AppUtils.DataGrid.allMarks=[];
	//map.clearOverlays();
}
AppUtils.DataGrid.addMarks=function(data,parameters){
	AppUtils.DataGrid.removeAllMarks();
	for(var i=0;i<data.length;i++){
			var kkjd=data[i].KKJD;
			var kkwd=data[i].KKWD;
			if(!kkjd||!kkwd||kkjd==""||kkwd==""){
				continue;
			}
		   var shape = "POINT("+kkjd+" "+kkwd+")";
		   var kkjc=data[i].KKJC;//卡口简称
		   var kklx=data[i]["kklx"]?data[i]["kklx"]:data[i]["KKLX"];//卡口类型
			var kkgzzt=data[i]["KKGZZT"]?data[i]["KKGZZT"]:data[i]["kkgzzt"];//卡口工作状态
			var isBuild=data[i]["ISBUILD"];
			var markImg=null;
			if(kkgzzt){
				markImg=AppUtils.getGzztMarkImg(kklx,kkgzzt);
			}else{
				markImg=AppUtils.getKKLXMarkImg(kklx,isBuild);
			}
		   var mark=DMap.Overlay.createByWKT(shape,{type:0,text:kkjc,url:markImg,size:new DMap.Size(32,32)});
		   map.addOverlay(mark);
		   AppUtils.DataGrid.allMarks.push(mark);
		   if(kklx==2||kklx==3){
				if(kkgzzt==3||kkgzzt==4){
					mark.flash();
				}
			}
		   var lonlat=mark.getLonlat();
			 mark.setCommonEvent();
			 mark.point=lonlat;
			 mark.name=name;
			 mark.datas=data[i];
			 mark.labels=
			 DMap.$(mark).bind("click",function(e){
				   var me=this;
				 	if(me.infor){
				 		map.openInfoWindowHtml(me.point, me.infor);
				 		return;
				 	}
			      $.ajax({
					  url:cdxxUrl,
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
/**
 * 周边查询
 * @param point
 * @param distince
 * @param layers
 */
AppUtils.DataGrid.aroundSearch=function(point,distince,layers){
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
	//alert(point.lon);
}
AppUtils.DataGrid.createMarkTip=function(lonlat,datas,labels){
	/**
	 //定义周边查询空间	
	 var roundQuery=new RoundQuery({
		 width:"150px",
		 datas:aroundLayers,
		 lon:lonlat.lon,
		 lat:lonlat.lat,
		 distances:[50,100,200,500,1000,1500,10000],
		 queryEvent:function(point,distince,layers){
			 //在这里可以添加周边查询的实现代码
		 	//alert("选择距离："+distince+" 图层信息："+layers[0].text+","+layers[0].value);
			 AppUtils.DataGrid.aroundSearch(point,distince,layers);
		 }
	});
	*/
	 var tableHtml="<table style='font-size:12px;width:350px;'>";
	 var attrCount=labels.length;
	 var index=0;
	 for(var i=0;i<attrCount;i++){
		 var field=labels[i]["field"];
		 var title=labels[i]["title"];
		 var value=datas[field.toLowerCase()]?datas[field.toLowerCase()]:datas[field.toUpperCase()];
		 var kkbh=datas["KKBH"]?datas["KKBH"]:datas["kkbh"];
		 if(labels[i]["formatter"]){
			 value=labels[i]["formatter"](value);
		 }
		 if(field=='KKGZZT'){
			 value=AppUtils.getKKGZZTText(value);
		 }
		 if(!title||!value){
			 continue;
		 }
		 tableHtml+="<tr>";
		 tableHtml+= "<td>"+title+":</td><td>"+value+"</td>";
		 if(index==0&&kkbh){
			 tableHtml+= "<td   valign='top' rowspan='"+attrCount+"'><img onclick='AppUtils.openBigPic(this)' baseUrl='"+tollgatePicUrl+"&kkbh="+kkbh+"' style='border:solid 1px;cursor:hand;width:100px;height:90px' src='"+tollgatePicUrl+"&kkbh="+kkbh+"&width=100&height=100'/></td>";
		 }
		 tableHtml+="</tr>";
		 index++;
	 }
	 tableHtml+="</table>";
	 var sxfxbm=datas.sxfxbm?datas.sxfxbm:datas.SXFXBM;
	 if(!sxfxbm||sxfxbm==""){
		 sxfxbm="未知";
	 }
	 if(datas.roads&&datas.roads.length!=0){
		 var sketchMap=new SketchMap({
			 direction:"上行方向："+sxfxbm,
			 width:300,
	    	  roads:datas.roads
	      });
		 tableHtml+=sketchMap.getHTML();
	 }
	 //加入功能按钮
	 var objStr=ObjectToStr(datas);
	 //alert(objStr);
	 var btnHtml="<span style='width:100%;height:10px'></span>";
	 if(kkbh){		
	 		btnHtml+="<div style='font-size:12px;text-align:center;width:320px;position:relative'>" +
			"<a onClick='AppUtils.openInfoWindow("+objStr+",0)' href='#'>详细信息</a>&nbsp;&nbsp;" +
			"<a onClick='AppUtils.openInfoWindow("+objStr+",1)'  href='#'>工作状态</a>&nbsp;&nbsp;" +
			"<a onClick='AppUtils.openInfoWindow("+objStr+",2)'  href='#'>实时过车</a>&nbsp;&nbsp;" +
			"<a onClick='AppUtils.openInfoWindow("+objStr+",3)'  href='#'>历史过车</a>&nbsp;&nbsp;" +
			"<a onClick='AppUtils.openInfoWindow("+objStr+",4)'  href='#'>预警信息</a>&nbsp;&nbsp;" +
			"</div>";
	 }
	 //var infor=tableHtml+roundQuery.getPageHtml()+btnHtml;
	 var infor=tableHtml+btnHtml;
	 return infor;
};
/**
 * 初始化DataGrid
 */
AppUtils.DataGrid.init=function(baseContent,parameters){
	var width=$(baseContent).width();
	var height=$(baseContent).height();
	if(parameters){
		if(parameters["width"]){
			width=parameters["width"];
		}
		if(parameters["height"]){
			height=parameters["height"];
		}
	}
	if(!$("#dg").get(0)){
		$("<div>").attr("id","dg").appendTo(baseContent).css({"width":width,"height":height});
	}
	var content=$("#dg");
	var defaultParameters={
		pageSize:15,
		fix:true,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		pageList:[10,15,20],
		columns:[[//设置表格字段
			        {field:'KKMC',title:'卡口名称'},
				     {field:'KKLX',title:'类型',align:"center",formatter:AppUtils.DataGrid.kklxFormatter},
				     {field:'GLBM',title:'管理部门',formatter:AppUtils.DataGrid.glbmFormatter}
			    ]],
		markLabels:[//设置气泡显示内容
		           {field:'KKMC',title:'卡口名称'},
				     {field:'KKLX',title:'类型',formatter:function(val){
				    	 return AppUtils.getDicText("kklx","0"+val);
				     }},
				     {field:'GLBM',title:'管理部门',formatter:function(val){
				    	 return AppUtils.getDicText("glbm",val);
				     }}
		  ],
		  onClickRow:function(rowIndex, rowData){
			var kkjd=rowData["KKJD"];
			var kkwd=rowData["KKWD"];
			if(!kkjd||!kkwd||kkjd==""||kkwd==""){
				alert("此卡口暂无位置信息，无法定位！");
				return;
			}
	    	var mark=DMap.Overlay.createByWKT("POINT("+rowData["KKJD"]+" "+rowData["KKWD"]+")");
	    	map.setCenter(mark.getLonlat());
	    	map.activateFalshOut(mark.getLonlat());
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
	    },
	    rowStyler: function(index,rowData){
	    	var kkjd=rowData["KKJD"]||rowData["kkjd"];
			var kkwd=rowData["KKWD"]||rowData["kkwd"];
			if(!kkjd||!kkwd){
				return 'color:red;';
			}
	    }
	};
	//判断当前传入的参数是否包含直接返回的grid data数组
	if(parameters&&!parameters["data"]){
		defaultParameters["loader"]=function(param,success,error){
		    	var opts=$(this).datagrid("options");
		    	if(!opts.url){
		    		return false;
		    	}	
		    	//alert(param.page);
		    	param["pageIndex"]=param["page"]+10000;
		    	param["pageSize"]=param["rows"]+10000;
		    	$.ajax({type:opts.method,url:opts.url,data:param,dataType:"json",success:function(data){
		    		if(data&&data["success"]==false){
		    			alert(data["msg"]);
		    			return;
		    		}		    		
		    		success(data);
		    		var callback=params["callback"];
		    		if(callback){
		    			callback(data,params);
		    		}
	    	},error:function(e){	    		
	    		error.apply(this,arguments);
	    	}});
	    }
	}else{		
		defaultParameters["data"]=parameters["data"];		
	}
	var params=DMap.$.extend(defaultParameters,parameters);
	content.datagrid(params);
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
AppUtils.DataGrid.autoZoom=function(content){
	var bounds=AppUtils.DataGrid.getBounds($(content));
	if(bounds==null){
		return;
	}
	var zoom=map.getZoomByBounds(bounds);
	//map.zoomToLonlatBounds(bounds,zoom);
	map.setCenter(bounds.midLonLat(),zoom-1);
}
AppUtils.DataGrid.getBounds=function(content,lonField,latField){
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
 * DataGrid表格卡口类型格式化
 */
AppUtils.DataGrid.kklxFormatter=function(value,row,index){
	var kklx=row["KKLX"]?row["KKLX"]:row["kklx"];
	var isbuild=row["ISBUILD"];//0 未建 1 已建
	var imgsrc=AppUtils.getKKLXMarkImg(kklx,isbuild);
	return "<img src='"+imgsrc+"'/>";
}
/**
 * DataGrid表格管理部门格式化
 */
AppUtils.DataGrid.glbmFormatter=function(value,row,index){
	var glbm=row["GLBM"]?row["GLBM"]:row["glbm"];
	var glbmmc=AppUtils.getDicText("glbm",glbm);
	return glbmmc;
}