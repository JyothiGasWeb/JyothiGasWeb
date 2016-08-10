package com.jyothigas.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConnectionTypeDAO;
import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.DealerDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.dao.RoleDAO;
import com.jyothigas.app.entity.ConnectionTypeEntity;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.DealerEntiy;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.entity.RoleEntity;
import com.jyothigas.app.model.ConsumerDetails;
import com.jyothigas.app.model.Register;

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
	
	@Autowired
	DealerDAO dealerDAO;
	
	@Autowired
	ConnectionTypeDAO connectionTypeDAO;

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
					consumerDetails.setConnectionQty(registrationEntity.getConnectionQty());
					consumerDetails.setStatus(registrationEntity.getStatus());
					//Fetching the Role Details
					List<RoleEntity> roleEntityList = roleDAO.findByRoleId(registrationEntity.getRoleId());
					if (roleEntityList.size() > 0) {
						consumerDetails.setRoleName(roleEntityList.get(0).getName());
					}
					
					//Fetching the Consumer Id
					List<ConsumerEntity> consumerEntity = consumerDAO.findByRegId(registrationEntity.getId());
					if (consumerEntity.size() > 0) {
						consumerDetails.setConsumer_id(consumerEntity.get(0).getId());
					 }
					
					//Fetching the Dealer Details
					DealerEntiy dealerEntiy = dealerDAO.findById(DealerEntiy.class, registrationEntity.getDealerId());
					if(dealerEntiy != null){
						consumerDetails.setDealerId(registrationEntity.getDealerId());
						consumerDetails.setDealerName(dealerEntiy.getDealer_name());
					}
					//Fetching the connection type details
					ConnectionTypeEntity connectionTypeEntity = connectionTypeDAO.findById(ConnectionTypeEntity.class, registrationEntity.getConnectionTypeId());
					if(connectionTypeEntity != null){
						consumerDetails.setConnectionTypeName(connectionTypeEntity.getConnectionType());
						consumerDetails.setConnectionTypeDesc(connectionTypeEntity.getConnectionDesc());
						consumerDetails.setConnectionPrice(connectionTypeEntity.getConnectionPrice());
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("Error while fetching consumer Details....." + e.getMessage());
			e.printStackTrace();
		}

		return consumerDetails;
	}

	/**
	 * Method for update consumer data
	 * @param register
	 * @return
	 */
	public int updateConsumer(Register register) {
		int result = 0;
		try {
			RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class, register.getId());
			if(registrationEntity != null){
				if(register.getAddress() != null){
					registrationEntity.setAddress(register.getAddress());
				}
				if(register.getDealerId() > 0){
					registrationEntity.setDealerId(register.getDealerId());
				}
				if(register.getConnectionTypeId() > 0){
					registrationEntity.setConnectionTypeId(register.getConnectionTypeId());
				}
				if(register.getAreaCode() != null){
					registrationEntity.setAreaCode(register.getAreaCode());
				}
				if(register.getStatus() != null){
					registrationEntity.setStatus(register.getStatus());
				}
				registrationEntity.setId(register.getId());
				RegistrationEntity entity = registrationDAO.merge(registrationEntity);
				result = entity.getId();
			}
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
		}
		return result;
	}

}
