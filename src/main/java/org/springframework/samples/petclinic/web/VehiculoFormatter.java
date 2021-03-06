package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.stereotype.Component;

@Component
public class VehiculoFormatter implements Formatter<Vehiculo> {

	@Autowired 
	private VehiculoService vehiculoService;
	
	
	@Override
	public String print(Vehiculo object, Locale locale) {
		return object.getModelo() + " - " + object.getMatricula();
	}

	@Override
	public Vehiculo parse(String text, Locale locale) throws ParseException {
		String[] cadena = text.split(" - ");
		String matricula = cadena[1];
		Optional<Vehiculo> v = vehiculoService.findVehiculoByMatricula(matricula);
		if(v.isPresent()) {
			return v.get();
		}
		throw new ParseException("Vehiculo not found: " + text, 0);
	}

}
