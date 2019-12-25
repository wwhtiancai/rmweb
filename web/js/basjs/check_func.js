/**
 * ���ļ����ڼ��ͻ�������Ϸ���У��
 * 
 */
// -------------------------------
// ��������isDate(i_field,thedate)
// ���ܽ��ܣ�У���ַ����Ƿ�Ϊ���ڸ�ʽ
// ����˵���������������ַ���
// ����ֵ ��0-���ǣ�1--��
// -------------------------------

// �ж����������Ƿ�Ϸ� zhoujn 20060323
function isDate(i_field,thedate){
  if(thedate.length==0||thedate==null){
	  displayInfoHtml(i_field+"���ڸ�ʽ����,\nҪ��Ϊyyyy-mm-dd��");
	  return 0;
  }
  if (!(thedate.length==8 || thedate.length==10)){
	  displayInfoHtml(i_field+"���ڸ�ʽ����,\nҪ��Ϊyyyy-mm-dd��");
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
    displayInfoHtml(i_field+"���ڸ�ʽ����,\nҪ��Ϊyyyymmdd��yyyy-mm-dd��");
    return 0;
  }else{
	displayInfoHtml(i_field+"���ڸ�ʽ����,\nҪ��Ϊyyyymmdd��yyyy-mm-dd��");
	return 0;  
  }	   
}

//��������minlen������
function chk_core_hanzi(obj,tip,minlen){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value.trim();
	var reg   =   /^w[\u4E00-\u9FA5]+/g;   
	if (reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"����������ģ�");
		return false;
	}else{
		var len=value.replace(/[^\u4e00-\u9fa5]/gi,"").length;
		if(len<minlen){
			displayInfoHtml(tip+"���ٰ���"+minlen+"�����֣�");
			obj.focus();
			return false;
		}	
	}	
	return true;
}

//����ԭ�Ȳ���
function notNull(i_field,i_value){
	if (i_value==null || i_value.trim()==""){
		displayInfoHtml(i_field+"����Ϊ�գ�");
    	return 0;
 	}
 	return 1;
}


//cal_years
//�Ƚ����ڲ�������ĳ��ֵ

function chk_core_betw(date1,date2,tip,num){
	var value1=date1.value;
	var value2=date2.value;
	var num1=cal_years(value1);
	var num2=cal_years(value2);
	var num3=num1-num2;
	if(num3<num){
		displayInfoHtml(tip+"��ӦС��"+num);
		date2.focus();
		return false;
	}else{
		return true;
	}	
}

//��֤��������Ϊ��
function chk_core_null(obj,tip){
	var value=obj.value.trim();
	if(value!=""){
		displayInfoHtml(tip+"����Ϊ�գ�");
		obj.focus();
		return false;
	}
	return true;
}


/***********�����Ǻϳ���֤****************/

//�û�������̨�ʣ�Ѳ���񾯸������Ƶ�
function  getcount(obj,tip,maxvalue){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	var count=value.replace(/[^/]/g, '').length+1;
	if(count>maxvalue){
		displayInfoHtml('������ӦС��'+maxvalue);
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
        result=result+"������ӦС��"+maxvalue;
		temp=false;
		return temp;
	}
	
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}
//�û�������̨�ʣ�Ѳ���񾯸������Ƶ�
function  getcountpatrol(obj,tip,maxvalue){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	var count=value.replace(/[^#]/g, '').length+1;
	if(count>maxvalue){
		displayInfoHtml('������ӦС��'+maxvalue);
		return false;
	}
	return true;
}

//��֤���������յ�
function check_bxqz(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	
	var value=obj.value.trim();
	var reg   =   /^[\u4e00-\u9fa5]*\-[\u4e00-\u9fa5]*$/;   
	if (!reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"��ʽΪ[���(����)-�յ�(����)]!");
		return false;
	}	
	return true;
}

//��֤���������յ�
function check_zyld(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	
	var value=obj.value.trim();
	var reg   =   /^[\u4e00-\u9fa5]*\/[\u4e00-\u9fa5]*$/;   
	if (!reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"��ʽΪ[����(����)/����(����)]!");
		return false;
	}	
	return true;
}







function chk_core_notnull(obj,tip){
	var value=obj.value.trim();
	if(value==""){
		displayInfoHtml(tip+"�������벻��Ϊ�գ�");
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
		result=result+"�������벻��Ϊ��";
		temp=false;
	}
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}



//��֤����
function chk_core_hanziall(obj,tip){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	var   reg   =   /[^\u4E00-\u9FA5]/g;   
	if (reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"����ֻ���������ģ�");
		return false;
	}
	return true;
}






function na_chk_core_hanzi(obj,tip,minlen){
	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if (value==null || value.trim()==""){
		result=result+"����Ϊ��"
		temp=false;
	}	

	var reg   =   /^w[\u4E00-\u9FA5]+/g;   
	if (reg.test(value)){
		obj.focus();
		result=result+",�����������";
		temp=false;
	}
	
	var len=value.replace(/[^\u4e00-\u9fa5]/gi,"").length;
	if(len<minlen){
		result=result+",���ٰ���"+minlen+"�����֣�";
		temp=false;
	}	
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}



