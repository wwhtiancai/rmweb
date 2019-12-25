//charset "gbk"
function changeHtml(html){
	var h1 = html.replace('border=0','border=1 style="font-size:12px;" width="100%" ');
	var h2 = h1.replace(new RegExp('<td class=data','gi'),'<TD style="font-sze:12px;text-align:right;height:22px;"');
	var h3 = h2.replace(new RegExp('<td class=head','gi'),'<TD style="font-sze:12px;text-align:center;background-color:#D7F2F9;height:22px;"');
	var h4 = h3.replace(new RegExp('<td class=title','gi'),'<TD style="font-sze:12px; text-align:center; font-weight:bold;background-color: #D7F2F9;height:22px;"');
	var h5 = h4.replace(new RegExp('<td class=subtotal','gi'),'<TD style="font-sze:12px; text-align:center; background-color: #FFFF99;height:22px;"');
	var h6 = h5.replace(new RegExp('<td class=total','gi'),'<TD style="font-sze:12px; text-align:center; background-color: #CC99FF;height:22px;"');
	
	var h7 = h6.replace(new RegExp('<div class=title','gi'),'<DIV style="width:100%;font-size: 28px;font-weight:bold;text-align: center;"');
	var h8 = h7.replace(new RegExp('<div class=subtitleleft','gi'),'<DIV style="width:100%;font-size:12px;font-weight:normal;text-align:left;"');
	var h9 = h8.replace(new RegExp('<div class=subtitleright','gi'),'<DIV style="width:100%;font-size:12px;font-weight:normal;text-align:right;"');
	var h10 = h9.replace(new RegExp(' class="stats comments"','gi'),' style="width:100%;font-size:12px;font-weight:normal;text-align:left;"');

	return h10;
}

function word(html){
	var xlsWindow = window.open("", "_blank", "width=1,height=1,scrollbars=no,toolbar=no");
    xlsWindow.document.write(changeHtml(html));
    xlsWindow.document.close();
    xlsWindow.document.execCommand('Saveas', true, '%homeDrive%\\Data.doc');
    xlsWindow.close();
}

function excel(html){
	var xlsWindow = window.open("", "_blank", "width=1,height=1,scrollbars=no,toolbar=no");
    xlsWindow.document.write(changeHtml(html));
    xlsWindow.document.close();
    xlsWindow.document.execCommand('Saveas', true, '%homeDrive%\\Data.xls');
    xlsWindow.close();
}

function excelbyname(html,name){
	var xlsWindow = window.open("", "_blank", "width=1,height=1,scrollbars=no,toolbar=no");
	xlsWindow.document.write(changeHtml(html));
	xlsWindow.document.close();
	xlsWindow.document.execCommand('Saveas', true, '%homeDrive%\\'+name+'.xls');
	xlsWindow.close();
}

function excel2(html) {
	var xlsWindow = window.open("", "_blank", "width=1,height=1,scrollbars=no,toolbar=no");
    xlsWindow.document.write(html);
    xlsWindow.document.close();
    xlsWindow.document.execCommand('Saveas', true, '%homeDrive%\\Data.xls');
    xlsWindow.close();
}

function pdf(html){
}

function zzt(charttype, xmldata){
	var chart;
	if(charttype == '0'){
		chart = new FusionCharts("chart/charts/Column3D.swf", "chart", "100%", "350");
	}else{
		chart = new FusionCharts("chart/charts/StackedColumn3D.swf", "chart", "100%", "350");
	}
	chart.setDataXML(xmldata);	   
	chart.render("chart");
}

function bt(xmldata){
	var chart = new FusionCharts("chart/charts/Pie3D.swf", "chart", "100%", "350");
	chart.setDataXML(xmldata);	   
	chart.render("chart");
}

function gohelp(hurl){
	window.open(hurl, "_blank");
}