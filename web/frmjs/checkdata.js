var checklen = 0;
var checkfields = new Array(100);
var FRM_CHECK_NULL = 1;    //单个函数 _checknull或者checknull
var FRM_CHECK_NUMBER = 2;  //单个函数 _checknum或者checknum
var FRM_CHECK_DATE = 3;  //_checkdate或者checkdate
var FRM_CHECK_SFZMHM = 4;  //_checkallsfzmhm或者checkallsfzmhm
var FRM_CHECK_LENGTH = 5;  //_checklength或者checklength
var FRM_CHECK_DATETIME = 6; //_checkdate1或者checkdate1
var FRM_CHECK_LESS_DATETIME = 7;  
var FRM_CHECK_LARGE_DATETIME = 8;
var FRM_CHECK_HPHM = 9; //_checkHphm或者checkHphm
var FRM_CHECK_SPECIAL_CHAR = 10; //_checkSpecilCharAndNull 包含英文字母和数字 或者checkSpecilCharAndNull
var FRM_CHECK_ZJCX = 11; //_check_zjcx 或者check_zjcx
var FRM_CHECK_ZJHM = 12; //证件号码检测 otherinfo是证件名称信息 A代表身份证  //_check_zjhm 或者check_zjhm
var FRM_CHECK_JSZZXBH = 13;//驾驶证证芯编号 //_zxbhCheck 或者zxbhCheck
var FRM_CHECK_IPDZ = 14; //IP地址校验   //_checkIp 或者 checkIp
var FRM_CHECK_MINLENGTH = 15;  //_checkminlength或者checkminlength
var FRM_CHECK_EMAIL = 16; //检查电子邮箱 _checkemail或者checkmail
var FRM_CHECK_SJHM = 17; //检查手机号码 _checksjhm或者checksjhm
var FRM_CHECK_LXDH = 18;//检查联系电话 _checklxdh或者checklxdh
var FRM_CHECK_IPDZ_ALL = 19; //全IP地址校验   //_checkIpAll 或者 checkIpAll
var FRM_CHECK_SFDM = 20; //省份代码校验   //_checkSfdm 或者 checkSfdm
var FRM_CHECK_CHINESE = 21; //检测必须为中文 checkChinese
var FRM_CHECK_CHINESE_MIN = 22; //必须大于等于多少的中文字符 //_checkChinese_min 或者 checkChinese_min
var FRM_CHECK_YZBM = 23;//检测邮政编码是否规范check_yzbm 或者_check_yzbm
var FRM_CHECK_HPHM_VIO =24;
//-------------------------------
//  字段校验CheckObj(fieldname,fieldname_cn,checktype,canfocus)
//  fieldname:Form字段
//  fieldname_cn:Form字段中文名称
//  checktype: 校验类型 NULL_CHECK-非空校验 NUM_CHECK-数字校验 3-日期校验 4-身份证号校验 5-长度校验 6-日期时间校验 
//					   7-小于他时间比较 8-大于其他时间 9-检测号牌号码  10-非空且不允许有特异字符
//					11-车辆类型校验
//  canfocus: 错误后是否允许聚焦
//-------------------------------
function CheckObj(fieldname,fieldname_cn,checktype,otherinfo,canfocus){
	this._fieldname = fieldname;
	this._fieldname_cn = fieldname_cn;
	this._checktype = checktype;
	this._otherinfo = otherinfo;
	this._canfocus = canfocus;	
	this._result = 0;
	this._resultdesc = "";
	this._errorinfo = "";
	this._disabledfield = ""; //控制是否失效字段
	this._disabledvalue = ""; //控制是否失效字段值
	this._enabledfield = ""; //控制是否有效字段	
	this._enabledvalue = ""; //控制是否有效字段值	
	this._otherfield = "";//其他比较字段
	this._formname = "";//指定Formname
}
function movTsdmToFirst(s_value){   
  var iPos = s_value.indexOf("[");
  var iPos1 = s_value.indexOf("]:");
  var s_result = s_value;
  if(iPos>=0&&iPos1>=0&&iPos1>iPos){
  	s_result = s_result.substring(iPos,iPos1 + 2) + s_result.substring(0,iPos) + s_result.substr(iPos1 + 2); 
  }
  return s_result;
}
//-------------------------------
//  函数名  ：check_zjcx(s_value，s_cx_dm)
//  参数说明：准驾车型字符串，合法的准驾车行字符串数组。
//  功能介绍：检查车型输入是否正确，只检查是否包含合法的准驾车行字符串，重复、次序颠倒不认为是错误
//  返回值  ：1-包含合法的准驾车行，0-不合法
//
//  戴嘉 2004.05.12
//-------------------------------
function _check_zjcx(s_value)
{
	  //合法的准驾车行字符串数组
	var s_cx_dm=new Array("A1","A2","A3","B1","B2","C1","C2","C3","C4","C5","D","E","F","M","N","P");
	 //字符串数组的长度
	var s_cx_input;//存放需要检验的字符串
	var i_pos;//查找子串定位
	var i;
    s_cx_input=s_value;
    if(s_cx_input=="无"){
    	return "1";
    }
	for(i in s_cx_dm)//对合法准驾车行字符串数组轮循
	{
		do
		{
		
		i_pos=s_cx_input.indexOf(s_cx_dm[i]);//是否包含当前车型
		if(i_pos!=-1)
		//包含
		{
		                          //去掉找到的子串
		
		s_cx_input=s_cx_input.slice(0,i_pos)+s_cx_input.slice(i_pos+s_cx_dm[i].length);
		}
		}while(i_pos!=-1);//找不到当前车型子串，进入下一车型子串查找
	}
	
	if(s_cx_input.length==0)//输入字符串包含的都是合法的车型子串（全部被去掉了）
	return "1";
	else//输入字符串还包含有非法的字符串
	return "0";
}
//-------------------------------
//  函数名：check_yzbm(i_value)
//  参数说明：邮政编码值。
//  功能介绍：检查邮政编码是否是6位长数字。
//  返回值  ：1-是，false-不是
//-------------------------------
function check_yzbm(obj,mc,canfocus)
{

	var result = _check_yzbm(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
function _check_yzbm(value)
{
	var result = _checklength(value,6);
	if(result!="1"){
		return result;
	}
	result = _checknum(value);
	if(result!="1"){
		return result;
	}
	return "1";
}
//至少输入minlen个汉字
function _checkChinese_min(value,minlen){
	var reg   =   /^w[\u4E00-\u9FA5]+/g;   
	if (reg.test(value)){
		return "必须包括中文！";
	}else{
		var len=value.replace(/[^\u4e00-\u9fa5]/gi,"").length;
		if(len<minlen){
			return "至少包含"+minlen+"个汉字！";
		}	
	}	
	return "1";;
}

//至少输入minlen个汉字
function checkChinese_min(obj,mc,minlen,canfocus){
	var result = _checkChinese_min(obj.value,minlen);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}

function check_zjcx(obj,mc,canfocus){
	var result = _check_zjcx(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
//检测号牌号码
function _checkHphm(value1){
	if((value1!="无")&&(value1.length>0)){
		if(value1.length<5){
			return "[00G0102J1]:输入不正确！";
		}
		if(value1.length>10){
			return  "[00G0102J1]:输入不正确！";
		}
		
		re=new RegExp("[^0-9A-Za-z]");
					 	
	 	if(s=value1.substring(1,value1.length-1).match(re))
	    {
	    	return "[00G0102J2]:输入的值中含有非法字符'"+s+"'请检查！";
        }
       return "1";
	}else
		return "1";
}

//检测号牌号码
function _checkHphm_vio(value1){
	if((value1!="无")&&(value1.length>0)){
		if(value1.length<5){
			return "[00G0102J1]:输入不正确！";
		}
		if(value1.length>10){
			return  "[00G0102J1]:输入不正确！";
		}
       return "1";
	}else
		return "1";
}

function checkHphm(obj,mc,canfocus){
	var result = _checkHphm(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
function _checkdate(value1)
{
	if(!(value1==null||value1.length==0)){
	    var oldValue = value1; 
	    if (value1.length==8){
		  	oldValue=value1.substr(0,4)+"-"+value1.substr(4,2)+"-"+value1.substr(6,2);
		}	
		var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
		var r = oldValue.match(reg);
		if (r!=null){
			var d= new Date(r[1],r[3]-1,r[4]);
			var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
			var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
			if (newStr!=newDate){
				return "[00G0042J1]:日期格式错误,\n正确格式为yyyy-mm-dd"
			}else{
				return "1";
			}
		}else{
			return "[00G0042J1]:日期格式错误,\n正确格式为yyyy-mm-dd"
		}
	}
	return "1";
}

function checkdate(obj,mc,canfocus){
	var result = _checkdate(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}else{
		if(obj.value.length==8){
			obj.value= obj.value.substr(0,4)+"-"+obj.value.substr(4,2)+"-"+obj.value.substr(6,2);		
		}
	}
	return result;
}

function _isTrueDateTime(day, month, year,hour,min,second) {
      if (month < 1 || month > 12) {
          return false;
          }
      if (day < 1 || day > 31) {
          return false;
          }
      if ((month == 4 || month == 6 || month == 9 || month == 11) &&
          (day == 31)) {
          return false;
          }
      if (month == 2) {
          var leap = (year % 4 == 0 &&
         (year % 100 != 0 || year % 400 == 0));
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
function _checkdate1(value1){
	 if(value1.length==16)
	 {
	   value1=value1+":00";
	 }
	 if (!(value1.length==19))
	 {    
	   	return "[00G0072J1]:格式不对,请输入正确的格式：yyyy-mm-dd hh24:mi！";
	 }
	 dateRegexp = new RegExp("^(\\d{4})[-](\\d{2})[-](\\d{2})[ ](\\d{2})[:](\\d{2})[:](\\d{2})$");
	  var matched = dateRegexp.exec(value1);
	  if(matched != null) {
		  if (!_isTrueDateTime(matched[3], matched[2], matched[1],matched[4], matched[5], matched[6]))
		  {
		    return "[00G0072J1]:格式不对，请输入正确的格式：yyyy-mm-dd hh24:mi！";
		  }
		  return "1";
	  }else{
		return "1";	  
	  }
}
function checkdate1(obj,mc,canfocus){
	var result = _checkdate1(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
function _checklength(value1,iLength){
	if(value1.length==0){return "1"}
	if(value1.length!=iLength){
		return "[00G0062J1]:长度必须为" + iLength + "位！";
	}else{
		return "1";			
	}
}
function checklength(obj,iLength,mc,canfocus){
	var result = _checklength(obj.value,iLength);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
function _checkminlength(value1,iLength){
	if(value1.length==0){return "1"}
	if(value1.length<iLength){
		return "[00G0162J1]:长度最少为" + iLength + "位！";
	}else{
		return "1";			
	}
}
function checkminlength(obj,iLength,mc,canfocus){
	var result = _checkminlength(obj.value,iLength);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
//检测Email地址的合法性
function _checkEmail(value1){
    if(value1.length>0){
		if((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value1))==false){   			
			return "[00G0172J1]:Email地址输入有误！"; 
		}else{
			return "1";
		}
    }else{
    	return "1";
    }
}
function checkEmail(obj,mc,canfocus){
	var result = _checkEmail(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;	
}
//检测省份代码
function _checkSfdm(value1){
    var result = "1";
    if(value1.length>0){
    	if("11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65".indexOf(value1)<0){
    		return "[00G00202J1]:无效省份代码";
    	}
    	return "1";
    }else{
    	return result;
    }
}
function checkSfdm(obj,mc,canfocus){
	var result = _checkSfdm(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;	
}
function _checklxdh(value1,hmcd){
    var result = "1";
    var i = 0;
    if(value1.length>0){
        if(value1.substring(0,1)=="0"&&value1.indexOf("-")>0){
        	return result;
        }
		var iPos = value1.indexOf("-");
		var iLength = 0 ;
		while(iPos >=0){
			iLength = iLength + 1;
			iPos = value1.indexOf("-",iPos + 1);
		}
		if(iLength==0){			
			if(_checknum(value1)!="1"){
				return "必须为数字";
			}
			if(hmcd==undefined){
				if(value1.length<7){
					return "至少7位";
				}			
			}else{
				if(value1.length!=hmcd){
					return "长度必须为" + hmcd;
				}
			}
			if(value1.substring(0,1)=="1"){
				return "第1位不能为'1'"
			}
		}else if(iLength==1){
			var zjhm = value1.substring(0,iPos);
			var fjhm = value1.substr(iPos + 1);
			if(_checknum(zjhm)!="1"){
				return "必须为数字";
			}
			if(hmcd==undefined){
				if(zjhm.length<7){
					return "至少7位";
				}			
			}else{
				if(zjhm.length!=hmcd){
					return "长度必须为" + hmcd;
				}
			}
			if(zjhm.substring(0,1)=="1"){
				return "第1位不能为'1'"
			}			
			if(_checknum(fjhm)!="1"){
				return "分机号必须为数字";
			}
		}else{
			return "格式不正确"
		}
		return "1";
    }else{
    	return result;
    }
}
function checklxdh(obj,mc,canfocus,hmcd){
	var result = _checklxdh(obj.value,hmcd);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;	
}

//检测全IP地址
function _checkIpAll(value1){
    var result = "1";
    var i = 0;
    if(value1.length>0){
		var iparr = value1.split(".");
		if(iparr.length!=4){
			return "[00G0202J1]:格式不正确";
		}else{
			for(i=0;i<iparr.length;i++){
				result = _checkIp(iparr[i]);
				if(result!="1"){
					return result;					
				}
			}
		}
    	return "1";
    }else{
    	return result;
    }
}

//检测中文
function _checkChinese(value1){
    var result = "1";
    var i = 0;
    if(value1.length>0){
    	for(var i=0;i<value1.length;i++){
			if(value1.substring(i,i+1).charCodeAt(0)<=255 && value1.substring(i,i+1) != '_' ){
				return "[00G00222J1]:必须为中文！";
			}
		}
    	return "1";
    }else{
    	return result;
    }
}

//检测中文
function checkChinese(obj,mc,canfocus){
	var result = _checkChinese(obj.value);
	if(result!="1"){
		displayInfoHtml("[00G0022J1]:" + mc + "必须为中文!");
		if(canfocus) obj.focus();		
	}
	return result;
}


function checkIpAll(obj,mc,canfocus){
	var result = _checkIpAll(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;	
}
//检测手机号码的合法性
function _checksjhm(value1){
    var result = "1";
    if(value1.length>0){
    	result = _checknum(value1);
    	if(result!="1"){
    		return result;
    	}
    	if(value1.length<7||value1.length>12){
    		result = "[00G0182J1]:7位到12位";
    		return result;
    	}
    	return "1";
    }else{
    	return result;
    }
}
function checksjhm(obj,mc,canfocus){
	var result = _checksjhm(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;	
}
function _checkallsfzmhm(value1){
	if(value1.length!=0){
		if (!(value1.length == 15 || value1.length == 18)){
		      return "[00G0052J1]:身份证长度不对,请检查！";
		}
	    zjhm1 = value1;
	    if (value1.length == 18) {
	      zjhm1 = value1.substr(0, 17);
	      zjhm2 = value1.substr(17, 1);
	    }			
	    re = new RegExp("[^0-9]");
	    if (s = zjhm1.match(re)){
	      return  "[00G0052J2]:输入的值中含有非法字符'" + s + "'请检查！";
	    }
	    if (value1.length == 15) {
	      birthday = "19" + value1.substr(6, 6);
	    }else{
	      re = new RegExp("[^0-9A-Z]");
	      if (s = zjhm2.match(re)) { 
	        //18位身份证对末位要求数字或字符
	        return "[00G0052J2]:输入的值中含有非法字符'" + s + "'请检查！";
	      }
	      birthday = value1.substr(6, 8);
	    }
	    birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +  birthday.substr(6, 2);
	    result = _checkdate(birthday);	
	    if ( result!="1") { //检查日期的合法性
	      return result;			    
	    }			    	
	    if(value1.length==18){
    				result = _checksfzmhm(value1);
    				if(result!="1"){
	      	  return result;			          				
    				}
	    }						
	}
	return "1";
}
function displayZhxx(mc,result){
	var iPos = result.indexOf("]:");
	alert(mc + result);
	alert(result.substring(0,iPos+2) + mc + result.substr(iPos+2));
	if(iPos>0){
		alert(result);
		displayInfoHtml(result);
	}else{
		displayInfoHtml(mc+result);
	}
}
function checkallsfzmhm(obj,mc,canfocus){
    obj.value = obj.value.toUpperCase();
	var result = _checkallsfzmhm(obj.value);
	if(result!="1"){
		displayZhxx(mc,result);
		if(canfocus) obj.focus();		
	}else{
		obj.value = obj.value.toUpperCase();
	}
	return result;
}
//检测输入项中是否存在特殊字符
function _checkSpecilCharAndNull(tmpvalues){
	var s_tmp,s;
	
	s_tmp=tmpvalues;
	s_tmp=s_tmp.trim();
	//检测是否有特殊符号
	re=new RegExp("[^0-9A-Za-z]");
	if(s=s_tmp.match(re))
	{
	    return "[00G0112J1]:输入的值中含有非法字符'"+s+"'请检查！";
	}
	return "1";
}
function checkSpecilCharAndNull(obj,mc,canfocus){
	var result = _checkSpecilCharAndNull(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
function _checksfzmhm(value1){
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

     for (ll_i=0;ll_i<=16;ll_i++)
     {   //alert("ll_i:"+ll_i+" "+hm.substr(ll_i,1)+"w[ll_i]:"+w[ll_i]+"  ll_sum:"+ll_sum);
        ll_sum=ll_sum+(value1.substr(ll_i,1)-0)*w[ll_i];

     }
     ll_sum=ll_sum % 11;


     switch (ll_sum)
      {
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
      if (value1.substr(17,1) != ls_check)
      {
            return "[00G0132J4]:身份证校验错误！------ 末位应该:"+ls_check+" 实际:"+value1.substr(17,1);
      }
	  return "1"
}
function _isNum(fieldname,value1){
   re=new RegExp("[^0-9]");
   var s;
   if(s=value1.match(re)){
        return "[00G0032J1]:" + fieldname + "输入的值中含有非法字符'"+s+"'请检查！";
   }else{
   		return "1";
   }	
}
function _checknum(value1){
   re=new RegExp("[^0-9]");
   var s;
   if(s=value1.match(re)){
        return "[00G0032J1]:输入的值中含有非法字符'"+s+"'请检查！";
   }else{
   		return "1";
   }	
}
function checknum(obj,mc,canfocus){
	var result = _checknum(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}

// 判断年度类型是否合法 yngzm 20100823
function check_year(obj,tip){
  var value=obj.value.trim();
  if (value!=null && value.trim()!=""){ 
     if (!(value.length==4)){
	    alert(tip+"长度不对,要求为yyyy！");
	    return false;
     }
     var reg=new RegExp("[1-9][0-9]{3}");
 // var reg = /^(\d{1,4})$/;
     if(!reg.test(value)){
		alert(tip+"格式不对,要求为yyyy！");
		return false;
	 }
  }
  return true;
}

// 判断年度季度类型是否合法 yngzm 20100824
function check_yearseason(obj,tip){
  var value=obj.value;
  if (value!=null && value.trim()!=""){
     if (!(value.length==5)){
	     alert(tip+"长度不对,要求为yyyys！");
	     return false;
     }
     var reg=new RegExp("[1-9][0-9]{3}[1-4]");
     if(!reg.test(value)){
		alert(tip+"格式不对,要求为yyyys！");
		return false;
	 }
  }
  return true;
}

function _checknull(value1){
	value1 = value1.trim();
	if (value1.length==0)
	{
		return "[00G0022J1]:不能为空！";			
	}else{
		return "1";
	}
}
function checknull(obj,mc,canfocus){
	var result = _checknull(obj.value);
	if(result!="1"){
		displayInfoHtml("[00G0022J1]:" + mc + "不能为空!");
		if(canfocus) obj.focus();		
	}
	return result;
}
CheckObj.prototype.checkdata = function() {
    var value1;
    var k;
    if((this._checktype==FRM_CHECK_ZJCX)||(this._checktype==FRM_CHECK_SFZMHM)||(this._checktype==FRM_CHECK_ZJHM)){
	    if(this._formname==""){
		    document.all[this._fieldname].value = document.all[this._fieldname].value.toUpperCase();
	    }else{
	    	document.all[this._formname].all[this._fieldname].value = document.all[this._formname].all[this._fieldname].value.toUpperCase();
	    }
    }
    if(this._formname==""){
	    //document.all[this._fieldname].value = document.all[this._fieldname].value.trim();
    	value1 = document.all[this._fieldname].value;
    }else{
    	//document.all[this._formname].all[this._fieldname].value = document.all[this._formname].all[this._fieldname].value.trim();
    	value1 = document.all[this._formname].all[this._fieldname].value;
    }
    var result;
    if(this._disabledfield!=""){
//        alert(this._disabledfield);
//        alert(document.all[this._disabledfield].value);
//        alert(this._disabledvalue);
		var arr = this._disabledvalue.split(",");
		for(k=0;k<arr.length;k++){
	    	if((document.all[this._disabledfield].value + "") == arr[k]){
	    		this._result = 1;
	    		return;    	
	    	}		
		}
    }
    if(this._enabledfield!=""){
//    	alert(this._enabledfield);
//    	alert(this._enabledvalue);    
//    	alert(document.all[this._enabledfield].value);	
    	if(document.all[this._enabledfield].value != this._enabledvalue){
    		this._result = 1;
    		return;    	
    	}    
    }
    if(this._otherfield!=""){
    	if(this._formname==""){
	    	this._otherinfo = document.all[this._otherfield].value;    	
    	}else{
	    	this._otherinfo = document.all[this._formname].all[this._otherfield].value;    	
    	}
    }
    
	switch(this._checktype){
		case FRM_CHECK_NULL:
			//非空校验
			result = _checknull(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;		
				else
					this._resultdesc = this._errorinfo;						
			}						
			break;
		case FRM_CHECK_NUMBER:
			//数字
			result = _checknum(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;		
				else
					this._resultdesc = this._errorinfo;						
			}
			break;
		case FRM_CHECK_DATE:
			//日期校验
			result = _checkdate(value1);		
			if(result=="1"){	
				if(value1.length==8){
					if(this._formname==""){
					    document.all[this._fieldname].value = value1.substr(0,4)+"-"+value1.substr(4,2)+"-"+value1.substr(6,2);
				    }else{
				    	document.all[this._formname].all[this._fieldname].value = value1.substr(0,4)+"-"+value1.substr(4,2)+"-"+value1.substr(6,2)
				    }					
				}
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_SFZMHM:
			//身份证号校验
			result = _checkallsfzmhm(value1);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_LENGTH:
			//长度校验
			if(typeof(this._otherinfo)=="object"){
				result = _checklength(value1,this._otherinfo.value);
			}else{
				result = _checklength(value1,this._otherinfo);
			}						
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_DATETIME:
		   //日期时间校验
			result = _checkdate1(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;	
			}	
			break;	  
		case FRM_CHECK_LESS_DATETIME:
		   //小于等于目标值
		   if(value1==""){
		   		this._result = 1;
		   		break;
		   }
		   if(typeof(this._otherinfo)=="object"){
		   		if(this._otherinfo.value.length==0){
		   			this._result = 1;
		   		}else{
					if(value1<=this._otherinfo.value){
						this._result = 1;
					}else{
						this._result = 0;
					}		   		   		
		   		}
		   }else{
		   		if(this._otherinfo.length==0){
		   			this._result = 1;
		   		}else{
					if(value1<=this._otherinfo){
						this._result = 1;
					}else{
						this._result = 0;
					}		   		   		   		
		   		}
		   }
			if(this._result==0){
				if(this._errorinfo==""){
					if(typeof(this._otherinfo)=="object"){
						this._resultdesc = "[00G0082J1]:" + this._fieldname_cn + "必须小于等于" + this._otherinfo.value + "！";
					}else{
						this._resultdesc = "[00G0082J1]:" + this._fieldname_cn + "必须小于等于" + this._otherinfo + "！";					
					}
				}
				else
					this._resultdesc = this._errorinfo;	
			}	
			break;	
		case FRM_CHECK_LARGE_DATETIME:
		   //大于目标值
		   if(value1==""){
		   		this._result = 1;
		   		break;
		   }		   
		   if(typeof(this._otherinfo)=="object"){
				if(this._otherinfo.value.length==0){
		   			this._result = 1;
		   		}else{
				   if(value1>this._otherinfo.value){
						this._result = 1;
					}else{
						this._result = 0;
					}		   		
		   		}		   
		   }else{
		   		if(this._otherinfo.length==0){
		   			this._result = 1;
		   		}else{
				   if(value1>this._otherinfo){
						this._result = 1;
					}else{
						this._result = 0;
					}		   		   		
		   		}
		   }
			if(this._result==0){
				if(this._errorinfo==""){
					if(typeof(this._otherinfo)=="object"){
						this._resultdesc = "[00G0092J1]:" + this._fieldname_cn + "必须大于" + this._otherinfo.value + "！";
					}else{
						this._resultdesc = "[00G0092J1]:" + this._fieldname_cn + "必须大于" + this._otherinfo + "！";					
					}
				}
				else
					this._resultdesc = this._errorinfo;	
			}	
			break;		
		case FRM_CHECK_HPHM:
			result = _checkHphm(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_HPHM_VIO:
			result = _checkHphm_vio(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_SPECIAL_CHAR:
			result = _checkSpecilCharAndNull(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;	
				else
					this._resultdesc = this._errorinfo;								
			}
			break;
		case FRM_CHECK_ZJCX:
			result = _check_zjcx(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				this._resultdesc = "[00G0122J1]:" + this._fieldname_cn + "不合法!";
			}
			break;
		case FRM_CHECK_ZJHM:
			if(typeof(this._otherinfo)=="object"){
				result = _check_zjhm(this._otherinfo.value,value1);			
			}else{
				result = _check_zjhm(this._otherinfo,value1);			
			}			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;	
				else
					this._resultdesc = this._errorinfo;								
			}
			break;
		case FRM_CHECK_JSZZXBH:
			result = _zxbhCheck(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;	
				else
					this._resultdesc = this._errorinfo;								
			}
			break;
		case FRM_CHECK_IPDZ:
			result = _checkIp(value1);
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;	
				else
					this._resultdesc = this._errorinfo;								
			}
			break;		
		case FRM_CHECK_MINLENGTH:
			result = _checkminlength(value1,this._otherinfo);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;								
		case FRM_CHECK_EMAIL:
			result = _checkEmail(value1);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_SJHM:
			result = _checksjhm(value1);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;		
		case FRM_CHECK_LXDH:
		    if(value1.length>0){
		    	if(value1.substring(0,1)=="0"&&value1.indexOf("-")>0){
		    		result = "1";		    
		    	}else{
					if(typeof(this._otherinfo)=="object"){
						result = _checklength(value1,this._otherinfo.value);
					}else{
						result = _checklength(value1,this._otherinfo);
					}	
				}
			}else{
				result = "1";
			}
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;				
		case FRM_CHECK_IPDZ_ALL:
			result = _checkIpAll(value1);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;		
		case FRM_CHECK_CHINESE:
			result = _checkChinese(value1);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
		case FRM_CHECK_CHINESE_MIN:
			result = _checkChinese_min(value1,this._otherinfo);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;			
		case FRM_CHECK_YZBM:
			result = _check_yzbm(value1);			
			if(result=="1"){
				this._result = 1;
			}else{
				this._result = 0;
				if(this._errorinfo=="")
					this._resultdesc = this._fieldname_cn + result;			
				else
					this._resultdesc = this._errorinfo;					
			}
			break;
	}
	this._resultdesc = movTsdmToFirst(this._resultdesc);
}

	//-------------------------------
	//  检查所有字段checkallfields(arr,flag)
	//  arr需要检查的字段列表
	//  flag遇到错误是否直接退出标记 1-是
	//  checklength 检查数组的长度	
	//-------------------------------
	function checkallfields(arr,flag,checklength){
	    var obj;
	    var errfield;
	    var strerrfield;
	    var result;
	    var len;
	    result = "";
	    
	    if(checklength!=undefined){
	    	len = checklength;
	    }else{
	    	len = checklen;
	    }
		for(i = 0;i<len;i++){
			obj = arr[i];	
			
		//	alert(obj._fieldname + obj._checktype + obj._result + obj._resultdesc);		
			obj.checkdata();
			
			
			if(obj._result!=1){
				if(obj._canfocus==1&&errfield==undefined){
					if(obj._formname!=""){
						errfield = document.all[obj._formname].all[obj._fieldname];
						strerrfield = "document.all[\""+obj._formname+"\"].all[\""+obj._fieldname+"\"]";
					}else{
						errfield = document.all[obj._fieldname];
						strerrfield = "document.all[\""+obj._fieldname+"\"]";
					}					
				}		
				result = result + obj._resultdesc + "\n";
				if(flag==1){
					break;
				}	
			}
		}
		if(result!=""){
			if(errfield!=undefined){
				displayInfoHtml(result,strerrfield+".focus();");
			}else{
				displayInfoHtml(result);
			}
			return 0;
		}else{
			return 1;
		}
	}

	function checkhaschar(str,substr,vmc){
		if(str.indexOf(substr)>=0){
			alert("输入的”"+vmc+"“中包含非法字符"+substr);
			return 0;
		}
		return 1;
	}

	//判断要处于时间段内objdate需要判断的目标日期，startdate起始时间点，lendays日期跨度（可能为负）
	function checkInDays(objdatestr,startdatestr,lendays){
		if(objdatestr.length>10)
			objdatestr=objdatestr.substr(0,10);
		if(startdatestr.length>10)
			startdatestr=startdatestr.substr(0,10);
		if(_checkdate(objdatestr)!="1"||_checkdate(startdatestr)!="1"){
			return false;
		}
		var objdate=new Date(Date.parse(objdatestr.replace(/-/g,"/")));
		var startdate,enddate;
		if(lendays>0)
		{
			enddate=new Date(Date.parse(startdatestr.replace(/-/g,"/")));
			startdate=new Date(Date.parse(startdatestr.replace(/-/g,"/")));
			startdate=startdate.setDate(startdate.getDate()-lendays);
		}else{
			if(lendays==0){
				startdate=new Date(Date.parse(startdatestr.replace(/-/g,"/")));
				enddate=new Date(Date.parse(startdatestr.replace(/-/g,"/")));
			}else{
				startdate=new Date(Date.parse(startdatestr.replace(/-/g,"/")));
				enddate=new Date(Date.parse(startdatestr.replace(/-/g,"/")));
				enddate=enddate.setDate(enddate.getDate()-lendays);
			}
		}
		if(objdate<startdate||objdate>enddate){
			return false;
		}else{
			return true;
		}
	}
	
	
//-------------------------------
//  函数名：check_zjhm(zjmc,obj)
//  参数说明：证件名称，证件号码。
//  功能介绍：检查身份证号码合法性。
//	      对身份证检查是否为全数字；出生日期格式是否正确；是否<=18,<=70；校验码检查
//  返回值  ：1-是，0-不
//-------------------------------
function _check_zjhm(zjmc,zjhm)
{

  var birthday="";
  var zjhm1="";
  var zjhm2="";

  var s="";
  var result="";
  if(zjhm.length==0){
	  return "1";
  }
  if(zjmc=="A")   //身份证号码
   {
       if(!(zjhm.length==15 || zjhm.length==18) )
	     {
	       	   return "[00G0132J1]:身份证长度不对,请检查！";
	     }
        zjhm1=zjhm;
        if (zjhm.length==18)
            {
                zjhm1=zjhm.substr(0,17)	;
                zjhm2=zjhm.substr(17,1);
            }

         re=new RegExp("[^0-9]");
	 if(s=zjhm1.match(re))
	    {
	        return  "[00G0132J2]:输入的值中含有非法字符'"+s+"'请检查！";
        }
        //取出生日期
        if(zjhm.length==15 )
            {
               birthday="19"+zjhm.substr(6,6);
            }
         else
            {
            	re=new RegExp("[^0-9X]");
               if(s=zjhm2.match(re))     //18位身份证对末位要求数字或字符
               {
                   return "输入的值中含有非法字符'"+s+"'请检查！";
                }
                birthday=zjhm.substr(6,8);
            }
           birthday=birthday.substr(0,4)+"-"+birthday.substr(4,2)+"-"+birthday.substr(6,2)
          //alert("birthday"+birthday)
		  result = _isDate("证件号码",birthday);
          if(result!="1")  //检查日期的合法性
          {
             return result;
          }
          if(zjhm.length==18 )
          {
          	result = _sfzCheck(zjhm);  //对18位长的身份证进行末位校验
          	if(result!="1"){
          		return result;
          	}
          }
       }
else
	{if (zjhm.length>17){

	       return "非‘居民身份证’长度不得超过17位,请检查！";
	}
	}

   return "1";
   }

function check_zjhm(obj,zjmc,mc,canfocus){
	var result = _check_zjhm(obj.value,zjmc);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}
//-------------------------------
//函数名：sfzCheck(hm)
//功能介绍：对18位长的身份证进行末位校验
//参数说明：身份证号码
//返回值  ：1-符合,0-不符合
//-------------------------------

function _sfzCheck(hm)
{

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

 for (ll_i=0;ll_i<=16;ll_i++)
 {   //alert("ll_i:"+ll_i+" "+hm.substr(ll_i,1)+"w[ll_i]:"+w[ll_i]+"  ll_sum:"+ll_sum);
    ll_sum=ll_sum+(hm.substr(ll_i,1)-0)*w[ll_i];

 }
 ll_sum=ll_sum % 11;


 switch (ll_sum)
  {
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

  if (hm.substr(17,1) != ls_check)
  {
        return "[00G0132J3]:身份证校验错误！------ 末位应该:"+ls_check+" 实际:"+hm.substr(17,1);
 }
 return "1";
}

//-------------------------------
//函数名：isDate(i_field,thedate)
//功能介绍：校验字符串是否为日期格式
//参数说明：数据项，输入的字符串
//返回值  ：0-不是，1--是
//-------------------------------

function _isDate(i_field,thedate)
{
	if (!(thedate.length==8 || thedate.length==10))
	{
  		return "[00G0132J5]:'"+i_field+"'日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！";
	}
	if (thedate.length==8)
	{
		thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
	}

	var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
	var r = thedate.match(reg);
	if (r==null)
	{
	   	return "请输入正确的'"+i_field+"' ！";
	}
	var d= new Date(r[1],r[3]-1,r[4]);
	var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
	var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)

	if (newStr==newDate)
     {
      	return 1;
     }
 	return "[00G0132J5]:'"+i_field+"'日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！"; 
}

//驾驶证证芯编号校验位判断
function _zxbhCheck(hm)
{
  if(hm.length==0) return "1";
  try{
  var w=new Array();
  var ll_sum;
  var ll_i;
  var ls_check;
  w[0]=9;
  w[1]=8;
  w[2]=0;
  w[3]=8;
  w[4]=7;
  w[5]=3;
  w[6]=2;
  w[7]=3;
  w[8]=5;
  w[9]=6;
  w[10]=7;
  w[11]=8;
  w[12]=9;
  ll_sum=0;
  hm = hm.substr(0,2).replace(/4/g,"8") + hm.substr(2,1) + hm.substr(3).replace(/4/g,"8");

  if (hm.length < 13){
   return "[00G0142J1]:驾驶证证芯编号录入有误，请核对！";
  }
  for (ll_i=0;ll_i<=12;ll_i++)
  {
  if (ll_i != 2){
  if (hm.substr(ll_i,1) < "0" || hm.substr(ll_i,1) > "9"){
   return "[00G0142J1]:驾驶证证芯编号录入有误，请核对！";
  }
  ll_sum=ll_sum+(hm.substr(ll_i,1)-0)*w[ll_i];
  }else{
  if ((hm.substr(ll_i,1) < "0" || hm.substr(ll_i,1) > "9") && hm.substr(ll_i,1) != "X"){
  return "[00G0142J1]:驾驶证证芯编号录入有误，请核对！";
  }
  }
  }
  ll_sum=ll_sum % 11;

	if (ll_sum == 10) ls_check = "X";
	else ls_check = ll_sum;
	
	if (hm.substr(2,1) != ls_check)
	{
	 return "[00G0142J1]:驾驶证证芯编号录入有误，请核对！";
	}
	return "1";
	}catch(err){
	  return err.description;
	}
}
function _checkIp(ipdz){
	var s_tmp,s;
	var iValue;
	s_tmp=ipdz;
	s_tmp=s_tmp.trim();	
	if (s_tmp.length==0)
	{
		return "输入值不能为空!";
	}	
	//检测是否有特殊符号
	re=new RegExp("[^0-9]");
	if(s=s_tmp.match(re))
	{
		return "[00G0152J1]:输入值中含有非法字符'"+s+"'请检查！";
	}	
	iValue=parseInt(ipdz);
	if (iValue>255)
	{
		return "[00G0152J2]:输入的值"+iValue+"大于255，请检查！";
	}
	return "1";
}
function checkIp(obj,mc,canfocus){
	var result = _checkIp(obj.value);
	if(result!="1"){
		alert(mc + result);
		if(canfocus) obj.focus();		
	}
	return result;
}