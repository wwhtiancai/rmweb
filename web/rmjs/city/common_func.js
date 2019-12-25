//对数据求和， modifyLine(lineData)对每一行数据进行处理， modifyResult(lineData)对求和结果进行处理
//startLineNum开始统计的行，如有3行表头，startLineNum=4, 
//统计结果插入在第insertPosition行之前，如有三行表头，要插在第四行之前，那么insertPosition=4，
//如果insertPosition='end'加在末尾 
function doCalculation(tableId, startLineNum, modifyLine, modifyResult, precision, insertPosition){
	var pc=parseInt(precision);
	if(isNaN(pc))pc=1;
	var sum=[];
	var columnNum=$("#"+tableId+" tr:last-child td").length;
	for(var i=0;i<columnNum;i++){
		sum[i]=0;
	}
	$("#"+tableId+" tr").each(function(index,element){
		if(index >= startLineNum-1){
			var lineData=getLineValueArray2($(this));
			if(typeof(modifyLine)=='function')
				modifyLine(lineData);
			for(var j=1; j<columnNum+1; j++){
				sum[j]=sum[j]+lineData[j];
			}
		}
	});
	var sumLine=$('<tr class="total"><td>合计</td></tr>');
	var r_total=$("#"+tableId+" tr").length;
	var n_row=r_total-startLineNum+1;
	if(typeof(modifyResult)=='function')
		modifyResult(sum, n_row);
	for(var i=1; i<columnNum; i++){
		sum[i]=toPrecision(sum[i],pc);
		sumLine.append('<td class="total">'+sum[i]+'</td>');
	}
	if(insertPosition!=undefined && insertPosition!=null){
		if(insertPosition=='end')
			$("#"+tableId+" tr:last-child").after(sumLine);
		else {
			var n=parseFloat(insertPosition);
			var l=$("#"+tableId+" tr:nth-child("+n+")")[0];
			if(!isNaN(n) && l!=undefined && l!=null)
				l.after(sumLine);
			else
				$("#"+tableId+" tr:nth-child("+(startLineNum-1)+")").after(sumLine);
		}
	}else{
		$("#"+tableId+" tr:nth-child("+(startLineNum-1)+")").after(sumLine);
	}
}

//对idxArray的数据进行计算，结果放在后面一个td中， modifyLine(lineData)对每一行数据进行计算返回这一行中对应idxArray后面空单元格的数据Array
//idxArray单元格的index，从0开始计算
function addCalculatedCell(tableId, startLineNum, modifyLine, idxArray){
	var table=$("#"+tableId);
	table.find("tr").each(function(index,element){
		if(index >= startLineNum-1){
			var lineData=getLineValueArray2($(this));
			var newCells=modifyLine(lineData);
			fillCell(idxArray, newCells, $(this));
		}
	});
}

//lineData行数据，position为需要求和计算百分比的列的index，从0开始计算，返回对应position顺序的百分比的Array
function calculatePercent(lineData, position){
	var ret=[], sum=0;
	var n=position.length;
	for(var i=0; i<n; i++){
		ret[i]=parseFloat(lineData[parseFloat(position[i])]);
		sum=sum+ret[i];
	}
	for(var i=0; i<n; i++){
		if(sum!=0)
			ret[i]=toPrecision(ret[i]/sum*100,1)+'%';
		else
			ret[i]='-';
	}
	return ret;
}

//从第lineNum行的position获取数据，position从0开始计算
function getPiChartXmlHelper(caption, tableId, lineNum, position, namelist){
	var line=$("#"+tableId+" tr:nth-child("+lineNum+")");
	var valuelist=[];
	for(var i=0; i<position.length; i++){
		valuelist[i]=line.find("td:nth-child("+(position[i]+1)+")").text();
		if(isNaN(valuelist[i]))valuelist[i]=0;
	}
	return getPieChartXml(caption, null, namelist, valuelist);
}

//拼装pieChart的xml
function getPieChartXml(caption, list, namelist, valuelist){
	if(list==null || list.length==0){
		list=[];
		for(var i=0; i<namelist.length; i++){
			var o={};
			o.name=namelist[i];
			o.value=valuelist[i];
			list[i]=o;
		}
	}
	var ret='';
	var colors=["AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E",
	            "D64646","8E468E","588526","B3AA00","008ED6",
	            "9D080D","A186BE","1D8BD1","F1683C","2AD62A",
	            "DBDC25","A88415","769320","91ABBF","BBC0B5"];
	ret=ret+"<graph caption='"+caption+"' showNames='1' decimalPrecision='0' formatNumberScale='0' baseFont='宋体' baseFontSize='12'>";
	for(var i=0; i<list.length; i++){
		ret=ret+"<set name='"+list[i].name+"' value='"+list[i].value+"' isSliced='1' color='"+colors[i%20]+"'/>";
	}
	ret=ret+"</graph>";
	return ret;
}

//排序--地级市--县级市 从第startlineNum开始
function sortTableDjsXjs(tableId, startLineNum){
	var line=$("#"+tableId+" tr:nth-child("+startLineNum+")");
	var xjsTop;
	
	while(line[0]){
		var xzqhls2=getXzqhFromLineObj(line).substring(4,6);
		if(xzqhls2!='00'){
			var ftd=line.find("a");
			ftd.text(ftd.text()+'(*)');
			if(xjsTop==undefined){
				xjsTop=line;
			}
			line=line.next();
		}else if(xzqhls2=='00' && xjsTop!=undefined){
			var nextline=line.next();
			xjsTop.before(line);
			line=nextline;
		}else{
			line=line.next();
		}
	}
	
}

//从<tr><td><a onclick="showdetaio('xzqh')"></td></tr>中获取行政区划
function getXzqhFromLineObj(lineObj){
	var link=String(lineObj.find("a").attr('onclick'));
	var xzqh=link.substring(link.indexOf("'")+1);
	xzqh=xzqh.substring(0,xzqh.indexOf("'"));
	return xzqh;
}

function fillCell(idxArray, newCells, lineObj){
	for(var i=0; i<idxArray.length; i++){
		lineObj.find("td:nth-child("+(parseInt(idxArray[i])+1)+")").next().text(newCells[i]);
	}
}

function getLineValueArray2(lineObj){
	var ret=[];
	lineObj.find("td").each(function(index, element){
		var text=$(this).text();
		ret[index] = parseFloat(text);
		if(isNaN(ret[index]))
			ret[index]=0;//text
	});
	return ret;
}

function toPrecision(value, n){
	var num=parseFloat(value);
	if(isNaN(num))
		return value;
	var s=num.toString();
	var p=s.indexOf(".");
	if(p==-1){
		p=s.length-1;
	}
	s=roundTo(num,n).toString();
	for(var i=0; i<n-(s.length-1-p); i++){
		if(s.indexOf(".")==-1)s=s+".";
		s=s+"0";
	}
	return s;
}

function roundTo(x, n){
	var m=Math.pow(10,n);
	var ret=Math.round(x*m)/m
	return ret;
}


//-------------deprecated functions-------------
function getCellValue(tableId, rowNum, columnNum){
	var text=$("#"+tableId+" tr:nth-child("+rowNum+") td:nth-child("+columnNum+")").text();
	var ret=parseFloat(text);
	if(isNaN(ret))return 0;
	return ret;
}

function getLineValueArray(tableId, rowNum){
	var ret=[];
	var n=$("#"+tableId+" tr:nth-child("+rowNum+") td").length;
	for(var i=0; i<n; i++)
		ret[i]=getCellValue(tableId, rowNum, i+1);
	return ret;
}