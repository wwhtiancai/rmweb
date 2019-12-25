 /**
  * 事项String replaceAll
  * @param reallyDo
  * @param replaceWith
  * @param ignoreCase
  * @returns
  */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")),
				replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
}

/**
 * 获得URL传递的参数值
 * @param name
 * @returns
 */
function getURLParamValue(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
}

//返回值为：[lon1,lat1,lon2,lat2......]
function toWktArrayWithPrecisionFromLonlats(lonlats,n){
	if(lonlats==null && lonlats.length==0)return[];
	var temp=[];
	for(var i=0;i<lonlats.length;i++){
		temp[i]=toPrecision(lonlats[i].lon,n)+' '+toPrecision(lonlats[i].lat,n);
	}
	return temp;
}

//返回精确到小数点后n位的字符串，不足补0
//e.g:	toPrecision(3.14159,2)='3.14';
//		toPrecision(3.14,5)='3.14000';
function toPrecision(value, n){
	var num=parseFloat(value);
	if(isNaN(num))
		return value;
	var s=num.toString();
	var p=s.indexOf(".");
	if(p==-1){
		p=s.length-1;
	}
	s=roundTo(num,n).toString();
	for(var i=0; i<n-(s.length-1-p); i++){
		if(s.indexOf(".")==-1)s=s+".";
		s=s+"0";
	}
	return s;
}

function roundTo(x, n){
	var m=Math.pow(10,n);
	var ret=Math.round(x*m)/m
	return ret;
}