package ca.plantcare.dto;
import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ca.plantcare.models.Reminder;

public class WateringScheduleDto {

	private Integer scheduleId;
	private Integer hoursBetweenWatering;
	private List<ReminderDto> reminderDto;

	public WateringScheduleDto() {
	}

	public WateringScheduleDto(Integer scheduleId, Integer hoursBetweenWatering) {
		this.scheduleId = scheduleId;
		this.hoursBetweenWatering = hoursBetweenWatering;
		this.reminderDto = Collections.emptyList();
	}

	public WateringScheduleDto(Integer scheduleId, Integer hoursBetweenWatering, List<ReminderDto> reminderDto) {
		this.scheduleId = scheduleId;
		this.hoursBetweenWatering = hoursBetweenWatering;
		this.reminderDto = reminderDto;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getHoursBetweenWatering() {
		return hoursBetweenWatering;
	}

	public void setHoursBetweenWatering(Integer hoursBetweenWatering) { this.hoursBetweenWatering = hoursBetweenWatering; }

	public List<ReminderDto> getReminder() { return this.reminderDto; }

	public void setReminder(List<ReminderDto> reminderDto) {
		this.reminderDto = reminderDto;
	}

}