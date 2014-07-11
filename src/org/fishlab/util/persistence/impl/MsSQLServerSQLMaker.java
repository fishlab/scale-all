package org.fishlab.util.persistence.impl;

import java.lang.reflect.Field;

public class MsSQLServerSQLMaker extends StandardSQLMaker{
	
	@Override
	public String getInsert(Object obj){
		ClassInfo cli=this.getClassInfo(obj.getClass());
		int p0=cli.getNamelist().indexOf(cli.getIdentifier());
		if (p0!=-1){
			Field fd= cli.getFieldlist().get(p0);
			fd.setAccessible(true);
			try {
				Object o=fd.get(obj);
				String cv=super.typeConvert(o);
				if (cv.equals("0")||cv.equals("null") ){
					return this.insertWithoutIdentifier(obj, cli,p0);
				};
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		StringBuilder stb=new StringBuilder("set IDENTITY_Insert "+cli.getDatatable()+" ON;");
		stb.append(this.inertWithIdentifier(obj, cli));
		return stb.toString();
		
	}

}
