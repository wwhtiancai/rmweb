	/*
	��Js�������ڴ���һ���Զ����ȷ�ϴ���,
	���幦�ܰ���:�Զ��崰�ڱ���,�Զ��崰������,�Ƿ���ʾȡ����ť,����λ���趨
	
	Author:Jedliu
	Blog  :Jedliu.cublog.cn 
	����ҳת���뱣����Ȩ��Ϣ,ʵ��ʹ��ʱ���Գ�ȥ����Ϣ��
	*/
	function get_width(){
		return (document.body.clientWidth+document.body.scrollLeft);
	}
	function get_height(){
		return (document.body.clientHeight+document.body.scrollTop);
	}
	function get_left(w){
		var bw=document.body.clientWidth;
		var bh=document.body.clientHeight;
		w=parseFloat(w);
		return (bw/2-w/2+document.body.scrollLeft);
	}
	function get_top(h){
		var bw=document.body.clientWidth;
		var bh=document.body.clientHeight;
		h=parseFloat(h);
		return (bh/2-h/2+document.body.scrollTop);
	}
	function create_mask(){//�������ֲ�ĺ���
		var mask=document.createElement("div");
		mask.id="mask";
		mask.style.position="absolute";
		mask.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=4,opacity=25)";//IE�Ĳ�͸������
		mask.style.opacity=0.4;//Mozilla�Ĳ�͸������
		mask.style.background="black";
		mask.style.top="0px";
		mask.style.left="0px";
		mask.style.width=get_width();
		mask.style.height=get_height();
		mask.style.zIndex=1000;
		document.body.appendChild(mask);
	}
	function create_msgbox(w,h,t){//���������Ի���ĺ���
		var box=document.createElement("div")	;
		box.id="msgbox";
		box.style.position="absolute";
		box.style.width=w;
		box.style.height=h;
		box.style.overflow="visible";
		box.innerHTML=t;
		box.style.zIndex=1001;
		document.body.appendChild(box);
		re_pos();
	}
	function re_mask(){
		/*
		�������ֲ�Ĵ�С,ȷ���ڹ����Լ����ڴ�С�ı�ʱ�����Ը������е�����
		*/
		var mask=document.getElementById("mask")	;
		if(null==mask)return;
		mask.style.width=get_width()+"px";
		mask.style.height=get_height()+"px";
	}
	function re_pos(){
		/*
		���ĵ����Ի�����λ��,ȷ���ڹ����Լ����ڴ�С�ı�ʱһֱ��������ҳ�����м�
		*/
		var box=document.getElementById("msgbox");
		if(null!=box){
			var w=box.style.width;
			var h=box.style.height;
			box.style.left=get_left(w)+"px";
			box.style.top=get_top(h)+"px";
		}
	}
	function remove(){
		/*
		������ֲ��Լ������ĶԻ���
		*/
		var mask=document.getElementById("mask");
		var msgbox=document.getElementById("msgbox");
		if(null==mask&&null==msgbox)return;
		document.body.removeChild(mask);
		document.body.removeChild(msgbox);
	}
	function msgbox(title,text,func,cancel,focus){
		/*		
		�����б�˵��:
		title :�����Ի���ı���,�������������25���ַ���,����ᵼ����ʾͼƬ���쳣															
		text  :�����Ի��������,����ʹ��HTML����,����<font color='red'>ɾ��ô?</font>,���ֱ�Ӵ��뺯��,ע��ת��
		func  :�����Ի�����ȷ�Ϻ�ִ�еĺ���,��Ҫдȫ����������,����add(),���ֱ�Ӵ��뺯��,ע��ת�塣
		cancel:�����Ի����Ƿ���ʾȡ����ť,Ϊ�յĻ�����ʾ,Ϊ1ʱ��ʾ
		focus :�����Ի��򽹵��λ��,0������ȷ�ϰ�ť��,1��ȡ����ť��,Ϊ��ʱĬ����ȷ�ϰ�ť��
		
		Author:Jedliu
		Blog  :Jedliu.cublog.cn 
		����ҳת���뱣����Ȩ��Ϣ,ʵ��ʹ��ʱ���Գ�ȥ����Ϣ��
		*/
		create_mask();
		var temp="<table width=\"355\" height=\"127\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"font:14px Verdana, Geneva, Arial, Helvetica, sans-serif\">";
		temp+="<tr><td background=\"msgbox/alert_01.gif\" width=\"355\" height=\"22\" style=\"padding-left:8px;padding-top:2px;font-weight: bold;color:white;\">"+title+"</td></tr>";
		temp+="<tr><td background=\"msgbox/alert_02.gif\" width=\"355\" height=\"75\" style=\"padding-left:6px;padding-right:2px;padding-bottom:10px;\">&nbsp;<img src=\"msgbox/alert_mark.gif\">&nbsp;"+text+"</td>";
		temp+="</tr><tr><td width=\"355\" height=\"22\" align=\"center\" background=\"msgbox/alert_03.gif\"><input name=\"msgconfirmb\" type=\"button\" id=\"msgconfirmb\" value=\"ȷ��\" onclick=\"remove();"+func+";\">";
		if(null!=cancel){temp+="&nbsp;&nbsp;<input name=\"msgcancelb\" type=\"button\" id=\"msgcancelb\" value=\"ȡ��\" onclick=\"remove();\"></td>";}
		temp+="</tr><tr><td background=\"msgbox/alert_04.gif\" width=\"355\" height=\"8\"></td></tr></table>";
		create_msgbox(400,200,temp);
		if(focus==0||focus=="0"||null==focus){document.getElementById("msgconfirmb").focus();}
		else if(focus==1||focus=="1"){document.getElementById("msgcancelb").focus();}		
	}
	function re_show(){
		/*
		������ʾ���ֲ��Լ���������Ԫ��
		*/
		re_pos();
		re_mask();	
	}
	function load_func(){
		/*
		���غ���,����window��onresize��onscroll����
		*/
		window.onresize=re_show;
		window.onscroll=re_show;	
	}