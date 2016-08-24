package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Booking")
public class BookingEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -205671590628944910L;
	private int id;
	private int consumer_id;
	private Date booking_date;
	private Date date_of_deleivery;
	private Date last_issue;
	private Date last_deleivery;
	private int qunatity;
	private int connectionTypeId;
	private String status;
	private String reference;

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

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Cust_Id")
	public int getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Booking_Date")
	public Date getBooking_date() {
		return booking_date;
	}

	public void setBooking_date(Date booking_date) {
		this.booking_date = booking_date;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Date_Of_Delivery")
	public Date getDate_of_deleivery() {
		return date_of_deleivery;
	}

	public void setDate_of_deleivery(Date date_of_deleivery) {
		this.date_of_deleivery = date_of_deleivery;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Last_Date_Of_Issue")
	public Date getLast_issue() {
		return last_issue;
	}

	public void setLast_issue(Date last_issue) {
		this.last_issue = last_issue;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Last_Date_Of_Delivery")
	public Date getLast_deleivery() {
		return last_deleivery;
	}

	public void setLast_deleivery(Date last_deleivery) {
		this.last_deleivery = last_deleivery;
	}

	@Column(name = "Quantity")
	public int getQunatity() {
		return qunatity;
	}

	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}

	@Column(name = "Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "Connection_Type_Id")
	public int getConnectionTypeId() {
		return connectionTypeId;
	}

	public void setConnectionTypeId(int connectionTypeId) {
		this.connectionTypeId = connectionTypeId;
	}

	@Column(name = "Reference")
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

	@Column(name = "Booking_Type")
	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

}
