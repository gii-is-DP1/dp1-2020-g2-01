package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;
	
	public Cliente cliente;
	
	@BeforeEach
	void insertCliente() throws DataAccessException, InvalidPasswordException {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Antonio");
		cliente.setApellidos("Vargas Ruda");
		cliente.setDni("88888888M");
		cliente.setEmail("añoño@gmail.com");
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		cliente.setUser(userP);
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		this.cliente = cliente;

	}
	
	@Test
	public void shouldAddCliente() {
		assertEquals(cliente, clienteService.findClientesByUsername("nombreusuario").get());
	}

	
	@Test
	public void shouldUpdateCliente() throws DataAccessException, InvalidPasswordException {
		Cliente cliente1 = clienteService.findClienteByDNI("88888888M").get();
		cliente1.setDni("34567890K");
		cliente1.getUser().setPassword("passdeprueba1"); //la password viene codificada de base de datos
		
		clienteService.saveCliente(cliente1);
		
		assertFalse(clienteService.findClienteByDNI("88888888M").isPresent());
		assertTrue(clienteService.findClienteByDNI("34567890K").isPresent());
		
	
	}
	
	@Test
	public void shouldDeleteCliente() {
		clienteService.delete(cliente);
		
		assertFalse(clienteService.findClienteByDNI("11223346X").isPresent());
		
	}
}

