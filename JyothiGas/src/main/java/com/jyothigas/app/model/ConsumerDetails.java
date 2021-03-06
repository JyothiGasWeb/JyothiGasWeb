package com.jyothigas.app.model;

import java.util.Date;

public class ConsumerDetails {

	private int reg_id;
	private int consumer_id;
	private String email;
	private String name;
	private String contactNo;
	private String city;
	private String areaCode;
	private int roleId;
	private String roleName;
	private String address;
	private String dealerName;
	private int dealerId;
	private int distributor_Id;
	private String connectionTypeName;
	private String connectionTypeDesc;
	private int connectionPrice;
	private int connectionTypeId;
	private String status;
	private String userType;

	private String surrenderStatus;
	private Date createdDate;
	private Date updatedDate;
	private Date surrender_Date;

	private int connectionQty;

	public Date getSurrender_Date() {
		return surrender_Date;
	}

	public void setSurrender_Date(Date surrender_Date) {
		this.surrender_Date = surrender_Date;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getReg_id() {
		return reg_id;
	}

	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}

	public int getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public String getConnectionTypeName() {
		return connectionTypeName;
	}

	public void setConnectionTypeName(String connectionTypeName) {
		this.connectionTypeName = connectionTypeName;
	}

	public int getConnectionTypeId() {
		return connectionTypeId;
	}

	public String getConnectionTypeDesc() {
		return connectionTypeDesc;
	}

	public void setConnectionTypeDesc(String connectionTypeDesc) {
		this.connectionTypeDesc = connectionTypeDesc;
	}

	public int getConnectionPrice() {
		return connectionPrice;
	}

	public void setConnectionPrice(int connectionPrice) {
		this.connectionPrice = connectionPrice;
	}

	public void setConnectionTypeId(int connectionTypeId) {
		this.connectionTypeId = connectionTypeId;
	}

	public int getConnectionQty() {
		return connectionQty;
	}

	public void setConnectionQty(int connectionQty) {
		this.connectionQty = connectionQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSurrenderStatus() {
		return surrenderStatus;
	}

	public void setSurrenderStatus(String surrenderStatus) {
		this.surrenderStatus = surrenderStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getDistributor_Id() {
		return distributor_Id;
	}

	public void setDistributor_Id(int distributor_Id) {
		this.distributor_Id = distributor_Id;
	}

}
