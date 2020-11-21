package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CitaServiceTest {

	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected CitaService citaService;
	

	@Test
	void shouldInsertCita() {
		Cita c = new Cita();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		c.setTipoCita(TipoCita.AIRE_ACONDICIONADO);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
		
		citaService.saveCita(c);
		
		assertEquals(c, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
	}
	
	@Test
	void shouldInsertCitaInvalida() {
		Cita c = new Cita();
		
		c.setFecha(LocalDate.now());
		c.setHora(10);
		c.setTipoCita(TipoCita.NEUMATICOS);
		
		Vehiculo v = new Vehiculo();
		
		v.setMatricula("1111AAA");
		v.setModelo("Seat Ibiza");
		v.setNumBastidor("1");
		vehiculoService.saveVehiculo(v);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1111AAA"));
	
		
		assertThrows(ConstraintViolationException.class, () -> this.citaService.saveCita(c));
	}

}
