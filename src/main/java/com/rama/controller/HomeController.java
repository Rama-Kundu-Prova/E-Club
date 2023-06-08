package com.rama.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rama.model.UserInfo;
import com.rama.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index()
	{
		return "index";
	}
	
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	@GetMapping("/register")
	public String register()
	{
		return "register";
	}
	@PostMapping("/createUser")
	public String createuser(@ModelAttribute UserInfo user, HttpSession session) {
		
		
		boolean chk=userService.checkEmail(user.getEmail());
		if(chk) {
			session.setAttribute("msz", "Email Id already exists");
		}
		else {
			UserInfo userInfo=userService.createUser(user);
			if(userInfo !=null) {
				session.setAttribute("msz", "Registered Succcessfully");
			}
			else {
				session.setAttribute("msz","Something wrong on server" );
			}
		}
		
		return "redirect:/register";
		
	}

}
