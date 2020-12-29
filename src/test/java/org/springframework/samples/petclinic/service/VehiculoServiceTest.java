package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VehiculoServiceTest {

	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ClienteService clienteService;
	
	
	
	
	
	@Test
	void shouldInsertVehiculo() throws DataAccessException, DuplicatedMatriculaException {
		Vehiculo v = new Vehiculo();
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("VSSZZZ6KZ1R149943");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		vehiculoService.saveVehiculo(v);
		
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA"));
	}

	@Test
	void shouldInsertVehiculoInvalido() {
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("VSSZZZ6KZ1R149943");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		
		assertThrows(ConstraintViolationException.class, () -> this.vehiculoService.saveVehiculo(v));
	}
	
	@Test
	void shoulDeleteVehiculo() throws DataAccessException, DuplicatedMatriculaException {
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("VSSZZZ6KZ1R149943");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		vehiculoService.saveVehiculo(v);
		
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA"));
		
		vehiculoService.delete(v);
		
		assertNull(vehiculoService.findVehiculoByMatricula("1111AAA"));
	}

		

	
}
