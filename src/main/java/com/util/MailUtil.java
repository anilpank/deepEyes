package com.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.naukri.NaukriLaunch;
import com.properties.PropReader;

public class MailUtil {	
	private static final Logger log = LogManager.getLogger(MailUtil.class);
	
	public boolean mail(final String subject, final String body) {
		boolean result = true;
		PropReader propReader = new PropReader();
		Properties props = new Properties();
		
		props.put("mail.smtp.host", propReader.get("SMTP_HOSTNAME"));
		props.put("mail.from",propReader.get("mail.from"));
		props.put("mail.smtp.starttls.enable", propReader.get("mail.smtp.starttls.enable"));
		props.put("mail.smtp.auth", propReader.get("mail.smtp.auth"));
		props.put("mail.debug", propReader.get("mail.debug"));

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				PropReader propReader = new PropReader();
				String USERNAME =propReader.get("SMTP_USERNAME");
				String PASSWORD = propReader.get("SMTP_PASSWORD");
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom();
			msg.setRecipients(Message.RecipientType.TO,
					propReader.get("emailTo"));
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(body);
			Transport.send(msg);
		} catch (MessagingException mex) {
			log.error("Unable to send message", mex);
			result = false;
			return result;
		}
		
		return result;
	}


}
