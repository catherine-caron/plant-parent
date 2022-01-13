package ca.plantcare.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.plantcare.models.Plant;
import ca.plantcare.models.Plant.BloomTime;
import ca.plantcare.models.Plant.SoilType;
import ca.plantcare.models.Plant.SunExposure;
import ca.plantcare.models.Plant.Toxicity;

public interface PlantRepository extends CrudRepository<Plant, Integer> {

	Plant findPlantByPlantId(Integer plantId);
	Plant findPlantByCommonName(String commonName);
	Plant findPlantByGivenName(String givenName);
	Plant findPlantByBotanicalName(String botanicalName);
	List<Plant> findPlantBySunExposure(SunExposure sunExposure);
	List<Plant> findPlantBySoilType(SoilType soilType);
	List<Plant> findPlantByToxicity(Toxicity toxicity);
	List<Plant> findPlantByBloomTime(BloomTime bloomTime);


}
