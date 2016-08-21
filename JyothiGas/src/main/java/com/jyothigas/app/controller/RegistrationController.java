package com.jyothigas.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.model.ForgotPassword;
import com.jyothigas.app.model.LoginRequest;
import com.jyothigas.app.model.Mail;
import com.jyothigas.app.model.OTP;
import com.jyothigas.app.model.Register;
import com.jyothigas.app.model.SMS;
import com.jyothigas.app.service.EmailService;
import com.jyothigas.app.service.OTPService;
import com.jyothigas.app.service.RegistrationService;
import com.jyothigas.app.service.SMSService;
import com.jyothigas.utils.Constant;
import com.jyothigas.utils.PasswordProtector;

@Controller
public class RegistrationController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	RegistrationService registrationService;

	@Autowired
	OTPService otpService;

	@Autowired
	SMSService smsService;

	@Autowired
	EmailService emailService;

	/**
	 * API for register
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.REGISTER, method = RequestMethod.POST)
	public @ResponseBody AppResponse registerUser(@RequestBody Register register) {
		logger.info("Registering the User");
		AppResponse appResponse = new AppResponse();

		try {
			if (!registrationService.verifyEmail(register.getEmail())) {
				appResponse.setMessage("The Email Id you provided is already exiting in our system. Please use other email id.");
				appResponse.setStatus("Fail");
				appResponse.setHttpErrorCode(405);
			} else if (!registrationService.verifyMobileNO(register.getContactNo())) {
				appResponse.setMessage("The Mobile Number you provided is already exiting in our system. Please use other Mobile No.");
				appResponse.setStatus("Fail");
				appResponse.setHttpErrorCode(405);
			} else {
				int result = registrationService.registerUser(register);
				if (result > 0) {
					appResponse.setStatus("OK");
					appResponse.setMessage("Success");
					appResponse.setHttpErrorCode(200);
					appResponse.setOauth2ErrorCode("valid_token");
				}
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
	 * API for get existing OTP
	 * 
	 * @param otp
	 * @return
	 */
	@RequestMapping(value = Constant.GET_OTP, method = RequestMethod.POST)
	public @ResponseBody OTP getOTP(@RequestBody OTP otp) {
		logger.info("Request received for Get My OTP " + otp.getVerificationId());

		otp = otpService.getOTP(otp);
		return otp;
	}

	/**
	 * API for verify Phone
	 * 
	 * @param token
	 * @param number
	 * @return
	 */
	@RequestMapping(value = Constant.PHONE_VERIFICATION, method = RequestMethod.GET)
	public @ResponseBody AppResponse verifyMobileNo(@RequestParam String token, @RequestParam String number) {
		logger.info("Request received for verify mobile");
		OTP otp = new OTP();
		otp.setOtp(token);
		otp.setVerificationId(number);
		otp.setType("MOBILE");
		AppResponse response = new AppResponse();
		try {
			otp = otpService.verifyOTP(otp);
			if ("VERIFIED".equals(otp.getStatus())) {
				response.setMessage("Success");
				response.setStatus("OK");
			} else if ("EXPIRED".equals(otp.getStatus())) {
				response.setMessage("OTP Expired. Please generate another OTP");
				response.setStatus("Fail");
			} else {
				response.setMessage("Invalid OTP");
				response.setStatus("Fail");
			}
		} catch (Exception e) {
			logger.equals(e.getStackTrace());
			response.setMessage("System error occured while verifying OTP");
			response.setStatus("Fail");
		}

		return response;
	}

	/**
	 * API for create new OTP
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = Constant.NEW_SMSOTP, method = RequestMethod.POST)
	public @ResponseBody AppResponse getNewSMSOTP(@RequestBody OTP otpObj) {
		logger.info("Request received for Get new SMS OTP");
		AppResponse response = new AppResponse();
		try {

			Register register = registrationService.findUserByEmailId(otpObj.getVerificationId());
			if (register != null) {
				OTP otp = new OTP();
				otp.setVerificationId(register.getContactNo());
				otp.setType("MOBILE");
				otp = otpService.reCreateOTP(otp);
				SMS sms = new SMS();
				sms.setPhoneNumber(register.getContactNo());
				sms.setTemplate(SMSService.VERIFY_NO);
				Map<String, String> valueMap = new HashMap<String, String>();
				valueMap.put("OTP", PasswordProtector.decrypt(otp.getOtp()));
				sms.setValueMap(valueMap);
				smsService.sendSMS(sms);
				Mail mail = new Mail();
				mail.setTemplateName(EmailService.EMAIL_OTP);
				mail.setMailTo(register.getEmail());
				mail.setValueMap(valueMap);
				emailService.sendMail(mail);
				response.setMessage("Success");
				response.setStatus("OK");
			} else {
				response.setMessage("Invalid user Id");
				response.setStatus("Fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error occuured while creating OTP");
			response.setStatus("Error");
		}
		return response;
	}

	/**
	 * API for forgot password
	 * 
	 * @param forgotPassword
	 * @return
	 */
	@RequestMapping(value = Constant.FORGOT_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody AppResponse forgotPassword(@RequestBody ForgotPassword forgotPassword) {
		logger.info("Request received for login");
		AppResponse response = new AppResponse();

		try {
			OTP otp = new OTP();
			otp.setOtp(forgotPassword.getOtp());
			Register register = registrationService.findUserByEmailId(forgotPassword.getUserName());
			if (forgotPassword.getNewPassword() != null && forgotPassword.getConfirmPassword() != null
					&& forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword())) {
				if (register != null) {
					otp.setVerificationId(register.getContactNo());
					otp.setType("MOBILE");
					otpService.verifyOTP(otp);
					if ("VERIFIED".equals(otp.getStatus())) {
						registrationService.updatePassword(forgotPassword);
						response.setMessage("Success");
						response.setStatus("OK");
					} else if ("EXPIRED".equals(otp.getStatus())) {
						response.setMessage("OTP Expired. Please generate another OTP");
						response.setStatus("Fail");
					} else {
						response.setMessage("Invalid OTP");
						response.setStatus("Fail");
					}
				} else {
					response.setMessage("Invalid Login Id");
					response.setStatus("Fail");
				}
			} else {
				response.setMessage("Password and confirm  password does not match");
				response.setStatus("Fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error occuured while updating password");
			response.setStatus("Error");
		}

		return response;
	}

	@RequestMapping(value = Constant.LOGIN, method = RequestMethod.POST)
	public @ResponseBody AppResponse loginUser(@RequestBody LoginRequest loginRequest) {
		logger.info("Login the User");
		AppResponse appResponse = new AppResponse();

		try {
			
			if (!registrationService.verifyLoginCredentials(loginRequest)) {
				appResponse.setMessage("Invalid Username and Password");
				appResponse.setStatus("Fail");
				appResponse.setHttpErrorCode(405);
			}else{
				appResponse.setMessage("Success");
				appResponse.setStatus("OK");
				appResponse.setHttpErrorCode(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
		}
		return appResponse;
	}
	
	/**
	 * API for register
	 * 
	 * @param register
	 * @return
	 */
	@RequestMapping(value = Constant.SURRENDER, method = RequestMethod.POST)
	public @ResponseBody AppResponse surrenderConnection(@RequestBody Register register) {
		logger.info("Registering the User");
		AppResponse appResponse = new AppResponse();

		try {
			if (registrationService.surrenderConnection(register)) {
				appResponse.setMessage("Successfully Surrendered.");
				appResponse.setStatus("OK");
				appResponse.setHttpErrorCode(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
		}
		return appResponse;
	}
}
