package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.DecorationQuote;
import com.rapidquotation.repository.DecorationQuoteRepo;
import com.rapidquotation.service.DecorationService;

@Service
public class DecorationServiceImpl implements DecorationService {

	@Autowired
	private DecorationQuoteRepo decorationRepo;
	
	@Override
	public void saveDecorationQuote(MultipartFile file, DecorationQuote decorationQuote) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			decorationQuote.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		decorationRepo.save(decorationQuote);
		
		
	}

}
