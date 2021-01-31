package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Solicitud;


public interface SolicitudRepository extends CrudRepository<Solicitud, Integer> {
	
	@Query("SELECT s FROM Solicitud s WHERE s.terminada = false")
	List<Solicitud> findSolicitudesNoTerminadas(); 
	
	@Query("SELECT s FROM Solicitud s WHERE s.terminada = true")
	List<Solicitud> findSolicitudesTerminadas(); 

}
