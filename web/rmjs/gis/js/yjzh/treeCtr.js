/**
 * 设置Ztree基本参数 示例数据
 zTreeOptions={
  	"url":url,//basePath+"ResultTree.gis?query=district&sbssDmzArray="+sbssDmzArray+"&queryWhereArray="+queryWhereArray
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
		dataType:"json",
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
		}
	});
}
/**
 * @author 剑侠 
 */
function showSyncRoadType(treeDivId) {
	$.ajax({
		url : basePath+"jtztjc.gis?method=getRoadTreeJson",
		type : "post",
		data : param,
		dataType:"json",
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
		}
	});
}

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
	//var glbmArray=[];
	if(checkedNodes.length>0){
		for(var i=0;i<checkedNodes.length;i++){
			var id=checkedNodes[i].id;
			districtIdArray.push(id);			
		}
		districtTreeIds=districtIdArray.join();
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
			if(!checkedNode.isParent){
				selectedNodeArray.push(checkedNodeInfo);
			}
			//selectedNodeArray.push(checkedNodeInfo);
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