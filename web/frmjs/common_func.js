var MSG_RANK_OK="0";//�ɹ�
var MSG_RANK_FE="1";//���ش���
var MSG_RANK_IE="2";//�м�����
var MSG_RANK_LE="3";//�ͼ�����

var subwinname; //�Ӵ���
var isclosesubwin = 1;//�Ƿ�ر��Ӵ���
var iscloseenterfun = 0;
var focusObject;
//-------------------------------
//  ���ܽ��ܣ���ȡ�ַ���ǰ��Ŀո�
//-------------------------------
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
//-------------------------------
//  ���ܽ��ܣ��س�ת��ΪTAB������
//-------------------------------
document.onkeydown=function enterToTab(){
  if(iscloseenterfun==0){
	  if(event.srcElement.type != 'submit' && event.srcElement.type!="image" 
		  	&& event.srcElement.type != 'textarea'&& event.keyCode == 13){
		    event.keyCode = 9;
		  }
  }
};

//-------------------------------
//  ���ܽ��ܣ���������ƶ������
//-------------------------------
function movelast()
{
  var e = event.srcElement;
  var r =e.createTextRange();
  r.moveStart('character',e.value.length);
  r.collapse(true);
  r.select();
}
//�۽�����һ���ؼ�
function goNext()
{
  if (event.keyCode==8)
  {
    if(event.srcElement.type=="text"||event.srcElement.type=="password")
    {
    }
    else
    {
      event.keyCode=0;
      event.returnValue=false;
    }
  }
  else
  {
    if (event.srcElement.name!="Submit")
    {
      if (event.keyCode==13)
      event.keyCode=9;
    }
  }
}
//-------------------------------
//  ���ܽ��ܣ�ɾ�������ؼ����е��ӿؼ�
//  ����˵����container ҳ�������ؼ�
//-------------------------------
function clearBodyById(container)
 {
 	while(container.childNodes.length>0)
 	{
   	container.removeChild(container.childNodes[0]);
 	}
 }
//-------------------------------
//  ���ܽ��ܣ�����Ƶ�ҳ��ؼ�����ʾָ����ɫ
//  ����˵����obj ҳ��ؼ�
//  ����ֵ  ���޷���ֵ
//-------------------------------
function mouse_over(obj){
  obj.style.backgroundColor="#ffff33"
}
//-------------------------------
//  ���ܽ��ܣ�����Ƴ�ҳ��ؼ�����ʾָ����ɫ
//  ����˵����obj ҳ��ؼ�
//  ����ֵ  ���޷���ֵ
//-------------------------------
function mouse_out(obj){
  obj.style.backgroundColor=""
}

function openwindialog(url,data){
	var ret = window.showModalDialog(url,data,
			"dialogWidth:840px;dialogHeight:650px;center:1;help:0;resizable:0;status:0;scroll:0;");
	return ret;
}

//-------------------------------
//  ���ܽ��ܣ��½�800*600����
//  ����˵����url URL��ַ;winname ��������;scrollbars �Ƿ��й�����
//  ����ֵ  ����������
//-------------------------------
function openwin800(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-600)/2;
	windowwidth=(windowwidth-800)/2;
	if(scrollbars==null)
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=510,top="+windowheight+",left="+windowwidth);
	else if(scrollbars=="no")
		subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=510,top="+windowheight+",left="+windowwidth);
	else
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=510,top="+windowheight+",left="+windowwidth);
	
	
	subwinname.focus();
	return subwinname;
}


function openwin800no(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-600)/2;
	windowwidth=(windowwidth-800)/2;
	if(scrollbars==null)
	 	return window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=510,top="+windowheight+",left="+windowwidth);
	else if(scrollbars=="no")
		return window.open(url,winname,"resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=510,top="+windowheight+",left="+windowwidth);
	else
		return window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=510,top="+windowheight+",left="+windowwidth);
	
	
	//subwinname.focus();
	//return subwinname;
}


