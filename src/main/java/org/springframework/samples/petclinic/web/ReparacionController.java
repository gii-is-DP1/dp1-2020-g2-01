package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	
	@ModelAttribute("empleados")
	public List<Empleado> empleados() {
		return (List<Empleado>) this.empleadoService.findAll();
	}
	
	@GetMapping(value = { "/listadoReparaciones" })
	public String listadoReparaciones(ModelMap model) {
		String vista = "reparaciones/listadoReparaciones";
		Iterable<Reparacion> reparaciones = reparacionService.findAll();
		model.put("reparaciones", reparaciones);
		return vista;
		
	}
	
	
	@GetMapping(value = "/new")
	public String crearReparacion(ModelMap model) {
		String vista = "reparaciones/editReparacion";
		List<Cita> citas = citaService.findCitaSinReparacion();
		model.addAttribute("citas", citas);
		model.addAttribute("reparacion", new Reparacion());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarReparacion(@Valid Reparacion reparacion, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("reparacion", reparacion);
			vista = "reparaciones/editReparacion";
		} else {
			try {
				reparacionService.saveReparacion(reparacion);
			
			} catch (FechasReparacionException e) {
				result.rejectValue("fechaEntrega", "Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida ", 
						"Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida");
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
			List<Cita> citas = citaService.findCitaSinReparacion();
			Cita c = reparacion.get().getCita();
			c.setVehiculo(null);
			citas.add(c);
			model.addAttribute("citas", citas);
			model.addAttribute("reparacion", reparacion.get());
		}
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
			model.addAttribute("message", "Error inesperado al finalizar la reparación "+rep.getId());
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoReparaciones(model);
	
		return vista;
	}
	

}
