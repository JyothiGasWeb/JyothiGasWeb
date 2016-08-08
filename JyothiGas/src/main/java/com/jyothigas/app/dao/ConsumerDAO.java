package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.ConsumerEntity;

@Repository("consumerDAO")
public class ConsumerDAO extends JyothiGasDAO<ConsumerEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(ConsumerDAO.class);

	public List<ConsumerEntity> findByRegId(Integer reg_id) {
		log.info("Getting ConsumerEntity Instance with reg_id: " + reg_id);
		List<ConsumerEntity> conumerEntity = new ArrayList<ConsumerEntity>();
		try {
			conumerEntity = entityManager.createQuery("select s from ConsumerEntity s Where s.reg_id = :reg_id ",ConsumerEntity.class)
					.setParameter("reg_id", reg_id)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return conumerEntity;
	}

}