//-------------------------------
//���ܽ��ܣ��½�1024*768����
//����˵����url URL��ַ;winname ��������;scrollbars �Ƿ��й�����
//����ֵ  ����������
//-------------------------------
function openwin(url,winname,scrollbars){
	return openwin1024(url,winname,scrollbars);
}

//-------------------------------
//  ���ܽ��ܣ��½�1024*768����
//  ����˵����url URL��ַ;winname ��������;scrollbars �Ƿ��й�����
//  ����ֵ  ����������
//-------------------------------
function openwin1024(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	if(windowheight<700||windowwidth<1000){
		openwin800(url,winname,scrollbars);
	}else{
		windowheight=(windowheight-768)/2;
		windowwidth=(windowwidth-1024)/2;
		if(scrollbars==null)
			subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=1014,height=680,top="+windowheight+",left="+windowwidth);
		else if(scrollbars=="no")
			subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=1014,height=680,top="+windowheight+",left="+windowwidth);
		else
			subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=1014,height=680,top="+windowheight+",left="+windowwidth);
		subwinname.focus();
		return subwinname;
	}
}
//-------------------------------
//���ܽ��ܣ��½�ȫ������
//����˵����url URL��ַ;winname ��������;scrollbars �Ƿ��й�����
//����ֵ  ����������
//-------------------------------
function openwinfull(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	if(scrollbars==null)
		subwinname = window.open(url,winname,"resizable=yes,fullscreen=yes,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no,directories=no,width="+(windowwidth-10)+",height="+(windowheight-90)+",top=0,left=0");
	else if(scrollbars=="no")
		subwinname = window.open(url,winname,"resizable=yes,fullscreen=yes,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width="+(windowwidth-10)+",height="+(windowheight-90)+",top=0,left=0");
	else
		subwinname = window.open(url,winname,"resizable=yes,fullscreen=yes,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no,directories=no,width="+(windowwidth-10)+",height="+(windowheight-90)+",top=0,left=0");
	subwinname.focus();
	return subwinname;
}
//-------------------------------
//  ���ܽ��ܣ��½�����Excel����
//  ����˵����url URL��ַ;winname ��������
//  ����ֵ  ����������
//-------------------------------
function openwinexcel(url,winname){
  subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=1,height=1,top=10,left=10");
  return subwinname;
}
//-------------------------------
//  ���ܽ��ܣ���������ѡ������
//  ����˵����obj �����ֶ�
//  ����ֵ  ����
//-------------------------------
function riqi(rq){
  gfPop.fPopCalendar(document.all[rq]);
  return false;
}
//-------------------------------
//���ܽ��ܣ���������ѡ������
//����˵����obj �����ֶ�
//����ֵ  ����
//-------------------------------
function riqiobj(rq){
gfPop.fPopCalendar(rq);
return false;
}
//-------------------------------
//  ���ܽ��ܣ���������ѡ������ʱ��
//  ����˵����obj ����ʱ���ֶ�
//  ����ֵ  ����
//-------------------------------
function riqitime(rq){
  gfPoptime.fPopCalendar(document.all[rq]);
  return false;
}

//-------------------------------
//���ܽ��ܣ���ʾҵ���ܷ��ش�����Ϣ
//����˵����strinfo ������Ϣ
//����ֵ  ����
//-------------------------------
function displayInfoHtml_new(xtlb,gnid,msg_rank,xh,msg)
{
	var strinfo="["+xtlb+gnid+msg_rank+"J"+xh+"]:"+msg;
	displayInfoHtml(strinfo);
}


