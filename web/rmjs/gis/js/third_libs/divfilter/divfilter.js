/**
 * @author king
 */
(function($) { 
	function getValue(target){
		return $(target).data("checkedata");
	}
	/**
	 * �����б�  ��ť
	 * type=-1 ������ʾδѡ����������İ�ť type=1 ���رհ�ť type=2��ѡ���
	 * yejj 2014-09-11 �޸ļ��밴��һ�а�����������ʾcheckbox
	 * @colsInRow һ�а������checkboxԪ�� �ⲿ�������
	 * @colIndex ��ʾ��Ԫ����ĳһ��֮���к�
	 */
	function createListBtn(target,val,type,chkWidth,colsInRow,colIndex){
		var content=$(target).find(".selected-c");	
		var div=$("<DIV style='width:"+chkWidth+";'>");
		var a=$("<A>");
		//var s=$("<STRONG>");
		var s=$("<SPAN>");
		var b=$("<B>");
		if(type==2){
			b=$("<INPUT>");
			b.attr("type","checkbox");
			div.addClass("chk").appendTo(content);
		}else{
			div.addClass("divFilterList").appendTo(content);
		}
		s.append(val.text);
		if(type==-1){			
			a.append(s).appendTo(div);
		}else{	
			//��ֵ��ÿһ�а�������ȡ�����ж��Ƿ����һ��
			if((colIndex+1)%colsInRow==0){
				a.append(b).append(s).appendTo(div);
				div.append("<br>");
			}else{
				a.append(b).append(s).appendTo(div);
			}
			//a.append(s).append(b).appendTo(div);
		}
		
		b.data("itemData",val);
		return div;
	}
	/**
	 *�����б� 
	 */
	function createList(target,data,type,isCheck,chkWidth,colsRow){
		if(type==1){
			$(target).data("checkedata",[]);
			/*if(data.length==0){
				data.push({text:"δѡ��"});
			}*/
		}
		$.each(data,function(i,val){
			if(isCheck!=false){
				if(val.checked==false){
					return;
				}
			}
			//yejj2014-09-11 ��Ӱ��մ���һ���ܹ����ö���Ԫ�ط���		
			createListBtn(target,val,type,chkWidth,colsRow,i);
			if(type==1){
				$(target).data("checkedata").push(val);
			}
		});
		$(target).find("div.divFilterList b").unbind().bind("click",function(){
			var b=$(this);
			$.each($(target).data("checkedata"),function(i,val){
				var val1=val.value;
				var val2=b.data("itemData").value;
				if(val1==val2){
					$(target).data("checkedata").splice(i, 1);
					if($(target).data("checkedata").length==0){
						createNoSelBtn(target);
					}
					return false;
				}
			});
			b.parents("div.divFilterList").remove();
		});
	}
	/**
	 * �����һ����Ӱ�ť
	 */
	function createBottomBtn(target,text,content){
		if(!content){
		      content=$(target).find(".selected-c");
		}
		var divMore=$("<div>");
		var spanMore=$("<SPAN>");
		//var b=$("<B>");
		//spanMore.append(b).append(text);
		spanMore.append(text);
		spanMore.addClass("o-more");
		divMore.addClass("more");
		divMore.append(spanMore);
		content.append(divMore);
		return spanMore;
	}
	/**
	 * ����ѡ��״̬
	 */
	function setChecked(target,checkeddata){
		var self=$(target);
		if(self.data("checkedata")){
			checkeddata=self.data("checkedata");
		}
		self.find("div.chk input").removeAttr("checked");
		$.each(checkeddata,function(i,val){
			$.each(self.find("div.chk input"),function(i,input){
				var val1=val.value;
				var val2=$(this).data("itemData").value;
				if(val1==val2){
					$(this).attr("checked","checked");
					isFind=true;
					return false;
				}
			});
		});
	}
	function initPanel(target,data,isCheck){
		var self=$(target);
		var content=self.find(".selected-c");
		content.children().remove();
		createList(target,data,1,isCheck);
		createMoreBtn(target);
	}
	/**
	 * �������ఴť
	 */
	function createMoreBtn(target){
		var self=$(target);
		//��Ӹ��ఴť
		var spanMore=createBottomBtn(target,"����");
		var b=$("<B>");
		spanMore.append(b);
		//�������ʱ�������¼�
		spanMore.bind("click",function(e){
			createSelWin(self,e);
			//createSelPanel(self);
		});
	}
	/**
	 * �ڱ�DIV����ʾѡ��
	 */
	function createSelPanel(target){
		var self=$(target);
		var content=self.find(".selected-c");
		content.children().remove();
		createList(self,self.data("data"),2,false);
		//alert(self.data("checkedata"));
		setChecked(self);
		var btnOk=createBottomBtn(target,"ȷ��");
		//���ȷ���¼�
		btnOk.bind("click",function(evt){
			var checkedData=[];
			$.each(self.find("div.chk input"),function(i,val){
				//alert($(this).attr("checked"));
				if($(this).attr("checked")){
					checkedData.push($(this).data("itemData"));
				}
			});
			//createList(self,checkedData,1,false);
			if(checkedData.length==0){
				createNoSelBtn(self);
			}else{
				initPanel(self,checkedData,false);
			}
		});
	}
	/**
	 * �����´���ѡ��ʽ
	 */
	function createSelWin(target,evt){
		var top=evt.pageY;
		var left=evt.pageX;
		var self=$(target);
		var win=self.data("win");
		if(win){
			win.show();
			setChecked(win,self.data("checkedata"));
			win.css({"top":top,"left":left});
			return;
		}
		var winWidth=self.data("options").winWidth;
		//alert("winWidth "+winWidth);
		var divWin=$("<DIV>");
		var divTitle=$("<DIV>");
		var divClose=$("<DIV>");
		var divContent=$("<DIV>");
		//var divChkAll=$("<DIV>");
		//var chkAll=$("<INPUT type='checkbox'>");
		
		//left="10px";
		divWin.appendTo($(document.body)).addClass("divfilter-win").css({"top":top,"left":left,"width":winWidth});
		divTitle.appendTo(divWin).addClass("title");
		divClose.appendTo(divTitle).addClass("close").append("[ �ر� ]");
		//divChkAll.appendTo(divTitle).addClass("chk").append(chkAll);
		divTitle.append(self.data("options").title);
		var chkWidth=self.data("options").itemWidth;
		var colsRow=self.data("options").colsInRow;
		divClose.bind("click",function(){
			$(this).parents(".divfilter-win").hide();
		});
		
		divWin.draggable({
			handle:$(divWin).find(".title")
		});
		divContent.appendTo(divWin).addClass("selected-c").css({width:"100%",height:"100%"});
		createList(divWin,self.data("data"),2,false,chkWidth,colsRow);
		setChecked(divWin,self.data("checkedata"));
		//����ȫѡ��ť
		var divChkAll=$("<DIV>").css({float:"right",position:"relative",width:"100%","text-align":"center"});
		var btnOk=$("<a>").html("ȷ��").css("cursor","hand");
		var chkAll=$("<INPUT type='checkbox'>");
		//divChkAll.insertBefore(btnOk).append(chkAll).append("ȫѡ").css({float:"right",position:"relative"});
		divChkAll.appendTo(divContent).append(chkAll).append("ȫѡ").append(btnOk);
		btnOk.css({"border":"solid  #E6E6E6 1px","padding":"5px 5px"});
		//btnOk.parents(".more").css({width:"200px",right:"10px",position:"absolute"});
		chkAll.bind("change",function(){
			var checked=$(this).attr("checked");
			$.each(divWin.find("div.chk input"),function(i,val){
				if(checked){
					$(this).attr("checked","checked");
				}else{
					$(this).removeAttr("checked");
				}
			});
		});
		//
		//���ȷ���¼�
		btnOk.bind("click",function(evt){
			var checkedData=[];
			$.each(divWin.find("div.chk input"),function(i,val){
				//alert($(this).attr("checked"));
				if($(this).attr("checked")){
					checkedData.push($(this).data("itemData"));
				}
			});
			//createList(self,checkedData,1,false);
			if(checkedData.length==0){
				createNoSelBtn(self);
			}else{
				initPanel(self,checkedData,false);
			}
			self.data("checkedata",checkedData);
			$(this).parents(".divfilter-win").hide();
		});
		//
		self.data("win",divWin);
	}
	/**
	 * ����һ����ʾδѡ��İ�ť
	 */
	function createNoSelBtn(target){
		var self=$(target);
		var content=self.find(".selected-c");
		content.children().remove();
		var chkWidth=self.data("options").itemWidth;
		var colsInRow=self.data("options").colsInRow;
		//alert("chkWidth "+chkWidth+" colsInRow "+colsInRow);
		var btnChoose=createListBtn(self,{text:"��ѡ��"},-1,chkWidth,colsInRow,0);
		btnChoose.css({
			cursor:"hand"
		}).bind("click",function(e){
			createSelWin(self,e);
			//createSelPanel(self);
		});
		createMoreBtn(self);
	}
	$.fn.divFilter = function(args,param) { 
		if (typeof args == 'string'){
			return $.fn.divFilter.methods[args](this, param);
		}
		return this.each(function(){
			var state = $.data(this, 'divFilter');
			var self=$(this);
			args = args || {};
			var data=args.data||[];//[{text:a,value:1}]
			var beforeRemove=args.beforeRemove||function(itemData){return true};
			var afterRemove=args.afterRemove||function(itemData){};
			if(!args.title){
				args.title="";
			}
			if(args.itemWidth==undefined){
				args.itemWidth=100;
			}
			if(args.colsInRow==undefined){
				args.colsInRow=3;
			}
			if(args.winWidth==undefined){
				args.winWidth=300;
			}
			var content=$("<DIV>");
			//var ul=$("<UL>");
			$(this).data("data",data);//���������				
			$(this).data("checkedata",[]);//yi
			$(this).data("options",args);
			$(this).append(content);
			content.appendTo(this).addClass("selected-c");
			//initPanel(this,data);
			createNoSelBtn(self);
		});
	}; 
	$.fn.divFilter.methods={getValue:function(divFilter){
		return getValue(divFilter);
	}};
})( jQuery ); 
