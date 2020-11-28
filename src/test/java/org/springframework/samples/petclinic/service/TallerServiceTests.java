package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TallerServiceTests {
	
	@Autowired
	protected TallerService tallerService;
	
	
	@Test
	void shouldFindTalleres() {
		Collection<Taller> talleres = (Collection<Taller>) tallerService.findAll();
		
		Taller taller = EntityUtils.getById(talleres, Taller.class, 1);
		
		assertThat(taller.getName()).isEqualTo("Taller Sevilla Customs");
		assertThat(taller.getCorreo()).isEqualTo("prueba@gmail.com");
		assertThat(taller.getTelefono()).isEqualTo(666666666);
		assertThat(taller.getUbicacion()).isEqualTo("Calle Prueba, número 2");
		
	}
	
	@Test
	void shouldNotFindSpecificTaller() {
		Collection<Taller> talleres = (Collection<Taller>) tallerService.findAll();
		assertThrows(ObjectRetrievalFailureException.class, ()->EntityUtils.getById(talleres, Taller.class, 10000000));
	}
	
	

}
