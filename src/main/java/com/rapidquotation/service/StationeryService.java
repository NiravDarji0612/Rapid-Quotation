package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.StationeriesQuote;

public interface StationeryService {

	public void saveStationeryQuote(MultipartFile file,StationeriesQuote quote);
	
}
