/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.dao;

import com.jyothigas.app.entity.DealerApplianceEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rprashar
 */
@Repository
public class DealerApplianceBookingDAO extends JyothiGasDAO<DealerApplianceEntity> {

    private static final Log log = LogFactory.getLog(DealerApplianceBookingDAO.class);
    public int addApplianceBooking(List<DealerApplianceEntity> appliance) {
        int ststus = 0;
        try {
            for (DealerApplianceEntity entity : appliance) {
                entityManager.persist(entity);
            }
            ststus = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ststus;
    }

    public List<DealerApplianceEntity> getApplianceBooking(int bookingId) {
        List<DealerApplianceEntity> applianceIdList = new ArrayList<DealerApplianceEntity>();
        try {
            applianceIdList = entityManager
                    .createQuery("select s from DealerApplianceEntity s Where s.bookingId = :bookingId",
                            DealerApplianceEntity.class)
                    .setParameter("bookingId", bookingId).getResultList();
            log.info("get successfull");
        } catch (Exception e) {
            log.error("Failed : " + e);
            e.printStackTrace();
        }
        return applianceIdList;
    }
}
