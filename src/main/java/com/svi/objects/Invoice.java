package com.svi.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author darimbuyutan
 *
 */
@XmlRootElement(name = "INVOICE_INFO")
public class Invoice {

	
	private String invoiceId;
	private String invoiceDate;
	private Table table;
	private Seats seats;
	
	
//	private List<Seat> seats;
	
	
//	public List<Seat> getSeats() {
//		return seats;
//	}
//
//	@XmlElement(name = "Seats")
//	public void setSeats(List<Seat> seats) {
//		this.seats = seats;
//	}

	public Seats getSeats() {
		return seats;
	}

	@XmlElement(name = "Seats")
	public void setSeats(Seats seats) {
		this.seats = seats;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	@XmlElement(name = "Invoice_ID")
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}
	
	@XmlElement(name = "Invoice_Date")
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Table getTable() {
		return table;
	}

	@XmlElement(name = "Table")
	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice [invoiceId=");
		builder.append(invoiceId);
		builder.append(", invoiceDate=");
		builder.append(invoiceDate);
		builder.append(", table=");
		builder.append(table);
		builder.append(", seats=");
		builder.append(seats);
		builder.append("]");
		return builder.toString();
	}
	
}
