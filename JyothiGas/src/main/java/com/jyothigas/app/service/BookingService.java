package com.jyothigas.app.service;

import java.math.BigDecimal;
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

import com.jyothigas.app.dao.ApplianceBookingDAO;
import com.jyothigas.app.dao.BookingDAO;
import com.jyothigas.app.dao.ConnectionTypeDAO;
import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.entity.ApplianceBookingEntity;
import com.jyothigas.app.entity.BookingEntity;
import com.jyothigas.app.entity.ConnectionTypeEntity;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.model.ApplianceBooking;
import com.jyothigas.app.model.Appliances;
import com.jyothigas.app.model.Booking;
import com.jyothigas.app.model.OrderDetail;
import com.jyothigas.utils.Constant;
import com.jyothigas.utils.OTPUtil;

@Service("bookingService")
@Transactional
public class BookingService {

	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

	@Autowired
	BookingDAO bookingDAO;

	@Autowired
	SMSService smsService;

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	ConsumerDAO consumerDAO;

	@Autowired
	ConnectionTypeDAO connectionTypeDao;

	@Autowired
	ApplianceBookingDAO applianceBookingDAO;

	@Autowired
	CommonService commonService;

	/**
	 * Method for Insert booking data
	 * 
	 * @param booking
	 */
	public Booking insertBookingCylinder(Booking booking) {
		logger.info("Inserting booking data...");
		Booking bookingObj = new Booking();
		String refToken = String.valueOf(OTPUtil.generateIntToken());
		BookingEntity bookingEntity = new BookingEntity();
		try {
			BeanUtils.copyProperties(booking, bookingEntity);
			bookingEntity.setBooking_date(new Date());
			if (checkDeliveryDate().before(new Date())) {
				bookingEntity.setDate_of_deleivery(new Date());
			} else {
				bookingEntity.setDate_of_deleivery(getDeliveryDate());
			}
			bookingEntity.setReference(refToken);
			bookingEntity.setStatus("PENDING");
			BookingEntity bookingEntityObj = bookingDAO.merge(bookingEntity);

			// Saving AppliancesIds to booking
			List<ApplianceBookingEntity> applianceEntityList = createAppliaceBookingEntity(booking.getApplianceIds(),
					bookingEntityObj.getId());
			if (applianceBookingDAO.addApplianceBooking(applianceEntityList) == 0) {
				bookingDAO.remove(bookingEntityObj);
				throw new Exception("Error while saving appliances; Rollingback all Booking transactions");
			}

			ConsumerEntity consumerEntity = consumerDAO.findById(ConsumerEntity.class, booking.getConsumer_id());
			RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class,
					consumerEntity.getReg_id());
			// Send reference to mobile
			smsService.sendMessage(Constant.BOOKING_MESSAGE + refToken, registrationEntity.getContactNo());

			BeanUtils.copyProperties(bookingEntityObj, bookingObj);
		} catch (Exception e) {
			logger.error("Error in Inserting booking data...");
			e.printStackTrace();
		}
		return bookingObj;
	}

	// Will create applianceBooking Entity
	private List<ApplianceBookingEntity> createAppliaceBookingEntity(List<ApplianceBooking> applianceIds,
			int bookingId) {
		List<ApplianceBookingEntity> entityList = new ArrayList<ApplianceBookingEntity>();
		for (ApplianceBooking appliance : applianceIds) {
			ApplianceBookingEntity entity = new ApplianceBookingEntity();
			entity.setApplianceId(appliance.getApplianceId());
			entity.setBookingId(bookingId);
			entity.setQuantity(appliance.getQuantity());
			entityList.add(entity);
		}
		return entityList;
	}

	/**
	 * Method for update booking status
	 * 
	 * @param booking
	 */
	public int updateBookingCylinderStatus(Booking booking) {
		logger.info("Update booking data...");
		int result = 0;
		try {
			BookingEntity bookingEntity = bookingDAO.findById(BookingEntity.class, booking.getId());
			bookingEntity.setStatus("IN PROGRESS");
			bookingDAO.merge(bookingEntity);
			result = 1;
		} catch (Exception e) {
			result = 0;
			logger.error("Error in Update booking data...");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method for fetch booking details by status
	 * 
	 * @param booking
	 * @return
	 */
	public List<Booking> fetchBookingsByStatus(Booking booking) {
		logger.info("fetchBookingsByStatus...");
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			List<BookingEntity> bookingEntityList = bookingDAO.findByStatus(booking.getStatus());
			for (BookingEntity bookingEntity : bookingEntityList) {
				Booking bookingObj = new Booking();
				BeanUtils.copyProperties(bookingEntity, bookingObj);
				bookingEntity.getId();
				List<Appliances> applianceList = commonService.getApplianceByBookingId(bookingEntity.getId());
				bookingObj.setAppliancesObj(applianceList);
				bookingList.add(bookingObj);
			}
		} catch (Exception e) {
			logger.error("Error in fetchBookingsByStatus");
			e.printStackTrace();
		}
		return bookingList;
	}

	/**
	 * Method for fetch booking details by consumer
	 * 
	 * @param booking
	 * @return
	 */
	public List<Booking> findByConsumerId(Booking booking) {
		logger.info("findByConsumerId...");
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			List<BookingEntity> bookingEntityList = bookingDAO.findByConsumerId(booking.getConsumer_id());
			for (BookingEntity bookingEntity : bookingEntityList) {
				Booking bookingObj = new Booking();
				BeanUtils.copyProperties(bookingEntity, bookingObj);
				List<Appliances> applianceList = commonService.getApplianceByBookingId(bookingEntity.getId());
				bookingObj.setAppliancesObj(applianceList);
				bookingList.add(bookingObj);
			}
		} catch (Exception e) {
			logger.error("Error in findByConsumerId");
			e.printStackTrace();
		}
		return bookingList;
	}

	/**
	 * Method for fetch booking details by Connection type
	 * 
	 * @param booking
	 * @return
	 */
	public List<Booking> findByConnectionTypeId(Booking booking) {
		logger.info("findByConnectionTypeId...");
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			List<BookingEntity> bookingEntityList = bookingDAO.findByConnectionTypeId(booking.getConnectionTypeId());
			for (BookingEntity bookingEntity : bookingEntityList) {
				Booking bookingObj = new Booking();
				BeanUtils.copyProperties(bookingEntity, bookingObj);
				List<Appliances> applianceList = commonService.getApplianceByBookingId(bookingEntity.getId());
				bookingObj.setAppliancesObj(applianceList);
				bookingList.add(bookingObj);
			}
		} catch (Exception e) {
			logger.error("Error in findByConnectionTypeId");
			e.printStackTrace();
		}
		return bookingList;
	}

	/**
	 * Method for fetch booking details by status and custid
	 * 
	 * @param booking
	 * @return
	 */
	public OrderDetail findInProgressOrderDetail(int bookingId) {
		logger.info("findInProgressOrderDetail...");
		OrderDetail order = null;
		try {
			// Step-1 get booking detail
			BookingEntity bookingEntity = bookingDAO.findInProgressOrderDetail(bookingId);
			System.out.println(bookingEntity.getConnectionTypeId());
			// Step-2 get connection type detail
			ConnectionTypeEntity connectionType = connectionTypeDao.findById(ConnectionTypeEntity.class,
					bookingEntity.getConnectionTypeId());
			System.out.println(connectionType);
			// Step-3 make booking details for UI
			order = createOrderDetail(connectionType, bookingEntity);
		} catch (Exception e) {
			logger.error("Error in findByConnectionTypeId");
			e.printStackTrace();
		}
		return order;
	}

	/**
	 * Method fetch all booking details
	 * 
	 * @param booking
	 * @return
	 */
	public List<Booking> findAllBookings() {
		logger.info("findAllBookings...");
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			List<BookingEntity> bookingEntityList = bookingDAO.findAllBookings();
			for (BookingEntity bookingEntity : bookingEntityList) {
				Booking bookingObj = new Booking();
				BeanUtils.copyProperties(bookingEntity, bookingObj);
				List<Appliances> applianceList = commonService.getApplianceByBookingId(bookingEntity.getId());
				bookingObj.setAppliancesObj(applianceList);
				bookingList.add(bookingObj);
			}
		} catch (Exception e) {
			logger.error("Error in findAllBookings");
			e.printStackTrace();
		}
		return bookingList;
	}

	/**
	 * Method for check booking DateTime (Before 6 PM)
	 * 
	 * @return
	 */
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

	/**
	 * Method for get Delivery Date
	 * 
	 * @return
	 */
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

	// For now only for cylinder
	private OrderDetail createOrderDetail(ConnectionTypeEntity connectionType, BookingEntity entity) {
		OrderDetail order = new OrderDetail();
		order.setQunatity(entity.getQunatity());
		order.setBooking_date(entity.getBooking_date());
		BigDecimal price = new BigDecimal(String.valueOf(entity.getQunatity() * connectionType.getConnectionPrice()));
		order.setConnectionPrice(price);
		order.setConnectionDesc(connectionType.getConnectionDesc());
		order.setConnectionType(connectionType.getConnectionType());
		order.setConsumer_id(entity.getConsumer_id());
		order.setBooking_id(entity.getId());
		order.setLast_deleivery(entity.getLast_deleivery());
		order.setLast_issue(entity.getLast_issue());
		order.setStatus(entity.getStatus());
		BigDecimal delvCharges = new BigDecimal("120.00");
		order.setDeliveryCharges(delvCharges);
		BigDecimal hndlCharges = new BigDecimal("70.00");
		order.setHandlingCharges(hndlCharges);
		BigDecimal totalCharges = price.add(delvCharges).add(hndlCharges);
		order.setTotalCharges(totalCharges);
		return order;

	}
}
