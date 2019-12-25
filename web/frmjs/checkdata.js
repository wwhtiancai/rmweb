var checklen = 0;
var checkfields = new Array(100);
var FRM_CHECK_NULL = 1;    //�������� _checknull����checknull
var FRM_CHECK_NUMBER = 2;  //�������� _checknum����checknum
var FRM_CHECK_DATE = 3;  //_checkdate����checkdate
var FRM_CHECK_SFZMHM = 4;  //_checkallsfzmhm����checkallsfzmhm
var FRM_CHECK_LENGTH = 5;  //_checklength����checklength
var FRM_CHECK_DATETIME = 6; //_checkdate1����checkdate1
var FRM_CHECK_LESS_DATETIME = 7;  
var FRM_CHECK_LARGE_DATETIME = 8;
var FRM_CHECK_HPHM = 9; //_checkHphm����checkHphm
var FRM_CHECK_SPECIAL_CHAR = 10; //_checkSpecilCharAndNull ����Ӣ����ĸ������ ����checkSpecilCharAndNull
var FRM_CHECK_ZJCX = 11; //_check_zjcx ����check_zjcx
var FRM_CHECK_ZJHM = 12; //֤�������� otherinfo��֤��������Ϣ A�������֤  //_check_zjhm ����check_zjhm
var FRM_CHECK_JSZZXBH = 13;//��ʻ֤֤о��� //_zxbhCheck ����zxbhCheck
var FRM_CHECK_IPDZ = 14; //IP��ַУ��   //_checkIp ���� checkIp
var FRM_CHECK_MINLENGTH = 15;  //_checkminlength����checkminlength
var FRM_CHECK_EMAIL = 16; //���������� _checkemail����checkmail
var FRM_CHECK_SJHM = 17; //����ֻ����� _checksjhm����checksjhm
var FRM_CHECK_LXDH = 18;//�����ϵ�绰 _checklxdh����checklxdh
var FRM_CHECK_IPDZ_ALL = 19; //ȫIP��ַУ��   //_checkIpAll ���� checkIpAll
var FRM_CHECK_SFDM = 20; //ʡ�ݴ���У��   //_checkSfdm ���� checkSfdm
var FRM_CHECK_CHINESE = 21; //������Ϊ���� checkChinese
var FRM_CHECK_CHINESE_MIN = 22; //������ڵ��ڶ��ٵ������ַ� //_checkChinese_min ���� checkChinese_min
var FRM_CHECK_YZBM = 23;//������������Ƿ�淶check_yzbm ����_check_yzbm
var FRM_CHECK_HPHM_VIO =24;
//-------------------------------
//  �ֶ�У��CheckObj(fieldname,fieldname_cn,checktype,canfocus)
//  fieldname:Form�ֶ�
//  fieldname_cn:Form�ֶ���������
//  checktype: У������ NULL_CHECK-�ǿ�У�� NUM_CHECK-����У�� 3-����У�� 4-���֤��У�� 5-����У�� 6-����ʱ��У�� 
//					   7-С����ʱ��Ƚ� 8-��������ʱ�� 9-�����ƺ���  10-�ǿ��Ҳ������������ַ�
//					11-��������У��
//  canfocus: ������Ƿ�����۽�
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
	this._disabledfield = ""; //�����Ƿ�ʧЧ�ֶ�
	this._disabledvalue = ""; //�����Ƿ�ʧЧ�ֶ�ֵ
	this._enabledfield = ""; //�����Ƿ���Ч�ֶ�	
	this._enabledvalue = ""; //�����Ƿ���Ч�ֶ�ֵ	
	this._otherfield = "";//�����Ƚ��ֶ�
	this._formname = "";//ָ��Formname
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
//  ������  ��check_zjcx(s_value��s_cx_dm)
//  ����˵����׼�ݳ����ַ������Ϸ���׼�ݳ����ַ������顣
//  ���ܽ��ܣ���鳵�������Ƿ���ȷ��ֻ����Ƿ�����Ϸ���׼�ݳ����ַ������ظ�������ߵ�����Ϊ�Ǵ���
//  ����ֵ  ��1-�����Ϸ���׼�ݳ��У�0-���Ϸ�
//
//  ���� 2004.05.12
//-------------------------------
function _check_zjcx(s_value)
{
	  //�Ϸ���׼�ݳ����ַ�������
	var s_cx_dm=new Array("A1","A2","A3","B1","B2","C1","C2","C3","C4","C5","D","E","F","M","N","P");
	 //�ַ�������ĳ���
	var s_cx_input;//�����Ҫ������ַ���
	var i_pos;//�����Ӵ���λ
	var i;
    s_cx_input=s_value;
    if(s_cx_input=="��"){
    	return "1";
    }
	for(i in s_cx_dm)//�ԺϷ�׼�ݳ����ַ���������ѭ
	{
		do
		{
		
		i_pos=s_cx_input.indexOf(s_cx_dm[i]);//�Ƿ������ǰ����
		if(i_pos!=-1)
		//����
		{
		                          //ȥ���ҵ����Ӵ�
		
		s_cx_input=s_cx_input.slice(0,i_pos)+s_cx_input.slice(i_pos+s_cx_dm[i].length);
		}
		}while(i_pos!=-1);//�Ҳ�����ǰ�����Ӵ���������һ�����Ӵ�����
	}
	
	if(s_cx_input.length==0)//�����ַ��������Ķ��ǺϷ��ĳ����Ӵ���ȫ����ȥ���ˣ�
	return "1";
	else//�����ַ����������зǷ����ַ���
	return "0";
}
//-------------------------------
//  ��������check_yzbm(i_value)
//  ����˵������������ֵ��
//  ���ܽ��ܣ�������������Ƿ���6λ�����֡�
//  ����ֵ  ��1-�ǣ�false-����
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
//��������minlen������
function _checkChinese_min(value,minlen){
	var reg   =   /^w[\u4E00-\u9FA5]+/g;   
	if (reg.test(value)){
		return "����������ģ�";
	}else{
		var len=value.replace(/[^\u4e00-\u9fa5]/gi,"").length;
		if(len<minlen){
			return "���ٰ���"+minlen+"�����֣�";
		}	
	}	
	return "1";;
}

