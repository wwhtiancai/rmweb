var detailWin = null;
var handledWin = null;
if (!window.console) {
	window.console = {
		log: function() {
			alert(arguments[0]);
		}
	}
}
$(window).bind("load",function(){
	$.datepicker.setDefaults($.datepicker.regional['']);
	$(".jscal").each(function () {
		eval($(this).html());
	});
});




window.onunload = function (){
	if(detailWin != null){
		detailWin.close();
	}
	if(handledWin != null){
		handledWin.close();
	}
}