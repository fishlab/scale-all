package com.czway.util.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXReader;
import org.fishlab.util.io.FileUtils;
import org.xml.sax.SAXException;

import com.czway.util.xml.XmlUtils;
import com.czway.util.bean.BeanAccess;

public class XmlConfiguration implements Configuration {
	protected String fn;

//	private Map<String,Object> cachedmap;

	public XmlConfiguration(String fn) {
		this.fn = fn;
//		this.cachedmap=new HashMap<String,Object> ();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getConfigurationMap(String name){
		Map<String, Object> map = XmlUtils.dom2Map(this.fn);
		if (map instanceof Map)
			return (Map<String, Object>) map.get(name);
		else
			return null;
	}
	
	public <T> T getConfigurationBean(Class<T> t) {
		String classname = t.getName();
		String shortname = null;
		int p0 = 0;
		p0 = classname.lastIndexOf('.');
		if (p0 == -1)
			shortname = classname.toLowerCase();
		else
			shortname = classname.substring(++p0).toLowerCase();
		return getConfigurationBean(t, shortname);
	}

	@SuppressWarnings("unchecked")
	public <T> T getConfigurationBean(Class<T> t, String name) {
		Map<String, Object> map;
		map = XmlUtils.dom2Map(this.fn);
		Map<String, Object> m = (Map<String, Object>) map.get(name);
		// System.out.println(m);
		T cfgbean = null;
		try {
			cfgbean = t.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		Field fl[] = t.getDeclaredFields();
		Object value = null;
		String fieldname = null;
		Class<?> type = null;
		for (Field f : fl) {
			type = f.getType();
			fieldname = f.getName();
			value = m.get(fieldname);
			if (value != null) {
				if (type == String.class)
					// value =(String) value;
					BeanAccess
							.setFieldValue(cfgbean, fieldname, (String) value);
				else if (type == int.class)
					value = Integer.parseInt((String) value);
				else if (type ==boolean.class)
					value =new Boolean((String)value).booleanValue();
				BeanAccess.setFieldValue(cfgbean, fieldname, value);
			}
		}
		return cfgbean;
	}

	private String getSimpleName(Class<?> c) {
		String n = c.getName();
//		System.out.println(c);
		int p = n.lastIndexOf('.');
		if (p != -1) {
			p++;
			n = n.substring(p);
		}
		return n.toLowerCase();
	}

	@Override
	public <T> void writeConfiguratioin(Object o, String name) {
		File xmlfile = FileUtils.getFileFromClassPath(this.fn);
//		System.out.println(xmlfile);
		if (!xmlfile.exists()) {
			try {
				xmlfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (xmlfile.length()<64){
			try {
				FileOutputStream fos=new FileOutputStream(xmlfile);
				fos.write( ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
						  "\n\t<bean-config>"+
						  "\n\t</bean-config>").getBytes() );
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Document dct = null;
		SAXReader sar = new SAXReader();
		try {
			sar.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-external-dtd",
					false);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		try {
			dct = sar.read(xmlfile);
			Element el = dct.getRootElement();
			String cn = o.getClass().getSimpleName();
			Element e2 = el.element(cn);
			String e2name;
			if (e2==null){
				e2=el.addElement(cn);
			}
			else if( (e2name=e2.attributeValue("name"))!=null&&e2name.equals(name)  ){
				e2.clearContent();
			}	
			e2.addAttribute("name", name);
			if (e2!=null) {
//				el.remove(e2);
//				e2=el.addElement(cn);
				Vector<Field[]> vf;//=new Vector<Field[]>();
				vf=BeanAccess.getDeclaredFields(o.getClass());
				for (Field fd[]:vf)
				for (Field f:fd){
					Object v=BeanAccess.getFieldValue(o, f);
					Element e=new DOMElement(f.getName());
					if (v!=null)
						e.setText(v.toString());
					e2.add(e);
//					System.out.println (f.getName()+"="+ v);
				}
			}
			dct.normalize();
			FileWriter fw=new FileWriter(xmlfile);
//			fw.write(dct.asXML());			 
			dct.write(fw);
			fw.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeSingleFile(Object o, String n){
		String sn=this.getSimpleName(o.getClass());
		try {
			FileOutputStream fos=new FileOutputStream(this.fn);
			fos.write( ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
					  "\n <bean-config>"
					 ).getBytes() );
			
			Vector<Field[]> vf=new Vector<Field[]>();
			vf=BeanAccess.getDeclaredFields(o.getClass());
			fos.write( ("\n\t<"+sn+">" ).getBytes());
			for (Field fd[]:vf)
			for (Field f:fd){
				f.setAccessible(true);
				try {
					Object v=f.get(o);
					
					fos.write( ("\n\t\t<"+f.getName()+">").getBytes() );
					if (v!=null)
						fos.write((v.toString()).getBytes());
					else {
						fos.write("null".getBytes());
					}
					fos.write( ("</"+f.getName()+">").getBytes());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
//				Object v=BeanAccess.getFieldValue(o, f.getName());
//				System.out.println (f.getName()+"="+ v.toString());
			}
			fos.write( ("\n\t</"+sn+">" ).getBytes());
			fos.write( "\n </bean-config>".getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
