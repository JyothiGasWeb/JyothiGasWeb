package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dealer")
public class DealerEntiy implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8611241394324140199L;
	private int id;
	private String dealer_email;
	private String dealer_name;
	private String dealer_contact_no;
	private String dealer_city;
	private String dealer_area_code;
	private String dealer_address;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Dealer_Email")
	public String getDealer_email() {
		return dealer_email;
	}

	public void setDealer_email(String dealer_email) {
		this.dealer_email = dealer_email;
	}

	@Column(name = "Dealer_Name")
	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}

	@Column(name = "Dealer_Contact_No")
	public String getDealer_contact_no() {
		return dealer_contact_no;
	}

	public void setDealer_contact_no(String dealer_contact_no) {
		this.dealer_contact_no = dealer_contact_no;
	}

	@Column(name = "Dealer_City")
	public String getDealer_city() {
		return dealer_city;
	}

	public void setDealer_city(String dealer_city) {
		this.dealer_city = dealer_city;
	}

	@Column(name = "Dealer_Area_Code")
	public String getDealer_area_code() {
		return dealer_area_code;
	}

	public void setDealer_area_code(String dealer_area_code) {
		this.dealer_area_code = dealer_area_code;
	}

	@Column(name = "Dealer_Addresss")
	public String getDealer_address() {
		return dealer_address;
	}

	public void setDealer_address(String dealer_address) {
		this.dealer_address = dealer_address;
	}

}
