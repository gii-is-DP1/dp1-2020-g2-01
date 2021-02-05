package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

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
  
	@Test
	void shouldInsertEmpleado() throws NoMayorEdadEmpleadoException, DataAccessException, InvalidPasswordException {
		
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
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
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R").get());
	}
	
	@Test
	@Transactional
	void shouldNotInsertEmpleadoInvalido() {
		
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("234567890123");
		e.setSueldo(1098);
		e.setTelefono("678456736");
		e.setUsuario(userP);
		
		User u = new User();
		u.setUsername("Laurita");
		u.setPassword("laura123");
		
		e.setUsuario(u);
		
		assertThrows(ConstraintViolationException.class, () -> empleadoService.saveEmpleado(e));
	}
	
	@Test
	@Transactional
	void shouldUpdateEmpleado() throws NoMayorEdadEmpleadoException, DataAccessException, InvalidPasswordException {
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
		e.setNum_seg_social("345678901234");
		e.setSueldo(1099);
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
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R").get();
		e1.getUsuario().setPassword("laura123");  //la contraseña viene codificada de base de datos
		e1.setDni("36283951M");
		
		empleadoService.saveEmpleado(e1);
		
		assertTrue(empleadoService.findEmpleadoDni("36283951M").isPresent());
		assertFalse(empleadoService.findEmpleadoDni("36283951R").isPresent());
	}
	
	@Test
	@Transactional
	void shouldNotUpdateEmpleadoInvalido() throws NoMayorEdadEmpleadoException, DataAccessException, InvalidPasswordException {
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
		e.setNum_seg_social("567890123413");
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
		
		Empleado e1 = empleadoService.findEmpleadoDni("36283951R").get();
		e1.getUsuario().setPassword("laura123"); //la contraseña viene codificada
		e1.setDni("");
		assertThrows(ConstraintViolationException.class, () ->{
			empleadoService.saveEmpleado(e1);
			em.flush();
		});
		
	}
	
	@Test 
	@Transactional
	void shoulDeleteEmplead() throws NoMayorEdadEmpleadoException, DataAccessException, InvalidPasswordException {
		User userP = new User();
		userP.setUsername("nombreusuario");
		userP.setPassword("passdeprueba");
		userP.setEnabled(true);
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(40));
		e.setNombre("Laura");
		e.setNum_seg_social("678901234567");
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
		assertEquals(e, empleadoService.findEmpleadoDni("36283951R").get());
		
		empleadoService.delete(e);
		
		assertFalse(empleadoService.findEmpleadoDni("36283951R").isPresent());

	}
	
	
	@Test 
	@Transactional
	void shouldNotInsertIfMenorDeEdad() throws NoMayorEdadEmpleadoException {
		Empleado e = new Empleado();
		
		e.setApellidos("Ramirez Perez");
		e.setEmail("laurita@gmail.com");
		e.setDni("36283951R");
		e.setFecha_fin_contrato(LocalDate.now().plusYears(10));
		e.setFecha_ini_contrato(LocalDate.now().minusYears(2));
		e.setFechaNacimiento(LocalDate.now().minusYears(10)); //menor de edad
		e.setNombre("Laura");
		e.setNum_seg_social("678901234567");
		e.setSueldo(1098);
		e.setTelefono("678456736");
		
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
		
		assertThrows(NoMayorEdadEmpleadoException.class, () -> this.empleadoService.saveEmpleado(e));
		

	}
	
	@Test
	void prueba() throws InvalidPasswordException {
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
		if (!Pattern.matches(regex, "laura123")) {
			throw new InvalidPasswordException();
		}
		
	}
	
	
}
