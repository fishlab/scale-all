package org.fishlab.util.configuration.test;

import com.czway.util.configuration.XmlConfiguration;
class Employee {
	private String name;
	private String sex;
	private int age;
	private boolean marry;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isMarry() {
		return marry;
	}
	public void setMarry(boolean marry) {
		this.marry = marry;
	}
	
}


public class test {
	
	public static void main(String args[]){
		
		Employee e=new Employee();
		e.setAge(18);
		e.setName("gfeen");
		e.setSex("male");
		e.setMarry(false);
		XmlConfiguration axf=new  XmlConfiguration("E:/web/config.xml");
		axf.writeConfiguratioin(e,null);
//		 axf.writeSingleFile(e, "g");
//		 System.out.println(axf.getSimpleName(String.class) );	 
//		 e=axf.getConfigurationBean(Employee.class); 
		 System.out.println(e.getAge()+e.getName()+e.getSex()+e.isMarry());

	}
	
}
