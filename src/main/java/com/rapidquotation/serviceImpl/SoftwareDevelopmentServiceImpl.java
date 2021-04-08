package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.SoftwareDevelopmentQuote;
import com.rapidquotation.repository.SoftwareDevelopmentRepo;
import com.rapidquotation.service.SoftwareDevelopmentService;

@Service
public class SoftwareDevelopmentServiceImpl implements SoftwareDevelopmentService {

	@Autowired
	private SoftwareDevelopmentRepo softwareRepo;
	
	@Override
	public void saveSoftDevlopmentQuote(MultipartFile file, SoftwareDevelopmentQuote quote) {
		
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			quote.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		softwareRepo.save(quote);
		

	}

}
