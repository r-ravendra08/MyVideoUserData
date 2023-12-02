package com.userdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userdata.entity.EmailDetails;

@Repository
public interface EmailRepository extends JpaRepository<EmailDetails, Integer>{

}
