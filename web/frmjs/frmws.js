  function query_jkid()
  {
	     var xtlb=formain.xtlb.value;
         var spath="ws.frm?method=queryWsContentByXtlb&xtlb="+xtlb;
      	 send_request(spath,"getWsContentsListByXtlb()",false); 
  }
  function getWsContentsListByXtlb()
  {
  	var xmlDoc = _xmlHttpRequestObj.responseXML;
    var head=xmlDoc.getElementsByTagName("head");
	var m_code=head[0].getElementsByTagName("code")[0].firstChild.nodeValue;
	var m_des=head[0].getElementsByTagName("message")[0].firstChild.nodeValue;	
	var items = xmlDoc.getElementsByTagName("item");
	clearFieldOptions(formain.jkid);
    if(m_code==null||m_code=="null")
    {
        m_code="0";
    }
	if(m_code=="1")
	{
		var items=xmlDoc.getElementsByTagName("item");
		if(items.length>0)
		{
			addcomboxitem(formain.jkid,"","--全部--");
		}
		for (var i = 0; i < items.length; i++) 
		{
    		var item = items[i];
    		var jkid = item.getElementsByTagName("jkid")[0].firstChild.nodeValue;
    		var jkmc = item.getElementsByTagName("jkmc")[0].firstChild.nodeValue;
    		addcomboxitem(formain.jkid,jkid,jkid+":"+jkmc);
		}
	}
	else
  	{
  	    displayInfoHtml("[0094033J4]:"+m_des);
  	    //displayInfoHtml_new("00","9403",MSG_RANK_LE,"1",m_des);
  	}  
  }
  
  function query_jkidByJkxlh()
  {
	     var jkxlh=formain.jkxlh.value;
         var spath="ws.frm?method=queryWsContentsByJkxlh&jkxlh="+jkxlh;
      	 send_request(spath,"getWsContentsListByJkxlh()",false); 
  }
  function getWsContentsListByJkxlh()
  {
  	var xmlDoc = _xmlHttpRequestObj.responseXML;
    var head=xmlDoc.getElementsByTagName("head");
	var m_code=head[0].getElementsByTagName("code")[0].firstChild.nodeValue;
	var m_des=head[0].getElementsByTagName("message")[0].firstChild.nodeValue;	
	var items = xmlDoc.getElementsByTagName("item");
	clearFieldOptions(formain.jkid);
    if(m_code==null||m_code=="null")
    {
        m_code="0";
    }
	if(m_code=="1")
	{
		var items=xmlDoc.getElementsByTagName("item");
		if(items.length>0)
		{
			addcomboxitem(formain.jkid,"","--全部--");
		}
		for (var i = 0; i < items.length; i++) 
		{
    		var item = items[i];
    		var jkid = item.getElementsByTagName("jkid")[0].firstChild.nodeValue;
    		var jkmc = item.getElementsByTagName("jkmc")[0].firstChild.nodeValue;
    		addcomboxitem(formain.jkid,jkid,jkid+":"+jkmc);
		}
	}
	else
  	{
  	    displayInfoHtml("[0094043J1]:"+m_des);
  	    //displayInfoHtml_new("00","9404",MSG_RANK_LE,"1",m_des);
  	}  
  }  
  