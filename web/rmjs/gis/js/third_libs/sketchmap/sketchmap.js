/**
 * ���ɵ�·ʾ��ͼ�ؼ�
 */
(function($) {
	function getHTML(target){
		//alert($(target).data("checkedata"));
		return $(target).data("checkedata");
	}
	/**
	 * �������� fxlx��1���� 2������
	 */
	function createRoadWay(target,fxlx,roads,index){
		/*
		var roadWayClassConf={1:"roadWay",2:"roadway2"};//��·������ʽ
		var arrowClassConf={1:"arrowleft",2:"arrowright"};//��ͷ��ʽ
		var numClassConf={1:"roundnumright",2:"roundnumleft"};//��·�����ʽ
		*/
		var roadNum=roads.length-index;
		if(fxlx==2){
			roadNum=index+1;
		}
		var options=$(target).data("SketchMap").options;
		var roadWayClassConf=options.roadWayClassConf;
		var roadWayBorderClassConf=options.roadWayBorderClassConf;
		var arrowClassConf=options.arrowClassConf;
		var numClassConf=options.numClassConf;
		
		var roadWayClass=roadWayClassConf[fxlx];
		var arrowClass=arrowClassConf[fxlx];
		var numClass=numClassConf[fxlx];
		var roadWayBorderClass=roadWayBorderClassConf[fxlx];
		
		if(roadNum==(roads.length-index)){
			roadWayClass=roadWayBorderClass;
		}
		var roadWay=$("<DIV>");
		var arrow=$("<DIV>");
		var num=$("<DIV>");
		if(roadNum!=undefined){
			num.html(roadNum);
		}
		roadWay.addClass(roadWayClass);
		arrow.addClass(arrowClass).appendTo(roadWay);
		num.addClass(numClass).appendTo(roadWay);
		return roadWay;
	}
	/**
	 * �����ָ��
	 */
	function createSplitTree(){
		var tree=$("<DIV>");
		tree.addClass("greentree");
		return tree;
	}
	function init(target){
		var options=$(target).data("SketchMap").options;
		var content=$("<DIV>");
		content.addClass("road");
		var roads=options.roads;// ��Ӧdev_lane���в鵽������ fxlx��1���� 2������
		var roads1=[];//������г���
		var roads2=[];//������г���
		
		for(var i=0;i<roads.length;i++){
			var roadWay=roads[i];
			if(roadWay["fxlx"]==1){
				roads1.push(roadWay);
			}else{
				roads2.push(roadWay);
			}
		}
		//�ȴ������г���
		for(var i=0;i<roads1.length;i++){
			var roadWay=createRoadWay(target,1,roads1,i);
			content.append(roadWay);
		}
		//���������
		var split=createSplitTree().attr("height","13px");
		content.append(split);
		//�������г���
		for(var i=0;i<roads2.length;i++){
			var roadWay=createRoadWay(target,2,roads2,i);
			content.append(roadWay);
		}
		content.css({
			width:"100%",
			height:roads.length*40+13
		});
		//��������ͷ
		var splitcontent=$("<DIV>").addClass("splitcontent");
		var split=$("<DIV>").addClass("split");
		var videoleft=$("<DIV>").addClass("videoleft");
		var videoright=$("<DIV>").addClass("videoright");
		splitcontent.append(split);
		if(roads1.length>0){
			splitcontent.append(videoright);
		}
		if(roads2.length>0){
			splitcontent.append(videoleft);
		}
		content.append(splitcontent);
		//
		$(target).append(content);
	}
	$.fn.SketchMap = function(options,param) { 
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'SketchMap');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'SketchMap', {
					options: $.extend({},$.fn.SketchMap.defaults,options)
				});
				init(this);
			}
		});
	}
	$.fn.SketchMap.defaults = {
			roads:[],
			roadWayClassConf:{1:"roadway",2:"roadway2"},//��·������ʽ
			roadWayBorderClassConf:{1:"roadway_1",2:"roadway2_1"},
			arrowClassConf:{1:"arrowleft",2:"arrowright"},//��ͷ��ʽ
			numClassConf:{1:"roundnumright",2:"roundnumleft"}//��·�����ʽ
		};
})(jQuery);
var SketchMap_INDEX=0;
var SketchMap=function(options){
	options = options || {};
	var defaults = {
			direction:"",//����˵������
			isShowVideo:true,//�Ƿ���ʾ��Ƶʾ��
			isShowRoadText:false,//�Ƿ���ʾ��·˵��
			width:"300px",
			roads:[],
			roadWayClassConf:{1:"roadway",2:"roadway2"},//��·������ʽ
			roadWayBorderClassConf:{1:"roadway_1",2:"roadway2_1"},
			arrowClassConf:{1:"arrowright",2:"arrowleft"},//��ͷ��ʽ
			numClassConf:{1:"roundnumleft",2:"roundnumright"},//��·�����ʽ
			cdlx:{//01-��ͨ������02-С������03-��ͳ�����04-��������05-��������06-�г�����07-����ר�ó�����08-Ӧ��������09-�ǻ�����������11-������ת����ͷ������12-������ת������13-����ֱ�г�����14-������ת������15-������ת��ֱ�г���
				"01":"��ͨ����",
				"02":"С����",
				"03":"��ͳ���",
				"04":"������",
				"05":"������",
				"06":"�г���",
				"07":"����ר�ó���",
				"08":"Ӧ������",
				"09":"�ǻ���������",
				"11":"������ת����ͷ����",
				"12":"������ת����",
				"13":"����ֱ�г���",
				"14":"������ת����",
				"15":"������ת��ֱ�г���"
			}
		};
	this.id=new Date().getTime()+SketchMap_INDEX;//�ؼ�ID
	this.options=$.extend({},defaults,options);
	this.content=null;
	SketchMap_INDEX++;
	/**
	 * �������� fxlx��1���� 2������
	 */
	this.createRoadWay=function (fxlx,roads,index){
		/*
		var roadWayClassConf={1:"roadWay",2:"roadway2"};//��·������ʽ
		var arrowClassConf={1:"arrowleft",2:"arrowright"};//��ͷ��ʽ
		var numClassConf={1:"roundnumright",2:"roundnumleft"};//��·�����ʽ
		*/
		var road=roads[index];
		var roadNum=roads.length-index;
		if(fxlx==2){
			roadNum=index+1;
		}
		var roadWayClassConf=this.options.roadWayClassConf;
		var roadWayBorderClassConf=this.options.roadWayBorderClassConf;
		var arrowClassConf=this.options.arrowClassConf;
		var numClassConf=this.options.numClassConf;
		
		var roadWayClass=roadWayClassConf[fxlx];
		var roadWayBorderClass=roadWayBorderClassConf[fxlx];
		var arrowClass=arrowClassConf[fxlx];
		var numClass=numClassConf[fxlx];
		if(roadNum==roads.length){
			roadWayClass=roadWayBorderClass;
		}
		var roadWay=$("<DIV>");
		var arrow=$("<DIV>");
		var num=$("<DIV>");
		if(roadNum!=undefined){
			num.html(roadNum);
		}
		roadWay.addClass(roadWayClass);
		arrow.addClass(arrowClass).appendTo(roadWay);
		num.addClass(numClass).appendTo(roadWay);
		//�ڵ�·�ϼ�����Ϣ
		if(this.options.isShowRoadText){
			var marginTop=6;
			
			var roadText=$("<DIV>").css({"margin-top":marginTop+"px","font-size":"12px"});
			roadText.attr({"type":"roadtext_"+this.id});
			var cdlx=road["cdlx"]?road["cdlx"]:road["CDLX"];
			var text="";
			if(this.options.cdlx[cdlx]){
				text=this.options.cdlx[cdlx];
			}
			//alert(text);
			if(text!=""){
				text+="��";	
			}
			//alert(text);
			text+="����Ϊ����:"+this.getSpeed(road,"dcxs")+"��С��:"+this.getSpeed(road,"xcxs")+"�����:"+this.getSpeed(road,"zdxs")+"��";
			if(this.options.gzzt){
				roadText.html(text+"<span type='gzzt_"+this.id+"'>����״̬��<font color='red'>"+this.options.gzzt+"</font></span>");
			}else{
				roadText.html(text);
			}
			
			roadWay.css({"text-align":"center"});
			roadWay.append(roadText);
		}
		//
		return roadWay;
	}
	this.setGZZT=function(gzzt){
		var len=$("[type='gzzt_"+sketchMap.id+"']").length;
		if(len==0){
			$("[type='roadtext_"+this.id+"']").each(function(i,obj){
				var span=$("<SPAN>");
				span.attr({type:'gzzt_'+this.id}).html(gzzt);
				$(obj).append(span);
			});
		}else{
			$("[type='gzzt_"+sketchMap.id+"']").each(function(i,obj){
			 	//alert(obj);
			 	$(obj).html(gzzt);
			 });
		}
	}
	/**
	 * �����ָ��
	 */
	this.createSplitTree=function(){
		var tree=$("<DIV>");
		tree.addClass("greentree");
		return tree;
	}
	this.init=function(){
		var options=this.options;
		var content=$("<DIV>");
		content.addClass("road");
		var roads=options.roads;// ��Ӧdev_lane���в鵽������ fxlx��1���� 2������
		var roads1=[];//������г���
		var roads2=[];//������г���
		for(var i=0;i<roads.length;i++){
			var roadWay=roads[i];
			if(roadWay["fxlx"]==1){
				roads1.push(roadWay);
			}else{
				roads2.push(roadWay);
			}
		}
		//��������ָʾ��
		var dirction=$("<DIV>").addClass("direction");
		dirction.html(options["direction"]).appendTo(content);
		//�ȴ������г���
		//alert(Math.ceil(roads1.length/2));
		for(var i=0;i<roads1.length;i++){
			var roadWay=this.createRoadWay(1,roads1,i);
			
			content.append(roadWay);
		}
		
		//���������
		var split=this.createSplitTree().attr("height","13px");
		content.append(split);
		//�������г���
		for(var i=0;i<roads2.length;i++){
			var roadWay=this.createRoadWay(2,roads2,i);
			content.append(roadWay);
		}
		var roadDivHeight=roads.length*22+13+25;
		if(roadDivHeight>150){
			content.css({
				"width":options.width,
				"overflow-y":"auto",
				"height":240
			});
		}else{
			content.css({
				width:options.width,
				height:roadDivHeight
			});
		}
		
		//��������ͷ
		if(this.options.isShowVideo){
			var splitcontent=$("<DIV>").addClass("splitcontent");
			var split=$("<DIV>").addClass("split");
			var videoleft=$("<DIV>").addClass("videoleft");
			var videoright=$("<DIV>").addClass("videoright");
			splitcontent.append(split);
			if(roads1.length>0){
				splitcontent.append(videoleft);
			}
			if(roads2.length>0){
				splitcontent.append(videoright);
			}
			splitcontent.css({
				height:roads.length*22+13
			});
			content.append(splitcontent);
		}
		return content;
	}
	this.getSpeed=function(datas,flag){
		var speed=datas[flag.toUpperCase()]?datas[flag.toUpperCase()]:datas[flag.toLowerCase()];
		if(!speed||speed==0){
			return "��";
		}else{
			return speed;
		}
	}
	this.getHTML=function(){
		if(this.content==null){
			this.content=this.init();
		}
		return this.content[0].outerHTML;
	}
}
