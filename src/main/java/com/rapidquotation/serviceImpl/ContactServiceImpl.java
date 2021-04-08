package com.rapidquotation.serviceImpl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendContactMail(String subject,String from,String message,String to) {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		try {
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(message,true);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setSentDate(new Date());
			this.mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	  

}
