package org.springframework.samples.petclinic.web;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
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
	private static final String FORMULARIO_CITA_COVID = "citas/covid_confirmation";
	
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
	private ClienteService clienteService;
	
	@Autowired
	protected EntityManager em;
	
	@GetMapping(value="")
	public String listado(ModelMap model) {
		return "redirect:/citas/listadoCitas";
	}
	
	@GetMapping(value = { "/listadoCitas"})
	public String listadoCitas(ModelMap model) {
		String vista = "citas/listadoCitas";
		List<Cita> citas = citaService.findAll();
		Comparator<Cita> ordenarPorFechaYHora = Comparator.comparing(Cita::getFecha)
				.thenComparing(Comparator.comparing(Cita::getHora));
		model.put("citas", citas.stream().sorted(ordenarPorFechaYHora).collect(Collectors.toList()));
		return vista;
	}
	
	@GetMapping(value = { "/listadoCitas/{username}"})
	public String listadoCitasCliente(@PathVariable(value="username") String username, ModelMap model) { // Tiene que mostrar solo las citas del cliente
		String vista = "";
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			List<Cita> citas = citaService.findByCliente(cliente.get());
			Comparator<Cita> ordenarPorFechaYHora = Comparator.comparing(Cita::getFecha)
					.thenComparing(Comparator.comparing(Cita::getHora));
			model.put("citas", citas.stream().sorted(ordenarPorFechaYHora).collect(Collectors.toList()));
			vista = "citas/listadoCitas";
		}else {
			vista = "redirect:/login";
		}
		return vista;
	}
	
	private String saveCita(Cita cita, BindingResult result, ModelMap model, String mensaje, String username) {
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
			model.addAttribute("message", "Cita " + mensaje + " successfully");
			vista = listadoCitasCliente(username, model);
		}
		return vista;
	}
	
	@GetMapping(value = "/new/{username}")
	public String crearCita(@PathVariable String username, ModelMap model) {
		String vista = "citas/editCita";
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cliente.get()));
			model.addAttribute("tipos", tipoCitaService.findAll());
			model.addAttribute("citas", citaService.findAll());
			model.addAttribute("cita", new Cita());
		}else {
			vista = "redirect:/login";			
		}
		return vista;
	}
	
	@PostMapping(value="/new/{username}")
	public String guardarCita(@PathVariable String username, @Valid Cita cita, BindingResult result, ModelMap model) {
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			if(!cita.getVehiculo().getCliente().equals(cliente.get())) {
				model.addAttribute("message", "cliente erroneo");
				model.addAttribute("messageType", "warning");
				return "/new/" + username;
			}
		}
		return saveCita(cita, result, model, "created", username);
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
			model.addAttribute("vehiculos", vehiculoService.getVehiculosSeleccionadoPrimero(cita));
			model.addAttribute("tipos", tipoCitaService.geTiposCitaSeleccionadoPrimero(cita));
			model.addAttribute("citas", citaService.findAll());
			model.addAttribute("cita", cita);
			vista = "citas/editCita";
		}
		return vista;
	}
  
	@PostMapping(value="/update/{citaId}")
	public String updateCitaPost(@Valid Cita cita, @PathVariable("citaId") int id, BindingResult result, ModelMap model) {
		cita.setId(id);  // #### A la hora de hacer un update con un error no pasa por aquí y salta el fallo directamente
		return saveCita(cita, result, model, "updated", cita.getVehiculo().getCliente().getUser().getUsername());
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
	
	@GetMapping(value="/covid")
	public String initDeleteCitasCOVID(ModelMap model) {
		return FORMULARIO_CITA_COVID;
	}
	
	@GetMapping(value="/eliminarCitasCovid")
	public String processDeleteCitasCovid(ModelMap model) {
		String vista = "";
		try {
			citaService.deleteCOVID();
			model.addAttribute("message", "Citas canceladas correctamente");

		}catch(Exception e){
			model.addAttribute("message", "Error inesperado al cancelar las citas");
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoCitas(model);
	
		return vista;
	}
}
