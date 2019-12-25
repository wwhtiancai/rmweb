/**
 *涉及过车信息查询，公共的js函数 
 */
var comm_passData = null;
var comm_curPage;
var comm_startPage;
var comm_pageCount;
var comm_pageSize;
var comm_condition=null;
var comm_curShowStyle;
var comm_passPicWidth;
var comm_passPicHeight;
var comm_resinterval;
var comm_pageinterval;
var comm_stop;

window.onresize = function(){
	pass_initSize();
};

$(document).ready(function(){
	comm_passData = null;
	comm_curShowStyle = $("input[name=showStyle]:checked").val();
	pass_initSize();
	pass_emptyDataDiv();
	closes();
	//initpanel();
	opens();
});

function pass_emptyDataDiv(){
	$("#data").empty();
}

function pass_initSize(){
	var bodyWidth = $(document.body).width();
	comm_passPicWidth = parseInt((bodyWidth - 40) * 0.96 * 0.25);
	comm_passPicHeight = parseInt(comm_passPicWidth * 0.8);
	if(comm_curShowStyle == 2){
		//缩略图显示
		$(".passPicB").css("width", comm_passPicWidth + "px");
		$(".passPicB").css("height", comm_passPicHeight + "px");
	}
}

function pass_localReturns(data){
	var code = data['code'];
	var msg = data['msg'];
	var cmds=data['condition'];
	comm_curPage = data['curPage'];
	comm_startPage = data['startPage'];
	comm_pageCount = data['pageCount'];
	comm_pageSize = data['pageSize'];
	if(code == 1){
		comm_passData = msg;
		comm_condition = cmds;
		pass_reloadData();
		//window.clearInterval(comm_resinterval);
		//pass_getpages();
		//comm_pageinterval=window.setInterval("pass_getpages()",1000);
	}
	else{
		$("#data").empty();
		alert(decodeURIComponent(msg));
	}
	opens();
}

function pass_reloadData(){
	if(comm_curShowStyle == 1){
		//列表显示
		pass_getListHtml();
		pass_getPageHtml();
	}else if(comm_curShowStyle == 2){
		//缩略图显示
		comm_getThumbHtml();
		pass_getPageHtml();
	}
}

function pass_getListHtml(){
	pass_setLoaddataHtml();
	curHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list">\n';
	curHtml += '<tr class="head">\n';
	curHtml += '<td width="10%">号牌种类</td>\n';
	curHtml += '<td width="10%">号牌号码</td>\n';
	curHtml += '<td width="10%">行政区划</td>\n';
	curHtml += '<td width="20%">道路名称</td>\n';
	curHtml += '<td width="23%">卡口简称</td>\n';
	curHtml += '<td width="12%">行驶方向</td>\n';
	curHtml += '<td width="15%">过车时间</td>\n';
	curHtml += '</tr>\n';
	
	if(comm_passData!=null){
		$.each(comm_passData, function(i,item){
			curHtml += '<tr class="out" style="cursor:pointer" onMouseOver="this.className=\'over\'" onMouseOut="this.className=\'out\'" ondblclick="pass_showDetail(\'' + item['gcxh'] + '\',' + comm_curPage + ',' + i + ')">\n';
			curHtml += '<td>' + decodeURIComponent(item['hpzlmc']) + '</td>';
			curHtml += '<td class="hpys' + item['hpys'] + '">' + decodeURIComponent(item['hphm']) + '</td>';
			curHtml += '<td>' + decodeURIComponent(item['xzqhmc']) + '</td>';
			curHtml += '<td>' + decodeURIComponent(item['dldmmc']) + '</td>';
			curHtml += '<td>' + decodeURIComponent(item['kkbhmc']) + '</td>';
			curHtml += '<td>' + decodeURIComponent(item['fxlxmc']) + '</td>';
			curHtml += '<td>' + item['gcsj'].substring(0, 19) + '</td>';
			//curHtml += '<td><span>查看详细</span>&nbsp;<span>精确追踪</span></td>';
			curHtml += '</tr>';
		});
	}
	//共' + totalCount + '条&nbsp;共' + comm_pageCount + '页&nbsp;第' + comm_curPage + '页 &nbsp;
	curHtml += '<tr><td colspan="8" class="page" id="pagehtml">';
	curHtml += '</td></tr>';	
	curHtml += '</table>';
	$("#data").empty();
	$("#data").html(curHtml);
	$(document.doby).scrollTop($(document.doby).height());
}


