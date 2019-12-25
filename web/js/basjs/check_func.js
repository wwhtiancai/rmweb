/**
 * 该文件用于检测客户端输入合法性校验
 * 
 */
// -------------------------------
// 函数名：isDate(i_field,thedate)
// 功能介绍：校验字符串是否为日期格式
// 参数说明：数据项，输入的字符串
// 返回值 ：0-不是，1--是
// -------------------------------

// 判断日期类型是否合法 zhoujn 20060323
function isDate(i_field,thedate){
  if(thedate.length==0||thedate==null){
	  displayInfoHtml(i_field+"日期格式不对,\n要求为yyyy-mm-dd！");
	  return 0;
  }
  if (!(thedate.length==8 || thedate.length==10)){
	  displayInfoHtml(i_field+"日期格式不对,\n要求为yyyy-mm-dd！");
	  return 0;
  }
  if (thedate.length==8){
  	thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
  }
  var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
  var r = thedate.match(reg);
  if (!(r==null)){
    var d= new Date(r[1],r[3]-1,r[4]);
    var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
    var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
    if (newStr==newDate){
      return 1;
    }
    displayInfoHtml(i_field+"日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！");
    return 0;
  }else{
	displayInfoHtml(i_field+"日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！");
	return 0;  
  }	   
}

//至少输入minlen个汉字
function chk_core_hanzi(obj,tip,minlen){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value.trim();
	var reg   =   /^w[\u4E00-\u9FA5]+/g;   
	if (reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"必须包括中文！");
		return false;
	}else{
		var len=value.replace(/[^\u4e00-\u9fa5]/gi,"").length;
		if(len<minlen){
			displayInfoHtml(tip+"至少包含"+minlen+"个汉字！");
			obj.focus();
			return false;
		}	
	}	
	return true;
}

//保持原先不变
function notNull(i_field,i_value){
	if (i_value==null || i_value.trim()==""){
		displayInfoHtml(i_field+"不可为空！");
    	return 0;
 	}
 	return 1;
}


//cal_years
//比较日期差必须大于某个值

function chk_core_betw(date1,date2,tip,num){
	var value1=date1.value;
	var value2=date2.value;
	var num1=cal_years(value1);
	var num2=cal_years(value2);
	var num3=num1-num2;
	if(num3<num){
		displayInfoHtml(tip+"不应小于"+num);
		date2.focus();
		return false;
	}else{
		return true;
	}	
}

//验证该栏不能为空
function chk_core_null(obj,tip){
	var value=obj.value.trim();
	if(value!=""){
		displayInfoHtml(tip+"必须为空！");
		obj.focus();
		return false;
	}
	return true;
}


/***********以下是合成验证****************/

//用户勤务工作台帐，巡逻民警个数限制的
function  getcount(obj,tip,maxvalue){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	var count=value.replace(/[^/]/g, '').length+1;
	if(count>maxvalue){
		displayInfoHtml('民警人数应小于'+maxvalue);
		obj.focus();
		return false;
	}
	return true;
}


function  na_getcount(obj,tip,maxvalue){
		var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(!na_chk_core_notnull(obj,tip)){
		return temp;
	}

	var count=value.replace(/[^/]/g, '').length+1;
	if(count>maxvalue){
        result=result+"民警人数应小于"+maxvalue;
		temp=false;
		return temp;
	}
	
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}
//用户勤务工作台帐，巡逻民警个数限制的
function  getcountpatrol(obj,tip,maxvalue){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	var count=value.replace(/[^#]/g, '').length+1;
	if(count>maxvalue){
		displayInfoHtml('民警人数应小于'+maxvalue);
		return false;
	}
	return true;
}

//验证班线起点和终点
function check_bxqz(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	
	var value=obj.value.trim();
	var reg   =   /^[\u4e00-\u9fa5]*\-[\u4e00-\u9fa5]*$/;   
	if (!reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"格式为[起点(中文)-终点(中文)]!");
		return false;
	}	
	return true;
}

//验证班线起点和终点
function check_zyld(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	
	var value=obj.value.trim();
	var reg   =   /^[\u4e00-\u9fa5]*\/[\u4e00-\u9fa5]*$/;   
	if (!reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"格式为[姓名(中文)/姓名(中文)]!");
		return false;
	}	
	return true;
}







function chk_core_notnull(obj,tip){
	var value=obj.value.trim();
	if(value==""){
		displayInfoHtml(tip+"该栏输入不能为空！");
		obj.focus();
		return false;
	}
	return true;
}

function na_chk_core_notnull(obj,tip){
	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(value==""){
		result=result+"该栏输入不能为空";
		temp=false;
	}
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}



//验证汉字
function chk_core_hanziall(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	var   reg   =   /[^\u4E00-\u9FA5]/g;   
	if (reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"该栏只能输入中文！");
		return false;
	}
	return true;
}






function na_chk_core_hanzi(obj,tip,minlen){
	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if (value==null || value.trim()==""){
		result=result+"不能为空"
		temp=false;
	}	

	var reg   =   /^w[\u4E00-\u9FA5]+/g;   
	if (reg.test(value)){
		obj.focus();
		result=result+",必须包括中文";
		temp=false;
	}
	
	var len=value.replace(/[^\u4e00-\u9fa5]/gi,"").length;
	if(len<minlen){
		result=result+",至少包含"+minlen+"个汉字！";
		temp=false;
	}	
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}