//��֤����
function chk_core_minlen(obj,tip,minlen){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	if(value.length<minlen){
		obj.focus();
		displayInfoHtml(tip+"�������벻������"+minlen+"����");
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
		displayInfoHtml(tip+"�������벻�ܴ���"+maxlen+"����");
		return false;
	}
	return true;
}

//��ⳤ��
function chk_core_len(obj,tip,len){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}	
	var value=obj.value.trim();
	if(value.length!=len){
		obj.focus();
		displayInfoHtml(tip+"�������볤�ȱ���Ϊ"+len+"��");
		return false;
	}
	return true;
}

//ֻ������֤�������Ƿ���ȷ
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

//ֻ������Ӣ��
function chk_core_letter(obj,tip){
	var value=obj.value;
	var reg=new RegExp("[^A-Za-z]");
	if(reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"�����ֵ�к��зǷ��ַ���");
		return false;
	}
	return true;
}
//ֻ����������
function chk_core_num(obj){
	var value=obj.value;
	var reg=new RegExp("[^0-9]");
	if(reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"�����ֵ�к��зǷ��ַ���");
		return false;
	}
	return true;
}

//��֤�����������֧��λС��
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
		displayInfoHtml(tip+"���֧��"+dotlen+"λС����");
		return false;
	}
	return true;	
}


function chk_core_letternum(obj,tip){
	var value=obj.value;
	var reg=new RegExp("[^0-9A-Za-z]");
	if(reg.test(value)){
		obj.focus();
		displayInfoHtml(tip+"�����ֵ�к��зǷ��ַ���");
		return false;
	}
	return true;
}

//����̶��绰�����������ţ�
//obj��֤����len���ص绰λ����tip��ʾ����
//��ȷ����true�����󷵻�false
//type 1����2������ δ����
//���������Ż��зֻ�
function check_gddh(obj,tip,len,type){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var format="";
	var value=obj.value;
	if(len==8){
		var partten = /^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{7}(-(\d{3,}))*$/;
		format="[����-8λ����-�ֻ�]���������š��ֻ���ѡ,8λ���벻��1��ͷ";
	}else if(len==7){
		var partten = /^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6}(-(\d{3,}))*$/;
		format="[����-7λ����-�ֻ�]���������š��ֻ���ѡ,7λ���벻��1��ͷ";
	}else{
		var partten = /^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6,7}(-(\d{3,}))*$/;
		format="[����-7λ����-�ֻ�]���������š��ֻ���ѡ,(6,7)λ���벻��1��ͷ";
	}	
	
	
    if(!partten.test(value)){
		obj.focus();
		displayInfoHtml("["+tip+"]����Ĺ̶��绰��ʽ����ȷ,��ȷΪ"+format);
		return false;
	}else{
		if(len==8){
			var partten=/^(1{8})|(2{8})|(3{8})|(4{8})|(5{8})|(6{8})|(7{8})|(8{8})|(9{8})|(0{8})$/;
			formate="����8��ͬ������!"
		}else if(len==7){
			var partten=/^(1{7})|(2{7})|(3{7})|(4{7})|(5{7,8})|(6{7})|(7{7})|(8{7})|(9{7})|(0{7})$/;
			formate="����7��ͬ������!"
		}else{
			var partten=/^(1{7,8})|(2{7,8})|(3{7,8})|(4{7,8})|(5{7,8})|(6{7,8})|(7{7,8})|(8{7,8})|(9{7,8})|(0{7,8})$/;
			formate="����(7,8)��ͬ������!"
		}	
		
		if(partten.test(value)){
			obj.focus();
			displayInfoHtml("["+tip+"],�벻Ҫ���������ض��绰,"+formate);
			return false;
		}	
	}	
	return true;			
}

//�����ƶ��绰�����������ţ�
//obj��֤����len���ص绰λ����tip��ʾ����
//��ȷ����true�����󷵻�false
//type 1����2������ δ����
//����С��ͨ���������зֻ���
function check_yddh(obj,tip,len){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var format="";
	if(len==8){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{7}$)/;
		format="11λ�ֻ��Ż�8λС��ͨ������С��ͨ��ʽ[����-7λ����]�����ſ�ѡ,8λ���벻��1��ͷ";
	}else if(len==7){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6}$)/;
		format="11λ�ֻ��Ż�7λС��ͨ������С��ͨ��ʽ[����-7λ����]�����ſ�ѡ,7λ���벻��1��ͷ";
	}else{
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6,7}$)/;
		format="11λ�ֻ��Ż�7λС��ͨ������С��ͨ��ʽ[����-(7,8)λ����]�����ſ�ѡ,(7,8)λ���벻��1��ͷ";
	}	
	var value=obj.value;

	if(!partten.test(value)){
		obj.focus();
		displayInfoHtml("["+tip+"]��ʽ����ȷ,��ȷΪ"+format);
		return false;
	}else{
		if(len==8){
			var partten=/^(1{8})|(2{8})|(3{8})|(4{8})|(5{8})|(6{8})|(7{8})|(8{8})|(9{8})|(0{8})$/;
			formate="����8��ͬ������!"
		}else if(len==7){
			var partten=/^(1{7})|(2{7})|(3{7})|(4{7})|(5{7,8})|(6{7})|(7{7})|(8{7})|(9{7})|(0{7})$/;
			formate="����7��ͬ������!"
		}else{
			var partten=/^(1{7,8})|(2{7,8})|(3{7,8})|(4{7,8})|(5{7,8})|(6{7,8})|(7{7,8})|(8{7,8})|(9{7,8})|(0{7,8})$/;
			formate="����(7,8)��ͬ������!"
		}	
		
		if(partten.test(value)){
			obj.focus();
			displayInfoHtml("["+tip+"],�벻Ҫ���������ض��绰,"+formate);
			return false;
		}	
	}	

	return true;
}

