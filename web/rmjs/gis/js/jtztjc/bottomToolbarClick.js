var tipWinHasInitial=false;//�ж�window�Ƿ��Ѿ���ʼ��
var showWindowTipId="";//����������ʾ������tipId ���ڴ�������ʱʹ��
var prevClickJtBtnId="jtll";//��һ�ε����ͨ������ťid Ĭ��ֵΪ��ͨ����
var jtzdMarkersArray=[];//ÿ�ཻͨ���Markers
var qtzdSelectedSjlx="";//��¼������ϵ�ѡ�������

//ÿ����Դ��������һ����Ӧ�Ķ�̬����
function createOrReplaceTipContent(tipTitle,tipHref,width,height){	
	//��2������һ��window֮�е�href�����Ƿ���Ϊ��ǰҳ������
	if(tipWinHasInitial){
		$("#tipConditionWin").window({
			title:tipTitle,
			width:width,
			height:height,
			href: tipHref
		});
	}else{
		$("#tipConditionWin").window({
		title:tipTitle,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		draggable:false,
		resizable:false,
		width:width,
		height:height,
		href: tipHref
		});	
		tipWinHasInitial=true;
	}
	//$("#tipConditionWin").window("refresh");
}

function fillTipWindowContent(tipId) {
	showWindowTipId=tipId;
	switch (tipId) {
	case "zbjczy":// �ܱ߻�����Դ
		createOrReplaceTipContent("�ܱ߻�����Դ","jtztjc.gis?toolTip=zbjczy",280,150);
		break;
	case "zbjlzy":// ������Դ
		createOrReplaceTipContent("������Դ","jtztjc.gis?toolTip=zbjlzy",280,150);	
		break;
	case "jtll":// ��ͨ����
		createOrReplaceTipContent("��ͨ����","jtztjc.gis?toolTip=jtll",320,120);
		break;
	case "qtzdsj":// ��������¼�
		createOrReplaceTipContent("�������","jtztjc.gis?toolTip=qtzdsj",170,160);
		break;
	}
}

/**
 * ���·��������İ�ť���ʱ�����ݲ�ͬ�İ�ť���ز�ͬ��tip
 */

$(function(){
	//-------*�������¼�*--------
	$('#toolgjx').tooltip({
        content: $('<div></div>'),
        showEvent: 'click',
        onUpdate: function(content){
            content.panel({
            	width: 36,
                border: false,
                href: 'jtztjc.gis?toolTip=maptool'
            });
        },
        onShow: function(){
            var t = $(this);
            t.tooltip('tip').unbind().bind('mouseenter', function(){
                t.tooltip('show');
            }).bind('mouseleave', function(){
                t.tooltip('hide');
            });
        }
    });	
});

function hideOtherTip(buttonId){//����btnCtr֮������пؼ���   tip��ʾ��
	var ctrs=new Array("zbjczy","zbjlzy","jtll","dlsg","jtsg","eltq","qtzdsj"); 
	for(var i=0;i<ctrs.length;i++){
		if(buttonId=ctrs[i]){
			var ctrname="#"+ctrs[i];
			var t = $(ctrname);
        	t.tooltip('hide');
		}
	}
}

var leftToolBarsStatus= [ {
	"btnId" : "zbjczy",
	"status" : "off"//�ܱ���Դ
}, {
	"btnId" : "zbjlzy",
	"status" : "off"//�ܱ߾�����Դ
}];

var rightToolBarsStatus =[{
	"btnId" : "jtll",
	"status" : "off"//��ͨ����
}, {
	"btnId" : "dlsg",
	"status" : "off"//��·ʩ��
}, {
	"btnId" : "jtsg",
	"status" : "off"//��ͨ�¹�
},{
	"btnId" : "eltq",
	"status" : "off"//��������
}, {
	"btnId" : "qtzdsj",
	"status" : "off"//��������¼�
}];

