//======�������ೣ��=====
var hpzlList = [{
	"value" : "no",
	"text" : "---��ѡ��---"
},{
	"value" : "01",
	"text" : "01-��������"
}, {
	"value" : "02",
	"text" : "02-С������"
}, {
	"value" : "03",
	"text" : "03-ʹ������"
}, {
	"value" : "04",
	"text" : "04-�������"
}, {
	"value" : "05",
	"text" : "05-��������"
}, {
	"value" : "06",
	"text" : "06-�⼮����"
}, {
	"value" : "07",
	"text" : "07-��ͨĦ�г�"
}, {
	"value" : "08",
	"text" : "08-���Ħ�г�"
}, {
	"value" : "13",
	"text" : "13-���ٳ�"
}, {
	"value" : "14",
	"text" : "14-������"
}, {
	"value" : "15",
	"text" : "15-�ҳ�"
}, {
	"value" : "16",
	"text" : "16-��������"
}, {
	"value" : "23",
	"text" : "23-��������"
}, {
	"value" : "24",
	"text" : "24-����Ħ��"
}, {
	"value" : "25",
	"text" : "25-ԭũ������"
}, {
	"value" : "26",
	"text" : "26-����������"
}, {
	"value" : "27",
	"text" : "27-�����������"
}, {
	"value" : "31",
	"text" : "31-�侯����"
}, {
	"value" : "32",
	"text" : "32-���Ӻ���"
}, {
	"value" : "41",
	"text" : "41-�޺���"
}, {
	"value" : "42",
	"text" : "42-�ٺ���"
}, {
	"value" : "43",
	"text" : "43-Ų�ú���"
}, {
	"value" : "44",
	"text" : "44-�޷�ʶ��"
}, {
	"value" : "99",
	"text" : "99-��������"
} ];

//======�������ͳ���========
var bklxList = [ {
	"value" : "01",
	"text" : "01-�¹����ݳ���"
}, {
	"value" : "02",
	"text" : "02-���Ƴ���"
}, {
	"value" : "03",
	"text" : "03-���Ƴ���"
}, {
	"value" : "04",
	"text" : "04-����δ��쳵��"
}, {
	"value" : "05",
	"text" : "05-����δ���ϳ���"
}, {
	"value" : "06",
	"text" : "06-Υ��δ������"
}, {
	"value" : "08",
	"text" : "08-�������ɳ���"
}, {
	"value" : "21",
	"text" : "21-���°���"
}, {
	"value" : "22",
	"text" : "22-�ش��ΰ�����"
}, {
	"value" : "23",
	"text" : "23-Υ���������ɽ�ͨ����"
}, {
	"value" : "24",
	"text" : "24-������������"
}, {
	"value" : "25",
	"text" : "25-�ΰ���̬�ܿ�"
} ];

/**
 * �Ѳ���������䵽ҳ��֮��
 * @param tdId
 * @param bklxList
 */
function fillBKLXToPage(tdId,bklxList){
	$("#"+tdId).divFilter({
		 title:"��������ѡ��",
		 winWidth:360,
		 data:bklxList,
		 itemWidth:165,
		 colsInRow:2
	 });
}

/**
 * �Ѻ���������䵽ҳ��֮��
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
 * ��ǩ�ս����䵽ҳ��֮��
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
//======ǩ�ս��=====
var qsjgList = [{
	"value" : "no",
	"text" : "---��ѡ��---"
}, {
	"value" : "0",
	"text" : "0-δȷ��"
}, {
	"value" : "1",
	"text" : "1-ȷ����Ч"
}, {
	"value" : "2",
	"text" : "2-ȷ����Ч"
}, {
	"value" : "3",
	"text" : "3-ȷ��Ϊ�ѽӱ�"
}, {
	"value" : "9",
	"text" : "9-����ǩ��"
}];

/**
 * �Ѳ�ѯԭ����䵽ҳ��֮��
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
//======��ѯԭ��=====
var cxyyList = [{
	"value" : "no",
	"text" : "---��ѡ��---"
}, {
	"value" : "1",
	"text" : "1-���Ƽ��Ƴ����켣����"
}, {
	"value" : "2",
	"text" : "2-�¹����ݳ����켣����"
}, {
	"value" : "3",
	"text" : "3-�����������켣����"
},{
	"value" : "4",
	"text" : "4-��������Υ�������켣����"
}, {
	"value" : "5",
	"text" : "5-�����ΰ����������켣��ѯ"
},{
	"value" : "9",
	"text" : "9-������ѯ"
}];