
	    function getLocalXzqh(o_select)
	    {
	    	var localXzqh="";
	    	if(o_select.options.length!=0)
	    	{
	    		 for (var i = 0; i<o_select.options.length; i++)
	    		 {
	    			 localXzqh=localXzqh+","+o_select.options(i).value;
	    		 }
	    	}
	    	return localXzqh;
	    }
	    
	   //获取外地的行政区划
	   function select_xzqh(o_select)
	   {
	      var spath="ajax.frm?method=showPccMain";  
	      //var o_select;	//下拉列表对象	       
	      if(o_select==undefined)
	      {
	            return false;
	      }
	      if(o_select.tagName!="SELECT")
	      {
	            return false;
	      }
  			var a_result=window.showModalDialog(spath,o_select.value,'dialogwidth:450px;dialogheight:300px;center:yes;resizable:yes'); 
	       
			  if(a_result!=null)	//有返回值
			  {
				  var localXzqh=getLocalXzqh(o_select);
			      if(localXzqh.indexOf(a_result[1])>-1)
			      {
				        o_select.value=a_result[1];
			      }
			      else
			      {
						o_select.options.add(new Option(a_result[1]+":"+a_result[2],a_result[1]));	//直接新增“外地”项目
                        o_select.selectedIndex=o_select.options.length-1;
  			    }	
	   		}
	   	}