//-------------------------------
//  ���ܽ��ܣ���ʾҵ���ܷ��ش�����Ϣ
//  ����˵����strinfo ������Ϣ
//  ����ֵ  ����
//-------------------------------
function displayInfoHtml()
{   
	var strinfo,strCommond;
	x=arguments.length;
	strinfo=arguments[0];
	strCommond=arguments[1];
	if(x==0){
		return false;
	}else if(x<2)
	{
		strCommond="";
	}
  if(strinfo==null) strinfo="";
  while(strinfo.indexOf("A~A~")>0) {
	  strinfo = strinfo.replace("A~A~", "\n");
  }
  var iPos = -1;
  var iPos1 = -1;
  var sLx = "-1";
  var sTsdh = "";
  var sCwdj = "";
  iPos = strinfo.indexOf("[");
  iPos1 = strinfo.indexOf("]:");
  var sTmp = strinfo;  
  while(iPos>=0&&iPos1>=0&&iPos1>iPos){
  	sTsdh = sTmp.substring(iPos,iPos1+2);
  	if(sTsdh.length<12){
  		sLx="-1";
  		break;
  	}
  	sCwdj = sTsdh.substring(7,8);
  	if(sLx == "0"||sLx=="-1"){
  		sLx = sCwdj;
  	}else{
  		if(parseInt(sCwdj)<parseInt(sLx)){
  			sLx = sCwdj;
  		}
  	}  
  	sTmp = sTmp.substr(iPos1 + 2);
  	iPos = sTmp.indexOf("[");
  	iPos1 = sTmp.indexOf("]:");  		
  }
  if(sLx=="0")sLx="2";
  else if(sLx=="1")sLx="3";
  else if(sLx=="2")sLx="1";
  else if(sLx=="3")sLx="4";
  else if(sLx=="-1")sLx="3";
  displayDialog(sLx,strinfo,strCommond);
}

//-------------------------------
//  ���ܽ��ܣ���ʾ�����ĶԻ���
//  ����˵����m-ͼ������ 1-��̾�� 2-�ɹ� 3-�� 4-�ʺ� 0-��
//  ����ֵ  ����
//-------------------------------
function displayDialog(m,xx_dialog,strCommond, c, title, i, confirm, cancel){
	var color="",message="",image="";
	if(m==1){
		color="#FFE752";
		message="��ʾ";
		image="info.gif";
	}else	if(m==2){
		color="#73AA10";
		message="�ɹ�";
		image="right.gif";
	}else	if(m==3){
		color="#F02B2B";
		message="����";
		image="err.gif";
	}else	if(m==4){
		color="#73A2C6";
		message="ѯ��";
		image="ask.gif";
	} else if (m == 5) {
        color = c;
        message = title;
        image = i;
    } else{
		color="#888888";
		message="����";
		image="info.gif";
	}

    var s="";
    s+='<iframe src="" style="background-color=transparent;width:390px;height:180px;top:0px;left:0px;position:absolute;visibility:inherit;z-index:-1;" frameborder=0></iframe>';
    s+='<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background:'+color+';height:27px;font-size:14px;font-weight:bold;padding:1px 0 0 11px;"><tr><td>'+message+'</td></tr></table>';
    s+='<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="text_12"><tr>';
    if (image) {
        s+='<td width="100" rowspan="2" align="center">';
        s+='<img src="frmimage/'+image+'" width="50" height="46" />';
        s+='</td>';
    }
    s+='<td height="100" class="text_12 text_bold" align="center">&nbsp;&nbsp;'+xx_dialog+'&nbsp</td></tr></table>';

    Common_Func_NS.openNewDiv('newDiv',s,strCommond, confirm, cancel);return false;
}

var docEle = function()
{
    return document.getElementById(arguments[0]) || false;
};

