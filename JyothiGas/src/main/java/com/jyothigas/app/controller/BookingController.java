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
import com.jyothigas.app.model.Booking;
import com.jyothigas.app.model.Reports;
import com.jyothigas.app.service.BookingService;
import com.jyothigas.utils.Constant;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

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
     *
     */
    /* =======> NOTE : THIS API ASSOCIATED WITH THREE CALLS <======= */
    @RequestMapping(value = {Constant.BOOK_CONNECTION, Constant.BOOK_APPLIANCES,
        Constant.BOOK_REFILL}, method = RequestMethod.POST)
    public @ResponseBody
    Object insertProductBooking(@RequestBody Booking booking) {
        logger.info("insertBookingCylinder....");
        AppResponse appResponse = new AppResponse();
        try {
            Booking result = bookingService.bookProduct(booking);
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
     * API for updateBooking
     *
     * @param register
     * @return
     */
    @RequestMapping(value = Constant.UPDATE_BOOKING, method = RequestMethod.POST)
    public @ResponseBody
    AppResponse updateBookingCylinder(@RequestBody Booking booking) {
        logger.info("updateBookingCylinder....");
        AppResponse appResponse = new AppResponse();
        try {
            int result = bookingService.updateProductBooking(booking);
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
    public @ResponseBody
    Object fetchBookingsByStatus(@RequestBody Booking booking) {
        logger.info("Getting User Details..");

        try {
            List<Booking> bookingList = bookingService.fetchBookingsByStatus(booking);
            return bookingList;
        } catch (Exception e) {
            logger.error("Error While Getting User Details.." + e);
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
    public @ResponseBody
    Object findByConsumerId(@RequestBody Booking booking) {
        logger.info("Getting User Details..");

        try {
            List<Booking> bookingList = bookingService.findByConsumerId(booking);
            return bookingList;
        } catch (Exception e) {
            logger.error("Error While Getting User Details.." + e);
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
    public @ResponseBody
    Object findByConnectionTypeId(@RequestBody Booking booking) {
        logger.info("Getting User Details..");

        try {
            List<Booking> bookingList = bookingService.findByConnectionTypeId(booking);
            return bookingList;
        } catch (Exception e) {
            logger.error("Error While Getting User Details.." + e);
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
    public @ResponseBody
    Object findAllBookings() {
        logger.info("Getting User Details..");

        try {
            List<Booking> bookingList = bookingService.findAllBookings();
            return bookingList;
        } catch (Exception e) {
            logger.error("Error While Getting User Details.." + e);
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
     * API for fetch booking details In financial Year
     *
     * @param year
     * @return
     */
    @RequestMapping(value = Constant.GET_FY_CYLINDER_BOOKING, method = RequestMethod.GET)
    public @ResponseBody
    Object findCylinderBookedFY(@RequestParam Integer year) {
        logger.info("Getting User Details..");
        AppResponse appResponse = new AppResponse();
        try {
            int bookingCount = bookingService.findCylinderBookedFY(year);
            appResponse.setStatus("OK");
            appResponse.setResult(bookingCount);
            return appResponse;
        } catch (Exception e) {
            logger.error("Error While Getting User Details.." + e);
            e.printStackTrace();
            List<AppResponse> appObjList = new ArrayList<AppResponse>();

            appResponse.setStatus("Error");
            appResponse.setMessage("Please try after sometime");
            appResponse.setHttpErrorCode(405);
            appResponse.setOauth2ErrorCode("invalid_token");
            appObjList.add(appResponse);
            return new ResponseEntity<List<AppResponse>>(appObjList, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API for fetch booking details In financial Year
     *
     * @param year
     * @return
     */
    @RequestMapping(value = Constant.GET_FY_CYLINDER_BOOKING_CUST, method = RequestMethod.GET)
    public @ResponseBody
    Object findCylinderBookedCustomerFY(@RequestParam Integer year) {
        logger.info("Getting User Details..");
        AppResponse appResponse = new AppResponse();
        try {
             Map<String, Integer> bookingCount = bookingService.cylinderBookForFY(year);
            appResponse.setStatus("OK");
            appResponse.setResult(bookingCount);
            return appResponse;
        } catch (Exception e) {
            logger.error("Error While Getting User Details.." + e);
            e.printStackTrace();
            List<AppResponse> appObjList = new ArrayList<AppResponse>();

            appResponse.setStatus("Error");
            appResponse.setMessage("Please try after sometime");
            appResponse.setHttpErrorCode(405);
            appResponse.setOauth2ErrorCode("invalid_token");
            appObjList.add(appResponse);
            return new ResponseEntity<List<AppResponse>>(appObjList, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API for fetch booking details In financial Year
     *
     * @param year
     * @return
     */
    @RequestMapping(value = Constant.GET_FY_CYLINDER_SOLD, method = RequestMethod.GET)
    public @ResponseBody
    Object findCylinderSoldFY(@RequestParam Integer year, @RequestParam String connectionType) {
        logger.info("Getting Sold Cylinder Details..");
        AppResponse appResponse = new AppResponse();
        try {
            int bookingCount = bookingService.findCylinderSoldFY(year, connectionType);
            appResponse.setStatus("OK");
            appResponse.setResult(bookingCount);
            return appResponse;
        } catch (Exception e) {
            logger.error("Error While Getting Booking Details.." + e);
            e.printStackTrace();
            List<AppResponse> appObjList = new ArrayList<AppResponse>();
            appResponse.setStatus("Error");
            appResponse.setMessage("Please try after sometime");
            appResponse.setHttpErrorCode(405);
            appResponse.setOauth2ErrorCode("invalid_token");
            appObjList.add(appResponse);
            return new ResponseEntity<List<AppResponse>>(appObjList, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = Constant.GET_SALES_REPORT_DEALER, method = RequestMethod.GET)
    public @ResponseBody
    Object salesReportForDealer(@RequestParam Integer dealerId) {
        logger.info("Getting Sold Cylinder Details..");
        AppResponse appResponse = new AppResponse();
        try {
        	List<Reports> reportMap = bookingService.salesReportForDealer(dealerId);
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
}
