package com.svi.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Order")
public class OrderObj {

	private String itemId;
	private String itemnames;
	private double itemPrice;
	private int itemQuantity;
	private double itemTotalPrice;
	private String itemGroup;
	private String waiterName;
	public String getItemId() {
		return itemId;
	}
	
	@XmlElement(name = "ItemID")
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemnames() {
		return itemnames;
	}
	
	@XmlElement(name = "ItemNames")
	public void setItemnames(String itemnames) {
		this.itemnames = itemnames;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	
	@XmlElement(name = "ItemPrice")
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	
	@XmlElement(name = "ItemQuantity")
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public double getItemTotalPrice() {
		return itemTotalPrice;
	}
	
	@XmlElement(name = "ItemTotalPrice")
	public void setItemTotalPrice(double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
	public String getItemGroup() {
		return itemGroup;
	}
	
	@XmlElement(name = "ItemGroup")
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	public String getWaiterName() {
		return waiterName;
	}
	
	@XmlElement(name = "ItemWaiterName")
	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderObj [itemId=");
		builder.append(itemId);
		builder.append(", itemnames=");
		builder.append(itemnames);
		builder.append(", itemPrice=");
		builder.append(itemPrice);
		builder.append(", itemQuantity=");
		builder.append(itemQuantity);
		builder.append(", itemTotalPrice=");
		builder.append(itemTotalPrice);
		builder.append(", itemGroup=");
		builder.append(itemGroup);
		builder.append(", waiterName=");
		builder.append(waiterName);
		builder.append("]");
		return builder.toString();
	}
	
	
}
