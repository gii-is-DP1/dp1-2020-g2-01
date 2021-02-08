package org.springframework.samples.petclinic.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class RecambioControllerSecurityTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	@WithMockUser(username = "jesfunrud", authorities = {"cliente"})
	@Test
	void DenegerAccesoClienteRecambios() throws Exception {
		mockMvc
		.perform(get("/recambios"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@WithMockUser(username = "jesfunrud", authorities = {"cliente"})
	@Test
	void DenegerAccesoClienteSolicitudes() throws Exception {
		mockMvc
		.perform(get("/pedidosRecambio"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
}