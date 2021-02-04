package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.RecambioService;
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

import com.sun.tools.sjavac.Log;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	private CitaService citaService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private RecambioService recambiosService;
	
	@ModelAttribute("empleados")
	public List<Empleado> empleados() {
		return (List<Empleado>) this.empleadoService.findAll();
	}
	
	
	@ModelAttribute("citas")
	public List<Cita> citas() {
		return this.citaService.findCitaSinReparacion();
	}
	
	@ModelAttribute("reparaciones")
	public List<Reparacion> reparaciones() {
		return (List<Reparacion>) this.reparacionService.findAll();
	}
	
	@ModelAttribute("recambios")
	public List<Recambio> recambios() {
		return (List<Recambio>) this.recambiosService.findAll();
	}
	
	
	@GetMapping(value = "/new/{citaId}")
	public String crearReparacion(@PathVariable("citaId") int id, ModelMap model) {
		String vista = "reparaciones/editReparacion";
		try {
			Cita c = citaService.findCitaById(id);
			List<Cita> citas = citaService.findCitaSinReparacion();
			if(!citas.contains(c)) {
				model.addAttribute("message", "Esta cita ya tiene una reparación asociada");
				model.addAttribute("messageType", "warning");
				vista = listadoReparaciones(model);
			}else {
				Reparacion reparacion = new Reparacion();
				reparacion.setCita(c);
				model.addAttribute("reparacion", reparacion);
			}
		}catch(NotFoundException e) {
			log.warn("Excepción: cita no encontrada");
			model.addAttribute("message", "Cita no encontrada");
			model.addAttribute("messageType", "warning");
			vista = listadoReparaciones(model);
		}

		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarReparacion(@Valid Reparacion reparacion, BindingResult result, ModelMap model) {
		String vista;
		
		//Al hacer RequestParam con la lista vacía da null, como se añade los empleados a mano a la reparación @Valid no comprueba el @NotNull
		//de empleados
		if(result.hasErrors()) { 
//			if(empleados==null) {
//				result.rejectValue("empleados", "Se debe escoger al menos un empleado", "Se debe escoger al menos un empleado");
//			}
			model.addAttribute("reparacion", reparacion);
			vista = "reparaciones/editReparacion";
		
		} else {
			try {
//				reparacion.setEmpleados(empleados);
				reparacionService.saveReparacion(reparacion);
			
			} catch (FechasReparacionException e) {
				log.warn("Excepción: fechas incongruentes; fecha de entrega: " +reparacion.getFechaEntrega().toString(), 
						"; fecha de finalización: " + reparacion.getFechaFinalizacion().toString(),
						"; fecha de recogida: " + reparacion.getFechaRecogida().toString());
				result.rejectValue("fechaEntrega", "Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida.", 
						"Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida.");
				return "reparaciones/editReparacion";
			} catch (Max3ReparacionesSimultaneasPorEmpleadoException e) {
				log.warn("Excepción: uno de los empleados de " +  
						reparacion.getEmpleados().stream().map(x->x.getNombre() + x.getApellidos()).toArray() + " tiene 3 reparaciones simultáneas");
				result.rejectValue("empleados", "Los empleados no pueden trabajar en más de 3 reparaciones simultáneas.", 
						"Los empleados no pueden trabajar en más de 3 reparaciones simultáneas.");
				return "reparaciones/editReparacion";
			}
			
			model.addAttribute("message", "Reparacion created successfully");
			vista = verReparacion(reparacion.getId(), model);
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
		}else { //como se obliga a loguearse, si no es cliente debe ser admin, luego poder ver todas las reparaciones
			reparaciones = (List<Reparacion>) reparacionService.findAll();
			model.put("reparaciones", reparaciones);
		}
		model.put("reparaciones", reparaciones);
		return vista;
	}
	
	
	
	@GetMapping(value="/finalizar/{reparacionId}")
	public String initFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		Reparacion reparacion = reparacionService.findReparacionById(id).get();
		model.addAttribute("reparacion", reparacion);
		return FORMULARIO_REPARACION_FINALIZADA;
	}
	
	@GetMapping(value="/finalizarReparacion/{reparacionId}")
	public String processFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista = "";
		Reparacion rep = this.reparacionService.findReparacionById(id).get();
		try {
			reparacionService.finalizar(rep);
			model.addAttribute("message", "Reparación "+rep.getId()+" finalizada correctamente");

		}catch(Exception e){
			log.warn("Excepción: error inesperado al finalizar la reparación");
			model.addAttribute("message", "Error inesperado al finalizar la reparación "+rep.getId());
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoReparaciones(model);
	
		return vista;
	}
	
	@GetMapping(value="/getReparacion/{idReparacion}")
	public String verReparacion(@PathVariable("idReparacion") int id, ModelMap model) {
		String vista = "";
		Optional<Reparacion> rep = reparacionService.findReparacionById(id);
		if(rep.isPresent()) {
			model.put("reparacion", rep.get());
			vista = "reparaciones/datosReparacion";
		}else {
			model.put("message", "Reparación no encontrada");
			model.put("messageType", "warning");
			vista = listadoReparaciones(model);
		}
		return vista;
	}
	

}
