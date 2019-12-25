String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ReplaceAll = stringReplaceAll;
function stringReplaceAll(AFindText,ARepText){
	raRegExp = new RegExp(AFindText,"g");
	return this.replace(raRegExp,ARepText)
}
function opens(){
	$("input:button").each(function(){
		$(this).attr("disabled",false);
	});
}
function closes(){
	$("input:button").each(function(){
		$(this).attr("disabled",true);
	});
}
jQuery.fn.limit=function(){
	var self = $("[limit]");
	self.each(function(){
		var objString=$(this).text();
		var objLength=$(this).text().length;
		var num=$(this).attr("limit");
		if(num=='date'){
			objString=$(this).text(objString.substring(0,10));
		}else if(num=='time'){
			if(objLength==10){
				objString=$(this).text(objString.substring(0,num)+" 00:00:00");
			}else{
				objString=$(this).text(objString.substring(0,19));
			}
		}else{
			if(objLength>num){
				$(this).attr("title",objString);
				objString=$(this).text(objString.substring(0,num)+"..");
			}		
		}
	})
}

jQuery.fn.limits=function(limitId){
	var self = $(limitId);
	self.each(function(){
		var objString=$(this).text();
		var objLength=$(this).text().length;
		var num=$(this).attr("limit");
		if(num=='date'){
			objString=$(this).text(objString.substring(0,10));
		}else if(num=='time'){
			if(objLength==10){
				objString=$(this).text(objString.substring(0,num)+" 00:00:00");
			}else{
				objString=$(this).text(objString.substring(0,19));
			}
		}else{
			if(objLength>num){
				$(this).attr("title",objString);
				objString=$(this).text(objString.substring(0,num)+"..");
			}		
		}
	})
}
function setCheckBox(idname,idx){
	var obj=$(":checkbox[id="+idname+"]");
	for(var i=0;i<obj.size();i++){
		if(i==idx){
			if(!obj.attr("disabled")){
				obj[i].checked=!obj[i].checked;			
			}
		}
	}
}
function setdepartment(obj,objname,val,valname){
	obj.val(val);
	objname.val(valname);
	objname.attr("title",val+":"+valname);
	objname.css("cursor","pointer");
}
function selectdepartment(obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("department.frm?method=choosedept&glbm="+obj.val(),"","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:1;");
	if (typeof r=="undefined"||r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==3){
		eval(fun);
	}
}
function selectdepartments(obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("department.frm?method=choosedept&glbm="+obj.val()+"&flag=1","","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:1;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==3){
		eval(fun);
	}
}
function selectdepartmentss(obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("department.frm?method=choosedeptss","","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:1;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==3){
		eval(fun);
	}
}
function selDeptList(showOffice, obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("dept.conf?method=getDeptList&showOffice=" + (showOffice ? "true" : "false"),"","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==4){
		eval(fun);
	}
}

function selDeptListBmjb(showOffice,zxbmjb,obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("dept.conf?method=getDeptList&showOffice=" + (showOffice ? "true" : "false")+"&zxbmjb="+zxbmjb,"","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==5){
		eval(fun);
	}
}

function selDeptListBmjb2(showOffice,zdbmjb,obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("dept.conf?method=getDeptList&showOffice=" + (showOffice ? "true" : "false")+"&zdbmjb="+zdbmjb,"","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==5){
		eval(fun);
	}
}

