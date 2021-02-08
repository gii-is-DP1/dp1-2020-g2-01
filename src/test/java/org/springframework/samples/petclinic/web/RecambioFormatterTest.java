package org.springframework.samples.petclinic.web;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.TipoVehiculoService;
import org.springframework.samples.petclinic.web.RecambioFormatter;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RecambioFormatterTest {
	
	@Mock
	protected RecambioService recambioService;
	
	@Autowired
	protected TipoVehiculoService tipoVehiculoService;
	
	protected RecambioFormatter recambioFormatter;
	
	private Recambio r;
	
	
	@BeforeEach
	void setup() {
		
		this.recambioFormatter = new RecambioFormatter(recambioService);
	
		Recambio r1 = new Recambio();
		r1.setName("Recambio prueba 1");
		r1.setCantidadActual(5);
		TipoVehiculo tipo = tipoVehiculoService.findByTipo("COCHE").get();
		r1.setTipoVehiculo(tipo);

		recambioService.saveRecambio(r1);
		
		this.r = r1;
			
	}
	
	
	
	@Test
	void shouldPrint() {
		assertEquals("Recambio prueba 1", recambioFormatter.print(r, Locale.ENGLISH));
	}
	
	
	
	@Test
	void shouldParse() throws ParseException {
		Mockito.when(recambioService.findRecambioByName("Recambio prueba 1")).thenReturn(Optional.of(this.r));
		Recambio r2 = recambioFormatter.parse("Recambio prueba 1", Locale.ENGLISH);
		assertEquals(r, r2);
	}
	
	
	@Test
	void shouldThrowParseException() throws ParseException {
		assertThrows(ParseException.class, () -> recambioFormatter.parse("", Locale.ENGLISH));
	}
	

}
