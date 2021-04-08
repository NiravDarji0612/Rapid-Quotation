package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.MechanicalDesignQuote;

public interface MechService {
	
	public void saveMechQuote(MultipartFile file,MechanicalDesignQuote quote);

}