//������ϵ�绰������Ϊ�ֻ����ߴ����ŵĹ̶��绰����С��ͨ
function check_lxdh(obj,tip,len){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var format="";
	if(len==8){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{7}$)/;
		format="11λ�ֻ��Ż�8λ�̶��绰�����й̶��绰[����-7λ����]�����ſ�ѡ,8λ���벻��1��ͷ";
	}else if(len==7){
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6}$)/;
		format="11λ�ֻ��Ż�7λ�̶��绰�����й̶��绰[����-7λ����]�����ſ�ѡ,7λ���벻��1��ͷ";
	}else{
		var partten =/(^1\d{10}$)|(^(0(([1,2]\d)|([3-9]\d{2}))-)*[2-9]\d{6,7}$)/;
		format="11λ�ֻ��Ż�7λ�̶��绰�����й̶��绰[����-(7,8)λ����]�����ſ�ѡ,(7,8)λ���벻��1��ͷ";
	}	
	var value=obj.value;

	if(!partten.test(value)){
		obj.focus();
		displayInfoHtml("["+tip+"]��ʽ����ȷ,��ȷΪ"+format);
		return false;
	}else{
		if(len==8){
			var partten=/^(1{8})|(2{8})|(3{8})|(4{8})|(5{8})|(6{8})|(7{8})|(8{8})|(9{8})|(0{8})$/;
			formate="����8��ͬ������!"
		}else if(len==7){
			var partten=/^(1{7})|(2{7})|(3{7})|(4{7})|(5{7,8})|(6{7})|(7{7})|(8{7})|(9{7})|(0{7})$/;
			formate="����7��ͬ������!"
		}else{
			var partten=/^(1{7,8})|(2{7,8})|(3{7,8})|(4{7,8})|(5{7,8})|(6{7,8})|(7{7,8})|(8{7,8})|(9{7,8})|(0{7,8})$/;
			formate="����(7,8)��ͬ������!"
		}	
		
		if(partten.test(value)){
			obj.focus();
			displayInfoHtml("["+tip+"],�벻Ҫ���������ض��绰,"+formate);
			return false;
		}	
	}	

	return true;
}

//��֤���ֵ����Сֵ
function chk_core_maxmin(obj,tip,maxvalue,minvalue){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(value>maxvalue||value<minvalue){
		displayInfoHtml(tip+"Ӧ����"+minvalue+",С��"+maxvalue);
		obj.focus();
		return false;
	}	
	return true;
}
//����ö������ֵ
function chk_core_max(obj,tip,maxvalue){
	
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(value>maxvalue){
		displayInfoHtml(tip+"�������ֵӦС��"+maxvalue);
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
		result=result+"����Ϊ��";
		temp=false;
	}
	
	if(value>maxvalue){
		result=result+"�������ֵӦС��"+maxvalue;
		temp=false;
	}	
	
	if(temp==false){
		return result;
	}else{	
		return true;
	}
}



//�������ֵ
//type3���4�ж�
//maxvalue1������ֵ��maxvalue2�ж����ֵ��
function chk_core_max12(obj,tip,type,maxvalue1,maxvalue2){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(type==3){
		if(value>maxvalue1){
			displayInfoHtml(tip+"��Ӳɼ����ݲ��������ֵΪ"+maxvalue1);
			obj.focus();
			return false;
		}	
	}else if(type==4){
		if(value>maxvalue2){
			displayInfoHtml(tip+"�жӲɼ����ݲ��������ֵΪ"+maxvalue2);
			obj.focus();
			return false;
		}		
	}
	
	return true;
}


//�������ֵ
//type3���4�ж�
//maxvalue1������ֵ��maxvalue2�ж����ֵ��
function chk_core_max12onfocus(obj,tip,type,maxvalue1,maxvalue2){
	if(!chk_core_notnull(obj,tip)){
		return false;
	}
	var value=obj.value;
	if(type==3){
		if(value>maxvalue1){
			displayInfoHtml(tip+"��Ӳɼ����ݲ�����");
			
			return false;
		}	
	}else if(type==4){
		if(value>maxvalue2){
			displayInfoHtml(tip+"�жӲɼ����ݲ�����");
	
			return false;
		}		
	}
	
	return true;
}

