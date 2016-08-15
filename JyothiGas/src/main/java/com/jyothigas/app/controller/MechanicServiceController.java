package com.jyothigas.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.MechanicServiceModel;
import com.jyothigas.app.service.MechanicServService;
import com.jyothigas.utils.Constant;

@Controller
public class MechanicServiceController {
	private static final Logger logger = LoggerFactory.getLogger(MechanicServiceController.class);

	@Autowired
	MechanicServService mechanicService;

	@RequestMapping(value = Constant.ADD_MECHANIC_SERVICE, method = RequestMethod.POST)
	public @ResponseBody Object addMechanicService(@RequestBody MechanicServiceModel mechServ) {
		logger.info("Save Mechanic ..");
		AppResponse appResponse = new AppResponse();
		try {
			if (mechanicService.addMechanicService(mechServ) > 0) {
				appResponse.setStatus("Success");
				appResponse.setMessage("OK");
				appResponse.setHttpErrorCode(200);
				appResponse.setOauth2ErrorCode("valid_token");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
		}
		return appResponse;
	}
	
	@RequestMapping(value = Constant.UPDATE_MECHANIC_SERVICE, method = RequestMethod.POST)
	public @ResponseBody Object updateMechanicService(@RequestBody MechanicServiceModel mechServ) {
		logger.info("Save Mechanic ..");
		AppResponse appResponse = new AppResponse();
		try {
			if (mechanicService.updateMechanicService(mechServ) > 0) {
				appResponse.setStatus("Success");
				appResponse.setMessage("OK");
				appResponse.setHttpErrorCode(200);
				appResponse.setOauth2ErrorCode("valid_token");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
		}
		return appResponse;
	}
}
