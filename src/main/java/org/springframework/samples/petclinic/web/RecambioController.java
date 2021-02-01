package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class RecambioController {

	
private static final String FORMULARIO_ADD_UPDATE_SOLICITUD = "recambios/createOrUpdateSolicitud";


	@Autowired
	private RecambioService recambioService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private EmpleadoService empleadoService;
	

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
		//Esta función debería añadir los datos a PedidoRecambio automáticamente. 
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
	
	@GetMapping(value="/nuevaSolicitud")
	public String initNuevaSolicitud(ModelMap model) {
		Solicitud solicitud = new Solicitud();
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("empleados", empleadoService.findAll());
		model.addAttribute("recambios", recambioService.findAll());
		return FORMULARIO_ADD_UPDATE_SOLICITUD;
	}
	
	@PostMapping(value="/nuevaSolicitud")
	public String processNuevaSolicitud(@Valid Solicitud solicitud, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return FORMULARIO_ADD_UPDATE_SOLICITUD;
		}
		else {
			this.solicitudService.saveSolicitud(solicitud);
			return listadoRecambiosSolicitados(null, model);
		}
	}
	
	
	
	
}
