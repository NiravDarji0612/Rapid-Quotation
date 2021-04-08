package com.rapidquotation.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Path.ReturnValueNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.rapidquotation.entities.Contact;
import com.rapidquotation.entities.RecaptchaResponse;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.helper.MessageHelper;
import com.rapidquotation.serviceImpl.UserServiceImplentation;

@Controller
public class UserController {
	

	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	 
	@Value("${recaptcha.secret}")
	private String recaptchaSecret;
	
	@Value("${recaptcha.url}")
	private String recaptchaServerUrl;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private UserServiceImplentation userService;
	
	//to get a homepage
	@RequestMapping("/home")
	public ModelAndView gethomePage()
	{
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}
	
	//to get a Register form
	@RequestMapping("/register")
	public  ModelAndView register(Model model)
	{
		UserRegistration user = new UserRegistration();
		ModelAndView view = new ModelAndView();
		view.addObject("user" , user);
		view.setViewName("Register-form");
		return view;
	}
	
	//after filling all the data in register-form , request comes here.
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	 public String saveUser(@Valid @ModelAttribute("user") UserRegistration user,BindingResult result,HttpSession session,HttpServletRequest request,HttpServletResponse httpServletResponse) throws IOException
	{
		
		String gReCaptchaResponse=request.getParameter("g-recaptcha-response");
		
		if(!verifyCaptcha(gReCaptchaResponse)){
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
			
				}
		
		
		
		if(result.hasErrors())
		{
			return "Register-form";
		}
		
		else {
			
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.create(user);
			session.setAttribute("message", new MessageHelper("Registration succesfull!! please login","alert-success"));
			return "Register-form";	 
		} 
		
	}
	

	
	
	private boolean verifyCaptcha(String gReCaptchaResponse) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("secret",recaptchaSecret);
		map.add("response",gReCaptchaResponse);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,httpHeaders);
		
		RecaptchaResponse response =restTemplate.postForObject(recaptchaServerUrl, request, RecaptchaResponse.class);
		
		System.out.println("success: "+response.isSuccess());
		System.out.println("Hostname: "+response.getHostname());
		System.out.println("Challenge Timestamp: "+response.getChallenge_ts());
		
		if(response.getErrorCodes() != null) {
			for(String error:response.getErrorCodes()) {
				System.out.println("\t" + error);
			}
		}
		
		return  response.isSuccess();
		
	}

	@RequestMapping("/signin")
	public String getLoginPage()
	{
		return "login-page";	
	}
	  
	  
	  @RequestMapping("/about")
	  public String getAboutUs()
	  {
		  return "about";
	  }
}
