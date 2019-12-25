<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.service.*"%>
<html>
<head>
<title>请选择管理部门</title>
<%
String fwzjljb=(String)request.getAttribute("fwzjljb");
String bmjb=(String)request.getAttribute("bmjb");
String glbm=(String)request.getAttribute("glbm");
String ywlb=(String)request.getAttribute("ywlb");
SysService sysService = (SysService)request.getAttribute("sysservice");
String hpt="";
GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)request.getAttribute("gSysparaCodeService");
String bdfzjg=gSysparaCodeService.getSysParaValue("00","BDFZJG");
String[] fzjg=bdfzjg.split(",");
if(fzjg.length>0)
	hpt=fzjg[0];
if(hpt.length()>1)
	  hpt=hpt.substring(0,1);
%>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src='js/tools.js'></script>
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript">
var glbm='<%=glbm%>';
var bmjb='<%=bmjb%>';
var fwzjljb='<%=fwzjljb%>';
var ywlb='<%=ywlb%>';
function initdata(){
	if(bmjb=='2')
	{
		tr1.style.display = "none";
		tr2.style.display = "none";	
		tr3.style.display = "none";
		tr4.style.display = "none";	
		tr5.style.display = "none";
		tr6.style.display = "none";	
		tr7.style.display = "none";
		if(glbm.substr(8,2)=='04'){
			dialogArguments.all["glbm5"].value='';
			dialogArguments.all["glbm5"].readOnly=false;
			clearFieldOptions(dialogArguments.all["bmjb"]);
			dialogArguments.all.ywlb.value=ywlb;
			addcomboxitem1(dialogArguments.all["bmjb"],"3","支队");
			inityfly("4");
			initfzjg("3");
			dialogArguments.all["lx"].value="6";//用于判断是否下属机构的下属机构
			dialogArguments.all["bmmc"].value="";
			dialogArguments.all["bmqc"].value="";
			dialogArguments.all["yzmc"].value="";
			dialogArguments.all["fzjg"].value="";
			dialogArguments.all["fzr"].value="";
			dialogArguments.all["lxr"].value="";
			dialogArguments.all["lxdh"].value="";
			dialogArguments.all["czhm"].value="";
			dialogArguments.all["lxdz"].value="";
			dialogArguments.all["bz"].value="";
			dialogArguments.all["modal"].value="new";
			dialogArguments.all("delbutton").disabled=true;
			dialogArguments.all("bmyzbutton").disabled=true;
			dialogArguments.all["sjbm"].value=glbm;
			dialogArguments.all["yfly"].value="";
			clearFieldOptions(dialogArguments.all["sjcjzdbm"]);
			clearFieldOptions(dialogArguments.all["sjwfzdbm"]);
			clearFieldOptions(dialogArguments.all["sjsgzdbm"]);
			window.close();
		}else if(glbm.substr(8,2)=='05'){
			tr8.style.display = "none";	
			tr9.style.display = "none";
			tr10.style.display = "none";
			tr11.style.display = "none";
			tr12.style.display = "none";

			tr13.style.display = "block";
			if(fwzjljb<=bmjb)
			    tr14.style.display = "block";
			else
				tr14.style.display = "none";
			tr15.style.display = "none";
			document.all.choose13.checked=true;	
		}else{
				tr8.style.display = "block";	
				tr9.style.display = "block";
				tr15.style.display = "block";
				tr11.style.display = "none";
				tr12.style.display = "none";
				if(fwzjljb<=bmjb)
				    tr10.style.display = "block";
				else
					tr10.style.display = "none";
				tr13.style.display = "none";
				tr14.style.display = "none";
				document.all.choose8.checked=true;
		}
	}else if(bmjb=='3'){
		if(glbm.substr(8,2)=='00' ||glbm.substr(8,2)=='05')
		{
		}else{
			dialogArguments.all["glbm5"].value='';
			dialogArguments.all["glbm5"].readOnly=false;
			clearFieldOptions(dialogArguments.all["bmjb"]);
			dialogArguments.all.ywlb.value=ywlb;
			if(glbm.substr(8,2)=='04')
			    addcomboxitem1(dialogArguments.all["bmjb"],"4","大队");
			else
				addcomboxitem1(dialogArguments.all["bmjb"],"3","支队");
			inityfly("4");
			initfzjg("3");
			dialogArguments.all["lx"].value="6";//用于判断是否下属机构的下属机构
			dialogArguments.all["bmmc"].value="";
			dialogArguments.all["bmqc"].value="";
			dialogArguments.all["yzmc"].value="";
			dialogArguments.all["fzjg"].value="";
			dialogArguments.all["fzr"].value="";
			dialogArguments.all["lxr"].value="";
			dialogArguments.all["lxdh"].value="";
			dialogArguments.all["czhm"].value="";
			dialogArguments.all["lxdz"].value="";
			dialogArguments.all["bz"].value="";
			dialogArguments.all["modal"].value="new";
			dialogArguments.all("delbutton").disabled=true;
			dialogArguments.all("bmyzbutton").disabled=true;
			dialogArguments.all["sjbm"].value=glbm;
			dialogArguments.all["yfly"].value="";
			clearFieldOptions(dialogArguments.all["sjcjzdbm"]);
			clearFieldOptions(dialogArguments.all["sjwfzdbm"]);
			clearFieldOptions(dialogArguments.all["sjsgzdbm"]);
			window.close();
		}
		tr1.style.display = "block";
		tr2.style.display = "block";	
		if(fwzjljb<=bmjb)
			tr3.style.display = "block";
		else
			tr3.style.display = "none";
		tr4.style.display = "none";	
		tr5.style.display = "none";
		tr6.style.display = "none";	
		tr7.style.display = "none";
		tr8.style.display = "none";	
		tr9.style.display = "none";
		tr10.style.display = "none";
		tr11.style.display = "none";
		tr12.style.display = "none";
		tr13.style.display = "none";
		tr14.style.display = "none";
		tr15.style.display = "none";
		document.all.choose1.checked=true;	
	}else if(bmjb=='4'){
		if(glbm.substr(8,2)=='00' ||glbm.substr(8,2)=='05')
		{
		}else{
			dialogArguments.all["glbm5"].value='';
			dialogArguments.all["glbm5"].readOnly=false;
			clearFieldOptions(dialogArguments.all["bmjb"]);
			dialogArguments.all.ywlb.value=ywlb;
			addcomboxitem1(dialogArguments.all["bmjb"],"5","中队");
			inityfly("5");
			initfzjg("3");
			dialogArguments.all["lx"].value="6";//用于判断是否下属机构的下属机构
			dialogArguments.all["bmmc"].value="";
			dialogArguments.all["bmqc"].value="";
			dialogArguments.all["yzmc"].value="";
			dialogArguments.all["fzjg"].value="";
			dialogArguments.all["fzr"].value="";
			dialogArguments.all["lxr"].value="";
			dialogArguments.all["lxdh"].value="";
			dialogArguments.all["czhm"].value="";
			dialogArguments.all["lxdz"].value="";
			dialogArguments.all["bz"].value="";
			dialogArguments.all["modal"].value="new";
			dialogArguments.all("delbutton").disabled=true;
			dialogArguments.all("bmyzbutton").disabled=true;
			
			dialogArguments.all["sjbm"].value=glbm;
			dialogArguments.all["yfly"].value="";
			clearFieldOptions(dialogArguments.all["sjcjzdbm"]);
			clearFieldOptions(dialogArguments.all["sjwfzdbm"]);
			clearFieldOptions(dialogArguments.all["sjsgzdbm"]);
			window.close();
		}
		tr1.style.display = "none";
		tr2.style.display = "none";	
		tr3.style.display = "none";
		tr4.style.display = "block";	
		tr5.style.display = "block";
		tr6.style.display = "block";
		if(fwzjljb<=bmjb)
			tr7.style.display = "block";
		else
			tr7.style.display = "none";
		tr8.style.display = "none";
		tr9.style.display = "none";	
		tr10.style.display = "none";	
		tr11.style.display = "none";
		tr12.style.display = "none";
		tr13.style.display = "none";
		tr14.style.display = "none";	
		tr15.style.display = "none";
		document.all.choose4.checked=true;	
	}else if(bmjb=='5'){
		tr1.style.display = "none";
		tr2.style.display = "none";	
		tr3.style.display = "none";
		tr4.style.display = "none";	
		tr5.style.display = "none";
		tr6.style.display = "none";
		tr7.style.display = "none";
		tr8.style.display = "none";
		tr9.style.display = "none";	
		tr10.style.display = "none";	
		tr11.style.display = "block";
		tr12.style.display = "block";	
		tr13.style.display = "none";
		tr14.style.display = "none";	
		tr15.style.display = "none";
		document.all.choose11.checked=true;	
	}
}
function savedata(){
	if(window.dialogArguments.ylzd5.value=="0"){
		dialogArguments.hzl1.value = document.all("hzl").value;
		dialogArguments.cllx1.value = document.all("cllx").value;				
		dialogArguments.clpp1.value = document.all("clpp").value;		
		dialogArguments.clxh1.value = document.all("clxh").value;	
	}
}
function saveone(){
	dialogArguments.all["lx"].value="";
if(bmjb=='2'){
	//创建高速总队下的高速支队
	if(glbm.substr(8,2)=='05'){
		dialogArguments.all["glbm2"].value='';
		dialogArguments.all["glbm2"].readOnly=false;
		dialogArguments.all["glbm4"].value='00';
		dialogArguments.all["glbm5"].value='10';
		dialogArguments.all.ywlb.value="11111111110000000003";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"3","支队");
		inityfly("3");
	}else{
		if(document.all.choose8.checked==false&&document.all.choose9.checked==false&&document.all.choose10.checked==false&&document.all.choose15.checked==false)
		{
			alert("请选择创建类型");
			return false;
		}
		//创建总队下属机构
		if(document.all.choose8.checked==true)
		{
			//检查下属机构
			if(document.all.zondxsjg19.checked==true)
			{
				dialogArguments.all["glbm4"].value='';
				dialogArguments.all["glbm4"].readOnly=false;
				dialogArguments.all.ywlb.value="00000000000000000000";
			}else{
				if(checkxsjg(1))
				{
					dialogArguments.all["glbm4"].readOnly=true;
					if(document.all.zondxsjg1.checked==true){
						dialogArguments.all["glbm4"].value='01';
					}else{
						if(document.all.zondxsjg2.checked==true){
							dialogArguments.all["glbm4"].value='02';
						}else{
							if(document.all.zondxsjg3.checked==true){
								dialogArguments.all["glbm4"].value='03';
							}else{
								if(document.all.zondxsjg4.checked==true){
									dialogArguments.all["glbm4"].value='04';
								}else{
									if(document.all.zondxsjg5.checked==true){
										dialogArguments.all["glbm4"].value='05';
										dialogArguments.all["glbm5"].value='10';
										dialogArguments.all["glbm5"].readOnly=true;
									}else{
										if(document.all.zondxsjg6.checked==true){
											dialogArguments.all["glbm4"].value='06';
										}else{
											if(document.all.zondxsjg7.checked==true){
												dialogArguments.all["glbm4"].value='07';
											}else{
												if(document.all.zondxsjg8.checked==true){
													dialogArguments.all["glbm4"].value='08';
												}else{
													if(document.all.zondxsjg9.checked==true){
														dialogArguments.all["glbm4"].value='09';
													}
												}
											}
										}
									}
								}
							}
						}
					}
					var ywlb="";
					if(document.all.zondxsjg1.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg2.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg3.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg4.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg5.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg6.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg7.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg8.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					if(document.all.zondxsjg9.checked==true)
						ywlb=ywlb+"1";
					else
						ywlb=ywlb+"0";
					ywlb=ywlb+"0";
					if(document.all.zondxsjg5.checked==true){
						ywlb=ywlb+'0000000003';
					}else{
						ywlb=ywlb+'0000000000';
					}
					dialogArguments.all.ywlb.value=ywlb;
				}else
					return false;
			}
			clearFieldOptions(dialogArguments.all["bmjb"]);
			addcomboxitem1(dialogArguments.all["bmjb"],"2","总队");
			inityfly("2");
			initfzjg("2");
		}else if(document.all.choose9.checked==true){
			//下属支队
			dialogArguments.all["glbm2"].value='';
			dialogArguments.all["glbm2"].readOnly=false;
			if(document.all.select.value=='3'){
				dialogArguments.all["glbm5"].value='10';
			    dialogArguments.all.ywlb.value="11111111110000000003";
			}
			else if(document.all.select.value=='1')
				dialogArguments.all.ywlb.value="11111111110000000001";
			//dialogArguments.all.ywlb.value="11111111110000000001";
			clearFieldOptions(dialogArguments.all["bmjb"]);
			addcomboxitem1(dialogArguments.all["bmjb"],"3","支队");
			inityfly("3");
			initfzjg("3");
		}else if(document.all.choose15.checked==true){
			//省管县大队
			dialogArguments.all["glbm2"].value='90';
			dialogArguments.all["glbm3"].value='';
			dialogArguments.all["glbm3"].readOnly=false;
			if(document.all.select2.value=='2')
				dialogArguments.all.ywlb.value="11111111110000000002";
			else
				dialogArguments.all.ywlb.value="11111111110000000001";
			clearFieldOptions(dialogArguments.all["bmjb"]);
			addcomboxitem1(dialogArguments.all["bmjb"],"3","支队");
			addcomboxitem1(dialogArguments.all["bmjb"],"4","大队");
			inityfly("4");
			initfzjg("3");
		}else if(document.all.choose10.checked==true){
			//服务站
			dialogArguments.all["glbm5"].value='';
			dialogArguments.all["glbm5"].readOnly=false;
			dialogArguments.all["glbm4"].value='';
			dialogArguments.all["glbm4"].readOnly=false;
			dialogArguments.all["lx"].value="4";
			dialogArguments.all.ywlb.value="00000000000000000000";
			clearFieldOptions(dialogArguments.all["bmjb"]);
			addcomboxitem1(dialogArguments.all["bmjb"],"6","执法站");
			inityfly("6");
			initfzjg("3");
		}
	}
}else if(bmjb=='3'){
	if(document.all.choose1.checked==false&&document.all.choose2.checked==false&&document.all.choose3.checked==false)
	{
		alert("请选择创建类型");
		return false;
	}	
	//创建支队下属机构
	if(document.all.choose1.checked==true)
	{
		//检查下属机构
		
		if(document.all.zdxsjg19.checked==true)
		{
			dialogArguments.all["glbm4"].value='';
			dialogArguments.all["glbm4"].readOnly=false;
			dialogArguments.all.ywlb.value="00000000000000000000";
		}else{
			if(checkxsjg(2))
			{
				dialogArguments.all["glbm4"].readOnly=true;
				if(document.all.zdxsjg1.checked==true){
					dialogArguments.all["glbm4"].value='01';
				}else{
					if(document.all.zdxsjg2.checked==true){
						dialogArguments.all["glbm4"].value='02';
					}else{
						if(document.all.zdxsjg3.checked==true){
							dialogArguments.all["glbm4"].value='03';
						}else{
							if(document.all.zdxsjg4.checked==true){
								dialogArguments.all["glbm4"].value='04';
							}else{
								if(document.all.zdxsjg6.checked==true){
									dialogArguments.all["glbm4"].value='06';
								}else{
									if(document.all.zdxsjg7.checked==true){
										dialogArguments.all["glbm4"].value='07';
									}else{
										if(document.all.zdxsjg8.checked==true){
											dialogArguments.all["glbm4"].value='08';
										}else{
											if(document.all.zdxsjg9.checked==true){
												dialogArguments.all["glbm4"].value='09';
											}
										}
									}
								}
							}
						}
					}
				}
				var ywlb="";
				if(document.all.zdxsjg1.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.zdxsjg2.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.zdxsjg3.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.zdxsjg4.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				ywlb=ywlb+"0";
				if(document.all.zdxsjg6.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.zdxsjg7.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.zdxsjg8.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.zdxsjg9.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				ywlb=ywlb+"0";
				ywlb=ywlb+'0000000000';
				dialogArguments.all.ywlb.value=ywlb;
			}else
				return false;
		}
		initfzjg("3");
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"3","支队");
		inityfly("3");
	}else if(document.all.choose2.checked==true){
		//下属大队
		dialogArguments.all["glbm3"].value='';
		dialogArguments.all["glbm3"].readOnly=false;
		if(document.all.select2.value=='3')
		{
			dialogArguments.all["glbm5"].value='10';
		    dialogArguments.all.ywlb.value="11111111110000000003";
		}
		else if(document.all.select2.value=='2')
			dialogArguments.all.ywlb.value="11111111110000000002";
		else
			dialogArguments.all.ywlb.value="11111111110000000001";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"4","大队");
		inityfly("4");
	}else if(document.all.choose3.checked==true){
		//服务站
		dialogArguments.all["glbm5"].value='';
		dialogArguments.all["glbm5"].readOnly=false;
		dialogArguments.all["glbm4"].value='';
		dialogArguments.all["glbm4"].readOnly=false;
		dialogArguments.all["lx"].value="4";
		dialogArguments.all.ywlb.value="00000000000000000000";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"6","执法站");
		inityfly("6");
	}	
	initfzjg("3");
}else if(bmjb=='4'){
	if(document.all.choose4.checked==false&&document.all.choose5.checked==false&&document.all.choose6.checked==false&&document.all.choose7.checked==false)
	{
		alert("请选择创建类型");
		return false;
	}	
	//创建大队下属机构
	if(document.all.choose4.checked==true)
	{
		//检查下属机构
		if(document.all.ddxsjg19.checked==true)
		{
			dialogArguments.all["lx"].value="1";
			dialogArguments.all["glbm4"].value='';
			dialogArguments.all["glbm4"].readOnly=false;
			dialogArguments.all.ywlb.value="00000000000000000000";
		}else{
			if(checkxsjg(3))
			{
				dialogArguments.all["glbm4"].readOnly=true;
				if(document.all.ddxsjg1.checked==true){
					dialogArguments.all["glbm4"].value='01';
				}else{
					if(document.all.ddxsjg2.checked==true){
						dialogArguments.all["glbm4"].value='02';
					}else{
						if(document.all.ddxsjg3.checked==true){
							dialogArguments.all["glbm4"].value='03';
						}else{
							if(document.all.ddxsjg4.checked==true){
								dialogArguments.all["glbm4"].value='04';
							}else{
								if(document.all.ddxsjg6.checked==true){
									dialogArguments.all["glbm4"].value='06';
								}else{
									if(document.all.ddxsjg7.checked==true){
										dialogArguments.all["glbm4"].value='07';
									}else{
										if(document.all.ddxsjg8.checked==true){
											dialogArguments.all["glbm4"].value='08';
										}else{
											if(document.all.ddxsjg9.checked==true){
												dialogArguments.all["glbm4"].value='09';
											}
										}
									}
								}
							}
						}
					}
				}
				var ywlb="";
				if(document.all.ddxsjg1.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.ddxsjg2.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.ddxsjg3.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.ddxsjg4.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				ywlb=ywlb+"0";
				if(document.all.ddxsjg6.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.ddxsjg7.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.ddxsjg8.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				if(document.all.ddxsjg9.checked==true)
					ywlb=ywlb+"1";
				else
					ywlb=ywlb+"0";
				ywlb=ywlb+"0";
				ywlb=ywlb+'0000000000';
				dialogArguments.all.ywlb.value=ywlb;
			}else
				return false;
		}
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"4","大队");
		inityfly("4");
	}else if(document.all.choose5.checked==true){
		//下属中队
		dialogArguments.all["lx"].value="2";
		dialogArguments.all["glbm4"].value='';
		dialogArguments.all["glbm4"].readOnly=false;
		if(document.all.select3.value=='1')
		    dialogArguments.all.ywlb.value="11111111110000000001";
		else if(document.all.select3.value=='2')
			dialogArguments.all.ywlb.value="11111111110000000002";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"5","中队");
		inityfly("5");
	}else if(document.all.choose6.checked==true){
		//派出所
		dialogArguments.all["lx"].value="3";
		dialogArguments.all["glbm4"].value='';
		dialogArguments.all["glbm4"].readOnly=false;
		dialogArguments.all.ywlb.value="00000000000000000000";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"5","中队");
		inityfly("5");
	}else if(document.all.choose7.checked==true){
		//服务站
		dialogArguments.all["lx"].value="4";
		dialogArguments.all["glbm5"].value='';
		dialogArguments.all["glbm5"].readOnly=false;
		dialogArguments.all["glbm4"].value='';
		dialogArguments.all["glbm4"].readOnly=false;
		dialogArguments.all.ywlb.value="00000000000000000000";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"6","执法站");
		inityfly("6");
	}	
	initfzjg("3");
}else if(bmjb=='5'){
	if(document.all.choose11.checked==false&&document.all.choose12.checked==false)
	{
		alert("请选择创建类型");
		return false;
	}	
	//创建中队下属机构
	if(document.all.choose11.checked==true)
	{
		dialogArguments.all["lx"].value="1";
		dialogArguments.all["glbm5"].value='';
		dialogArguments.all["glbm5"].readOnly=false;
		ywlb='00000000000000000000';
		dialogArguments.all.ywlb.value=ywlb;
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"5","中队");
		inityfly("5");
	}else if(document.all.choose12.checked==true){
		//服务站
		dialogArguments.all["lx"].value="4";
		dialogArguments.all["glbm5"].value='';
		dialogArguments.all["glbm5"].readOnly=false;
		dialogArguments.all["glbm4"].value='';
		dialogArguments.all["glbm4"].readOnly=false;
		dialogArguments.all.ywlb.value="00000000000000000000";
		clearFieldOptions(dialogArguments.all["bmjb"]);
		addcomboxitem1(dialogArguments.all["bmjb"],"6","执法站");
		inityfly("6");
	}	
	initfzjg("3");
}
	dialogArguments.all["bmmc"].value="";
	dialogArguments.all["bmqc"].value="";
	dialogArguments.all["yzmc"].value="";
	dialogArguments.all["fzjg"].value="";
	dialogArguments.all["fzr"].value="";
	dialogArguments.all["lxr"].value="";
	dialogArguments.all["lxdh"].value="";
	dialogArguments.all["czhm"].value="";
	dialogArguments.all["lxdz"].value="";
	dialogArguments.all["bz"].value="";
	dialogArguments.all["modal"].value="new";
	dialogArguments.all("delbutton").disabled=true;
	dialogArguments.all("bmyzbutton").disabled=true;
	dialogArguments.all["sjbm"].value=glbm;
	dialogArguments.all["yfly"].value="";
	dialogArguments.all["txzqfr"].value="";
	clearFieldOptions(dialogArguments.all["sjcjzdbm"]);
	clearFieldOptions(dialogArguments.all["sjwfzdbm"]);
	clearFieldOptions(dialogArguments.all["sjsgzdbm"]);
	window.close();
}
function checkxsjg(bj){
	var i=0;
	if(bj==1){
		if(document.all.zondxsjg1.checked==true) i=i+1;
		if(document.all.zondxsjg2.checked==true) i=i+1;
		if(document.all.zondxsjg3.checked==true) i=i+1;
		if(document.all.zondxsjg4.checked==true) i=i+1;
		if(document.all.zondxsjg6.checked==true) i=i+1;
		if(document.all.zondxsjg7.checked==true) i=i+1;
		if(document.all.zondxsjg8.checked==true) i=i+1;
		if(document.all.zondxsjg9.checked==true) i=i+1;
		if(document.all.zondxsjg5.checked==true) i=i+1;
		if(i==0)
		{
			alert("[00R9922JH]:请选择下属机构类型");
			return false;
		}else if(i==1){
			return true;
		}else if(i==2){
			if(document.all.zondxsjg5.checked==true)
            {
				alert("[00R9922JG]:下属机构高速部门只能选择一个");
				return false;
			}
		}
	}else if (bj==2){
		if(document.all.zdxsjg1.checked==true) i=i+1;
		if(document.all.zdxsjg2.checked==true) i=i+1;
		if(document.all.zdxsjg3.checked==true) i=i+1;
		if(document.all.zdxsjg4.checked==true) i=i+1;
		if(document.all.zdxsjg6.checked==true) i=i+1;
		if(document.all.zdxsjg7.checked==true) i=i+1;
		if(document.all.zdxsjg8.checked==true) i=i+1;
		if(document.all.zdxsjg9.checked==true) i=i+1;
		if(i==0)
		{
			alert("[00R9922JH]:请选择下属机构类型");
			return false;
		}else if(i==1){
			return true;
		}
	}else if (bj==3){
		if(document.all.ddxsjg1.checked==true) i=i+1;
		if(document.all.ddxsjg2.checked==true) i=i+1;
		if(document.all.ddxsjg3.checked==true) i=i+1;
		if(document.all.ddxsjg4.checked==true) i=i+1;
		if(document.all.ddxsjg6.checked==true) i=i+1;
		if(document.all.ddxsjg7.checked==true) i=i+1;
		if(document.all.ddxsjg8.checked==true) i=i+1;
		if(document.all.ddxsjg9.checked==true) i=i+1;
		if(i==0)
		{
			alert("[00R9922JH]:请选择下属机构类型");
			return false;
		}else if(i==1){
			return true;
		}	
	}
	return true;
}
function initfzjg(jb){
	clearFieldOptions(dialogArguments.all["fzjg"]);
	if(jb=='2'){
		addcomboxitem2(dialogArguments.all["fzjg"],"<%=hpt%>"+"O","<%=hpt%>"+"O");
	}else{
    	<%for(int i=0;i<fzjg.length;i++){%>
     	    addcomboxitem2(dialogArguments.all["fzjg"],"<%=fzjg[i]%>","<%=fzjg[i]%>");
     	<%}%>
	}
}
function inityfly(jb){
	if(jb=='4'||jb=='5')
		dialogArguments.all.yfly.disabled=false;
	else{
		dialogArguments.all.yfly.value='';
		dialogArguments.all.yfly.disabled=true;
	}
		
}
function addcomboxitem2(field,value,caption){
	  opt = dialogArguments.createElement( "OPTION" );
	  dialogArguments.all["fzjg"].options.add(opt);
	  opt.innerText =caption;
	  opt.value = value;
	}
