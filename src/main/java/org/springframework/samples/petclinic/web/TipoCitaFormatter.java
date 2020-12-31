package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.service.TallerService;
import org.springframework.stereotype.Component;

@Component
public class TipoCitaFormatter implements Formatter<Taller> {

	@Autowired
	private TallerService tallerService;
	
	@Override
	public String print(Taller object, Locale locale) {
		return object.getUbicacion();
	}

	@Override
	public Taller parse(String ubicacion, Locale locale) throws ParseException {
		Optional<Taller> taller = tallerService.findByUbicacion(ubicacion);
		if(!taller.isPresent()) {
			throw new ParseException("Taller not found: " + ubicacion, 0);		
		}

		return taller.get();	
		
	}
	
	

}
