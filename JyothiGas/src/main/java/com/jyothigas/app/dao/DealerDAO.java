package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.DealerEntity;

@Repository
public class DealerDAO extends JyothiGasDAO<DealerEntity> {

	private static final Log log = LogFactory.getLog(DealerDAO.class);

	public List<DealerEntity> findAllDealers() {
		log.info("getting Dealer details");
		List<DealerEntity> instance = new ArrayList<DealerEntity>();
		try {

			instance = entityManager.createQuery("select s from DealerEntity s", DealerEntity.class)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}

	
	public List<DealerEntity> findByDealerName(String dealer_name) {
		log.info("getting Dealer details using dealer name : " + dealer_name);
		List<DealerEntity> instance = new ArrayList<DealerEntity>();
		try {

			instance = entityManager.createQuery("select s from DealerEntity s Where s.dealer = :dealer_name", DealerEntity.class)
					.setParameter("dealer_name", dealer_name)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}
	
	public List<DealerEntity> findByDealerEmail(String dealer_email) {
		log.info("getting Dealer details using dealer email : " + dealer_email);
		List<DealerEntity> instance = new ArrayList<DealerEntity>();
		try {

			instance = entityManager.createQuery("select s from DealerEntity s Where s.email = :dealer_email", DealerEntity.class)
					.setParameter("dealer_email", dealer_email)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}
}
