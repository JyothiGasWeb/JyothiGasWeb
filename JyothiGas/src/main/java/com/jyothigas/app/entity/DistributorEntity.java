package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Distributor")
public class DistributorEntity {

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Distributor_Id", nullable = false)
	private int dealer_id;

	@Column(name = "Reg_id")
	private int reg_id;

	public int getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(int dealer_id) {
		this.dealer_id = dealer_id;
	}

	public int getReg_id() {
		return reg_id;
	}

	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}
	
	
}
