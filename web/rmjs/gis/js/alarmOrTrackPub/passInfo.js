$(document).ready(function() {
	//【1】填充号牌种类到页面之中
	fillHPZLToPage("hpzl_select",hpzlList);		
	//【3】填充查询原因到页面之中
	fillQSJGToPage("cxyy_select", cxyyList);
	setDefaultStartEndTime();
	setDefaultCondition();//设置当前卡口默认查询条件
});

var pageLimit=10;

/**
 * 默认为当前卡口通过车辆
 * @return
 */
function setDefaultCondition(){
	var hphmValue=getURLParamValue("hphm");
	var hpzlValue=getURLParamValue("hpzl");
	//设置挡墙的号牌号码
	$("#hphm_text").val(hphmValue);
	if(hpzlValue!="undefined"){
		$("#hpzl_select").combobox('setValue',hpzlValue);
	}else{
		$("#hpzl_select").combobox('setValue','no');
	}
	$("#cxyy_select").combobox('setValue','9');
}
/**
* 根据缉查布控系统要求设置默认的开始和结束时间
*/
function setDefaultStartEndTime(){
	var curDate=new Date().Format("yyyy-MM-dd hh:mm:ss"); 	
	//设置开始时间为当前之前往前推4小时
	var dateTemp= new Date();	
	dateTemp.setHours(dateTemp.getHours()-4);
	var dateTempFormat=dateTemp.Format("yyyy-MM-dd hh:mm:ss");
	//alert("dateTempFormat "+dateTempFormat);
	$('#gccx_start_date').datetimebox('setValue', dateTempFormat);	
	//设置结束时间为当前时间
	$('#gccx_end_date').datetimebox('setValue', curDate);		
}

function verifyCondition() {
	var verifyResult = true;
	var carLicenses = $("#hphm_text").val();
	var carLicenseType = $("#hpzl_select").combobox('getValue');
	var startDate = $("#gccx_start_date").datetimebox('getValue');
	var endDate = $("#gccx_end_date").datetimebox('getValue');
	switch (true) {
	case carLicenseType == "no":
		$.messager.alert("提示消息", "查询条件中号牌种类不能为空！", "info");
		verifyResult = false;
		break;
	case carLicenses == "":
		$.messager.alert("提示消息", "查询条件中号牌号码不能为空！", "info");
		verifyResult = false;
		break;
	case startDate == "":
		$.messager.alert("提示消息", "查询条件中号牌号码不能为空！", "info");
		verifyResult = false;
		break;
	case endDate == "":
		$.messager.alert("提示消息", "查询条件中号牌号码不能为空！", "info");
		verifyResult = false;
		break;
	}
	return verifyResult;
	}

function getParameters(){
	var analysisCondition={
			"query_car_license":"",//车牌号
			"query_license_type":"",//车牌种类
			"query_start_date":"",//查询开始日期
			"query_end_date":"",//查询结束日期
			"query_reason":"",//查询原因
			"query_district":""//查询限制区域			
	};
	var carLicenses=$("#hphm_text").val();
	var carLicenseType=$('#hpzl_select').combobox('getValue');
	var queryReason=$('#cxyy_select').combobox('getValue');
	var startDate=$('#gccx_start_date').datetimebox('getValue');
	var endDate=$('#gccx_end_date').datetimebox('getValue');
	analysisCondition.query_car_license=encodeURI(carLicenses);
	analysisCondition.query_license_type=carLicenseType;
	analysisCondition.query_start_date=startDate;
	analysisCondition.query_end_date=endDate;
	analysisCondition.query_reason=queryReason;
	analysisCondition.query_district="";
	return analysisCondition;
}


/**
 * 请求获得车辆过车信息
 */
function getVehiclePassInfo(){	
	var verifyResult=verifyCondition();
	if(!verifyResult){
		return;
	}
	//【1】获得页面之中查询过车信息的条件
	var requestParameters=getParameters();
	var filterCondition=ObjectToStr(requestParameters);
	//【2】请求后台服务器获得过车信息结果	
	$.ajax({
		url : basePath + "passTrack.gis?method=getTrackPathQuery",
		data : {"analysis_condition":filterCondition},
		dataType : 'text',
		success : function(data) {
			var passInfoData = $.evalJSON(data);	
			//【3】把查询结果填充到页面中
			showToDataGrid("gcdg", passInfoData);
		}
	});
}

/**
 * 前端分页过滤
 * @param data
 * @returns {___anonymous4154_4198}
 */
function pagerFilter(data){
	if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
		data = {
			total: data.length,
			rows: data
		};
	}
	var dg = $(this);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	pager.pagination({
		onSelectPage:function(pageNum, pageSize){
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh',{
				pageNumber:pageNum,
				pageSize:pageSize
			});
			dg.datagrid('loadData',data);
		}
	});
	if (!data.originalRows){
		data.originalRows = (data.rows);
	}
	var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	data.rows = (data.originalRows.slice(start, end));
	return data;
}

/**
 * 显示预警查询结果到DataGrid之中
 * @param divId
 * @param data
 */
function showToDataGrid(divId,data){
	$("#"+divId).css("width",$(document.body).width()-30);
	$("#"+divId).datagrid({
		pageSize : pageLimit,
		fix : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		pageList:[10,15,20],
		loadFilter:pagerFilter,
		columns : [[{
			field : "HPZLMC",
			title : "号牌种类"
		},{
			field : "HPHM",
			title : "车牌号牌"
		}, {
			field : "DLMC",
			title : "道路名称",				
			align : "left"
		}, {
			field : "KKMC",
			title : "卡口名称",				
			align : "left"
		},{
			field : "KKLXMC",
			title : "卡口类型",				
			align : "left"
		},{
			field : "XSFX",
			title : "行驶方向",				
			align : "left"
		},		
		{
			field : "GCSJ",
			title : "过车时间",				
			align : "left"
		}]],			
		data: data
	});
	var pager = $("#"+divId).datagrid("getPager");
	$(pager).pagination({
		showRefresh : false,
		showPageList : false,
		afterPageText : "页 共 {pages} 页",
		displayMsg : "共 {total} 条"
	});
}