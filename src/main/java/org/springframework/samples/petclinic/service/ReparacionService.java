package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.repository.ReparacionRepository;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReparacionService {
	
	private ReparacionRepository reparacionRepository;
	
	@Autowired
	public ReparacionService(ReparacionRepository reparacionRepository) {
		this.reparacionRepository = reparacionRepository;
	}
	
	@Autowired
	private SendEmailService sendEmailService;
	
//	@Autowired
//	private RecambioService recambioService;
//	
//	@Autowired
//	private LineaFacturaService lfService;
	
	
	@Transactional
	public void saveReparacion(Reparacion reparacion) throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		LocalDate fechaEntrega = reparacion.getFechaEntrega();
		LocalDate fechaRecogida = reparacion.getFechaRecogida();
		LocalDate fechaFinalizacion = reparacion.getFechaFinalizacion();
		
		if(fechaEntrega != null && fechaRecogida != null && fechaEntrega.isAfter(fechaRecogida)) {
			throw new FechasReparacionException();
		}
		
		if(fechaEntrega != null && fechaFinalizacion != null && fechaEntrega.isAfter(fechaFinalizacion)) {
			throw new FechasReparacionException();
		}
		
		if(fechaFinalizacion != null && fechaRecogida != null && fechaFinalizacion.isAfter(fechaRecogida)) {
			throw new FechasReparacionException();
		}
		
		Collection<Empleado> empleados = reparacion.getHorasTrabajadas().stream().map(x->x.getEmpleado())
																		.collect(Collectors.toList());
		for(Empleado e:empleados) {
			Integer repActivas = this.findReparacionesActivasEmpleado(e);
			if(repActivas == 3) {
				throw new Max3ReparacionesSimultaneasPorEmpleadoException();
			}
		}
		
		reparacionRepository.save(reparacion);
	}
	
	@Transactional
	public List<Reparacion> m(Empleado e){
		return reparacionRepository.findReparacionesActivasEmpleado1(e);
	}
	
	
	@Transactional(readOnly = true)
	public Iterable<Reparacion> findAll() throws DataAccessException {
		return reparacionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Reparacion> findReparacionById(int id) throws DataAccessException {
		return reparacionRepository.findById(id);
	}
	
	
	@Transactional
	public void delete(Reparacion reparacion) throws DataAccessException{
		reparacionRepository.delete(reparacion);
	}
	
	@Transactional
	public void finalizar(Reparacion reparacion) throws DataAccessException{
		LocalDate fechaActual = LocalDate.now();
		reparacion.setFechaFinalizacion(fechaActual);
		reparacionRepository.save(reparacion);
		String to = reparacion.getCita().getVehiculo().getCliente().getEmail();
		String subject = "Reparación de su vehículo finalizada";
		String content = "Estimado cliente,\nSirva este correo para informarle de que se ha finalizado "
				+ "la reparación de su vehículo "+ reparacion.getCita().getVehiculo().getModelo()
				+" con matrícula " + reparacion.getCita().getVehiculo().getMatricula() 
				+ ". Puede pasar por nuestro taller a recogerlo.\n\nGracias por confiar en nosotros,\nTaller Sevilla Customs.\n\n\nPD.: Dispone desde hoy de un plazo de 10 "
				+ "días laborales para recoger su vehículo sin coste adicional. Pasado ese tiempo, se le cobrarán 20€ por cada día fuera de plazo.";
		sendEmailService.sendEmail(to, subject, content);
		
//		int i=0;
//		while(i<reparacion.getLineaFactura().size()-1) { //¿LA ÚLTIMA LÍNEAFACTURA ES LA DE LOS DESCUENTOS Y NO TIENE EJEMPLARRECAMBIO ASOCIADO?
//			int idLf = reparacion.getLineaFactura().get(i).getId();
//			LineaFactura lf = lfService.findLineaFacturaById(idLf).get();
//			if(!lf.getEjemplarRecambio().equals(null)) {
//				Recambio recambio = lf.getEjemplarRecambio().getRecambio();
//				Integer cantActual=recambio.getCantidadActual();
//				Integer cantidadUsada = lf.getCantidad();
//				Integer cantidadSobrante = cantActual-cantidadUsada;
//				if(cantidadSobrante>0) {
//					recambio.setCantidadActual(cantidadSobrante);
//					recambioService.saveRecambio(recambio);
//				}
//			}
//			i++;
//		}
	}

	@Transactional(readOnly = true)
	public List<Reparacion> findReparacionesCliente(Cliente cliente) {
		return reparacionRepository.findReparacionesByCliente(cliente);
	}
	
	
	//Devuelve el número de reparaciones no finalizadas asociadas a dicho empleado
	@Transactional(readOnly = true) 
	public Integer findReparacionesActivasEmpleado(Empleado e) {
		return reparacionRepository.findReparacionesActivasEmpleado(e);
	}
	

	

}
