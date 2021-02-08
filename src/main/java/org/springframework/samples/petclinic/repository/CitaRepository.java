package org.springframework.samples.petclinic.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;

public interface CitaRepository extends CrudRepository<Cita, Integer> {
	
	List<Cita> findAll(Sort sort) throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.fecha LIKE :fecha AND cita.hora LIKE :hora")
	Cita findCitaByFechaAndHora(@Param("fecha") LocalDate fecha, @Param("hora") Integer hora) throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.fecha LIKE :fecha")
	List<Cita> findCitasByFecha(@Param("fecha") LocalDate fecha) throws DataAccessException;
	
	@Query(value = "SELECT id FROM CITAS EXCEPT (SELECT CITA_ID FROM REPARACIONES)", nativeQuery = true)
	List<Integer> findCitaIdSinReparacion();

	@Query("SELECT cita FROM Cita cita WHERE (cita.vehiculo.cliente LIKE :cliente) AND (cita.fecha > :today)")
	List<Cita> findCitaByClienteAndFechaAfter(@Param("cliente") Cliente cliente, @Param("today") LocalDate today, Sort sort);

	List<Cita> findCitaByTallerUbicacionAndFechaAfter(String ubicacion, LocalDate today, Sort sort) throws DataAccessException;
	
	@Query("SELECT cita FROM Cita cita WHERE cita.vehiculo.cliente.user.username LIKE :username")
	List<Cita> findByUsername(@Param("username") String username, Sort sort);

	List<Cita> findCitasByFechaAfter(LocalDate today, Sort sort);

	List<Cita> findCitasByFechaBefore(LocalDate today, Sort sort);

	List<Cita> findCitasByFechaEquals(LocalDate now, Sort by);

	List<Cita> findCitaByTallerUbicacionAndFechaEquals(String ubicacion, LocalDate now, Sort sort);

	List<Cita> findCitaByTallerUbicacionAndFechaBefore(String ubicacion, LocalDate now, Sort by);

	List<Cita> findCitaByVehiculoClienteAndFechaBefore(Cliente cliente, LocalDate now, Sort by);

	List<Cita> findCitaByVehiculoClienteAndFechaEquals(Cliente cliente, LocalDate now, Sort by);
	
	List<Cita> findCitaByTallerUbicacionAndFechaAfterOrFechaEquals(String ubicacion, LocalDate now, LocalDate now2);
	
}
