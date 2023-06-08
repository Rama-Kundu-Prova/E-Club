package com.rama.service;


import com.rama.model.UserInfo;

public interface UserService {
	public UserInfo createUser(UserInfo user);
	public boolean checkEmail(String email);
	

}
