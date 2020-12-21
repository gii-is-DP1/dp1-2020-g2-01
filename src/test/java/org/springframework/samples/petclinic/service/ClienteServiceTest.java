package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;
	
	@Test
	public void shouldAddCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Antonio");
		cliente.setApellidos("Vargas Ruda");
		cliente.setDni("11223344M");
		cliente.setEmail("añoño@gmail.com");
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		cliente.setUser(userP);
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		assertEquals(cliente, clienteService.findClientesByUsername("nombreusuario").get());
	
	}
	
	@Test
	public void shouldUpdateCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Antonio");
		cliente.setApellidos("Vargas Ruda");
		cliente.setDni("11223345M");
		cliente.setEmail("añoño@gmail.com");
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		cliente.setUser(userP);
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		Cliente cliente1 = clienteService.findClienteByDNI("11223345M").get();
		cliente1.setDni("34567890K");
		
		clienteService.saveCliente(cliente1);
		
		assertFalse(clienteService.findClienteByDNI("11223345M").isPresent());
		assertTrue(clienteService.findClienteByDNI("34567890K").isPresent());
		
	
	}
	
	@Test
	public void shouldDeleteCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Antonio");
		cliente.setApellidos("Vargas Ruda");
		cliente.setDni("11223346M");
		cliente.setEmail("añoño@gmail.com");
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		cliente.setUser(userP);
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		clienteService.delete(cliente);
		
		assertFalse(clienteService.findClienteByDNI("11223346M").isPresent());
		
	
	}
}
