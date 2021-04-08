package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.MechanicalDesignQuote;
import com.rapidquotation.repository.MechRepo;
import com.rapidquotation.service.MechService;


@Service
public class MechServiceImpl implements MechService {

	@Autowired
	private MechRepo mechRepo;
	
	@Override
	public void saveMechQuote(MultipartFile file,MechanicalDesignQuote quote) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			quote.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mechRepo.save(quote);
	}

}
