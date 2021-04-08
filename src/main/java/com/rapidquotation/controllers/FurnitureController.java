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
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.FurnitureQuote;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.FurnitureServiceImpl;

@Controller
public class FurnitureController {
	
	
	@Autowired
	private FurnitureServiceImpl furnitureServiceImpl;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@RequestMapping("/furniture")
	public ModelAndView getFurnitureView(Principal principal)
	{
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		
		ModelAndView view = new ModelAndView();
		Company company = new Company();
		FurnitureQuote furnitureQuote = new FurnitureQuote();
		furnitureQuote.getFurnitureItems().add(new FurnitureItems());
		furnitureQuote.setCompany(company);
		view.addObject("furniture", furnitureQuote);
		view.addObject("user",user);
		view.setViewName("furniture");
		return view;
	}
	
	@PostMapping(value = "/furniture-quotation-details")
	public ModelAndView getElectricalQuoteDetails(@ModelAttribute("furniture") FurnitureQuote furnitureQuote ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		int price = 0;
		for(FurnitureItems item : furnitureQuote.getFurnitureItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
			
		}
		furnitureQuote.setTotal(price);
		int labourCost = furnitureQuote.getLabourCost();
		long totalIncludingAllCosts = (long) labourCost+price;
		furnitureQuote.setTotalIncludingAllCosts(totalIncludingAllCosts);
		
		Integer gst = Integer.parseInt(furnitureQuote.getGst());
		long gstCount= totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		furnitureQuote.setTotalIncludingAllCostsWithGst(totalWithGst);

		
		furnitureServiceImpl.saveFurnitureQuote(multipartFile,furnitureQuote);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("furniture", furnitureQuote);
		//view.addObject("image", multipartFile);
		view.setViewName("FurnitureQuoteClientView");
		return view;	
	}
	
	@GetMapping("/generate-furniture-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		FurnitureQuote furnitureQuote = (FurnitureQuote) session.getAttribute("furniture");
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("furniture",furnitureQuote );
	        String orderHtml = templateEngine.process("FurniturePDfGeneration", context);

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
