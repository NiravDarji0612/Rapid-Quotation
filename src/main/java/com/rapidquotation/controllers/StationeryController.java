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
import com.rapidquotation.entities.StationeriesItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.StationeriesQuote;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.StationeryServiceImpl;

@Controller
public class StationeryController {
	
	@Autowired
	private StationeryServiceImpl stationeryImpl;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@RequestMapping("/stationery")
	public ModelAndView getStationeryView(Principal principal)
	{
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		ModelAndView view = new ModelAndView();
		Company company = new Company();
		StationeriesQuote stationeriesQuote = new StationeriesQuote();
		stationeriesQuote.getStationeriesItems().add(new StationeriesItems());
		stationeriesQuote.setCompany(company);
		view.addObject("stationery", stationeriesQuote);
		view.addObject("user",user);
		view.setViewName("Stationeries");
		return view;
	}
	
	
	@PostMapping(value = "/stationery-quotation-details")
	public ModelAndView getSoftwareDevelopmentQuoteDetails(@ModelAttribute("stationery") StationeriesQuote stationeriesQuote ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		int price = 0;
		for(StationeriesItems item : stationeriesQuote.getStationeriesItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
			
		}
		stationeriesQuote.setTotal(price);
		
		int labourCost = stationeriesQuote.getLabourCost();
		int transportationCost = stationeriesQuote.getTransportationCost();
		int packagingCost = stationeriesQuote.getPackagingCost();
		
		long totalIncludingAllCosts = (long) labourCost+transportationCost+packagingCost+price;
		stationeriesQuote.setTotalIncludingAllCosts(totalIncludingAllCosts);
		
		Integer gst = Integer.parseInt(stationeriesQuote.getGst());
		long gstCount= totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		stationeriesQuote.setTotalIncludingAllCostsWithGst(totalWithGst);

		stationeryImpl.saveStationeryQuote(multipartFile,stationeriesQuote);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("stationery",stationeriesQuote);
		//view.addObject("image", multipartFile);
		view.setViewName("StationeryQuoteClientView");
		return view;	
	}
	
	
	@GetMapping("/generate-stationery-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		StationeriesQuote stationeriesQuote = (StationeriesQuote) session.getAttribute("stationery");
		
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("stationery",stationeriesQuote);
	        String orderHtml = templateEngine.process("StationeryPDFGeneration", context);

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