//验证长度
function chk_core_minlen(obj,tip,minlen){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	if(value.length<minlen){
		obj.focus();
		displayInfoHtml(tip+"该栏输入不能少于"+minlen+"个！");
		return false;
	}
	return true;
}

function chk_core_maxlen(obj,tip,maxlen){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	if(value.length>maxlen){
		obj.focus();
		displayInfoHtml(tip+"该栏输入不能大于"+maxlen+"个！");
		return false;
	}
	return true;
}

//检测长度
function chk_core_len(obj,tip,len){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	if(value.length!=len){
		obj.focus();
		displayInfoHtml(tip+"该栏输入长度必须为"+len+"！");
		return false;
	}
	return true;
}

//只检测身份证明号码是否正确
function chk_core_sfzmhm(obj){
	if(!chk_core_notnull(obj)){
		return false;
	}	
	var value=obj.value;
	if(check_zjhm("A",value)==0){
		obj.focus();
		return false;
	}	
	return true;
}

//只能输入英文
function chk_core_letter(obj,tip){
	var value=obj.value;
	var reg=new RegExp("[^A-Za-z]");
	if(reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"输入的值中含有非法字符！");
		return false;
	}
	return true;
}
//只能输入数字
function chk_core_num(obj){
	var value=obj.value;
	var reg=new RegExp("[^0-9]");
	if(reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"输入的值中含有非法字符！");
		return false;
	}
	return true;
}

//验证最多输入输入支持位小数
function chk_core_numdot(obj,tip,dotlen){
	var value=obj.value;
	var reg ;
	if(dotlen==1){
		reg = /^-?\d+(\.\d{1,1})?$/ ;
	}else if(dotlen==2){
		reg = /^-?\d+(\.\d{1,2})?$/ ;
	}else if(dotlen==3){
		reg = /^-?\d+(\.\d{1,3})?$/ ;
	}	
	if(!reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"最多支持"+dotlen+"位小数！");
		return false;
	}
	return true;	
}


function chk_core_letternum(obj,tip){
	var value=obj.value;
	var reg=new RegExp("[^0-9A-Za-z]");
	if(reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"输入的值中含有非法字符！");
		return false;
	}
	return true;
}

//检验固定电话（不包括区号）
//obj验证对象，len当地电话位数，tip提示标题
//正确返回true，错误返回false
//type 1区号2无区号 未启用
//可以有区号或有分机
function check_gddh(obj,tip,len,type){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var format="";
	var value=obj.value;
	if(len==8){
		var partten = /^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{7}(-(\d{3,}))*$/;
		format="[区号-8位号码-分机]，其中区号、分机可选,8位号码不能1开头";
	}else if(len==7){
		var partten = /^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6}(-(\d{3,}))*$/;
		format="[区号-7位号码-分机]，其中区号、分机可选,7位号码不能1开头";
	}else{
		var partten = /^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6,7}(-(\d{3,}))*$/;
		format="[区号-7位号码-分机]，其中区号、分机可选,(6,7)位号码不能1开头";
	}	
	
	
    if(!partten.test(value)){
		obj.focus();
		displayInfoHtml("["+tip+"]输入的固定电话格式不正确,正确为"+format);
		return false;
	}else{
		if(len==8){
			var partten=/^(1{8})|(2{8})|(3{8})|(4{8})|(5{8})|(6{8})|(7{8})|(8{8})|(9{8})|(0{8})$/;
			formate="例如8个同样数字!"
		}else if(len==7){
			var partten=/^(1{7})|(2{7})|(3{7})|(4{7})|(5{7,8})|(6{7})|(7{7})|(8{7})|(9{7})|(0{7})$/;
			formate="例如7个同样数字!"
		}else{
			var partten=/^(1{7,8})|(2{7,8})|(3{7,8})|(4{7,8})|(5{7,8})|(6{7,8})|(7{7,8})|(8{7,8})|(9{7,8})|(0{7,8})$/;
			formate="例如(7,8)个同样数字!"
		}	
		
		if(partten.test(value)){
			obj.focus();
			displayInfoHtml("["+tip+"],请不要随意输入特定电话,"+formate);
			return false;
		}	
	}	
	return true;			
}

//检验移动电话（不包括区号）
//obj验证对象，len当地电话位数，tip提示标题
//正确返回true，错误返回false
//type 1区号2无区号 未启用
//包括小灵通，但不能有分机号
function check_yddh(obj,tip,len){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var format="";
	if(len==8){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{7}$)/;
		format="11位手机号或8位小灵通，其中小灵通格式[区号-7位号码]，区号可选,8位号码不能1开头";
	}else if(len==7){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6}$)/;
		format="11位手机号或7位小灵通，其中小灵通格式[区号-7位号码]，区号可选,7位号码不能1开头";
	}else{
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6,7}$)/;
		format="11位手机号或7位小灵通，其中小灵通格式[区号-(7,8)位号码]，区号可选,(7,8)位号码不能1开头";
	}	
	var value=obj.value;

	if(!partten.test(value)){
		obj.focus();
		displayInfoHtml("["+tip+"]格式不正确,正确为"+format);
		return false;
	}else{
		if(len==8){
			var partten=/^(1{8})|(2{8})|(3{8})|(4{8})|(5{8})|(6{8})|(7{8})|(8{8})|(9{8})|(0{8})$/;
			formate="例如8个同样数字!"
		}else if(len==7){
			var partten=/^(1{7})|(2{7})|(3{7})|(4{7})|(5{7,8})|(6{7})|(7{7})|(8{7})|(9{7})|(0{7})$/;
			formate="例如7个同样数字!"
		}else{
			var partten=/^(1{7,8})|(2{7,8})|(3{7,8})|(4{7,8})|(5{7,8})|(6{7,8})|(7{7,8})|(8{7,8})|(9{7,8})|(0{7,8})$/;
			formate="例如(7,8)个同样数字!"
		}	
		
		if(partten.test(value)){
			obj.focus();
			displayInfoHtml("["+tip+"],请不要随意输入特定电话,"+formate);
			return false;
		}	
	}	

	return true;
}

