package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BalanceEconomicoMensual;

public interface BalanceEconomicoMensualRepository extends CrudRepository<BalanceEconomicoMensual, Integer>{

	List<BalanceEconomicoMensual> findAll(Sort sort);
	
	@Query("SELECT balanceEconomicoMensual FROM BalanceEconomicoMensual balanceEconomicoMensual WHERE balanceEconomicoMensual.anyo LIKE :anyo")
	List<BalanceEconomicoMensual> findBalanceEconomicoMensualByAnyo(@Param("anyo") int y) throws DataAccessException;

}