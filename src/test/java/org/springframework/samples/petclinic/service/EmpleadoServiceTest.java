package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NoMayorEdadEmpleadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EmpleadoServiceTest {

	@Autowired
	protected EmpleadoService empleadoService;
	
	@Autowired
	protected TallerService tallerService;
	
	@Autowired
	protected EntityManager em;
	
	public Empleado e;
	
	@BeforeEach
	void insertEmpleado() throws DataAccessException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba1");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("123456789074");
		e.setSueldo(1098);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		Taller t = new Taller();
		t.setCorreo("test@test.com");
		t.setName("test");
		t.setTelefono("123456789");
		t.setUbicacion("calle test");
		
		tallerService.saveTaller(t);
		
		e.setTaller(t);
		
		empleadoService.saveEmpleado(e);
		
		this.e=e;
	}
  
	@Test
	void shouldInsertEmpleado() {
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R").get());
	}
	
	@Test
	@Transactional
	void shouldNotInsertEmpleadoInvalido() {
		
		User userP1 = new User();
		userP1.setUsername("nombreusuario1");
		userP1.setPassword("passdeprueba1");
		userP1.setEnabled(true);
		Empleado e1 = new Empleado();
		
		e1.setApellidos("Ramirez Perez");
		e1.setEmail("laurita@gmail.com");
		e1.setDni("");
		e1.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e1.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e1.setFechaNacimiento(LocalDate.now().minusYears(40));
		e1.setNombre("Laura");
		e1.setNum_seg_social("234567898123");
		e1.setSueldo(1098);
		e1.setTelefono("678456736");
		e1.setUsuario(userP1);
		
		User u1 = new User();
		u1.setUsername("Laurita1");
		u1.setPassword("laura123");
		
		e1.setUsuario(u1);
		
		assertThrows(ConstraintViolationException.class, () -> empleadoService.saveEmpleado(e1));
	}
	
	@Test
	@Transactional
	void shouldUpdateEmpleado() throws DataAccessException, NoMayorEdadEmpleadoException, InvalidPasswordException {
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R").get();
		e1.setDni("36283951M");
		
		empleadoService.saveEmpleado(e1);
		
		assertTrue(empleadoService.findEmpleadoDni("36283951M").isPresent());
		assertFalse(empleadoService.findEmpleadoDni("36283951R").isPresent());
	}
	
	@Test
	@Transactional
	void shouldNotUpdateEmpleadoInvalido() {
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R").get();
		e1.setDni("");
		assertThrows(ConstraintViolationException.class, () ->{
			empleadoService.saveEmpleado(e1);
			em.flush();
		});
		
	}
	
	@Test 
	@Transactional
	void shoulDeleteEmplead() {
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R").get());
		
		empleadoService.delete(e);
		
		assertFalse(empleadoService.findEmpleadoDni("36283951R").isPresent());

	}
	
	
	@Test 
	@Transactional
	void shouldNotInsertIfMenorDeEdad() throws NoMayorEdadEmpleadoException {		
		assertThrows(NoMayorEdadEmpleadoException.class, () -> this.empleadoService.saveEmpleado(e));
	}	
	
	
}
