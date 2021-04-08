package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.ElectricalQuote;

public interface ElectricalService {
	
	public void saveMechQuote(MultipartFile file,ElectricalQuote quote);

}
