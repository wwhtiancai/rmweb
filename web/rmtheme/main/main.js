function htmlshow(){
	if($("#block").size()>=1){
		block();
	}
	if($("#query").size()==1){
		query();
	}
	if($("#result").size()==1){
		result();
	}
	if($("#panel").size()==1){
		panel();
	}
}
var titles;
var cookie = readCookie("mytheme");
if(cookie=="null"||cookie==null||cookie==""){
	titles=getActiveStyleSheet();
} else{
	titles=cookie;
}
setActiveStyleSheet(titles);
$(function(){htmltheme();initbutton();});
function readCookie(nm){
	var m=""; 
	if(window.RegExp){ 
		var re=new RegExp(";\\s*"+nm+"=([^;]*)","i"); 
		m=re.exec(';'+document.cookie); 
	}
	return(m?unescape(m[1]):"");
}
function getActiveStyleSheet(){
	var t;
	try{
		t=$("link:first").attr("href");
		t=t.split("/")[t.split("/").length-1];
		t=t.substr(0,t.indexOf('.css'));
	}catch(e){
		r="main";
	}
	return t;
}
function setActiveStyleSheet(t){
	try{
		$("link:first").attr("href","rmtheme/"+t+"/main.css");	
	}catch(e){}
}
function htmlload(urls){
	$.ajax({url:urls,async:false,success:function(html){
		if(html!=null){ 
			var oHead = document.getElementsByTagName('HEAD').item(0); 
			var oScript = document.createElement("script"); 
			oScript.language = "javascript";
			oScript.type = "text/javascript";
			oScript.id = "srcA";
			oScript.defer = true;
			oScript.text = html;
			oHead.appendChild(oScript); 
		}
		htmlshow();
	}});
}
function htmltheme(){
	if(titles==null||titles.length<1){
		titles="main";
	}
	htmlload("./rmtheme/"+titles+"/theme.js?ver=1.110");
}
function initbutton(){
	if (typeof jQuery != 'undefined'){
		$(":button").each(function(){
			var className=$(this).attr("class");
			if(typeof className=='undefined'||className==''){
				$(this).attr("class","button white small");
			}
		});
	}else{
		return;
	}
}

function errorHandler(data, statusCode, xhr) {
	return function (xhr) {
		if (statusCode && statusCode[xhr.status]) {
			return statusCode[xhr.status]();
		}
		if (xhr.status == 404) {
			window.top.location.href = "";
		} else if (xhr.status == 500) {
			window.top.location.href = "";
		} else {
			window.top.location.href = "error.html";
		}
	};
}

function reload()
{
	try{
		var mainWindow = self;
		if(self.opener) {
			self.opener.reload();
			self.close();
		} else {
			while(mainWindow != top) {
				mainWindow = mainWindow.parent;
			}
			mainWindow.location.href = "index.jsp";
		}

	}catch(ex){}
}

