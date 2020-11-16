package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cita;

public interface CitaRepository extends CrudRepository<Cita, Integer> {

	Cita save(Cita cita) throws DataAccessException;
	
	Iterable<Cita> findAll() throws DataAccessException;
	
	@Query("SELECT DISTINCT cita FROM Cita cita WHERE cita.fecha LIKE :fecha AND cita.hora LIKE :hora")
	Cita findCitaByFechaAndHora(@Param("fecha") LocalDate fecha, @Param("hora") Integer hora) throws DataAccessException;
}
