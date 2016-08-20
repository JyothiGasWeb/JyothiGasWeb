package com.jyothigas.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ConnectionType")
public class ConnectionTypeEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8090215873730199745L;
	private int id;
	private String connectionType;
	private String connectionDesc;
	private int connectionPrice;
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

	@Column(name = "ConnectionType")
	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	@Column(name = "ConnectionDesc")
	public String getConnectionDesc() {
		return connectionDesc;
	}

	public void setConnectionDesc(String connectionDesc) {
		this.connectionDesc = connectionDesc;
	}

	@Column(name = "ConnectionPrice")
	public int getConnectionPrice() {
		return connectionPrice;
	}

	public void setConnectionPrice(int connectionPrice) {
		this.connectionPrice = connectionPrice;
	}

	@Column(name = "ConnectionTypeId")
	public int getConnectionTypeId() {
		return connectionTypeId;
	}

	public void setConnectionTypeId(int connectionTypeId) {
		this.connectionTypeId = connectionTypeId;
	}

}
