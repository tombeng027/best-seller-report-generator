package com.svi.objects;

import java.util.ArrayList;
import java.util.List;

public class MenuSections {
	
	private List<MenuSection> menuSections = new ArrayList<MenuSection>();

	public List<MenuSection> getList() {
		return menuSections;
	}

	public void setMenuSections(List<MenuSection> menuSections) {
		this.menuSections = menuSections;
	}

	public void add(MenuSection menuSection) {
		this.menuSections.add(menuSection);
	}
	public int size(){
		return this.menuSections.size();
	}
	public List<Item> get(String name){
			for(MenuSection m : menuSections){
				if(m.getSectionName().equals(name)){
					return m.getMenuItems();
				}
			}
			return null;
	}
	public MenuSection popToJasper(){
		MenuSection m = new MenuSection("");
		m.set(this.menuSections.get(0).getMenuItems(),this.menuSections.get(0).getSectionName());
		this.menuSections.remove(0);
		return m;
	}

	public boolean isNotEmpty() {
		return !this.menuSections.isEmpty();
	}
	
}
