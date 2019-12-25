// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //月份 
		"d+" : this.getDate(), //日 
		"h+" : this.getHours(), //小时 
		"m+" : this.getMinutes(), //分 
		"s+" : this.getSeconds(), //秒 
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
		"S" : this.getMilliseconds()//毫秒 
	};
	if (/(y+)/.test(fmt)){
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}		
	for ( var k in o){
		if (new RegExp("(" + k + ")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}		
	return fmt;
};
/**
 * 把获得字段对象转换字符串
 * @param o
 * @returns
 */
function ObjectToStr(o){
	   var r = [];
	   if(typeof o == "string" || o == null) {
	     return '"'+o+'"';
	   }
	   if(typeof o == "object"){
	     if(!o.sort){
	       r[0]="{";
	       for(var i in o){
	         r[r.length]=i;
	         r[r.length]=":";
	         r[r.length]=ObjectToStr(o[i]);
	         r[r.length]=",";
	       }
	       r[r.length-1]="}";
	     }else{	   
	       if(o.length>0){
	    	   r[0]="[";
	    	   for(var i =0;i<o.length;i++){
	    		   r[r.length]=ObjectToStr(o[i]);
	  		       r[r.length]=","; 
	  	       }
	  	       r[r.length-1]="]"; 
	       }else{
	    	   r[0]="[]"; 
	       }	      
	     }
	     return r.join("");
	   }
	   return o.toString();
	}
var AppUtils=AppUtils||{};
//常用配置
/**
 * 卡口图片配置
	 * 02	省际卡口
03	市际卡口
04	县际卡口
05	公路主线卡口
06	公路收费站卡口
07	城区道路卡口
08	城区路口卡口
	 */
AppUtils.markTypeConfig={
			"02":"province_normal",
			"03":"city_normal",
			"04":"country_normal",
			"05":"mainline_normal",
			"06":"toll-gate_normal",
			"07":"districtRoad_normal",
			"08":"roadIntersection_normal"
	};
AppUtils.openInfoWindow=function(kkObj,selectIndex,tabsConfig){
	var kkbh=kkObj["kkbh"]?kkObj["kkbh"]:kkObj["KKBH"];
	var kkmc=kkObj["kkmc"]?kkObj["kkmc"]:kkObj["KKMC"];
	//alert(kkbh);
	/*var newWindow=window.open(modelPath+'jsp/kkgl/kkxx.jsp','newwindow','location=no,status=no');//,'height=500,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no'
	newWindow.focus();*/
	
	var windowDivId="windowDiv";
	if($("#"+windowDivId).get(0)){
		$("#"+windowDivId).remove();
	}
	var windowDiv=$("<DIV>").attr("id",windowDivId);
	$(document.body).append(windowDiv);	
	$('#'+windowDivId).window({
		title:kkmc,
		width:800,
		height:$(document.body).height()-200,
		collapsible:false,
		minimizable:false,
		maximizable:false
	});
	$('#'+windowDivId).window('center');
	var tabW=$("#"+windowDivId).css("width");	
	var tabH=$("#"+windowDivId).css("height");
	var tabs=$("<DIV>").attr("id",windowDivId+"_tabs").css({width:"auto",height:tabH}).appendTo(windowDiv);
	//$("<DIV>").attr("title","详细信息").attr("href",modelPath+'').appendTo(tabs);
	if(!tabsConfig){
		tabsConfig=[
		       {title:"卡口详情",href:basePath + 'alarmOrTrackPub.gis?method=tollgateInfo'},
		       {title:"工作状态",href:basePath + 'alarmOrTrackPub.gis?method=tollgateStatus'},
		       {title:"实时过车",href:basePath+'real.ana?method=forwardSinglePass'},
		       {title:"历史过车",href:basePath + 'alarmOrTrackPub.gis?method=passInfo'},
		       {title:"预警信息",href:basePath + 'alarmOrTrackPub.gis?method=alarmQuery'}
		];
	}
	for(var i=0;i<tabsConfig.length;i++){
		//alert("第1次："+tabsConfig[i].href);
		var config=$.extend({},tabsConfig[i]);
		var href=config["href"];
		var reqParams=config["reqParams"];
		if(reqParams){
			href+=reqParams;
		}
		if(href&&href.indexOf("?")>0){
			href=href+"&kkbh="+kkbh;
		}else{
			href=href+"?kkbh="+kkbh;
		}
		var url=href+"&r="+Math.random();
		$("<DIV>").attr("title",config["title"]).css({width:'auto',height:'auto',"overflow":"auto"}).html('<iframe id="windowDivId_Frm'+i+'" frameborder="0" src="#"  basesrc="'+url+'" style="width:100%;height:100%;"></iframe>').appendTo(tabs);
	}
	if(!selectIndex){
		selectIndex=0;
	}
	tabs.tabs({
		border:false,
		selected:selectIndex,//1.35支持此属性
		onSelect:function(title,index){
			var frm=$("#windowDivId_Frm"+index);
			if(frm.get(0)){
				if(frm.attr("src")=="#"){
					frm.attr("src",frm.attr("basesrc"));
				}
			}
		}
	});
	tabs.tabs("select",selectIndex);
};
//打开卡口的放大图片
AppUtils.openBigPic=function(objImg){
	//alert(objImg.baseUrl);
	var maxImgControl=new MaxImgControl({imgUrl:objImg.baseUrl});
	maxImgControl.show();
}
AppUtils.getDics=function(){
	if(AppUtils.dics){
		return AppUtils.dics;
	}
	var url=basePath+"alarmOrTrackPub.gis?method=getDicData";
	var async=false;
	var callBackData=null;
	jQuery.ajax({  
        type : "post",  
        url : url,    
        async : async,  
        success : function(data){
			callBackData=eval("("+data+")");
        }  
    });
	AppUtils.dics=callBackData;
	return callBackData;
}
/**
 * 获取字典表值
 * type:字典表类型 kklx glbm
 * value:字典表值 
 */
AppUtils.getDicText=function(type,value){
	if(!type||!value){
		return null;
	}
	var dics=AppUtils.getDics();
	var dic=dics[type.toLowerCase()]?dics[type.toLowerCase()]:dics[type.toUpperCase()];
	if(dic){
		for(var i=0;i<dic.length;i++){
			var val=dic[i]["VALUE"];
			var text=dic[i]["TEXT"];
			if(val==value){
				return text;
			}
		}
	}
	return null;
}

/**
 * 获得URL传递的参数值
 * @param name
 * @returns
 */
function getURLParamValue(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null){
		return decodeURI(r[2]);
	}else{
		return null;
	}	
}
/**
 * 获取checkbox里面选中的值
 * @param name checkbox名称
 * @returns {Array}
 */
AppUtils.getCheckBoxValue=function(name){
	var values=$("[name='"+name+"']");
	var selValue=[];
	$.each(values,function(i,radio){
		if($(radio).attr("checked")){
			selValue.push($(radio).val());
		}
	});
	return selValue;
}
/**
 * 获取多选控件的值
 * @param id 多选控件的ID
 * @returns {Array}
 */
AppUtils.getMutiSelValue=function(id,tag){
	if(id.indexOf("#")<0){
		id="#"+id;
	}
	var values=$(id).divFilter("getValue");
	if(!tag){
		tag="";
	}
	if(values.length>0){
		var arr=[];
		for(var i=0;i<values.length;i++){
			arr.push(tag+values[i].value+tag);
		}
		//filter.push("KKLX IN ("+arr.join(",")+")");
		return arr;
	}
	return [];
}
/**
 * 获取单选控件的值
 * @param name 单选控件名称
 * @returns
 */
 AppUtils.getRadioValue=function(name){
	var values=$("[name='"+name+"']");
	var selValue=null;
	$.each(values,function(i,radio){
		if($(radio).attr("checked")){
			selValue=$(radio).val();
			return false;
		}
	});
	return selValue;
};


/**
 * 
 * @param type 卡口类型
 * @param gztype 卡口工作状态
 * @returns {String}
 */
AppUtils.getKKGZZTText=function(gzztCode){
	for(var i=0;i<AppUtils.kkztConfig.length;i++){
		var obj=AppUtils.kkztConfig[i];
		if(obj["code"]==gzztCode){
			return obj["label"];
		}
	}
	return null;
}

/**
 * 通过卡口类型获取卡口图标
 * @param type
 * @returns {String}
 */
AppUtils.getKKLXMarkImg=function(type,isBuild){
	var imgName=AppUtils.markTypeConfig[type];
	if(imgName){
		if(isBuild==0){//未建
			imgName+="_scrap";
		}
	}
	if(!imgName){
		imgName="province_normal.png";
	}else{
		imgName+=".png";
	}
	var imgPath=modelPath+"images/markers/tollgates/"+imgName;
	return imgPath;
}
/**
 * DataGrid表格卡口工作状态格式化
 */
AppUtils.kkgzztFormatter=function(value,row,index){
	var kklx=row["kklx"]?row["kklx"]:row["KKLX"];
	var kkgzzt=row["KKGZZT"]?row["KKGZZT"]:row["kkgzzt"];
	var imgSrc=null;
	if(kkgzzt){
		imgsrc=AppUtils.getGzztMarkImg(kklx,kkgzzt);
	}else{
		imgsrc=AppUtils.getKKLXMarkImg(kklx);
	}
	return "<img src='"+imgsrc+"'/>";
}
 