//�������ֵ
//type3���4�ж�
//maxvalue1������ֵ��maxvalue2�ж����ֵ��
function na_chk_core_max12(obj,tip,type,maxvalue1,maxvalue2){
	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(!na_chk_core_notnull(obj,tip)){
		return false;
	}

	if(type==3){
		if(value>maxvalue1){
			result=result+"��Ӳɼ����ݲ�����";
		    temp=false;

		}	
	}else if(type==4){
		if(value>maxvalue2){
          result=result+"�жӲɼ����ݲ�����";
		    temp=false;
		}		
	}	
	if(temp==false){
		return result;
	}else{	
		return true;
	}
}
//��������
//��ȷ����true�����󷵻�false
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

//����ʱ��
//��ȷ����true�����󷵻�false
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

/***********������ԭ����֤*****************/



// У�鴫����ı����ı��Ƿ�Ϊ��ֵ����
function checkNumber(obj,vMc){
  var v;
  v=obj.value;
  v=v.trim();
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
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
    return vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
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
    displayInfoHtml(vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
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
    displayInfoHtml("�����"+vMc+"�ĳ��Ȳ���"+vLength+"λ��");
    return false;
  }
  re=new RegExp("[^0-9.]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
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
    return "�����"+vMc+"�ĳ��Ȳ���"+vLength+"λ��";
  }
  re=new RegExp("[^0-9.]");
  if(s=v.match(re)){
    return vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡";
  }
  obj.value=v;
  return true;
}


//�������֤������
//������������
//��ȷ����true�����󷵻�false
function check_sfzm(obj){
    if(!chk_core_notnull(obj,tip)||!chk_core_sfzmhm(obj)){
    	return false;
	}
    return true;
}



//��⺺�����ٶ��ٸ�
//obj��֤����minlen���ٶ���λ��tip��ʾ����
function check_hanzi_minlen(obj,tip,minlen){
	var value=obj.value.trim();
    if(!chk_core_notnull(obj,tip)||!chk_core_hanziall(obj,tip)
    		||!chk_core_minlen(obj,tip,minlen)){
    	return false;
	}
	return true;
}

//��⺺������¼��minlen,����¼��maxlen
function check_hanzi_maxmin(obj,tip,maxlen,minlen){
	var value=obj.value.trim();
    if(!chk_core_notnull(obj,tip)||!chk_core_hanziall(obj,tip)
    		||!chk_core_minlen(obj,tip,minlen)
    		||!chk_core_maxlen(obj,tip,maxlen)){
    	return false;
	}
	return true;
}

//����¹ʱ���Ƿ����Ҫ��
//��鳤��Ϊ16λ��ȫ��Ϊ����
function check_acdNo(obj,i_field){

}


function checkzero_null(i_field){
  var i_value=i_field.value;
 	if (i_value==null || jstrim(i_value)==0){
    	return 0;
 	}
 	return 1;
}

//����Ƿ�Ϊ0
function checkzero(i_field,i_name){
  var i_value=i_field.value;
 	if (i_value==null || jstrim(i_value)==0){
     	displayInfoHtml(i_name+"����Ϊ�㣡");
     	i_field.focus();
    	return 0;
 	}
 	return 1;
}


//����Ƿ�Ϊ0
function na_checkzero(obj,tip){

	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(value==""){
		
	   result=result+"�������벻��Ϊ��";
		temp=false;
		
	}
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}

//����Ƿ�Ϊ0
function na_checknotzero(obj,tip){

	var result=tip+":";
	var value=obj.value.trim();
	var temp=true;
	if(jstrim(value)!=0){
		
	   result=result+"��������ӦΪ��";
		temp=false;
		
	}
	if(temp==false){
		return result;
	}else{
		return temp;
	}	
}



//����ʾ��ֻ����
function na_notNull(i_field,i_value){
	var result=1;
 	if (i_value==null || i_value.trim()==""){
    	result=i_field+"����Ϊ�գ�";
    	return result;
 	}
 	return 1;
}

// -------------------------------
// ��������DateAddMonth(strDate,iMonths)
// ���ܽ��ܣ�������ڼ���iMonths�����������
// ����˵����strDate ����
// ����ֵ ���޷���ֵ
// -------------------------------
function check_zjhmNoAge(zjmc, zjhm) {

    var birthday = "";
    var zjhm1 = "";
    var zjhm2 = "";

    var s = "";
    if (zjmc == "A") { // ���֤����
        if (!(zjhm.length == 15 || zjhm.length == 18)) {
            displayInfoHtml("���֤���Ȳ���,���飡");
            return 0;
        }
        zjhm1 = zjhm;
        if (zjhm.length == 18) {
            zjhm1 = zjhm.substr(0, 17);
            zjhm2 = zjhm.substr(17, 1);
        }

        re = new RegExp("[^0-9]");
        if (s = zjhm1.match(re)) {
            displayInfoHtml("�����ֵ�к��зǷ��ַ�'" + s + "'���飡");
            return 0;
        }
        // ȡ��������
        if (zjhm.length == 15) {
            birthday = "19" + zjhm.substr(6, 6);
        } else {
            re = new RegExp("[^0-9X]");
            if (s = zjhm2.match(re)) { // 18λ���֤��ĩλҪ�����ֻ��ַ�
                displayInfoHtml("�����ֵ�к��зǷ��ַ�'" + s + "'���飡");
                return 0;
            }
            birthday = zjhm.substr(6, 8);
        }
        birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +
                   birthday.substr(6, 2)
                   // displayInfoHtml("birthday"+birthday)

                   if (isDate("֤������", birthday) == 0) { // ������ڵĺϷ���
            return 0;
        }

        if (zjhm.length == 18) {
            return (sfzCheck(zjhm)); // ��18λ�������֤����ĩλУ��
        }
    } else {
        if (zjhm.length > 17) {

            displayInfoHtml("�ǡ��������֤�����Ȳ��ó���17λ,���飡");
            return 0;
        }
    }

    return 1;
}

