/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.controller;

import com.jyothigas.app.entity.DealerBookingEntity;
import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Booking;
import com.jyothigas.app.model.Reports;
import com.jyothigas.app.service.DealerBookingService;
import com.jyothigas.utils.Constant;

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

/**
 *
 * @author rprashar
 */
@Controller
public class DealerBookingController {

	@Autowired
	DealerBookingService dealerBookingService;
	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

	@RequestMapping(value = { Constant.BOOK_DEALER_APPLIANCES, Constant.BOOK_DISTRIBUTOR_APPLIANCES,
			Constant.BOOK_DEALER_REFILL, Constant.BOOK_DISTRIBUTOR_REFILL }, method = RequestMethod.POST)
	public @ResponseBody Object insertProductBooking(@RequestBody Booking booking) {
		logger.info("insertBookingCylinder....");
		AppResponse appResponse = new AppResponse();
		try {
			Booking result = dealerBookingService.bookProduct(booking);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appResponse.setHttpErrorCode(405);
			appResponse.setOauth2ErrorCode("invalid_token");
			return appResponse;
		}
	}

	// Purchase Report for both Dealer as well as Distributer
	@RequestMapping(value = Constant.GET_PURCHASE_REPORT, method = RequestMethod.GET)
	public @ResponseBody Object getPurchaseReport(@RequestParam Integer year, @RequestParam Integer bookToId) {
		logger.info("Getting Sold Cylinder Details..");
		AppResponse appResponse = new AppResponse();
		try {
			List<Reports> reportMap = dealerBookingService.getPurchaseReport(year, bookToId);
			return reportMap;
		} catch (Exception e) {
			logger.error("Error While Getting Booking Details.." + e);
			e.printStackTrace();
			List<AppResponse> appObjList = new ArrayList<AppResponse>();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appObjList.add(appResponse);
			return new ResponseEntity<List<AppResponse>>(appObjList, HttpStatus.BAD_REQUEST);
		}
	}

	// Report for Admin - Financial Year Booking month wise
	@RequestMapping(value = Constant.GET_FY_REPORT, method = RequestMethod.GET)
	public @ResponseBody Object bookingInFY(@RequestParam Integer year, @RequestParam Integer month) {
		logger.info("Getting Sold Cylinder Details..");
		AppResponse appResponse = new AppResponse();
		try {
			List<DealerBookingEntity> bookingList = dealerBookingService.bookingInFY(year, month);
			return bookingList;
		} catch (Exception e) {
			logger.error("Error While Getting Booking Details.." + e);
			e.printStackTrace();
			List<AppResponse> appObjList = new ArrayList<AppResponse>();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
			appObjList.add(appResponse);
			return new ResponseEntity<List<AppResponse>>(appObjList, HttpStatus.BAD_REQUEST);
		}
	}

}
