package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.TipoCitaService;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private EmpleadoService empleadoService;
	
	@Autowired
	protected EntityManager em;
	
	@GetMapping(value="")
	public String listado(ModelMap model) {
		return "redirect:/citas/listadoCitas";
	}
	
	@GetMapping(value = { "/listadoCitas"})
	public String listadoCitas(ModelMap model) {
		String vista = "";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			List<Cita> citas = citaService.findByCliente(cliente.get());
			Comparator<Cita> ordenarPorFechaYHora = Comparator.comparing(Cita::getFecha)
					.thenComparing(Comparator.comparing(Cita::getHora));
			model.put("citas", citas.stream().sorted(ordenarPorFechaYHora).collect(Collectors.toList()));
			vista = "citas/listadoCitas";
		}else {
			List<Cita> citas = citaService.findAll();
			Comparator<Cita> ordenarPorFechaYHora = Comparator.comparing(Cita::getFecha)
					.thenComparing(Comparator.comparing(Cita::getHora));
			model.put("citas", citas.stream().sorted(ordenarPorFechaYHora).collect(Collectors.toList()));
			vista = "citas/listadoCitas";
		}
		return vista;
	}
	
	@PostMapping(value="/save/{citaId}")
	public String saveCita(@PathVariable("citaId") Integer id, @Valid Cita cita, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("vehiculos", vehiculoService.getVehiculosSeleccionadoPrimero(cita));
			model.addAttribute("tipos", tipoCitaService.findAll());
			model.addAttribute("citas", citaService.findAll());
			if(id != 0) {
				cita.setId(id);
			}
			model.addAttribute("cita", cita);
			vista = CREATE_OR_UPDATE_FORM;
		}else {
			if(id != 0) {
				cita.setId(id);
			}
			citaService.saveCita(cita);
			model.addAttribute("message", "Cita guardada successfully");
			vista = listadoCitas(model);
		}
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String crearCita(ModelMap model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String vista = "citas/editCita";
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cliente.get()));
			model.addAttribute("tipos", tipoCitaService.findAll());
			model.addAttribute("citas", citaService.findAll());
			model.addAttribute("cita", new Cita());
		}else {
			model.addAttribute("message", "Debes haber iniciado sesión como cliente");
			vista = listadoCitas(model);			
		}
		return vista;
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
			if(cita.getFecha().isBefore(LocalDate.now())) {
				model.addAttribute("message", "No puedes modificar una cita que ya ha pasado");
				model.addAttribute("messageType", "warning");
				vista = listadoCitas(model);
			}else {
				model.addAttribute("vehiculos", vehiculoService.getVehiculosSeleccionadoPrimero(cita));
				model.addAttribute("tipos", tipoCitaService.findAll());
				model.addAttribute("citas", citaService.findAll());
				model.addAttribute("cita", cita);
				vista = "citas/editCita";
			}
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
			if(cita.getFecha().isBefore(LocalDate.now())) {
				model.addAttribute("message", "No puedes modificar una cita que ya ha pasado");
				model.addAttribute("messageType", "warning");
				vista = listadoCitas(model);
			}else {
				citaService.delete(cita);
				model.addAttribute("message", "Cita borrado con éxito.");
				vista = listadoCitas(model);
			}
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
	
	@GetMapping(value="/atender/{citaId}")
	public String addEmpleadoACita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = listadoCitas(model);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
		if(empleado.isPresent()) {
			Optional<Cita> cita = citaService.findCitaById(id);
			if(cita.isPresent()) {
				Cita c = cita.get();
				if(!c.getEmpleados().contains(empleado.get())) {
					c.getEmpleados().add(empleado.get());
					citaService.saveCita(c);
					model.put("message", "Te has unido correctamente");
				}else {
					model.put("message", "Ya estás atendiendo a la cita");	
					model.put("messageType", "warning");				
				}
			}else {
				model.put("message", "Cita no encontrada");
				model.put("messageType", "warning");
			}
		}else {
			model.put("message", "No has iniciado sesión como empleado");
			model.put("messageType", "warning");
		}
		return vista;
	}
	
	@GetMapping(value="/noAtender/{citaId}")
	public String eliminarEmpleadoDeCita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = listadoCitas(model);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
		if(empleado.isPresent()) {
			Optional<Cita> cita = citaService.findCitaById(id);
			if(cita.isPresent()) {
				Cita c = cita.get();
				if(c.getEmpleados().contains(empleado.get())) {
					c.getEmpleados().remove(empleado.get());
					citaService.saveCita(c);
					model.put("message", "Te has quitado correctamente");
				}else {
					model.put("message", "No estabas atendiendo la cita");	
					model.put("messageType", "warning");				
				}
			}else {
				model.put("message", "Cita no encontrada");
				model.put("messageType", "warning");
			}
		}else {
			model.put("message", "Empleado no encontrado");
			model.put("messageType", "warning");
		}
		return vista;
	}
}
