var gis_resource_template = "<!-- 模板开始 --> 模板内容 <!-- 模板结束 -->";


function getResourceColumnsArray(filedpara){
	var columnsArray = [];
    if(eval(filedpara).length==1){
    	columnsArray=[[{field:'jl',title:"距离(M)",width:53,align:'right'},
    		          {field:filedpara[0].field,title:filedpara[0].title,width:196,formatter: subcols12}
    		]];
    }else if(eval(filedpara).length==2){
		columnsArray=[[{field:'jl',title:"距离(M)",width:53,align:'right'},
				  {field:filedpara[0].field,title:filedpara[0].title,width:81,formatter: subcols6},   
		          {field:filedpara[1].field,title:filedpara[1].title,width:115,formatter: subcols8}
		]];
    }else if(eval(filedpara).length==3){
		columnsArray=[[{field:'jl',title:"距离(M)",width:53,align:'right'},
		          {field:filedpara[0].field,title:filedpara[0].title,width:53,formatter: subcols4}, 
		          {field:filedpara[1].field,title:filedpara[1].title,width:53,formatter: subcols4},
		          {field:filedpara[2].field,title:filedpara[2].title,width:90,formatter: subcols6}
		]];
    }
    return columnsArray;
}

//json数据构造html
//对于循环，可以考虑先先构造td，再构造tr
function fillResourceList(resultHead,param) {
	//alert("zbzylx "+zbzylx);
	var filedpara = get_zbzylx(zbzylx);
	//获取表头数组
	var columnsArray = getResourceColumnsArray(filedpara);
	$('#zy').datagrid({
	    url:"jtztjc.gis?method=getResourceList",
	    queryParams:param,
		singleSelect: true,//只能选择单行					
		fitColumns: true,
		nowrap:true,
		rownumbers:false,
		showFooter:false,     
		onSelect:function(rowIndex, rowData) {
		panToResourceGeo(rowData.id,drawResourceMarkerType);
		}, 
		columns:columnsArray,
		onLoadSuccess:function(data){
			$("#resourceFloatWindow").window({title:resultHead+"("+data.total+")"});
			$(".note").tooltip({
				onShow: function(){
					$(this).tooltip('tip').css({ 
						width:'200',
						boxShadow: '1px 1px 3px #CC5522'                        
                    });
                },
                onPosition: function(){
                	$(this).tooltip('tip').css('left', $(this).offset().left);
                	$(this).tooltip('arrow').css('left', 20);
                }
            });
			if(data.total>0){addResourceMarkers(drawResourceMarkerType,data);}
		}
	});
	
}


function getPoliceColumnsArray(){
	var columnsArray = [[{field:'jl',title:"距离(M)",width:53,align:'right'},
	   				  {field:'idlx',title:'类型',width:81,formatter: subcols6},   
			          {field:'mc',title:'名称',width:115,formatter: subcols8}
			]];
	return columnsArray;
}


function fillPoliceforceList(param){
	//获取表头数组
	var columnsArray = getPoliceColumnsArray();
	//组装显示的结果
    $("#zy").datagrid({
    	url:"jtztjc.gis?method=getPoliceforceList",
    	queryParams:param,
    	singleSelect:true,
    	collapsible:false,
    	loadMsg:"正在加载...",
    	method:'get',
    	rownumbers:false,
        onSelect:function(rowIndex, rowData) {
    		panToResourceGeo(rowData.id,drawResourceMarkerType);
		},
		columns:columnsArray,
		onLoadSuccess:function(data){
		   $("#resourceFloatWindow").window({title:"警员警车("+data.total+")"});
           $(".note").tooltip({
               onShow: function(){
                   $(this).tooltip('tip').css({ 
                       width:'200',
					   boxShadow: '1px 1px 3px #CC5522'                        
                   });
               },
               onPosition: function(){
               	$(this).tooltip('tip').css('left', $(this).offset().left);
               	$(this).tooltip('arrow').css('left', 20);
               }
           });
           //gridData=data;
           if(data.total>0){addResourceMarkers(drawResourceMarkerType,data);}
        },
        onBeforeLoad:function(none){
        	$("#resourceFloatWindow").window({title:"警员警车(0)"});
        }
    });

}

