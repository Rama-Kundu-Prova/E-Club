package com.rama.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rama.model.UserInfo;
import com.rama.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserInfo user=userRepo.findByEmail(email);
		
		if(user!=null) {
			return new UserDetailsImpl(user);
		}
		throw new UsernameNotFoundException("user not available");
	}
	
	
	

}