var Common_Func_NS = {

    init : function() {

    },

    openNewDiv : function(_id,s,strCommond, confirm, cancel)
    {
        var s=s;
        var m = "mask";
        if (docEle(_id)) document.body.removeChild(docEle(_id));
        if (docEle(m)) document.body.removeChild(docEle(m));

        //mask���ֲ�
        var newMask = document.createElement("div");
        newMask.id = m;
        newMask.style.position = "absolute";
        newMask.style.zIndex = "1";
        var _scrollWidth = Math.max(document.body.scrollWidth,document.documentElement.scrollWidth);
        var _scrollHeight = Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
        newMask.style.width = _scrollWidth + "px";
        newMask.style.height = _scrollHeight + "px";
        newMask.style.top = "0px";
        newMask.style.left = "0px";
        newMask.style.background = "#333333";
        newMask.style.filter = "alpha(opacity=0)";
        newMask.style.opacity = "0.60";
        newMask.style.zIndex=99;
        document.body.appendChild(newMask);

        //�µ�����
        var newDiv = document.createElement("div");
        newDiv.id = _id;
        newDiv.style.position = "absolute";
        newDiv.style.zIndex = "9999";
        var newDivWidth = 410;
        var newDivHeight = 200;
        newDiv.style.width = newDivWidth + "px";
        newDiv.style.height = newDivHeight + "px";
        newDiv.style.top = (document.body.scrollTop + document.body.clientHeight/2 - newDivHeight/2) + "px";
        newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth/2 - newDivWidth/2)+ "px";
        newDiv.style.background = "#EFEFEF";
        newDiv.style.border = "10px solid #cccccc";
        newDiv.style.padding = "5px";
        newDiv.innerHTML = s;

        document.body.appendChild(newDiv);

        //�������������
        function newDivCenter()
        {
            newDiv.style.top = (document.body.scrollTop + document.body.clientHeight/2 - newDivHeight/2) + "px";
            newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth/2 - newDivWidth/2)+ "px";
        }
        if(document.all)
        {
            window.attachEvent("onscroll",newDivCenter);
        } else{
            window.addEventListener('scroll',newDivCenter,false);
        }
        //�ر���ͼ���mask���ֲ�
        focusObject=document.activeElement;
        var newA = document.createElement("a");
        newA.href = "#";
        var close_div=
            '<table width="380" border="0" cellspacing="0" cellpadding="0" align="center">' +
                '<tr>' +
                    '<td align="center">' +
            '           <input id="div_alert_comm" type="button" name="Submit" onkeydown="this.onclick();backfocus();" value=" �� �� " class="button">' +
            '       </td>' +
            '   </tr>' +
            '</table>';
        if(strCommond=="confirm"){
            close_div=
                '<table width="380" border="0" cellspacing="0" cellpadding="0" align="center">' +
                '   <tr>' +
                '       <td align="center">' +
                '           <input id="div_alert_comm" type="button" onkeydown="this.onOKclick();backfocus();"  name="Submit" value=" ȷ �� " class="button" onclick="">&nbsp;&nbsp;<input id="div_alert_cancel" type="button" onkeydown="this.click();backfocus();"  name="Cancel" value=" ȡ �� " class="button" onclick="">' +
                '       </td>' +
                '   </tr>' +
                '</table>';
        }

        newA.innerHTML = close_div;

        newDiv.appendChild(newA);

        document.getElementById("div_alert_comm").focus();
        //alert(document.getElementById("div_alert_comm").Value);

        $("body").on("click", "#div_alert_comm", function() {
            if (confirm != null) {
                eval(confirm);
            }
            if(document.all)
            {
                window.detachEvent("onscroll",newDivCenter);
            }else{
                window.removeEventListener('scroll',newDivCenter,false);
            }
            document.body.removeChild(docEle(_id));
            document.body.removeChild(docEle(m));
            if(strCommond!="" && strCommond!="confirm"){
                eval(strCommond);
            }
            return true;
        });
        $("body").on("click", "#div_alert_cancel", function() {
            if (cancel != null) {
                eval(cancel);
            }
            if(document.all)
            {
                window.detachEvent("onscroll",newDivCenter);
            }else{
                window.removeEventListener('scroll',newDivCenter,false);
            }
            document.body.removeChild(docEle(_id));
            document.body.removeChild(docEle(m));
            if(strCommond!="" && strCommond!="confirm"){
                eval(strCommond);
            }
            return false;
        });
    }

};

(function() {
    Common_Func_NS.init();
});

