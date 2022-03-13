package com.quickstartdev.usermanagemensystem;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.quickstartdev.usermanagemensystem.entity.Role;
import com.quickstartdev.usermanagemensystem.entity.User;
import com.quickstartdev.usermanagemensystem.service.UserService;

@SpringBootApplication
public class Application {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate() {
		return (args) -> {

			// Add dummy datas
			var user = new User("abcuser", "abcuser", "abcuser@user.in", passwordEncoder.encode("userTemp123"),
					Arrays.asList(new Role("ROLE_USER")));

			var user1 = new User("user1", "user1", "user1@dummy.in", passwordEncoder.encode("U1Temp123"),
					Arrays.asList(new Role("ROLE_USER")));

			var admin = new User("admin", "admin", "admin@admin.in", passwordEncoder.encode("Temp123"),
					Arrays.asList(new Role("ROLE_ADMIN")));

			userService.createUser(admin);
			userService.createUser(user1);
			userService.createUser(user);

		};
	}
}
