package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
	
	 @Autowired
	 private JavaMailSender mailSender;
	 
//	 public void setMailSender(MailSender mailSender){
//			this.mailSender = mailSender;
//	}
	 
	 public void sendEmail(String to, String subject, String content) {

		SimpleMailMessage email = new SimpleMailMessage();

//		email.setFrom("tallersevillacustoms@gmail.com");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);

        mailSender.send(email);
    }


}
