function initDistrictnTreeRootNode(rootId) {
	var districtRootNode = [//行政区划树的根节点，id注解：d，表示行政区划；d和冒号直接的数字表示树的级数(跟是0级)，用与拆分id字段异步加载树；冒号后面为id为标准6位
	                        { "id":"000000", "name":"全国",isParent:true  }
	                        ];
	var districtTreesetting = {//用于测试ztree的测试setting数据
			view: {
				selectedMulti: false
			},
			async: {
				enable: true,
				url:basePath+"ResultTree.gis?method=district&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray,
				autoParam:["id=districtId"],
				type:"GET"
			},
			callback: {
				onClick: resultTreeOnClick
			},
			data: {
				simpleData: {
					enable: true
				}
			}
	};
	var reqParams={
			"districtId":rootId
		};
	$.ajax({ 
		url: basePath+"ResultTree.gis?method=district&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray+"&districtId="+rootId,
		type:"post",
		//data:reqParams,
		success: function(data){
			if(data!=null){
				var jsonData=eval(data);
				$.fn.zTree.init($("#resultTree"), districtTreesetting, jsonData);
			}else{
				alert("数据空！");
			}
	}});
};
function initRoadTypeTreeRootNode(rootId) {
	var roadTypeRootNode = [//行政区划树的根节点，id注解：d，表示行政区划；d和冒号直接的数字表示树的级数(跟是0级)，用与拆分id字段异步加载树；冒号后面为id为标准6位
	                        { "id":"000000", "name":"道路类型",isParent:true  }
	                        ];
	var roadTypeTreesetting = {//用于测试ztree的测试setting数据
			view: {
				selectedMulti: false
			},
			async: {
				enable: true,
				url:basePath+"ResultTree.gis?method=roadType&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray,
				autoParam:["id=districtId"],
				type:"GET"
			},
			callback: {
				onClick: resultTreeOnClick
			},
			data: {
				simpleData: {
					enable: true
				}
			}
	};
	var reqParams={
			"districtId":rootId
		};
	$.ajax({ 
		url: basePath+"ResultTree.gis?method=roadType&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray,
		type:"post",
		data:reqParams,
		success: function(data){
			if(data!=null){
				var jsonData=eval(data);
				$.fn.zTree.init($("#resultTree"), roadTypeTreesetting, jsonData);
			}else{
				alert("数据空！");
			}
	}});
};
var isSBJGfirstClik = true;
function resultTreeOnClick(event, treeId, treeNode) {
    var selTreeNodeIdFlag=(treeNode.id).substring(0,1);
    if(selTreeNodeIdFlag=="s"){//节点id格式：s:bh+";"+mc+";"+fllx+";"+x+";"+y+";"+selectDMZ
    	var idArray=(treeNode.id).split(";");
    	//alert(idArray);
    	var bh=idArray[0].split(":");
    	var sbssId=idArray[5];//
    	var fllxId=idArray[2];
    	var nodeId=bh[1];
    	var nodeText=idArray[1];
    	var nodeX=idArray[3];
    	var nodeY=idArray[4];
    	var qpHtml=null;
    	var ifClusterMode=false;
    	if(sbssId<10){
    		sbssId="0"+sbssId;
    	}
    	 map.closeInfoWindow();
    	//alert("---->");
//    	alert(sbssId+","+fllxId+","+nodeId+","+nodeText+","+nodeX+","+nodeY);
    	//var smarker =drawMarker(sbssId,fllxId,nodeId,nodeText,nodeX,nodeY,qpHtml,ifClusterMode);
    	//alert(sbssId+"----"+fllxId);
    	map.setCenter(new DMap.LonLat(nodeX,nodeY),16);
    	//闪烁 		
	     var imgurl;
	     if(sbssId!="" && fllxId!="" && fllxId!=null){
		     if(sbssId=="01"){
		    	 imgurl= getMakerImg[sbssId][fllxId]['01']; //不同卡口分类
			 }else{		
				 if(fllxId!="null"&&fllxId!=""){
					 imgurl= getMakerImg[sbssId][fllxId];  
				 }else{
					 imgurl= getMakerImg[sbssId]['onlyone']; //图标路径       
				 } 
			 }
	     }else{
	    	 imgurl= getMakerImg[sbssId]['onlyone']; //图标路径            
	     }
         var markerSymbol = {  // marker样式
	         url : imgurl,
			 size : new DMap.Size(36,36),
			 offsetType : "mm", 
			 borderWidth : 4,
			 borderColor : "red",
			 color :"white",
			 opacity : 1
		 }
         var jd =nodeX;
	     var wd =nodeY;
		 var pPoint = new DMap.LonLat(jd,wd); //创建点对象
		 var marker=new DMap.Marker(pPoint,markerSymbol);//创建marker对象
		    marker.xxlx=sbssId;
		     marker.id=nodeId;
	   		 marker.imgUrl = imgurl;
	   		 marker.lonlat=pPoint;	
			 marker.setCommonEvent(); //激活marker的点击事件
			 DMap.$(marker).bind("click",function(e){
				 openGisInfoWindowHtml(marker,map);
	         });     		
		 FLASH_MARKER =marker;
		 setTimeout(function(){
			 DMap.$(marker).trigger("click");	
			 flashMark();			 
		 },500);
    	if(!isSBJGfirstClik){
    		queryData();
    	}    	
    	isSBJGfirstClik =false;
    }
};
function showResTreeClick(ind){
	if(ind=="1"){
		initDistrictnTreeRootNode("000000");//行政区划树
	}else{
		initRoadTypeTreeRootNode("000000");//道理类型树
	}
}
function getResultTree(){
	var content = slidebarControl.getContentDiv();	
	if (!$("#resultDiv").get(0)) {	
		var titleHtml="<div style='background-color:#cfe2fb;'><span id='yjtitle' style='font-weight:bolder;margin-top:7px;";
		titleHtml+="height:20px;width:100%;font-size:14px;text-align:center;'>查询结果信息</span></div>";			
		//titleHtml+="<div class='inforWindowFontCss' style='width:110px;float:left;padding-left:20px'><input type='radio' name='resultTreeRadio'  />设备类型</div>";
		titleHtml+="<div class='inforWindowFontCss' style='width:90px;float:left'><input type='radio' name='resultTreeRadio' id='xzqhTreeRadio' onclick='showResTreeClick(1)' checked />管理辖区</div>";
		titleHtml+="<div class='inforWindowFontCss' style='width::90px;float:left'><input type='radio' name='resultTreeRadio' id='roadTreeRadio' onclick='showResTreeClick(2)' />道路类型</div>";
		
		$(titleHtml).appendTo(content);
		$("<div>").attr("id", "resultDiv").appendTo(content).css("width", "280px");	
		$("#resultDiv").append("<span><div id='resultTree' class='ztree'></div></span>");
		$("#resultDiv").append("<span><div id='resultRoadTree' class='ztree'></div></span>");
	}
	if(resultShowRec=="roadRec"){
		$("#roadTreeRadio").attr("checked","true");
		initRoadTypeTreeRootNode("000000");//道理类型树		
	}else{
		$("#xzqhTreeRadio").attr("checked","true");
		initDistrictnTreeRootNode("000000");//行政区划树
	}
	if(slidebarOut){
		slidebarControl.slideOut(); //弹出结果浮动窗口
	}
	
	
//结果树查询
	/*
    $('#resultTree').tree({
         url:basePath+"zhzx.gis?method=getResWinInfo&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray,
         onClick:function(node){
               var node1=$('#resultTree').tree('getParent',node.target);
               var node2=$('#resultTree').tree('getParent',node1.target);
               map.setCenter(new DMap.LonLat(node.attributes.X,node.attributes.Y),15);			                
               //drawMarker(node2.id,node1.id,node.id,node.text,node.attributes.X,node.attributes.Y);
         },
         onLoadSuccess : function(node, data) {
       	  slidebarControl.slideOut(); //弹出结果浮动窗口
       	 // recResultData = data; //记录查询结果
       	 // drawMarkersFunc(true); //绘制marker
         } 
    })   
    */ 
}