function selDeptListGlbm(glbm, showOffice, obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("dept.conf?method=getDeptList&glbm="+glbm+"&showOffice=" + (showOffice ? "true" : "false"),"","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==5){
		eval(fun);
	}
}
function selectmultidepartment(obj,objname,fun){
	var len=arguments.length;
	var a=new Array(2);
	a[0]=obj.val();
	a[1]=objname.val();
	var r=window.showModalDialog("department.do?method=tree&multi=true",a,"dialogWidth:640px;dialogHeight:480px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r != "undefined"){
		if(r[0]=="0"){
			obj.val("");
			objname.val("");
			objname.attr("title","");
			objname.css("cursor","auto");
		}else{
			obj.val(r[0]);
			objname.val(r[1]);
			objname.attr("title",r[0]+":"+r[1]);
			objname.css("cursor","pointer");
			if(len==3){
				eval(fun);
			}
		}
	}
}
function getVehicle(hpzl,hphm,fun){
	var len=arguments.length;
	if(hpzl==null||hpzl.length<1||hphm==null||hphm.length<1||fun==null||fun.length<1){
		alert("请输入查询项");
	}else{
		$.getJSON("ajax.do?method=getVehicle&hpzl="+hpzl+"&hphm="+encodeURI(encodeURI(hphm)),{async:false},function(data){
			try{
				if(data["result"]=="1"){
					var json='{"xh":"'+data["xh"]+'","hphm":"'+decodeURIComponent(data["hphm"])+'","clxh":"'+decodeURIComponent(data["clxh"])+'","clpp":"'+decodeURIComponent(data["clpp"])+'","syr":"'+decodeURIComponent(data["syr"])+'","lxdh":"'+decodeURIComponent(data["lxdh"])+'","xxdz":"'+decodeURIComponent(data["xxdz"])+'","hpzl":"'+data["hpzl"]+'","hpzlmc":"'+decodeURIComponent(data["hpzlmc"])+'","cllx":"'+data["cllx"]+'","cllxmc":"'+decodeURIComponent(data["cllxmc"])+'","csys":"'+data["csys"]+'","csysmc":"'+decodeURIComponent(data["csysmc"])+'","fdjh":"'+data["fdjh"]+'","clsbdh":"'+data["clsbdh"]+'"}';
					eval(fun+"('"+json+"')");
				}else{
					alert(decodeURIComponent(data["result"]));
					eval(fun+"('"+json+"')");
				}
			}catch(e){
				alert(e);
			}
		});	
	}
}
//校验是否符合该字符规则
function invalidLetter(name,str,LetterReg){
	var c;
	for (var i=0;i<str.length;i++) {
		c=str.charAt(i);
		if(LetterReg.indexOf(c)==-1){
			alert("“"+name+"”中字符‘"+c+"’不符合输入要求!");
			return false;
		}
	}
	return true;
}
//检查对象是否为空，obj-对象，name-提示的输入项
function checkNull(obj,name){
	if (obj.val()==""){
		alert("“"+name+"”不能为空！");
		obj.focus();
		return false;
	}else{
		return true;
	}
}
//检查Radio是否有选择，obj-对象，name-提示的输入项
function checkByName(obj,name){
	var val="";
	obj.each(function(){
		val=$(this).val();
	});
	if (val==""){
		alert("“"+name+"”未选择！");
		return false;
	}else{
		return true;
	}
}
//检查对象值的长度是否相等
function checkPrecision(obj,leng,name){
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		if(obj.val().length!=leng){
			alert("“"+name+"”中输入值必须是"+leng+"个长度！");
			obj.focus();
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}
//检查对象值的长度是否符合范围
function checkLength(obj,leng,name){
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		if(obj.val().length>leng){
			alert("“"+name+"”中输入值超过"+leng+"个长度的限制！");
			obj.focus();
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}
//校验传入的文本框文本是否为数值类型
function checkNumber(obj,name){
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		if(!invalidLetter(name,obj.val(),"0123456789.")){
			return false;
		}else{
			if(isNaN(obj.val())){
				alert(name + "不是数值类型！");
				return false;
			}
			return true;
		}
	}else{
		return true;
	}
}
//校验传入的文本框文本是否为整数类型
function checkInt(obj,name,min,max){
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		if(!invalidLetter(name,obj.val(),"0123456789-")){
			return false;
		}else{
			if(isNaN(obj.val())){
				alert(name + "不是整数类型！");
				return false;
			}
			if(typeof min!="undefined"&&obj.val()<min){
				alert(name+"必须大于等于"+min+"！");
				return false;
			}
			if(typeof max!="undefined"&&obj.val()>max){
				alert(name+"必须小于等于"+max+"！");
				return false;
			}
			return true;
		}
	}else{
		return true;
	}
}
//校验传入的文本框文本是否为电话号码
function checkTel(obj,name){
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		if(!invalidLetter(name,obj.val(),"0123456789-(),")){
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}
//校验输入值的时间格式是否正确，限定于YYYY-MM-DD和YYYY-MM-DD HH24:MI的格式
function checkDate(thedate,name){
	try{
		if(thedate.length==10){
			var objRegExp=/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
			if(!objRegExp.test(thedate)){
				alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD”的格式。");
				return false;
			}else{
				var arrayDate=thedate.split(RegExp.$1);
				var intDay=new Number(arrayDate[2],10);
				var intYear=new Number(arrayDate[0],10);
				var intMonth=new Number(arrayDate[1],10);
				if(intMonth>12||intMonth<1){ 
					alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD”的格式。");
					return false;
				} 
				var arrayLookup={'1' : 31,'3' : 31, '4' : 30,'5' : 31,'6' : 30,'7' : 31, '8' : 31,'9' : 30,'10' : 31,'11' : 30,'12' : 31}
				if(arrayLookup[intMonth]!=null){ 
					if(intDay<=arrayLookup[intMonth]&&intDay!=0)
						return true;
				}
				if (intMonth-2==0){
					var booLeapYear=(intYear%4==0&&(intYear%100!=0||intYear%400==0));
					if( ((booLeapYear&&intDay<=29)||(!booLeapYear&&intDay<=28))&&intDay!=0)
						return true;
				}
			}
			alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD”的格式。");
			return false;
		}else if(thedate.length==16){
			var objRegExp=/^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2})$/;
			if(!objRegExp.test(thedate)){
				alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD HH:MM”的格式。");
				return false; 
			}else{
				var intYear=new Number(thedate.substr(0,4));
				var intMonth=new Number(thedate.substr(5,2));
				var intDay=new Number(thedate.substr(8,2));
				var intHour=new Number(thedate.substr(11,2));
				var intMinute=new Number(thedate.substr(14,2));
				if(intMonth>12||intMonth<1){
					alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD HH:MM”的格式。");
					return false;
				}
				if(intHour>23||intHour<0){
					alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD HH:MM”的格式。");
					return false;
				}
				if(intHour>59||intHour<0){
					alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD HH:MM”的格式。");
					return false;
				}
				var arrayLookup={'1' : 31,'3' : 31, '4' : 30,'5' : 31,'6' : 30,'7' : 31, '8' : 31,'9' : 30,'10' : 31,'11' : 30,'12' : 31}
				if(arrayLookup[intMonth]!=null){
					if(intDay<=arrayLookup[intMonth]&&intDay!=0)
						return true;
				}
				if (intMonth-2==0){
					var booLeapYear=(intYear%4==0&&(intYear%100!=0||intYear%400==0));
					if( ((booLeapYear&&intDay<=29)||(!booLeapYear&&intDay<=28))&&intDay!=0)
						return true;
				}
			} 
			alert("“"+name+"”中日期数据不符合要求，\r\n应输入为“YYYY-MM-DD HH:MM”的格式。");
			return false;
		}else if(thedate.length==0){
			return true;
		}else{
			alert("“"+name+"”中日期格式不符合要求");
			return false;
		}
	}catch(e){
		alert("“"+name+"”中日期格式不符合要求");
		return false;
	}
}
//检测开始时间和结束时间
function compareDate(kssj,jssj,name1,name2){
	if(!checkDate(kssj,name1))return false;
	if(!checkDate(jssj,name2))return false;
	if(kssj>jssj){
		alert("“"+name1+"”不能大于“"+name2+"”！");
		return false;
	}
	return true;
}

function compareDDate(kssj,jssj,name1,name2){
	if(!checkDate(kssj,name1))return false;
	if(!checkDate(jssj,name2))return false;
	if(kssj>jssj){
		alert("“"+name1+"”不能大于“"+name2+"”！");
		return false;
	}
	var kstime = new Date(kssj).getTime();
	var jstime = new Date(jssj).getTime();
	if ((jstime - kstime) > 86400 * 2000) {
		alert("分析时段不可超过48小时!");
		return false;
	}
	return true;
}
//检测开始时间和结束时间,不能超过days天
function compareDayTime(kssj,jssj,name1,name2,days,tip){	
	if(!checkDate(kssj,name1))return false;
	if(!checkDate(jssj,name2))return false;
	if(kssj>jssj){
		alert("“"+name1+"”不能大于“"+name2+"”！");
		return false;
	}
	var ksArr = kssj.split(/[-, ,:]/);
	var jsArr = jssj.split(/[-, ,:]/);
	if (ksArr.length <= 5){
		ksArr[5] = 0;
	}
	if (jsArr.length <= 5){
		jsArr[5] = 0;
	}
	if (tip == null){
		tip = "分析时段";
	}
	var kstime = new Date(ksArr[0],ksArr[1],ksArr[2],ksArr[3],ksArr[4],ksArr[5]).getTime();
	var jstime = new Date(jsArr[0],jsArr[1],jsArr[2],jsArr[3],jsArr[4],jsArr[5]).getTime();
	if ((jstime - kstime) > days * 86400 * 1000) {
		alert(tip + "不可超过" + days + "天!");
		return false;
	}
	return true;
}