//检验联系电话，可以为手机或者带区号的固定电话或者小灵通
function check_lxdh(obj,tip,len){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var format="";
	if(len==8){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{7}$)/;
		format="11位手机号或8位固定电话，其中固定电话[区号-7位号码]，区号可选,8位号码不能1开头";
	}else if(len==7){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6}$)/;
		format="11位手机号或7位固定电话，其中固定电话[区号-7位号码]，区号可选,7位号码不能1开头";
	}else{
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6,7}$)/;
		format="11位手机号或7位固定电话，其中固定电话[区号-(7,8)位号码]，区号可选,(7,8)位号码不能1开头";
	}	
	var value=obj.value;

	if(!partten.test(value)){
		obj.focus();
		displayInfoHtml("["+tip+"]格式不正确,正确为"+format);
		return false;
	}else{
		if(len==8){
			var partten=/^(1{8})|(2{8})|(3{8})|(4{8})|(5{8})|(6{8})|(7{8})|(8{8})|(9{8})|(0{8})$/;
			formate="例如8个同样数字!"
		}else if(len==7){
			var partten=/^(1{7})|(2{7})|(3{7})|(4{7})|(5{7,8})|(6{7})|(7{7})|(8{7})|(9{7})|(0{7})$/;
			formate="例如7个同样数字!"
		}else{
			var partten=/^(1{7,8})|(2{7,8})|(3{7,8})|(4{7,8})|(5{7,8})|(6{7,8})|(7{7,8})|(8{7,8})|(9{7,8})|(0{7,8})$/;
			formate="例如(7,8)个同样数字!"
		}	
		
		if(partten.test(value)){
			obj.focus();
			displayInfoHtml("["+tip+"],请不要随意输入特定电话,"+formate);
			return false;
		}	
	}	

	return true;
}

//验证最大值和最小值
function chk_core_maxmin(obj,tip,maxvalue,minvalue){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(value>maxvalue||value<minvalue){
		displayInfoHtml(tip+"应大于"+minvalue+",小于"+maxvalue);
		obj.focus();
		return false;
	}	
	return true;
}
//检验该对象最大值
function chk_core_max(obj,tip,maxvalue){
	
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(value>maxvalue){
		displayInfoHtml(tip+"数据最大值应小于"+maxvalue);
		obj.focus();
		return false;
	}	
	return true;
}


function na_chk_core_max(obj,tip,maxvalue){
	
	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if (value==null || value.trim()==""){
		result=result+"不能为空";
		temp=false;
	}
	
	if(value>maxvalue){
		result=result+"数据最大值应小于"+maxvalue;
		temp=false;
	}	
	
	if(temp==false){
		return result;
	}else{	
		return true;
	}
}



//检验最大值
//type3大队4中队
//maxvalue1大队最大值，maxvalue2中队最大值，
function chk_core_max12(obj,tip,type,maxvalue1,maxvalue2){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(type==3){
		if(value>maxvalue1){
			displayInfoHtml(tip+"大队采集数据不合理，最大值为"+maxvalue1);
			obj.focus();
			return false;
		}	
	}else if(type==4){
		if(value>maxvalue2){
			displayInfoHtml(tip+"中队采集数据不合理，最大值为"+maxvalue2);
			obj.focus();
			return false;
		}		
	}
	
	return true;
}


//检验最大值
//type3大队4中队
//maxvalue1大队最大值，maxvalue2中队最大值，
function chk_core_max12onfocus(obj,tip,type,maxvalue1,maxvalue2){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(type==3){
		if(value>maxvalue1){
			displayInfoHtml(tip+"大队采集数据不合理！");
			
			return false;
		}	
	}else if(type==4){
		if(value>maxvalue2){
			displayInfoHtml(tip+"中队采集数据不合理！");
	
			return false;
		}		
	}
	
	return true;
}

//检验最大值
//type3大队4中队
//maxvalue1大队最大值，maxvalue2中队最大值，
function na_chk_core_max12(obj,tip,type,maxvalue1,maxvalue2){
	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(!na_chk_core_notnull(obj,tip)){
		return false;
	}

	if(type==3){
		if(value>maxvalue1){
			result=result+"大队采集数据不合理";
		    temp=false;

		}	
	}else if(type==4){
		if(value>maxvalue2){
          result=result+"中队采集数据不合理";
		    temp=false;
		}		
	}	
	if(temp==false){
		return result;
	}else{	
		return true;
	}
}
//检验日期
//正确返回true，错误返回false
function chk_core_date(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(isDate(tip,value)==0){
		obj.focus();
		return false;
	}	
	return true;
}

