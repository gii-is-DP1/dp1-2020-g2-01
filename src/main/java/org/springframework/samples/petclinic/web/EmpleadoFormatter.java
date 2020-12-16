package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoFormatter implements Formatter<Empleado>{
	
	@Autowired
	private EmpleadoService empleadoService;
	
	
	@Override
	public String print(Empleado object, Locale locale) {
		return object.getNombre() + "," + object.getApellidos();
	}

	@Override
	public Empleado parse(String text, Locale locale) throws ParseException {
		List<Empleado> empleados = (List<Empleado>) empleadoService.findAll();
		String[] cadena = text.split(",");
		String nombre = cadena[0];
		String apellidos = cadena[1];
		for (Empleado e: empleados) {
			if (e.getNombre().equals(nombre) && e.getApellidos().equals(apellidos)) {
				return e;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
