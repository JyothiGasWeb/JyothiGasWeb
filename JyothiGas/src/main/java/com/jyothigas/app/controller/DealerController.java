package com.jyothigas.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Dealer;
//import com.jyothigas.app.service.DealerService_del;
import com.jyothigas.utils.Constant;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DealerController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);




    @RequestMapping(value = Constant.REGISTER_DEALER, method = RequestMethod.POST)
    public @ResponseBody
    AppResponse registerDealer(@RequestBody Dealer dealer) {
        logger.info("Registering the User");
        AppResponse appResponse = new AppResponse();
//
//        try {
//            if (!dealerService.verifyEmail(dealer.getDealer_email())) {
//                appResponse.setMessage("The Email Id you provided is already exist in our system. Please use other email id.");
//                appResponse.setStatus("Fail");
//                appResponse.setHttpErrorCode(405);
//            } else if (!dealerService.verifyMobileNO(dealer.getDealer_contact_no())) {
//                appResponse.setMessage("The Mobile Number you provided is already exist in our system. Please use other Mobile No.");
//                appResponse.setStatus("Fail");
//                appResponse.setHttpErrorCode(405);
//            } else {
//                int result = dealerService.registerDealer(dealer);
//                if (result > 0) {
//                    appResponse.setStatus("OK");
//                    appResponse.setMessage("Success");
//                    appResponse.setHttpErrorCode(200);
//                    appResponse.setOauth2ErrorCode("valid_token");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            appResponse.setStatus("Error");
//            appResponse.setMessage("Please try after sometime");
//            appResponse.setHttpErrorCode(405);
//            appResponse.setOauth2ErrorCode("invalid_token");
//
//        }
        return appResponse;
    }

}
