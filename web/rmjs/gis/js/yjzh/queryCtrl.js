var currentClickShower="";//��ǰѡ����
var eventRecord=""; //��¼��һ�β�ѯʱ�Ĳ�ѯ�����������Ƿ�5���ѯ�ж�
var recTimer;   //��ʱ��
var queryPerTime=5*1000; //�Զ���ѯ���ʱ��(����)
var markerJsonArray = [];//װ��markerjson
var yjczlcMakerArray = [];//Ӧ����������װ��marker
var yjXxlx = "yjqr";
//����Զ���ѯ
function clearTimer(){
	if(recTimer){
		clearTimeout(recTimer); 
	}
}
//�����趨��ʱʱ��
function resetQueryTimer(){ 
	if(recTimer){
		clearTimeout(recTimer); 
	}		
    recordTimeQuery();	
}
//����ʱ���ʱ��ѯ
function recordTimeQuery(){  
	recTimer = setTimeout(function(){
		if(currentClickShower!=""){
			setTimeout(function(){
				guide_queryEventData(currentClickShower,"timer"); //��֯��ѯ
			},500);			
		}
		
	},queryPerTime);
	
}
//��ò�ѯ��������
function getQueryParam(){
   var params = {}; //��ѯ��������
   var sjlxs = ""; //��������
   var xflxs = ""; //ϸ������
   var dllxs = ""; //��·����
   var xzqhs = ""; //��������
   //�¼�����
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
   //��·����
   var roadTypeCheckedArray = $("input[name='roadTypeCheck']:checked");
   for(var i=0; i<roadTypeCheckedArray.length; i++){
	   if(i!=0){
		   dllxs+=","; 
	   }
	   dllxs+=roadTypeCheckedArray[i].value;
   }
   //��������
   xzqhs = getSelectedDistrictTreeIds("selectXzqhTreeDiv");
   params.sjlxs = sjlxs;
   params.xflxs = xflxs;
   params.dllxs = dllxs;
   params.xzqhs = xzqhs;
   return params;
}

//�����ѯ
function guide_queryEventData(queryType,fromType){
	showLoad();
	//clearMakerInType("yjqr,yjcz,czfk");
	clearYjczlcMakerArray(); //���Ӧ�����������в�ѯ���Ƶ�ͼ��
    map.closeInfoWindow();
   var params = getQueryParam();
   if(fromType=="timer" && eventRecord==queryType+params.sjlxs+params.xflxs+params.dllxs+params.xzqhs){ //��ѯ����û�仯
	   return;
   }else{
	   eventRecord=queryType+params.sjlxs+params.xflxs+params.dllxs+params.xzqhs;
   }  
   var queryUrl = basePath + "yjzh.gis?method=guideYjqr";
   if(queryType=="yjqr"){ //Ԥ��ȷ��
	   queryUrl = basePath + "yjzh.gis?method=guideYjqr";
   }else if(queryType=="yjcz"){ //Ӧ������
	   queryUrl = basePath + "yjzh.gis?method=guideYjcz";
   }else if(queryType=="czfk"){ //���÷���
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
				closeLoad(); //ȥ���ȴ���
			},500);			
			$('#guideFloatWindow').window({collapsed:false,collapsible:true,closable:true,closed:false});
			//var data={"total":4,"rows":[{"sxh":1,'sjlx':'A',"id":"320200000000050002","tz":"�ƶ�����","mc":"�������ƶ������豸","jl":"1390.0","geo":"POINT(120.30566 31.56713)"},{"sxh":2,'sjlx':'A',"id":"320200000000070005","tz":"�������","mc":"Ǯ��·����������豸","jl":"1631.0","geo":"POINT(120.29687 31.57958)"},{"sxh":3,'sjlx':'B',"id":"320200000000070200","tz":"�������","mc":"������������������豸","jl":"245.0","geo":"POINT(120.31347 31.5791)"},{"sxh":4,'sjlx':'C',"id":"320200000000070810","tz":"�������","mc":"EEEEEE","jl":"4713.0","geo":"POINT(120.28777 31.61328)"}]}
	        guide_eventResult(data,queryType); //д�б�����
			
			var lonlatArray = [];
			yjczlcMakerArray = [];
			//����easyUi����
			if(data.total>0){
				for(var i=0; i<data.rows.length; i++){				
					var id=data.rows[i].id;
					var geo = data.rows[i].geo;  
					var sjlx = data.rows[i].sjlx; //�¼�����
					var sjdj = data.rows[i].sjdj; //�¼��ȼ�
					if(geo!="POINT( )"){
						//var maker = drawGeo(geo,queryType,id);
						var maker = drawGeo(geo,sjlx,id,sjdj,queryType);
						yjczlcMakerArray.push(maker);
						lonlatArray.push(maker.getLonlat());
					}			
				}

				var markerJson={ //������װ�ز�ѯ���
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
//��ת���˹��ɼ�ҳ��
function guide_jumpToRgcjPage(){
	gis_add_incident();
}
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