// -------------------------------
// ��������check_zjhm(zjmc,obj)
// ����˵����֤�����ƣ�֤�����롣
// ���ܽ��ܣ�������֤����Ϸ��ԡ�
// �����֤����Ƿ�Ϊȫ���֣��������ڸ�ʽ�Ƿ���ȷ���Ƿ�<=18,<=70��У������
// ����ֵ ��1-�ǣ�0-��
// -------------------------------
function check_zjhm(zjmc, zjhm) {

    var birthday = "";
    var zjhm1 = "";
    var zjhm2 = "";

    var s = "";
    if (notNull("֤������", zjhm) == 0) {
        return 0;
    }
    if (zjmc == "A") { // ���֤����
        if (!(zjhm.length == 15 || zjhm.length == 18)) {
            displayInfoHtml("���֤���Ȳ���,���飡");
            return 0;
        }
        zjhm1 = zjhm;
        if (zjhm.length == 18) {
            zjhm1 = zjhm.substr(0, 17);
            zjhm2 = zjhm.substr(17, 1);
        }

        re = new RegExp("[^0-9]");
        if (s = zjhm1.match(re)) {
            displayInfoHtml("�����ֵ�к��зǷ��ַ�'" + s + "'���飡");
            return 0;
        }
        // ȡ��������
        if (zjhm.length == 15) {
            birthday = "19" + zjhm.substr(6, 6);
        } else {
            re = new RegExp("[^0-9X]");
            if (s = zjhm2.match(re)) { // 18λ���֤��ĩλҪ�����ֻ��ַ�
                displayInfoHtml("�����ֵ�к��зǷ��ַ�'" + s + "'���飡");
                return 0;
            }
            birthday = zjhm.substr(6, 8);
        }

        birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +
                   birthday.substr(6, 2)
                   // displayInfoHtml("birthday"+birthday)

                   if (isDate("֤������", birthday) == 0) { // ������ڵĺϷ���
            return 0;
        }
        var nl = cal_years(birthday); // ������

        if (nl-0<18 || nl>70){
        // if (nl - 0 < 18) {
            displayInfoHtml("����Ҫ�� 18������ 70�����£���ǰ " + nl + " ��");
            return 0;
        }
        if (zjhm.length == 18) {
            return (sfzCheck(zjhm)); // ��18λ�������֤����ĩλУ��
        }
    } else {
        if (zjhm.length > 17) {

            displayInfoHtml("�ǡ��������֤�����Ȳ��ó���17λ,���飡");
            return 0;
        }
    }

    return 1;
}

// -------------------------------
// ��������isLength(i_field,i_length,i_value)
// ���ܽ��ܣ��������ֵ�Ƿ�Ϊָ������
// ����˵���������Ҫ�󳤶ȣ�ֵ
// ����ֵ ��1-��ָ������,0-����
// -------------------------------
function isLength(i_field, i_length, i_value) { // displayInfoHtml("---����Ҫ��:"+i_length+"
												// "+i_value.length);
    if (!(i_value.length == i_length)) {
        displayInfoHtml("'" + i_field + "' �ĳ���Ҫ��Ϊ' " + i_length + " '��");
        return 0;
    }
    return 1;
}


// �ж�����ʱ���Ƿ�Ϸ� zhoujn 20060323
function isDateTime(i_field,thedate){
  if(thedate.length==0||thedate==null){return 0;}
  if(thedate.length==16){
    thedate=thedate+":00";
  }
  if (!(thedate.length==19)){
     displayInfoHtml(i_field+"��ʽ����,��������ȷ�ĸ�ʽ��yyyy-mm-dd hh24:mi��");
     return 0;
  }
  dateRegexp = new RegExp("^(\\d{4})[-](\\d{2})[-](\\d{2})[ ](\\d{2})[:](\\d{2})[:](\\d{2})$");
  var matched = dateRegexp.exec(thedate);
  if(matched != null) {
    if (!isTrueDateTime(matched[3], matched[2], matched[1],matched[4], matched[5], matched[6])){
      displayInfoHtml(i_field+"��ʽ���ԣ���������ȷ�ĸ�ʽ��yyyy-mm-dd hh24:mi��");
      return 0;
    } else {
      return 1;
    }
  }
  return 0;
}

