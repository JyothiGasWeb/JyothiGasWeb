package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Dealer_SubRegion")
public class DealerRegionEntity {

	@Column(name="Dealer_Id")
	private int dealerId;
	
	@Column(name="Sub_Region_Id")
	private int regionId;
	
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", nullable = false)
	private int id;

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

}
