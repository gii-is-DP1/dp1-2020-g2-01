package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SolicitudService {
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	
	@Transactional(readOnly = true)
	public List<Solicitud> findSolicitudesNoTerminadas() {
		return this.solicitudRepository.findSolicitudesNoTerminadas();
	}
	
	@Transactional(readOnly = true)
	public List<Solicitud> findSolicitudesTerminadas() {
		return this.solicitudRepository.findSolicitudesTerminadas();
	}
	
	@Transactional(readOnly = true)
	public List<Solicitud> findAll() {
		return (List<Solicitud>) this.solicitudRepository.findAll();
	}
	
	@Transactional
	public void saveSolicitud(Solicitud s) {
		this.solicitudRepository.save(s);
		log.info("Solicitud creada");
		
	}
	
	@Transactional(readOnly = true)
	public Optional<Solicitud> findById(Integer id) {
		return this.solicitudRepository.findById(id);
	}
	
	@Transactional
	public void delete(Solicitud s) {
		this.solicitudRepository.delete(s);
		log.info("Solicitud con id " + s.getId() + " borrada");
	}

}
