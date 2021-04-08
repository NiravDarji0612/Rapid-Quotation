package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.Company;
import com.rapidquotation.entities.mechanical.DecorationQuote;

public interface DecorationService {

	public void saveDecorationQuote(MultipartFile file,DecorationQuote quote);
	
}
