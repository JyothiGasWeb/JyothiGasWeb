/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.controller;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Booking;
import com.jyothigas.app.service.DealerBookingService;
import com.jyothigas.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = {Constant.BOOK_DEALER_APPLIANCES,Constant.BOOK_DISTRIBUTOR_APPLIANCES,
        Constant.BOOK_DEALER_REFILL,Constant.BOOK_DISTRIBUTOR_REFILL}, method = RequestMethod.POST)
    public @ResponseBody
    Object insertProductBooking(@RequestBody Booking booking) {
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


}
