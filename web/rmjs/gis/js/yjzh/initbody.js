var showBasicType=false; //��Դ����չʾ��״̬

String.prototype.Trim = function(){ 
    return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
String.prototype.LTrim = function(){ 
    return this.replace(/(^\s*)/g, ""); 
} 
String.prototype.RTrim = function(){ 
    return this.replace(/(\s*$)/g, ""); 
} 

$(document).ready(function() {
	showLoad(); //��ʾ�ȴ���
	initMap();
	var toolBar = new DMap.ZhzxToolBarControl();
	map.addControl(toolBar);
	
	getTraficEventCatalog(); //��ý�ͨ�¼�����
	initResourceSelect();//��ʼ��������ԴSelect����
	$('#glxqFloatWindow').window({left:"80px", top:"60px",collapsed:false,collapsible:true,closable:true,closed:true});
	$('#roadFloatWindow').window({left:"80px", top:"60px",collapsed:false,collapsible:true,closable:true,closed:true});
	$('#guideFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#resourceFloatWindow').window({left:"80px", top:"30px",collapsed:false,collapsible:true,closable:true,closed:true});
	$('#trafficEventWindow').window({left:"80px", top:"60px",collapsed:false,collapsible:true,closable:true,closed:true});
	showSyncDistrictTree("selectXzqhTreeDiv"); //��ʼ������������
	showRoadTypePage('selectRoadDiv'); //��ʼ����·����

    $('#trafficEventWindow').window({
        onBeforeClose:function(){ 
        	hideEventDialog();
        }
    });
    closeLoad(); //ȥ���ȴ���
    $('#yjqr').click(); //��ʼ��Ԥ��ȷ��  
    //�û���ʾ���򼰳�ʼ����ѯ����
	if(bmjb=="1"){ //���ּ�
		if(!isNaN(bcenterx)){
			map.setCenter(new DMap.LonLat(parseFloat(bcenterx), parseFloat(bcentery)),5, null);	 
		}		
	}else{ 
		mapToInitMBRAndCenter();
	}
    setInterval(function(){ //ʮ����ˢ��һ��Ԥ����Ϣ
    	guide_queryEventData("yjqr","btn"); //btn �� timer����������Ϊ��������5���ѯ���ǰ�ť����
    },10*60*1000);
  //Ϊ��ͼ��Ӽ����¼�
    zoomVal=0;
    DMap.$(map).bind('zoomend.yjzhZoomend',function(){
	    var zoom = map.getZoom();
	    if(zoomVal!= zoom){ //���ּ�	
	    	map.closeInfoWindow();
	    }
	    zoomVal = zoom;
    }); 
	findDimensions();
	setBottomBarCollapse();
	//closed:true, 
	toolBar.addLeftTool({
		name: '��ͨ�¼�',
		id: 'tool-jczyfl',
		pushGroup: 'tool',
		defaultImge:modelPath+"images/business/common/toolbar/zhzx_jczyfl.jpg",
		activeImage:modelPath+"images/business/common/toolbar/zhzx_jczyfl_hover.jpg",
		onActive: function (){
			showBasicItem();					
		}
	});
	toolBar.addLeftTool({
		name: '����Ͻ��',
		id: 'tool-xzqh',
		pushGroup: 'tool',
		defaultImge:modelPath+"images/business/common/toolbar/zhzx_xzqh.jpg",
		activeImage:modelPath+"images/business/common/toolbar/zhzx_xzqh_hover.jpg",
		onActive: function (){	
			hideEventDialog();
			$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:false});
			$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
			$('#trafficEventWindow').window({collapsed:false,collapsible:true,closed:true});
			$('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:true});		    
		}
	});
	toolBar.addLeftTool({
		name: '��·',
		id: 'tool-road',
		pushGroup: 'tool',
		defaultImge:modelPath+"images/business/common/toolbar/zhzx_road.jpg",
		activeImage:modelPath+"images/business/common/toolbar/zhzx_road_hover.jpg",
		onActive: function (){
			hideEventDialog();
			$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
			$('#roadFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:false});
			$('#trafficEventWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
			$('#resourceFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:true});
		}
	});
	/*
	setTimeout(function(){
		closeLoad(); //ȥ���ȴ���
	},500);
	*/
});



function showBasicItem(){
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#trafficEventWindow').window({collapsed:false,collapsible:true,closed:false});
	$('#resourceFloatWindow').window({collapsed:false,collapsible:true,closed:true});
}
/**��·������ʾ*/
function showRoadTypePage(divId){
	$.ajax({
		url : basePath+"yjzh.gis?method=getRoadTypeTreeJson",
		type : "post",
		dataType:"json",
		success : function(data) {
			var roadTypeHtml = "";
			for(var i=0; i<data.length; i++){
				roadTypeHtml+="<li style='list-style-type:none'><input type='checkbox' name='roadTypeCheck' onclick='resetQueryTimer()' value='"+data[i].dllxdm+"'>"+data[i].dllxmc+"</li>";
			}
			$("#"+divId).html(roadTypeHtml);
		}
	})
}


var Collapseflag=1;//��ʶ�Ƿ��·��������۵�
var bottombarWind=null;
function setBottomBarCollapse(){//�����·����������۵�
	/*
	$('#bottomToolbar').window({
	    tools:[{
            iconCls:'panel-tool-collapse',
            handler:function(){
            	var winHeight =document.body.clientHeight;
            	var btbExpandTop=winHeight-100;
            	var btbCollapseTop=winHeight-100;
            	if(Collapseflag==1){
    				Collapseflag=0;
    				$('#bottomToolbar').window({left:"0px", top:btbExpandTop+"px"});
    			}else{
    				Collapseflag=1;
    				$('#bottomToolbar').window({left:"0px", top:btbCollapseTop+"px"});
    			};
            }
        }]
	});*/
	bottombarWind =$('#bottomToolbar').window({collapsible:true,onCollapse:function(){
		if(bottombarWind!=null){
			var winHeight =document.body.clientHeight;
			bottombarWind.window("resize",{top:winHeight-30});
		}
	},onBeforeExpand:function(){
		if(bottombarWind!=null){
			var winHeight =document.body.clientHeight;
			bottombarWind.window("resize",{top:winHeight-105});
		}
	}});
}

/**
 * ����ҳ���и����ؼ���λ�ã�topbar��bottombar
 */
function findDimensions() { //��������ȡ�ߴ�,�����ø����ؼ��ĳ�ʼ��λ�á�
	var winWidth =document.body.clientWidth;
	var winHeight =document.body.clientHeight;
    var btbTop=winHeight-85;
    $('#bottomToolbar').window({left:"0px", top:btbTop+"px"});
    //��ʼ����Դλ��  
    $('#guideFloatWindow').window({left:winWidth-280+"px", top:"80px"});
    //����tipWindow λ��
    if(tipWinHasInitial){
   	 var tipWinClosed=$('#tipConditionWin').window("options").closed;
	    if(!tipWinClosed){	 	    
	 	    //�������Ŵ���ǰ������	
	    	var left=$("#"+showWindowTipId).offset().left;
			//���window�ĸ߶�
			var winHeight=$('#tipConditionWin').window("options").height;
			var top=$("#"+showWindowTipId).offset().top-(winHeight+30);
			fillTipWindowContent(showWindowTipId);
			if(showWindowTipId=="qtzy"){
	 			setTimeout(function(){
	 				fillZySelect();			
				}, 500);
		 	} 			
			$("#tipConditionWin").window("open");
			$("#tipConditionWin").window({left:left,top:top,collapsed:false,collapsible:false,draggable:false,closed:false,closable:true});
			
			 						
		}
	}
}

window.onresize=findDimensions;