//20141105 调整地图展示加载
//1:本页面；2：dialog
function json2array(json){  
    var result = [];  
    var keys = Object.keys(json);  
    keys.forEach(function(key){  
        result.push(json[key]);  
    });  
    return result;  
}  

function send_req_gisdata(curUrl,formname,wintype){
	var queryobj = $("#"+formname).serializeArray();
	$.ajax({
		type: "POST",
		url: curUrl,
		cache:false,
		async:false,
		dataType:"json",
		data:queryobj,
		success:(function(data){
			/*
			var code=data["code"];
			var message=data["message"];
			$.each(message,function(i,n){
				var keys = Object.keys(message[i]);  
			    keys.forEach(function(key){  
			    	alert(key);
			    	alert(message[i][key]);
			    }); 
		    });  
		    */
			
			if(wintype=="1"){
				gisiframename.xxxxx(data);	
			}else if(wintype=="2"){
				display_gis(data);
			}
		})
	});
}

//将数据在地图上展示出来
function display_gis(data){
	var retdata=window.showModalDialog("gisfeatures.gis?method=featuresShowInWin",data
			,"dialogWidth:840px;dialogHeight:650px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof retdata != "undefined"){
		return;
	}
}


function setFormval(gisSbmc,gisXzqh,gisDldm,gisLddm,gisJd,gisWd){
	$("#editfeatureform input[id='gissbmc']").val(gisSbmc);
	$("#editfeatureform input[id='gisxzqh']").val(gisXzqh);
	$("#editfeatureform input[id='gisdldm']").val(gisDldm);
	$("#editfeatureform input[id='gislddm']").val(gisLddm);
	$("#editfeatureform input[id='gisjd']").val(gisJd);
	$("#editfeatureform input[id='giswd']").val(gisWd);
}


function setFormvalSip(gisSbmc,gisJd,gisWd){
	$("#editfeatureform input[id='gissbmc']").val(gisSbmc);
	$("#editfeatureform input[id='gisjd']").val(gisJd);
	$("#editfeatureform input[id='giswd']").val(gisWd);
}
//行政区划、道路代码、桩米数
function checkFormval(){
	//alert($("#editfeatureform input[id='gisxzqh']").val());
	//alert($("#editfeatureform input[id='gisdldm']").val());
	//alert($("#editfeatureform input[id='gislddm']").val());
	if($("#editfeatureform input[id='gisxzqh']").val()!=""
		&&$("#editfeatureform input[id='gisdldm']").val()!=""
		&&$("#editfeatureform input[id='gislddm']").val()!=""){
		return true;
	}else{
		alert("请先采集行政区划、道路代码、桩米数（路段）等信息，再进行地图标注！");
		return false;
	}
}

function do_mark(obj1,obj2){
	var data = $("#editfeatureform").serializeArray(); //自动将form表单封装成json
	var url = "gisfeatures.gis?method=addFeature";
	//var retdata=openwindialog("gisfeatures.gis?method=addFeature",data);
	var retdata=window.showModalDialog(url,data,
			"dialogWidth:840px;dialogHeight:650px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof retdata != "undefined"){
		ret_mark(retdata,obj1,obj2);
		return;
	}
}
function ret_mark(retdata,obj1,obj2){
	if (typeof retdata != "undefined"){
		$("#editfeatureform input[id='gisjd']").val(retdata[0]);
		$("#editfeatureform input[id='giswd']").val(retdata[1]);
		obj1.val(retdata[0]);
		obj2.val(retdata[1]);
		return;
	}
}
function checkJwd(jwd, coord){
	var t = jwd.trim().split(".");
	if(t.length > 2){
		return false;
	}
	
	if(!/^[0-9]{1,3}$/.test(t[0])){
		return false;
	}
	
	if(t.length == 2 && !/^[0-9]{1,20}$/.test(t[1])){
		return false;
	}
	
	return true;
}

function checkJwdScope(jwd, coord){
	var res = true;
	$.ajax({
		url:"<c:url value='/tollgate.dev'/>?method=checkJwdScope&jwd=" + jwd + "&xzqh=" + $("#editfeatureform input[id='gisxzqh']").val() + "&coord=" + coord,
		dataType:"json",
		cache:false,
		async:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			if(data["code"] == "1"){
				res = true;
			}else{
				res = false;
				alert("根据所选择的行政区划" + decodeURIComponent(data["message"]));
			}
		}
	});
	return res;
}

//需先引入form，再给form赋值，最后显示gis
function showGisMap(jd,wd){
	if(jd==""||wd==""){
		$("#pgisFrame").css("display", "none");
	}else{
		$("#pgisMsg").css("display", "block");
		$("#pgis").remove();
		var iframe = document.createElement("iframe");
		iframe.src = "gisfeatures.gis?method=featPosition";
		iframe.id="pgis";
		iframe.name="pgis";
		iframe.style.width="100%";
		iframe.style.height="1px";
		iframe.scrolling="no";
		iframe.frameBorder="0";
		if (iframe.attachEvent){
			iframe.attachEvent("onload", function(){
				$("#pgisMsg").css("display", "none");
				iframe.style.height="400px";
			});
		} else {
			iframe.onload = function(){
				$("#pgisMsg").css("display", "none");
				iframe.style.height="400px";
			};
		}
		$("#pgisFrame").append(iframe);
	}
}


//只根据经纬度信息，显示Gis信息
function showGisMapSimple(jd,wd){
	if(jd==""||wd==""){
		$("#pgisFrame").css("display", "none");
	}else{
		setFormvalSip("我的位置",jd,wd);
		$("#pgisMsg").css("display", "block");
		$("#pgis").remove();
		var iframe = document.createElement("iframe");
		iframe.src = "gisfeatures.gis?method=featPositionXY";
		iframe.id="pgis";
		iframe.name="pgis";
		iframe.style.width="100%";
		iframe.style.height="1px";
		iframe.scrolling="no";
		iframe.frameBorder="0";
		if (iframe.attachEvent){
			iframe.attachEvent("onload", function(){
				$("#pgisMsg").css("display", "none");
				iframe.style.height="400px";
			});
		} else {
			iframe.onload = function(){
				$("#pgisMsg").css("display", "none");
				iframe.style.height="400px";
			};
		}
		$("#pgisFrame").append(iframe);
	}
}


