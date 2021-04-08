package com.rapidquotation.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.lowagie.text.DocumentException;
import com.rapidquotation.entities.Company;
import com.rapidquotation.entities.QuoteItems;
import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.entities.mechanical.MechanicalDesignQuote;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.serviceImpl.MechServiceImpl;

@Controller
public class MechnicalQuoteController {

	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MechServiceImpl mechServiceImpl;
	
    @Autowired
    ServletContext servletContext;

    @Autowired
    private TemplateEngine templateEngine;
    
  
	@RequestMapping("/mechanical")
	public ModelAndView GetMechanicalView(Principal principal)
	{
		
		String username = principal.getName();
		UserRegistration user=userRepo.getUserbyUserName(username);
		
	
		ModelAndView view = new ModelAndView();
		Company company = new Company();
		MechanicalDesignQuote mech = new MechanicalDesignQuote();
		mech.getMechItems().add(new QuoteItems());
		mech.setCompany(company);
		view.addObject("mech" , mech);
		view.addObject("user",user);
		view.setViewName("Mechanical.html");
		
		
		
		return view;
	}
	
	
	
	
	@PostMapping(value = "/quotation-details")
	public ModelAndView getMechQuoteDetails(@ModelAttribute("mech") MechanicalDesignQuote mech ,HttpSession session ,
			@RequestParam("fileImage")MultipartFile multipartFile)
	{
	
		
		int price = 0;
		for(QuoteItems item : mech.getMechItems())
		{
			int quantity = item.getQuantity();
			int individualPrice = item.getPrice();
			int countPrice = quantity*individualPrice;
			price = price+countPrice;
		}
		mech.setTotal(price);
		int labourCost = mech.getLabourCost();
		int setupCost = mech.getSetupCost();
		int engineeringCost = mech.getEngineeringCost();
		int toolingAndConsumablesCost = mech.getToolingAndConsumablesCost();
		int outsideServicesAndProcessesCost = mech.getOutsideServicesAndProcessesCost();
		int maintainanceCost = mech.getMaintainanceCost();
		int transportationCost= mech.getTransportationCost();
		int packagingCost=mech.getPackagingCost();
		
		Long totalIncludingAllCosts = (long) (price+labourCost + setupCost + engineeringCost + toolingAndConsumablesCost + outsideServicesAndProcessesCost + maintainanceCost + transportationCost + packagingCost);
		mech.setTotalIncludingAllCosts(totalIncludingAllCosts);
			
		Integer gst = Integer.parseInt(mech.getGst());
		System.out.println("GST value = "+gst);
		long gstCount= totalIncludingAllCosts*gst/100;
		long totalWithGst = totalIncludingAllCosts+gstCount;
		mech.setTotalIncludingAllCostsWithGst(totalWithGst);

		mechServiceImpl.saveMechQuote(multipartFile,mech);
		
		ModelAndView view = new ModelAndView();
		session.setAttribute("Mech", mech);
		//view.addObject("image", multipartFile);
		view.setViewName("MechDesignPdfClientView");
		return view;	
}
	
	
	
	
	@GetMapping("/MechDesignPdfClientView")
	public String getQuoteFormate()
	{
		return "MechDesignPdfClientView";
	}
	
	
	@GetMapping("/generate-pdf")
	public ResponseEntity<?> getQuotePdf(HttpSession session , HttpServletRequest request , HttpServletResponse response) throws DocumentException, IOException
	{
		
		MechanicalDesignQuote mechanicalDesignQuote =(MechanicalDesignQuote) session.getAttribute("Mech");
		
		
		 WebContext context = new WebContext(request, response, servletContext);
	        context.setVariable("mech",mechanicalDesignQuote );
	        String orderHtml = templateEngine.process("MechDesignPdfGenratedFormat", context);

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
