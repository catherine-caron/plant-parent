package ca.plantcare.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "wateringSchedule")
public class WateringSchedule {

	private Integer scheduleId;
	private Integer hoursBetweenWatering;
	private List<Reminder> reminder;
	private Plant plant;

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
	
	public void setReminder(List<Reminder> reminders) {
		this.reminder = reminders;
	}

	/**
	 * @return the plant
	 */
	@OneToOne(cascade = CascadeType.ALL)
	public Plant getPlant() {
		return plant;
	}

	/**
	 * @param plant the plant to set
	 */
	public void setPlant(Plant plant) {
		this.plant = plant;
	}

}
