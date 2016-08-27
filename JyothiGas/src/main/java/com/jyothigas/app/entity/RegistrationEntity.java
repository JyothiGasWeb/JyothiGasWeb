package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Registration", uniqueConstraints = { @UniqueConstraint(columnNames = "Role_Id") })
public class RegistrationEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1240300618104412980L;
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
	private int dealerId;
	private String status;
	private int connectionQty;
	private String surrenderInfo;
	private String userType;

	private String surrenderStatus;
	private Date createdDate;
	private Date updatedDate;
	private Date Surrender_Date;
	
	
	
	@Column(name = "Surrender_Date")
	public Date getSurrender_Date() {
		return Surrender_Date;
	}

	public void setSurrender_Date(Date surrender_Date) {
		Surrender_Date = surrender_Date;
	}

	@Column(name = "Surrender_Status")
	public String getSurrenderStatus() {
		return surrenderStatus;
	}

	public void setSurrenderStatus(String surrenderStatus) {
		this.surrenderStatus = surrenderStatus;
	}

	@Column(name = "Created_Date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "Updated_Date")
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Contact_No")
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Column(name = "City")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "Area_Code")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "Ency_Password")
	public String getEncyPassword() {
		return encyPassword;
	}

	public void setEncyPassword(String encyPassword) {
		this.encyPassword = encyPassword;
	}

	@Column(name = "Role_Id")
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(name = "Address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "Connection_Type_Id")
	public int getConnectionTypeId() {
		return connectionTypeId;
	}

	public void setConnectionTypeId(int connectionTypeId) {
		this.connectionTypeId = connectionTypeId;
	}

	@Column(name = "Dealer_Id")
	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "Connection_Qty")
	public int getConnectionQty() {
		return connectionQty;
	}

	public void setConnectionQty(int connectionQty) {
		this.connectionQty = connectionQty;
	}

	@Column(name = "Surrender_info")
	public String getSurrenderInfo() {
		return surrenderInfo;
	}

	public void setSurrenderInfo(String surrenderInfo) {
		this.surrenderInfo = surrenderInfo;
	}

	@Column(name = "UserType")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