function pass_genPageNum(num) {
	var inputnum = parseInt(num);
	var pageString = "";
	var end = parseInt(comm_pageCount)+parseInt(comm_startPage)-1;
	for(var i = comm_startPage; i <= end; i++) {
		if(inputnum == i) {
			pageString += '<label id="pageCMD">' + i + '</label>&nbsp;';
		} else {
			pageString += '<label id="pageCMD" onclick="pass_gotoPage(' + i + ')" style="cursor:hand"><U>' + i + '</U></label>&nbsp;';
		}
	}
	return pageString;
}


function comm_getThumbHtml(){
	pass_setLoaddataHtml();
	curHtml = '';
	curCount = 0;
	curHtml += '<table border="0" cellpadding="0" cellspacing="6px" width="100%" class="thumbTable">';
	curH = '';
	if(comm_passData!=null){
		$.each(comm_passData, function(i,item){
			if(i > 0 && (i % 4) == 0){
				curHtml += '<tr>' + curH + '</tr>';
				curH = '';
			}
			//缩略图模式中有放大镜功能
			//curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="pass_showDetail(\'' + item['gcxh'] + '\',' + comm_curPage + ',' + i + ')">' + '<img class="passPicB" style="width:' + comm_passPicWidth + 'px;height:' + comm_passPicHeight + 'px;" src="' + imgSrc + '" jqimg="' + imgSrc + '"></div>';
			//缩略图模式中无放大镜功能
			curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="pass_showDetail(\'' + item['gcxh'] + '\',' + comm_curPage + ',' + i + ')">' 
				+ '<img class="passPicB" id="'+item['gcxh']+'" style="width:' + comm_passPicWidth + 'px;height:' + comm_passPicHeight + 'px;" src=""></div>';
			curH += '<div>号牌种类：' + decodeURIComponent(item['hpzlmc']) + '&nbsp;&nbsp;号牌号码：' + decodeURIComponent(item['hphm']) + '<br>';
			curH += '行政区划：' + decodeURIComponent(item['xzqhmc']) + '<br>道路名称：' + decodeURIComponent(item['dldmmc']) + '<br>';
			curH += '卡口简称：' + decodeURIComponent(item['kkbhmc']) + '<br>行驶方向：' + decodeURIComponent(item['fxlxmc']) + '<br>';
			curH += '过车时间：' + decodeURIComponent(item['gcsj']).substring(0,19) + '<br></div></td>';
			curCount = i;
		});
		if((curCount+1 % 4) != 0 || curCount == 0){
			//curHtml += '</div>';
			tmpIdx = 1;
			if(curCount == 0){
				tmpIdx = 1;
			}else{
				tmpIdx = curCount % 4;
			}
			
			for(var k = tmpIdx; k < 4; k++){
				curH += '<td width="25%"></td>';
			}
			
			curHtml += '<tr>' + curH + '</tr>';
		}
	}
	curHtml += '<tr><td colspan="8" class="page" id="pagehtml">';
	curHtml += '</td></tr>';	
	curHtml += '</table>';
	$("#data").empty();
	$("#data").html(curHtml);
	$(document.doby).scrollTop($(document.doby).height());
	
	if(comm_passData!=null){
		$.each(comm_passData, function(i,item){
			var curUrl2 = "pic.tfc?method=getTplj&gcxh=" + item['gcxh'];
			$.ajaxSettings.async=true;
			$.getJSON(curUrl2, 
				function(data){
					opens();
					code = data['code'];
					if(code == 1){
						var tmpFwdz = decodeURIComponent(data['fwdz']);
						var imgSrc = '&gcxh=' + item['gcxh'] + '&tpxh=1';
						if(tmpFwdz != null && tmpFwdz != ''){
							imgSrc = tmpFwdz + '/readPassPic.tfc?method=getPassPic' + imgSrc;
						}else{
							imgSrc = 'readPassPic.tfc?method=getPassPic' + imgSrc;
						}
						$("#"+item['gcxh']).attr("src",imgSrc);
						//$("#"+item['gcxh']).attr("jqimg",imgSrc);
					}
				}		
			);
		});
	}
	
	
//	$(".jqzoomPassQuery").jqueryzoom({
//        xzoom: 300, //设置放大 DIV 长度（默认为 200）
//        yzoom: 300, //设置放大 DIV 高度（默认为 200）
//        offset: 10, //设置放大 DIV 偏移（默认为 10）
//        position: "buttom", //设置放大 DIV 的位置（默认为右边）
//        preload:1,
//        lens:1
//    });
}


