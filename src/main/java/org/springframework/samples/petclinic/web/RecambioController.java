package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PedidoRecambio;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.PedidoRecambioService;
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

	
	@Autowired
	private RecambioService recambioService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private PedidoRecambioService pedidoRecambioService;
	
	
	@GetMapping("/listadoRecambiosSolicitados")
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

	@GetMapping("/pedidosRecambio/listadoPedidosRecambio")
	public String irListadoPedidos(ModelMap model) {
		Iterable<PedidoRecambio> op = pedidoRecambioService.findAll();
		model.addAttribute("pedidoRecambio",op);
		return "pedidosRecambio/listadoPedidosRecambio";
	}
	
	@GetMapping("/update/{id}")
	public String actualizarStock(@PathVariable int id, ModelMap model) {
		Optional<PedidoRecambio> opt = pedidoRecambioService.findById(id);
		if(opt.isPresent()) {
			PedidoRecambio s = opt.get();
			String nombre = s.getName();
			int cantidad = s.getCantidad();
			s.setSeHaRecibido(true);
			Optional<Recambio> opt1 = recambioService.findRecambioByNombre(nombre);
			if(opt1.isPresent()) {
				Recambio p = opt1.get();
				int cantidadActual= p.getCantidadActual();
				p.setCantidadActual(cantidadActual+cantidad);
				recambioService.saveRecambio(p);
			}else {
				model.addAttribute("message", "No existe el recambio");
			}
		} else {
			model.addAttribute("message", "No existe el pedido");
		}
		
		return irListadoPedidos(model);
	}
	
}
