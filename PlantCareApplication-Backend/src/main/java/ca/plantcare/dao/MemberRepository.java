package ca.plantcare.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ca.plantcare.models.Member;

@RepositoryRestResource(collectionResourceRel = "Member_data", path = "Member_data")
public interface MemberRepository extends CrudRepository<Member, String>  {

	Member findMemberByUsername(String username);
	Member findMemberByEmail(String email);

}
