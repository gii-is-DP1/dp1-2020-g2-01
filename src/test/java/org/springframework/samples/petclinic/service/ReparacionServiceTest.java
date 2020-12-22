package org.springframework.samples.petclinic.service;

<<<<<<< Upstream, based on origin/master
import static org.junit.Assert.assertEquals;
=======
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
>>>>>>> 113a3fe reparaciontest



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ReparacionServiceTest {
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected CitaService citaService;
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
<<<<<<< Upstream, based on origin/master
	protected CitaService citaService;
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
=======
	protected EntityManager em;
>>>>>>> 113a3fe reparaciontest
	
	@Test
<<<<<<< Upstream, based on origin/master
	@Transactional
	@Disabled
	void shouldFinalizar() {
=======
	void shouldInsertReparacion() throws DataAccessException, FechasReparacionException {
		
>>>>>>> 113a3fe reparaciontest
		Reparacion r = new Reparacion();
		r.setId(7);
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		Vehiculo v = new Vehiculo();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		c.setFecha(LocalDate.now());
		c.setHora(18);
		c.setTipoCita(t);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now(), 18));
		
		Empleado e1 = new Empleado();
		Empleado e2 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("8989898A");
		e2.setNombre("Blanca");
		e2.setApellidos("Nieves");
		e2.setDni("8787878B");
		empleados.add(e1);
		empleados.add(e2);
		
		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		assertEquals(r, reparacionService.findReparacionById(7).get());
		
		
	}
	
	@Test
	void shouldInsertReparacion() throws DataAccessException, FechasReparacionException {
		
		Reparacion r = new Reparacion();
		r.setId(7);
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		Vehiculo v = new Vehiculo();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		c.setFecha(LocalDate.now());
		c.setHora(18);
		c.setTipoCita(t);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now(), 18));
		
		Empleado e1 = new Empleado();
		Empleado e2 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("8989898A");
		e2.setNombre("Blanca");
		e2.setApellidos("Nieves");
		e2.setDni("8787878B");
		empleados.add(e1);
		empleados.add(e2);
		
		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		assertEquals(r, reparacionService.findReparacionById(7).get());
		
		
	}
	
	

}