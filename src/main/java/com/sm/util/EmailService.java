package com.sm.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sm.config.EnvironmentConfig;
import com.sm.exception.EmailNotSent;

@Component
public class EmailService {
	
	
	@Autowired
	EnvironmentConfig config;
	
	public boolean sentMail(String email,String code) {
	      String recipientAddress = email;
	      final  String senderAddress = config.getEmail().getSender(); //"swarup.bip@outlook.com";
	      final String password=config.getEmail().getPassword();  //"Swarup@123";
	      boolean result = false;
	      String host = config.getEmail().getHost(); //"smtp.office365.com";

	      // Get system properties
	      Properties props = System.getProperties();
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.port", "587");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.auth", "true");

	      Authenticator authenticator = new Authenticator() {
	          @Override
	          protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderAddress, password);
	          }
	      };
	      
	      Session session = Session.getInstance(props, authenticator);

	      try {
	             MimeMessage msg = new MimeMessage(session);
	             msg.addHeader(config.getEmail().getContentType(), "text/HTML; charset=UTF-8"); //"Content-type"
	             msg.addHeader("format", "flowed");
	             msg.addHeader("Content-Transfer-Encoding", "8bit");
	             msg.setFrom(new InternetAddress(senderAddress));
	             msg.setSentDate(new Date());
	             msg.setSubject(config.getEmail().getSubject());  //"Reset Password"
	             msg.setText(config.getEmail().getMessage()+"  "+ code);  //"Hi , Your OTP for Resetting the Password is  : "
	             msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress, false));
	             System.out.println(config.getEmail().getStatus()); //"Message is ready"
	             Transport.send(msg);
	             result = true;
	      } catch (MessagingException mex) {
	            throw new EmailNotSent(Constants.EMAIL_NOT_SENT);
	      }
	   return result;   
	}

}
