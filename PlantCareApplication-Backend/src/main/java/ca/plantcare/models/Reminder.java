package ca.plantcare.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.sql.Time;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Reminder {

	private Integer reminderId;
	private String message;
	private Time time;
	private Date date;
	
	@Id
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