//时间差小于一个月
function compareMDate(kssj,jssj,name1,name2){
	if(!checkDate(kssj,name1))return false;
	if(!checkDate(jssj,name2))return false;
	if(kssj>jssj){
		alert("“"+name1+"”不能大于“"+name2+"”！");
		return false;
	}
	//date.substring(0,4)+"年"+date.substring(5,7)+"月"+date.substring(8,10)+"日";
	var m1=kssj.substring(5,7);
	var m2=jssj.substring(5,7);
	var d1=kssj.substring(8,10);
	var d2=jssj.substring(8,10);
	if(m1<m2&&d1<d2){
		alert("分析时段不可超过一个月！");
		return false;
	}
	return true;
}
//校验号牌号码
function checkHPHM(hphm,name){
	if (hphm!=null&&hphm.val()!=null&&hphm.val().length>0){
		hphmVal = hphm.val();
		if(hphmVal.length < 6){
			alert("“" + name + "”输入不完整！")
			hphm.focus();
			return false;
		}
		if(hphmVal.indexOf('*') != -1){
			alert("“" + name + "”输入不正确！出现“*”")
			hphm.focus();
			return false;
		}
		if(hphmVal.indexOf('?') != -1){
			alert("“" + name + "”输入不正确！出现“?”")
			hphm.focus();
			return false;
		}
		if(hphmVal.indexOf(' ') != -1){
			alert("“" + name + "”输入不正确！出现空格")
			hphm.focus();
			return false;
		}
		if(hphmVal.indexOf('　') != -1){
			alert("“" + name + "”输入不正确！出现半角空格")
			hphm.focus();
			return false;
		}
		/**var preCN = new Array('京','津','冀','晋','蒙','辽','吉','黑','沪','苏','浙','皖','闽','赣','鲁','豫','鄂','湘','粤','桂','琼','渝','川','贵','云','藏','陕','甘','青','宁');
		//,'新','警','学','使','领','挂','港','澳',
		if(hpzl.val() == '01' || hpzl.val() == '02'){
			reg_1 = /^[A_Z][A-Z,0-9]{5}$/g;
			if(!reg_1.test(hphm.val().substr(1))){
				alert("“号牌号码”输入不正确！");
				hphm.focus();
				return false;
			}
			hphm_1 = hphm.val().substring(0,1);
			isV = false;
			for(i = 0; i < 31; i++){
				if(hphm_1 == preCN[i]){
					isV = true;
					break;
				}
			}
			if(!isV){
				alert("“号牌号码”输入不正确！");
				hphm.focus();
				return false;
			}
		}else if(hpzl.val() == '15'){
			
		}else if(hpzl.val() == '23' || hphm.val() == '24'){
			
		}*/
		
		return true;
	} else {
		return true;
	}

	
}
//校验IP地址
function checkIP(obj,name) { 
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式 
		var str=obj.val();
		if(re.test(str)){ 
			if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true; 
		}
		alert("“"+name+"”中IP地址格式不符合要求！");
		return false; 
	} 
}
//校验IP及端口地址
function checkIP_PORT(obj,name) { 
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)(\:?)(\d*)$/g //匹配IP地址的正则表达式 
		var str=obj.val();
		if(re.test(str)){ 
			if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true; 
		}
		alert("“"+name+"”中IP及端口地址格式不符合要求！");
		return false; 
	} 
}
//校验手机号码
function checkMobile(obj,name){
	if (obj!=null&&obj.val()!=null&&obj.val().length>0){
		var reg = /^0?1[358]\d{9}$/;
		if (!reg.test(obj.val())){
			alert("“"+name+"”中的手机号码不符合要求！");
			return false;
		}else{
			return true;
		}
	}
}

/*
//检测开始时间和结束时间，忽略日期检查。
function checkKssjJssjByTime(kssj,jssj){
	if(!checkDateTime("开始时间",kssj))return false;
	if(!checkDateTime("结束时间",jssj))return false;
	if(kssj>jssj){
		alert("起始时间不能大于终止时间！");
		return false;
	}
	return true;
}
//检查数字和长度
function checkNumberAndLength(obj,vMc,vLength){
	var v;
	v=obj.val();
	v=v.trim();
	if (v.length!=vLength){
		alert("输入的"+vMc+"的长度不是"+vLength+"位！");
		obj.focus();
		return false;
	}
	re=new RegExp("[^0-9]");
	if(s=v.match(re)){
		alert(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
		obj.focus();
		return false;
	}
	return true;
}
function checkDateTime(i_field,thedate){
	if(thedate==null||thedate.length!=16){
		alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
		return false;
	}
	var reg = /^(\d{1,4})(-)(\d{1,2})(-)(\d{1,2})( )(\d{1,2})(:)(\d{1,2})$/;
	var re=new RegExp(reg);
	if(re.test(thedate) == false){
		alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
		return false;
	}
	var r = thedate.match(reg);
	if(r!=null){
		var hh = new Number(r[7]);
		if(isNaN(hh)){
			alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
			return false;
		}
		if(hh < 0){
			alert("'"+i_field+"'日期格式不对,小时不能小于0！");
			return false;
		}
		if(hh > 23){
			alert("'"+i_field+"'日期格式不对,小时不能大于23！");
			return false;
		}
		var mi = new Number(r[9]);
		if(isNaN(mi)){
			alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
			return false;
		}
		if(mi < 0){
			alert("'"+i_field+"'日期格式不对,分钟不能小于0！");
			return false;
		}
		if(mi > 59){
			alert("'"+i_field+"'日期格式不对,分钟不能大于59！");
			return false;
		}
		var mm = new Number(r[3]);
		if(isNaN(mm)){
			alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
			return false;
		}
		var dd = new Number(r[5]);
		if(isNaN(dd)){
			alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
			return false;
		}
		var d= new Date(r[1],r[3]-1,r[5]);
		var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate();
		var newStr2 = r[1]+"-"+mm+"-"+dd;
		if(newStr != newStr2){
			alert(newStr + "\n" + newStr2);
			alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
			return false;
		}
	}else{
		alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd hh:mi！");
		return false;
	}
	return true;
}
*/
//翻译道路表中的道路类型至卡口表中的道路类型
function translateDllx(dldm){
	if(dldm==null||dldm.length<1){
		return "9:其他道路";
	}else{
		var d=dldm.substring(0,1);
		if(d=="0"){
			return d + ":高速";
		}else if(d=="1"){
			return d + ":国道";
		}else if(d=="2"){
			return d + ":省道";
		}else if(d=="3"){
			return d + ":县道";
		}else if(d=="4"){
			return d + ":乡村道";
		}else if(d=="5"){
			return d + ":城市快速路";
		}else if(d=="6"){
			return d + ":城市主干道";
		}else if(d=="7"){
			return d + ":城市次干道";
		}else if(d=="8"){
			return d + ":城市支路";
		}else if(d=="A"){
			return d + ":单位小区自建路";
		}else if(d=="B"){
			return d + ":公共停车场";
		}else if(d=="C"){
			return d + ":公共广场";
		}else{
			return "9:其他道路";
		}
	}
}

