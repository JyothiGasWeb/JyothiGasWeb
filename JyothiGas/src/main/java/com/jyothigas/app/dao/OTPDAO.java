package com.jyothigas.app.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.OTPEntity;

@Repository
public class OTPDAO extends JyothiGasDAO<OTPEntity> {

	private static final Log log = LogFactory.getLog(OTPDAO.class);

	public OTPEntity findByVerificationId(String verificationId) {
		log.info("getting OTP instance with verification Id: " + verificationId);
		OTPEntity instance = new OTPEntity();
		try {

			instance = entityManager
					.createQuery("select s from OTPEntity s Where s.verificationId = :verificationId ", OTPEntity.class)
					.setParameter("verificationId", verificationId)
					.getResultList().get(0);
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}

}
