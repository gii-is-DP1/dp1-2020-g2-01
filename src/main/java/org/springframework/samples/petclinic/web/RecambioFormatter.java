package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecambioFormatter implements Formatter<Recambio> {
	
	
	
	private RecambioService recambioService;
	
	@Autowired
	public RecambioFormatter(RecambioService recambioService) {
		this.recambioService = recambioService;
	}

	@Override
	public String print(Recambio object, Locale locale) {
		return object.getName();
	}

	@Override
	public Recambio parse(String text, Locale locale) throws ParseException {				
		Optional<Recambio> r = recambioService.findRecambioByName(text);
		if(r.isPresent()) {
			return r.get();
		} 
		log.warn("Excepción: RecambioFormatter no ha podido pasar de " + text + " a objeto Recambio");
		throw new ParseException("Recambio no encontrado: " + text, 0);

		
	}

}
