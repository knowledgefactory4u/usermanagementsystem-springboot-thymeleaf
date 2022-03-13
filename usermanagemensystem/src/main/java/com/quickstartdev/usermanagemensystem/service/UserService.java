package com.quickstartdev.usermanagemensystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.quickstartdev.usermanagemensystem.entity.User;

public interface UserService extends UserDetailsService {
	public List<User> findAllUsers();

	public User findUserById(Long id);

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(Long id) throws Exception;
}