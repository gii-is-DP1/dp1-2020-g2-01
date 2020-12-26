package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.service.TipoCitaService;
import org.springframework.stereotype.Component;

@Component
public class TipoCitaFormatter implements Formatter<TipoCita> {

	@Autowired
	private TipoCitaService tipoCitaService;
	
	@Override
	public String print(TipoCita object, Locale locale) {
		return object.getTipo();
	}

	@Override
	public TipoCita parse(String id, Locale locale) throws ParseException {
		return tipoCitaService.findById(Integer.valueOf(id)).get();
	}
	
	

}
