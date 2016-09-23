/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.dao;

import com.jyothigas.app.entity.DealerBookingEntity;
import com.jyothigas.app.model.Reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rprashar
 */
@Repository
public class DealerBookingDAO extends JyothiGasDAO<DealerBookingEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(DealerBookingDAO.class);

	public List<DealerBookingEntity> findAllBookings() {
		log.info("Getting bookingEntity Instance");
		List<DealerBookingEntity> bookingEntity = new ArrayList<DealerBookingEntity>();
		try {
			bookingEntity = entityManager.createQuery("select s from DealerBookingEntity s ", DealerBookingEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return bookingEntity;
	}

	// Report-1-DEALER - • Number of Cylinder booked for the financial year 'BY'
	// Dealer
	public List<Reports> getPurchaseReport(Date fromDate, Date toDate, int userId) {
		log.info("Getting bookingEntity Instance");
		List<Reports> report = new ArrayList<Reports>();
		try {
			List<Object[]> object = entityManager
					.createQuery(
							"select SUM(s.quantity),bookingType,SUM(total) from DealerBookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate "
									+ "and s.status='DELIVERED' and s.user_id=:userId group by bookingType",
							Object[].class)
					.setParameter("userId", userId).setParameter("fromDate", fromDate).setParameter("toDate", toDate)
					.getResultList();
			for (Object[] result : object) {
				System.out.println((Long) result[0]);
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

	public int purchaseReportDealer(Date fromDate, Date toDate) {
		log.info("Getting bookingEntity Instance");
		int count = 0;
		try {
			count = entityManager.createQuery(
					"select s from DealerBookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.userType='DEALER'",
					DealerBookingEntity.class).getResultList().size();
			log.info("get successfull");

		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return count;
	}

	// Report for   Number of Cylinder booked for the financial year by
	// Distributor
	public int noOfCylinderBookedFYbyDistributor(Date fromDate, Date toDate, int userId) {
		log.info("Getting bookingEntity Instance");
		int count = 0;
		try {
			count = entityManager.createQuery(
					"select s from DealerBookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.userType='DISTRIBUTOR' and s.user_id=:userId",
					DealerBookingEntity.class).setParameter("userId", userId).getResultList().size();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
			e.printStackTrace();
		}
		return count;
	}
}
