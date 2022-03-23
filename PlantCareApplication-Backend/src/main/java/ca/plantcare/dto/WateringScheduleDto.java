package ca.plantcare.dto;

import java.util.List;

import ca.plantcare.models.Reminder;
import ca.plantcare.models.WateringSchedule;

public class WateringScheduleDto {

	private Integer scheduleId;
	private Integer hoursBetweenWatering;
	private List<Reminder> reminder;
	
	public WateringScheduleDto(){
		
	}
	
	public WateringScheduleDto(Integer hoursBetweenWatering,Integer scheduleId,List<Reminder> reminder ){ 
		//plantid should be there but it's not a field in wateringschedule. maybe we should add it.
		this.hoursBetweenWatering = hoursBetweenWatering;
		this.reminder= reminder;
		this.scheduleId = scheduleId;
	}
	
	public static WateringScheduleDto converToDto(WateringSchedule wateringSchedule) {
		WateringScheduleDto wateringScheduleDto = new WateringScheduleDto(wateringSchedule.getHoursBetweenWatering(),
				wateringSchedule.getScheduleId(),
				wateringSchedule.getReminder());
		return wateringScheduleDto;
	}

	/**
	 * @return the scheduleId
	 */
	public Integer getScheduleId() {
		return scheduleId;
	}
	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	/**
	 * @return the hoursBetweenWatering
	 */
	public Integer getHoursBetweenWatering() {
		return hoursBetweenWatering;
	}
	/**
	 * @param hoursBetweenWatering the hoursBetweenWatering to set
	 */
	public void setHoursBetweenWatering(Integer hoursBetweenWatering) {
		this.hoursBetweenWatering = hoursBetweenWatering;
	}
	/**
	 * @return the reminder
	 */
	public List<Reminder> getReminder() {
		return reminder;
	}
	/**
	 * @param reminder the reminder to set
	 */
	public void setReminder(List<Reminder> reminder) {
		this.reminder = reminder;
	}

}
