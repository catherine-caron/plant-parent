package ca.plantcare.dao;

import org.springframework.data.repository.CrudRepository;

import ca.plantcare.models.Member;


public interface MemberRepository extends CrudRepository<Member, Integer>  {

	Member findMemberByUsername(Integer username);
	Member findMemberByEmail(Integer email);

}
