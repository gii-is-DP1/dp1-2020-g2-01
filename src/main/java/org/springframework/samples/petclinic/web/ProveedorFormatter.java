package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProveedorFormatter implements Formatter<Proveedor>{
	
	@Autowired
	private ProveedorService proveedorService;

	@Override
	public String print(Proveedor object, Locale locale) {
		return object.getName();
	}

	@Override
	public Proveedor parse(String text, Locale locale) throws ParseException {
		Optional<Proveedor> op = proveedorService.findProveedorByName(text);
		if(op.isPresent()) {
			return op.get();
		}
		log.warn("Excepción: ProveedorFormatter no ha podido pasar de " + text + " a objeto Proveedor");
		throw new ParseException("Proveedor not found: " + text, 0);
	}

}