//检测输入项中是否存在特殊字符
function checkSpecilCharAndNull(obj,vMc){
	var s_tmp,s;
	s_tmp=obj.val();
	s_tmp=s_tmp.trim();
	if (s_tmp.length==0){
		alert(vMc+"输入值为空！");
		return false;
	}
	re=new RegExp("[^0-9A-Za-z]");
	if(s=s_tmp.match(re)){
		alert("输入的"+vMc+"值中含有非法字符'"+s+"'请检查！");
		obj.focus();
		return false;
	}
	return true;
}
//检测输入的ip地址是否正确
function checkIp(obj,vMc){
	var s_tmp,s;
	var iValue;
	s_tmp=obj.val();
	s_tmp=s_tmp.trim();
	if (s_tmp.length==0){
		alert(vMc+"输入值为空！");
		return false;
	}
	re=new RegExp("[^0-9]");
	if(s=s_tmp.match(re)){
		alert("输入的"+vMc+"值中含有非法字符'"+s+"'请检查！");
		obj.focus();
		return false;
	}
	iValue=parseInt(obj.value);
	if (iValue>255){
		alert("输入的"+vMc+"值"+iValue+"大于255，请检查！");
		obj.focus();
		return false;
	}
	return true;
}

//关闭窗口
function closewin(){
	window.close();
}

//检查输入字符串是否为数字
//参数说明：数据项，输入的对应值
//返回值：1-是数字,0-非数字
function isNum(i_field,i_value){
	re=new RegExp("[^0-9]");
	var s;
	if(s=i_value.match(re)){
		alert("'"+i_field+"' 中含有非法字符 '"+s+"'！");
		return 0;
	}
	return 1;
}

function isDate(obj){
	var thedate="";
	try{
		thedate=$("#"+obj);
	}catch(e){
		thedate=document.all[obj];
	}
	if(typeof(thedate.value)=='undefined'){
		thedate=document.all[obj];
	}
	if(thedate.value.length==0||thedate.value==null){
		alert("日期格式不对,\n要求为yyyy-mm-dd！");
		riqi(obj);
		return 0;
	}
/*
	if (!(thedate.value.length==10)){    
		alert("日期格式不对,\n要求为yyyy-mm-dd！");
		riqi(obj);
		return 0;
	}
*/
	var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
	var r = thedate.value.match(reg);
	if (!(r==null)){
		var d= new Date(r[1],r[3]-1,r[4]);
		var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
		var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
		if (newStr==newDate){
			return 1;
		}
		alert("日期格式不对,\n要求为yyyy-mm-dd！");
//		riqi(obj);
		return 0;
	}
	alert("日期格式不对,\n要求为yyyy-mm-dd！");
//	riqi(obj);
	return 0;
}

