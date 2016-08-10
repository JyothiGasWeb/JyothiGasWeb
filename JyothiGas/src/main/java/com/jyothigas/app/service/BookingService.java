package com.jyothigas.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.BookingDAO;
import com.jyothigas.app.model.Booking;

@Service("bookingService")
@Transactional
public class BookingService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

	@Autowired
	BookingDAO bookingDAO;

	public void insertBookingCylinder(Booking booking){
		BookingEntity bookingEntity = new BookingEntity();
		BeanUtils.copyProperties(booking, bookingEntity);
		
	}
}
