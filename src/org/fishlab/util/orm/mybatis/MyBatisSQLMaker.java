package org.fishlab.util.orm.mybatis;

import java.util.List;

import org.fishlab.util.persistence.impl.ClassInfo;
import org.fishlab.util.persistence.impl.ClassInfoCache;

import com.czway.util.bean.AttributeUtil;

public class MyBatisSQLMaker extends ClassInfoCache {



	public String getSelect(Class<?> cl) {
		ClassInfo cli = this.getClassInfo(cl);
		StringBuilder stb = new StringBuilder(32);
		stb.append("select * from ");
		stb.append(cli.getDatatable());
		return stb.toString();
	}

	public String getSelect(Class<?> cl, String condition) {
		ClassInfo cli = this.getClassInfo(cl);
		StringBuilder stb = new StringBuilder(32);
		stb.append("select * from " + cli.getDatatable() + " where "
				+ condition);
		return stb.toString();
	}

	public String getInsert(Class<?> cl) {
		ClassInfo cli = this.getClassInfo(cl);
		StringBuilder stb = new StringBuilder(32);
		List<String> names = AttributeUtil.getSetterNames(cl);
		int p0 = cli.getIdentifier() == null ? names.indexOf("id") : names
				.indexOf(cli.getIdentifier());
		stb.append("insert into " + cli.getDatatable() + "(");
		if (names.size() > 0) {
			int s=1;
			int i=0;
			int j = 1;
			if (p0==0){
				s=0;
				i=1;
			}
			stb.append(names.get(i));
			for (j=s; j < names.size(); j++) {
				if (p0 != j&&j!=i)
					stb.append("," + names.get(j));
			}
			stb.append(")\nvalues(");

			stb.append("#{" + names.get(i) + "}");
			for (j=s; j < names.size(); j++) {
				if (j != p0&&j!=i) {
					stb.append(",#{");
					stb.append(names.get(j));
					stb.append("}");
				}
			}
			stb.append(")");
		}
		return stb.toString();
	}

	public String getUpdate(Class<?> cl) {
		ClassInfo cli = this.getClassInfo(cl);
		StringBuilder stb = new StringBuilder(32);
		List<String> names = AttributeUtil.getSetterNames(cl);
		int p0 = cli.getIdentifier() == null ? names.indexOf("id") : names
				.indexOf(cli.getIdentifier());
		stb.append("update " + cli.getDatatable() + " set\n");
		if (names.size() > 0) {
			int s=1;
			int i=0;
			int j = 1;
			if (p0==0){
				s=0;
				i=1;
			}
			stb.append(names.get(i));
			stb.append("=#{" + names.get(i) + "}");
			for (j=s; j < names.size(); j++) {
				if (p0 != j&&j!=i){
					stb.append("," + names.get(j));
					stb.append("=#{" + names.get(j) + "}");
				}
			}
			
		}
		String id=cli.getIdentifier();
		stb.append(" where "+id+"=#{"+id+"}");
		return stb.toString();
	}
	
	public String getUpdateSet(Class<?> cl) {
		ClassInfo cli = this.getClassInfo(cl);
		StringBuilder stb = new StringBuilder(32);
		List<String> names = AttributeUtil.getSetterNames(cl);
		int p0 = cli.getIdentifier() == null ? names.indexOf("id") : names
				.indexOf(cli.getIdentifier());
		stb.append("update " + cli.getDatatable() + "\n<set>");
		if (names.size() > 0) {
//			int i=0;
			int j = 0;//index
//			stb.append(names.get(i));
//			stb.append("=#{" + names.get(i) + "}");
			for (; j < names.size(); j++) {
				if (p0 != j){//不是 id
					stb.append("<if test=\""+names.get(j)+"!=null\">");
					stb.append(names.get(j));
					if(j!=names.size()-1){//不是最后一个元素
						stb.append("=#{" + names.get(j) + "},");
					}else{
						stb.append("=#{" + names.get(j) + "}");
					}
					stb.append("</if>");
				}
			}
			
		}
		String id=cli.getIdentifier();
		stb.append("</set>\nwhere "+id+"=#{"+id+"}");
		return stb.toString();
	}

	public String getUpdate(Object obj, String... names) {
		return null;
	}

	public String getDelete(Object obj) {
		return "delete from ? where ?";
	}

	public String getDelete(Class<?> cl, String condition) {
		return null;
	}

}
