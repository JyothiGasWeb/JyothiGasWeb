package com.jyothigas.app.model;

public class Register implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6013184916318431767L;
	private int id;
	private String email;
	private String name;
	private String contactNo;
	private String city;
	private String areaCode;
	private String encyPassword;
	private int roleId;
	private String address;
	private int connectionTypeId;
	private int dealer_id;
	private int distributer_id;
	private String status;
	private int connectionQty;
	private String surrenderInfo;
	private String userType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEncyPassword() {
		return encyPassword;
	}

	public void setEncyPassword(String encyPassword) {
		this.encyPassword = encyPassword;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getConnectionTypeId() {
		return connectionTypeId;
	}

	public void setConnectionTypeId(int connectionTypeId) {
		this.connectionTypeId = connectionTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getConnectionQty() {
		return connectionQty;
	}

	public void setConnectionQty(int connectionQty) {
		this.connectionQty = connectionQty;
	}

	public String getSurrenderInfo() {
		return surrenderInfo;
	}

	public void setSurrenderInfo(String surrenderInfo) {
		this.surrenderInfo = surrenderInfo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(int dealer_id) {
		this.dealer_id = dealer_id;
	}

	public int getDistributer_id() {
		return distributer_id;
	}

	public void setDistributer_id(int distributer_id) {
		this.distributer_id = distributer_id;
	}

}
