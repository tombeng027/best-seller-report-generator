package com.svi.objects;


public class Item{

	private String itemName;
	private String groupName;
	private int quantity;
	private double price;
	private String shift;
	
	public Item(String itemName, String groupName, int quantity, double price,String shift) {
		this.itemName = itemName;
		this.groupName = groupName;
		this.quantity = quantity;
		this.price = price;
		this.shift = shift;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [itemName=");
		builder.append(itemName);
		builder.append(", groupName=");
		builder.append(groupName);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", price=");
		builder.append(price);
		builder.append(", shift=");
		builder.append(shift);
		builder.append("]");
		return builder.toString();
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}	
	
}
