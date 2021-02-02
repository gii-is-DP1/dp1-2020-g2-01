package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class RecambioController {


	@Autowired
	private RecambioService recambioService;
	
	@Autowired
	private SolicitudService solicitudService;

	@GetMapping(value="/recambios/inventario")
	public String listadoInventario(ModelMap model) {
		String vista="recambios/inventario";
		Iterable<Recambio> recambios = recambioService.findAll();
		model.put("recambios", recambios);
		return vista;
	}
	
	@GetMapping(value="/recambios/listadoRecambiosSolicitados")
	public String listadoRecambiosSolicitados(@RequestParam(required=false, name="terminadas") Boolean terminadas, ModelMap model) {
		String vista = "recambios/listadoRecambiosSolicitados";
		List<Solicitud> solicitudes;
		if (terminadas==null) {
			 solicitudes = this.solicitudService.findAll();
		} else if(terminadas==true) {
			solicitudes = this.solicitudService.findSolicitudesTerminadas();
		} else {
			solicitudes = this.solicitudService.findSolicitudesNoTerminadas();
		}
		model.addAttribute("solicitudes", solicitudes);	
		return vista;
	}
	

	@GetMapping("/recambios/terminarSolicitud/{id}")
	public String terminarSolicitud(@PathVariable int id, ModelMap model) {
		Optional<Solicitud> opt = solicitudService.findById(id);
		if(opt.isPresent()) {
			Solicitud s = opt.get();
			s.setTerminada(true);
			solicitudService.saveSolicitud(s);
		} else {
			model.addAttribute("message", "No existe la solicitud");
		}
		
		return listadoRecambiosSolicitados(null, model);
		
	}	
}
