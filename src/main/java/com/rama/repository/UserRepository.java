package com.rama.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.rama.model.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer>{
	
	public boolean existsByEmail(String email);
	public UserInfo findByEmail(String email);

}
