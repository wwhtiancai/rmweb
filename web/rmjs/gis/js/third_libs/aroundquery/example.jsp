<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<style type="text/css">
		body { font:12px Helvetica, arial, sans-serif }

	</style>
	<script type="text/javascript" src="http://10.80.1.15:8080/Nasoft_MapClient/maps"></script>
	<script type="text/javascript" src="aroundquery.jsp"></script>
	

<script type="text/javascript">
		var map=null;
		
		window.onload=function(){
			
			map = new DMap.Map(document.getElementById('map1'));
			map.showZoomBarControl();//缩放等级
			map.showOverviewMap();//鹰眼
			map.showMapTypeControl();//地图addControl(new DMap.MapTypeControl());	
			map.showScaleControl();//比例尺addControl(new DMap.ScaleControl());	
			map.showCopyright();//版权
			var toolBar = new DMap.ToolBarControl();
			   //   toolBar.addQueryTools();
				  map.addControl(toolBar);
				  
			map.activateTool('POINT',function(e,lonlats){
				 var mark = new DMap.Marker(lonlats);
				 var roundQuery=new RoundQuery({
					 width:"150px",
					 datas:[{text:"视频",value:"SP_PT"}],
					 lon:lonlats.lon,
					 lat:lonlats.lat,
					 queryEvent:function(point,distince,layers){
					 	aroundSearch(point,distince,layers);
					 }
				});
				//$("#testContent").html(roundQuery.getPageHtml());
				 var name="测试一下";
				 var infor="<table><tr><td>名称:</td><td>"+name+"</td></tr><tr><td colspan=2>"+roundQuery.getPageHtml()+"</td></tr></table>";
				 mark.setCommonEvent();
				 mark.point=lonlats;
				 mark.name=name;
				 DMap.$(mark).bind("click",function(e){ 
				      map.openInfoWindowHtml(this.point, infor );     
			    }).bind("mouseover",function(e){
			        map.showFloatHelper(this.name);
			    }).bind("mouseout",function(e){
			        map.hideFloatHelper();
			    });
				 map.addOverlay(mark);      
			});
			//var roundQuery=new EhlRoundQuery({});
			//alert(roundQuery.getPageHtml());
			//$("#testContent").html(roundQuery.getPageHtml());
		}
		function aroundSearch(point,distince,layers){
			if(distince==0){
				alert("请选择距离！");
				return;
			}else if(layers.length==0){
				alert("请选择图层！");
				return;
			}
			alert(layers[0].text+","+layers[0].value);
		}
		/*查询成功后的回调函数*/
		function callback(data){
		    var rowList = data.rowList;   
		    var roundPoints = "";
		    /*if(data.roundPoints){  //没有查到数据情况下是没有roundPoints属性的
			    roundPoints = data.roundPoints;
			    if(roundPoints.length>0){
				    var xyArray = roundPoints.split(",");
				    var xyPointArray = new Array();
				    for(var i=0; i<xyArray.length; i++){
				       if(i%2==1){
				          xyPointArray.push(new DMap.LonLat(xyArray[i-1],xyArray[i]));
				       }
				    }
				    var polygon = new DMap.Polygon (xyPointArray);
				    map.addOverlay(polygon);       
			    }  
		    }*/
		    if(rowList.length==0){
		       alert("没有查到数据！");
		       return;
		    }
		    for(var i=0; i<rowList.length; i++){
		       var shape = rowList[i].SHAPE;
		       var name = rowList[i][data.xszd];
		       var x= shape.substring(shape.lastIndexOf("(")+1,shape.lastIndexOf(" "));
		       var y= shape.substring(shape.lastIndexOf(" "),shape.lastIndexOf(")"));
		       //alert(data.markUrl);
		       var addressSymble={
		         type : 0,
		         url : data.markUrl,
				 size : new DMap.Size(18,18),
				 offsetType : "mm",
				 borderWidth : 4,
				 borderColor : "red",
				 color :"white",
				 opacity : 1,
				 labelText : name,
				 labelFontSize: 12
		      }
		       drawMarker(x,y,addressSymble,data.layerName,rowList[i]["OBJECTID"]);
		    }
		    //alert(data.tblj);
		}
		function drawMarker(x,y,addressSymble,layerName,keyId){
		      var point =new DMap.LonLat(x,y);
		   	  var mark=new DMap.Marker(point,addressSymble);
		      mark.setCommonEvent();
		      mark.name=addressSymble.labelText;
		      mark.point=point;
		      var opt = {
			 		isAdjustPositon : true, //infoWIndow是否自适应，即infoWindow总会自动调整到视野范围内
			 		offsetSize : new DMap.Size(0,0) //infoWindow箭头偏移大小
			  }
		      var roundQuery=new RoundQuery({
					 width:"150px",
					 dataBaseId:"dcappcon",
					 appCode:"PGIS_APP_People",
					 lon:x,
					 lat:y,
					 queryEvent:function(point,distince,layers){
					 	aroundSearch(point,distince,layers);
					 }
				});
			  var infor="<table><tr><td>名称:</td><td><a style='font-size:12px' href='javascript:openInfoWin(\""+layerName+"\",\""+keyId+"\")'>"+addressSymble.labelText+"</a></td></tr><tr><td colspan=2>"+roundQuery.getPageHtml()+"</td></tr></table>";
			  //alert(infor);
			  DMap.$(mark).bind("click",function(e){ 
			      map.openInfoWindowHtml(this.point, infor ,opt);     
		      }).bind("mouseover",function(e){
		          map.showFloatHelper(this.name);
		      }).bind("mouseout",function(e){
		          map.hideFloatHelper();
		      });
			  map.addOverlay(mark);
		}		
		function openInfoWin(layerName,keyId){
			//alert(layerName+","+keyId);
			window.open(ehlAroundQueryServer+"/infoquery/feature.jsp?id="+keyId+"&tabName="+layerName);
		}
	</script>
</head>
  <body>
  <div id="map1" style="width:1024px;height:800px"></div> 
  <div id="testContent"></div>
  
  <select id="zdrytype">
						 <option value='headerSKRY' name="1">涉恐人员</option>
					     <option value='headerSWRY' name="2">涉稳人员</option>
					     <option value='headerSDRY' name="3">涉毒人员</option>
					     <option value='headerZDXSFZQKRY' name="4">重大刑事犯罪前科人员</option>
					     <option value='headerZSZHJSBR' name="5">肇事肇祸精神病人员</option>
					     <option value='headerZDSFRY' name="6">重点上访人员</option>
					     <option value='headerZTRY' name="7">在逃人员</option>
					</select>
  </body>
</html>
