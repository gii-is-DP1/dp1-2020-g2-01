package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters=@ComponentScan.Filter(Service.class))
class CitaTypeServiceTest {
	
	@Autowired
	protected TipoCitaService tipoCitaService;
	
	@Test
	void shouldExistTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotEquals(listTiposCita, new ArrayList<>());
	}
	
	@Test
	void shouldBeNotNullTiposCita() {
		List<TipoCita> listTiposCita = tipoCitaService.findAll();
		assertNotNull(listTiposCita);
	}
	
	@Test
	void shouldFindTipoCitaNotNull() {
		TipoCita t=tipoCitaService.findById(3).get();
		assertNotNull(t);
	}
	
	@Test
	void shouldFindTipoCita() {
		String t=tipoCitaService.findById(3).get().getTipo();
		assertEquals(t, "FRENOS");
	}
	
	

}