function backfocus()
{
	focusObject.focus();
}
//-------------------------------
//  ���ܽ��ܣ��滻����
//  ����˵����strinfo ������Ϣ
//  ����ֵ  ����
//-------------------------------
function replaceAll(strinfo,oldvalue,newvalue)
{
  var result = strinfo;
  if(strinfo==null) result="";
  while(result.indexOf(oldvalue)>0) {
	  result = result.replace(oldvalue, newvalue);
  }
  return result;
}
//-------------------------------
//  ���ܽ��ܣ��رյ�ǰҳ��
//  ����ֵ  ����
//-------------------------------
function quit(){
		window.close();
}
function closesubwin(){
  if(subwinname!=null&&isclosesubwin==1){
    subwinname.close();
  }
}
//-------------------------------
//  ���ܽ��ܣ�15λ���֤������ת18λ���֤������
//  ����˵����zjhm 15λ���֤������
//  ��    �أ�18λ���֤������
//-------------------------------
function id15to18(zjhm) {
    var strJiaoYan = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
                               "2");
    var intQuan = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
                            1);
    var ll_sum = 0;
    var i;
    var ls_check;
    zjhm = zjhm.substring(0, 6) + "19" + zjhm.substring(6);
    for (i = 0; i <= 16; i++) {
        ll_sum = ll_sum + (parseFloat(zjhm.substr(i, 1))) * intQuan[i];
    }
    ll_sum = ll_sum % 11;
    zjhm = zjhm + strJiaoYan[ll_sum];
    return zjhm;
}
//-------------------------------
//  ���ܽ��ܣ���ȡĳ���ַ����к��������ַ��ĸ���
//  ����˵����strValue ĳ�ַ���;strChar �����ַ�
//  ��    �أ�����
//-------------------------------
function getCharCounts(strValue,strChar){
	var iCount = 0;
	var i = strValue.indexOf(strChar,0);
	while(i>=0){
		iCount = iCount + 1;
		i = strValue.indexOf(strChar,i + 1);
	}
	return iCount;
}
function MessureString(str,width){
  var s;
  var w=0;

  for(var i=0;i<str.length;i++){
    s = str.charCodeAt(i);
    if(s<256){
      w = w + 0.5;
    }
    else{
      w = w + 1;
    }
    if(w>width){
      return i;
    }
  }
  return 0;
}
//-------------------------------
//  ���ܽ��ܣ���ʾ�����ַ���
//  ����˵����b_width �����ַ�������;ԭʼ�ַ���
//  ��    ��: ҳ����������ַ���
//-------------------------------
function outputSkipStr(b_width, Str){
      t_Str = "";
      var pos =MessureString(Str,b_width);
      if(pos>0){
              t_Str = "<font color =#00B5B5 style='font-size: 9pt'><a title = '"+Str+"'>"
              t_Str = t_Str + Str.substr(0,pos);
              t_Str = t_Str+"</a></font>"
      }else{
      		t_Str = "<font style='font-size: 9pt'>" + Str +"</font>";
      }
      document.write(t_Str.fontsize('-1'));
}
//-------------------------------
//  ���ܽ��ܣ�ֻ���������������� onKeypress='fCheckInputInt()'
//  ��    ��: ��
//-------------------------------
function fCheckInputInt(){
  if (event.keyCode < 48 || event.keyCode > 57)
    event.returnValue = false;
}

function fCheckInputIntOrBackSpace(){
  if ((event.keyCode < 48 && event.keyCode != 8) || event.keyCode > 57)
    event.returnValue = false;
}

//-------------------------------
//���ܽ��ܣ�ֻ����С������
//��    ��: ��
//-------------------------------
function fCheckInputFloat(){
	  if (event.keyCode < 46 || event.keyCode > 57|| event.keyCode ==47)
	    event.returnValue = false;
	}
function fCheckInputFs(){
	  if (event.keyCode < 45 || event.keyCode > 57|| event.keyCode ==47)
		    event.returnValue = false;
}
/**
 * zhoujn �����֤�������ȡ�Ա�
 * zhoujn 20090123
 */

function getxbbysfzmhm(sfzmhm){
	var xb='';
    if (sfzmhm.length==15){
    	xb=sfzmhm.substr(14,1);
    } else {
        xb=sfzmhm.substr(16,1);
    }
    if (xb % 2==1){
        return "1";
    }else{
        return "2";
        
    }    
}

