/**
* ImagesView 1.0 - Zoom Drag ImageBox
* 
* auto by jinks.zhang@163.com
*
* Thanks for the community that is helping the improvement
* of this little piece of code.
*
* For usage instructions please visit http://www.nbblack.cn
*/

var imagesview_width;
var imagesview_height;
var imagesview_fix_width;
var imagesview_fix_height;

$(document).ready(function(){
	img_init('a.imagesview, area.imagesview, input.imagesview');//pass where to apply thickbox
});

function img_init(domChunk){
	$(domChunk).click(function(){
		var t = this.title || this.name || null;
		var a = this.href || this.alt;
		var g = this.rel || false;
		img_show(t,a,g);
		this.blur();
		return false;
	});
}

function img_show(caption, url, imageGroup) {
	try{
		
		if (typeof document.body.style.maxHeight === "undefined") {
			$("body","html").css({height: "100%", width: "100%"});
			$("html").css("overflow","hidden");
			if (document.getElementById("IV_HideSelect") === null) {
				imgvwBox().append("<iframe id='IV_HideSelect'></iframe><div id='IV_overlay'></div><div id='IV_window'></div>");
				$("#IV_overlay").click(iv_remove);
			}
		}else{
			if(document.getElementById("IV_overlay") === null){
				imgvwBox().append("<div id='IV_overlay'></div><div id='IV_window'></div>");
				$("#IV_overlay").click(iv_remove);
			}
		}
		
		if(iv_detectMacXFF()){
			$("#IV_overlay").addClass("IV_overlayMacFFBGHack");//use png overlay so hide flash
		}else{
			$("#IV_overlay").addClass("IV_overlayBG");//use background and opacity
		}
		
		if(caption===null){caption="";}
		
		imgvwBox().append("<div id='IV_load'>loading..</div>");//add loader to the page
		$('#IV_load').show();//show loader
		
		var baseURL;
		if(url.indexOf("?")!==-1){ //ff there is a query string involved
			baseURL = url.substr(0, url.indexOf("?"));
		}else{ 
			baseURL = url;
		}
	
		var urlString = /\.jpg$|\.jpeg$|\.png$|\.gif$|\.bmp$/;
		var urlType = baseURL.toLowerCase().match(urlString);

		urlType = '.jpg';	
		//code to show images
		if(urlType == '.jpg' || urlType == '.jpeg' || urlType == '.png' || urlType == '.gif' || urlType == '.bmp'){
			
			IV_PrevCaption = "";
			IV_PrevURL = "";
			IV_PrevHTML = "";
			IV_NextCaption = "";
			IV_NextURL = "";
			IV_NextHTML = "";
			IV_imageCount = "";
			IV_FoundURL = false;
			
			if(imageGroup){
				
			}
			
			imgPreloader = new Image();
			
			imgPreloader.onload = function(){	
			
				imgPreloader.onload = null;
				
				// Resizing large images - orginal by Christian Montoya edited by me.
				var pagesize = iv_getPageSize();
				var x = pagesize[0] - 150;
				var y = pagesize[1] - 150;
				var imageWidth = imgPreloader.width;
				var imageHeight = imgPreloader.height;
				imagesview_width = imageWidth;
				imagesview_height = imageHeight;
				if (imageWidth > x) {
					imageHeight = imageHeight * (x / imageWidth); 
					imageWidth = x; 
					if (imageHeight > y) { 
						imageWidth = imageWidth * (y / imageHeight); 
						imageHeight = y; 
					}
				} else if (imageHeight > y) { 
					imageWidth = imageWidth * (y / imageHeight); 
					imageHeight = y; 
					if (imageWidth > x) { 
						imageHeight = imageHeight * (x / imageWidth); 
						imageWidth = x;
					}
				}
				imagesview_fix_width = imageWidth;
				imagesview_fix_height = imageHeight;
				// End Resizing
				
				IV_WIDTH = x;
				IV_HEIGHT = y + 60;
				
				var toolbar = "<div id='IV_toolbar'><a class='IV_ico IV_zoomIn' title='缩小'></a><a class='IV_ico IV_zoomOut' title='放大'></a><a class='IV_ico IV_zoomAuto' title='实际大小'></a><a class='IV_ico IV_zoomFix' title='合适大小'></a></div>";
				
				$("#IV_window").append("<div id='IV_header'><div id='IV_caption'>"+caption+"<div id='IV_secondLine'>" + IV_imageCount + IV_PrevHTML + IV_NextHTML + "</div></div>" + toolbar + "<div id='IV_closeWindow'><a href='#' id='IV_closeWindowButton' title='Close'>Close</a> or Esc Key</div></div>" + "<img id='IV_Image' src='"+url+"' width='"+imageWidth+"' height='"+imageHeight+"' alt='"+caption+"'/>"); 

				$("#IV_closeWindowButton").click(iv_remove);
				
				if (!(IV_PrevHTML === "")) {
					function goPrev(){
						if($(document).unbind("click",goPrev)){$(document).unbind("click",goPrev);}
						$("#IV_window").remove();
						imgvwBox().append("<div id='IV_window'></div>");
						iv_show(IV_PrevCaption, IV_PrevURL, imageGroup);
						return false;	
					}
					$("#IV_prev").click(goPrev);
				}
				
				if (!(IV_NextHTML === "")) {
					function goNext(){
						$("#IV_window").remove();
						imgvwBox().append("<div id='IV_window'></div>");
						iv_show(IV_NextCaption, IV_NextURL, imageGroup);				
						return false;	
					}
					$("#IV_next").click(goNext);
				}
				
				document.onkeydown = function(e){ 
					if (e == null) { // ie
						keycode = event.keyCode;
					} else { // mozilla
						keycode = e.which;
					}
					if(keycode == 27){ // close
						iv_remove();
					} else if(keycode == 190){ // display previous image
						if(!(IV_NextHTML == "")){
							document.onkeydown = "";
							goNext();
						}
					} else if(keycode == 188){ // display next image
						if(!(IV_PrevHTML == "")){
							document.onkeydown = "";
							goPrev();
						}
					}	
				};
				
				iv_position();
				
				$("#IV_load").remove();
				$("#IV_window").css({display:"block"}); //for safari using css instead of show
				
				//$("#IV_Image").click(function(){ return false;	});
				//$("#IV_Image").bind('contextmenu', function(e) { return false; });	
				
				updatePosition($("#IV_Image"));
				
				$("#IV_Image").easydrag(true);
				
				$(".IV_zoomIn").click(function(){ zoomIn($("#IV_Image")); });
				$(".IV_zoomOut").click(function(){ zoomOut($("#IV_Image")); });
				$(".IV_zoomFix").click(function(){ zoomFix($("#IV_Image")); });
				$(".IV_zoomAuto").click(function(){ zoomAuto($("#IV_Image")); });
								
			};

			imgPreloader.src = url;
			
		}
	} catch(e) {
		alert(e);
	}
	
}

