
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
	    
	   //��ȡ��ص���������
	   function select_xzqh(o_select)
	   {
	      var spath="ajax.frm?method=showPccMain";  
	      //var o_select;	//�����б����	       
	      if(o_select==undefined)
	      {
	            return false;
	      }
	      if(o_select.tagName!="SELECT")
	      {
	            return false;
	      }
  			var a_result=window.showModalDialog(spath,o_select.value,'dialogwidth:450px;dialogheight:300px;center:yes;resizable:yes'); 
	       
			  if(a_result!=null)	//�з���ֵ
			  {
				  var localXzqh=getLocalXzqh(o_select);
			      if(localXzqh.indexOf(a_result[1])>-1)
			      {
				        o_select.value=a_result[1];
			      }
			      else
			      {
						o_select.options.add(new Option(a_result[1]+":"+a_result[2],a_result[1]));	//ֱ����������ء���Ŀ
                        o_select.selectedIndex=o_select.options.length-1;
  			    }	
	   		}
	   	}
