package org.springframework.samples.petclinic.service;

import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.model.BalanceEconomicoMensual;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.repository.BalanceEconomicoMensualRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BalanceEconomicoMensualService {

	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private FacturaRecambioService fRecambioService;
	
	@Autowired 
	private BalanceEconomicoMensualRepository bemRepository;
	
	@Transactional
	public void  saveBalanceEconomicoMensual(BalanceEconomicoMensual bem) throws DataAccessException{
		this.bemRepository.save(bem);	
		log.info("Balance Economico Mensual creado");
	}
	
	@Transactional(readOnly = true)
	public List<BalanceEconomicoMensual> findAll() throws DataAccessException {
		return bemRepository.findAll(Sort.by(Sort.Direction.DESC, "anyo", "mes"));
	}
	
	@Transactional
	public void cargaDatos() throws DataAccessException{
		this.bemRepository.deleteAll();
		List<Month> meses = this.getMesesConjuntos();
		List<Integer> anyos = this.getAnyosConjuntos();
		for(int anyo:anyos) {
			for(Month mes:meses) {
				List<Factura> facturasMes = this.facturaService.findFacturasMesAnyo(mes, anyo);
				List<FacturaRecambio> facturaRecambioMes = this.fRecambioService.findFacturasRecambioMesAnyo(mes, anyo);
				BalanceEconomicoMensual bem = new BalanceEconomicoMensual();
				bem.setAnyo(anyo);
				bem.setMes(mes);
				bem.setFactura(facturasMes);
				bem.setFacturaRecambio(facturaRecambioMes);
				this.saveBalanceEconomicoMensual(bem);
			}
		}
		log.info("Datos Balance Económico Mensual cargado correctamente.");

	}
	
	@Transactional(readOnly = true)
	public List<Month> getMesesConjuntos(){
		Set<Month> meses = new HashSet<>();
		List<Month> mesF = this.facturaService.getMesesFactura();
		for(Month mes:mesF) {
			meses.add(mes);
		}
		List<Month> mesFR = this.fRecambioService.getMesesFacturaRecambio();
		for(Month mes:mesFR) {
			meses.add(mes);
		}
		return meses.stream().collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<Integer> getAnyosConjuntos(){
		Set<Integer> anyos = new HashSet<>();
		List<Integer> anyoF = this.facturaService.getAnyosFactura();
		for(int anyo:anyoF) {
			anyos.add(anyo);
		}
		List<Integer> anyoFR = this.fRecambioService.getAnyosFacturaRecambio();
		for(int anyo:anyoFR) {
			anyos.add(anyo);
		}
		return anyos.stream().collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public List<BalanceEconomicoMensual> findByAnyo(int y){
		return this.bemRepository.findBalanceEconomicoMensualByAnyo(y);
	}
}
