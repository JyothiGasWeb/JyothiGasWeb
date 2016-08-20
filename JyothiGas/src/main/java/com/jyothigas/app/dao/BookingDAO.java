package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.ConsumerConnectionEntity;

@Repository("bookingDAO")
public class BookingDAO extends JyothiGasDAO<ConsumerConnectionEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(BookingDAO.class);
	
	public List<ConsumerConnectionEntity> findAllBookings() {
		log.info("Getting ConsumerConnectionEntity Instance");
		List<ConsumerConnectionEntity> ConsumerConnectionEntity = new ArrayList<ConsumerConnectionEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s ",ConsumerConnectionEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	public List<ConsumerConnectionEntity> findByConsumerId(Integer consumer_id) {
		log.info("Getting ConsumerConnectionEntity Instance with consumer_id: " + consumer_id);
		List<ConsumerConnectionEntity> ConsumerConnectionEntity = new ArrayList<ConsumerConnectionEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.consumer_id = :consumer_id ",ConsumerConnectionEntity.class)
					.setParameter("consumer_id", consumer_id)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	/*
	 * For now assuming there will be only one type of booking; later when
	 * product will be added this needs to be changed @Rishabh
	 */
	public ConsumerConnectionEntity findInProgressOrderDetail(int bookingId) {
		log.info("Getting ConsumerConnectionEntity Instance with consumer_id: " + bookingId);
		ConsumerConnectionEntity ConsumerConnectionEntity = new ConsumerConnectionEntity();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.id = :bookingId and s.status ='PENDING'",ConsumerConnectionEntity.class)
					.setParameter("bookingId", bookingId).getResultList().get(0);
			log.info("get successfull");
		} catch (Exception e) {
			System.out.println(e);
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	public List<ConsumerConnectionEntity> findByConnectionTypeId(Integer connectionTypeId) {
		log.info("Getting ConsumerConnectionEntity Instance with connectionTypeId: " + connectionTypeId);
		List<ConsumerConnectionEntity> ConsumerConnectionEntity = new ArrayList<ConsumerConnectionEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.connectionTypeId = :connectionTypeId ",ConsumerConnectionEntity.class)
					.setParameter("connectionTypeId", connectionTypeId)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	public List<ConsumerConnectionEntity> findByStatus(String status) {
		log.info("Getting ConsumerConnectionEntity Instance with status: " + status);
		List<ConsumerConnectionEntity> ConsumerConnectionEntity = new ArrayList<ConsumerConnectionEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.status = :status ",ConsumerConnectionEntity.class)
					.setParameter("status", status)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}

}