/**
 * 设置Ztree基本参数 示例数据
 zTreeOptions={
  	"url":url,//basePath+"ResultTree.gis?method=district&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray
   "autoParams":autoParams //"id=districtId"
  "check":false
 }
 */
var zTreeSet=null;
function zTreeSetting(zTreeOption){
	var checkFlag=zTreeOption.check==true?true:false;
	var asynaParams=null;
	var zTreeSetInner = {
			check: {
				enable: checkFlag
			},
			view: {
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable: true
				}
			}
	};
	if(zTreeOption.url!=undefined){
		//是否异步标识
		asynaParams={
			enable: true,
			url:zTreeOption.url,
			autoParam:[zTreeOption.autoParams],
			type:"post"
		};
		zTreeSet=$.extend({},zTreeSetInner,{async:asynaParams});
	}else{
		zTreeSet=zTreeSetInner;
	}
}  

/**
 * @author 剑侠 
 */
function showSyncDistrictTree(treeDivId) {
	$.ajax({
		url : basePath + "gisfeatures.gis?method=getSyncDistrictTree",
		type : "post",
		success : function(data) {
			var zTreeInitialNodes = eval(data);
			$.fn.zTree.init($("#"+treeDivId), {
				 check :{
					enable: true
				 },
				 view: {
					selectedMulti: false
				 },
				 callback:{
					 onCheck:function(event,treeId,treeNode){
					 	 isZoomToInitBound=true;//控制回到登录时候的初始化范围
					 	 isSBJGfirstClik=true;//控制结果树点击
						 resetQueryTimer();
					 },
					 onClick:function(event,treeId,treeNode){
						 //getRoadSelectedNode();						 
					 }
		        },data: {
					simpleData: {
						enable: true
					}
				}
			}, zTreeInitialNodes);
			/*
			var zTreeInitialNodes = eval(data);			
			zTreeSetting({"check" : true,callback:{
				 onCheck:function(event,treeId,treeNode){
					 resetQueryTimer();
					// alert(treeId+":"+treeNode.id+":"+treeNode.name);
				 },
				 onClick:function(event,treeId,treeNode){
					 //getRoadSelectedNode();
				 }
	        }});		
			$.fn.zTree.init($("#"+treeDivId), zTreeSet, zTreeInitialNodes);
			*/
		}
	});
}
/**
 * @author 剑侠
 */

