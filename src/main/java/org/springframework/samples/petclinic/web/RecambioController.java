package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/recambios")
public class RecambioController {

	
//	private static final String FORM_CONFIRM_DELETE_RECAMBIO = "recambios/confirmar_recambio_borrado";

	@Autowired
	private RecambioService recambioService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@GetMapping(value="/listadoRecambios")
	public String listadoInventario(ModelMap model) {
		String vista="recambios/inventario";
		Iterable<Recambio> recambios = recambioService.findAll();
		model.put("recambios", recambios);
		return vista;
	}
	
//	@GetMapping(value="/delete/{recambioId}")
//	public String initDeleteRecambio(@PathVariable ("recambioId") int id, ModelMap model) {
//		String vista;
//		Recambio r = recambioService.findRecambioById(id).get();
//		model.addAttribute("recambios", r);
//		vista=FORM_CONFIRM_DELETE_RECAMBIO;
//		return vista;
//	}
//	
//	@GetMapping(value="/deleteRecambio/{recambioId}")
//	public String processDeleteVehiculo(@PathVariable("recambioId") int id, ModelMap model) {
//		String vista;
//		Recambio r = recambioService.findRecambioById(id).get();
//		System.out.println(r);
//		try {
//			recambioService.delete(r);
//			model.addAttribute("message", "Recambio borrado correctamente.");
//		}catch(Exception e) {
//			model.addAttribute("message", "Error inesperado al borrar el recambio.");
//			model.addAttribute("messageType", "danger");
//		}
//		vista=listadoInventario(model);
//		return vista;
//	}
	
	@GetMapping(value="/listadoRecambiosSolicitados")
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
	
	
	@GetMapping("/terminarSolicitud/{id}")
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
