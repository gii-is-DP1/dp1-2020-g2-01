package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.stereotype.Component;

@Component
public class CitaFormatter implements Formatter<Cita> {

	@Autowired 
	private CitaService citaService;
	
	
	@Override
	public String print(Cita object, Locale locale) {
		return object.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+ " a las " + object.getHora() + ":00";
	}

	@Override
	public Cita parse(String text, Locale locale) throws ParseException {
		String[] cadena = text.split(" a las ");
		LocalDate fecha = LocalDate.parse(cadena[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer hora = Integer.valueOf(cadena[1].replace(":00", ""));
		return citaService.findCitaByFechaAndHora(fecha, hora);
	}

}
