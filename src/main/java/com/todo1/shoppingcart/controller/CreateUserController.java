package com.todo1.shoppingcart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo1.shoppingcart.model.User;
import com.todo1.shoppingcart.service.UserService;

@Controller
@RequestMapping("/createuser")
public class CreateUserController {

	private final UserService userService;

	@Autowired
	public CreateUserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String registration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "createuser";
	}

	@PostMapping
	public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {

		if (userService.findByEmail(user.getEmail()).isPresent()) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (userService.findByUsername(user.getUsername()).isPresent()) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the username provided");
		}

		if (bindingResult.hasErrors()) {

		} else {
			this.userService.createUser(user);
			model.addAttribute("successMessage", "User has been registered successfully");
			model.addAttribute("user", new User());
		}

		return "createuser";
	}

}
