package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.repository.TallerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TallerService {

	@Autowired
	private TallerRepository tallerRepository;
	
	@Transactional
	public void saveTaller(Taller taller) {
		tallerRepository.save(taller);
	}
	
	@Transactional
	public Iterable<Taller> findAll() {
		return tallerRepository.findAll();
	}
	
	@Transactional
	public Optional<Taller> findById(Integer id) {
		return tallerRepository.findById(id);
	}
	
	@Transactional
	public void delete(Taller taller) {
		tallerRepository.delete(taller);
	}
}
