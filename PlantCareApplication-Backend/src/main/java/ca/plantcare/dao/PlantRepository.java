package ca.plantcare.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.plantcare.models.Plant;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;

public interface PlantRepository extends CrudRepository<Plant, Integer> {

	Plant findPlantByPlantId(Integer plantId); //added to service
	Plant findPlantByCommonName(String commonName); //added to service
	Plant findPlantByGivenName(String givenName); //added to service
	Plant findPlantByBotanicalName(String botanicalName);
	Plant findPlantBySunExposure(SunExposure sunExposure); //added to service
	Plant findPlantBySoilType(SoilType soilType);
	Plant findPlantByToxicity(Toxicity toxicity);
	//Plant findPlantByBloomTime(BloomTime bloomTime);


}
