package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.FurnitureQuote;
import com.rapidquotation.repository.FunitureQuoteRepo;
import com.rapidquotation.service.FurnitureService;

@Service
public class FurnitureServiceImpl implements FurnitureService {

	@Autowired
	private FunitureQuoteRepo furnitureRepo;
 	
	@Override
	public void saveFurnitureQuote(MultipartFile file, FurnitureQuote quote) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			quote.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		furnitureRepo.save(quote);
		
	}

}
