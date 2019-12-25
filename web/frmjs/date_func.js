function isDate(obj){
	var thedate="";
	try{
		thedate=document.all[obj];
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
		riqi(obj);
		return 0;
	}
	alert("日期格式不对,\n要求为yyyy-mm-dd！");
	riqi(obj);
	return 0;
}

//-------------------------------
//  功能介绍：计算两个字符(YYYY-MM-DD)的日期相隔天数
//  参数说明：asStartDate 起始日期;asEndDate 结束日期
//  返回值  ：相隔天数
//-------------------------------
function Date_Compare(asStartDate,asEndDate){
	var miStart = Date.parse(asStartDate.replace(/\-/g, '/'));
	var miEnd   = Date.parse(asEndDate.replace(/\-/g, '/'));
	return (miEnd-miStart)/(1000*24*3600);
}
//-------------------------------
//  功能介绍：计算两个字符(YYYY-MM-DD)的日期相隔年龄，驾龄
//  参数说明：asStartDate 起始日期;asEndDate 结束日期
//  返回值  ：相隔年龄或驾龄
//-------------------------------
function DateDiffNl(asStartDate,asEndDate){
	var miStart = parseInt(asStartDate.substring(0,4),10);
	var miEnd   = parseInt(asEndDate.substring(0,4),10);
	var vNl = miEnd - miStart;
	var vRqStart = asStartDate.substr(5);
	var vRqEnd = asEndDate.substr(5);
	if(vRqEnd>=vRqStart){
		vNl = vNl + 1;
	}
	return vNl;
}
//-------------------------------
//  功能介绍：计算两个字符(YYYY-MM-DD hh24:mi:ss)的时间相隔时间
//  参数说明：kssj 开始时间；jssj 结束时间;
//  返回值  ：秒、分、时、日、周、月、年
//-------------------------------
function datediff(kssj,jssj,interval){
	var begin =kssj.value;
	var end=jssj.value;
	var sDT=new Date(kssj.substr(0,4),kssj.substr(5,2),kssj.substr(8,2),kssj.substr(11,2),kssj.substr(14,2));
	var eDT=new Date(jssj.substr(0,4),jssj.substr(5,2),jssj.substr(8,2),jssj.substr(11,2),jssj.substr(14,2));
    switch (interval) {
    //計算秒差
    case "s":return parseInt((eDT-sDT)/1000);
    //計算分差
    case "n":return parseInt((eDT-sDT)/60000);
    //計算時差
    case "h":return parseInt((eDT-sDT)/3600000);
    //計算日差
    case "d":return parseInt((eDT-sDT)/86400000);
    //計算週差
    case "w":return parseInt((eDT-sDT)/(86400000*7));
    //計算月差
    case "m":return (eDT.getMonth()+1)+((eDT.getFullYear()-sDT.getFullYear())*12)-(sDT.getMonth()+1);
    //計算年差
    case "y":return eDT.getFullYear()-sDT.getFullYear();
    //輸入有誤
    default:return undefined;
  }
}
//-------------------------------
//  功能介绍：获得上个月的最后一天
//  返回值  ：上月最后一天日期
//-------------------------------
function getLastMonthEnd(){
  var strDate=DateAddMonth(getToday(),-1);
  var iYear = parseFloat(strDate.substring(0, 4));
  var iMonth = parseFloat(strDate.substring(5, 7));
  var iDay=getMonthLastDate(iYear,iMonth);
  var iDate=iYear+'-'+iMonth+'-'+iDay;
	return iDate;
}

//-------------------------------
//  功能介绍：获得上个月的第一天
//  返回值  ：上个月第一天日期
//-------------------------------
function getLastMonthBegin(){
  var strDate=DateAddMonth(getToday(),-1);
  var iDate=strDate.substring(0,8)+'01';
	return iDate;
}
//-------------------------------
//  功能介绍：获得日期加上iMonths月数后的日期
//  参数说明：strDate 日期;iMonths 增加月数
//  返回值  ：无返回值
//-------------------------------
function DateAddMonth(strDate, iMonths) {
    var thisYear = parseFloat(strDate.substring(0, 4));
    var thisMonth = parseFloat(strDate.substring(5, 7));
    var thisDate = parseFloat(strDate.substring(8, 10));
    var d = thisYear * 12 + thisMonth + parseFloat(iMonths);

    var newMonth = d % 12;
    if (newMonth == 0) {
        newMonth = 12;
    }
    var newYear = (d - newMonth) / 12;
    var newDate = thisDate;
    var iMonthLastDate = getMonthLastDate(newYear, newMonth)
                         if (newDate > iMonthLastDate) {
        newDate = iMonthLastDate;
    }
    var newDay = "";

    newDay += newYear;
    if (newMonth < 10) {
        newDay += "-" + "0" + newMonth;
    } else {
        newDay += "-" + newMonth;
    }

    if (newDate < 10) {
        newDay += "-" + "0" + newDate;
    } else {
        newDay += "-" + newDate;
    }
    return (newDay); // 返回日期。
}

