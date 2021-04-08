
package com.rapidquotation.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rapidquotation.entities.UserRegistration;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private UserRegistration userRegistration;

	public CustomUserDetails(UserRegistration userRegistration) {
		this.userRegistration = userRegistration;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return userRegistration.getPassword();
	}

	@Override
	public String getUsername() {
		return userRegistration.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
