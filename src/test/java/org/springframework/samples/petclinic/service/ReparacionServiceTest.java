package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	protected TipoVehiculoService tipoVehiculoService;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
	protected EntityManager em;
	
	
	@Test
	void shouldInsertReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		
		Reparacion r = new Reparacion();
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		Vehiculo v = new Vehiculo();
		TipoVehiculo tv = tipoVehiculoService.findById(1).get();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		v.setTipoVehiculo(tv);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		vehiculoService.saveVehiculo(v);
		citaService.saveCita(c);
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000L);
		e1.setUsuario(userP);
		e1.setNum_seg_social("1");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		assertEquals(r, reparacionService.findReparacionById(r.getId()).get());
		
		
	}
	
	@Test
	@Transactional
	void shouldNotInsertReparacionInvalida() {
		
		Reparacion r = new Reparacion();
		r.setName("Nombre");
		r.setDescripcion("");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		Vehiculo v = new Vehiculo();
		TipoVehiculo tv = tipoVehiculoService.findById(1).get();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		v.setTipoVehiculo(tv);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		vehiculoService.saveVehiculo(v);
		citaService.saveCita(c);
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000L);
		e1.setUsuario(userP);
		e1.setNum_seg_social("1");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		assertThrows(ConstraintViolationException.class, () -> this.reparacionService.saveReparacion(r));
		
	}
	
	@Test
	void shouldNotInsertReparacionConFechasIncorrectas() {
		
		Reparacion r = new Reparacion();
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(11));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		Vehiculo v = new Vehiculo();
		TipoVehiculo tv = tipoVehiculoService.findById(1).get();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		v.setTipoVehiculo(tv);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		vehiculoService.saveVehiculo(v);
		citaService.saveCita(c);
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000L);
		e1.setUsuario(userP);
		e1.setNum_seg_social("1");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		assertThrows(FechasReparacionException.class, () -> this.reparacionService.saveReparacion(r));
		
	}
	
	@Test
	void shouldUpdateReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		Reparacion r = new Reparacion();
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		Vehiculo v = new Vehiculo();
		TipoVehiculo tv = tipoVehiculoService.findById(1).get();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		v.setTipoVehiculo(tv);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		vehiculoService.saveVehiculo(v);
		citaService.saveCita(c);
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000L);
		e1.setUsuario(userP);
		e1.setNum_seg_social("1");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		
		Reparacion r1 = reparacionService.findReparacionById(r.getId()).get();
		r1.setName("Nombre cambiado");
		
		reparacionService.saveReparacion(r1);
		
		assertTrue(reparacionService.findReparacionById(r1.getId()).isPresent());

	}
	
	@Test
	void shouldNotUpdateReparacionInvalida() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		Reparacion r = new Reparacion();
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		Vehiculo v = new Vehiculo();
		TipoVehiculo tv = tipoVehiculoService.findById(1).get();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		v.setTipoVehiculo(tv);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		vehiculoService.saveVehiculo(v);
		citaService.saveCita(c);
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000L);
		e1.setUsuario(userP);
		e1.setNum_seg_social("1");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		
		Reparacion r1 = reparacionService.findReparacionById(r.getId()).get();
		r1.setName("");
		
		
		
		assertThrows(ConstraintViolationException.class, () ->{
			reparacionService.saveReparacion(r1);
			em.flush();
		});

	}
	
	
	@Test
	void shouldDeleteReparacion() throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		
		Reparacion r = new Reparacion();
		r.setName("Nombre");
		r.setDescripcion("Una descripcion hola que tal");
		r.setFechaEntrega(LocalDate.now().plusDays(7));
		r.setTiempoEstimado(LocalDate.now().plusDays(8));
		r.setFechaFinalizacion(LocalDate.now().plusDays(9));
		r.setFechaRecogida(LocalDate.now().plusDays(10));
	
		Cita c = new Cita();
		TipoCita t = tipoCitaService.findById(1).get();
		List<TipoCita> tipos = new ArrayList<>();
		tipos.add(t);
		Vehiculo v = new Vehiculo();
		TipoVehiculo tv = tipoVehiculoService.findById(1).get();
		v.setMatricula("4052DMR");
		v.setModelo("Renault Clio 2006");
		v.setNumBastidor("12");
		v.setTipoVehiculo(tv);
		c.setFecha(LocalDate.now().plusDays(2));
		c.setHora(18);
		c.setTiposCita(tipos);
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("4052DMR"));
		vehiculoService.saveVehiculo(v);
		citaService.saveCita(c);
		
		r.setCita(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(2), 18));
		
		Empleado e1 = new Empleado();
		List<Empleado> empleados = new ArrayList<>();
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		e1.setNombre("Pepito");
		e1.setApellidos("Grillo");
		e1.setDni("89898988A");
		e1.setFechaNacimiento(LocalDate.now().minusYears(20));
		e1.setFecha_ini_contrato(LocalDate.now().minusDays(10));
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(1));
		e1.setSueldo(1000L);
		e1.setUsuario(userP);
		e1.setNum_seg_social("1");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		
		empleados.add(e1);
		empleadoService.saveEmpleado(e1);

		r.setEmpleados(empleados);
		
		reparacionService.saveReparacion(r);
		assertTrue(reparacionService.findReparacionById(r.getId()).isPresent());
		
		reparacionService.delete(r);
		assertFalse(reparacionService.findReparacionById(r.getId()).isPresent());
		
	}
	
}