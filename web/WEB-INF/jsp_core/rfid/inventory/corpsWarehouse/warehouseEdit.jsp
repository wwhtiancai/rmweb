<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>总队入库维护</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">总队入库维护</div>
	<form action="" method="post" name="myform" id="myform">
	<c:if test="${bean==null}">
	    <input type="hidden" name="detailList" id="detailList">
	</c:if>
	<div id="block">
		<div id="blocktitle">总队入库信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<tr>
				<td class="head">入库单号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.rkdh}'/>
				    <input type="hidden" name="rkdh" id="rkdh">
				</c:if>
				<c:if test="${bean==null}">
					<!-- <input type="text" name="rkdh" id="rkdh"> -->
				</c:if>
				</td>
				<td class="head">所属部门</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.ssbm}'/>
				</c:if>
				<c:if test="${bean==null}">
					<select id="ssbm" name="ssbm" style="width: 80%;">
						<c:forEach var="current" items="${xjbmList}">
							<option value="${current.glbm}">
								<c:out value="${current.bmmc}" />
							</option>
						</c:forEach>
					</select>
				</c:if>
				</td>
			</tr>
			<tr>
                <td class="head">产品类别</td>
                <td class="body">
                    <select id="cplb" name="cplb" style="width: 80%;" onChange="changeCplb()">
                        <option value="">--请选择--</option>
                        <c:forEach var="productCategory" items="${productCategories}">
                            <option value="${productCategory.cplb}">
                                <c:out value="${productCategory.lbmc}" />
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td class="head">产品名称</td>
                <td class="body">
                    <select id="cpdm" name="cpdm" style="width:80%;">
                        <option value="">--请先选择产品类别--</option>
                    </select>
                </td>
            </tr>
			<tr>
				<td class="head">备注</td>
				<td class="body" colspan="3">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.bz}'/>
				</c:if>
				<c:if test="${bean==null}">
					<textarea name="bz" id="bz" rows="4" style="width:92%;"></textarea>
				</c:if>
				</td>
			</tr>
			
			<c:if test="${list==null}">
			<tr>
                <td class="head" style="padding:10px 0;">箱/盒号:</td>
                <td class="body">
                    <input type="text" name="bzhm" id="bzhm" style="width:75%"/>
                    <input type="text" style="display:none;"/>
                    <input type="button" name="addInbutton" id="addInbutton" value="添加" class="button_save"/>
                </td>
                <td class="body" colspan="2" style="text-align:right;">
                    <input type="button" name="addInbutton" id="addInbutton" onclick="save()" value="提交" class="button_save">
                    <input type="button" name="closebutton" onclick="javascript:window.close();" value="取消" class="button_close">
                </td>
            </tr>
			</c:if>
		</table>
		
		<div id="productList">
			<c:if test="${list==null}">
				<table border="0" cellspacing="1" cellpadding="0" class="list" id="list">
					<col width="10%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<tr class="head">
						<td>序号</td>
						<td>
							单位
						</td>
						<td>
							包装号码
						</td>
						<td>
							起始卡号
						</td>
						<td>
							终止卡号
						</td>
						<td>
							操作
						</td>
					</tr>
				</table>
			</c:if>
			
			<c:if test="${list!=null}">
				<table border="0" cellspacing="1" cellpadding="0" class="detail" style="margin-top:30px;">
                    <tr>
                        <td class="head" width="100%" style="height:30px;text-align:left;font-weight:bold;padding-left:10px;font-size:14px;">入库清单</td>
                    </tr>
                </table>
                <table border="0" cellspacing="1" cellpadding="0" class="list">
	            	<col width="10%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<tr class="head">
						<td>序号</td>
						<td>
							单位
						</td>
						<td>
							包装号码
						</td>
						<td>
							起始卡号
						</td>
						<td>
							终止卡号
						</td>
						<td>
							操作
						</td>
					</tr>
					<c:set var="rowcount" value="0" />
					<c:forEach items="${list}" var="current">
						<tr class="out dw_<c:out value="${current.dw}" />" onMouseOver="mouseOver(this)"
							onMouseOut="mouseOut(this)" style="cursor: pointer" bzhm="<c:out value="${current.bzhm}" />">
							<td><c:out value="${rowcount+1}" /></td>
							<td>
								<c:forEach var="unit" items="${units}">
									<c:if test="${unit.code == current.dw}">
										<c:out value="${unit.name}" />
									</c:if>
								</c:forEach>
							</td>
							<td>
								<c:out value="${current.bzhm}" />
							</td>
							<td>
								<c:out value="${current.qskh}" />
							</td>
							<td>
								<c:out value="${current.zzkh}" />
							</td>
							<td>
								<c:if test="${current.dw == 1}">
								<a href="#" class="zk" onclick="_expand(this)">展开</a>
								<a href="#" class="zd" onclick="_collapse(this)" style="display: none;">折叠</a>
								</c:if>
							</td>
						</tr>
						<c:set var="rowcount" value="${rowcount+1}" />
					</c:forEach>
				</table>
			</c:if>
		</div>
		
	</div>
	
