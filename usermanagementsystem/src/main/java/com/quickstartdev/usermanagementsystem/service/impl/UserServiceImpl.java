package com.quickstartdev.usermanagementsystem.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quickstartdev.usermanagementsystem.entity.Role;
import com.quickstartdev.usermanagementsystem.entity.User;
import com.quickstartdev.usermanagementsystem.exception.NotFoundException;
import com.quickstartdev.usermanagementsystem.repository.UserRepository;
import com.quickstartdev.usermanagementsystem.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		var user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<User> findAllUsers() {

		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Author not found with ID %d", id)));

	}

	@Override
	public void createUser(User user) {

		userRepository.save(user);

	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);

	}

	@Override
	public void deleteUser(Long id) throws Exception {

		var user = this.findUserById(id);

		// only normal users can remove
		if (user.getRoles().stream().filter(o -> o.getName().equals("ROLE_ADMIN")).findFirst().isPresent()) {
			throw new AccessDeniedException("Cannot remove admin user");
		}
		userRepository.deleteById(user.getId());

	}

}
