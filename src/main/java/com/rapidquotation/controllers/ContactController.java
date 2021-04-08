package com.rapidquotation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rapidquotation.entities.Contact;
import com.rapidquotation.serviceImpl.ContactServiceImpl;

@Controller
public class ContactController {
	
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Autowired
	private ContactServiceImpl contactServiceImpl;
	
	  @RequestMapping("/contact")
	  public ModelAndView getContactUs()
	  {
		 
		  ModelAndView modelAndView = new ModelAndView();
		  Contact contact = new Contact();
		  modelAndView.addObject("contact",contact);
		  modelAndView.setViewName("contact");
		  return modelAndView;
	  }
	  
	  @PostMapping("/send")
	  public String send(@ModelAttribute("contact")Contact contact,ModelMap map){
		  
		try {
		 String message = contact.getMessage()+" "+"Email: "+contact.getFrom();
		 String subject = contact.getSubject();
		 String from = contact.getFrom();
		 String to = this.username;
		 this.contactServiceImpl.sendContactMail(subject, from, message, to);
		 map.put("msg","Done..Thank You for contacting us,we will reach you soon.");
		 
		}catch (Exception e) {
			map.put("errors", e.getMessage());
		}
		
		 return "contact";
	  }
	  
	  
	
}
