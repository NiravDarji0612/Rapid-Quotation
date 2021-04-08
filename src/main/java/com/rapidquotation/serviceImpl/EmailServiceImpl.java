package com.rapidquotation.serviceImpl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class EmailServiceImpl {	
	
	String from = "darjinirav18@gmail.com";
	
	public boolean sendEmail(String subject,String message,String  to) {
		
		
			boolean f = false;
		
				//Variable for gmail host
				String host = "smtp.gmail.com";
				
				//get the system properties
				Properties properties = System.getProperties();
				System.out.println("PROPERTIES "+properties);
				
				//setting important information to properties object
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.port", "465");
				properties.put("mail.smtp.ssl.enable","true");
				properties.put("mail.smtp.auth","true");
				
				//Step:1 GET THE SESSION OBJECT
				Session session = Session.getInstance(properties, new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						
						return new PasswordAuthentication("darjinirav18@gmail.com","Nirav@1299");
					}
					
				});
				session.setDebug(true);
				
				//Step:2 COMPOSE THE MESSAGE[TEXT OR MULTI-MEDIA]
				MimeMessage m = new MimeMessage(session);
				
				 
				try {
					
					//from
					m.setFrom(from);
				
					//to
					m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					
					//subject
					m.setSubject(subject);
					
					//adding text to message
					//m.setText(message);
					m.setContent(message,"text/html");
					
					//Step:2 SEND THE MESSAGE USING TRANSPORT CLASS
					
					Transport.send(m);
					
					System.out.println("sent succesfully......");
				
					f=true;
					
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				return f;
				
	}
	
	
	public boolean contactUsEmailService(String fullName,String from,String message,String to) {
		
		boolean f = false;
		
		//Variable for gmail host
		String host = "smtp.gmail.com";
		
		//get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);
		
		//setting important information to properties object
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		
		return f;
	}
	
	
	
	
	
	
}
