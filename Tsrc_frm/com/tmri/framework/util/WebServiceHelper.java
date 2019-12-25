package com.tmri.framework.util;

import com.tmri.framework.ws.client.bean.ReturnInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Joey on 2015/12/30.
 */
public class WebServiceHelper {

    public static String getXMLFileHead() {
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>\n";
    }

    public static String getXMLFileFoot() {
        return "</root>";
    }

    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

    public static String encodeUTF8(String xmlDoc) {
        String str = "";
        try {
            str = URLEncoder.encode(xmlDoc, "utf-8");
        } catch (Exception e) {
            str = e.toString();
        }
        return str;
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

    public static ReturnInfo parseResponse(String response, Class classType) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(response));
        Element root = document.getRootElement();
        Element head = root.element("head");
        Element body = root.element("body");
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(head.element("code").getText());
        returnInfo.setMessage(head.element("message").getText());
        if (head.elementText("rownum") != null) {
            int rowCount = Integer.valueOf(head.elementText("rownum"));
            returnInfo.setRowNum(rowCount);
            List resultList = new ArrayList(rowCount);
            List<Element> entries = body.elements("veh");
            for (Element entry : entries) {
                int rowNo = Integer.valueOf(entry.attributeValue("id"));
                Object object = classType.newInstance();
                for(Field field :classType.getDeclaredFields()) {
                    field.setAccessible(true);
                    String fieldValue = entry.elementText(field.getName());
                    if (StringUtils.isNotEmpty(fieldValue)) {
                        if (field.getGenericType().toString().equals(Date.class.toString())) {
                            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
                            field.set(object, format.parse(fieldValue));
                        } else if (field.getGenericType().toString().equals(Integer.class.toString())
                                || field.getGenericType().toString().equals("int")) {
                            field.set(object, Integer.valueOf(fieldValue));
                        } else if (field.getGenericType().toString().equals(Long.class.toString())
                                || field.getGenericType().toString().equals("long")) {
                            field.set(object, Long.valueOf(fieldValue));
                        } else if (field.getGenericType().toString().equals(Double.class.toString())
                                || field.getGenericType().toString().equals("double")) {
                            field.set(object, Double.valueOf(fieldValue));
                        }
                        else {
                            field.set(object, fieldValue);
                        }
                    }
                }
                resultList.add(rowNo, object);
            }
            returnInfo.setResultList(resultList);
        }
        return returnInfo;
    }

}
