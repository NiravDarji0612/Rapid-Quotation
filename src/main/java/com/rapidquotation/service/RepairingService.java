package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.RepairingQuotation;

public interface RepairingService {

	public void saveRepairingQuote(MultipartFile multipartFile,RepairingQuotation repairingQuotation);
	
}
