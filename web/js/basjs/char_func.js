//�޳��ո�
String.prototype.trim = function(){
  return this.replace(/(^\s*)|(\s*$)/g, "");
}

//-------------------------------
//  ��������jstrim(s_value)
//  ���ܽ��ܣ�ȥ��s_value���˵Ŀո�
//  ����˵�������������ַ���
//  ����ֵ  ���������ַ���
//-------------------------------
function jstrim(s_value){
	var i;
	var ibegin;

	for(i=0;i<s_value.length;i++){
		if(s_value.charAt(i)!=' ')
			break;
	}

	if(i==s_value.length)
		return "";
	else
		ibegin=i;

	for(i=s_value.length-1;i>=0;i--){
		if(s_value.charAt(i)!=' ')
			break;
	}
	return s_value.substr(ibegin,i-ibegin+1);
}