package com.svi.objects;

import java.util.ArrayList;
import java.util.List;

public class MenuSection {
	
	private String sectionName;
	
	private List<Item> menuItems = new ArrayList<Item>();

	public MenuSection(String name){
		this.sectionName = name;
	}
	
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<Item> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<Item> menuItems) {
		this.menuItems = menuItems;
	}
	
	public void set(List<Item> menuItems,String sectionName){
		this.sectionName = sectionName;
		this.menuItems = menuItems;
	}
	
	public void add(Item e){
		this.menuItems.add(e);
	}
	

}
