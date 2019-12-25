if(typeof ezwidget == "undefined" || !ezwidget){
	var ezwidget = {};
} 

ezwidget.isIe = /msie/i.test(navigator.userAgent);//是否是IE浏览器

ezwidget.XMLDom = {
	// 得到xmlDom对象
	getXMLDom : function(){
		var axo = null;
		var MS_XML_DOM = ["MSXML2.DOMDocument", "Microsoft.XMLDOM", "MSXML.DOMDocument", "MSXML3.DOMDocument"];
		if (ezwidget.isIe)
		{
			for (var i=0; i<4; i++)
			{
				try
				{
					axo = new ActiveXObject(MS_XML_DOM[i]);
					return axo;
				}
				catch (e){return null;}
			}
		}
		else return document.implementation.createDocument("", "doc", null);
	},
	
	// 装载一个XMLDom
	loadXML : function (url, async, handle){
		var xmlDom = ezwidget.XMLDom.getXMLDom();
		xmlDom.preserveWhiteSpace=true;//兼容FireFox
		xmlDom.async = (async==true) ? true : false;
		if (async)
		{
			if (ezwidget.isIe) xmlDom.onreadystatechange = function ()
			{
				if(xmlDom.readyState == 4) handle(xmlDom);
			}
			else xmlDom.onload = function ()
			{
				handle(xmlDom);
			}
		}
		xmlDom.load(url);
		if(!async)	return xmlDom;
	},
	
	//得到节点的属性
	getAttribute : function(pNode,pAttribute) {
		try{
			return pNode.attributes.getNamedItem(pAttribute).nodeValue;
		}catch(e){
			return null;
		}
	}
}