//检验时间
//正确返回true，错误返回false
function chk_core_datetime(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(isDateTime(tip,value)==0){
		obj.focus();
		return false;
	}	
	return true;
}

/***********以下是原子验证*****************/



// 校验传入的文本框文本是否为数值类型
function checkNumber(obj,vMc){
  var v;
  v=obj.value;
  v=v.trim();
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
    return false;
  }
  obj.value=v;
  return true;
}



function na_checkNumber(obj,vMc){
  var v;
  v=obj.value;
  v=v.trim();
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    return vMc+"输入的值中含有非法字符'"+s+"'请检查！";
  }
  obj.value=v;
  return true;
}

function checkNumberDot(obj,vMc){
  var v;
  v=obj.value;
  v=v.trim();
  re=new RegExp("[^0-9.]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
    return false;
  }
  obj.value=v;
  return true;
}


function checkNumberDotAndLength(obj,vMc,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length!=vLength){
    displayInfoHtml("输入的"+vMc+"的长度不是"+vLength+"位！");
    return false;
  }
  re=new RegExp("[^0-9.]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
    return false;
  }
  obj.value=v;
  return true;
}

function na_checkNumberDotAndLength(obj,vMc,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length!=vLength){
    return "输入的"+vMc+"的长度不是"+vLength+"位！";
  }
  re=new RegExp("[^0-9.]");
  if(s=v.match(re)){
    return vMc+"输入的值中含有非法字符'"+s+"'请检查！";
  }
  obj.value=v;
  return true;
}


//检验身份证明号码
//检验年龄条件
//正确返回true，错误返回false
function check_sfzm(obj){
    if(!chk_core_notnull(obj,tip)||!chk_core_sfzmhm(obj)){
    	return false;
	}
    return true;
}



//检测汉字至少多少个
//obj验证对象，minlen至少多少位，tip提示标题
function check_hanzi_minlen(obj,tip,minlen){
	var value=obj.value.trim();
    if(!chk_core_notnull(obj,tip)||!chk_core_hanziall(obj,tip)
    		||!chk_core_minlen(obj,tip,minlen)){
    	return false;
	}
	return true;
}

//检测汉字至少录入minlen,至多录入maxlen
function check_hanzi_maxmin(obj,tip,maxlen,minlen){
	var value=obj.value.trim();
    if(!chk_core_notnull(obj,tip)||!chk_core_hanziall(obj,tip)
    		||!chk_core_minlen(obj,tip,minlen)
    		||!chk_core_maxlen(obj,tip,maxlen)){
    	return false;
	}
	return true;
}

//检查事故编号是否符合要求
//检查长度为16位，全部为数字
function check_acdNo(obj,i_field){

}


function checkzero_null(i_field){
  var i_value=i_field.value;
 	if (i_value==null || jstrim(i_value)==0){
    	return 0;
 	}
 	return 1;
}

//检测是否为0
function checkzero(i_field,i_name){
  var i_value=i_field.value;
 	if (i_value==null || jstrim(i_value)==0){
     	displayInfoHtml(i_name+"不可为零！");
     	i_field.focus();
    	return 0;
 	}
 	return 1;
}


//检测是否为0
function na_checkzero(obj,tip){

	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(value==""){
		
	   result=result+"该栏输入不可为零";
		temp=false;
		
	}
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}

//检测是否为0
function na_checknotzero(obj,tip){

	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(jstrim(value)!=0){
		
	   result=result+"该栏输入应为零";
		temp=false;
		
	}
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}



//不提示，只返回
function na_notNull(i_field,i_value){
	var result=1;
 	if (i_value==null || i_value.trim()==""){
    	result=i_field+"不可为空！";
    	return result;
 	}
 	return 1;
}

// -------------------------------
// 函数名：DateAddMonth(strDate,iMonths)
// 功能介绍：获得日期加上iMonths月数后的日期
// 参数说明：strDate 日期
// 返回值 ：无返回值
// -------------------------------
function check_zjhmNoAge(zjmc, zjhm) {

    var birthday = "";
    var zjhm1 = "";
    var zjhm2 = "";

    var s = "";
    if (zjmc == "A") { // 身份证号码
        if (!(zjhm.length == 15 || zjhm.length == 18)) {
            displayInfoHtml("身份证长度不对,请检查！");
            return 0;
        }
        zjhm1 = zjhm;
        if (zjhm.length == 18) {
            zjhm1 = zjhm.substr(0, 17);
            zjhm2 = zjhm.substr(17, 1);
        }

        re = new RegExp("[^0-9]");
        if (s = zjhm1.match(re)) {
            displayInfoHtml("输入的值中含有非法字符'" + s + "'请检查！");
            return 0;
        }
        // 取出生日期
        if (zjhm.length == 15) {
            birthday = "19" + zjhm.substr(6, 6);
        } else {
            re = new RegExp("[^0-9X]");
            if (s = zjhm2.match(re)) { // 18位身份证对末位要求数字或字符
                displayInfoHtml("输入的值中含有非法字符'" + s + "'请检查！");
                return 0;
            }
            birthday = zjhm.substr(6, 8);
        }
        birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +
                   birthday.substr(6, 2)
                   // displayInfoHtml("birthday"+birthday)

                   if (isDate("证件号码", birthday) == 0) { // 检查日期的合法性
            return 0;
        }

        if (zjhm.length == 18) {
            return (sfzCheck(zjhm)); // 对18位长的身份证进行末位校验
        }
    } else {
        if (zjhm.length > 17) {

            displayInfoHtml("非‘居民身份证’长度不得超过17位,请检查！");
            return 0;
        }
    }

    return 1;
}

