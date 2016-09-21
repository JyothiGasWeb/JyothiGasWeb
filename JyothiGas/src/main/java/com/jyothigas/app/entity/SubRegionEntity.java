package com.jyothigas.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dealer_SubRegion")
public class SubRegionEntity {

	private int id;
	private String name;
	private int pinCode;
	private int description;

	@Id
	@Column(name = "Id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Pin_Code")
	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	@Column(name = "Description")
	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}

}
