if(typeof ezwidget == "undefined" || !ezwidget){
	var ezwidget = {};
}

/*
 *@param xmlURL XML文件的地址
 */
ezwidget.JXTree = function(xmlURL){
  ezwidget.JXTree.seqNextValue();
  this.pk = ezwidget.JXTree.sequence;
	var result = new Array();
	//Node 节点构建结束，开始解释XML文件
	var DOMRoot = ezwidget.XMLDom.loadXML(xmlURL).documentElement;//同步加载XML文件
	var level = -1;//root节点level
	var stack = new Array(1);
	result.push("<table border='0'cellpadding='0' cellspacing='0'><tr><td><div class='root'></div></td><td><input class='checkbox' checked type='checkbox' id='chk_" + ezwidget.JXTree.sequence + "_" + ezwidget.XMLDom.getAttribute(DOMRoot,"id") + "' name='chk_" + ezwidget.JXTree.sequence + "_" + ezwidget.XMLDom.getAttribute(DOMRoot,"id") + "' value='root' onclick='ezwidget.JXTree.click(this,\""+ (ezwidget.JXTree.sequence + "_" + ezwidget.XMLDom.getAttribute(DOMRoot,"id"))+"\")'></td><td class='text' valign='bottom' nowrap>"+ezwidget.XMLDom.getAttribute(DOMRoot,"name")+"</td></tr></table>");

	//解释xml文件内容成树状态展开的HTML代码，递归调用
	this.parseXML = function(node){
		stack.push(level);
		var element = new ezwidget.ElementNode(node,level,this.pk);
		if(level == -1){ 	
			ezwidget.JXTree.nodes[element.id] = element;
			element._parentNode = null;
    }
    level++;
 		var elements = node.childNodes;
		if(level != 0){
			if(element.isLast)
				result.push("<div id='"+element.id+"_body' class='body_empty' style='display:none'>");
			else
				result.push("<div id='"+element.id+"_body' class='body_line' style='display:none'>");
		}
		for(var i=0;i<elements.length;i++){
			if(elements.item(i).nodeName == "item"){ //节点为树叶
				var textNode = new ezwidget.TextNode(elements.item(i),level,this.pk);
				textNode._parentNode = element.id == "" ? ezwidget.JXTree.sequence + "_root" : element.id;
				result.push(textNode.toHTML());
				ezwidget.JXTree.nodes[textNode.id] = textNode;
				textNode = null;
			}
			else if(elements.item(i).nodeType == 1){ //节点为树枝
				var elementNode = new ezwidget.ElementNode(elements.item(i),level,this.pk);
				elementNode._parentNode = element.id == "" ? ezwidget.JXTree.sequence + "_root" : element.id;
				if(elements.item(i).childNodes.length > 1){
					result.push(elementNode.toFolderHTML());
					ezwidget.JXTree.nodes[elementNode.id] = elementNode;
					elementNode = null;
					this.parseXML(elements.item(i));
					
			  }
			  else{
			  	result.push(elementNode.toEmpFolderHTML());
					ezwidget.JXTree.nodes[elementNode.id] = elementNode;
					elementNode = null;
			  }
			}
		}
	};
	
	//得到解释结果并返回
	this.getTree = function(){
		this.parseXML(DOMRoot);
		DOMRoot = null;
		return result.join("");
	};

};


//静态属性
ezwidget.JXTree.curText = null;

ezwidget.JXTree.nodes = new Array();

ezwidget.JXTree.sequence = 0;	

ezwidget.JXTree.seqNextValue = function(){
	++ezwidget.JXTree.sequence;
};

//静态方法
ezwidget.JXTree.changeState = function (id){//展开或者收缩节点内容
	var _body = document.getElementById(id + "_body");
	var _join = document.getElementById(id+"_join");
	var folder = document.getElementById(id+"_folder");
	(_body.style.display == "none") ? (
		_body.style.display = "block",
		_join.className = _join.className.replace("plus","minus"),
		folder.className = "folder_open"
	) : (
		_body.style.display = "none",
		_join.className = _join.className.replace("minus","plus"),
		folder.className = "folder_close"
	)
};

ezwidget.JXTree.setFocus = function(id){
	if(ezwidget.JXTree.curText){
		with(document.getElementById(ezwidget.JXTree.curText).style){
			backgroundColor = "";
			color = "#000";
		}
	}
	with(	document.getElementById(id).style){
		backgroundColor = "#003366";
		color = "#FFF";
	} 
	ezwidget.JXTree.curText = id;
};

ezwidget.JXTree.getNode = function(id){  
	 return ezwidget.JXTree.nodes[id];
};

ezwidget.JXTree.click = function(chk,id){
	var node = ezwidget.JXTree.getNode(id);
	ezwidget.JXTree.childState(node,chk.checked);
    ezwidget.JXTree.parentState(node,chk.checked);
    if(chk.checked)  {
  	  dobusiness(id);
    }
    else{
	  undobusiness(id);
  }
};

ezwidget.JXTree.childState = function(node,state){
  var cids = node._childNodes;
	if(cids){
		for(var i = 0; i < cids.length; i++){
			var cid = cids[i];
			var child = ezwidget.JXTree.getNode(cid); 
			if(child.type == "item"){
				var cb = document.getElementById(cid + "_chk_item");
				cb.checked = state;				
			}
			else{
				var cb = document.getElementById(cid + "_chk_folder");
				cb.checked = state;
				ezwidget.JXTree.childState(child,state);		  
			}		
		}		     
  }
};

