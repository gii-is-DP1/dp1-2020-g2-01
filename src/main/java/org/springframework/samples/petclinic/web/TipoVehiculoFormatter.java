package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.service.TipoVehiculoService;
import org.springframework.stereotype.Component;

@Component
public class TipoVehiculoFormatter implements Formatter<TipoVehiculo> {

	@Autowired
	private TipoVehiculoService tipoVehiculoService;
	
	
	@Override
	public String print(TipoVehiculo object, Locale locale) {
		return object.getTipo();
	}

	@Override
	public TipoVehiculo parse(String tipo, Locale locale) throws ParseException {
		Optional<TipoVehiculo> tipoVehiculo = tipoVehiculoService.findByTipo(tipo);
		if(tipoVehiculo.isPresent()) {
			return tipoVehiculo.get();
		}
		throw new ParseException("type not found: " + tipo, 0);
	}

}
