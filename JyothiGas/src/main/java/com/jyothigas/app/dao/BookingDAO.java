package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.BookingEntity;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.model.Reports;

@Repository("bookingDAO")
public class BookingDAO extends JyothiGasDAO<BookingEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(BookingDAO.class);

	public List<BookingEntity> findAllBookings() {
		log.info("Getting bookingEntity Instance");
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager.createQuery("select s from BookingEntity s ", BookingEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	public int findCylinderBookedFY(Date fromDate, Date toDate) {
		log.info("Getting bookingEntity Instance");
		int bookingCount = 0;
		try {
			bookingCount = entityManager
					.createQuery(
							"select s from BookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.bookingType='REFILL'",
							BookingEntity.class)
					.setParameter("fromDate", fromDate).setParameter("toDate", toDate).getResultList().size();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return bookingCount;
	}

	public Map<Integer, Integer> findCylinderSoldFYbyCustId(Date fromDate, Date toDate, List<ConsumerEntity> custIds) {
		log.info("Getting bookingEntity Instance");
		int bookingCount = 0;
		Map<Integer, Integer> report = new HashMap<Integer, Integer>();
		try {
			for (ConsumerEntity custId : custIds) {
				bookingCount = entityManager
						.createQuery(
								"select s from BookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.status ='DELIVERED' and s.bookingType='REFILL' and s.consumer_id =:custId",
								BookingEntity.class)
						.setParameter("fromDate", fromDate).setParameter("custId", custId.getId())
						.setParameter("toDate", toDate).getResultList().size();
				report.put(custId.getReg_id(), bookingCount);
			}

			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return report;
	}

	public int findCylinderSoldFY(Date fromDate, Date toDate, int connectionType) {
		log.info("Getting bookingEntity Instance");
		int bookingCount = 0;
		try {
			bookingCount = entityManager
					.createQuery(
							"select s from BookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.status ='DELIVERED' and s.bookingType='REFILL' and s.connectionTypeId=:connectionType",
							BookingEntity.class)
					.setParameter("fromDate", fromDate).setParameter("connectionType", connectionType)
					.setParameter("toDate", toDate).getResultList().size();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return bookingCount;
	}

	public List<BookingEntity> findByConsumerId(Integer consumer_id) {
		log.info("Getting bookingEntity Instance with consumer_id: " + consumer_id);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager.createQuery(
					"select s from BookingEntity s Where s.consumer_id = :consumer_id ORDER BY s.created_date DESC",
					BookingEntity.class).setParameter("consumer_id", consumer_id).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	/*
	 * For now assuming there will be only one type of booking; later when
	 * product will be added this needs to be changed @Rishabh
	 */
	public BookingEntity findInProgressOrderDetail(int bookingId) {
		log.info("Getting bookingEntity Instance with consumer_id: " + bookingId);
		BookingEntity bookingEntity = new BookingEntity();
		try {
			bookingEntity = entityManager
					.createQuery("select s from BookingEntity s Where s.id = :bookingId and s.status ='PENDING'",
							BookingEntity.class)
					.setParameter("bookingId", bookingId).getResultList().get(0);
			log.info("get successfull");
		} catch (Exception e) {
			System.out.println(e);
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	public List<BookingEntity> findByConnectionTypeId(Integer connectionTypeId) {
		log.info("Getting bookingEntity Instance with connectionTypeId: " + connectionTypeId);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager
					.createQuery("select s from BookingEntity s Where s.connectionTypeId = :connectionTypeId ",
							BookingEntity.class)
					.setParameter("connectionTypeId", connectionTypeId).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	public List<BookingEntity> findByStatus(String status) {
		log.info("Getting bookingEntity Instance with status: " + status);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager
					.createQuery("select s from BookingEntity s Where s.status = :status ", BookingEntity.class)
					.setParameter("status", status).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	@SuppressWarnings("unchecked")
	public List<Reports> salesReportForDealer(List<Integer> cust_ids) {
		log.info("Getting bookingEntity Instance");
		List<Reports> report = null;
		try {

			Query query = entityManager.createQuery(
					"SELECT count(Cust_Id) as Count,bookingType,sum(total) FROM BookingEntity s where bookingType='REFILL' and s.consumer_id in (:cust_ids) group by bookingType",
					Object[].class).setParameter("cust_ids", cust_ids);

			List<Object[]> resultList = query.getResultList();
			report = new ArrayList<Reports>(resultList.size());
			for (Object[] result : resultList) {
				Reports info = new Reports();
				info.setCount((Long) result[0]);
				info.setType((String) result[1]);
				info.setTotal(Double.valueOf(String.valueOf(result[2])));
				report.add(info);
			}
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return report;
	}
}
