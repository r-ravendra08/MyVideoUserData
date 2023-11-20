package com.userdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userdata.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmail(String email);

	Optional<Users> findByMobileOrEmail(String mobile, String email);

	Optional<Users> findByMobile(String mobile);

	Boolean existsByMobile(String mobile);

	Boolean existsByEmail(String email);
}
