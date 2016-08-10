package com.jyothigas.app.service;

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

	private int id;
	private int consumer_id;
	private Date booking_date;
	private Date date_of_deleivery;
	private Date last_issue;
	private Date last_deleivery;
	private int qunatity;
	private String product_type;
	private String status;

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

	@Column(name = "Product_Type")
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "Status")
	public String getStatus() {
		return status;
	}

	public String getProduct_type() {
		return product_type;
	}

}
