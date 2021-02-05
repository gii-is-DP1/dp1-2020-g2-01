package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.PedidoRecambio;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.PedidoRecambioService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.samples.petclinic.service.SolicitudService;
import org.springframework.samples.petclinic.util.LoggedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private EmpleadoService empleadoService;
	
	@Autowired
	private PedidoRecambioService pedidoRecambioService;
	
	@Autowired
	private ReparacionService reparacionService;
	
	
	@ModelAttribute("empleados")
	public List<Empleado> empleados() {
		return (List<Empleado>) this.empleadoService.findAll();
	}
	
	
	@ModelAttribute("recambios")
	public List<Recambio> recambios() {
		return (List<Recambio>) this.recambioService.findAll();
	}
	
	
	@ModelAttribute("reparaciones")
	public List<Reparacion> reparaciones() {
		return (List<Reparacion>) this.reparacionService.findAll();
	}
	

	@GetMapping(value="/listadoRecambios")
	public String listadoInventario(ModelMap model) {
		String vista="recambios/inventario";
		Iterable<Recambio> recambios = recambioService.findAll();
		model.put("recambios", recambios);
		return vista;
	}

	
	@GetMapping(value="/listadoRecambiosSolicitados")
	public String listadoRecambiosSolicitados(@RequestParam(required=false, name="terminadas") Boolean terminadas, ModelMap model) {
		String vista = "recambios/listadoRecambiosSolicitados";
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Empleado> opt = empleadoService.findEmpleadoByUsuarioUsername(username);
		if(opt.isPresent()) {
			return "welcome";
		}
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
	

	@GetMapping("/solicitud/terminarSolicitud/{id}")
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
	
	
	
	@GetMapping("/solicitud/update/{id}")
	public String editarSolicitud(@PathVariable int id, ModelMap model) {
		String vista = "recambios/editSolicitud";
		Optional<Solicitud> solicitud = solicitudService.findById(id);
		if(!solicitud.isPresent()) {
			model.addAttribute("message", "Solicitud no encontrada");
			vista = listadoRecambiosSolicitados(null, model);
		} else {
			model.addAttribute("solicitud", solicitud.get());
		}
		return vista;
		
	}

	@GetMapping("/pedidosRecambio/listadoPedidosRecambio")
	public String irListadoPedidos(ModelMap model) {
		Iterable<PedidoRecambio> op = pedidoRecambioService.findAll();
		model.addAttribute("pedidoRecambio",op);
		return "pedidosRecambio/listadoPedidosRecambio";
	}
	

	@PostMapping("/solicitud/save")
	public String guardarSolicitud(@Valid Solicitud solicitud, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) { 
			model.addAttribute("solicitud", solicitud);
			vista = "recambios/editSolicitud";
		
		} else {
			solicitudService.saveSolicitud(solicitud);
			model.addAttribute("message", "Solicitud creada con éxito");
			vista = listadoRecambiosSolicitados(null,model);
		}
		return vista;
	}
	
	@GetMapping("/solicitud/new")
	public String crearSolicitud(ModelMap model) {
		String vista;
		Solicitud s = new Solicitud();
		String username = LoggedUser.getUsername();
		Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
		if(!empleado.isPresent()) { //si es un admin debe introducir el empleado
			vista = "recambios/editSolicitud";
		} else { //si es un empleado pone su nombre automáticamente 
			s.setEmpleado(empleado.get());
			vista = "recambios/addSolicitud";
		}
		model.addAttribute("solicitud", s);
		return vista;
		
	}
	
	
	@GetMapping("/solicitud/delete/{id}")
	public String borrarSolicitud(@PathVariable("id") int id, ModelMap model) {
		String vista;
		Optional<Solicitud> s = solicitudService.findById(id);
		if(!s.isPresent()) {
			model.addAttribute("message", "No existe la solicitud dada");
		} else {
			solicitudService.delete(s.get());
			model.addAttribute("message", "Solicitud borrada con éxito");
		}
		vista = listadoRecambiosSolicitados(null, model);
		return vista;
		
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