/**
 * zhoujn �����֤�������ȡ��������
 * zhoujn 20090123 
 */
function getcsrqbysfzmhm(sfzmhm){
	var csrq='';
    if (sfzmhm.length==15){
    	csrq="19"+sfzmhm.substr(6,2)+"-"+sfzmhm.substr(8,2)+"-"+sfzmhm.substr(10,2);
    } else {
    	csrq=sfzmhm.substr(6,4)+"-"+sfzmhm.substr(10,2)+"-"+sfzmhm.substr(12,2);
    }	
    return csrq;
}

/******************************************************************************************
 * �������ǿ��
 ******************************************************************************************/
checkPasswordLevel = function(strPassword)
{

	//alert("enter ");
	//check length
	var result = 0;
	if ( strPassword.length == 0)
		result += 0;
	else if ( strPassword.length<8 && strPassword.length >0 )
		result += 5;
	else if (strPassword.length>10)
		result += 15;
	else
		result += 10;
	//alert("��鳤��:"+strPassword.length+"-"+result);
	
	//check letter
	var bHave = false;
	var bAll = false;
	var capital = strPassword.match(/[A-Z]{1}/);//�Ҵ�д��ĸ
	var small = strPassword.match(/[a-z]{1}/);//��Сд��ĸ
	if ( capital == null && small == null )
	{
		result += 0; //û����ĸ
		bHave = false;
	}
	else if ( capital != null && small != null )
	{
		result += 20;
		bAll = true;
	}
	else
	{	
		result += 10;
		bAll = true;
	}
	//alert("�����ĸ��"+result);
	
	//�������
	var bDigi = false;
	var digitalLen = 0;
	for ( var i=0; i<strPassword.length; i++)
	{
	
		if ( strPassword.charAt(i) <= '9' && strPassword.charAt(i) >= '0' )
		{
			bDigi = true;
			digitalLen += 1;
			//alert(strPassword[i]);
		}
		
	}
	if ( digitalLen==0 )//û������
	{
		result += 0;
		bDigi = false;
	}
	else if (digitalLen>2)//2����������
	{
		result += 20 ;
		bDigi = true;
	}
	else
	{
		result += 10;
		bDigi = true;
	}
	//alert("���ָ�����" + digitalLen);
	//alert("������֣�"+result);
	
	//���ǵ����ַ�
	var bOther = false;
	var otherLen = 0;
	for (var i=0; i<strPassword.length; i++)
	{
		if ( (strPassword.charAt(i)>='0' && strPassword.charAt(i)<='9') ||  
			(strPassword.charAt(i)>='A' && strPassword.charAt(i)<='Z') ||
			(strPassword.charAt(i)>='a' && strPassword.charAt(i)<='z'))
			continue;
		otherLen += 1;
		bOther = true;
	}
	if ( otherLen == 0 )//û�зǵ����ַ�
	{
		result += 0;
		bOther = false;
	}
	else if ( otherLen >1)//1�����Ϸǵ����ַ�
	{
		result +=25 ;
		bOther = true;
	}
	else
	{
		result +=10;
		bOther = true;
	}
	//alert("���ǵ��ʣ�"+result);
	//�����⽱��
	if ( bAll && bDigi && bOther)
		result += 5;
	else if (bHave && bDigi && bOther)
		result += 3;
	else if (bHave && bDigi )
		result += 2;
	//alert("�����⽱����"+result);

	var level = "";
	//���ݷ�����������ǿ�ȵĵȼ�
	if ( result >=90 )
		level = "rank r7";
	else if ( result>=80)
		level = "rank r6";
	else if ( result>=70)
		level = "rank r5";
	else if ( result>=60)
		level = "rank r4";
	else if ( result>=50)
		level = "rank r3";
	else if ( result>25)
		level = "rank r2";
	else if ( result>0)
		level = "rank r1";
	else
		level = "rank r0";

//		alert("return:"+level);
	return level.toString();
}
/******************************************************************************************
 * ��������ǿ����ʽ
 ******************************************************************************************/
