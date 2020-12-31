package org.springframework.samples.petclinic.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;

public interface CitaRepository extends CrudRepository<Cita, Integer> {

	Cita save(Cita cita) throws DataAccessException;
	
	List<Cita> findAll() throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.fecha LIKE :fecha AND cita.hora LIKE :hora")
	Cita findCitaByFechaAndHora(@Param("fecha") LocalDate fecha, @Param("hora") Integer hora) throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.fecha LIKE :fecha")
	List<Cita> findCitaByFecha(@Param("fecha") LocalDate fecha) throws DataAccessException;
	
	@Query(value = "SELECT id FROM CITAS EXCEPT (SELECT CITA_ID FROM REPARACIONES)", nativeQuery = true)
	List<Integer> findCitaIdSinReparacion();

	@Query("SELECT cita FROM Cita cita WHERE cita.vehiculo.cliente LIKE :cliente")
	List<Cita> findByUsername(@Param("cliente") Cliente cliente);

	List<Cita> findCitaByTallerUbicacion(String ubicacion) throws DataAccessException;
}
