package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VehiculoServiceTest {

	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ClienteService clienteService;
	
	
	
	
	
	@Test
	void shouldInsertVehiculo() {
		Vehiculo v = new Vehiculo();
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		vehiculoService.saveVehiculo(v);
		
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA"));
	}

	@Test
	void shouldInsertVehiculoInvalido() {
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		
		assertThrows(ConstraintViolationException.class, () -> this.vehiculoService.saveVehiculo(v));
	}
	
	@Test
	void shoulDeleteVehiculo() {
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		vehiculoService.saveVehiculo(v);
		
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA"));
		
		vehiculoService.delete(v);
		
		assertNull(vehiculoService.findVehiculoByMatricula("1111AAA"));
	}
	
	
	
	@Test
	@Transactional
	void shouldFindVehiculoByCliente() {
		Cliente c = new Cliente();
		c.setNombre("nombre");
		c.setApellidos("apellidos");
		c.setDni("12345678A");
		c.setTelefono("646123456");
		c.setEmail("prueba@gmail.com");
		
		User u = new User();
		u.setUsername("usernamedeprueba");
		u.setPassword("prueba");
		u.setEnabled(true);
		
		c.setUser(u);
		
		clienteService.saveCliente(c);
			
		Vehiculo v = new Vehiculo();
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		v.setCliente(c);
		vehiculoService.saveVehiculo(v);
		
		assertEquals(v, vehiculoService.findByClienteId(c.getId()).get(0));
		
	}
	
	
}
