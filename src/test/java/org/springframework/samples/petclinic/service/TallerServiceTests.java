package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TallerServiceTests {
	
	@Autowired
	protected TallerService tallerService;
	
	@Autowired
	protected EntityManager em;
	
	public Taller t;
	
	
	@BeforeEach
	void insertTaller() {
		Taller t = new Taller();
		t.setName("Taller de prueba");
		t.setCorreo("prueba@gmail.com");
		t.setTelefono("666666666");
		t.setUbicacion("Calle Prueba, número 2");
		tallerService.saveTaller(t);
		this.t = t;
	}
	
	@Test
	void shouldFindTaller() {
		assertEquals(t, tallerService.findById(t.getId()).get());
		
	}
	
	@Test
	void shouldNotFindTaller() {
		tallerService.delete(t);
		assertThrows(NoSuchElementException.class, () -> tallerService.findById(t.getId()).get());
		
	}
	
	@Test 
	void shouldUpdateTaller() {
		Taller t2 = new Taller();
		t2.setName("Taller de prueba diferente");
		t2.setCorreo("pruebadiferente@gmail.com");
		t2.setTelefono("666666667");
		t2.setUbicacion("Calle Prueba diferente, número 2");
		t2.setId(t.getId());
		
		tallerService.saveTaller(t2);
		
		assertEquals(t2, tallerService.findById(t.getId()).get());
		
	}
	
	@Test 
	void shouldNotUpdateTaller() {
		Taller t2 = new Taller();
		t2.setName("");
		t2.setCorreo("pruebadiferente@gmail.com");
		t2.setTelefono("666666667");
		t2.setUbicacion("Calle Prueba diferente, número 2");
		t2.setId(t.getId());
		
		tallerService.saveTaller(t2);
		
		assertThrows(ConstraintViolationException.class, () ->{
			tallerService.saveTaller(t2);
			em.flush();
		});
		
	}
	
	@Test
	void shouldDeleteTaller() throws DataAccessException {
		tallerService.delete(t);
		assertThrows(NoSuchElementException.class, () -> tallerService.findById(t.getId()).get());
	}
	
	

}
