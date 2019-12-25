var alarmMarkers=[];
var curClickMarker=null;
var isStopRefresh=false;//�Ƿ�ֹͣ�������Ԥ����Ϣ
var passPicturesBaseUrl=basePath+"readPassPic.tfc?method=getPassPic&gcxh=";
var tollgatePicUrl=basePath+"alarmOrTrackPub.gis?method=getTollgatePicture";
var labelShowFields={ 
		 KKMC:"��������",
		 GLBMMC:"������",
		 KKLXMC:"��������"	 
};

var yjtabsConfig = [ {
	title : "����Ԥ������",
	href : basePath + 'alarmOrTrackPub.gis?method=alarmDetail',
	reqParams:""
}, {
	title : "Ԥ����Ϣ��ѯ ",
	href : basePath + 'alarmOrTrackPub.gis?method=alarmQuery',
	reqParams:""
},{
	title : "������Ϣ��ѯ ",
	href : basePath + 'alarmOrTrackPub.gis?method=passInfo',
	reqParams:""
}, {
	title : "�������� ",
	href : basePath + 'alarmOrTrackPub.gis?method=tollgateInfo',
	reqParams:""
}, {
	title : "���ڹ���״̬ ",
	href : basePath + 'alarmOrTrackPub.gis?method=tollgateStatus',
	reqParams:""
} ];

/**
 * ����ͼƬ����
	 * 02	ʡ�ʿ���
03	�мʿ���
04	�ؼʿ���
05	��·���߿���
06	��·�շ�վ����
07	������·����
08	����·�ڿ���
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
 * ��䵱ǰ�û�Ԥ�����ڵ�ǰ��ҳ��֮��
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
 * ʵʱԤ������
 */
