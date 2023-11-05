package com.userdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userdata.entity.UserInfo;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByName(String username);

	Boolean existsByMobile(String mobile);

	Boolean existsByEmail(String email);
}
