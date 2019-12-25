//�˹��ɼ�ҳ��
function gis_add_incident(){
	var url="incidentAlarmCtrl.guide?method=incident_add&sjbh=";
	openwin(url,"gis_add_incident",false);
}
var tableTitle;
function getIncidentColumnsArray(queryType){
	var columnsArray = [];
	if(queryType=="yjqr"){ //Ԥ��ȷ��
		tableTitle="Ԥ��ȷ��";
		columnsArray=[[{field:'sjflmc',title:"�¼�����",width:30,align:'center',formatter:showAlarmtips},
		               {field:'fssj',title:"����ʱ��",width:50,align:'center',formatter:showAlarmtips}
		             ]];
	}else if(queryType=="yjcz"){ //Ӧ������
		tableTitle="Ӧ������";
		columnsArray=[[{field:'sjflmc',title:"�¼�����",width:30,align:'center',formatter:showInfotips},
		               {field:'fssj',title:"����ʱ��",width:50,align:'center',formatter:showInfotips}
		]];

	}else if(queryType=="czfk"){ //���÷���
		tableTitle="���÷���";
		columnsArray=[[{field:'sjflmc',title:"�¼�����",width:30,align:'center',formatter:showtips},
		               {field:'czsj',title:"����ʱ��",width:50,align:'center',formatter:showtips}
		]];

	}	
	   
    return columnsArray;
}
function showAlarmtips(value, row, index){
	var abValue = value;
	var content = '<a href="#" title="�ص�������' + row.ddms+'('+row.sbbhmc+')' + '" class="note">' + abValue + '</a>';
	return content;
}

function showInfotips(value, row, index){
	var abValue = value;
	var content = '<a href="#" title="�ص�������' + row.ddms+'<br/>����ȷ�������'+row.cbqrqk + '" class="note">' + abValue + '</a>';
	return content;
}

function showtips(value, row, index){
	var abValue = value;
	var content = '<a href="#" title="�ص�������' + row.ddms+ '" class="note">' + abValue + '</a>';
	return content;
}

//resultHead,param
function guide_eventResult(data,queryType){
    //data������id
	//panToGeo(id,queryType)
	//��ȡ��ͷ����
	var columnsArray = getIncidentColumnsArray(queryType);
	$('#yjzhContent').datagrid({
		singleSelect: true,//ֻ��ѡ����					
		fitColumns: true,
		nowrap:true,
		loadMsg:"���ڼ���...",
		rownumbers:false,
		showFooter:false,     
		onSelect:function(rowIndex, rowData) {
			panToGeo(rowData.id,queryType);
		}, 
		columns:columnsArray,
		data:data.rows,
		onLoadSuccess:function(data){
			$("#guideFloatWindow").window({title:tableTitle+"("+data.total+")"});
			$(".note").tooltip({
				onShow: function(){
					$(this).tooltip('tip').css({ 
						width:'260',
						boxShadow: '1px 1px 3px #CC5522'                        
                    });
                },
                onPosition: function(){
                	$(this).tooltip('tip').css('left', $(this).offset().left);
                	$(this).tooltip('arrow').css('left', 20);
                }
            });  			
		}
	});	
}

