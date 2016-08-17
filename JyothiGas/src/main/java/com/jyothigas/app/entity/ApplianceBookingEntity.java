package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ApplianceBooking")
public class ApplianceBookingEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", nullable = false)
	private int id;
	
	@Column(name = "ApplianceId")
	private int applianceId;
	
	@Column(name = "BookingId")
	private int bookingId;

	@Column(name = "Quantity")
	private int quantity;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getApplianceId() {
		return applianceId;
	}

	public void setApplianceId(int applianceId) {
		this.applianceId = applianceId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
