package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.StationeriesQuote;
import com.rapidquotation.repository.StationeryQuoteRepo;
import com.rapidquotation.service.StationeryService;

@Service
public class StationeryServiceImpl implements StationeryService {

	
	@Autowired
	private StationeryQuoteRepo stationeriesRepo;
	
	@Override
	public void saveStationeryQuote(MultipartFile file, StationeriesQuote quote) {
		
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			quote.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stationeriesRepo.save(quote);
		

	}

}