function setToolBarCss(toolId,toolBarsStatus){
		var setCssBtnId="";
		var setCssIndex=0;
		//���õ�ǰ������ť��ʽ������ұ�������ťѡ����ʽ����ʾ����
		for(var i=0;i<toolBarsStatus.length;i++){
			var toolBarId=toolBarsStatus[i].btnId;
			if(toolId==toolBarId){
				setCssBtnId=toolId;	
				setCssIndex=i;
			}else{
				//����Ϊ�ر�״̬����ʽ
				$("#"+toolBarId).addClass(toolBarId+"Off").removeClass(toolBarId+"On");	
				toolBarsStatus[i].status="off";
				$("#"+toolBarId).attr("status","off");
			}
		}
		if(setCssBtnId!=""){
			//��������ʾ��������ToolTip
			if($("#"+setCssBtnId).attr("status")=="off"){
				if(!((setCssBtnId=="dlsg")||(setCssBtnId=="jtsg")||(setCssBtnId=="eltq"))){
					fillTipWindowContent(setCssBtnId);//�������滻window����
					var left=$("#"+setCssBtnId).offset().left;
					//���window�ĸ߶�
					var winHeight=$('#tipConditionWin').window("options").height;
					var top=$("#"+setCssBtnId).offset().top-(winHeight+30);
					$("#tipConditionWin").window({left:left,top:top,collapsed:false,collapsible:false,draggable:false,closed:false,closable:true});
					$("#tipConditionWin").window("open");
				}
				$("#"+setCssBtnId).addClass(setCssBtnId+"On").removeClass(setCssBtnId+"Off");				
				toolBarsStatus[setCssIndex].status="on";	
				$("#"+setCssBtnId).attr("status","on");
			}else{
				if(!((setCssBtnId=="dlsg")||(setCssBtnId=="jtsg")||(setCssBtnId=="eltq"))){
					$("#tipConditionWin").window("close");
				}
				toolBarsStatus[setCssIndex].status="off";
				$("#"+setCssBtnId).attr("status","off");
				$("#"+setCssBtnId).addClass(setCssBtnId+"Off").removeClass(setCssBtnId+"On");		
			}
		}
}

function showTrafficStateListContent(listIndex){
	var menuArray = ['0','1','2','3','4'];
	$("#firstpane .menu_head:eq("+listIndex+")").addClass("current").next("div.menu_body");
	$("#firstpane .menu_head:eq("+listIndex+")").siblings().removeClass("current");
	$("#firstpane .menu_body:eq("+listIndex+")").slideDown();
	for(var i=0; i<menuArray.length; i++){
		if(menuArray[i]!=listIndex){
			$("#firstpane .menu_body:eq("+menuArray[i]+")").slideUp();
		}
	}
	/*
	//�����ұߵ���Ϣ�б�
	$("#firstpane .menu_head:eq("+listIndex+")").addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
	$("#firstpane .menu_head:eq("+listIndex+")").siblings().removeClass("current");
	$("#firstpane .menu_body:eq("+listIndex+")").slideDown();
	*/
}
function getQueryParam(){
	var param={};
	var xzqhChecked = getSelectedDistrictTreeIds('selectXzqhTreeDiv');	       
	/*
	if(xzqhChecked.length>0){
		//alert(bmjb+"<----param.glbms-->"+xzqhChecked);
		if(bmjb=="1"||bmjb=="2"){
			param.glbms=xzqhChecked;
		}else{
			param.xzqhs = xzqhChecked;
		}		
	}else{		
		if(bmjb=="1"||bmjb=="2"){
			param.glbms="";
		}else{
			param.xzqhs ="";
		}
	}*/
	if(xzqhChecked.length>0){
		param.xzqhs = xzqhChecked;
	}else{
		param.xzqhs = "";
	}
	var roadChecked = getSelectedDistrictTreeIds('selectRoadTreeDiv');

	if(prevClickJtBtnId==""){
		curSelectedRoadNodes="";
	}else{
		curSelectedRoadNodes=roadChecked;
	}
	param.dldms =roadChecked ; //��õ�·��ѡ	 roadChecked
	param.geo=drawPointOrPolylineWkt;//�ռ��ѯ���Ƶĵ㡢��WKT
	param.wkt=drawBufferWkt;//���ƺ�Ļ�������WKT
	param.sjlx=zbzylx;//��������
	param.curSelectedRoad=curSelectedRoadNodes;
	if(bmjb=="1"){
		param.sjbj="1";
	}else{
		//param.sjbj="0";
		param.sjbj=$("input[name='roadBj']:checked")[0].value;
		//param.sjbj=$("input[name='roadBj']:checked")[0].value;
	}
	return param;
}