//-------------------------------
//  功能介绍：获得某年某月的最后一天日期
//  参数说明：iYear 年份;iMonth 月份
//  返回值  ：最后一天日期
//-------------------------------
function getMonthLastDate(iYear,iMonth){
	var dateArray= new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	var days=dateArray[iMonth-1];
	if ((((iYear % 4 == 0) && (iYear % 100 != 0)) || (iYear % 400 == 0)) && iMonth==2){
		days=29;
	}
	return days;
}

//-------------------------------
//  功能介绍：获取当前月份的第一天
//  返回值  ：当月第一天日期
//-------------------------------
function getMonthBegin(){
  var today = new Date();
  var month=today.getMonth() + 1;
  var year=today.getFullYear();

	if(month<10)
     r = year + "-0" + month;
  else
     r = year + "-" + month;

  r = r + "-01";
  return r;
}

//获取当前月份
function getMonth(){
  var today = new Date();
  var month=today.getMonth() + 1;
  var year=today.getFullYear();

  if(month<10)
     r = year + "-0" + month;
  else
     r = year + "-" + month;

  return r;
}


//-------------------------------
//  功能介绍：获取当前日期 格式为YYYY-MM-DD
//  返回值  ：当前日期
//-------------------------------
function getToday(){
  var today = new Date();
  var day=today.getDate();
  var month=today.getMonth() + 1;
  var year=today.getFullYear();
  if(month<10)
     r = year + "-0" + month;
  else
     r = year + "-" + month;
  if(day<10)
     r = r + "-0" + day;
  else
     r = r + "-" + day;
  return r;
}

//-------------------------------
//  功能介绍：获取当前日期加i天后的日期 格式为YYYY-mm-dd
//  参数说明：i 天数
//  返回值  ：i天后日期
//-------------------------------
function getTodayN(i){
  var today = new Date();
  var day=today.getDate();
  day = day + i;
  today.setDate(day);
  var month=today.getMonth() + 1;
  day=today.getDate();
  if(month<10) sMonth="0" + month;
  else sMonth = month;
  if(day<10) sDay="0" + day;
  else sDay = day;
  r = today.getFullYear() + "-" + sMonth + "-" + sDay;
  return r;
}
//-------------------------------
//  功能介绍：获取指定日期加i天后的日期 格式为YYYY-mm-dd
//  参数说明：i 天数
//  返回值  ：i天后日期
//-------------------------------
function getDay(thedate,i){
  var i_flag = 1;
  if(thedate.length<16){
  	thedate = thedate + " 00:00";
  	i_flag = 0;
  }
  if(thedate.length>=16){
    var today = new Date(parseInt(thedate.substring(0,4)),parseInt(thedate.substring(5,7),10)-1,thedate.substring(8,10),thedate.substring(11,13),thedate.substring(14,16));
    var day=today.getDate();
    day = day + i;
    today.setDate(day);
    var month=today.getMonth()+1;
    day=today.getDate();
    if(month<10) sMonth="0" + month;
    else sMonth = month;
    if(day<10) sDay="0" + day;
    else sDay = day;
    hour = today.getHours();
    if(hour<10) strHour = "0" + hour;
    else strHour = hour;
    min = today.getMinutes();
    if(min<10) strMin = "0" + min;
    else strMin = min;
    sec = today.getSeconds();
    if(sec<10) strSec = "0" + sec;
    else strSec = sec;
    if(i_flag==1){
    	r = today.getFullYear() + "-" + sMonth + "-" + sDay + " " + strHour + ":" + strMin;
    }else{
    	r = today.getFullYear() + "-" + sMonth + "-" + sDay;
    }
    return r;
  }
}
//-------------------------------
//  功能介绍：获取当前系统时间 格式为YYYY-mm-dd hh24:mi
//  参数说明：i 天数
//  返回值  ：i天后日期
//-------------------------------
function getNow(i){
  var today = new Date();
  var day=today.getDate();
  day = day + i;
  today.setDate(day);
  var month=today.getMonth() + 1;
  day=today.getDate();
  if(month<10) sMonth="0" + month;
  else sMonth = month;
  if(day<10) sDay="0" + day;
  else sDay = day;
  hour = today.getHours();
  if(hour<10) strHour = "0" + hour;
  else strHour = hour;
  min = today.getMinutes();
  if(min<10) strMin = "0" + min;
  else strMin = min;
  sec = today.getSeconds();
  if(sec<10) strSec = "0" + sec;
  else strSec = sec;
  r = today.getFullYear() + "-" + sMonth + "-" + sDay + " " + strHour + ":" + strMin;
  return r;
}