function addcomboxitem1(field,value,caption){
	  opt = dialogArguments.createElement( "OPTION" );
	  dialogArguments.all["bmjb"].options.add(opt);
	  opt.innerText =caption;
	  opt.value = value;
	}
function onClickXsjg(bj){
	if(bj=='1'){
	  if(document.all.zdxsjg19.checked){
	  	document.all.zdxsjg1.checked = false;
	  	document.all.zdxsjg2.checked = false;
	  	document.all.zdxsjg3.checked = false;
	  	document.all.zdxsjg4.checked = false;
	  	document.all.zdxsjg6.checked = false;
	  	document.all.zdxsjg7.checked = false;
	  	document.all.zdxsjg8.checked = false;
	  	document.all.zdxsjg9.checked = false;
	  	document.all.zdxsjg1.disabled = true;
	  	document.all.zdxsjg2.disabled = true;
	  	document.all.zdxsjg3.disabled = true;
	  	document.all.zdxsjg4.disabled = true;
	  	document.all.zdxsjg6.disabled = true;
	  	document.all.zdxsjg7.disabled = true;
	  	document.all.zdxsjg8.disabled = true;
	  	document.all.zdxsjg9.disabled = true;
	  }else{
		  	document.all.zdxsjg1.disabled = false;
		  	document.all.zdxsjg2.disabled = false;
		  	document.all.zdxsjg3.disabled = false;
		  	document.all.zdxsjg4.disabled = false;
		  	document.all.zdxsjg6.disabled = false;
		  	document.all.zdxsjg7.disabled = false;
		  	document.all.zdxsjg8.disabled = false;
		  	document.all.zdxsjg9.disabled = false;
	  }
	}else if(bj=='2'){
		  if(document.all.ddxsjg19.checked){
			  	document.all.ddxsjg1.checked = false;
			  	document.all.ddxsjg2.checked = false;
			  	document.all.ddxsjg3.checked = false;
			  	document.all.ddxsjg4.checked = false;
			  	document.all.ddxsjg6.checked = false;
			  	document.all.ddxsjg7.checked = false;
			  	document.all.ddxsjg9.checked = false;
			  	document.all.ddxsjg1.disabled = true;
			  	document.all.ddxsjg2.disabled = true;
			  	document.all.ddxsjg3.disabled = true;
			  	document.all.ddxsjg4.disabled = true;
			  	document.all.ddxsjg6.disabled = true;
			  	document.all.ddxsjg7.disabled = true;
			  	document.all.ddxsjg8.disabled = true;
			  	document.all.ddxsjg9.disabled = true;
			  }else{
				  	document.all.ddxsjg1.disabled = false;
				  	document.all.ddxsjg2.disabled = false;
				  	document.all.ddxsjg3.disabled = false;
				  	document.all.ddxsjg4.disabled = false;
				  	document.all.ddxsjg6.disabled = false;
				  	document.all.ddxsjg7.disabled = false;
				  	document.all.ddxsjg8.disabled = false;
				  	document.all.ddxsjg9.disabled = false;
			  }		
	}else if(bj=='3'){
		  if(document.all.zondxsjg19.checked){
			  	document.all.zondxsjg1.checked = false;
			  	document.all.zondxsjg2.checked = false;
			  	document.all.zondxsjg3.checked = false;
			  	document.all.zondxsjg4.checked = false;
			  	document.all.zondxsjg6.checked = false;
			  	document.all.zondxsjg7.checked = false;
			  	document.all.zondxsjg8.checked = false;
			  	document.all.zondxsjg9.checked = false;
			  	document.all.zondxsjg5.checked = false;
			  	document.all.zondxsjg1.disabled = true;
			  	document.all.zondxsjg2.disabled = true;
			  	document.all.zondxsjg3.disabled = true;
			  	document.all.zondxsjg4.disabled = true;
			  	document.all.zondxsjg6.disabled = true;
			  	document.all.zondxsjg7.disabled = true;
			  	document.all.zondxsjg8.disabled = true;
			  	document.all.zondxsjg9.disabled = true;
			  	document.all.zondxsjg5.disabled = true;
			  }else{
				  	document.all.zondxsjg1.disabled = false;
				  	document.all.zondxsjg2.disabled = false;
				  	document.all.zondxsjg3.disabled = false;
				  	document.all.zondxsjg4.disabled = false;
				  	document.all.zondxsjg6.disabled = false;
				  	document.all.zondxsjg7.disabled = false;
				  	document.all.zondxsjg8.disabled = false;
				  	document.all.zondxsjg9.disabled = false;
				  	document.all.zondxsjg5.disabled = false;
			  }			
	}else if(bj=='4'){
		  if(document.all.zduixsjg19.checked){
			  	document.all.zduixsjg1.checked = false;
			  	document.all.zduixsjg2.checked = false;
			  	document.all.zduixsjg3.checked = false;
			  	document.all.zduixsjg4.checked = false;
			  	document.all.zduixsjg6.checked = false;
			  	document.all.zduixsjg7.checked = false;
			  	document.all.zduixsjg9.checked = false;
			  	document.all.zduixsjg1.disabled = true;
			  	document.all.zduixsjg2.disabled = true;
			  	document.all.zduixsjg3.disabled = true;
			  	document.all.zduixsjg4.disabled = true;
			  	document.all.zduixsjg6.disabled = true;
			  	document.all.zduixsjg7.disabled = true;
			  	document.all.zduixsjg8.disabled = true;
			  	document.all.zduixsjg9.disabled = true;
			  }else{
				  	document.all.zduixsjg1.disabled = false;
				  	document.all.zduixsjg2.disabled = false;
				  	document.all.zduixsjg3.disabled = false;
				  	document.all.zduixsjg4.disabled = false;
				  	document.all.zduixsjg6.disabled = false;
				  	document.all.zduixsjg7.disabled = false;
				  	document.all.zduixsjg8.disabled = false;
				  	document.all.zduixsjg9.disabled = false;
			  }			
	}
	}
