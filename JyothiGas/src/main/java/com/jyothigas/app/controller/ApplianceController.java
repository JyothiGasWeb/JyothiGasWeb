package com.jyothigas.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
			logger.error("Error while fetching appliances.." + e);
			AppResponse appResponse = new AppResponse();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
			e.printStackTrace();
			return appResponse;
		}

	}

	/**
	 * API for fetch all appliances
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.ADD_APPLIANCES, method = RequestMethod.POST)
	public @ResponseBody Object addAppliance(@RequestBody Appliances appliance) {
		logger.info("Fetch all Appliances..");
		try {
			Appliances applianceObj = applianceService.addAppliance(appliance);
			return applianceObj;
		} catch (Exception e) {
			logger.error("Error while Saving appliance.." + e);
			AppResponse appResponse = new AppResponse();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
			e.printStackTrace();
			return appResponse;
		}
	}

	@RequestMapping(value = Constant.ADD_APPLIANCES, method = RequestMethod.DELETE)
	public @ResponseBody Object removeAppliance(@RequestParam Integer id) {
		AppResponse appResponse = new AppResponse();
		logger.info("Fetch all Appliances..");
		try {
			boolean result = applianceService.removeAppliance(id);
			appResponse.setResult(result);
			return appResponse;
		} catch (Exception e) {
			logger.error("Error while Saving appliance.." + e);

			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			e.printStackTrace();
			return appResponse;
		}
	}

	@RequestMapping(value = Constant.GET_PRODUCT_BY_TYPE, method = RequestMethod.GET)
	public @ResponseBody Object getAppliancesByType(@RequestParam Integer connectionTypeID) {
		logger.info("Fetch all Appliances..");
		try {
			List<Appliances> appliancesList = applianceService.findAppliancesByConnectionTypeId(connectionTypeID);
			return appliancesList;
		} catch (Exception e) {
			logger.error("Error while fetching appliances.." + e);
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
