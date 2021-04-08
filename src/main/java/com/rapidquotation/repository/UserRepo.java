package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.UserRegistration;

@Repository
public interface UserRepo extends JpaRepository<UserRegistration, Integer> {
	
	public UserRegistration findByEmailAndPassword(String email , String password);
	
	@Query("select u from UserRegistration u where u.email = :email")
	public UserRegistration getUserbyUserName(@Param("email") String email);

}
