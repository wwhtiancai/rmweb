//剔除空格
String.prototype.trim = function(){
  return this.replace(/(^\s*)|(\s*$)/g, "");
}

//-------------------------------
//  函数名：jstrim(s_value)
//  功能介绍：去掉s_value两端的空格
//  参数说明：被操作的字符串
//  返回值  ：处理结果字符串
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