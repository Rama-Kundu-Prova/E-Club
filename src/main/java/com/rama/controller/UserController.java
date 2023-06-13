package com.rama.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rama.model.UserInfo;
import com.rama.repository.UserRepository;
import com.rama.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		UserInfo user = userRepository.findByEmail(email);
		m.addAttribute("user", user);

	}

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@GetMapping("/changePassword")
	public String loadChangePassword() {
		return "user/change_password";
	}

	@PostMapping("/updatePassword")

	public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, HttpSession session) {
		String email = p.getName();
		UserInfo loginUser = userRepository.findByEmail(email);

		boolean f = bCryptPasswordEncoder.matches(oldPass, loginUser.getPassword());

		if (f) {

			loginUser.setPassword(bCryptPasswordEncoder.encode(newPass));
			UserInfo updatePasswordUser = userRepository.save(loginUser);
			if (updatePasswordUser != null) {

				session.setAttribute("msg", "Password Change Sucess");
			} else {
				session.setAttribute("msg", "someThing wrong on server");
			}
		} else {
			session.setAttribute("msg", "Old password incorrect");

		}

		return "redirect:/user/changePassword";
	}

	@GetMapping("/profile")
	public String loadProfile() {
		return "user/profile";
	}

}
