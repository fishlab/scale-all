package org.fishlab.util.mail;

import java.util.Properties;

public abstract class AbstractMailSender implements MailSender{
	public final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	 
	private boolean enableSSL=true;
	@Override
	public void setUseSSL(boolean state) {
		this.enableSSL=state;
	}

	@Override
	public boolean isSSLEnabled() {
		return this.enableSSL;
	}
	
	protected Properties getProperties(MailSenderInfo info) {
		Properties p = new Properties();
		p.put("mail.smtp.host", info.getMailServerHost());
		p.put("mail.smtp.port", String.valueOf(info.getMailServerPort()) );
		p.put("mail.smtp.auth", info.isValidate() ? "true" : "false");
		p.put("mail.smtp.connectiontimeout", "10000");
		p.put("mail.smtp.timeout", "10000");
		if(this.enableSSL)
			p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		return p;
	}

}
