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
import com.rapidquotation.entities.DecorationItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.DecorationQuote;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.DecorationServiceImpl;

@Controller
public class DecorationController {
	
	
	@Autowired
	private DecorationServiceImpl decorationServiceImpl;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@RequestMapping("/decoration")
	public ModelAndView getDecorationView(Principal principal)
	{	
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		
		ModelAndView view = new ModelAndView();
		Company company = new Company();
		DecorationQuote decoration = new DecorationQuote();
		decoration.getDecorationItems().add(new DecorationItems());
		decoration.setCompany(company);
		view.addObject("decoration",decoration);
		view.addObject("user",user);
		view.setViewName("Decoration");
		return view;
	}
	
	@PostMapping(value = "/decoration-quotation-details")
	public ModelAndView getDecorationQuoteDetails(@ModelAttribute("decoration") DecorationQuote decorationQuote ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
		
		
		
		
		int price = 0;
		for(DecorationItems item : decorationQuote.getDecorationItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
		}
		decorationQuote.setTotal(price);
		
		int labourCost = decorationQuote.getLabourCost();
		
		long totalIncludingAllCosts = (long) labourCost+price;
		
		decorationQuote.setTotalIncludingAllCosts(totalIncludingAllCosts);
		
		Integer gst=Integer.parseInt(decorationQuote.getGst());
		long gstCount = totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		decorationQuote.setTotalIncludingAllCostsWithGst(totalWithGst);
		
		decorationServiceImpl.saveDecorationQuote(multipartFile,decorationQuote);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("decoration", decorationQuote);
		//view.addObject("image", multipartFile);
		view.setViewName("DecorationQuotationClientView");
		return view;	
	}
	
	@GetMapping("/generate-decoration-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		DecorationQuote decorationQuote = (DecorationQuote) session.getAttribute("decoration");
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("decoration",decorationQuote);
	        String orderHtml = templateEngine.process("DecorationPDFGeneration", context);

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
