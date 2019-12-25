/**
 * @author king
 */
(function($) { 
	function getValue(target){
		return $(target).data("checkedata");
	}
	/**
	 * 创建列表  按钮
	 * type=-1 创建提示未选择过滤条件的按钮 type=1 带关闭按钮 type=2带选择框
	 * yejj 2014-09-11 修改加入按照一行包含多少列显示checkbox
	 * @colsInRow 一行包含多个checkbox元素 外部传入参数
	 * @colIndex 表示此元素在某一行之中列号
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
			//列值与每一行包含列数取余数判断是否到最后一列
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
	 *创建列表 
	 */
	function createList(target,data,type,isCheck,chkWidth,colsRow){
		if(type==1){
			$(target).data("checkedata",[]);
			/*if(data.length==0){
				data.push({text:"未选择"});
			}*/
		}
		$.each(data,function(i,val){
			if(isCheck!=false){
				if(val.checked==false){
					return;
				}
			}
			//yejj2014-09-11 添加按照传入一行能够放置多少元素分行		
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
	 * 在最后一行添加按钮
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
	 * 设置选中状态
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
	 * 创建更多按钮
	 */
	function createMoreBtn(target){
		var self=$(target);
		//添加更多按钮
		var spanMore=createBottomBtn(target,"更多");
		var b=$("<B>");
		spanMore.append(b);
		//点击更多时触发的事件
		spanMore.bind("click",function(e){
			createSelWin(self,e);
			//createSelPanel(self);
		});
	}
	/**
	 * 在本DIV内显示选项
	 */
	function createSelPanel(target){
		var self=$(target);
		var content=self.find(".selected-c");
		content.children().remove();
		createList(self,self.data("data"),2,false);
		//alert(self.data("checkedata"));
		setChecked(self);
		var btnOk=createBottomBtn(target,"确定");
		//点击确定事件
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
	 * 弹出新窗体选择方式
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
		divClose.appendTo(divTitle).addClass("close").append("[ 关闭 ]");
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
		//创建全选按钮
		var divChkAll=$("<DIV>").css({float:"right",position:"relative",width:"100%","text-align":"center"});
		var btnOk=$("<a>").html("确定").css("cursor","hand");
		var chkAll=$("<INPUT type='checkbox'>");
		//divChkAll.insertBefore(btnOk).append(chkAll).append("全选").css({float:"right",position:"relative"});
		divChkAll.appendTo(divContent).append(chkAll).append("全选").append(btnOk);
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
		//点击确定事件
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
	 * 创建一个提示未选择的按钮
	 */
	function createNoSelBtn(target){
		var self=$(target);
		var content=self.find(".selected-c");
		content.children().remove();
		var chkWidth=self.data("options").itemWidth;
		var colsInRow=self.data("options").colsInRow;
		//alert("chkWidth "+chkWidth+" colsInRow "+colsInRow);
		var btnChoose=createListBtn(self,{text:"请选择"},-1,chkWidth,colsInRow,0);
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
			$(this).data("data",data);//传入的数据				
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
