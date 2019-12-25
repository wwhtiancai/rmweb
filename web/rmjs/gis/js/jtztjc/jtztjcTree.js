var curSelectedRoadNodes="";//记录当前选择的道路节点

function xzqhTreeDialogFunc(){ 
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:false});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
}

function roadTreeDialogFunc(){	
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:false});
}
function hideTreeDialog(){ //隐藏所有dialog
	$('#glxqFloatWindow').window({collapsed:false,collapsible:true,closed:true});
	$('#roadFloatWindow').window({collapsed:false,collapsible:true,closed:true});
}

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
 *  
 * */
function canleCheck(){
	var trobjt=$.fn.zTree.getZTreeObj("selectXzqhTreeDiv");
	if(trobjt==null) return;
	var nodes=trobjt.getCheckedNodes(true);
	if(nodes==null) return;
	for(var i=0;i<nodes.length;i++){
		trobjt.checkNode(nodes[i],false,false);
	}
	var trobjt2=$.fn.zTree.getZTreeObj("selectRoadTreeDiv");
	if(trobjt2==null) return;
	var nodes2=trobjt2.getCheckedNodes(true);
	if(nodes2==null) return;
	for(var i=0;i<nodes2.length;i++){
		trobjt2.checkNode(nodes2[i],false,false);
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

function showSyncRoadtTree(treeDivId,param) {
	$.ajax({
		url : basePath+"jtztjc.gis?method=getRoadTreeJson",
		type : "post",
		data : param,
		dataType:"json",
		success : function(data) {
			var zTreeInitialNodes = eval(data);
			if(zTreeInitialNodes==null || zTreeInitialNodes.length==0){
				$("#"+treeDivId).html("<font color='blue'>没有获取到道路数据</font>");
				return;
			}			
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
		/*
		for(var i=0;i<checkedNodes.length;i++){
			var id=checkedNodes[i].id;
			if(id.length==12){
				glbmArray.push("'"+id+"'");
			}else{
				districtIdArray.push("'"+id+"'");
			}
		}
		districtTreeIds=districtIdArray.join()+";"+glbmArray.join();
		*/
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
		return node.level == 0;
	}, true);
}

//判断是否被全勾选
function getCheckedNodes(node){
	if(node.checked){
		if(node.children==null || node.children.length==0)
			return {stat: true, ids: [node.id]};
		var tempStat=true;
		var tempIds=[];
		$.each(node.children,function(){
			var checkStat=getCheckedNodes(this);
			tempIds=tempIds.concat(checkStat.ids);
			if(!checkStat.stat)
				tempStat=false;
		});
		if(tempStat)
			tempIds=[node.id];
		return {stat: tempStat, ids: tempIds};
	}else{
		return {stat: false, ids:[]};
	}
}

function zTreeOnCheck(treeDivId) {
	 var idString = "";
	var tree = $.fn.zTree.getZTreeObj(treeDivId);
	if(tree==null){
		return  idString;
	}
	var rootNodes=tree.getNodesByParam("level", 0);
	var idArray=[];
	$.each(rootNodes,function(){
		var checkStat=getCheckedNodes(this);
		idArray=idArray.concat(checkStat.ids);
	}); 
    for(var i=0; i<idArray.length; i++){
    	if(i!=0){
    		idString+=",";
    	}
    	idString+=idArray[i];
    }	
	return idString;
}