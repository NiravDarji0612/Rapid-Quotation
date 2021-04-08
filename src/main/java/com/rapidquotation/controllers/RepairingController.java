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
import com.rapidquotation.entities.FurnitureItems;
import com.rapidquotation.entities.RepairingQuotationItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.FurnitureQuote;
import com.rapidquotation.entities.mechanical.RepairingQuotation;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.RepairingServiceImpl;

@Controller
public class RepairingController {

	@Autowired
	private RepairingServiceImpl repairServiceImpl;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@RequestMapping("/repair")
	public ModelAndView getRepairingView(Principal principal) {
		
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		
		
		ModelAndView modelAndView = new ModelAndView();
		Company company = new Company(); 
		RepairingQuotation repairingQuotation = new RepairingQuotation();
		repairingQuotation.getRepairingQuotationItems().add(new RepairingQuotationItems());
		repairingQuotation.setCompany(company);
		modelAndView.addObject("repair",repairingQuotation);
		modelAndView.addObject("user",user);
		modelAndView.setViewName("Repair");
		return modelAndView;
	}
	
	@PostMapping(value = "/repair-quotation-details")
	public ModelAndView getElectricalQuoteDetails(@ModelAttribute("repair") RepairingQuotation repairingQuotation ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		int price = 0;
		for(RepairingQuotationItems item : repairingQuotation.getRepairingQuotationItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
			
		}
		repairingQuotation.setTotal(price);
		int labourCost = repairingQuotation.getLabourCost();
		long totalIncludingAllCosts = (long) labourCost+price;
		repairingQuotation.setTotalIncludingAllCosts(totalIncludingAllCosts);
		
		Integer gst = Integer.parseInt(repairingQuotation.getGst());
		long gstCount= totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		repairingQuotation.setTotalIncludingAllCostsWithGst(totalWithGst);

		
		repairServiceImpl.saveRepairingQuote(multipartFile,repairingQuotation);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("repair", repairingQuotation);
		//view.addObject("image", multipartFile);
		view.setViewName("RepairingClientView");
		return view;	
	}
	
	@GetMapping("/generate-repair-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		RepairingQuotation repairingQuotation = (RepairingQuotation) session.getAttribute("repair");
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("repair",repairingQuotation);
	        String orderHtml = templateEngine.process("RepairPDfGeneration", context);

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