//判断身份证号的合法性
function checksfz(zjhmObj) {
	var birthday = "";
	var zjhm1 = "";
	var zjhm2 = "";
	var s = "";
	var zjhm = zjhmObj.val();
	if(zjhm.length>0){
		if (!(zjhm.length == 15 || zjhm.length == 18)) {
			alert("证件长度不对,请检查！",2);
			zjhmObj.focus();
			return 0;
		}
		zjhm1 = zjhm;
		if (zjhm.length == 18) {
			zjhm1 = zjhm.substr(0, 17);
			zjhm2 = zjhm.substr(17, 1);
		}
		re = new RegExp("[^0-9]");
		if (s = zjhm1.match(re)) {
			alert("输入的值中含有非法字符'" + s + "'请检查！",2);
			zjhmObj.focus();
			return 0;
		}
		if (zjhm.length == 15) {
			birthday = "19" + zjhm.substr(6, 6);
		}else {
			re = new RegExp("[^0-9A-Z]");
			if (s = zjhm2.match(re)) {
				alert("输入的值中含有非法字符'" + s + "'请检查！",2);
				zjhmObj.focus();
				return 0;
			}
			birthday = zjhm.substr(6, 8);
		}
		birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +
		birthday.substr(6, 2)
		if (newisDate("证件号码出生日期", birthday) == 0) {
			zjhmObj.focus();
			return 0;
		}
		/*
		if (nl-0<18 || nl>70){
			alert("申请年龄要求 18--70 ,当前 "+nl+" ！");
			return 0;
		}
		*/
		if (zjhm.length == 18) {
			return (newsfzCheck(zjhm));
		}
		// else{
		//  zjhmObj.value=id15to18(zjhm);
		// }
	}
	return 1;
}
function newsfzCheck(hm){
	var w=new Array();
	var ll_sum;
	var ll_i;
	var ls_check;
	w[0]=7;
	w[1]=9;
	w[2]=10;
	w[3]=5;
	w[4]=8;
	w[5]=4;
	w[6]=2;
	w[7]=1;
	w[8]=6;
	w[9]=3;
	w[10]=7;
	w[11]=9;
	w[12]=10;
	w[13]=5;
	w[14]=8;
	w[15]=4;
	w[16]=2;
	ll_sum=0;
	for (ll_i=0;ll_i<=16;ll_i++){
		ll_sum=ll_sum+(hm.substr(ll_i,1)-0)*w[ll_i];
	}
	ll_sum=ll_sum % 11;
	switch (ll_sum){
		case 0 :
			ls_check="1";
			break;
		case 1 :
			ls_check="0";
			break;
		case 2 :
			ls_check="X";
			break;
		case 3 :
			ls_check="9";
			break;
		case 4 :
			ls_check="8";
			break;
		case 5 :
			ls_check="7";
			break;
		case 6 :
			ls_check="6";
			break;
		case 7 :
			ls_check="5";
			break;
		case 8 :
			ls_check="4";
			break;
		case 9 :
			ls_check="3";
			break;
		case 10 :
			ls_check="2";
			break;
	}
	if (hm.substr(17,1) != ls_check){
		alert("证件校验错误！末位应该:"+ls_check+" 实际:"+hm.substr(17,1));
		return 0;
	}
	return 1
}
function newisDate(i_field,thedate){
	if (thedate.length==8){
		thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
	}
	var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
	var r = thedate.match(reg);
	if (r==null){
		alert("'"+i_field+"'含非法字符！");
		return 0;
	}
	var d= new Date(r[1],r[3]-1,r[4]);
	var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
	var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
	if (newStr==newDate){
		return 1;
	}
	alert("'"+i_field+"'日期格式不对！");
	return 0;
}
function id15to18(zjhm) {
	var strJiaoYan = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
	var intQuan = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
	var ll_sum = 0;
	var i;
	var t;
	zjhm = zjhm.substr(0, 6) + "19" + zjhm.substr(6);
	for (i = 0; i < zjhm.length; i++) {
		ll_sum = ll_sum +
		parseInt(zjhm.substr(i,1)) * intQuan[i];
	}
	ll_sum = ll_sum % 11;
	zjhm = zjhm + strJiaoYan[ll_sum];
	return zjhm;
}
//判断日期时间是否合法
function isDateTime(i_field,obj){
	var thedate;
	thedate=obj.val();
	if(thedate.length==0||thedate==null){
		alert("请输入"+i_field);
		obj.focus();
		return 0;
	}
	if(thedate.length==16){
		thedate=thedate+":00";
	}
	if (!(thedate.length==19)){
		alert("'"+i_field+"'格式不对,请输入正确的格式：yyyy-mm-dd hh24:mi！");
		obj.focus();
		return 0;
	}
	dateRegexp = new RegExp("^(\\d{4})[-](\\d{2})[-](\\d{2})[ ](\\d{2})[:](\\d{2})[:](\\d{2})$");
	var matched = dateRegexp.exec(thedate);
	if(matched != null) {
		if (!isTrueDateTime(matched[3], matched[2], matched[1],matched[4], matched[5], matched[6])){
			alert("'"+i_field+"'格式不对，请输入正确的格式：yyyy-mm-dd hh24:mi！");
			obj.focus();
			return 0;
		}else{
			return 1;
		}
	}
	return 0;
}
function isTrueDateTime(day, month, year,hour,min,second) {
	if (month < 1 || month > 12) {
		return false;
	}
	if (day < 1 || day > 31) {
		return false;
	}
	if ((month == 4 || month == 6 || month == 9 || month == 11) &&(day == 31)) {
		return false;
	}
	if (month == 2) {
		var leap = (year % 4 == 0 &&(year % 100 != 0 || year % 400 == 0));
		if (day>29 || (day == 29 && !leap)) {
			return false;
		}
	}
	if(hour<0||hour>23){
		return false;
	}
	if(min<0||min>59){
		return false;
	}
	if(second<0||second>59){
		return false;
	}
	return true;
}

//改变form的状态 0-可写 1-编辑只读 disabled 2-查看只读readonly
function changeformstate(form,iZt){
	switch (iZt){
	case 0:
	for(i=0;i<form.elements.length;i++)
	form.elements[i].disabled = false;
	break;
	case 1:
		for(i=0;i<form.elements.length;i++){
			form.elements[i].disabled = true;
		}
		break;
	case 2:
		for(j=0;j<form.elements.length;j++){
			if(form.elements[j].type=="select-one"&&form.elements[j].size<=1){
				if(form.elements[j].options.selectedIndex>=0){
					sValue = form.elements[j].value;
					sCaption = form.elements[j].options[form.elements[j].options.selectedIndex].innerText;
					//clearFieldOptions(form.elements[j]);
					form.elements[j].innerHTML = "";
					changecomboxvalue(form.elements[j],sValue,sCaption);
				}else{
				//clearFieldOptions(form.elements[j]);
					form.elements[j].innerHTML = "";
				}
			}else if(form.elements[j].type=="checkbox"||form.elements[j].type=="radio"){
				form.elements[j].disabled = true;
			}else if(form.elements[j].type=="button"){
				form.elements[j].style.visibility = 'hidden';
			}else{
				form.elements[j].readOnly = true;
			}
		}
		break;
	}
}

function setformtextvalue(form,value){
	for(i=0;i<form.elements.length;i++)
		if(form.elements[i].type=="text"||form.elements[i].type=="select-one"||form.elements[i].type=="hidden")
			form.elements[i].value = value;
}

//取字符串的字节长度
function strlen(str){
	var len;
	var i;
	len = 0;
	for (i=0;i<str.length;i++){
		if (str.charCodeAt(i)>255) len+=2; else len++;
	}
	return len;
}
//取指定月的最后一天
function getMonthLastDate(iYear,iMonth){
	var dateArray= new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	var days=dateArray[iMonth-1];
	if ((((iYear % 4 == 0) && (iYear % 100 != 0)) || (iYear % 400 == 0)) && iMonth==2){
		days=29;
	}
	return days;
}
//取年月日格式
function getNyr(date){
	if(date.length==10)//类似2005-02-01
	{
		return date.substring(0,4)+"年"+date.substring(5,7)+"月"+date.substring(8,10)+"日";
	}else if(date.length==16)//类似2005-02-01 01:01
	{
		return date.substring(0,4)+"年"+date.substring(5,7)+"月"+date.substring(8,10)+"日"+date.substring(11,13)+"时"+date.substring(14,16)+"分";
	}else if(date.length==18)//18位身份证号
	{
		return date.substring(6,10)+"年"+date.substring(10,12)+"月"+date.substring(12,14)+"日";
	}else if(date.length==15)//15位身份证号
	{
		return "19"+date.substring(6,8)+"年"+date.substring(8,10)+"月"+date.substring(10,12)+"日";
	}else{
		return "";
	}
}