function clearResultHtml(valbtn){
	if(valbtn){
		$(("#"+valbtn)).html("");
	}
	/*
	$("#result_jtll").html("");
	$("#result_dlsg").html("");
	$("#result_jtsg").html("");
	$("#result_eltq").html("");	
	$("#result_qtzd").html("");*/
}
function bottomToolbarClick(buttonId){  //����·���ť��ѯ	
	clearResultHtml(buttonId); //��ս���б�� html
	clearTimer(); //ȥ����ѯ��ʱ��״̬
	hideOtherTip(buttonId);//����ؼ���ʱ���Ȱ����������ؼ���tip����.�÷�����:--**���ļ���**--
	//clickRoadTree(); //ˢ�µ�·��
	hideTreeDialog(); //������	
	
	if(buttonId!="zbjczy" || buttonId!="zbjlzy"){
		if(prevClickJtBtnId!=buttonId){
			canleCheck();
			prevClickJtBtnId=buttonId;
			curSelectedRoadNodes="";
		}else {
			if((prevClickJtBtnId==buttonId)&&($("#"+buttonId).attr("status")=="off")){
				curSelectedRoadNodes="";
				prevClickJtBtnId="";
			}
		}
	}
	var param = getQueryParam();
	if(buttonId=="zbjczy"){
		spatialSearchType="jczy";
		//�ڵ��������Դʱ�� ��ʼ����Դselect
		var selectVal=$("#jczy_id option:selected").attr("value");		
		if(selectVal==undefined){
			setTimeout(function(){
				fillZySelect();
			},500);
		}	
		reoveBlockMouduleDraw();
		if($("#"+buttonId).attr("status")=="off"){
			//����Ѿ����ƻ���߽�
			removeDrawOverlay();
			//�ر���Դ��ʾ����
			showOrCloseResourceWin(false);
		}
	}else if(buttonId=="zbjlzy"){
		spatialSearchType="jlzy";
		reoveBlockMouduleDraw();		
		if($("#"+buttonId).attr("status")=="off"){
			//����Ѿ����ƻ���߽�
			removeDrawOverlay();
			//�ر���Դ��ʾ����
			showOrCloseResourceWin(false);
		}
	}else if(buttonId=="jtll"){
		//����Ѿ����ƻ���߽�
		reoveBlockMouduleDraw();
		removeClusterMarkers();//�����ؾۺ���
		showOrHideRoadItem();
		if($("#"+buttonId).attr("status")=="off"){			
			//�ر���Դ��ʾ����
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
		}else{			
			currentClickShower = buttonId;  
			showTrafficStateListContent(0);//��ͨ������ť����¼�.�÷�����:--**���ļ���**--
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
		}		
	}else if(buttonId=="dlsg"){
		//����Ѿ����ƻ���߽�
		reoveBlockMouduleDraw();
		removeClusterMarkers();//�����ؾۺ���
		$("#tipConditionWin").window("close");
		if($("#"+buttonId).attr("status")=="off"){
			//�ر���Դ��ʾ����
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//�ұ���ʾ������𵽳�ʼ״̬
		}else{
			currentClickShower = buttonId; 
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
			param.sjlxs = "A2";
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;

			getLatestBlockedSingle(param,"result_dlsg");		
			
			showTrafficStateListContent(1);//��·ʩ����ť����¼�.�÷�����:--**���ļ���**--			
	    }		
	}else if(buttonId=="jtsg"){
		//����Ѿ����ƻ���߽�
		reoveBlockMouduleDraw();
		removeClusterMarkers();//�����ؾۺ���
		$("#tipConditionWin").window("close");
		if($("#"+buttonId).attr("status")=="off"){
			//�ر���Դ��ʾ����
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//�ұ���ʾ������𵽳�ʼ״̬
		}else{
			currentClickShower = buttonId; 
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
			param.sjlxs = "A3";
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;

			//�����̨
			getLatestBlockedSingle(param,"result_jtsg");		
			
			showTrafficStateListContent(2);//��ͨ�¹ʰ�ť����¼�.�÷�����:--**���ļ���**--
		}	
		
	}else if(buttonId=="eltq"){
		//����Ѿ����ƻ���߽�
		reoveBlockMouduleDraw();
		removeClusterMarkers();//�����ؾۺ���
		$("#tipConditionWin").window("close");
		if($("#"+buttonId).attr("status")=="off"){
			//�ر���Դ��ʾ����
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//�ұ���ʾ������𵽳�ʼ״̬
		}else{
			currentClickShower = buttonId; 
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;
			param.sjlxs = "A4";
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;
			
			//�����̨
			getLatestBlockedSingle(param,"result_eltq");					
			showTrafficStateListContent(3);//����������ť����¼�.�÷�����:--**���ļ���**--
		}			
	}else if(buttonId=="qtzdsj"){	
		//����Ѿ����ƻ���߽�
		reoveBlockMouduleDraw();
		removeClusterMarkers();//�����ؾۺ���
		if($("#"+buttonId).attr("status")=="off"){			
			//�ر���Դ��ʾ����
			$("#result_"+buttonId).html("");
			currentClickShower = ""; 
			eventRecord="";
			showTrafficStateListContent(4);//�ұ���ʾ������𵽳�ʼ״̬
		}else{
			DMap.$(map).unbind('zoomend.jtllZoomend');
	    	ifAddListener = false;
			setTimeout(function(){
				showOtherBlock();			
			}, 500);
			currentClickShower = buttonId; 
			var sjlxStr = "";
			var otherBlockCheckedArray = $("input[name='otherBlockName']:checked");	
			for(var i=0; i<otherBlockCheckedArray.length; i++){
				if(i!=0){
					sjlxStr+=",";
				}
				sjlxStr+=otherBlockCheckedArray[i].id;
			}
			param.sjlxs = sjlxStr;
			eventRecord=param.xzqhs+","+param.dldms+","+param.sjlx+","+param.sjbj+","+param.dldms+","+currentClickShower;			
			showTrafficStateListContent(4);//����������ť����¼�.�÷�����:--**���ļ���**--
		}
	};
}

