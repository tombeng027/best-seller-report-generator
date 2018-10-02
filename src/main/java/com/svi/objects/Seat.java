
package com.svi.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Seat")
public class Seat {

//	private List<OrderObj> orderObj;
	private OrdersObj ordersObj;
	private Discounts discounts;
//	private String num;

	public OrdersObj getOrdersObj() {
		return ordersObj;
	}

	@XmlElement(name = "Orders")
	public void setOrdersObj(OrdersObj ordersObj) {
		this.ordersObj = ordersObj;
	}

	
	public Discounts getDiscounts() {
		return discounts;
	}

	@XmlElement(name = "Discounts")
	public void setDiscounts(Discounts discounts) {
		this.discounts = discounts;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat [ordersObj=");
		builder.append(ordersObj);
		builder.append("]");
		return builder.toString();
	}
	
	
//	public String getNum() {
//		return num;
//	}
//
//	@XmlAttribute(name = "Num")
//	public void setNum(String num) {
//		this.num = num;
//	}


		
	
}
