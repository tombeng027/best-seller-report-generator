package com.svi.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Discounts")
public class Discounts {
	
	private List<Discount> discounts;

	public List<Discount> getDiscounts() {
		return discounts;
	}

	@XmlElement(name = "Discount")
	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}
	
	

}
