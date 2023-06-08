package com.rama.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rama.model.UserInfo;
import com.rama.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserInfo createUser(UserInfo user) {
		return userRepo.save(user);
	}

	@Override
	public boolean checkEmail(String email) {
		return userRepo.existsByEmail(email);
	}
	

}
