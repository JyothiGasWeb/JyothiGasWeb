package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.DealerDAO;
import com.jyothigas.app.dao.DistributorDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.dao.RoleDAO;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.DealerEntity;
import com.jyothigas.app.entity.DistributorEntity;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.entity.RoleEntity;
import com.jyothigas.app.model.Dealer;
import com.jyothigas.app.model.ForgotPassword;
import com.jyothigas.app.model.LoginRequest;
import com.jyothigas.app.model.Mail;
import com.jyothigas.app.model.OTP;
import com.jyothigas.app.model.Register;
import com.jyothigas.app.model.Role;
import com.jyothigas.app.model.SMS;
import com.jyothigas.utils.Constant;
import com.jyothigas.utils.PasswordProtector;

@Service("registrationService")
@Transactional
public class RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	ConsumerDAO consumerDAO;

	@Autowired
	OTPService otpService;

	@Autowired
	SMSService smsService;

	@Autowired
	EmailService emailService;

	@Autowired
	RoleDAO roleDao;

	@Autowired
	DealerDAO dealerDAO;

	@Autowired
	DistributorDAO distributorDAO;

	/**
	 * Method for Registration
	 *
	 * @param register
	 */
	public int registerUser(Register register) {
		int result = 0;
		RegistrationEntity registrationEntity = new RegistrationEntity();
		try {
			logger.info("User Registration...");
			BeanUtils.copyProperties(register, registrationEntity);
			registrationEntity.setEncyPassword(PasswordProtector.encrypt(register.getEncyPassword()));
			registrationEntity.setStatus(Constant.INACTIVE);
			registrationEntity.setCreatedDate(new Date());
			registrationEntity = registrationDAO.merge(registrationEntity);
			result = registrationEntity.getId();

			// Creating Consumer
			switch (register.getRoleId()) {
			case 1:
				createConsumer(result);
				break;
			case 2:
				createDealer(result);
				break;
			case 3:
				createDistributor(result);
				break;
			}

			OTP emailOTP = new OTP();
			OTP mobileOTP = new OTP();

			emailOTP.setVerificationId(register.getEmail());
			emailOTP.setType("EMAIL");
			emailOTP = otpService.reCreateOTP(emailOTP);
			// sendEmailForVerification(emailOTP, "Mr. " + register.getName());

			mobileOTP.setVerificationId(register.getContactNo());
			mobileOTP.setType("MOBILE");
			mobileOTP = otpService.reCreateOTP(mobileOTP);
			sendSMSForVerification(mobileOTP);
			emailOTP.setOtp(mobileOTP.getOtp());
			sendEmailOTP(emailOTP);

		} catch (Exception e) {
			logger.error("Error while registation : " + e.getMessage());
			e.printStackTrace();
			result = 0;
		}
		return result;

	}

	/**
	 * Method for create consumer after registration
	 *
	 * @param regId
	 */
	public void createConsumer(int regId) {
		ConsumerEntity consumerEntity = new ConsumerEntity();
		try {
			logger.info("Creating Consumer...");
			consumerEntity.setReg_id(regId);
			consumerDAO.merge(consumerEntity);
		} catch (Exception e) {
			logger.error("Error while creating  consumer : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void createDealer(int regId) {
		DealerEntity entity = new DealerEntity();
		try {
			logger.info("Creating Consumer...");
			entity.setReg_id(regId);
			dealerDAO.merge(entity);
		} catch (Exception e) {
			logger.error("Error while creating  consumer : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void createDistributor(int regId) {
		DistributorEntity entity = new DistributorEntity();
		try {
			logger.info("Creating Consumer...");
			entity.setReg_id(regId);
			distributorDAO.merge(entity);
		} catch (Exception e) {
			logger.error("Error while creating  consumer : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean verifyEmail(String emailId) {
		boolean result = true;
		List<RegistrationEntity> registrationEntity = registrationDAO.findByEmailId(emailId);
		if (registrationEntity != null && registrationEntity.size() > 0) {
			result = false;
		}

		return result;
	}

	public boolean verifyMobileNO(String mobileNo) {
		boolean result = true;
		List<RegistrationEntity> registrationEntity = registrationDAO.findByMobileNo(mobileNo);
		if (registrationEntity != null && registrationEntity.size() > 0) {
			result = false;
		}

		return result;
	}

	/*
	 * private void sendEmailForVerification(OTP emailOTP, String name) { Mail
	 * mail = new Mail(); mail.setTemplateName(EmailService.VERIFY_EMAIL);
	 * mail.setMailTo(emailOTP.getVerificationId()); Map<String, String>
	 * domainValue = new HashMap<String, String>(); String url =
	 * MedRepProperty.getInstance().getProperties("medrep.home") +
	 * "web/registration/verifyEmail/"; String token =
	 * emailOTP.getVerificationId() + "MEDREP" +
	 * PasswordProtector.decrypt(emailOTP.getOtp()); String encryteptoken =
	 * PasswordProtector.encrypt(token) ; encryteptoken =
	 * encryteptoken.replace('/', '.').replace('+','-'); url = url +
	 * encryteptoken + "/"; domainValue.put("URL", url); domainValue.put("NAME",
	 * name); mail.setValueMap(domainValue); emailService.sendMail(mail); }
	 */
	private void sendSMSForVerification(OTP smsOTP) {

		SMS sms = new SMS();
		sms.setPhoneNumber(smsOTP.getVerificationId());
		String otp = PasswordProtector.decrypt(smsOTP.getOtp());
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("OTP", otp);
		sms.setTemplate(SMSService.VERIFY_NO);
		sms.setValueMap(valueMap);
		smsService.sendSMS(sms);

	}

	private void sendEmailOTP(OTP emailOTP) {

		Mail mail = new Mail();
		mail.setTemplateName(EmailService.EMAIL_OTP);
		mail.setMailTo(emailOTP.getVerificationId());
		Map<String, String> domainValue = new HashMap<String, String>();
		String otp = PasswordProtector.decrypt(emailOTP.getOtp());
		domainValue.put("OTP", otp);
		mail.setValueMap(domainValue);
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

	public Register findUserByEmailId(String email) {
		Register register = new Register();
		try {
			List<RegistrationEntity> registrationEntityList = registrationDAO.findByEmailId(email);
			for (RegistrationEntity registrationEntity : registrationEntityList) {
				BeanUtils.copyProperties(registrationEntity, register);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return register;
	}

	public void updatePassword(ForgotPassword forgotPassword) {
		try {
			List<RegistrationEntity> registrationEntityList = registrationDAO
					.findByEmailId(forgotPassword.getUserName());
			for (RegistrationEntity registrationEntity : registrationEntityList) {
				registrationEntity.setEncyPassword(PasswordProtector.encrypt(forgotPassword.getNewPassword()));
				registrationDAO.merge(registrationEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean verifyLoginCredentials(LoginRequest loginRequest) {
		boolean result = true;
		try {
			RegistrationEntity entity = registrationDAO.checkLoginCredentials(loginRequest.getUsername(),
					PasswordProtector.encrypt(loginRequest.getPassword()));
			if (null == entity) {
				return false;
			}
			if (null != entity && entity.getStatus().equalsIgnoreCase(Constant.INACTIVE)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// for surrendering the connection
	public boolean surrenderConnection(Register register) {
		boolean result = false;
		try {
			RegistrationEntity registerEntity = registrationDAO.findById(RegistrationEntity.class, register.getId());
			registerEntity.setSurrenderInfo(register.getSurrenderInfo());
			registerEntity.setSurrenderStatus(Constant.STATUS_SURRENDER);
			registerEntity.setUpdatedDate(new Date());
			registerEntity.setSurrender_Date(new Date());
			registrationDAO.merge(registerEntity);
			sendNotificationSMS(registerEntity.getName(), registerEntity.getContactNo());
			sendNotificationEmail("Surrender Connection", registerEntity.getName(), registerEntity.getEmail());
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void sendNotificationEmail(String entity, String name, String EmailTo) {
		Mail mail = new Mail();
		mail.setTemplateName(EmailService.EMAIL_SERVICE_REQUEST);
		mail.setMailTo(EmailTo);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("ENTITY", entity);
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

	private void sendNotificationSMS(String name, String phoneNumber) {
		SMS sms = new SMS();
		sms.setPhoneNumber(phoneNumber);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("NAME", name);
		sms.setTemplate(SMSService.SURRENDER);
		sms.setValueMap(valueMap);
		smsService.sendSMS(sms);

	}

	public void sendChngPswdEmail(String name, String EmailTo) {
		Mail mail = new Mail();
		mail.setTemplateName(EmailService.EMAIL_USER_UPDATE);
		mail.setMailTo(EmailTo);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("ENTITY", "Password");
		valueMap.put("NAME", name);
		valueMap.put("SUBJECT", "Password");
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

	// This method provides User Role List
	public List<Role> getRoles() {
		List<Role> roleList = new ArrayList<Role>();
		List<RoleEntity> roleListEntity = roleDao.getRoles();
		for (RoleEntity role : roleListEntity) {
			Role info = new Role();
			BeanUtils.copyProperties(role, info);
			roleList.add(info);
		}
		return roleList;
	}

	public List<Dealer> getUserList(Integer roleId) {
		List<Dealer> dealerList = new ArrayList<Dealer>();
		try {
			List<RegistrationEntity> dealerEntityList = registrationDAO.getUserList(roleId);
			for (RegistrationEntity entity : dealerEntityList) {
				Dealer info = new Dealer();
				info.setDealer_name(entity.getName());
				info.setId(entity.getId());
				info.setDealer_address(entity.getAddress());
				info.setDealer_area_code(entity.getAreaCode());
				info.setDealer_contact_no(entity.getContactNo());
				info.setDealer_email(entity.getEmail());
				dealerList.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Could fetch Dealers List", e);
		}
		return dealerList;
	}
}
