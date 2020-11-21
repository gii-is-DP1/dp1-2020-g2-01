package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
}
