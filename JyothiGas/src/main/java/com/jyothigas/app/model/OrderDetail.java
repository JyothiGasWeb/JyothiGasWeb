package com.jyothigas.app.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDetail {
	private int id;
	private int consumer_id;
	private Date booking_date;
	private Date last_issue;
	private Date last_deleivery;
	private String status;
	private int qunatity;
	private BigDecimal connectionPrice;
	private String connectionType;
	private String connectionDesc;
	private BigDecimal deliveryCharges;
	private BigDecimal handlingCharges;
	private BigDecimal totalCharges;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getQunatity() {
		return qunatity;
	}

	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}

	public BigDecimal getConnectionPrice() {
		return connectionPrice;
	}

	public void setConnectionPrice(BigDecimal connectionPrice) {
		this.connectionPrice = connectionPrice;
	}

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public BigDecimal getHandlingCharges() {
		return handlingCharges;
	}

	public void setHandlingCharges(BigDecimal handlingCharges) {
		this.handlingCharges = handlingCharges;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getConnectionDesc() {
		return connectionDesc;
	}

	public void setConnectionDesc(String connectionDesc) {
		this.connectionDesc = connectionDesc;
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

}
