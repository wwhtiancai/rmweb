
var const_gis_marker_deptinfo_id="16";//������
var const_gis_marker_car_id="18";//Ѳ�߳�
var const_gis_marker_person_id="19";//����
var const_gis_marker_incident_id="20";//�¼�����豸
var const_gis_marker_flow_id="21";//������Ϣ
var const_gis_marker_block_id="22";//�����Ϣ
var const_gis_marker_incident_alarm_id="23";//�¼�Ԥ��
var const_gis_marker_incident_info_id="24";//�¼�ǩ��
var const_gis_marker_incident_confirm_id="25";//�¼�����


var gis_marker_template = "<!-- ģ�忪ʼ --> ģ������ <!-- ģ����� -->";

var gis_marker_gate = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{kkmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>�������ͣ�</font>{{kklxmc}}</td><td class='body'><font class='deepblue'>�������ʣ�</font>{{kkxzmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>����������</font>{{ljtjmc}}</td><td class='body'><font class='deepblue'>��Ƶ���ܣ�</font>{{sfjbspmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>ͼƬ�洢��ʽ��</font>{{gctpwzmc}}</td><td class='body'><font class='deepblue'>�����ϴ�ģʽ��</font>{{sjscmsmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{dlmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_vio = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>�豸���ͣ�</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�豸���̣�</font>{{sbcsmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�豸�����ˣ�</font>{{sbzrr}}</td><td class='body'><font class='deepblue'>��ϵ�绰��</font>{{sblxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_position = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{szdd}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>���뷽ʽ��</font>{{jrfsmc}}</td><td class='body'><font class='deepblue'>����λ�ã�</font>{{wlwzmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�������ƣ�</font>{{wgmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�������ڣ�</font>{{kkmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openVgaPosition('{{id}}'); value=��Ƶ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_station = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{fwzmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>ִ��վ���ͣ�</font>{{fwzlxmc}}</td><td class='body'><font class='deepblue'>��鷽��</font>{{jcfxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>����ʱ�䣺</font>{{qwsjmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>���ӹ�������</font>{{ljgawmc}}</td><td class='body'><font class='deepblue'>����Υ��ҵ��</font>{{blwfywmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>��ϵ�ˣ�</font>{{lxr}}</td><td class='body'><font class='deepblue'>��ϵ�绰��</font>{{lxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openStationduty('{{id}}'); value=ֵ����Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_park = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{tcclxmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>��λλ�ã�</font>{{pwwzmc}}</td><td class='body'><font class='deepblue'>��λ����</font>{{pws}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>��Ӫʱ�䣺</font>{{jysj}}</td><td class='body'><font class='deepblue'>��ϵ�绰��</font>{{syrlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter('{{gisx}}','{{gisy}}'); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_weather = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>�����ϴ�ģʽ��</font>{{sjscmsmc}}</td><td class='body'><font class='deepblue'>�ϱ����ڣ�</font>{{sbzqmc}}(����)</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>������ͣ�</font>{{yjlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_flow = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>�豸���ͣ�</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>������ͣ�</font>{{jcsllxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�����ϴ�ģʽ��</font>{{sjscmsmc}}</td><td class='body'><font class='deepblue'>�ϱ����ڣ�</font>{{sbzqmc}}(����)</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�豸�����ˣ�</font>{{sbzrr}}</td><td class='body'><font class='deepblue'>��ϵ�绰��</font>{{sblxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_inducement = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>�豸��Դ��</font>{{sblymc}}</td><td class='body'><font class='deepblue'>�豸���ͣ�</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�豸���</font>{{sbggmc}}</td><td class='body'><font class='deepblue'>������ʽ��</font>{{fbfsmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>��ʾ�ߴ磺</font>�� {{height}}mm;�� {{width}}mm; �ֱ��� {{fbl}}X��/M; ���� {{djjmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�������ݣ�</font>{{fbnr}}</td></tr>" +
	//"<tr><td height='270' width='350' align='center' class='body' style='text-align:center;' colspan='2'><img src='http://localhost:9080/rmweb/inducement.dev?method=getFbtp&sbbh={{sbbh}}' alt='' id='picbox' width='250' height='250' border='0' style='border:1px solid black;display:none;'></td></tr>" +
	"<tr><td height='270' width='420' align='center' class='body' style='text-align:center;' colspan='2'><img src='http://localhost:9080/rmweb/inducement.dev?method=getFbtp&sbbh={{sbbh}}' id='picbox' border='0' style='border:solid 1px;cursor:hand;width:390px;height:260px''></td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
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
	"<tr><td class='body' colspan='2'><font class='deepblue'>�豸���ͣ�</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_traffic = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>Ӧ����ϵ�ˣ�</font>{{yjlxr}}</td><td class='body'><font class='deepblue'>Ӧ����ϵ�绰��</font>{{yjlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ϳ���</font>{{qzcxx}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>������</font>{{dcxx}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_fire = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>��������</font>{{jgjbmc}}</td><td class='body'><font class='deepblue'>Ӧ����ϵ�ˣ�</font>{{yjlxr}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>����������</font>{{xfcs}}</td><td class='body'><font class='deepblue'>����������</font>{{xfrs}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_hospital = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>ҽԺ�ȼ���</font>{{yydjmc}}</td><td class='body'><font class='deepblue'>���ȳ�����</font>{{jjcs}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>Ӧ����ϵ�ˣ�</font>{{yjlxr}}</td><td class='body'><font class='deepblue'>Ӧ����ϵ�绰��</font>{{yjlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_repair = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{jgmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ʼ���</font>{{zzjbmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>Ӧ����ϵ�ˣ�</font>{{yjlxr}}</td><td class='body'><font class='deepblue'>Ӧ����ϵ�绰��</font>{{yjlxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_broadcast = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>�㲥��Χ��</font>{{gbfwlxmc}}</td><td class='body'><font class='deepblue'>˫��Խ���</font>{{sxdjmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_deptinfo = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{glbmmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>��ϵ�ˣ�</font>{{lxr}}</td><td class='body'><font class='deepblue'>��ϵ�绰��</font>{{lxdh}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{dlmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openDeptduty('{{id}}'); value=ֵ����Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_car = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{hphm}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>�������ࣺ</font>{{clzlmc}}</td><td class='body'><font class='deepblue'>�泵�ֻ���</font>{{scsj}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>ʹ�ò��ţ�</font>{{sybmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�ϱ�ʱ�䣺</font>{{gxsj}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=��ʷ�켣��Ϣ  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_person = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{xm}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body'><font class='deepblue'>��Ա���ͣ�</font>{{rylxmc}}</td><td class='body'><font class='deepblue'>�ֻ����룺</font>{{sjhm}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{bmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�ϱ�ʱ�䣺</font>{{gxsj}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=��ʷ�켣��Ϣ  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openDevDetailTemp('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_marker_incident = "<table  class='query'><tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sbmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	"<tr><td class='body' colspan='2'><font class='deepblue'>������ͣ�</font>{{sjlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{szdd}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	
	"<input type=button id=xxxx onclick=openDevDetail('{{xxlx}}','{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";


var gis_marker_flow_info = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sflkmc}}:{{lkldmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>��·���ƣ�</font>{{dlmc}}</td><td class='body'><font class='deepblue'>��������</font>{{cds}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>��·���ͣ�</font>{{dllxmc}}</td><td class='body'><font class='deepblue'>����������</font>{{xzqhmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td><td class='body'><font class='deepblue'>�豸���ͣ�</font>{{sblxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�ϱ�ʱ�䣺</font>{{sbsj}}</td><td class='body'><font class='deepblue'>{{jtllmc}}��</font>{{jtll}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>{{pjsdmc}}��</font>{{pjsd}}</td><td class='body'><font class='deepblue'>{{bhztmc}}��</font>{{bhzt}}</td></tr>" +
	
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openFlowDetail('{{xxlx}}','{{id}}','{{lkldbh}}'); value=�鿴��������  class=button/>&nbsp;" +
	"<input type=button id=xxxx onclick=openAroundVgaPositionDialog('{{gisx}}','{{gisy}}'); value=�ܱ���Ƶ  class=button/>&nbsp;" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_block = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{sjlxmc}}:{{dlmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body' colspan='2'><font class='deepblue'>��Ͽ�ʼʱ�䣺</font>{{zdkssj}}ʱ</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>Ԥ�ƻָ�ʱ�䣺</font>{{yjhfsj}}ʱ</td></tr>" +

	"<tr><td class='body' colspan='2'><font class='deepblue'>�ϱ����ţ�</font>{{bmmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���������</font><label title='{{bz}}'>{{zdxx}}</label></td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openBlockDetail('{{id}}','{{sjly}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";
var gis_guide_warning = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{yjtsms}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>Ӱ�쿪ʼʱ�䣺</font>{{yxkssj}}</td><td class='body'><font class='deepblue'>Ӱ�����ʱ�䣺</font>{{yxjssj}}</td></tr>" +
	
	"<tr><td class='body' colspan='2'><font class='deepblue'>Ԥ����ʾ���ͣ�</font>{{yjlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>Ԥ����ʾ���ݣ�</font>{{yjnr}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openWarningDetail('{{id}}'); value=��ϸ��Ϣ  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_incident_alarm = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{title}}:{{sjflmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>����ʱ�䣺</font>{{fssj}}</td><td class='body'><font class='deepblue'>�¼������ͣ�</font>{{sjzlxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>����豸��</font>{{sbbhmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�������ԣ�</font>{{fzsxmc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>���ڵص㣺</font>{{ddms}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openIncidentConfirm('{{xxlx}}','{{id}}'); value=��Ϣȷ��  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_incident_info = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{title}}:{{sjflmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>����ʱ�䣺</font>{{fssj}}</td><td class='body'><font class='deepblue'>�¼������ͣ�</font>{{sjzlxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�¼��ȼ���</font>{{sjdjmc}}</td><td class='body'><font class='deepblue'>��Ϣ��Դ��</font>{{xxlymc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�ȷ��ص㣺</font>{{ddms}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openIncidentConfirm('{{xxlx}}','{{id}}'); value=Ӧ������  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";

var gis_marker_incident_confirm = "<table  class='query'>" +
	"<tr><td class='top' colspan='2' style='word-break:keep-all;width:100%'>{{title}}:{{sjflmc}}</td></tr>" +
	"<% if(code!=1) { %>"+
	"<tr><td class='body' colspan='2'>{{message}}</td></tr>" +
	"<% }else{ %>"+
	
	"<tr><td class='body'><font class='deepblue'>����ʱ�䣺</font>{{fssj}}</td><td class='body'><font class='deepblue'>�¼������ͣ�</font>{{sjzlxmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�¼��ȼ���</font>{{sjdjmc}}</td><td class='body'><font class='deepblue'>��Ϣ��Դ��</font>{{xxlymc}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�·��ص㣺</font>{{ddms}}</td></tr>" +
	"<tr><td class='body' colspan='2'><font class='deepblue'>�����ţ�</font>{{glbmmc}}</td></tr>" +
	"<tr><td class='body'><font class='deepblue'>�����ˣ�</font>{{czr}}</td><td class='body'><font class='deepblue'>����ʱ�䣺</font>{{czsj}}</td></tr>" +
	"<tr><td colspan='2' class='bottom' align='center'>" +
	//�Ƿ����Ķ�λ
	"<% if(zxdw==1) { %>"+
	"<input type=button id=zxdw onclick=setGisCenter({{gisx}},{{gisy}}); value=���Ķ�λ  class=button/>&nbsp;" +
	"<% }%>"+
	"<input type=button id=xxxx onclick=openIncidentConfirm('{{xxlx}}','{{id}}'); value=���÷���  class=button/>" +
	"</td></tr>" +
	"<% }%>"+
	"</table>";


function openGisInfoWindowHtml(v_pmark,v_map){
	//����json����
	var v_data=getGisInfoWindowHtml(v_pmark.xxlx,v_pmark.id,v_pmark.lkldbh);
	v_data["xxlx"] = v_pmark.xxlx;
	v_data["id"] = v_pmark.id;
	//�����������ȡ�ά��
	v_data["gisx"] = v_pmark.lonlat.lon; 
	v_data["gisy"] = v_pmark.lonlat.lat;
	v_data["zxdw"] = 1;	
	
	var markerinfo = getGisMarkerinfo(v_pmark,v_data);
	var qpOpt = {
		size : new DMap.Size(markerinfo.mwidth, markerinfo.mheight), //infowindow��С
		isAdjustPositon : true, //infoWIndow�Ƿ�����Ӧ����infoWindow�ܻ��Զ���������Ұ��Χ��
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
		v_data["title"]="Ԥ��ȷ��";
		var render = template.compile(gis_marker_incident_alarm);
		v_html = render(v_data);

		v_height="180";
	}
	if(v_pmark.xxlx==const_gis_marker_incident_info_id){
		v_data["title"]="Ӧ������";
		var render = template.compile(gis_marker_incident_info);
		v_html = render(v_data);

		v_height="180";
	}
	if(v_pmark.xxlx==const_gis_marker_incident_confirm_id){
		v_data["title"]="���÷���";
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


//����ͨ����getHtml,���ڹ�������Ϣ
//�޸ķ�������XXX
function getGisInfoWindowHtml(xxlx,id,lkldbh){
	if(xxlx==''||id==''||typeof xxlx=='undefined'||typeof id=='undefined'){
		alert("��Ϣ��ȫ���޷���ȡ��");
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
//������Ϣ���ͣ����豸��ϸ��Ϣ
function openDevDetail(xxlx,id){
	xxlx=padLeft(xxlx,2);
	var url="component.dev?method=getDevDetail&xxlx="+xxlx+"&id="+id;
	openwin(url,"openDevDetail",false);
}


//�鿴ĳ��λ����Ƶ��Ϣ�����԰��������Ƶ����
function openVgaPosition(idval){
	var urlpara="&cxlx=1&xh="+idval;
	openVideoPositionMain(urlpara);
}

//�鿴�ܱ���Ƶ��Ϣ,����gisx,gisy
function openAroundVgaPositionDialog(gisxval,gisyval){
	var urlpara="&cxlx=2&gisx="+gisxval+"&gisy="+gisyval;
	openVideoPositionMainDialog(urlpara);
}

//�鿴�ܱ���Ƶ��Ϣ,����gisx,gisy
function openAroundVgaPosition(gisxval,gisyval){
	var urlpara="&cxlx=2&gisx="+gisxval+"&gisy="+gisyval;
	openVideoPositionMain(urlpara);
}

//�鿴��Ƶ��ͳһ���
function openVideoPositionMainDialog(urlpara){
	var url="playvideo.dev?method=positionVideo"+urlpara;
	var ret = openwindialog(url,null);
}

//�鿴��Ƶ��ͳһ���
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
	alert("���뾯����Դ���ã�");
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

//���ŵ���ֵ����Ϣ
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