//�ڲ�����
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
// ��������isNum(i_field,i_value)
// ���ܽ��ܣ���������ַ����Ƿ�Ϊ����
// ����˵�������������Ķ�Ӧֵ
// ����ֵ ��1-������,0-������
// -------------------------------
function isNum(i_field, i_value) {
    if (notNull(i_field, i_value) == 0) {
        return 0;
    }

    re = new RegExp("[^0-9]");
    var s;
    if (s = i_value.match(re)) {
        displayInfoHtml("'" + i_field + "' �к��зǷ��ַ� '" + s + "' ��");
        return 0;
    }
    return 1;
}
// //////////////
/*
 * ���������ƺ����Ƿ���ȷ ���ƺ����磺A12345 ��Σ�obj (�ı������������) ����false:����ȷ��true:��ȷ xuxd 20060313
 */
function checkHphm(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("���ƺ��벻��Ϊ�գ�");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		if (i_valueLen!=6){
			displayInfoHtml("���ƺ��볤�Ȳ��ԣ�");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(5,1);
			if(s_tmp=="��"){    // ��������ҳ�
				s_tmp=s_value.substr(0,5);
			}else{
				s_tmp=s_value;
			}
			// ����Ƿ����������
			re=new RegExp("[^0-9A-Za-z]");
			if(s=s_tmp.match(re)){
				displayInfoHtml("�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
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
 * ���������������ƺ����Ƿ���ȷ ���ƺ����磺��A12345 ��Σ�obj (�ı������������) ����false:����ȷ��true:��ȷ yangzm 20100823
 */
function checkFullHphm(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("���ƺ��벻��Ϊ�գ�");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		if (i_valueLen!=7){
			displayInfoHtml("���ƺ��볤�Ȳ��ԣ�");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(6,1);
			if(s_tmp=="��" || s_tmp=="��"){    // ��������ҳ�������
				s_tmp=s_value.substr(1,6);
			}else{
				s_tmp=s_value.substr(1,7);
			}
			// ����Ƿ����������
			re=new RegExp("[^0-9A-Za-z]");
			if(s=s_tmp.match(re)){
				displayInfoHtml("�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
				obj.focus();
                return false;
            }
            s_tmp = s_value.substr(0,1);
            re2=new RegExp("[\u4e00-\u9fa5]");
            if(s=s_tmp.match(re2)){
				displayInfoHtml("�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
				obj.focus();
                return false;
            }
		}
	}
	obj.value=s_value;
	return true;
}
//�����ƺ���
function checkHphmNull(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
  if(s_value!=""){
		i_valueLen=s_value.length;
		if (i_valueLen!=6){
			displayInfoHtml("���ƺ��볤�Ȳ��ԣ�");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(5,1);
			if(s_tmp=="��"){    // ��������ҳ�
				s_tmp=s_value.substr(0,5);
			}else{
				s_tmp=s_value;
			}
			// ����Ƿ����������
			re=new RegExp("[^0-9A-Za-z]");
			if(s=s_tmp.match(re)){
				displayInfoHtml("�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
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
 * ���������ƺ���Ϊ����,Ѳ��ִ�ڳ��� ��: E1234��
 */
function checkJcHphm(obj){
	var s_value=obj.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("���ƺ��벻��Ϊ�գ�");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		if (i_valueLen!=6){
			displayInfoHtml("���ƺ��볤�Ȳ��ԣ�ӦΪ6λ��");
			obj.focus();
			return false
		}else{
			s_tmp=s_value.substr(5,1);
			if(s_tmp=="��"){    // ��������ҳ�
				s_tmp=s_value.substr(0,5);
			}else{
				s_tmp=s_value;
			}
		}
	}
	obj.value=s_value;
	return true;
}
// thatΪ��Ӧ��text��,��ֻ֤������������
function  fCheckInputNumber(that){
	var keycode = event.keyCode ;
	var realkey = String.fromCharCode(event.keyCode) ;
	var thatValue=that.value;
  keycode=keycode*1;
    // ȱʡ��Ϊevent.returnValue=true;
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

//����ֻ��ʯ��������
function CheckInputLengthAndNumber(that,vLength){
	var keycode = event.keyCode ;
	var realkey = String.fromCharCode(event.keyCode) ;
	var thatValue=that.value;
	keycode=keycode*1;

  // ȱʡ��Ϊevent.returnValue=true;
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
// У�鴫����ı����ı��Ƿ�Ϊ��ֵ���ͳ����Ƿ����Ҫ��
function checkNumberAndLength(obj,vMc,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length!=vLength){
    displayInfoHtml("�����"+vMc+"�ĳ��Ȳ���"+vLength+"λ��");
    return false;
  }
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
    return false;
  }
  obj.value=v;
  return true;
}

// У�鴫����ı����ı��Ƿ�Ϊ��ĸ�����֣����ͳ����Ƿ����Ҫ��
function checkCharAndLength(obj,vMc,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length!=vLength){
    displayInfoHtml("�����"+vMc+"�ĳ��Ȳ���"+vLength+"λ��");
    return false;
  }
  re=new RegExp("[^0-9A-Za-z]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
    return false;
  }
  obj.value=v;
  return true;
}

// У�鴫����ı����ı��Ƿ�Ϊ��ĸ����������
function checkChar(obj,vMc){
  var v;
  v=obj.value;
  v=v.trim();
  re=new RegExp("[^0-9A-Za-z]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
    return false;
  }
  obj.value=v;
  return true;
}





// ��⴫���ı�ֵ�Ƿ��ѳ�������
function checkMaxLength(obj,vMc,vL){
  var v ;
  v=obj.value;
  v=v.trim();
  if(v.length>vL){
    displayInfoHtml(vMc+"����ֵ�Ѿ�����ָ�����ȣ�");
    return false;
  }
  obj.value=v;
  return true;
}

// У�鴫����ı����ı��Ƿ�Ϊ��
function checkNull(obj,vMc){
	var v;
	v=obj.value;
	v=v.trim();
	
	if (v.length==0)
	{
		displayInfoHtml(vMc+"����ֵΪ�գ�");
		return false;
	}
	obj.value=v;
	return true;
}

// У�鴫��ĵ�ѡ���Ƿ�Ϊ��
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
		displayInfoHtml(vMc+"δѡ��");
		return false;
	}
	if (value==undefined)
	{
		displayInfoHtml(vMc+"δѡ��");
		return false;
	}
	return true;
}

// ������������Ƿ���������ַ�
function checkSpecilCharAndNull(obj,vMc){
  var s_tmp,s;

  s_tmp=obj.value;
  s_tmp=s_tmp.trim();

  if (s_tmp.length==0){
    displayInfoHtml(vMc+"����ֵΪ�գ�");
    return false;
  }

  // ����Ƿ����������
  re=new RegExp("[^0-9A-Za-z]");
  if(s=s_tmp.match(re)){
    displayInfoHtml("�����"+vMc+"ֵ�к��зǷ��ַ�'"+s+"'���飡");
    obj.focus();
    return false;
  }
  obj.value=s_tmp;
  return true;
}


// ��������ip��ַ�Ƿ���ȷ
function checkIp(obj,vMc){
  var s_tmp,s;
  var iValue;
  s_tmp=obj.value;
  s_tmp=s_tmp.trim();

  if (s_tmp.length==0){
    displayInfoHtml(vMc+"����ֵΪ�գ�");
    return false;
  }

  // ����Ƿ����������
  re=new RegExp("[^0-9]");
  if(s=s_tmp.match(re)){
    displayInfoHtml("�����"+vMc+"ֵ�к��зǷ��ַ�'"+s+"'���飡");
    obj.focus();
    return false;
  }

  iValue=parseInt(obj.value);
  if (iValue>255){
    displayInfoHtml("�����"+vMc+"ֵ"+iValue+"����255�����飡");
    obj.focus();
    return false;
  }
  obj.value=s_tmp;
  return true;
}

// ȷ�ϱ�text��ֻ���������� onKeypress="fCheckInputInt()"
// zhoujn 20060323
function fCheckInputInt(){
  if (event.keyCode < 48 || event.keyCode > 57)
    event.returnValue = false;
}
function fCheckInputIntAlert(){
  if (event.keyCode < 48 || event.keyCode > 57){
  displayInfoHtml("���������֣�");
  event.returnValue = false;
  }
}


// -------------------------------
// ��������sfzCheck(hm)
// ���ܽ��ܣ���18λ�������֤����ĩλУ��
// ����˵�������֤����
// ����ֵ ��1-����,0-������
// -------------------------------
//�ڲ�����
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
				displayInfoHtml("���֤У�����------ ĩλӦ��:"+ls_check+" ʵ��:"+hm.substr(17,1));
				return 0;
  }
	return 1
}
    

/*
 * ���������ƺ����Ƿ���ȷ ���� �������ҳ����������� ��Σ�obj :���ƺ���,objzl:�������� ����false:����ȷ��true:��ȷ
 */
function checkHphm2(obj,objzl){
	var s_value=obj.value.trim();
	var zl_value=objzl.value.trim();
	var i_valueLen=0;
	var s_tmp="";
	if (s_value.length==0){
		displayInfoHtml("���ƺ��벻��Ϊ�գ�");
		obj.focus();
		return false
	}else{
		i_valueLen=s_value.length;
		// �ҳ���������,����
		if(zl_value=="03" || zl_value=="04" || zl_value=="15" || zl_value=="16" || zl_value=="17" || zl_value=="18" || zl_value=="19" || zl_value=="23" || zl_value=="24"){
      if (i_valueLen!=5){
        displayInfoHtml("���ƺ��볤�Ȳ���,ӦΪ5λ��");
        obj.focus();
        return false
      }else{
        s_tmp=s_value;
        // ����Ƿ����������
        re=new RegExp("[^0-9A-Za-z]");
        if(s=s_tmp.match(re)){
          displayInfoHtml("�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
          obj.focus();
          return false;
        }
      }
		}else{
      if (i_valueLen!=6){
        displayInfoHtml("���ƺ��볤�Ȳ��ԣ�ӦΪ6λ��");
        obj.focus();
        return false
      }else{
        s_tmp=s_value;
        // ����Ƿ����������
        re=new RegExp("[^0-9A-Za-z]");
        if(s=s_tmp.match(re)){
          displayInfoHtml("�����ֵ�к��зǷ��ַ�'"+s+"'���飡");
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
// У�鴫���ʱ��Сʱ�ı����ı��Ƿ�Ϊ��ֵ���ͳ����Ƿ����Ҫ��,������ʾΪСʱ
function checkHourNumberAndLength(vMc,obj,vLength){
  var v;
  v=obj.value;
  v=v.trim();
  if (v.length ==1){
    v='0'+v;
  }
  if (v.length!=vLength){
    displayInfoHtml("�����"+vMc+"�ĳ��Ȳ���"+vLength+"λ�� ");
    obj.focus();
    return false;
  }
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"���뺬�зǷ��ַ� '"+s+"'���飡 ����: ����9��������: 09 ");
    obj.focus();
    return false;
  }
  if(v>=24){
    displayInfoHtml("ע��:СʱӦ����00-23��Χ��! ����: ����9��������: 09 ");
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
    c = m_Value.charAt(i); // �ַ���s�е��ַ�
    if (strLetter.indexOf(c) == -1) {
// obj.style.backgroundColor = errorColor;
      displayInfoHtml(m_fields + "���зǷ��ַ���" + c + "��!");
      obj.focus();
      return false;
    }
  }
// obj.style.backgroundColor = okColor;
  return true;
}



// -------------------------------
// ��������dyLength(i_field,i_length,i_value)
// ���ܽ��ܣ��������ֵ�Ƿ�ﵽָ����������
// ����˵���������Ҫ�󳤶ȣ�ֵ
// ����ֵ ��1-����,0-����
// -------------------------------
function dyLength(i_field,i_length,obj)
{ // displayInfoHtml("---����Ҫ��:"+i_length+" "+i_value.length);
  obj.value=obj.value.trim();
  var i_value=obj.value;
  if (i_value.length<i_length)
  {
// obj.style.backgroundColor = errorColor;
    displayInfoHtml("'"+i_field+"' �ĳ�������Ϊ '"+i_length+"'��");
    obj.focus();
    return 0;
  }
// obj.style.backgroundColor = okColor;
  return 1;
}

// by:shijianrong 20090420
// У�鴫���ʱ������ı����ı��Ƿ�Ϊ��ֵ���ͳ����Ƿ����Ҫ��,������ʾΪ����
function checkMinuteNumberAndLength(vMc,obj,vLength){
  var v;

  v=obj.value;
  v=v.trim();
  if (v.length ==1){
    v='0'+v;
  }
  if (v.length!=vLength){
    displayInfoHtml("�����"+vMc+"�ĳ��Ȳ���"+vLength+"λ�� ");
    obj.focus();
    return false;
  }
  re=new RegExp("[^0-9]");
  if(s=v.match(re)){
    displayInfoHtml(vMc+"���뺬�зǷ��ַ� '"+s+"'���飡 ����: ����9��15��������: 15 ");
    obj.focus();
    return false;
  }
  if(v>=60){
    displayInfoHtml("ע��:����Ӧ����00-59��Χ��! ����: ����9��15��������: 15 ");
    obj.focus();
    return false;
  }
  obj.value=v;
  return true;
}


//-------------------------------
//�� �� ����cal_years(rq)
//���ܽ��ܣ�����ָ�����ڵ������Ƕ�����.
//����˵��������
//�� �أ�����
//-------------------------------
//�ڲ�����
function cal_years(rq){
	var years;
	var dDate = new Date();  
	// ϵͳ����(ϵͳ����Ӧ�ô���rq)
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
	if (month2 > month1)   // �·�δ����years-1
	{
	  years = years - 1;
	}
	else
	{
	  if ( (month1 == month2) && (day2 > day1))  // �·ݵ��ˣ�����δ����years-1
	  {
	    years = years - 1;
	  }
	}
	// displayInfoHtml("-----------years-"+years);
	return years;
}
//У�鳵����ɫ
function checkCsys(obj,tip)
{
  if(obj.value=="")
	  return false;
  var arr=new Array("��","��","��","��","��","��","��","��","��","��");
  if(obj.value.length==1)
  {
	  for(i=0;i<10;i++)
	  {
	     if(obj.value==arr[i])
	     {
	    	 return true;
	     }
	  }
	  displayInfoHtml(tip+"�������");
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
              displayInfoHtml(tip+"�������");
			  return false;
		  }

	  }
  }
  return true;
}

// �ж������Ƿ���Ͼ���
function isFloat(obj,tip, iInt, iFloat) {
	var inum=obj.value;
	if (inum != "") {
		if (isNaN(inum)) {
			displayInfoHtml(tip + "�������֣�");
			obj.focus();
			return false;
		} else {
			var arr = (inum + "").split(".");
			if (arr.length >= 2) {
				if (arr[0].length > iInt) {
					displayInfoHtml(tip + "����λ���ֻ��Ϊ" + iInt + "λ��");
					obj.focus();
					return false;
				}
				if (arr[1].length > iFloat) {
					displayInfoHtml(tip + "С��λ���ֻ��Ϊ" + iFloat + "λ��");
					obj.focus();
					return false;
				}
			} else if (arr.length >= 1) {
				if (arr[0].length > iInt) {
					displayInfoHtml(tip + "����λ���ֻ��Ϊ" + iInt + "λ��");
					obj.focus();
					return false;
				}
			}
		}
	}
	return true;
}