/**
 * 
 * @param valparam
 * @param resultAccordion
 * @param sjlxs
 * @return
 */

var  queryParam = null;
function getLatestBlockedSingle(valparam,resultAccordion){	
	queryParam =valparam;
	showLoad();
	$.ajax({
		url:basePath+"jtztjc.gis?method=getLatestBlockedSingle",
		data: valparam,
		type:"post",
		dataType:"json",
		async:false,
		cache:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){	
			if(data.length==0){
				reoveBlockMouduleDraw();//�������marker
				setTimeout(function(){	        		
	        		closeLoad();
	        	}, 500);	
				$(("#"+resultAccordion)).html("");
			}else{
				blockCommonInfo(data,resultAccordion,valparam.sjlxs);					
			}					
		},error:function(){
			closeLoad();
		}
	});	
}

function queryOtherBlockData(){
	
	showLoad();
	//�����̨
	var param = getQueryParam();
	var otherBlockCheckedArray = $("input[name='otherBlockName']:checked");	
	var sjlxStr = "";
	for(var i=0; i<otherBlockCheckedArray.length; i++){
		if(i!=0){
			sjlxStr+=",";
		}
		sjlxStr+=otherBlockCheckedArray[i].id;
	}
	param.sjlxs = sjlxStr;
	/**
	if(sjlxStr!=""){
		sjlxStr="A5,A6,A7,A8,A9";
	}
	*/
	eventRecord=param.xzqhs+","+param.dldms+","+param.sjlxs+","+param.sjbj+","+param.dldms+","+currentClickShower;
	$(function(){
		$.ajax({
			url:basePath+"jtztjc.gis?method=getLatestBlockedMul",
			type:"post",
			data: param,
			dataType:"json",
			async:false,
			cache:false,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			success:function(data){
				if(data.length==0){
					reoveBlockMouduleDraw();//�������marker	
					$("#result_qtzd").html(""); 
					closeLoad();
				}else{								
		        	setTimeout(function(){	 
		        		blockCommonInfo(data,"result_qtzd",param.sjlxs);		        		
		        	}, 500);	
				}
			}
		});
	});
}

