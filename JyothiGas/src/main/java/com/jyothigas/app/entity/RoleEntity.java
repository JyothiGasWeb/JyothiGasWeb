package com.jyothigas.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
public class RoleEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3594311021868290765L;
	private String name;
	private int roleId;

	@Id
	@Column(name = "Role")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Role_Id")
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