//日期类设置方法
/*
function riqi(rq){
	try{
		gfPop.fPopCalendar(document.all[rq]);
	}catch(e){
		gfPop.fPopCalendar(document.myform.all[rq]);
	}
	return false;
}
*/
//固定表头的滚动条占位计算方法
function echo() {
	var a, t = document.getElementById("scrollbar");
	t.wrap = 'off';
	a = t.offsetHeight;
	t.wrap = 'soft';
	a -= t.offsetHeight;
	a ? a : 0;
	return a
}

//强制把大写转换成小写
function toLowCase(){
	if(event.keyCode>=65 && event.keyCode<=90)
		event.keyCode=event.keyCode+32;
}
//检测对象项是否已输入值	by wengyf
function isEmpty(obj,msg){
	try{
		if(obj.val().length<1){
			if(msg.length>1)
				alert(msg)
			else
				alert('请输入必填项！');
			document.all[obj].focus();
			return true;
		}else{
			return false;
		}
	}catch(e){
		return false;
	}
}

//substring	by wengyf
function substr(str,begin,end){
	if(begin>=str.length){
		document.write(str);
	}else if(end>=str.length){
		document.write(str.substring(begin,str.length));
	}else{
		document.write(str.substring(begin,end));
	}
}
function getsubstr(str,begin,end){
	if(begin>=str.length){
		return str;
	}else if(end>=str.length){
		return str.substring(begin,str.length);
	}else{
		return str.substring(begin,end);
	}
}
//截字显示	by wengyf
function getwordshow(str,len){
	var t_Str = "";
	if(len<str.length){
		t_Str="<span title = \""+str+"\" style=\"cursor:pointer;\">";
		t_Str+=str.substr(0,len)+"..";
		t_Str+="</span>"
	}else{
		t_Str = str;
	}
	return t_Str;
}
//显示年月日	by wengyf
function getshowdate(str){
	var t_Str = "";
	if(str.length>0){
		t_Str=str.substr(0,10);	
	}
	return t_Str;
}

function getshowtime(str){
	var t_Str = "";
	if(str.length==10){
		t_Str=str+" 00:00:00";
	}
	if(str.length>=19){
		t_Str=str.substr(0,19);	
	}
	return t_Str;
}
//反射OBJECT中的CHECK属性和WARN属性	by wengyf
function check(){
	var objs=$("form input");
	for(var i=0;i<objs.length;i++){
		if($(objs[i]).attr("check")!=undefined){
			var sReg=$(objs[i]).attr("check");
			var sVal=$(objs[i]).val();
			var reg = new RegExp(sReg,"i");
			if(!reg.test(sVal)){
				alert($(objs[i]).attr("warn"));
				return true;
			}
		}
	}
	return false;
}
//取得当前时间
function getNow(time){
	var len= arguments.length;
	var r;
	var d=new Date();
	if(0==len){
		r=d.getFullYear()+"-";
		if(d.getMonth()>=9)
			r+=(d.getMonth()+1)+"-";
		else
			r+="0"+(d.getMonth()+1)+"-";	
		if(d.getDate()<10)
			r+="0"+(d.getDate());
		else
			r+=(d.getDate());
	}else if(1==len){
		r=d.getFullYear()+"-";
		if(d.getMonth()>=9)
			r+=(d.getMonth()+1)+"-";
		else
			r+="0"+(d.getMonth()+1)+"-";	
		if(d.getDate()<10)
			r+="0"+(d.getDate())+" ";
		else
			r+=(d.getDate())+" ";
		if(d.getHours()<10)
			r+="0"+(d.getHours())+":";
		else
			r+=(d.getHours())+":";
		if(d.getMinutes()<10)
			r+="0"+(d.getMinutes())+":";
		else
			r+=(d.getMinutes())+":";
		if(d.getSeconds()<10)
			r+="0"+(d.getSeconds());
		else
			r+=(d.getSeconds());
	}
	return r;
}

//取得昨天时间
function getYesterday(){
	var today = new Date();   
  today.setTime(today.getTime()-(24*60*60*1000));   
	var month=today.getMonth()+1; 
	var day=today.getDate(); 
	if(month<10) sMonth="0"+month;
	else sMonth = month;
	if(day<10) sDay="0"+day; 
	else sDay = day; 
	r=today.getFullYear()+"-"+sMonth+"-"+sDay; 
	return r;
}
function getTomorrow(){
	var today = new Date();   
  today.setTime(today.getTime()+(24*60*60*1000));   
	var month=today.getMonth()+1; 
	var day=today.getDate(); 
	if(month<10) sMonth="0"+month;
	else sMonth = month;
	if(day<10) sDay="0"+day; 
	else sDay = day; 
	r=today.getFullYear()+"-"+sMonth+"-"+sDay; 
	return r;
}
//取得上周时间
function getLastWeek(){
	var r;
	var d=new Date();
	var t = d.getDate();
	t = t-7;
	d.setDate(t);
	r=d.getFullYear()+"-";
	if(d.getMonth()>=9)
		r+=(d.getMonth()+1)+"-";
	else
		r+="0"+(d.getMonth()+1)+"-";	
	if(d.getDate()<10)
		r+="0"+(d.getDate());
	else
		r+=(d.getDate());
	return r;
}
//获取下周日期
function getNextWeek(){
	var r;
	var d=new Date();
	var t = d.getDate();
	t = t+7;
	d.setDate(t);
	r=d.getFullYear()+"-";
	if(d.getMonth()>=9)
		r+=(d.getMonth()+1)+"-";
	else
		r+="0"+(d.getMonth()+1)+"-";	
	if(d.getDate()<10)
		r+="0"+(d.getDate());
	else
		r+=(d.getDate());
	return r;
}

//取得本月第一天时间
function getCurMonthFirstDay(){
	var date1 = new Date();
	year1 = date1.getFullYear();
	month1 = date1.getMonth(); 
	sMonth = month1+1;
	if(sMonth < 10)
		sMonth = "0" + sMonth;
	sDay = "01";
	var myredate = year1 + "-" + sMonth + "-" + sDay;
	return myredate;
}


