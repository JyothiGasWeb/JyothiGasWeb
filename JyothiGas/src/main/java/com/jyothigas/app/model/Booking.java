package com.jyothigas.app.model;

import java.util.Date;

public class Booking implements java.io.Serializable {

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

	public int getQunatity() {
		return qunatity;
	}

	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
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

}
