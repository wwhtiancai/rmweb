var gis_resource_template = "<!-- ģ�忪ʼ --> ģ������ <!-- ģ����� -->";


function getResourceColumnsArray(filedpara){
	var columnsArray = [];
    if(eval(filedpara).length==1){
    	columnsArray=[[{field:'jl',title:"����(M)",width:53,align:'right'},
    		          {field:filedpara[0].field,title:filedpara[0].title,width:196,formatter: subcols12}
    		]];
    }else if(eval(filedpara).length==2){
		columnsArray=[[{field:'jl',title:"����(M)",width:53,align:'right'},
				  {field:filedpara[0].field,title:filedpara[0].title,width:81,formatter: subcols6},   
		          {field:filedpara[1].field,title:filedpara[1].title,width:115,formatter: subcols8}
		]];
    }else if(eval(filedpara).length==3){
		columnsArray=[[{field:'jl',title:"����(M)",width:53,align:'right'},
		          {field:filedpara[0].field,title:filedpara[0].title,width:53,formatter: subcols4}, 
		          {field:filedpara[1].field,title:filedpara[1].title,width:53,formatter: subcols4},
		          {field:filedpara[2].field,title:filedpara[2].title,width:90,formatter: subcols6}
		]];
    }
    return columnsArray;
}

//json���ݹ���html
//����ѭ�������Կ������ȹ���td���ٹ���tr
function fillResourceList(resultHead,param) {
	//alert("zbzylx "+zbzylx);
	var filedpara = get_zbzylx(zbzylx);
	//��ȡ��ͷ����
	var columnsArray = getResourceColumnsArray(filedpara);
	$('#zy').datagrid({
	    url:"jtztjc.gis?method=getResourceList",
	    queryParams:param,
		singleSelect: true,//ֻ��ѡ����					
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
	var columnsArray = [[{field:'jl',title:"����(M)",width:53,align:'right'},
	   				  {field:'idlx',title:'����',width:81,formatter: subcols6},   
			          {field:'mc',title:'����',width:115,formatter: subcols8}
			]];
	return columnsArray;
}


function fillPoliceforceList(param){
	//��ȡ��ͷ����
	var columnsArray = getPoliceColumnsArray();
	//��װ��ʾ�Ľ��
    $("#zy").datagrid({
    	url:"jtztjc.gis?method=getPoliceforceList",
    	queryParams:param,
    	singleSelect:true,
    	collapsible:false,
    	loadMsg:"���ڼ���...",
    	method:'get',
    	rownumbers:false,
        onSelect:function(rowIndex, rowData) {
    		panToResourceGeo(rowData.id,drawResourceMarkerType);
		},
		columns:columnsArray,
		onLoadSuccess:function(data){
		   $("#resourceFloatWindow").window({title:"��Ա����("+data.total+")"});
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
        	$("#resourceFloatWindow").window({title:"��Ա����(0)"});
        }
    });

}

function get_zbzylx(zbzylx){
	var filedpara = [{}];
	if(zbzylx=='01'){
		filedpara = [{"field":"tz","title":"��������"},{"field":"mc","title":"��������"}];
	}
	if(zbzylx=='02'){
		filedpara = [{"field":"tz","title":"�豸����"},{"field":"mc","title":"�豸����"}];
	}
	if(zbzylx=='03'){
		filedpara = [{"field":"tz","title":"���뷽ʽ"},{"field":"mc","title":"�������λ"}];
	}
	if(zbzylx=='04'){
		filedpara = [{"field":"tz","title":"ִ��վ����"},{"field":"mc","title":"ִ��վ����"}];
	}
	if(zbzylx=='05'){
		filedpara = [{"field":"tz","title":"ͣ��������"},{"field":"mc","title":"ͣ��������"}];
	}
	if(zbzylx=='06'){
		filedpara = [{"field":"mc","title":"�������豸����"}];
	}
	if(zbzylx=='07'){
		filedpara = [{"field":"mc","title":"��������豸����"}];
	}
	if(zbzylx=='08'){
		filedpara = [{"field":"mc","title":"�ɱ���Ϣ�豸����"}];
	}
	if(zbzylx=='09'){
		filedpara = [{"field":"mc","title":"�źŵ��豸����"}];
	}
	if(zbzylx=='11'){
		filedpara = [{"field":"mc","title":"��ͨ��λ����"}];
	}
	if(zbzylx=='12'){
		filedpara = [{"field":"tz","title":"��������"},{"field":"mc","title":"������λ����"}];
	}
	if(zbzylx=='13'){
		filedpara = [{"field":"tz","title":"ҽԺ�ȼ�"},{"field":"mc","title":"ҽԺ����"}];
	}
	if(zbzylx=='14'){
		filedpara = [{"field":"tz","title":"���ʼ���"},{"field":"mc","title":"��������"}];
	}
	if(zbzylx.indexOf("11,")>-1||zbzylx.indexOf("12,")>-1||zbzylx.indexOf("13,")>-1||zbzylx.indexOf("14,")>-1){
		filedpara = [{"field":"mc","title":"��������"}];
	}
	if(zbzylx=='15'){
		filedpara = [{"field":"tz","title":"�㲥��Χ����"},{"field":"mc","title":"�㲥�豸����"}];
	}
	if(zbzylx=='16'){
		filedpara = [{"field":"tz","title":"����"},{"field":"mc","title":"����"}];
	}
	if(zbzylx=='20'){
		filedpara = [{"field":"mc","title":"��ͨ�¼�����豸����"}];
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

//����
function gis_flow_list(){
	var param = getJtllParams();
	var url="rs.flow?method=getFlowRealStatePage&sjbj="+param.sjbj+"&jtldx="+param.jtldx+"&sflk="+param.sflk+"&sszt="+param.sszt+"&xzqh="+param.xzqh+"&dldms="+param.dldms;
	openwin(url,"gis_flow_list",false);
}
