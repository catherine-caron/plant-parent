package ca.plantcare.dao;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource; errors
import ca.plantcare.models.Member;

//@RepositoryRestResource(collectionResourceRel = "Member_data", path = "Member_data") errors
public interface MemberRepository extends CrudRepository<Member, String>  {

	Member findMemberbyMemberId (Integer MemberId);
	Member findMemberByUsername(String username);
	Member findMemberByEmail(String email);

}