</form>

</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" src="rmjs/json2.js" type="text/javascript"></script>
	<script language="JavaScript" type="text/javascript">
	var detailList = [];
	var units = "<c:out value='${unitsString}'/>";
	var dwList = JSON.parse(units.replace(new RegExp(/(&#034;)/g),'"'));
	//var statusString = "<c:out value='${statusString}'/>";
	//var statusList = JSON.parse(statusString.replace(new RegExp(/(&#034;)/g),'"'));
	
	var sortInsert = function(){
		$(".xh").each(function(i,me){
			me.innerHTML = i + 1;
		});
	};
	
	var _expand = function(t){
		var bzxh = $.trim(t.parentNode.parentNode.getAttribute("bzhm"));
		
		var successFn = function(arr){
			//var arr = [{bzhh:1000,qskh:1,zzkh:20,zt:1},{bzhh:1001,qskh:21,zzkh:40,zt:1}];
			arr.sort(function(a,b){return b.bzhh-a.bzhh});
			for(var i in arr){
				var obj = arr[i]
				var addHtml = "<tr class=\"out\" bzxh=\"bzxh_" + bzxh + "\" onMouseOver=\"mouseOver(this)\" onMouseOut=\"mouseOut(this)\" style=\"cursor: pointer\" bzhm=\""+obj.bzhh+"\">"
				+"<td></td><td>&nbsp;盒</td><td>&nbsp;"+obj.bzhh+"</td><td>&nbsp;"+obj.qskh+"</td><td>&nbsp;"+obj.zzkh+"</td><td></td></tr>";
				$(t.parentNode.parentNode).after(addHtml);
			}
			$(t.parentNode).children(".zk").hide();
			$(t.parentNode).children(".zd").show();
		}
		
		$.ajax({ url: "/rmweb/inventory.frm?method=query-by-bzxh", 
			async:false,
			data:{ bzxh:bzxh}, 
			success: function(){
				if(arguments[1] == "success"){
	      			var content = arguments[0];
	      			var resultJson = JSON.parse(arguments[0]);
	      			if(resultJson["resultId"] == "00"){
		      			var inventoryList = resultJson.inventoryList;
		      			successFn(inventoryList);
	      			}else{
	      				displayDialog(3,resultJson["resultMsg"],"");
	      			}
	      		}
			}
		});
		
	};
	var _collapse = function(t){
		var bzxh = $.trim(t.parentNode.parentNode.getAttribute("bzhm"));
		$("[bzxh=bzxh_"+bzxh+"]").each(function(){
			$(this).remove();
		});
		
		$(t.parentNode).children(".zd").hide();
		$(t.parentNode).children(".zk").show();
	};
	
	var _deleteRaw = function(row){
		var bzhm = row.parentNode.parentNode.getAttribute("bzhm");
		var dw = bzhm.substring(0,1);
		
		for(var i in detailList){
			var rawValue = detailList[i];
			if(rawValue.dw == dw && rawValue.bzhm == bzhm){
				detailList.splice(i,1);
			}
		}
		$(row.parentNode.parentNode).remove();
		if(dw == 1){
			$("[bzxh=bzxh_"+bzhm+"]").each(function(){
				$(this).remove();
			})
		}
		sortInsert();
	};
	
	var checkBZHM = function(bzhm){
		var dm = bzhm.substring(0,1);
		return (bzhm.length == 13 && (dm == 1 || dm == 2 ));
	};
	
	var checkNotInsert = function(hm,qs,zz){
		var flag = true;
		var dw = hm.substring(0,1);
		$(".added").each(function(){
			console.log(arguments)
			var qskh = $(this).children(".qskh").html();
			var zzkh = $(this).children(".zzkh").html();
			if(dw == 1){
				if(qs <= qskh && zz >= zzkh)flag =  false;
			}else{
				if(qs >= qskh && zz <= zzkh)flag =  false;
			}
		});
		
		return flag;
	};
	
	$(document).ready(function(){
	    $("#addbutton").attr("disabled",true);
	    
	    <c:if test="${bean==null}">
	    </c:if>
	    
	<c:if test="${bean!=null}">
		$("#addbutton").attr("disabled",false);
	    $("#savebutton").attr("disabled",true);
	
		var rkdh = "<c:out value='${bean.rkdh}'/>";
	    $("#rkdh").val("<c:out value='${bean.rkdh}'/>");
	    $("#ssbm").val("<c:out value='${bean.ssbm}'/>");
	    $("#cplb").val("<c:out value='${bean.cplb}'/>");
	    changeCplb();
	    $("#cpdm").val("<c:out value='${bean.cpdm}'/>");
	    $("#bz").val("<c:out value='${bean.bz}'/>");
		
	    $("#cplb").attr("readOnly","readOnly");
	    $("#cpdm").attr("readOnly","readOnly");
	
	    $("#bzhm").focus();
	    
		//$("#delbutton").attr("disabled",false);
		
		<c:if test="${bmjb!=null}">
		</c:if>
	</c:if>
	
		var _addRaw = function(){
			var rkdw = "";
			var bzhm = $("#bzhm").val().trim();
			if(checkBZHM(bzhm)){
				rkdw = bzhm.substring(0,1);
					
				if(rkdw == 1){//箱为单位
					$.ajax({ url: "/rmweb/inventory.frm?method=check-by-bzxh", 
						async:false,
						data:{ bzxh:bzhm,type:"corps-warehouse"}, 
						success: function(){
							if(arguments[1] == "success"){
				      			var content = arguments[0];
				      			var resultJson = JSON.parse(content);
				      			if(resultJson["resultId"] == "00"){
									var bean = resultJson["bean"];
				      				var _insertData = function(){
					      				var detailObj = "<tr class=\"out added dw_1\" onMouseOver=\"mouseOver(this)\" onMouseOut=\"mouseOut(this)\" style=\"cursor: pointer\" bzhm=\""+bzhm+"\">"
					      					+"<td class=\"xh\"></td>"
					      					+"<td>"+ dwList[rkdw]+"</td>"
					      					+"<td>"+ bzhm+"</td>"
					      					+"<td class=\"qskh\">"+bean.qskh+"</td>"
					      					+"<td class=\"zzkh\">"+bean.zzkh+"</td>"
					      					+"<td><a href=\"#\" onclick=\"_deleteRaw(this)\">删除</a>";
					      				
					      				detailObj += " <a href=\"javascript:void(0)\" class=\"zk\" onclick=\"_expand(this)\">展开</a>";
										detailObj += " <a href=\"javascript:void(0)\" class=\"zd\" onclick=\"_collapse(this)\" style=\"display: none;\">折叠</a>";
										detailObj += "</td></tr>";

										detailList.push({dw:rkdw,bzhm:bzhm});
										$("#detailList").val(JSON.stringify(detailList));
										$("#list").append(detailObj);
							      		$("#bzhm").val("");
							      		sortInsert();
				      				}
				      				
				      				if(!checkNotInsert(bzhm,bean.qskh,bean.zzkh)){
				      					displayDialog(3,"您录入的包装盒号已经录入！","");
				      				}else if($("#detailList").val() == ""){//若录入数据为空
				      					$("#cplb").val(resultJson.cplb);
				      				    changeCplb();
				      				  	$("#cpdm").val(resultJson.cpdm);
				      				  	_insertData();
				      				}else if(resultJson.cpdm != $("#cpdm").val() || resultJson.cplb != $("#cplb").val()){
				      					displayDialog(3,"您输入的该包装号码与本批次的产品不符！","");
				      				}else{
				      					_insertData();
				      				}
				      				
				      			}else{
				      				displayDialog(3,resultJson["resultMsg"],"");
				      			}
				      		}
						}
					});
				}else{//盒为单位
					$.ajax({ url: "/rmweb/inventory.frm?method=check-query-by-bzhh", 
						async:false,
						data:{ bzhh:bzhm,checkType:"corps-warehouse"}, 
						success: function(){
							if(arguments[1] == "success"){
				      			var content = arguments[0];
				      			var resultJson = JSON.parse(arguments[0]);
				      			if(resultJson["resultId"] == "00"){
				      				var inventoryList = resultJson.inventoryList;
					      			var inventory = inventoryList[0];
					      			var cplb = inventory.cplb;
					      			var cpdm = inventory.cpdm;
					      			
					      			var _insertData = function(){
					      				var detailObj = "<tr class=\"out added\" onMouseOver=\"mouseOver(this)\" onMouseOut=\"mouseOut(this)\" style=\"cursor: pointer\" bzhm=\""
						      				+bzhm+"\">"
						      				+"<td class=\"xh\"></td>"
						      				+"<td>"+ dwList[rkdw]+"</td>"
											+"<td>"+ bzhm+"</td>"
											+"<td class=\"qskh\">"+ inventory.qskh +"</td>"
											+"<td class=\"zzkh\">"+ inventory.zzkh +"</td>"
											+"<td><a href=\"#\" onclick=\"_deleteRaw(this)\">删除</a>";
					      				
										detailList.push({dw:rkdw,bzhm:bzhm});
										$("#detailList").val(JSON.stringify(detailList));
										$("#list").append(detailObj);
							      		$("#bzhm").val("");
							      		sortInsert();
					      			}
					      			
					      			if(!checkNotInsert(bzhm,inventory.qskh,inventory.zzkh)){
				      					displayDialog(3,"您录入的包装盒号已经录入！","");
				      				}else if($("#detailList").val() == ""){//若录入数据为空
				      					$("#cplb").val(cplb);
				      				    changeCplb();
				      				  	$("#cpdm").val(cpdm);
				      				  	_insertData();
				      				}else if(cpdm != $("#cpdm").val() || cplb != $("#cplb").val()){
				      					displayDialog(3,"您输入的该包装号码与本批次的产品不符！","");
				      				}else{
				      					_insertData();
				      				}
					      			
				      			}else{
				      				displayDialog(3,resultJson["resultMsg"],"");
				      			}
			      			}
						}
					});
				}
					
			}else{
  				displayDialog(1,"您输入包装号码不符合规范","");
			}
		};
		

		$('#bzhm').keydown(function(event){
			setTimeout(function(){
				var key = event.keyCode;
				if (key == 13) {
					_addRaw();
			  	}
			});
		});
		$("#addInbutton").bind("click",function(){
			_addRaw();
		});
	});
	
	
	function save(){
	    if(!checkNull($("#cplb"),"产品类别")) return false;
	    if(!checkNull($("#cpdm"),"产品名称")) return false;
		$("#myform").attr("action","<c:url value='/corps-warehouse.frm?method=save-warehouse'/>");
		closes();
		$("#myform").ajaxSubmit({
			dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
		});
	}
	function returns(data) { 
		if(arguments[1] == "success"){
  			var resultJson = arguments[0];
  			if(resultJson["resultId"] == "00"){
  				window.opener.query_cmd();
  				displayDialog(2,"保存成功！","");
  				window.close();
  			}else{
  				displayDialog(3,resultJson["resultMsg"],"");
  				opens();
  			}
  		}
	}
	
	function del(){
		if(confirm("是否确定删除该入库单？")){
			$("#myform").attr("action","<c:url value='/corps-warehouse.frm?method=del-warehouse'/>");
			closes();
			$("#myform").ajaxSubmit({
				dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
			});
		}
	}
	
	function returndeletes(data) { 
		if(arguments[1] == "success"){
  			var resultJson = arguments[0];
  			if(resultJson["resultId"] == "00"){
  				window.opener.query_cmd();
  				displayDialog(2,"删除入库单成功！","");
  				window.close();
  			}else{
  				displayDialog(3,resultJson["resultMsg"],"");
  				opens();
  			}
  		}
	}
	
	function query_cmd(){
		window.location.reload(); 
	}
	
	function changeCplb(){
		var category = $("#cplb").val();
		if(category){
			$.ajax({ url: "/rmweb/product.frm?method=fetch-by-category", 
				async:false,
				data:{category:category}, success: function(){
		      		if(arguments[1] == "success"){
		      			var content = arguments[0];
		      			var cpdms = JSON.parse(arguments[0]);
		      			var options = $("#cpdm")[0].options;
		      			var length = options.length;
		      			if(length > 1){
		      				for(var i = 1; i < length; i++){
		      					options.remove(i);
		      				}
		      			}
		      			for(var j = 0; j < cpdms.length; j++){
		  					options.add(new Option(cpdms[j].cpmc,cpdms[j].cpdm));
		  				}
		      		}
					
		    }});
		}
	}
	</script>
</body>
</html>
