
var const_gis_marker_deptinfo_id="16";//交警队
var const_gis_marker_car_id="18";//巡逻车
var const_gis_marker_person_id="19";//单警
var const_gis_marker_incident_id="20";//事件检测设备
var const_gis_marker_flow_id="21";//流量信息
var const_gis_marker_block_id="22";//阻断信息
var const_gis_marker_incident_alarm_id="23";//事件预警
var const_gis_marker_incident_info_id="24";//事件签收
var const_gis_marker_incident_confirm_id="25";//事件反馈


var gis_marker_template = "<!-- 模板开始 --> 模板内容 <!-- 模板结束 -->";

var gis_marker_gate = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{kkmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>卡口类型：</font>{{kklxmc}}</td><td class='body'><font class='deepblue'>卡口性质：</font>{{kkxzmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>拦截条件：</font>{{ljtjmc}}</td><td class='body'><font class='deepblue'>视频功能：</font>{{sfjbspmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>图片存储方式：</font>{{gctpwzmc}}</td><td class='body'><font class='deepblue'>数据上传模式：</font>{{sjscmsmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{dlmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_vio = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>设备类型：</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>设备厂商：</font>{{sbcsmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>设备责任人：</font>{{sbzrr}}</td><td class='body'><font class='deepblue'>联系电话：</font>{{sblxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_position = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{szdd}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>接入方式：</font>{{jrfsmc}}</td><td class='body'><font class='deepblue'>网络位置：</font>{{wlwzmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>网关名称：</font>{{wgmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>关联卡口：</font>{{kkmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openVgaPosition('{{id}}'); value=视频信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_station = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{fwzmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>执法站类型：</font>{{fwzlxmc}}</td><td class='body'><font class='deepblue'>检查方向：</font>{{jcfxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>勤务时间：</font>{{qwsjmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>连接公安网：</font>{{ljgawmc}}</td><td class='body'><font class='deepblue'>办理违法业务：</font>{{blwfywmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>联系人：</font>{{lxr}}</td><td class='body'><font class='deepblue'>联系电话：</font>{{lxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openStationduty('{{id}}'); value=值班信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_park = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{tcclxmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>泊位位置：</font>{{pwwzmc}}</td><td class='body'><font class='deepblue'>泊位数：</font>{{pws}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>经营时间：</font>{{jysj}}</td><td class='body'><font class='deepblue'>联系电话：</font>{{syrlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter('{{gisx}}','{{gisy}}'); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_weather = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>数据上传模式：</font>{{sjscmsmc}}</td><td class='body'><font class='deepblue'>上报周期：</font>{{sbzqmc}}(分钟)</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>检测类型：</font>{{yjlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_flow = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>设备类型：</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>检测类型：</font>{{jcsllxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>数据上传模式：</font>{{sjscmsmc}}</td><td class='body'><font class='deepblue'>上报周期：</font>{{sbzqmc}}(分钟)</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>设备责任人：</font>{{sbzrr}}</td><td class='body'><font class='deepblue'>联系电话：</font>{{sblxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_inducement = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>设备来源：</font>{{sblymc}}</td><td class='body'><font class='deepblue'>设备类型：</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>设备规格：</font>{{sbggmc}}</td><td class='body'><font class='deepblue'>发布方式：</font>{{fbfsmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>显示尺寸：</font>高 {{height}}mm;宽 {{width}}mm; 分辨率 {{fbl}}X点/M; 点间距 {{djjmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>发布内容：</font>{{fbnr}}</td></tr>" +
	//"<tr><td height='270' width='350' align='center' class='body' style='text-align:center;' colspan='2'><img src='http://localhost:9080/rmweb/inducement.dev?method=getFbtp&sbbh={{sbbh}}' alt='' id='picbox' width='250' height='250' border='0' style='border:1px solid black;display:none;'></td></tr>" +
	"<tr><td height='270' width='420' align='center' class='body' style='text-align:center;' colspan='2'><img src='http://localhost:9080/rmweb/inducement.dev?method=getFbtp&sbbh={{sbbh}}' id='picbox' border='0' style='border:solid 1px;cursor:hand;width:390px;height:260px''></td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
/*var gis_marker_inducement = "<table >"
+"<tr><td height='270' width='350' valign='top' colspan='2'>"
+"<img src='/rmweb/inducement.dev?method=getFbtp&sbbh=320200000000081000' id='picbox' border='0' style='border:solid 1px;cursor:hand;width:250px;height:250px'>" +
+"</td></tr></table>";*/
var gis_marker_signal = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>设备类型：</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_traffic = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>应急联系人：</font>{{yjlxr}}</td><td class='body'><font class='deepblue'>应急联系电话：</font>{{yjlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>清障车：</font>{{qzcxx}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>吊车：</font>{{dcxx}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_fire = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>机构级别：</font>{{jgjbmc}}</td><td class='body'><font class='deepblue'>应急联系人：</font>{{yjlxr}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>消防车数：</font>{{xfcs}}</td><td class='body'><font class='deepblue'>消防人数：</font>{{xfrs}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_hospital = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>医院等级：</font>{{yydjmc}}</td><td class='body'><font class='deepblue'>急救车数：</font>{{jjcs}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>应急联系人：</font>{{yjlxr}}</td><td class='body'><font class='deepblue'>应急联系电话：</font>{{yjlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_repair = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>资质级别：</font>{{zzjbmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>应急联系人：</font>{{yjlxr}}</td><td class='body'><font class='deepblue'>应急联系电话：</font>{{yjlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_broadcast = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>广播范围：</font>{{gbfwlxmc}}</td><td class='body'><font class='deepblue'>双向对讲：</font>{{sxdjmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_deptinfo = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{glbmmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>联系人：</font>{{lxr}}</td><td class='body'><font class='deepblue'>联系电话：</font>{{lxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{dlmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openDeptduty('{{id}}'); value=值班信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_car = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{hphm}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>车辆种类：</font>{{clzlmc}}</td><td class='body'><font class='deepblue'>随车手机：</font>{{scsj}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>使用部门：</font>{{sybmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>上报时间：</font>{{gxsj}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=历史轨迹信息  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_person = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{xm}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>人员类型：</font>{{rylxmc}}</td><td class='body'><font class='deepblue'>手机号码：</font>{{sjhm}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{bmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>上报时间：</font>{{gxsj}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=历史轨迹信息  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_incident = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>检测类型：</font>{{sjlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";


var gis_marker_flow_info = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sflkmc}}:{{lkldmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>道路名称：</font>{{dlmc}}</td><td class='body'><font class='deepblue'>车道数：</font>{{cds}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>道路类型：</font>{{dllxmc}}</td><td class='body'><font class='deepblue'>行政区划：</font>{{xzqhmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td><td class='body'><font class='deepblue'>设备类型：</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>上报时间：</font>{{sbsj}}</td><td class='body'><font class='deepblue'>{{jtllmc}}：</font>{{jtll}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>{{pjsdmc}}：</font>{{pjsd}}</td><td class='body'><font class='deepblue'>{{bhztmc}}：</font>{{bhzt}}</td></tr>" +
	
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openFlowDetail('{{xxlx}}','{{id}}','{{lkldbh}}'); value=查看近期流量  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openAroundVgaPositionDialog('{{gisx}}','{{gisy}}'); value=周边视频  class=button/>&nbsp;" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_block = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sjlxmc}}:{{dlmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body' colspan='2'><font class='deepblue'>阻断开始时间：</font>{{zdkssj}}时</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>预计恢复时间：</font>{{yjhfsj}}时</td></tr>" +

	"<tr><td class='body' colspan='2'><font class='deepblue'>上报部门：</font>{{bmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>阻断描述：</font><label title='{{bz}}'>{{zdxx}}</label></td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openBlockDetail('{{id}}','{{sjly}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_guide_warning = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{yjtsms}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>影响开始时间：</font>{{yxkssj}}</td><td class='body'><font class='deepblue'>影响结束时间：</font>{{yxjssj}}</td></tr>" +
	
	"<tr><td class='body' colspan='2'><font class='deepblue'>预警提示类型：</font>{{yjlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>预警提示内容：</font>{{yjnr}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openWarningDetail('{{id}}'); value=详细信息  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_incident_alarm = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{title}}:{{sjflmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>发生时间：</font>{{fssj}}</td><td class='body'><font class='deepblue'>事件子类型：</font>{{sjzlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>检测设备：</font>{{sbbhmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>辅助属性：</font>{{fzsxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>所在地点：</font>{{ddms}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openIncidentConfirm('{{xxlx}}','{{id}}'); value=信息确认  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_incident_info = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{title}}:{{sjflmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>发生时间：</font>{{fssj}}</td><td class='body'><font class='deepblue'>事件子类型：</font>{{sjzlxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>事件等级：</font>{{sjdjmc}}</td><td class='body'><font class='deepblue'>信息来源：</font>{{xxlymc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>等发地点：</font>{{ddms}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openIncidentConfirm('{{xxlx}}','{{id}}'); value=应急处置  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_incident_confirm = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{title}}:{{sjflmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>发生时间：</font>{{fssj}}</td><td class='body'><font class='deepblue'>事件子类型：</font>{{sjzlxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>事件等级：</font>{{sjdjmc}}</td><td class='body'><font class='deepblue'>信息来源：</font>{{xxlymc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>事发地点：</font>{{ddms}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>管理部门：</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>处置人：</font>{{czr}}</td><td class='body'><font class='deepblue'>处置时间：</font>{{czsj}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//是否中心定位
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=中心定位  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openIncidentConfirm('{{xxlx}}','{{id}}'); value=处置反馈  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";


function openGisInfoWindowHtml(v_pmark,v_map){
	//返回json数据
	var v_data=getGisInfoWindowHtml(v_pmark.xxlx,v_pmark.id,v_pmark.lkldbh);
	v_data["xxlx"] = v_pmark.xxlx;
	v_data["id"] = v_pmark.id;
	//新增参数经度、维度
	v_data["gisx"] = v_pmark.lonlat.lon; 
	v_data["gisy"] = v_pmark.lonlat.lat;
	v_data["zxdw"] = 1;	
	
	var markerinfo = getGisMarkerinfo(v_pmark,v_data);
	var qpOpt = {
		size : new DMap.Size(markerinfo.mwidth, markerinfo.mheight), //infowindow大小
		isAdjustPositon : true, //infoWIndow是否自适应，即infoWindow总会自动调整到视野范围内
		offsetSize : new DMap.Size(5, 15)
	};
	map.openInfoWindowHtml(v_pmark.lonlat, markerinfo.mhtml, qpOpt);
}	



function getGisMarkerinfo(v_pmark,v_data){
	var v_html="";
	var v_height="200";
	var v_width="435";
	
	if(v_pmark.xxlx=='01'){
		var render = template.compile(gis_marker_gate);
		v_html = render(v_data);
		v_height="226";
		v_width="450";
	}
	if(v_pmark.xxlx=='02'){
		var render = template.compile(gis_marker_vio);
		v_html = render(v_data);

		v_height="216";
		v_width="440";
	}
	if(v_pmark.xxlx=='03'){
		var render = template.compile(gis_marker_position);
		v_html = render(v_data);

		v_height="186";
		v_width="440";
	}
	if(v_pmark.xxlx=='04'){
		var render = template.compile(gis_marker_station);
		v_html = render(v_data);

		v_height="236";
		v_width="440";
	}
	if(v_pmark.xxlx=='05'){
		var render = template.compile(gis_marker_park);
		v_html = render(v_data);

		v_height="186";
	}
	if(v_pmark.xxlx=='06'){
		var render = template.compile(gis_marker_weather);
		v_html = render(v_data);

		v_height="196";
	}
	if(v_pmark.xxlx=='07'){
		var render = template.compile(gis_marker_flow);
		v_html = render(v_data);

		v_height="236";
		v_width="440";
	}
	if(v_pmark.xxlx=='08'){
		var render = template.compile(gis_marker_inducement);
		v_html = render(v_data);

		v_height="476";
		v_width="440";
	}
	if(v_pmark.xxlx=='09'){
		var render = template.compile(gis_marker_signal);
		v_html = render(v_data);

		v_height="166";
	}
	if(v_pmark.xxlx=='11'){
		var render = template.compile(gis_marker_traffic);
		v_html = render(v_data);

		v_height="240";
	}
	if(v_pmark.xxlx=='12'){
		var render = template.compile(gis_marker_fire);
		v_html = render(v_data);

		v_height="186";
		v_width="445";
	}
	if(v_pmark.xxlx=='13'){
		var render = template.compile(gis_marker_hospital);
		v_html = render(v_data);

		v_height="196";
	}
	if(v_pmark.xxlx=='14'){
		var render = template.compile(gis_marker_repair);
		v_html = render(v_data);

		v_height="186";
	}
	if(v_pmark.xxlx=='15'){
		var render = template.compile(gis_marker_broadcast);
		v_html = render(v_data);

		v_height="176";
	}
	if(v_pmark.xxlx=='16'){
		var render = template.compile(gis_marker_deptinfo);
		v_html = render(v_data);

		v_height="166";
	}
	if(v_pmark.xxlx=='18'){
		var render = template.compile(gis_marker_car);
		v_html = render(v_data);

		v_height="166";
	}
	if(v_pmark.xxlx=='19'){
		var render = template.compile(gis_marker_person);
		v_html = render(v_data);

		v_height="166";
	}
	if(v_pmark.xxlx=='20'){
		var render = template.compile(gis_marker_incident);
		v_html = render(v_data);

		v_height="176";
	}
	if(v_pmark.xxlx=='21'){
		v_data["lkldbh"] = v_pmark.lkldbh;
		var render = template.compile(gis_marker_flow_info);
		v_html = render(v_data);

		v_height="200";
	}	
	if(v_pmark.xxlx=='22'){
		var render = template.compile(gis_marker_block);
		v_html = render(v_data);

		v_height="240";
	}
	if(v_pmark.xxlx==const_gis_marker_incident_alarm_id){
		v_data["title"]="预警确认";
		var render = template.compile(gis_marker_incident_alarm);
		v_html = render(v_data);

		v_height="180";
	}
	if(v_pmark.xxlx==const_gis_marker_incident_info_id){
		v_data["title"]="应急处置";
		var render = template.compile(gis_marker_incident_info);
		v_html = render(v_data);

		v_height="180";
	}
	if(v_pmark.xxlx==const_gis_marker_incident_confirm_id){
		v_data["title"]="处置反馈";
		var render = template.compile(gis_marker_incident_confirm);
		v_html = render(v_data);

		v_height="240";
	}	
	
	var markerinfo=new Object();
	markerinfo.mhtml=v_html;
	markerinfo.mwidth=v_width;
	markerinfo.mheight=v_height;
	
	return markerinfo;
}


//定义通过的getHtml,放在公共的信息
//修改返回数据XXX
function getGisInfoWindowHtml(xxlx,id,lkldbh){
	if(xxlx==''||id==''||typeof xxlx=='undefined'||typeof id=='undefined'){
		alert("信息不全，无法获取！");
		return false;
	}
	var retdata=null;
    
	var url="component.dev?method=getHtml&xxlx="+xxlx+"&id="+encodeURI(encodeURI(id))+"&lkldbh="+lkldbh;
	$.ajax({
		url:url,
		dataType:"json",
		type:"POST",
		async:false,
		cache:false,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(data){
			retdata=data;
		},
		error:function(data){
			retdata=data;
		}
	});
	return retdata;
}

function setGisCenter(gisx,gisy){
	var markerLonLat=new DMap.LonLat(gisx,gisy);
	map.setCenter(markerLonLat);
}

function padLeft(str,length){
	if(str.length>=length){
		return str;
	}else{
		return padLeft("0"+str,length);
	}
}
//根据信息类型，打开设备详细信息
function openDevDetail(xxlx,id){
	xxlx=padLeft(xxlx,2);
	var url="component.dev?method=getDevDetail&xxlx="+xxlx+"&id="+id;
	openwin(url,"openDevDetail",false);
}


//查看某点位的视频信息，可以包括多个视频信心
function openVgaPosition(idval){
	var urlpara="&cxlx=1&xh="+idval;
	openVideoPositionMain(urlpara);
}

//查看周边视频信息,根据gisx,gisy
function openAroundVgaPositionDialog(gisxval,gisyval){
	var urlpara="&cxlx=2&gisx="+gisxval+"&gisy="+gisyval;
	openVideoPositionMainDialog(urlpara);
}

//查看周边视频信息,根据gisx,gisy
function openAroundVgaPosition(gisxval,gisyval){
	var urlpara="&cxlx=2&gisx="+gisxval+"&gisy="+gisyval;
	openVideoPositionMain(urlpara);
}

//查看视频的统一入口
function openVideoPositionMainDialog(urlpara){
	var url="playvideo.dev?method=positionVideo"+urlpara;
	var ret = openwindialog(url,null);
}

//查看视频的统一入口
function openVideoPositionMain(urlpara){
	var url="playvideo.dev?method=positionVideo"+urlpara;
	openwin(url,"openVideoPositionMain",false);
}

function openFlowDetail(xxlx,id,lkldbh){
	var url="rs.flow?method=getFlowTodayBySbbhLkldBh&sbbh="+id+"&lkldbh="+lkldbh;
	openwin(url,"openFlowDetail",false);
}

function openBlockDetail(id,xxlx){
	var url="rodblock.guide?method=detail&edit=0&zdbh="+id+"&xxlx="+xxlx;
	openwin(url,"openBlockDetail",false);
}
function openWarningDetail(id){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-600)/2;
	windowwidth=(windowwidth-800)/2;
	
	//id="320000000000141218100184";
	var url="warningqszf.guide?method=detail&xh="+id;
	var subwinname = window.open(url,"openWarningDetail","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=1014,height=680,top="+windowheight+",left="+windowwidth);
	subwinname.focus();
	return subwinname;	
}

function openDevDetailTemp(xxlx,id){
	alert("待与警力资源共用！");
}


function openIncidentConfirm(xxlx,id){
	var url="";
	if(xxlx==const_gis_marker_incident_alarm_id){
		url="incidentAlarmCtrl.guide?method=incident_alarm_add&sjbh="+id;
	}else if(xxlx==const_gis_marker_incident_info_id){
		url="ergincident.guide?method=detail&sjbh="+id;
	}else if(xxlx==const_gis_marker_incident_confirm_id){
		url="ergincidentfk.guide?method=detail&sjbh="+id;
	}
	openwin(url,"openIncidentConfirm",false);
}

//部门当月值班信息
function openDeptduty(glbm){
	var kssj=getTodayN(0);
	var jssj=getTodayN(6);
	//ondutyQuery.dev?method=querySche&glbm=220700000000&kssj=2015-06-03&jssj=2015-06-10
	var url="ondutyQuery.dev?method=querySche&glbm="+glbm+"&kssj="+kssj+"&jssj="+jssj;
	openwin(url,"openDeptduty",false);
}

function openStationduty(glbm){
	var month=getMonth().replaceAll("-","");
	var url="dutyQuery.dev?method=scheduleMonthDetail&fwzbh="+glbm+"&pbny="+month+"&canModify=false";
	openwin(url,"openStationduty",false);
}

function getTodayN(i){
  var today = new Date();
  var day=today.getDate();
  day = day + i;
  today.setDate(day);
  var month=today.getMonth() + 1;
  day=today.getDate();
  if(month<10) sMonth="0" + month;
  else sMonth = month;
  if(day<10) sDay="0" + day;
  else sDay = day;
  r = today.getFullYear() + "-" + sMonth + "-" + sDay;
  return r;
}

//http://10.2.40.208:8080/rmweb/dutyQuery.dev?method=scheduleMonthDetail&fwzbh=320200000000041020&pbny=201412&canModify=false