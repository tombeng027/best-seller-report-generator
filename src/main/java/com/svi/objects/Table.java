package com.svi.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Table")
public class Table {

	
	private String tableId;
	
	
	private String timeStamp;
	
	
	private int numberOfPax;
	
	
	private int popUpSeniorCt;

	public String getTableId() {
		return tableId;
	}

	@XmlElement(name = "TableID")
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	@XmlElement(name = "TimeStamp")
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNumberOfPax() {
		return numberOfPax;
	}

	@XmlElement(name = "NumberOfPax")
	public void setNumberOfPax(int numberOfPax) {
		this.numberOfPax = numberOfPax;
	}

	public int getPopUpSeniorCt() {
		return popUpSeniorCt;
	}

	@XmlElement(name = "PopUpSeniorCt")
	public void setPopUpSeniorCt(int popUpSeniorCt) {
		this.popUpSeniorCt = popUpSeniorCt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Table [tableId=");
		builder.append(tableId);
		builder.append(", timeStamp=");
		builder.append(timeStamp);
		builder.append(", numberOfPax=");
		builder.append(numberOfPax);
		builder.append(", popUpSeniorCt=");
		builder.append(popUpSeniorCt);
		builder.append("]");
		return builder.toString();
	}
	
}
