package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.DealerEntiy;

@Repository
public class DealerDAO extends JyothiGasDAO<DealerEntiy> {

	private static final Log log = LogFactory.getLog(DealerDAO.class);

	public List<DealerEntiy> findAllDealers() {
		log.info("getting Dealer details");
		List<DealerEntiy> instance = new ArrayList<DealerEntiy>();
		try {

			instance = entityManager.createQuery("select s from DealerEntiy s", DealerEntiy.class)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}

	
	public List<DealerEntiy> findByDealerName(String dealer_name) {
		log.info("getting Dealer details using dealer name : " + dealer_name);
		List<DealerEntiy> instance = new ArrayList<DealerEntiy>();
		try {

			instance = entityManager.createQuery("select s from DealerEntiy s Where s.dealer_name = :dealer_name", DealerEntiy.class)
					.setParameter("dealer_name", dealer_name)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}
	
	public List<DealerEntiy> findByDealerEmail(String dealer_email) {
		log.info("getting Dealer details using dealer email : " + dealer_email);
		List<DealerEntiy> instance = new ArrayList<DealerEntiy>();
		try {

			instance = entityManager.createQuery("select s from DealerEntiy s Where s.dealer_email = :dealer_email", DealerEntiy.class)
					.setParameter("dealer_email", dealer_email)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}
}
