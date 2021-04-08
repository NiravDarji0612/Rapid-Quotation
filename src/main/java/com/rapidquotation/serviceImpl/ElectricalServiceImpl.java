package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.ElectricalQuote;
import com.rapidquotation.repository.ElectricalRepo;
import com.rapidquotation.service.ElectricalService;

@Service
public class ElectricalServiceImpl implements ElectricalService {

	
	@Autowired
	private ElectricalRepo electricalRepo;
	
	@Override
	public void saveMechQuote(MultipartFile file, ElectricalQuote quote) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			quote.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		electricalRepo.save(quote);
		
		
		
	}

}
