package org.springframework.samples.petclinic.service;


import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService{
	
	private JavaMailSender jms;
	
	 @Autowired
	 public JavaMailSender setMailSender() {
		 return this.jms= getJavaMailSender();
	 }
	 
	 public void sendEmail(String to, String subject, String content) {
		 
		 MimeMessage message = jms.createMimeMessage();
		 
		 try {
			 MimeMessageHelper helper = new MimeMessageHelper(message, true);
			 helper.setTo(to);
			 helper.setFrom("tallersevillacustoms@gmail.com");
			 helper.setText(content);
			 helper.setSubject(subject);
			 jms.send(message);

		 }catch(Exception e) {
			 e.printStackTrace();
		 }
    }
	 
	 @Bean
	 public JavaMailSender getJavaMailSender() {
	     JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	     mailSender.setHost("smtp.gmail.com");
	     mailSender.setPort(587);
	       
	     mailSender.setUsername("tallersevillacustoms@gmail.com");
	     mailSender.setPassword("iiyillkfbajlwfqa");
	       
	     Properties props = mailSender.getJavaMailProperties();
	     props.put("mail.transport.protocol", "smtp");
	     props.put("mail.smtp.auth", "true");
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.debug", "true");
	     props.put("mail.smtp.starttls.required", "true");
	     props.put("mail.smtp.socketFactory.port","465");
	     props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	     props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	       
	     return mailSender;
	 }


}
