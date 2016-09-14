package com.jyothigas.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.MechanicServiceDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.MechanicServiceEntity;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.model.Mail;
import com.jyothigas.app.model.MechanicServiceModel;
import com.jyothigas.app.model.SMS;

@Service
@Transactional
public class MechanicServService {

	@Autowired
	MechanicServiceDAO mechanicDAO;

	@Autowired
	SMSService smsService;
	@Autowired
	EmailService emailService;

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	ConsumerDAO consumerDAO;

	public int addMechanicService(MechanicServiceModel mechServ) {
		int result = 0;
		MechanicServiceEntity entity = new MechanicServiceEntity();
		BeanUtils.copyProperties(mechServ, entity);
		entity.setStatus("NEW");
		entity = mechanicDAO.merge(entity);
		ConsumerEntity consumer = consumerDAO.findById(ConsumerEntity.class, mechServ.getConsumerId());
		RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class,
				consumer.getReg_id());
		sendNotificationSMS(registrationEntity.getName(), registrationEntity.getContactNo());
		sendNotificationEmail("Mechanic Service ", registrationEntity.getName(), registrationEntity.getEmail());
		result = 1;
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

	public int updateMechanicService(MechanicServiceModel mechServ) {
		int result = 0;
		MechanicServiceEntity entity = new MechanicServiceEntity();
		BeanUtils.copyProperties(mechServ, entity);
		entity.setStatus("IN PROGRESS");
		entity = mechanicDAO.merge(entity);
		result = 1;
		return result;
	}

	private void sendNotificationSMS(String name, String phoneNumber) {
		SMS sms = new SMS();
		sms.setPhoneNumber(phoneNumber);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("NAME", name);
		sms.setTemplate(SMSService.MECHANIC_SERVICE);
		sms.setValueMap(valueMap);
		smsService.sendSMS(sms);

	}

}
