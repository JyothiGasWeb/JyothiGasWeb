package com.jyothigas.app.controller;

import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jyothigas.app.model.UserSecurityContext;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final String CLIENT_PARAMS = "client_id=rest-client&client_secret=secret";
	private static final String GRANT_GET="grant_type=password";
	private static final String GRANT_REFRESH="grant_type=refresh_token";

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public String validate() {
		return "auth/validate";
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Object login(@RequestBody UserSecurityContext user,HttpServletRequest request){
		String requestUrl=request.getRequestURL().toString().replace("/auth/login","");
		String oauthUrl=requestUrl+"/oauth/token?"+CLIENT_PARAMS+"&"+GRANT_GET+"&username="+user.getUsername()+"&password="+user.getPassword();
		try {
			RestTemplate restTemplate=new RestTemplate();
			Object obj=restTemplate.postForObject(oauthUrl, null, Object.class);
			return obj;
		} catch (Exception e) {
			HashMap<String, String> response=new HashMap<String, String>();
			response.put("status", "error");
			response.put("message", e.getMessage());
			response.put("stacktrace", Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
			return response;
		}
	}

	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public HashMap<String,String> logout(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, String> _response=new HashMap<String, String>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			  if (auth != null){
			     new SecurityContextLogoutHandler().logout(request, response, auth);
			  }
			SecurityContextHolder.getContext().setAuthentication(null);
			_response.put("status", "success");
			_response.put("message","Succesfully logged out");

		} catch (Exception e) {
			e.printStackTrace();
			_response.put("status", "error");
			_response.put("message","Unable to logout, Please try again.");
		}
		return _response;
	}

	@RequestMapping(value="/token/get",method=RequestMethod.POST)
	public Object getAccessToken(@RequestBody UserSecurityContext user,HttpServletRequest request){
		String requestUrl=request.getRequestURL().toString().replace("/auth/token/get","");
		String oauthUrl=requestUrl+"/oauth/token?"+CLIENT_PARAMS+"&"+GRANT_GET+"&username="+user.getUsername()+"&password="+user.getPassword();
		try {
			RestTemplate restTemplate=new RestTemplate();
			Object obj=restTemplate.postForObject(oauthUrl, null, Object.class);
			return obj;
		} catch (Exception e) {
			HashMap<String, String> response=new HashMap<String, String>();
			response.put("status", "error");
			response.put("message", e.getMessage());
			response.put("stacktrace", Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
			return response;
		}
	}

	@RequestMapping(value="/token/refresh",method=RequestMethod.POST)
	public Object refreshAccessToken(@RequestParam String refresh_token,HttpServletRequest request){
		String requestUrl=request.getRequestURL().toString().replace("/auth/token/refresh","");
		String oauthUrl=requestUrl+"/oauth/token?"+CLIENT_PARAMS+"&"+GRANT_REFRESH+"&refresh_token="+refresh_token;
		try {
			RestTemplate restTemplate=new RestTemplate();
			Object obj=restTemplate.postForObject(oauthUrl, null, Object.class);
			return obj;
		} catch (Exception e) {
			HashMap<String, String> response=new HashMap<String, String>();
			response.put("status", "error");
			response.put("message", e.getMessage());
			response.put("stacktrace", Arrays.toString(e.getStackTrace()));
			e.printStackTrace();
			return response;
		}
	}

}