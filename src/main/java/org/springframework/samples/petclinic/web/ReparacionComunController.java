package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ReparacionComun;
import org.springframework.samples.petclinic.service.ReparacionComunService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("reparacionesComunes")
public class ReparacionComunController {
	
	@Autowired
	private ReparacionComunService reparacionComunSc;
	
	@GetMapping(value = "/listadoRepCom")
	public String listadoRepCom(ModelMap m) {
		String vista = "comunes/listadoRepCom";
		Iterable<ReparacionComun> comunes = reparacionComunSc.findAll();
		m.addAttribute("comunes", comunes);
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String crearRepCom(ModelMap model) {
		String vista = "comunes/editRepCom";
		model.addAttribute("repCom", new ReparacionComun());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarRepCom(@Valid ReparacionComun repCom, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("repCom", repCom);
			vista = "comunes/editRepCom";
		} else {
			reparacionComunSc.saveReparacionComun(repCom);
			model.addAttribute("message", "Reparación común creada correctamente");
			vista = listadoRepCom(model);
		}
		return vista;
	}
	
	@GetMapping(value = "/delete/{repComId}")
	public String borrarRepCom(@PathVariable("repComId") int id, ModelMap m) {
		String vista = "comunes/editRepCom";
		Optional<ReparacionComun> op = reparacionComunSc.findById(id);
		if(op.isPresent()) {
			reparacionComunSc.delete(op.get());
			m.addAttribute("message", "Reparacion común borrada correctamente");
		} else {
			m.addAttribute("message", "Reparación común no encontrada");
			
		}
		vista = listadoRepCom(m);
		return vista;
	}
	
	@GetMapping(value = "/update/{repComId}")
	public String editarRepCom(@PathVariable("repComId") int id, ModelMap model) {
		String vista = "comunes/editRepCom";
		Optional<ReparacionComun> op = reparacionComunSc.findById(id);
		if(!op.isPresent()) {
			model.addAttribute("message", "Reparación común no encontrada");
			vista = listadoRepCom(model);
		} else {
			model.addAttribute("repCom", op.get());
		}
		return vista;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET) 
	public String displayRepCom(ModelMap model) { 
	    model.addAttribute("repCom", new ReparacionComun()); 
	    return "repCom"; 
	}
	
//	@GetMapping(value = "")
//	public String initFindForm(Map<String, Object> model) {
//		model.put("repCom", new ReparacionComun());
//		return "comunes/listadoRepCom";
//	}
	
	@PostMapping(value="")
	public String processFindForm(ReparacionComun repCom, BindingResult result, Map<String, Object> model) {

		if(result.hasErrors()) {
			return "/comunes/listadoRepCom";
		}
		
		if (repCom.getNombre() == null||repCom.getNombre()=="") {
			model.put("comunes", reparacionComunSc.findAll());
			return "comunes/listadoRepCom";
		}

		Collection<ReparacionComun> results = this.reparacionComunSc.findRepComByNombre(repCom.getNombre());
		if (results.isEmpty()) {
			
			return "comunes/listadoRepCom";
		}
		
		else {
			model.put("comunes", results);
			return "comunes/listadoRepCom";
		}
	}
	
	@GetMapping(value="show/{repComId}")
	public String showRepCom(@PathVariable("repComId") int id, ModelMap model) {
		String vista = "comunes/mostrarRepCom";
		Optional<ReparacionComun> op = reparacionComunSc.findById(id);
		if(!op.isPresent()) {
			model.addAttribute("message", "Reparación común no encontrada");
			vista = listadoRepCom(model);
		} else {
			model.addAttribute("repCom", op.get());
		}
		return vista;
	}

}
