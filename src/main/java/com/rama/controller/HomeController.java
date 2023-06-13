package com.rama.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rama.model.UserInfo;
import com.rama.repository.UserRepository;
import com.rama.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
	public String createuser(@ModelAttribute UserInfo user, HttpSession session) {

		boolean chk = userService.checkEmail(user.getEmail());
		boolean ok = false;
		if (chk) {
			session.setAttribute("msz", "Email Id already exists");
			ok = true;
		} else {
			UserInfo userInfo = userService.createUser(user);
			if (userInfo != null) {
				session.setAttribute("msz", "Registered Succcessfully");
			} else {
				session.setAttribute("msz", "Something wrong on server");
			}
		}
		if (ok) {

			return "redirect:/register";
		} else {
			return "login";
		}

	}

	@GetMapping("/loadForgetPassword")

	public String loadForgetPassword() {
		return "forget_password";
	}
    

}