function showRoadTree(treeDivId,sbDmzStr) {	
	$.ajax({
		url : basePath+"zhzx.gis?method=getRoadTreeJson&sbssDmzArray="+sbDmzStr+"&queryWhereArray="+queryWhereArray,
		type : "post",

		success : function(data) {
			var zTreeInitialNodes = eval(data);
			$.fn.zTree.init($("#"+treeDivId), {
				 check :{
					enable: true
				 },
				 view: {
					selectedMulti: false
				 },
				 callback:{
					 onCheck:function(event,treeId,treeNode){
					 	 isZoomToInitBound=true;//控制回到登录时候的初始化范围
					 	 isSBJGfirstClik=true;//控制结果树点击
						 resetQueryTimer();
						 //alert(treeId+":"+treeNode.id+":"+treeNode.name);
					 },
					 onClick:function(event,treeId,treeNode){
						 //getRoadSelectedNode();
					 }
		        },data: {
					simpleData: {
						enable: true
					}
				}
			}, zTreeInitialNodes);
		}
	});
}
/*
function getRoadSelectedNode(){
	var treeIds ="";
	var treeObj = $.fn.zTree.getZTreeObj("selectRoadTreeDiv");
	if(!treeObj){
		return treeIds;
	}
	var checkedNodes=treeObj.getCheckedNodes(true);
	
	for(var i=0; i<checkedNodes.length; i++){
		//var halfCheck = checkedNodes[i].getCheckStatus();
		//alert(checkedNodes[i].id+":"+halfCheck.half);
		//if(!halfCheck.half){
		if(i==0){
			treeIds+="'"+checkedNodes[i].id+"'";
		}else{
			treeIds+=",'"+checkedNodes[i].id+"'";
		}			
		//}
		
	}

	return treeIds;
}
*/
/**
 * 
 * @param districtDivId 行政区划树Id
 * @param targetTreeDivId 目标树Id
 */
