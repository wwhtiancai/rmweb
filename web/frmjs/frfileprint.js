//��ʼ����������Ԫ�ص�ֵ
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
//�����û�ѡ�񣬲��رնԻ���
function closeWin(formname,sel){
	 //�޸����� ָ��ʹ�õ�Form
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
//�򿪱༭ҳ��
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
		if(strBackValue!="N"){//�û�ѡ��ȷ��
		    //����Ԫ�ظ�ֵ
			popuPageField(strBackValue);
	        //������ӡ����
			savePrtDataCzlx2(xh,keyvalue,formobj);
		     
		}else{//�û�ѡ��ȡ��
			
		}
	}  
}
//  ��д��ʷ���ӡ��ģʽ
//  xh=ģ����ţ�keyvalue=  (key=value)
function _savePrtDataCzlx2(xh,keyvalue,fromobj){
	//���ʹ�ӡ����   
  var url1=window.location.href; 
  url1=url1.substring(0,url1.indexOf("trffweb"));  
  var httpUrl=url1+"trffweb/frp.frm?method=pd&czlx=2&xh="+xh+"&"+keyvalue; 
  
  fromobj.target="printIframe";
  fromobj.action=httpUrl;
  fromobj.submit();
  
}
/**
 * ��ӡ��������������������������Ϊ���������ӡ���򲻼�¼��־
 * Ĭ��Ϊ��¼��־����ʾԤ�����棬���Ҫ�Զ��壬��ʹ��printFormDataThroughEditer����
 * @param xh   
 * @param keyvalue  ʹ��key=value�ִ��ĸ�ʽ
 * @param fromobj
 * @return
 */
function savePrtDataCzlx2(xh,keyvalue,fromobj){
	//���ʹ�ӡ����   
	var paraStr="&xh="+xh+"&"+keyvalue;
	var showFlag="1";
	var logFlag="1";
	printFormData(paraStr,fromobj,showFlag,logFlag);
}
//��Ӧframe�е���
function printpd(hisid){	   
    var url1=window.location.href; 
    url1=url1.substring(0,url1.indexOf("trffweb"));  
    var httpUrl=url1+"trffweb/frp.frm?method=pd&czlx=3&hisid="+hisid; 
    tmriPrint.doPrintReport(httpUrl,"1","1");
    displayInfoHtml("[0091083J8]:��ӡ��ϵͳ�������ô�ӡ����(HISID:"+hisid+")");
}
function openModalDialog(v1,v2,v3){
	return window.showModalDialog(v1,v2,v3);
}



/**
 * ��ӡ�ͻ���������
 * ���� 2009-04-08
 * ��ӡForm�е�����,���ж��û��Ƿ��ӡ
 * �����ӡ��Ҫ��д����־,����д��־�����������
 * @param paraStr �����ִ���ʹ��'&'��ͷ
 *                ���ٰ���xh(ģ�����)��keyvalue��ģ�涨�������������Ӧ��ֵ �綨��wfbh������wfbh��ֵ 32008980
 * @param fromobj �������ݵ�Form����
 * @param editerUrl �༭ҳ���ַ,��Ϊ��ʱ���򿪱༭���� 0: ����  ����:��
 * @param showFlag �Ƿ�Ԥ�� 1����ʾ��Ԥ����2����ʾ��Ԥ��
 * @param logFlag �Ƿ��¼��־,1:��¼ 0:����¼
 * @return
 */
function printFormDataThroughEditer(paraStr,fromobj,editerUrl,showFlag,logFlag){
	//���ʹ�ӡ����
  var userSelection='N';
  if(editerUrl=='0'){//����
	  userSelection='Y';
  }else{//��
	  userSelection=openEditerPage(editerUrl,fromobj);
  }
  if(userSelection=="Y"){//���ʹ�ӡ����
	  printFormData(paraStr,fromobj,showFlag,logFlag);
  }
}
/**
 * ��ӡ�ͻ�����������ͬprintFormDataThroughEditer�������򿪱༭����
 * ��ӡForm�е�����,���ж��û��Ƿ��ӡ
 * �����ӡ��Ҫ��д����־,����д��־�����������
 * 
 * @param paraStr �����ִ���ʹ��'&'��ͷ
 *                ���ٰ���xh(ģ�����)��keyvalue��ģ�涨�������������Ӧ��ֵ �綨��wfbh������wfbh��ֵ 32008980
 * @param fromobj �������ݵ�Form����
 * @param showFlag �Ƿ�Ԥ�� 1����ʾ��Ԥ����2����ʾ��Ԥ��
 * @param logFlag �Ƿ��¼��־,1:��¼ 0:����¼
 * @return
 */
function printFormData(paraStr,fromobj,showFlag,logFlag){

	  var strValue="";    //�����ִ�
	  var elem=fromobj.elements;
	  var print_result;
	 
	  //�ϳ�����
	  
	  for(var i=0;i<elem.length;i++){
		  if(i>0){strValue=strValue+"~;~";}
		  strValue=strValue+elem[i].name+"="+elem[i].value;
	  }
	  //��������
	  var url1=window.location.href; 
	  url1=url1.substring(0,url1.indexOf("trffweb"));  
	  var httpUrl=url1+"trffweb/frp.frm?method=pd&czlx=4"+paraStr;
	  tmriPrint.doPrintReportPost(httpUrl,strValue,showFlag,'1');
	  
	  //�����ӡ���
    print_result=tmriPrint.print_result;//��ӡ�������ȡ��ӡ�ɹ�����ǣ�1����ʾ��ӡ�ɹ���0����ʾδ��ӡ������Ϊ�׳��쳣
    //alert("print result:"+print_result);
    if(print_result=='1' && logFlag=='1'){
  	  //����д����ʷ���ݵ�����
  	  var httpUrl2=url1+"trffweb/frp.frm?method=pd&respbj=N&czlx=2"+paraStr;
  	  fromobj.target="printIframe";
  	  fromobj.action=httpUrl2;
  	  fromobj.submit();  
    }
}

//�򿪱༭ҳ�棬�����û�ѡ���־
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
			//�û�ѡ��ȷ��
		    //����Ԫ�ظ�ֵ
			popuPageField(strBackValue);
			return 'Y';
		}else{
			//�û�ѡ��ȡ��
			return 'N';
		}
	}else{
		//����ֵ��Ч
		return 'N';
	}  
}
function saveVehDadPrintData(xh,keyvalue,fromobj){
	//���ʹ�ӡ����   
	var paraStr="&xh="+xh+"&"+keyvalue;
	var showFlag="2";
	var logFlag="1";
	printFormData(paraStr,fromobj,showFlag,logFlag);
}