function blockCommonInfo(data,blockResultId,sjlxs){
	closeLoad();
	reoveBlockMouduleDraw();//�������marker
	var zoom = map.getZoom();
	var htmlStr = "<div>";
	var minLon=0,maxLon=0,minLat=0,maxLat=0;
    for(var i=0; i<data.length; i++){        	
    	var id = data[i].id;
    	var sxh = data[i].sxh;
    	var xxlx = data[i].xxlx;
    	var sjnr = data[i].sjnr;
    	var sjjs= data[i].sjjs;
    	var xxly = data[i].xxly;
    	var geo = data[i].geo;
    	var marker = drawGeo(geo,xxlx,id);//���Ƶ㡢��	    	
    	var jd=marker.lonlat.lon;
    	var wd=marker.lonlat.lat;
    	 if(jd!=""&&wd!=""){
    		 marker.lonlat=new DMap.LonLat(jd,wd);
				if(minLon==0){
					minLon = jd;
			     	minLat = wd;
			     	maxLon = jd;
			     	maxLat = wd;
				}else{
				 if(maxLon<jd){
			        maxLon = jd;
			      }             	
			      if(maxLat<wd){
			        maxLat = wd;
			      }
			 	if(minLon>jd){
		           minLon = jd;
		         }	
			 	if(minLat>wd){
			       minLat = wd;
			    } 
				} 
		}
    	if(zoom>9){
        	//var marker = drawGeo(geo,xxlx,id); //���Ƶ㡢��	   
        	map.addOverlay(marker);
        	drawMarkerArray.push(marker);	
        	//�Ի�ò�ѯ�����ͼ����	        
    	}
    	if(i<=10){
    		htmlStr += "<a href='#' style='font-size:12px;' onclick='panToGeo(\""+geo+"\",\""+id+"\")' title='"+sjnr+"'>"+sjjs+"</a>";        		
    	}
    }       
	if(data.length>9){
		htmlStr+="<a href='#' style='font-size:12px;padding-left:200' onclick=javascript:gis_block_list(\'"+sjlxs+"\');return false;>������Ϣ</a>";
	}
    htmlStr+="</div>";
    $("#"+blockResultId).html(htmlStr);
    
	
	/**
	 * �������¹�����ʾ�������
	 * 1��8��������ʡΪ���Ķ�λͳ�ƽ��ͼ��
	 * 2��8-9���� ����Ϊ����ͳ�ƽ�� ����ʾͼ��
	 * 3��10������ �ۺ���ʾ������� 
	 * 4�����ÿ��ͳ������ʱ���Ե�ǰ��λ����Ϊ��ͼ���ĵ�
	 */
	if(zoom<8){//�ܶӵ�1����ת(ͳ��ʡ����Ϣ)
		queryBlockCenterCombine('0');
	}else if(zoom==8||zoom==9){//�ܶӵ�2����ת (ͳ���м���Ϣ)
		queryBlockCenterCombine('1');
	}else{
	    if(minLon>0&&maxLon>0&&minLat>0&&maxLat>0){
		    var minx = parseFloat(minLon)-0.2;
	        var miny = parseFloat(minLat)-0.2;
	        var maxx = parseFloat(maxLon)+0.2;            
	        var maxy = parseFloat(maxLat)+0.2;		           
	        var statCenterX=0.5*(parseFloat(minLon)+parseFloat(maxLon));
	        var statCenterY=0.5*(parseFloat(minLat)+parseFloat(maxLat));
	        var statCenter=new DMap.LonLat(statCenterX,statCenterY);  
	        var curLevel=map.getZoom();
	        if(curLevel==8||curLevel==9){
	        	 //map.setCenter(statCenter,curLevel);
	        	 queryBlockCenterCombine('1');
	        }else if((curLevel>=10&&curLevel<=13)){	
	        	//map.setCenter(statCenter,curLevel);
	        	drawBlockMarkersFunc(true);
	        	//map.centerMBR(new DMap.LonLatBounds(minx,miny,maxx,maxy));            	
	        }else{
	        	drawBlockMarkersFunc(false);
	        	//map.setCenter(statCenter,curLevel);
	        }         
	    }else{
	    	if(maxLon>0&&maxLat>0){
				var statCenter=new DMap.LonLat(maxLon,maxLat);
				//map.setCenter(statCenter,10);
			}
	    }
    }
    zoomVal=0;
    //����󶨵�ͼ
    if(!ifAddBlockListener){
    	ifAddBlockListener = true;
		 DMap.$(map).bind('zoomend.blockInfo',function(){
			 if(currentClickShower==""){
			    return;
			 }
		     var zoom = map.getZoom();
		     switch(true){
				case zoom<7://����(ͳ��ʡ����Ϣ)			         
		        	 reoveBlockMouduleDraw();
		        	 queryBlockCenterCombine('0');	
					break;
				case zoom<8://�ܶӵ�1����ת(ͳ��ʡ����Ϣ)
					reoveBlockMouduleDraw();
					queryBlockCenterCombine('0');
					break;
				case (zoom==8||zoom==9)://�ܶӵ�2����ת (ͳ���м���Ϣ)
		        	 reoveBlockMouduleDraw();
		        	 queryBlockCenterCombine('1');	
					break;
				default:
					reoveBlockMouduleDraw();
					removeClusterMarkers();
					if(currentClickShower=="qtzdsj"){
		         		queryOtherBlockData();
		         	}else{
		         		bottomToolbarClick(currentClickShower);
		         	}
					break;
		     }	
		     closeLoad();
		     zoomVal=zoom;
		 });
    }
    

	      
}

