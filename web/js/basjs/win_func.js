var subwinname;
var isclosesubwin = 1;
function closesubwin(){
  if(subwinname!=null&&isclosesubwin==1){
    subwinname.close();
  }
}

//��һ���µĴ���
function openwin(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-600)/2;
	windowwidth=(windowwidth-800)/2;
	if(scrollbars==null||scrollbars==false)
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=600,top="+windowheight+",left="+windowwidth);
	else
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=600,top="+windowheight+",left="+windowwidth);
	return subwinname;
}
//��һ���µ�ȫ������
function openfullwin(url,winname,scrollbars){
	var windowheight=screen.Height-60;
	var windowwidth =screen.width-10;
	if(scrollbars==null||scrollbars==false)
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width="+windowwidth+",height="+windowheight+",top=0,left=0");
	else
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width="+windowwidth+",height="+windowheight+",top=0,left=0");
	return subwinname;
}
//�гߴ�Ĵ򿪴���,daybook��by:kris.wang
//openmywin("url.jsp","200","500","")
//openmywin("url.jsp","","","")
function openmywin(url,width,height,scrollbars){
  if(width==null || width==""){
    width=800;
  }
  if(height==null|| height==""){
    height=600;
  }
  var windowheight=screen.height;
  var windowwidth =screen.width;
  windowheight=(windowheight-height)/2;
  windowwidth=(windowwidth-width)/2;

  if(scrollbars==null)
    subwinname = window.open(url,"","resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width="+width+",height="+height+",top="+windowheight+",left="+windowwidth);
  else
    subwinname = window.open(url,"","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width="+width+",height="+height+",top="+windowheight+",left="+windowwidth);

  return subwinname;
}

//��һ���µ�С����
function openwinhalf(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-300)/2;
	windowwidth=(windowwidth-400)/2;

	if(scrollbars==null)
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=250,height=130,top="+windowheight+",left="+windowwidth);
	else
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=250,height=130,top="+windowheight+",left="+windowwidth);

	return subwinname;
}
//-----------------------------
function openWindowByModal(url,vObject){
  window.showModalDialog(url,vObject,"dialogHeight:200px;dialogWidth:300px;dialogLeft:350;dialogTop:300;center:yes")
}

