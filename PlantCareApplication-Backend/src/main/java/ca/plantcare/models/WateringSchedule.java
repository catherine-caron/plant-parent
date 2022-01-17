package ca.plantcare.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class WateringSchedule {

	private Integer scheduleId;
	private Integer hoursBetweenWatering;
	private List<Reminder> reminder;

	@Id
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getHoursBetweenWatering() {
		return hoursBetweenWatering;
	}

	public void setHoursBetweenWatering(Integer hoursBetweenWatering) {
		this.hoursBetweenWatering = hoursBetweenWatering;
	}

	@OneToMany(cascade = {CascadeType.ALL})
	public List<Reminder> getReminder()
	{
	  return this.reminder;
	}
	
	public void setReminder(List<Reminder> aReminder) {
		this.reminder = aReminder;
	}

}
