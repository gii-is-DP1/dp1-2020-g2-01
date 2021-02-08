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
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedUsernameException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPasswordException;
import org.springframework.samples.petclinic.service.exceptions.NoMayorEdadEmpleadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdministradorServiceTest{
	
	@Autowired
	private AdministradorService adminService;
	
	@Autowired
	protected EntityManager em;
		
	public Administrador admin;
	
	@BeforeEach
	void insertAdministrador() throws DataAccessException, InvalidPasswordException, DuplicatedUsernameException{

		User u = new User();
		u.setUsername("adminprueba");
		u.setPassword("Prueba123");
		u.setEnabled(true);
		Administrador admin = new Administrador();
		
		admin.setNombre("Administrador");
		admin.setApellidos("Morales García");
		admin.setDni("77889911H");
		admin.setEmail("admin@gmail.com");
		admin.setFechaNacimiento(LocalDate.now().minusYears(35));
		admin.setTelefono("654817364");
		admin.setNum_seg_social("123456789000");
		admin.setUsuario(u);
		adminService.saveAdministrador(admin);
		
		this.admin=admin;
		
	}
	
	@Test
	@Transactional
	void shouldInsertAdministrador() {
		assertEquals(admin, adminService.findAdministradorByDni("77889911H").get());
	}
	
	@Test
	@Transactional
	void shouldNotInsertAdministradorUsernameDuplicado() { //Username repetido
		User u = new User();
		u.setUsername("adminprueba");
		u.setPassword("Prueba123");
		u.setEnabled(true);
		Administrador admin = new Administrador();
		
		admin.setNombre("Administrador de prueba");
		admin.setApellidos("Morales García");
		admin.setDni("77889911H");
		admin.setEmail("admin@gmail.com");
		admin.setFechaNacimiento(LocalDate.now().minusYears(35));
		admin.setTelefono("654817364");
		admin.setNum_seg_social("");
		admin.setUsuario(u);
	
		assertThrows(DuplicatedUsernameException.class, () -> adminService.saveAdministrador(admin));

	}
	
	@Test
	@Transactional
	void shouldNotInsertAdministradorPasswordInvalido() { //Password invalid
		User u = new User();
		u.setUsername("adminprueba2");
		u.setPassword("Prueba");
		u.setEnabled(true);
		Administrador admin = new Administrador();
		
		admin.setNombre("Administrador de prueba");
		admin.setApellidos("Morales García");
		admin.setDni("77889911H");
		admin.setEmail("admin@gmail.com");
		admin.setFechaNacimiento(LocalDate.now().minusYears(35));
		admin.setTelefono("654817364");
		admin.setNum_seg_social("123456789000");
		admin.setUsuario(u);
	
		assertThrows(InvalidPasswordException.class, () -> adminService.saveAdministrador(admin));

	}
	
	@Test
	@Transactional
	void shouldNotInsertAdministrador() { //ConstraintViolation
		User u = new User();
		u.setUsername("adminprueba1");
		u.setPassword("Prueba123");
		u.setEnabled(true);
		Administrador admin = new Administrador();
		
		admin.setNombre("Administrador de prueba");
		admin.setApellidos("Morales García");
		admin.setDni("77882911H");
		admin.setEmail("admin@gmail.com");
		admin.setFechaNacimiento(LocalDate.now().minusYears(35));
		admin.setTelefono("651817364");
		admin.setNum_seg_social("");
		admin.setUsuario(u);
	
		assertThrows(ConstraintViolationException.class, () -> adminService.saveAdministrador(admin));

	}
	
	@Test
	@Transactional
	void shouldUpdateAdministrador() throws DataAccessException, NoMayorEdadEmpleadoException, InvalidPasswordException, DuplicatedUsernameException {
		Administrador a = adminService.findAdministradorByDni("77889911H").get();
		a.setDni("36283951M");
		a.getUsuario().setPassword("passdeprueba1"); //la password viene codificada de base de datos
		adminService.saveAdministrador(a);		
		assertTrue(adminService.findAdministradorByDni("36283951M").isPresent());
		assertFalse(adminService.findAdministradorByDni("36283951R").isPresent());
	}
	
	@Test
	@Transactional
	void shouldNotUpdateAdministradorInvalido() throws DataAccessException, InvalidPasswordException {
		
		Administrador a = adminService.findAdministradorByDni("77889911H").get();
		a.setDni("");
		a.getUsuario().setPassword("passdeprueba1"); //la password viene codificada de base de datos
		assertThrows(ConstraintViolationException.class, () ->{
			adminService.saveAdministrador(a);
			em.flush();
		});
		
	}
	
	@Test 
	@Transactional
	void shoulDeleteAdministrador() {
		assertEquals(admin, adminService.findAdministradorByDni("77889911H").get());
		
		adminService.delete(admin);
		
		assertFalse(adminService.findAdministradorByDni("77889911H").isPresent());

	}
	
}
