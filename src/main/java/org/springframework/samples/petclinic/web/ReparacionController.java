package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
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
@RequestMapping("/reparaciones")
public class ReparacionController {

		
	private static final String FORMULARIO_REPARACION_FINALIZADA = "reparaciones/finalizar_confirmacion";

//	@InitBinder("reparacion")
//	public void initReparacionBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new ReparacionValidator());
//	}
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}


	@Autowired
	private ReparacionService reparacionService;
	
	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private ClienteService clienteService;

	
	@ModelAttribute("empleados")
	public List<Empleado> empleados() {
		return (List<Empleado>) this.empleadoService.findAll();
	}
	
	
	@ModelAttribute("citas")
	public List<Cita> citas() {
		return this.citaService.findCitaSinReparacion();
	}
	
	
	@GetMapping(value = "/new")
	public String crearReparacion(ModelMap model) {
		String vista = "reparaciones/editReparacion";
		model.addAttribute("reparacion", new Reparacion());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarReparacion(@RequestParam("empleados") List<Empleado> empleados, @Valid Reparacion reparacion, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("reparacion", reparacion);
			vista = "reparaciones/editReparacion";
		} else {
			try {
				reparacion.setEmpleados(empleados);
				reparacionService.saveReparacion(reparacion);
			
			} catch (FechasReparacionException e) {
				result.rejectValue("fechaEntrega", "Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida.", 
						"Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida.");
				return "reparaciones/editReparacion";
			} catch (Max3ReparacionesSimultaneasPorEmpleadoException e) {
				result.rejectValue("empleados", "Los empleados no pueden trabajar en más de 3 reparaciones simultáneas.", 
						"Los empleados no pueden trabajar en más de 3 reparaciones simultáneas.");
				return "reparaciones/editReparacion";
			}
			
			model.addAttribute("message", "Reparacion created successfully");
			vista = listadoReparaciones(model);
		}
		
		return vista;
	}
	

	
	
	@GetMapping(value = "/delete/{reparacionId}")
	public String borrarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista;
		Optional<Reparacion> op = reparacionService.findReparacionById(id);
		if(op.isPresent()) {
			reparacionService.delete(op.get());
			model.addAttribute("message", "Reparacion deleted successfully");
		} else {
			model.addAttribute("message", "Reparacion not found");
			
		}
		vista = listadoReparaciones(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{reparacionId}")
	public String editarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista = "reparaciones/editReparacion";
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.addAttribute("message", "Reparacion not found");
			vista = listadoReparaciones(model);
		} else {
			model.addAttribute("reparacion", reparacion.get());
		}
		return vista;
	}
	
	
	
	@GetMapping(value = { "/listadoReparaciones" })
	public String listadoReparaciones(ModelMap model) {
		String vista = "reparaciones/listadoReparaciones";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		List<Reparacion> reparaciones = null;
		if(cliente.isPresent()) {
			reparaciones = reparacionService.findReparacionesCliente(cliente.get());
			model.put("reparaciones", reparaciones);
		}else {
			reparaciones = (List<Reparacion>) reparacionService.findAll();
			model.put("reparaciones", reparaciones);
		}
		model.put("reparaciones", reparaciones);
		return vista;
	}
	
	
	
	@GetMapping(value="/finalizar/{reparacionId}")
	public String initFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		Reparacion reparacion = reparacionService.findReparacionById(id).get();
		List<LineaFactura> lineaFactura = reparacion.getLineaFactura();
		Factura factura = new Factura();
		factura.setDescuento(0.0);
		factura.setFechaPago(LocalDate.now());
		factura.setLineaFactura(lineaFactura);
		model.addAttribute("factura",factura);
		model.addAttribute("reparacion", reparacion);
		return FORMULARIO_REPARACION_FINALIZADA;
	}
	
	@PostMapping(value="/finalizarReparacion/{reparacionId}")
	public String processFinalizarReparacion(@PathVariable("reparacionId") int id, @Valid Factura factura, ModelMap model) {
		String vista = "";
		Reparacion rep = this.reparacionService.findReparacionById(id).get();
		try {
			reparacionService.finalizar(rep);
			facturaService.saveFactura(factura);
			model.addAttribute("message", "Reparación "+rep.getId()+" finalizada correctamente");

		}catch(Exception e){
			model.addAttribute("message", "Error inesperado al finalizar la reparación "+rep.getId());
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoReparaciones(model);
	
		return vista;
	}
	

}
