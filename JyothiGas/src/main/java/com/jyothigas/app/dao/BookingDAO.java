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
		log.info("Getting BookingEntity Instance");
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager.createQuery("select s from BookingEntity s ",BookingEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}
	
	public List<BookingEntity> findByConsumerId(Integer consumer_id) {
		log.info("Getting BookingEntity Instance with consumer_id: " + consumer_id);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager.createQuery("select s from BookingEntity s Where s.consumer_id = :consumer_id ",BookingEntity.class)
					.setParameter("consumer_id", consumer_id)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}
	
	/*
	 * For now assuming there will be only one type of booking; later when
	 * product will be added this needs to be changed @Rishabh
	 */
	public BookingEntity findInProgressOrderDetail(int bookingId) {
		log.info("Getting BookingEntity Instance with consumer_id: " + bookingId);
		BookingEntity bookingEntity = new BookingEntity();
		try {
			bookingEntity = entityManager.createQuery("select s from BookingEntity s Where s.id = :bookingId and s.status ='PENDING'",BookingEntity.class)
					.setParameter("bookingId", bookingId).getResultList().get(0);
			log.info("get successfull");
		} catch (Exception e) {
			System.out.println(e);
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}
	
	public List<BookingEntity> findByConnectionTypeId(Integer connectionTypeId) {
		log.info("Getting BookingEntity Instance with connectionTypeId: " + connectionTypeId);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager.createQuery("select s from BookingEntity s Where s.connectionTypeId = :connectionTypeId ",BookingEntity.class)
					.setParameter("connectionTypeId", connectionTypeId)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}
	
	public List<BookingEntity> findByStatus(String status) {
		log.info("Getting BookingEntity Instance with status: " + status);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager.createQuery("select s from BookingEntity s Where s.status = :status ",BookingEntity.class)
					.setParameter("status", status)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

}