// -------------------------------
// 函数名：check_zjhm(zjmc,obj)
// 参数说明：证件名称，证件号码。
// 功能介绍：检查身份证号码合法性。
// 对身份证检查是否为全数字；出生日期格式是否正确；是否<=18,<=70；校验码检查
// 返回值 ：1-是，0-不
// -------------------------------
function check_zjhm(zjmc, zjhm) {

    var birthday = "";
    var zjhm1 = "";
    var zjhm2 = "";

    var s = "";
    if (notNull("证件号码", zjhm) == 0) {
        return 0;
    }
    if (zjmc == "A") { // 身份证号码
        if (!(zjhm.length == 15 || zjhm.length == 18)) {
            displayInfoHtml("身份证长度不对,请检查！");
            return 0;
        }
        zjhm1 = zjhm;
        if (zjhm.length == 18) {
            zjhm1 = zjhm.substr(0, 17);
            zjhm2 = zjhm.substr(17, 1);
        }

        re = new RegExp("[^0-9]");
        if (s = zjhm1.match(re)) {
            displayInfoHtml("输入的值中含有非法字符'" + s + "'请检查！");
            return 0;
        }
        // 取出生日期
        if (zjhm.length == 15) {
            birthday = "19" + zjhm.substr(6, 6);
        } else {
            re = new RegExp("[^0-9X]");
            if (s = zjhm2.match(re)) { // 18位身份证对末位要求数字或字符
                displayInfoHtml("输入的值中含有非法字符'" + s + "'请检查！");
                return 0;
            }
            birthday = zjhm.substr(6, 8);
        }

        birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +
                   birthday.substr(6, 2)
                   // displayInfoHtml("birthday"+birthday)

                   if (isDate("证件号码", birthday) == 0) { // 检查日期的合法性
            return 0;
        }
        var nl = cal_years(birthday); // 求年龄

        if (nl-0<18 || nl>70){
        // if (nl - 0 < 18) {
            displayInfoHtml("年龄要求 18岁以上 70岁以下，当前 " + nl + " ！");
            return 0;
        }
        if (zjhm.length == 18) {
            return (sfzCheck(zjhm)); // 对18位长的身份证进行末位校验
        }
    } else {
        if (zjhm.length > 17) {

            displayInfoHtml("非‘居民身份证’长度不得超过17位,请检查！");
            return 0;
        }
    }

    return 1;
}

// -------------------------------
// 函数名：isLength(i_field,i_length,i_value)
// 功能介绍：检查输入值是否为指定长度
// 参数说明：数据项，要求长度，值
// 返回值 ：1-是指定长度,0-不是
// -------------------------------
function isLength(i_field, i_length, i_value) { // displayInfoHtml("---长度要求:"+i_length+"
												// "+i_value.length);
    if (!(i_value.length == i_length)) {
        displayInfoHtml("'" + i_field + "' 的长度要求为' " + i_length + " '！");
        return 0;
    }
    return 1;
}


// 判断日期时间是否合法 zhoujn 20060323
function isDateTime(i_field,thedate){
  if(thedate.length==0||thedate==null){return 0;}
  if(thedate.length==16){
    thedate=thedate+":00";
  }
  if (!(thedate.length==19)){
     displayInfoHtml(i_field+"格式不对,请输入正确的格式：yyyy-mm-dd hh24:mi！");
     return 0;
  }
  dateRegexp = new RegExp("^(\\d{4})[-](\\d{2})[-](\\d{2})[ ](\\d{2})[:](\\d{2})[:](\\d{2})$");
  var matched = dateRegexp.exec(thedate);
  if(matched != null) {
    if (!isTrueDateTime(matched[3], matched[2], matched[1],matched[4], matched[5], matched[6])){
      displayInfoHtml(i_field+"格式不对，请输入正确的格式：yyyy-mm-dd hh24:mi！");
      return 0;
    } else {
      return 1;
    }
  }
  return 0;
}

