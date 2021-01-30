package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recambios")
public class RecambioController {

	
	@Autowired
	private RecambioService recambioService;
	
	
	
	@GetMapping("/listadoRecambiosSolicitados")
	public String listadoRecambiosSolicitados(ModelMap model) {
		String vista = "recambios/listadoRecambiosSolicitados";
		//
		//falta una consulta sobre las solicitudes de recambios
		//
			
		//model.addAttribute("solicitudes", solicitudes);
			
		return vista;
		
	}
	
	
	
	
}
