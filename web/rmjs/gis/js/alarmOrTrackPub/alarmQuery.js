var pageLimit=10;

$(document).ready(function() {
	//��1�����������ൽҳ��֮��
	fillHPZLToPage("hpzl_select",hpzlList);
	//��2����䲼�����͵�ҳ��֮��
	fillBKLXToPage("td_bklx", bklxList);	
	//��3�����ǩ�ս����ҳ��֮��
	fillQSJGToPage("qszt_select", qsjgList);
	setDefaultStartEndTime();
});
/**
 * ���ݼ��鲼��ϵͳҪ������Ĭ�ϵĿ�ʼ�ͽ���ʱ��
 */
function setDefaultStartEndTime(){
	var curDate=new Date().Format("yyyy-MM-dd hh:mm:ss"); 	
	//���ÿ�ʼʱ��Ϊ��ǰ֮ǰ��ǰ��1Сʱ
	var dateTemp= new Date();	
	dateTemp.setHours(dateTemp.getHours()-1);
	var dateTempFormat=dateTemp.Format("yyyy-MM-dd hh:mm:ss");
	//alert("dateTempFormat "+dateTempFormat);
	$('#yjcx_start_date').datetimebox('setValue', dateTempFormat);	
	//���ý���ʱ��Ϊ��ǰʱ��
	$('#yjcx_end_date').datetimebox('setValue', curDate);		
}

var earlyWarnParameters={
		"kkbh":"",// ���ؿ���
		"hpzl":"",// ��������
		"hphm":"",// ���ƺ���
		"bklx":"",// ��������
		"qszt":"",// ǩ��״̬
		"fkzt":"",// ����״̬
		"kssj":"",// ��ʼʱ��
		"jssj":""//����ʱ��
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
	//�Ѳ������͵�ֵƴ��Ϊ�ַ���
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
		columns : [[{
			field : "HPHM",
			title : "���س��ƺ���"
		}, {
			field : "KKMC",
			title : "Ԥ����������",				
			align : "left"
		},{
			field : "BKLXMC",
			title : "��������",				
			align : "left"
		},{
			field : "FXBM",
			title : "�������",				
			align : "left"
		},		
		{
			field : "GCSJ",
			title : "����ʱ��",				
			align : "left"
		},{
			field : "GLBMMC",
			title : "����������",				
			align : "left"
		},{
			field : "QSZT",
			title : "ǩ�ս��",				
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

function getWarnInfo(){
	setDefaultStartEndTime();
	//��1�����ҳ��֮�в�ѯԤ����Ϣ������
	var requestParameters=getParameters();
	//��2�������̨���������Ԥ����Ϣ���
	$.ajax({
		url:basePath + "realAlarm.gis?method=getAlarmHistoryQuery",
		data : requestParameters,
		dataType : 'text',
		success : function(data) {
			var yjInfoData = $.evalJSON(data);	
			//��3���Ѳ�ѯ�����䵽ҳ����
			showToDataGrid("yjdg", yjInfoData);
		}
	});
}
