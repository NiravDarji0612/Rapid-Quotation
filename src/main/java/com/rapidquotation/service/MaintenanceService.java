package com.rapidquotation.service;

import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.MaintenanceQuotation;

public interface MaintenanceService {
	
	public void saveMaintenanceQuote(MultipartFile multipartFile,MaintenanceQuotation maintenanceQuotation);

}
