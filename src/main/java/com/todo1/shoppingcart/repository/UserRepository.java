package com.todo1.shoppingcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.todo1.shoppingcart.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByUsername(@Param("username") String username);

}
