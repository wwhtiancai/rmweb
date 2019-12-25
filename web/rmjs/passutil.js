/**
 *�漰������Ϣ��ѯ��������js���� 
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
		//����ͼ��ʾ
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
		//�б���ʾ
		pass_getListHtml();
		pass_getPageHtml();
	}else if(comm_curShowStyle == 2){
		//����ͼ��ʾ
		comm_getThumbHtml();
		pass_getPageHtml();
	}
}

function pass_getListHtml(){
	pass_setLoaddataHtml();
	curHtml = '<table border="0" cellspacing="1" cellpadding="0" class="list">\n';
	curHtml += '<tr class="head">\n';
	curHtml += '<td width="10%">��������</td>\n';
	curHtml += '<td width="10%">���ƺ���</td>\n';
	curHtml += '<td width="10%">��������</td>\n';
	curHtml += '<td width="20%">��·����</td>\n';
	curHtml += '<td width="23%">���ڼ��</td>\n';
	curHtml += '<td width="12%">��ʻ����</td>\n';
	curHtml += '<td width="15%">����ʱ��</td>\n';
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
			//curHtml += '<td><span>�鿴��ϸ</span>&nbsp;<span>��ȷ׷��</span></td>';
			curHtml += '</tr>';
		});
	}
	//��' + totalCount + '��&nbsp;��' + comm_pageCount + 'ҳ&nbsp;��' + comm_curPage + 'ҳ &nbsp;
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
			//����ͼģʽ���зŴ󾵹���
			//curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="pass_showDetail(\'' + item['gcxh'] + '\',' + comm_curPage + ',' + i + ')">' + '<img class="passPicB" style="width:' + comm_passPicWidth + 'px;height:' + comm_passPicHeight + 'px;" src="' + imgSrc + '" jqimg="' + imgSrc + '"></div>';
			//����ͼģʽ���޷Ŵ󾵹���
			curH += '<td width="25%" valign="top">\n<div class="jqzoomPassQuery" style="width:100%;" ondblclick="pass_showDetail(\'' + item['gcxh'] + '\',' + comm_curPage + ',' + i + ')">' 
				+ '<img class="passPicB" id="'+item['gcxh']+'" style="width:' + comm_passPicWidth + 'px;height:' + comm_passPicHeight + 'px;" src=""></div>';
			curH += '<div>�������ࣺ' + decodeURIComponent(item['hpzlmc']) + '&nbsp;&nbsp;���ƺ��룺' + decodeURIComponent(item['hphm']) + '<br>';
			curH += '����������' + decodeURIComponent(item['xzqhmc']) + '<br>��·���ƣ�' + decodeURIComponent(item['dldmmc']) + '<br>';
			curH += '���ڼ�ƣ�' + decodeURIComponent(item['kkbhmc']) + '<br>��ʻ����' + decodeURIComponent(item['fxlxmc']) + '<br>';
			curH += '����ʱ�䣺' + decodeURIComponent(item['gcsj']).substring(0,19) + '<br></div></td>';
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
//        xzoom: 300, //���÷Ŵ� DIV ���ȣ�Ĭ��Ϊ 200��
//        yzoom: 300, //���÷Ŵ� DIV �߶ȣ�Ĭ��Ϊ 200��
//        offset: 10, //���÷Ŵ� DIV ƫ�ƣ�Ĭ��Ϊ 10��
//        position: "buttom", //���÷Ŵ� DIV ��λ�ã�Ĭ��Ϊ�ұߣ�
//        preload:1,
//        lens:1
//    });
}


function pass_setQueryingHtml(){
	$("#data").empty();
	queryingHtml = '<br><br><img alt="" src="images/running.gif"><br>���ڲ�ѯ�������ĵȺ�...';
	$("#data").html(queryingHtml);
	$("#thdQueryStatDiv").empty();
	queryingStatHtml = '<img src="rmjs/zoom/ajax-loader.gif">���ڲ�ѯ�����Ժ�...';
	$("#thdQueryStatDiv").html(queryingStatHtml);
}


function pass_setLoaddataHtml(){
	$("#data").empty();
	loadDataHtml = '<br><br><img alt="" src="images/running.gif"><br>�����������ݣ������ĵȺ�...';
	$("#data").html(loadDataHtml);
}


//�ı���ʵģʽ
function pass_doShowStyleChange(){
	var tmpShowStyle = $("input[id=showStyle][checked=true]").val();
	if(comm_passData == null){
		comm_curShowStyle = tmpShowStyle;
		return;
	}
	if(comm_curShowStyle != tmpShowStyle){
		if(tmpShowStyle == 1){
			//�б���ʾ
			pass_getListHtml();
			pass_getPageHtml();
		}else if(tmpShowStyle == 2){
			//����ͼ��ʾ
			comm_getThumbHtml();
			pass_getPageHtml();
		}
	}
	comm_curShowStyle = tmpShowStyle;
}

//�ύ�����ѯ���
function example_pass_query_cmd(){
	if(!doChecking()){
		return;
	}
	$("#thdQueryResultDiv").html('');
	closes();
	pass_setQueryingHtml();
	comm_curPage = 1;
	//�ύ��ѯ���
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

//�ύ����󣬴����ؽ��
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

//���صڼ�ҳ����
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

//���ط�ҳ��Ϣ
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
	pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(1)" style="cursor:hand">��ҳ</label>&nbsp;';
	pageHtml += "��&nbsp;" + pass_genPageNum(comm_curPage) + "ҳ&nbsp;";
	if(comm_curPage == 1){
		pageHtml += '<label id="pageCMD">��һҳ</label>&nbsp;';
	}else{
		pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(' + (comm_curPage - 1) + ')" style="cursor:hand">��һҳ</label>&nbsp;';
	}
	var i=parseInt(comm_curPage)-parseInt(comm_startPage)+1;
	if((comm_pageCount<5&&i==comm_pageCount)||comm_pageSize<20){
		pageHtml += '<label id="pageCMD">��һҳ</label>&nbsp;';
	}else{
		pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(' + (comm_curPage + 1) + ')" style="cursor:hand">��һҳ</label>&nbsp;';
	}
	//pageHtml += '<label id="pageCMD" onclick="pass_gotoPage(' + (comm_curPage + 1) + ')" style="cursor:hand">��һҳ</label>&nbsp;';
	$("#pagehtml").html(pageHtml);
}

function example_pass_showDetail(gcxh, pageNum, listNum){
	var detailUrl = "epasskeyQuery.ana?method=showKeyLocalDetail&gcxh=" +gcxh+"&page="+pageNum+"&idx="+listNum
			+"&cmds="+JSON.stringify(comm_condition);
	detailWin = openwin(detailUrl, "PassDetail");
}
