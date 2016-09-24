package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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
import com.jyothigas.app.model.Mail;
import com.jyothigas.app.model.Reports;
import com.jyothigas.app.model.SMS;
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

	@Autowired
	EmailService emailService;

	/**
	 * Method for Insert booking data
	 *
	 * @param booking
	 */
	/*
	 * =======> NOTE : THIS API ASSOCIATED WITH THREE CALLS <======= PLEASE BE
	 * CAREFULL BEFORE CHNAGING ANYTHING IN THIS
	 */
	public Booking bookProduct(Booking booking) {
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
			bookingEntity.setCreated_date(new Date());
			bookingEntity.setReference(refToken);
			bookingEntity.setStatus("PENDING");
			BookingEntity bookingEntityObj = bookingDAO.merge(bookingEntity);

			// Saving AppliancesIds to booking
			if (booking.getApplianceIds() != null && !booking.getApplianceIds().isEmpty()) {
				List<ApplianceBookingEntity> applianceEntityList = createAppliaceBookingEntity(
						booking.getApplianceIds(), bookingEntityObj.getId());
				if (applianceBookingDAO.addApplianceBooking(applianceEntityList) == 0) {
					bookingDAO.remove(bookingEntityObj);
					throw new Exception("Error while saving appliances.");
				}
			}

			// Send Notification to Customer
			ConsumerEntity consumerEntity = consumerDAO.findById(ConsumerEntity.class, booking.getConsumer_id());
			RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class,
					consumerEntity.getReg_id());
			RegistrationEntity dealerEntity = registrationDAO.findById(RegistrationEntity.class,
					registrationEntity.getId());
			String mesg = Constant.BOOKING_MESSAGE.replace("{REF}", refToken).replace("{NAME}",
					registrationEntity.getName());
			smsService.sendMessage(mesg + dealerEntity.getContactNo(), registrationEntity.getContactNo());
			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put("REFERENCE", refToken);
			tokenMap.put("NAME", registrationEntity.getName());
			tokenMap.put("PRICE", String.valueOf(booking.getTotal()));
			tokenMap.put("DEALER_NAME", dealerEntity.getName());
			tokenMap.put("DEALER_NO", dealerEntity.getContactNo());
			sendNotificationEmail(booking.getBookingType(), registrationEntity.getEmail(), tokenMap);

			// Send Notification to Dealer
			sendEmailToDealer(refToken, registrationEntity.getEmail(), dealerEntity.getEmail(), dealerEntity.getName(),
					registrationEntity.getContactNo(), booking.getBookingType());
			// sendSMSToDealer(refToken, registrationEntity.getEmail(),
			// dealerEntity.getDealer_contact_no(),
			// dealerEntity.getDealer_name(), registrationEntity.getContactNo(),
			// booking.getBookingType());
			BeanUtils.copyProperties(bookingEntityObj, bookingObj);
		} catch (Exception e) {
			logger.error("Error in Inserting booking data...");
			e.printStackTrace();
		}
		return bookingObj;
	}

	private void sendSMSToDealer(String refToken, String CustEmail, String DealerNo, String dealerName, String custNO,
			String BookingType) {

		SMS sms = new SMS();
		sms.setPhoneNumber(DealerNo);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("DEALER_NAME", dealerName);
		valueMap.put("CUST_EMAIL", CustEmail);
		valueMap.put("ENTITY", BookingType);
		valueMap.put("REFERENCE", refToken);
		valueMap.put("CUST_PH", custNO);
		sms.setTemplate(SMSService.BOOKING_MSG_DEALER);
		sms.setValueMap(valueMap);
		smsService.sendSMS(sms);

	}

	private void sendNotificationEmail(String bookingType, String EmailTo, Map<String, String> refillMap)
			throws MailException, InterruptedException {
		Mail mail = new Mail();
		if (bookingType.equals("REFILL")) {
			mail.setTemplateName(EmailService.EMAIL_BOOKING_REFILL);
			mail.setMailTo(EmailTo);
			Map<String, String> valueMap = new HashMap<String, String>();
			valueMap.put("REFERENCE", refillMap.get("REFERENCE"));
			valueMap.put("NAME", refillMap.get("NAME"));
			valueMap.put("PRICE", refillMap.get("PRICE"));
			valueMap.put("DEALER_NAME", refillMap.get("DEALER_NAME"));
			valueMap.put("DEALER_NO", refillMap.get("DEALER_NO"));
			Calendar currentTime = Calendar.getInstance();
			if (currentTime.get(Calendar.HOUR_OF_DAY) > 20) {
				valueMap.put("TIME_MSG",
						"Since your booking is recorded after 8PM, the cylinder will be delivered tomorrow.");
			} else {
				valueMap.put("TIME_MSG", "Your Cylinder will be delivered in 6hrs from booking.");
			}
			mail.setValueMap(valueMap);
		} else {
			mail.setTemplateName(EmailService.EMAIL_BOOKING);
			mail.setMailTo(EmailTo);
			Map<String, String> valueMap = new HashMap<String, String>();
			valueMap.put("REFERENCE", refillMap.get("REFERENCE"));
			valueMap.put("NAME", refillMap.get("NAME"));
			mail.setValueMap(valueMap);
		}

		emailService.sendMail(mail);
	}

	private void sendEmailToDealer(String ref, String custEmail, String EmailTo, String name, String custNo,
			String entity) throws MailException, InterruptedException {
		Mail mail = new Mail();
		mail.setTemplateName(EmailService.EMAIL_BOOKING_DEALER);
		mail.setMailTo(EmailTo);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("REFERENCE", ref);
		valueMap.put("NAME", name);
		valueMap.put("CUST_PH", custNo);
		valueMap.put("CUST_EMAIL", custEmail);
		valueMap.put("ENTITY", entity);
		mail.setValueMap(valueMap);

		emailService.sendMail(mail);
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
	public int updateProductBooking(Booking booking) {
		logger.info("Update booking data...");
		int result = 0;
		try {
			BookingEntity bookingEntity = bookingDAO.findById(BookingEntity.class, booking.getId());
			bookingEntity.setStatus("IN PROGRESS");
			bookingEntity.setUpdated_date(new Date());
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
	 * Method for fetch booking details by status
	 *
	 * @param booking
	 * @return
	 */
	public List<Booking> findByDealerOrDistId(int bookToId) {
		logger.info("fetchBookingsByStatus...");
		List<Booking> bookingList = new ArrayList<Booking>();
		try {
			List<BookingEntity> bookingEntityList = bookingDAO.findByDealerOrDistId(bookToId);
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
			System.out.println("--> " + bookingEntityList.size());
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
	 * Method fetch all booking details
	 *
	 * @param booking
	 * @return
	 */
	// REPORT-1-DEALER
	public int findCylinderBookedWithDealerFY(int year, int dealerId) {
		logger.info("findAllBookings...");
		try {
			Calendar fromdate = Calendar.getInstance();
			Calendar todate = Calendar.getInstance();
			fromdate.set(Calendar.MONTH, Calendar.MARCH);
			fromdate.set(Calendar.DAY_OF_MONTH, 31);
			fromdate.set(Calendar.YEAR, year);

			todate.set(Calendar.MONTH, Calendar.MARCH);
			todate.set(Calendar.DAY_OF_MONTH, 31);
			todate.set(Calendar.YEAR, year + 1);
			return bookingDAO.findCylinderBookedWithDealerFY(fromdate.getTime(), todate.getTime(), dealerId);

		} catch (Exception e) {
			logger.error("Error in findAllBookings");
			e.printStackTrace();
		}
		return 0;
	}

	// Report -2 -DEALER
	public Map<String, Integer> findCylinderSoldProductWiseFY(int year, int typeId, int dealerId) {
		logger.info("findAllBookings...");
		Map<String, Integer> reportMap = new HashMap<String, Integer>();
		try {
			Calendar fromdate = Calendar.getInstance();
			Calendar todate = Calendar.getInstance();
			fromdate.set(Calendar.MONTH, Calendar.MARCH);
			fromdate.set(Calendar.DAY_OF_MONTH, 31);
			fromdate.set(Calendar.YEAR, year);

			todate.set(Calendar.MONTH, Calendar.MARCH);
			todate.set(Calendar.DAY_OF_MONTH, 31);
			todate.set(Calendar.YEAR, year + 1);
			Map<Integer, Integer> bookingData = bookingDAO.findCylinderSoldProductWiseFY(fromdate.getTime(),
					todate.getTime(), typeId, dealerId);
			if (bookingData.size() > 0) {
				System.out.println(" MAP > 0 ");
				Set<Integer> productId = bookingData.keySet();
				for (Integer id : productId) {
					System.out.println(" Set " + id);
					ConnectionTypeEntity product = connectionTypeDao.findById(ConnectionTypeEntity.class, id);
					if (null != product) {
						System.out.println(" List " + product.getConnectionType() + " - " + bookingData.get(id));
						reportMap.put(product.getConnectionType(), bookingData.get(id));
					}
				}

			}

		} catch (Exception e) {
			logger.error("Error in findAllBookings");
			e.printStackTrace();
		}
		return reportMap;
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

	// Reporting
	public Map<String, Integer> cylinderBookBYeachCustForFY(int year, int userId) {

		Calendar fromdate = Calendar.getInstance();
		Calendar todate = Calendar.getInstance();
		fromdate.set(Calendar.MONTH, Calendar.MARCH);
		fromdate.set(Calendar.DAY_OF_MONTH, 31);
		fromdate.set(Calendar.YEAR, year);

		todate.set(Calendar.MONTH, Calendar.MARCH);
		todate.set(Calendar.DAY_OF_MONTH, 31);
		todate.set(Calendar.YEAR, year + 1);
		Map<String, Integer> custMap = new HashMap<String, Integer>();
		Map<Integer, Integer> customerBooking = bookingDAO.findCylinderSoldFYbyCustId(fromdate.getTime(),
				todate.getTime(), userId);
		for (int regId : customerBooking.keySet()) {
			System.out.println(regId);
			RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class, regId);
			if (null != registrationEntity)
				custMap.put(registrationEntity.getEmail(), customerBooking.get(regId));
		}
		return custMap;
	}

	// Report report for Dealer
	public List<Reports> salesReportForDealer(int dealerId, int year) {
		Calendar fromdate = Calendar.getInstance();
		Calendar todate = Calendar.getInstance();
		fromdate.set(Calendar.MONTH, Calendar.MARCH);
		fromdate.set(Calendar.DAY_OF_MONTH, 31);
		fromdate.set(Calendar.YEAR, year);

		todate.set(Calendar.MONTH, Calendar.MARCH);
		todate.set(Calendar.DAY_OF_MONTH, 31);
		todate.set(Calendar.YEAR, year + 1);
		List<Reports> customerBooking = bookingDAO.salesReportForDealer(dealerId, fromdate.getTime(), todate.getTime());
		return customerBooking;
	}

}
