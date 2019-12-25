var hourskip=1;
var minskip=5;
function padLeft(str,length){
	if(str.length>=length){
		return str;
	}else{
		return padLeft("0"+str,length);
	}
}
$(function(){
	$('.timeRange').click(function(){
		$('#timeRange_div').remove();
		
		var hourOpts = '';
		for (i=0;i<24;i+=hourskip) hourOpts += '<option>'+padLeft(i+'',2)+'</option>';
		var minuteOpts = '';
		for (i=0;i<60;i+=minskip) minuteOpts += '<option>'+padLeft(i+'',2)+'</option>';
		var html;
		if(minskip>59){
			html = $('<div id="timeRange_div"><select id="timeRange_a">'+hourOpts+
					'</select><input type="hidden" id="timeRange_b" value="00"/>'+
					'-<select id="timeRange_c">'+hourOpts+
					'</select><input type="hidden" id="timeRange_d" value="00"/>'+
					'<input type="button" value="确定" id="timeRange_btn" /></div>')
					.css({
						"filter": "alpha(opacity=80)",
						"position": "absolute",
						"z-index": "999",
						"padding": "5px",
						"border": "1px solid #AAA",
						"background-color": "#FFF",
						"box-shadow": "1px 1px 3px rgba(0,0,0,.4)"
					})
					.click(function(){return false;});
		}else{
			html = $('<div id="timeRange_div"><select id="timeRange_a">'+hourOpts+
					'</select>:<select id="timeRange_b">'+minuteOpts+
					'</select>-<select id="timeRange_c">'+hourOpts+
					'</select>:<select id="timeRange_d">'+minuteOpts+
					'</select><input type="button" value="确定" id="timeRange_btn" /></div>')
					.css({
						"filter": "alpha(opacity=80)",
						"position": "absolute",
						"z-index": "999",
						"padding": "5px",
						"border": "1px solid #AAA",
						"background-color": "#FFF",
						"box-shadow": "1px 1px 3px rgba(0,0,0,.4)"
					})
					.click(function(){return false;});
		}
		// 如果文本框有值
		var v = $(this).val();
		if (v) {
			v = v.split(/:|-/);
			html.find('#timeRange_a').val(v[0]);
			html.find('#timeRange_b').val(v[1]);
			html.find('#timeRange_c').val(v[2]);
			html.find('#timeRange_d').val(v[3]);
		}
		// 点击确定的时候
		var pObj = $(this);
		html.find('#timeRange_btn').click(function(){
			var str = html.find('#timeRange_a').val()+':'
				+html.find('#timeRange_b').val()+'-'
				+html.find('#timeRange_c').val()+':'
				+html.find('#timeRange_d').val();
			var a=Number(html.find('#timeRange_a').val());
			var b=Number(html.find('#timeRange_b').val());
			var c=Number(html.find('#timeRange_c').val());
			var d=Number(html.find('#timeRange_d').val());
			if(a>c){
				alert("后者小时不可小于前者小时！");
				return false;
			}
			if(a==c&&b>d){
				alert("后者分钟不可小于前者分钟！");
				return false;
			}
			pObj.val(str);
			$('#timeRange_div').remove();
		});
		
		$(this).after(html);
		return false;
	});
	$(document).click(function(){
		$('#timeRange_div').remove();
	});
});