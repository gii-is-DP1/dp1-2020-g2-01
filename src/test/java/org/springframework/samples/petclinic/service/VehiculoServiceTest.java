package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VehiculoServiceTest {

	@Autowired
	protected VehiculoService vehiculoService;
	
	@Test
	void shouldInsertVehiculo() {
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		vehiculoService.saveVehiculo(v);
		
		assertEquals(v, vehiculoService.findVehiculoByMatricula("1111AAA"));
	}

	@Test
	void shouldInsertVehiculoInvalido() {
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		
		assertThrows(ConstraintViolationException.class, () -> this.vehiculoService.saveVehiculo(v));
	}
}
