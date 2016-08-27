package com.jyothigas.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.MechanicServiceDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.MechanicServiceEntity;
import com.jyothigas.app.entity.RegistrationEntity;
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
	RegistrationDAO registrationDAO;
	
	@Autowired
	ConsumerDAO consumerDAO;
	
	public int addMechanicService(MechanicServiceModel mechServ) {
		int result = 0;
		MechanicServiceEntity entity = new MechanicServiceEntity();
		BeanUtils.copyProperties(mechServ, entity);
		entity.setStatus("NEW");
		entity = mechanicDAO.merge(entity);
		ConsumerEntity consumer = consumerDAO.findById(ConsumerEntity.class, entity.getConsumerId());
		RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class, consumer.getReg_id());
		sendNotificationSMS(registrationEntity.getName(), registrationEntity.getContactNo());
		result = 1;
		return result;
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
		sms.setTemplate(SMSService.SURRENDER);
		sms.setValueMap(valueMap);
		smsService.sendSMS(sms);

	}

}
