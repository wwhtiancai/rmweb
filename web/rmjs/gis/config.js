var mapClientUrl=basePath+"gisServer.gis?method=mapclient";
var mapDataUrl=basePath+"gisServer.gis?method=mapData";
//var mapClientUrl="http://10.35.18.56/PGIS_S_TileMapServer/Maps/PGIS_SL";
//var mapDataUrl="http://10.2.43.115:8080/Nasoft_MapDataForORCL/mapData/serviceSupply.jsp?isProxy=true&proxyUrl=/TGISProxy.do";
var RMICGISConfig={    
	MapJs:[mapClientUrl,mapDataUrl],
    _mapHost: '',
	PositionLayers:{"XZQH":"GIS_XZQH_VECTOR","GSGS_DL":"GIS_ROAD_VECTOR"},
	LoadMapApi : function(mapHost){
		RMICGISConfig._mapHost= mapHost || "";		
		RMICGISConfig.LoadJs(RMICGISConfig.MapJs,""); 
	},
	LoadJs : function(files,basePath){
		var fileArray=[];
		typeof files=='string'?fileArray[0]=files:fileArray=files;
		var allScriptTags="";
		var head = document.getElementsByTagName("head")[0];
		var version=new Date().getTime();//todo
		for(var i=0;i<fileArray.length;i++){			
			var url=basePath + fileArray[i]; 
			if(url.indexOf("?")!=-1){
				url=url+"&"+version;
			}else{
				url=url+"?"+version;
			}
			if (/MSIE/.test(navigator.userAgent) || /Safari/.test(navigator.userAgent)) {
                allScriptTags += "<script src='" + url +"'"+""+"></script>";
			}else{
				var script = document.createElement("script");
				script.src = url;
				head.appendChild(script);
			}
		}
		if (allScriptTags) document.write(allScriptTags);
	},
	LoadCss : function(files,basePath){
		var fileArray=[];
		typeof files=='string'?fileArray[0]=files:fileArray=files;
		var allScriptTags="";
		var head = document.getElementsByTagName("head")[0];
		for(var i=0;i<fileArray.length;i++){
			var url=basePath + fileArray[i];
			if (/MSIE/.test(navigator.userAgent) || /Safari/.test(navigator.userAgent)) {
               // allScriptTags += "<script src='" + url +"'"+""+"></script>";
                 allScriptTags += "<link rel='stylesheet' type='text/css' href='"+url+"'/>";
                 alert(allScriptTags);
			}else{
				var script = document.createElement("link");
				script.rel = url;
				head.appendChild(script);
				alert(url);
			}
		}
		if (allScriptTags) document.write(allScriptTags);
	},
	getImageUrl : function(imgName){
		return gisapi._mapHost+'lib/images/'+imgName;
	}
};