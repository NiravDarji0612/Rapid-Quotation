package com.rapidquotation.serviceImpl;

import java.io.IOException;
import java.util.Base64;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquotation.entities.mechanical.MaintenanceQuotation;
import com.rapidquotation.repository.MaintenanceRepo;
import com.rapidquotation.service.MaintenanceService;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

	@Autowired
	private MaintenanceRepo repo;
	
	@Override
	public void saveMaintenanceQuote(MultipartFile multipartFile, MaintenanceQuotation maintenanceQuotation) {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		try {
			maintenanceQuotation.getCompany().setCompanyLogo(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repo.save(maintenanceQuotation);

	}

}
