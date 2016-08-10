package com.jyothigas.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Appliances;
import com.jyothigas.app.service.ApplianceService;
import com.jyothigas.utils.Constant;

@Controller
public class ApplianceController {
	private static final Logger logger = LoggerFactory.getLogger(ApplianceController.class);

	@Autowired
	ApplianceService applianceService;
	
	/**
	 * API for fetch all appliances
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_APPLIANCES, method = RequestMethod.GET)
	public @ResponseBody Object getAppliances() {
		logger.info("Fetch all Appliances..");
		try {
			List<Appliances> appliancesList = applianceService.fetchAllAppliance();
			return appliancesList;
		} catch (Exception e) {
			logger.error("Error while fetching appliances.."+e);
			AppResponse appResponse = new AppResponse();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
			e.printStackTrace();
			return appResponse;
		}
		
	}
}
