package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.service.TallerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("talleres")
public class TallerController {
	
	@Autowired
	private TallerService tallerService;
	
	@GetMapping(value = "/listadoTalleres")
	public String listadoTalleres(ModelMap m) {
		
		String vista= "/talleres/listadoTalleres";
		Iterable<Taller> talleres = tallerService.findAll();
        m.addAttribute("taller", talleres);
		return vista;
	}
		
	@GetMapping(value = "/new")
	public String crearTaller(ModelMap model) {
		String vista = "talleres/editTaller";
		model.addAttribute("taller", new Taller());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarTaller(@Valid Taller taller, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("taller", taller);
			vista = "talleres/editTaller";
		} else {
			tallerService.saveTaller(taller);
			model.addAttribute("message", "Taller created successfully");
			vista = listadoTalleres(model);
		}
		
		return vista;
	}
	
	
	@GetMapping(value = "/delete/{tallerId}")
	public String borrarTaller(@PathVariable("tallerId") int id, ModelMap model) {
		String vista;
		Optional<Taller> op = tallerService.findById(id);
		if(op.isPresent()) {
			tallerService.delete(op.get());
			model.addAttribute("message", "Taller deleted successfully");
		} else {
			model.addAttribute("message", "Taller not found");
			
		}
		vista = listadoTalleres(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{tallerId}")
	public String editarTaller(@PathVariable("tallerId") int id, ModelMap model) {
		String vista = "talleres/editTaller";
		Optional<Taller> taller = tallerService.findById(id);
		if(!taller.isPresent()) {
			model.addAttribute("message", "Taller not found");
			vista = listadoTalleres(model);
		} else {
			model.addAttribute("taller", taller.get());
		}
		return vista;
	}
	
	@GetMapping(value="contacto")
	public String showTalleres(ModelMap model) {
		String vista = "talleres/contacto";
		Iterable<Taller> talleres = tallerService.findAll();
		model.addAttribute("talleres", talleres);
		return vista;
	}

}
