package com.czway.util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fishlab.util.io.FileUtils;
import org.xml.sax.SAXException;


public class XmlUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator<Element> iterator = root.elementIterator(); iterator
				.hasNext();) {
			Element e = iterator.next();
			// System.out.println(e.getName());
			List<Element> list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	public static Map<String,Object> dom2Map(String fn){
		File xmlfile = FileUtils.getFileFromClassPath(fn);
		if (xmlfile!=null&&xmlfile.exists()) {
			Document dct = null;
			SAXReader sar = new SAXReader();
			
			try {
				sar.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
			} catch (SAXException e) {
				e.printStackTrace();
			}
			try {
				dct = sar.read(xmlfile);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			return dom2Map(dct);
		}
		else
			return null;
		
	}

	@SuppressWarnings("unchecked")
	protected final static Map<String, Object> dom2Map(Element e) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Element> list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List<Object> mapList = new ArrayList<Object>();
				if (iter.elements().size() > 0) {
					Map<String, Object> m = dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List<Object>) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List<Object>) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
}
