var pageLimit=10;

$(document).ready(function() {
	//【1】填充号牌种类到页面之中
	fillHPZLToPage("hpzl_select",hpzlList);
	//【2】填充布控类型到页面之中
	fillBKLXToPage("td_bklx", bklxList);	
	//【3】填充签收结果到页面之中
	fillQSJGToPage("qszt_select", qsjgList);
	setDefaultStartEndTime();
});
/**
 * 根据缉查布控系统要求设置默认的开始和结束时间
 */
function setDefaultStartEndTime(){
	var curDate=new Date().Format("yyyy-MM-dd hh:mm:ss"); 	
	//设置开始时间为当前之前往前推1小时
	var dateTemp= new Date();	
	dateTemp.setHours(dateTemp.getHours()-1);
	var dateTempFormat=dateTemp.Format("yyyy-MM-dd hh:mm:ss");
	//alert("dateTempFormat "+dateTempFormat);
	$('#yjcx_start_date').datetimebox('setValue', dateTempFormat);	
	//设置结束时间为当前时间
	$('#yjcx_end_date').datetimebox('setValue', curDate);		
}

var earlyWarnParameters={
		"kkbh":"",// 布控卡口
		"hpzl":"",// 号牌种类
		"hphm":"",// 号牌号码
		"bklx":"",// 布控类型
		"qszt":"",// 签收状态
		"fkzt":"",// 反馈状态
		"kssj":"",// 开始时间
		"jssj":""//结束时间
	};
function getParameters(){
	earlyWarnParameters.kkbh=getURLParamValue("kkbh"); 
	var hpzlVal=$('#hpzl_select').combobox('getValue');
	if(hpzlVal=="no"){
		hpzlVal="";
	}
	earlyWarnParameters.hpzl=hpzlVal;
	earlyWarnParameters.hphm=encodeURI($("#hphm_text").val());
	var values=$("#td_bklx").divFilter("getValue");
	var bklxValues="";
	//把布控类型的值拼接为字符串
	if(values.length>0){
		for(var i=0;i<values.length;i++){
			if(bklxValues!=""){
				bklxValues+=","+values[i]["value"];
			}else{
				bklxValues+=values[i]["value"];
			}
		}
	}	
	earlyWarnParameters.bklx=values;
	earlyWarnParameters.qszt=$('#qszt_select').combobox('getValue');
	earlyWarnParameters.kssj=$('#yjcx_start_date').datetimebox('getValue');
	earlyWarnParameters.jssj=$('#yjcx_end_date').datetimebox('getValue');
	earlyWarnParameters.fkzt=$('#fkzt_select').combobox('getValue');
	return earlyWarnParameters;
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
		columns : [[{
			field : "HPHM",
			title : "布控车牌号牌"
		}, {
			field : "KKMC",
			title : "预警卡口名称",				
			align : "left"
		},{
			field : "BKLXMC",
			title : "布控类型",				
			align : "left"
		},{
			field : "FXBM",
			title : "方向别名",				
			align : "left"
		},		
		{
			field : "GCSJ",
			title : "过车时间",				
			align : "left"
		},{
			field : "GLBMMC",
			title : "管理部门名称",				
			align : "left"
		},{
			field : "QSZT",
			title : "签收结果",				
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

function getWarnInfo(){
	setDefaultStartEndTime();
	//【1】获得页面之中查询预警信息的条件
	var requestParameters=getParameters();
	//【2】请求后台服务器获得预警信息结果
	$.ajax({
		url:basePath + "realAlarm.gis?method=getAlarmHistoryQuery",
		data : requestParameters,
		dataType : 'text',
		success : function(data) {
			var yjInfoData = $.evalJSON(data);	
			//【3】把查询结果填充到页面中
			showToDataGrid("yjdg", yjInfoData);
		}
	});
}
