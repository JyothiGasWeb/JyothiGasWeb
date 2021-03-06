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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.ConsumerDetails;
import com.jyothigas.app.model.Register;
import com.jyothigas.app.service.ConsumerService;
import com.jyothigas.utils.Constant;
import com.jyothigas.utils.FileUploader;

@Controller
public class ConsumerController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

	@Autowired
	ConsumerService consumerService;

	@Autowired
	FileUploader uploader;

	/**
	 * API for fetch consumer details using email
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_CONSUMER_DETAILS, method = RequestMethod.POST)
	public @ResponseBody Object getConsumerDetails(@RequestBody Register register) {
		logger.info("Getting User Details..");

		try {
			ConsumerDetails consumerDetails = consumerService.fetchConsumerDetailsByEmail(register.getEmail());
			return consumerDetails;
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
			return new ResponseEntity<List<AppResponse>>(appObjList, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * API for update consumer details
	 * 
	 * @param register
	 * @return
	 * 
	 * 
	 */
	// =======> NOTE : THIS API ASSOCIATED WITH THREE CALLS <=======
	@RequestMapping(value = { Constant.UPDATE_CONSUMER, Constant.UPDATE_ADDRESS,
			Constant.UPDATE_DEALER }, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateConsumer(@RequestBody Register register) {
		logger.info("Registering the User");
		AppResponse appResponse = new AppResponse();

		try {
			int result = consumerService.updateConsumer(register);
			if (result > 0) {
				appResponse.setStatus("OK");
				appResponse.setMessage("Success");
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

	@RequestMapping(value = Constant.UPLOAD_IMAGE, method = RequestMethod.POST)
	public @ResponseBody Object uploadImage(@RequestParam("custId") Integer custId,
			@RequestParam("file") MultipartFile file) {
		AppResponse appResponse = new AppResponse();
		if (!file.isEmpty()) {
			try {
				uploader.uploadFile(custId, file, Constant.PROFILE_PIC,"");
				appResponse.setStatus("OK");
				appResponse.setMessage("Success");
				return appResponse;
			} catch (Exception e) {
				appResponse.setStatus("Error");
				appResponse.setMessage("Please try after sometime");
				e.printStackTrace();
				return appResponse;
			}
		} else {
			appResponse.setStatus("Error");
			appResponse.setMessage("Empty File");
			return appResponse;
		}
	}
}
