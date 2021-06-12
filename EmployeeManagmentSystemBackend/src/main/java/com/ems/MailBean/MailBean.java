package com.ems.MailBean;

public class MailBean {

	private String receiverMailId;
	private String subject;
	private String message;
	
	
	
	
	public MailBean() {
	
	}
	public MailBean(String receiverMailId, String subject, String message) {
		this.receiverMailId = receiverMailId;
		this.subject = subject;
		this.message = message;
	}
	public String getReceiverMailId() {
		return receiverMailId;
	}
	public void setReceiverMailId(String receiverMailId) {
		this.receiverMailId = receiverMailId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
