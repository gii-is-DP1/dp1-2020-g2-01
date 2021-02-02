package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	
	
	public List<Solicitud> findSolicitudesNoTerminadas() {
		return this.solicitudRepository.findSolicitudesNoTerminadas();
	}
	
	public List<Solicitud> findSolicitudesTerminadas() {
		return this.solicitudRepository.findSolicitudesTerminadas();
	}
	
	public List<Solicitud> findAll() {
		return (List<Solicitud>) this.solicitudRepository.findAll();
	}
	
	
	public void saveSolicitud(Solicitud s) {
		this.solicitudRepository.save(s);
	}
	
	public Optional<Solicitud> findById(Integer id) {
		return this.solicitudRepository.findById(id);
	}
	
	public void delete(Solicitud s) {
		this.solicitudRepository.delete(s);
	}

}