</script>
</head>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" onload="initdata()">

	  <table border="0" cellspacing="1" cellpadding="0" class="list_table">
	  <%--支队 --%>
        <tr id="tr1">
          <td class="list_headrig" width="50">
          <input type="radio" name="radiobutton" id="choose1" onclick="zhidxs1.style.display='inline-block';zhidxs2.style.display='none';" value="radiobutton"></td>
          <td class="list_body_out" width="500" colspan="2">下属机构
          <span id="zhidxs1" style="display:inline-block">
          <input class="text_12" type="checkbox" name="zdxsjg1"  id="zdxsjg1" value="1"><label for="zdxsjg1">秩序部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg2"  id="zdxsjg2" value="2"><label for="zdxsjg2">公路部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg3"  id="zdxsjg3" value="3"><label for="zdxsjg3">事故部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg4"  id="zdxsjg4" value="4"><label for="zdxsjg4">车驾管部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg6"  id="zdxsjg6" value="6"><label for="zdxsjg6">队伍建设部门</label><br>
          <input class="text_12" type="checkbox" name="zdxsjg7"  id="zdxsjg7" value="7"><label for="zdxsjg7">宣教部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg8"  id="zdxsjg8" value="8"><label for="zdxsjg8">法制部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg9"  id="zdxsjg9" value="9"><label for="zdxsjg9">科技部门</label>
          <input class="text_12" type="checkbox" name="zdxsjg19"  id="zdxsjg19" value="19" onclick="onClickXsjg(1)"><label for="zdxsjg19">其他部门</label>
          </span>
          </td>
        </tr>
        <tr id="tr2">
          <td class="list_headrig">
          <input type="radio" name="radiobutton" id="choose2"  onclick="zhidxs1.style.display='none';zhidxs2.style.display='inline-block';" value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >大队
          <span id="zhidxs2" style="display:inline-block">
            <select name="select2" style="width:120">
              <option value="1" selected>城区大队</option>
              <option value="2">县市大队</option>
              <option value="3">高速大队</option>
            </select>
          </span>
          </td>
        </tr>
        <tr id="tr3">
          <td class="list_headrig"><input type="radio" id="choose3" onclick="zhidxs1.style.display='none';zhidxs2.style.display='none';"  name="radiobutton" value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >执法站</td>
        </tr>
        <%--大队 --%>
        <tr id="tr4">
          <td class="list_headrig"><input type="radio" name="radiobutton"  onclick="ddxs1.style.display='inline-block';ddxs2.style.display='none';" id="choose4"  value="radiobutton"></td>
          <td class="list_body_out" colspan="2">下属机构
          <span id="ddxs1" style="display:inline-block">
          <input class="text_12" type="checkbox" name="ddxsjg1"  id="ddxsjg1" value="1"><label for="ddxsjg1">秩序部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg2"  id="ddxsjg2" value="2"><label for="ddxsjg2">公路部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg3"  id="ddxsjg3" value="3"><label for="ddxsjg3">事故部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg4"  id="ddxsjg4" value="4"><label for="ddxsjg4">车驾管部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg6"  id="ddxsjg6" value="6"><label for="ddxsjg6">队伍建设部门</label><br>
          <input class="text_12" type="checkbox" name="ddxsjg7"  id="ddxsjg7" value="7"><label for="ddxsjg7">宣教部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg8"  id="ddxsjg8" value="8"><label for="ddxsjg8">法制部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg9"  id="ddxsjg9" value="9"><label for="ddxsjg9">科技部门</label>
          <input class="text_12" type="checkbox" name="ddxsjg19" id="ddxsjg19" value="19" onclick="onClickXsjg(2)"><label for="ddxsjg19">其他部门 </label>
          </span>
          </td>
        </tr>
        <tr id="tr5">
          <td class="list_headrig"><input type="radio" name="radiobutton" onclick="ddxs1.style.display='none';ddxs2.style.display='inline-block';"  id="choose5"  value="radiobutton"></td>
          <td  colspan="2" class="list_body_out">中队
          <span id="ddxs2" style="display:inline-block">
            <select name="select3" style="width:120">
              <option value="1" selected>城区中队</option>
              <option value="2">公路中队</option>
            </select>
          </span>
          </td>
        </tr>
        <tr id="tr6">
          <td class="list_headrig"><input type="radio" name="radiobutton" id="choose6"  onclick="ddxs1.style.display='none';ddxs2.style.display='none';"  value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >派出所</td>
        </tr>        
        <tr id="tr7">
          <td class="list_headrig"><input type="radio" name="radiobutton" id="choose7"  onclick="ddxs1.style.display='none';ddxs2.style.display='none';"  value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >执法站</td>
        </tr>
        <%--总队 --%>
        <tr id="tr8">
          <td class="list_headrig"><input type="radio" name="radiobutton" id="choose8"  onclick="zdxs1.style.display='inline-block';zdxs2.style.display='none';zdxs3.style.display='none';" value="radiobutton"></td>
          <td class="list_body_out" colspan="2">直属部门
          <span id="zdxs1" style="display:inline-block">
          <input class="text_12" type="checkbox" name="zondxsjg1" id="zondxsjg1"  value="1"><label for="zondxsjg1">秩序部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg2" id="zondxsjg2" value="2"><label for="zondxsjg2">公路部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg3" id="zondxsjg3" value="3"><label for="zondxsjg3">事故部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg4" id="zondxsjg4" value="4"><label for="zondxsjg4">车驾管部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg6" id="zondxsjg6" value="6"><label for="zondxsjg6">队伍建设部门</label><br>
          <input class="text_12" type="checkbox" name="zondxsjg7" id="zondxsjg7" value="7"><label for="zondxsjg7">宣教部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg8" id="zondxsjg8"  value="8"><label for="zondxsjg8">法制部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg9" id="zondxsjg9"  value="9"><label for="zondxsjg9">科技部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg5" id="zondxsjg5" value="9"><label for="zondxsjg5">高速部门</label>
          <input class="text_12" type="checkbox" name="zondxsjg19" id="zondxsjg19" value="19" onclick="onClickXsjg(3)"><label for="zondxsjg19">其他部门</label>
          </span>         
          </td>
        </tr>
        <tr id="tr9">
          <td class="list_headrig"><input type="radio" name="radiobutton" onclick="zdxs1.style.display='none';zdxs2.style.display='inline-block';zdxs3.style.display='none';" id="choose9" value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >支队
          <span id="zdxs2" style="display:none">
            <select name="select" style="width:120">
              <option value="1" selected>地市支队</option>
              <option value="3">高速支队</option>
            </select>
            </span>
            </td>
        </tr>
        <tr id="tr15">
          <td class="list_headrig">
          <input type="radio" name="radiobutton" id="choose15" onclick="zdxs1.style.display='none';zdxs2.style.display='none';zdxs3.style.display='inline-block';" value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >大队
          <span id="zdxs3" style="display:none">
            <select name="select15" style="width:120">
              <option value="1" selected>城区大队</option>
              <option value="2">县市大队</option>
           </select>
           </span>
           </td>
        </tr>
        <tr id="tr10">
          <td class="list_headrig"><input type="radio" id="choose10"  onclick="zdxs1.style.display='none';zdxs2.style.display='none';zdxs3.style.display='none';"  name="radiobutton" value="radiobutton"></td>
          <td  class="list_body_out" colspan="2" >执法站
          </td>
        </tr>
        <%--中队 --%>
        <tr id="tr11">
          <td class="list_headrig"><input type="radio" name="radiobutton"  id="choose11"  value="radiobutton"></td>
          <td class="list_body_out" class="list_headrig" colspan="2">下属机构
          </td>
        </tr>     
        <tr id="tr12">
          <td class="list_headrig"><input type="radio" id="choose12"   name="radiobutton" value="radiobutton"></td>
          <td class="list_body_out" colspan="2" >执法站</td>
        </tr>   
        <%--高速总队 --%>
        <tr id="tr13">
          <td class="list_headrig"><input type="radio" name="radiobutton"  onclick="gszdxs1.style.display='inline-block';" id="choose13" value="radiobutton"></td>
          <td height="26" class="list_body_out" colspan="2" >支队
          <span id="gszdxs1" style="display:inline-block">
            <select name="select13" style="width:120">
              <option value="3">高速支队</option>
            </select>
          </span>
          </td>
        </tr>
        <tr id="tr14">
          <td class="list_headrig"><input type="radio" id="choose14" onclick="gszdxs1.style.display='none';" name="radiobutton" value="radiobutton"></td>
          <td class="list_body_out">执法站
          </td>
        </tr>                
        <tr>
          <td height="26" colspan="3" align="center" class="list_body_out"><input name="button" type="button"  class="button"  onClick="saveone()" value=" 确 定 ">
              <input name="button" type="button"  onClick="window.close()"  class="button" value=" 取 消 "></td>
        </tr>
      </table>
</body>
</html>
