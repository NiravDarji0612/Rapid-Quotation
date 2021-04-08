package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.RepairingQuotation;
import com.rapidquotation.repository.RepairingRepo;
import com.rapidquotation.service.RepairingService;

@Service
public class RepairingServiceImpl implements RepairingService {

	@Autowired
	private RepairingRepo repairRepo;
	
	@Override
	public void saveRepairingQuote(MultipartFile multipartFile, RepairingQuotation repairingQuotation) {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		try {
			repairingQuotation.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repairRepo.save(repairingQuotation);
		
	}

}
