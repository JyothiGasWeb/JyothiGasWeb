package com.jyothigas.app.model;

public class Dealer implements java.io.Serializable {

	private int id;
	private String dealer_email;
	private String dealer_name;
	private String dealer_contact_no;
	private String dealer_city;
	private String dealer_area_code;
	private String dealer_address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDealer_email() {
		return dealer_email;
	}

	public void setDealer_email(String dealer_email) {
		this.dealer_email = dealer_email;
	}

	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}

	public String getDealer_contact_no() {
		return dealer_contact_no;
	}

	public void setDealer_contact_no(String dealer_contact_no) {
		this.dealer_contact_no = dealer_contact_no;
	}

	public String getDealer_city() {
		return dealer_city;
	}

	public void setDealer_city(String dealer_city) {
		this.dealer_city = dealer_city;
	}

	public String getDealer_area_code() {
		return dealer_area_code;
	}

	public void setDealer_area_code(String dealer_area_code) {
		this.dealer_area_code = dealer_area_code;
	}

	public String getDealer_address() {
		return dealer_address;
	}

	public void setDealer_address(String dealer_address) {
		this.dealer_address = dealer_address;
	}

}
