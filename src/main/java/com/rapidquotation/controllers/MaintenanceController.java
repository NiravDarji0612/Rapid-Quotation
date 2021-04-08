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
import com.rapidquotation.entities.MaintenanceQuotationItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.DecorationQuote;
import com.rapidquotation.entities.mechanical.MaintenanceQuotation;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.DecorationServiceImpl;
import com.rapidquotation.serviceImpl.MaintenanceServiceImpl;

@Controller
public class MaintenanceController {

	@Autowired
	private MaintenanceServiceImpl maintenanceServiceImpl;
	
//	@Autowired
//	private UserRepo userRepo;
//	
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@RequestMapping("/maintenance")
	public ModelAndView getMaintenanceView(Principal principal) {
		
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		
		ModelAndView modelAndView = new ModelAndView();
		Company company = new Company(); 
		MaintenanceQuotation maintenanceQuotation = new MaintenanceQuotation();
		maintenanceQuotation.getMaintenanceQuotationItems().add(new MaintenanceQuotationItems());
		maintenanceQuotation.setCompany(company);
		modelAndView.addObject("maintenance",maintenanceQuotation);
		modelAndView.addObject("user",user);
		modelAndView.setViewName("Maintenance");
		return modelAndView;
	}
	
	
	@PostMapping(value = "/maintenance-quotation-details")
	public ModelAndView getMaintenanceQuoteDetails(@ModelAttribute("maintenance") MaintenanceQuotation maintenanceQuotation ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		
		int price = 0;
		for(MaintenanceQuotationItems item : maintenanceQuotation.getMaintenanceQuotationItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
		}
		maintenanceQuotation.setTotal(price);
		int labourCost = maintenanceQuotation.getLabourCost();
		long totalIncludingAllCosts = (long) labourCost+price;
		maintenanceQuotation.setTotalIncludingAllCosts(totalIncludingAllCosts);
		Integer gst=Integer.parseInt(maintenanceQuotation.getGst());
		long gstCount = totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		maintenanceQuotation.setTotalIncludingAllCostsWithGst(totalWithGst);
		
		maintenanceServiceImpl.saveMaintenanceQuote(multipartFile,maintenanceQuotation);
		ModelAndView view = new ModelAndView();
		session.setAttribute("maintenance", maintenanceQuotation);
		view.setViewName("MaintenanceQuotationClientView");
		return view;	
	}
	
	
	@GetMapping("/generate-maintenance-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		MaintenanceQuotation maintenanceQuotation = (MaintenanceQuotation) session.getAttribute("maintenance");
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("maintenance",maintenanceQuotation);
	        String orderHtml = templateEngine.process("MaintenancePDFGeneration", context);

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
