package ca.plantcare.dao;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ca.plantcare.models.WateringSchedule;

//@RepositoryRestResource(collectionResourceRel = "WateringSchedule_data", path = "WateringSchedule_data")
public interface WateringScheduleRepository extends CrudRepository<WateringSchedule, Integer>{

	WateringSchedule findWateringScheduleByScheduleId(Integer scheduleId);

}
