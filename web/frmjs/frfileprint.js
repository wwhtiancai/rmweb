//初始化界面输入元素的值
function popuPageField(fv){
    var array=fv.split("|");
    for(var i=0;i<array.length;i++){
        var pairarray=pairarray=array[i].split("=");
        try{
            document.all[pairarray[0]].value=pairarray[1];
        }catch(err){
        }
    }
}
//接收用户选择，并关闭对话框
function closeWin(formname,sel){
	 //修改这行 指定使用的Form
     var elem=formname.elements;
	 if(sel=="Y"){
		 var strValue="";
	     for(var i=0;i<elem.length;i++){
	           if(i>0){strValue=strValue+"|";}
		       strValue=strValue+elem[i].name+"="+elem[i].value;
		 } 
	    
	     window.returnValue=strValue;
     }
	 if(sel=="N"){
		 window.returnValue="N";
     }
     window.close();
}
//打开编辑页面
function openPrintEditPage(strUrl,xh,keyvalue,formobj){
	var elem=formobj.elements;
	var strValue="";
	var strBackValue;


	for(var i=0;i<elem.length;i++){
		if(elem[i].type=="hidden"){
			 if(i>0){strValue=strValue+"|";}
		     strValue=strValue+elem[i].name+"="+elem[i].value;
		}
	}
	strBackValue=openModalDialog(strUrl,strValue,"dialogWidth:600px;dialogHeight:550px;dialogTop:100px;dialogLeft:200px");
	//alert(strBackValue);
	if (typeof(strBackValue)!="undefined"){
		if(strBackValue!="N"){//用户选择确认
		    //界面元素赋值
			popuPageField(strBackValue);
	        //发出打印请求
			savePrtDataCzlx2(xh,keyvalue,formobj);
		     
		}else{//用户选择取消
			
		}
	}  
}
//  先写历史后打印的模式
//  xh=模版序号，keyvalue=  (key=value)
function _savePrtDataCzlx2(xh,keyvalue,fromobj){
	//发送打印请求   
  var url1=window.location.href; 
  url1=url1.substring(0,url1.indexOf("trffweb"));  
  var httpUrl=url1+"trffweb/frp.frm?method=pd&czlx=2&xh="+xh+"&"+keyvalue; 
  
  fromobj.target="printIframe";
  fromobj.action=httpUrl;
  fromobj.submit();
  
}
/**
 * 打印操作的升级方法，升级的内容为：如果不打印，则不记录日志
 * 默认为记录日志和显示预览界面，如果要自定义，则使用printFormDataThroughEditer方法
 * @param xh   
 * @param keyvalue  使用key=value字串的格式
 * @param fromobj
 * @return
 */
function savePrtDataCzlx2(xh,keyvalue,fromobj){
	//发送打印请求   
	var paraStr="&xh="+xh+"&"+keyvalue;
	var showFlag="1";
	var logFlag="1";
	printFormData(paraStr,fromobj,showFlag,logFlag);
}
//响应frame中调用
function printpd(hisid){	   
    var url1=window.location.href; 
    url1=url1.substring(0,url1.indexOf("trffweb"));  
    var httpUrl=url1+"trffweb/frp.frm?method=pd&czlx=3&hisid="+hisid; 
    tmriPrint.doPrintReport(httpUrl,"1","1");
    displayInfoHtml("[0091083J8]:打印子系统已升级该打印方法(HISID:"+hisid+")");
}
function openModalDialog(v1,v2,v3){
	return window.showModalDialog(v1,v2,v3);
}



