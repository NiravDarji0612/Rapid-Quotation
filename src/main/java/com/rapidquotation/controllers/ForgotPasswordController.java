package com.rapidquotation.controllers;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.EmailServiceImpl;

@Controller
public class ForgotPasswordController {
	
	
	Random random = new Random(1000);
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;	
	
	@Autowired
	private UserRepo userRepo;
		
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	
	  @RequestMapping("/forgot-password")
	  public String getForgotPasswordView()
	  {
		  return "ForgotPassword";
	  }
	  
	  @PostMapping("/send-otp")
	  public String sendOtp(@RequestParam("email")String email,HttpSession session)
	  {
		 
		  UserRegistration userRegistration =userRepo.getUserbyUserName(email);
		  
		  if(userRegistration == null)
		  {
			  //send error message
			  session.setAttribute("message", "User does not exists with this email");
			  return "ForgotPassword";
		  }else {
			  
			  session.setAttribute("email", email);
		  }
		  
		 System.out.println(email);
		 
		 //generating otp of 4 digit
		 int otp =  random.nextInt(9999); 
		 System.out.println(otp);
		 
		 //writing code for send otp to email...
		 
		 String subject = "OTP from Rapid Quotation";
		 String message = ""
		 		+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
		 		+ "<h1>"
		 		+ "OTP : "
		 		+"<b>"+otp
		 		+"</b>"
		 		+"</h1>"
		 		+ "</div>";
		 String to = email;
		 
		 boolean flag = emailServiceImpl.sendEmail(subject, message, to);
		 
		 if(flag) {
			 session.setAttribute("myotp",otp);
			 return "verify_otp";
		 }else {
			 session.setAttribute("message","Check Your Email id !");
			 return "ForgotPassword" ;
		}
		 
	  }
	  
	  
	  //verify otp
	  @PostMapping("/verify-otp")
	  public String verifyOtp(@RequestParam("otp")int otp,HttpSession session) {
		  
		  int myOtp = (int) session.getAttribute("myotp");
		  
		  if(myOtp == otp) { 
			  return "ChangePassword";
		  }else
		  {
			  session.setAttribute("message", "You have entered Wrong Otp..");
			  return "verify_otp";
		  }
		  
	  }
	
	  @PostMapping("/change-password")
	  public String changePassword(@RequestParam("newpassword")String newpassword,HttpSession session) {
		  
		 
		  if(newpassword.length()<8) {
			  session.setAttribute("message", "password size should be minimum 8 characters");
			  return "ChangePassword";
		  }
		  
		  
		  String email = (String) session.getAttribute("email");
		  UserRegistration user = userRepo.getUserbyUserName(email);
		  user.setPassword(passwordEncoder.encode(newpassword));
		  userRepo.save(user);
		  return "redirect:/signin?change=password change successfully please login..";
	  }
	  
	  
	  
	  
	

}