//内部函数
function isTrueDateTime(day, month, year,hour,min,second) {
  if (month < 1 || month > 12) {
    return false;
  }
  if (day < 1 || day > 31) {
    return false;
  }
  if ((month == 4 || month == 6 || month == 9 || month == 11) && (day == 31)) {
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


// -------------------------------
// 函数名：isNum(i_field,i_value)
// 功能介绍：检查输入字符串是否为数字
// 参数说明：数据项，输入的对应值
// 返回值 ：1-是数字,0-非数字
// -------------------------------
function isNum(i_field, i_value) {
    if (notNull(i_field, i_value) == 0) {
        return 0;
    }

    re = new RegExp("[^0-9]");
    var s;
    if (s = i_value.match(re)) {
        displayInfoHtml("'" + i_field + "' 中含有非法字符 '" + s + "' ！");
        return 0;
    }
    return 1;
}
// //////////////
/*
 * 检测输入号牌号码是否正确 号牌号码如：A12345 入参：obj (文本框或其它对象) 返回false:不正确，true:正确 xuxd 20060313
 */
function checkHphm(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("号牌号码不能为空！");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		if (i_valueLen!=6){
			displayInfoHtml("号牌号码长度不对！");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(5,1);
			if(s_tmp=="挂"){    // 允许输入挂车
				s_tmp=s_value.substr(0,5);
			}else{
				s_tmp=s_value;
			}
			// 检测是否有特殊符号
			re=new RegExp("[^0-9A-Za-z]");
			if(s=s_tmp.match(re)){
				displayInfoHtml("输入的值中含有非法字符'"+s+"'请检查！");
				obj.focus();
        return false;
      }
		}
	}
	obj.value=s_value;
	return true;
}

// //////////////
/*
 * 检测输入的完整号牌号码是否正确 号牌号码如：川A12345 入参：obj (文本框或其它对象) 返回false:不正确，true:正确 yangzm 20100823
 */
function checkFullHphm(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("号牌号码不能为空！");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		if (i_valueLen!=7){
			displayInfoHtml("号牌号码长度不对！");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(6,1);
			if(s_tmp=="挂" || s_tmp=="警"){    // 允许输入挂车、警车
				s_tmp=s_value.substr(1,6);
			}else{
				s_tmp=s_value.substr(1,7);
			}
			// 检测是否有特殊符号
			re=new RegExp("[^0-9A-Za-z]");
			if(s=s_tmp.match(re)){
				displayInfoHtml("输入的值中含有非法字符'"+s+"'请检查！");
				obj.focus();
                return false;
            }
            s_tmp = s_value.substr(0,1);
            re2=new RegExp("[\u4e00-\u9fa5]");
            if(s=s_tmp.match(re2)){
				displayInfoHtml("输入的值中含有非法字符'"+s+"'请检查！");
				obj.focus();
                return false;
            }
		}
	}
	obj.value=s_value;
	return true;
}
//检测号牌号码
function checkHphmNull(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
  if(s_value!=""){
		i_valueLen=s_value.length;
		if (i_valueLen!=6){
			displayInfoHtml("号牌号码长度不对！");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(5,1);
			if(s_tmp=="挂"){    // 允许输入挂车
				s_tmp=s_value.substr(0,5);
			}else{
				s_tmp=s_value;
			}
			// 检测是否有特殊符号
			re=new RegExp("[^0-9A-Za-z]");
			if(s=s_tmp.match(re)){
				displayInfoHtml("输入的值中含有非法字符'"+s+"'请检查！");
				obj.focus();
        return false;
      }
		}
  }else{
    return true;
  }
	obj.value=s_value;
	return true;
}

/**
 * 检测输入号牌号码为警车,巡逻执勤车号 如: E1234警
 */
function checkJcHphm(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("号牌号码不能为空！");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		if (i_valueLen!=6){
			displayInfoHtml("号牌号码长度不对！应为6位！");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(5,1);
			if(s_tmp=="挂"){    // 允许输入挂车
				s_tmp=s_value.substr(0,5);
			}else{
				s_tmp=s_value;
			}
		}
	}
	obj.value=s_value;
	return true;
}
// that为对应的text框,保证只允许数字输入
function  fCheckInputNumber(that){
	var keycode = event.keyCode ;
	var realkey = String.fromCharCode(event.keyCode) ;
	var thatValue=that.value;
  keycode=keycode*1;
    // 缺省均为event.returnValue=true;
	if ((keycode>=45)&&(keycode<=57)){
		if(thatValue==''){
      if(realkey=='.')
        event.returnValue = false;
    } else if(thatValue=='0'){
      if(realkey!='.')
        event.returnValue = false;
    }	else if(thatValue.indexOf('.')>=0){
      if(realkey=='.'){
        event.returnValue = false;
      }else{
        var len=thatValue.indexOf(".");
        var str=thatValue.substring(len,thatValue.length);
      }
		}else{
      event.returnValue = true;
		}
	}else{
    event.returnValue =false;
	}
}

//限制只能石输入数字
function CheckInputLengthAndNumber(that,vLength){
	var keycode = event.keyCode ;
	var realkey = String.fromCharCode(event.keyCode) ;
	var thatValue=that.value;
	keycode=keycode*1;

  // 缺省均为event.returnValue=true;
	if ((keycode>=45)&&(keycode<=57)){
	    if (thatValue.length>vLength){
	    	event.returnValue = false;
	    }else{
	    	event.returnValue = true;
	    }
	}else{
		event.returnValue =false;
	}
}

// ////////////////////////////
// 校验传入的文本框文本是否为数值类型长度是否符合要求
function checkNumberAndLength(obj,vMc,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length!=vLength){
    displayInfoHtml("输入的"+vMc+"的长度不是"+vLength+"位！");
    return false;
  }
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
    return false;
  }
  obj.value=v;
  return true;
}

// 校验传入的文本框文本是否为字母或数字，类型长度是否符合要求
function checkCharAndLength(obj,vMc,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length!=vLength){
    displayInfoHtml("输入的"+vMc+"的长度不是"+vLength+"位！");
    return false;
  }
  re=new RegExp("[^0-9A-Za-z]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
    return false;
  }
  obj.value=v;
  return true;
}

// 校验传入的文本框文本是否为字母或数组类型
function checkChar(obj,vMc){
  var v;
  v=obj.value;
  v=v.trim();
  re=new RegExp("[^0-9A-Za-z]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
    return false;
  }
  obj.value=v;
  return true;
}





