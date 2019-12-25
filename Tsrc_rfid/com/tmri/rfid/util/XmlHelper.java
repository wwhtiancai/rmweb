package com.tmri.rfid.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Joey on 2015/12/30.
 */
public class XmlHelper {

    public static String getXMLFileHead() {
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>\n";
    }

    public static String getXMLFileFoot() {
        return "</root>";
    }

    public static String map2XMLUTF8(Map<String, String> map, String itemName, String itemId) throws Exception {
        StringBuffer buffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
        buffer.append("<");
        buffer.append(itemName);
        if (StringUtils.isEmpty(itemId)) {
            buffer.append(">");
        } else {
            buffer.append(" id=\"");
            buffer.append(itemId);
            buffer.append("\">");
        }
        while(i.hasNext()) {
            Map.Entry<String, String> entry = i.next();
            String value = entry.getValue();
            buffer.append("\n <");
            buffer.append(entry.getKey());
            buffer.append(">");
            if (StringUtils.isEmpty(value)) {
                buffer.append("");
            } else {
                value = encodeUTF8(value);
                buffer.append(value);
            }
            buffer.append("</");
            buffer.append(entry.getKey());
            buffer.append(">");
        }
        buffer.append("\n</");
        buffer.append(itemName);
        buffer.append(">\n");
        return buffer.toString();
    }

    public static String bean2XMLUTF8(Object bean, String itemName, String itemId) throws Exception {
        StringBuffer buffer = new StringBuffer();
        Map p = BeanUtils.describe(bean);
        Set s = p.keySet();
        Iterator i = s.iterator();
        buffer.append("<");
        buffer.append(itemName);
        if (StringUtils.isEmpty(itemId)) {
            buffer.append(">");
        } else {
            buffer.append(" id=\"");
            buffer.append(itemId);
            buffer.append("\">");
        }
        while(i.hasNext()) {
            Object key = i.next();
            if (!key.equals("class")) {
                Object value = p.get(key);
                buffer.append("\n <");
                buffer.append(key);
                buffer.append(">");
                if (value == null || value.toString().equals("")) {
                    buffer.append("");
                } else {
                    value = encodeUTF8(value.toString());
                    buffer.append(value);
                }
                buffer.append("</");
                buffer.append(key);
                buffer.append(">");
            }
        }
        buffer.append("\n</");
        buffer.append(itemName);
        buffer.append(">\n");
        return buffer.toString();
    }

    /**
     * @deprecated XML to map
     * @param xmlString
     * @return
     * @throws DocumentException
     */
    public static  Map xml2map(String xmlString) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlString);
        Element rootElement = doc.getRootElement();
        Map<String, Object> map = new HashMap<String, Object>();
        ele2map(map, rootElement);
        return map;
    }

    /***
     * ���ķ����������еݹ����
     *
     * @param map
     * @param ele
     */
    static void ele2map(Map map, Element ele) {
        // ��õ�ǰ�ڵ���ӽڵ�
        List<Element> elements = ele.elements();
        if (elements.size() == 0) {
            // û���ӽڵ�˵����ǰ�ڵ���Ҷ�ӽڵ㣬ֱ��ȡֵ����
            map.put(ele.getName(), ele.getText());
        } else if (elements.size() == 1) {
            // ֻ��һ���ӽڵ�˵�����ÿ���list�������ֱ�Ӽ����ݹ鼴��
            Map<String, Object> tempMap = new HashMap<String, Object>();
            ele2map(tempMap, elements.get(0));
            map.put(ele.getName(), tempMap);
        } else {
            // ����ӽڵ�Ļ��͵ÿ���list������ˣ��������ӽڵ��нڵ�������ͬ��
            // ����һ��map����ȥ��
            Map<String, Object> tempMap = new HashMap<String, Object>();
            for (Element element : elements) {
//				tempMap.put(element.getName(), null);
//				ele2map(tempMap, element);
                tempMap.put(element.getName(), element.getText());
            }
            map.put(ele.getName(), tempMap);
			/*Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = elements.get(0).getNamespace();
				List<Element> elements2 = ele.elements(new QName(string, namespace));
				// ���ͬ������Ŀ����1���ʾҪ����list
				if (elements2.size() > 1) {
					List<Map> list = new ArrayList<Map>();
					for (Element element : elements2) {
						Map<String, Object> tempMap1 = new HashMap<String, Object>();
						ele2map(tempMap1, element);
						list.add(tempMap1);
					}
					map.put(string, list);
				} else {
					// ͬ��������������1��ֱ�ӵݹ�ȥ
					Map<String, Object> tempMap1 = new HashMap<String, Object>();
					ele2map(tempMap1, elements2.get(0));
					map.put(string, tempMap1);
				}
			}*/
        }
    }
    public static String encodeUTF8(String xmlDoc) {
        String str = "";
        try {
            str = URLEncoder.encode(xmlDoc, "utf-8");
        } catch (Exception e) {
            str = e.toString();
        }
        return str;
    }
    public static String map2XML(Map<String, String> map, String itemName, String itemId) throws Exception {
        StringBuffer buffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
        buffer.append("<");
        buffer.append(itemName);
        if (StringUtils.isEmpty(itemId)) {
            buffer.append(">");
        } else {
            buffer.append(" id=\"");
            buffer.append(itemId);
            buffer.append("\">");
        }
        while(i.hasNext()) {
            Map.Entry<String, String> entry = i.next();
            String value = entry.getValue();
            buffer.append("\n <");
            buffer.append(entry.getKey());
            buffer.append(">");
            if (StringUtils.isEmpty(value)) {
                buffer.append("");
            } else {
//                value = encodeUTF8(value);
                buffer.append(value);
            }
            buffer.append("</");
            buffer.append(entry.getKey());
            buffer.append(">");
        }
        buffer.append("\n</");
        buffer.append(itemName);
        buffer.append(">\n");
        return buffer.toString();
    }

    public static String decodeUTF8(String str) throws Exception {
        String xmlDoc = "";
        try {
            xmlDoc = URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
            xmlDoc = e.toString();
        }
        return xmlDoc;
    }

}
