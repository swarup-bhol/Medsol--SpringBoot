package com.sm.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sm.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByUserEmail(String email);

	User findUserByUserEmailAndUserPassword(String email, String password);
	
	User findUserByUserEmailOrUserMobile(String email, String mobile);

	User findByUserId(long userId);
	@Query("SELECT u FROM User u where u.userId not in ?1")
	List<User> findAllUser(List<Long> userId,Pageable pageable);
//	@Query("SELECT u.userId as userId,u.name,p.id as profileId, p.institute,p.grade FROM User u inner join UserProfile p on u = p.user")
//	List<ArrayList<Object>> findAllUser(Pageable pageable);
////	List<Object> findUser();
//	@Query("SELECT u.id,u.name,p.id, p.institute,p.grade FROM User u inner join UserProfile p on u = p.user")
//	List<Object> findUser();
	
	@Query("SELECT u FROM User u WHERE u.fullName LIKE CONCAT(:fullName,'%')")
	List<User> findUsersWithStartPartOfName(@Param("fullName") String username);
	@Query("SELECT u FROM User u WHERE u.fullName LIKE CONCAT('%',:fullName,'%')")
	List<User> findUsersWithPartOfName(@Param("fullName") String username);
}
