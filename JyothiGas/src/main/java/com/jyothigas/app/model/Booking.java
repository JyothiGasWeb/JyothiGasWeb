package com.jyothigas.app.model;

import java.util.Date;
import java.util.List;

public class Booking implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2242391092920269445L;
    private int id;
    private int consumer_id;
    private int user_id;
    private Date booking_date;
    private Date date_of_deleivery;
    private Date last_issue;
    private Date last_deleivery;
    private int quantity;
    private int connectionTypeId;
    private String status;
    private String reference;
    private double gasRefill_charges;
    private double handling_charges;
    private double delievery_charges;
    private double total;
    private List<ApplianceBooking> applianceIds;
    private List<Appliances> appliancesObj;
    private String bookingType;
    private String userType;

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public Date getDate_of_deleivery() {
        return date_of_deleivery;
    }

    public void setDate_of_deleivery(Date date_of_deleivery) {
        this.date_of_deleivery = date_of_deleivery;
    }

    public Date getLast_issue() {
        return last_issue;
    }

    public void setLast_issue(Date last_issue) {
        this.last_issue = last_issue;
    }

    public Date getLast_deleivery() {
        return last_deleivery;
    }

    public void setLast_deleivery(Date last_deleivery) {
        this.last_deleivery = last_deleivery;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConnectionTypeId() {
        return connectionTypeId;
    }

    public void setConnectionTypeId(int connectionTypeId) {
        this.connectionTypeId = connectionTypeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<ApplianceBooking> getApplianceIds() {
        return applianceIds;
    }

    public void setApplianceIds(List<ApplianceBooking> applianceIds) {
        this.applianceIds = applianceIds;
    }

    public List<Appliances> getAppliancesObj() {
        return appliancesObj;
    }

    public void setAppliancesObj(List<Appliances> appliancesObj) {
        this.appliancesObj = appliancesObj;
    }

    public double getGasRefill_charges() {
        return gasRefill_charges;
    }

    public void setGasRefill_charges(double gasRefill_charges) {
        this.gasRefill_charges = gasRefill_charges;
    }

    public double getHandling_charges() {
        return handling_charges;
    }

    public void setHandling_charges(double handling_charges) {
        this.handling_charges = handling_charges;
    }

    public double getDelievery_charges() {
        return delievery_charges;
    }

    public void setDelievery_charges(double delievery_charges) {
        this.delievery_charges = delievery_charges;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
