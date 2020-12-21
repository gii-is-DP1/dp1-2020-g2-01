package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SendEmailServiceTest {
	
	@Autowired 
	protected SendEmailService sendEmailService;
	
	@Test
	void shouldSendEmail() {
		String to="sergiariasruda@gmail.com";
		String subject = "Pruebesita";
		String content = "Cuerpo del email de prueba.";
		assertDoesNotThrow(() ->sendEmailService.sendEmail(to, subject, content));
		
	}

}
