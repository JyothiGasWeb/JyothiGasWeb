package com.jyothigas.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Dealer_Region")
public class DealerRegionEntity {

	@Column(name="Dealer_Id")
	private int dealerId;
	
	@Column(name="Region_Id")
	private int regionId;

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