//��������minlen������
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
//�����ƺ���
function _checkHphm(value1){
	if((value1!="��")&&(value1.length>0)){
		if(value1.length<5){
			return "[00G0102J1]:���벻��ȷ��";
		}
		if(value1.length>10){
			return  "[00G0102J1]:���벻��ȷ��";
		}
		
		re=new RegExp("[^0-9A-Za-z]");
					 	
	 	if(s=value1.substring(1,value1.length-1).match(re))
	    {
	    	return "[00G0102J2]:�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
        }
       return "1";
	}else
		return "1";
}

//�����ƺ���
function _checkHphm_vio(value1){
	if((value1!="��")&&(value1.length>0)){
		if(value1.length<5){
			return "[00G0102J1]:���벻��ȷ��";
		}
		if(value1.length>10){
			return  "[00G0102J1]:���벻��ȷ��";
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
				return "[00G0042J1]:���ڸ�ʽ����,\n��ȷ��ʽΪyyyy-mm-dd"
			}else{
				return "1";
			}
		}else{
			return "[00G0042J1]:���ڸ�ʽ����,\n��ȷ��ʽΪyyyy-mm-dd"
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
	   	return "[00G0072J1]:��ʽ����,��������ȷ�ĸ�ʽ��yyyy-mm-dd hh24:mi��";
	 }
	 dateRegexp = new RegExp("^(\\d{4})[-](\\d{2})[-](\\d{2})[ ](\\d{2})[:](\\d{2})[:](\\d{2})$");
	  var matched = dateRegexp.exec(value1);
	  if(matched != null) {
		  if (!_isTrueDateTime(matched[3], matched[2], matched[1],matched[4], matched[5], matched[6]))
		  {
		    return "[00G0072J1]:��ʽ���ԣ���������ȷ�ĸ�ʽ��yyyy-mm-dd hh24:mi��";
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
		return "[00G0062J1]:���ȱ���Ϊ" + iLength + "λ��";
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
		return "[00G0162J1]:��������Ϊ" + iLength + "λ��";
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
//���Email��ַ�ĺϷ���
function _checkEmail(value1){
    if(value1.length>0){
		if((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value1))==false){   			
			return "[00G0172J1]:Email��ַ��������"; 
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
//���ʡ�ݴ���
function _checkSfdm(value1){
    var result = "1";
    if(value1.length>0){
    	if("11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65".indexOf(value1)<0){
    		return "[00G00202J1]:��Чʡ�ݴ���";
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
				return "����Ϊ����";
			}
			if(hmcd==undefined){
				if(value1.length<7){
					return "����7λ";
				}			
			}else{
				if(value1.length!=hmcd){
					return "���ȱ���Ϊ" + hmcd;
				}
			}
			if(value1.substring(0,1)=="1"){
				return "��1λ����Ϊ'1'"
			}
		}else if(iLength==1){
			var zjhm = value1.substring(0,iPos);
			var fjhm = value1.substr(iPos + 1);
			if(_checknum(zjhm)!="1"){
				return "����Ϊ����";
			}
			if(hmcd==undefined){
				if(zjhm.length<7){
					return "����7λ";
				}			
			}else{
				if(zjhm.length!=hmcd){
					return "���ȱ���Ϊ" + hmcd;
				}
			}
			if(zjhm.substring(0,1)=="1"){
				return "��1λ����Ϊ'1'"
			}			
			if(_checknum(fjhm)!="1"){
				return "�ֻ��ű���Ϊ����";
			}
		}else{
			return "��ʽ����ȷ"
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

//���ȫIP��ַ
function _checkIpAll(value1){
    var result = "1";
    var i = 0;
    if(value1.length>0){
		var iparr = value1.split(".");
		if(iparr.length!=4){
			return "[00G0202J1]:��ʽ����ȷ";
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

//�������
function _checkChinese(value1){
    var result = "1";
    var i = 0;
    if(value1.length>0){
    	for(var i=0;i<value1.length;i++){
			if(value1.substring(i,i+1).charCodeAt(0)<=255 && value1.substring(i,i+1) != '_' ){
				return "[00G00222J1]:����Ϊ���ģ�";
			}
		}
    	return "1";
    }else{
    	return result;
    }
}

//�������
function checkChinese(obj,mc,canfocus){
	var result = _checkChinese(obj.value);
	if(result!="1"){
		displayInfoHtml("[00G0022J1]:" + mc + "����Ϊ����!");
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
//����ֻ�����ĺϷ���
function _checksjhm(value1){
    var result = "1";
    if(value1.length>0){
    	result = _checknum(value1);
    	if(result!="1"){
    		return result;
    	}
    	if(value1.length<7||value1.length>12){
    		result = "[00G0182J1]:7λ��12λ";
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
		      return "[00G0052J1]:���֤���Ȳ���,���飡";
		}
	    zjhm1 = value1;
	    if (value1.length == 18) {
	      zjhm1 = value1.substr(0, 17);
	      zjhm2 = value1.substr(17, 1);
	    }			
	    re = new RegExp("[^0-9]");
	    if (s = zjhm1.match(re)){
	      return  "[00G0052J2]:�����ֵ�к��зǷ��ַ�'" + s + "'���飡";
	    }
	    if (value1.length == 15) {
	      birthday = "19" + value1.substr(6, 6);
	    }else{
	      re = new RegExp("[^0-9A-Z]");
	      if (s = zjhm2.match(re)) { 
	        //18λ���֤��ĩλҪ�����ֻ��ַ�
	        return "[00G0052J2]:�����ֵ�к��зǷ��ַ�'" + s + "'���飡";
	      }
	      birthday = value1.substr(6, 8);
	    }
	    birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +  birthday.substr(6, 2);
	    result = _checkdate(birthday);	
	    if ( result!="1") { //������ڵĺϷ���
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
//������������Ƿ���������ַ�
function _checkSpecilCharAndNull(tmpvalues){
	var s_tmp,s;
	
	s_tmp=tmpvalues;
	s_tmp=s_tmp.trim();
	//����Ƿ����������
	re=new RegExp("[^0-9A-Za-z]");
	if(s=s_tmp.match(re))
	{
	    return "[00G0112J1]:�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
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
            return "[00G0132J4]:���֤У�����------ ĩλӦ��:"+ls_check+" ʵ��:"+value1.substr(17,1);
      }
	  return "1"
}
function _isNum(fieldname,value1){
   re=new RegExp("[^0-9]");
   var s;
   if(s=value1.match(re)){
        return "[00G0032J1]:" + fieldname + "�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
   }else{
   		return "1";
   }	
}
function _checknum(value1){
   re=new RegExp("[^0-9]");
   var s;
   if(s=value1.match(re)){
        return "[00G0032J1]:�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
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

// �ж���������Ƿ�Ϸ� yngzm 20100823
function check_year(obj,tip){
  var value=obj.value.trim();
  if (value!=null && value.trim()!=""){ 
     if (!(value.length==4)){
	    alert(tip+"���Ȳ���,Ҫ��Ϊyyyy��");
	    return false;
     }
     var reg=new RegExp("[1-9][0-9]{3}");
 // var reg = /^(\d{1,4})$/;
     if(!reg.test(value)){
		alert(tip+"��ʽ����,Ҫ��Ϊyyyy��");
		return false;
	 }
  }
  return true;
}

// �ж���ȼ��������Ƿ�Ϸ� yngzm 20100824
function check_yearseason(obj,tip){
  var value=obj.value;
  if (value!=null && value.trim()!=""){
     if (!(value.length==5)){
	     alert(tip+"���Ȳ���,Ҫ��Ϊyyyys��");
	     return false;
     }
     var reg=new RegExp("[1-9][0-9]{3}[1-4]");
     if(!reg.test(value)){
		alert(tip+"��ʽ����,Ҫ��Ϊyyyys��");
		return false;
	 }
  }
  return true;
}

function _checknull(value1){
	value1 = value1.trim();
	if (value1.length==0)
	{
		return "[00G0022J1]:����Ϊ�գ�";			
	}else{
		return "1";
	}
}
function checknull(obj,mc,canfocus){
	var result = _checknull(obj.value);
	if(result!="1"){
		displayInfoHtml("[00G0022J1]:" + mc + "����Ϊ��!");
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
			//�ǿ�У��
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
			//����
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
			//����У��
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
			//���֤��У��
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
			//����У��
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
		   //����ʱ��У��
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
		   //С�ڵ���Ŀ��ֵ
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
						this._resultdesc = "[00G0082J1]:" + this._fieldname_cn + "����С�ڵ���" + this._otherinfo.value + "��";
					}else{
						this._resultdesc = "[00G0082J1]:" + this._fieldname_cn + "����С�ڵ���" + this._otherinfo + "��";					
					}
				}
				else
					this._resultdesc = this._errorinfo;	
			}	
			break;	
		case FRM_CHECK_LARGE_DATETIME:
		   //����Ŀ��ֵ
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
						this._resultdesc = "[00G0092J1]:" + this._fieldname_cn + "�������" + this._otherinfo.value + "��";
					}else{
						this._resultdesc = "[00G0092J1]:" + this._fieldname_cn + "�������" + this._otherinfo + "��";					
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
				this._resultdesc = "[00G0122J1]:" + this._fieldname_cn + "���Ϸ�!";
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
	//  ��������ֶ�checkallfields(arr,flag)
	//  arr��Ҫ�����ֶ��б�
	//  flag���������Ƿ�ֱ���˳���� 1-��
	//  checklength �������ĳ���	
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
			alert("����ġ�"+vmc+"���а����Ƿ��ַ�"+substr);
			return 0;
		}
		return 1;
	}

	//�ж�Ҫ����ʱ�����objdate��Ҫ�жϵ�Ŀ�����ڣ�startdate��ʼʱ��㣬lendays���ڿ�ȣ�����Ϊ����
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
//  ��������check_zjhm(zjmc,obj)
//  ����˵����֤�����ƣ�֤�����롣
//  ���ܽ��ܣ�������֤����Ϸ��ԡ�
//	      �����֤����Ƿ�Ϊȫ���֣��������ڸ�ʽ�Ƿ���ȷ���Ƿ�<=18,<=70��У������
//  ����ֵ  ��1-�ǣ�0-��
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
  if(zjmc=="A")   //���֤����
   {
       if(!(zjhm.length==15 || zjhm.length==18) )
	     {
	       	   return "[00G0132J1]:���֤���Ȳ���,���飡";
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
	        return  "[00G0132J2]:�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
        }
        //ȡ��������
        if(zjhm.length==15 )
            {
               birthday="19"+zjhm.substr(6,6);
            }
         else
            {
            	re=new RegExp("[^0-9X]");
               if(s=zjhm2.match(re))     //18λ���֤��ĩλҪ�����ֻ��ַ�
               {
                   return "�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
                }
                birthday=zjhm.substr(6,8);
            }
           birthday=birthday.substr(0,4)+"-"+birthday.substr(4,2)+"-"+birthday.substr(6,2)
          //alert("birthday"+birthday)
		  result = _isDate("֤������",birthday);
          if(result!="1")  //������ڵĺϷ���
          {
             return result;
          }
          if(zjhm.length==18 )
          {
          	result = _sfzCheck(zjhm);  //��18λ�������֤����ĩλУ��
          	if(result!="1"){
          		return result;
          	}
          }
       }
else
	{if (zjhm.length>17){

	       return "�ǡ��������֤�����Ȳ��ó���17λ,���飡";
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
//��������sfzCheck(hm)
//���ܽ��ܣ���18λ�������֤����ĩλУ��
//����˵�������֤����
//����ֵ  ��1-����,0-������
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
        return "[00G0132J3]:���֤У�����------ ĩλӦ��:"+ls_check+" ʵ��:"+hm.substr(17,1);
 }
 return "1";
}

//-------------------------------
//��������isDate(i_field,thedate)
//���ܽ��ܣ�У���ַ����Ƿ�Ϊ���ڸ�ʽ
//����˵���������������ַ���
//����ֵ  ��0-���ǣ�1--��
//-------------------------------

function _isDate(i_field,thedate)
{
	if (!(thedate.length==8 || thedate.length==10))
	{
  		return "[00G0132J5]:'"+i_field+"'���ڸ�ʽ����,\nҪ��Ϊyyyymmdd��yyyy-mm-dd��";
	}
	if (thedate.length==8)
	{
		thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
	}

	var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
	var r = thedate.match(reg);
	if (r==null)
	{
	   	return "��������ȷ��'"+i_field+"' ��";
	}
	var d= new Date(r[1],r[3]-1,r[4]);
	var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
	var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)

	if (newStr==newDate)
     {
      	return 1;
     }
 	return "[00G0132J5]:'"+i_field+"'���ڸ�ʽ����,\nҪ��Ϊyyyymmdd��yyyy-mm-dd��"; 
}

//��ʻ֤֤о���У��λ�ж�
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
   return "[00G0142J1]:��ʻ֤֤о���¼��������˶ԣ�";
  }
  for (ll_i=0;ll_i<=12;ll_i++)
  {
  if (ll_i != 2){
  if (hm.substr(ll_i,1) < "0" || hm.substr(ll_i,1) > "9"){
   return "[00G0142J1]:��ʻ֤֤о���¼��������˶ԣ�";
  }
  ll_sum=ll_sum+(hm.substr(ll_i,1)-0)*w[ll_i];
  }else{
  if ((hm.substr(ll_i,1) < "0" || hm.substr(ll_i,1) > "9") && hm.substr(ll_i,1) != "X"){
  return "[00G0142J1]:��ʻ֤֤о���¼��������˶ԣ�";
  }
  }
  }
  ll_sum=ll_sum % 11;

	if (ll_sum == 10) ls_check = "X";
	else ls_check = ll_sum;
	
	if (hm.substr(2,1) != ls_check)
	{
	 return "[00G0142J1]:��ʻ֤֤о���¼��������˶ԣ�";
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
		return "����ֵ����Ϊ��!";
	}	
	//����Ƿ����������
	re=new RegExp("[^0-9]");
	if(s=s_tmp.match(re))
	{
		return "[00G0152J1]:����ֵ�к��зǷ��ַ�'"+s+"'���飡";
	}	
	iValue=parseInt(ipdz);
	if (iValue>255)
	{
		return "[00G0152J2]:�����ֵ"+iValue+"����255�����飡";
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