//取得上月时间
function getLastMonth(){
	var date1 = new Date();
	var month1 = date1.getMonth();
	date1.setMonth(month1-1);
	month1 = date1.getMonth();
	var date2 = new Date();
	var day2 = date2.getDate();
	var msecs = date2 - date1;
	var days = msecs/24/60/60/1000;
	var year1 = "";
	if(days < day2){
		var tmpmonth = date1.getMonth();
		date1.setMonth(tmpmonth-1);
		date1.setDate(days);
	}
	year1 = date1.getFullYear();
	month1 = date1.getMonth() + 1;
	date1 = date1.getDate();
	var sMonth = month1;
	var sDay = date1;
 
	if(month1 < 10)
		sMonth = "0" + month1;
	if(date1 < 10)
		sDay = "0" + date1;
	var myredate = year1 + "-" + sMonth + "-" + sDay;
	return myredate;
}

//取得上月时间,YYYYMM格式
function getLastMonth2(){
	var date1 = new Date();
	var month1 = date1.getMonth();
	date1.setMonth(month1-1);
	month1 = date1.getMonth();
	var date2 = new Date();
	var day2 = date2.getDate();
	var msecs = date2 - date1;
	var days = msecs/24/60/60/1000;
	var year1 = "";
	if(days < day2){
		var tmpmonth = date1.getMonth();
		date1.setMonth(tmpmonth-1);
		date1.setDate(days);
	}
	year1 = date1.getFullYear();
	month1 = date1.getMonth() + 1;
	date1 = date1.getDate();
	var sMonth = month1;
	var sDay = date1;
 
	if(month1 < 10)
		sMonth = "0" + month1;
	if(date1 < 10)
		sDay = "0" + date1;
	var myredate = year1 + sMonth;
	return myredate;
}

//取得下月时间
function getNextMonth(){
	var today = new Date(); 
	var x=today.getMonth(); 
	x=x+1;
	today.setMonth(x); 
	var month=today.getMonth()+1;
	var day=today.getDate(); 
	if(month<10) sMonth="0"+month; 
	else sMonth = month; 
	if(day<10) sDay="0"+day; 
	else sDay = day; 
	r=today.getFullYear()+"-"+sMonth+"-"+sDay; 
	return r;
}
//取得去年时间
function getLastYear(){
	var today = new Date(); 
	var x=today.getFullYear(); 
	x=x-1;
	today.setYear(x); 
	var month=today.getMonth()+1; 
	var day=today.getDate(); 
	if(month<10) sMonth="0"+month; 
	else sMonth = month; 
	if(day<10) sDay="0"+day; 
	else sDay = day; 
	r=today.getFullYear()+"-"+sMonth+"-"+sDay; 
	return r;
}

function getHPZLMAP(v){
	if(v==null||v.length<1){
		return -1;
	}else{
		return $("#hpzl").data(v);
	}
}
var hphmtwidth,hphmwidth,hphmtmp;
function chooseHPZL(v){
	if(v==null||v.length<1){
	}else{
		var preobj;
		var sufobj;
		preobj=$("#prehphm");
		sufobj=$("#sufhphm");
		if(typeof(hphmtmp)=='undefined'){
			hphmtmp=preobj.val();
		}
		if(typeof(hphmtwidth)=='undefined'||hphmtwidth==null||hphmtwidth.length<1)
			hphmtwidth=preobj.css("width");
		if(typeof(hphmwidth)=='undefined'||hphmwidth==null||hphmwidth.length<1)
			hphmwidth=sufobj.css("width");
		var i=$("#hpzl").data(v);
		if(i==0){
//			preobj.css("width","0%");
			preobj.hide();
			sufobj.css("width","100%");
			sufobj.attr("maxLength",10);
			preobj.val("");
		}else if(i==1){
			if(sufobj.val().length>6);
				sufobj.val(sufobj.val().substring(0,6));
//			preobj.css("width",hphmtwidth);
			preobj.show();
			sufobj.css("width",hphmwidth);
			sufobj.attr("maxLength",6);
			preobj.val(hphmtmp);
		}
	}
}
function chooseHPHMT(v){
	hphmtmp=$("#prehphm").val();
}

//检查视频控件版本
function checkVersion(oVersion,nVersion){
	if (nVersion==null || nVersion.length<7)
	    nVersion = "1.5.3.0";
	
	var oVerarr=oVersion.split(".");
	var nVerarr=nVersion.split(".");
	
	if (oVerarr[2]<nVerarr[2] || (oVerarr[2]==nVerarr[2] && oVerarr[3]<nVerarr[3]))
        return false;
	return true;
}

/*
//校验号牌号码
function checkHPHM(hpzl,hphm){
	var len= arguments.length;
	if(1==len){
		var hphm = arguments[0];
		if(hphm==""||hphm.length<1){
			alert("请输入号牌号码！");
			return false;
		}
		if(hphm.length<=6){
			alert("请输入完整的号牌号码！");
			return false;
		}
		if(hphm.length==7){
		  var h=hphm.toUpperCase().substring(1,hphm.length);
		  if(!invalidLetter("号牌号码",h,"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ挂学试警港澳")){
		    return false;
		  }
		}
		return true;
	}else if(2==len){
		var hpzl = arguments[0];
		var hphm = arguments[1];
	  var h=hphm.toUpperCase().substring(1,hphm.length);
		var jp="军海空北南沈济兰成广";
		if(hpzl==""||hpzl.length<1){
			alert("请输入号牌种类！");
			return false;
		}
		if(hphm==""||hphm.length<1){
			alert("请输入号牌号码！");
			return false;
		}
		if (hpzl=="03"||hpzl=="04"||hpzl=="15"||hpzl=="16"||hpzl=="17"||hpzl=="18"||hpzl=="19"||hpzl=="23" ||hpzl=="24"){
			if(hphm.length<=5){
				alert("请输入完整的号牌号码！");
				return false;			
			}
		}else if(hphm.length<=6){
			alert("请输入完整的号牌号码！");
			return false;
		}
		var h=hphm.toUpperCase().substring(1,hphm.length);
		if(hphm.length==7){
		  if(!invalidLetter("号牌号码",h,"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ挂学试警港澳")){
		    return false;
		  }
		}
		if((hpzl=="01"||hpzl=="02")&&h.length!=6){
			alert("号牌号码长度必须为6位！")
			return false;
		}else if(hpzl=="31"&&hphm.toUpperCase().substr(0,2)!="WJ"){
			alert("错误的武警号牌！");
			return false;
		}else if(hpzl=="32"&&jp.indexOf(hphm.substr(0,1))==-1){
			alert("错误的军牌！");
			return false;
		}
		return true;
	}
}
*/
jQuery.fn.addOption = function(value,text){
	jQuery(this).get(0).options.add(new Option(text,value));
}
jQuery.fn.clearOption = function(){
	jQuery(this).empty();
}

