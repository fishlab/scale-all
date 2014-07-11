package org.fishlab.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TextMailSender extends AbstractMailSender {

	@Override
	public boolean send(MailSenderInfo mailInfo) {
		Session sendMailSession = null;
		Properties pro = this.getProperties(mailInfo);
		if (mailInfo.isValidate()) {
			MailAuthenticator authenticator = null;
			authenticator = new MailAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
			sendMailSession = Session.getDefaultInstance(pro, authenticator);
		} else {
			sendMailSession = Session.getDefaultInstance(pro);
		}
//		sendMailSession.setDebug(true);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setText(mailInfo.getContent());
//			mailMessage.setContent(mailInfo.getContent(), "text/plain; charset=utf-8");
			mailMessage.setSentDate(new Date());
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
