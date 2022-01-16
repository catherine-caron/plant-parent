package ca.plantcare.service;

import org.springframework.stereotype.Service;

@Service
public class PlantService {




	/**
     * Find all plants owned by specific member
	 * @param memberUsername
	 * @return
	 */
	@Transactional
	public List<Plant> findPlantsByMember(String memberUsername){
		List<Member> members = toList(memberRepository.findAll());
		for (Member member:members) {
			if (member.getUsername().equals(memberUsername)) {
				return member.getPlant();
			}
		}
		throw new InvalidInputException("Username not found.");
	}

    
	/**
     * Helper method that converts iterable to list
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
