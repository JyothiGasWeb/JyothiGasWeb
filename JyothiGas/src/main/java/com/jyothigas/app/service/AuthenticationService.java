package com.jyothigas.app.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jyothigas.app.model.UserSecurityContext;
import com.jyothigas.utils.PasswordProtector;

@Component
public class AuthenticationService implements AuthenticationProvider {
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println(authentication.getName()+":"+authentication.getPrincipal()+":"+authentication.getCredentials());
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserSecurityContext user = null;

//		String accessToken=null;
//		if(RequestContextHolder.getRequestAttributes()!=null){
//			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//			accessToken=request.getParameter("access_token");
//		}

//		if(accessToken!=null&&accessToken.trim().length()!=0){
//			user=userDetailsService.getUserDetailsByAccessToken(accessToken);
//			return
//		}

		 user = (UserSecurityContext) userDetailsService.loadUserByUsername(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (password == null || !PasswordProtector.encrypt(password).equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

//		if(accessToken!=null && !"".equalsIgnoreCase(accessToken)){
//			userDetailsService.updateRegistrationToken(accessToken, username);
//		}

		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	public static String getLoggedInUserName() {
		String username = null;
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null)
					username = context.getUsername();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}
}