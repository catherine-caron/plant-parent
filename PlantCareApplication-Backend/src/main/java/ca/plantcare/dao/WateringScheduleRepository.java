package ca.plantcare.dao;

import org.springframework.data.repository.CrudRepository;

import ca.plantcare.models.WateringSchedule;

public interface WateringScheduleRepository extends CrudRepository<WateringSchedule, Integer>{

	WateringSchedule findWateringSchedulebyScheduleid(Integer scheduleId);

}
