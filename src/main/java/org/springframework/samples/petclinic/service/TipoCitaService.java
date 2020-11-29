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

}
