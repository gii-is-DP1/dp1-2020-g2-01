package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reparaciones")
public class ReparacionController {
	
	@Autowired
	private ReparacionService reparacionService;
	
	
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
			reparacionService.saveReparacion(reparacion);
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
	

}
