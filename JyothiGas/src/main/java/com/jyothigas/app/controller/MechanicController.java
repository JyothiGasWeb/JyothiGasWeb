package com.jyothigas.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Mechanic;
import com.jyothigas.app.service.MechanicService;
import com.jyothigas.utils.Constant;

/**
 * Handles requests for the Mechanic
 */
@Controller
public class MechanicController {
	private static final Logger logger = LoggerFactory.getLogger(MechanicController.class);

	@Autowired
	MechanicService mechanicService;
	
	@RequestMapping(value = Constant.GET_MECHANIC_BY_DEALER_ID, method = RequestMethod.POST)
	public @ResponseBody Object getMechanicByDealerId(@RequestBody Mechanic mechanic) {
		logger.info("Getting Mechanic List..");
		try {
			List<Mechanic> mechanicList = mechanicService.getMechanicByDealerId(mechanic.getDealerId());
			return mechanicList;
		} catch (Exception e) {
			logger.error("Error While Getting Mechanic Details..");
			e.printStackTrace();
			List<AppResponse> appObjList = new ArrayList<AppResponse>();
			AppResponse appResponse = new AppResponse();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405); 
			appObjList.add(appResponse);
			return new ResponseEntity<List<AppResponse>>(appObjList,HttpStatus.BAD_REQUEST);
		}
	}
}
