package com.rapidquotation.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.lowagie.text.DocumentException;
import com.rapidquotation.entities.Company;
import com.rapidquotation.entities.SoftwareDevelopmentItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.SoftwareDevelopmentQuote;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.SoftwareDevelopmentServiceImpl;

@Controller
public class SoftwareDevelopmentController {
	
	

	@Autowired
	private SoftwareDevelopmentServiceImpl softwareDevelopmentServiceImpl;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@RequestMapping("/softwareDevelopment")
	public ModelAndView getSoftwareDevelopmentView(Principal principal)
	{
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		ModelAndView view = new ModelAndView();
		Company company = new Company();
		SoftwareDevelopmentQuote softwareDevelopmentQuote = new SoftwareDevelopmentQuote();
		softwareDevelopmentQuote.getSoftwareItems().add(new SoftwareDevelopmentItems());
		softwareDevelopmentQuote.setCompany(company);
		view.addObject("software", softwareDevelopmentQuote);
		view.addObject("user",user);
		view.setViewName("SoftwareDevelopment");
		return view;
	}
	
	
	@PostMapping(value = "/quotation-software-details")
	public ModelAndView getSoftwareDevelopmentQuoteDetails(@ModelAttribute("software") SoftwareDevelopmentQuote softwareDevelopmentQuote ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		int price = 0;
		for(SoftwareDevelopmentItems item : softwareDevelopmentQuote.getSoftwareItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
			
		}
		softwareDevelopmentQuote.setTotal(price);
		
		int contentWritingCost = softwareDevelopmentQuote.getContentWritingCost();
		int codingDevelopmentCost = softwareDevelopmentQuote.getCodingDevelopmentCost();
		int paymentIntegrationCost= softwareDevelopmentQuote.getPaymentGatewayCost();
		int domainHostingCost = softwareDevelopmentQuote.getDomainHosting();
		int technicalSupport = softwareDevelopmentQuote.getTechnicalSupportCost();
		int testing = softwareDevelopmentQuote.getTestingCost();
		int maintainanceCost = softwareDevelopmentQuote.getMaintainanceCost();
		
		long totalIncludingAllCosts = (long) contentWritingCost+codingDevelopmentCost+paymentIntegrationCost+domainHostingCost+technicalSupport+testing+maintainanceCost+price;
		softwareDevelopmentQuote.setTotalIncludingAllCosts(totalIncludingAllCosts);
		
		Integer gst = Integer.parseInt(softwareDevelopmentQuote.getGst());
		long gstCount= totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		softwareDevelopmentQuote.setTotalIncludingAllCostsWithGst(totalWithGst);

	
		
		
		softwareDevelopmentServiceImpl.saveSoftDevlopmentQuote(multipartFile,softwareDevelopmentQuote);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("software", softwareDevelopmentQuote);
		//view.addObject("image", multipartFile);
		view.setViewName("SoftwareDevelopmentQuoteClientView");
		return view;	
	}
	
	
	@GetMapping("/generate-software-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		SoftwareDevelopmentQuote softwareDevelopmentQuote = (SoftwareDevelopmentQuote) session.getAttribute("software");
		
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("software",softwareDevelopmentQuote );
	        String orderHtml = templateEngine.process("SoftwareDevelopmentPDFGeneration", context);

	        /* Setup Source and target I/O streams */

	        ByteArrayOutputStream target = new ByteArrayOutputStream();

	        /*Setup converter properties. */
	        ConverterProperties converterProperties = new ConverterProperties();
	        converterProperties.setBaseUri("http://localhost:8080");

	        /* Call convert method */
	        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

	        /* extract output as bytes */
	        byte[] bytes = target.toByteArray();


	        /* Send the response as downloadable PDF */

	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(bytes);

	}
	
}
