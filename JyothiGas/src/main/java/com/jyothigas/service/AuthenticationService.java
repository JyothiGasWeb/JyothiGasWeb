package com.jyothigas.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationProvider {

	public Authentication authenticate(final Authentication authentication, User user)
			 {
		final String username = authentication.getName();
		final String password = authentication.getCredentials().toString();
		if (username != null && !username.trim().equalsIgnoreCase("")
				&& password != null && !password.trim().equalsIgnoreCase("")) {
			boolean isValidUser = false;
			/*if (authenticationEnabled) {
				
			} else {
				isValidUser = false;
				final List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
				
				return new UsernamePasswordAuthenticationToken(username, password,
						grantedAuthority);
			}
			logger.debug("Is user valid? : " + isValidUser);
			if (isValidUser) {
				return true;
			}*/
		}
		return null;
	}

	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}
}
