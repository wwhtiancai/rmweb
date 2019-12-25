// 是否手持设备 true是 false 否
function isHandheld(sblx){
	if("5" == sblx || "6" == sblx){
		return true;
	}
	return false;	
}

// 是否固定测速设备 true 是 false 否
function isFixedDevice(sblx){
	if("3" == sblx || "7" == sblx || "5" == sblx || "9" == sblx){
		return true;
	}
	return false;
}
// 是否区间测速设备 true 是 false 否
function isPtpsDevice(sblx){
	if("7" == sblx){
		return true;
	}
	return false;
}
// 是否卡口设备 true 是 false 否
function isTollgateDevice(sblx){
	if("2" == sblx){
		return true;
	}
	return false;
}
function selFxjg(obj,objname,fun){
	var len=arguments.length;
	var r=window.showModalDialog("dept.conf?method=getDeptList&showOffice=true&all=true","","dialogWidth:400px;dialogHeight:440px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r=="undefined"){
	}else if (r==""){
		setdepartment(obj,objname,"","");
	}else{
		var idx=r.indexOf("$");
		setdepartment(obj,objname,r.substring(0,idx),r.substring(idx+1));
	}
	if(len==3){
		eval(fun);
	}
}