function fillRealAlarmCondition(){			
	//��1�����������ൽҳ��֮��
	fillHPZLToPage("hpzl_select",hpzlList);
	//��2����䲼�����͵�ҳ��֮��
	fillBKLXToPage("td_bklx", bklxList);
	//��3����䵱ǰ�û��µ�Ԥ��Ԥ������
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

//���Ԥ����صĲ���
var realAlarmParamters={
	hpzl:"",//��������
	hphm:"",//���ƺ���
	bklx:"",//��������
	kkbh:""//���ؿ���	
};

function getSelectedAlarmTollgates(){
	var selectedTollgates=AppUtils.getCheckBoxValue("cbTollgates");
	return selectedTollgates;
}

/**
 * ���Ԥ����ص�����
 * @return
 */
function getRealAlarmParameters(){
	var hpzlVal=$("#hpzl_select").combobox("getValue");//��������	
	if(hpzlVal=="no"){
		hpzlVal="";
	}
	realAlarmParamters.hpzl=hpzlVal;
	var hphmVal=$("#hphm_text").val();//���ƺ���
	realAlarmParamters.hphm=encodeURI(hphmVal);
	var values=$("#td_bklx").divFilter("getValue");
	var bklxValues="";
	//�Ѳ������͵�ֵƴ��Ϊ�ַ���
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
	//����Ѿ����صĿ��ڱ��
	var kkbhValues=getSelectedAlarmTollgates();	
	realAlarmParamters.kkbh=kkbhValues.join(",");
	return realAlarmParamters;
}

/**
 * ˢ�²���Ԥ����Ϣ
 */
function refreshBkInfo(){	
	getRealAlarmParameters();
	//�ж���֤һ�� ������ؿ���Ϊ�ղ�ִ�в���
	/**
	if(realAlarmParamters.kkbh.length==0){
		$.messager.alert("��ʾ��Ϣ", "��ѯ����Ԥ�����ڲ���Ϊ�գ�", "info");
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
 * ���ˢ��������
 * @param reqUrl ����Ļ���url
 * @param reqParams ����Ĳ���
 * @param freshMillisecond ÿ��ˢ�µ�ʱ�� ��λ����
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
			//���������֮�����»��������			
			setTimeout(function() {
				// ��1���ڵ�ͼ����ʾ����Ԥ���ĳ��� ���￨�ڲ��ڵ�ͼ����ʾ
				refreshAlarmInfo(reqUrl,reqParams,freshMillisecond);
			}, freshMillisecond);
		}
	});
}

function stopRefreshBKInfo(){	
	document.getElementById("startRefreshBtn").disabled=false;
	document.getElementById("stopRefreshBtn").disabled=true;
	//alert("ֹͣ���Ԥ����Ϣ");
	isStopRefresh=true;
	//�������˸Ч����
	for(var i=0;i<alarmMarkers.length;i++){
		alarmMarkers[i].clearFlash();
	}	
}

/**
 * �Ƴ�Ԥ��Markers
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
 * ��ʾ����Ԥ����Ϣ����ͼ��
 * @param rows
 */
function showAlarmInfoToMap(rows){
	//�������Ԥ��Markers
	removeAllMarkers();	
	//��1�����Ԥ��������Ϣ��ʾ����ͼ��
	for(var i=0;i<rows.length;i++){
		var kkjd=rows[i].KKJD;//Ԥ�����ھ���
		var kkwd=rows[i].KKWD;//Ԥ������γ��
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
			//[****]��õ�ǰ��ǰ�򿪵�������Ϣ ���Ҹ��µ�ǰ���ݵ�������������		
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
*��ʾ���µ�Ԥ����Ϣ���ұߵ�ҳ��
* @param data
*/
function showWarnInfoClientPagination(data) {		
	var content = slidebarControl.getContentDiv();
	//$(content).empty();
	slidebarControl.slideOut();
	if (!$("#yjdg").get(0)) {	
		var titleHtml="<span id='yjtitle' style='color:red;font-weight:bolder;margin-top:7px;";
		titleHtml+="height:20px;width:100%;font-size:12px;text-align:center;'>����Ԥ����Ϣ</span>";
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
			title : "���س��ƺ���"
		}, {
			field : "KKMC",
			title : "Ԥ����������",				
			align : "left"
		},{
			field : "BKLXMC",
			title : "��������",				
			align : "left",
			hidden:true
		},
		{
			field : "YJSJ",
			title : "Ԥ��ʱ��",				
			align : "left"
		},{
			field : "FXBM",
			title : "�������",				
			align : "left"
		}]],			
		data: data,			
		onClickRow : function(rowIndex, rowData) {
			var kkjd=rowData.KKJD;//Ԥ�����ھ���
			var kkwd=rowData.KKWD;//Ԥ������γ��
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
				$.messager.alert("��ʾ��Ϣ", "������������꣬�޷���λ��", "info");
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
		afterPageText : "ҳ �� {pages} ҳ",
		displayMsg : "�� {total} ��",
	    buttons: [{
			iconCls:'icon-markers-to-map',
			title:"���п���λ�õ���ͼ",
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
 * ����Mark��ʾ��Ϣ
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
	 //��ӿ���Ԥ����Ϣͳ��
	 /**
	 tableHtml+="<tr><td>����Ԥ����:</td><td style='color:red;font-weight:bolder;'>10</td></tr>";
	 tableHtml+="<tr><td>δǩ��Ԥ����:</td><td style='color:red;font-weight:bolder;'>5</td></tr>";
	 tableHtml+="<tr><td>δ����Ԥ����:</td><td style='color:red;font-weight:bolder;'>4</td></tr>";	
	 */
	 tableHtml+="</table>";
	 //����Ԥ����Ϣ��ʾ
	var warnInfoHtml="<fieldset style='font-size:12px;width:360px;border:solid #1c69b9 1px;'>";
	warnInfoHtml+="<legend style='color:red;font-weight:bolder;'>����Ԥ����Ϣ</legend>";
	warnInfoHtml+="<table style='font-size:12px;' width='100%'>";
	warnInfoHtml+="<tr><td>��������:</td><td>С����</td></td><td valign='top' style='text-align:right;' rowspan='5'>";
	warnInfoHtml+="<img onclick='openBigPic(this)' baseUrl='"+passPicturesBaseUrl+datas["GCXH"]+"&width=100&height=100";
	warnInfoHtml+=" style='border:solid 1px;cursor:hand' width='100px' heigh='100px' ";
	warnInfoHtml+=" src='"+passPicturesBaseUrl+datas["GCXH"]+"&width=100&height=100'/></td></tr>";
	warnInfoHtml+="<tr><td>���ƺ���:</td><td>"+datas["HPHM"]+"</tr>";
	warnInfoHtml+="<tr><td>��������:</td><td>"+datas["BKLXMC"]+"</td></tr>";
	warnInfoHtml+="<tr><td>Ԥ��ʱ��:</td><td>"+datas["YJSJ"]+"</td></tr>";	
	warnInfoHtml+="<tr><td>ǩ��״̬:</td><td style='color:red;font-weight:bolder;'>δǩ��</td></tr>";	
	//�������Ԥ����Ϣ����Ĳ��� *** �˴���Ҫmarker ����֮�л��
	var hphm=datas["HPHM"];//��ú��ƺ���
	var hpzl=datas["HPZL"];//��ú�������
	var bklx=datas["BKLX"];//��ò�������
	//����Ԥ���������
	var alarmDetailParams="&hpzl="+hpzl+"&hphm="+hphm+"&bklx="+bklx;
	yjtabsConfig[0]["reqParams"]=alarmDetailParams;		
	warnInfoHtml+="<tr><td colspan='3' style='text-align:center;'><a onClick='AppUtils.openInfoWindow("+objStr+",0,"+ObjectToStr(yjtabsConfig)+")' href='#'>����Ԥ������</a>&nbsp;</td></tr>";
	//&nbsp;<a onClick='testForecast()' href='#'>�켣Ԥ��</a>
	warnInfoHtml+="</table></fieldset><br>";	
	tableHtml+=warnInfoHtml;
	//�����·ʾ��
	 //alert(node.attributes.roads.length);	 
	 if(datas.roads&&datas.roads.length!=0){
		 var sketchMap=new SketchMap({
			 direction:datas.sxfxbm?datas.sxfxbm:datas.SXFXBM,
			 width:360,
	    	  roads:datas.roads
	      });
		 tableHtml+=sketchMap.getHTML();
	 }	 
	 
	 //��Թ�����Ϣ����Ĳ���
	 var passInfoParams="&hpzl="+hpzl+"&hphm="+hphm;
	 yjtabsConfig[2]["reqParams"]=passInfoParams;
	//���빦�ܰ�ť
	var btnHtml="<span style='height:10px;'></span><div style='font-size:12px;text-align:center;width:345px;position:relative'>";	
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",1,"+ObjectToStr(yjtabsConfig)+")' href='#'>Ԥ����Ϣ��ѯ</a>&nbsp;&nbsp;";
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",2,"+ObjectToStr(yjtabsConfig)+")' href='#'>������Ϣ��ѯ</a>&nbsp;&nbsp;";
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",3,"+ObjectToStr(yjtabsConfig)+")' href='#'>��������</a>&nbsp;&nbsp;";
	btnHtml+="<a onClick='AppUtils.openInfoWindow("+objStr+",4,"+ObjectToStr(yjtabsConfig)+")' href='#'>���ڹ���״̬</a></div>";	
    var infor=tableHtml+btnHtml;
	return infor;
}

//�򿪿��ڵķŴ�ͼƬ
function openBigPic(objImg){
	//alert(objImg.baseUrl);
	var maxImgControl=new MaxImgControl({imgUrl:objImg.baseUrl});
	maxImgControl.show();
}
