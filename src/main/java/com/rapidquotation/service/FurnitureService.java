package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.FurnitureQuote;

public interface FurnitureService {

	public void saveFurnitureQuote(MultipartFile file,FurnitureQuote quote);
	
}
