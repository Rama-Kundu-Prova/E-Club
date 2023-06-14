package com.rama.service;


import com.rama.model.UserInfo;

public interface UserService {
	public boolean checkEmail(String email);
	public UserInfo createUser(UserInfo user, String url);
	public boolean verifyAccount(String code);
	
	
	
	

}
