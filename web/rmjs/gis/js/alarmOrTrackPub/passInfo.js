$(document).ready(function() {
	//��1�����������ൽҳ��֮��
	fillHPZLToPage("hpzl_select",hpzlList);		
	//��3������ѯԭ��ҳ��֮��
	fillQSJGToPage("cxyy_select", cxyyList);
	setDefaultStartEndTime();
	setDefaultCondition();//���õ�ǰ����Ĭ�ϲ�ѯ����
});

var pageLimit=10;

/**
 * Ĭ��Ϊ��ǰ����ͨ������
 * @return
 */
function setDefaultCondition(){
	var hphmValue=getURLParamValue("hphm");
	var hpzlValue=getURLParamValue("hpzl");
	//���õ�ǽ�ĺ��ƺ���
	$("#hphm_text").val(hphmValue);
	if(hpzlValue!="undefined"){
		$("#hpzl_select").combobox('setValue',hpzlValue);
	}else{
		$("#hpzl_select").combobox('setValue','no');
	}
	$("#cxyy_select").combobox('setValue','9');
}
/**
* ���ݼ��鲼��ϵͳҪ������Ĭ�ϵĿ�ʼ�ͽ���ʱ��
*/
function setDefaultStartEndTime(){
	var curDate=new Date().Format("yyyy-MM-dd hh:mm:ss"); 	
	//���ÿ�ʼʱ��Ϊ��ǰ֮ǰ��ǰ��4Сʱ
	var dateTemp= new Date();	
	dateTemp.setHours(dateTemp.getHours()-4);
	var dateTempFormat=dateTemp.Format("yyyy-MM-dd hh:mm:ss");
	//alert("dateTempFormat "+dateTempFormat);
	$('#gccx_start_date').datetimebox('setValue', dateTempFormat);	
	//���ý���ʱ��Ϊ��ǰʱ��
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
		$.messager.alert("��ʾ��Ϣ", "��ѯ�����к������಻��Ϊ�գ�", "info");
		verifyResult = false;
		break;
	case carLicenses == "":
		$.messager.alert("��ʾ��Ϣ", "��ѯ�����к��ƺ��벻��Ϊ�գ�", "info");
		verifyResult = false;
		break;
	case startDate == "":
		$.messager.alert("��ʾ��Ϣ", "��ѯ�����к��ƺ��벻��Ϊ�գ�", "info");
		verifyResult = false;
		break;
	case endDate == "":
		$.messager.alert("��ʾ��Ϣ", "��ѯ�����к��ƺ��벻��Ϊ�գ�", "info");
		verifyResult = false;
		break;
	}
	return verifyResult;
	}

function getParameters(){
	var analysisCondition={
			"query_car_license":"",//���ƺ�
			"query_license_type":"",//��������
			"query_start_date":"",//��ѯ��ʼ����
			"query_end_date":"",//��ѯ��������
			"query_reason":"",//��ѯԭ��
			"query_district":""//��ѯ��������			
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
 * �����ó���������Ϣ
 */
function getVehiclePassInfo(){	
	var verifyResult=verifyCondition();
	if(!verifyResult){
		return;
	}
	//��1�����ҳ��֮�в�ѯ������Ϣ������
	var requestParameters=getParameters();
	var filterCondition=ObjectToStr(requestParameters);
	//��2�������̨��������ù�����Ϣ���	
	$.ajax({
		url : basePath + "passTrack.gis?method=getTrackPathQuery",
		data : {"analysis_condition":filterCondition},
		dataType : 'text',
		success : function(data) {
			var passInfoData = $.evalJSON(data);	
			//��3���Ѳ�ѯ�����䵽ҳ����
			showToDataGrid("gcdg", passInfoData);
		}
	});
}

/**
 * ǰ�˷�ҳ����
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
 * ��ʾԤ����ѯ�����DataGrid֮��
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
			title : "��������"
		},{
			field : "HPHM",
			title : "���ƺ���"
		}, {
			field : "DLMC",
			title : "��·����",				
			align : "left"
		}, {
			field : "KKMC",
			title : "��������",				
			align : "left"
		},{
			field : "KKLXMC",
			title : "��������",				
			align : "left"
		},{
			field : "XSFX",
			title : "��ʻ����",				
			align : "left"
		},		
		{
			field : "GCSJ",
			title : "����ʱ��",				
			align : "left"
		}]],			
		data: data
	});
	var pager = $("#"+divId).datagrid("getPager");
	$(pager).pagination({
		showRefresh : false,
		showPageList : false,
		afterPageText : "ҳ �� {pages} ҳ",
		displayMsg : "�� {total} ��"
	});
}