//ifClusterMode��true������ʾ���ƣ�false����ʾ���� 	
function drawBlockMarkersFunc(ifClusterMode){
	if (ifClusterMode) { // �Ƿ����ۺ�Ч��
		var imageUrl=modelPath + 'images/markers/m1.png';
		var clusterOption = {
			gridSize : 60,
			minimumClusterSize : 3,
			maxZoom : 14,
			// imagePath:getMakerImg[dmz]['cluster'],
			imagePath : imageUrl,
			imageExtension : ".png",
			zoomOnClick : true,
			maxZoomFunc : "drawBlockMarkersFunc(false)"
		};
		var markerCluster = new DMap.MarkerClusterer(map, drawMarkerArray,
				clusterOption);
		blockMarkerClusterArray.push(markerCluster);
	}
    closeLoad();
}

// �õ������������
function getOtherBlockSjlx(){
	var otherBlocks = $("input[name='otherBlockName']:checked");
	var otherBlockStr="";	
	for(var i=0; i<otherBlocks.length; i++){
	   	 if(i!=0){
	   		otherBlockStr+=",";
		 }
		 otherBlockStr+=otherBlocks[i].id;		
	}
	/**
	if(otherBlockStr==""){
		otherBlockStr="A5,A6,A7,A8,A9";
	}
	*/
	return otherBlockStr;
}
/*
//���
function gis_block_list(xxlx){
	var param = getQueryParam();
	param.xxlx=xxlx;
	return;
}
//����
function gis_flow_list(){
	var param = getJtllParams();
	return;
}
*/
//************************ �ȴ��� ***************************  

function showLoad(valmesg){
	var msg = "���ڼ�����Դ,���Ժ򡣡���������";
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
//        alert("����ʧ�ܣ�");
    }
};

