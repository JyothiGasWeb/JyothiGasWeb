package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.ApplianceBookingEntity;

@Repository
public class ApplianceBookingDAO extends JyothiGasDAO<ApplianceBookingEntity> {

    private static final Log log = LogFactory.getLog(ApplianceBookingDAO.class);

    public int addApplianceBooking(List<ApplianceBookingEntity> appliance) {
        int ststus = 0;
        try {
            for (ApplianceBookingEntity entity : appliance) {
                entityManager.persist(entity);
            }
            ststus = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ststus;
    }

    public List<ApplianceBookingEntity> getApplianceBooking(int bookingId) {
        List<ApplianceBookingEntity> applianceIdList = new ArrayList<ApplianceBookingEntity>();
        try {
            applianceIdList = entityManager
                    .createQuery("select s from ApplianceBookingEntity s Where s.bookingId = :bookingId",
                            ApplianceBookingEntity.class)
                    .setParameter("bookingId", bookingId).getResultList();
            log.info("get successfull");
        } catch (Exception e) {
            log.error("Failed : " + e);
            e.printStackTrace();
        }
        return applianceIdList;
    }
}
