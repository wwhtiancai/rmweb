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
 * ��õ�ǰ���ڵ�����Ԥ����Ϣ
 */
function getLatestAlarmInfo(){
	//��1���������Ԥ����Ϣ����
	//���Ԥ����صĲ���
	var realAlarmParamters={
		"xzqh":"",//��������
		"hpzl":"",//��������
		"hphm":"",//���ƺ���
		"bklx":"",//��������
		"kkbh":"",//���ؿ���
		"kksj":""//��ʼʱ��
	};
	
	//����������� Ϊ��������ǰ��λ
	var xzqValue=getXZQH(alarmGlbm);
	realAlarmParamters.xzqh=xzqValue;//340204000000 ;
	realAlarmParamters.hpzl=getURLParamValue("hpzl")==null?"":getURLParamValue("hpzl");
	var hphmVal=getURLParamValue("hphm")==null?"":getURLParamValue("hphm");
	realAlarmParamters.hphm=encodeURI(hphmVal);
	realAlarmParamters.bklx=getURLParamValue("bklx")==null?"":getURLParamValue("bklx");
	//�˴ν�����õ�ǰ����ĵ�һ����
	realAlarmParamters.kkbh=getURLParamValue("kkbh")==null?"":getURLParamValue("kkbh");
	var curDate=new Date().Format("yyyy-MM-dd hh:mm:ss");  	
	realAlarmParamters.kksj=curDate;//ÿ��5���õ�ǰ���ڵ�����Ԥ����Ϣ
	//��2������Ajax���Ԥ����Ϣ���	
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