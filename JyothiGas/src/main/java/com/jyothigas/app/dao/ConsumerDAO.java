package com.jyothigas.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.ConsumerEntity;
import java.util.ArrayList;
import java.util.List;

@Repository("consumerDAO")
public class ConsumerDAO extends JyothiGasDAO<ConsumerEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(ConsumerDAO.class);

	public ConsumerEntity findByRegId(Integer reg_id) {
		log.info("Getting ConsumerEntity Instance with reg_id: " + reg_id);
		ConsumerEntity conumerEntity = new ConsumerEntity();
		try {
			conumerEntity = entityManager
					.createQuery("select s from ConsumerEntity s Where s.reg_id = :reg_id ", ConsumerEntity.class)
					.setParameter("reg_id", reg_id).getResultList().get(0);
			log.info("get successfull");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed : " + e);
		}
		return conumerEntity;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findByRegIds(List<Integer> reg_ids) {
		log.info("Getting ConsumerEntity Instance with reg_id: " + reg_ids);
		List<Integer> custIds = null;
		try {
			Query query = entityManager
					.createQuery("select s.id from ConsumerEntity s Where s.reg_id IN(:reg_id) ", Object[].class)
					.setParameter("reg_id", reg_ids);
			List<Integer[]> resultList = query.getResultList();
			custIds = new ArrayList<Integer>(resultList.size());
			for (int i = 0; i < resultList.size(); i++) {
				custIds.add((Integer.parseInt(String.valueOf(resultList.get(i)))));
			}
			log.info("get successfull");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed : " + e);
		}
		return custIds;
	}

	public List<ConsumerEntity> getAllCustomer() {
		log.info("Getting ConsumerEntity Instance with reg_id: ");
		List<ConsumerEntity> conumerEntity = new ArrayList<ConsumerEntity>();
		try {
			conumerEntity = entityManager.createQuery("select s from ConsumerEntity s  ", ConsumerEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed : " + e);
		}
		return conumerEntity;
	}

}