function get_zbzylx(zbzylx){
	var filedpara = [{}];
	if(zbzylx=='01'){
		filedpara = [{"field":"tz","title":"卡口类型"},{"field":"mc","title":"卡口名称"}];
	}
	if(zbzylx=='02'){
		filedpara = [{"field":"tz","title":"设备类型"},{"field":"mc","title":"设备名称"}];
	}
	if(zbzylx=='03'){
		filedpara = [{"field":"tz","title":"接入方式"},{"field":"mc","title":"摄像机点位"}];
	}
	if(zbzylx=='04'){
		filedpara = [{"field":"tz","title":"执法站类型"},{"field":"mc","title":"执法站名称"}];
	}
	if(zbzylx=='05'){
		filedpara = [{"field":"tz","title":"停车场类型"},{"field":"mc","title":"停车场名称"}];
	}
	if(zbzylx=='06'){
		filedpara = [{"field":"mc","title":"气象检测设备名称"}];
	}
	if(zbzylx=='07'){
		filedpara = [{"field":"mc","title":"流量检测设备名称"}];
	}
	if(zbzylx=='08'){
		filedpara = [{"field":"mc","title":"可变信息设备名称"}];
	}
	if(zbzylx=='09'){
		filedpara = [{"field":"mc","title":"信号灯设备名称"}];
	}
	if(zbzylx=='11'){
		filedpara = [{"field":"mc","title":"交通单位名称"}];
	}
	if(zbzylx=='12'){
		filedpara = [{"field":"tz","title":"机构级别"},{"field":"mc","title":"消防单位名称"}];
	}
	if(zbzylx=='13'){
		filedpara = [{"field":"tz","title":"医院等级"},{"field":"mc","title":"医院名称"}];
	}
	if(zbzylx=='14'){
		filedpara = [{"field":"tz","title":"资质级别"},{"field":"mc","title":"修理厂名称"}];
	}
	if(zbzylx.indexOf("11,")>-1||zbzylx.indexOf("12,")>-1||zbzylx.indexOf("13,")>-1||zbzylx.indexOf("14,")>-1){
		filedpara = [{"field":"mc","title":"机构名称"}];
	}
	if(zbzylx=='15'){
		filedpara = [{"field":"tz","title":"广播范围类型"},{"field":"mc","title":"广播设备名称"}];
	}
	if(zbzylx=='16'){
		filedpara = [{"field":"tz","title":"类型"},{"field":"mc","title":"名称"}];
	}
	if(zbzylx=='20'){
		filedpara = [{"field":"mc","title":"交通事件检测设备名称"}];
	}
	return filedpara;
}

function subcols4(value, row, index){
	return subcols(value, row, index, 4);
}
function subcols6(value, row, index){
	  return subcols(value, row, index, 6);
}
function subcols8(value, row, index){
	return subcols(value, row, index, 8);
}
function subcols12(value, row, index){
	return subcols(value, row, index, 12);
}
function subcols(value, row, index, len){
	var abValue = value;
	var content = '';
	if(value!=null){
		if (value.length>len) {
			abValue = value.substring(0,len) + "...";
			content = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
		}else{
			content = '<a href="#">' + value + '</a>';
		}
	}
	return content;
}


function gis_block_list(xxlx){
	var param = getQueryParam();
	var url="jtztjc.gis?method=moreBlockList&xxlx="+xxlx+"&xzqhs="+param.xzqhs+"&dldms="+param.dldms+"&sjbj="+param.sjbj+"&sjlxs="+param.sjlxs;
	openwin(url,"gis_block_list",false);
}

//流量
function gis_flow_list(){
	var param = getJtllParams();
	var url="rs.flow?method=getFlowRealStatePage&sjbj="+param.sjbj+"&jtldx="+param.jtldx+"&sflk="+param.sflk+"&sszt="+param.sszt+"&xzqh="+param.xzqh+"&dldms="+param.dldms;
	openwin(url,"gis_flow_list",false);
}
