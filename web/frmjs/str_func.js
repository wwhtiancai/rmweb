//��ά����ת�ַ���
//�ַ���������Ĵ���
var delimiter1 = "$$";
var delimiter2 = "~~";
//arr��ά���� len��Ҫת���ĸ���
function arr2tostring(arr,len){
  var str = "";
  for(i=0;i<len;i++){
    str = str + arr[i].join(delimiter1) + delimiter2;
//alert(str);
}
  str = str.substring(0,str.length - 2);
  return str;
}
//�ַ���ת��ά���� str��Ҫת���ַ��� desArrĿ������
function stringtoarr2(str,desArr){
  var arr = str.split(delimiter2);
  for(i=0;i<arr.length;i++){
    desArr[i] = arr[i].split(delimiter1);
  }
}
//ȡ���֤�ŵ�����
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
//ȡ���֤�ŵ�����
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
//�������֤�������ȡ��������
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

//ȡ������ŵ��Ա�
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

