package com.jyothigas.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.Notification;
import com.jyothigas.app.service.NotificationService;
import com.jyothigas.utils.Constant;

@Controller
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@RequestMapping(value = Constant.GET_ALL_NOTIFICATION, method = RequestMethod.GET)
	public @ResponseBody Object getAllNotification() {
		logger.debug("Getting All Notification ..");
		try {
			List<Notification> list = notificationService.getAllNotification();
			return list;
		} catch (Exception e) {
			logger.error("Error while fetching Notification List.." + e);
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
