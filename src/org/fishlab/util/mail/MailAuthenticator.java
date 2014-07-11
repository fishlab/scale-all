package org.fishlab.util.mail;

import javax.mail.*;
  
public class MailAuthenticator extends Authenticator{
	String userName=null;
	String password=null;
	 
	public MailAuthenticator(){
	}
	public MailAuthenticator(String username, String password) { 
		this.userName = username; 
		this.password = password; 
	} 
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
 
