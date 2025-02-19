package com.jwtpr.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwtpr.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String name);
}
