package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.BookingEntity;

@Repository("bookingDAO")
public class BookingDAO extends JyothiGasDAO<BookingEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(BookingDAO.class);
	
	public List<BookingEntity> findAllBookings() {
		log.info("Getting ConsumerConnectionEntity Instance");
		List<BookingEntity> ConsumerConnectionEntity = new ArrayList<BookingEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s ",BookingEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	public List<BookingEntity> findByConsumerId(Integer consumer_id) {
		log.info("Getting ConsumerConnectionEntity Instance with consumer_id: " + consumer_id);
		List<BookingEntity> ConsumerConnectionEntity = new ArrayList<BookingEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.consumer_id = :consumer_id ",BookingEntity.class)
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
	public BookingEntity findInProgressOrderDetail(int bookingId) {
		log.info("Getting ConsumerConnectionEntity Instance with consumer_id: " + bookingId);
		BookingEntity ConsumerConnectionEntity = new BookingEntity();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.id = :bookingId and s.status ='PENDING'",BookingEntity.class)
					.setParameter("bookingId", bookingId).getResultList().get(0);
			log.info("get successfull");
		} catch (Exception e) {
			System.out.println(e);
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	public List<BookingEntity> findByConnectionTypeId(Integer connectionTypeId) {
		log.info("Getting ConsumerConnectionEntity Instance with connectionTypeId: " + connectionTypeId);
		List<BookingEntity> ConsumerConnectionEntity = new ArrayList<BookingEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.connectionTypeId = :connectionTypeId ",BookingEntity.class)
					.setParameter("connectionTypeId", connectionTypeId)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}
	
	public List<BookingEntity> findByStatus(String status) {
		log.info("Getting ConsumerConnectionEntity Instance with status: " + status);
		List<BookingEntity> ConsumerConnectionEntity = new ArrayList<BookingEntity>();
		try {
			ConsumerConnectionEntity = entityManager.createQuery("select s from ConsumerConnectionEntity s Where s.status = :status ",BookingEntity.class)
					.setParameter("status", status)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return ConsumerConnectionEntity;
	}

}