// 检测传入文本值是否已超出长度
function checkMaxLength(obj,vMc,vL){
  var v ;
  v=obj.value;
  v=v.trim();
  if(v.length>vL){
    displayInfoHtml(vMc+"输入值已经超过指定长度！");
    return false;
  }
  obj.value=v;
  return true;
}

// 校验传入的文本框文本是否为空
function checkNull(obj,vMc){
	var v;
	v=obj.value;
	v=v.trim();
	
	if (v.length==0)
	{
		displayInfoHtml(vMc+"输入值为空！");
		return false;
	}
	obj.value=v;
	return true;
}

// 校验传入的单选框是否为空
function checkRadioNull(obj,vMc){
	var value;
	if(obj.length==undefined){
		var length=obj.length
		for(var i=0;i<length;i++){
			if(obj.item(i).checked){
			  value=obj.item(i).value
			}
		}
	}else{
		displayInfoHtml(vMc+"未选择！");
		return false;
	}
	if (value==undefined)
	{
		displayInfoHtml(vMc+"未选择！");
		return false;
	}
	return true;
}

// 检测输入项中是否存在特殊字符
function checkSpecilCharAndNull(obj,vMc){
  var s_tmp,s;

  s_tmp=obj.value;
  s_tmp=s_tmp.trim();

  if (s_tmp.length==0){
    displayInfoHtml(vMc+"输入值为空！");
    return false;
  }

  // 检测是否有特殊符号
  re=new RegExp("[^0-9A-Za-z]");
  if(s=s_tmp.match(re)){
    displayInfoHtml("输入的"+vMc+"值中含有非法字符'"+s+"'请检查！");
    obj.focus();
    return false;
  }
  obj.value=s_tmp;
  return true;
}


// 检测输入的ip地址是否正确
function checkIp(obj,vMc){
  var s_tmp,s;
  var iValue;
  s_tmp=obj.value;
  s_tmp=s_tmp.trim();

  if (s_tmp.length==0){
    displayInfoHtml(vMc+"输入值为空！");
    return false;
  }

  // 检测是否有特殊符号
  re=new RegExp("[^0-9]");
  if(s=s_tmp.match(re)){
    displayInfoHtml("输入的"+vMc+"值中含有非法字符'"+s+"'请检查！");
    obj.focus();
    return false;
  }

  iValue=parseInt(obj.value);
  if (iValue>255){
    displayInfoHtml("输入的"+vMc+"值"+iValue+"大于255，请检查！");
    obj.focus();
    return false;
  }
  obj.value=s_tmp;
  return true;
}

// 确认本text框只能输入数字 onKeypress="fCheckInputInt()"
// zhoujn 20060323
function fCheckInputInt(){
  if (event.keyCode < 48 || event.keyCode > 57)
    event.returnValue = false;
}
function fCheckInputIntAlert(){
  if (event.keyCode < 48 || event.keyCode > 57){
  displayInfoHtml("请输入数字！");
  event.returnValue = false;
  }
}


// -------------------------------
// 函数名：sfzCheck(hm)
// 功能介绍：对18位长的身份证进行末位校验
// 参数说明：身份证号码
// 返回值 ：1-符合,0-不符合
// -------------------------------
//内部函数
function sfzCheck(hm){
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
				displayInfoHtml("身份证校验错误！------ 末位应该:"+ls_check+" 实际:"+hm.substr(17,1));
				return 0;
  }
	return 1
}
    

/*
 * 检测输入号牌号码是否正确 包括 警车，挂车，教练车等 入参：obj :号牌号码,objzl:号牌种类 返回false:不正确，true:正确
 */
function checkHphm2(obj,objzl){
	var s_value=obj.value.trim();
	var zl_value=objzl.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("号牌号码不能为空！");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		// 挂车，教练车,警车
		if(zl_value=="03" || zl_value=="04" || zl_value=="15" || zl_value=="16" || zl_value=="17" || zl_value=="18" || zl_value=="19" || zl_value=="23" || zl_value=="24"){
      if (i_valueLen!=5){
        displayInfoHtml("号牌号码长度不对,应为5位！");
        obj.focus();
        return false
      }else{
        s_tmp=s_value;
        // 检测是否有特殊符号
        re=new RegExp("[^0-9A-Za-z]");
        if(s=s_tmp.match(re)){
          displayInfoHtml("输入的值中含有非法字符'"+s+"'请检查！");
          obj.focus();
          return false;
        }
      }
		}else{
      if (i_valueLen!=6){
        displayInfoHtml("号牌号码长度不对，应为6位！");
        obj.focus();
        return false
      }else{
        s_tmp=s_value;
        // 检测是否有特殊符号
        re=new RegExp("[^0-9A-Za-z]");
        if(s=s_tmp.match(re)){
          displayInfoHtml("输入的值中含有非法字符'"+s+"'请检查！");
          obj.focus();
          return false;
        }
      }
    }
	}
	obj.value=s_value;
	return true;
}




// by:shijianrong 20090420
// 校验传入的时间小时文本框文本是否为数值类型长度是否符合要求,并且提示为小时
function checkHourNumberAndLength(vMc,obj,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length ==1){
    v='0'+v;
  }
  if (v.length!=vLength){
    displayInfoHtml("输入的"+vMc+"的长度不是"+vLength+"位！ ");
    obj.focus();
    return false;
  }
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入含有非法字符 '"+s+"'请检查！ 例如: 上午9点则输入: 09 ");
    obj.focus();
    return false;
  }
  if(v>=24){
    displayInfoHtml("注意:小时应该在00-23范围内! 例如: 上午9点则输入: 09 ");
    obj.focus();
    return false;
  }
  obj.value=v;
  return true;
}