setPasswordLevel = function(passwordObj, levelObj)
{
	var level = "rank r0";	
	level = checkPasswordLevel(passwordObj.value.trim());
	levelObj.className = level;
	//alert("level"+level);
}

function MM_preloadImages() { //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}	
/*
function show_Form(idNum,pageNum) {
	for(var i=1;i<=pageNum;i++){
		var divName = "g" + i;
		var idName = "id" + i;			
		var imageName = "Image"+i;
		if(idNum==i){				
			document.all[divName].style.display = "block";				
			document.all[idName].style.background="#D3D1CC";
			MM_swapImage(imageName,'','images/huijiao.gif',1);			
		}else{
			document.all[divName].style.display = "none";
			document.all[idName].style.background="#EDEDDC";
			MM_swapImage(imageName,'','images/huijiao2.gif',1); 
		}
	}
}
*/
//�滻A~A~Ϊ'\n'
function replaceEnter(strinfo)
{
  if(strinfo==null) strinfo="";
  while(strinfo.indexOf("A~A~")>=0) {
	  strinfo = strinfo.replace("A~A~", "\r\n");
  }
  return strinfo;
}

//���ͷ�����������й�����
function setTableOverflow(){
	var t_n =document.getElementById("table_num").innerHTML; 
	var table_height =document.getElementById("infot_"+t_n).innerHTML; 
	var th = document.getElementById("tablehead_"+t_n);
	var tb = document.getElementById("tablebody_"+t_n);
	var bodyh = table_height;

	var con = 0
	if(tb.rows.length!=0){
		con=tb.rows.item(0).cells.length;
	}
	var ot = document.getElementById("infot_"+t_n).clientHeight;
	document.getElementById("cov_"+t_n).style.height = bodyh-ot+"px";
	if(con==""){

	}else{
		for(i=0;i<con;i++){
			th.childNodes[0].rows[0].cells[i].width = tb.childNodes[0].rows[0].cells[i].width;
			var th_width=th.rows.item(0).cells[i].style.width;
			var tb_width= tb.rows.item(0).cells[i].clientWidth+"px";
		}
		//alert(th_width);
		th.style.width = tb.clientWidth;//�����
	}
	
	}

//�Զ���Ӧ����С
function Resize(){
	setTimeout(setTableOverflow,1)
}
//ǿ�ưѴ�дת����Сд
function fCheckInputLow(){
  if(event.keyCode>=65 && event.keyCode<=90)
    event.keyCode=event.keyCode+32;
}
//ǿ�ư�Сдת���ɴ�д
function fCheckInputUp(){
  if(event.keyCode>=97 && event.keyCode<=122)
    event.keyCode=event.keyCode-32;
}
//-------------------------------
//  ���ܽ��ܣ�������֤��¼�봰�� 
//  ��   �أ� "0"������֤��ʧ�� ��������4λ��֤��
//-------------------------------
function showIndentCode(){
    var result = showModalDialog("commonCode.frm?method=genIdentCode",""," scroll:no;status:0;help:0;resizable:0;dialogHeight:200px;dialogWidth:360px;unadorne:0;edge:raised;");
    return result;
}	  

//ȡ�����
function getrandom(){
	var maxNumber = 1000; 
	var randomNumber = Math.round(maxNumber * Math.random());
	return randomNumber;
}	

function openwinDef(url,winname,width,height){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-height)/2;
	windowwidth=(windowwidth-width)/2;
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=auto,status=yes,toolbar=no,menubar=no,location=no,directories=no,width="+(width-10)+",height="+(height-10)+",top="+windowheight+",left="+windowwidth);
	
	
	subwinname.focus();
	return subwinname;
}

//������ѡ����ʱ����ʽ�仯
var mouseOver = function(me){
	$(me).addClass("over");
}
var mouseOut = function(me){
	$(me).removeClass("over");
}