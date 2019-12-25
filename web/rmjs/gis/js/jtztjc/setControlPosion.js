

var Collapseflag=1;//标识是否下方工具条折叠
function setBottomBarCollapse(){//设置下方工具条的折叠
	
	$('#bottomToolbar').window({
	    tools:[{
            iconCls:'panel-tool-collapse',
            handler:function(){
            	var winHeight =document.body.clientHeight;
            	var btbExpandTop=winHeight-30;
            	var btbCollapseTop=winHeight-85;
            	if(Collapseflag==1){
    				Collapseflag=0;
    				$('#bottomToolbar').window({left:"0px", top:btbExpandTop+"px"});
    			}else{
    				Collapseflag=1;
    				$('#bottomToolbar').window({left:"0px", top:btbCollapseTop+"px"});
    			};
            }
        }]
	});
}
$(document).ready(function() {	
	setBottomBarCollapse();
});
