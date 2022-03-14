package com.todo1.shoppingcart.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo1.shoppingcart.model.User;
import com.todo1.shoppingcart.repository.RoleRepository;
import com.todo1.shoppingcart.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private static final String USER_ROLE = "ROLE_USER";
	
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {		
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}
	
	public Optional<User> findByUsername(String username){
		return this.userRepository.findByUsername(username);
	}
	
	public Optional<User> findByEmail(String email){
		return this.userRepository.findByEmail(email);
	}
	
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
		return this.userRepository.saveAndFlush(user);
	}
	

}
