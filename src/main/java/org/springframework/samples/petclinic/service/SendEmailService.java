package org.springframework.samples.petclinic.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailManager")
public class SendEmailService{
	
	 @Autowired
	 public JavaMailSender mailSender;
	 
//	 public void setMailSender(MailSender mailSender){
//			this.mailSender = mailSender;
//	}
	 
	 public Boolean sendEmail(String to, String subject, String content) {
//
//		SimpleMailMessage email = new SimpleMailMessage();
//
////		email.setFrom("tallersevillacustoms@gmail.com");
//        email.setTo(to);
//        email.setSubject(subject);
//        email.setText(content);
//
//        mailSender.send(email);
		 Boolean sent = false;
		 
		 MimeMessage message = mailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		 
		 try {
			 helper.setTo(to);
			 helper.setText(content, true);
			 helper.setSubject(subject);
			 mailSender.send(message);
			 sent = true;
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 return sent;
    }


}
