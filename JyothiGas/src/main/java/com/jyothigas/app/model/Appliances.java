package com.jyothigas.app.model;

public class Appliances implements java.io.Serializable{

	private int id;
	private String appliance_Name;
	private String appliance_Pic;
	private String appliance_Details;
	private int applliance_Cost;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppliance_Name() {
		return appliance_Name;
	}

	public void setAppliance_Name(String appliance_Name) {
		this.appliance_Name = appliance_Name;
	}

	public String getAppliance_Pic() {
		return appliance_Pic;
	}

	public void setAppliance_Pic(String appliance_Pic) {
		this.appliance_Pic = appliance_Pic;
	}

	public String getAppliance_Details() {
		return appliance_Details;
	}

	public void setAppliance_Details(String appliance_Details) {
		this.appliance_Details = appliance_Details;
	}

	public int getApplliance_Cost() {
		return applliance_Cost;
	}

	public void setApplliance_Cost(int applliance_Cost) {
		this.applliance_Cost = applliance_Cost;
	}

}