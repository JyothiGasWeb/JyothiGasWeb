/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.service;

import com.jyothigas.app.dao.DealerApplianceBookingDAO;
import com.jyothigas.app.dao.DealerBookingDAO;
import com.jyothigas.app.entity.ApplianceBookingEntity;
import com.jyothigas.app.entity.DealerApplianceEntity;
import com.jyothigas.app.entity.DealerBookingEntity;
import com.jyothigas.app.model.ApplianceBooking;
import com.jyothigas.app.model.Booking;
import com.jyothigas.utils.OTPUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rprashar
 */
@Service
@Transactional
public class DealerBookingService {

    @Autowired
    SMSService smsService;

    @Autowired
    EmailService emailService;

    @Autowired
    DealerBookingDAO dealerBookingDAO;

    @Autowired
    DealerApplianceBookingDAO dealerAppliacneDAO;

    private static final Logger logger = LoggerFactory.getLogger(DealerBookingService.class);

    public Booking bookProduct(Booking booking) {
        logger.info("Inserting booking data...");
        Booking bookingObj = new Booking();
        String refToken = String.valueOf(OTPUtil.generateIntToken());
        DealerBookingEntity bookingEntity = new DealerBookingEntity();
        try {
            BeanUtils.copyProperties(booking, bookingEntity);
            bookingEntity.setBooking_date(new Date());
            if (checkDeliveryDate().before(new Date())) {
                bookingEntity.setDate_of_deleivery(new Date());
            } else {
                bookingEntity.setDate_of_deleivery(getDeliveryDate());
            }
            bookingEntity.setCreated_date(new Date());
            bookingEntity.setReference(refToken);
            bookingEntity.setStatus("PENDING");
            DealerBookingEntity bookingEntityObj = dealerBookingDAO.merge(bookingEntity);

            // Saving AppliancesIds to booking
            if (booking.getApplianceIds() != null && !booking.getApplianceIds().isEmpty()) {
                List<DealerApplianceEntity> applianceEntityList = createAppliaceBookingEntity(
                        booking.getApplianceIds(), bookingEntityObj.getId());
                if (dealerAppliacneDAO.addApplianceBooking(applianceEntityList) == 0) {
                    dealerBookingDAO.remove(bookingEntityObj);
                    throw new Exception("Error while saving appliances for dealer");
                }
            }

            BeanUtils.copyProperties(bookingEntityObj, bookingObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingObj;
    }

//    Dealer for report
    public int noOfCylinderBookedFYbyDealer(int year,int userId) {
        int count = 0;
        try {
            Calendar fromdate = Calendar.getInstance();
            Calendar todate = Calendar.getInstance();
            fromdate.set(Calendar.MONTH, Calendar.MARCH);
            fromdate.set(Calendar.DAY_OF_MONTH, 31);
            fromdate.set(Calendar.YEAR, year);

            todate.set(Calendar.MONTH, Calendar.MARCH);
            todate.set(Calendar.DAY_OF_MONTH, 31);
            todate.set(Calendar.YEAR, year + 1);
            count = dealerBookingDAO.noOfCylinderBookedFYbyDealer(fromdate.getTime(), todate.getTime(),userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    
//Report for distributor
    public int noOfCylinderBookedFYbyDistributor(int year,int userId) {
        int count = 0;
        try {
            Calendar fromdate = Calendar.getInstance();
            Calendar todate = Calendar.getInstance();
            fromdate.set(Calendar.MONTH, Calendar.MARCH);
            fromdate.set(Calendar.DAY_OF_MONTH, 31);
            fromdate.set(Calendar.YEAR, year);

            todate.set(Calendar.MONTH, Calendar.MARCH);
            todate.set(Calendar.DAY_OF_MONTH, 31);
            todate.set(Calendar.YEAR, year + 1);
            count = dealerBookingDAO.noOfCylinderBookedFYbyDistributor(fromdate.getTime(), todate.getTime(),userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @SuppressWarnings("deprecation")
    private Date checkDeliveryDate() {
        Date date = null;
        try {
            date = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate(), 18, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date getDeliveryDate() {
        Calendar cal = null;
        try {
            cal = Calendar.getInstance();
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.DAY_OF_MONTH, 1); // adds one day
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();
    }

    private List<DealerApplianceEntity> createAppliaceBookingEntity(List<ApplianceBooking> applianceIds,
            int bookingId) {
        List<DealerApplianceEntity> entityList = new ArrayList<DealerApplianceEntity>();
        for (ApplianceBooking appliance : applianceIds) {
            DealerApplianceEntity entity = new DealerApplianceEntity();
            entity.setApplianceId(appliance.getApplianceId());
            entity.setBookingId(bookingId);
            entity.setQuantity(appliance.getQuantity());
            entityList.add(entity);
        }
        return entityList;
    }

}
