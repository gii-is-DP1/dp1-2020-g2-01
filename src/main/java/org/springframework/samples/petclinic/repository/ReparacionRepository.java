package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Reparacion;

public interface ReparacionRepository extends CrudRepository<Reparacion, Integer> {

	@Query("SELECT r FROM Reparacion r WHERE r.cita.vehiculo.cliente LIKE :cliente")
	List<Reparacion> findReparacionesByCliente(@Param("cliente") Cliente cliente);

	@Query("SELECT count(r) FROM Reparacion r WHERE (r.fechaFinalizacion IS NULL) AND (:empleado IN (SELECT h.empleado FROM HoraTrabajada h WHERE h MEMBER OF r.horasTrabajadas))")
	Integer findReparacionesActivasEmpleado(@Param("empleado") Empleado empleado);

	@Query("SELECT r FROM Reparacion r WHERE (r.fechaFinalizacion IS NULL) AND (:empleado IN (SELECT h.empleado FROM HoraTrabajada h WHERE h MEMBER OF r.horasTrabajadas))")
	List<Reparacion> findReparacionByEmpleadoAndReparacionActiva(Empleado empleado);

	List<Reparacion> findAll(Sort by);

	List<Reparacion> findReparacionByCitaTallerUbicacion(String ubicacion);
}
