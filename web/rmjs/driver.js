function getDriver(sfzmhm) {
	curUrl = "<c:url value='alarm.alert?method=getDriver&sfzmhm='/>" + sfzmhm ;
	
	$.getJSON(curUrl, function(data) {
		code = data['code'];
		if (code == 1) {
			msg = data['msg'];
			$("#sfzmhm").html(msg['drvsfzmhm']);
			$("#xm").html(decodeURIComponent(msg['drvxm']));
			$("#yxqz").html(msg['drvyxqz']);
			$("#zjcx").html(decodeURIComponent(msg['drvzjcxmc']));
			$("#fzjg").html(decodeURIComponent(msg['drvfzjg']));
			$("#cfrq").html(msg['drvcfrq']);
			$("#ljjf").html(msg['drvljjf']);
			$("#jszzt").html(decodeURIComponent(msg['drvjszzt']));			

		} else {
			alert(decodeURIComponent(data['msg']));
		}
	});
}