package com.jyothigas.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Dealer;
import com.jyothigas.app.service.DealerService;
import com.jyothigas.utils.Constant;

@Controller
public class DealerController {

private static final Logger logger = LoggerFactory.getLogger(ConumerController.class);
	
	@Autowired
	DealerService dealerService;
	
	@RequestMapping(value = Constant.GET_DEALER_DETAILS, method = RequestMethod.GET)
	public @ResponseBody Object getDealersDetail() {
		logger.info("Getting Dealer Details..");
		
		try {
			List<Dealer> dealer = dealerService.fetchAllDealers();
			return dealer;
		} catch (Exception e) {
			logger.error("Error While Getting User Details..");
			e.printStackTrace();
			List<AppResponse> appObjList = new ArrayList<AppResponse>();
			AppResponse appResponse = new AppResponse();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
			appObjList.add(appResponse);
			return new ResponseEntity<List<AppResponse>>(appObjList,HttpStatus.BAD_REQUEST);
		}
	}
	
}