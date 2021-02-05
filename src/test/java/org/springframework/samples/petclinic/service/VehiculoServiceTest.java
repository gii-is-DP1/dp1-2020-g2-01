package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VehiculoServiceTest {

	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ClienteService clienteService;
	
	
	public Vehiculo v;
	
	@BeforeEach
	void insertVehiculo() throws DataAccessException, DuplicatedMatriculaException {
		Vehiculo v = new Vehiculo();
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("VSSZZZ6KZ1R149943");
		v.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		vehiculoService.saveVehiculo(v);
		this.v=v;
	}
	
	
	
	@Test
	@Transactional
	void shouldInsertVehiculo() {
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA").get());
	}
	
	@Test
	@Transactional
	void shouldNotInsertVehiculoMismaMatricula() throws DataAccessException, DuplicatedMatriculaException {
				
		Vehiculo v1 = new Vehiculo();
		v1.setMatricula("1111AAA");
		v1.setModelo("Dacia Sandero");
		v1.setNumBastidor("VSSZZZ6KZ1R149836");
		v1.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(1));
		
		
		assertThrows(DuplicatedMatriculaException.class, ()->this.vehiculoService.saveVehiculo(v1));
	}

	@Test
	void shouldNotInsertVehiculoInvalido() {
		Vehiculo v1 = new Vehiculo();
		
		v1.setMatricula("");
		v1.setModelo("Seat Ibiza");
		v1.setNumBastidor("VSSZZZ6KZ1R149943");
		v1.setTipoVehiculo(vehiculoService.findVehiculoTypes().get(0));
		
		assertThrows(ConstraintViolationException.class, () -> this.vehiculoService.saveVehiculo(v1));
	}
	
	@Test
	@Transactional
	void shoulDeleteVehiculo() throws DataAccessException, DuplicatedMatriculaException {
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA").get());
		
		vehiculoService.delete(v);
		
		assertFalse(vehiculoService.findVehiculoByMatricula("1111AAA").isPresent());
	}

		

	
}
