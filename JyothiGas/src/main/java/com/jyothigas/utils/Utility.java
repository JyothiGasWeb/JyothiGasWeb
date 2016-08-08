package com.jyothigas.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Utility {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("\"Hello\"");
		String baseURL = "http://sms.scubedigi.com/api.php?username=JyothiGas&password=162230";
		baseURL = baseURL + "&to=8050616972";
		baseURL = baseURL + "&from=VMBASB";
		baseURL = baseURL + "&message=\"Hello\"";
		System.out.println(baseURL);
		ResponseEntity<String> response = restTemplate.getForEntity(baseURL, String.class);
		System.out.println(response.getStatusCode());
	}

}
