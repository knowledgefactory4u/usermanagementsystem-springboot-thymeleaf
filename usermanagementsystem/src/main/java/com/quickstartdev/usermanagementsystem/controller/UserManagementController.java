package com.quickstartdev.usermanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickstartdev.usermanagementsystem.entity.User;
import com.quickstartdev.usermanagementsystem.service.UserService;

@Controller
public class UserManagementController {

	@Autowired
	UserService userService;

	@RequestMapping({ "/users", "/" })
	public String findAllUsers(Model model) {

		model.addAttribute("users", userService.findAllUsers());
		return "list-users";
	}

	@RequestMapping("/user/{id}")
	public String findUserById(@PathVariable("id") Long id, Model model) {

		model.addAttribute("user", userService.findUserById(id));
		return "list-user";
	}

	@GetMapping("/addUser")
	public String showCreateForm(User user) {
		return "add-user";
	}

	@RequestMapping("/add-user")
	public String createUser(User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}

		userService.createUser(user);
		model.addAttribute("user", userService.findAllUsers());
		return "redirect:/users";
	}

	@GetMapping("/updateUser/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("user", userService.findUserById(id));
		return "update-user";
	}

	@RequestMapping("/update-user/{id}")
	public String updateUser(@PathVariable("id") Long id, User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}

		userService.updateUser(user);
		model.addAttribute("user", userService.findAllUsers());
		return "redirect:/users";
	}

	@RequestMapping("/remove-user/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) throws Exception {
		userService.deleteUser(id);

		model.addAttribute("user", userService.findAllUsers());
		return "redirect:/users";
	}

}
