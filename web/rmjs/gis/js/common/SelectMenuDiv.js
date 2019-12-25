function SelectMenuDiv(){
	var me = this;
	this.cell;
	this.openItemDiv = function(obj){    
	    this.cell = obj;  
	    this.setPlace(this.cell);	    
	    this.showItemDiv();
	}

    this.getPos = function(cell){   
          var pos = new Array();   
          var t=cell.offsetTop;   
          var l=cell.offsetLeft;   
          while(cell=cell.offsetParent){   
             t+=cell.offsetTop;   
             l+=cell.offsetLeft;   
          }   
          pos[0]= t;   
          pos[1]= l;   
          return pos;   
    }   
    
    this.setPlace = function(cell){   
    	  var imgObj = $("<img src='rmjs/gis/js/third_libs/Dialog/dialogImages/dialog_t4.gif' style='padding-top:2px; cursor:pointer'/>");
    	  $(imgObj).click(function(){
    		  me.hideItemDiv();
    	  });
    	  var tempDiv = $("<div id='closeItemDiv' style='float:right;height:16px;width:16px;'></div>");
    	  tempDiv.append(imgObj);

		  var arrPos = this.getPos(cell);   		     
		  var oDiv = document.all.select_div_table;    
		  oDiv.style.display = '';  
		  var tdleft = arrPos[1];   
		  var tdtop = arrPos[0];   
		 // oTable.style.width = tdwidth;   
		 // oDiv.style.width = tdwidth;   
		  oDiv.style.left = tdleft;   
		  oDiv.style.top = tdtop+28;	
		  $(oDiv).append(tempDiv);
		  $(oDiv).append(selectTreeDiv);
     }   

	this.hideItemDiv = function(){
	   document.getElementById("select_div_table").style.display = 'none';
	}

	this.showItemDiv = function(){
	   document.getElementById("select_div_table").style.display = 'block';
	}
	
}
var selectMenuDiv = new SelectMenuDiv();