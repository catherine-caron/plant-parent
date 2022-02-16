package ca.plantcare.dto;
import java.sql.Date;
import java.sql.Time;

public class ReminderDto {

	private Integer reminderId;
	private String message;
	private Time time;
	private Date date;

	public ReminderDto() {
	}

	public ReminderDto(Integer reminderId, String message, Time time, Date date) {
		this.reminderId = reminderId;
		this.message = message;
		this.time = time;
		this.date = date;
	}

	public Integer getReminderId() {
		return reminderId;
	}

	public void setReminderId(Integer reminderId) {
		this.reminderId = reminderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getDate() { return date; }

	public void setDate(Date date) {
		this.date = date;
	}
}