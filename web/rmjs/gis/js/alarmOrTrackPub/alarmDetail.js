function getXZQH(glbm){
	var xzqh="";
	$.ajax({
	  url:basePath+ "alarmOrTrackPub.gis?method=getDistrict",
	  data:{glbm:glbm},
	  dataType:'text',
	  success: function(data) {
	  	if(!data){
	  		return;
	  	}
	  	var arr=eval(data);	  
	  	if(arr.length>0){
	  		var value=arr[0]["value"];
	  		xzqh=value;
	  		return xzqh;
	  	}
	  }
	 });
}

/**
 * 获得当前卡口的最新预警信息
 */
function getLatestAlarmInfo(){
	//【1】获得最新预警信息参数
	//获得预警相关的参数
	var realAlarmParamters={
		"xzqh":"",//行政区划
		"hpzl":"",//号牌种类
		"hphm":"",//号牌号码
		"bklx":"",//布控类型
		"kkbh":"",//布控卡口
		"kksj":""//开始时间
	};
	
	//获得行政区划 为管理代码的前六位
	var xzqValue=getXZQH(alarmGlbm);
	realAlarmParamters.xzqh=xzqValue;//340204000000 ;
	realAlarmParamters.hpzl=getURLParamValue("hpzl")==null?"":getURLParamValue("hpzl");
	var hphmVal=getURLParamValue("hphm")==null?"":getURLParamValue("hphm");
	realAlarmParamters.hphm=encodeURI(hphmVal);
	realAlarmParamters.bklx=getURLParamValue("bklx")==null?"":getURLParamValue("bklx");
	//此次仅仅获得当前点击的单一卡口
	realAlarmParamters.kkbh=getURLParamValue("kkbh")==null?"":getURLParamValue("kkbh");
	var curDate=new Date().Format("yyyy-MM-dd hh:mm:ss");  	
	realAlarmParamters.kksj=curDate;//每隔5秒获得当前卡口的最新预警信息
	//【2】调用Ajax获得预警信息结果	
	$.ajax({
		url : basePath + "alarmOrTrackPub.gis?method=getAlarmQyeryDetail",
		data : realAlarmParamters,
		dataType : 'text',
		success : function(data) {	
			var latestWarnInfo = $.evalJSON(data);			
			$("#hpzl").text(latestWarnInfo.HPZLMC);
			$("#hphm").text(latestWarnInfo.HPHM);
			$("#hpys").text(latestWarnInfo.HPYS);
			$("#qszt").text(latestWarnInfo.QSZT);
			$("#qsr").text(latestWarnInfo.QSR);
			$("#qssj").text(latestWarnInfo.QSSJ);
			$("#fkzt").text(latestWarnInfo.FKZT);
			$("#fkr").text(latestWarnInfo.FKR);
			$("#fksj").text(latestWarnInfo.FKSJ);
			$("#yjsj").text(latestWarnInfo.YJSJ);
			$("#ljzt").text(latestWarnInfo.LJZT);
		}
	});	
}