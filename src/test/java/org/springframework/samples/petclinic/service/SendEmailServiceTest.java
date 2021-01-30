package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SendEmailServiceTest {
	
	@Mock
	private JavaMailSender mock;
	
	@Autowired
	protected SendEmailService sendEmailService;
	
	
	@Test
	void shouldSendEmail() {
		this.sendEmailService.setMailSender(mock);
		when(mock.createMimeMessage()).thenReturn(mock(MimeMessage.class));
		String to="tallersevillacustoms@gmail.com";
		String subject = "Pruebesita";
		String content = "Cuerpo del email de prueba.";
		assertDoesNotThrow(() ->sendEmailService.sendEmail(to, subject, content));
		verify(mock, times(1)).createMimeMessage();
		verify(mock, times(1)).send(any(MimeMessage.class));
	}

}
