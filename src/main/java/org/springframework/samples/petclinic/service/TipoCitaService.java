package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.repository.TipoCitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoCitaService {
	
	@Autowired
	private TipoCitaRepository tipoCitaRep;
	
	@Transactional
	public List<TipoCita> findAll() {
		return tipoCitaRep.findAll();
	}
	
	@Transactional
	public Optional<TipoCita> findById(Integer id) {
		return tipoCitaRep.findById(id);
	}

//	@Transactional
//	public List<TipoCita> geTiposCitaSeleccionadoPrimero(Cita cita) {
//		Integer tipoCitaId = cita.getTipoCita().getId();
//		TipoCita tipo = tipoCitaRep.findById(tipoCitaId).get();
//		List<TipoCita> tipos = tipoCitaRep.findAll();
//		tipos.remove(tipo);
//		tipos.add(0, tipo);
//		return tipos;
//	}

}
