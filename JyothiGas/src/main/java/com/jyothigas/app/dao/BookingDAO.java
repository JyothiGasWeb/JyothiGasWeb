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

	// Report-1-DEALER - • Number of Cylinder booked for the financial year
	// 'WITH' Dealer
	public int findCylinderBookedWithDealerFY(Date fromDate, Date toDate, int dealerId) {
		log.info("Getting bookingEntity Instance");
		int bookingCount = 0;
		try {
			List<Object[]> count = entityManager
					.createQuery(
							"select SUM(s.quantity) from BookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.bookingType='REFILL' and s.dealerId=:dealer",
							Object[].class)
					.setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("dealer", dealerId)
					.getResultList();
			bookingCount = Integer.parseInt(String.valueOf(count.get(0)));
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return bookingCount;
	}

	// Report -3
	public Map<Integer, Integer> findCylinderSoldFYbyCustId(Date fromDate, Date toDate, int userId) {
		log.info("Getting bookingEntity Instance");
		Map<Integer, Integer> report = new HashMap<Integer, Integer>();
		try {
			List<Object[]> object = entityManager
					.createQuery(
							"select SUM(s.quantity),s.consumer_id from BookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.status ='DELIVERED' and s.bookingType='REFILL' and s.dealerId =:userId group by consumer_id",
							Object[].class)
					.setParameter("fromDate", fromDate).setParameter("userId", userId).setParameter("toDate", toDate)
					.getResultList();
			for (int i = 0; i < object.size(); i++) {
				// key -> ConnectionTypeID
				// Value -> Count
				report.put(Integer.parseInt(String.valueOf(object.get(i)[1])),
						Integer.parseInt(String.valueOf(object.get(i)[0])));
			}

			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return report;
	}

	// REPORT -2 - Number of Cylinder booked for the financial year
	public Map<Integer, Integer> findCylinderSoldProductWiseFY(Date fromDate, Date toDate, int connectionType,
			int dealerId) {
		log.info("Getting bookingEntity Instance");
		Map<Integer, Integer> booking = new HashMap<Integer, Integer>();
		try {
			List<Object[]> object = entityManager
					.createQuery(
							"select SUM(quantity),connectionTypeId from BookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate "
									+ "and s.status ='DELIVERED' and s.bookingType='REFILL' and s.connectionTypeId=:connectionType and s.dealerId=:dealerId"
									+ " group by connectionTypeId",
							Object[].class)
					.setParameter("fromDate", fromDate).setParameter("connectionType", connectionType)
					.setParameter("dealerId", dealerId).setParameter("toDate", toDate).getResultList();
			for (int i = 0; i < object.size(); i++) {
				// key -> ConnectionTypeID
				// Value -> Count
				booking.put(Integer.parseInt(String.valueOf(object.get(i)[1])),
						Integer.parseInt(String.valueOf(object.get(i)[0])));
			}
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return booking;
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
	
	public List<BookingEntity> findByDealerOrDistId(int bookToId) {
		log.info("Getting bookingEntity Instance with status: " + bookToId);
		List<BookingEntity> bookingEntity = new ArrayList<BookingEntity>();
		try {
			bookingEntity = entityManager
					.createQuery("select s from BookingEntity s Where s.status = 'PENDING' and s.dealerDistributorId =:bookToId", BookingEntity.class)
					.setParameter("bookToId", bookToId).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	@SuppressWarnings("unchecked")
	public List<Reports> salesReportForDealer(Integer userId, Date fromDate, Date toDate) {
		log.info("Getting bookingEntity Instance");
		List<Reports> report = null;
		try {

			Query query = entityManager
					.createQuery(
							"SELECT  SUM(quantity),bookingType,dealerId FROM BookingEntity s where status='DELIVERED' and s.created_date >=:fromDate and s.created_date <=:toDate and s.dealerId =:userId group by bookingType,dealerId",
							Object[].class)
					.setParameter("userId", userId).setParameter("fromDate", fromDate).setParameter("toDate", toDate);

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
