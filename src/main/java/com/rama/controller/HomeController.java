package com.rama.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rama.model.UserInfo;
import com.rama.repository.UserRepository;
import com.rama.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {

		if (p != null) {
			String email = p.getName();
			UserInfo user = userRepository.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String index() {
		return "index";
	}
	

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute UserInfo user,HttpServletRequest request) {

		
		 String url=request.getRequestURI().toString();
		 url=url.replace(request.getServletPath(), "");
		 
		boolean f = userService.checkEmail(user.getEmail());
		int ok=0;
		
		if (f) {
			// session.setAttribute("msg", "Email Id alreday exists");
			ok=1;
			
		}
		else
		{
			UserInfo userInfo = userService.createUser(user,url);
			

			/*if (userInfo != null) {
				session.setAttribute("msg", "Register Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}*/
			
		}
		if(ok==1)
		{
			System.out.println("hereeeeeeeeeeeeeeeeeeeee");
			return "redirect:/register";
		}
		else
		{
			return "login";
		}
	

		

		
	}
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code)
	{
		if(userService.verifyAccount(code))
		{
			
			return "login";
		}
		else
		{
			
			return "redirect:/register";
		}
		
	}

	
    

}
