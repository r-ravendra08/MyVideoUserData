package com.userdata.service.email;

import com.userdata.entity.EmailDetails;

public interface EmailService {
	// Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
 
    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
