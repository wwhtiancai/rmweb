//  ************************ 等待条 ***************************  
    function showLoad(valmesg){
    	var msg = "正在加载资源,请稍候。。。。。。";
    	if(valmesg){
    		msg = valmesg;
    	}
    	var loadDiv= document.getElementById("ly");
    	if(!loadDiv){
    		lyDiv = document.createElement("div");                 
            lyDiv.id = "ly";
            lyDiv.style.position = "absolute";
            lyDiv.style.width = document.body.clientWidth;
            lyDiv.style.height = document.body.clientHeight;
            lyDiv.style.top = "0px";
            lyDiv.style.left = "0px";
            lyDiv.style.filter = "alpha(opacity=80)";
            lyDiv.style.backgroundColor = "#777";
            lyDiv.style.zIndex = 9999;
            lyDiv.style.display = "block";//absolute
            var leftVal=(document.body.clientWidth)/2-107;   
            var imgHtml = "<img style='position:relative ;left:"+leftVal+";top:50%' src='"+modelPath+"images/business/zhzx/progressbar_green.gif' />";    
            imgHtml+="<br /><span style='position:relative ;left:"+leftVal+";top:51%;font-size:14px'>"+msg+"</span>";
            lyDiv.innerHTML = imgHtml;             
            document.body.appendChild(lyDiv); 
    	}              
    };
    function closeLoad(){
        try {
            document.body.removeChild(this.lyDiv);        
        } 
        catch (e) {
    //        alert("请求失败！");
        }
    }; 
    
    
    function createIframe(valurl,valid,valcontainer){
    	showLoad();  	
    	var iframe = document.createElement("iframe");
    	iframe.src =valurl;
    	iframe.id=valid;
    	iframe.name=valid;
        iframe.style.scrolling="no";
        iframe.style.frameborder="0";
    	iframe.style.width="100%";
    	iframe.style.height="100%";
    	iframe.scrolling="no";
    	iframe.frameBorder="0";
    	if(!/*@cc_on!@*/0){
    	  iframe.onload = function(){
    		  closeLoad();
    	  };
    	}else{
    		iframe.onreadystatechange = function(){
    			if(iframe.readyState=="complete"){
    				closeLoad();
    			}
    		};
        }
    	valcontainer.append(iframe);
    }
    
    