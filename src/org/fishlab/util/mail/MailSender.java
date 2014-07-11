package org.fishlab.util.mail;

public interface MailSender {
	public void setUseSSL(boolean state);
	public boolean isSSLEnabled();
	public boolean send(MailSenderInfo info);
}
