package com.userdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userdata.entity.EmailDetails;
import com.userdata.repository.EmailRepository;
import com.userdata.service.email.EmailService;

@RequestMapping("/email")
@RestController
@CrossOrigin("http://localhost:5173")
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	
	@Autowired
	private EmailRepository emailRepository;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details) {
		String status = emailService.sendSimpleMail(details);

		emailRepository.save(details);
		return status;
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}
}