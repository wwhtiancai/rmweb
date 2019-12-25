//人工采集页面
function gis_add_incident(){
	var url="incidentAlarmCtrl.guide?method=incident_add&sjbh=";
	openwin(url,"gis_add_incident",false);
}
var tableTitle;
function getIncidentColumnsArray(queryType){
	var columnsArray = [];
	if(queryType=="yjqr"){ //预警确认
		tableTitle="预警确认";
		columnsArray=[[{field:'sjflmc',title:"事件分类",width:30,align:'center',formatter:showAlarmtips},
		               {field:'fssj',title:"发生时间",width:50,align:'center',formatter:showAlarmtips}
		             ]];
	}else if(queryType=="yjcz"){ //应急处置
		tableTitle="应急处置";
		columnsArray=[[{field:'sjflmc',title:"事件分类",width:30,align:'center',formatter:showInfotips},
		               {field:'fssj',title:"发生时间",width:50,align:'center',formatter:showInfotips}
		]];

	}else if(queryType=="czfk"){ //处置反馈
		tableTitle="处置反馈";
		columnsArray=[[{field:'sjflmc',title:"事件分类",width:30,align:'center',formatter:showtips},
		               {field:'czsj',title:"处置时间",width:50,align:'center',formatter:showtips}
		]];

	}	
	   
    return columnsArray;
}
function showAlarmtips(value, row, index){
	var abValue = value;
	var content = '<a href="#" title="地点描述：' + row.ddms+'('+row.sbbhmc+')' + '" class="note">' + abValue + '</a>';
	return content;
}

function showInfotips(value, row, index){
	var abValue = value;
	var content = '<a href="#" title="地点描述：' + row.ddms+'<br/>初步确认情况：'+row.cbqrqk + '" class="note">' + abValue + '</a>';
	return content;
}

function showtips(value, row, index){
	var abValue = value;
	var content = '<a href="#" title="地点描述：' + row.ddms+ '" class="note">' + abValue + '</a>';
	return content;
}

//resultHead,param
function guide_eventResult(data,queryType){
    //data的主键id
	//panToGeo(id,queryType)
	//获取表头数组
	var columnsArray = getIncidentColumnsArray(queryType);
	$('#yjzhContent').datagrid({
		singleSelect: true,//只能选择单行					
		fitColumns: true,
		nowrap:true,
		loadMsg:"正在加载...",
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

