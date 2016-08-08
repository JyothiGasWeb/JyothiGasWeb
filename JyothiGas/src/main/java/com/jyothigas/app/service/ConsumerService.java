package com.jyothigas.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.dao.RoleDAO;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.entity.RoleEntity;
import com.jyothigas.app.model.ConsumerDetails;

@Service("consumerService")
@Transactional
public class ConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	ConsumerDAO consumerDAO;

	/**
	 * Method for fetch Consumer Details 
	 * @param email
	 * @return
	 */
	public ConsumerDetails fetchConsumerDetailsByEmail(String email) {
		logger.info("Fetching the Consumer Details..");
		ConsumerDetails consumerDetails = new ConsumerDetails();

		try {
			List<RegistrationEntity> registrationEntityList = registrationDAO.findByEmailId(email);
			for (RegistrationEntity registrationEntity : registrationEntityList) {

				if (registrationEntity != null && registrationEntity.getId() > 0) {
					consumerDetails.setReg_id(registrationEntity.getId());
					consumerDetails.setEmail(registrationEntity.getEmail());
					consumerDetails.setName(registrationEntity.getName());
					consumerDetails.setContactNo(registrationEntity.getContactNo());
					consumerDetails.setCity(registrationEntity.getCity());
					consumerDetails.setAreaCode(registrationEntity.getAreaCode());
					consumerDetails.setRoleId(registrationEntity.getRoleId());
					consumerDetails.setAddress(registrationEntity.getAddress());
					List<RoleEntity> roleEntityList = roleDAO.findByRoleId(registrationEntity.getRoleId());
					if (roleEntityList.size() > 0) {
						consumerDetails.setRoleName(roleEntityList.get(0).getName());
					}
					List<ConsumerEntity> consumerEntity = consumerDAO.findByRegId(registrationEntity.getId());
					if (consumerEntity.size() > 0) {
						consumerDetails.setConsumer_id(consumerEntity.get(0).getId());
					 }
				}
			}
		} catch (Exception e) {
			logger.error("Error while fetching consumer Details....." + e.getMessage());
			e.printStackTrace();
		}

		return consumerDetails;
	}

}
