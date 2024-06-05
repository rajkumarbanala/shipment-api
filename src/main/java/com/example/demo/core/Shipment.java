package com.example.demo.core;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.enums.EnumsUtil.ShipmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Rajkumar Banala 05-June-2024
 *
 */

public class Shipment {

	@JsonProperty
	private long id;

	@JsonProperty
	private String itemName;

	@JsonProperty
	private String sourceLocation;

	@JsonProperty
	private String targetLocation;

	@Enumerated(EnumType.STRING)
	@JsonProperty
	private ShipmentStatus shipmentStatus;
	
	@JsonProperty
	private Long userId;
	
	public Shipment() {
	}
	
	public Shipment(long id, String itemName, String sourceLocation, String targetLocation,
			ShipmentStatus shipmentStatus, Long userId) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.sourceLocation = sourceLocation;
		this.targetLocation = targetLocation;
		this.shipmentStatus = shipmentStatus;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(String targetLocation) {
		this.targetLocation = targetLocation;
	}

	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
