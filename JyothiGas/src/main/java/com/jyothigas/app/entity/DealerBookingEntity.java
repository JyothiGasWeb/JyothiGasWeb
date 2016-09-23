/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jyothigas.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rprashar
 */
@Entity
@Table(name = "Dealer_Distributor_Booking")
public class DealerBookingEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -205671590628944910L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
    @Column(name = "User_Id")
    private int user_id;
    @Temporal(TemporalType.DATE)
    @Column(name = "Booking_Date")
    private Date booking_date;
    @Temporal(TemporalType.DATE)
    @Column(name = "Date_Of_Delivery")
    private Date date_of_deleivery;
    @Temporal(TemporalType.DATE)
    @Column(name = "Last_Date_Of_Issue")
    private Date last_issue;
    @Temporal(TemporalType.DATE)
    @Column(name = "Last_Date_Of_Delivery")
    private Date last_deleivery;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Status")
    private String status;
    @Column(name = "Reference")
    private String reference;
    @Column(name = "UserType")
    private String userType;
    @Column(name = "Created_Date")
    private Date created_date;
    @Column(name = "Updated_Date")
    private Date updated_date;

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    @Column(name = "Booking_Type")
    private String bookingType;

    @Column(name = "Registration_charges")
    private double registration_charges;

    @Column(name = "Regulator_charges")
    private double regulator_charges;

    @Column(name = "GasRefill_charges")
    private double gasRefill_charges;

    @Column(name = "Handling_charges")
    private double handling_charges;

    @Column(name = "Delievery_charges")
    private double delievery_charges;

    @Column(name = "Total")
    private double total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getRegistration_charges() {
        return registration_charges;
    }

    public void setRegistration_charges(double registration_charges) {
        this.registration_charges = registration_charges;
    }

    public double getRegulator_charges() {
        return regulator_charges;
    }

    public void setRegulator_charges(double regulator_charges) {
        this.regulator_charges = regulator_charges;
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

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
