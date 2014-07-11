package org.fishlab.util.persistence.impl;

import java.lang.reflect.Field;
import java.util.List;

public class ClassInfo {
	private String identifier;
	private String database;
	private String datatable;
	
	private List<Field> fieldlist;
	private List<String> namelist;
	
//	private Field identifier;
	
	public ClassInfo(){
		
	}

	public String getIdentifier() {
		if(this.identifier!=null)
		return identifier;
		else{
			return "id";
		}
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public List<Field> getFieldlist() {
		return fieldlist;
	}

	public void setFieldlist(List<Field> fieldlist) {
		this.fieldlist = fieldlist;
	}

	public List<String> getNamelist() {
		return namelist;
	}

	public void setNamelist(List<String> namelist) {
		this.namelist = namelist;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getDatatable() {
		return datatable;
	}

	public void setDatatable(String datatable) {
		this.datatable = datatable;
	}
	
	
}
