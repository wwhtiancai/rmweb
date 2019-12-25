/***
 * create by king
 * �ܱ߲�ѯ�ؼ�
 * @return 
 */
var RoundQueryManager=new function(){
	this.layerSelId="layerSelId";//ͼ��������ID
	this.disSelId="disSelId";//����������ID
	this.initBtnId="initBtnId";//��ʼ����ťID
	this.queryBtnId="queryBtnId";//��ѯ��ťID
	this.textCol="text";//��ʾ�ֶ�
	this.valueCol="value";//��ֵ�ֶ�
	this.controls=[];//�ؼ�����
	this.appDatas=[];//�ܱ߲�ѯͼ����Ϣ����
	this.isLoading=false;//�Ƿ����ڼ�������
	this.getId=function(parentId,myId){
		if(myId.indexOf("_")<0){
			return parentId+"_"+myId;
		}else{
			return myId.substr(myId.indexOf("_")+1);
		}
	}
	this.getControl=function(id){
		for(var i=0;i<this.controls.length;i++){
			var c=this.controls[i];
			if(c.id==id){
				return c;
			}
		}
		return null;
	}
	this.getAppDatas=function(appCode){
		for(var i=0;i<this.appDatas.length;i++){
			var c=this.appDatas[i];
			if(c.appCode==appCode){
				return c;
			}
		}
		return null;
	}
	this.getSelectDatas=function(roundQuery){
		var multiselect=roundQuery.multiselect;
		var checks=multiselect.ehlmultiselect("getChecked");
		var appDatas=multiselect.data("datas");
		var datas=[];
		for(var i=0;i<checks.length;i++){
			for(var j=0;j<appDatas.length;j++){
				if(checks[i].value==appDatas[j][this.valueCol]){
					datas.push(appDatas[j]);
					break;
				}
			}
		}
		return datas;
	}
}
RoundQueryManager.initPage=function(controlId){
	var self=this;
	if(self.isLoading){
		//alert("���ڼ�������");
		return;
	}
	var roundQuery=this.getControl(controlId);
	if(roundQuery==null){
		alert("��ʼ���ؼ�����");
		return;
	}
	
	self.initSelect(roundQuery);
}
RoundQueryManager.initSelect=function(roundQuery){
	var self=this;
	var options={
			header: false,
			height: 175,
			minWidth: 225,
			classes: '',
			checkAllText: 'ȫѡ',
			uncheckAllText: 'ȫ��ȡ��',
			noneSelectedText: '��ѡ��ͼ��',
			selectedText: '��ѡ��#��ͼ��',
			selectedList: 0,
			show: '',
			hide: '',
			autoOpen: true,
			autoClose:true,
			multiple: true,
			position: {}
	}
	var data=roundQuery.datas;
	if(typeof(data)=='string'){
		data=eval("("+data+")");
	}
	options.minWidth=parseInt(roundQuery.width);
	//alert(jQuery("#"+self.getId(roundQuery.id,self.layerSelId)).ehlmultiselect);
	var multiselect=jQuery("#"+self.getId(roundQuery.id,self.layerSelId)).ehlmultiselect(options);
	jQuery("#"+self.getId(roundQuery.id,self.initBtnId)).css("display","none");
	for(var i=0;i<data.length;i++){
		var opt = jQuery('<option />', {
			value: data[i][self.valueCol],
			text: data[i][self.textCol]
		});
		opt.appendTo(multiselect);
	}
	multiselect.ehlmultiselect('refresh');
	multiselect.data("datas",data);
	//multiselect.multiselect('open');
	roundQuery.multiselect=multiselect;
}
RoundQueryManager.queryEvent=function(controlId){
	var roundQuery=this.getControl(controlId);
	var disSelId=RoundQueryManager.getId(controlId,RoundQueryManager.disSelId);
	var datas=[];
	if(roundQuery.multiselect==null){
		roundQuery.queryEvent({lon:roundQuery.lon,lat:roundQuery.lat},jQuery("#"+disSelId).val(),datas);
	}else{
		datas=this.getSelectDatas(roundQuery);
		roundQuery.queryEvent({lon:roundQuery.lon,lat:roundQuery.lat},jQuery("#"+disSelId).val(),datas);
	}
	
}
RoundQueryManager.loadJs=function(src,callback){
	var oHead = document.getElementsByTagName('HEAD')[0]; 
    var oScript= document.createElement("script"); 
    oScript.type = "text/javascript"; 
    oScript.charset="utf-8";
    oScript.src=src; 
    oHead.appendChild(oScript); 
	if(callback){
		if (!window.ActiveXObject) { //if not IE
		   //Firefox2��Firefox3��Safari3.1+��Opera9.6+ support js.onload
		   oScript.onload = function () {
			//alert('Firefox2��Firefox3��Safari3.1+��Opera9.6+ support js.onload');
				//alert("js loaded!");
				callback();
		   }
		} else {
		   //IE6��IE7 support js.onreadystatechange
		   oScript.onreadystatechange = function () {
			if (oScript.readyState == 'loaded' || oScript.readyState == 'complete') {
			 //alert('IE6��IE7 support js.onreadystatechange');
				//alert("js loaded!");
				callback();
			}
		   }
		}
	}
}
function RoundQueryData(){
	var args=arguments[0];
	this.appCode=args.appCode||null;
	this.datas=args.datas||null;
}
var RoundQuery_INDEX=0;
function RoundQuery(){
	var args=arguments[0];
	this.id=new Date().getTime()+RoundQuery_INDEX;//�ؼ�ID
	this.width=args.width||"225px";
	this.distances=args.distances||[50,100,200,500,1000];
	this.unit=args.unit||"��";
	this.multiselect=null;
	this.dataBaseId=args.dataBaseId||null;
	this.appCode=args.appCode||null;
	this.lon=args.lon||null;
	this.lat=args.lat||null;
	this.queryEvent=args.queryEvent||function(){};
	this.datas=args.datas||[];
	RoundQueryManager.controls.push(this);
	if(RoundQuery_INDEX==0){
		this.loadJsLib();
	}
	RoundQuery_INDEX++;
}
RoundQuery.prototype.loadJsLib=function(){
	if(!jQuery.fn.multiselect){
		function loadControlJs(){
			RoundQueryManager.loadJs(aroundQueryServer+"epgis/js/third_libs/multiselect/jquery-ui.min.js",function(){
				RoundQueryManager.loadJs(aroundQueryServer+"epgis/js/third_libs/multiselect/jquery.multiselect.js");
			});
		}
		if(typeof(jQuery)=="undefined"){
   			RoundQueryManager.loadJs(aroundQueryServer+"epgis/js/third_libs/jquery-1.6.2.js",loadControlJs);
   		}else{
   			var n=jQuery.fn.jquery.replace(/\./g,"");
   			var v1=parseInt(n);
   			var v2=162;//jQuery��С�汾Ϊ1.6.2
   			if(v2>v1){
   					RoundQueryManager.loadJs(aroundQueryServer+"epgis/js/third_libs/jquery-1.6.2.js",loadControlJs);
   			}else{
				loadControlJs();
			}
   		}
	}
}
RoundQuery.prototype.getPageHtml=function(){
	var error=null;
   if(this.lon==null){
		error="��ָ�����ȣ�";
	}else if(this.lat==null){
		error="��ָ��γ�ȣ�";
	}
	if(error!=null){
		error="<div style='font-size:12px;color:red'>"+error+"</div>"
		return error;
	}
	var width=parseInt(this.width);
	var html='<div style="width:'+(width+120)+'px;font-size:12px;border:solid red 0px">';
	html+="<fieldset style='' onselectstart='return false;'><legend style='padding-top: 1px;'>�ܱ߲�ѯ</legend>";
	html+="ѡ��Χ��<select id='"+RoundQueryManager.getId(this.id,RoundQueryManager.disSelId)+"' style='width:"+width+"px'><option value='0' checked>��ѡ��Χ</option>";
	for(var i=0;i<this.distances.length;i++){
		html+="<option value='"+this.distances[i]+"'>"+this.distances[i]+this.unit+"</option>";
	}
	html+="</select><br>";
	//alert(RoundQueryManager.getId(this.id,RoundQueryManager.layerSelId));
	html+='ѡ��ͼ�㣺<select title="ѡ��ͼ��" id='+RoundQueryManager.getId(this.id,RoundQueryManager.layerSelId)+' style="display:none" multiple="multiple" name="example-basic" size="5">';
	html+='</select>';
	//html+='<button onclick="RoundQueryManager.initPage(\''+this.id+'\')" id="'+RoundQueryManager.getId(this.id,RoundQueryManager.initBtnId)+'" class="ui-multiselect ui-widget ui-state-default ui-corner-all" style="width:'+width+'px" type="button"><SPAN class="ui-icon ui-icon-triangle-2-n-s"></SPAN><SPAN>��ѡ��ͼ��</SPAN></button>';
	html+='<button onclick="RoundQueryManager.initPage(\''+this.id+'\')" id="'+RoundQueryManager.getId(this.id,RoundQueryManager.initBtnId)+'" class="ui-multiselect ui-widget ui-state-default ui-corner-all" style="width:'+width+'px" type="button">��ѡ��ͼ��</button>';
	html+="<input type='button' id='"+RoundQueryManager.getId(this.id,RoundQueryManager.queryBtnId)+"' onclick='RoundQueryManager.queryEvent(\""+this.id+"\")'  value='��ѯ'/>";
	html+="</fieldset>";
	html+='</div>';
	return html;
};