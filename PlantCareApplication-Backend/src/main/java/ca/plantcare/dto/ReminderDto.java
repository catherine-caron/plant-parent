package ca.plantcare.dto;

import java.sql.Date;
import java.sql.Time;

import ca.plantcare.models.Reminder;

public class ReminderDto {

	private Integer reminderId;
	private String message;
	private Time time;
	private Date date;
	
	//
	public ReminderDto() {
		
	}
	
	public ReminderDto(Integer scheduleId, String message, Time time, Date date) {
		this.date =date;
		this.message = message;
		this.reminderId = scheduleId;
		this.time = time;
	}
	
	public ReminderDto converToDto(Reminder reminder) {
		ReminderDto reminderDto = new ReminderDto( reminder.getReminderId(),
				reminder.getMessage(),
				reminder.getTime(),
				reminder.getDate());
		return reminderDto;
	}
	/**
	 * @return the reminderId
	 */
	public Integer getReminderId() {
		return reminderId;
	}
	/**
	 * @param reminderId the reminderId to set
	 */
	public void setReminderId(Integer reminderId) {
		this.reminderId = reminderId;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
}
