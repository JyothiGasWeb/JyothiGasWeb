package com.jyothigas.app.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.OTPDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.entity.OTPEntity;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.model.Mail;
import com.jyothigas.app.model.OTP;
import com.jyothigas.utils.Constant;
import com.jyothigas.utils.OTPUtil;
import com.jyothigas.utils.PasswordProtector;

@Service("otpService")
@Transactional
public class OTPService {

	@Autowired
	OTPDAO otpdao;

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	EmailService emailService;

	private static final Log log = LogFactory.getLog(OTPService.class);

	public OTP createOTP(OTP otp) {
		log.info("Creating new OTP");
		OTPEntity otpEntity = new OTPEntity();
		otpEntity.setVerificationId(otp.getVerificationId());
		otpEntity.setType(otp.getType());
		Calendar calendar = Calendar.getInstance();
		if ("EMAIL".equals(otp.getType())) {
			calendar.add(Calendar.MONTH, 1);
			otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
			otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateToken()));
			otpEntity.setOtp(otp.getOtp());

			// Send Email Notification to user
		} else {
			calendar.add(Calendar.MINUTE, 30);
			otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
			otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateIntToken()));
			otpEntity.setOtp(otp.getOtp());

			// Send SMS Verification to user
		}
		otpEntity.setStatus("WAITING");
		otpdao.persist(otpEntity);

		return otp;
	}

	public OTP verifyOTP(OTP otp) {
		OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());

		if (otpEntity != null && "WAITING".equals(otpEntity.getStatus())) {
			if (otp.getType().equals(otpEntity.getType())
					&& PasswordProtector.encrypt(otp.getOtp()).equals(otpEntity.getOtp())) {
				if (otpEntity.getValidUpto().getTime() > System.currentTimeMillis()) {
					otp.setStatus("VERIFIED");
					RegistrationEntity entity = registrationDAO.findByMobileNo(otp.getVerificationId()).get(0);
					entity.setStatus(Constant.ACTIVE);
					entity = registrationDAO.merge(entity);
					// sendEmailToCustomer(entity.getName(),
					// String.valueOf(entity.getId()), entity.getEmail());
				} else {
					otp.setStatus("EXPIRED");
				}

				otpEntity.setStatus(otp.getStatus());
				otpdao.merge(otpEntity);
			} else {
				otp.setStatus("INVALID");
			}
		} else {
			otp.setStatus("INVALID");
		}

		return otp;
	}

	public void sendRegEmailToCustomer(OTP otp) {
		RegistrationEntity entity = registrationDAO.findByMobileNo(otp.getVerificationId()).get(0);
		Mail mail = new Mail();
		String name = entity.getName();
		String regId = String.valueOf(entity.getId());
		String EmailTo = entity.getEmail();
		mail.setTemplateName(EmailService.EMAIL_USER_REGISTER);
		mail.setMailTo(EmailTo);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("MESSAGE", Constant.NEW_CUSTOMER_EMAIL.concat(regId));

		valueMap.put("NAME", name);
		mail.setValueMap(valueMap);
		try {
			emailService.sendMail(mail);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public OTP reCreateOTP(OTP otp) {

		OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());
		if (otpEntity != null && otpEntity.getOtpId() != null) {

			otpEntity.setVerificationId(otp.getVerificationId());
			otpEntity.setType(otp.getType());
			Calendar calendar = Calendar.getInstance();
			if ("EMAIL".equals(otp.getType())) {
				calendar.add(Calendar.MONTH, 1);
				otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
				otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateToken()));
				otpEntity.setOtp(otp.getOtp());

				// TODO Send Email Notification to user
			} else {
				calendar.add(Calendar.MINUTE, 30);
				otpEntity.setValidUpto(new Date(calendar.getTimeInMillis()));
				otp.setOtp(PasswordProtector.encrypt(OTPUtil.generateIntToken()));
				otpEntity.setOtp(otp.getOtp());

				// TODO send SMS Verification to user
			}
			otpEntity.setStatus("WAITING");
			otpdao.merge(otpEntity);
		} else {
			otp = createOTP(otp);
		}

		return otp;
	}

	public OTP getOTP(OTP otp) {
		OTPEntity otpEntity = otpdao.findByVerificationId(otp.getVerificationId());
		if (otpEntity != null) {
			otp.setOtp(PasswordProtector.decrypt(otpEntity.getOtp()));
			otp.setType(otpEntity.getType());
			otp.setStatus(otpEntity.getStatus());
			return otp;
		}
		return null;
	}

}
