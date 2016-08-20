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

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.ConsumerConnection;
import com.jyothigas.app.model.OrderDetail;
import com.jyothigas.app.service.BookingService;
import com.jyothigas.utils.Constant;

@Controller
public class BookingController {

	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	BookingService bookingService;

	/**
	 * API for Booking connection as well as appliances
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = { Constant.BOOK_CONNECTION, Constant.BOOK_APPLIANCES }, method = RequestMethod.POST)
	public @ResponseBody Object insertBookingCylinder(@RequestBody ConsumerConnection booking) {
		logger.info("insertBookingCylinder....");
		AppResponse appResponse = new AppResponse();
		try {
			ConsumerConnection result = bookingService.insertBookingCylinder(booking);
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

	/**
	 * API for updateBookingCylinder
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.UPDATE_BOOKING, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateBookingCylinder(@RequestBody ConsumerConnection booking) {
		logger.info("updateBookingCylinder....");
		AppResponse appResponse = new AppResponse();
		try {
			int result = bookingService.updateBookingCylinderStatus(booking);
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

	/**
	 * API for fetch booking details using status
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_BOOKING_BY_STATUS, method = RequestMethod.POST)
	public @ResponseBody Object fetchBookingsByStatus(@RequestBody ConsumerConnection booking) {
		logger.info("Getting User Details..");

		try {
			List<ConsumerConnection> bookingList = bookingService.fetchBookingsByStatus(booking);
			return bookingList;
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
	 * API for fetch booking details using consumer
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_BOOKING_BY_CONSUMER, method = RequestMethod.POST)
	public @ResponseBody Object findByConsumerId(@RequestBody ConsumerConnection booking) {
		logger.info("Getting User Details..");

		try {
			List<ConsumerConnection> bookingList = bookingService.findByConsumerId(booking);
			return bookingList;
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
	 * API for fetch booking details using consumer
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_BOOKING_BY_CONNECTION_TYPE, method = RequestMethod.POST)
	public @ResponseBody Object findByConnectionTypeId(@RequestBody ConsumerConnection booking) {
		logger.info("Getting User Details..");

		try {
			List<ConsumerConnection> bookingList = bookingService.findByConnectionTypeId(booking);
			return bookingList;
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
	 * API for fetch booking details using consumer
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_ALL_BOOKINGS, method = RequestMethod.GET)
	public @ResponseBody Object findAllBookings() {
		logger.info("Getting User Details..");

		try {
			List<ConsumerConnection> bookingList = bookingService.findAllBookings();
			return bookingList;
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
	 * API for fetch booking details using consumer
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.GET_IN_PROGRESS_BOOKING, method = RequestMethod.POST)
	public @ResponseBody Object findInProgressOrderDetail(@RequestParam Integer bookingId) {
		logger.info("Getting User Details..");
		try {
			OrderDetail bookingDetail = bookingService.findInProgressOrderDetail(bookingId);
			return bookingDetail;
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

}
