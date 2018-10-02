package com.svi.objects;

import java.util.ArrayList;
import java.util.List;

public class SalesReport {

	private String date;
	private String shift;
	private List<Item> groupTotals;
	private List<Item> itemsByGroup;
	private int totalNoOfCustomers;
	private int totalNoOfSeniorDiscounts;
	private double percentOfSD;
	private MenuSections menuSections;
	private List<String> groupNames = new ArrayList<String>();
	private String week;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public int getTotalNoOfCustomers() {
		return totalNoOfCustomers;
	}
	public void setTotalNoOfCustomers(int totalNoOfCustomers) {
		this.totalNoOfCustomers = totalNoOfCustomers;
	}
	public int getTotalNoOfSeniorDiscounts() {
		return totalNoOfSeniorDiscounts;
	}
	public void setTotalNoOfSeniorDiscounts(int totalNoOfSeniorDiscounts) {
		this.totalNoOfSeniorDiscounts = totalNoOfSeniorDiscounts;
	}
	public double getPercentOfSD() {
		return percentOfSD;
	}
	public void setPercentOfSD(double percentOfSD) {
		this.percentOfSD = percentOfSD;
	}
	public List<Item> getGroupTotals() {
		return groupTotals;
	}
	public void setGroupTotals(List<Item> groupTotals) {
		this.groupTotals = groupTotals;
		for(Item i : groupTotals){
			this.groupNames.add(i.getGroupName());
		}
	}
	public List<Item> getItemsByGroup() {
		return itemsByGroup;
	}
	public void setItemsByGroup(List<Item> itemsByGroup) {
		this.itemsByGroup = itemsByGroup;
	}
	public MenuSections getMenuSections() {
		return menuSections;
	}
	public void setMenuSections(MenuSections menuSections) {
		this.menuSections = menuSections;
	}
	public List<String> getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
}
