package com.jyothigas.utils;

public class Constant {

    public static final String REGISTER = "/register";
    public static final String REGISTER_DEALER = "/registerDealer";
    public static final String SURRENDER = "/surrenderConnection";
    public static final String ROLES = "/getRoles";
    public static final String GET_OTP = "/getOtp";
    public static final String PHONE_VERIFICATION = "/verifyMobileNo";
    public static final String LOGIN = "/login";
    public static final String GET_CONSUMER_DETAILS = "/getConsumerDetails";

    public static final String GET_USER_LIST = "/getUserList";
    public static final String UPDATE_CONSUMER = "/updateConsumer";
    public static final String UPDATE_ADDRESS = "/updateAddress";
    public static final String UPDATE_DEALER = "/updateDealer";
    public static final String GET_APPLIANCES = "/getAppliances";
    public static final String GET_PRODUCT_BY_TYPE = "/getProductByType";
    public static final String ADD_APPLIANCES = "/addAppliance";
    public static final String REMOVE_APPLIANCES = "/removeAppliance";
    public static final String BOOK_CONNECTION = "/bookConnection";
    public static final String BOOK_APPLIANCES = "/bookAppliances";
    public static final String BOOK_DEALER_APPLIANCES = "/bookDealerAppliances";
    public static final String BOOK_DISTRIBUTOR_APPLIANCES = "/bookDistributorAppliances";
    public static final String BOOK_REFILL = "/bookRefill";
    public static final String BOOK_DEALER_REFILL = "/bookDealerRefill";
    public static final String BOOK_DISTRIBUTOR_REFILL = "/bookDistributorRefill";
    public static final String GET_BOOKING_BY_STATUS = "/getBookingByStatus";
    public static final String GET_PENDING_BOOKING = "/getPendingBooking";
    public static final String UPDATE_BOOKING = "/updateBooking";
    public static final String GET_BOOKING_BY_CONSUMER = "/getBookingByConsumer";
    public static final String GET_BOOKING_BY_CONNECTION_TYPE = "/getBookingByConnectionType";
    public static final String GET_ALL_BOOKINGS = "/getAllBookings";
    public static final String GET_IN_PROGRESS_BOOKING = "/getInprogressBooking";
    public static final String GET_CONNECTION_TYPE = "/getConnectionType";
    public static final String GET_CONNECTION_BY_TYPE = "/getConnectionsByType";
    public static final String NEW_SMSOTP = "/getNewSMSOTP";
    public static final String GET_MECHANIC_BY_DEALER_ID = "/getMechanicByDealerID";
    public static final String ADD_MECHANIC_SERVICE = "/addMechanicService";
    public static final String UPDATE_MECHANIC_SERVICE = "/updateMechanicService";
    public static final String GET_ALL_NOTIFICATION = "/getAllNotification";
    public static final String KYC_UPLOAD = "/uploadFile";
    public static final String KYC_DOWNLOAD = "/downloadFile";
    public static final String GET_DOCUMENT_NAME = "/getDocumentDetail";
    public static final String UPLOAD_IMAGE = "/uploadProfileImage";
    public static final String GET_DEALER_SUBREGION = "/getDealerSubRegions";

    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String STATUS_SURRENDER = "SURRENDERED";
    public static final String STATUS_TRANSFERRED = "TRANSFERRED";
    public static final String BOOKING_MESSAGE = "Dear {NAME}, Thanks for booking with us, your booking reference no. is {REF}.For more details you can contact your dealer at ";

    public static final String KYC = "KYC";
    public static final String PROFILE_PIC = "PROFILE_PIC";

    // DEALER
    public static final String GET_FY_CYLINDER_BOOKING = "/getCylinderBookingsFY";
    public static final String GET_FY_CYLINDER_BOOKING_CUST = "/getCylinderBookingsCustomerFY";
    public static final String GET_FY_CYLINDER_SOLD = "/getCylinderSoldFY";
    public static final String GET_SALES_REPORT = "/getSalesReportDealer";
    public static final String GET_PURCHASE_REPORT = "/getPurchaseReport";
    public static final String GET_FY_REPORT = "/getFYreport";
    // Not Working API's
    public static final String FORGOT_PASSWORD = "/forgotPassword";

    public static final String OLD_DEALER_EMAIL = "You are no longer dealer of {EMAIL}, as customer has changed his dealer.";
    public static final String NEW_DEALER_EMAIL = "Customer {EMAIL} has assigned you as his dealer";
    public static final String NEW_CUSTOMER_EMAIL = "You are successfully registered with Jyoti Gas, your registeration number is ";

}
