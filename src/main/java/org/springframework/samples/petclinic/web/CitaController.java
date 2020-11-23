package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
public class CitaController {
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@GetMapping(value = { "/listadoCitas"})
	public String listadoCitas(ModelMap model) {
		String vista = "citas/listadoCitas";
		Iterable<Cita> citas = citaService.findAll();
		model.put("citas", citas);
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String crearCita(ModelMap model) {
		String vista = "citas/editCita";
		model.addAttribute("vehiculos", vehiculoService.findAll());
		model.addAttribute("cita", new Cita());
		return vista;
	}
	
	@PostMapping(value="/save")
	public String guardarCita(Cita cita, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("cita", cita);
			vista = "citas/editCita";
		}else {
			Integer vehiculoId = cita.getVehiculo().getId();
			Vehiculo vehiculo = vehiculoService.findVehiculoById(vehiculoId).get();
			cita.setVehiculo(vehiculo);
			citaService.saveCita(cita);
			model.addAttribute("message", "Cita created successfully");
			vista = listadoCitas(model);
		}
		return "redirect:/" + vista;
	}
	
	@GetMapping(value="/update/{citaId}")
	public String updateCita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = "";
		Optional<Cita> c = citaService.findCitaById(id);
		if(!c.isPresent()) {
			model.addAttribute("message", "Cita not found");
			vista = listadoCitas(model);
		}else {
			Cita cita = c.get();
			Vehiculo vehiculo = cita.getVehiculo();
			List<Vehiculo> vehiculos = vehiculoService.findAll();
			vehiculos.remove(vehiculo); // Con esto el vehículo de la cita aparece el primero en el desplegable
			vehiculos.add(0, vehiculo); // para que esté seleccionado por defecto
			cita.getVehiculo().setCitas(null); // Evita stackOverflowError
			model.addAttribute("vehiculos", vehiculos);
			model.addAttribute("cita", cita);
			vista = "citas/editCita";
		}
		return vista;
	}
	
	@GetMapping(value="/delete/{citaId}")
	public String deleteCita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = "";
		Optional<Cita> c = citaService.findCitaById(id);
		if(!c.isPresent()) {
			model.addAttribute("message", "Cita not found");
			vista = listadoCitas(model);
		}else {
			Cita cita = c.get();
			citaService.delete(cita);
			model.addAttribute("message", "Cita borrado con éxito.");
			vista = listadoCitas(model);
		}
		return vista;
	}
}