function pass_setQueryingHtml(){
	$("#data").empty();
	queryingHtml = '<br><br><img alt="" src="images/running.gif"><br>正在查询，请耐心等候...';
	$("#data").html(queryingHtml);
	$("#thdQueryStatDiv").empty();
	queryingStatHtml = '<img src="rmjs/zoom/ajax-loader.gif">正在查询，请稍后...';
	$("#thdQueryStatDiv").html(queryingStatHtml);
}


function pass_setLoaddataHtml(){
	$("#data").empty();
	loadDataHtml = '<br><br><img alt="" src="images/running.gif"><br>正在整理数据，请耐心等候...';
	$("#data").html(loadDataHtml);
}


//改变现实模式
function pass_doShowStyleChange(){
	var tmpShowStyle = $("input[id=showStyle][checked=true]").val();
	if(comm_passData == null){
		comm_curShowStyle = tmpShowStyle;
		return;
	}
	if(comm_curShowStyle != tmpShowStyle){
		if(tmpShowStyle == 1){
			//列表显示
			pass_getListHtml();
			pass_getPageHtml();
		}else if(tmpShowStyle == 2){
			//缩略图显示
			comm_getThumbHtml();
			pass_getPageHtml();
		}
	}
	comm_curShowStyle = tmpShowStyle;
}

//提交请求查询结果
function example_pass_query_cmd(){
	if(!doChecking()){
		return;
	}
	$("#thdQueryResultDiv").html('');
	closes();
	pass_setQueryingHtml();
	comm_curPage = 1;
	//提交查询结果
	var tmpUrl = "epasskeyQuery.ana?method=fuzzyQueryLocal";
	$("#myform").ajaxSubmit({
		url:tmpUrl,
		dataType:"json",
		async:true,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			comm_resinterval=window.setInterval("pass_dowork()",50);
		}
	});
}

//提交请求后，处理返回结果
function example_pass_dowork(){
	var tmpUrl = "epasskeyQuery.ana?method=fuzzyQueryLocalResult";
	$("#myform").ajaxSubmit({
		url:tmpUrl,
		dataType:"json",
		async:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			pass_localReturns(data);
		}
	});
}

//加载第几页内容
function example_pass_gotoPage(idx){
	closes();
	$("#pageCMD").attr("disabled", true);
	pass_setQueryingHtml();

	var tmpUrl = "epasskeyQuery.ana?method=fuzzyQueryLocalResult" + "&page=" + idx;

	$("#myform").ajaxSubmit({
		url:tmpUrl,
		dataType:"json",
		async:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			comm_curPage = idx;
			pass_localReturns(data);
		}
	});
	opens();
}

//加载分页信息
function example_pass_getpages(){
	if(comm_stop==1){
		var pageurl="epasskeyQuery.ana?method=queryIndexlist&curpage="+comm_curPage;
		$("#myform").ajaxSubmit({
			url:pageurl,
			dataType:"json",
			async:true,
			contentType:"application/x-www-form-urlencoded;charset=utf-8",
			success:function(data){
				pass_indexReturns(data);
			}
		});
	}
}

function pass_indexReturns(data){
	//var code = data['code'];
	//comm_pageCount = data['pageCount'];
	//if(code==1){
	//	window.clearInterval(comm_pageinterval);
	//}
	pass_getPageHtml(code);
}

function pass_getPageHtml(){
	var pageHtml="";
	pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(1)" style="cursor:hand">首页</label>&nbsp;';
	pageHtml += "第&nbsp;" + pass_genPageNum(comm_curPage) + "页&nbsp;";
	if(comm_curPage == 1){
		pageHtml += '<label id="pageCMD">上一页</label>&nbsp;';
	}else{
		pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(' + (comm_curPage - 1) + ')" style="cursor:hand">上一页</label>&nbsp;';
	}
	var i=parseInt(comm_curPage)-parseInt(comm_startPage)+1;
	if((comm_pageCount<5&&i==comm_pageCount)||comm_pageSize<20){
		pageHtml += '<label id="pageCMD">下一页</label>&nbsp;';
	}else{
		pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(' + (comm_curPage + 1) + ')" style="cursor:hand">下一页</label>&nbsp;';
	}
	//pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(' + (comm_curPage + 1) + ')" style="cursor:hand">下一页</label>&nbsp;';
	$("#pagehtml").html(pageHtml);
}

function example_pass_showDetail(gcxh, pageNum, listNum){
	var detailUrl = "epasskeyQuery.ana?method=showKeyLocalDetail&gcxh=" +gcxh+"&page="+pageNum+"&idx="+listNum
			+"&cmds="+JSON.stringify(comm_condition);
	detailWin = openwin(detailUrl, "PassDetail");
}
