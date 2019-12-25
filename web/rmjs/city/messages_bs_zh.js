/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, ���� (Zh��ngw��n), ����, �h�Z)
 */
jQuery.extend(jQuery.validator.messages, {
        required: "�����ֶ�",
		remote: "���������ֶ�",
		email: "��������ȷ��ʽ�ĵ����ʼ�",
		url: "��������ַ",
		date: "����������",
		dateISO: "���������� (ISO).",
		number: "����������",
		digits: "ֻ����������",
		creditcard: "���������ÿ���",
		equalTo: "���ٴ�������ͬ��ֵ",
		accept: "������ӵ�кϷ���׺�����ַ���",
		maxlength: jQuery.validator.format("������һ����������� {0} ���ַ���"),
		minlength: jQuery.validator.format("������һ������������ {0} ���ַ���"),
		rangelength: jQuery.validator.format("������Χ"),
		range: jQuery.validator.format("������Χ"),
		max: jQuery.validator.format("������һ�����Ϊ {0} ��ֵ"),
		min: jQuery.validator.format("������һ����СΪ {0} ��ֵ")
});

jQuery.extend(jQuery.validator.defaults, {
    errorElement: "span"
});