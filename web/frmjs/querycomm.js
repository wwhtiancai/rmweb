function selectGlbm(v_field){
	var value=v_field.value;
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("department.frm?method=choosedept&minbmjb=5&flag=1&glbm="+value + "&rtn=1", "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		v_field.value=vReturnValue;
	}
}
function moveSelectedOptions(from,to) {
	index=from.selectedIndex;
	if(from.selectedIndex!=-1){
		while(from.selectedIndex!=-1){
			oDiv=new Option(from.options[from.selectedIndex].text,from.options[from.selectedIndex].value);
			to.options[to.options.length]=oDiv;
			from.remove(from.selectedIndex);
		}
		if(from.options.length!=0){
			if(from.options.length>index)
				from.options[index].selected=true;
			else
				from.options[index-1].selected=true;
		}
	}
}
function changeTjsjfwState(fieldstat1){
		formain.radio_TJNY.checked = (fieldstat1==1);
		formain.radio_TJNYD.checked = (fieldstat1==2);
		formain.radio_TJRQD.checked = (fieldstat1==3);
		formain.tjny_1.disabled = (fieldstat1!=1);
		formain.tjny_2.disabled = (fieldstat1!=1);	
		
		formain.tjnyd_1.disabled = (fieldstat1!=2);
		formain.tjnyd_2.disabled = (fieldstat1!=2);
		formain.tjnyd_3.disabled = (fieldstat1!=2);
		formain.tjnyd_4.disabled = (fieldstat1!=2);

		formain.tjrqds.disabled = (fieldstat1!=3);
		formain.tjrqde.disabled = (fieldstat1!=3);
		formain.sjbj.value = fieldstat1;
}
function changeXzqhGlbmState(fieldstat){
		if(formain.radio_GLBM!=undefined){
			formain.radio_GLBM.checked = fieldstat;
		}
		formain.tjdwlb1.disabled = fieldstat;
		formain.tjdwlb2.disabled = fieldstat;
		formain.tianjia.disabled = fieldstat;
		formain.yiqu.disabled = fieldstat;
		formain.glbm.disabled = (!fieldstat);
		formain.selglbm.disabled = (!fieldstat);
		formain.includeXjbm.disabled = (!fieldstat);
		formain.tjbj.value = (fieldstat?1:2);		
}
function changeSgbhDjbhState(fieldstat){
    formain.radio_SGBH.checked = fieldstat;
	formain.sgbh_1.disabled = (!fieldstat);
	formain.sgbh_2.disabled = (!fieldstat);
	
	formain.djbh_1.disabled = fieldstat;
	formain.djbh_2.disabled = fieldstat;	
	formain.sgbj.value = (fieldstat?1:2);
}
function changeDsrxxState(fieldstat){
	formain.checkbox_DSRXX.checked = fieldstat;
	formain.xm.disabled = (!fieldstat);
	formain.sfzmhm.disabled = (!fieldstat);
	formain.hphm.disabled = (!fieldstat);
	formain.dsrbj.value = (fieldstat?2:1);
}
function getSelectCode(codefieldname){
    var sTmp = "";
	for(var i=0;i<document.all[codefieldname+"lb2"].options.length;i++){
		sTmp += document.all[codefieldname+"lb2"].options[i].value + ",";
	}
	if(sTmp!="")sTmp = sTmp.substring(0,sTmp.length - 1);
	document.all[codefieldname].value = sTmp;
}
function changeSelectCodeState(fieldname){
	var fn = fieldname.substr(9).toLowerCase();
	var fieldstat = false;
	if(formain.all[fieldname].checked){
		fieldstat = true;
	}
	formain.all[fn+"lb1"].style.visibility = (fieldstat?'visible':'hidden');
	formain.all[fn+"lb2"].style.visibility = (fieldstat?'visible':'hidden');
	formain.all[fn+"lb1"].disabled = (!fieldstat);
	formain.all[fn+"lb2"].disabled = (!fieldstat);			
	formain.all["add" + fn +"button"].style.visibility = (fieldstat?'visible':'hidden');
	formain.all["move" + fn +"button"].style.visibility = (fieldstat?'visible':'hidden');
	formain.all["add" + fn +"button"].disabled = (!fieldstat);
	formain.all["move" + fn +"button"].disabled = (!fieldstat);	
	if(fieldstat)getSelectCode(fn);
	else formain.all[fn].value="";			
}
function changeBaxjglbm(){
	if(formain.baxjbm.value=="1"){
		formain.includeXjbm.checked = true;	
	}else{
		formain.includeXjbm.checked = false;
	}
}
function checkTjtj(){
	if(formain.tjbj.value==2){
		var i = 0;
		formain.xzqh.value = "";
		for(i=0;i<formain.tjdwlb2.options.length;i++){
			formain.xzqh.value += formain.tjdwlb2.options[i].value + ",";
		}
		if(formain.xzqh.value!=""){
			formain.xzqh.value = formain.xzqh.value.substring(0,formain.xzqh.value.length - 1);
		}
		if(formain.xzqh.value==""){
				displayInfoHtml("[03G3072J3]:请选择行政区划!");									
				return false;			
		}
	}else{
		formain.xzqh.value = "";
	}
	return true;
}
function checkTjsj(){
   if(formain.sjbj.value==1){
   		if(formain.tjny_1.value.length!=4){
				displayInfoHtml("[03G3072J4]:统计年份必须为4位数字!");
				formain.tjny_1.focus();						
				return false;			   		
   		}
   		if(formain.tjny_2.value.length==1)formain.tjny_2.value = "0" + formain.tjny_2.value;
   		if(formain.tjny_2.value.length!=2&&parseInt(formain.tjny_2.value,10)<1||parseInt(formain.tjny_2.value,10)>12){
				displayInfoHtml("[03G3072J5]:统计月份必须为2位数字，范围为1-12!");
				formain.tjny_2.focus();						
				return false;			   		
   		}   		
   }else if(formain.sjbj.value==2){
   		if(formain.tjnyd_1.value.length!=4){
				displayInfoHtml("[03G3072J6]:统计起始年份必须为4位数字!");
				formain.tjnyd_1.focus();						
				return false;			   		
   		}
   		if(formain.tjnyd_2.value.length==1)formain.tjnyd_2.value = "0" + formain.tjnyd_2.value;
   		if(formain.tjnyd_2.value.length!=2&&parseInt(formain.tjnyd_2.value,10)<1||parseInt(formain.tjnyd_2.value,10)>12){
				displayInfoHtml("[03G3072J7]:统计起始月份必须为2位数字，范围为1-12!");
				formain.tjnyd_2.focus();						
				return false;			   		
   		}
   		if(formain.tjnyd_3.value.length!=4){
				displayInfoHtml("[03G3072J8]:统计结束年份必须为4位数字!");
				formain.tjnyd_3.focus();						
				return false;			   		
   		}
   		if(formain.tjnyd_4.value.length==1)formain.tjnyd_4.value = "0" + formain.tjnyd_4.value;
   		if(formain.tjnyd_4.value.length!=2&&parseInt(formain.tjnyd_4.value,10)<1||parseInt(formain.tjnyd_4.value,10)>12){
				displayInfoHtml("[03G3072J9]:统计结束月份必须为2位数字，范围为1-12!");
				formain.tjnyd_4.focus();						
				return false;			   		
   		}
   		if(formain.tjnyd_1.value+formain.tjnyd_2.value>formain.tjnyd_3.value+formain.tjnyd_4.value){
				displayInfoHtml("[03G3072J10]:统计结束年月必须大于开始年月!");
				formain.tjnyd_1.focus();						
				return false;			   		   		   		      		   		   
   		}
   }
   return true;
}
function procSgfssj(){
	if(formain.sjbj.value==1){
		formain.tjny.value = formain.tjny_1.value + formain.tjny_2.value;
		if(V_SG_SFSYY=="2"){
			formain.sgfssj1.value = formain.tjny_1.value + "-" + formain.tjny_2.value + "-" + V_SG_TJKSR;
		    formain.sgfssj2.value = getDay(DateAddMonth(formain.sgfssj1.value,1),-1);				
		}else{		
			formain.sgfssj1.value = DateAddMonth(formain.tjny_1.value + "-" + formain.tjny_2.value + "-" + V_SG_TJKSR,-1);
			formain.sgfssj2.value = getDay(formain.tjny_1.value + "-" + formain.tjny_2.value + "-" + V_SG_TJKSR,-1);			
		}			 
	}else if(formain.sjbj.value==2){	    
		formain.tjnyd.value = formain.tjnyd_1.value + formain.tjnyd_2.value + "-" + formain.tjnyd_3.value + formain.tjnyd_4.value;
		if(V_SG_SFSYY=="2"){
			formain.sgfssj1.value = formain.tjnyd_1.value + "-" + formain.tjnyd_2.value + "-" + V_SG_TJKSR;
			formain.sgfssj2.value = getDay(DateAddMonth(formain.tjnyd_3.value + "-" + formain.tjnyd_4.value + "-"+V_SG_TJKSR,1),-1);			    
		}else{
			formain.sgfssj1.value = DateAddMonth(formain.tjnyd_1.value + "-" + formain.tjnyd_2.value + "-" + V_SG_TJKSR,-1);
			formain.sgfssj2.value = getDay(formain.tjnyd_3.value + "-" + formain.tjnyd_4.value + "-"+V_SG_TJKSR,-1);		
		}
	}else if(formain.sjbj.value==3){
		formain.sgfssj1.value = formain.tjrqds.value;
		formain.sgfssj2.value = formain.tjrqde.value;
	}
}
function procDsrxx(){
	if(formain.dsrbj.value==1){
		formain.xm.value = "";
		formain.sfzmhm.value = "";
		formain.hphm.value = "";
	}else{
		if(formain.hphm.value.length==1){
			formain.hphm.value = "";
		}
	}
}
function query(tableid,flag,other_flag){
	//查询定制处理
	if(tableid=="032011"){
		if(formain.sgbj.value==1){
			formain.sgbh.value = formain.sgbh_2.value;
			formain.djbh.value = "";
			if(formain.sglx.value==""){
				displayInfoHtml("[03G3072J1]:请选择事故类型!");									
				return false;
			}
			if(formain.sgbh.value==""){
				displayInfoHtml("[03G3072J2]:请确定事故编号!");
				return false;
			}
			if(formain.sglx.value=="2"){
				formain.fparam1.value = "acd_dutysimple";
				formain.fparam2.value = "b";				
			}else{
				formain.fparam1.value = "acd_file";
				formain.fparam2.value = "b";	
			}
		}else{
			formain.djbh.value = formain.djbh_2.value;
			formain.sgbh.value = "";
			if(formain.djbh.value==""){
				displayInfoHtml("[03G3072J2]:请确定登记编号!");
				return false;
			}		
			formain.fparam2.value = "";				
		}
	}else if(tableid=="032021"||tableid=="032023"||tableid=="032101"||tableid=="032201"){
		procDsrxx();
	}else if(tableid=="032043"||tableid=="035031"){
		displaysxzt();
		if(formain.sxlx_sxlx.value=="2"){
			if(formain.sxlx_cqlx1.checked){
				formain.gnxh.value = "0";
			}else{
				formain.gnxh.value = "1";
			}			
		}else if(formain.sxlx_sxlx.value=="3"){
			if(formain.sxlx_cqlx1.checked){
				formain.gnxh.value = "2";
			}else{
				formain.gnxh.value = "3";
			}			
		}else if(formain.sxlx_sxlx.value=="4"){
			formain.gnxh.value = "4";
		}else if(formain.sxlx_sxlx.value=="5"){
			if(formain.sxlx_cqlx1.checked){
				formain.gnxh.value = "5";
			}else{
				formain.gnxh.value = "6";
			}			
		}
		formain.cqts.value = "-" + formain.sxlx_cqts.value;
	}else if(tableid=="059507"){
		formain.gnxh.value = "1";
	}
	if(!checkcondition()){
		return false;
	}	
	formain.excel.value = flag;
	if(other_flag==undefined){
		if(flag==1){
			formain.action="commonquery.frm?method=queryResult";	
			formain.target = "paramIframe";
		}else{
			formain.action="commonquery.frm?method=queryCommon";	
			formain.target = "";
		}	
	}else{
		if(flag==1){
			formain.action="commonquery.frm?method=queryResult" + other_flag;	
			formain.target = "paramIframe";
		}else{
			formain.action="commonquery.frm?method=queryCommon" + other_flag;	
			formain.target = "";
		}		
	}
	formain.submit();
} 