//滚动条
function echo() {
	var a, t = document.getElementById("scrollbar");
	t.wrap = 'off';
	a = t.offsetHeight;
	t.wrap = 'soft';
	a -= t.offsetHeight;
	a ? a : 0;
	return a
}
//读写COOKIE
function getCookie(param,defaultvalue){ 
	var m=""; 
	if(window.RegExp){ 
		var re=new RegExp(";\\s*"+param+"=([^;]*)","i"); 
		m=re.exec(';'+document.cookie); 
	} 
	return(m?unescape(m[1]):defaultvalue);
}
function setCookie(param,values){
	var exp="";
	var dt=new Date(); 
	dt.setTime(dt.getTime()+(1*86400000)); 
	exp="; expires="+dt.toGMTString();
	document.cookie=param+"="+escape(values)+exp+";path=/";
}
function getMultiple(obj){
	var r="";
	obj.each(function(){
		r+=$(this).val()+",";
	})
	if(r.length>0){
		r=r.substr(0,r.length-1);
	}
	return r;
}
function setHphmToUpcase(obj){
	var len= arguments.length;
	if(1==len){
		var sn=obj.val();
		sn=sn.toUpperCase();
		if(obj.val()!=sn)
			obj.val(sn);
	}else{
		var sn=$("#sufhphm").val();
		sn=sn.toUpperCase();
		if($("#sufhphm").val()!=sn)
			$("#sufhphm").val(sn);
	}
}

function copyToClipBoard(clipBoardContent){
	if(window.clipboardData){ 
		window.clipboardData.clearData(); 
		window.clipboardData.setData("Text", clipBoardContent);
	}else if(navigator.userAgent.indexOf("Opera") != -1){ 
		window.location = clipBoardContent; 
	}else if (window.netscape){ 
		try{ 
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
		}catch (e){ 
			alert("您的当前浏览器设置已关闭此功能！请按以下步骤开启此功能！\n新开一个浏览器，在浏览器地址栏输入'about:config'并回车。\n然后找到'signed.applets.codebase_principal_support'项，双击后设置为'true'。\n声明：本功能不会危极您计算机或数据的安全！"); 
		} 
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard); 
		if (!clip) return; 
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable); 
		if (!trans) return; 
		trans.addDataFlavor('text/unicode'); 
		var str = new Object(); 
		var len = new Object(); 
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString); 
		var copytext = clipBoardContent; 
		str.data = copytext; 
		trans.setTransferData("text/unicode",str,copytext.length*2); 
		var clipid = Components.interfaces.nsIClipboard; 
		if (!clip) return false; 
		clip.setData(trans,null,clipid.kGlobalClipboard); 
	}
	return true;
}

function openImage(imageIdx) {
	var imgPath = 'pic.tfc?method=toZoomJsp&ImgAreaName='+imageIdx;
	return window.open(imgPath,'','');
}

/*
//add by mxd
showLoadingMessage=function(message){
	var loadingMessage;
	if (message) loadingMessage = message;
	else loadingMessage = "Loading";
    var disabledZone = document.getElementById('disabledZone');
    var img2 = document.createElement("img");
    img2.setAttribute("src","image/loading.gif");
//    img2.style("vertical-align")="middle";
    if (!disabledZone) {
      disabledZone = document.createElement('div');
      disabledZone.setAttribute('id', 'disabledZone');
      disabledZone.style.position = "absolute";
      disabledZone.style.zIndex = "1000";
      disabledZone.style.left = "0px";
      disabledZone.style.top = "0px";
      disabledZone.style.width = "100%";
      disabledZone.style.height = "100%";
      document.body.appendChild(disabledZone,-2);
      var messageZone = document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
      messageZone.style.position = "absolute";
      messageZone.style.top = "200px";
      messageZone.style.right = "400px";
//      messageZone.style.background = "red";
      messageZone.style.color = "red";
      messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
      messageZone.style.padding = "4px";
      disabledZone.appendChild(messageZone);
      var text = document.createTextNode(loadingMessage,-2);
      messageZone.appendChild(img2);
      messageZone.appendChild(text);
    }
    else {
   	  messageZone.appendChild(img2);
      document.getElementById('messageZone').innerHTML = loadingMessage;
      disabledZone.style.visibility = 'visible';
	}
}

function showLoadingMessageA(message){
	var loadingMessage;
	if (message) loadingMessage = message;
	else loadingMessage = "Loading";
    var disabledZone = document.getElementById('disabledZone');
    var img2 = document.createElement("img");
    img2.setAttribute("src","image/loading.gif");
    img2.style.visibility="visible";
    img2.style.display="block";
	var mes = document.createElement("p");
	mes.innerHTML=message;
    
    if (!disabledZone) {
      disabledZone = document.createElement('div');
      disabledZone.setAttribute('id', 'disabledZone');
      disabledZone.style.position = "absolute";
      disabledZone.style.zIndex = "1000";
      disabledZone.style.left = "0px";
      disabledZone.style.top = "0px";
      disabledZone.style.width = "100%";
      disabledZone.style.height = "100%";
      document.body.appendChild(disabledZone,-2);
      var messageZone = document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
      messageZone.style.position = "absolute";
      messageZone.style.top = "140px";
      messageZone.style.right = "365px";
      messageZone.style.background = "white";
      messageZone.style.color = "red";
      messageZone.style.fontFamily = "Arial";
      messageZone.style.padding = "4px";
      messageZone.style.textAlign = "center";
      messageZone.style.verticalAlign = "middle";
      disabledZone.appendChild(messageZone);
      var text = document.createTextNode(loadingMessage,-2);
      messageZone.appendChild(img2);
      messageZone.appendChild(mes);
    }
    else {
    	document.body.removeChild(disabledZone);
//   	  messageZone.appendChild(img2);
//   	  messageZone.appendChild(mes);
//      document.getElementById('messageZone').innerHTML = loadingMessage;
 //     disabledZone.style.visibility = 'visible';
 showLoadingMessageA(message);
	}
}
*/