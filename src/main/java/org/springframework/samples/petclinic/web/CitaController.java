package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
public class CitaController {
	
	@Autowired
	private CitaService citaService;
	
	@GetMapping(value = { "/listadoCitas"})
	public String listadoCitas(ModelMap model) {
		String vista = "citas/listadoCitas";
		Iterable<Cita> citas = citaService.findAll();
		model.put("citas", citas);
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String crearVehiculo(ModelMap model) {
		String vista = "citas/editCita";
		model.addAttribute("cita", new Cita());
		return vista;
	}
	
	@PostMapping(value="/save")
	public String guardarVehiculo(@Valid Cita cita, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("cita", cita);
			vista = "citas/editCita";
		}else {
			citaService.saveCita(cita);
			model.addAttribute("message", "Cita created successfully");
			vista = listadoCitas(model);
		}
		return vista;
	}
}
