/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.dao;

import com.jyothigas.app.entity.DealerBookingEntity;
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

    public int noOfCylinderBookedFYbyDealer(Date fromDate, Date toDate, int userId) {
        log.info("Getting bookingEntity Instance");
        int count = 0;
        try {
            count = entityManager.createQuery(
                    "select s from DealerBookingEntity s where s.created_date >=:fromDate and s.created_date <=:toDate and s.userType='DEALER' and s.user_id=:userId",
                    DealerBookingEntity.class).setParameter("userId", userId).getResultList().size();
            log.info("get successfull");
        } catch (Exception e) {
            log.error("Failed : " + e);
            e.printStackTrace();
        }
        return count;
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

//    Report for   Number of Cylinder booked for the financial year by Distributor
    public int noOfCylinderBookedFYbyDistributor(Date fromDate, Date toDate,int userId) {
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
