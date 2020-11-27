package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
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
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		cliente.setPassword("pass");
		cliente.setUsername("antvarrud");
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		assertEquals(cliente, clienteService.findClientesByUsername("antvarrud").get());
	
	}
	
	@Test
	public void shouldUpdateCliente() {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Antonio");
		cliente.setApellidos("Vargas Ruda");
		cliente.setDni("11223344M");
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		cliente.setPassword("pass");
		cliente.setUsername("antvarrud");
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		Cliente cliente1 = clienteService.findClientesByUsername("antvarrud").get();
		cliente1.setUsername("antvarrud1");
		
		clienteService.saveCliente(cliente1);
		
		System.out.println(cliente1.getAuthorities().get(0));
		assertFalse(clienteService.findClientesByUsername("antvarrud").isPresent());
		assertEquals(cliente1, clienteService.findClientesByUsername("antvarrud1").get());
		
	
	}
}
