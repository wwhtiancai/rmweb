//二维数组转字符串
//字符串和数组的处理
var delimiter1 = "$$";
var delimiter2 = "~~";
//arr二维数组 len需要转换的个数
function arr2tostring(arr,len){
  var str = "";
  for(i=0;i<len;i++){
    str = str + arr[i].join(delimiter1) + delimiter2;
//alert(str);
}
  str = str.substring(0,str.length - 2);
  return str;
}
//字符串转二维数组 str需要转的字符串 desArr目标数组
function stringtoarr2(str,desArr){
  var arr = str.split(delimiter2);
  for(i=0;i<arr.length;i++){
    desArr[i] = arr[i].split(delimiter1);
  }
}
//取身份证号的年龄
function getsfznl(sfzh){
  var today = new Date();
  var iYear1,iMonth1,iDay1;
  var iYear,iMonth,iDay;
  var iNl;
  iYear1 = today.getYear();
  iMonth1 = today.getMonth();
  iDay1 = today.getDate();
  if(sfzh.length==15){
    iYear = parseInt("19" + sfzh.substr(6,2));
    iMonth = parseInt(sfzh.substr(8,2));
    iDay = parseInt(sfzh.substr(10,2));
  }else{
    iYear = parseInt(sfzh.substr(6,4));
    iMonth = parseInt(sfzh.substr(10,2));
    iDay = parseInt(sfzh.substr(12,2));
  }
  iNl = iYear1 - iYear;
  if(iNl>0&&iNl<200){
    return iNl;
  }else{
    return "";
  }
/*  if((iMonth1<iMonth)||(iMonth1==iMonth&&iDay1<iDay)){
    iNl = iNl - 1;
  }*/
}
//取身份证号的年龄
function getsfznl1(sfzh,sgfssj){
  var today = new Date();
  var iYear1,iMonth1,iDay1;
  var iYear,iMonth,iDay;
  var iNl;
  iYear1 = parseInt(sgfssj.substring(0,4),10);
  iMonth1 = parseInt(sgfssj.substring(5,7),10);
  iDay1 = parseInt(sgfssj.substring(8,10),10);
  if(sfzh.length==15){
    iYear = parseInt("19" + sfzh.substr(6,2),10);
    iMonth = parseInt(sfzh.substr(8,2),10);
    iDay = parseInt(sfzh.substr(10,2),10);
  }else{
    iYear = parseInt(sfzh.substr(6,4),10);
    iMonth = parseInt(sfzh.substr(10,2),10);
    iDay = parseInt(sfzh.substr(12,2),10);
  }
  iNl = iYear1 - iYear;
  if((iMonth>iMonth1)||((iMonth==iMonth1)&&(iDay>=iDay1))){
  	iNl = iNl - 1;
  }
  if(iNl>0&&iNl<200){
    return iNl;
  }else{
    return "";
  }
/*  if((iMonth1<iMonth)||(iMonth1==iMonth&&iDay1<iDay)){
    iNl = iNl - 1;
  }*/
}
//根据身份证明号码获取出生日期
//author zhuwd 20060810
function getcsrqFromSfzmhm(sfzh){
var csrq = '';
  if(sfzh.length==15){
  csrq = '19' + sfzh.substr(6,2) + sfzh.substr(8,2) + sfzh.substr(10,2);
  }else{
   if(sfzh.substr(6,4)>="1800"&&sfzh.substr(6,4)<="3000")
     csrq = sfzh.substr(6,4) + sfzh.substr(10,2) + sfzh.substr(12,2);
   else
     csrq = "";
  }
  return csrq;
}

//取身份整号的性别
function getsfzxb(sfzh){
  if(sfzh.length==15){
    s = sfzh.substr(14,1);
    if(parseInt(s)%2==1){
      return "1";
    }else{
      return "2";
    }
  }else{
    s = sfzh.substr(16,1);
    if(parseInt(s)%2==1){
      return "1";
    }else{
      return "2";
    }
  }
}

