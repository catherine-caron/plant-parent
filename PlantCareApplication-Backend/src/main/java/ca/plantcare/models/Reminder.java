package ca.plantcare.models;

public class Reminder {

	private String message;
	private Integer reminderId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getReminderId() {
		return reminderId;
	}
	public void setReminderId(Integer reminderId) {
		this.reminderId = reminderId;
	}


}
