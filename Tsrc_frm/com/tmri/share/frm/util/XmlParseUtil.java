package com.tmri.share.frm.util;

import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParseUtil {
	/**
	 * ��������ʵ�岻ͬ�����ֶε�ֵ <��ʱֻ֧�� ���� �ַ��� boolean Integerֵ���� ������>
	 * 
	 * @param field
	 * @param obj
	 * @param value
	 * @throws Exception
	 */
	public static void convertValue(Field field, Object obj, String value)
			throws Exception {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (field.getGenericType().toString().equals("class java.lang.Integer")) {
			field.set(obj, Integer.parseInt(value));
		} else if (field.getGenericType().toString().equals("boolean")) {
			field.set(obj, Boolean.parseBoolean(value));
		} else if (field.getGenericType().toString()
				.equals("class java.util.Date")) {
			field.set(obj, sim.parse(value));
		} else {
			field.set(obj, value);
		}
	}

	/**
	 * ����xml�ļ����ر���cls��Bean
	 * 
	 * @param xml
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object parseXml2Bean(String xml, Class<?> cls)
			throws Exception {
		Document doc = DocumentHelper.parseText(xml);
		Element et = doc.getRootElement();
		String root = et.getName();
		if (et != null && et.hasContent()) {
			List<Element> li = et.elements();
			Class<?> cl = (Class<?>) Class.forName(cls.getName());
			Object ob = cl.newInstance();
			for (Element element2 : li) {
				String name = element2.getName();
				String value = element2.getText();
				Field field = null;
				try{
				    field = ob.getClass().getDeclaredField(name);
				}catch(Exception e){
					continue;
				}
				field.setAccessible(true);
				convertValue(field, ob, value);
			}
			return ob;
		}
		return null;
	}

	/**
	 * ����xml�ļ����ر���cls��List���ϣ����������resultCode=1ʱ�򷵻�ListΪnull
	 * 
	 * @param xml
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<?> parseXml2List(String xml, Class<?> cls)
			throws Exception {
		List<Object> lists = null;
		Document doc = DocumentHelper.parseText(xml);
		Element et = doc.getRootElement();
		String root = et.getName();
		// �鿴�������Ƿ�Ϊ��.
		List<Element> list = doc.selectNodes("//" + root + "/resultCode");
		if (!list.isEmpty() && list.size() > 0) {
			Element element = list.get(0);
			String returnResult = element.getText();
			if (returnResult.equals("0")) {
				List<Element> father = doc.selectNodes("//" + root + "/"
						+ cls.getSimpleName() + "s");
				// �ж϶��󸸽ڵ��Ƿ��а�������
				if (father != null && !father.isEmpty() && father.size() == 1) {
					List<Element> userLists = father.get(0).elements();
					if (userLists != null && !list.isEmpty()) {
						lists = new ArrayList<Object>();
						for (Element e : userLists) {
							List<Element> li = e.elements();
							Class<?> cl = (Class<?>) Class.forName(cls
									.getName());
							Object ob = cl.newInstance();
							for (Element element2 : li) {
								String name = element2.getName();
								String value = element2.getText();
								Field field = ob.getClass().getDeclaredField(
										name);
								field.setAccessible(true);
								convertValue(field, ob, value);
							}
							lists.add(ob);
						}
					}
				}
			}
		}
		return lists;
	}

	/**
	 * ����xml�ļ����ر���cls��List���ϣ����������resultCode=1ʱ�򷵻�ListΪnull
	 * 
	 * @param url
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<?> parseXml2List(URL url, Class<?> cls) throws Exception {
		List<Object> lists = null;
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(url);
		Element et = doc.getRootElement();
		String root = et.getName();
		// �鿴�������Ƿ�Ϊ��.
		List<Element> list = doc.selectNodes("//" + root + "/resultCode");
		if (!list.isEmpty() && list.size() > 0) {
			Element element = list.get(0);
			String returnResult = element.getText();
			if (returnResult.equals("0")) {
				List<Element> father = doc.selectNodes("//" + root + "/"
						+ cls.getSimpleName() + "s");
				// �ж϶��󸸽ڵ��Ƿ��а�������
				if (father != null && !father.isEmpty() && father.size() == 1) {
					List<Element> userLists = father.get(0).elements();
					if (userLists != null && !list.isEmpty()) {
						lists = new ArrayList<Object>();
						for (Element e : userLists) {
							List<Element> li = e.elements();
							Class<?> cl = (Class<?>) Class.forName(cls
									.getName());
							Object ob = cl.newInstance();
							for (Element element2 : li) {
								String name = element2.getName();
								String value = element2.getText();
								Field field = ob.getClass().getDeclaredField(
										name);
								field.setAccessible(true);
								convertValue(field, ob, value);
							}
							lists.add(ob);
						}
					}
				}
			}
		}
		return lists;
	}

	/**
	 * ����xml�ļ����ر���Map�ļ��ϣ�map�п��ܰ���keyֵΪreturnCode��desc��totalCount�ȵ��ֶ�.
	 * Ҳ���ܰ����洢����ΪList<cls>�ļ���ֵ. ��ȡListֵkey cls_List
	 * 
	 * @param requestPath
	 * @param cls
	 * @return map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseXml2Map(String requestPath,
			Class<?> cls) throws Exception {
		Map<String, Object> maps = new HashMap<String, Object>();
		List<Object> lists = new ArrayList<Object>();
		SAXReader saxReader = new SAXReader();
		// Document doc = saxReader.read(new File(requestPath));
		Document doc = saxReader.read(new URL(requestPath));
		Element et = doc.getRootElement();
		// ���List�Ƿ�Ϊ��
		// boolean bool = true ;
		// ���ڵ�����
		List<Element> rList = et.elements();
		for (Element element : rList) {
			List<Element> rLists = element.elements();
			if (!rLists.isEmpty() && rLists.size() > 0) {
				// bool = false;
				// �ж϶����ڵ�
				for (Element e : rLists) {
					List<Element> li = e.elements();
					Class<?> cl = (Class<?>) Class.forName(cls.getName());
					Object ob = cl.newInstance();
					for (Element element2 : li) {
						String name = element2.getName();
						String value = element2.getText();
						Field field = ob.getClass().getDeclaredField(name);
						field.setAccessible(true);
						convertValue(field, ob, value);
					}
					lists.add(ob);
				}
			} else {
				maps.put(element.getName(), element.getText());
			}
			maps.put(cls.getSimpleName() + "_List", lists);
		}
		return maps;
	}

	/**
	 * ֻ��ȡ������0Ϊ����ɹ�(true)1Ϊ����ʧ��(false)
	 */
	@SuppressWarnings("unchecked")
	public static boolean parseXmlReturnCode(String xml) {
		boolean bool = false;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element et = doc.getRootElement();
			String root = et.getName();
			// �鿴�������Ƿ�Ϊ��.
			List<Element> list = doc.selectNodes("//" + root + "/resultCode");
			if (!list.isEmpty() && list.size() > 0) {
				Element element = list.get(0);
				String returnResult = element.getText();
				if (returnResult.equals("0")) {
					bool = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
}
