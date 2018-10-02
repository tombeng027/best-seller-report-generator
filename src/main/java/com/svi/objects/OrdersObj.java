package com.svi.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Orders")
public class OrdersObj {

	private List<OrderObj> orderObj;

	public List<OrderObj> getOrderObj() {
		return orderObj;
	}

	@XmlElement(name = "Order")
	public void setOrderObj(List<OrderObj> orderObj) {
		this.orderObj = orderObj;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrdersObj [orderObj=");
		builder.append(orderObj);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
