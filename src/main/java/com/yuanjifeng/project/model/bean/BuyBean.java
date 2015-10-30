package com.yuanjifeng.project.model.bean;

public class BuyBean extends BaseBean {
	private int id;
	private int productId;
	private String productName;
	private int buyType;
	private double unitPrice;
	private double shipping;
	private int quantity;
	private double total;
	private double unitCost;
	private String tracking;
	private int trackStatus;
	private String trackStatusDesc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getBuyType() {
		return buyType;
	}
	public void setBuyType(int buyType) {
		this.buyType = buyType;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getShipping() {
		return shipping;
	}
	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	public String getTracking() {
		return tracking;
	}
	public void setTracking(String tracking) {
		this.tracking = tracking;
	}
	public int getTrackStatus() {
		return trackStatus;
	}
	public void setTrackStatus(int trackStatus) {
		this.trackStatus = trackStatus;
	}
	public String getTrackStatusDesc() {
		return trackStatusDesc;
	}
	public void setTrackStatusDesc(String trackStatusDesc) {
		this.trackStatusDesc = trackStatusDesc;
	}
	
	
}
