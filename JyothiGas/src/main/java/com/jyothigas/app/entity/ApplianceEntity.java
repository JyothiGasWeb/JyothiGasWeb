package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Appliance")
public class ApplianceEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 657092305171893359L;
	private int id;
	private String appliance_Name;
	private String appliance_Pic;
	private String appliance_Details;
	private int applliance_Cost;
	private float dealerWithoutTax;
	private float customerWithoutTax;
	private float tax;
	
	@Column(name = "DealerWithoutTax")
	public float getDealerWithoutTax() {
		return dealerWithoutTax;
	}

	public void setDealerWithoutTax(float dealerWithoutTax) {
		this.dealerWithoutTax = dealerWithoutTax;
	}

	@Column(name = "CustomerWithoutTax")
	public float getCustomerWithoutTax() {
		return customerWithoutTax;
	}

	public void setCustomerWithoutTax(float customerWithoutTax) {
		this.customerWithoutTax = customerWithoutTax;
	}
	@Column(name = "Tax")
	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	@Column(name = "ConnectionTypeId")
	private int connectionTypeId;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Appliance_Name")
	public String getAppliance_Name() {
		return appliance_Name;
	}

	public void setAppliance_Name(String appliance_Name) {
		this.appliance_Name = appliance_Name;
	}

	@Column(name = "Appliance_Pic")
	public String getAppliance_Pic() {
		return appliance_Pic;
	}

	public void setAppliance_Pic(String appliance_Pic) {
		this.appliance_Pic = appliance_Pic;
	}

	@Column(name = "Appliance_Details")
	public String getAppliance_Details() {
		return appliance_Details;
	}

	public void setAppliance_Details(String appliance_Details) {
		this.appliance_Details = appliance_Details;
	}

	@Column(name = "Applliance_Cost")
	public int getApplliance_Cost() {
		return applliance_Cost;
	}

	public void setApplliance_Cost(int applliance_Cost) {
		this.applliance_Cost = applliance_Cost;
	}

	public int getConnectionTypeId() {
		return connectionTypeId;
	}

	public void setConnectionTypeId(int connectionTypeId) {
		this.connectionTypeId = connectionTypeId;
	}
	
	

}
