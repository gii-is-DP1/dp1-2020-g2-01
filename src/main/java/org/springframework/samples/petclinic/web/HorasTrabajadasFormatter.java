package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.HoraTrabajada;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HorasTrabajadasFormatter implements Formatter<HoraTrabajada> {	

	@Autowired
	private EmpleadoService empleadoService;
	
	@Override
	public String print(HoraTrabajada object, Locale locale) {
		return object.getEmpleado().getNombre() + "," + object.getEmpleado().getApellidos();
	}

	@Override
	public HoraTrabajada parse(String h, Locale locale) throws ParseException {
		try {
			HoraTrabajada hora = new HoraTrabajada();
			hora.setEmpleado(empleadoParse(h, locale));
			hora.setHorasTrabajadas(0);
			hora.setPrecioHora(0.);
			hora.setTrabajoRealizado("Sin especificar");
			return hora;
		}catch(Exception e) {
			throw new ParseException("empleado (hora) not found: " + h, 0);
			
		}
		
	}
	
	public Empleado empleadoParse(String text, Locale locale) throws ParseException {
		List<Empleado> empleados = (List<Empleado>) empleadoService.findAll();
		String[] cadena = text.split(",");
		String nombre = cadena[0];
		String apellidos = cadena[1];
		for (Empleado e: empleados) {
			if (e.getNombre().equals(nombre) && e.getApellidos().equals(apellidos)) {
				return e;
			}
		}
		log.warn("Excepción: EmpleadoFormatter no ha podido pasar de " + text + " a objeto Empleado");
		throw new ParseException("type not found: " + text, 0);

	}

}