/**
 * 打印客户端主方法
 * 武红斌 2009-04-08
 * 打印Form中的数据,并判断用户是否打印
 * 如果打印且要求写入日志,则发送写日志请求给服务器
 * @param paraStr 参数字串，使用'&'开头
 *                至少包含xh(模版序号)、keyvalue：模版定义的数据主键对应的值 如定义wfbh，则传入wfbh的值 32008980
 * @param fromobj 容纳数据的Form对象
 * @param editerUrl 编辑页面地址,不为空时将打开编辑窗口 0: 不打开  其他:打开
 * @param showFlag 是否预览 1：表示有预览，2：表示无预览
 * @param logFlag 是否记录日志,1:记录 0:不记录
 * @return
 */
function printFormDataThroughEditer(paraStr,fromobj,editerUrl,showFlag,logFlag){
	//发送打印请求
  var userSelection='N';
  if(editerUrl=='0'){//不打开
	  userSelection='Y';
  }else{//打开
	  userSelection=openEditerPage(editerUrl,fromobj);
  }
  if(userSelection=="Y"){//发送打印请求
	  printFormData(paraStr,fromobj,showFlag,logFlag);
  }
}
/**
 * 打印客户端主方法，同printFormDataThroughEditer，但不打开编辑窗口
 * 打印Form中的数据,并判断用户是否打印
 * 如果打印且要求写入日志,则发送写日志请求给服务器
 * 
 * @param paraStr 参数字串，使用'&'开头
 *                至少包含xh(模版序号)、keyvalue：模版定义的数据主键对应的值 如定义wfbh，则传入wfbh的值 32008980
 * @param fromobj 容纳数据的Form对象
 * @param showFlag 是否预览 1：表示有预览，2：表示无预览
 * @param logFlag 是否记录日志,1:记录 0:不记录
 * @return
 */
function printFormData(paraStr,fromobj,showFlag,logFlag){

	  var strValue="";    //数据字串
	  var elem=fromobj.elements;
	  var print_result;
	 
	  //合成请求串
	  
	  for(var i=0;i<elem.length;i++){
		  if(i>0){strValue=strValue+"~;~";}
		  strValue=strValue+elem[i].name+"="+elem[i].value;
	  }
	  //发送请求
	  var url1=window.location.href; 
	  url1=url1.substring(0,url1.indexOf("trffweb"));  
	  var httpUrl=url1+"trffweb/frp.frm?method=pd&czlx=4"+paraStr;
	  tmriPrint.doPrintReportPost(httpUrl,strValue,showFlag,'1');
	  
	  //处理打印结果
    print_result=tmriPrint.print_result;//打印结束后获取打印成功与否标记，1：表示打印成功，0：表示未打印，其他为抛出异常
    //alert("print result:"+print_result);
    if(print_result=='1' && logFlag=='1'){
  	  //发送写入历史数据的请求
  	  var httpUrl2=url1+"trffweb/frp.frm?method=pd&respbj=N&czlx=2"+paraStr;
  	  fromobj.target="printIframe";
  	  fromobj.action=httpUrl2;
  	  fromobj.submit();  
    }
}

//打开编辑页面，返回用户选择标志
function openEditerPage(strUrl,formobj){
	var elem=formobj.elements;
	var strValue="";
	var strBackValue;
	for(var i=0;i<elem.length;i++){
			 if(i>0){strValue=strValue+"|";}
		     strValue=strValue+elem[i].name+"="+elem[i].value;
	}
	strBackValue=openModalDialog(strUrl,strValue,"dialogWidth:600px;dialogHeight:550px;dialogTop:100px;dialogLeft:200px");
	if (typeof(strBackValue)!="undefined"){
		if(strBackValue!="N"){
			//用户选择确认
		    //界面元素赋值
			popuPageField(strBackValue);
			return 'Y';
		}else{
			//用户选择取消
			return 'N';
		}
	}else{
		//返回值无效
		return 'N';
	}  
}
function saveVehDadPrintData(xh,keyvalue,fromobj){
	//发送打印请求   
	var paraStr="&xh="+xh+"&"+keyvalue;
	var showFlag="2";
	var logFlag="1";
	printFormData(paraStr,fromobj,showFlag,logFlag);
}
