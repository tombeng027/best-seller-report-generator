package com.svi.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Seats")
public class Seats {

	private List<Seat> seats;
	
	public List<Seat> getSeats() {
		return seats;
	}

	@XmlElement(name = "Seat")
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seats [seats=");
		builder.append(seats);
		builder.append("]");
		return builder.toString();
	}
	
	
}
