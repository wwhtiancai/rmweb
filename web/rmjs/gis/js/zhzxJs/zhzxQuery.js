	function getTreeQuery(){ //组装查询		
		var checkBoxArrays = $("input[name='sbCheckBoxName']:checked");
		var checkCount = $("input[name='sbCheckBoxName']:checked").length;//被选中个数 
		if(checkCount==0){
			stopQueryTimer();
		}else{			
			var queryWhereStr = "";
			sbssDmzArray="";
			queryWhereArray="";
			for(var i=0; i<checkBoxArrays.length; i++){
				var checkSbBoxId = checkBoxArrays[i].id;
				var dmz = checkSbBoxId.substring(checkSbBoxId.lastIndexOf("_")+1,checkSbBoxId.length);
				var sbjc = getSbssjcByDmz(dmz);	
				var oddArray=[]; //装载已勾选的类型与对应的值JSON
				var preSbsslx = "";
				$("input[type='checkbox']:checked").each(function(){ //计算每一类条件勾选个数，把是多个的保存到数组中
					var checkedId = $(this).attr("id");
					if($(this).attr("id").lastIndexOf(sbjc)>=0){
						var colval = checkedId.substring(checkedId.lastIndexOf("_")+1,checkedId.length);
						var oddValue={ 
								lx:"",
								val:""
							}
						if(preSbsslx==""){						
							preSbsslx = $(this).attr("id").substring(0,$(this).attr("id").lastIndexOf("_"));
							oddValue.lx = preSbsslx;
							oddValue.val = "'"+colval+"'";
							oddArray.push(oddValue);
						}else{
							if($(this).attr("id").lastIndexOf(preSbsslx)>=0){		
								for(var h=0; h<oddArray.length; h++){
									if(oddArray[h].lx == preSbsslx){
										oddArray[h].val = oddArray[h].val+",'"+colval+"'";
									}
								}
						    }else{						    	
						    	preSbsslx=$(this).attr("id").substring(0,$(this).attr("id").lastIndexOf("_"));
						    	oddValue.lx = preSbsslx;
								oddValue.val ="'"+colval+"'";
								oddArray.push(oddValue);
						    }
						}	
					}
				});
				queryWhereStr = "";
				for(var h=0; h<oddArray.length; h++){				
					if(oddArray[h].lx == "kk_kklx"){//卡口5+1
						  queryWhereStr+=" and kklx in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_kkxz"){
						  queryWhereStr+=" and kkxz in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_wlwz"){
						  queryWhereStr+=" and jrwz in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_ljtj"){
						  queryWhereStr+=" and ljtj in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_yxzt"){
						  queryWhereStr+=" and kkzt in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "zfqz_sblx"){//组织违法取证条件1+1
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "spjk_txcc"){//组织视频监控条件2+1
						  queryWhereStr+=" and txcc in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "spjk_gzzt"){
						  queryWhereStr+=" and zt in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "zffwz_fwzlx"){//==xj××//交通安全执法服务站3---DEV_STATION
						  queryWhereStr+=" and fwzlx in ("+oddArray[h].val+")";//服务站类型
					  }else if(oddArray[h].lx == "zffwz_sfdlyf"){//-----------------------
						  queryWhereStr+=" and jydlyf in ("+oddArray[h].val+")";//是否独立营房
					  }else if(oddArray[h].lx == "zffwz_jcfx"){
						  queryWhereStr+=" and jcfx in ("+oddArray[h].val+")";//检查方向
					  }else if(oddArray[h].lx == "tcc_fwzlx"){//停车场3
						  queryWhereStr+=" and tcclx in ("+oddArray[h].val+")";//停车场类型
					  }else if(oddArray[h].lx == "tcc_jsly"){
						  queryWhereStr+=" and zt in ("+oddArray[h].val+")";//建设来源-----------------------------------not found！
					  }else if(oddArray[h].lx == "tcc_tcpws"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="pws<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="pws>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(pws between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "qxjc_yjlx"){//气象检测设备1+1
						  queryWhereStr+=" and yjlx in ("+oddArray[h].val+")";//预警类型
					  }else if(oddArray[h].lx == "lljc_sblx"){//流量检测设备2+1
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";//设备类型
					  }else if(oddArray[h].lx == "lljc_jcsjlx"){
						  //由于后台数据库记录为1,2,4,5,6 必须使用instr or方式判断
						  var condArray=oddArray[h].val.split(",");
						  var condStr="";
						  for(var c=0;c<condArray.length;c++){
							  if(i==0){
								  condStr=" instr(Jcsllx, '"+condArray[c]+"',1,1)>0 ";
							  }else{
								  condStr+=" or instr(Jcsllx, '"+condArray[c]+"',1,1)>0 ";
							  }
						  }
						  //queryWhereStr+=" and jcsllx in ("+oddArray[h].val+")";
						  queryWhereStr+=" ( "+condStr+" ) "; //检测数据类型  【数据库为jcsllx】
					  }else if(oddArray[h].lx == "kbxx_sblx"){//可变信息标志3
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";//设备类型
					  }else if(oddArray[h].lx == "kbxx_sbgg"){
						  queryWhereStr+=" and sbgg in ("+oddArray[h].val+")";//设备规格
					  }else if(oddArray[h].lx == "kbxx_fbfs"){
						  queryWhereStr+=" and fbfs in ("+oddArray[h].val+")";//发布方式
					  }else if(oddArray[h].lx == "kbbz_sblx"){//交通信号found控制设备2
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";//设备类型
					  }else if(oddArray[h].lx == "kbbz_sflw"){
						  queryWhereStr+=" and sflw in ("+oddArray[h].val+")";//是否联网
					  }else if(oddArray[h].lx == "jtbm_tcs"){ //拖车数
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="tcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="tcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(tcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "jtbm_qzcs"){ //清障车数
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="qzcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="qzcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(qzcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "jtbm_dcs"){ //吊车数
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="dcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="dcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(dcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "xfbm_jgjb"){//消防部门3
						  queryWhereStr+=" and jgjb in ("+oddArray[h].val+")";//机构级别
					  }else if(oddArray[h].lx == "xfbm_xfcs"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="xfcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="xfcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(xfcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "xfbm_xfrs"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="xfrs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="xfrs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(xfrs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "yy_yylx"){//医院3
						  queryWhereStr+=" and yylx in ("+oddArray[h].val+")";//医院类型
					  }else if(oddArray[h].lx == "yy_yyjb"){
						  queryWhereStr+=" and yydj in ("+oddArray[h].val+")";//医院级别
					  }else if(oddArray[h].lx == "yy_jjcs"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }							  
							  if(singleElem.lastIndexOf("|")==0){								  
								  titleStr+="jjcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="jjcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(jjcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "yy_jjcs"){//------------------------------
						  queryWhereStr+=" and jjcs in ("+oddArray[h].val+")";//急救车数
					  }else if(oddArray[h].lx == "xlc_zzjb"){//修理厂1
						  queryWhereStr+=" and zzjb in ("+oddArray[h].val+")";//资质级别
					  }else if(oddArray[h].lx == "jjd_bmjb"){//交警队2
						  queryWhereStr+=" and bmjb in ("+oddArray[h].val+")";//部门级别
					  }else if(oddArray[h].lx == "jjd_bmfl"){
						  queryWhereStr+=" and zt in ("+oddArray[h].val+")";//部门分类-------------------------not found
					  }
				  }	  
				if(sbjc=="kk"){	
					   if($("#kk_sbcsmc").attr("autoSuggestValue")!="" && $("#kk_sbcsmc").attr("autoSuggestValue")!=undefined && $("#kk_sbcsmc").attr("autoSuggestValue")!="null" && $("#kk_sbcsmc").attr("autoSuggestValue")!=null){
						   queryWhereStr+=" and sbgys='"+$("#kk_sbcsmc").attr("autoSuggestValue")+"'";	 
					   }                                   	  
				}else if(sbjc=="zfqz"){
					   if($("#zfqz_sbcsmc").attr("autoSuggestValue")!="" && $("#zfqz_sbcsmc").attr("autoSuggestValue")!=undefined && $("#zfqz_sbcsmc").attr("autoSuggestValue")!="null" && $("#zfqz_sbcsmc").attr("autoSuggestValue")!=null){
		                   queryWhereStr+=" and sbcs='"+$("#zfqz_sbcsmc").attr("autoSuggestValue")+"'";	   
					   }    
	            }else if(sbjc=="spjk"){
	                   if($("#spjk_sbcsmc").attr("autoSuggestValue")!="" && $("#spjk_sbcsmc").attr("autoSuggestValue")!=undefined && $("#spjk_sbcsmc").attr("autoSuggestValue")!="null" && $("#spjk_sbcsmc").attr("autoSuggestValue")!=null){
		                   queryWhereStr+=" and sbgys='"+$("#spjk_sbcsmc").attr("autoSuggestValue")+"'";	
	                   }
	            }else if(sbjc=="qxjc"){//气象检测设备1+1
	                    if($("#qxjc_sbcs").attr("autoSuggestValue")!="" && $("#qxjc_sbcs").attr("autoSuggestValue")!= undefined && $("#qxjc_sbcs").attr("autoSuggestValue")!="null" && $("#qxjc_sbcs").attr("autoSuggestValue")!=null){
	 	                   queryWhereStr+=" and sbgys='"+$("#qxjc_sbcs").attr("autoSuggestValue")+"'";	
	                    }
	            }else if(sbjc=="lljc"){//流量检测设备2+1
	                    if($("#lljc_sbcs").attr("autoSuggestValue")!="" && $("#lljc_sbcs").attr("autoSuggestValue")!=undefined && $("#lljc_sbcs").attr("autoSuggestValue")!="null" && $("#lljc_sbcs").attr("autoSuggestValue")!=null){
	 	                   queryWhereStr+=" and sbgys='"+$("#lljc_sbcs").attr("autoSuggestValue")+"'";	
	                    }
	            }				
				if(dmz.lastIndexOf("0")==0){
					dmz=dmz.substring(1,dmz.length);
				}
				/*
				//道路树选择节点id
				//var roadCheckedTreeId = getRoadSelectedNode(); 	
				var roadCheckedTreeId = getSelectedDistrictTreeIds('selectRoadTreeDiv');
				roadCheckedTreeId = roadCheckedTreeId.replace(";","");
				if(roadCheckedTreeId.length!=0){
					resultShowRec = "roadRec";
					queryWhereStr+=" and dldm in ("+roadCheckedTreeId+")";
				}
				*/
				//加入行政区划树条件
				var districtCheckedIds = getSelectedDistrictTreeIds('selectXzqhTreeDiv');
				var xzqhStr = districtCheckedIds.substring(0,districtCheckedIds.lastIndexOf(";"));
				var glbmStr = districtCheckedIds.substring(districtCheckedIds.lastIndexOf(";")+1,districtCheckedIds.length);

				if(xzqhStr.length!=0){
					resultShowRec = "xzqhRec";					
					queryWhereStr+=" and xzqh in ("+xzqhStr+")";
				}				
				if(glbmStr.length!=0){
					resultShowRec = "xzqhRec";
					var glbmArray=glbmStr.split(",");
					if(bmjb=="1"){
						var likeOrGlbm="";
						for(var i=0;i<glbmArray.length;i++){
							var glbmLike=" glbm like '"+(glbmArray[i]+"").substr(1,2)+"%25'";//注意外部传入的为%转移字符为%25
							if(i==0){
								likeOrGlbm=glbmLike;
							}else{
								likeOrGlbm+=" or "+glbmLike;
							}
						}
						queryWhereStr+=" and ( "+likeOrGlbm+" ) ";
					}else if(bmjb=="2"){	
						var likeOrGlbm="";
						for(var i=0;i<glbmArray.length;i++){
							var glbmLike=" glbm like '"+(glbmArray[i]+"").substr(1,4)+"%25'";
							if(i==0){
								likeOrGlbm=glbmLike;
							}else{
								likeOrGlbm+=" or "+glbmLike;
							}
						}
						queryWhereStr+=" and ( "+likeOrGlbm+" ) ";
					}else{
						queryWhereStr+=" and glbm in ("+glbmStr+")";
					}
				}
				if(sbssDmzArray==""){
					sbssDmzArray+=dmz;
					if(queryWhereStr==""){
						queryWhereArray+="and 1=1";
					}else{
						queryWhereArray+=queryWhereStr.LTrim();
					}				    
				}else{
					sbssDmzArray+=";"+dmz;
					if(queryWhereStr==""){
						queryWhereArray+=";"+"and 1=1";
					}else{
						queryWhereArray+=";"+queryWhereStr.LTrim();
					}				
				}
			}//end for
		}
	}
	function makeQuery(){ //组装查询	
		var checkBoxArrays = $("input[name='sbCheckBoxName']:checked");
		var checkCount = $("input[name='sbCheckBoxName']:checked").length;//被选中个数 
		if(checkCount==0){
			stopQueryTimer();
		}else{	
			var queryWhereStr = "";
			sbssDmzArray="";
			queryWhereArray="";
			for(var i=0; i<checkBoxArrays.length; i++){			
				var checkSbBoxId = checkBoxArrays[i].id;
				var dmz = checkSbBoxId.substring(checkSbBoxId.lastIndexOf("_")+1,checkSbBoxId.length);
				var sbjc = getSbssjcByDmz(dmz);		
				var oddArray=[]; //装载已勾选的类型与对应的值JSON
				var preSbsslx = "";
				$("input[type='checkbox']:checked").each(function(){ //计算每一类条件勾选个数，把是多个的保存到数组中
					var checkedId = $(this).attr("id");
					if($(this).attr("id").lastIndexOf(sbjc)>=0){
						var colval = checkedId.substring(checkedId.lastIndexOf("_")+1,checkedId.length);
						var oddValue={ 
								lx:"",
								val:""
							}
						if(preSbsslx==""){						
							preSbsslx = $(this).attr("id").substring(0,$(this).attr("id").lastIndexOf("_"));
							oddValue.lx = preSbsslx;
							oddValue.val = "'"+colval+"'";
							oddArray.push(oddValue);
						}else{
							if($(this).attr("id").lastIndexOf(preSbsslx)>=0){		
								for(var h=0; h<oddArray.length; h++){
									if(oddArray[h].lx == preSbsslx){
										oddArray[h].val = oddArray[h].val+",'"+colval+"'";
									}
								}
						    }else{						    	
						    	preSbsslx=$(this).attr("id").substring(0,$(this).attr("id").lastIndexOf("_"));
						    	oddValue.lx = preSbsslx;
								oddValue.val ="'"+colval+"'";
								oddArray.push(oddValue);
						    }
						}	
					}
				});
				queryWhereStr = "";
				for(var h=0; h<oddArray.length; h++){				
					if(oddArray[h].lx == "kk_kklx"){//卡口5+1
						  queryWhereStr+=" and kklx in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_kkxz"){
						  queryWhereStr+=" and kkxz in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_wlwz"){
						  queryWhereStr+=" and jrwz in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_ljtj"){
						  queryWhereStr+=" and ljtj in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "kk_yxzt"){
						  queryWhereStr+=" and kkzt in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "zfqz_sblx"){//组织违法取证条件1+1
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "spjk_txcc"){//组织视频监控条件2+1
						  queryWhereStr+=" and txcc in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "spjk_gzzt"){
						  queryWhereStr+=" and zt in ("+oddArray[h].val+")";
					  }else if(oddArray[h].lx == "zffwz_fwzlx"){//==xj××//交通安全执法服务站3---DEV_STATION
						  queryWhereStr+=" and fwzlx in ("+oddArray[h].val+")";//服务站类型
					  }else if(oddArray[h].lx == "zffwz_sfdlyf"){//-----------------------
						  queryWhereStr+=" and jydlyf in ("+oddArray[h].val+")";//是否独立营房
					  }else if(oddArray[h].lx == "zffwz_jcfx"){
						  queryWhereStr+=" and jcfx in ("+oddArray[h].val+")";//检查方向
					  }else if(oddArray[h].lx == "tcc_fwzlx"){//停车场3
						  queryWhereStr+=" and tcclx in ("+oddArray[h].val+")";//停车场类型
					  }else if(oddArray[h].lx == "tcc_jsly"){
						  queryWhereStr+=" and zt in ("+oddArray[h].val+")";//建设来源-----------------------------------not found！
					  }else if(oddArray[h].lx == "tcc_tcpws"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="pws<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="pws>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(pws between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "qxjc_yjlx"){//气象检测设备1+1
						  queryWhereStr+=" and yjlx in ("+oddArray[h].val+")";//预警类型
					  }else if(oddArray[h].lx == "lljc_sblx"){//流量检测设备2+1
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";//设备类型
					  }else if(oddArray[h].lx == "lljc_jcsjlx"){
						  //queryWhereStr+=" and jcsllx in ("+oddArray[h].val+")";//检测数据类型 
						  //由于后台数据库记录为1,2,4,5,6 必须使用instr or方式判断
						  var condArray=oddArray[h].val.split(",");
						  var condStr="";
						  for(var c=0;c<condArray.length;c++){
							  if(i==0){
								  condStr=" instr(Jcsllx, '"+condArray[c]+"',1,1)>0 ";
							  }else{
								  condStr+=" or instr(Jcsllx, '"+condArray[c]+"',1,1)>0 ";
							  }
						  }
						  //queryWhereStr+=" and jcsllx in ("+oddArray[h].val+")";
						  queryWhereStr+=" ( "+condStr+" ) "; //检测数据类型  【数据库为jcsllx】
						  //【数据库为jcsllx】
					  }else if(oddArray[h].lx == "kbxx_sblx"){//可变信息标志3
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";//设备类型
					  }else if(oddArray[h].lx == "kbxx_sbgg"){
						  queryWhereStr+=" and sbgg in ("+oddArray[h].val+")";//设备规格
					  }else if(oddArray[h].lx == "kbxx_fbfs"){
						  queryWhereStr+=" and fbfs in ("+oddArray[h].val+")";//发布方式
					  }else if(oddArray[h].lx == "kbbz_sblx"){//交通信号found控制设备2
						  queryWhereStr+=" and sblx in ("+oddArray[h].val+")";//设备类型
					  }else if(oddArray[h].lx == "kbbz_sflw"){
						  queryWhereStr+=" and sflw in ("+oddArray[h].val+")";//是否联网
					  }else if(oddArray[h].lx == "jtbm_tcs"){ //拖车数
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="tcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="tcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(tcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "jtbm_qzcs"){ //清障车数
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="qzcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="qzcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(qzcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "jtbm_dcs"){ //吊车数
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="dcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="dcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(dcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "xfbm_jgjb"){//消防部门3
						  queryWhereStr+=" and jgjb in ("+oddArray[h].val+")";//机构级别
					  }else if(oddArray[h].lx == "xfbm_xfcs"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="xfcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="xfcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(xfcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "xfbm_xfrs"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="xfrs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="xfrs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(xfrs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "yy_yylx"){//医院3
						  queryWhereStr+=" and yylx in ("+oddArray[h].val+")";//医院类型
					  }else if(oddArray[h].lx == "yy_yyjb"){
						  queryWhereStr+=" and yydj in ("+oddArray[h].val+")";//医院级别
					  }else if(oddArray[h].lx == "yy_jjcs"){
						  var tempArray = oddArray[h].val.split(",");
						  var titleStr="";
						  for(var m=0; m<tempArray.length; m++){
							  var singleElem = tempArray[m].replace("'","");
							  singleElem = singleElem.replace("'","");
							  if(m!=0){
								  titleStr+=" or ";
							  }
							 // alert("dd "+ singleElem);
							  if(singleElem.lastIndexOf("|")==0){
								  titleStr+="jjcs<"+singleElem.substring(1,singleElem.length);								  
							  }else if(singleElem.lastIndexOf("|")==singleElem.length-1){
								  titleStr+="jjcs>"+singleElem.substring(0,singleElem.length-1);
							  }else{
								  var tempA = singleElem.substring(0,singleElem.lastIndexOf("|"));
								  var tempB = singleElem.substring(singleElem.lastIndexOf("|")+1,singleElem.length);
								  titleStr+="(jjcs between "+tempA+" and "+tempB+")";
							  }
						  }
						  queryWhereStr+=" and ("+titleStr+")";
					  }else if(oddArray[h].lx == "yy_jjcs"){//------------------------------
						  queryWhereStr+=" and jjcs in ("+oddArray[h].val+")";//急救车数
					  }else if(oddArray[h].lx == "xlc_zzjb"){//修理厂1
						  queryWhereStr+=" and zzjb in ("+oddArray[h].val+")";//资质级别
					  }else if(oddArray[h].lx == "jjd_bmjb"){//交警队2
						  queryWhereStr+=" and bmjb in ("+oddArray[h].val+")";//部门级别
					  }else if(oddArray[h].lx == "jjd_bmfl"){
						  queryWhereStr+=" and zt in ("+oddArray[h].val+")";//部门分类-------------------------not found
					  }
				  }	 
				if(sbjc=="kk"){	
					   if($("#kk_sbcsmc").attr("autoSuggestValue")!="" && $("#kk_sbcsmc").attr("autoSuggestValue")!=undefined && $("#kk_sbcsmc").attr("autoSuggestValue")!="null" && $("#kk_sbcsmc").attr("autoSuggestValue")!=null){
						   queryWhereStr+=" and sbgys='"+$("#kk_sbcsmc").attr("autoSuggestValue")+"'";	 
					   }                                   	  
					}else if(sbjc=="zfqz"){
					   if($("#zfqz_sbcsmc").attr("autoSuggestValue")!="" && $("#zfqz_sbcsmc").attr("autoSuggestValue")!=undefined && $("#zfqz_sbcsmc").attr("autoSuggestValue")!="null" && $("#zfqz_sbcsmc").attr("autoSuggestValue")!=null){
		                   queryWhereStr+=" and sbcs='"+$("#zfqz_sbcsmc").attr("autoSuggestValue")+"'";	   
					   }    
	                }else if(sbjc=="spjk"){
	                   if($("#spjk_sbcsmc").attr("autoSuggestValue")!="" && $("#spjk_sbcsmc").attr("autoSuggestValue")!=undefined && $("#spjk_sbcsmc").attr("autoSuggestValue")!="null" && $("#spjk_sbcsmc").attr("autoSuggestValue")!=null){
		                   queryWhereStr+=" and sbgys='"+$("#spjk_sbcsmc").attr("autoSuggestValue")+"'";	
	                   }
	                }else if(sbjc=="qxjc"){//气象检测设备1+1
	                    if($("#qxjc_sbcs").attr("autoSuggestValue")!="" && $("#qxjc_sbcs").attr("autoSuggestValue")!= undefined && $("#qxjc_sbcs").attr("autoSuggestValue")!="null" && $("#qxjc_sbcs").attr("autoSuggestValue")!=null){
	 	                   queryWhereStr+=" and sbgys='"+$("#qxjc_sbcs").attr("autoSuggestValue")+"'";	
	                    }
	                }else if(sbjc=="lljc"){//流量检测设备2+1
	                    if($("#lljc_sbcs").attr("autoSuggestValue")!="" && $("#lljc_sbcs").attr("autoSuggestValue")!=undefined && $("#lljc_sbcs").attr("autoSuggestValue")!="null" && $("#lljc_sbcs").attr("autoSuggestValue")!=null){
	 	                   queryWhereStr+=" and sbgys='"+$("#lljc_sbcs").attr("autoSuggestValue")+"'";	
	                    }
	                }
				if(dmz.lastIndexOf("0")==0){
					dmz=dmz.substring(1,dmz.length);
				}
				
				//道路树选择节点id
				//var roadCheckedTreeId = getRoadSelectedNode(); 	
				var roadCheckedTreeId = getSelectedDistrictTreeIds('selectRoadTreeDiv');
				if(roadCheckedTreeId!=""){
					roadCheckedTreeId = roadCheckedTreeId.replace(";","");
					if(roadCheckedTreeId.length!=0){
						resultShowRec = "roadRec";
						queryWhereStr+=" and dldm in ("+roadCheckedTreeId+")";
					}					
				}
				//加入行政区划树条件
				var districtCheckedIds = getSelectedDistrictTreeIds('selectXzqhTreeDiv');
				var xzqhStr = districtCheckedIds.substring(0,districtCheckedIds.lastIndexOf(";"));
				var glbmStr = districtCheckedIds.substring(districtCheckedIds.lastIndexOf(";")+1,districtCheckedIds.length);
				if(xzqhStr.length!=0){
					resultShowRec = "xzqhRec";
					queryWhereStr+=" and xzqh in ("+xzqhStr+")";
				}
				if(glbmStr.length!=0){
					resultShowRec = "xzqhRec";
					//queryWhereStr+=" and glbm in ("+glbmStr+")";
					var glbmArray=glbmStr.split(",");
					var likeOrGlbm="";
					if(bmjb=="1"){
						for(var ab=0;ab<glbmArray.length;ab++){
							var glbmLike=" glbm like '"+(glbmArray[ab]+"").substr(1,2)+"%25'";//注意外部传入的为%转移字符为%25
							if(ab==0){
								likeOrGlbm=glbmLike;
							}else{
								likeOrGlbm+=" or "+glbmLike;
							}
						}
						queryWhereStr+=" and ( "+likeOrGlbm+" ) ";
					}else if(bmjb=="2"){						
						for(var ab=0;ab<glbmArray.length;ab++){
							var glbmLike=" glbm like '"+(glbmArray[ab]+"").substr(1,4)+"%25'";
							if(ab==0){
								likeOrGlbm=glbmLike;
							}else{
								likeOrGlbm+=" or "+glbmLike;
							}
						}
						queryWhereStr+=" and ( "+likeOrGlbm+" ) ";	
					}else{
						queryWhereStr+=" and glbm in ("+glbmStr+")";
					}
				}
				if(sbssDmzArray==""){
					sbssDmzArray+=dmz;
					if(queryWhereStr==""){
						queryWhereArray+="and 1=1";
					}else{
						queryWhereArray+=queryWhereStr.LTrim();
					}	
				}else{
					sbssDmzArray+=";"+dmz;
					if(queryWhereStr==""){
						queryWhereArray+=";"+"and 1=1";
					}else{
						queryWhereArray+=";"+queryWhereStr.LTrim();
					}				
				}
				
			}//end for			
			//进行事件校验，检验是否查询条件发生改变需要进行查询
			if(eventRecordArray.length>0){
				if(eventRecordArray[0]==sbssDmzArray && eventRecordArray[1]==queryWhereArray){
					return;					
				}else{
					eventRecordArray=[];
					eventRecordArray.push(sbssDmzArray);
					eventRecordArray.push(queryWhereArray);	
				}		
			}else{
				eventRecordArray.push(sbssDmzArray);
				eventRecordArray.push(queryWhereArray);	
			}			
			clearMapDraw();	//清除地图draw
			clearClusterDraw(); //清除聚合draw
			showLoad(); //显示等待条
			hideDialog(); //查询的时候隐藏所有的dialog对话框
			setTimeout(function(){
				queryData();
				hideDialog();
			},500);
	}
}
function crtBetweenCondition(selectVal){
	preSbsslx=selectVal.substring(0,selectVal.lastIndexOf("_"));
	
}