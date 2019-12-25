//======号牌种类常量=====
var hpzlList = [{
	"value" : "no",
	"text" : "---请选择---"
},{
	"value" : "01",
	"text" : "01-大型汽车"
}, {
	"value" : "02",
	"text" : "02-小型汽车"
}, {
	"value" : "03",
	"text" : "03-使馆汽车"
}, {
	"value" : "04",
	"text" : "04-领馆汽车"
}, {
	"value" : "05",
	"text" : "05-境外汽车"
}, {
	"value" : "06",
	"text" : "06-外籍汽车"
}, {
	"value" : "07",
	"text" : "07-普通摩托车"
}, {
	"value" : "08",
	"text" : "08-轻便摩托车"
}, {
	"value" : "13",
	"text" : "13-低速车"
}, {
	"value" : "14",
	"text" : "14-拖拉机"
}, {
	"value" : "15",
	"text" : "15-挂车"
}, {
	"value" : "16",
	"text" : "16-教练汽车"
}, {
	"value" : "23",
	"text" : "23-警用汽车"
}, {
	"value" : "24",
	"text" : "24-警用摩托"
}, {
	"value" : "25",
	"text" : "25-原农机号牌"
}, {
	"value" : "26",
	"text" : "26-香港入出境车"
}, {
	"value" : "27",
	"text" : "27-澳门入出境车"
}, {
	"value" : "31",
	"text" : "31-武警号牌"
}, {
	"value" : "32",
	"text" : "32-军队号牌"
}, {
	"value" : "41",
	"text" : "41-无号牌"
}, {
	"value" : "42",
	"text" : "42-假号牌"
}, {
	"value" : "43",
	"text" : "43-挪用号牌"
}, {
	"value" : "44",
	"text" : "44-无法识别"
}, {
	"value" : "99",
	"text" : "99-其他号牌"
} ];

//======布控类型常量========
var bklxList = [ {
	"value" : "01",
	"text" : "01-事故逃逸车辆"
}, {
	"value" : "02",
	"text" : "02-套牌车辆"
}, {
	"value" : "03",
	"text" : "03-假牌车辆"
}, {
	"value" : "04",
	"text" : "04-逾期未年检车辆"
}, {
	"value" : "05",
	"text" : "05-逾期未报废车辆"
}, {
	"value" : "06",
	"text" : "06-违法未处理车辆"
}, {
	"value" : "08",
	"text" : "08-套牌嫌疑车辆"
}, {
	"value" : "21",
	"text" : "21-刑事案件"
}, {
	"value" : "22",
	"text" : "22-重大治安案件"
}, {
	"value" : "23",
	"text" : "23-违法犯罪嫌疑交通工具"
}, {
	"value" : "24",
	"text" : "24-被盗抢机动车"
}, {
	"value" : "25",
	"text" : "25-治安常态管控"
} ];

/**
 * 把布控类型填充到页面之中
 * @param tdId
 * @param bklxList
 */
function fillBKLXToPage(tdId,bklxList){
	$("#"+tdId).divFilter({
		 title:"布控类型选择",
		 winWidth:360,
		 data:bklxList,
		 itemWidth:165,
		 colsInRow:2
	 });
}

/**
 * 把号牌种类填充到页面之中
 * @param inputId
 * @param hpzlList
 */
function fillHPZLToPage(inputId,hpzlList){
	$('#'+inputId).combobox({
		data:hpzlList,
		valueField : 'value',
		textField : 'text'
	});
	$('#'+inputId).combobox('setValue', 'no');
}

/**
 * 把签收结果填充到页面之中
 * @param inputId
 * @param qsjgList
 */
function fillQSJGToPage(inputId,qsjgList){
	$('#'+inputId).combobox({
		data:qsjgList,
		valueField : 'value',
		textField : 'text'
	});
	$('#'+inputId).combobox('setValue', 'no');
}
//======签收结果=====
var qsjgList = [{
	"value" : "no",
	"text" : "---请选择---"
}, {
	"value" : "0",
	"text" : "0-未确认"
}, {
	"value" : "1",
	"text" : "1-确认有效"
}, {
	"value" : "2",
	"text" : "2-确认无效"
}, {
	"value" : "3",
	"text" : "3-确认为已接报"
}, {
	"value" : "9",
	"text" : "9-无需签收"
}];

/**
 * 把查询原因填充到页面之中
 * @param inputId
 * @param cxyyList
 */
function fillCXYYToPage(inputId,cxyyList){
	$('#'+inputId).combobox({
		data:cxyyList,
		valueField : 'value',
		textField : 'text'
	});
	$('#'+inputId).combobox('setValue', 'no');
}
//======查询原因=====
var cxyyList = [{
	"value" : "no",
	"text" : "---请选择---"
}, {
	"value" : "1",
	"text" : "1-套牌假牌车辆轨迹分析"
}, {
	"value" : "2",
	"text" : "2-事故逃逸车辆轨迹分析"
}, {
	"value" : "3",
	"text" : "3-被盗抢车辆轨迹分析"
},{
	"value" : "4",
	"text" : "4-其他涉牌违法车辆轨迹分析"
}, {
	"value" : "5",
	"text" : "5-刑事治安案件车辆轨迹查询"
},{
	"value" : "9",
	"text" : "9-其他查询"
}];