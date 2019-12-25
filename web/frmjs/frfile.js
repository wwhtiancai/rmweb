
function send_request( url,processName,isLoad)
{
  var spath=url + "&timestamp=" + getNowSec(0);
  if(isLoad)
  {
    postXmlHttp( spath, processName ,'load()');
  }
  else
  {
    postXmlHttp( spath, processName ,'');
  }
}
   function getReport()
   {
        var xmlDoc=_xmlHttpRequestObj.responseXML;
        
        var items=xmlDoc.getElementsByTagName("item"); 
        for(var i=0;i<items.length;i++)
        {
        	
            var item=items[i];
            var rownum=item.getElementsByTagName("rownum")[0].firstChild.nodeValue; 
            if(rownum=="1")
            {
               var xh=item.getElementsByTagName("xh")[0].firstChild.nodeValue;
               var tid=item.getElementsByTagName("tid")[0].firstChild.nodeValue;
               var glbm=item.getElementsByTagName("glbm")[0].firstChild.nodeValue;
               var tmc=item.getElementsByTagName("tmc")[0].firstChild.nodeValue;
               var tgxbj=item.getElementsByTagName("tgxbj")[0].firstChild.nodeValue;
               var datakey=item.getElementsByTagName("datakey")[0].firstChild.nodeValue;
               var impl=item.getElementsByTagName("impl")[0].firstChild.nodeValue;
               var tlb=item.getElementsByTagName("tlb")[0].firstChild.nodeValue;
               var tgxbjobj=document.getElementById("tgxbj");
               tgxbjobj.innerText=tgxbj;
               document.all["btnEdit"].disabled=false;
               document.all["btnSave"].disabled=false;
               document.all["xh"].value=xh;
               document.all["datakey"].value=datakey;
               document.all["tmc"].value=tmc;
               document.all["impl"].value=impl;
               document.all["datakey"].readOnly=true;
               document.all["impl"].readOnly=true;
               downStdReport();
            }  
            else
            {
               document.all["btnEdit"].disabled=true;
               document.all["btnSave"].disabled=true;
               document.all["datakey"].readOnly=false;
               document.all["impl"].readOnly=false;
               displayInfoHtml("[0091082J7]:该文书模板不存在！");
            } 
        } 
   }
   function load()
{
   var loadDiv= document.getElementById( "loading" );
   loadDiv.style.display="block";
}
function endLoading()
{
   var loadDiv= document.getElementById( "loading" );
   loadDiv.style.display="none"; 
}
var _postXmlHttpProcessPostChangeCallBack;
var _xmlHttpRequestObj;
var _loadingFunction;

function postXmlHttp( submitUrl, callbackFunc ,loadFunc)
{
  _postXmlHttpProcessPostChangeCallBack = callbackFunc;
  _loadingFunction = loadFunc;
  _xmlHttpRequestObj=createXMLHttpRequest();
  if (!_xmlHttpRequestObj) 
  {
    window.alert(" 不能创建XMLHttpRequest对象实例.");
	return false;
  }
  _xmlHttpRequestObj.open('POST',submitUrl,true);
  _xmlHttpRequestObj.onreadystatechange=postXmlHttpProcessPostChange;
  _xmlHttpRequestObj.send("");
};

function postXmlHttpProcessPostChange( )
{
  if( _xmlHttpRequestObj.readyState==4 && _xmlHttpRequestObj.status==200 )
  {
    setTimeout( _postXmlHttpProcessPostChangeCallBack, 2 );
  }
  if ( _xmlHttpRequestObj.readyState==1 )
  {
    setTimeout( _loadingFunction, 2 );
  }
}