var districtSubTreeData=[];
function getSelectedDistrictSubTree(districtDivId,targetTreeDivId){
	var districtSubTreeData=getSelectCheckedSubTreeNodes(districtDivId);
	var ids=getSelectedDistrictTreeIds(districtDivId);
	$.fn.zTree.init($("#" + targetTreeDivId), {"check" : true}, districtSubTreeData);
}

function getSelectedDistrictTreeIds(districtTreeDivId){
	var districtTreeIds="";
	var checkedNodes=getSelectCheckedSubTreeNodes(districtTreeDivId);
	var districtIdArray=[];
	var glbmArray=[];
	if(checkedNodes.length>0){
		for(var i=0;i<checkedNodes.length;i++){
			var id=checkedNodes[i].id;
			if(id.length==12){
				glbmArray.push("'"+id+"'");
			}else{
				districtIdArray.push("'"+id+"'");
			}
		}
		districtTreeIds=districtIdArray.join()+";"+glbmArray.join();
	}
	return districtTreeIds;
}

function getSelectCheckedSubTreeNodes(treeDivId){
	var selectedTreeObj = $.fn.zTree.getZTreeObj(treeDivId);
	if(!selectedTreeObj){
		return "";
	}
	var checkedNodes = selectedTreeObj.getCheckedNodes(true);
	var selectedNodeArray=[];
	for(var i=0;i<checkedNodes.length;i++){
		if(checkedNodes[i].children!=undefined){
			var checkedNode= $.extend({},checkedNodes[i]);
			var checkedNodeInfo={};
			checkedNodeInfo.id=checkedNode.id;
			checkedNodeInfo.pId=checkedNode.pId;
			checkedNodeInfo.name=checkedNode.name;
			checkedNodeInfo.isParent=checkedNode.isParent;			
			/**
			var checkedChildren=[];
			var oldChildren=checkedNodes[i].children;
			for(var m=0;m<oldChildren.length;m++){
				if(oldChildren[m].checked){
					checkedChildren.push(oldChildren[m]);
				}
			}			
			checkedNode.children=checkedChildren;
			*/
			//checkedNode.children=undefined;
			if(!checkedNode.isParent){
				selectedNodeArray.push(checkedNodeInfo);
			}
			
		}else{
			var checkedNode= $.extend({},checkedNodes[i]);
			var checkedNodeInfo={};
			checkedNodeInfo.id=checkedNode.id;
			checkedNodeInfo.pId=checkedNode.pId;
			checkedNodeInfo.name=checkedNode.name;
			checkedNodeInfo.isParent=checkedNode.isParent;		
			selectedNodeArray.push(checkedNodeInfo);
		}
	}
	return selectedNodeArray;
}

function getSeletedCheckSubTreeByRecursion(treeNode){
	if((treeNode.children!=undefined)&&treeNode.cheked){
		var nodeChildren=treeNode.children;
		for(var i=0;i<nodeChildren.length;i++){
			var nextNode=nodeChildren[i];
			var nextNodeInfo={};
			nextNodeInfo.id=nextNode.id;
			nextNodeInfo.pId=nextNode.pId;
			nextNodeInfo.name=nextNode.name;
			nextNodeInfo.isParent=true;
			districtSubTreeData.push(nextNodeInfo);
			getSeletedCheckSubTreeByRecursion(nextNode);			
		}
	}else{
		if(treeNode.cheked){
			var treeNodeInfo={};
			treeNodeInfo.id=treeNode.id;
			treeNodeInfo.pId=treeNode.pId;
			treeNodeInfo.name=treeNode.name;
			treeNodeInfo.isParent=treeNode.isParent;
			districtSubTreeData.push(treeNodeInfo);
		}
	}
	
}


function getRoot(treeId) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	//返回一个根节点
	return treeObj.getNodesByFilter(function(node) {
		return node.level == 0
	}, true);
}