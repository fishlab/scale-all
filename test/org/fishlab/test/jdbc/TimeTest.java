package org.fishlab.test.jdbc;

import java.io.Serializable;

public class TimeTest implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private java.sql.Date regdate;
	private java.sql.Time regtime; 
	private java.sql.Timestamp regtimestamp;
	private java.util.Date regdatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.sql.Date getRegdate() {
		return regdate;
	}
	public java.util.Date getRegdatetime() {
		return regdatetime;
	}
	public void setRegdatetime(java.util.Date regdatetime) {
		this.regdatetime = regdatetime;
	}
	public void setRegdate(java.sql.Date regdate) {
		this.regdate = regdate;
	}
	public java.sql.Time getRegtime() {
		return regtime;
	}
	public void setRegtime(java.sql.Time regtime) {
		this.regtime = regtime;
	}
	public java.sql.Timestamp getRegtimestamp() {
		return regtimestamp;
	}
	public void setRegtimestamp(java.sql.Timestamp regtimestamp) {
		this.regtimestamp = regtimestamp;
	}



}
