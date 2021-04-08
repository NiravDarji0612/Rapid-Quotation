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
import com.rapidquotation.entities.ElectricalQuoteItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.ElectricalQuote;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.ElectricalServiceImpl;

@Controller
public class ElectricalQuoteController {
	
	@Autowired
	private ElectricalServiceImpl electricalServiceImpl;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	
	@RequestMapping("/Electrical")
	public ModelAndView getElectricalView(Principal principal)
	{
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		ModelAndView view = new ModelAndView();
		Company company = new Company();
		ElectricalQuote electricalQuote = new ElectricalQuote();
		electricalQuote.getElectricalItems().add(new ElectricalQuoteItems());
		electricalQuote.setCompany(company);
		view.addObject("electrical", electricalQuote);
		view.addObject("user",user);
		view.setViewName("Electrical");
		return view;
	}
	
	@PostMapping(value = "/electrical-quotation-details")
	public ModelAndView getElectricalQuoteDetails(@ModelAttribute("electrical") ElectricalQuote electricalQuote ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		int price = 0;
		for(ElectricalQuoteItems item : electricalQuote.getElectricalItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
		}
		
		electricalQuote.setTotal(price);
		
		int labourCost = electricalQuote.getLabourCost();
		int transportationCost = electricalQuote.getTransportationCost();
		int packagingCost = electricalQuote.getPackagingCost();
		int maintainanceCost = electricalQuote.getMaintainanceCost();
		
		long totalIncludingAllCosts = (long) labourCost+transportationCost+packagingCost+maintainanceCost+price;
		electricalQuote.setTotalIncludingAllCosts(totalIncludingAllCosts);
		
		Integer gst =Integer.parseInt(electricalQuote.getGst());
		long gstCount= totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		electricalQuote.setTotalIncludingAllCostsWithGst(totalWithGst);

		electricalServiceImpl.saveMechQuote(multipartFile,electricalQuote);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("electrical", electricalQuote);
		//view.addObject("image", multipartFile);
		view.setViewName("ElectricalQuotationClientView");
		return view;	
	}
	
	@GetMapping("/generate-electrical-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		ElectricalQuote electricalQuote =(ElectricalQuote) session.getAttribute("electrical");
		
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("electrical",electricalQuote );
	        String orderHtml = templateEngine.process("ElectricalClientPdfView", context);

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
