package com.example.objects;

import java.util.Date;

public class Message {

	int messageId;
	Date date;
	String headerText;
	String bodyText;
	String creatorName;

	public Message(int messageId, Date date, String headerText, String bodyText, String creatorName) {
		super();
		this.messageId = messageId;
		this.date = date;
		this.headerText = headerText;
		this.bodyText = bodyText;
		this.creatorName = creatorName;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}




}