function updatePosition(o){
	
	var w_height = $("#IV_window").height() - 30;
	var w_width  = $("#IV_window").width();
	
	if( $(o).height() < w_height ){
		var margin_top = (w_height - $(o).height()) / 2 ;
		$(o).css({top: margin_top + 'px'});
	}else{
		if( $(o)[0].offsetTop > 30 ){
			$(o).css({top: '30px'});
		}
		if( $(o)[0].offsetTop < -1 * ($(o).height() - w_height - 30) ){
			$(o).css({top: -1 * ($(o).height() - w_height -30) + 'px'});
		}
	}
	
	if( $(o).width() < w_width ){
		var margin_left =  (w_width - $(o).width()) / 2 ;
		$(o).css({left: margin_left + 'px'});
	}else{
		if( $(o)[0].offsetLeft > 0 ){
			$(o).css({left: '0px'});
		}
		if( $(o)[0].offsetLeft < -1 * ( $(o).width() - w_width )  ){
			$(o).css({left: -1 * ($(o).width() - w_width) + 'px'});
		}
	}
}

function zoomAuto(o){
	$(o).width(imagesview_width);
	$(o).height(imagesview_height);
	updatePosition(o);
	return false; 
}

function zoomFix(o){
	$(o).width(imagesview_fix_width);
	$(o).height(imagesview_fix_height);
	updatePosition(o);
	return false; 
}

function zoomIn(o){
	if($(o).width() > 50){
		$(o).width( $(o).width() - $(o).width()*0.3 );
		$(o).height( $(o).height() - $(o).height()*0.3 );
		
		updatePosition(o);
	}
	return false; 	
}

function zoomOut(o){
	$(o).width( $(o).width() + $(o).width()*0.3 );
	$(o).height( $(o).height() + $(o).height()*0.3 );

	updatePosition(o);

	return false; 	
}

function imgvwBox(){
	var o = document.getElementById("#imagesview");
	if(o === null){
		return $("body");
	}else{
		return $(o);
	}
}

function iv_detectMacXFF() {
  var userAgent = navigator.userAgent.toLowerCase();
  if (userAgent.indexOf('mac') != -1 && userAgent.indexOf('firefox')!=-1) {
    return true;
  }
}

function iv_getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	arrayPageSize = [w,h];
	return arrayPageSize;
}