ezwidget.JXTree.parentState = function(node,state){
  var pid = node._parentNode;
  if(pid){
  	var parentNode = ezwidget.JXTree.getNode(pid);
  	var childs = parentNode._childNodes;
		var nid = parentNode.level == -1 ? "chk_" + parentNode.id : parentNode.id + "_chk_folder";
  	if(state){
  		var ps = true;	  	
  		for(var i = 0; i < childs.length; i++){
  			var cid = childs[i];
			  var child = ezwidget.JXTree.getNode(cid);
  		  var cb = document.getElementById(cid + "_chk_" + child.type);
  		  if(!cb.checked){
  		  	ps = false;
  		  	break;
  		  }  	  
  		}  		
	    document.getElementById(nid).checked = ps;
    }	
  	else{
      document.getElementById(nid).checked = state;
      
    }
    ezwidget.JXTree.parentState(parentNode,state);
  }	
};

/**
 *首先定义TreeNode抽象对象
 *TreeNode对象属性：
 *id 唯一编号,必须在xml文件里定义为节点属性
 *level 节点层次，从-1开始（即根节点）
 *_click 节点click，在xml文件定义为节点属性[可选]
 *isLast 是否为本节点所在层次最后一个节点
 *parent_isLast 父节点是否为父节点所在层次的最后一个节点
 *toHTML 本节点转成HTML代码的方法
**/
ezwidget.TreeNode = function (node,level){
	var parent_elements = node.parentNode ? (node.parentNode.parentNode ? node.parentNode.parentNode.childNodes : null) : null;
	var elements = node.parentNode ? node.parentNode.childNodes : null;
	this.id = ezwidget.JXTree.sequence + "_" + (ezwidget.XMLDom.getAttribute(node,"id") ? ezwidget.XMLDom.getAttribute(node,"id") : "");
	this.level = level;//节点的层次
	this.isLast = elements ? ((elements.item(elements.length-2) === node) ? true : false) : false;
	this._click = ezwidget.XMLDom.getAttribute(node,'click') ? ezwidget.XMLDom.getAttribute(node,'click') : "";
	this._checkboxValue = ezwidget.XMLDom.getAttribute(node,'value') ? ezwidget.XMLDom.getAttribute(node,'value') : "";	
	this.toHTML = null;//function
};

/**
 *ElementNode对象，继承自抽象对象TreeNode
 *新增属性：
 *_nodeName 节点名称
**/
ezwidget.ElementNode = function(node,level,pk){
	ezwidget.TreeNode.apply(this,arguments);
	this._nodeName = ezwidget.XMLDom.getAttribute(node,"name") ? ezwidget.XMLDom.getAttribute(node,"name") : "";
	this._childNodes = new Array();
	this.type = "folder";
	var children = node.childNodes;
	for(var i = 0; i < children.length; i++){
		id = ezwidget.XMLDom.getAttribute(children[i],"id") ? ezwidget.XMLDom.getAttribute(children[i],"id") : "";
		if(id != ""){		
			this._childNodes.push(ezwidget.JXTree.sequence + "_" + id);
		}		
	} 		
	this.toFolderHTML = function(){
		var result = "";			
		if(this.isLast) result += "<table border='0'cellpadding='0' cellspacing='0'><tr><td><div class='plus_bottom'";
		else result += "<table border='0'cellpadding='0' cellspacing='0'><tr><td><div class='plus'";
		result += " id='"+this.id+"_join' onclick=\"ezwidget.JXTree.changeState('"+this.id+"')\"></div></td><td><div id='"+this.id+"_folder' class='folder_close'></div></td><td><input class='checkbox' type='checkbox' id='"+this.id+"_chk_folder' checked name='chk_folder_" + pk + "' value='" + this._checkboxValue + "' onclick='ezwidget.JXTree.click(this,\""+this.id+"\")'></td><td class='text' valign='bottom' onclick=\""+this._click+"\" nowrap>"+this._nodeName+"</td></tr></table>";
		return result;
	}
	this.toEmpFolderHTML= function(){
		var result = "";			
		if(this.isLast) result += "<table border='0'cellpadding='0' cellspacing='0'><tr><td><div class='join_bottom'";
		else result += "<table border='0'cellpadding='0' cellspacing='0'><tr><td><div class='join'";
		result += " id='"+this.id+"_join'></div></td><td><div id='"+this.id+"_folder' class='folder_close'></div></td><td><input class='checkbox' type='checkbox' id='"+this.id+"_chk_folder' name='chk_folder_" + pk + "' value='" + this._checkboxValue + "' onclick='ezwidget.JXTree.click(this,\""+this.id+"\")'></td><td class='text' valign='bottom' onclick=\""+this._click+"\" nowrap>"+this._nodeName+"</td></tr></table>";
		return result;
	}
};

/**
 *TexNode对象，继承自抽象对象TreeNode
 *属性和TreeNode一样
 *新增属性：
 *_nodeValue 节点值
**/
ezwidget.TextNode = function(node,level,pk){
	ezwidget.TreeNode.apply(this,arguments);
	this._nodeValue = node.firstChild.nodeValue;
	this.type = "item";
	this.toHTML = function(){
		var result = "";
		if(this.isLast) result += "<table border='0' cellpadding='0' cellspacing='0'><tr><td width='18px'><div class='join_bottom'></div></td>";
		else result += "<table border='0'cellpadding='0' cellspacing='0'><tr><td><div class='join'></div></td>";
		result += "<td><div class='page'></div></td><td><input class='checkbox' type='checkbox' checked id='"+this.id+"_chk_item' name='chk_item_" + pk + "' value='" + this._checkboxValue + "' onclick='ezwidget.JXTree.click(this,\""+this.id+"\")'></td><td class='text' valign='bottom' id='"+this.id +"_item' onclick=\"ezwidget.JXTree.setFocus(this.id);"+this._click+"\" nowrap>"+this._nodeValue+"</td></tr></table>";
		return result;
	}
};