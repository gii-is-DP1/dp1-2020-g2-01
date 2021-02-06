package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.HoraTrabajada;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.HorasTrabajadasService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/horas")
public class HorasTrabajadasController {

	@Autowired
	private HorasTrabajadasService horasTrabajadasService;
	
	@Autowired
	private ReparacionService reparacionService;
	
	@Autowired
	private ReparacionController reparacionController;
	
	@GetMapping("/addHora/{reparacionId}")
	public String addHoraAReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.addAttribute("message", "Reparación no encontrada");
			model.addAttribute("messageType", "warning");
			return reparacionController.listadoReparaciones(model);
		}

		model.addAttribute("horaTrabajada", new HoraTrabajada());
		model.addAttribute("reparacion", reparacion.get());
		model.addAttribute("empleados", reparacion.get().getHorasTrabajadas().stream()
				.map(x->x.getEmpleado()).collect(Collectors.toList()));
		return "horas/editHora";
	}
	
	@PostMapping("/save/{reparacionId}")
	public String saveHora(@Valid HoraTrabajada hora, @PathVariable("reparacionId") int id, ModelMap model, BindingResult result) {
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.addAttribute("message", "Reparación no encontrada");
			model.addAttribute("messageType", "warning");
			return reparacionController.listadoReparaciones(model);
		}
		if(result.hasErrors()) {
			model.addAttribute("horaTrabajada", hora);
			model.addAttribute("reparacion", reparacion.get());
			model.addAttribute("empleados", reparacion.get().getHorasTrabajadas().stream()
					.map(x->x.getEmpleado()).distinct().collect(Collectors.toList()));
			return "horas/editHora";
		}
		
		try {
			horasTrabajadasService.save(hora);
			if(!reparacion.get().getHorasTrabajadas().stream().map(x->x.getId()).collect(Collectors.toList()).contains(hora.getId())) {
				reparacion.get().getHorasTrabajadas().add(hora);
				reparacionService.saveReparacion(reparacion.get());
			}
		}catch(Exception e) {
			log.warn("Error al crear hora" + e.getMessage());
			model.addAttribute("message", e.getMessage());
			model.addAttribute("horaTrabajada", hora);
			model.addAttribute("reparacion", reparacion.get());
			model.addAttribute("empleados", reparacion.get().getHorasTrabajadas().stream()
					.map(x->x.getEmpleado()).distinct().collect(Collectors.toList()));
			return "horas/editHora";
		}
		
		return "redirect:/reparaciones/getReparacion/" + reparacion.get().getId().toString();
	}
	
	@GetMapping("editHora/{horaId}/{reparacionId}")
	public String editHora(@PathVariable("horaId") int id, @PathVariable("reparacionId") int idRep, ModelMap model) {
		Optional<HoraTrabajada> hora = horasTrabajadasService.findById(id);
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(idRep);
		if(!hora.isPresent() || !reparacion.isPresent()) {
			model.addAttribute("message", "Hora o reparación no encontrada");
			model.addAttribute("messageType", "warning");
			return reparacionController.listadoReparaciones(model);
		}
		model.addAttribute("horaTrabajada", hora.get());
		model.addAttribute("reparacion", reparacion.get());
		model.addAttribute("empleados", reparacion.get().getHorasTrabajadas().stream()
				.map(x->x.getEmpleado()).distinct().collect(Collectors.toList()));
		return "horas/editHora";
	}
	
	@GetMapping("deleteHora/{horaId}/{reparacionId}")
	public String deleteHora(@PathVariable("horaId") int id, @PathVariable("reparacionId") int idRep, ModelMap model) throws DataAccessException, FechasReparacionException, Max3ReparacionesSimultaneasPorEmpleadoException {
		Optional<HoraTrabajada> hora = horasTrabajadasService.findById(id);
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(idRep);
		if(!hora.isPresent() || !reparacion.isPresent()) {
			model.addAttribute("message", "Hora o reparación no encontrada");
			model.addAttribute("messageType", "warning");
			return reparacionController.listadoReparaciones(model);
		}
		reparacion.get().getHorasTrabajadas().remove(hora.get());
		reparacionService.saveReparacion(reparacion.get());
		horasTrabajadasService.delete(hora.get());

		return "redirect:/reparaciones/getReparacion/" + reparacion.get().getId().toString();
	}
	
}
