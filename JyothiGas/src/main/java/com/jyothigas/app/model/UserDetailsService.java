package com.jyothigas.app.service;

import com.jyothigas.app.model.ForgotPassword;
import com.jyothigas.app.model.LoginRequest;
import com.jyothigas.app.model.OTP;
import com.jyothigas.app.model.Register;
import com.jyothigas.app.model.UserSecurityContext;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

	public int registerUser(Register register);

	public void createConsumer(int regId);

	public boolean verifyEmail(String emailId);

	public boolean verifyMobileNO(String mobileNo);

	void sendSMSForVerification(OTP smsOTP);

	void sendEmailOTP(OTP emailOTP);

	public Register findUserByEmailId(String email);

	public void updatePassword(ForgotPassword forgotPassword);

	public boolean verifyLoginCredentials(LoginRequest loginRequest);

	// for surrendering the connection
	public boolean surrenderConnection(Register register);

	void sendNotificationEmail(String entity, String name, String EmailTo);

	void sendNotificationSMS(String name, String phoneNumber);

	public void updateRegistrationToken(String regToken, String username);

	public UserSecurityContext getUserDetailsByAccessToken(String accessToken);
}