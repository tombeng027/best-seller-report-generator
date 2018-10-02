package com.svi.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Discount")
public class Discount {

	private String discountId;
	private String discountName;
	public String getDiscountId() {
		return discountId;
	}
	@XmlElement(name = "DiscountID")
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public String getDiscountName() {
		return discountName;
	}
	@XmlElement(name = "DiscountName")
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	
}
