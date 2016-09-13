package com.jyothigas.app.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OauthTestController {
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUserName(Principal principal) {
		return "passed :: " + principal.getName();
	}

	@RequestMapping(value = "/admin/test", method = RequestMethod.GET)
	public String adminTest(Principal principal) {
		return "passed :: " + principal.getName();
	}

	@RequestMapping(value = "/dealer/test", method = RequestMethod.GET)
	public String dealerTest(Principal principal) {
		return "passed :: " + principal.getName();
	}

	@RequestMapping(value = "/consumer/test", method = RequestMethod.GET)
	public String consumerTest(Principal principal) {
		return "passed :: " + principal.getName();
	}

	@RequestMapping(value = "/distributor/test", method = RequestMethod.GET)
	public String distributorTest(Principal principal) {
		return "passed :: " + principal.getName();
	}

}
