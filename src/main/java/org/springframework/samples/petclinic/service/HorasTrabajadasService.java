package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.HoraTrabajada;
import org.springframework.samples.petclinic.repository.HorasTrabajadasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HorasTrabajadasService {

	@Autowired
	private HorasTrabajadasRepository horasTrabajadasRepository;
	
	@Transactional
	public void save(HoraTrabajada horas) {
		horasTrabajadasRepository.save(horas);
	}
	
	@Transactional
	public void delete(HoraTrabajada horas) {
		horasTrabajadasRepository.delete(horas);
	}
	
	@Transactional
	public Optional<HoraTrabajada> findById(Integer id) {
		return horasTrabajadasRepository.findById(id);
	}
}