function stat(tableid){
	if(tableid=="033012"||tableid=="033102"||tableid=="033104"||tableid=="033105"){		
		if(!checkTjsj())return false;
		procSgfssj();				
	}
	if(tableid=="033012"){
		//简易事故统计
		if(!checkTjtj())return false;
		if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
			formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(a.glbm,'" + formain.glbm.value + "')";
			formain.flzd_m.value = "glbm";
			formain.flzd_dm.value = "glbm";				
		}else{
			formain.flzd.value = "frm_comm_pkg.gettjxzqh(a.xzqh," + formain.bmjb.value + ")";
			formain.flzd_m.value = "xzqh";
			formain.flzd_dm.value = "xzqh";						
		}		
	}
	if(tableid=="033102"){
		if(!checkTjtj())return false;
		if(formain.tjbb.value==""){
			displayInfoHtml("[03G3072J11]:请选择统计报表!");
			return false;
		}else{
			var tmparr = formain.tjbb.value.split("#");
			formain.flzd.value = tmparr[0].replace("$bmjb",formain.bmjb.value);
			formain.flzd_m.value = tmparr[1];
			formain.flzd_dm.value = tmparr[2];
			if(tmparr.length>3){
				formain.byzb.value = tmparr[2];
			}else{
				formain.byzb.value = "";
			}
			if(formain.flzd_m.value=="glbm"){
				if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
					formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(a.glbm,'" + formain.glbm.value + "')";
					formain.flzd_m.value = "glbm";
					formain.flzd_dm.value = "glbm";				
				}else{
					formain.flzd.value = "frm_comm_pkg.gettjxzqh(a.xzqh," + formain.bmjb.value + ")";
					formain.flzd_m.value = "xzqh";
					formain.flzd_dm.value = "xzqh";						
				}				
			}
		}
		if(formain.tjlx.value=="1"){
			if(formain.tbhb.value=="1"){
				formain.tjclid.value = "030005";
			}else if(formain.tbhb.value=="2"){
				formain.tjclid.value = "030007";
			}else{
				formain.tjclid.value = "030003";
			}			
		}else{
			if(formain.tbhb.value=="1"){
				formain.tjclid.value = "030004";
			}else if(formain.tbhb.value=="2"){
				formain.tjclid.value = "030006";
			}else{
				formain.tjclid.value = "030002";
			}		
		}
		if(formain.swsjxd.value==""){
			formain.fparam1.value = "swrs+swrsq+szrs";
			formain.fparam2.value = "ssrs";
		}else if(formain.swsjxd.value=="5"){
			formain.fparam1.value = "swrs24";
			formain.fparam2.value = "ssrs24";
		}else if(formain.swsjxd.value=="4"){
			formain.fparam1.value = "swrs3 + szrs";
			formain.fparam2.value = "ssrs3";
		}else if(formain.swsjxd.value=="2"){
			formain.fparam1.value = "swrs7+ szrs";
			formain.fparam2.value = "ssrs7";
		}else if(formain.swsjxd.value=="3"){
			formain.fparam1.value = "swrs30 + szrs";
			formain.fparam2.value = "ssrs30";
		}									
	}
	if(tableid=="033104"){
		if(!checkTjtj())return false;
		if(formain.tjbb_qx.value==""){
			displayInfoHtml("[03G3072J11]:请选择统计报表!");
			return false;
		}else{
			var tmparr = formain.tjbb_qx.value.split("#");
			formain.flzd.value = tmparr[0].replace("$bmjb",formain.bmjb.value);
			formain.flzd_m.value = tmparr[1];
			formain.flzd_dm.value = tmparr[2];
			if(tmparr.length>3){
				formain.byzb.value = tmparr[3];
			}else{
				formain.byzb.value = "";
			}
			if(formain.flzd_m.value=="glbm"){
				if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
					formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(a.glbm,'" + formain.glbm.value + "')";
					formain.flzd_m.value = "glbm";
					formain.flzd_dm.value = "glbm";				
				}else{
					formain.flzd.value = "frm_comm_pkg.gettjxzqh(a.xzqh," + formain.bmjb.value + ")";
					formain.flzd_m.value = "xzqh";
					formain.flzd_dm.value = "xzqh";						
				}				
			}			
		}
		if(formain.tjlx.value=="1"){
			if(formain.tbhb.value=="1"){
				formain.tjclid.value = "030011";
			}else if(formain.tbhb.value=="2"){
				formain.tjclid.value = "030013";
			}else{
				formain.tjclid.value = "030009";
			}			
		}else{
			if(formain.tbhb.value=="1"){
				formain.tjclid.value = "030010";
			}else if(formain.tbhb.value=="2"){
				formain.tjclid.value = "030012";
			}else{
				formain.tjclid.value = "030008";
			}		
		}
		if(formain.swsjxd.value==""){
			formain.fparam1.value = "swrs+swrsq+szrs";
			formain.fparam2.value = "ssrs";
		}else if(formain.swsjxd.value=="5"){
			formain.fparam1.value = "swrs24";
			formain.fparam2.value = "ssrs24";
		}else if(formain.swsjxd.value=="4"){
			formain.fparam1.value = "swrs3 + szrs";
			formain.fparam2.value = "ssrs3";
		}else if(formain.swsjxd.value=="2"){
			formain.fparam1.value = "swrs7+ szrs";
			formain.fparam2.value = "ssrs7";
		}else if(formain.swsjxd.value=="3"){
			formain.fparam1.value = "swrs30 + szrs";
			formain.fparam2.value = "ssrs30";
		}			
	}
	if(tableid=="033105"){
		if(!checkTjtj())return false;
		if(formain.tjbb_sw.value==""){
			displayInfoHtml("[03G3072J11]:请选择统计报表!");
			return false;
		}else{
			var tmparr = formain.tjbb_sw.value.split("#");
			formain.flzd.value = tmparr[0].replace("$bmjb",formain.bmjb.value);
			formain.flzd_m.value = tmparr[1];
			formain.flzd_dm.value = tmparr[2];
			if(tmparr.length>3){
				formain.byzb.value = tmparr[3];
			}else{
				formain.byzb.value = "";
			}
			if(formain.flzd_m.value=="glbm"){
				if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
					formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(a.glbm,'" + formain.glbm.value + "')";
					formain.flzd_m.value = "glbm";
					formain.flzd_dm.value = "glbm";				
				}else{
					formain.flzd.value = "frm_comm_pkg.gettjxzqh(a.xzqh," + formain.bmjb.value + ")";
					formain.flzd_m.value = "xzqh";
					formain.flzd_dm.value = "xzqh";						
				}				
			}			
		}
		if(formain.tbhb.value=="1"){
			formain.tjclid.value = "030015";
		}else if(formain.tbhb.value=="2"){
			formain.tjclid.value = "030016";
		}else{
			formain.tjclid.value = "030014";
		}			
		if(formain.swsjxd.value==""){
			formain.fparam1.value = "decode(b.shcd,'1',1,'2',1,'3',1,'4',1,0)";
			formain.fparam2.value = "decode(b.shcd,'1',1,'4',1,0)";
			formain.fparam3.value = "decode(b.shcd,'2',1,'3',1,0)";
			formain.fparam7.value = "swrs+swrsq+szrs";
		}else if(formain.swsjxd.value=="5"){
			formain.fparam1.value = "decode(b.shcd24,'1',1,'2',1,'3',1,'4',1,0)";
			formain.fparam2.value = "decode(b.shcd24,'1',1,'4',1,0)";
			formain.fparam3.value = "decode(b.shcd24,'2',1,'3',1,0)";
			formain.fparam7.value = "swrs24";
		}else if(formain.swsjxd.value=="4"){
			formain.fparam1.value = "decode(b.shcd3,'1',1,'2',1,'3',1,'4',1,0)";
			formain.fparam2.value = "decode(b.shcd3,'1',1,'4',1,0)";
			formain.fparam3.value = "decode(b.shcd3,'2',1,'3',1,0)";
			formain.fparam7.value = "swrs3 + szrs";
		}else if(formain.swsjxd.value=="2"){
			formain.fparam1.value = "decode(b.shcd7,'1',1,'2',1,'3',1,'4',1,0)";
			formain.fparam2.value = "decode(b.shcd7,'1',1,'4',1,0)";
			formain.fparam3.value = "decode(b.shcd7,'2',1,'3',1,0)";
			formain.fparam7.value = "swrs7+ szrs";
		}else if(formain.swsjxd.value=="3"){
			formain.fparam1.value = "decode(b.shcd30,'1',1,'2',1,'3',1,'4',1,0)";
			formain.fparam2.value = "decode(b.shcd30,'1',1,'4',1,0)";
			formain.fparam3.value = "decode(b.shcd30,'2',1,'3',1,0)";
			formain.fparam7.value = "swrs30 + szrs";
		}		
	}
	if(tableid=="035021"){
		if(!checkTjtj())return false;
		if(formain.cdbh.value=="C502"){	
			formain.fparam1.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'1',1,0)";
			formain.fparam2.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'2',1,0)";
			formain.fparam3.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'3',1,0)";
			formain.fparam4.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'4',1,0)";
			formain.fparam5.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'5',1,0)";
			formain.fparam6.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'6',1,0)";
			formain.fparam7.value = "decode(acd_comm_pkg.getClztdm(a.dqbz,a.clzt,b.tjr1),'7',1,0)";
		}else if(formain.cdbh.value=="C510"){	
			formain.fparam1.value = "decode(a.slbj,'2',1,0)";
			formain.fparam2.value = "decode(a.slbj,'1',1,0)";
			formain.fparam3.value = "decode(a.fhjg,'1',1,0)";
			formain.fparam4.value = "decode(a.fhjg,'2',1,0)";
		}else if(formain.cdbh.value=="C511"){
			formain.fparam1.value = "decode(a.xfyyfl,'01',1,0)";
			formain.fparam2.value = "decode(a.xfyyfl,'02',1,0)";
			formain.fparam3.value = "decode(a.xfyyfl,'03',1,0)";
			formain.fparam4.value = "decode(a.xfyyfl,'04',1,0)";
			formain.fparam5.value = "decode(a.xfyyfl,'05',1,0)";
			formain.fparam6.value = "decode(a.xfyyfl,'06',1,0)";	
		}
		if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
			formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(b.glbm,'" + formain.glbm.value + "')";
			formain.flzd_m.value = "glbm";
			formain.flzd_dm.value = "glbm";				
		}else{
			formain.flzd.value = "frm_comm_pkg.gettjxzqh(b.xzqh," + formain.bmjb.value + ")";
			formain.flzd_m.value = "xzqh";
			formain.flzd_dm.value = "xzqh";						
		}
	}
	if(tableid=="035112"){
			formain.fparam1.value = "decode(a.xfjl,'01',1,0)";
			formain.fparam2.value = "decode(a.xfjl,'02',1,0)";
			formain.fparam3.value = "decode(a.xfjl,'03',1,0)";
			formain.fparam4.value = "decode(a.xfjl,'04',1,0)";
			formain.fparam5.value = "decode(a.xfjl,'05',1,0)";
			formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(a.glbm,'" + formain.glbm.value + "')";	
			formain.flzd_m.value = "glbm";
			formain.flzd_dm.value = "glbm";				
	}
	if(tableid=="035061"){
		if(!checkTjtj())return false;	
		if(formain.cdbh.value=="C506"){
			formain.fparam1.value = "decode(a.jllx,'1',1,'2',1,0)";
			formain.fparam2.value = "decode(acd_comm_pkg.getQxdm(a.jllx,a.sgfssj,a.xc),'1',1,0)";
			formain.fparam3.value = "decode(acd_comm_pkg.getQxdm(a.jllx,a.sgfssj,a.xc),'2',1,0)";
			formain.fparam4.value = "decode(acd_comm_pkg.getQxdm(a.jllx,a.sgfssj,a.xc),'3',1,0)";
		}else if(formain.cdbh.value=="C512"){
			formain.fparam1.value = "decode(a.sglx,'1',1,0)";
			formain.fparam2.value = "decode(a.sglx,'2',1,0)";
			formain.fparam3.value = "decode(a.sglx,'3',1,0)";
		}
		if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
			formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(a.glbm,'" + formain.glbm.value + "')";
			formain.flzd_m.value = "glbm";
			formain.flzd_dm.value = "glbm";				
		}else{
			formain.flzd.value = "frm_comm_pkg.gettjxzqh(a.xzqh," + formain.bmjb.value + ")";
			formain.flzd_m.value = "xzqh";
			formain.flzd_dm.value = "xzqh";						
		}		
	}
	if(tableid=="035141"){
		if(!checkTjtj())return false;		
		formain.fparam1.value = "swrs+swrsq+szrs";
		formain.fparam2.value = "ssrs";
		if(formain.tjlx.value==""){
			formain.tjclid.value = "030024";
		}else{
			formain.tjclid.value = "030025";		
		}
		if(formain.radio_GLBM!=undefined&&formain.radio_GLBM.checked){
			formain.flzd.value = "frm_comm_pkg.Gethbtjbm_acd(glbm,'" + formain.glbm.value + "')";
			formain.flzd_m.value = "glbm";
			formain.flzd_dm.value = "glbm";				
		}else{
			formain.flzd.value = "frm_comm_pkg.gettjxzqh(xzqh," + formain.bmjb.value + ")";
			formain.flzd_m.value = "xzqh";
			formain.flzd_dm.value = "xzqh";						
		}
	}	
	openwin("","acdsearch1",true);
	formain.target = "acdsearch1";
	formain.action="commonquery.frm?method=statCommonResult";
	formain.submit();
}
function other(obj,tableid){
	if(obj.value=="显示其他条件"){
		obj.value="隐藏其他条件";
		div_qttj.style.display = "block";
	}else{
		obj.value="显示其他条件";
		div_qttj.style.display = "none";	
	}
}
function initcond(tableid){
	//初始化条件处理
	var fieldstat=false;
	var fieldstat1 = 1;
	if(tableid=="032011"){
		if(formain.sgbj.value=="1"){
			fieldstat = true;
		}
		changeSgbhDjbhState(fieldstat);
	}else if(tableid=="033012"||tableid=="033102"||tableid=="033104"||tableid=="033105"){
		if(formain.tjbj.value=="1"){
			fieldstat = true;
		}
		fieldstat1 = formain.sjbj.value;
		changeXzqhGlbmState(fieldstat);
		changeTjsjfwState(fieldstat1);
		if(tableid=="033102"){
			formain.tjbb.value=formain.tjbb.options[1].value;
		}else if(tableid=="033104"){
			formain.tjbb_qx.value=formain.tjbb_qx.options[1].value;
		}else if(tableid=="033105"){
			formain.tjbb_sw.value=formain.tjbb_sw.options[1].value;
		}
	}else if(tableid=="035021"||tableid=="035061"||tableid=="035141"){
		if(formain.tjbj.value=="1"){
			fieldstat = true;
		}
		changeXzqhGlbmState(fieldstat);	
	}else if(tableid=="032021"||tableid=="032023"||tableid=="032101"||tableid=='032201'){
		if(formain.dsrbj.value=="2"){
			fieldstat = true;
		}
		changeDsrxxState(fieldstat);
		changeBaxjglbm();
	}
}
function doclickfield(tableid,fieldname){
	//特殊页面组件，点击处理
	var fieldstat=false;
	var fieldstat1 = 1;
	if(tableid=="032011"){
		if(fieldname=="radio_SGBH"){
			fieldstat = true;		
		}
		changeSgbhDjbhState(fieldstat);
	}else if(tableid=="033101"||tableid=="033106"||tableid=="033107"||tableid=="033105"||tableid=="033102"||tableid=="033104"||tableid=="033011"||tableid=="033012"||tableid=="035021"||tableid=="035061"||tableid=="035141"){
		if(fieldname=="radio_GLBM"||fieldname=="radio_XZQH"){
			if(fieldname=="radio_GLBM"){
				fieldstat = true;			
			}else{
				fieldstat = false;
			}
			changeXzqhGlbmState(fieldstat);
		}else{
			if(fieldname=="radio_TJNY"){
				fieldstat1 = 1;
			}else if(fieldname=="radio_TJNYD"){
				fieldstat1 = 2;
			}else if(fieldname=="radio_TJRQD"){
				fieldstat1 = 3;
			}
			changeTjsjfwState(fieldstat1);
		}
	}else if(tableid=="032021"||tableid=="032023"||tableid=="032101"||tableid=='032201'){
		if(formain.all[fieldname].checked){
			fieldstat = true;
		}
		changeDsrxxState(fieldstat);
	}else if(tableid=="033103"){	
		if("checkbox_BX,checkbox_SFCZ,checkbox_YZWXP".indexOf(fieldname)>=0){
			if(!document.all[fieldname].checked){
				var fn = fieldname.substr(9).toLowerCase();
				document.all[fn].options.selectedIndex = 0;
			}
		}else if("checkbox_JL,checkbox_LH".indexOf(fieldname)>=0){
			if(!document.all[fieldname].checked){
				var fn = fieldname.substr(9).toLowerCase();
				document.all[fn+"1"].value = "";
				document.all[fn+"2"].value = "";				
			}
		}else{
			changeSelectCodeState(fieldname);
		}
	}
}