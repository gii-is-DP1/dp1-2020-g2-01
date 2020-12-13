package org.springframework.samples.petclinic.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cita;

public interface CitaRepository extends CrudRepository<Cita, Integer> {

	Cita save(Cita cita) throws DataAccessException;
	
	List<Cita> findAll() throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.fecha LIKE :fecha AND cita.hora LIKE :hora")
	Cita findCitaByFechaAndHora(@Param("fecha") LocalDate fecha, @Param("hora") Integer hora) throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.fecha LIKE :fecha")
	List<Cita> findCitaByFecha(@Param("fecha") LocalDate fecha) throws DataAccessException;
}
