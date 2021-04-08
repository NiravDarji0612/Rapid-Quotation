
package com.rapidquotation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rapidquotation.entities.UserRegistration;
import com.rapidquotation.repository.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserRegistration user = userRepo.getUserbyUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("could not found user!!");
		}

		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}

}
