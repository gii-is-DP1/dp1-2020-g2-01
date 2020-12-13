package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.TipoCitaService;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
public class CitaController {
	
	public static final String CREATE_OR_UPDATE_FORM = "citas/editCita";
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private TipoCitaService tipoCitaService;
	
	@Autowired
	protected EntityManager em;
	
	@GetMapping(value="")
	public String listado(ModelMap model) {
		return listadoCitas(model);
	}
	
	@GetMapping(value = { "/listadoCitas"})
	public String listadoCitas(ModelMap model) {
		String vista = "citas/listadoCitas";
		Iterable<Cita> citas = citaService.findAll();
		model.put("citas", citas);
		return vista;
	}
	
	private String saveCita(Cita cita, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			cita.getVehiculo().setCitas(null); // Evita stackOverflowError
			cita.getVehiculo().setCliente(null);
			model.addAttribute("vehiculos", vehiculoService.getVehiculosSeleccionadoPrimero(cita));
			model.addAttribute("tipos", tipoCitaService.geTiposCitaSeleccionadoPrimero(cita));
			vista = CREATE_OR_UPDATE_FORM;
		}else {
			Integer vehiculoId = cita.getVehiculo().getId();
			Vehiculo vehiculo = vehiculoService.findVehiculoById(vehiculoId).get();
			cita.setVehiculo(vehiculo);
			
			Integer tipoCitaId = cita.getTipoCita().getId();
			TipoCita tipoCita = tipoCitaService.findById(tipoCitaId).get();
			cita.setTipoCita(tipoCita);
			
			citaService.saveCita(cita);
			model.addAttribute("message", "Cita created successfully");
			vista = listadoCitas(model);
		}
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String crearCita(ModelMap model) {
		String vista = "citas/editCita";
		model.addAttribute("vehiculos", vehiculoService.findAll());
		model.addAttribute("tipos", tipoCitaService.findAll());
		model.addAttribute("cita", new Cita());
		return vista;
	}
	
	@PostMapping(value="/new")
	public String guardarCita(@Valid Cita cita, BindingResult result, ModelMap model) {
		return saveCita(cita, result, model);
	}
	
	@GetMapping(value="/update/{citaId}")
	public String updateCita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = "";
		Optional<Cita> c = citaService.findCitaById(id);
		if(!c.isPresent()) {
			model.addAttribute("message", "Cita not found");
			model.addAttribute("messageType", "warning");
			vista = listadoCitas(model);
		}else {
			Cita cita = c.get();
			cita.getVehiculo().setCitas(null); // Evita stackOverflowError
			cita.getVehiculo().setCliente(null);
			model.addAttribute("vehiculos", vehiculoService.getVehiculosSeleccionadoPrimero(cita));
			model.addAttribute("tipos", tipoCitaService.geTiposCitaSeleccionadoPrimero(cita));
			model.addAttribute("cita", cita);
			vista = "citas/editCita";
		}
		return vista;
	}
	
	@PostMapping(value="/update/{citaId}")
	public String updateCitaPost(@Valid Cita cita, @PathVariable("citaId") int id, BindingResult result, ModelMap model) {
		cita.setId(id);  // #### A la hora de hacer un update con un error no pasa por aquí y salta el fallo directamente
		return saveCita(cita, result, model);
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
