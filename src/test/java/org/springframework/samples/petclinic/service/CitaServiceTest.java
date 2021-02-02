package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CitaServiceTest {
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected CitaService citaService;
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Autowired
	protected TallerService tallerService;
	
	@Autowired
	protected EntityManager em;
	
	@Autowired
	protected TipoVehiculoService tipoVehiculoService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	@Test
	@Transactional
	void shouldInsertCita() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		Taller t = new Taller();
		t.setCorreo("test@test.com");
		t.setName("test");
		t.setTelefono("123456789");
		t.setUbicacion("calle test");
		
		tallerService.saveTaller(t);
		
		c.setTaller(t);
		
		citaService.saveCita(c, "jesfunrud");
		

		
		assertEquals(c, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
	}
	
	@Test
	@Transactional
	void shouldNotInsertCitaInvalida() throws DataAccessException, DuplicatedMatriculaException {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
	
		
		c.setFecha(LocalDate.now());
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		assertThrows(ConstraintViolationException.class, () -> this.citaService.saveCita(c, "jesfunrud"));
	}
	
	
	@Test
	@Transactional
	void shouldNotInsertCitaYEmpleadoDistintoTaller() throws DataAccessException, DuplicatedMatriculaException {
		
		//Setup inicial de cita
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
	
		
		c.setFecha(LocalDate.now());
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
	
		
		//Setup de 2 talleres distintos. Asigno t1 a cita y t2 al empleado
		Taller t1 = new Taller();
		t1.setCorreo("test@test.com");
		t1.setName("test");
		t1.setTelefono("123456789");
		t1.setUbicacion("calle test");
		
		tallerService.saveTaller(t1);
		
		
		c.setTaller(t1); 
		
		
		Taller t2 = new Taller();
		t2.setCorreo("test2@test.com");
		t2.setName("test2");
		t2.setTelefono("123456780");
		t2.setUbicacion("calle test 2");
		
		tallerService.saveTaller(t2);
		
		
		//Setup de empleado
		Empleado e1 = new Empleado();
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
		e1.setSueldo(1000);
		e1.setUsuario(userP);
		e1.setNum_seg_social("234567820145");
		e1.setEmail("prueba@prueba.com");
		e1.setTelefono("777777777");
		e1.setTaller(t2);
		empleadoService.saveEmpleado(e1);
		
		//Añadimos empleado a la cita 
		List<Empleado> empleados = new ArrayList<>();
		empleados.add(e1);
		c.setEmpleados(empleados);
		
		assertThrows(EmpleadoYCitaDistintoTallerException.class, () -> this.citaService.saveCita(c, "jesfunrud"));
	}
	
	
	
	@Test
	@Transactional
	void shouldUpdateCita() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		Taller t = new Taller();
		t.setCorreo("test@test.com");
		t.setName("test");
		t.setTelefono("123456789");
		t.setUbicacion("calle test");
		
		tallerService.saveTaller(t);
		
		c.setTaller(t);
		
		citaService.saveCita(c, "jesfunrud");
		Cita c1 = citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10);
		
		c1.setFecha(LocalDate.now().plusDays(3));
		citaService.saveCita(c1, "jesfunrud");
		assertEquals(c1, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(3), 10));
	}
	
	@Test
	@Transactional
	void shouldNotUpdateInvalidCita() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Taller t = new Taller();
		t.setCorreo("test@test.com");
		t.setName("test");
		t.setTelefono("123456789");
		t.setUbicacion("calle test");
		
		tallerService.saveTaller(t);
		
		c.setTaller(t);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		citaService.saveCita(c, "jesfunrud");
		
		Cita c1 = new Cita();
		c1.setId(c.getId());

		c1.setHora(10);
		c.setTiposCita(tipos);
		c1.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		c1.setFecha(LocalDate.now());
		assertThrows(ConstraintViolationException.class, () ->{
			this.citaService.saveCita(c1, "jesfunrud");
			em.flush();
		});	
	}
	
	@Test
	void shouldDeleteCita() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotAllowedException {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		Taller t = new Taller();
		t.setCorreo("test@test.com");
		t.setName("test");
		t.setTelefono("123456789");
		t.setUbicacion("calle test");
		
		tallerService.saveTaller(t);
		
		c.setTaller(t);
		
		citaService.saveCita(c, "jesfunrud");
		
		assertEquals(c, citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
		
		citaService.delete(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
		
		assertNull(citaService.findCitaByFechaAndHora(LocalDate.now().plusDays(1), 10));
	}
	
	@Test
	void shouldCancelarCitasCovid() throws DataAccessException, DuplicatedMatriculaException, EmpleadoYCitaDistintoTallerException, NotFoundException, NotAllowedException {
		Cita c = new Cita();
		TipoCita tipo = tipoCitaService.findById(1).get();
		
		c.setFecha(LocalDate.now().plusDays(1));
		c.setHora(10);
		List<TipoCita> tipos = new ArrayList<TipoCita>();
		tipos.add(tipo);
		c.setTiposCita(tipos);
		
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Antonio");
		cliente.setApellidos("Vargas Ruda");
		cliente.setDni("11223344M");
		cliente.setEmail("sevillacustoms@gmail.com");
		cliente.setFechaNacimiento(LocalDate.now().minusDays(1));
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		cliente.setUser(userP);
		cliente.setTelefono("111223344");
		
		clienteService.saveCliente(cliente);
		
		c.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		Taller t = new Taller();
		t.setCorreo("test@test.com");
		t.setName("test");
		t.setTelefono("123456789");
		t.setUbicacion("calle test");
		
		tallerService.saveTaller(t);
		
		c.setTaller(t);
		
		citaService.saveCita(c, "jesfunrud");
		
		Cita c1 = new Cita();
		TipoCita tipo1 = tipoCitaService.findById(1).get();
		
		c1.setFecha(LocalDate.now().plusDays(14));
		c1.setHora(10);
		List<TipoCita> tipos1 = new ArrayList<TipoCita>();
		tipos1.add(tipo1);
		c1.setTiposCita(tipos1);
		
		c1.setVehiculo(vehiculoService.findVehiculoByMatricula("1234ABC").get());
		
		Taller t1 = new Taller();
		t1.setCorreo("test@test.com");
		t1.setName("test");
		t1.setTelefono("123456789");
		t1.setUbicacion("calle test");
		
		tallerService.saveTaller(t1);
		
		c1.setTaller(t1);
		
		citaService.saveCita(c1, "jesfunrud");
		
		assertEquals(c, citaService.findCitaById(c.getId()));
		assertEquals(c1, citaService.findCitaById(c1.getId()));
		
		citaService.deleteCOVID();
		assertThrows(NotFoundException.class, () -> citaService.findCitaById(c.getId()));
		assertThrows(NotFoundException.class, () -> citaService.findCitaById(c.getId()));
	}
}
