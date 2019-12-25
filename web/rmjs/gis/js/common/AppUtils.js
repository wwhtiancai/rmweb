// ��Date����չ���� Date ת��Ϊָ����ʽ��String
// ��(M)����(d)��Сʱ(h)����(m)����(s)������(q) ������ 1-2 ��ռλ����
//��(y)������ 1-4 ��ռλ��������(S)ֻ���� 1 ��ռλ��(�� 1-3 λ������) 
//���ӣ� 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //�·� 
		"d+" : this.getDate(), //�� 
		"h+" : this.getHours(), //Сʱ 
		"m+" : this.getMinutes(), //�� 
		"s+" : this.getSeconds(), //�� 
		"q+" : Math.floor((this.getMonth() + 3) / 3), //���� 
		"S" : this.getMilliseconds()//���� 
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
 * �ѻ���ֶζ���ת���ַ���
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
//��������
/**
 * ����ͼƬ����
	 * 02	ʡ�ʿ���
03	�мʿ���
04	�ؼʿ���
05	��·���߿���
06	��·�շ�վ����
07	������·����
08	����·�ڿ���
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
	//$("<DIV>").attr("title","��ϸ��Ϣ").attr("href",modelPath+'').appendTo(tabs);
	if(!tabsConfig){
		tabsConfig=[
		       {title:"��������",href:basePath + 'alarmOrTrackPub.gis?method=tollgateInfo'},
		       {title:"����״̬",href:basePath + 'alarmOrTrackPub.gis?method=tollgateStatus'},
		       {title:"ʵʱ����",href:basePath+'real.ana?method=forwardSinglePass'},
		       {title:"��ʷ����",href:basePath + 'alarmOrTrackPub.gis?method=passInfo'},
		       {title:"Ԥ����Ϣ",href:basePath + 'alarmOrTrackPub.gis?method=alarmQuery'}
		];
	}
	for(var i=0;i<tabsConfig.length;i++){
		//alert("��1�Σ�"+tabsConfig[i].href);
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
		selected:selectIndex,//1.35֧�ִ�����
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
//�򿪿��ڵķŴ�ͼƬ
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
 * ��ȡ�ֵ��ֵ
 * type:�ֵ������ kklx glbm
 * value:�ֵ��ֵ 
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
 * ���URL���ݵĲ���ֵ
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
 * ��ȡcheckbox����ѡ�е�ֵ
 * @param name checkbox����
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
 * ��ȡ��ѡ�ؼ���ֵ
 * @param id ��ѡ�ؼ���ID
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
 * ��ȡ��ѡ�ؼ���ֵ
 * @param name ��ѡ�ؼ�����
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
 * @param type ��������
 * @param gztype ���ڹ���״̬
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
 * ͨ���������ͻ�ȡ����ͼ��
 * @param type
 * @returns {String}
 */
AppUtils.getKKLXMarkImg=function(type,isBuild){
	var imgName=AppUtils.markTypeConfig[type];
	if(imgName){
		if(isBuild==0){//δ��
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
 * DataGrid��񿨿ڹ���״̬��ʽ��
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
 