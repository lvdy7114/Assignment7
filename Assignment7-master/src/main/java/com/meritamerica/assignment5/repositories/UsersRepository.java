package com.meritamerica.assignment5.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.assignment5.models.Users;

@Repository
public interface UsersRepository extends JpaRepository <Users, Integer> {

	//org.springframework.security.core.userdetails.User findByName(String username);
		Users  findByUsername(String username);
}