function iv_position() {
	$("#IV_window").css({marginLeft: '-' + parseInt((IV_WIDTH / 2),10) + 'px', width: IV_WIDTH + 'px', height: IV_HEIGHT + 'px'});
	if ( !(jQuery.browser.msie && jQuery.browser.version < 7)) { // take away IE6
		$("#IV_window").css({marginTop: '-' + parseInt((IV_HEIGHT / 2),10) + 'px'});
	}
}

function iv_remove() {
	$("#IV_closeWindowButton").unbind("click");
	$("#IV_window").fadeOut("fast",function(){$('#IV_window,#IV_overlay,#IV_HideSelect').trigger("unload").unbind().remove();});
	$("#IV_load").remove();
	if (typeof document.body.style.maxHeight == "undefined") {//if IE 6
		$("body","html").css({height: "auto", width: "auto"});
		$("html").css("overflow","");
	}
	document.onkeydown = "";
	document.onkeyup = "";
	return false;
}


/**
* EasyDrag 1.4 - Drag & Drop jQuery Plug-in
*
* Thanks for the community that is helping the improvement
* of this little piece of code.
*
* For usage instructions please visit http://fromvega.com
*/

(function($){

	// to track if the mouse button is pressed
	var isMouseDown    = false;

	// to track the current element being dragged
	var currentElement = null;

	// callback holders
	var dropCallbacks = {};
	var dragCallbacks = {};

	// global position records
	var lastMouseX;
	var lastMouseY;
	var lastElemTop;
	var lastElemLeft;
	
	// track element dragStatus
	var dragStatus = {};	

	// returns the mouse (cursor) current position
	$.getMousePosition = function(e){
		var posx = 0;
		var posy = 0;

		if (!e) var e = window.event;

		if (e.pageX || e.pageY) {
			posx = e.pageX;
			posy = e.pageY;
		}
		else if (e.clientX || e.clientY) {
			posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
			posy = e.clientY + document.body.scrollTop  + document.documentElement.scrollTop;
		}

		return { 'x': posx, 'y': posy };
	};

	// updates the position of the current element being dragged
	$.updatePosition = function(e) {
		var pos = $.getMousePosition(e);

		var spanX = (pos.x - lastMouseX);
		var spanY = (pos.y - lastMouseY);

		$(currentElement).css("top",  (lastElemTop + spanY));
		$(currentElement).css("left", (lastElemLeft + spanX));
		
		//$("#IV_caption").html( (lastElemTop + spanY)  + ' : ' + (lastElemLeft + spanX));
	};

	// when the mouse is moved while the mouse button is pressed
	$(document).mousemove(function(e){
		if(isMouseDown && dragStatus[currentElement.id] == 'on'){
			// update the position and call the registered function
			$.updatePosition(e);
			if(dragCallbacks[currentElement.id] != undefined){
				dragCallbacks[currentElement.id](e, currentElement);
			}

			return false;
		}
	});

	// when the mouse button is released
	$(document).mouseup(function(e){
		if(isMouseDown && dragStatus[currentElement.id] == 'on'){
			isMouseDown = false;
			if(dropCallbacks[currentElement.id] != undefined){
				dropCallbacks[currentElement.id](e, currentElement);
			}

			return false;
		}
	});

	// register the function to be called while an element is being dragged
	$.fn.ondrag = function(callback){
		return this.each(function(){
			dragCallbacks[this.id] = callback;
		});
	};

	// register the function to be called when an element is dropped
	$.fn.ondrop = function(callback){
		return this.each(function(){
			dropCallbacks[this.id] = callback;
		});
	};
	
	// stop the element dragging feature
	$.fn.dragOff = function(){
		return this.each(function(){
			dragStatus[this.id] = 'off';
		});
	};
	
	
	$.fn.dragOn = function(){
		return this.each(function(){
			dragStatus[this.id] = 'on';
		});
	};

	// set an element as draggable - allowBubbling enables/disables event bubbling
	$.fn.easydrag = function(allowBubbling){

		return this.each(function(){

			// if no id is defined assign a unique one
			if(undefined == this.id || !this.id.length) this.id = "easydrag"+(new Date().getTime());

			// set dragStatus 
			dragStatus[this.id] = "on";
			
			// change the mouse pointer
			$(this).css("cursor", "move");

			// when an element receives a mouse press
			$(this).mousedown(function(e){

				// set it as absolute positioned
				$(this).css("position", "absolute");

				// update track variables
				isMouseDown    = true;
				currentElement = this;

				// retrieve positioning properties
				var pos    = $.getMousePosition(e);
				lastMouseX = pos.x;
				lastMouseY = pos.y;

				lastElemTop  = this.offsetTop;
				lastElemLeft = this.offsetLeft;

				$.updatePosition(e);

				return allowBubbling ? true : false;
			});
		});
	};

})(jQuery);