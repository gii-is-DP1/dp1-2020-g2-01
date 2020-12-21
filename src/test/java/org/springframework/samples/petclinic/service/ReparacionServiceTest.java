package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters=@ComponentScan.Filter(Service.class))
class ReparacionServiceTest {
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
	protected CitaService citaService;
	
	
	@Test
	@Transactional
	void shouldFinalizar() {
		Reparacion r = new Reparacion();
		r.setDescripcion("Descripción");
		Collection<Empleado> empleados = (Collection<Empleado>) empleadoService.findAll();
		r.setEmpleados(empleados);
		
		Cita cita = citaService.findCitaSinReparacion().get(0);
		r.setCita(cita);
		
		r.setTiempoEstimado(LocalDate.now().minusDays(1L));
		r.setFechaEntrega(LocalDate.now().minusDays(6L));
		r.setFechaFinalizacion(LocalDate.of(2020, 11, 14));
		r.setFechaRecogida(LocalDate.now().plusDays(2L));

		reparacionService.finalizar(r);
		assertEquals(r.getFechaFinalizacion(), LocalDate.now());
		
	}
	
	
}
