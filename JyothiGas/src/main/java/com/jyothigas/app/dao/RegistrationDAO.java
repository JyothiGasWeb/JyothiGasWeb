package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.RegistrationEntity;

@Repository("registrationDAO")
public class RegistrationDAO extends JyothiGasDAO<RegistrationEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(RegistrationDAO.class);

	public List<RegistrationEntity> findByEmailId(String emailId) {
		log.info("Getting UserEntity Instance with emailId: " + emailId);
		List<RegistrationEntity> registrationEntity = new ArrayList<RegistrationEntity>();
		try {
			registrationEntity = entityManager.createQuery("select s from RegistrationEntity s Where s.email = :emailId",RegistrationEntity.class)
					.setParameter("emailId", emailId)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return registrationEntity;
	}
	
	public List<RegistrationEntity> findByStatus(String status) {
		log.info("Getting UserEntity Instance with status: " + status);
		List<RegistrationEntity> registrationEntity = new ArrayList<RegistrationEntity>();
		try {
			registrationEntity = entityManager.createQuery("select s from RegistrationEntity s Where s.status = :status",RegistrationEntity.class)
					.setParameter("status", status)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return registrationEntity;
	}
	
	
	public List<RegistrationEntity> findByMobileNo(String contactNo) {
		log.info("Getting UserEntity Instance with contactNo: " + contactNo);
		List<RegistrationEntity> registrationEntity = new ArrayList<RegistrationEntity>();
		try {
			registrationEntity = entityManager.createQuery("select s from RegistrationEntity s Where s.contactNo = :contactNo",RegistrationEntity.class)
					.setParameter("contactNo", contactNo)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return registrationEntity;
	}

	public RegistrationEntity checkLoginCredentials(String email, String encyPassword) {
		log.info("Checking for user login credentials : " + email);
		RegistrationEntity registrationEntity = null;
		try {
			registrationEntity = entityManager.createQuery("select s from RegistrationEntity s Where s.email = :email and s.encyPassword = :encyPassword ",RegistrationEntity.class)
					.setParameter("email", email)
					.setParameter("encyPassword", encyPassword)
					.getResultList().get(0);
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return registrationEntity;
	}

}
