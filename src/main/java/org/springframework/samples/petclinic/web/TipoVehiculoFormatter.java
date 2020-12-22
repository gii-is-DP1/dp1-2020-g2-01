package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.stereotype.Component;

@Component
public class TipoVehiculoFormatter implements Formatter<TipoVehiculo> {

	@Autowired
	private VehiculoService vehiculoService;
	
	
	@Override
	public String print(TipoVehiculo object, Locale locale) {
		return object.getTipo();
	}

	@Override
	public TipoVehiculo parse(String id, Locale locale) throws ParseException {
		return vehiculoService.findVehiculoTypeById(Integer.valueOf(id));
//		throw new ParseException("type not found: ", 0);
	}

}