function invalidLetter(m_fields,obj,strLetter){
  var m_Value=obj.value;
  var i, c;
  for (i = 0; i < m_Value.length; i++) {
    c = m_Value.charAt(i); // 字符串s中的字符
    if (strLetter.indexOf(c) == -1) {
// obj.style.backgroundColor = errorColor;
      displayInfoHtml(m_fields + "含有非法字符‘" + c + "’!");
      obj.focus();
      return false;
    }
  }
// obj.style.backgroundColor = okColor;
  return true;
}



// -------------------------------
// 函数名：dyLength(i_field,i_length,i_value)
// 功能介绍：检查输入值是否达到指定长度以上
// 参数说明：数据项，要求长度，值
// 返回值 ：1-符合,0-不是
// -------------------------------
function dyLength(i_field,i_length,obj)
{ // displayInfoHtml("---长度要求:"+i_length+" "+i_value.length);
  obj.value=obj.value.trim();
  var i_value=obj.value;
  if (i_value.length<i_length)
  {
// obj.style.backgroundColor = errorColor;
    displayInfoHtml("'"+i_field+"' 的长度至少为 '"+i_length+"'！");
    obj.focus();
    return 0;
  }
// obj.style.backgroundColor = okColor;
  return 1;
}

// by:shijianrong 20090420
// 校验传入的时间分钟文本框文本是否为数值类型长度是否符合要求,并且提示为分钟
function checkMinuteNumberAndLength(vMc,obj,vLength){
  var v;

  v=obj.value;
  v=v.trim();
  if (v.length ==1){
    v='0'+v;
  }
  if (v.length!=vLength){
    displayInfoHtml("输入的"+vMc+"的长度不是"+vLength+"位！ ");
    obj.focus();
    return false;
  }
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"输入含有非法字符 '"+s+"'请检查！ 例如: 上午9点15分则输入: 15 ");
    obj.focus();
    return false;
  }
  if(v>=60){
    displayInfoHtml("注意:分钟应该在00-59范围内! 例如: 上午9点15分则输入: 15 ");
    obj.focus();
    return false;
  }
  obj.value=v;
  return true;
}


//-------------------------------
//函 数 名：cal_years(rq)
//功能介绍：计算指定日期到今天是多少年.
//参数说明：日期
//返 回：年数
//-------------------------------
//内部函数
function cal_years(rq){
	var years;
	var dDate = new Date();  
	// 系统日期(系统日期应该大于rq)
	// var dDate=todaystr;
	var month1= dDate.getMonth()+1;
	var year1= dDate.getFullYear();
	var day1=dDate.getDate()
	var year2= rq.substr(0,4);
	var month2= rq.substr(5,2);
	var day2=rq.substr(8,2);
	//displayInfoHtml("-----------dDate-"+dDate+" year1"+year1+" month1"+month1+"
	//day1"+day1);
	years = year1 - year2 - 0;
	if (month2 > month1)   // 月份未到，years-1
	{
	  years = years - 1;
	}
	else
	{
	  if ( (month1 == month2) && (day2 > day1))  // 月份到了，但日未到，years-1
	  {
	    years = years - 1;
	  }
	}
	// displayInfoHtml("-----------years-"+years);
	return years;
}
//校验车身颜色
function checkCsys(obj,tip)
{
  if(obj.value=="")
	  return false;
  var arr=new Array("白","灰","黄","粉","红","紫","绿","蓝","棕","黑");
  if(obj.value.length==1)
  {
	  for(i=0;i<10;i++)
	  {
	     if(obj.value==arr[i])
	     {
	    	 return true;
	     }
	  }
	  displayInfoHtml(tip+"代码错误！");
	  return false;
  }else{
	  var arrys=obj.value.split("/");
	  for(a=0;a<arrys.length;a++)
	  {
	     var temp="";
		  for(i=0;i<10;i++)
		  {
			  if(arrys[a]==arr[i])
			     {
				     temp="1";
			    	 break;
			     }
			  
		  }
		  if(temp=="")
		  {
              displayInfoHtml(tip+"代码错误！");
			  return false;
		  }

	  }
  }
  return true;
}

// 判断数字是否符合精度
function isFloat(obj,tip, iInt, iFloat) {
	var inum=obj.value;
	if (inum != "") {
		if (isNaN(inum)) {
			displayInfoHtml(tip + "不是数字！");
			obj.focus();
			return false;
		} else {
			var arr = (inum + "").split(".");
			if (arr.length >= 2) {
				if (arr[0].length > iInt) {
					displayInfoHtml(tip + "整数位数最长只能为" + iInt + "位！");
					obj.focus();
					return false;
				}
				if (arr[1].length > iFloat) {
					displayInfoHtml(tip + "小数位数最长只能为" + iFloat + "位！");
					obj.focus();
					return false;
				}
			} else if (arr.length >= 1) {
				if (arr[0].length > iInt) {
					displayInfoHtml(tip + "整数位数最长只能为" + iInt + "位！");
					obj.focus();
					return false;
				}
			}
		}
	}
	return true;
}
