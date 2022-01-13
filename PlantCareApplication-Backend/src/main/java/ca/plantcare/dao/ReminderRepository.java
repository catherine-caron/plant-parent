package ca.plantcare.dao;

import org.springframework.data.repository.CrudRepository;

import ca.plantcare.models.Reminder;


public interface ReminderRepository extends CrudRepository<Reminder, Integer> {

	Reminder findReminderByReminderId(Integer reminderId);
}
