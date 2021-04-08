package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.SoftwareDevelopmentQuote;

public interface SoftwareDevelopmentService {

	public void saveSoftDevlopmentQuote(MultipartFile file,SoftwareDevelopmentQuote quote);
	
	
}
