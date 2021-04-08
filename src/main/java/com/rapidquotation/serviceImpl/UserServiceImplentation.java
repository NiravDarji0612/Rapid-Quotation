package com.rapidquotation.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.repository.UserRepo;
import com.rapidquotation.service.UserService;


@Service
public class UserServiceImplentation implements UserService {

	@Autowired
	private UserRepo repo;
	
	@Override
	public void create(UserRegistration register) {
		
		repo.save(register);
		
	}

	public UserRegistration findbyEmailAndPassword(String email , String password)
	{
		return repo.findByEmailAndPassword(email , password);
	}



}
